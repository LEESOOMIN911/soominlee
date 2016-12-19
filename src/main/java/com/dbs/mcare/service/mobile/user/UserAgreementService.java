package com.dbs.mcare.service.mobile.user;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dbs.mcare.exception.mobile.ApiCallException;
import com.dbs.mcare.service.api.MCareApiCallService;
import com.dbs.mcare.service.api.PnuhApi;

@Service 
public class UserAgreementService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired 
	private MCareApiCallService apiCallService; 

	
	/**
	 * 사용자가 동의해야할 최신 동의서 반환
	 * @param chartNoValue
	 * @return
	 * @throws ApiCallException
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getUserNewAgreementList(String chartNoValue) throws ApiCallException {
		final List<Map<String, Object>> newAgreementList = (List<Map<String, Object>>) this.apiCallService.execute(PnuhApi.AGREEMENT_GETNEWAGREEMENTLIST, "pId", chartNoValue); 

		this.logger.debug("사용자가 동의해야할 동의서 목록 : " + ((newAgreementList == null) ? "0" : newAgreementList.size()) + "개"); 
		return newAgreementList;
	}

	/**
	 * 14세 미만 사용자가 동의해야할 최신 동의서 반환
	 * @param chartNoValue
	 * @return
	 * @throws ApiCallException 
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getUnder14UserNewAgreementList(String chartNoValue) throws ApiCallException {
		final List<Map<String, Object>> under14NewAgreementList = (List<Map<String, Object>>) this.apiCallService.execute(PnuhApi.AGREEMENT_GETUNDER14NEWAGREEMENTLIST, "pId", chartNoValue); 

		this.logger.debug("14미만 사용자가 동의해야할 동의서 목록 : " + ((under14NewAgreementList == null) ? "0" : under14NewAgreementList.size()) + "개"); 
		return under14NewAgreementList;
	}
	
	/**
	 * 사용자가 등록한 최신 버전의 동의서 목록을 반환
	 * @param chartNoValue
	 * @return
	 * @throws ApiCallException 
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getUserAgreementList(String chartNoValue) throws ApiCallException {
		final List<Map<String, Object>> userAgreementList = (List<Map<String, Object>>) this.apiCallService.execute(PnuhApi.USER_USERINFO_GETUSERAGREEMENTLIST, "pId", chartNoValue); 
		this.logger.debug("사용자가 동의 여부를 선택한 목록 : " + ((userAgreementList == null) ? "0" : userAgreementList.size()) + "개");
		return userAgreementList;
	}
}
