package com.dbs.mcare.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.HandlerMapping;

import com.dbs.mcare.MCareConstants.HOSPITAL;
import com.dbs.mcare.exception.admin.AdminControllerException;
import com.dbs.mcare.framework.FrameworkConstants;
import com.dbs.mcare.framework.exception.MCareServiceException;
import com.dbs.mcare.framework.service.admin.agg.AggAccessDailyService;
import com.dbs.mcare.framework.service.admin.agg.AggAccessHourlyService;
import com.dbs.mcare.framework.service.admin.agg.AggEventLogService;
import com.dbs.mcare.framework.service.admin.agg.AggMenuService;
import com.dbs.mcare.framework.service.admin.agg.AggMsgSendResultLogService;
import com.dbs.mcare.framework.service.admin.agg.AggPlatformService;
import com.dbs.mcare.framework.service.admin.agg.AggUserPlatformService;
import com.dbs.mcare.framework.service.admin.agg.AggUserRegisterService;
import com.dbs.mcare.framework.service.admin.agg.repository.dao.AggAccessDaily;
import com.dbs.mcare.framework.service.admin.agg.repository.dao.AggAccessHourly;
import com.dbs.mcare.framework.service.admin.agg.repository.dao.AggDailyMsgSendErrorLog;
import com.dbs.mcare.framework.service.admin.agg.repository.dao.AggEventLog;
import com.dbs.mcare.framework.service.admin.agg.repository.dao.AggMenu;
import com.dbs.mcare.framework.service.admin.agg.repository.dao.AggMsgSendResultLog;
import com.dbs.mcare.framework.service.admin.agg.repository.dao.AggPlatform;
import com.dbs.mcare.framework.service.admin.agg.repository.dao.AggUserPlatform;
import com.dbs.mcare.framework.service.admin.agg.repository.dao.AggUserRegister;
import com.dbs.mcare.framework.template.AdminAbstractController;
import com.dbs.mcare.framework.util.ConvertUtil;
import com.dbs.mcare.framework.util.HttpRequestUtil;
import com.dbs.mcare.service.PnuhConfigureService;
import com.dbs.mcare.service.admin.agg.AggUserAgeService;
import com.dbs.mcare.service.admin.agg.AggUserPostnoService;
import com.dbs.mcare.service.admin.agg.repository.dao.AggUserAge;
import com.dbs.mcare.service.admin.agg.repository.dao.AggUserPostno;


/**
 * 통계정보 보내주기 
 * @author DBS 
 * - 2015. 08. 10. hyeeun. 코드정리 
 */
@Controller
@RequestMapping("/admin/stats")
public class AggregationController extends AdminAbstractController {
	private static final Logger logger = LoggerFactory.getLogger(AggregationController.class); 
	// 시작날짜. 테이블 컬럼과 맵핑되지 않는, UI와 약속된 검색범위 
	private static final String KEY_START_DATE = "strDate"; 
	// 종료날짜 
	private static final String KEY_END_DATE = "endDate"; 
	// CSV 제목 
	private static final String KEY_EXPORT_TITLE = "exportTitle"; 
	// CSV 그리드 제목목록 
	private static final String KEY_EXPORT_TITLE_LIST = "exportTitleList"; 
	// CSV 그리드 데이터목록 
	private static final String KEY_EXPORT_DATA_KEY_LIST = "exportDataKeyList"; 
		
	//CSV 데이터 키 - getCSVData 메소드에서 사용 
	private static final String KEY_CVS_DATA_LIST = "list";
	private static final String KEY_CVS_DATA_CLASS = "clazz";
	
	@Autowired 
	private PnuhConfigureService configureService;
	
