package com.dbs.mcare.exception.mobile;

import com.dbs.mcare.framework.FrameworkConstants;
import com.dbs.mcare.framework.exception.MCareApiServiceException;
import com.dbs.mcare.service.api.PnuhApi;

/**
 * api 호출 시 발생한 예외 
 * @author hyeeun 
 *
 */
public class ApiCallException extends MCareApiServiceException {
	private static final long serialVersionUID = 7893623351257667839L;
	private PnuhApi callee; 
	
	/**
	 * 생성자 
	 * @param code
	 * @param message
	 * @param ex
	 * @param callee
	 */
	public ApiCallException(String code, String message, Exception ex, PnuhApi callee) {
		this(code, message, callee); 
		if(ex != null) { 
			super.setStackTrace(ex.getStackTrace());
		} 
	}
	/**
	 * 생성자 
	 * @param code
	 * @param message
	 */
	public ApiCallException(String code, String message, PnuhApi callee) { 
		super(code, message); 
		this.callee = callee; 
	}
	/**
	 * 어떤 api를 호출하다가 에러가 났는지 반환 
	 * @return
	 */
	public PnuhApi getCallee() { 
		return this.callee; 
	}
	
	@Override 
	public String toString() { 
		StringBuilder builder = new StringBuilder(); 
		
		builder.append(FrameworkConstants.NEW_LINE); 
		builder.append("- API : "); 
		
		if(this.callee == null) { 
			builder.append("정보누락됨"); 
		}
		else {
			builder.append(this.callee); 
		} 
		builder.append(FrameworkConstants.NEW_LINE); 
		builder.append("- Message : ").append(this.getMessage()).append(" (").append(this.getCode()).append(") ").append(FrameworkConstants.NEW_LINE); 
		
		return builder.toString(); 
	}
}
