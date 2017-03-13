package com.dbs.mcare.service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.dbs.mcare.exception.mobile.ApiCallException;
import com.dbs.mcare.exception.mobile.MobileControllerException;
import com.dbs.mcare.framework.FrameworkConstants;
import com.dbs.mcare.framework.exception.service.LoginException;
import com.dbs.mcare.framework.exception.service.PwdForcedChangeException;
import com.dbs.mcare.framework.exception.service.PwdOverDueException;
import com.dbs.mcare.framework.exception.service.UserBlockException;
import com.dbs.mcare.framework.service.AuthenticationService;
import com.dbs.mcare.framework.service.ConfigureService;
import com.dbs.mcare.framework.util.DateUtil;
import com.dbs.mcare.service.api.MCareApiCallService;
import com.dbs.mcare.service.api.PnuhApi;
import com.dbs.mcare.service.mobile.user.UserAgreementService;
import com.dbs.mcare.service.mobile.user.repository.dao.MCareUser;

/**
 * 사용자를 인증하고, 어느 페이지로 넘어가야할지 체크한다. 
 * 
 * @author heesung, hyeeun 
 * 
 */
@Service
public class AuthenticationDelegator {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private RememberMeCookieBaker cookieBaker;
	@Autowired 
	private MCareApiCallService apiCallService; 
	@Autowired 
	private UserAgreementService userAgreementService; 
	@Autowired 
	private ConfigureService configureService; 
	@Autowired 
	private AuthenticationService authService; 

	
	/**
	 * 로그인 처리 <br/> 
	 * 기존에는 plainText로만 로그인을 했는데, 생체정보를 이용한 로그인 기능이 추가되면서 hash적용된 상태의 값이 전달되는 것으로 변경되었음 
	 * 
	 * @param request 요청객체 
	 * @param response 응답객체 
	 * @param pId 환자번호 
	 * @param hashPassWord hash적용된 비번
	 * @param forcedPage 강제전이 페이지 
	 * @return 사용자 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public MCareUser authentication(HttpServletRequest request, HttpServletResponse response, String pId, String hashPassWord, String forcedPage) throws Exception {		
		// 사용자 플랫폼 확인 
		this.authService.checkAccessPlatform(request, pId);

		
		// 환자번호로 사용자 정보를 가져오고  
		Map<String, Object> resultMap = null; 
		
		try { 
			resultMap = (Map<String, Object>) this.apiCallService.execute(PnuhApi.USER_USERINFO_GETLOGININFO, "pId", pId); 
		} 
		catch(ApiCallException ex) { 
			// 어쨌든 화면에서는 로그인 처리실패로 진행되어야할것임 
			throw new LoginException("mcare.error.if.system", "시스템 호출 오류가 발생했습니다 (IF)", null);			
		}
			
		// 유효성 확인. hash적용된 비번은 64bytes, 사용자가 입력하는 비번은 8글자 이상 
		if(resultMap == null || (hashPassWord == null || hashPassWord.length() < 64)) { 
			if(this.logger.isDebugEnabled()) { 
				this.logger.debug("유효하지 않은 정보롤 가지고 로그인 시도한 사용자 : " + pId + ", 비번 : " + hashPassWord);
			} 
			
			
			// 등록된 사용자가 없는 경우이므로, 로그인 실패처리도 해줄 수 없는 경우임 
			throw new LoginException("mcare.auth.checkaccount", "아이디 또는 비밀번호를 다시 확인하세요", null);
		}
		
		// 비번이 NULL인 사용자인가? 즉, 비번유도로 넘어가야하는 사람 
		if(StringUtils.isEmpty(resultMap.get("passwordValue"))) { 
			// 비번유도 
			throw new PwdForcedChangeException("mcare.auth.pwd.forced.change", "비밀번호 재설정이 필요합니다", FrameworkConstants.URI_SPECIAL_PAGE.AUTH_USER_INFO); 
		}
		
		
		// 변환 
		final MCareUser user = MCareUser.convert(resultMap); 
		
		// block된 사용자인가? 
		if(user.getLoginFailCnt() != null && user.getLoginFailCnt() >= this.configureService.getPasswordTryCount()) {
			this.logger.error("block된 사용자 : " + user.getpId()); 
			throw new UserBlockException("mcare.auth.blockaccount", "비밀번호 입력회수 실패 초과로 계정이 잠겼습니다. 비밀번호 재설정이 필요합니다.", FrameworkConstants.URI_SPECIAL_PAGE.AUTH_USER_INFO); 
		}
		
//		if(this.logger.isDebugEnabled()) {
//			StringBuilder builder = new StringBuilder(); 
//			
//			builder.append(FrameworkConstants.NEW_LINE);
//			builder.append("로그인확인=====").append(FrameworkConstants.NEW_LINE); 
//			builder.append("- 사용자 : ").append(pId).append(FrameworkConstants.NEW_LINE); 
//			builder.append("- 입력한 비번 : ").append(plainPassWord).append(FrameworkConstants.NEW_LINE); 
//			builder.append("- 비번hash : ").append(hashPwd).append(FrameworkConstants.NEW_LINE); 
//			builder.append("- DB의 비번 : ").append(user.getPassWordValue()).append(FrameworkConstants.NEW_LINE); 
//			
//			this.logger.debug(builder.toString());
//		}
		

		// 비밀번호가 맞나? 
		if(hashPassWord.equals(user.getPasswordValue())) {
			// 로그인 실패 횟수 초기화 
			if(user.getLoginFailCnt() > 0) {
				try {
					this.apiCallService.execute(PnuhApi.USER_LOGIN_CLEARLOGINFAILCNT, "pId", pId); 
				}
				catch(final ApiCallException ex) {
					this.logger.error("로그인 실패횟수 초기화 실패", ex);
					// 실패해도 로그인은 되어야 하기 때문에 로그만 찍는다.
					// 관리자는 이 사실을 언제쯤 알게될까? 
				}
			}
		}
		// 비밀번호가 틀렸나 
		else {
			// 자동로그인이지만 패스워드가 바뀌어서 로그인에 실패한 경우 기존 쿠키는 반드시 지워야 한다
			this.cookieBaker.remove(request, response);

			// 로그인 실패횟수 증가 
			try { 
				this.apiCallService.execute(PnuhApi.USER_LOGIN_INCLOGINFAILCNT, "pId", pId); 
			}
			catch(final ApiCallException fex) { 
				this.logger.error("로그인 실패횟수 증가 실패", fex);
				// 이게 실패하더라도 어차피 로그인은 안되는 거고, 
				// 사용자한테 로그인 실패 횟수 증가시키다 오류 났다고 알려줄 필요도 없기 때문에 
				// 로그만 찍기로 한다. 
			}	
			// 해당 ID에 대한 로그인 실패 처리 
			throw new LoginException("mcare.auth.checkaccount", "아이디 또는 비밀번호를 다시 확인하세요", FrameworkConstants.URI_SPECIAL_PAGE.USER_LOGIN);			
		}
		
		// 우리시스템에는 사용자 개인정보 저장을 최소화한다. 
		// 하여 환자번호와 이름만 저장해두는데 세션에는 생년월일도 심어야 한다. (미성년자 동의서 때문임) 
		// 생년월일은 기간계에서 즉시 받아와서 사용한다. 
		// 환자마스터는 병원이 망하지 않는 이상 계속 가지고 있는다고 하고, 기간계 부하도 걱정했으나 걱정말고 호출하라고 해서 그러기로 한다. 
		Map<String, Object> legacyUserMap = null; 
		
		try { 
			legacyUserMap = (Map<String, Object>) this.apiCallService.execute(PnuhApi.USER_USERINFO_GETUSERINFO, "pId", pId); 
		}
		catch(ApiCallException ex) { 
			throw new LoginException("mcare.error.if.system", "시스템 호출 오류가 발생했습니다 (IF)", FrameworkConstants.URI_SPECIAL_PAGE.USER_LOGIN);	
		}
		
		//환자 정보가 기간계에 있는지 확인
		if(legacyUserMap == null || legacyUserMap.isEmpty()) {
			throw new LoginException("mobile.message.login008", "등록된 환자번호가 존재하지 않습니다.", FrameworkConstants.URI_SPECIAL_PAGE.USER_LOGIN);	
		}

		// 14세미만인지 체크 
		boolean bUnder14 = false; 
		String birthDay = (String) legacyUserMap.get("birthDt");
		
		try { 
			bUnder14 = DateUtil.checkUnder14(birthDay); 
		}
		catch(ParseException ex) { 
			throw new MobileControllerException("mcare.error.date.parse", "생일이 유효하지 않습니다. (" + birthDay + ")");  
		}
		
		// 환자번호와 비번을 올바르게 작성했고, 로그인 실패 횟수도 초기화 되었다면 세션에 사용자 정보 심기 
		this.authService.saveSession(request, pId, (String) legacyUserMap.get("pName"), user.getLocalCipherKeyValue(), birthDay);		

		// 사용자가 자기 손으로 순순히 로그인 하는 경우에는 대부분 index page로 가야 하지만 
		// 로그인하지 않은 상태에서 push를 받고, 로그인 한 다음에 어디로 가는 경우라던지 (2016.2.2.현재. 아직 고려되지 않고 있는 서비스 경로임) 
		// 로그인 된 상태에서 세션이 끊어졌는데 로그인이 필요한 어떤메뉴(예:번호표발급)를 누른 경우 자동로그인을 탄 다음에 해당 메뉴로 곧장 가기 위해 필요함 
		if(forcedPage != null) {
			// 이름은 page이지만, 사실 json요청일수도 있음 
			user.setNextPage(forcedPage);
			// index로 보낼려면 이렇게.... 
			//user.setNextPage(MCareConstants.URI_MCARE_INDEX);
		}
		
		// 로그인 비번 변경 유도가 필요한지  
		if(this.authService.checkPasswordValidDate(user.getPasswordUpdateDt().getTime())) {
			// UI에서 에러메시지를 출력하고, 넘어갈 수 있도록 예외를 전달한다. 
			throw new PwdOverDueException("mcare.auth.pwdoverdue", "비밀번호 유효기간이 만료되었습니다. 비밀번호 재설정이 필요합니다.",  FrameworkConstants.URI_SPECIAL_PAGE.CHANGE_MY_PWD); 
		
		}
		
		// 사용자 동의 필요한지 확인. 예외는 전파됨. 
		final List<Map<String, Object>> agreementList = this.userAgreementService.getUserNewAgreementList(user.getpId());
		
		// 14세 미만인가? 
		if(bUnder14) {
			//14세 미만 동의서가 필요한지 확인
			List<Map<String, Object>> under14AgreementList = null; 
			
			try { 
				under14AgreementList = this.userAgreementService.getUnder14UserNewAgreementList(user.getpId());
			}
			catch(ApiCallException ex) { 
				if(this.logger.isDebugEnabled()) {
					this.logger.debug("예외발생", ex); 
				}
				
				// 어쨌든 화면에서는 로그인 처리실패로 진행되어야할것임 
				throw new LoginException("mcare.error.if.system", "시스템 호출 오류가 발생했습니다 (IF)",  FrameworkConstants.URI_SPECIAL_PAGE.USER_LOGIN);
			}
			
			
			if (under14AgreementList.size() > 0) {
				agreementList.addAll(under14AgreementList);
			}
		}
		
		// 새롭게 동의할 내용이 있다면, 동의 페이지로 강제전이되어야 함 
		if(agreementList.size() > 0) {
			StringBuilder nextUriBuilder = new StringBuilder(); 
			
			nextUriBuilder.append(FrameworkConstants.URI_SPECIAL_PAGE.AGREEMENT_JOIN.getPage()).append("?"); 
			nextUriBuilder.append(FrameworkConstants.IS_FORCED_PAGE_PARAM).append("=true&"); 
			nextUriBuilder.append(FrameworkConstants.MENU_ID).append("="); 
			nextUriBuilder.append(FrameworkConstants.URI_SPECIAL_PAGE.AGREEMENT_JOIN.getMenuId()); 
			
			user.setNextPage(nextUriBuilder.toString());
			// 강제전이 페이지에 갔다가, 앱이 죽었다가 다시 살아나더라도 강제전이 페이지로 갈 수 있도록 세션에 정보를 심어둠 
			request.getSession().setAttribute(FrameworkConstants.SESSION.USER_FORCED_PAGE.getKey(), FrameworkConstants.URI_SPECIAL_PAGE.AGREEMENT_JOIN.getPage());
			
		}
		
		return user;
	}

}