package com.dbs.mcare.controller.mobile;



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
import org.springframework.web.bind.annotation.ResponseBody;

import com.dbs.mcare.exception.mobile.ApiCallException;
import com.dbs.mcare.exception.mobile.MobileControllerException;
import com.dbs.mcare.framework.exception.MCareServiceException;
import com.dbs.mcare.framework.service.menu.ServiceMenu;
import com.dbs.mcare.framework.template.AbstractController;
import com.dbs.mcare.framework.util.ResponseUtil;
import com.dbs.mcare.framework.util.SessionUtil;
import com.dbs.mcare.service.api.MCareApiCallService;
import com.dbs.mcare.service.api.PnuhApi;

@Controller
@RequestMapping("/mobile/pushToken")
@ServiceMenu(menuId="pushToken", description="push token 관리")
public class TokenController extends AbstractController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private MCareApiCallService apiCallService; 
	
	
	// 토큰을 가지고 있는 환자목록 반환 
	@RequestMapping(value = "/getList.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object>  getList(HttpServletRequest request, HttpServletResponse response, 
			@RequestBody Map<String, Object> reqMap) throws MobileControllerException {
		
		// 접근권한 및 파라미터 정리 
		try { 
			reqMap = super.isAccessableMenu(this.getClass(), request, reqMap); 
		}
		catch(MCareServiceException ex) { 
			throw new MobileControllerException(ex.getCode(), ex.getMessage()); 
		}
		
		
		List<Map<String, Object>> resultList = null; 
		
		// 기능호출 
		try { 
			resultList = this.apiCallService.execute(PnuhApi.USER_USERINFO_GETDEVICETOKENUSERLIST, reqMap).getResultAsList();
		}
		catch(ApiCallException ex) { 
			if(this.logger.isDebugEnabled()) {
				this.logger.debug("예외발생", ex); 
			}
			
			throw new MobileControllerException("mcare.error.if.system", "시스템 호출 오류가 발생했습니다 (IF)");
		}	

		
		// 결과가 없으면 빈거대로 반환 
		if(resultList == null || resultList.isEmpty()) {
			return ResponseUtil.wrapEmptyResultMap(); 
		}
		
		// 현재 요청자 pId구하고 
		final String currentUser = SessionUtil.getUserId(request); 
		final String currentUserKey = "currentUser"; 

		Map<String, Object> currentUserMap = null; 
				
		for(Map<String, Object> map  : resultList) { 
			String receiverId = (String) map.get("receiverId"); 
			if(currentUser.equals(receiverId)) {
				map.put(currentUserKey, "Y"); 
				currentUserMap = map; 
			}
			else {
				map.put(currentUserKey, "N"); 
			}
		}
				
		// 현재사용자를 목록의 제일 처음에 넣어줄것임 
		if(currentUserMap != null) { 
			resultList.remove(currentUserMap); 
			resultList.add(0, currentUserMap);
		} 

		return ResponseUtil.wrapResultMap(resultList);
	}
	
	// 삭제 
	@RequestMapping(value = "/remove.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object>  remove(HttpServletRequest request, HttpServletResponse response, 
			@RequestBody Map<String, Object> reqMap) throws MobileControllerException {
		
		// 접근권한 및 파라미터 정리 
		try { 
			reqMap = super.isAccessableMenu(this.getClass(), request, reqMap); 
		}
		catch(MCareServiceException ex) { 
			throw new MobileControllerException(ex.getCode(), ex.getMessage()); 
		}
		
		// 기능호출 
		try { 
			return this.apiCallService.call(PnuhApi.USER_USERINFO_DELETEDEVICETOKENUSER, reqMap); 
		}
		catch(ApiCallException ex) { 
			if(this.logger.isDebugEnabled()) {
				this.logger.debug("예외발생", ex); 
			}
			
			throw new MobileControllerException("mcare.error.if.system", "시스템 호출 오류가 발생했습니다 (IF)");
		}		
	}	
	
	// 사용자가 로그인했던 장비정보 수집 
	@RequestMapping(value = "/getUserTokenList.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object>  getUerTokenList(HttpServletRequest request, HttpServletResponse response, 
			@RequestBody Map<String, Object> reqMap) throws MobileControllerException {
		
		// 접근권한 및 파라미터 정리 
		try { 
			reqMap = super.isAccessableMenu(this.getClass(), request, reqMap); 
		}
		catch(MCareServiceException ex) { 
			throw new MobileControllerException(ex.getCode(), ex.getMessage()); 
		}
		
		// 기능호출 
		try { 
			return this.apiCallService.call(PnuhApi.USER_USERINFO_USERTOKENS, reqMap); 
		}
		catch(ApiCallException ex) { 
			if(this.logger.isDebugEnabled()) {
				this.logger.debug("예외발생", ex); 
			}
			
			throw new MobileControllerException("mcare.error.if.system", "시스템 호출 오류가 발생했습니다 (IF)");
		}		
	}	
}
