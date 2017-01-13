/**
 * mcare_mobile_sms
 * @author 김병진
 */
var mcare_mobile_sms = function(){
	//상속
	mcare_mobile.call(this);
	//super
	var self = this;
	//변수
	var $pNm = $("#pNm"),
		$smsCode = $("#smsCode"),
		$pid = $("#pid"),
		$encodePid = $("#encodePid"),
		$encodePname = $("#encodePname"),
		$btn = $("#btn"),
		$btn2 = $("#btn2"),
		$certReqType = $('#certReqType');
	/**	
	 * 초기화
	 */	
	this.init = function(){
		addEvent();
		$btn2.button();
		$btn2.addClass("disable").button("disable");
		$btn2.parent().css("padding","0");
	};
	/**
	 * 이벤트 등록
	 */
	var addEvent = function(){
		//인증요청 버튼 클릭 이벤트
		$btn.on("click", function(e) {
			//버튼 클릭 배경
			$(this).addClass("active");
			reqSMSCode();
		});
		$btn2.on("click", function(e) {
			//버튼 클릭 배경
			$(this).addClass("active");
			checkSMSCode();
		});
	};
	
	/**
	 * 인증번호 요청
	 */
	var reqSMSCode = function() {
		if(validatePname()) {
			var item  = {
                pId : $pid.val(), 
                pName : $pNm.val()
            };
			$.ajax({
                url : contextPath + "/mobile/user/reqSMSCode.json", 
                contentType: "application/json; charset=UTF-8",
                data : self.util.stringifyJson(item), 
                method : "POST", 
                success : function(data) {
                	try{
                		if( data.msg !== undefined ){
                			self.alert(data.msg);
                			return false;
                		} else {    					
                			self.alert(data.sendPhoneNo + self.getI18n("smsCertification013") + self.getI18n("smsCertification014"),function(){
                				$btn2.button();
                				$btn2.removeClass("disable").button("enable");
                				$btn2.parent().css({"padding":"0","outline":"none"});
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
        			$btn.removeClass("active");
                }
            });
		}
		//버튼 클릭 배경 제거
		$btn.removeClass("active");
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
					$btn2.removeClass("active");
				}
			});
		}
		//버튼 클릭 배경 제거
		$btn2.removeClass("active");
	}
	
	/**
	 * 환자이름 입력체크
	 */
	var validatePname = function() {
		if( $pNm.val() === "" ) {
			self.alert( self.getI18n("smsCertification006"),function(){				
				$input1.focus();
			} );
			return false;
		}
		
		return true;
	};
	
	/**
	 * SMS코드 입력체크
	 */
	var validateSMSCode = function() {
		if( $smsCode.val() === "" ) {
			self.alert( self.getI18n("smsCertification008"),function(){				
				$input1.focus();
			} );
			return false;
		}
		return true;
	};
	
	var callNative = function() {
		
		var reqUrl = "";
		var urlParam = "&pNm=" + $encodePname.val();
//		var reqUrl = "/mobile/user/registerPWD.page";
		urlParam = urlParam + "&reservedParam3=" + $encodePid.val();
		urlParam = urlParam + "&chartNoValue=" + $pid.val();
		
		if($certReqType.val() === "searchPId"){
			reqUrl = "/login.page?loginType=" + $certReqType.val();
		}else{
			reqUrl = "/mobile/user/" + $certReqType.val() + ".page?menuId=" + $certReqType.val();
		}
		
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