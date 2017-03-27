package com.dbs.mcare.service.api.reservation;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.dbs.mcare.exception.mobile.MobileControllerException;
import com.dbs.mcare.framework.util.NameComparator;
import com.dbs.mcare.service.api.MCareApiCallService;
import com.dbs.mcare.service.api.PnuhApi;

@Service 
public class ReservationService {
//	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	@Autowired
	private MCareApiCallService apiCallService; 
		
	/**
	 * 예약가능한 진료과 구하기 
	 * @param request
	 * @param response
	 * @param reqMap
	 * @return
	 * @throws MobileControllerException
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getDepartment(HttpServletRequest request, HttpServletResponse response, Map<String, Object> reqMap) throws MobileControllerException {

		// 기능호출 
		List<Map<String, Object>> resultMapList = null;
		resultMapList = (List<Map<String, Object>>) this.apiCallService.call(PnuhApi.RESERVATION_GETREVDEPT, reqMap); 
		
		// 결과정리 
		if(!resultMapList.isEmpty()) {
			Collections.sort(resultMapList, new NameComparator("departmentNm"));
		}
			
		// 결과반환 
		return resultMapList; 
	}	
	
	/**
	 * 진료과에 의사 
	 * @param request
	 * @param response
	 * @param reqMap
	 * @return
	 * @throws MobileControllerException
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getDoctor(HttpServletRequest request, HttpServletResponse response, Map<String, Object> reqMap) throws MobileControllerException {
		// 기능호출 
		List<Map<String, Object>> resultMapList = null;
		resultMapList = (List<Map<String, Object>>) this.apiCallService.call(PnuhApi.RESERVATION_GETREVDOC, reqMap); 
		
		// 결과정리 
		if(!resultMapList.isEmpty()) {
			Collections.sort(resultMapList, new NameComparator("doctorNm"));
		}
		
		// 결과반환 
		return resultMapList; 
	}	
	
	/**
	 * 의사의 예약가능한 날짜 
	 * @param request
	 * @param response
	 * @param reqMap
	 * @return
	 * @throws MobileControllerException
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getDoctorDate(HttpServletRequest request, HttpServletResponse response, Map<String, Object> reqMap) throws MobileControllerException {
		List<Map<String, Object>> resultMapList = null;
		resultMapList = (List<Map<String, Object>>) this.apiCallService.call(PnuhApi.RESERVATION_GETREVDATE, reqMap); 
		
		// 결과정리 
		if(!resultMapList.isEmpty()) { 
			Collections.sort(resultMapList, new NameComparator("date"));
		}
		
		// 결과반환 
		return resultMapList; 
	}
	
	/**
	 * 의사의 예약가능한 날짜에 대한 예약가능한 시간 
	 * @param request
	 * @param response
	 * @param reqMap
	 * @return
	 * @throws MobileControllerException
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getDoctorDateTime(HttpServletRequest request, HttpServletResponse response, Map<String, Object> reqMap) throws MobileControllerException {		
		List<Map<String, Object>> resultMapList = null;
		resultMapList = (List<Map<String, Object>>) this.apiCallService.call(PnuhApi.RESERVATION_GATEREVTIME, reqMap); 
		
		// 결과정리 time
		if(!resultMapList.isEmpty()) { 
			Collections.sort(resultMapList, new NameComparator("time"));
		}
		
		// 결과반환 
		return resultMapList; 
	}	
	
	/**
	 * 예약 - 진료 예약
	 * @param request
	 * @param response
	 * @param reqMap
	 * @return
	 * @throws MobileControllerException
	 */
	public Map<String, Object> reservation(HttpServletRequest request, HttpServletResponse response, Map<String, Object> reqMap ) throws MobileControllerException{
		Map<String, Object> userMap = null; 
		String data = null; 
		
		// 사용자 정보 구하기 
		userMap = this.apiCallService.execute(PnuhApi.USER_USERINFO_GETUSERINFO, reqMap).getResultAsMap(); 
		
		// 유효성 확인 - 주소 
		data = (String) userMap.get("address"); 
		if(StringUtils.isEmpty(data)) {
			throw new MobileControllerException("mcare.error.no.address", "주소가 등록되어 있지 않습니다. 원무과로 가서 주소를 확인하세요."); 
		}
		
		// 유효성 확인 - 핸드폰 번호 
		data = (String) userMap.get("cellphoneNo"); 
		if(StringUtils.isEmpty(data)) {
			throw new MobileControllerException("mcare.error.no.cellphone.no", "핸드폰 번호가 등록되어 있지 않습니다. 원무과로 가서 휴대폰 번호를 확인하세요."); 
		}
		
		// 기간계에서 가져온 것으로 사용자 정보 덮어쓰기 
		reqMap.putAll(userMap);
		
		Map<String, Object> resultMap = null;
		resultMap = (Map<String, Object>) this.apiCallService.call(PnuhApi.RESERVATION_RESERVATION, reqMap); 
		
		// 결과전달 
		return resultMap;
	}
}
