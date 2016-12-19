package com.dbs.mcare.controller.mobile;

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
import com.dbs.mcare.service.api.MCareApiCallService;
import com.dbs.mcare.service.api.PnuhApi;

@Controller
@RequestMapping("/mobile/prescription")
@ServiceMenu(menuId="prescription", description="처방조회")
public class PrescriptionController extends AbstractController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private MCareApiCallService apiCallService; 
	
	// 투약처방조회 
	@RequestMapping(value = "/search.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> search(HttpServletRequest request, HttpServletResponse response, 
			@RequestBody Map<String, Object> reqMap) throws MobileControllerException {

		// 접근권한 및 파라미터 정리 
		try { 
			reqMap = super.isAccessableMenu(this.getClass(), request, reqMap); 
		}
		catch(MCareServiceException ex) { 
			throw new MobileControllerException(ex.getCode(), ex.getMessage()); 
		} 

		try { 
			return this.apiCallService.call(PnuhApi.PRESCRIPTION_GETPRESCLIST, reqMap);
		}
		catch(ApiCallException ex) { 
			if(this.logger.isDebugEnabled()) {
				this.logger.debug("예외발생", ex); 
			}
			
			throw new MobileControllerException("mcare.error.if.system", "시스템 호출 오류가 발생했습니다 (IF)");
		}		
	}	
}
