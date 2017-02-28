package com.dbs.mcare.service.api;

import java.util.Collections;
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
import com.dbs.mcare.framework.exception.MCareApiServiceException;
import com.dbs.mcare.framework.service.ApiCallService;
import com.dbs.mcare.framework.util.ConvertUtil;
import com.dbs.mcare.framework.util.ResponseUtil;
import com.dbs.mcare.service.api.util.JSONResParser;
import com.dbs.mcare.service.api.util.ParamMappingUtil;
import com.dbs.mcare.service.api.util.TestUserDataGenerator;

import net.sf.json.JSONObject;

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
	public Object execute(PnuhApi api, Map<String, Object> reqMap, Map<String, String> headerMap) throws ApiCallException {
		Map<String, Object> resultMap = this.call(api, reqMap, headerMap); 
		// 결과 꺼내주기 
		return resultMap.get(FrameworkConstants.UIRESPONSE.RESULT.getKey()); 
	}
	
	/**
	 * 내부 서비스용, api를 호출하고, 결과를 전달함 
	 * @param api 대상 API 
	 * @param reqMap  요청 파라미터들 
	 * @return 결과 
	 * @throws ApiCallException
	 */
	public Object execute(PnuhApi api, Map<String, Object> reqMap) throws ApiCallException {
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
	public Object execute(PnuhApi api, String key, Object value) throws ApiCallException {
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
		return this.call(api, reqMap, null); 
	}

	/**
	 * UI용, api를 호출하고, 결과를 전달함 
	 * @param api 호출할 API 
	 * @param reqMap 요청 파라미터 맵 
	 * @param headerMap 추가헤더 
	 * @return {@link FrameworkConstatns.UIRESPONSE} 참고 
	 * @throws ApiCallException 
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> call(PnuhApi api, Map<String, Object> reqMap, Map<String, String> headerMap) throws ApiCallException {
		// 연산자 확인 
		if(api == null) { 
			throw new ApiCallException("mcare.error.param", "파라미터를 확인해주세요", null); 
		}
		
		// 파라미터 확인 
		if(reqMap == null) { 
			reqMap = Collections.emptyMap(); 
		}
		
		// 디버깅을 위한 메시지 추가 
		if(this.logger.isDebugEnabled()) { 
			StringBuilder builder = new StringBuilder(FrameworkConstants.NEW_LINE); 
			
			builder.append("Api 호출 =====").append(FrameworkConstants.NEW_LINE); 
			builder.append("- api : ").append(api).append(FrameworkConstants.NEW_LINE);  
			if(headerMap != null) {
				builder.append("- headers ").append(FrameworkConstants.NEW_LINE); 
				builder.append(headerMap).append(FrameworkConstants.NEW_LINE); 
			}
			
			builder.append("- request parameters ").append(FrameworkConstants.NEW_LINE); 
			builder.append(ConvertUtil.convertStringForDebug(reqMap)).append(FrameworkConstants.NEW_LINE); 
			
			this.logger.debug(builder.toString());
		}
		
		// 테스트 환자가 활성화 되어 있나? 
		// 릴리즈 할때는 여기를 막아도 됨 
		if(MCareConstants.MCARE_TEST_USER.ACTIVATE) {
			String pId = (String) reqMap.get("pId"); 
			
			if(MCareConstants.MCARE_TEST_USER.INFO.isTestId(pId)) { 
				// 테스트 코드 만들어내는 api라면 테스트 코드를 반환하고, 아니면 시스템으로 요청이 흘러들어감 
				Object obj = this.testDataGenerator.call(api, reqMap); 
				// 결과가 있으면 반환 
				if(obj != null) { 
					return (Map<String, Object>)obj;
				}				
			}
		
			// 테스트 환자가 아니라면 실제 api를 호출하러 감 
		}
		// 릴리즈 할때는 여기까지 막아도 됨 

		
		// 없으면 실제 api 호출 
		if(api.isList()) {
			return this.callList(api, reqMap, headerMap); 
		}

		return this.callObject(api, reqMap, headerMap); 
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
		if(StringUtils.isEmpty(key) || value == null) {
			throw new ApiCallException("mcare.error.param", "파라미터를 확인해주세요", null); 
		}
		
		// 맵 만들고 
		Map<String, Object> reqMap = new HashMap<String, Object>(); 
		reqMap.put(key, value);
		
		// 호출 
		return this.call(api, reqMap, null); 
	}
	
	/**
	 * list형 호출  
	 * @param api 호출할 API 
	 * @param reqMap 요청 파라미터 
	 * @param headerMap 헤더. WEB_SERVICE만 유효 
	 * @return UI단에 전달할 수 있도록 정리된 형태의 결과 맵 
	 * @throws ApiCallException
	 */
	private Map<String, Object> callList(PnuhApi api, Map<String, Object> reqMap, Map<String, String> headerMap) throws ApiCallException { 
		try { 
			switch(api.getApiType()) {
			case WEB_SERVICE : 
				return super.callHttpForList(this.apiDelegator, api.getApiUrl(), reqMap, headerMap); 
				
			case SQL : 
			case PROCEDURE : 			
				return super.callSqlForList(this.apiDelegator, api.getApiUrl(), reqMap); 	
			}
		}
		catch(final Exception ex) { 
			if(this.logger.isDebugEnabled()) { 
				this.logger.debug("api호출오류. " + api + ", reqMap=" + reqMap, ex);
			} 
			
			throw new ApiCallException("mcare.error.if.system", "시스템 호출 오류가 발생했습니다 (IF)", api);  
		}
		
		// 여기에 왔으면 개발단계에서 못잡은 오류 
		throw new ApiCallException("mcare.error.system", "시스템 호출 오류가 발생했습니다", api); 
	}

	/**
	 * object형 호출 
	 * @param api 호출할 api 
	 * @param reqMap 요청파라미터 
	 * @param headerMap 헤더. WEB_SERVICE만 유효 
	 * @return
	 * @throws ApiCallException
	 */
	private Map<String, Object> callObject(PnuhApi api, Map<String, Object> reqMap, Map<String, String> headerMap) throws ApiCallException { 
		try { 
			switch(api.getApiType()) {
			case WEB_SERVICE : 
				return super.callHttpForObject(this.apiDelegator, api.getApiUrl(), reqMap, headerMap); 
				
			case SQL :  	
			case PROCEDURE : 			
				return super.callSqlForObject(this.apiDelegator, api.getApiUrl(), reqMap); 	
			}
		}
		catch(final Exception ex) { 
			if(this.logger.isDebugEnabled()) { 
				this.logger.debug("api호출오류. " + api + ", reqMap=" + reqMap, ex);
			} 
			
			throw new ApiCallException("mcare.error.if.system", "시스템 호출 오류가 발생했습니다 (IF)", api); 
		}
		
		// 여기에 왔으면 개발단계에서 못잡은 오류 
		throw new ApiCallException("mcare.error.system", "시스템 호출 오류가 발생했습니다", api); 
	}

	@Override
	protected Map<String, Object> callHttp(ApiExecuteDelegator apiDelegator, String apiUrl, Map<String, Object> reqMap, Map<String, String> headerMap) throws MCareApiServiceException {
		// 결과 
		JSONObject resObj = null; 
		JSONResParser parser = null; 
		
		try { 
			// API 실행 
			resObj = (JSONObject) apiDelegator.execute(apiUrl, reqMap, headerMap); 
			// 결과분석 
			parser = new JSONResParser(resObj); 
		}
		catch(MCareApiServiceException ex) { 
			throw ex; 
		}
		catch(Exception ex) { 
			throw new MCareApiServiceException(ex); 
		}
		
		
		// 실패인가 
		if(parser.isError()) {
			throw new ApiCallException("mcare.error.if.system", "시스템 호출 오류가 발생했습니다 (IF)", null);  
		}

		// 추가 메시지가 없는 경우, 기본 메시지를 심어주면 좋겠지만 다국어까지 생각해야 해서 패스 
		Object resultObj = parser.getResult(); 
		
		// 결과정리. 부산대는 extraMsg가 없음. 
		return ResponseUtil.wrapResultMap(resultObj); 
	}

	/**
	 * 추가헤더 필요없는 API 그대로의 HTTP 호출 
	 * @param apiDelegator 실행자 
	 * @param apiUrl api 구분을 위한 uri 
	 * @param reqMap 요청 파라미터 
	 * @return 실행결과 
	 * @throws MCareApiServiceException
	 */
	protected Map<String, Object> callHttp(ApiExecuteDelegator apiDelegator, String apiUrl, Map<String, Object> reqMap) throws MCareApiServiceException {
		return this.callHttp(apiDelegator, apiUrl, reqMap, null); 
	}

}
