package com.dbs.mcare.controller;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.HandlerMapping;

import com.dbs.mcare.exception.mobile.MobileControllerException;
import com.dbs.mcare.framework.FrameworkConstants;
import com.dbs.mcare.framework.api.executor.ApiExecuteDelegator;
import com.dbs.mcare.framework.exception.service.LoginException;
import com.dbs.mcare.framework.exception.service.PwdOverDueException;
import com.dbs.mcare.framework.service.admin.history.repository.dao.LoginHistory;
import com.dbs.mcare.framework.service.log.LogService;
import com.dbs.mcare.framework.service.log.type.LogType;
import com.dbs.mcare.framework.service.menu.MenuLocaleResourceService;
import com.dbs.mcare.framework.util.AESUtil;
import com.dbs.mcare.framework.util.HashUtil;
import com.dbs.mcare.framework.util.HttpRequestUtil;
import com.dbs.mcare.framework.util.SessionUtil;
import com.dbs.mcare.service.AuthenticationDelegator;
import com.dbs.mcare.service.PnuhConfigureService;
import com.dbs.mcare.service.RememberMeCookieBaker;
import com.dbs.mcare.service.mobile.user.TokenService;
import com.dbs.mcare.service.mobile.user.UserRegisterService;
import com.dbs.mcare.service.mobile.user.repository.dao.MCareUser;

