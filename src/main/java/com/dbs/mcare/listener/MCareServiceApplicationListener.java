package com.dbs.mcare.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.dbs.mcare.MCareConstants;
import com.dbs.mcare.MCareConstants.HOSPITAL;
import com.dbs.mcare.framework.FrameworkConstants;
/**
 * context가 refresh될 때 필요한 상수나 환경설정 값을 출력하여 deploy 담당자가 내용을 한번 더 확인할 수 있도록 하기 위함 
 * 
 * @author aple
 *
 */
public class MCareServiceApplicationListener implements ApplicationListener<ContextRefreshedEvent> {
	private static final Logger logger = LoggerFactory.getLogger(MCareServiceApplicationListener.class);

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if(logger.isInfoEnabled()) {
			StringBuilder builder = new StringBuilder(); 
			
			builder.append(FrameworkConstants.NEW_LINE); 
			builder.append("병원 --------------------------------------------------------").append(FrameworkConstants.NEW_LINE); 
			
			HOSPITAL[] hospitals = MCareConstants.HOSPITAL.values(); 
			if(hospitals != null) { 
				for(HOSPITAL h : hospitals) { 
					builder.append(h.getHospitalName()).append(", ").append(h.getLinkUrl()).append(FrameworkConstants.NEW_LINE); 
				}
			}
			
			builder.append("------------------------------------------------------------").append(FrameworkConstants.NEW_LINE); 
		
			logger.info(builder.toString());
		}
	}
}
