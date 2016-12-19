package com.dbs.mcare.controller.mobile;

import java.util.HashMap;
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
import org.springframework.web.servlet.HandlerMapping;

import com.dbs.mcare.exception.mobile.MobileControllerException;
import com.dbs.mcare.service.api.MCareApiCallService;
import com.dbs.mcare.service.api.PnuhApi;
import com.dbs.mcare.service.api.util.ParamMappingUtil;

import net.sf.json.JSONArray;
/**
 * 간호호출 : 사용되지 않음. 사용할려면 코드정리 필요함. 
 * @author aple
 *
 */
@Controller
@RequestMapping("/mobile/call")
public class CallController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private MCareApiCallService apiCallService; 

	// 간호호출 
	@RequestMapping(value = "/prevCall.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object>  prevCall(HttpServletRequest request, HttpServletResponse response, 
			@RequestBody Map<String, Object> reqMap) throws MobileControllerException {

		if(this.logger.isDebugEnabled()) { 
			this.logger.debug("request : " + request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE));
		}
		
		// 요청변환 
		reqMap = ParamMappingUtil.requestParam(request, reqMap); 
		// 재원상태인지 먼저 확인 필요 
		final Map<String, Object> userMap = (Map<String, Object>) this.apiCallService.call(PnuhApi.USER_USERINFO_GETUSERINFO, reqMap); 
				
		// 등록되지 않은 환자의 경우 userMap은 비어있음 (우리 서비스에는 가입하고, 기간계에는 없는 환자번호 --> 현실에서는 발생하지 않을 것임) 
		// 재원여부에 값이 없는 경우 비어있는 JSONArray로 들어옴 
		if(userMap == null || userMap.isEmpty() ) {
				throw new MobileControllerException("mcare.service.call.nurse.error", "등록되지 않은 환자입니다."); 
		
		} 
		//재원중이 아니면 이전 내용 조회할 때, 불가능하게 정보를 전달한다
		else if( userMap.get("inHospitalYn").toString().equals("N") || userMap.get("inHospitalYn") instanceof JSONArray ){
			final Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("isHospitalYn", false);
			return resultMap;
		}
		
		//재원 중이면 이전 내역 있는지 조회해서 보내준다.
		return (Map<String, Object>) this.apiCallService.call(PnuhApi.CALL_NURSEPREVREQ, reqMap);
	}
	

	// 간호호출 
	@RequestMapping(value = "/nurse.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object>  callNurse(HttpServletRequest request, HttpServletResponse response, 
			@RequestBody Map<String, Object> reqMap) throws MobileControllerException {

		if(this.logger.isDebugEnabled()) { 
			this.logger.debug("request : " + request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE));
		}
		
		// 요청변환 
		reqMap = ParamMappingUtil.requestParam(request, reqMap); 
		
		// 재원상태인지 먼저 확인 필요 
		final Map<String, Object> userMap = (Map<String, Object>) this.apiCallService.call(PnuhApi.USER_USERINFO_GETUSERINFO, reqMap); 
		
		// 등록되지 않은 환자의 경우 userMap은 비어있음 (우리 서비스에는 가입하고, 기간계에는 없는 환자번호 --> 현실에서는 발생하지 않을 것임) 
		// 재원여부에 값이 없는 경우 비어있는 JSONArray로 들어옴 
		if(userMap == null || userMap.isEmpty() || userMap.get("inHospitalYn") instanceof JSONArray) {
			throw new MobileControllerException("mcare.service.call.nurse.inhospital", "간호요청은 입원상태에서만 가능합니다"); 
		}

		// 호출 및 결과 반환 
		return (Map<String, Object>) this.apiCallService.call(PnuhApi.CALL_NURSE, reqMap);
	}
}
