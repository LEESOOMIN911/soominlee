package com.dbs.mcare.service;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dbs.mcare.exception.mobile.MobileControllerException;
import com.dbs.mcare.framework.FrameworkConstants;
import com.dbs.mcare.framework.exception.service.LoginException;
import com.dbs.mcare.framework.exception.service.PwdOverDueException;
import com.dbs.mcare.framework.service.admin.history.repository.dao.LoginHistory;
import com.dbs.mcare.framework.service.log.LogService;
import com.dbs.mcare.framework.service.log.type.LogType;
import com.dbs.mcare.framework.util.HashUtil;
import com.dbs.mcare.service.mobile.user.TokenService;
import com.dbs.mcare.service.mobile.user.repository.dao.MCareUser;

/**
 * 로그인 처리 
 * @author aple
 *
 */
@Service 
public class LoginService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private AuthenticationDelegator authDelegator;
	@Autowired
	private RememberMeCookieBaker cookieBaker;
	@Autowired 
	private LogService logService; 
	@Autowired 
	private TokenService tokenService; 
	@Autowired 
	private PnuhConfigureService configureService; 
	
	
	private HashUtil hashUtils; 
	
	
	@PostConstruct 
	public void afterPropertiesSet() { 
		this.hashUtils = new HashUtil(this.configureService.getHashSalt()); 
	}
	
	/**
	 * 자동로그인 여부를 선택할 수 있는 로그인 처리 
	 * @param request
	 * @param response
	 * @param pId
	 * @param plainPassWord
	 * @param rememberMe
	 * @param tokenId
	 * @param certType
	 * @return
	 * @throws LoginException
	 */
	public MCareUser login(HttpServletRequest request, HttpServletResponse response,
			String pId,
			String plainPassWord,
			boolean rememberMe, 
			String tokenId, 
			String certType ) throws LoginException {

		MCareUser user = null; 
		
		// 기본 로그인 처리 
		try { 
			final String hashPassWord = this.hashUtils.sha256(plainPassWord); 
			// 처리 
			user = this.authDelegator.authentication(request, response, pId, hashPassWord, FrameworkConstants.URI_SPECIAL_PAGE.INDEX.getPage()); 
			// 로그인에 성공했으면, 자동로그인 쿠키 생성
			// 실패한 쿠키는 구워둘 필요가 없고, 이미 자동로그인 쿠기가 있더라도 다시 굽는건 만료시간 연장의 목적이 있음 
			if (rememberMe) { 
				this.cookieBaker.create(request, response, user.getpId(), hashPassWord);
			}
			// 자동로그인 체크 안했으면 기존 자동로그인 쿠키는 삭제되어야 함 
			else {
				this.cookieBaker.remove(request, response);
			}
		}
		catch(final PwdOverDueException pex) { 
			if(this.logger.isErrorEnabled()) { 
				this.logger.error("로그인 실패. id : " + pId + ", " + pex.getClass().getSimpleName());
			} 
			
			throw pex; 			
		}		
		catch(final LoginException ex) { 
			// id/pwd등이 달라서 로그인에 실패하는 것은 늘상 있는 일이므로 debug레벨로 찍음 
			if(this.logger.isDebugEnabled()) { 
				this.logger.debug("로그인 실패. id : " + pId + ", " + ex.getClass().getSimpleName());
			} 
			
			throw ex; 
		}
		catch(final Exception ex) { 
			if(this.logger.isErrorEnabled()) { 
				this.logger.error("로그인 관련 처리 실패", ex);
			} 
			
			throw new MobileControllerException(ex); 
		}
		
		// 후속처리 
		this.afterProcess(request, user, tokenId, certType);

		// 로그인한 사용자 정보 반환 
		return user;	
	}
	
	/**
	 * 생체인증을 이용한 로그인 
	 * @param request
	 * @param response
	 * @param pId
	 * @param hashPassWord
	 * @param rememberMe 
	 * @param tokenId
	 * @param certType
	 * @return
	 * @throws LoginException
	 */
	public MCareUser loginWithHashPassword(HttpServletRequest request, HttpServletResponse response,
			String pId,
			String hashPassWord,
			boolean rememberMe, 
			String tokenId, 
			String certType ) throws LoginException {

		MCareUser user = null; 
		
		
		// 기본 로그인 처리 
		try { 
			// 처리 
			user = this.authDelegator.authentication(request, response, pId, hashPassWord, FrameworkConstants.URI_SPECIAL_PAGE.INDEX.getPage()); 
			// 로그인에 성공했으면, 자동로그인 쿠키 생성
			// 실패한 쿠키는 구워둘 필요가 없고, 이미 자동로그인 쿠기가 있더라도 다시 굽는건 만료시간 연장의 목적이 있음 
			if (rememberMe) { 
				this.cookieBaker.create(request, response, user.getpId(), hashPassWord);
			}
			// 자동로그인 체크 안했으면 기존 자동로그인 쿠키는 삭제되어야 함 
			else {
				this.cookieBaker.remove(request, response);
			}
		}
		catch(final PwdOverDueException pex) { 
			this.logger.error("로그인 실패. id : " + pId + ", " + pex.getClass().getSimpleName());
			throw pex; 			
		}		
		catch(final LoginException ex) { 
			this.logger.error("로그인 실패. id : " + pId + ", " + ex.getClass().getSimpleName());
			throw ex; 
		}
		catch(final Exception ex) { 
			this.logger.error("로그인 관련 처리 실패", ex);
			throw new MobileControllerException(ex); 
		}
		
		// 후속처리 
		this.afterProcess(request, user, tokenId, certType);

		// 로그인한 사용자 정보 반환 
		return user;		
	}
	
	
	/**
	 * 로그인 이후 처리될 것들 
	 * @param request 요청객체 
	 * @param user 로그인한 사용자 
	 * @param tokenId 토큰식별자 
	 * @param certType 인증서 유형 
	 */
	private void afterProcess(HttpServletRequest request, MCareUser user, String tokenId, String certType) {
		// token 갱신 
		if(!this.tokenService.processDeviceToken(request, user.getpId(), tokenId, certType)) {
			// 이전에 로그인 처리를 했으니까 세션 비활성화 처리해주고 
			this.invalidateSession(request); 
			// 예외처리 
			throw new MobileControllerException("mcare.error.register.token", "토큰등록에 실패했습니다. 앱을 종료하고, 재시작해주세요"); 
		}
		
		// 로그인에 성공했으면 로그를 남김 
		this.addloginHistory(user, false);
	}

	/**
	 * 로그인 후 로그인 히스토리 등록
	 * @param user 사용자 정보
	 * @param isRememberMe 자동로그인여부
	 */
	public void addloginHistory(MCareUser user, boolean isRememberMe) {
		if(user != null) {
			String rememberMeYn = "N";
			if(isRememberMe == true) {
				rememberMeYn = "Y";
			}
			
			final LoginHistory history = new LoginHistory(); 
			final String key = user.getpId() + "_" + rememberMeYn; 
			
			history.setUserId(user.getpId());
			history.setRememberMeYn(rememberMeYn);
			// 로그인 이력 해쉬용 문자 만들기 
			history.setLoginHashValue(this.hashUtils.sha256(key));
			
			// 로그전달  
			this.logService.addLog(LogType.LOGIN_HISTORY, history);
		}
	}
	
	/**
	 * 세션비활성화 처리 
	 * 우리는 세션이 쿠키기반이라 응답이 client에 전달되어야 세션이 정말 비활성화 될것임 
	 * @param request 
	 * 
	 */ 
	private void invalidateSession(HttpServletRequest request) { 
		final HttpSession session = request.getSession(false);
		
		if (session != null) {
			this.logger.debug("로그인 페이지에 접근하면 세션 무효화 처리하고 로그인 페이지 보여줌");
			session.invalidate();
		}		
	}
}
