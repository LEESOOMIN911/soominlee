package com.dbs.mcare.service.batch.job;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.dbs.mcare.exception.mobile.ApiCallException;
import com.dbs.mcare.framework.service.batch.BatchJob;
import com.dbs.mcare.framework.util.ConvertUtil;
import com.dbs.mcare.service.PnuhConfigureService;
import com.dbs.mcare.service.api.MCareApiCallService;
import com.dbs.mcare.service.api.PnuhApi;
import com.dbs.mcare.service.api.SendSmsService;
import com.dbs.mcare.service.mobile.helper.HelperApiFacade;

/**
 * 특정기간동안 로그인하지 않은 사용자 탈퇴안내메시지 전송 
 * @author aple
 *
 */
@Component 
public class WithdrawalNotifyByDateBatchJob extends BatchJob {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired 
	private MCareApiCallService apiCallService;
	@Autowired 
	private SendSmsService smsService; 
	@Autowired 
	private HelperApiFacade helperFacade; 
	@Autowired 
	private PnuhConfigureService configureService; 
	// 
	private int deadline; 
	private int[] notifyDate; 
	private String smsFormat;
	private String pushFormat; 
	
	/**
	 * 생성자  
	 */
	public WithdrawalNotifyByDateBatchJob() {
		super("탈퇴안내배치");
	}
	
	@PostConstruct 
	public void init() { 
		// 탈퇴처리일 
		this.deadline = this.configureService.getWithdrawalDeadlineDate(); 
		// SMS안내 문자열 
		this.smsFormat = this.configureService.getWithdrawalNotifySmsFormat(); 
		// PUSH안내 문자열 
		this.pushFormat = this.configureService.getWithdrawalNotifyPushFormat(); 
		// 탈퇴처리 기준 몇일전에 안내해줄지 
		String notifyDateStr = this.configureService.getWithdrawalDeadlineNotifyDate(); 
		
		// 날짜가 유효하면 
		if(!StringUtils.isEmpty(notifyDateStr)) {
			// 분석 
			String[] dateStr =  notifyDateStr.split(","); 

			// 분석결과 담을 공간 만들고 
			this.notifyDate = new int[dateStr.length]; 
			
			try { 
				// 숫자로 변환 
				int idx = 0; 
				// 하나씩 분석해서 처리 
				for(String value : dateStr) { 
					this.notifyDate[idx++] = Integer.parseInt(value.trim()); 
				}
			}
			catch(Exception ex) { 
				if(this.logger.isErrorEnabled()) {
					this.logger.error("탈퇴 안내 대상 날짜를 분석하다가 에러났음.", ex);
				}
			}
		}		
	}	
	
	
	@SuppressWarnings("unchecked")
	@Override
	public void execute(Date aggDt) throws Exception {
		if(this.logger.isInfoEnabled()) {
			this.logger.info(this.getBatchName() + " : 시작.");
		}	
		
		// 노티만 해주면 되는 애들 
		for(int day : notifyDate) {
			// 몇일동안 로그인 안한 사람을 걸러야 하는지 계산하고 
			int baseDay = this.deadline - day; 
			
			// 대상자 뽑아오기 
			List<Map<String, Object>> targetList = null; 
			
			try { 
				targetList = (List<Map<String, Object>>) this.apiCallService.execute(PnuhApi.USER_LOGIN_GETACCESSDAY, "baseDay", baseDay); 
				if(this.logger.isInfoEnabled()) {
					this.logger.info(this.getBatchName() + " : 탈퇴예정알림대상자. " + day + "일전, " + targetList.size() + "명. ");
				}
			}
			catch(Exception ex) { 
				if(this.logger.isErrorEnabled()) {
					this.logger.error(this.getBatchName() + " : 대상자 추출 실패. baseDay=" + baseDay + ", day=" + day, ex);
				}
				
				continue; 
			}
			
			
			// 한명씩 처리. withdrawal.notify.message.format 
			for(Map<String, Object> map : targetList) { 
				Integer lastLoginDay = ConvertUtil.convertInteger(String.valueOf(map.get("lastLoginDay"))); 
				Integer registerDay = ConvertUtil.convertInteger(String.valueOf(map.get("registerDay"))); 
				String pId = (String) map.get("pId"); 
				
				// 대상자를 가져오는 질의문에 이미 날짜가 있음에도 한번 더 날짜를 확인하는 것은 
				// 질의 조건이 OR로 걸려있기 때문임. 
				if(lastLoginDay != null) { 
					// 로그인 했던 사람은 지정된 날짜만큼 로그인 안한 사람만 뽑아야함 
					if(lastLoginDay == baseDay) { 
						// 탈퇴 안내메시지를 수신할 대상자 
						this.notifyWithdrawlMessage(pId, String.format(this.smsFormat, pId, baseDay), String.format(this.pushFormat, pId, baseDay)); 
					} 
				}
				// 로그인한적이 없는 사람이라면, 가입일 기준으로 검사 
				else if(registerDay == baseDay) {
					// 탈퇴 안내메시지를 수신할 대상자 
					this.notifyWithdrawlMessage(pId, String.format(this.smsFormat, pId, baseDay), String.format(this.pushFormat, pId, baseDay)); 					
				}
			}
		}
	} 
	
	/**
	 * 탈퇴 메시지 보내기 
	 * @param pId
	 * @param sendMsg
	 * @throws ApiCallException
	 */
	private void notifyWithdrawlMessage(String pId, String smsMsg, String pushMsg) throws ApiCallException { 
		// 문자메시지를 보내기 위해 환자정보 조회 
		@SuppressWarnings("unchecked")
		Map<String, Object> map = (Map<String, Object>) this.apiCallService.execute(PnuhApi.USER_USERINFO_GETUSERINFO, "pId", pId); 

		// 핸드폰 번호 꺼내오기 
		String phoneNo = (String) map.get("cellphoneNo"); 
		// 핸드폰 번호가 있나? 
		if(StringUtils.isEmpty(phoneNo)) {
			if(this.logger.isErrorEnabled()) {
				this.logger.error(this.getBatchName() + " : 핸드폰 번호가 등록되지 않은 사용자. 탈퇴안내를 해줄 수 없음. pId=" + pId);
			}
		}
		else { 
			// SMS 보내기 
			this.smsService.sendSms(phoneNo, smsMsg);
		} 
		
		// PUSH 보내기 
		this.helperFacade.sendWithdrawal(pId, pushMsg);
	}
}
