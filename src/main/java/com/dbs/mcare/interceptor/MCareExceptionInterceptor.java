package com.dbs.mcare.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tiles.request.render.CannotRenderException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
/**
 * 최종 사용자에게 나가는 예외를 잡아서 처리하기 위한 인터셉터 ..... 안쓰는거네??? 
 * @author hyeeun 
 *
 */
public class MCareExceptionInterceptor extends HandlerInterceptorAdapter {
	private final Logger logger = LoggerFactory.getLogger(this.getClass()); 
	
	/**
	 * 생성자 
	 */
	public MCareExceptionInterceptor() {

	}
	
	@Override
	public void postHandle(HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            ModelAndView modelAndView) throws Exception {

		if(this.logger.isErrorEnabled()) { 
			this.logger.error("modelAndView : " + modelAndView);
		} 
	}
	
	@Override 
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		if(ex instanceof CannotRenderException) {
			this.logger.error("잘못된 페이지 요청 : " + ex.toString() + ", 에러페이지로 전이 필요.. ");
			response.sendRedirect("error.page");
		}
		else {
			this.logger.error("아직 핸들러가 없는 예외", ex);
		}
	}
}
