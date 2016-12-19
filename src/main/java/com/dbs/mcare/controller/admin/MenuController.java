package com.dbs.mcare.controller.admin;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.dbs.mcare.exception.admin.AdminControllerException;
import com.dbs.mcare.framework.FrameworkConstants;
import com.dbs.mcare.framework.exception.MCareServiceException;
import com.dbs.mcare.framework.service.admin.menu.MenuService;
import com.dbs.mcare.framework.service.admin.menu.repository.dao.I18n;
import com.dbs.mcare.framework.service.admin.menu.repository.dao.Menu;
import com.dbs.mcare.framework.service.admin.menu.repository.dao.MenuParam;
import com.dbs.mcare.framework.service.menu.MenuLocaleResourceService;
import com.dbs.mcare.framework.util.AESUtil;
import com.dbs.mcare.service.PnuhConfigureService;

@Controller
@RequestMapping("/admin/menu")
public class MenuController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
//	@Autowired
//	@Qualifier("apiExecuteDelegator")
//    private ApiExecuteDelegator executor;
	@Autowired 
	private PnuhConfigureService configureService; 
	@Autowired
	private MenuService menuService;
	@Autowired
	private MenuLocaleResourceService cacheLoader;

	@RequestMapping(value = "/view.page", method = RequestMethod.GET)
	public Model view(Model model) throws AdminControllerException {
		try { 
			model.addAttribute("defaultLanguage", this.configureService.getI18nDefault());
			model.addAttribute("supportedLanguages", this.configureService.getI18nSupported().split(","));
		}
		catch(final Exception ex) {
			this.logger.error("", ex);
			throw new AdminControllerException(ex); 
		}
		
		return model;
	}

	@RequestMapping(value = "/getList.json", method = RequestMethod.POST)
	@ResponseBody
	public List<Menu> getMenuList(Menu menu) throws AdminControllerException {
		try { 
			return this.menuService.getList(menu);
		}
		catch(final MCareServiceException ex) {
			this.logger.error("메뉴 가져오기 실패", ex);
			throw new AdminControllerException(ex); 
		}
	}

	@RequestMapping(value = "/save.json", method = RequestMethod.POST)
	@ResponseBody
	public Menu save(@RequestBody Menu menu) throws AdminControllerException {
		try { 
			this.menuService.save(menu);
		}
		catch(final MCareServiceException ex) {
			this.logger.error("메뉴 저장실패. menu : " + menu, ex);
			throw new AdminControllerException(ex); 
		}
		
		return menu;
	}
	
	@RequestMapping(value = "/cacheReload.json", method = RequestMethod.POST)
	@ResponseBody
	public Object cacheReload(HttpServletRequest request) throws AdminControllerException {
		final List<String> failedServerUrls = new ArrayList<String>();
		
		try { 
			final RestTemplate restTemplate = new RestTemplate();
	    	restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
	    	restTemplate.getMessageConverters().add(new FormHttpMessageConverter());
	    	
	        final HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
	        
	        final MultiValueMap<String, String> payload = new LinkedMultiValueMap<String, String>();
	        try {
	        	AESUtil aesUtil = new AESUtil(this.configureService.getAesIv()); 
				payload.add("requestTimeMillis", aesUtil.encrypt(String.valueOf(System.currentTimeMillis()), this.configureService.getCacheEncryptionKey()));
				payload.add("encryptionKey", this.configureService.getCacheEncryptionKey());
				payload.add("target", FrameworkConstants.CACHE_TARGET_MENU);
			} 
	        catch (final Exception e) {
				this.logger.error("키 복원실패. 리로드하지 않음", e);
				throw e; 
			}
	        
	        final String[] reloadUrls = this.configureService.getCacheReloadUrls().split(",");
	        for (final String reloadUrl : reloadUrls) {
	        	// 비교 
	        	this.logger.debug("대상 : " + reloadUrl + ", 요청 : " + this.configureService.getServerServiceAddr());
	        	// 현재 서버는 즉시 리로드
	        	if (reloadUrl.equals(this.configureService.getServerServiceAddr()) == true) {
	        		this.logger.error("현재 컨테이너 리로드 : {}", reloadUrl);
	        		//this.executor.loaded();
	        		this.cacheLoader.loaded();
	        	} 
	        	// 다른 서버는 http 요청을 만들어서 해당 서버에 의뢰 
	        	else {
	        		final String urlIncludeContextPath = reloadUrl + request.getContextPath() + FrameworkConstants.URI_CACHE_RELOAD;
	        		this.logger.error("다른 컨테이너 리로드로 인식함 : {}", urlIncludeContextPath);
	        		final int result = restTemplate.postForObject(urlIncludeContextPath, new HttpEntity<MultiValueMap<String, String>>(payload, headers), Integer.class);
	        		if (result == 0) {
	        			failedServerUrls.add(urlIncludeContextPath);
	        			this.logger.debug("- 다른 컨테이너 리로드 실패로 인식함 : " + urlIncludeContextPath);
	        		}
	        	}
	        }
	        
	        if (failedServerUrls.size() > 0) {
	        	for (final String serverUrl : failedServerUrls) {
	        		this.logger.error("캐시 리로드실패 : {}", serverUrl);
	        	}
	        	return 0;
	        } 
	        else {
	        	this.logger.error("캐시 리로드성공");
	        	return 1;
	        }
	        
		} 
		catch (final Exception e) {
			this.logger.error("캐시 리로드실패");
			throw new AdminControllerException(e); 
		}
	}
	
	@RequestMapping(value = "/remove.json", method = RequestMethod.POST)
	public void remove(@RequestParam("menuId") String menuId) throws AdminControllerException {
		try { 
			this.menuService.remove(menuId);
		}
		catch(final MCareServiceException ex) { 
			this.logger.error("메뉴 삭제실패. menuId : " + menuId);
			throw new AdminControllerException(ex); 
		}
	}

	@RequestMapping(value = "/getI18nList.json", method = RequestMethod.GET)
	@ResponseBody
	public List<I18n> getI18nList(@RequestParam(value = "menuId", required = false) String menuId) throws AdminControllerException {
		try { 
			return this.menuService.getMenuI18nListByMenuId(menuId); 
		}
		catch(final MCareServiceException ex) {
			this.logger.error("다국어 가져오기 실패. menuId : " + menuId);
			throw new AdminControllerException(ex); 
		}
	}
	
	/**
     * 파라미터 조회
     * 
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getParamList.json", method = RequestMethod.GET)
    @ResponseBody
    public List<MenuParam> getParamList(@RequestParam(value = "menuId", required = false) String menuId) throws AdminControllerException {
    	try { 
    		return this.menuService.getMenuParamListByMenuId(menuId);
    	}
    	catch(final Exception ex) {
    		this.logger.error("MENU 파리미터 조회 실패. menuId : " + menuId, ex);
    		throw new AdminControllerException(ex); 
    	}
    }
}
