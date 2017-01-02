package com.dbs.mcare.service.mobile.user.repository.dao;

import java.util.Date;

import com.dbs.mcare.framework.util.DateDeserializer;
import com.dbs.mcare.framework.util.DateSerializer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 사용자 탈퇴정보 
 * @author hyeeun 
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MCareUserWithdrawal {
	// 환자번호 
	private String pId; 
	// 등록일 
	@JsonSerialize(using = DateSerializer.class)
	@JsonDeserialize(using = DateDeserializer.class)		
	private Date registerDt; 
	// 탈퇴일 
	@JsonSerialize(using = DateSerializer.class)
	@JsonDeserialize(using = DateDeserializer.class)	
	private Date withdrawalDt; 
	// 탈퇴이유 
	private String reasonValue;
	
	/**
	 * 환자번호 
	 * @return the pId 환자번호 
	 */
	public String getpId() {
		return pId;
	}
	/**
	 * 환자번호 
	 * @param pId the pId to set
	 */
	public void setpId(String pId) {
		this.pId = pId;
	}
	/**
	 * 가입했던 날 
	 * @return the registerDt
	 */
	public Date getRegisterDt() {
		return registerDt;
	}
	/**
	 * 가입했던 날 
	 * @param registerDt the registerDt to set
	 */
	public void setRegisterDt(Date registerDt) {
		this.registerDt = registerDt;
	}
	/**
	 * 탈퇴일 
	 * @return the withdrawalDt
	 */
	public Date getWithdrawalDt() {
		return withdrawalDt;
	}
	/**
	 *  탈퇴일 
	 * @param withdrawalDt the withdrawalDt to set
	 */
	public void setWithdrawalDt(Date withdrawalDt) {
		this.withdrawalDt = withdrawalDt;
	}
	/**
	 * 탈퇴이유 
	 * @return the reasonValue
	 */
	public String getReasonValue() {
		return reasonValue;
	}
	/**
	 * 탈퇴이유 
	 * @param reasonValue the reasonValue to set
	 */
	public void setReasonValue(String reasonValue) {
		this.reasonValue = reasonValue;
	} 
	
	@Override 
	public String toString() { 
		return pId; 
	}
}
