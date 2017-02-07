package com.dbs.mcare.service.mobile.user;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.math.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.dbs.mcare.MCareConstants;
import com.dbs.mcare.MCareConstants.MCARE_TEST_USER.INFO;
import com.dbs.mcare.exception.mobile.ApiCallException;
import com.dbs.mcare.exception.mobile.MobileControllerException;
import com.dbs.mcare.framework.FrameworkConstants;
import com.dbs.mcare.framework.service.ConfigureService;
import com.dbs.mcare.framework.service.MessageService;
import com.dbs.mcare.framework.util.Base64ConvertUtil;
import com.dbs.mcare.framework.util.ConvertUtil;
import com.dbs.mcare.framework.util.ResponseUtil;
import com.dbs.mcare.service.api.MCareApiCallService;
import com.dbs.mcare.service.api.PnuhApi;
import com.dbs.mcare.service.api.SendSmsService;
import com.dbs.mcare.service.mobile.user.repository.dao.MCareUser;

import Kisinfo.Check.IPINClient;
import NiceID.Check.CPClient;

/**
 * 사용자 등록에 필요한 서비스 들 
 * @author heesung 
 *
 */
@Service 
public class UserRegisterService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired 
	private MCareApiCallService apiCallService; 
	@Autowired
	private ConfigureService configureService;
	@Autowired
	private SendSmsService smsService;
	@Autowired 
	private MessageService messageService; 
	// 
	private static final String SESSION_AUTH = FrameworkConstants.SESSION_PREFIX + "AUTH"; // NICE로부터 부여받은 사이트 패스워드
	
	/**
	 * 환자번호로 DB에 저장된 사용자 정보 가져오기 
	 * @param pId
	 * @return
	 */
	public MCareUser getUserInfo(String pId) {
		// 사용자 정보 가져오기 
		Map<String, Object> userMap = null; 
		
		try { 
			userMap = (Map<String, Object>) this.apiCallService.call(PnuhApi.USER_USERINFO_GETLOGININFO, "pId", pId); 
		}
		catch(final ApiCallException ex) { 
			this.logger.error("사용자 정보 찾기 실패", ex);
			return null; 
		}
		
		//ResponseUtil을 통해 결과가 있는지 확인
		//결과가 비어 있지 않으면 null반환
		if(ResponseUtil.isEmptyResult(userMap)) {
			this.logger.debug("등록되지 않은 사용자 : " + pId);
			return null;
		}
		
		// 가져온 데이터가 있으면 자료구조 만들어서 반환 
		return MCareUser.convert(userMap);
	}	
	
	/**
	 * 환자번호로 기간계에 저장된 사용자 정보 찾기 
	 * @param pId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> getPatientInfo(String pId) { 
		try { 
			return (Map<String, Object>) this.apiCallService.execute(PnuhApi.USER_USERINFO_GETUSERINFO, "pId", pId); 
		}
		catch(ApiCallException ex) { 
			if(this.logger.isDebugEnabled()) {
				this.logger.debug("예외발생", ex); 
			}
			
			return null; 
		}		
	}
	
	/**
	 * 환자이름, 핸드폰 번호로 사용자 정보 찾기
	 * @param pName
	 * @param phoneNo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> getPatientInfo(String pName, String phoneNo) {

		Map<String, Object> reqMap = new HashMap<String, Object>();
		reqMap.put("pName", pName);
		reqMap.put("cellphoneNo", phoneNo);
		
		try { 
			return (Map<String, Object>) this.apiCallService.execute(PnuhApi.USER_USERINFO_FINDPID, reqMap); 
		}
		catch(ApiCallException ex) { 
			if(this.logger.isDebugEnabled()) {
				this.logger.debug("예외발생", ex); 
			}
			
			return null; 
		}
	}	
	
	
	/**
	 * 휴대폰 인증(checkPlus)시 필요한 데이터(sEncData)를 생성해서 반환한다.
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> checkPlusRequest(HttpServletRequest request) {
		final Map<String, String> map = new HashMap<String, String>();
		
		if (this.logger.isDebugEnabled()) {
			this.logger.debug("## Site Code : " + this.configureService.getAuthPhoneSiteCode());
			this.logger.debug("## Site Password : " + this.configureService.getAuthPhoneSitePassword());
		}
		
		final String authPhoneSiteCode = this.configureService.getAuthPhoneSiteCode();
		final String authPhoneSitePassword = this.configureService.getAuthPhoneSitePassword();
		
		final HttpSession session = request.getSession();
		
		final CPClient niceCheck = new CPClient();
		// 요청 번호, 이는 성공/실패후에 같은 값으로 되돌려주게 되므로 업체에서 적절하게 변경하여 쓰거나, 아래와 같이 생성한다.
		String sRequestNumber = "REQ0000000001"; 
		sRequestNumber = niceCheck.getRequestNO(authPhoneSiteCode);
		// 해킹등의 방지를 위하여 세션을 쓴다면, 세션에 요청번호를 넣는다.
		session.setAttribute(SESSION_AUTH, sRequestNumber);
		// 없으면 기본 선택화면, M: 핸드폰, C: 신용카드, X: 공인인증서
		final String sAuthType = ""; 
		// Y : 취소버튼 있음 / N : 취소버튼 없음
		final String popgubun = "Y"; 
		// 없으면 기본 웹페이지 / Mobile : 모바일페이지
		final String customize = "Mobile"; 
		// CheckPlus(본인인증) 처리 후, 결과 데이타를 리턴 받기위해 다음예제와 같이 http부터 입력합니다.
		// TODO 여기주소 
		final String sReturnUrl = this.configureService.getServerServiceAddr() + request.getContextPath() + "/mobile/user/certificationResult.page"; // 성공시 이동될 URL
		final String sErrorUrl = this.configureService.getServerServiceAddr() + request.getContextPath() + "/mobile/user/checkPlusFailed.page"; // 실패시 이동될 URL
		
		final String plainDataSeperator = ":";
		// 입력될 plain 데이타를 만든다.
		final StringBuffer sPlainDataBuf = new StringBuffer();
		sPlainDataBuf.append("7:REQ_SEQ").append(sRequestNumber.getBytes().length).append(plainDataSeperator).append(sRequestNumber);
		sPlainDataBuf.append("8:SITECODE").append(authPhoneSiteCode.getBytes().length).append(plainDataSeperator).append(authPhoneSiteCode);
		sPlainDataBuf.append("9:AUTH_TYPE").append(sAuthType.getBytes().length).append(plainDataSeperator).append(sAuthType);
		sPlainDataBuf.append("7:RTN_URL").append(sReturnUrl.getBytes().length).append(plainDataSeperator).append(sReturnUrl);
		sPlainDataBuf.append("7:ERR_URL").append(sErrorUrl.getBytes().length).append(plainDataSeperator).append(sErrorUrl);
		sPlainDataBuf.append("11:POPUP_GUBUN").append(popgubun.getBytes().length).append(plainDataSeperator).append(popgubun);
		sPlainDataBuf.append("9:CUSTOMIZE").append(customize.getBytes().length).append(plainDataSeperator).append(customize);
		
		String sMessage = "";
		String sEncData = "";
		
		final int iReturn = niceCheck.fnEncode(authPhoneSiteCode, authPhoneSitePassword, sPlainDataBuf.toString());
		if (iReturn == 0) {
			sEncData = niceCheck.getCipherData();
		} else if (iReturn == -1) {
			sMessage = "암호화 시스템 에러입니다.";
		} else if (iReturn == -2) {
			sMessage = "암호화 처리오류입니다.";
		} else if (iReturn == -3) {
			sMessage = "암호화 데이터 오류입니다.";
		} else if (iReturn == -9) {
			sMessage = "입력 데이터 오류입니다.";
		} else {
			sMessage = "알수 없는 에러 입니다. iReturn : " + iReturn;
		}
		
		map.put("sMessage", sMessage);
		map.put("sEncData", sEncData);
		
		return map;
	}
	/**
	 * 휴대폰 인증 결과 데이터 생성
	 * @param request
	 * @param model
	 * @return
	 */
	public Model checkPlusResult(HttpServletRequest request, Model model) {
		final HttpSession session = request.getSession();
		
		final CPClient niceCheck = new CPClient();
		
		final String sEncodeData = this.requestReplace(request.getParameter("EncodeData"), "encodeData");
		final String sReserved1 = this.requestReplace(request.getParameter("param_r1"), "");
		final String sReserved2 = this.requestReplace(request.getParameter("param_r2"), "");
		final String sReserved3 = this.requestReplace(request.getParameter("param_r3"), "");
		String sCipherTime = "";	// 복호화한 시간 
		String sRequestNumber = "";	// 요청 번호 
		String sResponseNumber = "";// 인증 고유번호
		String sAuthType = "";		// 인증 수단
		String sName = "";			// 성명 
		String sDupInfo = "";		// 중복가입 확인값 (DI_64 byte) 
		String sConnInfo = "";		// 연계정보 확인값 (CI_88 byte) 
		String sBirthDate = "";		// 생일
		String sGender = "";		// 성별 
		String sNationalInfo = "";  // 내/외국인정보 (개발가이드 참조)
		String sMessage = "";
		String sPlainData = "";
		String result = "";
		String errorMsgCode = "";
		
		final String session_sRequestNumber = (String) session.getAttribute(SESSION_AUTH);
		this.logger.debug("session_sRequestNumber = " + session_sRequestNumber);
		
		final int iReturn = niceCheck.fnDecode(this.configureService.getAuthPhoneSiteCode(), this.configureService.getAuthPhoneSitePassword(), sEncodeData);
		
		if (iReturn == 0) {
			sPlainData = niceCheck.getPlainData();
			sCipherTime = niceCheck.getCipherDateTime();

			// 데이타를 추출합니다.
			final HashMap<?, ?> mapresult = niceCheck.fnParse(sPlainData);
			
			sRequestNumber = (String) mapresult.get("REQ_SEQ");
			sResponseNumber = (String) mapresult.get("RES_SEQ");
			sAuthType = (String) mapresult.get("AUTH_TYPE");
			sName = (String) mapresult.get("NAME");
			sBirthDate = (String) mapresult.get("BIRTHDATE");
			sGender = (String) mapresult.get("GENDER");
			sNationalInfo = (String) mapresult.get("NATIONALINFO");
			sDupInfo =  (String) mapresult.get("DI");
			sConnInfo = (String) mapresult.get("CI");
			
			if(this.comparePatientInfo(sReserved3, sName, sBirthDate)) {
				sMessage = "정상 처리되었습니다.";
				result = "success";
			}
			else {
				sMessage = "환자정보와 인증정보가 같지 않습니다.";
				result = "fail";
				errorMsgCode = "checkPlusCertification001";
				
				if(this.logger.isErrorEnabled()) { 
					this.logger.error("핸드폰 인증 에러 iRtn : " + iReturn + ", resultCode : " + errorMsgCode + ", Message : " + sMessage);
				} 
			}
			
		} else if (iReturn == -1) {
			sMessage = "복호화 시스템 에러입니다.";
			errorMsgCode = "checkPlusCertification002";
		} else if (iReturn == -4) {
			sMessage = "복호화 처리오류입니다.";
			errorMsgCode = "checkPlusCertification003";
		} else if (iReturn == -5) {
			sMessage = "복호화 해쉬 오류입니다.";
			errorMsgCode = "checkPlusCertification004";
		} else if (iReturn == -6) {
			sMessage = "복호화 데이터 오류입니다.";
			errorMsgCode = "checkPlusCertification005";
		} else if (iReturn == -9) {
			sMessage = "입력 데이터 오류입니다.";
			errorMsgCode = "checkPlusCertification006";
		} else if (iReturn == -12) {
			sMessage = "사이트 패스워드 오류입니다.";
			errorMsgCode = "checkPlusCertification007";
		} else {
			sMessage = "알수 없는 에러 입니다. iReturn : " + iReturn;
			errorMsgCode = "checkPlusCertification008";
		}
		
		model.addAttribute("sCipherTime", sCipherTime);
		model.addAttribute("sRequestNumber", sRequestNumber);
		model.addAttribute("sResponseNumber", sResponseNumber);
		model.addAttribute("sAuthType", sAuthType);
		model.addAttribute("sDupInfo", sDupInfo);
		model.addAttribute("sConnInfo", sConnInfo);
//		파라미터를 사용되는 Data만 Base64Encode
		model.addAttribute("sName", Base64ConvertUtil.base64Encode(sName));
		model.addAttribute("sBirthDate", Base64ConvertUtil.base64Encode(sBirthDate));
		model.addAttribute("sGenderCode", Base64ConvertUtil.base64Encode(sGender));
		model.addAttribute("sNationalInfo", Base64ConvertUtil.base64Encode(sNationalInfo));
		model.addAttribute("sReserved1", sReserved1);
		model.addAttribute("sReserved2", sReserved2);
		model.addAttribute("sReserved3", Base64ConvertUtil.base64Encode(sReserved3)); //사용자 ID로 사용되는 파라미터니까 Base64변환
		model.addAttribute("sMessage", sMessage);
		model.addAttribute("result", result);
		model.addAttribute("errorMsgCode",errorMsgCode);
		
		return model;
	}
	
	/**
	 * 휴대폰 인증실패 
	 * @param request
	 * @param model
	 * @return
	 */
	public Model checkPlusFailed(HttpServletRequest request, Model model) {
		final CPClient niceCheck = new CPClient();
		final String sEncodeData = this.requestReplace(request.getParameter("EncodeData"),	"encodeData");
		final String sReserved1 = this.requestReplace(request.getParameter("param_r1"), "");
		final String sReserved2 = this.requestReplace(request.getParameter("param_r2"), "");
		final String sReserved3 = this.requestReplace(request.getParameter("param_r3"), "");
		String sCipherTime = ""; 	// 복호화한 시간
		String sRequestNumber = ""; // 요청 번호
		String sErrorCode = ""; 	// 인증 결과코드
		String sAuthType = ""; 		// 인증 수단
		String sMessage = "";
		String sPlainData = "";

		final int iReturn = niceCheck.fnDecode(this.configureService.getAuthPhoneSiteCode(), this.configureService.getAuthPhoneSitePassword(), sEncodeData);

		if (iReturn == 0) {
			sPlainData = niceCheck.getPlainData();
			sCipherTime = niceCheck.getCipherDateTime();

			// 데이타를 추출합니다.
			final HashMap<?, ?> mapresult = niceCheck.fnParse(sPlainData);
			
			sRequestNumber = (String) mapresult.get("REQ_SEQ");
			sErrorCode = (String) mapresult.get("ERR_CODE");
			sAuthType = (String) mapresult.get("AUTH_TYPE");
			
		} else if (iReturn == -1) {
			sMessage = "복호화 시스템 에러입니다.";
		} else if (iReturn == -4) {
			sMessage = "복호화 처리오류입니다.";
		} else if (iReturn == -5) {
			sMessage = "복호화 해쉬 오류입니다.";
		} else if (iReturn == -6) {
			sMessage = "복호화 데이터 오류입니다.";
		} else if (iReturn == -9) {
			sMessage = "입력 데이터 오류입니다.";
		} else if (iReturn == -12) {
			sMessage = "사이트 패스워드 오류입니다.";
		} else {
			sMessage = "알수 없는 에러 입니다. iReturn : " + iReturn;
		}
		
		model.addAttribute("sCipherTime", sCipherTime);
		model.addAttribute("sRequestNumber", sRequestNumber);
		model.addAttribute("sAuthType", sAuthType);
		model.addAttribute("sErrorCode", sErrorCode);
		model.addAttribute("sReserved1", sReserved1);
		model.addAttribute("sReserved2", sReserved2);
		model.addAttribute("sReserved3", sReserved3);
		model.addAttribute("sMessage", sMessage);
		
		//본인인증 실패 로그
		logger.debug("{}/nice=REQNO[{}]/{}/{}/{}/{}/{}/{}/{}/", sCipherTime, sRequestNumber, sAuthType, sErrorCode, sReserved1, sReserved2, sReserved2, sReserved3);
		
		return model;
	}	
	
	/**
	 * iPin인증시 필요한 데이터(sEncData)를 생성해서 반환한다.
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> iPinRequest(HttpServletRequest request) {
		String sCPRequest	= null; 
		// TODO 수정필요 
//		final String sReturnURL = this.serverServiceAddr + request.getContextPath() + "/mobile/user/certificationResult.page";
		final String sReturnURL = this.configureService.getServerServiceAddr() + request.getContextPath() + "/mobile/user/certificationResult.page";
		final String certiReqType = request.getParameter("certiReqType");
		
		// 객체 생성
		final IPINClient pClient = new IPINClient();
		// 앞서 설명드린 바와같이, CP 요청번호는 배포된 모듈을 통해 아래와 같이 생성할 수 있습니다.
		sCPRequest = pClient.getRequestNO(this.configureService.getAuthIpinSiteCode());
		// CP 요청번호를 세션에 저장합니다.
		// 현재 예제로 저장한 세션은 ipin_result.jsp 페이지에서 데이타 위변조 방지를 위해 확인하기 위함입니다.
		// 필수사항은 아니며, 보안을 위한 권고사항입니다.
		request.getSession().setAttribute("CPREQUEST" , sCPRequest);
		// Method 결과값(iRtn)에 따라, 프로세스 진행여부를 파악합니다.
		final int iRtn = pClient.fnRequest(this.configureService.getAuthIpinSiteCode(), this.configureService.getAuthIpinSitePassword(), sCPRequest, sReturnURL);
		
		String sRtnMsg					= "";			// 처리결과 메세지
		String sEncData					= "";			// 암호화 된 데이타
		
		// Method 결과값에 따른 처리사항
		if (iRtn == 0) {
			// fnRequest 함수 처리시 업체정보를 암호화한 데이터를 추출합니다.
			// 추출된 암호화된 데이타는 당사 팝업 요청시, 함께 보내주셔야 합니다.
			sEncData = pClient.getCipherData();		//암호화 된 데이타
			sRtnMsg = "정상 처리되었습니다.";
		
		}
		else if (iRtn == -1 || iRtn == -2) {
			sRtnMsg =	"배포해 드린 서비스 모듈 중, 귀사 서버환경에 맞는 모듈을 이용해 주시기 바랍니다.<BR>" +
						"귀사 서버환경에 맞는 모듈이 없다면 ..<BR>iRtn 값, 서버 환경정보를 정확히 확인하여 메일로 요청해 주시기 바랍니다.";
		}
		else if (iRtn == -9) {
			sRtnMsg = "입력값 오류 : fnRequest 함수 처리시, 필요한 4개의 파라미터값의 정보를 정확하게 입력해 주시기 바랍니다.";
		}
		else {
			sRtnMsg = "iRtn 값 확인 후, NICE평가정보 개발 담당자에게 문의해 주세요.";
		}
		
		final Map<String, String> map = new HashMap<String, String>();
		map.put("certiReqType", certiReqType);
		map.put("sMessage", sRtnMsg);
		map.put("sEncData", sEncData);
		
		return map;
	}
	
	/**
	 * iPin인증 결과 데이터 생성
	 * @param request
	 * @param model
	 * @return
	 */
	public Model iPinResult(HttpServletRequest request, Model model) {
		/********************************************************************************************************************************************
		NICE평가정보 Copyright(c) KOREA INFOMATION SERVICE INC. ALL RIGHTS RESERVED
		
		서비스명 : 가상주민번호서비스 (IPIN) 서비스
		페이지명 : 가상주민번호서비스 (IPIN) 결과 페이지
		*********************************************************************************************************************************************/
		
		// 사용자 정보 및 CP 요청번호를 암호화한 데이타입니다.
		//t
	    final String sResponseData = this.requestReplace(request.getParameter("enc_data"), "encodeData");
	    final String sReserved1 = this.requestReplace(request.getParameter("param_r1"), "");
		final String sReserved2 = this.requestReplace(request.getParameter("param_r2"), "");
		final String sReserved3 = this.requestReplace(request.getParameter("param_r3"), "");
		
	    // CP 요청번호 : ipin_main.jsp 에서 세션 처리한 데이타
	
	    // 객체 생성
		final IPINClient pClient = new IPINClient();
		
		/*
		┌ 복호화 함수 설명  ──────────────────────────────────────────────────────────
			Method 결과값(iRtn)에 따라, 프로세스 진행여부를 파악합니다.
			
			fnResponse 함수는 결과 데이타를 복호화 하는 함수이며,
			'sCPRequest'값을 추가로 보내시면 CP요청번호 일치여부도 확인하는 함수입니다. (세션에 넣은 sCPRequest 데이타로 검증)
			
			따라서 귀사에서 원하는 함수로 이용하시기 바랍니다.
		└────────────────────────────────────────────────────────────────────
		*/
		final int iRtn = pClient.fnResponse(this.configureService.getAuthIpinSiteCode(), this.configureService.getAuthIpinSitePassword(), sResponseData);
		
		String sRtnMsg					= "";							// 처리결과 메세지
		final String sVNumber			= pClient.getVNumber();			// 가상주민번호 (13자리이며, 숫자 또는 문자 포함)
		final String sName				= pClient.getName();			// 이름
		final String sDupInfo			= pClient.getDupInfo();			// 중복가입 확인값 (DI - 64 byte 고유값)
		final String sAgeCode			= pClient.getAgeCode();			// 연령대 코드 (개발 가이드 참조)
		final String sGenderCode		= pClient.getGenderCode();		// 성별 코드 (개발 가이드 참조)
		final String sBirthDate			= pClient.getBirthDate();		// 생년월일 (YYYYMMDD)
		final String sNationalInfo		= pClient.getNationalInfo();	// 내/외국인 정보 (개발 가이드 참조)
		final String sCPRequestNum		= pClient.getCPRequestNO();		// CP 요청번호
		String result					= "fail";
		String resultCode				= "";
		String errorMsgCode 			= "";
		
		if (iRtn == 1) {
			if(this.comparePatientInfo(sReserved3, sName, sBirthDate)) {
				sRtnMsg = "정상 처리되었습니다.";
				result = "success";
			}
			else {
				sRtnMsg = "환자정보와 인증정보가 같지 않습니다.";
				result = "fail";
				errorMsgCode = "iPinCertification001";
			}
		}
		else if (iRtn == -1 || iRtn == -4) {
			sRtnMsg = "iRtn 값, 서버 환경정보를 정확히 확인하여 주시기 바랍니다.";
			errorMsgCode = "iPinCertification002";
		}
		else if (iRtn == -6) {
			sRtnMsg = "당사는 한글 charset 정보를 euc-kr 로 처리하고 있으니, euc-kr 에 대해서 허용해 주시기 바랍니다." +
						"한글 charset 정보가 명확하다면 ..<BR><B>iRtn 값, 서버 환경정보를 정확히 확인하여 메일로 요청해 주시기 바랍니다.";
			errorMsgCode = "iPinCertification003";
		}
		else if (iRtn == -9) {
			sRtnMsg = "입력값 오류 : fnResponse 함수 처리시, 필요한 파라미터값의 정보를 정확하게 입력해 주시기 바랍니다.";
			errorMsgCode = "iPinCertification004";
		}
		else if (iRtn == -12) {
			sRtnMsg = "CP 비밀번호 불일치 : IPIN 서비스 사이트 패스워드를 확인해 주시기 바랍니다.";
			errorMsgCode = "iPinCertification005";
		}
		else if (iRtn == -13) {
			sRtnMsg = "CP 요청번호 불일치 : 세션에 넣은 sCPRequest 데이타를 확인해 주시기 바랍니다.";
			errorMsgCode = "iPinCertification006";
		}
		else {
			sRtnMsg = "iRtn 값 확인 후, NICE평가정보 전산 담당자에게 문의해 주세요.";
			errorMsgCode = "iPinCertification007";
		}
		
		this.logger.debug("sRtnMsg : " + sRtnMsg + ", result : " + result);
		
		//에러가 발생한 경우 Exception 처리
		if(iRtn != 1 || result.equals("fail")) {
			this.logger.error("iPin인증 에러 iRtn : " + iRtn + ", resultCode : " + resultCode + ", Message : " + sRtnMsg);
		}
		
		model.addAttribute("sVNumber", sVNumber);
		model.addAttribute("sDupInfo", sDupInfo);
		//파라미터를 사용되는 Data만 Base64Encode
		model.addAttribute("sName", Base64ConvertUtil.base64Encode(sName));
		model.addAttribute("sAgeCode", Base64ConvertUtil.base64Encode(sAgeCode));
		model.addAttribute("sGenderCode", Base64ConvertUtil.base64Encode(sGenderCode));
		model.addAttribute("sBirthDate", Base64ConvertUtil.base64Encode(sBirthDate));
		model.addAttribute("sNationalInfo", sNationalInfo);
		model.addAttribute("sCPRequestNum", sCPRequestNum);
		model.addAttribute("sMessage", sRtnMsg);
		model.addAttribute("result", result);
		model.addAttribute("sReserved1", sReserved1);
		model.addAttribute("sReserved2", sReserved2);
		model.addAttribute("sReserved3", Base64ConvertUtil.base64Encode(sReserved3)); //사용자 ID로 사용되는 파라미터니까 Base64변환
		model.addAttribute("errorMsgCode",errorMsgCode);
		
		return model;
	}
	
	/**
	 * 인증기관 리턴 Data 특수문자 치환
	 * @param paramValue
	 * @param gubun
	 * @return
	 */
	private String requestReplace(String paramValue, String gubun) {
		String result = "";

		if (paramValue != null) {
			paramValue = paramValue.replaceAll("<", "&lt;").replaceAll(">",	"&gt;");
			paramValue = paramValue.replaceAll("\\*", "");
			paramValue = paramValue.replaceAll("\\?", "");
			paramValue = paramValue.replaceAll("\\[", "");
			paramValue = paramValue.replaceAll("\\{", "");
			paramValue = paramValue.replaceAll("\\(", "");
			paramValue = paramValue.replaceAll("\\)", "");
			paramValue = paramValue.replaceAll("\\^", "");
			paramValue = paramValue.replaceAll("\\$", "");
			paramValue = paramValue.replaceAll("'", "");
			paramValue = paramValue.replaceAll("@", "");
			paramValue = paramValue.replaceAll("%", "");
			paramValue = paramValue.replaceAll(";", "");
			paramValue = paramValue.replaceAll(":", "");
			paramValue = paramValue.replaceAll("-", "");
			paramValue = paramValue.replaceAll("#", "");
			paramValue = paramValue.replaceAll("--", "");
			paramValue = paramValue.replaceAll("-", "");
			paramValue = paramValue.replaceAll(",", "");
			
			if (!gubun.equals("encodeData")) {
				paramValue = paramValue.replaceAll("\\+", "");
				paramValue = paramValue.replaceAll("/", "");
				paramValue = paramValue.replaceAll("=", "");
			}
			
			result = paramValue;
		}
		
		return result;
	}	
	
	/**
	 * 인증정보와 기간계 환자 정보가 유효한지 확인
	 * @param pId
	 * @param pNm
	 * @param birthDt
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private boolean comparePatientInfo(String pId, String pName, String birthDt) {
		final Map<String, Object> responseMap = this.getPatientInfo(pId);	//ResponseUtil에 의해 result키에 결과가 담겨서 반환
		Map<String, Object> userMap = null;									//ResponseUtil에서 결과를 추출해서 담을 userMap;
		
		//여기까지 왔다는건 환자가 존재한다는 것이지만 혹시 모르니까 다시 검사
		if(ResponseUtil.isEmptyResult(responseMap)) {
			this.logger.debug("기간계에 환자정보가 존재하지 않습니다.");
			return false; 
		}
		
		// API 호출 결과 확인
		if (this.logger.isDebugEnabled()) {
			this.logger.debug("responseMap = " + responseMap.toString());
		}
		
		//responseMap에서 결과를 추출해서 userMap에 담
		userMap = (Map<String, Object>) responseMap.get(FrameworkConstants.UIRESPONSE.RESULT.getKey());
		String userName = (String)userMap.get("pName");
		String userBrith = (String)userMap.get("birthDt");
		String userPhoneNo = (String)userMap.get("cellphoneNo");
		
		// 이름이 다르면 땡! 
		if(pName == null || !pName.equals(userName)) {
			this.logger.debug("인증된 사용자 이름과 기간계에서 가져온 사용자의 이름이 다릅니다.");
			return false; 
		}
		// 생년월일이 다르면 떙! 
		if(birthDt == null || !birthDt.equals(userBrith)) {
			this.logger.debug("인증된 사용자의 생년월일과 기간계에서 가져온 사용자의 생녕월일이 다릅니다.");
			return false; 
		}
		// userPhoneNo가 null인 경우는 기간계에 핸드폰 번호가 등록되지 않은 경우
		if(userPhoneNo == null) {
			this.logger.debug("기간계에서 가져온 사용자의 핸드폰 번호가 존재하지 않습니다.");
			return false;
		}
		
		return true;
	}	
	
	

	/**
	 * SMS인증을 요청하고 인증 코드를 반환한다.
	 * @param pId 사용자가 입력한 환자번호 
	 * @param pName 사용자가 입력한 환자명 
	 * @param request
	 * @param model
	 * @return
	 * @throws MobileControllerException
	 */
	@SuppressWarnings("unchecked")
	public String reqSmsCertionfication(String pId, String pName, HttpServletRequest request, Model model) throws MobileControllerException{
		Map<String, Object> resultMap = null; 
			
		try { 
			// 환자정보 검색 
			resultMap = (Map<String, Object>) this.apiCallService.execute(PnuhApi.USER_USERINFO_GETUSERINFO, "pId", pId); 
		}
		catch(ApiCallException ex) { 
			if(this.logger.isDebugEnabled()) {
				this.logger.debug("예외발생", ex); 
			}
			return null; 
		}
			
		if(this.logger.isDebugEnabled()) { 
			this.logger.debug("검색된 환자 : " + resultMap);
		} 

		//입력된 이름하고 조회된 정보의 환자이름 하고 같나?? 
		if(pName != null && !pName.equals(resultMap.get("pName"))) {
			throw new MobileControllerException("mobile.message.smsCertification011", "환자번호의 환자명과 입력하신 환자명이 같지 않습니다.");
		}

		// SMS 전송을 위해 전화번호가 등록되었는지 여부 
		if(resultMap.get("cellphoneNo") == null || !resultMap.containsKey("cellphoneNo")) { 
			throw new MobileControllerException("mcare.error.no.cellphone.no", "핸드폰 번호가 등록되어 있지 않습니다. 원무과로 가서 휴대폰 번호를 확인하세요."); 
		}
		
		//Sms인증코드 전송을 요청하고 전송한 폰 번호 반환
		return this.reqSmsCode(pId, pName, request, (String) resultMap.get("cellphoneNo"));
	}

	/**
	 * 입력된 폰 번호로 SMS인증 코드를 전송하고 폰 번호를 ****처리 해서 반환
	 * @param user
	 * @param request
	 * @param cellPhoneNo
	 * @return
	 * @throws MobileControllerException
	 */
	public String reqSmsCode(String pId, String pName, HttpServletRequest request, String cellPhoneNo) throws MobileControllerException {
		//만약 SMS 인정번호 재전송 요청이 들어와서 기존 certificationCode가 남아있는 경우 기존 값을 제거
		if(request.getSession().getAttribute("smsCode") != null) {
			request.getSession().removeAttribute("smsCode");
		}
		
		// 환자가 확인되었으면, SMS 전송 
		String certiCode = null;
		String smsMessage = null;
		String sendPhoneNo = null;
		
		try {
			//demoPids의 계정은 Apple 테스트 계정으로 무조건 demoPhoneNumber로 demoCertCode로 설정된 인증번호가 SMS 전송됨.
			if(!MCareConstants.MCARE_TEST_USER.ACTIVATE) {
				certiCode = this.getSmsCertificationCode();
			}
			else {
				INFO testUser = MCareConstants.MCARE_TEST_USER.INFO.convert(pId); 
				
				// 테스트 환자가 아닌 경우 
				if(testUser == null) { 
					certiCode = this.getSmsCertificationCode();
				}
				// 테스트 환자인 경우 
				else {
					certiCode = testUser.getTempPassword(); 
				}
			}
			
			//SMS 전송 API를 위한 파라미터 설정
			smsMessage = messageService.getMessage("mobile.message.smsCertification012", request, new String[]{certiCode});
			//sms전송 요청 
			this.smsService.sendSms(pName, cellPhoneNo, smsMessage);
			request.getSession().setAttribute("smsCode", certiCode);
			sendPhoneNo = ConvertUtil.convertSecretPhoneNo(cellPhoneNo);
		} 
		catch(final Exception ex) {
			this.logger.error("SMS 전송 실패", ex);
			throw new MobileControllerException(ex);
		}
		
		return sendPhoneNo;
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public Map<String, String> certificationRequest(HttpServletRequest request, HttpServletResponse response) {
		final String reservedParam1  = this.requestReplace(request.getParameter("certType"), "");
		Map<String, String> map = null;
		if(reservedParam1.equals("checkPlus")) {
			map = this.checkPlusRequest(request);
		}
		else if (reservedParam1.equals("iPin")) {
			map = this.iPinRequest(request);
		}
		else if (reservedParam1.equals("sms")) {
			map = new HashMap<String, String>();
			map.put("result", "success");
		}
		return map;
	}
	
	/**
	 * 인증결과를 파라미터 매핑 시켜서 반환
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public Model certificationResult(HttpServletRequest request, Model model) {
		final String reservedParam1  = this.requestReplace(request.getParameter("param_r1"), "");
		
		if(reservedParam1.equals("checkPlus")) {
			model = this.checkPlusResult(request, model);
		}
		else if (reservedParam1.equals("iPin")) {
			model = this.iPinResult(request, model);
		}
		else if (reservedParam1.equals("sms")) {
			model.addAttribute("result", "success");
		}
		
		return model;
	}
	
	/**
	 * 인증된 사용자 정보를 Base64Decode하여 반환
	 * @param request
	 * @param model
	 * @return
	 */
	public Model getCertiUserInfo(HttpServletRequest request, Model model) {
		//이름을 Base64로 인코딩한 결과에 +가 포함될수 있는데 (예:홍길동)이 경우 +는 http get을 이용해서 전달할 수 없다. 
		//그래서 +가 공백으로 넘어오는데 이를 다시 복원해주기 위함이다.
		final String pName = Base64ConvertUtil.base64Decode(request.getParameter("pName").replace(" ", "+"));
		final String pId = Base64ConvertUtil.base64Decode(request.getParameter("reservedParam3"));
		model.addAttribute("pName", pName);
		model.addAttribute("pId", pId);
		
		//SMS인증에서는 사용되지 않는 파라미터
		if(request.getParameter("userBirthDate") != null) {
			final String userBirthDate = Base64ConvertUtil.base64Decode(request.getParameter("userBirthDate"));
			model.addAttribute("userBirthDate", userBirthDate);
		}
		
		//SMS인증에서는 사용되지 않는 파라미터
		if(request.getParameter("userGenderCode") != null) {
			final String userGenderCode = Base64ConvertUtil.base64Decode(request.getParameter("userGenderCode"));
			model.addAttribute("userGenderCode", userGenderCode);
		}
		
		return model;
	}
	
	/**
	 * SMS인증번호를 생성해서 반환한다.
	 * @return
	 */
	private String getSmsCertificationCode() {
		final int tmp_passwd = RandomUtils.nextInt();
		
		return Integer.toString(tmp_passwd).substring(0, 6);
	}	
}
