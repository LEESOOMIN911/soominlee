package com.dbs.mcare.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dbs.mcare.framework.service.RememberMeService;
import com.dbs.mcare.service.mobile.user.repository.dao.MCareUser;

/**
 * 자동로그인 쿠키
 * 
 * @author M-Care팀 
 * 
 */
@Service
public class RememberMeCookieBaker {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private AuthenticationDelegator authDelegator;
	@Autowired 
	private RememberMeService rememberMeService; 
	
	/**
	 * 자동로그인을 위한 환자번호/비번 굽기 
	 * @param request
	 * @param response
	 * @param pId
	 * @param hashPwd
	 */
	public void create(HttpServletRequest request, HttpServletResponse response, String pId, String hashPwd) {
		// 정보 굽기 
		this.rememberMeService.create(request, response, pId, hashPwd);
		
		if(this.logger.isDebugEnabled()) {
			this.logger.debug("remember-me 생성 : " + pId);
		} 
	}
	
	/**
	 * 자동로그인 쿠키 제거 
	 * 
	 * @param request
	 * @return
	 */
	public void remove(HttpServletRequest request, HttpServletResponse response) {
		this.rememberMeService.remove(request, response);
	}
	
	/**
	 * 자동로그인 쿠키 복원 
	 * 
	 * @param request
	 * @param response
	 * @param forcedPage 세션복원한 다음에 가야할 곳 
	 * 
	 * @return user : 복원된 사용자 정보 
	 * @throws Exception 복원해서 보니 비번재설정 필요 등 문제가 생긴 경우 
	 */
	public MCareUser restore(HttpServletRequest request, HttpServletResponse response, String forcedPage) throws Exception {
		final String split[] = this.rememberMeService.restore(request, response); 
		
		if(split == null) { 
			return null; 
		}
		
		// 복원해서 로그인 처리 
		if (split.length > 1) {
			if(this.logger.isDebugEnabled()) { 
				this.logger.debug("자동 로그인 처리 : " + split[0] + ", " + split[1]);
			} 
			
			return this.authDelegator.authentication(request, response, split[0], split[1], forcedPage);
		}

		return null;
	}
	
}