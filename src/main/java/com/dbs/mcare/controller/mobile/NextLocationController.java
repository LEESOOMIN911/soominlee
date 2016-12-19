package com.dbs.mcare.controller.mobile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dbs.mcare.exception.mobile.MobileControllerException;
import com.dbs.mcare.framework.exception.MCareServiceException;
import com.dbs.mcare.framework.service.menu.ServiceMenu;
import com.dbs.mcare.framework.template.AbstractController;
import com.dbs.mcare.framework.util.ResponseUtil;
import com.dbs.mcare.service.api.MCareApiCallService;
import com.dbs.mcare.service.api.PnuhApi;
/**
 * 가셔야할 곳 
 * @author hyeeun 
 *
 */
@Controller
@RequestMapping("/mobile/nextLocation") 
@ServiceMenu(menuId="nextLocation", description="가야할 곳")
public class NextLocationController extends AbstractController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private MCareApiCallService apiCallService; 
	
	// 가셔야할 곳 
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/next.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object>  getNextLocationList(HttpServletRequest request, HttpServletResponse response, 
			@RequestBody Map<String, Object> reqMap) throws MobileControllerException {

		// 접근권한 및 파라미터 정리 
		try { 
			reqMap = super.isAccessableMenu(this.getClass(), request, reqMap); 
		}
		catch(MCareServiceException ex) { 
			throw new MobileControllerException(ex.getCode(), ex.getMessage()); 
		} 

		// 진료과 목록을 뽑아옴 
		final List<Map<String, Object>> deptList = (List<Map<String, Object>>) this.apiCallService.execute(PnuhApi.ROADLIST_GETORDERDEPT, reqMap);
		if(deptList == null || deptList.isEmpty()) {
			this.logger.debug("가야할 진료과 목록이 없음.. ");
			return ResponseUtil.wrapEmptyResultListMap(); 
		}
		
		// 결과 정리할 곳 
		final List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>(); 
		List<Map<String, Object>> deptRoadList = null; 
		
		// 진료과 하나씩 돌면서  
		for(final Map<String, Object> deptMap : deptList) { 
			// 진료과 영수증번호를 붙여줘야 함 
			reqMap.put("receiptNo", deptMap.get("receiptNo")); 
			// 가야할 곳 목록을 뽑아옴 
			deptRoadList = (List<Map<String, Object>>) this.apiCallService.execute(PnuhApi.ROADLIST_GETVISITINFO, reqMap); 
			if(deptRoadList == null || deptRoadList.isEmpty()) {
				this.logger.debug("진료과 내에 가셔야할 곳이 없음. 진료과 = " + deptMap.get("departmentNm") + ", 영수증=" + reqMap.get("receiptNo"));
				continue; 
			}
			
			// 기간계에서 여차저차한 사정이 있어서 가야할 곳 목적지가 "null"인 경우는 제거해주어야 함 
			deptRoadList = this.nextLocationDataCleansing(deptRoadList); 
			if(deptRoadList == null) { 
				continue; 
			}
			
			
			// 가야할 곳이 있으면 정리 
			final Map<String, Object> map = new HashMap<String, Object>(); 
			
			// 진료과 
			map.put("departmentNm", deptMap.get("departmentNm")); 
			// 진료의 
			map.put("doctorNm", deptMap.get("doctorNm")); 
//			// 진료일 
//			map.put("date", deptMap.get("date")); 
//			// 진료시간 
//			map.put("time", deptMap.get("time")); 

			
			//가셔야할곳을 결과에 담는다
			map.put("nextLocationList", deptRoadList); 
			
			// 결과 추가 
			resultList.add(map); 
		}

	
		return ResponseUtil.wrapResultMap(resultList); 
	}
	
	/**
	 * 기간계 상황에 따라 가셔야할 곳 데이터가 삭제되는 경우가 있어서 값이 null인 경우는 무시하기 위함 
	 * @param list
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<Map<String, Object>> nextLocationDataCleansing(List<Map<String, Object>> list) { 
		final Map<String,Object> nextLocationMap = list.get(0);
		final Map<String, Object> resultMap = new HashMap<String, Object>(); 
		
		//맵 안에 담긴 실제 리스트를 돌림
		final Iterator<String> keyIter = nextLocationMap.keySet().iterator(); 
		String legacyName = null; 
		String key = null; 
		
		// 
		while(keyIter.hasNext()) {
			key = keyIter.next(); 
			legacyName = (String) nextLocationMap.get(key); 
			
			if("null".equalsIgnoreCase(legacyName)) { 
				continue; 
			}
			
			// 변환 
			final Map<String,Object> afterMap = (Map<String,Object>) this.apiCallService.execute(PnuhApi.POIMAPPING_MAPPINGLEGACY, "legacyName", legacyName);
			// 변환된 값이 있으면 바꾸고, 없으면 기간계값을 그냥 사용하게 됨 
			if(afterMap != null && !afterMap.isEmpty()) { 
				final String mapName = (String) afterMap.get("MAP_NAME"); 
				resultMap.put(legacyName, mapName); 
			}			
			// 
		}
		
		// 유효하게 반환된 값이 있는지 확인 
		if(resultMap.isEmpty()) { 
			return null; 
		}
		
		// 정리된 맵을 list에 담아서 반환 
		final List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>(); 
		resultList.add(resultMap); 
		 
		return resultList; 
	}
	
	
	
//	/**
//	 * 테스트용 데이터 
//	 * @return
//	 */
//	private List<Map<String, Object>> getTestData() { 
//		final List<Map<String, Object>> list = new ArrayList<Map<String, Object>>(); 
//		List<Map<String, Object>> roadList = null; 
//		Map<String, Object> map = null; 
//		Map<String, Object> roadMap = null;
//		
//		// 
//		map = new HashMap<String, Object>(); 
//		map.put("receiptNo", "TEST_1"); 
//		map.put("departmentNm", "[테스트] 진료과1"); 
//		map.put("doctorNm", "허난설헌"); 
//		roadMap = new HashMap<String, Object>(); 
//		roadMap.put("내시경실", "내시경실"); 
//		roadMap.put("주사실", "주사실"); 
//		roadList = new ArrayList<Map<String, Object>>(); 
//		roadList.add(roadMap); 
//		map.put("nextLocationList", roadList); 
//		list.add(map); 
//		//
//		map = new HashMap<String, Object>(); 
//		map.put("receiptNo", "TEST_2"); 
//		map.put("departmentNm", "[테스트] 진료과2"); 
//		map.put("doctorNm", "양만춘"); 
//		roadMap = new HashMap<String, Object>(); 
//		roadMap.put("X-Ray실", "X-Ray실"); 
//		roadMap.put("소변검사실", "소변검사실"); 
//		roadList = new ArrayList<Map<String, Object>>(); 
//		roadList.add(roadMap); 
//		map.put("nextLocationList", roadList); 
//		list.add(map); 		
//		
//		return list; 
//	}
}


