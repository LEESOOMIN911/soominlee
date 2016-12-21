package com.dbs.mcare.service.mobile.user.repository.oracle;

import com.dbs.mcare.framework.template.MCareQuery;

/**
 * 사용자 관리 
 * @author hyeeun
 *
 */
public class MCareUserMCareQuery extends MCareQuery {
	@Override
	public String getQueryForList(){
		return "SELECT USER_SEQ, P_ID, LOGIN_FAIL_CNT, REGISTER_DT FROM MCARE_USER";
	}
	@Override 
	public String getQueryForInsert() {
		return "INSERT INTO MCARE_USER (USER_SEQ, P_ID, PASSWORD_VALUE, PASSWORD_UPDATE_DT, " + 
				" LOGIN_FAIL_CNT, LOCAL_CIPHER_KEY_VALUE, REGISTER_DT) " + 
				" VALUES(SEQ_MCARE_USER_US.NEXTVAL, :pId, :passWordValue, SYSDATE, " +
				" 0, :localCipherKeyValue, SYSDATE)"; 
	}
	@Override
	public String getQueryForUpdate() {	
		return "UPDATE MCARE_USER SET LOGIN_FAIL_CNT = 0 " +
				"WHERE USER_SEQ=:userSeq"; 
	}
	@Override
	public String getQueryForDelete() {	
		return "DELETE FROM MCARE_USER WHERE P_ID=:pId"; 
	}
	
	@Override
	public String getQueryForCount() {
		return "SELECT COUNT(1) FROM MCARE_USER";
	}
	
	@Override 
	public String getQueryForObject() {
		return "SELECT " + 
			"USER_SEQ, P_ID, PASSWORD_VALUE, PASSWORD_UPDATE_DT, LOGIN_FAIL_CNT, LOCAL_CIPHER_KEY_VALUE, REGISTER_DT " +  
			"FROM MCARE_USER  " + 
			"WHERE P_ID=:pId";
	}
	
	/**
	 * 사용자의 디바이스 토큰 지우기 
	 * @return
	 */
	public String deleteUserDeviceToken() { 
		return "DELETE FROM MNS_RECEIVER_DEVICE WHERE RECEIVER_ID=:pId"; 
	}
	