	@Autowired 
	private AggAccessDailyService aggDailyService; 
	@Autowired 
	private AggAccessHourlyService aggHourlyService; 
	@Autowired 
	private AggPlatformService aggPlatformService; 
	@Autowired 
	private AggMenuService aggAccessService; 
	@Autowired
	private AggEventLogService aggDailyEventLogService;
	@Autowired 
	private AggMsgSendResultLogService aggDailyMsgSendResultLogService; 
	@Autowired 
	private AggUserAgeService aggUserAgeService; 
	@Autowired 
	private AggUserPostnoService aggUserPostnoService; 
	@Autowired 
	private AggUserRegisterService aggUserRegisterService; 
	@Autowired 
	private AggUserPlatformService aggUserPlatformService; 
	
	
	// 날짜별 방문자 수 
	@RequestMapping(value = "/getDailyList.json", method = RequestMethod.POST)
	public List<AggAccessDaily> findHitCountByDate(@RequestBody Map<String, Object> map) throws AdminControllerException { 		
		final String strDate = map.get(AggregationController.KEY_START_DATE).toString();
		final String endDate = map.get(AggregationController.KEY_END_DATE).toString();
		
		try { 
			return this.aggDailyService.findByDate(strDate, endDate); 
		}
		catch(final MCareServiceException ex) {
			logger.error("통계정보 수집 실패. strDate : " + strDate + ", endDate : " + endDate, ex); 
			throw new AdminControllerException(ex); 
		}
	} 
	
	// 요일별 방문자 수 
	@RequestMapping(value = "/getWeeklyList.json", method = RequestMethod.POST)
	public List<AggAccessDaily> findHitCountByWeek( @RequestBody Map<String, Object> map ) throws AdminControllerException {
		final String strDate = map.get(AggregationController.KEY_START_DATE).toString();
		final String endDate = map.get(AggregationController.KEY_END_DATE).toString();
		
		try { 
			return this.aggDailyService.findByWeek(strDate, endDate); 
		}
		catch(final MCareServiceException ex) {
			logger.error("통계정보 수집 실패. strDate : " + strDate + ", endDate : " + endDate, ex); 
			throw new AdminControllerException(ex); 
		}
	}
	
	// 시간별 방문자 수 
	@RequestMapping(value = "/getHourlyList.json", method = RequestMethod.POST)
	public List<AggAccessHourly> findHitCountByHour( @RequestBody Map<String, Object> map ) throws AdminControllerException {
		final String strDate = map.get(AggregationController.KEY_START_DATE).toString();
		final String endDate = map.get(AggregationController.KEY_END_DATE).toString();
		
		try { 
			return this.aggHourlyService.findByHour(strDate, endDate); 
		}
		catch(final MCareServiceException ex) {
			logger.error("통계정보 수집 실패. strDate : " + strDate + ", endDate : " + endDate, ex); 
			throw new AdminControllerException(ex); 
		}		
	}	
	
	// 플랫폼별 방문자 수 
	@RequestMapping(value = "/getPlatformList.json", method = RequestMethod.POST)
	public List<AggPlatform> findHitCountByPlatform( @RequestBody Map<String, Object> map ) throws AdminControllerException {
		final String strDate = map.get(AggregationController.KEY_START_DATE).toString();
		final String endDate = map.get(AggregationController.KEY_END_DATE).toString();
		
		try { 
			return this.aggPlatformService.findByDate(strDate, endDate); 
		}
		catch(final MCareServiceException ex) {
			logger.error("통계정보 수집 실패. strDate : " + strDate + ", endDate : " + endDate, ex); 
			throw new AdminControllerException(ex); 
		}	
	}
	
	// 메뉴별 방문자 수 
	@RequestMapping(value = "/getAccessList.json", method = RequestMethod.POST)
	public List<AggMenu> findHistCountByMenu( @RequestBody Map<String, Object> map ) throws AdminControllerException {
		final String strDate = map.get(AggregationController.KEY_START_DATE).toString();
		final String endDate = map.get(AggregationController.KEY_END_DATE).toString();
		
		try { 
			return this.aggAccessService.findByDate(strDate, endDate); 
		}
		catch(final MCareServiceException ex) {
			logger.error("통계정보 수집 실패. strDate : " + strDate + ", endDate : " + endDate, ex); 
			throw new AdminControllerException(ex); 
		}	
	}
	
