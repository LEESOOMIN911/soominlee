package com.dbs.mcare.service.mobile.user.repository.dao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
/**
 * 사용자 동의 여부 
 * @author hyeeun 
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MCareUserAgreement {
	private String pid; 
	private Long agreementSeq;
	private String agreementId;
	private String agreementYn;
	
	
	public String getPId() {
		return this.pid;
	}
	public void setPid(String patientId) {
		this.pid = patientId;
	}
	public Long getAgreementSeq() {
		return this.agreementSeq;
	}
	public void setAgreementSeq(Long agreementSeq) {
		this.agreementSeq = agreementSeq;
	}
	public String getAgreementId() {
		return agreementId;
	}
	public void setAgreementId(String agreementId) {
		this.agreementId = agreementId;
	}
	public String getAgreementYn() {
		return this.agreementYn;
	}
	public void setAgreementYn(String agreementYn) {
		this.agreementYn = agreementYn;
	} 
}
