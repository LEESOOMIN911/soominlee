package com.dbs.mcare.controller.mobile;

import java.util.Collections;
import java.util.HashMap;
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

import com.dbs.mcare.exception.mobile.MobileControllerException;
import com.dbs.mcare.framework.exception.MCareServiceException;
import com.dbs.mcare.framework.service.menu.ServiceMenu;
import com.dbs.mcare.framework.template.AbstractController;
import com.dbs.mcare.service.api.MCareApiCallService;
import com.dbs.mcare.service.api.PnuhApi;
/**
 * 검사결과조회 
 * 
 * @author hyeeun 
 *
 */
@Controller
@RequestMapping("/mobile/resultExam")
@ServiceMenu(menuId="resultExam", description="검사결과")
public class ResultExamController extends AbstractController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired 
	private MCareApiCallService apiCallService; 

	/**
	 * 검사결과 목록 
	 * @param request
	 * @param response
	 * @param reqMap
	 * @return
	 * @throws MobileControllerException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/search.json", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String, Object>> getExamList(HttpServletRequest request, HttpServletResponse response, 
			@RequestBody Map<String, Object> reqMap) throws MobileControllerException {

		// 샘플 환자번호 : 150346410
		// 샘플 시작일 : 20150902
		// 샘플 종료일 : 20150902 
		
		// 접근권한 및 파라미터 정리 
		try { 
			reqMap = super.isAccessableMenu(this.getClass(), request, reqMap); 
		}
		catch(MCareServiceException ex) { 
			throw new MobileControllerException(ex.getCode(), ex.getMessage()); 
		} 
			
		List<Map<String, Object>> resultMapList = null; 
		
		// 대분류 호출 
		resultMapList = (List<Map<String, Object>>) this.apiCallService.call(PnuhApi.TOTALEXAM_GETRESULTTOTAL, reqMap); 
		// 결과확인  
		if(resultMapList == null || resultMapList.size() == 0) { 
			this.logger.debug("결과없음 : " + reqMap);
			return Collections.EMPTY_LIST;
		}
		
		final StringBuilder builder = new StringBuilder(); 
		int cnt = resultMapList.size(); 
		
		for(final Map<String, Object> map : resultMapList) {
			builder.append(map.get("prescriptionNo")); 
			cnt--; 
			if(cnt > 1) { 
				builder.append(", "); 
			}
		}
		
		// 상세정보 호출 
		final Map<String, Object> paramMap = new HashMap<String, Object>(); 
		paramMap.put("prescriptionNo", builder.toString()); 
		
		resultMapList = (List<Map<String, Object>>) this.apiCallService.call(PnuhApi.TOTALEXAM_GETRESULTDETAIL, paramMap); 

	
		// 결과전달 
		return resultMapList; 		
	}
}
