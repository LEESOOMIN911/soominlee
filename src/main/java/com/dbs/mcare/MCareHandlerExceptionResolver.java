package com.dbs.mcare;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import com.dbs.mcare.framework.FrameworkConstants;
import com.dbs.mcare.framework.FrameworkHandlerExceptionResolver;
import com.dbs.mcare.framework.exception.MCareAuthException;
import com.dbs.mcare.framework.exception.MCareRuntimeException;
import com.dbs.mcare.framework.service.MessageService;
import com.dbs.mcare.framework.util.HttpRequestUtil;

/**
 * 화면에 보여질 예외 메시지 생성 
 * 
 * @author aple
 *
 */
@Component
public class MCareHandlerExceptionResolver extends FrameworkHandlerExceptionResolver {
	private AntPathMatcher matcher; 
	@Autowired 
	private MessageService messageService; 
	
	public MCareHandlerExceptionResolver() { 
		this.matcher = new AntPathMatcher();
	}
	

	@Override
	protected String resolveExceptionModelAndView(ModelAndView mav, HttpServletRequest request, HttpServletResponse response, Exception ex) {
		if (this.logger.isDebugEnabled()) {
			final StringBuilder builder = new StringBuilder(FrameworkConstants.NEW_LINE); 
			
			builder.append("* 예외정보--------").append(FrameworkConstants.NEW_LINE); 
			builder.append("- view name : "); 
			
			if(mav != null) { 
				builder.append(mav.getViewName()); 
			}
			builder.append(FrameworkConstants.NEW_LINE); 
	
			builder.append("- request type : ").append(HttpRequestUtil.getURIExtension(request)).append(FrameworkConstants.NEW_LINE);
			if(ex instanceof MCareAuthException) {
				MCareAuthException aEx = (MCareAuthException) ex;
				builder.append("- nextPage : ").append(aEx.getNextPage()).append(FrameworkConstants.NEW_LINE); 
			}
			builder.append("- message: ").append(ex.getMessage()).append(FrameworkConstants.NEW_LINE);
			builder.append("-----------------").append(FrameworkConstants.NEW_LINE); 
			
			this.logger.debug(builder.toString(), ex);
		}
		
//		final WebApplicationContext webAppContext = RequestContextUtils.getWebApplicationContext(request);
//		final MessageSource messageSource = (MessageSource) webAppContext.getBean("messageSource");
		String code = null; 

		String requestUri = HttpRequestUtil.getRequestURIExcludeContextPath(request); 
		
		// 관리자 요청인가? 
		if(this.matcher.match(FrameworkConstants.URI_REQUEST_PATTERN.ADMIN.getPattern(), requestUri)) {
			return "[예외] " + this.getExcepitonMessage(request, ex); 
		}
		
		// 일반 사용자의 요청의 예외인 경우 (다국어 고려 필요) 
		if(ex instanceof MCareRuntimeException) {
			// 지정된 코드값 확인 
			code = ((MCareRuntimeException) ex).getCode(); 
		}
		
		// 없는경우 500으로 처리  
		if(StringUtils.isEmpty(code)) {
			code = "mcare.error.500"; 
		}
		
		// 메시지 꺼내주기 
		return this.messageService.getMessage(code, request); 

		
		// Controller단에서 들어온 예외에서 resource code를 보고 text로 바꿔서 ui에 전달 
		// ui는 text를 찍기만 한다. 
		
		// AdinControllerException.getMessageResourceCode()
		
		// message resource code가 없는 경우, 개발하면서 없어져야 겠지만 
		// 발생할 수 밖에 없는 경우 ==> 표준 에러 페이지... 
		// 404 --> xxxx, 500 --> xxxx ==> 처리중 에러가 발생했습니다. 관리자에게 문의하세요. 
		
		// 예외 표시 페이지 
		// 관리자용 : PC, 사용자 : Mobile 
		// (1) 팝업 : 사용자 (window.alert, Dialog) 
		// (2) 꽉찬 페이지 --> error.json (cccc) [확인] 
	}

	/**
	 * 관리자용 에러메시지 생성  
	 * @param request 요청객체 
	 * @param ex 발생된 예외 
	 * @return 정리된 문자열 
	 */
	private String getExcepitonMessage(HttpServletRequest request, Exception ex) { 
		// 우리 시스템에서 명시적으로 생성한 예외가 아닌 경우 
		if(!(ex instanceof MCareRuntimeException)) {
			// 기본 메시지 처리 
			return this.messageService.getMessage("mcare.error.500", request); 
		}
		
		
		// 우리 시스템에서 생성한 예외는 제일 안쪽에 발생한 메시지만 보여주기 
		Throwable th = ex.getCause();
		
		if(th == null) { 
			th = ex; 
		}
		else { 
			int max = 5; 
			
			while(th.getCause() != null && max > 0) { 
				th = ex.getCause(); 
				// SQLException에서 계속 cause가 있어서 요청이 끝나지 않는 문제가 있음 
				// 하여 최대한 추적되는 깊이를 체크하는 것임 
				max--; 
			}
		}	
		
		String message = th.getMessage(); 
		
		// 예외는  nested exception 으로 구분되어 문장이 표시됨
		// 하여 보기 편하게 줄바꿈 추가 
		
		message = message.replaceAll("; nested exception is", FrameworkConstants.NEW_LINE + "; nested exception is"); 
		
		
		return message; 
	}
}
