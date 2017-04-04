/**
 * 회원 탈퇴
 */
var mcare_mobile_withdrawal = function(){
	mcare_mobile.call(this);
	
	var self = this;
	
	var $pwdInput = $("#pwdInput"),
		$withdrawalBtn = $("#withdrawalBtn"),
		$forReason = $("#forReason"),
		$reasonInputWrap = $(".reasonInputWrap"),
		$reasonInput = $("#reasonInput");
	
	this.init = function(){
		addEvent();
	};
	
	var addEvent = function(){
		$withdrawalBtn.on("click",function(e){
			$(this).addClass("active");

			var inputPwd = $pwdInput.val(),
			reason = $forReason.val()==="etc"?$reasonInput.val():$forReason.val();
			
			if(validatePWD(inputPwd,reason)){
				
				var options = {
						content : self.getI18n("withdrawal010"),
						callback : function(e){
							withdrawal(inputPwd, reason);
						}
				};
				
				self.popup( options );
			}
			
			
			//버튼 클릭 배경제거
			$withdrawalBtn.removeClass("active");
			
		});
		
		$forReason.on("change",function(e){
			if( $(this).val() === "etc" ){
				$reasonInputWrap.show();
			} else {
				$reasonInputWrap.hide();
			}
		});
	};
	/**
	 * 
	 */
	var validatePWD = function(inputPwd, reason){
			
		var patt = /(script)/gi; // script 찾기 정규식

		if( inputPwd === "" ){
			self.alert( self.getI18n("withdrawal005"),function(){
				$pwdInput.focus();
			});
			return false;
		} else if( reason === "" ){
			self.alert( self.getI18n("withdrawal019"),function(){
				
			});
			return false;
		} else if( reason.length > 255 ){
			self.alert( self.getI18n("withdrawal020"),function(){
				
			});
			return false;
		} else if( patt.test( reason ) ){
			self.alert( self.getI18n("withdrawal021"),function(){
				
			});
			return false;
		} 
		
		return true;
	};
	/**
	 * 
	 */
	var withdrawal = function(pwd, reason){
		$.ajax({
			url : contextPath + "/mobile/withdrawal/withdrawal.json",
			method : "POST",
			contentType: "application/json; charset=UTF-8",
			data : self.util.stringifyJson({"passWord" : pwd, "reason": reason}),
			success : function(data){
				try{
					if( data.msg !== undefined ){
						self.alert( data.msg ,function(){
							return;
						});
					} else if( data.extraMsg !== undefined ){
						self.alert( data.extraMsg );
						return;
					} else if ( data.result !== undefined && data.result === "success" ){
						self.alert( self.getI18n("withdrawal006"),function(){
							self.changePage( contextPath + "/logout.page" );
						});
					} else if ( data.result !== undefined && data.result === "fail" ){
						self.alert( self.getI18n("withdrawal007"),function(){
							return;
						});
					}
					
				} catch(e) {
					
				}
			},
			error :  function(xhr){
				console.log(xhr);
			}
		});
	};
};