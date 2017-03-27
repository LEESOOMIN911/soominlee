package com.dbs.mcare.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.dbs.mcare.framework.api.cache.ApiResourceService;
import com.dbs.mcare.framework.exception.service.LoginException;
import com.dbs.mcare.framework.service.menu.MenuLocaleResourceService;
import com.dbs.mcare.framework.util.AESUtil;
import com.dbs.mcare.framework.util.HttpRequestUtil;
import com.dbs.mcare.framework.util.SessionUtil;
import com.dbs.mcare.service.LoginService;
import com.dbs.mcare.service.PnuhConfigureService;
import com.dbs.mcare.service.RememberMeCookieBaker;
import com.dbs.mcare.service.mobile.user.MCareUserService;
import com.dbs.mcare.service.mobile.user.UserRegisterService;
import com.dbs.mcare.service.mobile.user.repository.dao.MCareUser;

@Controller
public class MCareController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ApiResourceService apiResourceService; 
	@Autowired
	private MenuLocaleResourceService cacheLoader;
	@Autowired 
	private RememberMeCookieBaker cookieBaker;
	@Autowired 
	private MCareUserService userService; 		
	@Autowired
	private UserRegisterService registerService;
	@Autowired 
	private LoginService loginService; 
	@Autowired 
	private PnuhConfigureService configureService; 

	
	
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
			StringBuilder builder = new StringBuilder(FrameworkConstants.NEW_LINE); 
			
			builder.append("request : ").append(request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE)).append(FrameworkConstants.NEW_LINE); 
			builder.append("user Agent : ").append(HttpRequestUtil.getUserAgent(request)).append(FrameworkConstants.NEW_LINE);
			
			this.logger.debug(builder.toString());
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
		
		// 로그인 처리 
		return this.loginService.login(request, response, pId, plainPassWord, rememberMe, tokenId, certType); 
	}

	@RequestMapping(value = "**/*.page", method = RequestMethod.GET)
	public Model submitPage(HttpServletRequest request, HttpServletResponse response, Model model) throws MobileControllerException {		
		if(this.logger.isDebugEnabled()) { 
			this.logger.debug("페이지요청 : " + request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE));
		}

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
		if(this.logger.isDebugEnabled()) { 
			this.logger.debug("request : " + request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE));
		}
		
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
	
	@RequestMapping(value = "/getUserExternalId.json", method = RequestMethod.POST)
	@ResponseBody	
	public Map<String, Object> getUserExternalId(HttpServletRequest request, HttpServletResponse response) throws MobileControllerException {
		if(this.logger.isDebugEnabled()) { 
			this.logger.debug("request : " + request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE));
		}
		
		// 세션에 있는 id로만 가능 
		String pId = SessionUtil.getUserId(request); 
		if(StringUtils.isEmpty(pId)) {
			throw new MobileControllerException("mcare.error.param", "파라미터를 확인해주세요."); 
		}
		
		// 사용자 정보 수집 
		MCareUser user = this.userService.get(pId); 
		if(user == null) {
			if(logger.isDebugEnabled()) {
				logger.debug("대상 환자번호 못 찾음. pId=" + pId);
			}
			throw new MobileControllerException("mcare.error.param", "파라미터를 확인해주세요."); 
		}
		
		// external id 만 꺼내서 반환 
		Map<String, Object> map = new HashMap<>(); 
		map.put("externalId", user.getExternalIdValue()); 
		
		// 
		return map; 
	}	
	
	
	
	@RequestMapping(value = FrameworkConstants.URI_CACHE_RELOAD, method = RequestMethod.POST, 
			consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public int cacheReload(@RequestParam(required = false) MultiValueMap<String, Object> payload) throws MobileControllerException {
		if (this.logger.isDebugEnabled()) {
			this.logger.debug("## cacheReload process start...");
		}
		
		// 구조적 유효성 검사 
		if (payload == null || payload.isEmpty()) {
			if(this.logger.isErrorEnabled()) { 
				this.logger.error("payload 없어서 실패처리");
			} 
			
			return 0;
		} 
			
		// 구조적 유효성 검사 
		if (payload.containsKey("requestTimeMillis") == false) {
			if(this.logger.isErrorEnabled()) { 
				this.logger.error("필수키가 없어서 실패처리");
			} 
			
			return 0;
		}
			
		// 요청의 의미적 유효성 검사 
		String requestTimeMillis = (String) payload.getFirst("requestTimeMillis");
		final String encryptionKey = (String) payload.getFirst("encryptionKey");
		String target = (String) payload.getFirst("target");
			
		try { 
			AESUtil aesUtil = new AESUtil(this.configureService.getAesIv()); 
			requestTimeMillis = aesUtil.decrypt(requestTimeMillis, encryptionKey);
		} 
		catch(final Exception ex) { 
			if(this.logger.isErrorEnabled()) { 
				this.logger.error("요청시간 복호화 실패", ex);
			}
			throw new MobileControllerException(ex); 
		}
			
		final long availableTime = System.currentTimeMillis() - Long.parseLong(requestTimeMillis);
		if (availableTime > 3000) { // 요청이 3초 이상 지난 경우 실패 
			if(this.logger.isErrorEnabled()) { 
				this.logger.error("요청시간 만료로 실패처리");
			} 
		
			return 0;
		}
			
		
		// 처리시작 
		try {
			// 공백제거 
			target = target.trim(); 
			if (this.logger.isDebugEnabled()) {
				this.logger.debug("## cache target : {}", target);
			}
			
			// API 캐시
			if (FrameworkConstants.CACHE_TARGET_API.equals(target)) { 
				this.apiResourceService.loaded();
				return 1;
			} 
			// 메뉴 캐시
			else if (FrameworkConstants.CACHE_TARGET_API.equals(target)) { 
				this.cacheLoader.loaded();
				return 1;
			}
			// 에러 
			else {
				if(this.logger.isErrorEnabled()) { 
					this.logger.error("준비되지 않은 유형의 캐시 리로딩 요청을 수신하여 무시함. " + target);
				}
				
				return 0; 
			} 
		} 
		catch (final Exception e) {
			if(this.logger.isErrorEnabled()) { 
				this.logger.error("## {} 캐시 리로드 실패", target, e);
			}
		
			return 0;
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
