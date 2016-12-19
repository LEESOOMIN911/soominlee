package com.dbs.mcare.controller.mobile;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dbs.mcare.exception.mobile.ApiCallException;
import com.dbs.mcare.exception.mobile.MobileControllerException;
import com.dbs.mcare.framework.FrameworkConstants;
import com.dbs.mcare.framework.exception.MCareServiceException;
import com.dbs.mcare.framework.exception.service.TimeLimitException;
import com.dbs.mcare.framework.service.menu.ServiceMenu;
import com.dbs.mcare.framework.template.AbstractController;
import com.dbs.mcare.service.PnuhConfigureService;
import com.dbs.mcare.service.api.MCareApiCallService;
import com.dbs.mcare.service.api.PnuhApi;
import com.dbs.mcare.service.api.util.ParamMappingUtil;

/**
 * 번호표 
 * @author hyeeun 
 *
 */
@Controller
@RequestMapping("/mobile/ticket")
@ServiceMenu(menuId="ticket", description="번호표발급")
public class TicketController  extends AbstractController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private MCareApiCallService apiCallService; 
	@Autowired 
	private PnuhConfigureService configureService; 
	
	/**
	 * 순번기 목록 
	 * @param request
	 * @param response
	 * @param reqMap
	 * @return
	 * @throws MobileControllerException
	 */
	@RequestMapping(value = "/getList.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getList(HttpServletRequest request, HttpServletResponse response, 
			@RequestBody Map<String, Object> reqMap) throws MobileControllerException {
		
		// 접근권한 및 파라미터 정리 
		try { 
			reqMap = super.isAccessableMenu(this.getClass(), request, reqMap); 
		}
		catch(MCareServiceException ex) { 
			throw new MobileControllerException(ex.getCode(), ex.getMessage()); 
		} 
		
		try { 
			return this.apiCallService.call(PnuhApi.TICKET_LIST, reqMap); 
		}
		catch(ApiCallException ex) { 
			if(this.logger.isDebugEnabled()) {
				this.logger.debug("예외발생", ex); 
			}
			
			throw new MobileControllerException("mcare.error.if.system", "시스템 호출 오류가 발생했습니다 (IF)");
		}		
	}
	/**
	 * 순번기의 대상 창구 
	 * @param request
	 * @param response
	 * @param reqMap
	 * @return
	 * @throws MobileControllerException
	 */
	@RequestMapping(value = "/getDeskList.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getDeskList(HttpServletRequest request, HttpServletResponse response, 
			@RequestBody Map<String, Object> reqMap) throws MobileControllerException {
		
		// 접근권한 및 파라미터 정리 
		try { 
			reqMap = super.isAccessableMenu(this.getClass(), request, reqMap); 
		}
		catch(MCareServiceException ex) { 
			throw new MobileControllerException(ex.getCode(), ex.getMessage()); 
		} 
		
		
		try { 
			// 결과반환 
			return this.apiCallService.call(PnuhApi.TICKET_DESKLIST, reqMap); 
		}
		catch(ApiCallException ex) { 
			if(this.logger.isDebugEnabled()) {
				this.logger.debug("예외발생", ex); 
			}
			
			throw new MobileControllerException("mcare.error.if.system", "시스템 호출 오류가 발생했습니다 (IF)");
		}		
	}
	/**
	 * 티켓 재발급 가능 여부
	 * @param request
	 * @param response
	 * @param reqMap
	 * @return
	 * @throws MobileControllerException, TimeLimitException
	 */
	@RequestMapping(value = "/isValidTicket.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> isValidTicket(HttpServletRequest request, HttpServletResponse response, 
			@RequestBody Map<String, Object> reqMap) throws MobileControllerException, TimeLimitException {
		
		// 접근권한 및 파라미터 정리 
		try { 
			reqMap = super.isAccessableMenu(this.getClass(), request, reqMap); 
		}
		catch(MCareServiceException ex) { 
			throw new MobileControllerException(ex.getCode(), ex.getMessage()); 
		} 
		
		// 발급시간대인가? 
		if(!super.checkTimeValidation(this.configureService.getTicketIssueStartTime(), this.configureService.getTicketIssueEndTime())) {
			throw new TimeLimitException("mcare.service.ticket.issue.time", "발급은 진료시간 중에만 가능합니다", FrameworkConstants.URI_SPECIAL_PAGE.INDEX);  
		}	
		
		
		Map<String, Object> resultMap = null;
		resultMap = (Map<String, Object>) this.apiCallService.call(PnuhApi.TICKET_BALRECALL, reqMap); 
		
		// 결과정리 
			
		// 결과반환 
		return resultMap; 
	}
	/**
	 * 티켓 발급
	 * @param request
	 * @param response
	 * @param reqMap
	 * @return
	 * @throws MobileControllerException, TimeLimitException
	 */
	@RequestMapping(value = "/issueTicket.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> issueTicket(HttpServletRequest request, HttpServletResponse response, 
			@RequestBody Map<String, Object> reqMap) throws MobileControllerException, TimeLimitException {
		
		// 접근권한 및 파라미터 정리 
		try { 
			reqMap = super.isAccessableMenu(this.getClass(), request, reqMap); 
		}
		catch(MCareServiceException ex) { 
			throw new MobileControllerException(ex.getCode(), ex.getMessage()); 
		} 
		
		// 발급시간대인가? 
		if(!super.checkTimeValidation(this.configureService.getTicketIssueStartTime(), this.configureService.getTicketIssueEndTime())) {
			throw new TimeLimitException("mcare.service.ticket.issue.time", "발급은 진료시간 중에만 가능합니다", FrameworkConstants.URI_SPECIAL_PAGE.INDEX);  
		}	
				
		
		// 파라미터 추가 
		reqMap.put("in_pid", reqMap.get("pId")); 
		
		Map<String, Object> resultMap = null;
		resultMap = (Map<String, Object>) this.apiCallService.call(PnuhApi.TICKET_BAL, reqMap); 
		
		// 결과정리 
			
		// 결과반환 
		return resultMap; 
	}
	/**
	 * 발급받은 티켓 확인 
	 * @param request
	 * @param response
	 * @param reqMap
	 * @return
	 * @throws MobileControllerException, TimeLimitException 
	 */
	@RequestMapping(value = "/myTicket.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> myTicketList(HttpServletRequest request, HttpServletResponse response, 
			@RequestBody Map<String, Object> reqMap) throws MobileControllerException, TimeLimitException {
		
		// 접근권한 및 파라미터 정리 
		try { 
			reqMap = super.isAccessableMenu(this.getClass(), request, reqMap); 
		}
		catch(MCareServiceException ex) { 
			throw new MobileControllerException(ex.getCode(), ex.getMessage()); 
		} 
		
		
		// 발급시간대인가? 
		if(!super.checkTimeValidation(this.configureService.getTicketIssueStartTime(), this.configureService.getTicketIssueEndTime())) {
			throw new TimeLimitException("mcare.service.ticket.issue.time", "발급은 진료시간 중에만 가능합니다", FrameworkConstants.URI_SPECIAL_PAGE.INDEX);  
		}	
			
		
		// 파라미터 정리 
		reqMap.put("in_pid", reqMap.get("pId")); 
		
		return this.apiCallService.call(PnuhApi.TICKET_BALLIST, reqMap); 
	}
	
	/**
	 * 발급 완료 여부 확인 
	 * @param request
	 * @param response
	 * @return
	 * @throws MobileControllerException, TimeLimitException
	 */
	@RequestMapping(value = "/checkIssue.json", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> checkIssue(HttpServletRequest request, HttpServletResponse response) 
			throws MobileControllerException, TimeLimitException {
		
		// 접근권한 및 파라미터 정리 
		try { 
			super.isAccessableMenu(this.getClass(), request); 
		}
		catch(MCareServiceException ex) { 
			throw new MobileControllerException(ex.getCode(), ex.getMessage()); 
		} 
		
		
		// 발급시간대인가? 
		if(!super.checkTimeValidation(this.configureService.getTicketIssueStartTime(), this.configureService.getTicketIssueEndTime())) {
			throw new TimeLimitException("mcare.service.ticket.issue.time", "발급은 진료시간 중에만 가능합니다", FrameworkConstants.URI_SPECIAL_PAGE.INDEX);  
		}	
		
		
		Map<String, Object> reqMap = new HashMap<String,Object>();
		// 파라미터 정리 
		reqMap = ParamMappingUtil.requestParam(request, reqMap); 
		reqMap.put("in_pid", reqMap.get("pId")); 
		
		return this.apiCallService.call(PnuhApi.TICKET_BALSUCCESS, reqMap); 
	}
}
