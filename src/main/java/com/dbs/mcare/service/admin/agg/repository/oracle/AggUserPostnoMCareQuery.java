package com.dbs.mcare.service.admin.agg.repository.oracle;

import com.dbs.mcare.framework.template.MCareQuery;
/**
 * 우편번호별(앞에 3자리) 사용자 
 * - 우편번호 체계 : 앞에 3자리는 시/군/구, 뒤에 2자리는 읍/면/동 
 * @author aple
 *
 */
public class AggUserPostnoMCareQuery extends MCareQuery { 
	@Override
	public String getQueryForInsert() {
		return "INSERT INTO MCARE_AGG_USER_POSTNO (" + 
					"AGG_SEQ, " + 
					"POSTNO_VALUE, " +
					"DATA_CNT, " + 
					"AGG_DT) " + 
				"VALUES (" + 
					"SEQ_MCARE_AGG_POSTNO_AP.NEXTVAL, " + 
					":postnoValue, " + 
					":dataCnt, " + 
					":aggDt" + 
				") "; 
	}
	
	// 날짜별 검색 
	@Override
	public String getQueryForListByDate() { 
		return "SELECT P.POSTNO_VALUE, (Z.SI_VALUE || ' ' || Z.GUNGU_VALUE) AS POSTNO_DESC, SUM(DATA_CNT) AS DATA_CNT, P.AGG_DT " + 
			"FROM MCARE_AGG_USER_POSTNO P " + 
			"LEFT JOIN MCARE_ZIPCODE Z  " + 
			"ON P.POSTNO_VALUE=Z.ZIP_ID  " + 
			"WHERE TO_CHAR(P.AGG_DT, 'yyyy-MM-dd') BETWEEN TO_CHAR(:strDate, 'yyyy-MM-dd') AND TO_CHAR(:endDate, 'yyyy-MM-dd') " + 
			"GROUP BY P.AGG_DT, P.POSTNO_VALUE, Z.SI_VALUE, Z.GUNGU_VALUE  " + 
			"ORDER BY P.AGG_DT ASC, P.POSTNO_VALUE ASC "; 
	} 
}
