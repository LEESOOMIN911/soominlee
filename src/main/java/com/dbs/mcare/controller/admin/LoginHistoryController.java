package com.dbs.mcare.controller.admin;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dbs.mcare.exception.admin.AdminControllerException;
import com.dbs.mcare.framework.exception.MCareServiceException;
import com.dbs.mcare.framework.service.admin.history.LoginHistoryService;

/**
 * 로그인 히스토리 내용 보내주기 
 * @author DBS 
 *
 */
@Controller
@RequestMapping("/admin/loginhistory")
public class LoginHistoryController {
	private static final Logger logger = LoggerFactory.getLogger(AggregationController.class); 
	// 시작날짜. 테이블 컬럼과 맵핑되지 않는, UI와 약속된 검색범위 
	private static final String KEY_START_DATE = "strDate"; 
	// 종료날짜 
	private static final String KEY_END_DATE = "endDate"; 
	//검색 아이디
	private static final String KEY_USER_ID = "userId";
	@Autowired 
	private LoginHistoryService loginHistoryService; 
	
	
	// 로그인 정보 
	@RequestMapping(value = "/getLoginList.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> findByDate( @RequestBody Map<String, Object> map ) throws AdminControllerException {
		final String strDate = map.get(LoginHistoryController.KEY_START_DATE).toString();
		final String endDate = map.get(LoginHistoryController.KEY_END_DATE).toString();
		final String userId = map.get(LoginHistoryController.KEY_USER_ID).toString();
		
		try { 
			return this.loginHistoryService.findByDate(strDate, endDate, userId, map); 
		}
		catch(final MCareServiceException ex) {
			logger.error("로그인 이력 가져오기 실패. strDate : " + strDate + ", endDate : " + endDate, ex);
			throw new AdminControllerException(ex); 
		}
	}
}
