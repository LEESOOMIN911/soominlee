package com.dbs.mcare.service.mobile.user.repository;

import java.util.Map;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import com.dbs.mcare.framework.exception.MCareServiceException;
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
	/**
	 * 신규 사용자 추가 
	 * @param pId 차트번호 
	 * @param pName 사용자 이름 
	 * @param sha256PassWord 해쉬적용된 로그인 비번 
	 * @param localCipherKey 로컬DB암호화 키 
	 * @throws MCareServiceException 
	 */
	public void insertUser(String pId, String pName, String sha256PassWord, String localCipherKey) throws MCareServiceException {
		final MapSqlParameterSource param = new MapSqlParameterSource(); 
		
		param.addValue("pId", pId); 
		param.addValue("pName", pName); 
		param.addValue("passWordValue", sha256PassWord); 
		param.addValue("localCipherKeyValue", localCipherKey); 
		
		// 추가 
		super.insert(param); 
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
