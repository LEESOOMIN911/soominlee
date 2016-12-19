package com.dbs.mcare.service.admin.agg.repository.dao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 사용자 정보 통계를 위한 임시 테이블용 
 * @author aple
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AggUserTmp {
	// 연령대 
	private Integer ageOrder; 
	// 우편번호 
	private String postnoValue;
	// 통계결과로 얻어지는 값 
	private Integer dataCnt; 
	
	
	public Integer getAgeOrder() {
		return ageOrder;
	}
	public void setAgeOrder(Integer ageOrder) {
		this.ageOrder = ageOrder;
	}
	public String getPostnoValue() {
		return postnoValue;
	}
	public void setPostnoValue(String postnoValue) {
		this.postnoValue = postnoValue;
	}
	public Integer getDataCnt() {
		return dataCnt;
	}
	public void setDataCnt(Integer dataCnt) {
		this.dataCnt = dataCnt;
	} 
}
