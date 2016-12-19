package com.dbs.mcare.service.api;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dbs.mcare.exception.mobile.ApiCallException;
import com.dbs.mcare.framework.service.ConfigureService;

/**
 * SMS 전송 처리 서비스 
 * @author aple
 *
 */
@Service 
public class SendSmsService {
	@Autowired 
	private ConfigureService configureService; 
	@Autowired 
	private MCareApiCallService apiCallService; 
	
	/**
	 * SMS 전송 
	 * @param pName
	 * @param phoneNo
	 * @param message
	 * @throws ApiCallException
	 */
	public void sendSms(String pName, String phoneNo, String message) throws ApiCallException { 
		Map<String, Object> paramMap = new HashMap<>(); 
		
		paramMap.put("callBack", this.configureService.getSmsCallbackNumber()); 
		paramMap.put("destInfo", pName + "^" + phoneNo); 
		paramMap.put("smsMsg", message); 
		
		// 호출하기 
		this.apiCallService.execute(PnuhApi.USER_PUTSMS, paramMap); 
	}
	
	/**
	 * SMS 전송 
	 * @param phoneNo
	 * @param message
	 * @throws ApiCallException
	 */
	public void sendSms(String phoneNo, String message) throws ApiCallException {
		this.sendSms("", phoneNo, message); 
	}	
}