	// 이벤트 발생 수 
	@RequestMapping(value = "/getEventLogList.json", method = RequestMethod.POST)
	public List<AggEventLog> findHistCountByEventLog( @RequestBody Map<String, Object> map ) throws AdminControllerException {
		final String strDate = map.get(AggregationController.KEY_START_DATE).toString();
		final String endDate = map.get(AggregationController.KEY_END_DATE).toString();
		final String searchEvent = map.get("searchEvent").toString();
			
		try { 
			if( searchEvent.equals("") ){					
				return this.aggDailyEventLogService.findByDate(strDate, endDate); 
			} else {
				return this.aggDailyEventLogService.findEventByDate(searchEvent, strDate, endDate); 
			}
		} catch(final MCareServiceException ex) {
			logger.error("통계정보 수집 실패. strDate : " + strDate + ", endDate : " + endDate, ex); 
			throw new AdminControllerException(ex); 
		}	
	}
	
	// push 전송결과  
	@RequestMapping(value = "/getMsgResultLogList.json", method = RequestMethod.POST)
	public List<AggMsgSendResultLog> findResultCountByMsgResultLog( @RequestBody Map<String, Object> map ) throws AdminControllerException {
		final String strDate = map.get(AggregationController.KEY_START_DATE).toString();
		final String endDate = map.get(AggregationController.KEY_END_DATE).toString();
			
		try { 
			return this.aggDailyMsgSendResultLogService.findByDate(strDate, endDate); 
		} 
		catch(final MCareServiceException ex) {
			logger.error("통계정보 수집 실패. strDate : " + strDate + ", endDate : " + endDate, ex); 
			throw new AdminControllerException(ex); 
		}	
	}	

	// push 전송결과 실패원인 
	@RequestMapping(value = "/getMsgResultErrorTypeList.json", method = RequestMethod.POST)
	public List<AggDailyMsgSendErrorLog> findErrorTypeByMsgResultLog( @RequestBody Map<String, Object> map ) throws AdminControllerException {
		final String aggDt = map.get(AggregationController.KEY_START_DATE).toString();
	
		try { 
			return this.aggDailyMsgSendResultLogService.findByErrorType(aggDt); 
		} 
		catch(final MCareServiceException ex) {
			logger.error("통계정보 수집 실패. aggDt=" + aggDt); 
			throw new AdminControllerException(ex); 
		}	
	}	
	
	// 연령별 가입통계 
	@RequestMapping(value = "/getUserAgeList.json", method = RequestMethod.POST)
	public List<AggUserAge> findUserAgeList( @RequestBody Map<String, Object> map ) throws AdminControllerException {
		final String strDate = map.get(AggregationController.KEY_START_DATE).toString();
		final String endDate = map.get(AggregationController.KEY_END_DATE).toString();
		
		try { 
			return this.aggUserAgeService.findByDate(strDate, endDate); 
		}
		catch(final MCareServiceException ex) {
			logger.error("통계정보 수집 실패. strDate : " + strDate + ", endDate : " + endDate, ex); 
			throw new AdminControllerException(ex); 
		}		
	}	
	
	// 지역별 가입통계 
	@RequestMapping(value = "/getUserPostnoList.json", method = RequestMethod.POST)
	public List<AggUserPostno> findUserPostnoList( @RequestBody Map<String, Object> map ) throws AdminControllerException {
		final String strDate = map.get(AggregationController.KEY_START_DATE).toString();
		final String endDate = map.get(AggregationController.KEY_END_DATE).toString();
		
		try { 
			return this.aggUserPostnoService.findByDate(strDate, endDate); 
		}
		catch(final MCareServiceException ex) {
			logger.error("통계정보 수집 실패. strDate : " + strDate + ", endDate : " + endDate, ex); 
			throw new AdminControllerException(ex); 
		}		
	}		
	
