package com.dbs.mcare.service.api;

import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.dbs.mcare.framework.api.DefApi;
import com.dbs.mcare.framework.api.model.ApiModel.ApiType;
import com.dbs.mcare.framework.api.model.ApiModel.ResultType;
import com.dbs.mcare.service.api.parser.PnuhJsonParser;

/**
 * 부산대병원 Api들 
 * 나중에 병원이 바뀌면 여기부터 리팩토링 들어가면 되겠지? 
 * 
 * 
 * @author hyeeun 
 *
 */
public enum PnuhApi {
	// 호출 - 간호호출 
	CALL_NURSE(ApiType.SQL, "/call/nurse", ResultType.MAP), 
	// 호출 - 간호호출 - 이전호출 미처리 내역  
	CALL_NURSEPREVREQ(ApiType.SQL, "/call/nursePrevReq", ResultType.MAP), 
	
	// 가셔야할 곳 - 진료과 리스트 
	ROADLIST_GETORDERDEPT(ApiType.WEB_SERVICE, "/roadlist/getOrderDept", ResultType.LIST, PnuhJsonParser.class.getName()), 
	// 가셔야할 곳 - 가셔야할 곳 
	ROADLIST_GETVISITINFO(ApiType.WEB_SERVICE, "/roadlist/getVisitInfo", ResultType.LIST, PnuhJsonParser.class.getName()), 
	
	// 검사결과 - 검사결과대분류 
	TOTALEXAM_GETRESULTTOTAL(ApiType.WEB_SERVICE, "/totalexam/getResultTotal", ResultType.LIST, PnuhJsonParser.class.getName()), 
	// 검사결과 - 검사결과 상세 
	TOTALEXAM_GETRESULTDETAIL(ApiType.WEB_SERVICE, "/totalexam/getResultDetail", ResultType.LIST, PnuhJsonParser.class.getName()), 
	// 검사결과 - 혈당/키/몸무게 
	TOTALEXAM_GETPERSONHEALTHINFO(ApiType.WEB_SERVICE, "/totalexam/getPersonHealthInfo", ResultType.MAP), 
	
	// 대기시간조회 - 진료대기시간 조회 
	WAITTIME_GETWAITINGLIST(ApiType.WEB_SERVICE, "/waittime/getwaitingList", ResultType.LIST, PnuhJsonParser.class.getName()), 
	// 대기시간조회 - 도착확인 
	WAITTIME_REQARRIVEDCONFIRM(ApiType.WEB_SERVICE, "/waittime/reqArrivedConfirm", ResultType.MAP), 
	// 대기시간조회 - 도착확인대상목록 
	WAITTIME_ARRIVECONFIRMTARGETLIST(ApiType.WEB_SERVICE, "/waittime/arriveConfirmTargetList", ResultType.LIST, PnuhJsonParser.class.getName()), 
	
	// 동의서 - 최신 동의서 조회 
	AGREEMENT_GETNEWAGREEMENTLIST(ApiType.SQL, "/agreement/getNewAgreementList", ResultType.LIST), 
	// 동의서 - 14세 미만 사용자가 동의 해야할 최신 동의서 조회
	AGREEMENT_GETUNDER14NEWAGREEMENTLIST(ApiType.SQL, "/agreement/getUnder14NewAgreementList", ResultType.LIST),
	
	// 사용자관리 - 로그인실패횟수증가 
	USER_LOGIN_INCLOGINFAILCNT(ApiType.SQL, "/user/login/incLoginFailCnt", ResultType.MAP), 
	// 사용자관리 - 로그인 실패횟수 초기화 
	USER_LOGIN_CLEARLOGINFAILCNT(ApiType.SQL, "/user/login/clearLoginFailCnt", ResultType.MAP), 
	// 사용자관리 - 최근 로그인 조회 (배치) 
	USER_LOGIN_GETACCESSDAY(ApiType.SQL, "/user/login/getAccessDay", ResultType.LIST), 	
	
	// 사용자관리 - 문자발송 
	USER_PUTSMS(ApiType.SQL, "/user/putSms", ResultType.MAP), 
	
