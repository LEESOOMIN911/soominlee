/**
 * mcare_mobile_resetPWD
 * @author 김병진
 */
var mcare_mobile_resetPWD = function(){
	//상속
	mcare_mobile.call(this);
	//super
	var self = this;
	//변수
	var $pass1 = $("#pass1"),
		$pass2 = $("#pass2"),
		$pass3 = $("#pass3"),
		$pId = $("#pId"),
		$pName = $("#pName"),
		$userBirth = $("#userBirth"),
		$userGender = $("#userGender"),
		$btn1 = $("#btn1"),
		$resetPWDType = $('#resetPWDType');
	//전달 받은 파라미터 
	var parameterMap = null;
	/**
	 * 초기화
	 */	
	this.init = function(){
		//파라미터 설정
		setParameterMap();
		if( parameterMap["force"] === "true" ){
			$("#menuBars_btn,#headerArrowLeft_btn").hide();
		}
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
	var addEvent = function(){
		$btn1.on("click", function(e) {
			//버튼 클릭 배경
			$(this).addClass("active");
			if( validateData() ) {				
                $.ajax({
                    url : contextPath + "/mobile/user/resetPWD.json", 
                    data: {'oldPassWordValue':$pass1.val(),'newPassWordValue':$pass2.val(),'resetPWDType':$resetPWDType.val(),'pId':$pId.val()},
                    method : "GET", 
                    success : function(data) {
                    	try{
                    		if( data.resultMsg){
                    			self.alert( self.getI18n("resetPWD007") );
                    			return false;
                    		} else if( data.msg !== undefined ){
                    			self.alert( data.msg );
                    			return false;
                    		} else {							
                    			self.alert( self.getI18n("resetPWD009"),function(){
                    				self.changePage(contextPath + "/logout.page");
                    			} );
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
		if( $pass1.val() === "" ) {
			self.alert( self.getI18n("resetPWD005"),function(){				
				$pass1.focus();
			});
			return false;
		}
		if( $pass2.val() === "" ) {
			self.alert( self.getI18n("resetPWD010"),function(){				
				$pass2.focus();
			});
			return false;
		}
		if( $pass3.val() === "" ) {
			self.alert( self.getI18n("resetPWD006"),function(){				
				$pass3.focus();
			});
			return false;
		}
		if( $pass1.val() === $pass2.val() ){
			self.alert( self.getI18n("resetPWD011"),function(){				
				$pass2.focus();
			});
			return false;
		}
		if( $pass2.val() != $pass3.val() ){
			self.alert( self.getI18n("resetPWD008"),function(){				
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
};