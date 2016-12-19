package com.dbs.mcare.controller.mobile;

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
import com.dbs.mcare.framework.exception.MCareServiceException;
import com.dbs.mcare.framework.service.menu.ServiceMenu;
import com.dbs.mcare.framework.template.AbstractController;
import com.dbs.mcare.service.api.MCareApiCallService;
import com.dbs.mcare.service.api.PnuhApi;
/**
 * 수진이력 
 * 
 * @author heesung, hyeeun  
 *
 */
@Controller
@RequestMapping("/mobile/history")
@ServiceMenu(menuId="treatmentHistory", description="진료받은내역")
public class HistoryController extends AbstractController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired 
	private MCareApiCallService apiCallService; 
	
	
	/**
     * 진료이력조회 - 외래 
     * 
     * @param model
     * @return
     */
	@RequestMapping(value = "/getOutList.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getOutList(HttpServletRequest request, HttpServletResponse response, 
			@RequestBody Map<String, Object> reqMap) throws MobileControllerException {
		
		// 접근권한 및 파라미터 정리 
		try { 
			reqMap = super.isAccessableMenu(this.getClass(), request, reqMap); 
		}
		catch(MCareServiceException ex) { 
			throw new MobileControllerException(ex.getCode(), ex.getMessage()); 
		}	
		
		try { 
			// http api 호출 
			return this.apiCallService.call(PnuhApi.HISTORY_GETOUTLIST, reqMap); 
		}
		catch(ApiCallException ex) { 
			if(this.logger.isDebugEnabled()) {
				this.logger.debug("예외발생", ex); 
			}
			
			throw new MobileControllerException("mcare.error.if.system", "시스템 호출 오류가 발생했습니다 (IF)");
		}
	}
	
	/**
     * 진료이력조회 - 입원  
     * 
     * @param model
     * @return
     */
	@RequestMapping(value = "/getInList.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getInList(HttpServletRequest request, HttpServletResponse response, 
			@RequestBody Map<String, Object> reqMap) throws MobileControllerException {
		
		// 접근권한 및 파라미터 정리 
		try { 
			reqMap = super.isAccessableMenu(this.getClass(), request, reqMap); 
		}
		catch(MCareServiceException ex) { 
			throw new MobileControllerException(ex.getCode(), ex.getMessage()); 
		}
		
		// http api 호출 
		try { 
			return this.apiCallService.call(PnuhApi.HISTORY_GETINLIST, reqMap);  
		}
		catch(ApiCallException ex) { 
			if(this.logger.isDebugEnabled()) {
				this.logger.debug("예외발생", ex); 
			}
			
			throw new MobileControllerException("mcare.error.if.system", "시스템 호출 오류가 발생했습니다 (IF)");
		}		
	}	
	
	
	/**
     * 진료이력조회 - 진료비 합계 
     * 
     * @param model
     * @return
     */
	@RequestMapping(value = "/getBillSum.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getBillSum(HttpServletRequest request, HttpServletResponse response, 
			@RequestBody Map<String, Object> reqMap) throws MobileControllerException {
		
		// 접근권한 및 파라미터 정리 
		try { 
			reqMap = super.isAccessableMenu(this.getClass(), request, reqMap); 
		}
		catch(MCareServiceException ex) { 
			throw new MobileControllerException(ex.getCode(), ex.getMessage()); 
		}
		
		try { 
			// http api 호출 
			return this.apiCallService.call(PnuhApi.HISTORY_GETBILLSUM, reqMap);  	
		}
		catch(ApiCallException ex) { 
			if(this.logger.isDebugEnabled()) {
				this.logger.debug("예외발생", ex); 
			}
			
			throw new MobileControllerException("mcare.error.if.system", "시스템 호출 오류가 발생했습니다 (IF)");
		}		
	}	
	
	/**
     * 진료이력조회 - 진료비 상세 
     * 
     * @param model
     * @return
     */
	@RequestMapping(value = "/getBillDetail.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getBillDetail(HttpServletRequest request, HttpServletResponse response, 
			@RequestBody Map<String, Object> reqMap) throws MobileControllerException {
		
		// 접근권한 및 파라미터 정리 
		try { 
			reqMap = super.isAccessableMenu(this.getClass(), request, reqMap); 
		}
		catch(MCareServiceException ex) { 
			throw new MobileControllerException(ex.getCode(), ex.getMessage()); 
		}
		
		// http api 호출 
		try { 
			return this.apiCallService.call(PnuhApi.HISTORY_GETBILLDETAIL, reqMap); 
		}
		catch(ApiCallException ex) { 
			if(this.logger.isDebugEnabled()) {
				this.logger.debug("예외발생", ex); 
			}
			
			throw new MobileControllerException("mcare.error.if.system", "시스템 호출 오류가 발생했습니다 (IF)");
		}	
	}	
}
