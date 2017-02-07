package com.dbs.mcare.service.mobile.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.dbs.mcare.framework.exception.MCareServiceException;
import com.dbs.mcare.framework.service.user.GeneralUserService;
import com.dbs.mcare.framework.template.GenericService;
import com.dbs.mcare.service.mobile.user.repository.MCareUserAgreementRepository;
import com.dbs.mcare.service.mobile.user.repository.MCareUserRepository;
import com.dbs.mcare.service.mobile.user.repository.dao.MCareUser;
import com.dbs.mcare.service.mobile.user.repository.dao.MCareUserAgreement;

/**
 * 사용자 관리  
 * @author hyeeun 
 *
 */
@Service 
public class MCareUserService extends GenericService<MCareUser, MCareUserRepository> {
	private static Logger logger = LoggerFactory.getLogger(MCareUserService.class); 
	@Autowired 
	private MCareUserAgreementRepository userAgreementRepository; 
	@Autowired
	private MCareUserRepository userRepository;
	@Autowired 
	private GeneralUserService generalUserService; 

	
	/**
	 * 사용자 추가 
	 * @param pId
	 * @param pName
	 * @param sha256PassWord
	 * @param aggList 동의서 목록 
	 * @throws MCareServiceException
	 */
	public String insertUser(String pId, String hashPwd) throws MCareServiceException {
		return this.generalUserService.insertUser(pId, hashPwd); 
	}
	
	/**
	 * 사용자 추가 후 동의서 추가 등록
	 * @param pId 환자번호 
	 * @param agreementList 동의서목록 
	 * @throws MCareServiceException
	 */
	public void insertUserAgreement(String pId, List<MCareUserAgreement> agreementList) throws MCareServiceException {
		// 동의서 등록 
		try {
			if(agreementList != null) { 
				for(final MCareUserAgreement m : agreementList) { 
					m.setPid(pId);
					this.userAgreementRepository.insert(m); 
				}
			}
		}
		catch(final Exception ex) {
			logger.error("사용자 동의서 등록 실패", ex);
			throw new MCareServiceException("사용자 동의서 등록 실패", ex);
		}
	}
	
	/**
	 * 사용자 패스워드 변경 
	 * @param chartNoValue 환자번호 
	 * @param hashPassword 해쉬적용된 비번 
	 * @throws MCareServiceException
	 */
	public void updateUserPassword(String chartNoValue, String hashPassword) throws MCareServiceException {
		this.generalUserService.updateUserPassword(chartNoValue, hashPassword);
	}
	
	/**
	 * 사용자 패스워드 확인 
	 * @param chartNoValue 환자번호 
	 * @param hashPwd 해쉬적용된 비번 
	 * @return
	 */
	public boolean checkUserPassword(String chartNoValue, String hashPwd) {
		return this.generalUserService.checkUserPassword(chartNoValue, hashPwd); 
	}

	/**
	 * 사용자 목록 조회 
	 * @param params
	 * @return
	 * @throws MCareServiceException
	 */
	public Map<String, Object> selectUserAccessDay(Map<String, Object> params) throws MCareServiceException {
		// 결과를 받아서 전달 
		// 관리자 사용자 관리에서 사용자 검색기능을 사용하면 다른 쿼리를 호출하도록 추가함
		
		String pId = (String) params.get("searchPId"); 
		
		if(!StringUtils.isEmpty(pId)){
			// 특정환자에 대한 것이므로 사실 paging이 필요없으나 리턴값을 맞추기 위함 
				return this.userRepository.queryForPaging("selectOneUserAccessDay", "selectOneUserAccessDayCount", params, false); 
		} 
		else {
			return this.userRepository.queryForPaging("selectUserAccessDay", "selectUserAccessDayCount", params, false); 
		}
	}
	
	/**
	 * 사용자 정보 가져오기 
	 * @param pId 환자번호 
	 * @return 사용자 정보 
	 * @throws MCareServiceException
	 */
	public MCareUser get(String pId) throws MCareServiceException { 
		MCareUser user = new MCareUser(); 
		user.setpId(pId);
		
		
		return super.get(user); 
	}
	