	// 사용자관리 - 비밀번호 변경 
	USER_PASSWORD_RESETPWD(ApiType.SQL, "/user/password/resetPWD", ResultType.MAP), 
	// 사용자관리 - 비밀번호 변경 
	USER_PASSWORD_TEMPRESETPWD(ApiType.SQL, "/user/password/tempResetPWD", ResultType.MAP), 
	// 사용자관리 - 비밀번호 확인 
	USER_PASSWORD_CHECKPWD(ApiType.SQL, "/user/password/checkPWD", ResultType.MAP), 
	/* 비슷한 api 구분선 ---------------------------------------------------------------------------------- */
	
	
	// 사용자관리 - 사용자 동의서 조회 
	USER_USERINFO_GETUSERAGREEMENTLIST(ApiType.SQL, "/user/userinfo/getUserAgreementList", ResultType.LIST), 
	// 사용자관리 - 사용자 등록정보 조회 
	USER_USERINFO_GETLOGININFO(ApiType.SQL, "/user/userinfo/getLoginInfo", ResultType.MAP), 
	// 사용자관리 - 환자번호 찾기 
	USER_USERINFO_FINDPID(ApiType.WEB_SERVICE, "/user/userinfo/findPid", ResultType.MAP), 
	// 사용자관리 - 환자정보조회 
	USER_USERINFO_GETUSERINFO(ApiType.WEB_SERVICE, "/user/userinfo/getUserInfo", ResultType.MAP), 
	// 사용자관리 - 환자번호목록조회 
	USER_USERINFO_GETPIDLIST(ApiType.SQL, "/user/userinfo/getPidList", ResultType.LIST), 
	/* 비슷한 api 구분선 ---------------------------------------------------------------------------------- */
	
	// 토큰기준으로 정보 가져오기 
	USER_USERINFO_GETDEVICETOKENUSERLIST(ApiType.SQL, "/user/userinfo/getDeviceTokenUserList", ResultType.LIST), 
	// 특정 토큰 삭제 
	USER_USERINFO_DELETEDEVICETOKENUSER(ApiType.SQL, "/user/userinfo/deleteDeviceTokenUser", ResultType.MAP), 
	// 등록된 토큰정보 가져오기 
	USER_USERINFO_USERTOKENS(ApiType.SQL, "/user/userinfo/userTokens", ResultType.LIST), 

	
	
	/* 비슷한 api 구분선 ---------------------------------------------------------------------------------- */
	
	// 토큰관리 - 디바이스 토큰저장 
	USER_TOKEN_SAVEDEVICETOKEN(ApiType.SQL, "/user/tokens/saveDeviceToken", ResultType.MAP), 
	// 토큰관리 - 토큰 로그인 시간갱신 
	USER_TOKENS_UPDATETOKENDATE(ApiType.SQL, "/user/tokens/updateTokenDate", ResultType.MAP), 		
	
	/* 비슷한 api 구분선 ---------------------------------------------------------------------------------- */
	
	
	// 앱관리 - 버전확인 - old 
	APP_CHECKAPPVERSION(ApiType.SQL, "/app/checkAppVersion", ResultType.MAP), 
	// 앱관리 - 버전확인 - new  
	APP_CHECKAPPNAMEVERSION(ApiType.SQL, "/app/checkAppNameVersion", ResultType.MAP), 
	
	// 전화번호 
	TELNO_GETLIST(ApiType.SQL, "/telno/getList", ResultType.LIST), 
	// 주차관리 - 차량번호 관리 
	PARK_CARNO(ApiType.WEB_SERVICE, "/park/carNo", ResultType.MAP), 
	
	// 진료이력조회 - 수진이력정보조회(외래) 
	HISTORY_GETOUTLIST(ApiType.WEB_SERVICE, "/history/getOutList", ResultType.LIST, PnuhJsonParser.class.getName()), 
	// 진료이력조회 - 수진이력정보조회(입원) 
	HISTORY_GETINLIST(ApiType.WEB_SERVICE, "/history/getInList", ResultType.LIST, PnuhJsonParser.class.getName()), 
	// 진료이력조회 - 진료비 상세내역 
	HISTORY_GETBILLDETAIL(ApiType.WEB_SERVICE, "/history/getBillDetail", ResultType.LIST, PnuhJsonParser.class.getName()), 
	// 진료이력조회 - 진료비 합계 
	HISTORY_GETBILLSUM(ApiType.WEB_SERVICE, "/history/getBillSum", ResultType.LIST, PnuhJsonParser.class.getName()), 
	
