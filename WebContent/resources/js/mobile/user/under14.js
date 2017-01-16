/**
 * mcare_mobile_under14
 * 
 */
var mcare_mobile_under14 = function(){
	//상속
	mcare_mobile.call(this);
	//super
	var self = this;
	//변수
	var $smsCode = $("#smsCode"),
		$sendSMS = $(".sendSMS"),
		$smsCode = $("#smsCode"),
		$checkCode = $(".checkCode"),
		$pId = $("#pId"),
		$certReqType = $("#certReqType"),
		$phoneNo = $("#phoneNo"),
		$encodePid = $("#encodePid"),
		$encodePname = $("#encodePname"),
		$pNm = $("#pNm");
		
	/**	
	 * 초기화
	 */	
	this.init = function(){
		addEvent();
		$checkCode.button();
		$checkCode.addClass("disable").button("disable");
		$checkCode.parent().css("padding","0");
	};
	/**
	 * 이벤트 등록
	 */
	var addEvent = function(){
		//인증요청 버튼 클릭 이벤트
		$sendSMS.on("click", function(e) {
			//버튼 클릭 배경
			$(this).addClass("active");
			reqSMSCode();
		});
		//확인
		$checkCode.on("click", function(e) {
			//버튼 클릭 배경
			$(this).addClass("active");
			checkSMSCode();
		});
	};
	/**
	 * 환자이름 입력체크
	 */
	var validatePname = function() {
		if( $pNm.val() === "" ) {
			self.alert( self.getI18n("under14013"),function(){				
				$pNm.focus();
			} );
			return false;
		}
		
		return true;
	};
	/**
	 * 인증번호 요청
	 */
	var reqSMSCode = function() {
		if(validatePname()) {
			
			$.ajax({
				type: "POST",
                url : contextPath + "/mobile/user/reqSMSCode.json", 
                data : {
                    pId : $pid.val(), 
                    pName : $pNm.val()
                }, 
                success : function(data) {
                	try{
                		if( data.msg !== undefined ){
                			self.alert(data.msg);
                			return false;
                		} else if( data.extraMsg !== undefined ){
                			self.alert( data.extraMsg );
                			return;
                		} else {    					
                			self.alert(data.sendPhoneNo + self.getI18n("under14007") + self.getI18n("under14008"),function(){
                				$checkCode.button();
                				$checkCode.removeClass("disable").button("enable");
                				$checkCode.parent().css({"padding":"0","outline":"none"});
                			});
                			$encodePid.val(data.reservedParam3);
                			$encodePname.val(data.pNm);
                		}
                		
                	} catch(e) {
                		
                	}
                },
                error : function(xhr) {
                	console.log(xhr);
                },
                complete: function(){
                	//버튼 클릭 배경 제거
                	$sendSMS.removeClass("active");
                }
            });
		}
		//버튼 클릭 배경 제거
		$sendSMS.removeClass("active");
	};
	
	var checkSMSCode = function() {
		if(validateSMSCode()) {
			$.ajax({
				url : contextPath + "/mobile/user/checkSMSCode.json",
				method : "GET",
				dataType : "json",
				data: {'smsCode':$smsCode.val()},
				contentType : "application/json",
				success : function(data){
					try{
						if( data.resultMsg == "fail" ){
							self.alert(self.getI18n("under14010"));
							return false;
						} else if( data.extraMsg !== undefined ){
							self.alert( data.extraMsg );
							return;
						} else {						
							callNative();
						}
						
					} catch(e) {
						
					}
				},
				error : function(xhr,d,t){
					self.alert(self.getI18n("under14011"));
					console.log( xhr );
				},
				complete: function(){
					//버튼 클릭 배경 제거
					$checkCode.removeClass("active");
				}
			});
		}
		//버튼 클릭 배경 제거
		$checkCode.removeClass("active");
	}

	/**
	 * SMS코드 입력체크
	 */
	var validateSMSCode = function() {
		if( $smsCode.val() === "" ) {
			self.alert( self.getI18n("under14009"),function(){				
				$smsCode.focus();
			} );
			return false;
		} 
		return true;
	};
	
	var callNative = function() {
		
		var reqUrl = "";
		
		if($certReqType.val() == "searchPId") {
			reqUrl = "/login.page?loginType=" + $certReqType.val();
		}
		else if ($certReqType.val() == "registerPId") {
			reqUrl = "/mobile/user/register.page?menuId=userRegister&registerType=" + $certReqType.val();
		}
		else {
			reqUrl = "/mobile/user/" + $certReqType.val() + ".page?menuId=" + $certReqType.val();
		}

		var urlParam = "&pNm=" + $encodePname.val();
		urlParam = urlParam + "&reservedParam3=" + $encodePid.val();
		urlParam = urlParam + "&chartNoValue=" + $pId.val();
		reqUrl = reqUrl + urlParam;
		
		var domain = self.getDomain();
		var varItem = {};
		
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
		
		self.toNative(item);
	};
};