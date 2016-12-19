package com.dbs.mcare.service.batch.job;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.dbs.mcare.MCareConstants;
import com.dbs.mcare.exception.mobile.ApiCallException;
import com.dbs.mcare.framework.service.batch.BatchJob;
import com.dbs.mcare.framework.util.DateUtil;
import com.dbs.mcare.service.admin.agg.repository.AggUserAgeRepository;
import com.dbs.mcare.service.admin.agg.repository.AggUserPostnoRepository;
import com.dbs.mcare.service.admin.agg.repository.AggUserTmpRepository;
import com.dbs.mcare.service.admin.agg.repository.dao.AggUserAge;
import com.dbs.mcare.service.admin.agg.repository.dao.AggUserPostno;
import com.dbs.mcare.service.admin.agg.repository.dao.AggUserTmp;
import com.dbs.mcare.service.api.MCareApiCallService;
import com.dbs.mcare.service.api.PnuhApi;

/**
 * 연령별 통계 생성 
 * @author aple
 *
 */
@Component 
public class UserInfoBatchJob extends BatchJob {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired 
	private MCareApiCallService apiCallService; 
	@Autowired 
	private AggUserTmpRepository userTmpRepository; 
	@Autowired 
	private AggUserAgeRepository userAgeRepository;
	@Autowired 
	private AggUserPostnoRepository userPostnoRepository; 
	
	/**
	 * 생성자 
	 */
	public UserInfoBatchJob() {
		super("연령별/지역별사용자통계");
	}

	@SuppressWarnings("unchecked")
	@Override
	public void execute(Date aggDt) throws Exception {
		// 임시테이블 정리. 실패 시 데이터가 유효하지 않으므로 더 이상 처리하지 않음. 그리고 간단한 DELETE문이라 에러나기도 어려울것으로 예상됨  
		this.userTmpRepository.delete(new AggUserTmp()); 
		
		// 현재등록된 전체 환자번호 가져오기 
		List<Map<String, Object>> pidList = null; 
	
		try { 
			pidList = (List<Map<String, Object>>) this.apiCallService.execute(PnuhApi.USER_USERINFO_GETPIDLIST, Collections.<String, Object> emptyMap()); 
		}
		catch(ApiCallException ex) { 
			if(logger.isErrorEnabled()) { 
				logger.error(this.getBatchName() + " : 환자정보수집실패로 통계작업중단. ", ex);
			} 
			
			return; 
		}		
		
		// 등록된 환자 확인 
		if(pidList == null || pidList.isEmpty()) {
			if(logger.isInfoEnabled()) { 
				logger.info(this.getBatchName() + " : 데이터가 없어서 통계작업중단. ");
			} 
			
			return; 
		}

		// 한명씩 처리 
		for(final Map<String, Object> pMap : pidList) { 
			final String pId = (String) pMap.get("pId"); 
			
			// 정보 가져오기 
			Map<String, Object> pInfoMap = null; 
			
			try { 
				pInfoMap = (Map<String, Object>) this.apiCallService.execute(PnuhApi.USER_USERINFO_GETUSERINFO, "pId", pId); 
			}
			catch(ApiCallException ex) { 
				if(logger.isErrorEnabled()) { 
					logger.error(this.getBatchName() + " : 정보수집이 불가능한 환자 발생. 통계에서 제외됨. pId=" + pId, ex);
				} 	
				
				continue; 			
			}
			
			// 변환 
			final AggUserTmp userTmp = this.convert(pInfoMap); 
			if(userTmp == null) { 
				if(logger.isErrorEnabled()) { 
					logger.error(this.getBatchName() + " : 정보수집이 불가능한 환자 발생. 통계에서 제외됨. pId=" + pId);
				} 	
				
				continue; 
			}
			
			// 저장 
			this.userTmpRepository.insert(userTmp); 
		}
		
		
		// 통계 데이터 생성 
		List<AggUserTmp> list = null; 
		
		// 연령별 통계 
		list = this.userTmpRepository.findByAgeOrder(); 
		// 하나씩 추가 
		for(final AggUserTmp userTmp : list) { 
			final AggUserAge age = new AggUserAge(); 
			age.setAgeOrder(userTmp.getAgeOrder());
			age.setDataCnt(userTmp.getDataCnt()); 
			age.setAggDt(aggDt);
			
			this.userAgeRepository.insert(age); 
		}
		
		// 우편번호별 통계 
		list = this.userTmpRepository.findByPostno(); 
		// 하나씩 추가 
		for(final AggUserTmp userTmp : list) { 
			final AggUserPostno postNo = new AggUserPostno(); 
			postNo.setPostnoValue(userTmp.getPostnoValue());
			postNo.setDataCnt(userTmp.getDataCnt());
			postNo.setAggDt(aggDt);
			
			this.userPostnoRepository.insert(postNo); 
		}
	}

	/**
	 * 조회된 데이터에 대한 자료구조 변환 
	 * 
	 * @param pInfoMap
	 * @return
	 */
	private AggUserTmp convert(Map<String, Object> pInfoMap) { 
		if(pInfoMap == null || pInfoMap.isEmpty()) {
			return null; 
		}
		
		
		// 자료구조 변신 
		final AggUserTmp userTmp = new AggUserTmp(); 
		
		// 연령대 
		try { 
			final int ageGroup = DateUtil.convertAgeGroup((String) pInfoMap.get("birthDt")); 
			userTmp.setAgeOrder(ageGroup);
		}
		catch(final Exception ex) { 
			if(logger.isErrorEnabled()) { 
				logger.error(this.getBatchName() + " : 연령대 변환실패. 기본연령으로처리 : " + MCareConstants.INVALID_AGE_GROUP); 
			} 	
			
			userTmp.setAgeOrder(MCareConstants.INVALID_AGE_GROUP);
		}
		
		// 우편번호 
		String zipCode = String.valueOf(pInfoMap.get("zipCode")); 
		
		if(StringUtils.isEmpty(zipCode)) {
			zipCode = MCareConstants.INVALID_POSTNO; 
		}
		else {
			zipCode = zipCode.trim(); 
			
			// 옛날 전화번호인 경우 
			if(zipCode.length() != 5) { 
				zipCode = MCareConstants.INVALID_POSTNO; 
			}
			// 정상인 경우,  두자리만 잘라옴. 시/군/구까지 했더니 데이터가 너무 복잡함. 
			else { 
				try { 
					zipCode = zipCode.substring(0, 2); 
				}
				catch(final Exception ex) { 
					zipCode = MCareConstants.INVALID_POSTNO; 
				}
			}
		}
		
		userTmp.setPostnoValue(zipCode);	
		
		
		return userTmp; 
	}
}
