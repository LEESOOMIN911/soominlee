package com.dbs.mcare.service.api.appointment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dbs.mcare.exception.mobile.ApiCallException;
import com.dbs.mcare.exception.mobile.MobileControllerException;
import com.dbs.mcare.framework.util.DateTimeComparator;
import com.dbs.mcare.service.api.MCareApiCallService;
import com.dbs.mcare.service.api.PnuhApi;

/**
 * 예약은 화면 뿐만 아니라 도우미에서도 사용되어 service단으로 끌어왔음 
 * 
 * 
 * @author hyeeun 
 *
 */
@Service 
public class AppointmentService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private MCareApiCallService apiCallService; 

	/**
	 * 예약취소 
	 * @param reqMap
	 * @throws MobileControllerException
	 */
	public Map<String, Object> cancel(Map<String, Object> reqMap) throws MobileControllerException {
		// 단순 api call만 있지만, 여러군데에서 사용될 수 있어서 서비스단으로 옮겨온 케이스임 
		try { 
			// 예약취소 요청 
			return this.apiCallService.execute(PnuhApi.RESERVATION_PUTRESCANCEL, reqMap).getResultAsMap(); 
		}
		catch(ApiCallException ex) { 
			if(this.logger.isDebugEnabled()) {
				this.logger.debug("예외발생", ex); 
			}
			
			throw new MobileControllerException("mcare.error.if.system", "시스템 호출 오류가 발생했습니다 (IF)");
		}
	}
	
	/**
	 * 예약목록반환 
	 * @param reqMap
	 * @return
	 * @throws MobileControllerException
	 */
	public List<Map<String, Object>> getList(Map<String, Object> reqMap) throws MobileControllerException {
		// 결과맵 
		final List<Map<String, Object>> resultMapList = new ArrayList<Map<String, Object>>(); 
		
		// 임시저장소 
		List<Map<String, Object>> list = null; 
		
		// 예약조회 
		list = this.apiCallService.execute(PnuhApi.RESERVATION_GETREVLIST, reqMap).getResultAsList(); 
		if(list != null && !list.isEmpty()) {
			// 예약된 항목이 있으면 복사 
			resultMapList.addAll(list); 
			list = null; 
		}
		
		// 인터넷 예약조회 
		list = this.apiCallService.execute(PnuhApi.RESERVATION_GETREVINTERNET, reqMap).getResultAsList(); 
		if(list != null && !list.isEmpty()) {
			resultMapList.addAll(list); 
		}
		
		// 결과정리 
		if(!resultMapList.isEmpty()) {
			Collections.sort(resultMapList, new DateTimeComparator("date", "time"));
		}
		
		// 결과 가져오기 
		return resultMapList; 
	}
}
