package com.dbs.mcare.controller.admin;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dbs.mcare.exception.admin.AdminControllerException;
import com.dbs.mcare.framework.FrameworkConstants;
import com.dbs.mcare.framework.exception.MCareServiceException;
import com.dbs.mcare.framework.service.admin.manager.ManagerService;
import com.dbs.mcare.framework.service.admin.manager.repository.dao.Manager;
import com.dbs.mcare.framework.util.HashUtil;
import com.dbs.mcare.service.PnuhConfigureService;

@Controller
@RequestMapping("/admin/manager")
public class ManagerController {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired 
	private PnuhConfigureService configureService; 
	@Autowired
	private ManagerService managerService;
	
    @RequestMapping(value = "/getList.json", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getList(@RequestBody Map<String, Object> map) throws AdminControllerException {
    	try { 
    		return this.managerService.getPagingData(map);
    	}
    	catch(final MCareServiceException ex) {
    		this.logger.error("관리자 가져오기 실패", ex);
    		throw new AdminControllerException(ex); 
    	}
    }

    @RequestMapping(value = "/save.json", method = RequestMethod.POST)
    @ResponseBody
    public Manager save(HttpServletRequest request, @RequestBody Manager manager) throws AdminControllerException {
    	final HttpSession session = request.getSession();
    	final Manager admin = (Manager) session.getAttribute(FrameworkConstants.SESSION.ADMIN.getKey());
    	
    	try { 
    		final HashUtil hashUtils = new HashUtil(this.configureService.getHashSalt()); 
    		// 비번적용 
    		manager.setPwdValue(hashUtils.sha256(manager.getPwdValue()));
    		
	        if (manager.getCreateDt() == null) {
	            manager.setCreateId(admin.getUserId());
	            this.managerService.add(manager);
	        } 
	        else {
	            manager.setUpdateId(admin.getUserId());
	            this.managerService.modify(manager); 
	        }
    	}
    	catch(final MCareServiceException ex) {
    		this.logger.error("관리자 저장 실패. manager : " + manager);
    		throw new AdminControllerException(ex); 
    	}
        
        return manager;
    }

    @RequestMapping(value = "/remove.json", method = RequestMethod.POST)
    public void remove(HttpServletRequest request, @RequestBody Manager param) throws AdminControllerException {
    	final HttpSession session = request.getSession();
    	final Manager admin = (Manager) session.getAttribute(FrameworkConstants.SESSION.ADMIN.getKey());
    	
    	if(param == null || StringUtils.isEmpty(param.getUserId())) {
    		throw new AdminControllerException("mcare.error.param", "파라미터를 확인해주세요"); 
    	}
    	
        if (param.getUserId().equals(admin.getUserId())) {
            throw new AdminControllerException("admin.manager.myaccount", "본인 계정은 삭제할 수 없습니다");
        }
        
        try { 
        	this.managerService.remove(param);
        }
        catch(final MCareServiceException ex) {
        	this.logger.error("관리자 삭제 실패. userId : " + param, ex);
        	throw new AdminControllerException(ex); 
        }
    }
}
