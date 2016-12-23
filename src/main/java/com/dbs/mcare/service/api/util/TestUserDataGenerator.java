package com.dbs.mcare.service.api.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.dbs.mcare.exception.mobile.ApiCallException;
import com.dbs.mcare.framework.util.DateUtil;
import com.dbs.mcare.framework.util.ResponseUtil;
import com.dbs.mcare.service.api.PnuhApi;

/**
 * 병원에서 테스트용 환자를 제공해 주지 않아서 어쩔 수 없이 생겨난 클래스 
 * 인터페이스는 됐다 치고, 내부로 데이터를 흘려보내주기 위함이다. 
 * 각 Controller에 테스트 코드를 심어두자니 릴리즈 할때 코드가 더러워질것 같아서 한곳에 모아두기로 한다. 
 * 
 * @author aple
 *
 */
@Component 
public class TestUserDataGenerator {
	private final Logger logger = LoggerFactory.getLogger(TestUserDataGenerator.class);
	
	/**
	 * 가짜 api 호출 
	 * @param api
	 * @param reqMap
	 * @return
	 * @throws ApiCallException
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> call(PnuhApi api, Map<String, Object> reqMap) throws ApiCallException {
		// 연산자 확인 
		if(api == null) { 
			throw new ApiCallException("mcare.error.param", "파라미터를 확인해주세요", null); 
		}
		// 파라미터 확인 
		if(reqMap == null) { 
			reqMap = Collections.emptyMap(); 
		}
		
		// 메소드 이름 만들어서 
		String apiUrl = api.getApiUrl(); 
		apiUrl = apiUrl.replace("/", "_"); 
		
		// 호출
		try { 
			final Class<?> clazz = TestUserDataGenerator.class; 
			final Method method = clazz.getDeclaredMethod(apiUrl, Map.class); 
			
			return (Map<String, Object>) method.invoke(this, reqMap); 
		}
		catch(NoSuchMethodException ex) { 
			logger.debug("테스트 데이터 생성 대상이 아니라서 테스트 환자번호로 실제 메소드를 호출할 것임. apiUrl=" + apiUrl);
		}
		catch(Exception ex) { 
			throw new ApiCallException("mcare.error.system", ex.getMessage(), null); 
		}

//		return ResponseUtil.getExtraMessageMap("이것은 테스트환자용 테스트 코드. 그렇지만 예상하지못한케이스입니다."); 
		return null;
	}
	
	/**
	 * 가셔야할 곳의 진료과 정보 
	 * @param map
	 * @return
	 */
	public static Map<String, Object> _roadlist_getOrderDept(Map<String, Object> map) {
		final List<Map<String, Object>> list = new ArrayList<Map<String, Object>>(); 
		Map<String, Object> resultMap = null; 

		resultMap = new HashMap<String, Object>(); 
		// 접수번호 
		resultMap.put("receiptNo","11111111"); 
		// 진료과명 
		resultMap.put("departmentNm", "대장항문과"); 		
		// 진료의 
		resultMap.put("doctorNm", "김수한무");   		
		list.add(resultMap); 
		
		return ResponseUtil.wrapResultMap(list); 
	}	
	
	/**
	 * 가셔야할 곳 
	 * @param map
	 * @return
	 */
	public Map<String, Object> _roadlist_getVisitInfo(Map<String, Object> map) {  
		final List<Map<String, Object>> list = new ArrayList<Map<String, Object>>(); 
		Map<String, Object> resultMap = null; 
		
		resultMap = new HashMap<String, Object>(); 
		resultMap.put("암센터1층 통합예약실(6번창구)", "암센터1층 통합예약실(6번창구)"); 
		list.add(resultMap); 
		//
		resultMap = new HashMap<String, Object>(); 
		resultMap.put("A동2층 신경과", "A동2층 신경과"); 
		list.add(resultMap); 		
		
		return ResponseUtil.wrapResultMap(list); 
	}
	
	/**
	 * 진료대기시간조회 
	 * @param map
	 * @return
	 */
	public static Map<String, Object> _waittime_getwaitingList(Map<String, Object> map) { 
		final List<Map<String, Object>> list = new ArrayList<Map<String, Object>>(); 
		Map<String, Object> resultMap = null;  
		Date dt = new Date(); 
		
		
		resultMap = new HashMap<String, Object>(); 
		// 진료일자 
		resultMap.put("date", "2016-06-16"); 
		// 진료시간 
		resultMap.put("time", "15:20"); 
		// 접수예약구분 
		resultMap.put("receiptGubun", "접수예약"); 
		// 진료여부 
		resultMap.put("treatmentYn", "Y"); 
		// 진찰료수납여부 
		resultMap.put("consultationFreeYn", "Y"); 
		// 진료과 코드 
		resultMap.put("departmentCd", "00000000"); 
		// 진료과명 
		resultMap.put("departmentNm", "내과"); 
		// 진료의사코드 
		resultMap.put("doctorId", "00000000"); 
		// 진료의사명 
		resultMap.put("doctorNm", "홍길동"); 
		// 진료진행상태 
		resultMap.put("progressStatusNm", "대기"); 
		// 환자도착일시 
		resultMap.put("pArrivalDt", DateUtil.convertYYYY_MM_DD_HH24_MM(dt)); 

		// 클리닉 종류 
		resultMap.put("clinicCd", "00"); 
		// 센터구분 
		resultMap.put("centerCd", "00"); 
		// 클리닉종류명 
		resultMap.put("clinicNm", "실험클리닉"); 
		// 센터구분명 
		resultMap.put("centerNm", "실험센터"); 
		// 계산상태 
		resultMap.put("receiptStatus", "대기"); 
		// 대기순번 
		resultMap.put("waitingNo", "12"); 
		//순서
		resultMap.put("ranks", "2");
		//확인시간
		resultMap.put("checkTime", "201606161520"); 
		// 추가 
		list.add(resultMap); 
		
		return ResponseUtil.wrapResultMap(list); 
	}	
	
