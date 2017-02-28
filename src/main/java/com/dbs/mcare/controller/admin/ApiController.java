package com.dbs.mcare.controller.admin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
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
import com.dbs.mcare.framework.api.executor.ApiExecuteDelegator;
import com.dbs.mcare.framework.api.model.ApiModel.ApiType;
import com.dbs.mcare.framework.api.model.ApiModel.HttpMethodType;
import com.dbs.mcare.framework.api.model.ApiModel.ReqParamType;
import com.dbs.mcare.framework.api.model.ApiModel.ResParamType;
import com.dbs.mcare.framework.api.model.ApiModel.ResultType;
import com.dbs.mcare.framework.api.model.ApiModel.WsHeaderType;
import com.dbs.mcare.framework.api.model.NodeType;
import com.dbs.mcare.framework.api.model.TreeNode;
import com.dbs.mcare.framework.exception.MCareServiceException;
import com.dbs.mcare.framework.service.admin.api.ApiService;
import com.dbs.mcare.framework.service.admin.api.CategoryService;
import com.dbs.mcare.framework.service.admin.api.ReqParamService;
import com.dbs.mcare.framework.service.admin.api.WsHeaderService;
import com.dbs.mcare.framework.service.admin.api.repository.dao.Api;
import com.dbs.mcare.framework.service.admin.api.repository.dao.Category;
import com.dbs.mcare.framework.service.admin.api.repository.dao.ReqParam;
import com.dbs.mcare.framework.service.admin.api.repository.dao.WsHeader;
import com.dbs.mcare.framework.service.admin.manager.repository.dao.Manager;
import com.dbs.mcare.framework.util.AESUtil;
import com.dbs.mcare.framework.util.ConvertUtil;
import com.dbs.mcare.service.PnuhConfigureService;

@Controller
@RequestMapping("/admin/api")
public class ApiController {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("apiExecuteDelegator")
    private ApiExecuteDelegator executor;
	@Autowired 
	private PnuhConfigureService configureService; 
	@Autowired
    private ApplicationContext applicationContext;
	@Autowired
    private CategoryService categoryService;
    @Autowired
    private ApiService apiService;
    @Autowired
    private ReqParamService reqParamService;
    @Autowired 
    private WsHeaderService wsHeaderService;
    
    /**
     * 이동
     * 
     * @param model
     * @return
     */
    @RequestMapping(value = "/view.page", method = RequestMethod.GET)
    public Model api(Model model) throws Exception {
        model.addAttribute("httpMethodTypes", HttpMethodType.values());
        model.addAttribute("reqParamTypes", ReqParamType.values());
        model.addAttribute("resParamTypes", ResParamType.values());
        model.addAttribute("wsHeaderTypes", WsHeaderType.values());
        model.addAttribute("resultTypes", ResultType.values());
        model.addAttribute("apiTypes", ApiType.values());
        model.addAttribute("dataSourceNames", this.applicationContext.getBeansOfType(DataSource.class).keySet().toArray(new String[0]));
        return model;
    }
	