	// 처방조회 - 처방조회 
	PRESCRIPTION_GETPRESCLIST(ApiType.WEB_SERVICE, "/prescription/getPrescList", ResultType.LIST, PnuhJsonParser.class.getName()), 
	
	// 예약 - 인터넷예약내역조회 
	RESERVATION_GETREVINTERNET(ApiType.WEB_SERVICE, "/reservation/getRevInternet", ResultType.LIST, PnuhJsonParser.class.getName()), 
	// 예약 - 인터넷예약취소 
	RESERVATION_PUTRESCANCEL(ApiType.SQL, "/reservation/putResCancel", ResultType.MAP), 
	// 에약 - 내역조회 
	RESERVATION_GETREVLIST(ApiType.WEB_SERVICE, "/reservation/getRevList", ResultType.LIST, PnuhJsonParser.class.getName()), 
	// 예약 - 예약가능한 진료과 
	RESERVATION_GETREVDEPT(ApiType.WEB_SERVICE, "/reservation/getRevDept", ResultType.LIST, PnuhJsonParser.class.getName()), 
	// 예약 - 진료과에 속해앴는 의사 
	RESERVATION_GETREVDOC(ApiType.WEB_SERVICE, "/reservation/getRevDoc", ResultType.LIST, PnuhJsonParser.class.getName()), 
	// 예약 - 의사의 예약가능 날짜 
	RESERVATION_GETREVDATE(ApiType.WEB_SERVICE, "/reservation/getRevDate", ResultType.LIST, PnuhJsonParser.class.getName()), 
	// 예약 - 의사의 예약가능 날짜에 대한 시간 
	RESERVATION_GATEREVTIME(ApiType.WEB_SERVICE, "/reservation/getRevTime", ResultType.LIST, PnuhJsonParser.class.getName()), 
	// 예약 - 진료 예약
	RESERVATION_RESERVATION(ApiType.SQL, "/reservation/reservation", ResultType.MAP),
	
	// 번호표 발급기 - 순번기 목록 
	TICKET_LIST(ApiType.SQL, "/ticket/list", ResultType.LIST), 
	// 번호표 발급기 - 순번기 대상창구 목록 
	TICKET_DESKLIST(ApiType.SQL, "/ticket/deskList", ResultType.LIST), 
	// 번호표 발급기 - 재발급 유무
	TICKET_BALRECALL(ApiType.SQL, "/ticket/balrecall", ResultType.MAP), 
	// 번호표 발급기 - 번호표발급 요청
	TICKET_BAL(ApiType.PROCEDURE, "/ticket/bal", ResultType.MAP),
	//번호표 발급기 - 번호표발급 리스트
	TICKET_BALLIST(ApiType.SQL, "/ticket/ballist", ResultType.LIST),
	//번호표 발급기 - 번호표발급완료 여부확인
	TICKET_BALSUCCESS(ApiType.SQL, "/ticket/balsucess", ResultType.LIST),
	
	// 위치명 맵핑  
	POIMAPPING_MAPPINGLEGACY(ApiType.SQL, "/poimapping/mappingLegacy", ResultType.MAP), 
	
