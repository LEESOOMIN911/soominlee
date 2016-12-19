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
import com.dbs.mcare.framework.util.NameComparator;
import com.dbs.mcare.framework.util.ResponseUtil;
import com.dbs.mcare.service.api.MCareApiCallService;
import com.dbs.mcare.service.api.PnuhApi;
import com.dbs.mcare.service.api.reservation.ReservationService;
/**
 * 진료예약 
 * @author hyeeun 
 *
 */
@Controller
@RequestMapping("/mobile/reservation")
@ServiceMenu(menuId="reservation", description="진료예약")
public class ReservationController extends AbstractController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired 
	private ReservationService reservationService; 
	@Autowired
	private MCareApiCallService apiCallService; 
		
	/**
	 * 예약가능한 진료과 
	 * @param request
	 * @param response
	 * @param reqMap
	 * @return
	 * @throws MobileControllerException
	 */
	@RequestMapping(value = "/getDepartment.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getDepartment(HttpServletRequest request, HttpServletResponse response, 
			@RequestBody Map<String, Object> reqMap) throws MobileControllerException {
		
		// 접근권한 및 파라미터 정리 
		try { 
			reqMap = super.isAccessableMenu(this.getClass(), request, reqMap); 
		}
		catch(MCareServiceException ex) { 
			throw new MobileControllerException(ex.getCode(), ex.getMessage()); 
		} 
		
		
		// 기능호출 
		Map<String, Object> resMap = null; 
				
		try { 
			resMap = this.apiCallService.call(PnuhApi.RESERVATION_GETREVDEPT, reqMap);
		}
		catch(ApiCallException ex) { 
			if(this.logger.isDebugEnabled()) {
				this.logger.debug("예외발생", ex); 
			}
					
			throw new MobileControllerException("mcare.error.if.system", "시스템 호출 오류가 발생했습니다 (IF)");
		}
				
		// 결과 update 
		return ResponseUtil.updateSortedResultMap(resMap, new NameComparator("departmentNm"));  
	}	
	
	/**
	 * 진료과에 의사 
	 * @param request
	 * @param response
	 * @param reqMap
	 * @return
	 * @throws MobileControllerException
	 */
	@RequestMapping(value = "/getDoctor.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getDoctor(HttpServletRequest request, HttpServletResponse response, 
			@RequestBody Map<String, Object> reqMap) throws MobileControllerException {
		
		// 접근권한 및 파라미터 정리 
		try { 
			reqMap = super.isAccessableMenu(this.getClass(), request, reqMap); 
		}
		catch(MCareServiceException ex) { 
			throw new MobileControllerException(ex.getCode(), ex.getMessage()); 
		} 
		
		// 기능호출 
		Map<String, Object> resMap = null; 
		
		try { 
			resMap = this.apiCallService.call(PnuhApi.RESERVATION_GETREVDOC, reqMap); 
		}
		catch(ApiCallException ex) { 
			if(this.logger.isDebugEnabled()) {
				this.logger.debug("예외발생", ex); 
			}
			
			throw new MobileControllerException("mcare.error.if.system", "시스템 호출 오류가 발생했습니다 (IF)");
		} 
		
		// 결과반환 
		return ResponseUtil.updateSortedResultMap(resMap, new NameComparator("doctorNm")); 
	}	
	
	/**
	 * 의사의 예약가능한 날짜 
	 * @param request
	 * @param response
	 * @param reqMap
	 * @return
	 * @throws MobileControllerException
	 */
	@RequestMapping(value = "/getDoctorDate.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getDoctorDate(HttpServletRequest request, HttpServletResponse response, 
			@RequestBody Map<String, Object> reqMap) throws MobileControllerException {
		
		// 접근권한 및 파라미터 정리 
		try { 
			reqMap = super.isAccessableMenu(this.getClass(), request, reqMap); 
		}
		catch(MCareServiceException ex) { 
			throw new MobileControllerException(ex.getCode(), ex.getMessage()); 
		} 
		
		
		// 기능호출 
		Map<String, Object> resMap = null; 
		
		try { 
			resMap = this.apiCallService.call(PnuhApi.RESERVATION_GETREVDATE, reqMap); 
		}
		catch(ApiCallException ex) { 
			if(this.logger.isDebugEnabled()) {
				this.logger.debug("예외발생", ex); 
			}
			
			throw new MobileControllerException("mcare.error.if.system", "시스템 호출 오류가 발생했습니다 (IF)");
		}
		
		// 결과반환 
		return ResponseUtil.updateSortedResultMap(resMap, new NameComparator("date")); 
	}
	
	/**
	 * 의사의 예약가능한 날짜에 대한 예약가능한 시간 
	 * @param request
	 * @param response
	 * @param reqMap
	 * @return
	 * @throws MobileControllerException
	 */
	@RequestMapping(value = "/getDoctorDateTime.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getDoctorDateTime(HttpServletRequest request, HttpServletResponse response, 
			@RequestBody Map<String, Object> reqMap) throws MobileControllerException {
		
		// 접근권한 및 파라미터 정리 
		try { 
			reqMap = super.isAccessableMenu(this.getClass(), request, reqMap); 
		}
		catch(MCareServiceException ex) { 
			throw new MobileControllerException(ex.getCode(), ex.getMessage()); 
		} 
		
		
		Map<String, Object> resMap = null;
		
		try { 
			resMap = this.apiCallService.call(PnuhApi.RESERVATION_GATEREVTIME, reqMap); 
		}
		catch(ApiCallException ex) { 
			if(this.logger.isDebugEnabled()) {
				this.logger.debug("예외발생", ex); 
			}
			
			throw new MobileControllerException("mcare.error.if.system", "시스템 호출 오류가 발생했습니다 (IF)");
		}
		
		// 결과반환 
		return ResponseUtil.updateSortedResultMap(resMap, new NameComparator("time")); 
	}	
	/**
	 * 예약 - 진료 예약
	 * @param request
	 * @param response
	 * @param reqMap
	 * @return
	 * @throws MobileControllerException
	 */
	@RequestMapping(value = "/reservation.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> reservation(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Map<String, Object> reqMap ) throws MobileControllerException{

		// 접근권한 및 파라미터 정리 
		try { 
			reqMap = super.isAccessableMenu(this.getClass(), request, reqMap); 
		}
		catch(MCareServiceException ex) { 
			throw new MobileControllerException(ex.getCode(), ex.getMessage()); 
		} 
		
		// 호출 
		return this.reservationService.reservation(request, response, reqMap);
	}
}
