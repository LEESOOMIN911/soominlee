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
import com.dbs.mcare.service.admin.poi.PoiMappingService;
import com.dbs.mcare.service.admin.poi.repository.dao.PoiMapping;

@Controller
@RequestMapping("/admin/poi")
public class PoiMappingController {
	private final  Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired 
	private PoiMappingService mappingService; 
	
	
    @RequestMapping(value = "/getList.json", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getList(@RequestBody Map<String, Object> map) throws AdminControllerException {
    	try { 
    		return this.mappingService.getPagingData(map);
    	}
    	catch(final MCareServiceException ex) {
    		this.logger.error("가져오기 실패", ex);
    		throw new AdminControllerException(ex); 
    	}
    }

    @RequestMapping(value = "/insert.json", method = RequestMethod.POST)
    @ResponseBody
    public PoiMapping insert(HttpServletRequest request, @RequestBody PoiMapping mapping) throws AdminControllerException {
		try { 
			// 추가 
			this.mappingService.add(mapping);
		}
		catch(final MCareServiceException ex) { 
			this.logger.error("추가 실패", ex);
			throw new AdminControllerException(ex); 
		}
        
        return mapping;
    }
    
	@RequestMapping(value="/update.json", method = RequestMethod.POST)
	@ResponseBody 		
	public PoiMapping update(@RequestBody PoiMapping mapping) throws AdminControllerException { 
		// 수정 
		try { 
			this.mappingService.modify(mapping);
		}
		catch(final MCareServiceException ex) { 
			this.logger.error("수정 실패", ex);
			throw new AdminControllerException(ex); 
		}
		
		return mapping; 
	}    

    @RequestMapping(value = "/remove.json", method = RequestMethod.POST)
    public void remove(HttpServletRequest request, @RequestBody PoiMapping mapping) throws AdminControllerException {
		try { 
			// 삭제 
			this.mappingService.remove(mapping);
		}
		catch(final MCareServiceException ex) { 
			this.logger.error("삭제 실패", ex);
			throw new AdminControllerException(ex); 
		}
    }
}
