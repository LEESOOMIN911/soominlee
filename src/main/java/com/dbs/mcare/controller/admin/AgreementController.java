package com.dbs.mcare.controller.admin;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dbs.mcare.exception.admin.AdminControllerException;
import com.dbs.mcare.framework.exception.MCareServiceException;
import com.dbs.mcare.framework.service.admin.agreement.AgreementService;
import com.dbs.mcare.framework.service.admin.agreement.repository.dao.Agreement;

/**
 * 동의서 Controller
 * @author heesungkim
 *
 */
@Controller
@RequestMapping("/admin/agreement")
public class AgreementController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass()); 
	
	@Autowired 
	private AgreementService agreementService; 

	@RequestMapping(value = "/view.page", method = RequestMethod.GET)
	public Model agreement(Model model) throws AdminControllerException {
		return model; 
	}
	
	@RequestMapping(value="/getList.json", method = RequestMethod.POST)
	@ResponseBody 
	public Map<String, Object> getAgreementList(@RequestBody Map<String, Object> map) throws AdminControllerException {
		try {
			return this.agreementService.getPagingData(map);
		}
		catch(final MCareServiceException ex) {
			throw new AdminControllerException(ex); 
		}
	}
	
	@RequestMapping(value="/insert.json", method = RequestMethod.POST)
	@ResponseBody 	
	public Agreement insert(@RequestBody Agreement agreement) throws AdminControllerException {
		// 유효성 확인 
		this.checkAgreementData(agreement);
		
		try { 
			agreement.setNewYn("Y"); //필수여부 기본값으로 추가
			// 추가 
			this.agreementService.add(agreement);
			this.logger.debug("agreement insert : title = " + agreement.getAgreementName());
		}
		catch(final MCareServiceException ex) { 
			throw new AdminControllerException(ex); 
		}

		return agreement; 
	}
	
	@RequestMapping(value="/remove.json", method = RequestMethod.POST)
	@ResponseBody 		
	public Agreement remove(@RequestBody Agreement agreement) throws AdminControllerException {
		// 유효성 확인 
		this.logger.debug("Remove Tester");
		this.checkAgreementSeq(agreement);
		try { 
			// 삭제 
			this.agreementService.remove(agreement);
			this.logger.debug("agreement remove : seq = " + agreement.getAgreementSeq() + ", title = " + agreement.getAgreementName());
		}
		catch(final MCareServiceException ex) { 
			throw new AdminControllerException(ex); 
		}
		
		return agreement; 
	}
	
	@RequestMapping(value="/addVersion.json", method = RequestMethod.POST)
	@ResponseBody 
	@Transactional(value = "mcareTransactionManager", rollbackFor = { Exception.class })
	public Agreement addVersion(@RequestBody Agreement agreement) throws AdminControllerException {
		
		this.logger.debug("Update Call");
		// 유효성 확인 
		this.checkAgreementSeq(agreement);
		this.checkAgreementData(agreement);
		
		//동의서 수정은 기존 version을 증가시키고 Insert
		agreement.setVersionNumber(agreement.getVersionNumber() + 1);
		// 수정 
		try { 
			//동의서 수정은 Version Add
			this.agreementService.add(agreement);
			//현재 동의서의 최신 여부를 N으로 설정
			agreement.setNewYn("N");
			this.agreementService.modify(agreement);
			this.logger.debug("agreement update : seq = " + agreement.getAgreementSeq());
		}
		catch(final MCareServiceException ex) { 
			throw new AdminControllerException(ex); 
		}
		
		return agreement; 
	}
	
	/**
	 * 동의서 seq있는지 확인 
	 * @param agreement
	 * @throws AdminControllerException
	 */
	private void checkAgreementSeq(Agreement agreement) throws AdminControllerException {
		if(agreement.getAgreementSeq() == null) {
			throw new AdminControllerException("seq 필수");
		}
	}
	
	/**
	 * 동의서 필수 입력 데이터 검증
	 * @param agreement
	 * @throws AdminControllerException
	 */
	private void checkAgreementData(Agreement agreement) throws AdminControllerException {
		
		if(StringUtils.isEmpty(agreement.getAgreementName())) { 
			throw new AdminControllerException("동의서 제목 필수"); 
		}
		
		if(StringUtils.isEmpty(agreement.getAgreementCl())) {
			throw new AdminControllerException("동의서 내용 필수"); 
		}
		
	}
}
