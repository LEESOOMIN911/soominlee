package com.dbs.mcare.service.mobile.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dbs.mcare.framework.util.SessionUtil;


@Service 
public class MCareHelperService {
//	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired 
	private HelperApiFacade apiFacade; 

	/**
	 * goodbye 받았을 때 처리 
	 * @param request
	 */
	public void processGoodBye(HttpServletRequest request) { 
		// 차량번호  
		this.apiFacade.sendCarNo(request);
		
		// 다음 내원일 
		this.apiFacade.sendNextReservation(request);
	}

	/**
	 * welcome 받았을 때 처리 
	 * 
	 * @param request
	 */
	public void processWelcome(HttpServletRequest request) {
		final String pId = SessionUtil.getUserId(request); 
		
		// 오늘 예약이 있는 환자인가? 
		final List<Map<String, Object>> list= this.apiFacade.getTodayReservation(pId); 
		
		// 예약이 없는 환자이면 번호표 뽑으라고 함 
		if(list == null || list.size() == 0) {
			this.apiFacade.sendRequestReceiptTicketGuid(request);
		}
//		// 예약이 있는 환자이면 도착알림 해야함 
// 도착알림은 메뉴에서 빠져서 도우미에서도 삭제함 
//		else {
//			this.apiFacade.processArrivedConfirm(request);
//		}
		// 예약있는 환자이면 push 라도 알려주자 
		else {
			Map<String, Object> map = null; 
			final List<String> departmentNmList = new ArrayList<String>(); 
			String value = null; 
			
			// 목적지 추려내고 
			for(int i = 0; i < list.size(); i++) { 
				map = list.get(i); 
				value = (String) map.get("departmentNm"); 
				if(value != null) { 
					departmentNmList.add(value); 
				}
			}
			
			// 갈곳이 있으면 push를 전송 
			if(!departmentNmList.isEmpty()) { 
				final Object[] param = departmentNmList.toArray(new String[0]); 
				this.apiFacade.sendTodayReservation(request, param);
			}
		}
	}

}
