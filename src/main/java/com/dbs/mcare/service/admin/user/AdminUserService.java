package com.dbs.mcare.service.admin.user;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.dbs.mcare.exception.admin.AdminControllerException;
import com.dbs.mcare.exception.mobile.ApiCallException;
import com.dbs.mcare.exception.mobile.MobileControllerException;
import com.dbs.mcare.framework.service.MessageService;
import com.dbs.mcare.framework.util.HashUtil;
import com.dbs.mcare.service.PnuhConfigureService;
import com.dbs.mcare.service.api.MCareApiCallService;
import com.dbs.mcare.service.api.PnuhApi;
import com.dbs.mcare.service.api.SendSmsService;
import com.dbs.mcare.service.mobile.user.MCareUserService;
import com.dbs.mcare.service.mobile.user.UserRegisterService;
import com.dbs.mcare.service.mobile.user.repository.dao.MCareUser;

/**
 * 관리자의 사용자(환자) 조작 
 * 직접적인 DB CRUD가 아니라서 template 대로 작성되지 않는 것임 
 * 
 * @author aple
 *
 */
@Service 
public class AdminUserService {
	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private UserRegisterService userRegisterService;
	@Autowired
	private MCareApiCallService apiCallService;
	@Autowired
	private PnuhConfigureService configureService;
	@Autowired
	private SendSmsService smsService;
	@Autowired 
	private MCareUserService userService; 
	@Autowired
	private MessageService messageService;
	
	/**
	 * 사용자 등록 
	 * @param pId 등록할 환자번호 
	 * @param phoneNumber SMS로 임시비밀번호를 전달해줄 전화번호 
	 * @return 결과 {success:true/false, message:결과메시지}. 실패면 그 이유. 성공이면 어디로 전화번호를 넘겼는지 안내. 
	 * @throws MobileControllerException
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> registerUser(String pId, String phoneNumber, HttpServletRequest request) throws MobileControllerException {
		//Message Resource
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("success", false);
		
		if(StringUtils.isEmpty(pId)) {
			resultMap.put("message", this.messageService.getMessage("mcare.error.param", request));
			return resultMap;
		}
		
		if(this.logger.isDebugEnabled()) { 
			this.logger.info("등록할 사용자 : " + pId);	
		}

		Map<String, Object> userMap = null;
		
		try { 
			userMap = (Map<String, Object>) this.apiCallService.execute(PnuhApi.USER_USERINFO_GETUSERINFO, "pId", pId); 
			//기간계에 환자 번호가 존재하지 않는 경우
			if(userMap == null || userMap.isEmpty()) {
				resultMap.put("message", this.messageService.getMessage("mobile.message.register022", request));
				return resultMap;
			}
		}
		catch(ApiCallException ex) { 
			if(this.logger.isDebugEnabled()) {
				this.logger.debug("예외발생", ex); 
			}
			
			resultMap.put("message", this.messageService.getMessage("mcare.error.system", request));
			return resultMap;
		} 
		
		try {
			//문자 전송에 필요한 데이터
			String pName = (String) userMap.get("pName");
			String cellphoneNo = (String) userMap.get("cellphoneNo");
			String birthDt = (String) userMap.get("birthDt");
			
			//PhoneNumber가 비어 있지 않으면 cellphoneNo에 phoneNumber 사용
			if(!StringUtils.isEmpty(phoneNumber)) {
				cellphoneNo = phoneNumber;
			}
			
			final String special = "!@#$%^&*?_~";
			final StringBuilder tmpPWDbuilder = new StringBuilder(); //임시 비밀 번호 생성
			//님의 환자번호는. 결과는 환자이름이 pNm으로 넘어옴 
			tmpPWDbuilder.append(RandomStringUtils.randomAlphabetic(2)); //알파뱃 문자열 두자리
			tmpPWDbuilder.append(birthDt); 
			tmpPWDbuilder.append(RandomStringUtils.random(2, special));  //special 문자열 두자리

			//패스워드 생성
			final String hashPwd = new HashUtil(this.configureService.getHashSalt()).sha256(tmpPWDbuilder.toString());
			//SMS전송을 위한 요건이 되나 판단해서 보내자
			this.userService.insertUser(pId, hashPwd);
			
			//SMS 전송 문자 ex) 홍길동님의 임시패스워드는 ab20160101!@입니다.
			String tmpPWDMessage = this.messageService.getMessage("admin.user.register.message", request, new String[]{pName, tmpPWDbuilder.toString()});
			
			//문자전송
			this.smsService.sendSms(pName, cellphoneNo, tmpPWDMessage);
			
			resultMap.put("success", true);
			resultMap.put("message", this.messageService.getMessage("admin.user.register.success.message", request, new String[]{cellphoneNo}));
			
		} 
		catch (AdminControllerException e) {
			//예외
			resultMap.put("message", this.messageService.getMessage("mcare.error.system", request));
			return resultMap;
		}
		
		if(this.logger.isInfoEnabled()) { 
			this.logger.info("등록된 사용자 pId : " + pId);
		}
		
		return resultMap; 
	}
	
	/**
	 * 사용자 등록 가능 여부 확인
	 * @param pId
	 * @param request
	 * @return
	 * @throws MobileControllerException
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> checkUser(String pId, HttpServletRequest request) throws MobileControllerException {
		//Local에서 테스트 하는 경우 아래 기능을 전부 주석처리해주세요.
		MCareUser userInfo = null; 
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("success", false);
		
		try { 
			userInfo = this.userRegisterService.getUserInfo(pId); 
		}
		catch(final ApiCallException ex) {
			if(this.logger.isDebugEnabled()) {
				this.logger.debug("예외발생", ex); 
			}

			resultMap.put("message", this.messageService.getMessage("mcare.error.system", request));
			return resultMap;
		}
		
		//등록된 사용자인지 확인한다.
		if(userInfo != null) {
			resultMap.put("message", this.messageService.getMessage("mobile.message.register005", request));
			return resultMap;
		}
		
		try { 
			Map<String, Object> userMap = (Map<String, Object>) this.apiCallService.execute(PnuhApi.USER_USERINFO_GETUSERINFO, "pId", pId); 

			//기간계에 환자 번호가 존재하지 않는 경우
			if(userMap == null || userMap.isEmpty()) {
				resultMap.put("message", this.messageService.getMessage("mobile.message.register022", request));
				return resultMap;
			}
		}
		catch(ApiCallException ex) { 
			if(this.logger.isDebugEnabled()) {
				this.logger.debug("예외발생", ex); 
			}
			
			resultMap.put("message", this.messageService.getMessage("mcare.error.system", request));
			return resultMap;
		} 
		
		//가입 가능인 경우 success true 이 외에는 예외로 메시지 전달
		resultMap.put("success", true);
		
		return resultMap;
	}
}
