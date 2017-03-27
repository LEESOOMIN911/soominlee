package com.dbs.mcare.service.mobile.helper;

import java.text.ParseException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.dbs.mcare.exception.mobile.ApiCallException;
import com.dbs.mcare.exception.mobile.MobileControllerException;
import com.dbs.mcare.framework.service.MessageService;
import com.dbs.mcare.framework.util.DateUtil;
import com.dbs.mcare.framework.util.SessionUtil;
import com.dbs.mcare.service.PnuhConfigureService;
import com.dbs.mcare.service.api.MCareApiCallService;
import com.dbs.mcare.service.api.PnuhApi;
import com.dbs.mcare.service.api.announceArrival.AnnounceArrivalService;
import com.dbs.mcare.service.api.appointment.AppointmentService;

/**
 * API를 wrapping하기 위한 클래스 
 * 도우미 기능 쪽에서 API 호출 서비스를 직접 호출해도 되지만 한번 더 추상화 하기 위함임 
 * 
 * 
 * @author hyeeun 
 *
 */
@Service 
public class HelperApiFacade {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private final static String WEB_HELPER_PROCESS = "WEB_HELPER_PROCESS"; 
	private final static String WEB_ADMIN_CONSOLE = "WEB_ADMIN_CONSOLE"; 
	// 도우미 상단 진행선택 없도록 
	private final static int EMPTY_STAGE_ORDER = 0; 
	@Autowired 
	private MCareApiCallService apiCallService; 
	@Autowired
	private MessageService messageService; 
	@Autowired 
	private AppointmentService appointmentService; 
	@Autowired 
	private AnnounceArrivalService announceArrivalService; 
	@Autowired 
	private PnuhConfigureService configureService; 
	
	
	/**
	 * 진료를 위해 원무과에 가서 번호표 뽑으라는 push 메시지 전달 의뢰 
	 * @param request
	 */
	public void sendRequestReceiptTicketGuid(HttpServletRequest request) {		
		final String message = this.messageService.getMessage("mobile.helper.ticket", request); 
		final String pId = SessionUtil.getUserId(request); 
		final String pName = SessionUtil.getUserName(request); 
		// 맵 만들기 
		final Map<String, Object> map = this.getPushMap(this.configureService.getMcareServiceCode(), 
				pId, pName, message, 10, HelperApiFacade.WEB_ADMIN_CONSOLE);		
		
		// 보내기 
		try { 
			this.apiCallService.execute(PnuhApi.HELPERPUSH_TICKET, map); 
		}
		catch(final ApiCallException ex) {
			if(this.logger.isErrorEnabled()) { 
				this.logger.error("번호표 뽑으라는 Push 요청 실패", ex); 
			} 
		}
	}
	
	/**
	 * 오늘 예약내역을 전달 
	 * @param request
	 * @param param 메시지에 맵핑될 파라미터 
	 */
	public void sendTodayReservation(HttpServletRequest request, Object[] param) { 
		// mobile.helper.today.reservation 
		final String message = this.messageService.getMessage("mobile.helper.today.reservation", request, param); 
		final String pId = SessionUtil.getUserId(request); 
		final String pName = SessionUtil.getUserName(request); 
		// 맵 만들기 
		final Map<String, Object> map = this.getPushMap(this.configureService.getMcareServiceCode(), 
				pId, pName, message, 10, HelperApiFacade.WEB_ADMIN_CONSOLE); 		
		
		// 보내기 
		try { 
			this.apiCallService.execute(PnuhApi.HELPERPUSH_APPOINTMENTSEARCH, map); 
		}
		catch(final ApiCallException ex) { 
			if(this.logger.isErrorEnabled()) { 
				this.logger.error("오늘 예약내역 전달을 위한 Push 요청 실패", ex); 
			} 
		}
	}

