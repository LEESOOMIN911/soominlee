package com.dbs.mcare.service.admin.agg.repository.oracle;

import com.dbs.mcare.framework.template.MCareQuery;

/**
 * 사용자 정보 통계를 위한 임시테이블용 질의 
 * @author aple
 *
 */
public class AggUserTmpMCareQuery extends MCareQuery { 

	@Override 
	public String getQueryForInsert() {
		return "INSERT INTO MCARE_AGG_USER_TMP(AGE_ORDER, POSTNO_VALUE) " + 
				"VALUES(:ageOrder, :postnoValue)";
	}
	
	@Override 
	public String getQueryForDelete() {
		return "DELETE FROM MCARE_AGG_USER_TMP"; 
	}
	
	/**
	 * 연령별 통계 
	 * @return
	 */
	public String findByAgeOrder() { 
		return "SELECT AGE_ORDER, COUNT(1) AS DATA_CNT " + 
				" FROM MCARE_AGG_USER_TMP " + 
				" GROUP BY AGE_ORDER " + 
				" ORDER BY AGE_ORDER ASC"; 
	}
	
	/**
	 * 우편번호별 통계 
	 * @return
	 */
	public String findByPostno() { 
		return "SELECT T.POSTNO_VALUE, COUNT(1) AS DATA_CNT  " + 
				" FROM ( " + 
				" 	SELECT  " + 
				"		(SELECT SI_VALUE FROM MCARE_ZIPCODE WHERE SI_ID=POSTNO_VALUE AND ROWNUM<=1) AS POSTNO_VALUE " +
				"	FROM MCARE_AGG_USER_TMP " +
				" ) T " + 
				" GROUP BY T.POSTNO_VALUE  "; 
	}
}
