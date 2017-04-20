/**
 * mcare_mobile_registerWithPId
 * 모바일 사용등록 - 환자번호 사용 
 * 
 */
var mcare_mobile_registerWithPId = function() {
	//상속
	mcare_mobile.call(this);
	//super
	var self = this;
	//변수
	var $pId = $("#pId"),
		$next = $("#next"),
		$hPid = $("#hPid");
	
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
		$pId.keypress(function(e) {
			onlyNumber(e);
		});
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
	 * 
	 */
	var nextPage = function(e) {
		if( validateData() ){
			var userData  = {
                    pId : $pId.val()
            };
			self.loading("show");
			
			self.ajaxMobile({
                url : contextPath + "/mobile/user/registerUserById.json", 
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
	 * 
	 */
	var certificationPage = function(data) {
		var domain = self.getDomain() + contextPath;
		
		if( data["bUnder14"] ){
			domain = domain + "/mobile/user/under14.page?menuId=under14&pId=" + $pId.val() + "&certReqType=registerPWD";
		}
		else {
			domain = domain + "/mobile/user/certification.page?menuId=certification&pId=" + $pId.val() + "&certReqType=registerPWD";
		}
		
		var msg = {
				type : "command", 
				functionType : "popup", 
				value : {"url" : domain}
		}; 
		self.toNative(msg);
	}
	
	/**
	 * 유효성 체크 일단은 null 체크만
	 */
	var validateData = function() {
		if( $pId.val() == 0 ) {
			self.alert(self.getI18n("registerWithPId002"));
			return;
		}
		return true;
	};
};