	/**
	 * 사용자 삭제 
	 * @param pId
	 * @throws MCareServiceException
	 */
	@Transactional(value = "mcareTransactionManager", rollbackFor = { Exception.class })
	public void removeUser(String pId) throws MCareServiceException {
		try {
			// 파라미터 정리 
			Map<String, Object> paramMap = new HashMap<>(); 
			paramMap.put("pId", pId); 
			
			// 토큰삭제 
			this.userRepository.deleteUserDeviceToken(paramMap);
			// 동의서 삭제 
			this.userAgreementRepository.delete(paramMap); 
			// 사용자 삭제 
			this.userRepository.delete(paramMap); 
		}
		catch(final Exception ex) { 
			final MCareServiceException f = new MCareServiceException("사용자 삭제 실패. pId=" + pId, ex); 
			logger.error("", f);
			throw f; 
		}
	}

	/**
	 * 사용자 패스워드 유효성 체크
	 * @param password
	 * @return
	 */
	public Map<String, Object> validatePWD (String password) {
		
		Pattern patt_1 = Pattern.compile("[a-zA-Z0-9]|[!@#$%^&*?_~]");
		Pattern patt_2 = Pattern.compile("([가-힣ㄱ-ㅎㅏ-ㅣ\\x20])");
		Pattern patt_3 = Pattern.compile("(\\w)\\1\\1");
		Pattern patt_4 = Pattern.compile("([!@#$%^&*?_~])\\1\\1");
		Pattern patt_5 = Pattern.compile("(012)|(123)|(234)|(345)|(456)|(567)|(678)|(789)|(890)|(901)");
		Pattern patt_6 = Pattern.compile("(abc)|(bcd)|(cde)|(def)|(efg)|(fgh)|(ghi)|(hij)|(ijk)|(jkl)|(klm)|(lmn)|" + 
				 						 "(mno)|(nop)|(opq)|(pqr)|(qrs)|(rst)|(stu)|(tuv)|(uvw)|(vwx)|(wxy)|(xyz)|(yxa)|" + 
				 						 "(ABC)|(BCD)|(CDE)|(DEF)|(EFG)|(FGH)|(GHI)|(HIJ)|(IJK)|(JKL)|(KLM)|(LMN)|(MNO)|" + 
										 "(NOP)|(OPQ)|(PQR)|(QRS)|(RST)|(STU)|(TUV)|(UVW)|(VWX)|(WXY)|(XYZ)|(YZA)");
		Pattern patt_7 = Pattern.compile("^(?=.*\\d)(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9]).*$");
		
		Matcher match_1 = patt_1.matcher(password);
		Boolean result = false;
		String invalidWord = match_1.replaceAll("");
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		if (password.length() < 8) {					//문자열 길이가 8자리 이상인지 확인
			resultMap.put("code", "mobile.message.common019");
		}
		else if (patt_2.matcher(password).find()) {		//한글 포함 확인
			resultMap.put("code", "mobile.message.common013");
		}
		else if(!StringUtils.isEmpty(invalidWord)) {	//사용가능한 특수문자이외의 문자가 포함되었는지 확인
			resultMap.put("code", "mobile.message.common024");
		} 
		else if (!patt_7.matcher(password).find()) {	//패스워드 패턴에 유효하지 않음
			resultMap.put("code", "mobile.message.common014");
		}
		else if (patt_3.matcher(password).find()) {		//같은 문자 또는 숫자가 세번 이상 반복되는지 확인
			resultMap.put("code", "mobile.message.common015");
		}
		else if (patt_4.matcher(password).find()) {		//같은 특수 문자가 세번 이상 반복되는지 확인
			resultMap.put("code", "mobile.message.common016");
		}
		else if (patt_5.matcher(password).find()) {		//연속된 숫자(012)가 있는지 확인
			resultMap.put("code", "mobile.message.common017");
		}
		else if (patt_6.matcher(password).find()) {		//연속된 문자(abc)가 있는지 확인
			resultMap.put("code", "mobile.message.common018");
		}
		else {
			//패스워드가 유효하면 true
			result = true; 
		}
		
		resultMap.put("result", result);
		return resultMap;
	}
	
	/**
	 * 사용자 비번 날짜 갱신 (비밀번호 사용연장) 
	 * @param pId 대상환자번호 
	 * @throws MCareServiceException
	 */
	public void updatePasswordUpdateTime(String pId) throws MCareServiceException {
		this.generalUserService.updatePasswordUpdateTime(pId);
	}
}
