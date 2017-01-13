package com.dbs.mcare.service.api.util;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.StringUtils;

import com.dbs.mcare.framework.util.ConvertUtil;
import com.dbs.mcare.framework.util.SessionUtil;

/**
 * 파라미터 맵핑 유틸 
 * @author DBS 
 *
 */
public class ParamMappingUtil {
	/**
	 * 요청 파라미터 정리 
	 * @param request 요청객체 
	 * @param map controller에서 요청을 맵으로 받은 경우 
	 * @return
	 */
	public static Map<String, Object> requestParam(HttpServletRequest request, Map<String, Object> map) { 
		Map<String, Object> reqParam = null;
		
		if(map == null) { 
			reqParam = new HashMap<String, Object>(); 
		}
		else {
			reqParam = map; 
		}
		
		// 요청 파라미터 가져오고 
		final Enumeration<?> parameterNames = request.getParameterNames();
		while (parameterNames.hasMoreElements()) {
			final String name = (String) parameterNames.nextElement();
			final String value = request.getParameter(name);
			reqParam.put(name, value);
		}

		// 세션의 속성 가져와서 덮어쓰기 --> 모두 가져와서 덮어쓸 필요가 있는지 확인 필요 
//		final HttpSession session = request.getSession();
//		@SuppressWarnings("rawtypes")
//		final Enumeration attributeNames = session.getAttributeNames();
//		while (attributeNames.hasMoreElements()) {
//			final String name = (String) attributeNames.nextElement();
//			reqParam.put(name, session.getAttribute(name));
//		}		
		
		// 현재 세션에서 가져올 값들 
		reqParam.put("pId", SessionUtil.getUserId(request)); 
		reqParam.put("pName", SessionUtil.getUserName(request)); 
		
		return reqParam; 
	}
	
	
	/*== 여기부터 땜빵 == */
	/**
	 * 버전체크 
	 * @param map
	 * @return
	 */
	public static Map<String, Object> _app_checkAppNameVersion(Map<String, Object> map) { 
		// camelCase가 적용되지 않아야 함. 
		// 왜냐면, 배포된 앱에서 camelCase가 적용되지 않은 형태로 버전을 체크하기 때문임 
		
		// 2016.09.08.kh2un 
		// Android 버전업을 하면서 기존에 대문자 내려가는 것에 덧붙여 소문자도 내려준다. 
		// 왜냐면, iOS는 심사가 번거로워서 그냥 두기로 했기 때문임 
		// 나중에 iOS도 수정하게 되면 이 메소드 자체를 없애면 됨 
		
		Map<String, Object> camelCaseMap = ConvertUtil.convertCamelCaseMap(map); 
		// 기존꺼 유지하고 추가가 맞음. 
		map.putAll(camelCaseMap);
		
		return map; 
	}
	/*== 여기까지 땜빵 == */
	
	/**
	 * 전화번호 
	 * @param map
	 * @return
	 */
	public static Map<String, Object> _telno_getList(Map<String, Object> map) { 
		return ConvertUtil.convertCamelCaseMap(map); 
	}
	
	
	/**
	 * 외래 진료이력 (수진이력) 
	 * @param map
	 * @return
	 */
	public static Map<String, Object> _history_getOutList(Map<String, Object> map) { 
		final Map<String, Object> resultMap = new HashMap<String, Object>(); 
		
		// 접수번호 
		resultMap.put("receiptNo", map.get("rcpn_no")); 
		// 환자번호 
		resultMap.put("pId", map.get("pid")); 
		// 예약접수구분코드 
		resultMap.put("receiptGubunCd", map.get("rcpn_apnt_kind_cd")); 
		// 예약접수구분명 
		resultMap.put("receiptGubunNm", map.get("rcpn_apnt_kind_nm")); 
		// 진료일자 
		resultMap.put("date", map.get("mdcr_date")); 
		// 진료시간 
		resultMap.put("time", map.get("mdcr_hm")); 
		// 진료과코드 
		resultMap.put("departmentCd", map.get("mdcr_dept_cd")); 
		// 진료과명 
		resultMap.put("departmentNm", map.get("mdcr_dept_nm")); 
		// 진료의 
		resultMap.put("doctorNm", map.get("mdcr_dr_nm")); 
		
		return resultMap; 
	}
	/**
	 * 진료이력 (입원) 
	 * @param map
	 * @return
	 */
	public static Map<String, Object> _history_getInList(Map<String, Object> map) { 
		final Map<String, Object> resultMap = new HashMap<String, Object>(); 
		
		// 접수번호 
		resultMap.put("receiptNo", map.get("rcpn_no")); 
		// 환자번호 
		resultMap.put("pId", map.get("pid")); 
		// 입원일 
		resultMap.put("admissionDt", map.get("adms_date")); 
		// 퇴원일 
		resultMap.put("dischargeDt", map.get("dsch_date")); 
		// 진료과코드 
		resultMap.put("departmentCd", map.get("mdcr_dept_cd")); 
		// 진료과명 
		resultMap.put("departmentNm", map.get("mdcr_dept_nm")); 
		// 주치의 
		resultMap.put("attendDoctorNm", map.get("atnd_dr_nm")); 
		
		return resultMap; 
	}
	
