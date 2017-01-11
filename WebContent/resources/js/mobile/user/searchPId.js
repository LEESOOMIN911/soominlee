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
	
	var parameterMap = null;	
	//초기화
	this.init = function(){
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
					pName : $pName.val(),
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
							var message = (data.msg).indexOf("search")>=0?self.getI18n(data.msg):data.msg;
							self.alert( message );
							return;
						} else {
							if(data.result !== undefined){
								if( data.result.bUnder14 ){
									self.alert(data.result.msg, function(){
										location.href = contextPath + "/login.page";
									});
									return;
								} else {								
									certificationPage(data.result.pId);
								}
							}else{
								location.href = contextPath + "/login.page";
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
		var base64EPname = window.btoa(encodeURIComponent($pName.val()));
//		SMS인증을 사용하지 않아 한글 파라미터 전송하는 부분 주석 처리 SMS인증이 살아나게 되면 변경 필요
		if(contextPath == "" || contextPath == null){
			domain = domain + "/mobile/user/certification.page?menuId=certification&pId=&certReqType=" + parameterMap["searchPIdType"] + "&phoneNo="+$pCellphone.val()+"&pName="+base64EPname;
		} else {
			domain = domain + contextPath + "/mobile/user/certification.page?menuId=certification&pId=&certReqType=" + parameterMap["searchPIdType"] + "&phoneNo="+$pCellphone.val()+"&pName="+base64EPname;
		}
//		if(contextPath == "" || contextPath == null){
//			domain = domain + "/mobile/user/certification.page?menuId=certification&pId=&certReqType=" + parameterMap["searchPIdType"] + "&phoneNo="+$pCellphone.val();
//		} else {
//			domain = domain + contextPath + "/mobile/user/certification.page?menuId=certification&pId=&certReqType=" + parameterMap["searchPIdType"] + "&phoneNo="+$pCellphone.val();
//		}
		
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