package com.dbs.mcare.controller.admin;

import java.text.MessageFormat;
import java.text.ParseException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dbs.mcare.exception.admin.AdminControllerException;
import com.dbs.mcare.framework.exception.MCareServiceException;
import com.dbs.mcare.framework.service.admin.telno.TelNoService;
import com.dbs.mcare.framework.service.admin.telno.repository.dao.TelNo;

/**
 * 전화번호 관리 
 * @author hyeeun 
 *
 */
@Controller
@RequestMapping("/admin/telno")
public class TelNoController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass()); 
	private final MessageFormat telFormat = new MessageFormat("{0}-{1}-{2}"); 
	
	@Autowired 
	private TelNoService telNoService; 

	@RequestMapping(value = "/view.page", method = RequestMethod.GET)
	public Model telno(Model model) throws AdminControllerException {
		return model; 
	}
	
	@RequestMapping(value="/getList.json", method = RequestMethod.POST)
	@ResponseBody 
	public Map<String, Object> getTelNoList(@RequestBody Map<String, Object> map) throws AdminControllerException {
		try { 
			return this.telNoService.getPagingData(map);
		}
		catch(final MCareServiceException ex) {
			this.logger.error("전화번호 조회 실패=", ex);
			throw new AdminControllerException(ex); 
		}
	}
	
	@RequestMapping(value="/insert.json", method = RequestMethod.POST)
	@ResponseBody 	
	public TelNo save(@RequestBody TelNo telNo) throws AdminControllerException {
		// 유효성 확인 
		this.checkTelNoData(telNo);
		this.checkTelNoFormat(telNo.getTelValue());
		
		try { 
			// 추가 
			this.telNoService.add(telNo);
			this.logger.debug("insert : " + telNo.getTelValue());
		}
		catch(final MCareServiceException ex) { 
			this.logger.error("전화번호 추가 실패", ex);
			throw new AdminControllerException(ex); 
		}

		return telNo; 
	}
	
	@RequestMapping(value="/remove.json", method = RequestMethod.POST)
	@ResponseBody 		
	public TelNo remove(@RequestBody TelNo telNo) throws AdminControllerException {
		// 유효성 확인 
		this.checkTelNoSeq(telNo);
		
		try { 
			// 삭제 
			this.telNoService.remove(telNo);
			this.logger.debug("remove : " + telNo.getTelValue());
		}
		catch(final MCareServiceException ex) { 
			this.logger.error("전화번호 삭제 실패", ex);
			throw new AdminControllerException(ex); 
		}
		
		return telNo; 
	}
	
	@RequestMapping(value="/update.json", method = RequestMethod.POST)
	@ResponseBody 		
	public TelNo update(@RequestBody TelNo telNo) throws AdminControllerException { 
		// 유효성 확인 
		this.checkTelNoSeq(telNo);
		this.checkTelNoData(telNo);
		this.checkTelNoFormat(telNo.getTelValue());
		
		// 수정 
		try { 
			this.telNoService.modify(telNo);
			this.logger.debug("update : " + telNo.getTelnoSeq() + ", " + telNo.getTelValue());
		}
		catch(final MCareServiceException ex) { 
			this.logger.error("전화번호 수정 실패", ex);
			throw new AdminControllerException(ex); 
		}
		
		return telNo; 
	}
	
	/**
	 * seq있는지 확인 
	 * @param telNo
	 * @throws AdminControllerException
	 */
	private void checkTelNoSeq(TelNo telNo) throws AdminControllerException {
		// seq 필요 
		if(telNo.getTelnoSeq() == null) { 
			throw new AdminControllerException("seq필요함"); 
		}
	}
	/**
	 * insert/update를 위한 data가 있는지 확인 
	 * @param telNo
	 * @throws AdminControllerException
	 */
	private void checkTelNoData(TelNo telNo) throws AdminControllerException {
		// 건물이든 방이든 둘 중 하나는 필요 
		if(StringUtils.isEmpty(telNo.getBuildingDesc()) && StringUtils.isEmpty(telNo.getRoomDesc())) {
			final AdminControllerException a =  new AdminControllerException("건물 혹은 방에 대한 이름, 둘 중 하나는 필수임"); 
			this.logger.error("유효성확인", a);
			throw a; 
		}
		
		// 전화번호는 필수 
		if(StringUtils.isEmpty(telNo.getTelValue())) {
			final AdminControllerException a =  new AdminControllerException("전화번호는 필수임"); 
			this.logger.error("유효성확인", a);
			throw a; 
		}		
	}
	/**
	 * 전화번호 형식 확인 
	 * @param telNo
	 * @throws AdminControllerException
	 */
	private void checkTelNoFormat(String telNo) throws AdminControllerException { 
		if(telNo == null) { 
			final AdminControllerException a = new AdminControllerException("유효하지 않은 전화번호"); 
			this.logger.error("전화번호는 null임");
			throw a; 
		}
		
		Object[] obj = null; 
		
		try { 
			obj = this.telFormat.parse(telNo); 
		}
		catch(final ParseException ex) {
			this.logger.error("전화번호 포맷. " + telNo, ex);
			throw new AdminControllerException(ex); 
		}
		
		if(obj == null || obj.length != 3) {
			this.logger.error("전화번호 포맷 : ", telNo);
			throw new AdminControllerException("유효하지 않은 전화번호"); 
		}
	}
}
