/**
 * mcare_mobile_treatmentHistory
 */
var mcare_mobile_treatmentHistoryDetail = function(){
	//상속
	mcare_mobile.call(this);
	//super
	var self = this;
	//변수
	var $datetime = $( ".for_datetime" ),
		$dept = $( ".for_dept" ),
		$doctor=$( ".for_doctor" ),
		$gubun = $(".for_gubun"),
		$type = $(".for_type"),
		$smart = $(".smart"),
		$pId = $("#pId");
	
	var parameterMap = null;
	/**
	 * 초기화
	 */
	this.init = function(){
		//상세로 갔다가 돌아올때, 이벤트가 발생하지 못할 수 있으므로 여기서 다시 돌려줌
//		self.changeOrientation("portrait");
		setParameterMap();
		retreive();
		addEvent();
	};
	/**
	 * 이벤트
	 */
	var addEvent = function(){
		
	};
	/**
	 * 
	 */
	var setParameterMap = function(){
		parameterMap = self.util.getParameter();
	};
	/**
	 * 진료비 합계조회
	 */
	var retreive = function(){
		
		var param = {
				receiptNo :	parameterMap["receiptNo"]
		};
		self.loading("show");
		$.ajax({
			url: contextPath + "/mobile/history/getBillSum.json",
			method : "POST",
			data : self.util.stringifyJson(param),
			dataType :"json",
			contentType:"application/json",
			success: function(data){
				try{
					self.loading("hide");
					if( data.msg !== undefined ){
						self.alert( data.msg );
						return;
					} else {
						if(data.result.length > 0) { 
							// 리스트이면 일단 첫번째것만 처리하도록 임시방편땜질..
							displayResult(data.result); 
						} else {
							// 진료비 내역이 기간계에서 아예 안나오는 경우 
							
							var tmp = {},
							tempArray = new Array(); 
							
							tmp["departmentNm"] = "-"; 
							tmp["totalAmt"] = "0"; 
							tmp["insuranceAmt"] = "0"; 
							tmp["patientAmt"] = "0"; 
							tmp["receiptAmt"] = "0"; 
							tmp["outstandingAmt"] = "0";
							tempArray.push( tmp );
							displayResult(tempArray); 
						}	
					}
					
				} catch(e) {
					
				}
			},
			error: function(xhr){
				self.loading("hide");
				console.log( xhr );
			},
			complete : function(){
				//self.loading("hide");
			}
		});
	};
	/**
	 * 진료비 합계 결과 표시
	 */
	var displayResult = function(data){
		if( parameterMap["gubun"] === "0" ){
			$gubun.html("&bull;&nbsp;" + self.getI18n("treatmentHistoryDetail010") );
			$type.html( self.getI18n("treatmentHistoryDetail001") );
			$datetime.text( self.util.restoreDate(parameterMap["date"]) +"  "+ self.util.restoreTime(parameterMap["time"]) );
		}else{
			$gubun.html("&bull;&nbsp;" + self.getI18n("treatmentHistoryDetail011") );
			$type.html( self.getI18n("treatmentHistoryDetail013") );
			$datetime.text( self.util.restoreDischarged(parameterMap["date"],parameterMap["time"]) );
		}
		//진료과
		$dept.text( data[0]["departmentNm"] !== undefined ? data[0]["departmentNm"] : "" );
		//의사명
		$doctor.text( decodeURI( parameterMap["doctorNm"] ) );
		
		$(".sec_con").html("");
		
		if( data.length > 0 ){			
			for( var i = 0; i < data.length; i++ ){
				var item = data[i],
				div = $("<div></div>"),
				ul = $("<ul></ul>").addClass("resultData"),
				li = $("<li></li>"),
				span = $("<span></span>"),
				a = $("<a></a>").addClass("ui-btn ui-btn-b").text( self.getI18n("treatmentHistoryDetail012") );
				//title
				var container = div.clone().addClass("resultContainer");
				container.html( div.clone().html("&bull;&nbsp;"+ self.getI18n("treatmentHistoryDetail004") ) );
				//total
				var totalAmt = li.clone().html( span.clone().html( self.getI18n("treatmentHistoryDetail005") ) ).append( span.clone().addClass("for_totalAmt amt").html( item["totalAmt"]) ); 
				//insuranceAmt
				var insuranceAmt = li.clone().html( span.clone().html( "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<i class='fa fa-minus-square-o'></i>&nbsp;"+self.getI18n("treatmentHistoryDetail006") ).css("font-size","0.9em") ).append( span.clone().addClass("for_insuranceAmt amt").html( item["insuranceAmt"]) ); 
				//patientAmt
				var patientAmt = li.clone().html( span.clone().html( "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<i class='fa fa-minus-square-o'></i>&nbsp;"+self.getI18n("treatmentHistoryDetail007") ).css("font-size","0.9em") ).append( span.clone().addClass("for_patientAmt amt").html( item["patientAmt"]) ); 
				//receiptAmt
				var receiptAmt = li.clone().html( span.clone().html( self.getI18n("treatmentHistoryDetail008") ) ).append( span.clone().addClass("for_receiptAmt amt").html( item["receiptAmt"]) ); 
				//outstandingAmt
				var outstandingAmt = li.clone().html( span.clone().html( self.getI18n("treatmentHistoryDetail009") ) ).append( span.clone().addClass("for_outstandingAmt amt").html( item["outstandingAmt"]) ); 
				//detail btn
//				var btn = div.clone().html( a.attr( {"href":contextPath + "/mobile/history/paymentDetail.page?billNo="+ item["billNo"],"data-ajax":false } ) );
				ul.html( totalAmt ).append( insuranceAmt ).append( patientAmt ).append( receiptAmt ).append( outstandingAmt );
//				ul.html( totalAmt ).append( insuranceAmt ).append( patientAmt ).append( receiptAmt ).append( outstandingAmt ).append( btn );
				
				//미납급있으면
//				if( parseInt( item["outstandingAmt"] ) > 0 ){
					//결제 btn
//					var smart = div.clone().html( a.clone().addClass("smart").text("스마트결제").attr({"data-billNo":item["billNo"],"href":"#","data-amount": item["outstandingAmt"]}) );
//					ul.append( smart );
					
//				}
				container.append( ul );
				
				$(".sec_con").append( container );
			}
			
			var smartCallback = function(e){
				var billNo = $(this).attr("data-billNo"),
					amount = $(this).attr("data-amount");
//				self.alert("스마트결제");
				self.billNo = billNo;
				self.amount = amount;
//				movePay();
			};
//			self.event.addEvent($(".smart"),"click",smartCallback);
		}
		//숫자 format
		self.util.numberFormat($("span.amt"));
	};
	/**
	 * 결제화면으로 이동하기 전 스마트 사인 확인
	 */
	var movePay = function(){
		//스마트 사인 으로 이동
		var jsonMsg = {
				type : "command", 
				functionType : "checkSmartSign",
				value : {
					callbackFn : "window.activeObj.checkSign",
					pId :$pId.val()
			}
		};
		self.toNative( jsonMsg );
		//스마트 사인 제외할 때는 위 소스,this.checkSing, this.registerSign 지우고 아래 소스 주석 해제
		/*
		*self.changePage( contextPath + "/mobile/pay/payment.page?menuId=payment&billNo="+ self.billNo+"&amount="+ self.amount);
		*/
	};
	/**
	 * 스마트 사인 확인 콜백
	 */
	this.checkSign = function(data){
		if( typeof data === "string" ){			
			data = JSON.parse( data );
		};
		
		if( data["success"] !== undefined ){		
			var returnTxt = (data["result"]!==undefined && data["result"]!=="")?data["result"]:"스마트사인 확인에 실패했습니다.<br/>계속될 경우, 관리자에게 문의하세요.";
			
			// 미 등록
			if( data["success"] === "notRegistered" ){	
				var jsonMsg = {
						type : "command", 
						functionType : "registerSmartSign",
						value : {
							callbackFn : "window.activeObj.registerSign",
							pId : $pId.val()
						}
				};
				self.toNative( jsonMsg );

			//확인 실패 or 지원하지 않음	
			} else if( !data["success"] || data["success"] === "notSupported" ){
				self.alert(returnTxt);
			//확인 성공
			} else if( data["success"] ){
//				self.alert("스마트사인이 확인 되었습니다.");
				self.changePage( contextPath + "/mobile/pay/payment.page?menuId=payment&billNo="+ self.billNo+"&amount="+ self.amount);
			} 
		} else {
			self.alert("스마트사인 확인에 실패했습니다.<br/>계속될 경우, 관리자에게 문의하세요.");
		}
	};
	/**
	 * 스마트 사인 등록 콜백
	 */
	this.registerSign = function(data){
		if( typeof data === "string" ){			
			data = JSON.parse( data );
		};
		if( data["success"] !== undefined ){			
			var returnTxt = (data["result"]!==undefined && data["result"]!=="")?data["result"]:"스마트사인 등록에 실패했습니다";
			//성공
			if( data["success"] ){	
				movePay();
			//등록 실패 or 지원하지 않음	
			} else if( !data["success"] || data["success"] === "error" ){
				self.alert(returnTxt);
			} 
		} else {
			self.alert("스마트사인 등록에 실패했습니다.<br/>계속될 경우, 관리자에게 문의하세요.");
		}
	};
};

