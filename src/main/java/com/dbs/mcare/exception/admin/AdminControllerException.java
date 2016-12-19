package com.dbs.mcare.exception.admin;

import com.dbs.mcare.framework.exception.MCareServiceException;
/**
 * 관리자 부분에서 발생한 예외 
 * @author DBS
 *
 */
public class AdminControllerException extends MCareServiceException {
	private static final long serialVersionUID = 7893623251257667838L;
	
	public AdminControllerException(String code, String message) {
		super(code, message);
	}
	
	public AdminControllerException(Throwable cause){
		super(cause);
	}
	
	public AdminControllerException(String message) { 
		super(message); 
	}
	
//	/**
//	 * 관리자 화면의 경우 에러를 좀 더 자세히 보여주기 위함 
//	 */
//	public String getMessage() { 
//		StringBuilder builder = new StringBuilder(); 
//		String baseMsg = super.getMessage(); 
//		
//		if(baseMsg != null) { 
//			builder.append(baseMsg).append(FrameworkConstants.NEW_LINE); 
//		}
//		
//		Throwable t = this.getCause(); 
//		int count = 0; 
//		
//		while(t != null && count < 5) { 
//			builder.append(t.getMessage()).append(FrameworkConstants.NEW_LINE); 
//			count++; 
//		}
//
//		return builder.toString(); 
//	}
}
