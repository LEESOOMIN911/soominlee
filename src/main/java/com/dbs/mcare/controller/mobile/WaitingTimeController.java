package com.dbs.mcare.controller.mobile;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dbs.mcare.exception.mobile.MobileControllerException;
import com.dbs.mcare.framework.exception.MCareServiceException;
import com.dbs.mcare.framework.service.menu.ServiceMenu;
import com.dbs.mcare.framework.template.AbstractController;
import com.dbs.mcare.framework.util.DateTimeComparator;
import com.dbs.mcare.framework.util.ResponseUtil;
import com.dbs.mcare.service.api.MCareApiCallService;
import com.dbs.mcare.service.api.PnuhApi;

/**
 * 대기시간조회 
 * 
 * @author hyeeun 
 *
 */
@Controller
@RequestMapping("/mobile/waitingTime")
@ServiceMenu(menuId="waitingTime", description="진료대기순서")
public class WaitingTimeController  extends AbstractController {
	@Autowired
	private MCareApiCallService apiCallService; 


	/**
	 * 대기시간 조회 - 현재 실험용에서 데이터가 있는 날짜는 20150915 
	 * @param request
	 * @param response
	 * @return
	 * @throws MobileControllerException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/waitingTime.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getWaitingTime(HttpServletRequest request, HttpServletResponse response
			,@RequestBody Map<String, Object> reqMap) throws MobileControllerException {
		
		// 접근권한 및 파라미터 정리 
		try { 
			reqMap = super.isAccessableMenu(this.getClass(), request, reqMap); 
		}
		catch(MCareServiceException ex) { 
			throw new MobileControllerException(ex.getCode(), ex.getMessage()); 
		} 
		 
		List<Map<String, Object>> resultList = null; 
		// 결과 받아와서 
		resultList = (List<Map<String, Object>>) this.apiCallService.execute(PnuhApi.WAITTIME_GETWAITINGLIST, reqMap); 		
		// 시간순으로 sort 
		if(!resultList.isEmpty()) {
			Collections.sort(resultList, new DateTimeComparator("date", "time"));
		}		
		
		// 결과전달 
		return ResponseUtil.wrapResultMap(resultList); 
	}
}
