/**
 * mcare_mobile_searchPId
 */
var mcare_mobile_searchPId = function(){
	//상속
	mcare_mobile.call(this);
	//super
	var self = this;
	
	var $pName = $(".for_name"),
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
			var param = {
					pNm : $pName.val(),
					cellphoneNo : $pCellphone.val()
			};
			self.loading("show");
			$.ajax({
				url: contextPath + "/mobile/user/searchPid.json",
				method:"POST",
				data: self.util.stringifyJson( param ),
				dataType:"json",
				contentType:"application/json",
				success: function(data){
					try{
						if( data.msg !== undefined ){
							if( data.result !== undefined && data.result.sendPhoneNo !== undefined){
								self.alert(data.msg,function(){
									location.href= contextPath + "/login.page";
								});
								return;
							} else {
								var message = (data.msg).indexOf("search")>=0?self.getI18n(data.msg):data.msg;
								self.alert( message );
								return;
							}
						} else {						
							location.href= contextPath + "/login.page";
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
	 * 
	 */
	var validateData = function(){
		if( $pName.val() === "" ){
			self.alert( self.getI18n("searchPId006") );
			return false;
		} else if( $pCellphone.val() === "" ){
			self.alert( self.getI18n("searchPId007") );
			return false;
		}
		return true;
	};
};