	/**
	 * 외래 진료이력 (수진이력) 
	 * @param map
	 * @return
	 */
	public static Map<String, Object> _history_getOutList(Map<String, Object> map) { 
		final List<Map<String, Object>> list = new ArrayList<Map<String, Object>>(); 
		Map<String, Object> resultMap = null; 
		
		// 
		resultMap = new HashMap<String, Object>(); 
		// 접수번호 
		resultMap.put("receiptNo", "104866544"); 
		// 환자번호 
		resultMap.put("pId", map.get("pid")); 
		// 예약접수구분코드 
		resultMap.put("receiptGubunCd", "A"); 
		// 예약접수구분명 
		resultMap.put("receiptGubunNm", "검사예약"); 
		// 진료일자 
		resultMap.put("date", "20150825"); 
		// 진료시간 
		resultMap.put("time", "1105"); 
		// 진료과코드 
		resultMap.put("departmentCd", "D0101"); 
		// 진료과명 
		resultMap.put("departmentNm", "소화기내과"); 
		// 진료의 
		resultMap.put("doctorNm", "일반의사"); 
		list.add(resultMap); 
		
		// 
		resultMap = new HashMap<String, Object>(); 
		// 접수번호 
		resultMap.put("receiptNo", "104865828"); 
		// 환자번호 
		resultMap.put("pId", map.get("pId")); 
		// 예약접수구분코드 
		resultMap.put("receiptGubunCd", "1"); 
		// 예약접수구분명 
		resultMap.put("receiptGubunNm", "창구"); 
		// 진료일자 
		resultMap.put("date", "20150826"); 
		// 진료시간 
		resultMap.put("time", "1405"); 
		// 진료과코드 
		resultMap.put("departmentCd", "D0101"); 
		// 진료과명 
		resultMap.put("departmentNm", "내과"); 
		// 진료의 
		resultMap.put("doctorNm", "일반의사"); 
		list.add(resultMap); 		
		
		
		return ResponseUtil.wrapResultMap(list); 
	}	
	
