/**
 * mcare_mobile_register
 * 모바일 사용등록
 * @author 김병진
 */
var mcare_mobile_register = function() {
	//상속
	mcare_mobile.call(this);
	//super
	var self = this;
	//변수
	var $pId = $("#pId"),
		$off = $("#off"),
		$search = $("#search"),
		$next = $("#next");
		
	/**
	 * 초기화
	 */
	this.init = function() {
		addEvent();
	};
	/**
	 * 이벤트 등록
	 */
	var addEvent = function() {
		$pId.keypress(function(e) {
			onlyNumber(e);
		});
		
		$off.on("click", function(e) {
			offApp(e);
		});
		
		$search.on("click", function(e) {
			searchPage(e);
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
	var offApp = function(e) {
		if( confirm(self.getI18n("common006")) ) {
			// 종료
		} 
	};
	/**
	 * 
	 */
	var searchPage = function(e) {
		
		self.changePage( contextPath + "/mobile/user/searchPId.page?menuId=searchId" );
	};
	
	/**
	 * 
	 */
	var nextPage = function(e) {
		if( validateData() ){
			var userData  = {
                    pId : $pId.val(), 
            };
			self.loading("show");
			
            $.ajax({
                url : contextPath + "/mobile/user/checkPatientId.json", 
                contentType: "application/json; charset=UTF-8",
                data : self.util.stringifyJson(userData), 
                method : "POST", 
                success : function(data) {
                	try{
                		if( data.msg !== undefined ){
                			self.alert(data.msg);
                			return false;
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
			self.alert(self.getI18n("register003"));
			return;
		}
		return true;
	};
};