	/**
	 * 진료이력 (진료비합계) 
	 */
	public static Map<String, Object> _history_getBillSum(Map<String, Object> map) { 
		final Map<String, Object> resultMap = new HashMap<String, Object>(); 
		
		// 영수증번호 
		resultMap.put("billNo", map.get("bill_sqno")); 
		// 접수번호 
		resultMap.put("receiptNo", map.get("rcpn_no")); 
		// 수납번호 
		resultMap.put("billReceiptNo", map.get("rcpt_no")); 
		// 환자번호 
		resultMap.put("pId", map.get("pid")); 
		// 수납일자 
		resultMap.put("receiptDt", map.get("rcpt_date")); 
		// 주부여부 
		resultMap.put("mainSubCd", map.get("main_sub_typecd")); 
		// 진료과코드 
		resultMap.put("departmentCd", map.get("mdcr_dept_cd")); 
		// 진료과명 
		resultMap.put("departmentNm", map.get("mdcr_dept_nm")); 
		// 총진료비 
		resultMap.put("totalAmt", map.get("tot_amt")); 
		// 공단부담금 
		resultMap.put("insuranceAmt", map.get("insr_shar_amt")); 
		// 본인부담금 + 부가세 
		resultMap.put("patientAmt", map.get("tot_usch_amt")); 
		// 수납금액(환자가 낸 금액) 
		resultMap.put("receiptAmt", map.get("rcpt_amt")); 
		// 미수금 
		resultMap.put("outstandingAmt", map.get("uncl_amt")); 
		
		
		return resultMap; 
	}
	
	/**
	 * 진료이력조회 (진료비 상세내역) 
	 * @param map
	 * @return
	 */
	public static Map<String, Object> _history_getBillDetail(Map<String, Object> map) { 
		final Map<String, Object> resultMap = new HashMap<String, Object>(); 
		
		// 환자번호 
		resultMap.put("pId", map.get("pid")); 
		// 주부유형 
		resultMap.put("mainSubCd", map.get("main_sub_typecd")); 
		// 누적대분류코드 
		resultMap.put("accumulationLclsCd", map.get("acml_lcls_cd")); 
		// 누적대분류명 
		resultMap.put("accumulationLclsNm", map.get("acml_lcls_nm")); 
		// 본인부담금 (급여) 
		resultMap.put("patientAmt", map.get("pay_usch_amt")); 
		// 공단부담금 (급여) 
		resultMap.put("insuranceAmt", map.get("pay_insr_shar_amt")); 
		// 전액본인부담 (급여) 
		resultMap.put("fullPatientAmt", map.get("flam_piqs_usch_amt")); 
		// 선택진료료 (비급여) 
		resultMap.put("selectMedicalAmt", map.get("smcr_nopy_usch_amt")); 
		// 선택진료료이외 (비급여) 
		resultMap.put("exceptSelectMedicalAmt", map.get("clcl_nopy_usch_amt")); 
		// 총발생액 
		resultMap.put("totalAmt", map.get("tot_amt")); 
		
		return resultMap; 
	}
	
	/**
	 * 예약조회 
	 * @param map
	 * @return
	 */
	public static Map<String, Object> _reserva_getRevList(Map<String, Object> map) { 
		final Map<String, Object> resultMap = new HashMap<String, Object>(); 
		
		// 접수번호 
		resultMap.put("receiptNo", map.get("rcpn_no")); 
		// 접수 및 예약종류 코드 
		resultMap.put("receiptGubunKindCd", map.get("rcpn_apnt_kind_cd")); 
		// 접수 및 예약종류명 
		resultMap.put("receiptGubunKindNm", map.get("rcpn_apnt_kind_nm")); 
		// 진료일자 
		resultMap.put("date", map.get("mdcr_date")); 
		// 진료시간 
		resultMap.put("time", map.get("mdcr_hm")); 
		// 진료과 코드 
		resultMap.put("departmentCd", map.get("mdcr_dept_cd")); 
		// 진료의사명 
		resultMap.put("doctorNm", map.get("mdcr_dr_nm")); 
		// 진료과명 
		resultMap.put("departmentNm", map.get("mdcr_dept_nm")); 
		// 상태 
		resultMap.put("status", map.get("hstr_stat_cdnm")); 
		
		return resultMap; 
	}
	
