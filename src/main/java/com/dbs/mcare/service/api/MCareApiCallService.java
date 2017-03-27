package com.dbs.mcare.service.api;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.dbs.mcare.MCareConstants;
import com.dbs.mcare.exception.mobile.ApiCallException;
import com.dbs.mcare.framework.FrameworkConstants;
import com.dbs.mcare.framework.api.executor.ApiExecuteDelegator;
import com.dbs.mcare.framework.api.parser.ApiParser;
import com.dbs.mcare.framework.api.parser.DefaultApiParser;
import com.dbs.mcare.framework.service.ApiCallService;
import com.dbs.mcare.framework.util.ResponseUtil;
import com.dbs.mcare.service.api.util.ParamMappingUtil;
import com.dbs.mcare.service.api.util.TestUserDataGenerator;

/**
 * Api 호출 모음 
 * @author hyeeun 
 *
 */
@Service 
public class MCareApiCallService extends ApiCallService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	// 
	@Autowired
	@Qualifier("apiExecuteDelegator")
	private ApiExecuteDelegator apiDelegator;
	@Autowired 
	private TestUserDataGenerator testDataGenerator; 
	
	/**
	 * 생성자 
	 */
	public MCareApiCallService() { 
		super(ParamMappingUtil.class); 
	}
	
	/**
	 * 내부 서비스용, api를 호출하고, 결과를 전달함 
	 * @param api 대상 API 
	 * @param reqMap 요청 파라미터들 
	 * @param headerMap 헤더 (WEB_SERVICE인 경우에만 유효) 
	 * @return 결과 
	 * @throws ApiCallException
	 */
	public ApiParser execute(PnuhApi api, Map<String, Object> reqMap, Map<String, String> headerMap) throws ApiCallException {
		// 테스트 환자가 활성화 되어 있나? 
		// 릴리즈 할때는 여기를 막아도 됨 
		if(MCareConstants.MCARE_TEST_USER.ACTIVATE) {
			String pId = (String) reqMap.get("pId"); 
			
			if(MCareConstants.MCARE_TEST_USER.INFO.isTestId(pId)) { 
				// 테스트 코드 데이터는 화면용을 반환하도록 기술되어 있음 
				Map<String, Object> resMap = this.testDataGenerator.call(api, reqMap); 
				// 결과가 있으면 반환 
				if(resMap != null) { 
					ApiParser apiParser = new DefaultApiParser(); 
					apiParser.setResult(resMap.get(FrameworkConstants.UIRESPONSE.RESULT.getKey()));
					
					return apiParser; 
				}
			}
		
			// 테스트 환자가 아니라면 실제 api를 호출하러 감 
		}
		// 릴리즈 할때는 여기까지 막아도 됨 
	
		// api 호출 
		ApiParser apiParser = this.call(api.getDefApi(), reqMap, headerMap); 
	
		// 결과확인 
		if(this.logger.isDebugEnabled()) { 
			logger.debug("결과----- " + FrameworkConstants.NEW_LINE + apiParser);
		} 
		
		return apiParser; 
	}
	
	/**
	 * 내부 서비스용, api를 호출하고, 결과를 전달함 
	 * @param api 대상 API 
	 * @param reqMap  요청 파라미터들 
	 * @return 결과 
	 * @throws ApiCallException
	 */
	public ApiParser execute(PnuhApi api, Map<String, Object> reqMap) throws ApiCallException {
		return this.execute(api, reqMap, null); 
	}	
	
	/**
	 * 내부 서비스용, 파라미터가 1개인 api를 호출하고, 결과를 전달함 
	 * @param api 대상 API 
	 * @param key 요청 key 
	 * @param value 요청 value 
	 * @return 실행결과 
	 * @throws ApiCallException
	 */
	public ApiParser execute(PnuhApi api, String key, Object value) throws ApiCallException {
		if(StringUtils.isEmpty(key) || value == null) {
			throw new ApiCallException("mcare.error.param", "파라미터를 확인해주세요", null); 
		}		
		
		Map<String, Object> map = new HashMap<String, Object>(); 
		map.put(key, value); 
		
		
		return this.execute(api, map, null); 
	}
	
	/**
	 * UI용, api를 호출하고, 결과를 전달함 
	 * @param api 호출할 API 
	 * @param reqMap 요청 파라미터 맵 
	 * @return  {@link FrameworkConstatns.UIRESPONSE} 참고 
	 * @throws ApiCallException
	 */
	public Map<String, Object> call(PnuhApi api, Map<String, Object> reqMap) throws ApiCallException {
		ApiParser apiParser = this.execute(api, reqMap); 
		return ResponseUtil.wrapResultMap(apiParser); 
	}

	/**
	 * UI용, api를 호출하고, 결과를 전달함 
	 * @param api 호출할 API 
	 * @param reqMap 요청 파라미터 맵 
	 * @param headerMap 추가헤더 
	 * @return {@link FrameworkConstatns.UIRESPONSE} 참고 
	 * @throws ApiCallException 
	 */
	public Map<String, Object> call(PnuhApi api, Map<String, Object> reqMap, Map<String, String> headerMap) throws ApiCallException {
		ApiParser apiParser = this.execute(api, reqMap, headerMap); 
		return ResponseUtil.wrapResultMap(apiParser); 
	}

	/**
	 * UI용, 파라미터가 1개인 api를 호출하고, 결과를 전달함 
	 * @param api 호출할 API 
	 * @param key 요청 파라미터 key 
	 * @param value 요청 파라미터의 값 
	 * @return {@link FrameworkConstatns.UIRESPONSE}  참고 
	 * @throws ApiCallException 
	 */
	public Map<String, Object> call(PnuhApi api, String key, Object value) throws ApiCallException {
		ApiParser apiParser = this.execute(api, key, value); 
		return ResponseUtil.wrapResultMap(apiParser); 
	}

}
