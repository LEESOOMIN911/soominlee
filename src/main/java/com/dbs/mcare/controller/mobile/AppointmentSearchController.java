package com.dbs.mcare.controller.mobile;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dbs.mcare.exception.mobile.MobileControllerException;
import com.dbs.mcare.framework.exception.MCareServiceException;
import com.dbs.mcare.framework.service.menu.ServiceMenu;
import com.dbs.mcare.framework.template.AbstractController;
import com.dbs.mcare.framework.util.ResponseUtil;
import com.dbs.mcare.service.api.appointment.AppointmentService;
/**
 * 예약조회 
 * @author aple
 *
 */
@Controller
@RequestMapping("/mobile/appointmentSearch")
@ServiceMenu(menuId="appointmentSearch", description="예약조회")
public class AppointmentSearchController  extends AbstractController {
	@Autowired 
	private AppointmentService appointmentService; 

	// 예약목록조회 
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
		
		// 예약조회 
		// 도우미에서 사용될 수 있어서 서비스로 분리된 케이스임 
		//2016-05-25 서영일 result로 담아서 보내도록 함
		return ResponseUtil.wrapResultMap(this.appointmentService.getList(reqMap));
	}
	
	
	// 인터넷 예약 취소 
	@RequestMapping(value = "/cancel.json", method = RequestMethod.POST)
	public Map<String, Object> cancel(HttpServletRequest request, HttpServletResponse response, 
			@RequestBody Map<String, Object> reqMap) throws MobileControllerException {
		
		// 접근권한 및 파라미터 정리 
		try { 
			reqMap = super.isAccessableMenu(this.getClass(), request, reqMap); 
		}
		catch(MCareServiceException ex) { 
			throw new MobileControllerException(ex.getCode(), ex.getMessage()); 
		}

		return this.appointmentService.cancel(reqMap); 
	}
}