	// 일별 사용자 등록 통계 
	@RequestMapping(value = "/getUserRegisterList.json", method = RequestMethod.POST)
	public List<AggUserRegister> findUserRegisterList( @RequestBody Map<String, Object> map ) throws AdminControllerException {
		final String strDate = map.get(AggregationController.KEY_START_DATE).toString();
		final String endDate = map.get(AggregationController.KEY_END_DATE).toString();
		
		try { 
			return this.aggUserRegisterService.findByDate(strDate, endDate); 
		}
		catch(final MCareServiceException ex) {
			logger.error("통계정보 수집 실패. strDate : " + strDate + ", endDate : " + endDate, ex); 
			throw new AdminControllerException(ex); 
		}		
	}		
	
	// 플랫폼 타입 정보 
	@RequestMapping(value = "/getUserPlatformList.json", method = RequestMethod.POST)
	public List<AggUserPlatform> getUserPlatformList() throws AdminControllerException {
		try { 
			return this.aggUserPlatformService.getList();
		}
		catch(final MCareServiceException ex) {
			logger.error("통계정보 수집 실패. ", ex); 
			throw new AdminControllerException(ex); 
		}		
	}
	
	// 등록된 사용자와 토큰에 대한 정보 
	@RequestMapping(value = "/getUserTokenInfo.json", method = RequestMethod.POST)
	public Map<String, Long> getUserTokenInfo(@RequestBody Map<String, Object> map ) throws AdminControllerException {
	
		try { 
			return this.aggUserPlatformService.getRegisteredInfo(); 
		}
		catch(final MCareServiceException ex) {
			logger.error("통계정보 수집 실패. ", ex); 
			throw new AdminControllerException(ex); 
		}	
	}
	
	// 통계 데이터 저장 
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/exportCSV.json", method = RequestMethod.POST) 
	public void exporCSV(HttpServletRequest request, 
				HttpServletResponse response) throws AdminControllerException { 	
		if(logger.isDebugEnabled()) { 
			logger.debug("request : " + request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE));
		} 

		// 요청파라미터 정리 
		Map<String, Object> map = this.convertParam(request); 

		if(logger.isDebugEnabled()) {
			StringBuilder builder = new StringBuilder(FrameworkConstants.NEW_LINE); 
			
			builder.append("- serverServiceAddr : ").append(this.configureService.getServerServiceAddr()).append(FrameworkConstants.NEW_LINE); 
			builder.append("- contextPath : ").append(request.getContextPath()).append(FrameworkConstants.NEW_LINE);
			builder.append("- parameter map : ").append(ConvertUtil.convertStringForDebug(map)).append(FrameworkConstants.NEW_LINE); 

			logger.debug(builder.toString()); 
		}
		
		//데이터 구하고 
		Map<String,Object> cMap = this.getCSVData(map);
		List<?> dataList = (List<?>)cMap.get(AggregationController.KEY_CVS_DATA_LIST);
		Class<?> clazz = (Class<?>)cMap.get(AggregationController.KEY_CVS_DATA_CLASS);
		
