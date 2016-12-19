/**
 * 결제
 */
var mcare_mobile_payment = function(){
	mcare_mobile.call(this);
	
	var self = this;
	
	var $type1 = $("#payment_retrieve1"),
		$type2 = $("#payment_retrieve2"),
		$retrieve =$(".retrieve_type a.ui-btn"),
		$retrieve_result1 =$("#retrieve_result1"),
		$retrieve_result2 =$("#retrieve_result2"),
		$rType = $(".rType");
	
	//이전 화면에서 전달 받은 파라미터 
	var parameterMap = null;
	
	this.init = function(){
		//파라미터 설정
		setParameterMap();
//		retrieveData();
		addEvent();
		var hash = sessionStorage.getItem("payment");
		if( hash === "" || hash === null || hash === undefined ){
			showData("p1");
		} else {			
			$rType.filter("[data-type="+ hash.replace("#","")+"]")[0].click();
		}
	};
	
	var addEvent = function(){
		//조회 버튼 터치 이벤트
		$rType.on("click", function(e){
			var type = $(this).attr("data-type");
			sessionStorage.setItem("payment","#" + type);
			showData(type);
		});
	};
	/**
	 * 파라미터 맵 초기화
	 */
	var setParameterMap = function(){
		parameterMap = self.util.getParameter();
	};

	/**
	 * 데이터 조회
	 * 
	 */
	var retrieveData = function(){
		$type1.html("");
		$type2.html("");
		callType1();
		callType2();
	};
	var callType1 = function(){
		$.ajax({
			url : contextPath + "/mobile/smartpay/getPaymentList.json",
			method : "POST",
			data : self.util.stringifyJson({}),
			dataType : "json",
			contentType :"application/json",
			success: function(data){
				try{
					if( data.msg !== undefined ){
						self.alert( "결제목록 : " + data.msg );
						$retrieve_result1.hide();
						return;
					} else {
						displayResultP1(data.result);
					}
					
				} catch(e) {
					
				}
			},
			error: function(xhr){
				console.log(xhr);
			},
			complete: function(){

			}
		});
	};
	var callType2 = function(){
		$.ajax({
			url : contextPath + "/mobile/smartpay/getCancelList.json",
			method : "POST",
			data : self.util.stringifyJson({}),
			dataType : "json",
			contentType :"application/json",
			success: function(data){
				if( data.msg !== undefined ){
					$retrieve_result2.hide();
					self.alert( "취소목록 :" + data.msg );		
					return;
				} else {
					displayResultP2(data.result);
				}
			},
			error: function(xhr){
				console.log(xhr);
			},
			complete: function(){

			}
		});
		
//		var test2 = [
//		             {"dataDate":"20160420","dataTime":"1643","departmentNm":"흉부CT","amount":"1000","receiptNo":"321"}
//		             ];
//		displayResult(test2,"p2");
	};
	var showData = function(type){
		//조회후에 다시 되돌아올때를 위해서
		//self.stateChange( window.location.href , "&gubun=" + type);
		
		if( type === "p1" ){
			$type1.html("");
			callType1();
			$retrieve_result2.hide();
			$retrieve_result1.show();
		} else {
			$type2.html("");
			callType2();
			$retrieve_result1.hide();
			$retrieve_result2.show();
		}
	};
	/**
	 * 
	 */
	var displayResultP1 = function(data){
		if( data === undefined || data.length === 0){
			var bodyDiv = $("<div></div>");
			 bodyDiv.text( self.getI18n( "common005" )).css("text-align","center");
			 $type1.append( bodyDiv );
 
		} else {			
			for( var j = 0; j < data.length; j++ ) {
				 var item = data[j],
				 	 ul = $( "<ul></ul>" ).addClass( "resultData request" ),
				 	 li = $( "<li></li>" ),
				 	 span = $( "<span></span>" ),
				 	 a = $( "<a></a>" ),
				 	 i = $( "<i></i>" ).addClass( "fa font14 fa-plus-circle" );
				 //진료일시
				 var date = li.clone().html( span.clone().text( self.getI18n( "payment007" ) ) ).append( span.clone().html( self.util.restoreDate(item["dataDate"])+ "  "+ self.util.restoreTime(item["dataTime"]) )),
				 //진료과
				 dept = li.clone().html( span.clone().text( self.getI18n( "payment008" ) ) ).append( span.clone().html( item["departmentNm"] ) ),
				 //진료의사
				 doctor = li.clone().html( span.clone().text( self.getI18n( "payment016" ) ) ).append( span.clone().html( item["doctorNm"] ) );
				 ul.html( date ).append( dept ).append( doctor );

				 //미납액
//				 var amount = li.clone().html( span.clone().text( self.getI18n( "payment009" ) ) )
//				 .append( span.clone().html( span.clone().addClass("amt unpaid").html( item["amount"] ) ).append( span.clone().addClass("txt").html("&nbsp;" + self.getI18n("payment014") )) );
				 // ul.append( amount );
				 ul.attr({
					 //진료과 코드
					 "data-departmentCd":item["departmentCd"],
					 //진료과명
					 "data-departmentNm" : item["departmentNm"],
					 //금액
					 //"data-amount":item["amount"],
					 //진료유형
					 "data-orderCd": item["orderCd"],
					 //진료일시
					 "data-dataDate" : item["dataDate"],
					 //영수증번호
//					 "data-billNo" : item["billNo"],
					 //영수증 일련번호
//					 "data-billNoSeq" : item["billNoSeq"],
					 //접수번호
					 "data-receiptNo" : item["receiptNo"],
					 //진료의
					 "data-doctorNm" : item["doctorNm"],
					 //진료시간
					 "data-dataTime" : item["dataTime"]
				 });
				 
				 $type1.append( ul );
			}
			//조회된 내용 클릭 이벤트 - 상세보기로 이동
			var callback = function(e){
				e.preventDefault();
				var data = $(this).data();
				//결제 요청
				var url = contextPath + "/mobile/pay/payDetail.page?menuId=payDetail&payAll=Y";
				for( var key in data ){
					url += "&"+ key +"=" + data[key];
				}
				self.changePage( url );
			};
			//이벤트 등록 - 조회된 내용 클릭
			self.event.addEvent( $("ul.resultData.request"), "click", callback );
		}
		//숫자 format
		self.util.numberFormat($("span.amt"));
	};
	/**
	 * 
	 */
	var displayResultP2 = function(data){
		if( data === undefined || data.length === 0){
			var bodyDiv = $("<div></div>");
			 bodyDiv.text( self.getI18n( "common005" )).css("text-align","center");
			$type2.append( bodyDiv );
		} else {			
			for( var j = 0; j < data.length; j++ ) {
				 var item = data[j],
				 	 ul = $( "<ul></ul>" ).addClass( "resultData cancel" ),
				 	 li = $( "<li></li>" ),
				 	 span = $( "<span></span>" ),
				 	 a = $( "<a></a>" ),
				 	 i = $( "<i></i>" ).addClass( "fa font14 fa-plus-circle" );
				 //결제일시
				 var date = li.clone().html( span.clone().text( self.getI18n( "payment015" ) ) ).append( span.clone().html( self.util.restoreDate(item["authDate"]) )),
				 //진료과
				 dept = li.clone().html( span.clone().text( self.getI18n( "payment008" ) ) ).append( span.clone().html( item["departmentNm"] ) ),
				 //카드사
				 card = li.clone().html( span.clone().text( "카드사" ) ).append( span.clone().html( item["cardCompanyNm"] ) );
				 ul.html( date ).append( dept ).append( card );

				 //납부액 - 취소액
				 var amount = li.clone().html( span.clone().text( self.getI18n( "payment013" ) ) )
			 		.append( span.clone().html( span.clone().addClass("amt paid").html( item["amount"] ) ).append( span.clone().addClass("txt").html("&nbsp;" + self.getI18n("payment014")) ) );
				 ul.attr({
					 //진료과코드
					 "data-departmentCd" : item["departmentCd"],
					 //진료과명
					 "data-departmentNm" : item["departmentNm"],
					 //진료일자
					 "data-dataDate" : item["dataDate"],
					 //진료시간
					 "data-dataTime" : item["dataTime"],
					 //금액
					 "data-amount":item["amount"],
					 //transId
					 "data-transIdValue": item["transIdValue"],
					 //결제일자
					 "data-authDate" : item["authDate"],
					 //결제시간
					 "data-authTime" : item["authTime"],
					 //카드사
					 "data-cardCompanyNm" : item["cardCompanyNm"],
					 //카드사
					 "data-cardCompanyCd" : item["cardCompanyCd"],
					 //카드번호
					 "data-cardNoValue" : item["cardNoValue"],
					 //pg
					 "data-pgType" : item["pgType"],
					 //pid
					 "data-pId" : item["pId"],
					 //접수번호
					 "data-receiptNo" : item["receiptNo"],
					 //카드타입
					 "data-cardType" : item["cardType"],
					 //진료타입
					 "data-orderCd" : item["orderCd"],
					 //할부개월수
					 "data-cardQuotaValue" : item["cardQuotaValue"],
					 //원승인번호
					 "data-origTransValue" : item["origTransValue"],
					 //orderId
					 "data-cancelOriginOrderId" : item["cancelOriginOrderId"]
				 });
				 ul.append( amount );
			 
				 $type2.append( ul );
			}
			//조회된 내용 클릭 이벤트 - 상세보기로 이동
			var callback = function(e){
				e.preventDefault();
				var data = $(this).data();

				//결제 취소
				var amount = $(this).attr("data-amount");
				//결제 요청
				var url = contextPath + "/mobile/pay/payCancel.page?menuId=payCancel";
				for( var key in data ){
					url += "&"+ key +"=" + data[key];
				}
				self.changePage( url );
			};
			//이벤트 등록 - 조회된 내용 클릭
			self.event.addEvent( $("ul.resultData.cancel"), "click", callback );
		}
		//숫자 format
		self.util.numberFormat($("span.amt"));
	};
	
};