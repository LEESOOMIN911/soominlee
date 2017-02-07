package com.dbs.mcare.service.mobile.user.repository;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dbs.mcare.framework.exception.MCareServiceException;
import com.dbs.mcare.framework.service.user.repository.GeneralUserRepository;
import com.dbs.mcare.framework.template.GenericRepository;
import com.dbs.mcare.service.mobile.user.repository.dao.MCareUser;
/**
 * 한번등록되면 지워지지 않을것만 같은 사용자 등록 처리 
 * 
 * @author hyeeun 
 *
 */
@Repository 
public class MCareUserRepository extends GenericRepository<MCareUser> {
	@Autowired 
	private GeneralUserRepository generalUserRepository; // 프레임워크에 있는 공통연산 
	
	
	/**
	 * 기존 사용자 비번 수정 
	 * @param pId 환자번호 
	 * @param sha256PassWord 비번 
	 * @throws MCareServiceException
	 */
	public void updatePassword(String pId, String sha256PassWord) throws MCareServiceException {
		this.generalUserRepository.updatePassword(pId, sha256PassWord);
	} 
	
	/**
	 * 사용자 비번 확인 
	 * @param pId 환자번호 
	 * @param sha256PassWord 비번 
	 * @throws MCareServiceException
	 */
	public boolean checkPassword(String pId, String sha256PassWord) throws MCareServiceException {
		return this.generalUserRepository.checkPassword(pId, sha256PassWord); 
	} 
	
	/**
	 * 사용자의 deviceToken 지우기 
	 * 마땅이 꼽아 둘데가 없어서 여기에 위치했음 
	 * Api로 분리하자니 트랜잭션 걸기가 어렵고, 얘를 위해 query-repository-service를 다 만들기도 그렇고... 
	 * 
	 * @param pId
	 * @throws MCareServiceException
	 */
	public void deleteUserDeviceToken(Map<String, Object> param) throws MCareServiceException {
		// 삭제
		super.delete("deleteUserDeviceToken", param); 
	}
}
