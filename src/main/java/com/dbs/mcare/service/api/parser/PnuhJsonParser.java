package com.dbs.mcare.service.api.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dbs.mcare.framework.FrameworkConstants;
import com.dbs.mcare.framework.api.parser.ApiParser;
import com.dbs.mcare.framework.util.ConvertUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 부산대병원 파서 
 * @author aple
 *
 */
public class PnuhJsonParser extends ApiParser {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private static final String KEY_LIST = "list"; // object일수도 있고, array일수도 있음 
	private static final String KEY_RESULT_KM = "resultKM"; 
	private static final String KEY_RESULT_VALUE = "mdcrappt"; 	
	private static final String KEY_ERROR = "@error"; 
	private boolean error; 
	
	@Override
	public boolean parse(Object resultObj) {
		JSONObject obj = (JSONObject) resultObj; 

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
		final Object listTypeObj = obj.get(KEY_LIST); 
		
		if(listTypeObj instanceof JSONObject) {
			// 기본적인 분석 
			final JSONObject listObj = obj.getJSONObject(KEY_LIST); 
			final JSONObject resultKMObj = listObj.getJSONObject(KEY_RESULT_KM); 
			
			// 결과가 에러인지 여부 확인 
			this.error = this.checkError(resultKMObj); 
			
			// 결과정리 
			if(listObj.get(KEY_RESULT_VALUE) instanceof JSONObject) { 
				super.setResult(ConvertUtil.convert(listObj.getJSONObject(KEY_RESULT_VALUE)));
			}
			else {
				List<Map<String, Object>> list = new ArrayList<Map<String, Object>>(); 
				final JSONArray array = (JSONArray) listObj.get(KEY_RESULT_VALUE); 
				
				for(int i = 0; i < array.size(); i++) {
					list.add(ConvertUtil.convert(array.getJSONObject(i))); 
				}
				
				// 결과 붙이기 
				super.setResult(list); 
			}			
		}
		// 데이터가 없는 경우에 여기에 걸리는데 혹시나 다른 API를 호출하면 달라질수도 있음 
		else {
			this.logger.debug("기간계 호출 결과가 없는 것으로 인식됨");
			// 결과가 비어있음을 설정 
			super.setResult(null);
			// 에러인지 확인하기 위해 list를 뽑음 
			final JSONArray listArray = obj.getJSONArray(KEY_LIST); 
			
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
		
		return true;
	}

	@Override
	public boolean isError() {
		return this.error; 
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
		
}