	// 도우미 - 화면에 보일 메시지 목록 
	HELPER_MESSAGELIST(ApiType.SQL, "/helper/messageList", ResultType.LIST), 
	// 도우미 - 메시지 삭제 (통계에서 사용됨) 
	HELPER_DELETECONTENTS(ApiType.SQL, "/helper/deleteContents", ResultType.MAP), 	
	// 도우미 - 도우미 메시지에 접근하기 
	HELPERPUSH_GETACTION(ApiType.SQL, "/helperpush/getAction", ResultType.MAP), 
	// 도우미 - 번호표발급필요알림 
	HELPERPUSH_TICKET(ApiType.SQL, "/helperpush/ticket", ResultType.MAP), 
	// 도우미 - 차량번호알림 
	HELPERPUSH_PARKING(ApiType.SQL, "/helperpush/parking", ResultType.MAP), 
	// 도우미 - 예약알림 
	HELPERPUSH_APPOINTMENTSEARCH(ApiType.SQL, "/helperpush/appointmentSearch", ResultType.MAP), 
	// 도우미 - 다음예약권유 
	HELPERPUSH_RESERVATION(ApiType.SQL, "/helperpush/reservation", ResultType.MAP), 
	// 도우미 - 도착알림 
	HELPERPUSH_ANNOUNCEARRIVAL(ApiType.SQL, "/helperpush/announceArrival", ResultType.MAP), 
	// 도우미 - 가셔야할 곳 
	HELPERPUSH_NEXTLOCATOIN(ApiType.SQL, "/helperpush/nextLocation", ResultType.MAP), 
	// PUSH - 탈퇴알림 
	HELPERPUSH_WITHDRAWAL(ApiType.SQL, "/helperpush/withdrawal", ResultType.MAP), 
	// 도우미 - 굿바이 메시지 
	HELPERPUSH_GOODBYE(ApiType.SQL, "/helperpush/goodbye", ResultType.MAP), 
	// 도우미 - 웰컴 메시지 
	HELPERPUSH_WELCOME(ApiType.SQL, "/helperpush/welcome", ResultType.MAP); 
	
	
	
	private DefApi defApi; 

	/**
	 * 생성자 
	 * @param apiType {@link com.dbs.mcare.framework.api.model.ApiModel.ApiType}
	 * @param apiUrl 카테고리를 포함한 API URL 
	 * @param resultType {@link com.dbs.mcare.framework.api.model.ApiModel.ResultType}
	 * @param parserClazz api 결과를 파싱할 파서의 클래스명 (full name) 
	 */
	private PnuhApi(ApiType apiType, String apiUrl, ResultType resultType, String parserClazzName) {
		try { 
			this.defApi = new DefApi(apiType, apiUrl, resultType, parserClazzName); 
		}
		catch(ClassNotFoundException ex) {
			LoggerFactory.getLogger(PnuhApi.class).error("개발기간에 fix되어야 하는 에러", ex);
		}
	}
	/**
	 * 생성자 
	 * @param apiType {@link com.dbs.mcare.framework.api.model.ApiModel.ApiType}
	 * @param apiUrl 카테고리를 포함한 API URL 
	 * @param resultType {@link com.dbs.mcare.framework.api.model.ApiModel.ResultType}
	 */
	private PnuhApi(ApiType apiType, String apiUrl, ResultType resultType) {
		this(apiType, apiUrl, resultType, null); 
	}
	/**
	 * API 정의 반환 
	 * @return
	 */
	public DefApi getDefApi() { 
		return this.defApi; 
	}
	/**
	 * Api유형 
	 * @return
	 */
	public ApiType getApiType() { 
		return this.defApi.getApiType(); 
	}
	/**
	 * Api URL 
	 * @return
	 */
	public String getApiUrl() { 
		return this.defApi.getApiUrl(); 
	}
	/**
	 * 결과가 list형인지 여부 
	 * @return
	 */
	public boolean isList() { 
		return this.defApi.isList(); 
	}
	/**
	 * apiUrl로 해당 api정의 순차검색  
	 * @param apiUrl 찾고싶은 apiUrl 
	 * @return 해당 api의 정의 
	 */
	public static PnuhApi searchByApiUrl(String apiUrl) { 
		if(StringUtils.isEmpty(apiUrl)) {
			return null; 
		}
	
		PnuhApi[] apis = PnuhApi.values(); 
		for(PnuhApi api : apis) { 
			if(apiUrl.equals(api.getApiUrl())) {
				return api;
			}
		}
		
		return null; 
	}
		
	@Override 
	public String toString() { 
		return this.defApi.toString(); 
	}
}
