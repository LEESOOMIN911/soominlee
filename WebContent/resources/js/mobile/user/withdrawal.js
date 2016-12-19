/**
 * 회원 탈퇴
 */
var mcare_mobile_withdrawal = function(){
	mcare_mobile.call(this);
	
	var self = this;
	
	var $pwdInput = $("#pwdInput"),
		$withdrawalBtn = $("#withdrawalBtn");
	
	this.init = function(){
		addEvent();
	};
	
	var addEvent = function(){
		$withdrawalBtn.on("click",function(e){
			$(this).addClass("active");
			var options = {
					content : self.getI18n("withdrawal010"),
					callback : function(e){
						validatePWD();
					}
			};
			//버튼 클릭 배경제거
			$withdrawalBtn.removeClass("active");
			self.popup( options );
		});
	};
	
	var validatePWD = function(){
		var inputPwd = $pwdInput.val();
		if( inputPwd === "" ){
			self.alert( self.getI18n("withdrawal005"),function(){
				$pwdInput.focus();
			});
		} else {
			withdrawal(inputPwd);
		}
	};
	var withdrawal = function(pwd){
		$.ajax({
			url : contextPath + "/mobile/user/withdrawal.json",
			method : "POST",
			contentType: "application/json; charset=UTF-8",
			data : self.util.stringifyJson({passWord : pwd }),
			success : function(data){
				try{
					if( data.msg !== undefined ){
						self.alert( data.msg ,function(){
							return;
						});
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