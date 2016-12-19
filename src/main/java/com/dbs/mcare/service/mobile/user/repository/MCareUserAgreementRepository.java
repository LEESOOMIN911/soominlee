package com.dbs.mcare.service.mobile.user.repository;

import org.springframework.stereotype.Repository;

import com.dbs.mcare.framework.template.GenericRepository;
import com.dbs.mcare.service.mobile.user.repository.dao.MCareUserAgreement;
/**
 * 사용자가 동의한 동의서 목록 
 * 
 * @author hyeeun 
 *
 */
@Repository 
public class MCareUserAgreementRepository extends GenericRepository<MCareUserAgreement> {

}
