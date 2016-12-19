package com.dbs.mcare.controller.mobile;

import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.web.bind.annotation.ResponseBody;

import com.dbs.mcare.exception.mobile.ApiCallException;
import com.dbs.mcare.exception.mobile.MobileControllerException;
import com.dbs.mcare.framework.exception.MCareServiceException;
import com.dbs.mcare.framework.service.MessageService;
import com.dbs.mcare.framework.service.menu.ServiceMenu;
import com.dbs.mcare.framework.template.AbstractController;
import com.dbs.mcare.framework.util.DateUtil;
import com.dbs.mcare.framework.util.ResponseUtil;
import com.dbs.mcare.service.api.MCareApiCallService;
import com.dbs.mcare.service.api.PnuhApi;

@Controller
@RequestMapping("/mobile/helper")
@ServiceMenu(menuId="helper", description="도우미")
public class HelperController extends AbstractController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private MCareApiCallService apiCallService; 
	@Autowired 
	private MessageService messageService; 
	
	@RequestMapping(value = "/getList.json" , method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getList(HttpServletRequest request, HttpServletResponse response, 
			@RequestBody Map<String, Object> reqMap) throws MobileControllerException {

		// 접근권한 및 파라미터 정리 
		try { 
			reqMap = super.isAccessableMenu(this.getClass(), request, reqMap); 
		}
		catch(MCareServiceException ex) { 
			throw new MobileControllerException(ex.getCode(), ex.getMessage()); 
		}		
		
		// 호출 
		Map<String, Object> resultMap = null; 
		
		try { 
			resultMap = this.apiCallService.call(PnuhApi.HELPER_MESSAGELIST, reqMap); 
		}
		catch(ApiCallException ex) { 
			if(this.logger.isDebugEnabled()) {
				this.logger.debug("예외발생", ex); 
			}
			
			throw new MobileControllerException("mcare.error.if.system", "시스템 호출 오류가 발생했습니다 (IF)");
		}
		
		
		// DB에서 가져온 데이터가 비어있으면 기본 데이터 만들어주기 
		if(ResponseUtil.isEmptyResult(resultMap)) {
			 final Map<String, Object> map = new HashMap<String, Object>(); 
			 final String defaultStr = this.messageService.getMessage("mobile.helper.default.msg", request); 
			 
			 map.put("helperSeq", 0); 
			 map.put("sendDt", DateUtil.convertIso8601(new Date())); 
			 map.put("userMsg", defaultStr); 			 
			
			 final List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>(); 
			 resultList.add(map); 
			 
			 // 결과갱신
			 ResponseUtil.updateResultMap(resultMap, resultList); 
		}
		
		// 결과반환 
		return resultMap; 
	}
}