@Controller
public class MCareController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	@Qualifier("apiExecuteDelegator")
	private ApiExecuteDelegator apiDelegator;
	
	@Autowired
	private AuthenticationDelegator authDelegator;
	
	@Autowired
	private RememberMeCookieBaker cookieBaker;
	
	@Autowired
	private MenuLocaleResourceService cacheLoader;
	
	@Autowired 
	private LogService logService; 
	
	@Autowired 
	private TokenService tokenService; 
	@Autowired 
	private PnuhConfigureService configureService; 
	
	@Autowired
	private UserRegisterService registerService;
	
	private HashUtil hashUtils; 
	
	@PostConstruct 
	public void afterPropertiesSet() { 
		this.hashUtils = new HashUtil(this.configureService.getHashSalt()); 
	}
	
	@RequestMapping(value = "/logout.page", method = RequestMethod.GET)
	public void logout(HttpServletRequest request, HttpServletResponse response) throws MobileControllerException {

		try { 
			// 명시적인 로그아웃 시, 자동로그인 쿠키 삭제 
			this.cookieBaker.remove(request, response);
		} 
		catch(final Exception ex) {
			this.logger.error("자동로그인 쿠키 삭제 실패. 계속진행함.", ex);
			// 쿠키삭제실패해도, 이후처리 계속 가능함
		}
		
		final HttpSession session = request.getSession(false);
		if (session != null) {
			this.logger.debug("로그아웃을 위해 세션 초기화 처리");
			session.invalidate();
		}
		
		try { 
			String url = HttpRequestUtil.generateRedirectUrl(request, this.configureService.getServerServiceAddr(), FrameworkConstants.URI_SPECIAL_PAGE.USER_LOGIN.getPage()); 
			response.sendRedirect(url);
		} 
		catch(final IOException ex) { 
			this.logger.error("redirect 실패", ex);
			throw new MobileControllerException("mcare.error.500", ex.getMessage()); 
		}
	}	
	
	
	/**
	 * 로그인 페이지 
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/login.page", method = RequestMethod.GET)
	public Model login(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		if(this.logger.isDebugEnabled()) { 
			this.logger.debug("request : " + request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE));
		}
		
		// 세션 비활성화 처리 
		this.invalidateSession(request);
		final String loginType = request.getParameter("loginType");
		
		if(this.logger.isDebugEnabled()) { 
			this.logger.debug("loginType = " + loginType);
		} 
		
		if(!StringUtils.isEmpty(loginType) && loginType.equals("searchPId")) {
			return this.registerService.getCertiUserInfo(request, model);
		}
			
		return model;
	}
	
	@RequestMapping(value = "/loginSubmit.json", method = RequestMethod.POST)
	@ResponseBody
	public Object loginSubmit(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "pId", required = true) String pId,
			@RequestParam(value = "password", required = true) String plainPassWord,
			@RequestParam(value = "rememberMe", required = true) boolean rememberMe, 
			@RequestParam(value = "deviceTokenId", required = false) String tokenId, // 웹 화면에서 실험하는 경우를 위해 false로 처리 
			@RequestParam(value = "certType", required = false) String certType // 웹 화면에서 실험하는 경우를 위해 false로 처리 
			)throws MobileControllerException, LoginException {

		// 필수 파라미터 확인 
		if(StringUtils.isEmpty(pId) || StringUtils.isEmpty(plainPassWord)) {
			throw new LoginException("mcare.auth.checkaccount", "아이디 또는 비밀번호를 다시 확인하세요", FrameworkConstants.URI_SPECIAL_PAGE.USER_LOGIN);
		}
		
		// 사용자 인증 
		MCareUser user = null; 
		
		try { 
			user = this.authDelegator.authentication(request, response, pId, plainPassWord, FrameworkConstants.URI_SPECIAL_PAGE.INDEX.getPage()); 
			
			// 로그인에 성공했으면, 자동로그인 쿠키 생성
			// 실패한 쿠키는 구워둘 필요가 없고, 이미 자동로그인 쿠기가 있더라도 다시 굽는건 만료시간 연장의 목적이 있음 
			if (rememberMe) { 
				this.cookieBaker.create(request, response, user.getpId(), plainPassWord);
			}
			// 자동로그인 체크 안했으면 기존 자동로그인 쿠키는 삭제되어야 함 
			else {
				this.cookieBaker.remove(request, response);
			}			
		}
		catch(final PwdOverDueException pex) { 
			this.logger.error("로그인 실패. id : " + pId + ", " + pex.toString());
			throw pex; 			
		}		
		catch(final LoginException ex) { 
			this.logger.error("로그인 실패. id : " + pId + ", " + ex.toString());
			throw ex; 
		}
		catch(final Exception ex) { 
			this.logger.error("로그인 관련 처리 실패", ex);
			throw new MobileControllerException(ex); 
		}
		
		// token 갱신 실패하면 예외처리  
		if(!this.tokenService.processDeviceToken(request, pId, tokenId, certType)) { 
			// 이전에 로그인 처리를 했으니까 세션 비활성화 처리해주고 
			this.invalidateSession(request);
			// TODO 여기 보완해야함.
			throw new MobileControllerException("mcare.error.register.token", "토큰등록에 실패했습니다. 앱을 종료하고, 재시작해주세요"); 
		}
		
		// 로그인에 성공했으면 로그를 남김 
		if(user != null) { 
			final LoginHistory history = new LoginHistory(); 
			final String key = pId + "_" + rememberMe; 
			
			history.setUserId(pId);
			history.setRememberMeYn(rememberMe);
			// 로그인 이력 해쉬용 문자 만들기 
			history.setLoginHashValue(this.hashUtils.sha256(key));
			
			// 로그전달  
			this.logService.addLog(LogType.LOGIN_HISTORY, history); 
		}

		return user;
	}

	@RequestMapping(value = "**/*.page", method = RequestMethod.GET)
	public Model submitPage(HttpServletRequest request, HttpServletResponse response, Model model) throws MobileControllerException {		
		this.logger.debug("request : " + request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE));

		// 강제전이 페이지인가? 
		final String forcedPage = request.getParameter(FrameworkConstants.IS_FORCED_PAGE_PARAM); 
		// 강제전이 페이지임을 심어두기 
		if(StringUtils.isNotEmpty(forcedPage)) {
			if(this.logger.isDebugEnabled()) { 
				this.logger.debug("강제 페이지 심었음... " + request.getRequestURI());
			} 
			
			request.getSession().setAttribute(FrameworkConstants.SESSION.USER_FORCED_PAGE.getKey(), HttpRequestUtil.getRequestURIExcludeContextPath(request));
		}
		
		return model;
	}
	
	@RequestMapping(value = "/hospital.page", method = RequestMethod.GET)
	public String selectHospital(HttpServletRequest request,
			@RequestParam(value = "select", required = false) String selectYn) throws MobileControllerException {
		this.logger.debug("request : " + request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE));
		
		// 세션이 없으면 병원목록을 보여줌 
		String pId = SessionUtil.getUserId(request); 
		if(StringUtils.isEmpty(pId)) { 
			return "hospital"; 
		}
		
		// 명시적으로 병원 목록을 요청했으면 병원 목록을 보여줌 
		if(!StringUtils.isEmpty(selectYn) && "Y".equalsIgnoreCase(selectYn)) {
			return "hospital"; 
		}
		
		// 세션이 있는 상태에서 병원 목록을 요청했으면, index로 전이됨 
		// 앱에서 시작 URL이 hospital.page 이기 때문이며 
		// 세션이 유지되면 기존에 사용하던 병원 홈페이지로 바로 이동하여 사용자 편의성을 향상시키기 위함임 
		if(this.logger.isDebugEnabled()) { 
			this.logger.debug("당첨, forward.. ");
		} 
		
		// 세션이 있는 상태에서 병원 목록 선택화면에 비명시적으로 왔다면 포워딩을 통해 인덱스로 가야함 
		return "forward:" + FrameworkConstants.URI_SPECIAL_PAGE.INDEX.getPage(); 
	}
	
	
	@RequestMapping(value = FrameworkConstants.URI_CACHE_RELOAD, method = RequestMethod.POST, 
			consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public int cacheReload(@RequestParam(required = false) MultiValueMap<String, Object> payload) throws MobileControllerException {
		if (this.logger.isDebugEnabled()) {
			this.logger.debug("## cacheReload process start...");
		}
		
		if (payload == null || payload.isEmpty()) {
			this.logger.debug("payload 없어서 실패처리");
			return 0;
			
		} else {
			if (payload.containsKey("requestTimeMillis") == false) {
				this.logger.debug("필수키가 없어서 실패처리");
				return 0;
			}
			
			String requestTimeMillis = (String) payload.getFirst("requestTimeMillis");
			final String encryptionKey = (String) payload.getFirst("encryptionKey");
			String target = (String) payload.getFirst("target");
			
			try { 
				AESUtil aesUtil = new AESUtil(this.configureService.getAesIv()); 
				requestTimeMillis = aesUtil.decrypt(requestTimeMillis, encryptionKey);
				
			} catch(final Exception ex) { 
				this.logger.error("요청시간 복호화 실패", ex);
				throw new MobileControllerException(ex); 
			}
			
			final long availableTime = System.currentTimeMillis() - Long.parseLong(requestTimeMillis);
			if (availableTime > 3000) { // 요청이 3초 이상 지난 경우 실패 
				this.logger.debug("요청시간 만료로 실패처리");
				return 0;
			}
			
			try {
				// 공백제거 
				target = target.trim(); 
				if (this.logger.isDebugEnabled()) {
					this.logger.debug("## cache target : {}", target);
				}
				
				if (FrameworkConstants.CACHE_TARGET_API.equals(target) == true) { // API 캐시
					this.apiDelegator.loaded();
					this.logger.debug("api 리로드 완료");
					return 1;
				
				} 
				else if (FrameworkConstants.CACHE_TARGET_API.equals(target) == true) { // 메뉴 캐시
					this.cacheLoader.loaded();
					this.logger.debug("menu 리로드 완료");
					return 1;
				}
				else {
					this.logger.error("준비되지 않은 유형의 캐시 리로딩 요청을 수신함 : " + target);
				}
				
			} 
			catch (final Exception e) {
				this.logger.error("## {} 캐시 리로드 실패", target, e);
				return 0;
			}
			
		}
		
		return 0;
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
