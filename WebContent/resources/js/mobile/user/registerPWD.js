/**
 * mcare_mobile_registerPWD
 * @author heesungkim
 */
var mcare_mobile_registerPWD = function(){
	//상속
	mcare_mobile.call(this);
	//super
	var self = this;
	//변수
	var $pass2 = $("#pass2"),
		$pass3 = $("#pass3"),
		$pId = $("#pId"),
		$pNm = $("#pNm"),
		$userBirth = $("#userBirthDate"),
		$userGender = $("#userGenderCode"),
		$btn1 = $("#btn1");
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
		$btn1.on("click", function(e) {
			//버튼 클릭 배경
			$(this).addClass("active");
			
			if( validateData() ) {
				self.loading("show");
				
                var userData  = {
                    pId : $pId.val(), 
                    pName : $pNm.val(), 
                    passWordValue : $pass2.val(), 
                };
                
                $.ajax({
                    url : contextPath + "/mobile/user/registerCertifiedUser.json", 
                    contentType: "application/json; charset=UTF-8",
                    data : self.util.stringifyJson(userData), 
                    method : "POST", 
                    success : function(data) {
                    	try{
                    		if( data.msg !== undefined ){
                    			self.alert( data.msg );
                    			return false;
                    		} else {                    		
                    			showUserAgreement(data.result);
                    		}
                    		
                    	} catch(e) {
                    		
                    	}
                    },
                    error : function(xhr) {
                    	console.log(xhr);
                    },
                    complete: function(){
                    	//버튼 클릭 배경 제거
                    	$btn1.removeClass("active");
                    	self.loading("hide");
                    }
                });
			}
			//버튼 클릭 배경 제거
			$(this).removeClass("active");
		});
	};
	/**
	 * 유효성 체크
	 */
	var validateData = function(){
		if( $pass2.val() === "" ) {
			self.alert( self.getI18n("registerPWD005"),function(){				
				$pass2.focus();
			});
			return false;
		}
		
		if( $pass3.val() === "" ) {
			self.alert( self.getI18n("registerPWD006"),function(){				
				$pass3.focus();
			});
			return false;
		}
		if( $pass2.val() != $pass3.val() ){
			self.alert( self.getI18n("registerPWD008"),function(){				
				$pass3.focus();
			});
			return false;
		}
		
//		var testPwd = self.util.validatePWD( $pass2.val() );
//		//비밀번호 규칙
//		if( !testPwd.result ){
//			self.alert( self.getI18n( testPwd.code ) );
//			return false;
//		}
		return true;
	};
	/**
	 * 동의서 화면 요청
	 */
	var showUserAgreement = function (data){
		var pId = data.pId;
//		self.alert(pId + ""+ self.getI18n("registerPWD007"),function(){			
//			location.href = contextPath + "/login.page";
//		});
		self.changePage(contextPath + "/mobile/user/userAgreement.page?menuId=userAgreement&force=true&pId="+data.pId);
	}
};