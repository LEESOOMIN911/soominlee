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
		$regBtn = $("#reg_btn"),
		$currentUser = $("#currentUser");
	
	var parameterMap = null;
	//
	var rememberIdKey = "pId";
	
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
		//아이디 저장 - 무조건 마지막 접속 아이디
		$pId.on("keyup",function(e){
			setLocal( rememberIdKey, $(this).val() );
		});
		//최근 아이디 가져오기
		$currentUser.on("click",function(e){
			e.preventDefault();
			$pId.val( getLocal( rememberIdKey ) );
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
		    				if( data.type === "UserBlockException" || data.type === "PwdForcedChangeException"){ 
		    					self.alert( data.msg, function(){
		    						self.changePage( contextPath + data.nextPage );
		    					});
		    				//비밀번호 기한 만료일 경우, 예외처리 
		    				} else if( data.type === "PwdOverDueException" ){
		    					var options = {
		    							content : data.msg,
		    							pwdChange : function(e){
		    								self.changePage( contextPath + data.nextPage );
		    							},
		    							laterChange : function(e){
		    								pwdLaterChange();
		    							}
		    					};
		    					// 지금 변경, 나중에 변경 컨펌창 띄우기 
		    					confirm( options );
		    				}else{
		    					self.alert(data.msg);
		    				}
		    			} else if( data.extraMsg !== undefined ){
		    				self.alert( data.extraMsg );
		    				return;
		    			} else {						
		    				self.alert(data.msg);
		    			}
		    			return;
		    		} else {
		    			sessionStorage.removeItem("hash");
		    			//로그인된 아이디를 저장함
		    			setLocal( rememberIdKey, $pId.val() );
		    			
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
	/**
	 * localStorage 설정
	 */
	var setLocal = function(key,value){
	   localStorage.setItem(key,value);
	};
	/**
	 * localStorage 삭제
	 */ 
	var deleteLocal = function(key){
	    localStorage.removeItem(key);
	};
	/**
	 * localStorage 가져오기
	 */ 
	var getLocal = function(key) {	    
	    return localStorage.getItem(key);
	};
	/**
	 * 비밀번호 변경여부 컨펌창 
	 */
	var confirm = function( options ){
		try{			
			var confirmDialog = $("#confirmDialog"),
				content = confirmDialog.find(".confirmcontent"),
				pwdChange = confirmDialog.find(".pwdChange"),
				laterChange = confirmDialog.find(".laterChange");		
			
			if( options["content"] === "" || options["content"] === undefined ){
				console.log( "text empty or undefined" );
			}
			content.html( options["content"] );
			
			pwdChange.on("click",function(e){
				e.preventDefault();
				options["pwdChange"]();
				confirmDialog.popup("close");
			});
			laterChange.on("click",function(e){
				e.preventDefault();
				options["laterChange"]();
				confirmDialog.popup("close");
				
			});
			setTimeout(function(){				
				confirmDialog.popup("open",{});
				self.loading("hide");
			},300);
		} catch(e) {
			self.log(e,"pwd confirm");
		}
	};
	/**
	 * 비밀번호 나중에 변경 처리 
	 */
	var pwdLaterChange = function(){
		self.loading("show");
		$.ajax({
		    type: "POST",
		    url: contextPath + "/mobile/user/renewalPasswordUpdateTime.json",
		    cache: false,
		    data: {},
		    dataType: "json",
		    success: function(data) {
		    	try{
		    		if (data.msg) {
		    			if( data.extraMsg !== undefined ){
		    				self.alert( data.extraMsg );
		    				return;
		    			} else {						
		    				self.alert(data.msg);
		    			}
		    			return;
		    		//비밀번호 기한 연장 성공하면 다시 로그인 처리 
		    		} else if( data["success"] === "true" ){
		    			//다시 로그인 요청 - 필요한 정보는 이미 입력되어 있으므로 버튼 이벤트 트리거 
		    			$loginBtn.trigger("click");
		    		} else if( data["success"] === "false" ){
		    			self.alert(self.getI18n("login017"), function(){
		    				
		    			});
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
};