	/**
	 * 진료대기시간조회 결과 맵핑 
	 * @param map
	 * @return
	 */
	public static Map<String, Object> _waittime_getwaitingList(Map<String, Object> map) { 
		final Map<String, Object> resultMap = new HashMap<String, Object>(); 
		
		// 진료일자 
		resultMap.put("date", map.get("mdcr_date")); 
		// 진료시간 
		resultMap.put("time", map.get("mdcr_hm")); 
		// 접수예약구분 
		resultMap.put("receiptGubun", map.get("rcpn_apnt_kind_cdnm")); 
		// 진료여부 
		resultMap.put("treatmentYn", map.get("mdcr_yn")); 
		// 진찰료수납여부 
		resultMap.put("consultationFreeYn", map.get("real_mcch_rcpt_yn")); 
		// 진료과 코드 
		resultMap.put("departmentCd", map.get("mdcr_dept_cd")); 
		// 진료과명 
		resultMap.put("departmentNm", map.get("mdcr_dept_nm")); 
		// 진료의사코드 
		resultMap.put("doctorId", map.get("mdcr_dr_id")); 
		// 진료의사명 
		resultMap.put("doctorNm", map.get("mdcr_dr_nm")); 
		// 진료진행상태 
		resultMap.put("progressStatusNm", map.get("mdcr_prgr_stat_nm")); 
		// 환자도착일시 
		resultMap.put("pArrivalDt", map.get("pr_arvl_dt")); 
		// 클리닉 종류 
		resultMap.put("clinicCd", map.get("clcn_kind_cd")); 
		// 센터구분 
		resultMap.put("centerCd", map.get("cntr_kind_cd")); 
		// 클리닉종류명 
		resultMap.put("clinicNm", map.get("clcn_kind_nm")); 
		// 센터구분명 
		resultMap.put("centerNm", map.get("cntr_kind_nm")); 
		// 계산상태 
		resultMap.put("receiptStatus", map.get("clcl_stat_nm")); 
		// 대기순번 
		resultMap.put("waitingNo", map.get("wtng_sqno")); 
		//순서
		resultMap.put("ranks", map.get("ranks") );
		//확인시간
		resultMap.put("checkTime", map.get("pt_arvl_dt"));
		return resultMap; 
	}
	
	/**
	 * 도착확인 대상이 되는 항목 변환 
	 * 예약된 목록 중에서 도착확인 대상이 있으므로 예약목록 부분을 재활용함 
	 * @param map
	 * @return
	 */
	public static Map<String, Object> _waittime_arriveConfirmTargetList(Map<String, Object> map) { 
		return ParamMappingUtil._reservation_getRevList(map); 
	}
	/**
	 * 도착확인 처리 결과 
	 * @param map
	 * @return
	 */
	public static Map<String, Object> _waittime_reqArrivedConfirm(Map<String, Object> map) { 
		final Map<String, Object> resultMap = new HashMap<String, Object>(); 
		
		// TODO 맞는지 모르겠음 
		resultMap.put("result", map.get("rows_rslt_code")); 
		
		
		return resultMap; 
	}
	
	/**
	 * 환자정보 조회 맵핑 
	 * @param map
	 * @return
	 */
	public static Map<String, Object> _user_userinfo_findPid(Map<String, Object> map) { 
		final Map<String, Object> resultMap = new HashMap<String, Object>(); 
		
		// 생일 
		resultMap.put("birthDt", map.get("dobr")); 
		// 이름 
		resultMap.put("pName", map.get("pt_nm")); 
		// 전화번호  
		if(map.get("clph_no") instanceof String) { 
			// 정상적으로 값이 있을때만 String 형태로 넘어옴 
			resultMap.put("cellphoneNo", map.get("clph_no")); 
		}
		
		// 환자번호 
		resultMap.put("pId", map.get("pid")); 
		// 성별 
		resultMap.put("genderCd", map.get("pt_srrn")); 
		// 재원여부 (입원해 있는지 여부). 입원한 사용자만 간호호출 가능함 
		if(map.get("sths_yn") instanceof String) { 
			// 정상적으로 값이 있을때만 String 형태로 넘어옴 
			resultMap.put("inHospitalYn", map.get("sths_yn")); 
		}		
		// 차량번호 
		if(map.get("vhcl_no") instanceof String) { 
			// 정상적으로 값이 있을때만 String 형태로 넘어옴 
			resultMap.put("vehicleNo", map.get("vhcl_no")); 
		}
		// 주소 
		if(map.get("pt_addr") instanceof String) { 
			// 정상적으로 값이 있을때만 String 형태로 넘어옴 
			resultMap.put("address", map.get("pt_addr")); 
		}
		// 우편번호 
		if(map.get("pstl_no") instanceof String) { 
			// 정상적으로 값이 있을때만 String 형태로 넘어옴 
			resultMap.put("zipCode", map.get("pstl_no")); 
		}
		
		return resultMap; 
	}
	/**
	 * 환자정보 조회 맵핑 
	 * @param map
	 * @return
	 */
	public static Map<String, Object> _user_userinfo_getUserInfo(Map<String, Object> map) { 
		// 동일한 API에 파라미터만 다르게 해서 호출하는 경우이기 때문에 맵핑 함수도 같은걸 이용한다. 
		return _user_userinfo_findPid(map); 
	}
	
