package com.dbs.mcare.service.batch;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.dbs.mcare.framework.FrameworkConstants;
import com.dbs.mcare.framework.service.batch.BatchJob;
import com.dbs.mcare.framework.service.batch.job.AccessByDateBatchJob;
import com.dbs.mcare.framework.service.batch.job.AccessByHourBatchJob;
import com.dbs.mcare.framework.service.batch.job.AccessByMenuBatchJob;
import com.dbs.mcare.framework.service.batch.job.AccessByPlatformBatchJob;
import com.dbs.mcare.framework.service.batch.job.EventByDateBatchJob;
import com.dbs.mcare.framework.service.batch.job.MsgLogByDateBatchJob;
import com.dbs.mcare.framework.service.batch.job.UserRegisterByDateBatchJob;
import com.dbs.mcare.framework.util.DateUtil;
import com.dbs.mcare.service.PnuhConfigureService;
import com.dbs.mcare.service.batch.job.HelperDeleteByDateBatchJob;
import com.dbs.mcare.service.batch.job.UserInfoBatchJob;
import com.dbs.mcare.service.batch.job.WithdrawalNotifyByDateBatchJob;

@Component 
@EnableScheduling 
public class PnuhBatchJobManager {
	private final Logger logger = LoggerFactory.getLogger(this.getClass()); 
	
	@Autowired 
	private PnuhConfigureService configureService; 		
	// 프레임워크에 정의된 작업 ------  
	@Autowired 
	private AccessByDateBatchJob accessByDateBatchJob; // 일별접근 
	@Autowired 
	private AccessByHourBatchJob accessByHourBatchJob; // 시간별접근 
	@Autowired 
	private AccessByMenuBatchJob accessByMenuBatchJob; // 메뉴별접근 
	@Autowired 
	private AccessByPlatformBatchJob accessByPlatformBatchJob; // 플랫폼별접근 
	@Autowired 
	private EventByDateBatchJob eventByDateBatchJob; // 비콘이벤트 
	@Autowired 
	private MsgLogByDateBatchJob msgLogByDateBatchJob; // 메시지전송건수 
	@Autowired 
	private UserRegisterByDateBatchJob userRegisterByDateBatchJob; // 사용자등록 
	// 서비스에 정의된 작업 ------   
	@Autowired 
	private HelperDeleteByDateBatchJob helperDeleteByDateBatchJob; // 도우미 메시지 삭제 
	@Autowired 
	private UserInfoBatchJob userInfoBatchJob; // 사용자정보통계 (지역별, 연령별) 
	@Autowired 
	private WithdrawalNotifyByDateBatchJob withdrawalNotifyByDateBatchJob; // 사용자탈퇴 
	
	
	// 접근통계 관련 작업 
	private List<BatchJob> accessJobList; 
	// 사용자통계관련 
	private List<BatchJob> userJobList; 
	
	
	@PostConstruct 
	public void init() { 
		// 접근통계 
		this.accessJobList = new ArrayList<BatchJob>(); 
		this.accessJobList.add(this.accessByDateBatchJob); 
		this.accessJobList.add(this.accessByHourBatchJob); 
		this.accessJobList.add(this.accessByMenuBatchJob); 
		this.accessJobList.add(this.accessByPlatformBatchJob); 
		this.accessJobList.add(this.eventByDateBatchJob);  // 성격이 조금 다르지만 묶어서 같이 처리  
		this.accessJobList.add(this.msgLogByDateBatchJob); // 성격이 조금 다르지만 묶어서 같이 처리 
		this.accessJobList.add(this.helperDeleteByDateBatchJob); // 성격이 많이 다르지만 묶어서 같이 처리 
		
		// 사용자통계 
		this.userJobList = new ArrayList<BatchJob>(); 
		this.userJobList.add(this.userRegisterByDateBatchJob);  
		this.userJobList.add(this.userInfoBatchJob);
	}	
	
