package com.dbs.mcare.service.mobile.user.repository.oracle;

import com.dbs.mcare.framework.template.MCareQuery;

/**
 * 사용자가 체크한 동의서들 관리 
 * 
 * @author hyeeun
 *
 */
public class MCareUserAgreementMCareQuery  extends MCareQuery {
	@Override 
	public String getQueryForInsert() {
		return "INSERT INTO MCARE_USER_AGREEMENT (USER_AGREEMENT_SEQ, AGREEMENT_SEQ, AGREEMENT_ID, P_ID, AGREEMENT_YN, REGISTER_DT) " + 
				"VALUES (SEQ_MCARE_USER_AGREEMENT_UA.NEXTVAL, :agreementSeq, :agreementId, :pId, :agreementYn, SYSDATE) "; 
	}
	@Override 
	public String getQueryForDelete() {
		return "DELETE FROM MCARE_USER_AGREEMENT WHERE P_ID = :pId";
				
	}
}
