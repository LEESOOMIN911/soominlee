package com.dbs.mcare.service.api;

import com.dbs.mcare.framework.api.model.ApiModel.ApiType;

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
	CALL_NURSE(ApiType.SQL, "/call/nurse", false), 
	// 호출 - 간호호출 - 이전호출 미처리 내역  
	CALL_NURSEPREVREQ(ApiType.SQL, "/call/nursePrevReq", false), 
	
	// 가셔야할 곳 - 진료과 리스트 
	ROADLIST_GETORDERDEPT(ApiType.WEB_SERVICE, "/roadlist/getOrderDept", true), 
	// 가셔야할 곳 - 가셔야할 곳 
	ROADLIST_GETVISITINFO(ApiType.WEB_SERVICE, "/roadlist/getVisitInfo", true), 
	
	// 검사결과 - 검사결과대분류 
	TOTALEXAM_GETRESULTTOTAL(ApiType.WEB_SERVICE, "/totalexam/getResultTotal", true), 
	// 검사결과 - 검사결과 상세 
	TOTALEXAM_GETRESULTDETAIL(ApiType.WEB_SERVICE, "/totalexam/getResultDetail", true), 
	// 검사결과 - 혈당/키/몸무게 
	TOTALEXAM_GETPERSONHEALTHINFO(ApiType.WEB_SERVICE, "/totalexam/getPersonHealthInfo", false), 
	
	// 대기시간조회 - 진료대기시간 조회 
	WAITTIME_GETWAITINGLIST(ApiType.WEB_SERVICE, "/waittime/getwaitingList", true), 
	// 대기시간조회 - 도착확인 
	WAITTIME_REQARRIVEDCONFIRM(ApiType.WEB_SERVICE, "/waittime/reqArrivedConfirm", false), 
	// 대기시간조회 - 도착확인대상목록 
	WAITTIME_ARRIVECONFIRMTARGETLIST(ApiType.WEB_SERVICE, "/waittime/arriveConfirmTargetList", true), 
	
	// 동의서 - 최신 동의서 조회 
	AGREEMENT_GETNEWAGREEMENTLIST(ApiType.SQL, "/agreement/getNewAgreementList", true), 
	// 동의서 - 14세 미만 사용자가 동의 해야할 최신 동의서 조회
	AGREEMENT_GETUNDER14NEWAGREEMENTLIST(ApiType.SQL, "/agreement/getUnder14NewAgreementList", true),
	
	// 사용자관리 - 로그인실패횟수증가 
	USER_LOGIN_INCLOGINFAILCNT(ApiType.SQL, "/user/login/incLoginFailCnt", false), 
	// 사용자관리 - 로그인 실패횟수 초기화 
	USER_LOGIN_CLEARLOGINFAILCNT(ApiType.SQL, "/user/login/clearLoginFailCnt", false), 
	// 사용자관리 - 최근 로그인 조회 (배치) 
	USER_LOGIN_GETACCESSDAY(ApiType.SQL, "/user/login/getAccessDay", true), 	
	
	// 사용자관리 - 문자발송 
	USER_PUTSMS(ApiType.SQL, "/user/putSms", false), 
	
	// 사용자관리 - 비밀번호 변경 
	USER_PASSWORD_RESETPWD(ApiType.SQL, "/user/password/resetPWD", false), 
	// 사용자관리 - 비밀번호 변경 
	USER_PASSWORD_TEMPRESETPWD(ApiType.SQL, "/user/password/tempResetPWD", false), 
	// 사용자관리 - 비밀번호 확인 
	USER_PASSWORD_CHECKPWD(ApiType.SQL, "/user/password/checkPWD", false), 
	/* 비슷한 api 구분선 ---------------------------------------------------------------------------------- */
	
	
	// 사용자관리 - 사용자 동의서 조회 
	USER_USERINFO_GETUSERAGREEMENTLIST(ApiType.SQL, "/user/userinfo/getUserAgreementList", true), 
	// 사용자관리 - 사용자 등록정보 조회 
	USER_USERINFO_GETLOGININFO(ApiType.SQL, "/user/userinfo/getLoginInfo", false), 
	// 사용자관리 - 환자번호 찾기 
	USER_USERINFO_FINDPID(ApiType.WEB_SERVICE, "/user/userinfo/findPid", false), 
	// 사용자관리 - 환자정보조회 
	USER_USERINFO_GETUSERINFO(ApiType.WEB_SERVICE, "/user/userinfo/getUserInfo", false), 
	// 사용자관리 - 환자번호목록조회 
	USER_USERINFO_GETPIDLIST(ApiType.SQL, "/user/userinfo/getPidList", true), 
	/* 비슷한 api 구분선 ---------------------------------------------------------------------------------- */
	
	// 토큰기준으로 정보 가져오기 
	USER_USERINFO_GETDEVICETOKENUSERLIST(ApiType.SQL, "/user/userinfo/getDeviceTokenUserList", true), 
	// 특정 토큰 삭제 
	USER_USERINFO_DELETEDEVICETOKENUSER(ApiType.SQL, "/user/userinfo/deleteDeviceTokenUser", false), 
	// 등록된 토큰정보 가져오기 
	USER_USERINFO_USERTOKENS(ApiType.SQL, "/user/userinfo/userTokens", true), 

	
	
	/* 비슷한 api 구분선 ---------------------------------------------------------------------------------- */
	
	// 토큰관리 - 디바이스 토큰저장 
	USER_TOKEN_SAVEDEVICETOKEN(ApiType.SQL, "/user/tokens/saveDeviceToken", false), 
