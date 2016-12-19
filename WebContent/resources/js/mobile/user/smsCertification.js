/**
 * mcare_mobile_smsCertification
 * @author 김병진
 */
var mcare_mobile_smsCertification = function(){
	//상속
	mcare_mobile.call(this);
	//super
	var self = this;
	//변수
	var $smsCode = $("#smsCode"),
		$btn = $("#btn");
	/**	
	 * 초기화
	 */	
	this.init = function(){
		addEvent();
	};
	/**
	 * 이벤트 등록
	 */
	var addEvent = function(){
		//인증요청 버튼 클릭 이벤트
		$btn.on("click", function(e) {
			//버튼 클릭 배경
			$(this).addClass("active");
			
			if(validateData()) {
				$.ajax({
					url : contextPath + "/mobile/user/checkSMSCode.json",
					method : "GET",
					dataType : "json",
					data: {'smsCode':$smsCode.val()},
					contentType : "application/json",
					success : function(data){
						try{
							if( data.resultMsg == "fail" ){
								self.alert(self.getI18n("smsCertification009"));
								return false;
							} else {							
								callNative();
							}
							
						} catch(e) {
							
						}
					},
					error : function(xhr,d,t){
						self.alert(self.getI18n("smsCertification010"));
						console.log( xhr );
					},
					complete: function(){
						//버튼 클릭 배경 제거
						$btn.removeClass("active");
					}
				});
			}
			//버튼 클릭 배경 제거
			$(this).removeClass("active");
		});
	};
	
	var callNative = function() {
		var userName = $("#userName").val();
		var reservedParam3 = $("#reservedParam3").val();
		//HTTP의 Context 하위의 파라미터 생성 
		//EX : mobile/user/registerPWD.page?userName=name&userBirthDate=date&userGenderCode="code"& + 
		//		reservedParam1=iPin&reservedParam2=registerPWD&reservedParam3=MTIzNA==
		var reqUrl = "/mobile/user/registerPWD.page";
		var urlParam = "?userName=" + userName;
		urlParam = urlParam + "&reservedParam3=" + reservedParam3;
		urlParam = urlParam + "&menuId=registerPWD";
		reqUrl = reqUrl + urlParam;
		
		var domain = self.getDomain();
		var varItem = {};
		
		//contextPath의 상태에 따라 context 도메인 생성 
		if(contextPath == "" || contextPath == null){
			//context 없음 
			varItem['url'] = domain + reqUrl;
		} else {
			//context 있음 
			varItem['url'] = domain + "/" + contextPath + reqUrl;
		}
		
		var item = {};
		item['type'] = "command";
		item['functionType'] = "certificationResult";
		item['value'] = varItem;
		
		self.toNative(item);
	};
	
	/**
	 * 유효성 체크
	 */
	var validateData = function() {
		if( $smsCode.val() === "" ) {
			self.alert( self.getI18n("smsCertification008"),function(){				
				$input1.focus();
			});
			return false;
		}
		return true;
	};
};