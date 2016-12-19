package com.dbs.mcare.service.batch.job;

import java.util.Date;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dbs.mcare.framework.service.batch.BatchJob;
import com.dbs.mcare.framework.util.DateUtil;
import com.dbs.mcare.service.PnuhConfigureService;
import com.dbs.mcare.service.api.MCareApiCallService;
import com.dbs.mcare.service.api.PnuhApi;

/**
 * 도우미 메시지 삭제 배치 
 * @author aple
 *
 */
@Component 
public class HelperDeleteByDateBatchJob extends BatchJob {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired 
	private MCareApiCallService apiCallService; 
	@Autowired 
	private PnuhConfigureService configureService; 
	// 
	private int beforeDate; 
	
	/**
	 * 생성자 
	 * 병원환경에 따라 도우미 메시지 테이블은 공통일수도 있고, 아닐수도 있기 때문에 framework으로 안 들어갔음 
	 */
	public HelperDeleteByDateBatchJob() {
		super("도우미메시지삭제배치");
	}
	
	@PostConstruct 
	public void init() { 
		this.beforeDate = this.configureService.getHelperValidDate(); 
	}

	@Override
	public void execute(Date deleteDt) throws Exception {
		if(this.logger.isInfoEnabled()) {
			this.logger.info(this.getBatchName() + " : 시작. 유효기간 : " + this.beforeDate + "일.");
		}
		
		// 목표날짜 구하고  
		Date targetDate = DateUtil.getEndDate(this.beforeDate); 
		// 문자열로 표현하고 
		String targetDateStr = DateUtil.convertYYYYMMDD(targetDate); 
		
		// 실행 
		this.apiCallService.execute(PnuhApi.HELPER_DELETECONTENTS, "deleteDay", targetDateStr); 
	}
}
