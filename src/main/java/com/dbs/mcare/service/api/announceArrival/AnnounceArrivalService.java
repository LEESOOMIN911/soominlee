package com.dbs.mcare.service.api.announceArrival;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dbs.mcare.exception.mobile.ApiCallException;
import com.dbs.mcare.exception.mobile.MobileControllerException;
import com.dbs.mcare.service.api.MCareApiCallService;
import com.dbs.mcare.service.api.PnuhApi;
/**
 * 도착확인 
 * 화면과 도우미가 동시에 필요해서 서비스로 분리함 
 *
 * 
 * 
 * @author hyeeun 
 *
 */
@Service 
public class AnnounceArrivalService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private MCareApiCallService apiCallService; 	
	
	
	/**
	 * 도착확인 전체 대상 목록 (접수, 대기, 완료, 보류 상태가 모두 들어있음) 
	 * @param request
	 * @param response
	 * @param reqMap
	 * @return
	 * @throws MobileControllerException
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getArriveConfirmAllList(HttpServletRequest request, Map<String, Object> reqMap) throws MobileControllerException {
		if(reqMap == null || reqMap.isEmpty()) {
			return Collections.EMPTY_LIST; 
		}
		
		try { 
			// api 호출 
			return (List<Map<String, Object>>) this.apiCallService.execute(PnuhApi.WAITTIME_ARRIVECONFIRMTARGETLIST, reqMap); 
		}
		catch(ApiCallException ex) { 
			if(this.logger.isDebugEnabled()) {
				this.logger.debug("예외발생", ex); 
			}
			
			throw new MobileControllerException("mcare.error.if.system", "시스템 호출 오류가 발생했습니다 (IF)");
		}		
	}
	
	/**
	 * 도착확인 전체 대상 중에서 도착확인을 해야할 것들만 반환 
	 * @param request
	 * @param reqMap
	 * @return
	 * @throws MobileControllerException
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getArriveConfirmTargetList(HttpServletRequest request, Map<String, Object> reqMap) throws MobileControllerException {
		final List<Map<String, Object>> allList = this.getArriveConfirmAllList(request, reqMap); 
		if(allList == null || allList.isEmpty()) {
			return Collections.EMPTY_LIST; 
		}
		
		// 접수, 대기 상태인것만 골라내기. #3399 일감참고. 
		final List<Map<String, Object>> targetList = new ArrayList<Map<String, Object>>(); 
		for(final Map<String, Object> map : allList) { 
			final String state = (String) map.get("progressStatusCd"); 
			if("R".equalsIgnoreCase(state) || "W".equalsIgnoreCase(state)) {
				targetList.add(map); 
			}
			
		}
		
		return targetList; 
	}
	
	/**
	 * 도착확인 처리 
	 * @param request
	 * @param reqMapList
	 * @return
	 * @throws MobileControllerException
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> requestArriveConfirm(HttpServletRequest request, List<Map<String, Object>> reqMapList) throws MobileControllerException {
		if(reqMapList == null || reqMapList.isEmpty()) {
			return Collections.EMPTY_LIST; 
		}
		
		// 결과 담을 곳 
		final List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>(); 
		Map<String, Object> resultMap = null;
		
		// 하나씩 돌면서 
		for(final Map<String, Object> reqMap : reqMapList) { 
			// 처리 
			resultMap = (Map<String, Object>) this.apiCallService.execute(PnuhApi.WAITTIME_REQARRIVEDCONFIRM, reqMap); 
			// 요청에 결과담고 
			reqMap.put("result", resultMap.get("result")); 
			// 결과에 추가 
			resultList.add(reqMap); 
		}

		return resultList; 
	}
}
