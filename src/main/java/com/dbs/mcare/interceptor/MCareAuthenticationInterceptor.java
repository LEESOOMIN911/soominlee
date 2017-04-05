package com.dbs.mcare.interceptor;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;

import com.dbs.mcare.framework.FrameworkConstants;
import com.dbs.mcare.framework.interceptor.AuthenticationInterceptor;
import com.dbs.mcare.framework.service.ConfigureService;
import com.dbs.mcare.framework.util.HttpRequestUtil;
import com.dbs.mcare.service.LoginService;
import com.dbs.mcare.service.RememberMeCookieBaker;
import com.dbs.mcare.service.mobile.user.repository.dao.MCareUser;
/**
 * 요청에 대한 전처리 
 * 세션은 있는지, 제대로 된 곳으로 접근하고 있는지 등등 
 * 
 * @author hyeeun  
 *
 */
@Component 
public class MCareAuthenticationInterceptor extends AuthenticationInterceptor {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private RememberMeCookieBaker cookieBaker;
	@Autowired 
	private ConfigureService configureService; 
	@Autowired
	private LoginService loginService;
	
	// 패턴 매칭 작업용 
	private final AntPathMatcher matcher; 

	
	/**
	 * 생성자  
	 */
	public MCareAuthenticationInterceptor() { 
		this.matcher = new AntPathMatcher();
	}
	
	@Override 
	protected boolean isRequestByAdmin(String requestUri) { 
		return this.matcher.match(FrameworkConstants.URI_REQUEST_PATTERN.ADMIN.getPattern(), requestUri); 
	}
	
	@Override 
	protected boolean isRequestForLogin(String requestUri) { 
		return this.matcher.match(FrameworkConstants.URI_REQUEST_PATTERN.LOGIN.getPattern(), requestUri); 
	}
	
	@Override 
	protected boolean isRequestForLogout(String requestUri) { 
		return this.matcher.match(FrameworkConstants.URI_REQUEST_PATTERN.LOGOUT.getPattern(), requestUri); 
	}
	
	@Override 
	protected boolean isRequestForAjax(String requestUri) { 
		return this.matcher.match(FrameworkConstants.URI_REQUEST_EXT.JSON.getPattern(), requestUri); 
	}
	
	@Override 
	protected String getAdminLoginPageUrl() { 
		return FrameworkConstants.URI_SPECIAL_PAGE.ADMIN_LOGIN.getPage();
	}
	
	@Override 
	protected boolean isRequestForPage(String requestUri) { 
		return this.matcher.match(FrameworkConstants.URI_REQUEST_EXT.PAGE.getPattern(), requestUri); 
	}
	
	@Override 
	protected boolean isRequestByExternal(String requestUri) {
		return this.matcher.match(FrameworkConstants.URI_REQUEST_EXT.EXT.getPattern(), requestUri); 
	}
	
	
	@Override 
	protected boolean isForcedRedirectPage(String requestUri) {
		return FrameworkConstants.URI_SPECIAL_PAGE.AGREEMENT_JOIN.getPage().equals(requestUri) || // 서비스 가입 중 약관동의  
				FrameworkConstants.URI_SPECIAL_PAGE.CHANGE_MY_PWD.getPage().equals(requestUri) // 비밀번호 재설정 
				? true : false; 
	}
	
	@Override 
	protected boolean isIndexPage(String requestUri) {
		return this.matcher.match(FrameworkConstants.URI_REQUEST_PATTERN.INDEX.getPattern(), requestUri); 
	}
	
	@Override 
	protected boolean isHospitalList(String requestUri) {
		return this.matcher.match(FrameworkConstants.URI_SPECIAL_PAGE.HOSPITAL.getPage(), requestUri); 
	}
	
	@Override
	protected boolean isHelpPage(String requestUri){
		return this.matcher.match(FrameworkConstants.URI_SPECIAL_PAGE.HELP.getPage(), requestUri);
	}
	
	@Override 
	protected Map<String, Boolean> restoreSession(HttpServletRequest request, HttpServletResponse response) {
		// 결과 맵 
		final Map<String, Boolean> resultMap = new HashMap<>(); 
		// 복원할 사용자 
		MCareUser user = null; 
		// 전이할 페이지 정보 복원하고 
		final String restoreUri = HttpRequestUtil.getRedirectURI(request); 
		// 자동로그인 체크되어있으면 복원하기 
		try { 
			user = this.cookieBaker.restore(request, response, restoreUri); 
	        // 복원 성공했으면 로그를 남김 
			this.loginService.addloginHistory(user, true);
		}
		catch(Exception ex) { 
			if(this.logger.isDebugEnabled()) {
				this.logger.debug("자동로그인에 따른 세션복원 실패");
			}
			
			user = null; 
		}
		
		// 인증되었는지 여부 
		resultMap.put(KEY_AUTH_YN, (user != null) ? true : false); 
		resultMap.put(KEY_NEXT_PROCESS, true); 
		
		// 복원에 성공하면 다음 페이지 처리 
		if(user != null) { 
			if(this.logger.isDebugEnabled()) { 
				this.logger.debug("자동로그인에 의한 세션 복원 완료\n- restoreUri : " + restoreUri + "\n- nextPage : " + user.getNextPage());
			}	
			
			// 복원에 성공한 경우, 처음요청했던것과 다른 페이지가 다음페이지로 지정되면 비번유도나 서비스약관등록이 필요한 것임 
			// 그럼 그것을 우선적으로 처리해 주어야 함 
			if(StringUtils.isEmpty(user.getNextPage())) {
				String nextPage = HttpRequestUtil.generateRedirectUrl(request, this.configureService.getServerServiceAddr(), user.getNextPage()); 
				try { 	
					
					response.sendRedirect(nextPage); 
					resultMap.put(KEY_NEXT_PROCESS, false); 
				}
				catch(IOException ex) { 
					resultMap.put(KEY_NEXT_PROCESS, true);
					if(this.logger.isDebugEnabled()) {
						this.logger.debug("자동로그인 복원에 따른 다음 페이지 전이 실패. nextPage=" + nextPage);
					}
				}
			}			
		}
		
		return resultMap; 
	}

}
