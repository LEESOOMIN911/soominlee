package com.dbs.mcare.controller.mobile;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dbs.mcare.exception.mobile.MobileControllerException;
import com.dbs.mcare.framework.exception.MCareServiceException;
import com.dbs.mcare.framework.service.menu.ServiceMenu;
import com.dbs.mcare.framework.template.AbstractController;
import com.dbs.mcare.framework.util.ConvertUtil;
import com.dbs.mcare.framework.util.HashUtil;
import com.dbs.mcare.service.PnuhConfigureService;
import com.dbs.mcare.service.RememberMeCookieBaker;
import com.dbs.mcare.service.mobile.user.MCareUserService;
import com.dbs.mcare.service.mobile.user.MCareUserWithdrawalService;
import com.dbs.mcare.service.mobile.user.repository.dao.MCareUser;
import com.dbs.mcare.service.mobile.user.repository.dao.MCareUserWithdrawal;


@Controller
@RequestMapping("/mobile/withdrawal")
@ServiceMenu(menuId="withdrawal", description="탈퇴")
public class UserWithdrawalController  extends AbstractController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired 
	private PnuhConfigureService configureService; 	
	@Autowired 
	private MCareUserService userService; 
	@Autowired 
	private MCareUserWithdrawalService userWithdrawalService; 
	@Autowired
	private RememberMeCookieBaker cookieBaker;
	
	
	/**
	 * 탈퇴 
	 * @param request
	 * @param response
	 * @param reqMap {pId, passWord, reason(탈퇴이유) }
	 * @return
	 * @throws MobileControllerException
	 */
	@RequestMapping(value = "/withdrawal.json", method = RequestMethod.POST)
	public Map<String, Object> withdrawalUser(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Map<String, Object> reqMap) throws MobileControllerException { 

		// 접근권한 및 파라미터 정리 
		try { 
			reqMap = super.isAccessableMenu(this.getClass(), request, reqMap); 
		}
		catch(MCareServiceException ex) { 
			throw new MobileControllerException(ex.getCode(), ex.getMessage()); 
		} 
		
		
		// 환자변호 
		final String pId = (String) reqMap.get("pId"); 
		// 비번 
		final String plainPassword = (String) reqMap.get("passWord"); 
		// 탈퇴이유 
		final String reasonValue = (String) reqMap.get("reason"); 

		// 유효성 확인 
		if(StringUtils.isEmpty(pId) || StringUtils.isEmpty(plainPassword)) {
			throw new MobileControllerException("mcare.auth.unauthenticated", "인증되지 않은 사용자입니다"); 
		}
		
		// 비번확인 
		final HashUtil hashUtils = new HashUtil(this.configureService.getHashSalt()); 
		final boolean checkedPwd = this.userService.checkUserPassword(pId, hashUtils.sha256(plainPassword)); 
		if(!checkedPwd) { 
			throw new MobileControllerException("admin.auth.checkaccount", "비밀번호를 다시 확인하세요"); 
		}
		
		// 결과맵 
		final Map<String, Object> resultMap = new HashMap<String, Object>(); 
		
		try { 
			// 사용자 탈퇴정보 추가 
			this.insertWithdrawalData(pId, reasonValue); 
			// 사용자 삭제작업 
			this.removeUser(request, response, pId); 
			
			// 정상응답 
			resultMap.put("result", "success"); 
		}
		catch(MCareServiceException ex) { 
			if(this.logger.isErrorEnabled()) {
				this.logger.error("사용자 탈퇴처리 실패", ex);
			}
			
			// 실패응답 
			resultMap.put("result", "fail"); 
		}
		
		
		return resultMap;
	}
	
	/**
	 * 사용자 탈퇴정보 추가 <br/> 
	 * 혹시 이 단계에서 오류가 생기더라도 사용자 탈퇴에는 문제가 없어야 함 
	 * @param pId 대상 사용자 
	 * @param reason 이유 
	 * @throws MCareServiceException 
	 */
	private void insertWithdrawalData(String pId, String reason) throws MCareServiceException { 
		// 대상자 정보 가져오고 
		MCareUser targetUser = this.userService.get(pId); 
		
		if(targetUser == null) { 
			if(this.logger.isErrorEnabled()) { 
				this.logger.debug("존재하지 않는 사용자의 탈퇴요청. pId=" + pId);
			}
			
			throw new MCareServiceException("mcare.error.system", "시스템 호출 오류가 발생했습니다."); 
		}
		
		// 추가할 정보 만들고 
		MCareUserWithdrawal data = new MCareUserWithdrawal(); 
		
		// 정보 붙이기 
		data.setpId(pId);
		data.setRegisterDt(targetUser.getRegisterDt());
		data.setWithdrawalDt(new Date());
		if(StringUtils.isEmpty(reason)) {
			data.setReasonValue("");
		}
		else { 
			// 앞/뒤 공백제거 
			reason = reason.trim(); 
			// 길이제한은 질의문에 붙어있으나 특수문자가 포함되거나 하는 경우엔 문제가 있어서 한번 자르고 들어감 
			reason = ConvertUtil.substring(reason, 255); 
			// 붙이기 
			data.setReasonValue(reason);
		}
		
		// 탈퇴사유 추가 
		this.userWithdrawalService.add(data);
	}
	
	/**
	 * 사용자 탈퇴처리 
	 * @param request
	 * @param response
	 * @param pId
	 * @throws MCareServiceException 
	 */
	private void removeUser(HttpServletRequest request, HttpServletResponse response, String pId) throws MCareServiceException { 
		// 사용자 삭제 
		this.userService.removeUser(pId);

		// 회원정보 삭제 성공시, 자동로그인 쿠키 삭제 
		try { 
			this.cookieBaker.remove(request, response);
		} 
		catch(final Exception ex) {
			this.logger.error("자동로그인 쿠키 삭제 실패. 계속진행함.", ex);
			// 쿠키삭제실패해도, 이후처리 계속 가능함
		}
			
		//그리고 세션 날림
		final HttpSession session = request.getSession(false);
		if (session != null) {
			this.logger.debug("로그아웃을 위해 세션 초기화 처리");
			session.invalidate();
		}
	}
}