	/**
	 * 최신 동의서 목록 조회 맵핑 
	 * @param map
	 * @return
	 */
	public static Map<String, Object> _agreement_getNewAgreementList(Map<String, Object> map) { 
		final Map<String, Object> resultMap = new HashMap<String, Object>(); 
		
		// 동의서 seq 
		resultMap.put("agreementSeq", map.get("AGREEMENT_SEQ")); 
		// 동의서 Id 
		resultMap.put("agreementId", map.get("AGREEMENT_ID")); 
		// 동의서 버전
		resultMap.put("versionNumber", map.get("VERSION_NUMBER")); 
		// 동의서 순서
		resultMap.put("agreementOrder", map.get("AGREEMENT_ORDER")); 
		// 동의서 제목 
		resultMap.put("agreementName", map.get("AGREEMENT_NAME")); 
		// 동의서 내용 
		resultMap.put("agreementCl", map.get("AGREEMENT_CL")); 
		// 동의서 사용 여부 
		resultMap.put("enabledYn", map.get("ENABLED_YN"));
		// 필수 동의서 여부
		resultMap.put("requiredYn", map.get("REQUIRED_YN"));
		// 최신 동의서 여부
		resultMap.put("newYn", map.get("NEW_YN")); 
		// 동의서 타입
		resultMap.put("typeName", map.get("TYPE_NAME"));
		return resultMap; 
	}
	
	/**
	 * 사용자 동의서 목록 조회 맵핑 
	 * @param map
	 * @return
	 */
	public static Map<String, Object> _user_userinfo_getUserAgreementList(Map<String, Object> map) { 
		final Map<String, Object> resultMap = _agreement_getNewAgreementList(map);
		//사용자 동의서 목록 조회는 최신 동의서 목록 조회 컬럼에서 동의 여부인 AGREEMENT_YN만 추가하면 되니까.
		resultMap.put("agreementYn", map.get("AGREEMENT_YN")); 
		
		return resultMap; 
	}
	
	/**
	 * 14세 미만 사용자 동의서 목록 조회 여부
	 * _agreement_getNewAgreementList와 같은 테이블을 조회하며 WHERE 조건만 다름 
	 * @param map
	 * @return
	 */
	public static Map<String, Object> _agreement_getUnder14NewAgreementList(Map<String, Object> map) {
		return _agreement_getNewAgreementList(map);
	}
	
	/**
	 * 간호호출 - 이전 호출 중 미처리 내역 
	 * @param map
	 * @return
	 */
	public static Map<String, Object> _call_nursePrevReq(Map<String, Object> map) { 
		final Map<String, Object> resultMap = new HashMap<String, Object>(); 
		
		// 환자번호 
		resultMap.put("pId", map.get("P_ID")); 
		// 요청내역 숫자  
		resultMap.put("reqCodeValue", map.get("REQ_CODE_VALUE")); 
		// 요청내역 문자열   
		resultMap.put("reqValue", map.get("REQ_VALUE"));
		// 기타 요청 내역
		resultMap.put("reqEtcValue", map.get("REQ_ETC_VALUE"));
		// 등록일  
		resultMap.put("regDt", map.get("REG_DT")); 
		// 갱신일 
		resultMap.put("updateDt", map.get("UPDATE_DT")); 
		
		return resultMap; 		
	}	
	
