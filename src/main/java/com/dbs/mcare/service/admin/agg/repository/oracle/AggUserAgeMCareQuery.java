package com.dbs.mcare.service.admin.agg.repository.oracle;

import com.dbs.mcare.framework.template.MCareQuery;
/**
 * 연령별 통계 
 * @author aple
 *
 */
public class AggUserAgeMCareQuery extends MCareQuery { 
	@Override
	public String getQueryForInsert() {
		return "INSERT INTO MCARE_AGG_USER_AGE (" + 
					"AGG_SEQ, " + 
					"AGE_ORDER, " +
					"DATA_CNT, " + 
					"AGG_DT) " + 
				"VALUES (" + 
					" SEQ_MCARE_AGG_USER_AU.NEXTVAL, " + 
					" :ageOrder, " + 
					" :dataCnt, " + 
					" :aggDt" + 
				") "; 
	}
	
	// 날짜별 검색 
	@Override
	public String getQueryForListByDate() { 
		return "SELECT AGG_SEQ, AGE_ORDER, DATA_CNT, AGG_DT " + 
			"FROM MCARE_AGG_USER_AGE " + 
			"WHERE TO_CHAR(AGG_DT, 'yyyy-MM-dd') BETWEEN TO_CHAR(:strDate, 'yyyy-MM-dd') AND TO_CHAR(:endDate, 'yyyy-MM-dd') " + 
			"ORDER BY AGE_ORDER ASC "; 
	} 
}
