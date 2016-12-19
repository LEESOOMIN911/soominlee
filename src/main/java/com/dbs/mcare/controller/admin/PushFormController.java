package com.dbs.mcare.controller.admin;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import com.dbs.mcare.framework.service.admin.push.PushFormService;
import com.dbs.mcare.framework.service.admin.push.repository.dao.PushForm;

@Controller
@RequestMapping("/admin/pushform")
public class PushFormController {
	private final  Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired 
	private PushFormService pushFormService; 

	@RequestMapping(value = "/getList.json", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getList(@RequestBody Map<String, Object> map) throws AdminControllerException {
    	try { 
    		return this.pushFormService.getPagingData(map);
    	}
    	catch(final MCareServiceException ex) {
    		this.logger.error("가져오기 실패", ex);
    		throw new AdminControllerException(ex); 
    	}
    }

	@RequestMapping(value = "/insert.json", method = RequestMethod.POST)
    @ResponseBody
    public PushForm save(HttpServletRequest request, @RequestBody PushForm pushForm) throws AdminControllerException {
		try { 
			// 추가 
			this.pushFormService.add(pushForm);
		}
		catch(final MCareServiceException ex) { 
			this.logger.error("추가 실패", ex);
			throw new AdminControllerException(ex); 
		}
        
        return pushForm;
    }
    
	@RequestMapping(value="/update.json", method = RequestMethod.POST)
	@ResponseBody 		
	public void update(@RequestBody PushForm pushForm) throws AdminControllerException { 
		// 수정 
		try { 
			this.pushFormService.modify(pushForm);
		}
		catch(final MCareServiceException ex) { 
			this.logger.error("수정 실패", ex);
			throw new AdminControllerException(ex); 
		}
	}   
}