    /**
     * 조회
     * 
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getList.json", method = RequestMethod.GET)
    @ResponseBody
    public List<TreeNode> getList(@RequestParam(value = "id", required = false) Long id) throws AdminControllerException {
        final List<TreeNode> model = new ArrayList<TreeNode>();
        
        try { 
	        for (final Category category : this.categoryService.getList(new Category(id))) {
	        	model.add(new TreeNode(category.getCatSeq(), NodeType.CATEGORY, category.getCatName(), category));
	        }
        }
        catch(final Exception ex) { 
        	this.logger.error("카테고리 정리 실패. id : " + id, ex);
        	throw new AdminControllerException(ex); 
        }
	     
        // 카테고리가 없다면 api조회 
//        if (model.isEmpty()) {
        	try { 
	            for (final Api api : this.apiService.getList(new Api(null, id))) {
	                model.add(new TreeNode(api.getApiSeq(), NodeType.API, api.getApiName(), api));
	            }
        	}
        	catch(final Exception ex) { 
        		this.logger.error("API 정리 실패. id : " + id, ex);
        		throw new AdminControllerException(ex); 
        	}
//        }
        return model;
    }
	
    /**
     * 저장
     * 
     * @param api
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/save.json", method = RequestMethod.POST)
    @ResponseBody
    public Object save(HttpServletRequest request, @RequestBody Api api) throws AdminControllerException {
    	final HttpSession session = request.getSession();
		final Manager admin = (Manager) session.getAttribute(FrameworkConstants.SESSION.ADMIN.getKey());
		
		try { 
	        if (api.getApiSeq() == null) {
	        	api.setCreateId(admin.getUserId());
	        } else {
	        	api.setUpdateId(admin.getUserId());
	        }
	        this.apiService.save(api);
		}
		catch(final MCareServiceException ex) { 
			this.logger.error("api 저장 실패", ex);
			throw new AdminControllerException(ex); 
		}
        
        return api;
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
				payload.add("target", FrameworkConstants.CACHE_TARGET_API);
			} 
	        catch (final Exception e) {
				e.printStackTrace();
				this.logger.error("키 복원실패. 리로드하지 않음", e);
				throw e; 
			}
	        
	        final String[] reloadUrls = this.configureService.getCacheReloadUrls().split(",");
	        for (final String reloadUrl : reloadUrls) {
	        	// 현재 서버는 즉시 리로드
	        	if (reloadUrl.equals(this.configureService.getServerServiceAddr()) == true) {
	        		this.logger.error("현재 컨테이너 리로드 : {}", reloadUrl);
	        		this.executor.loaded();
	        	} else {
	        		final String urlIncludeContextPath = reloadUrl + request.getContextPath() + FrameworkConstants.URI_CACHE_RELOAD;
	        		this.logger.error("다른 컨테이너 리로드 : {}", urlIncludeContextPath);
	        		final int result = restTemplate.postForObject(urlIncludeContextPath, new HttpEntity<MultiValueMap<String, String>>(payload, headers), Integer.class);
	        		if (result == 0) {
	        			failedServerUrls.add(urlIncludeContextPath);
	        		}
	        	}
	        }
	        
	        if (failedServerUrls.size() > 0) {
	        	for (final String serverUrl : failedServerUrls) {
	        		this.logger.error("캐시 리로드실패 : {}", serverUrl);
	        	}
	        	return 0;
	        } else {
	        	this.logger.error("캐시 리로드성공");
	        	return 1;
	        }
	        
		} catch (final Exception e) {
			this.logger.error("캐시 리로드실패");
			throw new AdminControllerException(e); 
		}
	}
    
    /**
     * 삭제
     * 
     * @param apiSeq
     * @throws Exception
     */
    @RequestMapping(value = "/remove.json", method = RequestMethod.POST)
    @ResponseBody
    public void remove(@RequestParam("apiSeq") Long apiSeq) throws AdminControllerException {
    	try { 
    		this.apiService.remove(apiSeq);
    	}
    	catch(final Exception ex) { 
    		this.logger.error("api 삭제 실패. apiSeq : " + apiSeq, ex);
    		throw new AdminControllerException(ex); 
    	}
    }
    
    /**
     * 실행 테스트
     * 
     * @param response
     * @param api
     * @return
     * @throws Exception
     */
    @Transactional(value = "mcareTransactionManager", propagation = Propagation.NOT_SUPPORTED)
    @RequestMapping(value = "/test.json", method = RequestMethod.POST)
    @ResponseBody
    public Object test(HttpServletRequest request, HttpServletResponse response, @RequestBody Api api) throws AdminControllerException {
        final Map<String, Object> paramMap = new HashMap<String, Object>();
        
        // 요청 파라미터 정리 
        for (final ReqParam param : api.getReqParams()) {
            paramMap.put(param.getParamName(), param.getSampleValue());
        }
        
        if(this.logger.isDebugEnabled()) {
        	StringBuilder builder = new StringBuilder(FrameworkConstants.NEW_LINE); 
        	builder.append("정리된 요청 파라미터 ===== ").append(FrameworkConstants.NEW_LINE); 
        	builder.append(ConvertUtil.convertStringForDebug(paramMap)); 
        	this.logger.debug(builder.toString());
        }
        
        
        try {
            this.executor.validate(api);
            final Object result = this.executor.execute(api, paramMap, null);
            return  result == null? Collections.EMPTY_MAP: result;
        } 
        catch (final Exception e) {
        	this.logger.error("테스트 실행 중 예외발생. api : " + api + ", paramMap : " + paramMap, e);
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return e.getMessage();
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
    public List<ReqParam> getParamList(@RequestParam(value = "apiSeq", required = false) Long apiSeq) throws AdminControllerException {
    	try { 
    		return this.reqParamService.getListByApiSeq(apiSeq);
    	}
    	catch(final Exception ex) {
    		this.logger.error("API 파리미터 조회 실패. apiSeq : " + apiSeq, ex);
    		throw new AdminControllerException(ex); 
    	}
    }
    
    /**
     * 헤더정보 조회
     * 
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getHeaderList.json", method = RequestMethod.GET)
    @ResponseBody
    public List<WsHeader> getHeaderList(@RequestParam(value = "apiSeq", required = false) Long apiSeq) throws AdminControllerException { 
    	try { 
    		return this.wsHeaderService.getListByApiSeq(apiSeq);
    	}
    	catch(final Exception ex) {
    		this.logger.error("API 헤더정보 조회 실패. apiSeq : " + apiSeq, ex);
    		throw new AdminControllerException(ex); 
    	}
    }
}
