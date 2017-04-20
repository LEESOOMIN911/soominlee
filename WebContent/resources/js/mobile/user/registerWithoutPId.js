/**
 * mcare_mobile_registerWithoutPId
 * 모바일 사용등록
 * 
 */
var mcare_mobile_registerWithoutPId = function() {
	//상속
	mcare_mobile.call(this);
	//super
	var self = this;
	//변수
	var $pName = $("#pName"),
		$phoneNum = $("#phoneNum"),
		$birth = $("#birth"),
		$next = $("#next");
	
	var parameterMap = null;
	/**
	 * 초기화
	 */
	this.init = function() {
		setParameterMap();
		addEvent();
	};
	/**
	 * 파라미터 맵 초기화
	 */
	var setParameterMap = function(){
		parameterMap = self.util.getParameter();
	};
	/**
	 * 이벤트 등록
	 */
	var addEvent = function() {
//		$pName.keypress(function(e) {
//			//onlyText(e);
//		});

		$next.on("click", function(e) {
			nextPage(e);
		});
	};
	/**
	 * 
	 */
	var onlyNumber = function(e) {
		var code = e.which?e.which:e.keyCode;
		if (code < 48 || 57 < code){
			e.preventDefault();
		}
	};

	/**
	 * 인증하기전에 사용자 정보 얻어오기 
	 */
	var nextPage = function(e) {
		if( validateData() ){
			var userData  = {
                    pName : $pName.val(), 
                    cellphoneNo : $phoneNum.val(), 
                    birthDt : $birth.val()
            };
			self.loading("show");
			
			self.ajaxMobile({
                url : contextPath + "/mobile/user/registerUserByName.json", 
                //contentType: "application/json; charset=UTF-8",
                data : $.param(userData), 
                method : "POST", 
                success : function(data) {
                	try{                		
                		if( data.msg !== undefined ){
                			self.alert(data.msg);
                			return false;
                		} else if( data.extraMsg !== undefined ){
                			self.alert( data.extraMsg );
                			return;
                		} else {    					
                			certificationPage(data.result);
                		}
                	} catch(e) {
                		
                	}
                },
                error : function(xhr) {
                	console.log(xhr);
                },
                complete: function(){
                	self.loading("hide");
                }
            });
		}
	};
	
	/**
	 * 인증 요청 
	 */
	var certificationPage = function(data) {
		var domain = self.getDomain() + contextPath;
		
		if( data["bUnder14"] ){
			domain = domain + "/mobile/user/under14.page?menuId=under14&pId=" + data["pId"] + "&certReqType=registerPWD&phoneNo="+data["cellphoneNo"];
		}
		else {
			domain = domain + "/mobile/user/certification.page?menuId=certification&pId=" + data["pId"] + "&certReqType=registerPWD&phoneNo="+data["cellphoneNo"];
		}
		
		var msg = {
				type : "command", 
				functionType : "popup", 
				value : {"url" : domain}
		}; 
		self.toNative(msg);
	};
	
	/**
	 * 유효성 체크 일단은 null 체크만
	 */
	var validateData = function() {
		if( $pName.val() === "" ) {
			self.alert(self.getI18n("registerWithoutPId002"),function(){
				$pName.focus();
			});
			return false;
		} else if( $phoneNum.val() === "" ){
			self.alert(self.getI18n("registerWithoutPId004"),function(){
				$phoneNum.focus();
			});
			return false;
		} else if( $birth.val() === "" ){
			self.alert(self.getI18n("registerWithoutPId006"),function(){
				$birth.focus();
			});
			return false;
		}
		return true;
	};
};