	@Scheduled(cron="${access.batch.cron}")	// 접근통계 
	public void workAggAccess() { 
		final String batchName = "접근통계배치"; 
		
		// 활성화 여부 
		if(!this.configureService.isAccessBatchWork()) {
			if(logger.isInfoEnabled()) {
				logger.info(batchName + " : 비활성화");
			}
			
			return; 
		}
		
		
		// 생성할 통계 데이터가 양이 많은것도 아니고 복잡도가 높은 것도 아니고 시급한것도 아니고, 하나씩 차례대로 처리한다. 
		// 작업용 어제날짜  
		final Date aggDt = DateUtil.getStartDate(1); 	
		
		if(logger.isInfoEnabled()) { 
			logger.info(this.getLogStr(batchName, aggDt, this.accessJobList.toString()));
		}
		
		// 작업실행 
		for(BatchJob job : this.accessJobList) {
			this.executeBatchJob(aggDt, job);
		}
	}
	
	@Scheduled(cron="${agg.user.info.batch.cron}") // 사용자 통계 
	public void workUserInfo() { 
		final String batchName = "사용자배치"; 
		
		// 활성화 여부 
		if(!this.configureService.isAggUserInfoWork()) {
			if(logger.isInfoEnabled()) { 
				logger.info(batchName + " : 비활성화");
			} 
			
			return; 
		}

		// 기준날자 구하고 
		final Date aggDt = DateUtil.getStartDate(1); 	
		
		if(logger.isInfoEnabled()) { 
			logger.info(this.getLogStr(batchName, aggDt, this.userJobList.toString())); 
		}
		
		// 사용자정보통계 돌리기 
		for(BatchJob job : this.userJobList) {
			this.executeBatchJob(aggDt, job);
		}	
	}	
	
	@Scheduled(cron="${withdrawal.batch.cron}") // 회원탈퇴안내. 낮시간에 안내를 해줘야 해서 별도 시간설정이 있는것임 
	public void workWithdrawal() { 
		final String batchName = "탈퇴배치"; 
		
		// 회원탈퇴배치 활성화 여부 
		if(!this.configureService.isWithdrawalBatchWork()) {
			logger.info(batchName + " : 비활성화");
			return; 
		}
		
		// 생성할 통계 데이터가 양이 많은것도 아니고 복잡도가 높은 것도 아니고 시급한것도 아니고, 하나씩 차례대로 처리한다. 
		// 작업용 어제날짜  
		final Date aggDt = DateUtil.getStartDate(1); 		
		
		if(logger.isInfoEnabled()) { 
			logger.info(batchName, aggDt, this.withdrawalNotifyByDateBatchJob.toString());
		}	
		
		// 작업실행 
		this.executeBatchJob(aggDt, this.withdrawalNotifyByDateBatchJob);		
	}	
	
	
	/**
	 * 하나의 배치작업 실행 
	 * @param aggDt
	 * @param job
	 */
	private void executeBatchJob(Date aggDt, BatchJob job) { 
		// 실행 
		try { 
			job.execute(aggDt);
			
			if(logger.isInfoEnabled()) { 
				logger.info(job.getBatchName() + " : 작업완료. (" + aggDt + ")");
			} 
		}
		catch(final Exception ex) {
			logger.info(job.getBatchName() + " : 작업실패. (" + aggDt + ")", ex);
			// 예외 발생 시 로그만 찍는것은 정상적인 흐름임. 의도적으로 잡은 예외임.  
		}		
	}	
	
	/**
	 * 로그용 문자 만들기 
	 * @param batchName
	 * @param aggDt
	 * @param target
	 * @return
	 */
	private String getLogStr(String batchName, Date aggDt, String target) { 
		StringBuilder builder = new StringBuilder(FrameworkConstants.NEW_LINE); 
		
		builder.append(batchName).append("시작 ==========").append(FrameworkConstants.NEW_LINE); 
		builder.append("- 기준시간 : ").append(aggDt).append(FrameworkConstants.NEW_LINE); 
		builder.append("- 대상작업 : ").append(target).append(FrameworkConstants.NEW_LINE); 
		
		return builder.toString(); 
	}	
}
