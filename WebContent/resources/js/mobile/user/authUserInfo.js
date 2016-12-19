/**
 * mcare_mobile_authUserInfo
 */
var mcare_mobile_authUserInfo = function(){
	//상속
	mcare_mobile.call(this);
	//super
	var self = this;
	
	var $pId = $(".for_pid"),
		$pName = $(".for_name"),
		$pCellphone = $(".for_tel"),
		$sendSMS = $(".sendSMS");
		
	//초기화
	this.init = function(){
		addEvent();
	};
	/**
	 * 이벤트 등록
	 */
	var addEvent = function(){
		$sendSMS.on("click",function(e){
			//버튼 클릭 배경
			$(this).addClass("active");
			sendSMS();
		});
	};
	/**
	 * 
	 */
	var sendSMS = function(){
		if( validateData() ){
			var param = {pId:$pId.val(),pNm:$pName.val()};
			self.loading("show");
			$.ajax({
				url: contextPath + "/mobile/user/checkUserInfo.json",
				method:"POST",
				data: self.util.stringifyJson( param ),
				dataType:"json",
				contentType:"application/json",
				success: function(data){
					try{
						if ( data.msg !== undefined) {
							var message = (data.msg).indexOf("search")>=0?self.getI18n(data.msg):data.msg;
							self.alert( message );
							return false;
						} else if( data.extraMsg !== undefined ){
							self.alert( data.extraMsg );
							return;
						} else {
							//14세 미만일 때는 따로 비밀번호를 설정해서 보냄. 인증을 안탐
							if( data.result !== undefined ){
								if( data.result.bUnder14 ){
									self.alert( self.getI18n("authUserInfo017") , function(){
										location.href = contextPath + "/login.page";
									});
								} else {								
									certificationPage();
								}
							}
						}
						
					} catch(e) {
						
					}
				},
				error: function(xhr){
					console.log(xhr);
				},
				complete: function(){
					self.loading("hide");
					//버튼 클릭 배경 제거
					$sendSMS.removeClass("active");
				}
			});
		}
		//버튼 클릭 배경 제거
		$sendSMS.removeClass("active");
	};
	
	/**
	 * 본인인증 화면요청
	 */
	var certificationPage = function(e) {
		var domain = self.getDomain();
		if(contextPath == "" || contextPath == null){
			domain = domain + "/mobile/user/certification.page?menuId=certification&pId=" + $pId.val() + "&certReqType=setUserPwd";
		} else {
			domain = domain + contextPath + "/mobile/user/certification.page?menuId=certification&pId=" + $pId.val() + "&certReqType=setUserPwd";
		}
		
		var msg = {
				type : "command", 
				functionType : "popup", 
				value : {"url" : domain}
		}; 
		self.toNative(msg);
	}
	
	/**
	 * 
	 */
	var validateData = function(){
		if( $pId.val() === "" ){
			self.alert( self.getI18n("authUserInfo007"),function(){				
				$pId.focus();
			});
			return false;
		} else if( $pName.val() === "" ){
			self.alert( self.getI18n("authUserInfo008"),function(){				
				$pName.focus();
			});
			return false;
		} else if( $pCellphone.val() === "" ){
			self.alert( self.getI18n("authUserInfo009"),function(){				
				$pCellphone.focus();
			});
			return false;
		}
		return true;
	};
	
};