	/**
	 * 차량번호 전달 
	 * @param request
	 */
	public void sendCarNo(HttpServletRequest request) { 
		final String pId = SessionUtil.getUserId(request); 
		final String pName = SessionUtil.getUserName(request); 	
		// 차량번호를 구하기 위해 환자정보를 가져옴 
		@SuppressWarnings("unchecked")
		final Map<String, Object> carMap = (Map<String, Object>) this.apiCallService.execute(PnuhApi.USER_USERINFO_GETUSERINFO, "pId", pId); 
		// 차량번호 꺼내기 
		final String vehicleNo = (String) carMap.get("vechicleNo"); 
		// 메시지 
		String message = null; 

		if(StringUtils.isEmpty(vehicleNo)) {
			message = this.messageService.getMessage("mobile.helper.nocar", request); 
		}
		else {
			message = this.messageService.getMessage("mobile.helper.carno", request, new String[]{vehicleNo});
		}
		
		// push 보낼 메시지 만들기 
		final Map<String, Object> map = this.getPushMap(this.configureService.getMcareServiceCode(), 
				pId, pName, message,
				40, HelperApiFacade.WEB_HELPER_PROCESS); 
		
		// 보내기 
		try { 
			this.apiCallService.execute(PnuhApi.HELPERPUSH_PARKING, map); 
		}
		catch(ApiCallException ex) { 
			if(this.logger.isErrorEnabled()) { 
				this.logger.error("차량번호를 알려주기 위한 Push 요청 실패", ex); 
			} 
		}
	}
	/**
	 * 다음 내원일 
	 * @param pId
	 */
	public void sendNextReservation(HttpServletRequest request) { 
		final int MAX_DEAD_LINE = 90; 
		Map<String, Object> searchMap = null; 
		Map<String, Object> nextMap = null; 
		// 다음 내원일은 예약 리스트에서 가져와야 해서 검색기간 제약이 발생함 
		int deadline = 30; 
		
		// 환자정보 
		final String pId = SessionUtil.getUserId(request); 
		final String pName = SessionUtil.getUserName(request); 
		
		// 다음 예약 검색을 위한 파라미터 
		searchMap = new HashMap<String, Object>(); 
		searchMap.put("pId", pId); 
		searchMap.put("startDt", DateUtil.getStartDate(0)); 
		searchMap.put("endDt", DateUtil.getEndDateAfter(deadline)); 
		
		// 예약일 구하기 
		while(deadline <= MAX_DEAD_LINE) { 
			try {
				// 예약조회 
				final List<Map<String, Object>> list = this.appointmentService.getList(searchMap); 
				// 값이 있나? 
				if(list != null && !list.isEmpty()) {
					nextMap = list.get(0); 
					deadline += MAX_DEAD_LINE; 
				}
				else {
					deadline += 30;
				}
			}
			catch(final MobileControllerException ex) { 
				// 시스템 오류가 발생했을때 굳이 push로 보낼 필요는 없겠지 
				if(this.logger.isErrorEnabled()) { 
					this.logger.error("다음 내원일 구하기 실패", ex);
				} 
				return; 				
			}
		}
		
		// 다음 내원일 문자 만들기 
		String message = null; 
		
		// 다음 예약일이 없는 경우 
		if(nextMap == null) { 
			// 메시지 
			message = this.messageService.getMessage("mobile.helper.next.appointment", request); 
			// 보낼 맵 
			Map<String, Object> map = this.getPushMap(this.configureService.getMcareServiceCode(), 
					pId, pName, message, 40, HelperApiFacade.WEB_HELPER_PROCESS); 
			
			// 진료예약 할껀지 물어보기 
			try { 
				this.apiCallService.execute(PnuhApi.HELPERPUSH_RESERVATION, map); 
			}
			catch(ApiCallException ex) { 
				if(this.logger.isErrorEnabled()) { 
					this.logger.error("진료예약할건지 물어보는 Push 요청 실패", ex); 
				} 
			}
		}
		// 다음 예약일이 있으면 알려주기 
		else { 
			// 날짜 변경 
			final String nextDt = this.convertDateTime((String) nextMap.get("date"), (String) nextMap.get("time")); 	
			message = this.messageService.getMessage("mobile.helper.next.appointmentsearch", request, new String[]{nextDt});

			// 보낼 맵 
			Map<String, Object> map = this.getPushMap(this.configureService.getMcareServiceCode(), 
					pId, pName, message, 40, HelperApiFacade.WEB_HELPER_PROCESS); 
			
			// 다음 예약일 알림 
			try { 
				this.apiCallService.execute(PnuhApi.HELPERPUSH_APPOINTMENTSEARCH, map); 	
			}
			catch(ApiCallException ex) { 
				if(this.logger.isErrorEnabled()) { 
					this.logger.error("다음 진료예약일 알려주는 Push 요청 실패", ex); 
				} 
			}
		}
	}
	
	
	/**
	 * push로 나가는 메시지의 날짜와 시간을 일관성 있게 관리해줄려고 따로 뽑음 
	 * @param date
	 * @param time
	 * @return
	 */
	private String convertDateTime(String date, String time) { 
		String dateStr = null; 
		java.util.Date convertDt = null; 
		
		try{ 
			convertDt = DateUtil.convertYYYYMMDD_HHMM(date, time); 
			dateStr = DateUtil.convertYYYY_MM_DD_HH24_MM(convertDt); 
		}
		catch(final ParseException ex) {
			this.logger.error("날짜 변경 실패", ex);
			// raw data 그대로 사용 
			dateStr = date + " " + time; 
		}
		
		return dateStr; 
	}
	
