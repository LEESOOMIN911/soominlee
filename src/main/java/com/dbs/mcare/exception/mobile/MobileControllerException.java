package com.dbs.mcare.exception.mobile;

import com.dbs.mcare.framework.exception.MCareServiceException;
/**
 * 사용자 화면으로 나갈 예외 
 * 
 * @author DBS
 *
 */
public class MobileControllerException extends MCareServiceException {
	private static final long serialVersionUID = 7893623251257667839L;
	/**
	 * 생성자 
	 * @param ex
	 */
	public MobileControllerException(Exception ex) {
		super(ex); 
	}
	/**
	 * 생성자 
	 * @param code 다국어 코드 
	 * @param message
	 */
	public MobileControllerException(String code, String message) { 
		super(code, message); 
	}
}
