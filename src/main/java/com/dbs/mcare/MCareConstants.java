package com.dbs.mcare;

import org.springframework.util.StringUtils;

import com.dbs.mcare.framework.FrameworkConstants;
import com.dbs.mcare.framework.template.MCareServiceDomain;
/**
 * 서비스에서 사용하는 상수들 
 * @author aple 
 *
 */
public abstract class MCareConstants {
	/* 관리자 session timeout */ 
	public static final int ADMIN_SESSION_TIMEOUT_SEC = 60 * 60; // 60초 X 60개 = 1시간  
	/* 5자리가 아니거나, 우편번호가 등록되어 있지 않은 경우 */ 
	public static final String INVALID_POSTNO = "00"; 
	/* 생년월일 정보가 없거나 비정상적인 경우 */ 
	public static final int INVALID_AGE_GROUP = 200; 	
	
	/**
	 * MCare 실험용 환자 
	 * iOS 앱 심사용 데모 테스트를 위한 코드이기도 함
	 * 
	 * @author aple
	 *
	 */
	public static class MCARE_TEST_USER {
		// 실험용 환자를 활성화 시킬지 여부 
		public static final boolean ACTIVATE = true; 
		
		public enum INFO { 
			USER1("000000002", "권혜은", "19770125", "01020125023", "11111"), 
			USER4("000000004", "서영일", "19830628", "01033661397", "11111"); 
			
			private String pId; 
			private String pName; 
			private String birthDt; 
			private String cellPhoneNumber; 
			private String tempPwd; 
			
			private INFO(String pId, String pName, String birthDt, String cellPhoneNumber, String tempPwd) { 
				this.pId = pId; 
				this.pName = pName; 
				this.birthDt = birthDt; 
				this.cellPhoneNumber = cellPhoneNumber; 
				this.tempPwd = tempPwd; 
			}
			
			/**
			 * pId로 테스트 사용자 정보 찾기 
			 * @param pId
			 * @return
			 */
			public static INFO convert(String pId) { 
				if(StringUtils.isEmpty(pId)) {
					return null; 
				}
				
				INFO[] infos = INFO.values(); 
				
				for(INFO i : infos) { 
					if(i.getPid().equals(pId)) {
						return i; 
					}
				}
				
				return null; 
			}
			
			/**
			 * 테스트 ID인지 여부 
			 * @param pId
			 * @return
			 */
			public static boolean isTestId(String pId) { 
				if(StringUtils.isEmpty(pId)) {
					return false; 
				}
				
				INFO info = convert(pId); 
				if(info != null) { 
					return true; 
				}
				
				return false; 
			}
			
			/**
			 * 식별번호 
			 * @return
			 */
			public String getPid() { 
				return this.pId; 
			}
			/**
			 * 이름 
			 * @return
			 */
			public String getPname() { 
				return this.pName; 
			}
			/**
			 * 생년월일 
			 * @return
			 */
			public String getBirthDt() { 
				return this.birthDt; 
			}
			/**
			 * 전화번호 
			 * @return
			 */
			public String getCellPhoneNumber() { 
				return this.cellPhoneNumber; 
			}
			/**
			 * 임시비밀번호 
			 * @return
			 */
			public String getTempPassword() { 
				return this.tempPwd; 
			}
		}

		// 실험용 환자의 SMS 본인인증시 전달될 인증 코드값 
		public static final String SMS_CERTIFICATION_CODE = "123456";
		// 실험용 환자용 임시 비밀번호 
		public static final String TEMP_PWD = "11aabb**"; 
		// 테스트용 진료과 코드 
		public static final String DEPARTMENT_CODE = "0000000000"; 
	}
	
	
	
	/* ---------------------------------------------------
	 * 병원목록 (병원이 바뀌면 바뀔 상수 값) 
	  ---------------------------------------------------*/
	public enum HOSPITAL {		
		// 부산대학교 병원, 본원 
		PNUH_PUSAN("PNUH_B", "부산대학교병원", "https://beacon.pnuh.or.kr", "/mcare", FrameworkConstants.URI_SPECIAL_PAGE.INDEX.getPage(), "https://beacon.pnuh.or.kr/mcare/resources/css/images/mobile/main/toplogo.png"); 
		
		private MCareServiceDomain serviceDomain; 
		/**
		 * 생성자 
		 * @param code
		 * @param hospitalName
		 * @param domain
		 * @param contextName
		 * @param startPage
		 * @param logoImageUrl
		 */
		private HOSPITAL(String code, String hospitalName, String domain, String contextName, String startPage, String logoImageUrl) { 
			this.serviceDomain = new MCareServiceDomain(code, hospitalName, domain, contextName, startPage, logoImageUrl); 
		}
		
		/**
		 * 코드로 병원 찾기 
		 * @param code
		 * @return
		 */
		public static HOSPITAL convertCode(String code) { 
			if(StringUtils.isEmpty(code)) {
				return null; 
			}
			
			HOSPITAL[] h = HOSPITAL.values(); 
			
			for(HOSPITAL hospital : h) { 
				if(hospital.serviceDomain.getCode().equals(code)) {
					return hospital; 
				}
			}
			
			return null; 
		}	
		
		/**
		 * 구분코드 
		 * @return
		 */
		public String getCode() { 
			return this.serviceDomain.getCode(); 
		}
		/**
		 * 병원명 
		 * @return
		 */
		public String getHospitalName() { 
			return this.serviceDomain.getHosptialName(); 
		}
		/**
		 * 도메인 
		 * @return
		 */
		public String getDomain() { 
			return this.serviceDomain.getDomain(); 
		}
		
		public String getContextName() { 
			return this.serviceDomain.getContextName(); 
		}
		/**
		 * 시작페이지 
		 * @return
		 */
		public String getStartPage() {
			return this.serviceDomain.getStartPage(); 
		}
		/**
		 * 링크걸때 사용할 주소 
		 * @return
		 */
		public String getLinkUrl() { 
			return this.serviceDomain.getLinkUrl(); 
		}		
		/**
		 * 이미지 URL 
		 * @return
		 */
		public String getLogoImageUrl() { 
			return this.serviceDomain.getLogoImageUrl(); 
		}
	}		
}
