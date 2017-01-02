package com.dbs.mcare.service.mobile.user.repository.oracle;

import com.dbs.mcare.framework.template.MCareQuery;

/**
 * 사용자 탈퇴정보 
 * @author aple
 *
 */
public class MCareUserWithdrawalMCareQuery extends MCareQuery {
	@Override 
	public String getQueryForInsert() {
		return "INSERT INTO MCARE_USER_WITHDRAWAL (P_ID, REGISTER_DT, WITHDRAWAL_DT, REASON_VALUE) " + 
				" VALUES(:pId, :registerDt, :withdrawalDt, SUBSTR(:reasonValue, 255) ) ";
	}
}
