package com.dbs.mcare.service.mobile.user.repository.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.dbs.mcare.framework.service.user.repository.dao.GeneralUser;
import com.dbs.mcare.framework.util.ConvertUtil;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
/**
 * 사용자 
 * @author hyeeun 
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MCareUser extends GeneralUser {
	// 동의서 목록 
	private List<MCareUserAgreement> agreementList;
	// 사용자가 다음에 이동할 페이지 
	// DB에 저장되는 값은 아니고, 로그인 처리용임 
	private String nextPage; 
	
	

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
//		user.setpName((String) userMap.get("pName"));
		user.setLoginFailCnt(ConvertUtil.convertInteger(b));
		user.setPasswordValue((String) userMap.get("passwordValue"));
		user.setPasswordUpdateDt((Date) userMap.get("passwordUpdateDt"));
		user.setLocalCipherKeyValue((String) userMap.get("localCipherKeyValue"));
		
		return user; 
	}
	
	@Override
	public String toString() {
		return "MCareUser [pId=" + super.getpId() + "] "; 
	}
}
