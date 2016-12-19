package com.dbs.mcare.service.mobile.user;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.dbs.mcare.framework.FrameworkConstants;
import com.dbs.mcare.framework.util.ConvertUtil;
import com.dbs.mcare.framework.util.UserAgentParser.PLATFORM;
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

	/**
	 *  device token 처리
	 * @param pId
	 * @param tokenId
	 * @param cerType
	 * @param platform
	 * @return 성공적으로 처리했는지 여부 
	 */
	@SuppressWarnings("unchecked")
	public boolean processDeviceToken(String pId, String tokenId, String certType, PLATFORM platform) {
		// token 갱신 
		final Map<String, Object> map = new HashMap<String, Object>(); 
		final String platformType = platform.getPlatformName().charAt(0) + ""; 
		
		try { 
			if(this.logger.isDebugEnabled()) {
				StringBuilder builder = new StringBuilder(); 
				builder.append("processDeviceToken =====").append(FrameworkConstants.NEW_LINE); 
				builder.append("- pId : ").append(pId).append(FrameworkConstants.NEW_LINE); 
				builder.append("- tokenId : ").append(tokenId).append(FrameworkConstants.NEW_LINE); 
				builder.append("- certType : ").append(certType).append(FrameworkConstants.NEW_LINE); 
				builder.append("- platform : ").append(platform).append(" --> ").append(platformType).append(FrameworkConstants.NEW_LINE); 
				
				this.logger.debug(builder.toString());
			}
			
			
			// 실험용 접근을 허용해주기 위함 
			// 웹 브라우저에서 모바일인척하고 들어오면 A, I로 들어올 수 있으나 걔들은 token이 없음 
			if(!"A".equals(platformType) && !"i".equalsIgnoreCase(platformType)) {
				tokenId = "TEST_TOKEN"; 
				certType = "AppStore"; 
			}
			
			// token을 정상적으로 가지고 왔는지 확인 
			if(StringUtils.isEmpty(tokenId)) {
				if(this.logger.isErrorEnabled()) { 
					this.logger.error("tokenId없이 토큰 등록 요청. receiverId=" + pId + ", platformType=" + platformType);
				} 
				
				// 기본 토큰 ID 설정 
				// 앱에서는 토큰이 없는 상황이면, NOT_ALLOWED_DEVICE_TOKEN_BY_USER를 전달함. (사용자가 push수신을 꺼둔 상태) 
				// 여기를 타는 경우는 웹 화면에서 접근하는 경우일 것임 
				tokenId = "TEST_TOKEN"; 
			}
			
			// 2016.1.27. certType이 새롭게 추가되었음. 
			// 로그인 시에 따라 들어와야 하는데, 사용자들이 update를 하는 동안에는 값이 없는 경우 기본값 처리를 하기로 한다. 
			// 에러메시지를 수정할까도 생각했는데 그러면 정말 토큰등록에 실패하는 경우에 대한 처리가 안되기 때문에... 

			
			
			// certType이 정상적으로 들어왔는지 확인 
			if(StringUtils.isEmpty(certType)) {
				// 인증서 타입이 없으면 에러 전달해야함 
				return false; 
			}
		
			// 수신자 
			map.put("receiverId", pId); 
			// 인증서유형 
			map.put("certType", certType); 
			// Android/iOS 여부 
			//iOS의 경우 platformType이 i소문자이고 이후 구분하는 부분에서는 대문자로 사용하여 강제로 UpperCase로 변환 
			map.put("platformType", platformType.toUpperCase()); 
			// 등록/갱신할 토큰 
			map.put("deviceTokenId", tokenId); 
						
			Map<String, Object> resultMap = (Map<String, Object>) this.apiCallService.execute(PnuhApi.USER_TOKENS_UPDATETOKENDATE, map);
			
			// 비정상적인 경우임 
			if(resultMap == null || resultMap.isEmpty()) {
				this.logger.error("토큰기반 로그인 시간 갱신실패. param : " + map.toString());
				return false; 
			}

			final long userCnt = ConvertUtil.convertInteger(resultMap.get(FrameworkConstants.UIRESPONSE_EMPTY_RESULT_KEY)); 
			if(userCnt >= 1) { 
				this.logger.debug("기존에 토큰등록된 사용자. pId=" + pId + ", platformType=" + platformType);
			}
			else {
				// 등록이 안되어 있으면 저장 
				this.apiCallService.execute(PnuhApi.USER_TOKEN_SAVEDEVICETOKEN, map); 
			}
		}
		catch(final Exception ex) { 
			this.logger.error("token 갱신 실패 : " + ex.toString()); 
			// 실패하더라도 계속 진행함 
			return false; 
		}
				
		return true; 
	}
}
