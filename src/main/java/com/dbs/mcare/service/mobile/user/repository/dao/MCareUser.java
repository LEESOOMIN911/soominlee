package com.dbs.mcare.service.mobile.user.repository.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.dbs.mcare.framework.util.ConvertUtil;
import com.dbs.mcare.framework.util.DateDeserializer;
import com.dbs.mcare.framework.util.DateSerializer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
/**
 * 사용자 
 * @author hyeeun 
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MCareUser {
	// 시퀀스 
	private Long userSeq; 
	// 환자번호 
	private String pId; 
	// 환자명 
	private String pName; 
	// 로컬 암호화 키 
	private String localCipherKeyValue; 
	// 로그인 실패 횟수 
	private Integer loginFailCnt; 
	// 암호 업뎃 날짜 
	private Date passWordUpdateDt; 
	// 로그인 암호 
	private String passWordValue; 
	// 사용 등록일 
	@JsonSerialize(using = DateSerializer.class)
	@JsonDeserialize(using = DateDeserializer.class)		
	private Date registerDt; 
	// 동의서 목록 
	private List<MCareUserAgreement> agreementList;
	// 사용자가 다음에 이동할 페이지 
	// DB에 저장되는 값은 아니고, 로그인 처리용임 
	private String nextPage; 
	
	
	public Long getUserSeq() {
		return this.userSeq;
	}

	public void setUserSeq(Long userSeq) {
		this.userSeq = userSeq;
	}

	public String getpId() {
		return this.pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

	public String getpName() {
		return this.pName;
	}

	public void setpName(String pName) {
		this.pName = pName;
	}

	public String getLocalCipherKeyValue() {
		return this.localCipherKeyValue;
	}

	public void setLocalCipherKeyValue(String localCipherKeyValue) {
		this.localCipherKeyValue = localCipherKeyValue;
	}

	public Integer getLoginFailCnt() {
		return this.loginFailCnt;
	}

	public void setLoginFailCnt(Integer loginFailCnt) {
		this.loginFailCnt = loginFailCnt;
	}

	public Date getPassWordUpdateDt() {
		return this.passWordUpdateDt;
	}

	public void setPassWordUpdateDt(Date passWordUpdateDt) {
		this.passWordUpdateDt = passWordUpdateDt;
	}

	public String getPassWordValue() {
		return this.passWordValue;
	}

	public void setPassWordValue(String passWordValue) {
		this.passWordValue = passWordValue;
	}

	public Date getRegisterDt() {
		return this.registerDt;
	}

	public void setRegisterDt(Date registerDt) {
		this.registerDt = registerDt;
	}

	public List<MCareUserAgreement> getAgreementList() {
		return this.agreementList;
	}

	public void setAgreementList(List<MCareUserAgreement> agreementList) {
		this.agreementList = agreementList;
	}

	public String getNextPage() {
		return this.nextPage;
	}

	public void setNextPage(String nextPage) {
		this.nextPage = nextPage;
	}

	/**
	 * 변환 
	 * @param map
	 * @return
	 */
	public static MCareUser convert(Map<String, Object> userMap) { 
		final MCareUser user = new MCareUser(); 
		final BigDecimal b = (BigDecimal) userMap.get("loginFailCnt"); 
		
		user.setpId((String) userMap.get("pId"));
		user.setpName((String) userMap.get("pName"));
		user.setLoginFailCnt(ConvertUtil.convertInteger(b));
		user.setPassWordValue((String) userMap.get("passwordValue"));
		user.setPassWordUpdateDt((Date) userMap.get("passwordUpdateDt"));
		user.setLocalCipherKeyValue((String) userMap.get("localCipherKeyValue"));
		
		return user; 
	}
	
	@Override
	public String toString() {
		return "MCareUser [userSeq=" + this.userSeq + ", pId=" + this.pId + ", pName=" + this.pName
				+ ", localCipherKeyValue=" + this.localCipherKeyValue + ", loginFailCnt=" + this.loginFailCnt
				+ ", passWordUpdateDt=" + this.passWordUpdateDt + ", passWordValue=" + this.passWordValue + ", registerDt="
				+ this.registerDt + ", agreementList=" + this.agreementList + ", nextPage=" + this.nextPage + "]";
	}
}
