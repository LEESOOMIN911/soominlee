package com.dbs.mcare.controller.mobile;

import java.util.ArrayList;
import java.util.List;
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
import org.springframework.web.servlet.HandlerMapping;

import com.dbs.mcare.exception.mobile.MobileControllerException;
import com.dbs.mcare.framework.service.menu.ServiceMenu;
import com.dbs.mcare.framework.template.AbstractController;
import com.dbs.mcare.framework.util.ResponseUtil;
import com.dbs.mcare.service.api.announceArrival.AnnounceArrivalService;
import com.dbs.mcare.service.api.util.ParamMappingUtil;


@Controller 
@RequestMapping("/mobile/arrival")
@ServiceMenu(menuId="announceArrival", description="진료도착확인")
public class AnnounceArrivalController extends AbstractController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired // 도착확인처리 서비스 
	private AnnounceArrivalService announceArrivalService; 
	
	/**
	 * 대기시간 조회 - 도착확인 ==> 부산대병원은 오픈안한메뉴 
	 * @param request
	 * @param response
	 * @param reqMapList 도착확인 대상목록  
	 * @return
	 * @throws MobileControllerException
	 */
	@RequestMapping(value = "/arrivedConfirm.json", method = RequestMethod.POST)
	@ResponseBody	
	public Map<String, Object> requestArrivedConfirm(HttpServletRequest request, HttpServletResponse response, 
				@RequestBody List<Map<String, Object>> reqMapList) throws MobileControllerException {

		if(this.logger.isDebugEnabled()) { 
			this.logger.debug("request : " + request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE));
		}
		
		if(reqMapList == null || reqMapList.isEmpty()) {
			return ResponseUtil.wrapEmptyResultListMap(); 
		}
		
		// 요청 파라미터 정리 
		final List<Map<String, Object>> reqList = new ArrayList<Map<String, Object>>(); 
		for(final Map<String, Object> map : reqMapList) { 
			reqList.add(ParamMappingUtil.requestParam(request, map)); 
		}

		List<Map<String, Object>> resultList = this.announceArrivalService.requestArriveConfirm(request, reqList); 
		return ResponseUtil.wrapResultMap(resultList); 
	}
	
	/**
	 * 도착확인 화면에 보여지는 대상 목록  ==> 부산대병원은 오픈안한메뉴 
	 * 도착확인이 완료된 애들도 포함되어 있음 
	 * @param request
	 * @param response
	 * @param reqMap
	 * @return
	 * @throws MobileControllerException
	 */
	@RequestMapping(value = "/arrivedConfirmTargetList.json", method = RequestMethod.POST)
	@ResponseBody	
	public Map<String, Object> getArriveConfirmTargetList(HttpServletRequest request, HttpServletResponse response, 
				@RequestBody Map<String, Object> reqMap) throws MobileControllerException {

		if(this.logger.isDebugEnabled()) { 
			this.logger.debug("request : " + request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE));
		}
		
		// 요청 파라미터 만들기 
		reqMap = ParamMappingUtil.requestParam(request, reqMap); 
		
		List<Map<String, Object>> resultList = this.announceArrivalService.getArriveConfirmAllList(request, reqMap); 
		return ResponseUtil.wrapResultMap(resultList); 
	}
}
