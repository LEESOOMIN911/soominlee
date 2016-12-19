package com.dbs.mcare.service.admin.poi.repository.oracle;

import com.dbs.mcare.framework.template.MCareQuery;
/**
 * 위치맵핑 질의 
 * @author hyeeun 
 *
 */
public class PoiMappingMCareQuery extends MCareQuery {
	/**
	 * @return 전체 데이터를 반환하는 질의문 
	 */
	@Override
	public String getQueryForList() {
		return "SELECT POI_SEQ, LEGACY_NAME, MAP_NAME, MAPPING_DESC, REG_DT, USE_YN " + 
				"FROM MCARE_POI_MAPPING "; 
	}
	/**
	 * @return 하나의 데이터를 반환하는 질의문 
	 */
	@Override
	public String getQueryForObject() {
		return "SELECT POI_SEQ, LEGACY_NAME, MAP_NAME, MAPPING_DESC, REG_DT, USE_YN " + 
				"FROM MCARE_POI_MAPPING " +
				"WHERE POI_SEQ=:poiSeq"; 
	}

	/**
	 * @return 새로운 데이터를 추가하는 질의문 
	 */
	@Override
	public String getQueryForInsert() {
		return "INSERT INTO MCARE_POI_MAPPING (POI_SEQ, LEGACY_NAME, MAP_NAME, MAPPING_DESC, REG_DT, USE_YN) " +
				"VALUES(SEQ_MCARE_POI_PS.NEXTVAL, :legacyName, :mapName, :mappingDesc, SYSDATE, :useYn)"; 
	}
	/**
	 * @return 기존 데이터를 갱신하는 질의문 
	 */
	@Override
	public String getQueryForUpdate() {
		return "UPDATE MCARE_POI_MAPPING " + 
				"SET LEGACY_NAME=:legacyName, MAP_NAME=:mapName, MAPPING_DESC=:mappingDesc, USE_YN=:useYn " +
				"WHERE POI_SEQ=:poiSeq";  
	}
	/** 
	 * @return 기존 데이터를 삭제하는 질의문 
	 */
	@Override
	public String getQueryForDelete() {
		return "DELETE MCARE_POI_MAPPING WHERE POI_SEQ=:poiSeq"; 
	}
	
	
	/**
	 * 전체 데이터 숫자 반환 질의문
	 * @return
	 */
	@Override
	public String getQueryForCount() {
		return "SELECT COUNT(1) FROM MCARE_POI_MAPPING";
	}	
}