	/**
	 * 처방조회 
	 * @param map
	 * @return
	 */
	public static Map<String, Object> _prescription_getPrescList(Map<String, Object> map) { 
		final Map<String, Object> resultMap = new HashMap<String, Object>(); 
		
		// 환자번호 
		resultMap.put("pId", map.get("pid")); 
		// 접수번호 
		resultMap.put("receiptNo", map.get("rcpn_no")); 
		// 환자명 
		resultMap.put("pNm", map.get("pt_nm")); 
		// 진료과코드 
		resultMap.put("departmentCd", map.get("mdcr_dept_cd")); 
		// 진료과명 
		resultMap.put("departmentNm", map.get("mdcr_dept_nm")); 
		// 처방일 
		resultMap.put("prescriptionDt", map.get("prsc_date")); 
		// 약품명 
		resultMap.put("medicineNm", map.get("mdpr_enm")); 
		// 처방량 
		resultMap.put("dose", map.get("prsc_dosg")); 
		// 횟수 
		resultMap.put("doseTm", map.get("prsc_notm")); 
		// 일수 
		resultMap.put("doseDay", map.get("prsc_nody")); 
		// 용법명 
		resultMap.put("usageNm", map.get("iotm_nm")); 
		// 효능 
		resultMap.put("effectNm", map.get("effc_nm")); 
		// 효능분류 
		resultMap.put("effectCategoryNm", map.get("kims_clsf_dvnm")); 
		
		return resultMap; 
	}
	
	/**
	 * 검사결과조회 - 대분류 
	 * @param map
	 * @return
	 */
	public static Map<String, Object> _totalexam_getResultTotal(Map<String, Object> map) { 
		final Map<String, Object> resultMap = new HashMap<String, Object>(); 
		
		resultMap.put("prescriptionNo", map.get("prsc_sqno")); 
		
		return resultMap; 
	}
	
	/**
	 * 검사결과조회 - 상세 
	 * @param map
	 * @return
	 */
	public static Map<String, Object> _totalexam_getResultDetail(Map<String, Object> map) { 
		final Map<String, Object> resultMap = new HashMap<String, Object>(); 
		
		// 검사분류 
		resultMap.put("examGrpNm", map.get("exmn_grp_nm")); 
		// 검사명 
		resultMap.put("examNm", map.get("exmn_nm")); 
		// 검사결과 
		resultMap.put("examResult", map.get("rslt_valu")); 
		// 검사날짜 
		resultMap.put("date", map.get("prsc_date")); 
		// 참고치 
		resultMap.put("referenceVal", map.get("rfvl")); 
		// 단위 
		resultMap.put("unit", map.get("rslt_uncd")); 
		// 하이/로우 
		resultMap.put("maxMin", map.get("nrml_dvcd")); 
		// 이전결과값 
		resultMap.put("preResultVal", map.get("frtm_rslt")); 
		
		return resultMap; 
	}
	
	/**
	 * 예약 - 인터넷 예약 리스트 
	 * @param map
	 * @return
	 */
	public static Map<String, Object> _reservation_getRevInternet(Map<String, Object> map) { 
		final Map<String, Object> resultMap = new HashMap<String, Object>(); 
		
		// 접수 및 예약 종류 코드 
		resultMap.put("receiptGubunKindCd", map.get("rcpn_apnt_kind_cd")); 		
		// 진료과명 
		resultMap.put("departmentNm", map.get("mdcr_dept_nm")); 
		// 진료 의사명 
		resultMap.put("doctorNm", map.get("mdcr_dr_nm")); 		
		// 접수 및 예약 종류 이름 
		resultMap.put("receiptGubunKindNm", map.get("rcpn_apnt_kind_nm")); 			
		//
		resultMap.put("crmCd", map.get("mcrm_cd")); 		
		// 진료시간 
		resultMap.put("time", map.get("mdcr_hm")); 		
		// 접수번호 
		resultMap.put("receiptNo", map.get("rcpn_no")); 
		// 진료과 코드 
		resultMap.put("departmentCd", map.get("mdcr_dept_cd")); 
		// 상태
		resultMap.put("status", map.get("hstr_stat_cdnm")); 		
		// 진료일자 
		resultMap.put("date", map.get("mdcr_date")); 
		// 인터넷 예약 취소를 위한 번호 
		resultMap.put("bookingIdx", map.get("booking_idx")); 

// 예약에는 있지만, 인터넷 예약에는 없음... 
//		
//		// 접수 및 예약 종류 명 
//		resultMap.put("receiptGubunNm", map.get("rcpn_apnt_dvnm")); 
	
		return resultMap; 		
	}
	
	
	/**
	 * 예약 - 예약 리스트 
	 * @param map
	 * @return
	 */
	public static Map<String, Object> _reservation_getRevList(Map<String, Object> map) { 
		final Map<String, Object> resultMap = new HashMap<String, Object>(); 
		
		// 접수번호 
		resultMap.put("receiptNo", map.get("rcpn_no")); 
		// 접수 및 예약 종류 코드 
		resultMap.put("receiptGubunKindCd", map.get("rcpn_apnt_kind_cd")); 
		// 접수 및 예약 종류 명 
		resultMap.put("receiptGubunKindNm", map.get("rcpn_apnt_kind_nm"));
		
		// TODO 접수 및 예약 종류 코드 --> 확인필요 
		resultMap.put("receiptGubunCd", map.get("rcpn_apnt_dvcd")); // ex : 2 	
		// 접수 및 예약 종류 명 
		resultMap.put("receiptGubunNm", map.get("rcpn_apnt_dvnm")); 
		
		// 진료일자 
		resultMap.put("date", map.get("mdcr_date")); 
		// 진료시간 
		resultMap.put("time", map.get("mdcr_hm")); 
		// 진료과 코드 
		resultMap.put("departmentCd", map.get("mdcr_dept_cd")); 
		// 진료 의사명 
		resultMap.put("doctorNm", map.get("mdcr_dr_nm")); 
		// 진료과명 
		resultMap.put("departmentNm", map.get("mdcr_dept_nm")); 
		// 상태
		resultMap.put("status", map.get("hstr_stat_cdnm")); 
		//
		resultMap.put("crmCd", map.get("mcrm_cd")); 
		// 진료과 진행상태?? 
		resultMap.put("progressStatusCd", map.get("mdcr_prgr_stat_cd")); 		
		// 진료진행상태 
		resultMap.put("progressStatusNm", map.get("mdcr_prgr_stat_nm")); // ex : 완료 		

		
		return resultMap; 
	}
	
