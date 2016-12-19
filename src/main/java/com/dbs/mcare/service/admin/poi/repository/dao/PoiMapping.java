package com.dbs.mcare.service.admin.poi.repository.dao;

import java.util.Date;

import com.dbs.mcare.framework.util.DateDeserializer;
import com.dbs.mcare.framework.util.DateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 기간계와 지도서비스간의 위치 맵핑 
 * 
 * @author hyeeun  
 *
 */
public class PoiMapping {
	private Long poiSeq; 
	private String legacyName; 
	private String mapName; 
	private String mappingDesc; 
	@JsonSerialize(using = DateSerializer.class)
	@JsonDeserialize(using = DateDeserializer.class)		
	private Date regDt; 
	private String useYn;
	public Long getPoiSeq() {
		return this.poiSeq;
	}
	public String getLegacyName() {
		return this.legacyName;
	}
	public String getMapName() {
		return this.mapName;
	}
	public String getMappingDesc() {
		return this.mappingDesc;
	}
	public Date getRegDt() {
		return this.regDt;
	}
	public String getUseYn() {
		return this.useYn;
	}
	public void setPoiSeq(Long poiSeq) {
		this.poiSeq = poiSeq;
	}
	public void setLegacyName(String legacyName) {
		this.legacyName = legacyName;
	}
	public void setMapName(String mapName) {
		this.mapName = mapName;
	}
	public void setMappingDesc(String mappingDesc) {
		this.mappingDesc = mappingDesc;
	}
	public void setRegDt(Date regDt) {
		this.regDt = regDt;
	}
	public void setUseYn(String useYn) {
		this.useYn = useYn;
	} 
}