	/**
	 *  도착확인 처리하기 
	 * @param request
	 */
	public void processArrivedConfirm(HttpServletRequest request) { 
		final String pId = SessionUtil.getUserId(request); 
		final String pName = SessionUtil.getUserName(request); 
		// 
		Map<String, Object> arriveMap = null; 
		
		// 도착확인 대상 가져오기 위한 파라미터 만들고 
		arriveMap = new HashMap<String, Object>(); 
		arriveMap.put("pId", pId); 
		arriveMap.put("date", DateUtil.convertYYYYMMDD(Calendar.getInstance().getTime())); 
		// 대상 가져와서 
		List<Map<String, Object>> targetList = this.announceArrivalService.getArriveConfirmTargetList(request, arriveMap); 
		
		// 대상이 없으면 여기서 끝 
		if(targetList == null || targetList.isEmpty()) {
			return; 
		}
		
		// 대상이 있으면 처리하고 결과 받아서 
		targetList = this.announceArrivalService.requestArriveConfirm(request, targetList); 
		int success = 0; 
		int fail = 0; 
		final StringBuilder successBuilder = new StringBuilder(); 
		final StringBuilder failBuilder = new StringBuilder(); 
		
		
		// 도우미 메시지 만들어주기 
		for(final Map<String, Object> target : targetList) { 
			if("0".equals(target.get("success"))) {
				success++; 
				if(success > 1) {
					successBuilder.append(", "); 
				}
				successBuilder.append(target.get("departmentNm")).append("(").append(target.get("doctorNm")).append(")"); 
			}
			else {
				fail++; 
				if(fail > 1) { 
					failBuilder.append(", "); 
				}
				failBuilder.append(target.get("departmentNm")).append("(").append(target.get("doctorNm")).append(")"); 
			}
		}
		
		// 성공 메시지 보내기 
		if(success > 0) {
			// 메시지 만들기 
			final String message = this.messageService.getMessage("mobile.helper.announce.arrival.success", 
					request, new String[] {successBuilder.toString()}); 
			//맵만들기 
			Map<String, Object> map = this.getPushMap(this.configureService.getMcareServiceCode(), 
					pId, pName, message, 10, HelperApiFacade.WEB_HELPER_PROCESS); 
			// 보내기 
			try { 
				this.apiCallService.execute(PnuhApi.HELPERPUSH_NEXTLOCATOIN, map); 
			}
			catch(ApiCallException ex) { 
				this.logger.error("Push 요청 실패", ex); 
			}
		}
		
		// 실패 메시지 보내기 
		if(fail > 0) {
			final String message = this.messageService.getMessage("mobile.helper.announce.arrival.fail", request, new String[]{failBuilder.toString()}); 
			// 맵만들기 
			Map<String, Object> map = this.getPushMap(this.configureService.getMcareServiceCode(), 
					pId, pName, message, 10, HelperApiFacade.WEB_HELPER_PROCESS); 
			
			//
			try { 
				this.apiCallService.execute(PnuhApi.HELPERPUSH_ANNOUNCEARRIVAL, map); 
			}
			catch(ApiCallException ex) { 
				this.logger.error("Push 요청 실패", ex); 
			}
		}
	}
	
	/**
	 * 오늘의 예약이 있는지 확인 
	 * @param pId
	 * @return
	 */
	public List<Map<String, Object>> getTodayReservation(String pId) { 
		final Map<String, Object> reqMap = new HashMap<String, Object>(); 
		final String today = DateUtil.convertYYYYMMDD(Calendar.getInstance().getTime()); 
		
		reqMap.put("pId", pId); 
		reqMap.put("startDt", today); 
		reqMap.put("endDt", today); 
		
		// 오늘 예약 반환 
		return this.apiCallService.execute(PnuhApi.RESERVATION_GETREVLIST, reqMap).getResultAsList(); 
	}

	
	/**
	 * 탈퇴안내 
	 * @param pId
	 * @param message
	 */
	public void sendWithdrawal(String pId, String message) {
		// 맵 만들기 
		Map<String, Object> map = this.getPushMap(this.configureService.getMcareServiceCode(), pId, "", message); 

		// 보내기 	
		try { 
			this.apiCallService.execute(PnuhApi.HELPERPUSH_WITHDRAWAL, map); 		
		}
		catch(ApiCallException ex) { 
			this.logger.error("Push 요청 실패", ex); 
		}			
	}	
	
	/**
	 * push보낼때 필요한 파라미터 정리 
	 * @param mcareServiceKey
	 * @param pId
	 * @param pName
	 * @param message
	 * @return
	 */
	private Map<String, Object> getPushMap(String mcareServiceKey, String pId, String pName, String message) { 
		return this.getPushMap( mcareServiceKey, pId, pName, message, HelperApiFacade.EMPTY_STAGE_ORDER, HelperApiFacade.WEB_ADMIN_CONSOLE); 
	}
	
	/**
	 * push보낼때 필요한 파라미터 정리 
	 * @param mcareServiceKey
	 * @param pId
	 * @param pName
	 * @param message
	 * @param stageOrder
	 * @param senderId
	 * @return
	 */
	private Map<String, Object> getPushMap(String mcareServiceKey, String pId, String pName, String message, int stageOrder, String senderId) { 
		final Map<String, Object> map = new HashMap<String, Object>(); 
		
		// 서비스구분 
		map.put("mcareServiceKey", mcareServiceKey); 
		// 환자번호 
		map.put("pId", pId); 
		// 환자명 
		map.put("pName", pName);
		// 보낼 메시지 
		map.put("messageValue", message); 
		// 단계 
		map.put("stageOrder", stageOrder); 
		// 보낸사람 
		if(StringUtils.isEmpty(senderId)) {
			map.put("senderId", HelperApiFacade.WEB_ADMIN_CONSOLE);   // 질의문에 박혀있는 경우겠지만, 혹시 모르니 넘겨줌 
		}
		else {
			map.put("senderId", senderId); 
		}

		
		return map; 
	}		
}