	/**
	 * 예약 - 예약가능한 진료과 
	 * @param map
	 * @return
	 */
	public static Map<String, Object> _reservation_getRevDept(Map<String, Object> map) { 
		final Map<String, Object> resultMap = new HashMap<String, Object>(); 
		
		// 진료과 코드 
		resultMap.put("departmentCd", map.get("mdcr_dept_abcd")); 
		// 진료과명 
		resultMap.put("departmentNm", map.get("mdcr_dept_nm")); 
		// 순번 
		resultMap.put("order", map.get("sort_no")); 
		
		return resultMap; 
	}	
	
	/**
	 * 예약 - 진료과에 의사 
	 * @param map
	 * @return
	 */
	public static Map<String, Object> _reservation_getRevDoc(Map<String, Object> map) { 
		final Map<String, Object> resultMap = new HashMap<String, Object>(); 
		
		// 진료과 코드 
		resultMap.put("departmentCd", map.get("mdcr_dept_abcd")); 
		// 진료의사 ID 
		resultMap.put("doctorId", map.get("mdcr_dr_lcno")); 
		// 진료 의사명 
		resultMap.put("doctorNm", map.get("mdcr_dr_nm")); 
		
		return resultMap; 
	}	
	
	/**
	 * 예약 - 진료과에 의사가 이용 가능한(?) 날짜 
	 * @param map
	 * @return
	 */
	public static Map<String, Object> _reservation_getRevDate(Map<String, Object> map) { 
		final Map<String, Object> resultMap = new HashMap<String, Object>(); 
		
		// 요일순번. 0=일, 5=목 
		resultMap.put("weekOrder", map.get("dowk")); 
		// 년월일 
		resultMap.put("date", map.get("cal_date")); 
		// 년월일의 요일명 
		resultMap.put("weekNm", map.get("dowknm")); 
		
		return resultMap; 
	}	
	
	/**
	 * 예약 - 진료과에 의사가 이용 가능한(?) 날짜의 시간  
	 * @param map
	 * @return
	 */
	public static Map<String, Object> _reservation_getRevTime(Map<String, Object> map) { 
		final Map<String, Object> resultMap = new HashMap<String, Object>(); 
		
		// 에약가능한 시간 20150101
		resultMap.put("time", map.get("resvusablecnt")); 
		// 총 예약인원 
		resultMap.put("totalReservCnt", map.get("respersonnelamt")); 
		// 초진 예약인원 
		resultMap.put("firstReservCnt", map.get("respersonnelfst")); 		
		// 재진 예약인원 
		resultMap.put("secondReservCnt", map.get("respersonnelrev")); 
		// 예약 가능인원 
		resultMap.put("availReservCnt", map.get("usapersonnelamt")); 
		// 초진 가능인원 
		resultMap.put("firstAvailReservCnt", map.get("usapersonnelfst")); 
		// 재진 가능인원 
		resultMap.put("secondAvailReservCnt", map.get("usapersonnelrev")); 

		return resultMap; 
	}	
	
