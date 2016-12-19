package com.dbs.mcare.service.admin.agg.repository.dao;

import java.util.Date;

import com.dbs.mcare.framework.util.DateDeserializer;
import com.dbs.mcare.framework.util.DateSerializer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 사용자 연령별 통계 
 * @author aple
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AggUserAge {
	// 통계순번 
	private Long aggSeq; 
	// 연령대 
	// 0 : 0 ~ 9세, 10 : 10 ~ 19세, 20 : 20 ~ 29세, 30 : 30 ~ 39세 .... 
	private Integer ageOrder; 
	// 연령대별 인원수 
	private Integer dataCnt; 
	// 기준통계일 
	@JsonSerialize(using = DateSerializer.class)
	@JsonDeserialize(using = DateDeserializer.class)		
	private Date aggDt;
	
	
	public Long getAggSeq() {
		return aggSeq;
	}
	public void setAggSeq(Long aggSeq) {
		this.aggSeq = aggSeq;
	}
	public Integer getAgeOrder() {
		return ageOrder;
	}
	public void setAgeOrder(Integer ageOrder) {
		this.ageOrder = ageOrder;
	}
	public Integer getDataCnt() {
		return dataCnt;
	}
	public void setDataCnt(Integer dataCnt) {
		this.dataCnt = dataCnt;
	}
	public Date getAggDt() {
		return aggDt;
	}
	public void setAggDt(Date aggDt) {
		this.aggDt = aggDt;
	} 
}
