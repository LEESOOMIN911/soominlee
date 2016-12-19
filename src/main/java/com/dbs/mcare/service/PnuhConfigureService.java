package com.dbs.mcare.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.dbs.mcare.framework.service.ConfigureService;

@Component  
public class PnuhConfigureService extends ConfigureService {
	// 임시비밀번호 길이 
	@Value("#{mcareConfig['temporary.password.length'] ?: '5'}")	
	private int temporaryPasswordLength; 	
	
	public int getTemporaryPasswordLength() { 
		return this.temporaryPasswordLength; 
	}
}
