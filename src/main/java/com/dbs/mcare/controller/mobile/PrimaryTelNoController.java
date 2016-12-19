package com.dbs.mcare.controller.mobile;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dbs.mcare.exception.mobile.ApiCallException;
import com.dbs.mcare.exception.mobile.MobileControllerException;
import com.dbs.mcare.framework.exception.MCareServiceException;
import com.dbs.mcare.framework.service.menu.ServiceMenu;
import com.dbs.mcare.framework.template.AbstractController;
import com.dbs.mcare.service.api.MCareApiCallService;
import com.dbs.mcare.service.api.PnuhApi;

/**
 * 전화번호 조회 
 * 
 * @author hyeeun 
 *
 */
@Controller
@RequestMapping("/mobile/telno")
@ServiceMenu(menuId="telNo", description="주요전화번호")
public class PrimaryTelNoController extends AbstractController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired 
	private MCareApiCallService apiCallService; 

	/**
	 * 전화번호 목록 
	 * @param request
	 * @param response
	 * @return
	 * @throws MobileControllerException
	 */
	@RequestMapping(value = "/getList.json", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getList(HttpServletRequest request, HttpServletResponse response) throws MobileControllerException {
		// 접근권한 및 파라미터 정리 
		try { 
			super.isAccessableMenu(this.getClass(), request); 
		}
		catch(MCareServiceException ex) { 
			throw new MobileControllerException(ex.getCode(), ex.getMessage()); 
		} 	

		try { 
			// 호출 및 결과 반환. 파라미터 없음 
			return this.apiCallService.call(PnuhApi.TELNO_GETLIST, null);
		}
		catch(ApiCallException ex) { 
			if(this.logger.isDebugEnabled()) {
				this.logger.debug("예외발생", ex); 
			}
			
			throw new MobileControllerException("mcare.error.if.system", "시스템 호출 오류가 발생했습니다 (IF)");
		}
	}
}
