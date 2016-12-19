package com.dbs.mcare.controller.mobile;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.HandlerMapping;

import com.dbs.mcare.exception.mobile.ApiCallException;
import com.dbs.mcare.exception.mobile.MobileControllerException;
import com.dbs.mcare.framework.FrameworkConstants;
import com.dbs.mcare.framework.service.admin.history.repository.dao.EventLog;
import com.dbs.mcare.framework.service.log.LogService;
import com.dbs.mcare.framework.service.log.type.LogType;
import com.dbs.mcare.framework.util.HttpRequestUtil;
import com.dbs.mcare.service.PnuhConfigureService;
import com.dbs.mcare.service.api.MCareApiCallService;
import com.dbs.mcare.service.api.PnuhApi;
import com.dbs.mcare.service.api.util.ParamMappingUtil;
import com.dbs.mcare.service.mobile.helper.MCareHelperService;
import com.dbs.mcare.service.mobile.helper.TriggerEvent;

/**
 * device에서 발생되는 메시지를 처리하기 위한 컨트롤러 
 * 
 * 
 * @author hyeeun 
 *
 */
@Controller
@RequestMapping("/mobile/device")
public class DeviceEventController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private MCareApiCallService apiCallService;
	@Autowired 
	private MCareHelperService helperService; 
	@Autowired 
	private LogService logService; 
	@Autowired 
	private PnuhConfigureService configureService; 
	
	/**
	 * 웰컴 수신 시.. 내용을 처리하고 도우미 페이지를 보여줘야 하지 않을까?  
	 * 그럼 굳이 push를 여러개 보내지 않아도 될것 같은데 
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/welcome.json", method = RequestMethod.GET)
	public void welcome(HttpServletRequest request, HttpServletResponse response, Map<String, Object> reqMap) throws MobileControllerException {

		if(this.logger.isDebugEnabled()) { 
			this.logger.debug("request : " + request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE));
		}
		
		// 파라미터 정리 
		reqMap = ParamMappingUtil.requestParam(request, reqMap); 	
		// 로그처리 
		this.saveEventLog(request, reqMap);		
		

		// 리턴값 없음. 앱이 비콘 이벤트를 받으면 notification bar에 메시지를 올리고, 
		// 그걸 누르면 로그인 후 웹을 통해 여기에 들어옴.. 
		// 로그인이 필요한 주소에 갔는데 로그인이 안되어 있는 경우, 로그인 후 그 페이지로 가는 기능은 아직 없는데....
		String targetPage = null; 
		final String pId = (String) reqMap.get("pId"); 
		String serverAddr = this.configureService.getServerServiceAddr(); 
		
		
		// 환자번호가 안 따라온 경우는 로그인을 시킨다음 이후를 처리해야 하기 때문에 해당 페이지로 전이시켜줌 
		if(StringUtils.isEmpty(pId)) {
			String requestUri = HttpRequestUtil.getRequestURIExcludeContextPath(request);
			// 주소바꿔치기하고 
			requestUri = requestUri.replaceFirst("welcome", "welcomeHelper"); 
			// 세션이 없어서 해줄 수 있는게 없음 
			targetPage = HttpRequestUtil.generateRedirectUrl(request, serverAddr, FrameworkConstants.URI_SPECIAL_PAGE.USER_LOGIN.getPage(), requestUri, ""); 			
		}
		else {
			// 도우미 처리 
			this.processUser(request, reqMap);
			// 도우미 페이지 가져오기  
			targetPage = HttpRequestUtil.generateRedirectUrl(request, serverAddr, FrameworkConstants.URI_SPECIAL_PAGE.HELPER.getPage()  + "?" + FrameworkConstants.MENU_ID + "=" + FrameworkConstants.URI_SPECIAL_PAGE.HELPER.getMenuId()); 
			
		}

		// 페이지 전이 
		try { 
			response.sendRedirect(targetPage);
		} 
		catch(final IOException ex) { 
			this.logger.error("redirect 실패", ex);
			throw new MobileControllerException("mcare.error.500", ex.getMessage()); 
		}
	}
	
	/**
	 * 도우미 처리 
	 * welcome.json이 메인인데 로그인하지 않은 경우, 로그인 후 redirect되기 위한 입구임 
	 * @param request
	 * @param response
	 * @param reqMap
	 * @throws MobileControllerException
	 */
	@RequestMapping(value = "/welcomeHelper.json", method = RequestMethod.GET)
	public void welcomeHelper(HttpServletRequest request, HttpServletResponse response, Map<String, Object> reqMap) throws MobileControllerException {
		if(this.logger.isDebugEnabled()) { 
			this.logger.debug("request : " + request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE));
		}
		
		// 요청정리 
		reqMap = ParamMappingUtil.requestParam(request, reqMap); 
		
		// 요청 처리하고 
		this.processUser(request, reqMap);
		
		try { 
			String serverAddr = this.configureService.getServerServiceAddr(); 
			// 도우미 페이지 가져오기  
			String targetPage = HttpRequestUtil.generateRedirectUrl(request, serverAddr, FrameworkConstants.URI_SPECIAL_PAGE.HELPER.getPage()  + "?" + FrameworkConstants.MENU_ID + "=" + FrameworkConstants.URI_SPECIAL_PAGE.HELPER.getMenuId()); 
			// 도우미로 페이지 전이 
			response.sendRedirect(targetPage);
		} 
		catch(final IOException ex) { 
			this.logger.error("redirect 실패", ex);
			throw new MobileControllerException("mcare.error.500", ex.getMessage()); 
		}		
	}
			
	
	@RequestMapping(value = "/reqAction.json", method = RequestMethod.GET)
	@ResponseBody 
	public Map<String, Object> reqAction(HttpServletRequest request, HttpServletResponse response, Map<String, Object> reqMap) throws MobileControllerException {
		
		if(this.logger.isDebugEnabled()) { 
			this.logger.debug("request : " + request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE));
		}
		// Action이 들어있는 메시지가 반환됨 
		return (Map<String, Object>) this.apiCallService.call(PnuhApi.HELPERPUSH_GETACTION, reqMap); 
	}
	
	/**
	 * 비콘 로그 남기기 
	 * @param request
	 * @param reqMap
	 */
	private void saveEventLog(HttpServletRequest request, Map<String, Object> reqMap) {
		// 로그용 객체 만들고 
		final EventLog log = new EventLog(); 
		log.setpId((String) reqMap.get("pId"));

		// uuid 
		String deviceId = HttpRequestUtil.getUUID(request); 
		if(StringUtils.isEmpty(deviceId)) {
			deviceId = HttpRequestUtil.getClientIpAddr(request);
		}
		//log.setDeviceUuidId(deviceId);
		// eventName 
		log.setEventName((String) reqMap.get("event"));
		

		this.logService.addLog(LogType.BEACON_EVENT, log);		
	}
	
	/**
	 * 사용자 요청 처리 
	 * @param request
	 * @param reqMap 정리된 요청 
	 * @throws MobileControllerException
	 */
	private void processUser(HttpServletRequest request, Map<String, Object> reqMap) throws MobileControllerException {
		String eventName = (String) reqMap.get("event"); 
		
		// 이벤트 타입 
		TriggerEvent triggerEvent = null; 
		
		// 이벤트가 없으면 웰컴으로 처리 
		// 왜냐면, 현재는 웰컴밖에 없어서 
		if(StringUtils.isEmpty(eventName)) {
			triggerEvent = TriggerEvent.WELCOME; 
		}
		else {
			triggerEvent = TriggerEvent.valueOf(eventName); 
			if(triggerEvent == null) { 
				triggerEvent = TriggerEvent.UNKNOWN; 
			}
		}
		
		// 병원코드 심기
		reqMap.put("mcareServiceKey", this.configureService.getMcareServiceCode()); 
		
		// event 유형에 따라 움직임 
		switch(triggerEvent) { 
		case WELCOME : 
			// 웰컴 메시지는 push 보냄 
			try { 
				this.apiCallService.execute(PnuhApi.HELPERPUSH_WELCOME, reqMap); 			
			}
			catch(ApiCallException ex) { 
				this.logger.error("Push전송요청실패", ex);
			}		
			// 필요한 비즈니스로직 처리 
			this.helperService.processWelcome(request);				
			break; 
			
		case GOODBYE : 
			// 필요한 비즈니스로직 처리 
			this.helperService.processGoodBye(request);
			// 잘가라는 메시지도 push로 보냄. 실패 시 따로 응답해줄게 없으니까 execute로 호출함 
			try { 
				this.apiCallService.execute(PnuhApi.HELPERPUSH_GOODBYE, reqMap); 
			}
			catch(ApiCallException ex) { 
				this.logger.error("Push전송요청실패", ex);
			}
			break; 
			
				
		case IN : 
		case OUT : 
		case UNKNOWN : 
//			// 실험용 
//			reqMap.put("messageValue", "수신이벤트 : " + triggerEvent.name());
//			reqMap.put("stageOrder", "20");
//			this.apiCallService.call(PnuhApi.TEST_SENDPUSHTEXT, reqMap); 
			break; 
		}
	}
} 