//	// 토큰관리 - 디바이스 토큰확인 
//	USER_TOKEN_CHECKDEVICETOKEN(ApiType.SQL, "/user/tokens/checkDeviceToken", false), 	
	// 토큰관리 - 토큰 로그인 시간갱신 
	USER_TOKENS_UPDATETOKENDATE(ApiType.SQL, "/user/tokens/updateTokenDate", false), 		
	
	/* 비슷한 api 구분선 ---------------------------------------------------------------------------------- */
	
	
	// 앱관리 - 버전확인 - old 
	APP_CHECKAPPVERSION(ApiType.SQL, "/app/checkAppVersion", false), 
	// 앱관리 - 버전확인 - new  
	APP_CHECKAPPNAMEVERSION(ApiType.SQL, "/app/checkAppNameVersion", false), 
	
	// 전화번호 
	TELNO_GETLIST(ApiType.SQL, "/telno/getList", true), 
	// 주차관리 - 차량번호 관리 
	PARK_CARNO(ApiType.WEB_SERVICE, "/park/carNo", false), 
	
	// 진료이력조회 - 수진이력정보조회(외래) 
	HISTORY_GETOUTLIST(ApiType.WEB_SERVICE, "/history/getOutList", true), 
	// 진료이력조회 - 수진이력정보조회(입원) 
	HISTORY_GETINLIST(ApiType.WEB_SERVICE, "/history/getInList", true), 
	// 진료이력조회 - 진료비 상세내역 
	HISTORY_GETBILLDETAIL(ApiType.WEB_SERVICE, "/history/getBillDetail", true), 
	// 진료이력조회 - 진료비 합계 
	HISTORY_GETBILLSUM(ApiType.WEB_SERVICE, "/history/getBillSum", true), 
	
	// 처방조회 - 처방조회 
	PRESCRIPTION_GETPRESCLIST(ApiType.WEB_SERVICE, "/prescription/getPrescList", true), 
	
	// 예약 - 인터넷예약내역조회 
	RESERVATION_GETREVINTERNET(ApiType.WEB_SERVICE, "/reservation/getRevInternet", true), 
	// 예약 - 인터넷예약취소 
	RESERVATION_PUTRESCANCEL(ApiType.SQL, "/reservation/putResCancel", false), 
	// 에약 - 내역조회 
	RESERVATION_GETREVLIST(ApiType.WEB_SERVICE, "/reservation/getRevList", true), 
	// 예약 - 예약가능한 진료과 
	RESERVATION_GETREVDEPT(ApiType.WEB_SERVICE, "/reservation/getRevDept", true), 
	// 예약 - 진료과에 속해앴는 의사 
	RESERVATION_GETREVDOC(ApiType.WEB_SERVICE, "/reservation/getRevDoc", true), 
	// 예약 - 의사의 예약가능 날짜 
	RESERVATION_GETREVDATE(ApiType.WEB_SERVICE, "/reservation/getRevDate", true), 
	// 예약 - 의사의 예약가능 날짜에 대한 시간 
	RESERVATION_GATEREVTIME(ApiType.WEB_SERVICE, "/reservation/getRevTime", true), 
	// 예약 - 진료 예약
	RESERVATION_RESERVATION(ApiType.SQL, "/reservation/reservation", false),
	
	// 번호표 발급기 - 순번기 목록 
	TICKET_LIST(ApiType.SQL, "/ticket/list", true), 
	// 번호표 발급기 - 순번기 대상창구 목록 
	TICKET_DESKLIST(ApiType.SQL, "/ticket/deskList", true), 
	// 번호표 발급기 - 재발급 유무
	TICKET_BALRECALL(ApiType.SQL, "/ticket/balrecall", false), 
	// 번호표 발급기 - 번호표발급 요청
	TICKET_BAL(ApiType.PROCEDURE, "/ticket/bal",false),
	//번호표 발급기 - 번호표발급 리스트
	TICKET_BALLIST(ApiType.SQL, "/ticket/ballist", true),
	//번호표 발급기 - 번호표발급완료 여부확인
	TICKET_BALSUCCESS(ApiType.SQL, "/ticket/balsucess", true),
	
	// 위치명 맵핑  
	POIMAPPING_MAPPINGLEGACY(ApiType.SQL, "/poimapping/mappingLegacy", false), 
	
	// 도우미 - 화면에 보일 메시지 목록 
	HELPER_MESSAGELIST(ApiType.SQL, "/helper/messageList", true), 
	// 도우미 - 메시지 삭제 (통계에서 사용됨) 
	HELPER_DELETECONTENTS(ApiType.SQL, "/helper/deleteContents", false), 	
	// 도우미 - 도우미 메시지에 접근하기 
	HELPERPUSH_GETACTION(ApiType.SQL, "/helperpush/getAction", false), 
	// 도우미 - 번호표발급필요알림 
	HELPERPUSH_TICKET(ApiType.SQL, "/helperpush/ticket", false), 
	// 도우미 - 차량번호알림 
	HELPERPUSH_PARKING(ApiType.SQL, "/helperpush/parking", false), 
	// 도우미 - 예약알림 
	HELPERPUSH_APPOINTMENTSEARCH(ApiType.SQL, "/helperpush/appointmentSearch", false), 
	// 도우미 - 다음예약권유 
	HELPERPUSH_RESERVATION(ApiType.SQL, "/helperpush/reservation", false), 
	// 도우미 - 도착알림 
	HELPERPUSH_ANNOUNCEARRIVAL(ApiType.SQL, "/helperpush/announceArrival", false), 
	// 도우미 - 가셔야할 곳 
	HELPERPUSH_NEXTLOCATOIN(ApiType.SQL, "/helperpush/nextLocation", false), 
	// PUSH - 탈퇴알림 
	HELPERPUSH_WITHDRAWAL(ApiType.SQL, "/helperpush/withdrawal", false), 
	// 도우미 - 굿바이 메시지 
	HELPERPUSH_GOODBYE(ApiType.SQL, "/helperpush/goodbye", false), 
	// 도우미 - 웰컴 메시지 
	HELPERPUSH_WELCOME(ApiType.SQL, "/helperpush/welcome", false); 
	
	
	
	
	private ApiType apiType; 
	private String apiUrl; 
	// 결과가 list인지 여부 
	private boolean bList; 

	/**
	 * 생성자 
	 * @param apiType
	 * @param apiUrl
	 * @param bList 결과가 리스트인지 여부 
	 */
	private PnuhApi(ApiType apiType, String apiUrl, boolean bList) {
		this.apiType = apiType; 
		this.apiUrl = apiUrl; 
		this.bList = bList; 
	}
	/**
	 * Api유형 
	 * @return
	 */
	public ApiType getApiType() { 
		return this.apiType; 
	}
	/**
	 * Api URL 
	 * @return
	 */
	public String getApiUrl() { 
		return this.apiUrl; 
	}
	/**
	 * 결과가 list형인지 여부 
	 * @return
	 */
	public boolean isList() { 
		return this.bList; 
	}
}
