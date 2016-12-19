/**
 * 결제 상세
 */
var mcare_mobile_payDetail = function(){
	mcare_mobile.call(this);
	
	var self = this;
	
	var $paymentContent = $("#paymentContent"),
		$paymentBtn = $(".paymentBtn"),
		$payDetail = $("#payDetail"),
		$payType = $("#payType"),
		$pay_select = $(".pay_select"),
		$hospitalType = $("#hospitalType"),
		$payAll = $(".payAll"),
		$rDate = $(".rDate"),
		$rDept = $(".rDept"),
		$rDoctor = $(".rDoctor");
	
	//이전 화면에서 전달 받은 파라미터 
	var parameterMap = null;
	
	var dataObj = {};
	
	this.init = function(){
		//파라미터 설정
		setParameterMap();
//		initPayAll();
//		setType();
		retrieveData();
		addEvent();
	};
	
	var addEvent = function(){
		//결제 버튼 터치 이벤트
		$paymentBtn.on("click", function(e){
			beforePayment();
		});

	};
	/**
	 * 파라미터 맵 초기화
	 */
	var setParameterMap = function(){
		parameterMap = self.util.getParameter();
	};
	/**
	 * 부분 납부 설정
	 */
	var initPayAll = function(){
		//menuparam 부분납부 기본 최소값
		var pay = self.util.getMenuParam("pay"),
			//부분납부 구간
			step = self.util.getMenuParam("step");
		
		
		if( parameterMap["payAll"] === "Y" ){
			//구간값보다 작거나 같으면 부분납부 안보이게
			if( parseFloat(step) >=  parseFloat(dataObj["amount"]) ){
				$payAll.hide();
			} else if( parseFloat(step) <  parseFloat(dataObj["amount"]) ){
				$pay_select.html("");
				var i = 1;
				while( (i * step) < parseFloat(dataObj["amount"]) ){
					var option = $("<option></option>").addClass("amt").val((i * step)).text((i * step));
					$pay_select.append(option);
					i++;
				}
				
				$pay_select.selectmenu("refresh");
				$payAll.show();
			}
		} else {
			$payAll.hide();
		}
	};
	/**
	 * 화면에 금액 표시
	 */
	var setType = function(){
		//금액
		$paymentBtn.find(".amt").html( dataObj["amount"] );
		$payDetail.find(".amt").html( dataObj["amount"] );
		
		//숫자 format
		self.util.numberFormat($("span.amt"));
		
		$rDate.html( self.util.restoreDate( parameterMap["datadate"] ) + " " + self.util.restoreTime( parameterMap["datatime"] ));
		$rDept.html( decodeURI(parameterMap["departmentnm"]) );
		$rDoctor.html( decodeURI(parameterMap["doctornm"]) );
	};
	/**
	 * 데이터 조회
	 * 
	 */
	var retrieveData = function(type){
		
		var param = {
				receiptNo:parameterMap["receiptno"],
				dataDate:parameterMap["datadate"]
		};

		self.loading("show");
		$.ajax({
			url : contextPath + "/mobile/smartpay/getPaymentDetail.json",
			method : "POST",
			data : self.util.stringifyJson(param),
			dataType : "json",
			contentType :"application/json",
			success: function(data){
				try{
					if( data.msg !== undefined ){
						self.alert( data.msg );
						return;
					} else {
						dataObj = data.result;
//					displayResult(data);
						setType();
//					initPayAll();
					}
					
				} catch(e) {
					
				}
			},
			error: function(xhr){
				console.log(xhr);
			},
			complete: function(){
				self.loading("hide");
			}
		});
	};
	/**
	 * 없어질 수 있음. 금액 상세 내역 표시부분 - 현재 막아두었음
	 */
	var displayResult = function(data){
		$paymentContent.html("");
		
		if( data === undefined || data.length === 0){
			var bodyDiv = $("<div></div>");
			 bodyDiv.text( self.getI18n( "common005" )).css("text-align","center");
			 $paymentContent.append( bodyDiv );
		} else {
			var msgBox = $("<div></div>").addClass("msgBox");
			for( var j = 0; j < data.length; j++ ) {
				 var item = data[j],
					 div = $("<div></div>"),
				 	 icon = $("<i></i>"),
				 	 span = $("<span></span>");
				 //일시링크 - 상세로 들어가는 
				 var amount = div.clone().addClass("item infotxt").html( 
						 	span.clone().addClass("code").html( icon.clone().addClass("fa font14 fa-dot-circle-o") )
						 	.append( "&nbsp;" + item["name"] + "&nbsp;&#58;&nbsp;")
					 	).append( 
					 		span.clone().addClass("for_amount amt").html( item["amount"] ) 
					 	);
				 msgBox.append( amount );
				 
			}
			$paymentContent.append( msgBox );

		}
		//숫자 format
		self.util.numberFormat($("span.amt"));
	};
	/**
	 * 결제전 paymaster에 insert
	 */
	var beforePayment = function(){
		var payType = $payType.prop("checked")?"R2P":"SSG";
		var param = {
				//병원정보
				"hospitalValue":$hospitalType.val(),
		    	//진료과코드
		    	"departmentCd":parameterMap["departmentcd"],
		    	//진료유형
		    	"orderCd":parameterMap["ordercd"],
		    	//진료일시
		    	"dataDate":dataObj["dataDate"],
		    	//pg사 이름
		    	"pgType" :payType,
		    	//금액
		    	"amount":dataObj["amount"],
		    	//영수증번호
		    	"receiptNo" : dataObj["receiptNo"]
		};

		self.loading("show");
		$.ajax({
			url : contextPath + "/mobile/smartpay/getPaymentMaster.json",
			method : "POST",
			data : self.util.stringifyJson(param),
			dataType : "json",
			contentType :"application/json",
			success: function(data){
				try{
					if( data.msg !== undefined ){
						self.alert( data.msg );
						return;
					} else {
						payment(data.result);
					}
					
				} catch(e) {
					
				}
			},
			error: function(xhr){
				console.log(xhr);
			},
			complete: function(){
				self.loading("hide");
			}
		});
	};
	/**
	 * 결제 호출
	 */
	var payment = function( data ){
//		var payType = $payType.prop("checked")?"R2P":"SSG";
		//ssg 용
		var jsonssg = { 
				    "type" : "command", 
				    "functionType" : "payment", 
				    "value" : {
				        "actionName" : "request",
				        "callbackFn" :"window.activeObj.paymentCallback", 
				        "paymentType" : data["pgType"],
				    }, 
				    "actionValue" : { // 결제수단타입에 따라서 내부의  키/값이 결정됨 
				    	// 카드사에서 POST로 호출해주기 때문에 서버들러서 화면으로 넘어가야 함. 
				    	"nextPage" : window.location.origin + contextPath + "/mobile/ext/nicePayReceipt.ext",   
				    	"requestPage" : "https://web.nicepay.co.kr/smart/interfaceURL.jsp", 
				    	"goodsName" : decodeURI(parameterMap["departmentnm"]), // 여기에 진료과 넣어주세요 
				    	"pId" : data["pId"], 
				    	"cellphoneNo" : data["cellphoneNo"], 
				    	"pName" : data["pName"],
				    	// 환경설정에서 가져와야하는 값임.
				    	"mallId" : data["mallId"],
				    	// 환경설정에서 가져와야하는 값임.
				    	"merchantKey" : data["merchantKey"], 
//				    	//병원코드
//				    	"hospitalValue":data["hospitalValue"],
						//상품아이디
					    "orderId":data["orderId"],
					    //진료과코드
					    //"departmentCd":data["departmentCd"],
					    //진료유형
					    //"orderCd":data["orderCd"],
					    //진료일시
					    //"dataDate":data["dataDate"],
					    //금액
					    "amount":data["amount"], // 무이자 사용여부, 할부개월 대상 정보, 할부개월제한, 청구할인정보 추가될 수 잇음 
				    	// 접근 키 
					    "authReqValue" : data["authReqValue"],
					    // 전문번호 
					    "msgSeq" : data["msgSeq"],
					    //영수증번호
				    	"receiptNo" : dataObj["receiptNo"]
				    }
		};
		//r2p용
		var jsonr2p = { 
			    "type" : "command", 
			    "functionType" : "payment", 
			    "value" : {
			        "actionName" : "request",
			        "callbackFn" : "window.activeObj.paymentCallback", 
			        "paymentType" : data["pgType"],
			    }, 
			    "actionValue" : { // 결제수단타입에 따라서 내부의  키/값이 결정됨 
			    	// 요청보낼 페이지 
			    	"requestPage" : "https://test1.Ring2Pay.com:15000/O2O/authProc.emdo", // 오픈전 수정예정  
			    	// 영수증 페이지 
			    	"nextPage" : window.location.origin + contextPath + "/mobile/ext/r2pPayReceipt.ext",  	
			    	"goodsName" : decodeURI(parameterMap["departmentnm"]), 
			    	"pId" : data["pId"], 
			    	"cellphoneNo" : data["cellphoneNo"], 
//			    	"pName" : data["pName"],
			    	"birthDt" : data["birthDt"], 
			    	// 환경설정에서 가져와야하는 값임.
			    	"mallId" : data["mallId"],
			    	// 환경설정에서 가져와야하는 값임.
			    	"merchantKey" : data["merchantKey"], 
					//상품아이디
				    "orderId":data["orderId"],
				    //진료과코드
				    //"departmentCd":data["departmentCd"],
				    //진료유형
				    //"orderCd":data["orderCd"],
				    //진료일시
				    //"dataDate":data["dataDate"],
				    //금액
				    "amount":data["amount"], // 무이자 사용여부, 할부개월 대상 정보, 할부개월제한, 청구할인정보 추가될 수 잇음 
			    	// 접근 키 
				    "authReqValue" : data["authReqValue"],
				    // 전문번호 
				    "msgSeq" : data["msgSeq"],
				    //영수증번호
			    	"receiptNo" : dataObj["receiptNo"]
			    }

		};
		if(data["pgType"] == "R2P" ){
			self.toNative(jsonr2p);
		} else {			
			self.toNative(jsonssg);
		}
	};
	/**
	 * 
	 */
	this.paymentCallback = function(){
		//콜백하면서 history에 payDetail이 중첩됨. 그래서 단순히 history.back으로 하면 payDetail을 다시 부르기 때문에
		//아무런 동작하지 않은 것으로 보임. 두단계 뒤로 보내도록 처리해서 payment로 보냄
		//목록을 새로 고침을 해야해서 직접 이동으로 변경
//		window.history.go(-2);
		location.href = document.referrer;
	};
};