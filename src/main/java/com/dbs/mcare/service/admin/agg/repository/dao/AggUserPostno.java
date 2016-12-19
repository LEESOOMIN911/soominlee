package com.dbs.mcare.service.admin.agg.repository.dao;

import java.util.Date;

import com.dbs.mcare.framework.util.DateDeserializer;
import com.dbs.mcare.framework.util.DateSerializer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AggUserPostno {
	// 일련번호 
	private Long aggSeq; 
	// 우편번호 문자열 
	private String postnoValue; 
	// 건수(인원수) 
	private Integer dataCnt; 
	// 기준통계일 
	@JsonSerialize(using = DateSerializer.class)
	@JsonDeserialize(using = DateDeserializer.class)		
	private Date aggDt;
	
	
	public Long getAggSeq() {
		return this.aggSeq;
	}
	public void setAggSeq(Long aggSeq) {
		this.aggSeq = aggSeq;
	}
	public String getPostnoValue() {
		return this.postnoValue;
	}
	public void setPostnoValue(String postnoValue) {
		this.postnoValue = postnoValue;
	}
	public Integer getDataCnt() {
		return this.dataCnt;
	}
	public void setDataCnt(Integer dataCnt) {
		this.dataCnt = dataCnt;
	}
	public Date getAggDt() {
		return this.aggDt;
	}
	public void setAggDt(Date aggDt) {
		this.aggDt = aggDt;
	}
}
