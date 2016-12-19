package com.dbs.mcare.service.api.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dbs.mcare.framework.FrameworkConstants;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * JSONObject를 매번 까보는 대신에 공통적으로 필요한 부분을 꺼내기 위함 
 * 현재는 결과가 1차원으로 표현되는 부분만 구현되어 있음 
 * 차츰보강필요 
 * @author aple
 *
 */
public class JSONResParser {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private static final String KEY_LIST = "list"; // object일수도 있고, array일수도 있음 
	private static final String KEY_RESULT_KM = "resultKM"; 
	private static final String KEY_RESULT_VALUE = "mdcrappt"; 	
	private static final String KEY_ERROR = "@error"; 
	private final JSONObject obj; 
	private boolean error; 
	private boolean list; 
	private boolean emptyResult; 
	private Map<String, Object> resultMap; 
	private List<Map<String, Object>> resultList; 
	
	/**
	 * 생성자  
	 * @param obj
	 */
	public JSONResParser(JSONObject obj) { 
		this.obj = obj; 
		this.emptyResult = false; 

		// --- 디버깅용 
		final StringBuilder builder = new StringBuilder(); 
		
		builder.append(FrameworkConstants.NEW_LINE); 
		builder.append("- 원본 ").append(FrameworkConstants.NEW_LINE); 
		builder.append(obj.toString()).append(FrameworkConstants.NEW_LINE); 
		builder.append("list type : ").append(obj.get(KEY_LIST).getClass()); 
		builder.append(FrameworkConstants.NEW_LINE); 
		
		this.logger.debug("분석 메시지 : " + builder.toString());
		// -----------
		
		// 타입확인 
		final Object listTypeObj = this.obj.get(KEY_LIST); 
		
		if(listTypeObj instanceof JSONObject) {
			// 기본적인 분석 
			final JSONObject listObj = this.obj.getJSONObject(KEY_LIST); 
			final JSONObject resultKMObj = listObj.getJSONObject(KEY_RESULT_KM); 
			
			// 결과가 에러인지 여부 확인 
			this.error = this.checkError(resultKMObj); 
			
			// 결과정리 
			if(listObj.get(KEY_RESULT_VALUE) instanceof JSONObject) { 
				this.list = false; 
				this.resultMap = this.convert(listObj.getJSONObject(KEY_RESULT_VALUE)); 
			}
			else {
				this.list = true; 
				this.resultList = new ArrayList<Map<String, Object>>(); 
				final JSONArray array = (JSONArray) listObj.get(KEY_RESULT_VALUE); 
				
				for(int i = 0; i < array.size(); i++) {
					this.resultList.add(this.convert(array.getJSONObject(i))); 
				}
			}			
		}
		// 데이터가 없는 경우에 여기에 걸리는데 혹시나 다른 API를 호출하면 달라질수도 있음 
		else {
			this.logger.debug("기간계 호출 결과가 없는 것으로 인식됨");
			// 결과가 비어있음을 설정 
			this.emptyResult = true; 
			// 결과가 object인지 list인지 모르기 때문에 호출한데서 정리하도록 null로만 설정해줌 
			this.resultList = null; 
			this.resultMap = null; 
			// 에러인지 확인하기 위해 list를 뽑음 
			final JSONArray listArray = this.obj.getJSONArray(KEY_LIST); 
			
			// 혹시나 새로운 규격이 나타날까봐... 
			if(listArray.size() != 1) { 
				if(this.logger.isErrorEnabled()) { 
					this.logger.error("예상하지 못한 경우임... 새로운 메시지 규격으로 판단됨... ");
				} 
			}
			else {
				this.error = this.checkError(listArray.getJSONObject(0)); 
			}
		}
	}
	
	/**
	 * 에러여부 
	 * @param resultKMObj
	 * @return
	 */
	private boolean checkError(JSONObject resultKMObj) {
		// 결과가 에러인지 여부 확인 
		final String errYesNo = resultKMObj.getString(KEY_ERROR); 
		if(errYesNo == null || "".equals(errYesNo) || errYesNo.equalsIgnoreCase("yes")) {
			return true; 
		}
		
		return false; 
	}
	
	
	/**
	 * 결과가 에러인지 여부 
	 * @return
	 */
	public boolean isError() { 
		return this.error; 
	}
	/**
	 * 결과가 리스트인지 여부 
	 * @return
	 */
	public boolean isResultList() { 
		return this.list; 
	}
	
	/**
	 * isResultList == true 이면 List<Map<String, String>> 반환 
	 * false이면 Map<String, String> 반환 
	 * @return
	 */
	public Object getResult() { 
		// 에러이면 결과가 없는 것이고 
		if(this.error) { 
			return null; 
		}
		
		if(this.list) {
			return this.resultList; 
		}
		
		return this.resultMap; 
	}
	
	/**
	 * JSONObject를 Map형태로 변환 
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Map<String, Object> convert(JSONObject obj) { 
		if(obj == null) {
			return null; 
		}
		
		final Iterator<String> keyIter = obj.keys(); 
		String key = null; 
		final Map<String, Object> map = new HashMap<String, Object>(); 
		Object value = null;
		
		while(keyIter.hasNext()) {
			key = keyIter.next(); 
			value = obj.get(key); 
			
			if(value instanceof String) { 
				map.put(key, ((String) value).trim()); 
			}
			else {
				map.put(key, value); 
			}
		}
		
		return map; 
	}
	/**
	 * 결과가 empty인지 여부 
	 * @return
	 */
	public boolean isEmptyResult() { 
		return this.emptyResult; 
	}
	
	@Override 
	public String toString() {
		final StringBuilder builder = new StringBuilder(); 
		
		if(this.error) {
			builder.append("error : yes");
		}
		else {
			builder.append("error : no").append(FrameworkConstants.NEW_LINE); 
			builder.append("empty : ").append(this.emptyResult).append(FrameworkConstants.NEW_LINE); 
			builder.append("result : ").append("type=").append((this.list == true ? "list" : "map")).append(" --> "); 
			if(this.list) {
				builder.append(this.resultList); 
			}
			else {
				builder.append(this.resultMap); 
			}
		}
		
		return builder.toString(); 
	}
}
