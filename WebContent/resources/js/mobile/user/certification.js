/**
 * mcare_mobile_certification
 * 모바일 사용등록
 * @author 김병진
 */
var mcare_mobile_certification = function() {
	
	//상속
	mcare_mobile.call(this);
	//super
	var self = this;
	
	var $chechPlus = $("#chechPlus"),
		$encodeData = $("#EncodeData"),
		$m = $("#m"),
		$certType = $("#certType"),
		$iPin = $("#iPin"),
		$encData = $("#enc_data"),
		$phoneNo = $("#phoneNo"),
		$sms = $("#sms");

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
		// 휴대폰 인증 테스트 
		$chechPlus.on("click",function(e){
			chechPlus(e);
		});
		// i-Pin 인증 테스트
		$iPin.on("click",function(e){
			iPin(e);
		});
		//sms 인증 테스트
		$sms.on("click",function(e){
			var patientId = $("#pid").val();
			var certReqType = $("#certReqType").val();
			var phoneNo = $phoneNo.val();
			location.href = contextPath + "/mobile/user/sms.page?pid="+patientId+"&certReqType="+certReqType+"&phoneNo="+phoneNo;
		});
	};

	/**
	 * 
	 */
	var chechPlus = function(e){
		$.ajax({
			url: contextPath + "/mobile/user/checkPlus.json",
			method:"GET",
			data:{},
			dataType:"json",
			beforeSend:function(xhr){
				self.loading("show");
			},
			success: function( data ){
				try{
					$encodeData.val(data.sEncData);
					$m.val("checkplusSerivce");
					$certType.val("checkPlus");
					window.name ="Parent_window";
					document.form_chk.action = "https://nice.checkplus.co.kr/CheckPlusSafeModel/checkplus.cb";
					document.form_chk.target = "Parent_window";
					document.form_chk.submit();
					
				} catch(e) {
					
				}
			},
			error: function( xhr ){
				console.log( xhr );
			},
			complete:function(){
				self.loading("hide");
			}
		});
	};
	/**
	 * 
	 */
	var iPin = function(e){
		$.ajax({
			url: contextPath + "/mobile/user/iPin.json",
			method:"GET",
			data:{},
			dataType:"json",
			beforeSend:function(xhr){
				self.loading("show");
			},
			success: function( data ){
				console.log( data.sEncData );
				$m.val("pubmain");
				$encData.val(data.sEncData);
				$certType.val("iPin");
			 	window.name ="Parent_window";
			 	document.form_chk.target = "Parent_window";
				document.form_chk.action = "https://cert.vno.co.kr/ipin.cb";
				document.form_chk.submit();	
			},
			error: function( xhr ){
				console.log( xhr );
			},
			complete:function(){
				self.loading("hide");
			}
		});
	}
};