	/**
	 * 진료이력 (입원) 
	 * @param map
	 * @return
	 */
	public static Map<String, Object> _history_getInList(Map<String, Object> map) { 
		final List<Map<String, Object>> list = new ArrayList<Map<String, Object>>(); 
		Map<String, Object> resultMap = null;
		
		
		resultMap = new HashMap<String, Object>(); 
		
		// 접수번호 
		resultMap.put("receiptNo", "104931329"); 
		// 환자번호 
		resultMap.put("pId", map.get("pId")); 
		// 입원일 
		resultMap.put("admissionDt", "20150902"); 
		// 퇴원일 
		resultMap.put("dischargeDt", "20150905"); 
		// 진료과코드 
		resultMap.put("departmentCd", "D0101"); 
		// 진료과명 
		resultMap.put("departmentNm", "소화기내과"); 
		// 주치의 
		resultMap.put("attendDoctorNm", "송근암"); 
		list.add(resultMap); 
		
		return ResponseUtil.wrapResultMap(list); 
	}

	
	/**
	 * 진료이력 (진료비합계) 
	 */
	public static Map<String, Object> _history_getBillSum(Map<String, Object> map) { 
		final List<Map<String, Object>> list = new ArrayList<Map<String, Object>>(); 
		Map<String, Object> resultMap = null; 
		
		// 
		resultMap = new HashMap<String, Object>(); 
		// 영수증번호 
		resultMap.put("billNo", "47542579"); 
		// 접수번호 
		resultMap.put("receiptNo", "105230110"); 
		// 수납번호 
		resultMap.put("billReceiptNo", "136678781"); 
		// 환자번호 
		resultMap.put("pId", map.get("pId")); 
		// 수납일자 
		resultMap.put("receiptDt", "20151120"); 
		// 주부여부 
		resultMap.put("mainSubCd", "M0"); 
		// 진료과코드 
		resultMap.put("departmentCd", "D0102"); 
		// 진료과명 
		resultMap.put("departmentNm", "순환기내과"); 
		// 총진료비 
		resultMap.put("totalAmt", "9151745"); 
		// 공단부담금 
		resultMap.put("insuranceAmt", "6263268"); 
		// 본인부담금 + 부가세 
		resultMap.put("patientAmt", "2888477"); 
		// 수납금액(환자가 낸 금액) 
		resultMap.put("receiptAmt", "2888470"); 
		// 미수금 
		resultMap.put("outstandingAmt", "0"); 
		list.add(resultMap); 
		

		// 
		resultMap = new HashMap<String, Object>(); 		
		// 영수증번호 
		resultMap.put("billNo", "47542560"); 
		// 접수번호 
		resultMap.put("receiptNo", "105230110"); 
		// 수납번호 
		resultMap.put("billReceiptNo", "136678784"); 
		// 환자번호 
		resultMap.put("pId", map.get("pId")); 
		// 수납일자 
		resultMap.put("receiptDt", "20151120"); 
		// 주부여부 
		resultMap.put("mainSubCd", "S1"); 
		// 진료과코드 
		resultMap.put("departmentCd", "D0102"); 
		// 진료과명 
		resultMap.put("departmentNm", "순환기내과"); 
		// 총진료비 
		resultMap.put("totalAmt", "389832"); 
		// 공단부담금 
		resultMap.put("insuranceAmt", "0"); 
		// 본인부담금 + 부가세 
		resultMap.put("patientAmt", "389832"); 
		// 수납금액(환자가 낸 금액) 
		resultMap.put("receiptAmt", "0"); 
		// 미수금 
		resultMap.put("outstandingAmt", "389830"); 
		list.add(resultMap); 
						
		
		
		return ResponseUtil.wrapResultMap(list); 
	}
	


	
//	/**
//	 * 진료예약조회 
//	 * @param map
//	 * @return
//	 */
//	public Map<String, Object> _reservation_search_getRevList(Map<String, Object> map) { 
//		if(this.logger.isDebugEnabled()) {
//			this.logger.debug(FrameworkConstants.NEW_LINE + "진료예약조회 테스트 데이터 생성합니다.");
//		}
//		
//		
//		List<Map<String, Object>> list = new ArrayList<>(); 
//		Map<String, Object> resultMap = null; 
//		
//		//
//		resultMap = new HashMap<String, Object>(); 
//		resultMap.put("reservationMethod", "Y"); 
//		resultMap.put("dataDate", "20100101"); 
//		resultMap.put("ordExamKindNm", "진료예약"); 
//		resultMap.put("receiptGubunKindNm", "가예약"); 
//		resultMap.put("doctorId", "00000"); 
//		resultMap.put("receiptNo", "1"); 
//		resultMap.put("doctorNm", "심청"); 
//		resultMap.put("departmentCd", "0000000000"); 
//		resultMap.put("dataTime", "1330"); 
//		resultMap.put("departmentNm", "내과"); 
//		list.add(resultMap); 
//		
//		
//		//
//		resultMap = new HashMap<String, Object>(); 
//		resultMap.put("reservationMethod", "Y"); 
//		resultMap.put("dataDate", "20100101"); 
//		resultMap.put("ordExamKindNm", "검사예약"); 
//		resultMap.put("receiptGubunKindNm", "예약완료"); 
//		resultMap.put("doctorId", "00000"); 
//		resultMap.put("receiptNo", "1"); 
//		resultMap.put("doctorNm", "홍길"); 
//		resultMap.put("departmentCd", "0000000000"); 
//		resultMap.put("dataTime", "1530"); 
//		resultMap.put("departmentNm", "내분비대사"); 
//		list.add(resultMap); 		
//		
//		return ResponseUtil.getResultMap(list); 
//	}
	
	
	/**
	 * 창구접수 예약리스트 
	 * @param map
	 * @return
	 */
	public static Map<String, Object> _reserva_getRevList(Map<String, Object> map) { 
		final List<Map<String, Object>> list = new ArrayList<Map<String, Object>>(); 
		Map<String, Object> resultMap = null; 
		
		resultMap = new HashMap<>(); 
		// 접수번호 
		resultMap.put("receiptNo", "106699601"); 
		// 접수 및 예약종류 코드 
		resultMap.put("receiptGubunKindCd", "1"); 
		// 접수 및 예약종류명 
		resultMap.put("receiptGubunKindNm", "창구"); 
		// 진료일자 
		resultMap.put("date", DateUtil.convertYYYYMMDD(new Date())); 
		// 진료시간 
		resultMap.put("time", "----"); 
		// 진료과 코드 
		resultMap.put("departmentCd", "D0600"); 
		// 진료의사명 
		resultMap.put("doctorNm", "홍길동"); 
		// 진료과명 
		resultMap.put("departmentNm", "신경외과"); 
		// 상태 
		resultMap.put("status", "접수");
		// 추가 
		list.add(resultMap); 
		
		return ResponseUtil.wrapResultMap(list); 
	}	
}
