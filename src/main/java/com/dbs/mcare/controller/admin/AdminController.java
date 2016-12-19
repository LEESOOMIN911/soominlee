package com.dbs.mcare.controller.admin;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dbs.mcare.MCareConstants;
import com.dbs.mcare.exception.admin.AdminControllerException;
import com.dbs.mcare.framework.FrameworkConstants;
import com.dbs.mcare.framework.api.model.ApiModel.ApiType;
import com.dbs.mcare.framework.api.model.ApiModel.HttpMethodType;
import com.dbs.mcare.framework.api.model.ApiModel.ReqParamType;
import com.dbs.mcare.framework.api.model.ApiModel.ResParamType;
import com.dbs.mcare.framework.api.model.ApiModel.ResultType;
import com.dbs.mcare.framework.api.model.ApiModel.WsHeaderType;
import com.dbs.mcare.framework.exception.MCareServiceException;
import com.dbs.mcare.framework.exception.service.LoginException;
import com.dbs.mcare.framework.service.admin.manager.ManagerService;
import com.dbs.mcare.framework.service.admin.manager.repository.dao.Manager;
import com.dbs.mcare.framework.util.HashUtil;
import com.dbs.mcare.framework.util.HttpRequestUtil;
import com.dbs.mcare.service.PnuhConfigureService;

@Controller
@RequestMapping("/admin")
public class AdminController {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ManagerService managerService;
	@Autowired 
	private PnuhConfigureService configureService; 
	@Autowired
    private ApplicationContext applicationContext;
	
	/**
	 * 관리자 화면 호출 메소드
	 * 각 페이지별로 컨트롤러에서 view.page로 호출하는것을 
	 * 하나의 메소드로 제어하려고 생성함. 협의통해서 변경가능
	 * @author youngil.seo
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/**/*.page", method = RequestMethod.GET)
	public Model view(HttpServletRequest request, HttpServletResponse response, Model model)
			throws AdminControllerException {
		
		try { 
			//메뉴일 때 필요 데이터
			if( request.getRequestURI().contains("menu") ){
				model.addAttribute("defaultLanguage", this.configureService.getI18nDefault());
				model.addAttribute("supportedLanguages", this.configureService.getI18nSupported().split(","));
				model.addAttribute("reqParamTypes", ReqParamType.values());
			
			} 
			//api일 때 필요 데이터
			else if ( request.getRequestURI().contains("api") ){
				 model.addAttribute("httpMethodTypes", HttpMethodType.values());
			     model.addAttribute("reqParamTypes", ReqParamType.values());
			     model.addAttribute("resParamTypes", ResParamType.values());
			     model.addAttribute("wsHeaderTypes", WsHeaderType.values());
			     model.addAttribute("resultTypes", ResultType.values());
			     model.addAttribute("apiTypes", ApiType.values());
			     model.addAttribute("dataSourceNames", this.applicationContext.getBeansOfType(DataSource.class).keySet().toArray(new String[0]));
			}
		}
		catch(final Exception ex) {
			if(this.logger.isErrorEnabled()) { 
				// 99.9% 쯤은 예외가 발생하지 않겠지만.. 
				this.logger.error("관리자 화면 호출을 위한 속성 수집 실패. attribute : " + model.toString(), ex);
			} 
			
			throw new AdminControllerException(ex); 
		}
		
		//나머지는 그냥 tiles 설정대로
		return model;
	}
	
	@RequestMapping(value = "/login.page", method = RequestMethod.GET)
	public Model login(HttpServletRequest request, HttpServletResponse response, Model model)
			throws AdminControllerException {
		// 로그인한 사용자가 강제로 로그인 페이지에 접근하면 로그아웃 처리
		final HttpSession session = request.getSession(false);
		if (session != null) {
			if(this.logger.isDebugEnabled()) { 
				this.logger.debug("강제 로그아웃 처리");
			} 
			session.invalidate();
		}
		
		return model;
	}
	
	@RequestMapping(value = "/logout.page", method = RequestMethod.GET)
	public void logout(HttpServletRequest request, HttpServletResponse response) throws AdminControllerException {
		try { 
			// 세션 파일삭제 + 세션객체 내용 삭제 + 모든쿠키삭제  
			request.getSession().invalidate(); 
			
			final String url = HttpRequestUtil.generateRedirectUrl(request, this.configureService.getServerServiceAddr(), FrameworkConstants.URI_SPECIAL_PAGE.ADMIN_INDEX.getPage()); 
			response.sendRedirect(url);
		}
		catch(final IOException ex) { 
			if(this.logger.isErrorEnabled()) { 
				this.logger.error("logout 요청 처리 실패", ex);
			}
			
			throw new AdminControllerException(ex); 
		}
	}
	
	@RequestMapping(value = "/loginSubmit.json", method = RequestMethod.POST)
	@ResponseBody
	public Object loginSubmit(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "username", required = true) String userId,
			@RequestParam(value = "password", required = true) String plainPassword) throws LoginException, AdminControllerException {
		
		Manager admin = null;
		
		if(this.logger.isDebugEnabled()) { 
			this.logger.debug("관리자 로그인 확인 : " + userId);
		} 
		
		try { 
			admin = this.managerService.get(new Manager(userId));
		}
		catch(final MCareServiceException ex) {
			if(this.logger.isErrorEnabled()) { 
				this.logger.error("관리자 정보 가져오기 실패. userName=" + userId, ex);
			} 
			
			throw new AdminControllerException(ex); 
		}
		
		// 사용자가 보낸 암호 해쉬적용 
		final String hashPwd = new HashUtil(this.configureService.getHashSalt()).sha256(plainPassword); 
		// 유효성 확인 
		if (admin == null || StringUtils.isEmpty(plainPassword) || hashPwd.equals(admin.getPwdValue()) == false) {
			throw new LoginException("admin.auth.checkaccount", "아이디 또는 비밀번호를 다시 확인하세요", FrameworkConstants.URI_SPECIAL_PAGE.ADMIN_INDEX);
		}
		
		if ("Y".equals(admin.getEnabledYn()) == false) {
			throw new LoginException("admin.auth.blockaccount", "차단된 사용자입니다. 관리자에게 문의하세요", FrameworkConstants.URI_SPECIAL_PAGE.ADMIN_INDEX);
		}
		
		// 세션설정 
		final HttpSession session = request.getSession();
		
		if(session != null) { 
			session.setAttribute(FrameworkConstants.SESSION.ADMIN.getKey(), admin);
			// 관리자는 웹 서버의 세션을 사용하고, 그러므로 web.xml에 정의된 session timeout을 따른다. 
			// 별도로 우리 상수에 있는 시간으로 덮어써준다. 
			session.setMaxInactiveInterval(MCareConstants.ADMIN_SESSION_TIMEOUT_SEC);
			
			if(this.logger.isDebugEnabled()) { 
				this.logger.debug("maxInactiveInterval : remained " + session.getMaxInactiveInterval() + " sec"); 
			} 
		}
		// else 조건은 이변이 없는한 발생하지 않을것임 
		else {
			if(this.logger.isErrorEnabled()) { 
				this.logger.error("세션이 없어서 저장할 수 없음");
			} 
		}
		
		return 0;
	}
	
}
