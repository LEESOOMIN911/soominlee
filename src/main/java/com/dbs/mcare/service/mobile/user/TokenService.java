package com.dbs.mcare.service.mobile.user;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.dbs.mcare.exception.mobile.ApiCallException;
import com.dbs.mcare.framework.FrameworkConstants;
import com.dbs.mcare.framework.util.ConvertUtil;
import com.dbs.mcare.framework.util.HttpRequestUtil;
import com.dbs.mcare.framework.util.UserAgentParser;
import com.dbs.mcare.service.PnuhConfigureService;
import com.dbs.mcare.service.api.MCareApiCallService;
import com.dbs.mcare.service.api.PnuhApi;

/**
 * 디바이스 토큰 관련 
 * 해당 코드가 길지는 않지만 controller 쪽에 들어있어서 분리함 
 * @author hyeeun 
 *
 */
@Service 
public class TokenService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());	
	@Autowired 
	private MCareApiCallService apiCallService; 
	@Autowired 
	private PnuhConfigureService configureService; 

	
	
	/**
	 * device token에 대한 insert or update 
	 * @param request
	 * @param pId
	 * @param tokenId
	 * @param certType
	 * @return true : 작업성공, false : 작업실패 
	 * @throws ApiCallException
	 */
	public boolean processDeviceToken(HttpServletRequest request, String pId, String tokenId, String certType) throws ApiCallException {
		// 웹 접근이 허용된 사용자인가? 그럼 토큰이 없더라도 받아줌 
		// 웹 접근이 허용된 사용자는 최소한으로 유지 필요. 현실적으로는 서버개발자와 화면개발자 두명만 해당 
		if(this.configureService.getAnyLoginAllowUserSet().contains(pId)) {
			// 웹으로 들어오면 tokenId와 certType이 없기 때문에 심어줌 
			// 웹 브라우저에서 모바일인척하고 들어오면 A, I로 들어올 수 있으나 걔들은 token이 없음
			// 앱에서 알림허용을 안하는 경우, 정해진 상수로 토큰이 들어옴 
			// iPhone : NOT_ALLOWED_DEVICE_TOKEN_BY_USER 
			// Android : 관련 코드없음. 왜냐면, Andorid는 OS설정에서 앱의 알림차단을 하더라도 토큰은 올라오기 때문임 
			if(StringUtils.isEmpty(tokenId)) { 
				tokenId = "TEST_TOKEN"; 
				certType = "Developer"; 
				
				if(this.logger.isDebugEnabled()) {
					StringBuilder builder = new StringBuilder(FrameworkConstants.NEW_LINE); 
					
					builder.append("웹 접근이 허용된 사용자를 위한 토큰/인정서 설정 ===== ").append(FrameworkConstants.NEW_LINE); 
					builder.append("- pId : ").append(pId).append(FrameworkConstants.NEW_LINE); 
					builder.append("- tokenId : ").append(tokenId).append(FrameworkConstants.NEW_LINE); 
					builder.append("- certType : ").append(certType);  
					
					this.logger.debug(builder.toString());
				}
			} 
		}
		
		// 정상 사용자를 위한 분석 시작 
		final UserAgentParser parser = new UserAgentParser(HttpRequestUtil.getUserAgent(request)); 		
		final String platformType = parser.getPlatform().getPlatformName().charAt(0) + ""; 		
		
		// 유효성 확인 
		if(!checkTokenValidation(pId, tokenId, certType, platformType)) {
			return false; 
		}
		
		// 유효한 값이면 update or insert를 하기위한 맵을 만들고 
		final Map<String, Object> map = new HashMap<String, Object>();
		// 수신자 
		map.put("receiverId", pId); 
		// 인증서유형 
		map.put("certType", certType); 
		// Android/iOS 여부 
		//iOS의 경우 platformType이 i소문자이고 이후 구분하는 부분에서는 대문자로 사용하여 강제로 UpperCase로 변환 
		map.put("platformType", platformType.toUpperCase()); 
		// 등록/갱신할 토큰 
		map.put("deviceTokenId", tokenId); 
		
		
		// 기존에는 등록된 정보 갯수를 확인했으나 이제 최근 로그인 시간을 업데이트 해둠 
		Map<String, Object> resultMap = this.apiCallService.execute(PnuhApi.USER_TOKENS_UPDATETOKENDATE, map).getResultAsMap(); 
		
		// 비정상적인 경우임 
		if(resultMap == null || resultMap.isEmpty()) {
			if(this.logger.isErrorEnabled()) { 
				this.logger.error("토큰기반 로그인 시간 갱신실패. param : " + map.toString());
			} 
			
			return false; 
		}

		// update가 적용된 행의 갯수를 체크하는 방식으로 insert가 필요한지 검사 
		final Integer userCnt = ConvertUtil.convertInteger(resultMap.get(FrameworkConstants.UIRESPONSE_EMPTY_RESULT_KEY)); 
		if(userCnt != null && userCnt >= 1) { 
			this.logger.debug("이미 등록된 토큰. pId=" + pId + ", platformType=" + platformType);
		} 
		else {
			// 등록이 안되어 있으면 저장 
			this.apiCallService.execute(PnuhApi.USER_TOKEN_SAVEDEVICETOKEN, map); 
		}		
		
		return true; 
	}	
	
	
	/**
	 * 토큰 등록/갱신 하기에 값이 적절한지 검사 
	 * @param pId
	 * @param tokenId
	 * @param certType
	 * @param platformType
	 * @return true : 적절함, false : 적절하지 않음 
	 */
	private boolean checkTokenValidation(String pId, String tokenId, String certType, String platformType) { 
		boolean result = true; 
		
		// token. 실패인 경우 로그 찍어줄려고 if문으로 걸렀음  
		if(StringUtils.isEmpty(tokenId)) {
			result = false; 
		}
		// certType. token이 없는데 certType을 검사할 필요는 없으니까 의도적인 else if 임 
		else if(StringUtils.isEmpty(certType)) {
			result = false; 
		}
		// platformType. Android/iOS만 지원. 아직은. 
		else if(!"A".equalsIgnoreCase(platformType) && !"i".equalsIgnoreCase(platformType)) { 
			result = false; 
		}
		
		// 거절되는 경우 파라미터 찍어서 디버깅할 수 있도록 하고 
		if(StringUtils.isEmpty(tokenId) || StringUtils.isEmpty(certType)) {
			if(this.logger.isErrorEnabled()) {
				StringBuilder builder = new StringBuilder(FrameworkConstants.NEW_LINE); 
				
				builder.append("토큰등록 거절처리 ===== ").append(FrameworkConstants.NEW_LINE); 
				builder.append("- pId : ").append(pId).append(FrameworkConstants.NEW_LINE); 
				builder.append("- tokenId : ").append(tokenId).append(FrameworkConstants.NEW_LINE); 
				builder.append("- certType : ").append(certType).append(FrameworkConstants.NEW_LINE); 
				builder.append("- platformType : ").append(platformType); 
				
				this.logger.debug(builder.toString());
			}
		}
		
		return result; 
	}	
}
