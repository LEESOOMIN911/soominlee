package com.dbs.mcare.controller.mobile;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.HandlerMapping;

import com.dbs.mcare.MCareConstants;
import com.dbs.mcare.MCareConstants.MCARE_TEST_USER.INFO;
import com.dbs.mcare.exception.mobile.ApiCallException;
import com.dbs.mcare.exception.mobile.MobileControllerException;
import com.dbs.mcare.framework.FrameworkConstants;
import com.dbs.mcare.framework.service.MessageService;
import com.dbs.mcare.framework.util.Base64ConvertUtil;
import com.dbs.mcare.framework.util.ConvertUtil;
import com.dbs.mcare.framework.util.DateUtil;
import com.dbs.mcare.framework.util.HashUtil;
import com.dbs.mcare.framework.util.ResponseUtil;
import com.dbs.mcare.framework.util.SessionUtil;
import com.dbs.mcare.service.PnuhConfigureService;
import com.dbs.mcare.service.RememberMeCookieBaker;
import com.dbs.mcare.service.api.MCareApiCallService;
import com.dbs.mcare.service.api.PnuhApi;
import com.dbs.mcare.service.api.SendSmsService;
import com.dbs.mcare.service.api.util.ParamMappingUtil;
import com.dbs.mcare.service.mobile.user.MCareUserService;
import com.dbs.mcare.service.mobile.user.UserAgreementService;
import com.dbs.mcare.service.mobile.user.UserRegisterService;
import com.dbs.mcare.service.mobile.user.repository.dao.MCareUser;

import Kisinfo.Check.IPINClient;