	/**
	 * 순번기 - 목록 
	 * @param map
	 * @return
	 */
	public static Map<String, Object> _ticket_list(Map<String, Object> map) { 
		final Map<String, Object> resultMap = new HashMap<String, Object>(); 
		
		// 번호라고 불리지만 코드같이 생긴것 
		resultMap.put("seqCd", map.get("SEQ_NO")); 
		// 위치 문자열 
		resultMap.put("locationNm", map.get("SEQ_NM")); 
		// sort 순번
		resultMap.put("sort", map.get("SORT")); 
		// 병원구분코드 
		resultMap.put("hospitalCd", map.get("INSTCD")); 
		//display
		resultMap.put("display", map.get("DISPLAY"));
		
		return resultMap; 
	}	
	
	/**
	 * 순번기 - 대상창구 
	 * @param map
	 * @return
	 */
	public static Map<String, Object> _ticket_deskList(Map<String, Object> map) { 
		final Map<String, Object> resultMap = new HashMap<String, Object>(); 
		
		// 번호 
		resultMap.put("seqNo", map.get("SEQ_NO")); 
		// 업무명 - 접수, 수납, 입원 등등 
		resultMap.put("jobNm", map.get("CHU_GUBN_NM"));
		// 업무코드
		resultMap.put("jobCd", map.get("CHU_GUBN"));
		// sort
		resultMap.put("sort", map.get("SORT")); 
		// 병원구분코드 
		resultMap.put("hospitalCd", map.get("INSTCD"));
		//delay
		resultMap.put("delay", map.get("NO_WAIT"));
		//이전 발급 내역 여부
		resultMap.put("state", map.get("STATE"));
		
		return resultMap; 
	}
	/**
	 * 번호표 발급기 - 재발급 가능 유무
	 * @param map
	 * @return
	 */
	public static Map<String, Object> _ticket_balrecall(Map<String, Object> map){
		final Map<String, Object> resultMap = new HashMap<String, Object>();
		
		//재발급 가능 상태
		resultMap.put("state", map.get("STATE"));
		
		return resultMap;
	}
	/**
	 * 순번기 - 대상창구 
	 * @param map
	 * @return
	 */
	public static Map<String, Object> _ticket_ballist(Map<String, Object> map) { 
		final Map<String, Object> resultMap = new HashMap<String, Object>(); 
		
		// 발급번호 
		resultMap.put("balNo", map.get("BAL_NO")); 
		//업무 코드
		resultMap.put("jobCd", map.get("CHU_GUBN")); 
		//발급 시간
		resultMap.put("date", map.get("L_DATE")); 
		//병원 코드
		resultMap.put("hospitalCd", map.get("INSTCD")); 
		//발급 메시지
		resultMap.put("msg", map.get("MSG")); 
		//업무 명칭
		resultMap.put("jobNm", map.get("NAME")); 
		//발급기 위치
		resultMap.put("locationNm", map.get("NAME2")); 
		//
		resultMap.put("numPlus", map.get("NUM_PLUS")); 
		//발급기 코드
		resultMap.put("seqCd", map.get("SEQ_NO")); 
		//sort
		resultMap.put("sort", map.get("SORT")); 
		//대기
		resultMap.put("delay", map.get("WAIT")); 
		
		return resultMap; 
	}
	/**
	 * 검사결과 - 혈당 키 몸무게
	 * @param map
	 * @return
	 */
	public static Map<String, Object> _totalexam_getPersonHealthInfo(Map<String, Object> map){
		final Map<String, Object> resultMap = new HashMap<String, Object>(); 
		// 혈당
		resultMap.put("sugarValue", map.get("glus_rslt")); 
		// 몸무게 
		resultMap.put("weightValue", map.get("weight")); 
		// 키
		resultMap.put("heightValue", map.get("height")); 
		
//		final Logger logger = LoggerFactory.getLogger(ParamMappingUtil.class);
//		logger.info("rslt_inpt_dt : " + map.get("rslt_inpt_dt") + ", " + map.get("rslt_inpt_dt").getClass() +"==============");
		
		// 혈당 측정시간 
		Object o = map.get("rslt_inpt_dt"); 
		if(! (o instanceof net.sf.json.JSONArray)) {
			resultMap.put("sugarDate", map.get("rslt_inpt_dt")); 
		}

		// 키/몸무게 측정시간
		// 이 값은 값이 있는 경우 시간이 안 넘어오기 때문에 붙여줘야 함 
		o = map.get("info_rgdy"); 
		if(!(o instanceof net.sf.json.JSONArray)) {// 데이터가 없는 경우임 
			resultMap.put("infoDate", map.get("info_rgdy") + " 00:00:00"); 
		}		
		
		return resultMap;
	}
	
//	/**
//	 * 도우미 화면 목록  
//	 * @param map
//	 * @return
//	 */
//	public static Map<String, Object> _helper_messageList(Map<String, Object> map) { 
//		final Map<String, Object> resultMap = new HashMap<String, Object>(); 
//		
//		// 메시지 전송일자 yyyy-MM-dd HH24:MI:ss
//		resultMap.put("sendDt", map.get("SEND_DT")); 
//		// 메시지 내용 (JSON) 
//		resultMap.put("userMsg", map.get("USER_MSG")); 
//		
//		return map; 
//	}
	
