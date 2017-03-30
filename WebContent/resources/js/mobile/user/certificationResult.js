/**
 * mcare_mobile_certification
 * 모바일 사용등록
 * @author 김병진
 */
var mcare_mobile_certification_result = function() {
	
	//상속
	mcare_mobile.call(this);
	//super
	var self = this;
	
	var $close = $('#closeCert');

	/**
	 * 초기화
	 */
	this.init = function() {
		var pName = $("#pName").val(),
			userBirthDate = $("#userBirthDate").val(),
			userGenderCode = $("#userGenderCode").val(),
			reservedParam1 = $("#reservedParam1").val(),
			reservedParam2 = $("#reservedParam2").val(),
			reservedParam3 = $("#reservedParam3").val(),
			code =$("#errorMsgCode").val();
			
		
		//HTTP의 Context 하위의 파라미터 생성 
		//EX : mobile/user/registerPWD.page?userName=name&userBirthDate=date&userGenderCode="code"& + 
		//		reservedParam1=iPin&reservedParam2=registerPWD&reservedParam3=MTIzNA==
		var reqUrl = "";
		
		if(reservedParam2 == "searchPId") {
			reqUrl = "/login.page?loginType=" + reservedParam2;
		}else if (reservedParam2 == "registerPId") {
			reqUrl = "/mobile/user/register.page?menuId=userRegister&registerType=" + reservedParam2;
		}else{
			reqUrl = "/mobile/user/" + reservedParam2 + ".page?menuId=" + reservedParam2;
		}
		
		var urlParam = "&pName=" + pName;
		urlParam = urlParam + "&userBirthDate=" + userBirthDate;
		urlParam = urlParam + "&userGenderCode=" + userGenderCode;
		urlParam = urlParam + "&reservedParam1=" + reservedParam1;
		urlParam = urlParam + "&reservedParam2=" + reservedParam2;
		urlParam = urlParam + "&reservedParam3=" + reservedParam3;
		
		reqUrl = reqUrl + urlParam;
		
		var domain = self.getDomain();
		var varItem = {};
		
		if(code !== "") {
			self.alert( self.getI18n(code),function(){
				reqUrl = "/login.page";
				
				//contextPath의 상태에 따라 context 도메인 생성 
				if(contextPath == "" || contextPath == null){
					//context 없음 
					varItem['url'] = domain + reqUrl;
				} else {
					//context 있음 
					varItem['url'] = domain + contextPath + reqUrl;
				}
				
				var item = {};
				item['type'] = "command";
				item['functionType'] = "certificationResult";
				item['value'] = varItem;
				
				var data = JSON.stringify(item);
				self.toNative(item);
			});
		}
		else {
			
			//contextPath의 상태에 따라 context 도메인 생성 
			if(contextPath == "" || contextPath == null){
				//context 없음 
				varItem['url'] = domain + reqUrl;
			} else {
				//context 있음 
				varItem['url'] = domain + contextPath + reqUrl;
			}
			
			var item = {};
			item['type'] = "command";
			item['functionType'] = "certificationResult";
			item['value'] = varItem;
			
			var data = JSON.stringify(item);
			self.toNative(item);
		}

		addEvent();
	};
	/**
	 * 이벤트 등록
	 */
	var addEvent = function() {
		
	};
};