@Controller
@RequestMapping("/mobile/user")
public class UserController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired 
	private MCareApiCallService apiCallService; 
	@Autowired 
	private MCareUserService userService; 
	@Autowired 
	private UserRegisterService userRegisterService; 
	@Autowired 
	private UserAgreementService userAgreementService;
	
	@Autowired
	private MessageService messageService; 
	@Autowired
	private RememberMeCookieBaker cookieBaker;
	@Autowired 
	private PnuhConfigureService configureService; 
	@Autowired 
	private SendSmsService smsService; 
	
	/**
	 * 본인인증 페이지
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/checkPatientId.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> checkPatientId(HttpServletRequest request, HttpServletResponse response, 
			@RequestBody(required = false) MCareUser user) throws MobileControllerException {
		if(this.logger.isDebugEnabled()) {
			this.logger.debug("가입을 시도하는 환자 : " + user);
		}
		
		
		//Local에서 테스트 하는 경우 아래 기능을 전부 주석처리해주세요.
		MCareUser userInfo = null; 
		
		try { 
			userInfo = this.userRegisterService.getUserInfo(user.getpId()); 
			if(this.logger.isDebugEnabled()) {
				this.logger.debug("M-Care 등록여부 검사 : " + user.getpId() + ", DB확인결과 : " + userInfo);
			}
		}
		catch(final ApiCallException ex) {
			throw new MobileControllerException("mcare.error.system", "시스템 호출 오류가 발생했습니다."); 
		}
		
		//등록된 사용자인지 확인한다.
		if(userInfo != null) {
			throw new MobileControllerException("mobile.message.register005", "이미 등록된 환자번호입니다.");
		}
		
		Map<String, Object> resultMap = null;
		Map<String, Object> resMap = new HashMap<String, Object>(); //응답 결과 생성을 위한 Map
		
		String birthDay = null;
		try { 
			resultMap = (Map<String, Object>) this.apiCallService.execute(PnuhApi.USER_USERINFO_GETUSERINFO, "pId", user.getpId()); 
			
			//기간계에 환자 번호가 존재하지 않는 경우
			if(resultMap == null || resultMap.isEmpty()) {
				throw new MobileControllerException("mobile.message.register022", "환자번호가 없습니다. 원무과로 가서 환자번호를 확인하세요.");
			}
			
			birthDay = (String)resultMap.get("birthDt");
			boolean bUnder14 = DateUtil.checkUnder14(birthDay);
			//14세 미만인지 리턴
			resMap.put("bUnder14", bUnder14);
			
			//14세 미만인 경우
			if(bUnder14) {
				//14세 미만 가입 가능인 경우
				if(!this.configureService.getAllowJoinUnder14()) {
					throw new MobileControllerException("mobile.message.register007", "14세 미만 사용자는 가입할 수 없습니다.");
				}
			}
		}
		catch(ApiCallException ex) { 
			if(this.logger.isDebugEnabled()) {
				this.logger.debug("예외발생", ex); 
			}
			
			throw new MobileControllerException("mcare.error.if.system", "시스템 호출 오류가 발생했습니다 (IF)");
		} 
		catch (ParseException e) {
			throw new MobileControllerException("mcare.error.date.parse", "생일이 유효하지 않습니다. (" + birthDay + ")");  
		}	
		
		
		return ResponseUtil.wrapResultMap(resMap);
	}

	/**
	 * 본인인증 페이지
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/checkPlus.json", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, String> checkPlus(HttpServletRequest request, HttpServletResponse response) throws MobileControllerException {
		
		try { 
			return this.userRegisterService.checkPlusRequest(request);
		}
		catch(final Exception ex) { 
			this.logger.error("휴대폰본인인증", ex);
			throw new MobileControllerException(ex); 
		}
	}
	
	/**
	 * 아이핀인증 페이지
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/iPin.json", method = RequestMethod.GET)
	public Map<String, String> iPin(HttpServletRequest request,
			HttpServletResponse response, Model model) throws MobileControllerException {
		
		final IPINClient iPin = new IPINClient();
		if (this.logger.isDebugEnabled()) {
			this.logger.debug("## iPin lib test: {}", iPin);
		}
		
		Map<String, String> iPinMap = null; 
		
		try { 
			iPinMap = this.userRegisterService.iPinRequest(request);
		}
		catch(final Exception ex) { 
			this.logger.error("아이핀본인인증", ex);
			throw new MobileControllerException(ex); 
		}
		return iPinMap;
	}
	
	/**
	 * 본인인증 요청정보 생성
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/certificationRequest.json", method=RequestMethod.GET) 
	public Map<String, String> certificationRequest(HttpServletRequest request, HttpServletResponse response, Model model) throws MobileControllerException {
		// TODO 부산대에서는 해당 Controller가 호출될지는 모르겠음
		// 기존 부산대에서 인증 여청은 각가가의 iPin.json, checkPlus.json이 호출되고 있었음
		return this.userRegisterService.certificationRequest(request, response);
	}
	
	
	/**
	 * 본인인증 결과 
	 * checkPlus 휴대폰 인증인 경우는 success인 경우 호출되고
	 * iPin 인증인 경우는 result시 호출
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/certificationResult.page", method=RequestMethod.POST)
	public Model certificationResult(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		return this.userRegisterService.certificationResult(request, model);
	}
	
	/**
	 * SMS인증 문자전송을 요청하고 반환
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws MobileControllerException
	 */
	@RequestMapping(value = "/reqSMSCode.json", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Model reqSMSCode (HttpServletRequest request, HttpServletResponse response, Model model, 
			@RequestParam(value = "pId", required = true) String pId,
			@RequestParam(value = "pName", required = true) String pName,
			@RequestParam(value = "phoneNo", required = false) String phoneNo,
			@RequestParam(value = "certReqType", required = false) String certReqType) throws MobileControllerException {
		
		//인증 코드를 전송한 사용자 폰 번호
		String sendPhoneNo = this.userRegisterService.reqSmsCertionfication(pId, pName, request, model);
		
		//iPin인증, SMS인증과 같은 프로세스를 위한 파라미터를 설정한다.
		model.addAttribute("pNm", Base64ConvertUtil.base64Encode(pName));
		model.addAttribute("reservedParam3", Base64ConvertUtil.base64Encode(pId));
		model.addAttribute("sendPhoneNo", sendPhoneNo);

		return model;
	}
	
	/**
	 * SMS인증코드 확인
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws MobileControllerException
	 */
	@RequestMapping(value="/checkSMSCode.json", method=RequestMethod.GET)
	public Map<String, String> checkSMSCode(HttpServletRequest request,
			HttpServletResponse response, Model model) throws MobileControllerException {
		
		final String smsCode = request.getParameter("smsCode");
		final String sessionSmsCode = (String)request.getSession().getAttribute("smsCode");
		
		final Map<String, String> resultMap = new HashMap<String, String>();
		
		//session에 담았던 인증코드와 입력받은 인증코드가 같으면 success 반환
		if(smsCode.equals(sessionSmsCode)){
			resultMap.put("resultMsg", "success");
		} 
		else {
			resultMap.put("resultMsg", "fail");
		}
		
		return resultMap;
	}
	
	/**
	 * iPin 본인인증 성공
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/iPinSuccess.page", method = RequestMethod.POST)
	public Model iPinSuccess(HttpServletRequest request,
			HttpServletResponse response, Model model) throws MobileControllerException {
		
		return this.userRegisterService.iPinResult(request, model);
	}
	
	/**
	 * 휴대폰 본인인증 성공
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/checkPlusSuccess.page", method = RequestMethod.POST)
	public Model checkPlusSuccess(HttpServletRequest request,
			HttpServletResponse response, Model model) throws MobileControllerException {
		
		return this.userRegisterService.checkPlusResult(request, model);
	}
	
	/**
	 * 본인인증 실패
	 * 
	 * @param session
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/checkPlusFailed.page", method = RequestMethod.POST)
	public Model checkPlusFailed(HttpSession session, HttpServletRequest request,
			HttpServletResponse response, Model model) throws MobileControllerException {
		
		return this.userRegisterService.checkPlusFailed(request, model);
	}
	
	/**
	 * 비밀번호 등록 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws MobileControllerException
	 * @throws ServletRequestBindingException
	 */
	@RequestMapping(value="/registerPWD.page", method = RequestMethod.GET)
	public Model registerPWD (HttpServletRequest request, HttpServletResponse response, Model model) throws MobileControllerException,ServletRequestBindingException {
		return this.getCertiUserInfo(request, model);
	}
	
	/**
	 * 인증을 통한 사용자 패스워드 수정
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws MobileControllerException
	 */
	@RequestMapping(value="/setUserPwd.page", method = RequestMethod.GET)
	public Model setUserPwd (HttpServletRequest request, HttpServletResponse response, Model model) throws MobileControllerException {
		return this.getCertiUserInfo(request, model);
	}
	
	/**
	 * 비밀번호 재 설정
	 * @param user
	 * @param model
	 * @return
	 * @throws MobileControllerException
	 * @throws ServletRequestBindingException
	 */
	@RequestMapping(value="/resetPWD.json", method = RequestMethod.GET)
	public Model resetPWD (HttpServletRequest request, HttpServletResponse response, Model model) throws MobileControllerException, ServletRequestBindingException {
		String chartNoValue = SessionUtil.getUserId(request); 
		final String chartName = SessionUtil.getUserName(request); 
		// 
		final String newPassWordValue = request.getParameter("newPassWordValue");
		final String oldPassWordValue = request.getParameter("oldPassWordValue");
		final String resetPWDType = request.getParameter("resetPWDType");
		final HashUtil hashUtils = new HashUtil(this.configureService.getHashSalt()); 
		
		if(this.logger.isDebugEnabled()) { 
			this.logger.debug("사용자 : " + chartName);	
		} 
		
		if(resetPWDType.equals("changeMyPwd")) {
			final String hashOldPwd = hashUtils.sha256(oldPassWordValue);
			final boolean validPwd = this.userService.checkUserPassword(chartNoValue, hashOldPwd);
			
			if(!validPwd) {
				if(this.logger.isDebugEnabled()) {
					this.logger.debug("비밀번호초기화 실패 :pId = " + chartNoValue + ", oldPwd = " +oldPassWordValue + " (hash : " + hashOldPwd + "), newPwd = " + newPassWordValue);
				}
				
				model.addAttribute("resultMsg", "invalidPassword");
				return model;
			}
		}
		else {
			chartNoValue = request.getParameter("pId");
		}
		
		Map<String, Object> validPwdMap = this.userService.validatePWD(newPassWordValue);
		
		//패스워드 유효성 검사 결과가 False이면
		if(!(Boolean)validPwdMap.get("result")) {
			this.logger.error("입력된 패스워드가 유효하지 않습니다.");
			String messageCode = (String)validPwdMap.get("code");
			String message = this.messageService.getMessage(messageCode, request);	//사용자가 알아야 하니까. 다국어처리 해서 메시지 보냄
			
			throw new MobileControllerException(messageCode, message);
		}
		
		try {
			//SHA256변환 수행..  
			final String hashNewPwd = hashUtils.sha256(newPassWordValue); 
			this.userService.updateUserPassword(chartNoValue, hashNewPwd);
		} catch (final Exception e) {
			this.logger.error("패스워드 변경 실퍠", e);
			throw new MobileControllerException(e); 
		}
		
		// 비번 변경 성공했으면 강제전이 세션 키 지우기 
		final HttpSession session = request.getSession(); 
		session.removeAttribute(FrameworkConstants.SESSION.USER_FORCED_PAGE.getKey());
		
		return model;
	}


	/**
	 * 사용자가 동의해야할 최신 동의서 목록 반환
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws MobileControllerException
	 * @throws ServletRequestBindingException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/getNewAgreementList.json", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getNewAgreementList (HttpServletRequest request, HttpServletResponse response, Model model) throws MobileControllerException, ServletRequestBindingException {
		// 이미 가입된 사용자가 있는데, 새로운 동의서가 등록되거나 기존 동의서 version up에 의해 추가 등록되는 사항이 있으면 세션에 값이 있는 채로 여기 들어온다. 
		String pId = SessionUtil.getUserId(request);
		String birthDay = SessionUtil.getUserBirthday(request); 

		// 사용등록 후에 사용하기 위해서 세션이 아닌 파라미터로 넘어 올 경우를 처리한다. userAgreement.js와 key를 맞춘다. 
		final String strPid = request.getParameter("pId");
		
		if(this.logger.isDebugEnabled()) {
			StringBuilder builder = new StringBuilder(); 
			
			builder.append(FrameworkConstants.NEW_LINE); 
			builder.append("- session, pId = ").append(pId).append(FrameworkConstants.NEW_LINE); 
			builder.append("- parameter, pId = ").append(strPid).append(FrameworkConstants.NEW_LINE); 
			
			this.logger.debug(builder.toString());
		}
		
		//파라미터로 넘어오면 - 세션에는 값이 없을 것이므로 덮어쓴다.
		if( strPid != null && !strPid.equals("") ){
			pId = strPid;
			//id를 알고 있으므로 다시 읽어옴. 현 구조상 본인인증과 관련되어 다른 부분에 많은 의존성이 걸려있어 다시 읽어오도록 처리함
			Map<String,Object> resultMap = null;
			
			try { 
				resultMap = (Map<String, Object>) this.apiCallService.execute(PnuhApi.USER_USERINFO_GETUSERINFO, "pId", strPid); 
			}
			catch(ApiCallException ex) { 
				if(this.logger.isDebugEnabled()) {
					this.logger.debug("예외발생", ex); 
				}
				
				throw new MobileControllerException("mcare.error.if.system", "시스템 호출 오류가 발생했습니다 (IF)");
			}
			
			birthDay = (String)resultMap.get("birthDt");
		}

		List<Map<String, Object>> agreementList = null;
		
		try { 
			agreementList = this.userAgreementService.getUserNewAgreementList(pId);
		}
		catch(ApiCallException ex) { 
			this.logger.error("동의서 구하기 실패", ex);
			throw new MobileControllerException("mcare.error.system", "시스템 호출 오류가 발생했습니다."); 
		}
		
		// 14세미만인지 체크 
		boolean bUnder14 = false; 
		
		try { 
			bUnder14 = DateUtil.checkUnder14(birthDay); 
		}
		catch(ParseException ex) { 
			throw new MobileControllerException("mcare.error.date.parse", "생일이 유효하지 않습니다. (" + birthDay + ")");  
		}
		

		if(bUnder14) { 
			List<Map<String, Object>> under14AgreementList = null; 
			
			try { 
				under14AgreementList = this.userAgreementService.getUnder14UserNewAgreementList(pId);
			
				if (under14AgreementList.size() > 0) {
					agreementList.addAll(under14AgreementList);
				}
			}
			catch(ApiCallException ex) { 
				if(this.logger.isDebugEnabled()) {
					this.logger.debug("예외발생", ex); 
				}
				
				throw new MobileControllerException("mcare.error.if.system", "시스템 호출 오류가 발생했습니다 (IF)");
			}
		}
		
		return ResponseUtil.wrapResultMap(agreementList);
	}
	
	/**
	 * 사용자의 동의서 목록 반환
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws MobileControllerException
	 * @throws ServletRequestBindingException
	 */
	@RequestMapping(value="/getUserAgreementList.json", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getUserAgreementList (HttpServletRequest request, HttpServletResponse response, Model model) throws MobileControllerException, ServletRequestBindingException {
		final String pid = SessionUtil.getUserId(request); 
		//사용자 동의서 목록 조회 
		final List<Map<String, Object>> agreemntListMap = this.userAgreementService.getUserAgreementList(pid);
		return ResponseUtil.wrapResultMap(agreemntListMap);
	}
	
	/**
	 * 사용자가 동의한 동의서 등록
	 * @param user
	 * @return
	 * @throws MobileControllerException
	 * @throws ServletRequestBindingException
	 */
	@RequestMapping(value="/saveUserAgreement.json", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Model saveUserAgreement(HttpServletRequest request, HttpServletResponse response, @RequestBody(required = false) MCareUser user, Model model) throws MobileControllerException, ServletRequestBindingException {
		String pid = SessionUtil.getUserId(request); 
		// 사용등록 후에 사용하기 위해서 세션이 아닌 파라미터로 넘어 올 경우를 처리한다.
		final String strPid = user.getpId();
		//파라미터로 넘어오면 - 세션에는 값이 없을 것이므로 덮어쓴다.
		if( strPid != null && !strPid.equals("") ){
			pid = strPid;			
		}
		try {
			this.userService.insertUserAgreement(pid, user.getAgreementList());
		} 
		catch (final Exception e) {
			throw new MobileControllerException("mobile.message.agreement0007", "동의서 등록에 실패하였습니다.");
		}
		
		// 약관 등록 성공했으면 강제전이 세션 키 지우기 
		final HttpSession session = request.getSession(); 
		session.removeAttribute(FrameworkConstants.SESSION.USER_FORCED_PAGE.getKey());		
		
		return model;
	}
	
	
	/**
	 * 인증된 사용자를 등록함 
	 * @param request
	 * @param pId
	 * @param pName
	 * @param passWordValue
	 * @return
	 * @throws MobileControllerException
	 */
	@RequestMapping(value = "/registerCertifiedUser.json", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Map<String, Object> registerCertifiedUser(HttpServletRequest request, 
			@RequestParam(value = "pId", required = true) String pId,
			@RequestParam(value = "pName", required = false) String pName,  // 이제 필요없음 
			@RequestParam(value = "passWordValue", required = true) String passWordValue) throws MobileControllerException {

		if(this.logger.isDebugEnabled()) { 
			this.logger.debug("등록할 사용자 : " + pId + ", " + pName);	
		}
		
		Map<String, Object> validPwdMap = this.userService.validatePWD(passWordValue);
		
		//패스워드 유효성 검사 결과가 False이면
		if(!(Boolean)validPwdMap.get("result")) {
			this.logger.error("입력된 패스워드가 유효하지 않습니다.");
			String messageCode = (String)validPwdMap.get("code");
			String message = this.messageService.getMessage(messageCode, request);	//사용자가 알아야 하니까. 다국어처리 해서 메시지 보냄
			
			throw new MobileControllerException(messageCode, message);
		}
		
		// Client 에서 파라미터 검사는 했을테니 SHA256변환만 수행..  
		final String hashPwd = new HashUtil(this.configureService.getHashSalt()).sha256(passWordValue); 
		final String key = this.userService.insertUser(pId, hashPwd); 
		
		// 결과 맵 
		final Map<String, String> resultMap = new HashMap<String, String>(); 
		resultMap.put("localCipherKeyValue", key); 
		resultMap.put("pId", pId);
		
		if(this.logger.isInfoEnabled()) { 
			this.logger.info("등록된 사용자 : " + pId); 
		} 
		
		return ResponseUtil.wrapResultMap(resultMap); 
	}

	/**
	 * 환자명, 휴대전화번호로 환자번호 찾아서 SMS 보내주기 
	 * @param request
	 * @param response
	 * @param reqMap
	 * @return
	 * @throws MobileControllerException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/searchPid.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> searchPid(HttpServletRequest request, HttpServletResponse response, 
			@RequestBody Map<String, Object> reqMap) throws MobileControllerException { 

		if(this.logger.isDebugEnabled()) { 
			this.logger.debug("request : " + request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE));
		}
		
		// 필수항목 확인 
		if(reqMap == null || StringUtils.isEmpty(reqMap.get("pName")) || StringUtils.isEmpty(reqMap.get("cellphoneNo"))) {
			throw new MobileControllerException("mcare.error.param", "파라미터를 확인해주세요"); 
		}
		// 요청 파라미터는 client에서 넘어오는 것을 그대로 사용한다. 
		Map<String, Object> resultMap = null; 
		
		try{
			resultMap = (Map<String, Object>) this.apiCallService.execute(PnuhApi.USER_USERINFO_FINDPID, reqMap); 
		} catch(ApiCallException ex){
			if(this.logger.isDebugEnabled()) {
				this.logger.debug("예외발생", ex); 
			}
			
			throw new MobileControllerException("mcare.error.if.system", "시스템 호출 오류가 발생했습니다 (IF)");
		}
		
		//환자명과 핸드폰 번호를 조건으로 조회하기 때문에 핸드폰 번호가 맞지 않은 경우에도 발생
		//등록된 사용자가 없는 경우 resultMap이 비어 있음 그렇지만 null도 체크
		if(resultMap == null || resultMap.isEmpty()) { 
			resultMap = new HashMap<String, Object>(); 
			// 메시지는 등록되지 않았습니다. 다국어 코드
			resultMap.put("msg", "searchPId008"); 
			return resultMap; 
		}
		
		// 조회한 환자번호 꺼내와서 
		String findPid = (String)resultMap.get("pId");
		// 실험용 환자 모드가 켜져 있나? (iOS 앰 심사용 데모 테스트를 위한 코드이기도 함)
		if(MCareConstants.MCARE_TEST_USER.ACTIVATE) {
			INFO testUser = MCareConstants.MCARE_TEST_USER.INFO.convert(findPid);
			
			if(testUser != null) {
				resultMap.put("cellphoneNo", testUser.getCellPhoneNumber());
			}
		}

		final String birthDay = (String)resultMap.get("birthDt");
		final String sendPhoneNo = ConvertUtil.convertSecretPhoneNo((String)resultMap.get("cellphoneNo"));
		Map<String, Object> resMap = new HashMap<String, Object>();
		boolean bUnder14 = false;
		
		try {
			bUnder14 = DateUtil.checkUnder14(birthDay);

			//14세이상 환자가 본인인증페이지로 가지 않고 로그인페이지로 바로 전이되는 문제는 화면단에서 수정해야되는 부분입니다.
			//14세 미만인 경우 SMS로 환자번호 전송
			if(bUnder14) {
				
				//홍길동님의 환자번호는 01012345678입니다.
				String smsMessage = this.messageService.getMessage("mobile.message.searchPId010", request, new String[]{(String)resultMap.get("pName"), findPid});
				if(this.logger.isDebugEnabled()) { 
					this.logger.debug("smsMessage : " + smsMessage);
				}
				
				// 찾았으면, SMS 전송
				this.smsService.sendSms((String) resultMap.get("pName"), (String) resultMap.get("cellphoneNo"), smsMessage);
				
				resMap.put("sendPhoneNo", sendPhoneNo);
				resMap.put("msg", this.messageService.getMessage("mobile.message.searchPId013", request, new String[]{sendPhoneNo}));
			}
		} 
		catch (ApiCallException ex) {
			if(this.logger.isDebugEnabled()) {
				this.logger.debug("예외발생", ex); 
			}
			throw new MobileControllerException("mcare.error.if.system", "시스템 호출 오류가 발생했습니다 (IF)");
		}
		catch (ParseException e) {
			throw new MobileControllerException("mcare.error.date.parse", "생일이 유효하지 않습니다. (" + birthDay + ")");
		}
		
		resMap.put("bUnder14", bUnder14);
		resMap.put("pId", findPid);
		return ResponseUtil.wrapResultMap(resMap);
	}
	
	/**
	 * 환자번호로 사용자 정보 찾고 결과 반환
	 * @param request
	 * @param response
	 * @param reqMap
	 * @return
	 * @throws MobileControllerException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/checkUserInfo.json", method = RequestMethod.POST)
	public Map<String, Object> checkUserInfo(HttpServletRequest request, HttpServletResponse response, 
			@RequestBody Map<String, Object> reqMap) throws MobileControllerException { 
		if(this.logger.isDebugEnabled()) { 
			this.logger.debug("request : " + request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE));
		}

		final String pId = (String)reqMap.get("pId");
		final String pName = (String)reqMap.get("pNm");
		final HashUtil hashUtils = new HashUtil(this.configureService.getHashSalt());
		
		// 필수항목 확인 
		if(reqMap == null || StringUtils.isEmpty(reqMap.get("pId"))) {
			throw new MobileControllerException("mcare.error.param", "파라미터를 확인해주세요"); 
		}
		
		// 요청 파라미터는 clinet에서 넘어오는 것을 그대로 사용한다. 
		Map<String, Object> resultMap = null; 
		
		try { 
			resultMap = (Map<String, Object>) this.apiCallService.execute(PnuhApi.USER_USERINFO_GETUSERINFO, reqMap); 
		}
		catch(ApiCallException ex) { 
			if(this.logger.isDebugEnabled()) {
				this.logger.debug("예외발생", ex); 
			}
			
			throw new MobileControllerException("mcare.error.if.system", "시스템 호출 오류가 발생했습니다 (IF)");
		}		
		
		// 기간계에 환자 정보가 없으면
		if(resultMap == null) { 
			throw new MobileControllerException("mobile.message.patient002", "환자정보가 존재하지 않습니다.");
		}
		
		//pId 로 조회된 사용자 정보의 이름과 파라미터로 입력된 이름이 다른경우
		if(!pName.equals(resultMap.get("pName"))) {
			throw new MobileControllerException("mobile.message.authUserInfo016", "환자번호의 환자명과 입력하신 환자명이 같지 않습니다.");
		}
		
		//mcareUser로 등록된 사용자인지 확인한다.
		final MCareUser userInfo = this.userRegisterService.getUserInfo(pId);
		
		//등록된 사용자 정보가 없는 경우
		if(userInfo == null) {
			throw new MobileControllerException("mobile.message.authUserInfo010", "등록되지 않았습니다.");
		}
		
		final String birthDt = (String)resultMap.get("birthDt");
		boolean bUnder14 = false;
		
		try {
			bUnder14 = DateUtil.checkUnder14(birthDt);
		} catch (ParseException e) {
			throw new MobileControllerException("mcare.error.date.parse", "생일이 유효하지 않습니다. (" + birthDt + ")");
		}
		
		Map<String, Object> resMap = new HashMap<String, Object>();

		//14세 미만인가
		try {
			if(bUnder14) {
				final String hashNewPwd = hashUtils.sha256(birthDt); 
				resultMap.put("passwordValue", hashNewPwd);
				this.apiCallService.call(PnuhApi.USER_PASSWORD_TEMPRESETPWD, resultMap); 
			}
		}
		catch(ApiCallException ex) { 
			if(this.logger.isDebugEnabled()) {
				this.logger.debug("예외발생", ex); 
			}
			throw new MobileControllerException("mcare.error.if.system", "시스템 호출 오류가 발생했습니다 (IF)");
		}
		
		//웹에서 bUnder14를 확인하여 알림 메시지를 보여주거나 본인인증 화면으로 이동한다.
		resMap.put("bUnder14", bUnder14);
		return ResponseUtil.wrapResultMap(resMap);
	}	
	
	/**
	 * 차량번호 조회
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getCarNo.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object>  getCarNo(HttpServletRequest request, HttpServletResponse response, 
			@RequestBody Map<String, Object> reqMap) throws MobileControllerException {
		
		if(this.logger.isDebugEnabled()) { 
			this.logger.debug("request : " + request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE));
		}
		
		// 요청변환 
		reqMap = ParamMappingUtil.requestParam(request, reqMap); 
		
		// 호출 
		Map<String, Object> resultMap = null; 
		
		try { 
			resultMap = (Map<String, Object>) this.apiCallService.execute(PnuhApi.USER_USERINFO_GETUSERINFO, reqMap); 
		}
		catch(ApiCallException ex) { 
			if(this.logger.isDebugEnabled()) {
				this.logger.debug("예외발생", ex); 
			}
			
			throw new MobileControllerException("mcare.error.if.system", "시스템 호출 오류가 발생했습니다 (IF)");
		}		

		// 소중한 고객정보가 다 주렁주렁 딸려왔기 때문에 차량번호만 꺼내서 줌 
		final Map<String, Object> carMap = new HashMap<>(); 
		final String key = "vehicleNo"; 
		carMap.put(key, resultMap.get(key)); 
		
		return ResponseUtil.wrapResultMap(carMap); 
	}	


	/**
	 *  차량번호 변경 요청 
	 * @param request
	 * @param response
	 * @param reqMap
	 * @return
	 * @throws MobileControllerException
	 */
	@RequestMapping(value = "/changeCarNo.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object>  parkCarNo(HttpServletRequest request, HttpServletResponse response, 
			@RequestBody Map<String, Object> reqMap) throws MobileControllerException {

		if(this.logger.isDebugEnabled()) { 
			this.logger.debug("request : " + request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE));
		}
		
		// 요청변환 
		reqMap = ParamMappingUtil.requestParam(request, reqMap); 

		try { 
			// 호출 및 결과 반환 
			return ResponseUtil.wrapResultMap( this.apiCallService.execute(PnuhApi.PARK_CARNO, reqMap) );
		}
		catch(ApiCallException ex) { 
			if(this.logger.isDebugEnabled()) {
				this.logger.debug("예외발생", ex); 
			}
			
			throw new MobileControllerException("mcare.error.if.system", "시스템 호출 오류가 발생했습니다 (IF)");
		}
	}

	/**
	 * 주소 조회
	 * @param request
	 * @param response
	 * @param reqMap
	 */
	@RequestMapping(value = "/getUserAddr.json", method = RequestMethod.POST)
	public Map<String, Object> getUserAddr(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Map<String, Object> reqMap) throws MobileControllerException { 
		if(this.logger.isDebugEnabled()) { 
			this.logger.debug("request : " + request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE));
		}

		// 파라미터 정리 
		reqMap = ParamMappingUtil.requestParam(request, reqMap); 
		// 실행 
		try { 
			return ResponseUtil.wrapResultMap( this.apiCallService.execute(PnuhApi.USER_USERINFO_GETUSERINFO, reqMap) ); 
		}
		catch(ApiCallException ex) { 
			if(this.logger.isDebugEnabled()) {
				this.logger.debug("예외발생", ex); 
			}
			
			throw new MobileControllerException("mcare.error.if.system", "시스템 호출 오류가 발생했습니다 (IF)");
		}
	}
	
	/**
	 * 탈퇴 
	 * @param request
	 * @param response
	 * @param reqMap
	 * @return
	 * @throws MobileControllerException
	 */
	@RequestMapping(value = "/withdrawal.json", method = RequestMethod.POST)
	public Map<String, Object> withdrawalUser(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Map<String, Object> reqMap) throws MobileControllerException { 
		if(this.logger.isDebugEnabled()) { 
			this.logger.debug("request : " + request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE));
		}
		
		
		// 파라미터 정리 
		reqMap = ParamMappingUtil.requestParam(request, reqMap); 
		// 환자변호 
		final String pId = (String) reqMap.get("pId"); 
		// 비번 
		final String plainPassword = (String) reqMap.get("passWord"); 

		// 유효성 확인 
		if(StringUtils.isEmpty(pId) || StringUtils.isEmpty(plainPassword)) {
			throw new MobileControllerException("mcare.auth.unauthenticated", "인증되지 않은 사용자입니다"); 
		}
		
		// 비번확인 
		final HashUtil hashUtils = new HashUtil(this.configureService.getHashSalt()); 
		final boolean checkedPwd = this.userService.checkUserPassword(pId, hashUtils.sha256(plainPassword)); 
		if(!checkedPwd) { 
			throw new MobileControllerException("admin.auth.checkaccount", "비밀번호를 다시 확인하세요"); 
		}
		
		final Map<String, Object> resultMap = new HashMap<String, Object>(); 
		
		// 삭제 
		try { 
			this.userService.removeUser(pId);
			resultMap.put("result", "success"); 
			
			try { 
				// 회원정보 삭제 성공시, 자동로그인 쿠키 삭제 
				this.cookieBaker.remove(request, response);
			} 
			catch(final Exception ex) {
				this.logger.error("자동로그인 쿠키 삭제 실패. 계속진행함.", ex);
				// 쿠키삭제실패해도, 이후처리 계속 가능함
			}
			//그리고 세션 날림
			final HttpSession session = request.getSession(false);
			if (session != null) {
				this.logger.debug("로그아웃을 위해 세션 초기화 처리");
				session.invalidate();
			}
		}
		catch(final Exception ex) {
			if(this.logger.isDebugEnabled()) {
				this.logger.debug("예외발생", ex); 
			}
			
			resultMap.put("result", "fail"); 
		}
	
		return resultMap; 
	}
	
	/**
	 * 인증된 사용자 정보를 Base64Decode하여 반환
	 * @param request
	 * @param model
	 * @return
	 */
	private Model getCertiUserInfo(HttpServletRequest request, Model model) {
		//이름을 Base64로 인코딩한 결과에 +가 포함될수 있는데 (예:홍길동)이 경우 +는 http get을 이용해서 전달할 수 없다. 
		//그래서 +가 공백으로 넘어오는데 이를 다시 복원해주기 위함이다.
		final String pNm = Base64ConvertUtil.base64Decode(request.getParameter("pNm").replace(" ", "+"));
		final String pId = Base64ConvertUtil.base64Decode(request.getParameter("reservedParam3"));
		model.addAttribute("pNm", pNm);
		model.addAttribute("pId", pId);
		
		//SMS인증에서는 사용되지 않는 파라미터
		if(request.getParameter("userBirthDate") != null) {
			final String userBirthDate = Base64ConvertUtil.base64Decode(request.getParameter("userBirthDate"));
			model.addAttribute("userBirthDate", userBirthDate);
		}
		
		//SMS인증에서는 사용되지 않는 파라미터
		if(request.getParameter("userGenderCode") != null) {
			final String userGenderCode = Base64ConvertUtil.base64Decode(request.getParameter("userGenderCode"));
			model.addAttribute("userGenderCode", userGenderCode);
		}
		
		return model;
	}
}