package com.dbs.mcare.controller.admin;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dbs.mcare.exception.admin.AdminControllerException;
import com.dbs.mcare.framework.exception.MCareServiceException;
import com.dbs.mcare.framework.util.ResponseUtil;
import com.dbs.mcare.service.PnuhConfigureService;
import com.dbs.mcare.service.admin.user.AdminUserService;
import com.dbs.mcare.service.api.MCareApiCallService;
import com.dbs.mcare.service.api.SendSmsService;
import com.dbs.mcare.service.mobile.user.MCareUserService;
import com.dbs.mcare.service.mobile.user.UserRegisterService;
import com.dbs.mcare.service.mobile.user.repository.dao.MCareUser;

/**
 * 
 * @author 
 *
 */
@Controller
@RequestMapping("/admin/user")
public class AdminUserController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass()); 
	
	@Autowired 
	private MCareUserService userService; 
//	@Autowired
//	private MCareApiCallService apiCallService;
//	@Autowired
//	private PnuhConfigureService configureService;
//	@Autowired
//	private UserRegisterService userRegisterService;
//	@Autowired
//	private SendSmsService smsService;
	@Autowired
	private AdminUserService adminUserService;
	
	
	@RequestMapping(value = "/view.page", method = RequestMethod.GET)
	public Model user(Model model) throws AdminControllerException {
		return model; 
	}
	
	// 일반 사용자 목록임 
	@RequestMapping(value="/getList.json", method = RequestMethod.POST)
	@ResponseBody 
	public Map<String, Object> getUserList(@RequestBody Map<String, Object> map) throws AdminControllerException {
		try { 
			//return this.userService.getPagingData(map);
			return this.userService.selectUserAccessDay(map); 
		}
		catch(final MCareServiceException ex) {
			throw new AdminControllerException(ex); 
		}
	}
	
	// block 된거 해제하기 
	@RequestMapping(value="/clear.json", method = RequestMethod.POST)
	@ResponseBody 
	public MCareUser clear(@RequestBody MCareUser user) throws AdminControllerException {
		
		this.logger.debug("Update Call");

		// 수정 
		try { 
			this.userService.clear(user);
			this.logger.debug("user update clear : seq = " + user.getUserSeq());
		}
		catch(final MCareServiceException ex) { 
			throw new AdminControllerException(ex); 
		}
		
		return user; 
	}
	
	// 탈퇴처리
	@RequestMapping(value="/remove.json", method = RequestMethod.POST)
	@Transactional
	@ResponseBody 
	public Map<String,Object> remove(@RequestBody Map<String,String> map) throws AdminControllerException {
		Map<String, String> resultMap = new HashMap<String,String>(); 
		// 삭제 
		try { 
			for( String key : map.keySet() ) {
				 String pId = map.get(key);
				 this.userService.removeUser( pId );
				 if(this.logger.isDebugEnabled()) {
					this.logger.debug( "탈퇴처리 : pId = " + pId );
				 }
			}
			
			resultMap.put("type", "success");
			resultMap.put("msg", "사용자 탈퇴가 처리되었습니다.");
		} 
		catch(final MCareServiceException ex) { 
			resultMap.put("type", "fail");
			resultMap.put("msg", "사용자 탈퇴처리에 실패하였습니다.");
			throw new AdminControllerException(ex); 
		}
			
		return ResponseUtil.wrapResultMap(resultMap); 
	}
	
	/**
	 * 사용자 등록 가능 여부 확인
	 * @param request
	 * @param response
	 * @param user
	 * @return
	 * @throws AdminControllerException
	 */
	// 유효한 PID 인지 확인
	@RequestMapping(value="/checkUserPid.json", method = RequestMethod.POST)
	@ResponseBody 
	public Map<String, Object> checkUserPid(HttpServletRequest request, HttpServletResponse response,
			@RequestBody MCareUser user) throws AdminControllerException {
		if(this.logger.isDebugEnabled()) { 
			this.logger.debug("가입 시켜야할 환자 : " + user.getpId());
		}
		
		//가입이 가능한 pId인지 가져오고
		Map<String, Object> resultCheckUser = this.adminUserService.checkUser(user.getpId(), request);
		
		//가입이 안되는 사용자면 message를 Exception으로 던지기
		if(!(Boolean)resultCheckUser.get("success")) {
			throw new AdminControllerException((String)resultCheckUser.get("message"));
		}
		
		return ResponseUtil.wrapResultMap(resultCheckUser);
	}
	
	/**
	 * 관리자의 사용자 등록
	 * @param request
	 * @param response
	 * @param user
	 * @return
	 * @throws AdminControllerException
	 */
	@RequestMapping(value = "/registerUser.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> registerUser(HttpServletRequest request, HttpServletResponse response, 
			@RequestBody MCareUser user) throws AdminControllerException {
		final String pId = user.getpId();

		//phoneNumber가 null이면 기간계에 있는 사용자 폰 번호로 전달
		Map<String, Object> resultMap = this.adminUserService.registerUser(pId, null, request);
		
		if(!(Boolean)resultMap.get("success")) {
			throw new AdminControllerException((String)resultMap.get("message"));
		}
		
		return ResponseUtil.wrapResultMap(resultMap); 
	}
}
