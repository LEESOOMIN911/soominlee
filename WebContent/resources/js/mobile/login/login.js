/**
 * mcare_mobile_login
 */
var mcare_mobile_login = function(){
	//상속
	mcare_mobile.call(this);
	//super
	var self = this;
	//변수
	var deviceToken = "",
		certiType = "",
		$guestBtn = $("#guest_btn"),
		$loginBtn = $("#login_btn"),
		$pId = $("#pId"),
		$password = $("#password"),
		$rememberMe = $("#rememberMe"),
		$findId = $("#login_btn1"),
		$hPid = $("#hPid"),
		$findPWD = $("#login_btn2"),
		$regBtn = $("#reg_btn");
	
	var parameterMap = null;
	
	/**
	 * 초기화
	 */
	this.init = function(){
		setParameterMap();
		initDeviceToken();
		addEvent();
		checkSearchPid();
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
		//로그인 버튼 클릭 이벤트
		$loginBtn.on( "click", function(e){
			$(this).addClass("active");
			if( validateForm() ){
				//버튼 클릭 배경
				login();
			} else {
				$(this).removeClass("active");
			}
		});
		//환자번호 찾기 버튼 클릭 이벤트
		$findId.on( "click", function(e){
			self.changePage( contextPath + "/mobile/user/searchPId.page?menuId=searchPNumber&searchPIdType=searchPId" );
		});
		//비밀번호 설정 버튼 클릭 이벤트
		$findPWD.on( "click", function(e){
			self.changePage( contextPath + "/mobile/user/authUserInfo.page?menuId=authUserInfo" );
		});
		//사용등록 버튼 클릭 이벤트
		$regBtn.on( "click", function(e){
			self.changePage( contextPath + "/mobile/user/register.page?menuId=userRegister" );
		});
	};
	/**
	 * device token 설정
	 */
	var initDeviceToken = function(){
		var msg = {
				type : "command", 
				functionType : "token", 
				value : {
					callbackFn : "window.activeObj.getDeviceTokenCallBack"
				}
		}; 
		self.toNative( msg );
	};
	/**
	 * 
	 */
	this.getDeviceTokenCallBack = function(token, type){
		deviceToken = token; 
		certiType = type;
		//self.alert("token : " + token); 
	};
	/**
	 * 
	 */
	var validateForm = function(){
		if( $pId.val() === "" ) {
			self.alert( self.getI18n( "login005" ),function(){				
				$pId.focus();
			});
	        return false;
		} else if( $password.val() === "" ) {
			self.alert( self.getI18n( "login006" ), function(){				
				$password.focus();
			});
	        return false;
		}else{
			return true;
		}
	};
	/**
	 * 
	 */
	var login = function(){
		self.loading("show");
		var $isAuto = $rememberMe.is(':checked') ? true : false;
		
		$.ajax({
		    type: "POST",
		    url: contextPath + "/loginSubmit.json",
		    cache: false,
		    data: {
		    	pId : $pId.val(),
		        password : $password.val(), 
		        rememberMe : $isAuto, 
		        deviceTokenId : deviceToken,
		        certType : certiType
		    },
		    dataType: "json",
		    success: function(data) {
		    	try{
		    		if (data.msg) {
		    			if( data.type !== undefined ){
		    				if( data.type === "UserBlockException" ){ 
		    					self.alert( data.msg, function(){
		    						self.changePage( contextPath + data.nextPage );
		    					});
		    				} else if( data.type === "PwdOverDueException" ){
		    					self.alert( data.msg, function(){
		    						self.changePage( contextPath + data.nextPage );
		    					});
		    				} else{
		    					self.alert(data.msg);
		    				}
		    			} else {						
		    				self.alert(data.msg);
		    			}
		    			return;
		    		} else {
		    			sessionStorage.removeItem("hash");
		    			if( parameterMap["returnUrl"] && parameterMap["returnUrl"] !== "" ){
		    				//context 붙어와서 뺐음
		    				self.changePage( parameterMap["returnUrl"] );
		    			} else {		        		
		    				location.replace(contextPath + data.nextPage);
		    			}
		    		}
		    		
		    	} catch(e) {
		    		
		    	}
		    },
		    error:function(xhr){
		    	console.log(xhr);
		    },
		    complete:function(){
		    	self.loading("hide");
		    	//버튼 클릭 배경제거
		    	$loginBtn.removeClass("active");
		    }
		});
	};
	/**
	 * 환자번호 찾기에서 온것인지 확인
	 */
	var checkSearchPid = function(){
		//loginType이 serarchPId인 경우 환자번호 필드에 pId 넣어주기
		if(parameterMap["loginType"] == "searchPId") {
			$pId.val( $hPid.val() );
		}
	};
};