	/**
	 * 가셔야할 곳의 진료과 정보 
	 * @param map
	 * @return
	 */
	public static Map<String, Object> _roadlist_getOrderDept(Map<String, Object> map) {
		final Map<String, Object> resultMap = new HashMap<String, Object>(); 

		// 예약접수구분코드 
		resultMap.put("receiptGubunKindCd", map.get("rcpn_apnt_kind_cd"));  // ex : B 
		// 진료과 진행상태?? 
		resultMap.put("progressStatusCd", map.get("mdcr_prgr_stat_cd")); 		
		// 진료진행상태 
		resultMap.put("progressStatusNm", map.get("mdcr_prgr_stat_nm")); // ex : 완료 
		// 접수 및 예약 종류 명 
		resultMap.put("receiptGubunNm", map.get("rcpn_apnt_dvnm"));  // ex : 예약 
		// 접수 및 예약 종류 코드 
		resultMap.put("receiptGubunCd", map.get("rcpn_apnt_dvcd")); // ex : 2 
		// 진료과명 
		resultMap.put("departmentNm", map.get("mdcr_dept_nm")); 
		// 진료의 
		resultMap.put("doctorNm", map.get("mdcr_dr_nm"));   		
		// 예약접수구분명 
		resultMap.put("receiptGubunKindNm", map.get("rcpn_apnt_kind_nm")); // ex : 결과상담예약 
		// 뭔지는 모름 
		resultMap.put("crmCd", map.get("mcrm_cd")); 
		// 진료시간 
		resultMap.put("time", map.get("mdcr_hm"));
		// 진료일 
		resultMap.put("date", map.get("mdcr_date")); 
		// 접수번호 
		resultMap.put("receiptNo", map.get("rcpn_no")); 
		// 진료과코드 
		resultMap.put("departmentCd", map.get("mdcr_dept_cd")); 		
		// 상태 
		resultMap.put("status", map.get("hstr_stat_cdnm")); // ex : 접수 
		
		return resultMap; 
	}
	
	/**
	 * 가셔야할곳 목록  
	 * @param map
	 * @return
	 */
	public static Map<String, Object> _roadlist_getVisitInfo(Map<String, Object> map) { 
		final Map<String, Object> resultMap = new HashMap<String, Object>(); 
/*
 * 기간계에서 결과가 이런식으로 나옴. 
 * 파라미터로 들어오는 map에는 아래 ret_value가 들어 있을 것으로 값으로 들어오는 문자열을 파싱해서 사용해야 함. 
   "ret_value": "[0] [{ret_value:\"무인처방 발급기이용\"}]\n[1] [{ret_value:\"암센터1층 통합예약실(6번창구) (예약)\"}]\n"	
 */
	
		String value = (String) map.get("ret_value"); 
		if(StringUtils.isEmpty(value)) {
			return resultMap; 
		}
		
    	// 무시할 문자들 제거 
    	value = value.replace("\\n", ""); 
    	value = value.replace("[", ""); 
    	value = value.replace("]", ""); 
    	value = value.replace("\"", ""); 
		
    	// 목적지가 들어있는 key 단위로 한번 자르고 
    	final String[] split = value.split("ret_value"); 
    	
    	for (final String element : split) {
    		// 필요한 값인지 확인해서 
    		if(element.startsWith(":")) {
    			// 시작~종료 인덱스값 따기 
    			final int start = element.indexOf(":") + 1; 
    			final int end = element.indexOf("}"); 
    			final String destination = element.substring(start, end); 
    			
    			// key-value를 동일한 값으로 넣고, value는 지도의 값으로 치환될 것임 
    			resultMap.put(destination, destination); 
    		} 
    	}		
		
		return resultMap; 
	}
}