	/**
	 * 환자번호 | 로그인실패횟수 | 최근 접속일 | 비접속기간 | 등록일 | 가입기간 
	 * @return
	 */
	public String selectUserAccessDay() { 
		return "SELECT " + 
		"  A.P_ID, A.LOGIN_FAIL_CNT, NVL(A.ACCESS_DT,A.REGISTER_DT) AS ACCESS_DT, A.LAST_ACCESS_DAY, A.REGISTER_DT, A.REGISTER_DAY " + 
		" FROM ( " + 
		"  SELECT " + 
		"    U.P_ID, " + 
		"    U.LOGIN_FAIL_CNT, " + 
		"    NVL(HJ.ACCESS_DT,U.REGISTER_DT) AS ACCESS_DT, " + 
		"    U.REGISTER_DT AS REGISTER_DT, " + 
		"    CAST(SYSDATE - NVL(HJ.ACCESS_DT, U.REGISTER_DT) AS INTEGER) AS LAST_ACCESS_DAY,  " + 
		"    CAST(SYSDATE - U.REGISTER_DT AS INTEGER) AS REGISTER_DAY " + 
		"  FROM MCARE_USER U  " + 
		"  LEFT JOIN ( " + 
		"    SELECT DISTINCT(H.P_ID) AS P_ID, MAX(H.ACCESS_DT) AS ACCESS_DT " + 
		"    FROM MCARE_ACCESS_HISTORY H  " + 
		"    GROUP BY H.P_ID ) HJ " + 
		"  ON U.P_ID=HJ.P_ID) A "; 
		
	}
	/**
	 * selectUserAccessDay 질의 데이터 갯수 
	 * @return
	 */
	public String selectUserAccessDayCount() { 
		return "SELECT " + 
		"  COUNT(1) " + 
		" FROM ( " + 
		"  SELECT " + 
		"    U.P_ID, " + 
		"    U.LOGIN_FAIL_CNT, " + 
		"    NVL(HJ.ACCESS_DT,U.REGISTER_DT) AS ACCESS_DT, " + 
		"    U.REGISTER_DT AS REGISTER_DT, " + 
		"    CAST(SYSDATE - NVL(HJ.ACCESS_DT, U.REGISTER_DT) AS INTEGER) AS LAST_ACCESS_DAY,  " + 
		"    CAST(SYSDATE - U.REGISTER_DT AS INTEGER) AS REGISTER_DAY " + 
		"  FROM MCARE_USER U  " + 
		"  LEFT JOIN ( " + 
		"    SELECT DISTINCT(H.P_ID) AS P_ID, MAX(H.ACCESS_DT) AS ACCESS_DT " + 
		"    FROM MCARE_ACCESS_HISTORY H  " + 
		"    GROUP BY H.P_ID ) HJ " + 
		"  ON U.P_ID=HJ.P_ID) A "; 
		
	}	
	
	
	/**
	 * 환자번호 | 로그인실패횟수 | 최근 접속일 | 비접속기간 | 등록일 | 가입기간 
	 * @return
	 */
	public String selectOneUserAccessDay() { 
		return "SELECT " + 
		"  A.P_ID, A.LOGIN_FAIL_CNT, NVL(A.ACCESS_DT,A.REGISTER_DT) AS ACCESS_DT, A.LAST_ACCESS_DAY, A.REGISTER_DT, A.REGISTER_DAY " + 
		" FROM ( " + 
		"  SELECT " + 
		"    U.P_ID, " + 
		"    U.LOGIN_FAIL_CNT, " + 
		"    NVL(HJ.ACCESS_DT,U.REGISTER_DT) AS ACCESS_DT, " + 
		"    U.REGISTER_DT AS REGISTER_DT, " + 
		"    CAST(SYSDATE - NVL(HJ.ACCESS_DT, U.REGISTER_DT) AS INTEGER) AS LAST_ACCESS_DAY,  " + 
		"    CAST(SYSDATE - U.REGISTER_DT AS INTEGER) AS REGISTER_DAY " + 
		"  FROM MCARE_USER U  " + 
		"  LEFT JOIN ( " + 
		"    SELECT DISTINCT(H.P_ID) AS P_ID, MAX(H.ACCESS_DT) AS ACCESS_DT " + 
		"    FROM MCARE_ACCESS_HISTORY H  " + 
		"    GROUP BY H.P_ID ) HJ " + 
		"  ON U.P_ID=HJ.P_ID) A "+
		" WHERE P_ID = :searchPId "; 
	}
	/**
	 * selectOneUserAccessDay 질의 결과 갯수 구하기 
	 * @return
	 */
	public String selectOneUserAccessDayCount() { 
		return "SELECT " + 
		"  COUNT(1) " + 
		" FROM ( " + 
		"  SELECT " + 
		"    U.P_ID, " + 
		"    U.LOGIN_FAIL_CNT, " + 
		"    NVL(HJ.ACCESS_DT,U.REGISTER_DT) AS ACCESS_DT, " + 
		"    U.REGISTER_DT AS REGISTER_DT, " + 
		"    CAST(SYSDATE - NVL(HJ.ACCESS_DT, U.REGISTER_DT) AS INTEGER) AS LAST_ACCESS_DAY,  " + 
		"    CAST(SYSDATE - U.REGISTER_DT AS INTEGER) AS REGISTER_DAY " + 
		"  FROM MCARE_USER U  " + 
		"  LEFT JOIN ( " + 
		"    SELECT DISTINCT(H.P_ID) AS P_ID, MAX(H.ACCESS_DT) AS ACCESS_DT " + 
		"    FROM MCARE_ACCESS_HISTORY H  " + 
		"    GROUP BY H.P_ID ) HJ " + 
		"  ON U.P_ID=HJ.P_ID) A "+
		" WHERE P_ID = :searchPId "; 
	}	
}
