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
import com.dbs.mcare.framework.service.admin.version.VersionService;
import com.dbs.mcare.framework.service.admin.version.repository.dao.Version;

@Controller
@RequestMapping("/admin/version")
public class VersionController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private VersionService versionService;
	
	/**
	 * 앱 버전 List 조회 
	 * 
	 * @param version
	 * @return
	 * @throws AdminControllerException
	 */
	@RequestMapping(value="/getAllVersionList.json", method=RequestMethod.POST)
	public Map<String, Object> getAllVersionList(@RequestBody Map<String, Object> map) throws AdminControllerException {
		try { 
			return this.versionService.getPagingData(map);
		}
		catch(final MCareServiceException ex) {
			this.logger.error("버전정보 가져오기 실패", ex);
			throw new AdminControllerException(ex); 
		}
	}
	
	/**
	 * 조건에 맞는 하나의 앱 버전 정보 반환
	 * 조건 : platformType 
	 * 
	 * @param version
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getVersion.json", method=RequestMethod.POST)
	@ResponseBody
	public Version getVersion(@RequestBody Version version) throws AdminControllerException {
		try { 
			return this.versionService.get(version);
		}
		catch(final MCareServiceException ex) { 
			this.logger.error("앱 버전정보 가져오기 실패. version : " + version, ex);
			throw new AdminControllerException(ex); 
		}
	}
		
	/**
	 * platformType에 해당하는 앱 버전 정보를 삭제한다.
	 * @param version
	 * @throws Exception
	 */
	@RequestMapping(value="/removeVersion.json", method=RequestMethod.POST) 
	public void removeVersion(@RequestBody Version version) throws AdminControllerException {
		try { 
			this.versionService.remove(version);
		}
		catch(final MCareServiceException ex) { 
			this.logger.error("앱 버전정보 삭제. version : " + version);
			throw new AdminControllerException(ex); 
		}
	}
	
	/**
	 * 새로운 앱 버전 정보를 추가한다. 
	 * @param version  
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/insertVersion.json", method=RequestMethod.POST)
	@ResponseBody 
	public Version insertVersion(@RequestBody Version version) throws AdminControllerException {
		try { 
			this.versionService.add(version);
		}
		catch(final MCareServiceException ex) { 
			this.logger.error("앱 버전정보 추가 실패. version : " + version, ex);
			throw new AdminControllerException(ex); 
		}
		
		return version; 
	}
	/**
	 * 기존 앱 버전 정보를 수정한다. 
	 * @param version  
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/updateVersion.json", method=RequestMethod.POST)
	@ResponseBody 
	public Version saveVersion(@RequestBody Version version) throws AdminControllerException {
		try { 
			this.versionService.modify(version);
		}
		catch(final MCareServiceException ex) { 
			this.logger.error("앱 버전정보 수정 실패. version : " + version, ex);
			throw new AdminControllerException(ex); 
		}
		
		return version; 
	}
	
}
