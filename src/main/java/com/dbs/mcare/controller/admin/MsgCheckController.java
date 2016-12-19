package com.dbs.mcare.controller.admin;

import java.util.List;
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
import com.dbs.mcare.framework.service.admin.push.MsgMappingService;
import com.dbs.mcare.framework.service.admin.push.repository.dao.DetailMsg;


@Controller
@RequestMapping("/admin/msgcheck")
public class MsgCheckController {
	private static final Logger logger = LoggerFactory.getLogger(MsgCheckController.class); 

	// 시작날짜. 테이블 컬럼과 맵핑되지 않는, UI와 약속된 검색범위 
	private static final String KEY_START_DATE = "strDate"; 
	// 종료날짜 
	private static final String KEY_END_DATE = "endDate"; 
	
	@Autowired 
	private MsgMappingService msgMappingService; 
	
	
    @RequestMapping(value = "/getMsgList.json", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getMsgList(@RequestBody Map<String, Object> map) throws AdminControllerException {
		final String strDate = map.get(MsgCheckController.KEY_START_DATE).toString();
		final String endDate = map.get(MsgCheckController.KEY_END_DATE).toString();
		final String pId = map.get("pId").toString();
		
    	try { 
    		return this.msgMappingService.findByDate(strDate, endDate, pId, map);
    	}
    	catch(final MCareServiceException ex) {
    		logger.error("가져오기 실패", ex);
    		throw new AdminControllerException(ex); 
    	}
    }	
	
    @RequestMapping(value = "/getMsgDetailInfoList.json", method = RequestMethod.POST)
    @ResponseBody
    public List<DetailMsg> getMsgDetailInfoList(@RequestBody Map<String, String> map) throws AdminControllerException {
    	try { 
    		return this.msgMappingService.findForDetail(map.get("contentsSeq"), map.get("receiverId")); 
    	}
    	catch(final MCareServiceException ex) {
    		logger.error("가져오기 실패", ex);
    		throw new AdminControllerException(ex); 
    	}
    } 
}