		// 병원정보구하고  
		HOSPITAL hospital = HOSPITAL.convertCode(this.configureService.getMcareServiceCode());
		// 저장 
		super.saveCSV(request, response, hospital.getHospitalName(), (String) map.get("exportTitle"), 
				(List<String>) map.get("exportTitleList"), (List<String>) map.get("exportDataKeyList"), clazz, dataList);  
	}	
		
	/**
	 * 파라미터정리 
	 * @param request
	 * @return
	 */
	private Map<String, Object> convertParam(HttpServletRequest request) {
		try { 
			request.setCharacterEncoding("utf-8");
		}
		catch(Exception ex) { 
			if(logger.isDebugEnabled()) {
				logger.debug("설정오류", ex);
			}
		}
		
		// 요청파라미터 정리 
		Map<String, Object> map = HttpRequestUtil.requestParam(request, null); 
		
		// 검색 시작일 
		if(map.containsKey(AggregationController.KEY_START_DATE)) {
			Long longValue = Long.parseLong((String) map.get(AggregationController.KEY_START_DATE)); 
			map.put(AggregationController.KEY_START_DATE, longValue); 
		}
		
		// 검색 종료일 
		if(map.containsKey(AggregationController.KEY_END_DATE)) {
			Long longValue = Long.parseLong((String) map.get(AggregationController.KEY_END_DATE)); 
			map.put(AggregationController.KEY_END_DATE, longValue); 
		}	
		// 제목(exporttitle) - 변환없음 
		if(map.containsKey(AggregationController.KEY_EXPORT_TITLE)) {
			String strValue = (String) map.get(AggregationController.KEY_EXPORT_TITLE); 
			map.put(AggregationController.KEY_EXPORT_TITLE, ConvertUtil.convertUrlDecoding(strValue, "UTF-8")); 
		}
		
		// 컬럼제목 
		if(map.containsKey(AggregationController.KEY_EXPORT_TITLE_LIST)) {
			String strValue = (String) map.get(AggregationController.KEY_EXPORT_TITLE_LIST); 
			String decValue = ConvertUtil.convertUrlDecoding(strValue, "UTF-8"); 
			// 
			map.put(AggregationController.KEY_EXPORT_TITLE_LIST, ConvertUtil.convertList(decValue, ",")); 
		}
		
		// 데이터 꺼내올 key 
		if(map.containsKey(AggregationController.KEY_EXPORT_DATA_KEY_LIST)) {
			String strValue = (String) map.get(AggregationController.KEY_EXPORT_DATA_KEY_LIST); 
			map.put(AggregationController.KEY_EXPORT_DATA_KEY_LIST, ConvertUtil.convertList(strValue, ",")); 
		}
		
		return map; 
	}	
	/**
	 * 
	 * @param map
	 * @return
	 */
	private Map<String,Object> getCSVData( Map<String,Object> map ){
		List<?> list = null;
		Class<?> clazz = null;
		Map<String,Object> dataMap = new HashMap<String, Object>();
		String type = map.get("type").toString();
		
		switch (type) {
		case "daily":
			 list =  this.findHitCountByDate(map);
			 clazz = AggAccessDaily.class;
			 break;
		case "weekly":
			 list =  this.findHitCountByWeek(map);
			 clazz = AggAccessDaily.class;
			 break;
		case "hourly":
			 list =  this.findHitCountByHour(map);
			 clazz = AggAccessHourly.class;
			 break;
		case "platform":
			 list = this.findHitCountByPlatform(map);
			 clazz = AggPlatform.class;
			 break;
		case "access":
			 list =  this.findHistCountByMenu(map);
			 clazz = AggMenu.class;
			 break;
		case "eventLog":
			 list =  this.findHistCountByEventLog(map);
			 clazz = AggEventLog.class;
			 break;
		case "msgSendResultLog":
			 list =  this.findResultCountByMsgResultLog(map);
			 clazz = AggMsgSendResultLog.class;
			 break;
		case "dailyRegister":
			 list =  this.findUserRegisterList(map);
			 clazz = AggUserRegister.class;
			 break;
		case "userAge":
			 list =  this.findUserAgeList(map);
			 clazz = AggUserAge.class;
			 break;
		case "userPost":
			 list =  this.findUserPostnoList(map);
			 clazz = AggUserPostno.class;
			 break;
		}
		
		dataMap.put(AggregationController.KEY_CVS_DATA_LIST, list);
		dataMap.put(AggregationController.KEY_CVS_DATA_CLASS,clazz);
		return dataMap;
	}
}
