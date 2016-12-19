package com.dbs.mcare.controller.admin;

import java.security.InvalidParameterException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dbs.mcare.exception.admin.AdminControllerException;
import com.dbs.mcare.framework.exception.MCareServiceException;
import com.dbs.mcare.framework.service.admin.api.CategoryService;
import com.dbs.mcare.framework.service.admin.api.repository.dao.Category;
import com.dbs.mcare.framework.util.SessionUtil;

@Controller
@RequestMapping("/admin/category")
public class CategoryController {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private CategoryService categoryService;

	/**
	 * 카테고리 조회
	 * 
	 * @param category
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getList.json", method = RequestMethod.POST)
    @ResponseBody
    public List<Category> getList(Category category) throws AdminControllerException {
		try { 
			return this.categoryService.getList(category);
		}
		catch(final MCareServiceException ex) { 
			this.logger.error("카테고리 조회 실패. category : " + category, ex);
			throw new AdminControllerException(ex); 
		}
    }
	
	/**
	 * 카테고리 저장
	 * 
	 * @param request
	 * @param category
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/save.json", method = RequestMethod.POST)
    @ResponseBody
    public Object save(HttpServletRequest request, @RequestBody Category category) throws AdminControllerException {
		final String userId = SessionUtil.getUserId(request); 
		
		try { 
	        if (category.getCatSeq() == null) {
	            category.setCreateId(userId);
	        } else {
	            category.setUpdateId(userId);
	        }
	        this.categoryService.save(category);
		}
		catch(final Exception ex) {
			this.logger.error("카테고리 저장 실패. category : " + category, ex);
			throw new AdminControllerException(ex); 
		}
		
        return category;
    }
	
	/**
	 * 카테고리 삭제
	 * 
	 * @param catSeq 파라미터를 명시적으로 Long으로 했는데 지울때 이 메소드에 안걸려서 파라미터를 Object로 바꿔봤음.. 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/remove.json", method = RequestMethod.POST)
    @ResponseBody
    public Object remove(@RequestParam(value = "catSeq") Object catSeq) throws AdminControllerException {
		if(catSeq == null) {
			throw new AdminControllerException(new InvalidParameterException()); 
		}
		
		//this.logger.debug("catSeq : " + catSeq +", type=" + ((catSeq == null) ? "null" : catSeq.getClass())); 
		
		Long seq = null; 
		
		try { 
			seq = Long.parseLong(catSeq + ""); 
		}
		catch(final NumberFormatException ex) { 
			throw new AdminControllerException(ex); 
		}
		
		try { 
			this.categoryService.remove(new Category(seq));
		}
		catch(final MCareServiceException ex) { 
			this.logger.error("카테고리 삭제 실패. catSeq : " + catSeq, ex);
			throw new AdminControllerException(ex); 
		}
		
        return catSeq;
    }
}
