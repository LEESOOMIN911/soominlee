package com.dbs.mcare.controller.mobile;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.HandlerMapping;

import com.dbs.mcare.exception.mobile.ApiCallException;
import com.dbs.mcare.exception.mobile.MobileControllerException;
import com.dbs.mcare.framework.FrameworkConstants;
import com.dbs.mcare.framework.service.MessageService;
import com.dbs.mcare.framework.util.ConvertUtil;
import com.dbs.mcare.service.api.MCareApiCallService;
import com.dbs.mcare.service.api.PnuhApi;
/**
 * 버전확인 
 * @author aple
 *
 */
@Controller
@RequestMapping("/mobile/version")
public class VersionCheckController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private MCareApiCallService apiCallService; 	
	@Autowired
	private MessageService messageService;	
	

	/**
	 * 앱이름, 플랫폼, 인증서를 조합해서 버전확인 
	 * @param request
	 * @param response
	 * @param paramMap
	 * @return
	 * @throws MobileControllerException
	 */
	@RequestMapping(value = "/appVersion.json", method = RequestMethod.POST) 
	@ResponseBody
	public Map<String, Object> checkAppVersion(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Map<String, Object> paramMap) throws MobileControllerException {

		if(this.logger.isDebugEnabled()) { 
			this.logger.debug("request : " + request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE));
		}
		
		Map<String, Object> map = null; 
		
		
		try { 
			// 이 메소드는 UI가 아닌 device에서 호출됨 
			map = this.apiCallService.execute(PnuhApi.APP_CHECKAPPNAMEVERSION, paramMap).getResultAsMap(); 
		}
		catch(final ApiCallException ex) { 
			// 결과가 2개 조회되는 경우 CastException이 발생할것임 
			if(this.logger.isErrorEnabled()) {
				this.logger.error("앱버전확인 API 확인필요", ex);
			}
			
			return this.getVersionCheckError(request); 
		}

		// 결과가 없는 경우에도 에러러 전달해야 함 
		if(map == null || map.isEmpty()) { 
			if(this.logger.isErrorEnabled()) {
				this.logger.error("서버에 앱 버전이 등록되어 있지 않음. 관리자 콘솔에서 등록해주세요 : " + ConvertUtil.convertStringForDebug(paramMap));
			}
			return this.getVersionCheckError(request); 
		}
		
		// 앱이 보내온 hash값 확인 
		final String appHashValue = (String) paramMap.get("appHashValue"); 
		
		// 존재하는 경우에만 동일한지를 검사 
		// hash값은 Android만 현재는 해당되고 (iOS는 아직 검토중) appHashValue가 적용되지 않은 버전들도 있기 때문임 
		// Android 프레이무어크 2.2.22에 추가된 기능임 
		if(!StringUtils.isEmpty(appHashValue)) {
			final String savedAppHashValue = (String) map.get("appHashValue"); 
			
			if(this.logger.isDebugEnabled()) {
				StringBuilder builder = new StringBuilder(FrameworkConstants.NEW_LINE); 
				
				builder.append("- app : ").append(appHashValue).append(FrameworkConstants.NEW_LINE); 
				builder.append("- DB :  ").append(savedAppHashValue).append(FrameworkConstants.NEW_LINE); 
				
				this.logger.debug(builder.toString());
			}
			
			if(!appHashValue.equals(savedAppHashValue)) {
				return this.getHashCheckError(request); 
			}
		}
	
		return map; 
	}
	
	/**
	 * 버전오류 에러 메시지 만들기 
	 * @param request
	 * @return
	 */
	private Map<String, Object> getVersionCheckError(HttpServletRequest request) {
		final Map<String, Object> map = new HashMap<String, Object>(); 
		map.put("error", this.messageService.getMessage("mcare.error.no.version", request)); 
		return map; 
	}
	/**
	 * 해쉬오류 에러 메시지 만들기 
	 * @param request
	 * @return
	 */
	private Map<String, Object> getHashCheckError(HttpServletRequest request) { 
		final Map<String, Object> map = new HashMap<String, Object>(); 
		map.put("error", this.messageService.getMessage("mcare.error.invalid.hash", request)); 
		return map; 		
	}
}
