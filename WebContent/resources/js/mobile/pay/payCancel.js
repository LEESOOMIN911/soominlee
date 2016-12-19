/**
 * 결제 취소
 */
var mcare_mobile_payCancel = function(){
	mcare_mobile.call(this);
	
	var self = this;
	
	var $amt = $(".amt"),
		$tTime = $(".tTime"),
		$tDept = $(".tDept"),
		$pTime = $(".pTime"),
		$pType = $(".pType"),
		//$cancelReason = $("#cancelReason"),
		$payCancelBtn = $(".payCancelBtn"),
		$hospitalCd = $("#hospitalCd");
	//이전 화면에서 전달 받은 파라미터 
	var parameterMap = null;
	
	var dataObj = {};
	
	this.init = function(){
		//파라미터 설정
		setParameterMap();
		displayData();
		addEvent();
	};
	
	var addEvent = function(){
		//결제 취소 버튼 터치 이벤트
		$payCancelBtn.on("click", function(e){
			$(this).addClass("active");

			var options = {
					content : self.getI18n("payCancel013"),
					callback : function(e){
						cancel();
					}
			};

			self.popup( options );
			$(this).removeClass("active");
		});

	};
	/**
	 * 파라미터 맵 초기화
	 */
	var setParameterMap = function(){
		parameterMap = self.util.getParameter();
	};

	/**
	 * 
	 */
	var displayData = function( ){
		//금액
		$amt.text( parameterMap["amount"] );
		//숫자 format
		self.util.numberFormat($("span.amt"));
		//진료일시
		$tTime.text(self.util.restoreDate(parameterMap["datadate"])+ "  "+ self.util.restoreTime( parameterMap["datatime"] ));
		//진료과
		$tDept.text( decodeURI( parameterMap["departmentnm"] ));
		//결제일시
		$pTime.text(self.util.restoreDate( parameterMap["authdate"] ) + "  "+ self.util.restoreTime( parameterMap["authtime"] ));
		//결제수단
		$pType.text( decodeURI(parameterMap["cardcompanynm"]) );
	};
	//취소 함수
	var cancel = function(){
		self.loading("show");
		//var reason = $cancelReason.val();
		var param = {
				"transIdValue" : decodeURI(parameterMap["transidvalue"]),
				"amount" : parameterMap["amount"],
				"cardCompanyCd" : parameterMap["cardcompanycd"],
				"cardType" : parameterMap["cardtype"],
				"cardNoValue" : parameterMap["cardnovalue"],
				"cancelOriginOrderId" : parameterMap["canceloriginorderid"],
				"cancelOriginTransDt" : parameterMap["authdate"] + " " + parameterMap["authtime"],
				"pId" : parameterMap["pid"],
				//병원정보
				"hospitalCd":$hospitalCd.val(),
		    	//진료과코드
		    	"departmentCd":parameterMap["departmentcd"],
		    	//진료유형
		    	"orderCd":parameterMap["ordercd"],
		    	//진료일시
		    	"dataDate": parameterMap["datadate"],
		    	//진료시간
		    	"dataTime" : parameterMap["datatime"],
		    	//pg사 이름
		    	"pgType" : parameterMap["pgtype"],
		    	//접수번호
		    	"receiptNo" : parameterMap["receiptno"],
		    	//할부개월수
		    	"cardQuotaValue" :parameterMap["cardquotavalue"],
		    	//원승인번호
		    	"origTransValue" : parameterMap["origtransvalue"]
			};
		
		$.ajax({
			url : contextPath + "/mobile/smartpay/payCancel.json",
			method : "POST",
			data : self.util.stringifyJson(param),
			dataType : "json",
			contentType :"application/json",
			success: function(data){
				try{
					if( data.result !== undefined && data.result.msg !== undefined && data.result.result!==undefined && data.result.result === "fail"){
						self.alert( data.result.msg ,function(){
							self.loading("hide");
						});
						return;
					} else if( data.result !== undefined && data.result.msg !== undefined && data.result.result!==undefined && data.result.result === "success" ){
						self.alert( data.result.msg ,function(){
							self.loading("hide");
							//목록을 새로고침해야해서 직접이동으로 변경
							location.href = document.referrer;
						});
					} else {
						self.alert( data.msg ,function(){
							
						});
					}
					
				} catch(e) {
					
				}
			},
			error: function(xhr){
				console.log(xhr);
				self.loading("hide");
			},
			complete: function(){
				//self.loading("hide");
			}
		});
		
//		var json = { 
//				    "type" : "command", 
//				    "functionType" : "payment", 
//				    "value" : {
//				        "actionName" : "cancel",
//				        "callbackFn" : "window.activeObj.cancelCallback",
//				        "paymentType" : payType,
//				    }, 
//				    "actionValue" : { // 결제수단타입에 따라서 내부의  키/값이 결정됨 
//				    	"nextPage" : window.location.origin + contextPath + "/mobile/pay/receipt.page?menuId=receipt", 
//				    	"pId" : "93888", 
//				    	"amount" : parameterMap["amount"], 
//				    	"requestPage" : "https://web.nicepay.co.kr/smart/interfaceURL.jsp"
//				    }
//		};
//		
//		self.toNative(json);
	}
	/**
	 * 이유 null 체크
	 */
	var isReason = function(){
		var reason = $cancelReason.val();
		return reason !== "" ? true : false;
	};
};