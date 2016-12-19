/**
 * mcare_mobile_paymentDetail 
 */
var mcare_mobile_paymentDetail = function(){
	//상속
	mcare_mobile.call(this);
	//super
	var self = this;
	//변수
	var $paymentContainer = $( ".paymentContainer" ),
		$paymentTable = $(".paymentTable");
	//이전 화면에서 전달 받은 파라미터 
	var parameterMap = null;
	/**
	 * 초기화
	 */
	this.init = function(){
		//파라미터 설정
		setParameterMap();
		//진료비 상세내역 조회
		retreive();
		//이벤트 등록
		addEvent();
	};
	/**
	 * 이벤트 등록
	 */
	var addEvent = function(){
		//unload
		$(window).on("unload",function(e){
			self.changeOrientation("portrait");
		});
	};
	/**
	 * 파라미터 맵 초기화
	 */
	var setParameterMap = function(){
		parameterMap = self.util.getParameter();
	};
	/**
	 * 진료비 상세 내역 조회
	 */
	var retreive = function(){
		
		var param = {
			billNo :	parameterMap["billNo"]
		};
		self.loading("show");
		$.ajax({
			url: contextPath + "/mobile/history/getBillDetail.json",
			method : "POST",
			data : self.util.stringifyJson(param),
			dataType :"json",
			contentType:"application/json",
			success: function(data){
				try{
					if( data.msg !== undefined ){
						self.alert( data.msg );
						return;
					}  else {					
						displayResult( data.result );
						self.util.numberFormat( $("td.amt") );
					}
					
				} catch(e) {
					
				}
			},
			error: function(xhr){
				console.log( xhr );
			},
			complete : function(){
				self.loading("hide");
			}
		});
	};
	/**
	 * 결과 화면 표시
	 */
	var displayResult = function(data){
		$paymentTable.html( "" );
		
		//합계 데이터
		var totObj = {
				"patientAmt" : 0,
				"insuranceAmt" : 0,
				"fullPatientAmt" : 0,
				"selectMedicalAmt" : 0,
				"exceptSelectMedicalAmt" : 0
		};
		
		$paymentTable.html( getTableHeader() );
			
		for( var i = 0; i < data.length; i++ ){
			 var item = data[i],
			 	 tr = $("<tr></tr>"),
			 	 th = $("<th></th>"),
			 	 td = $("<td></td>").addClass("amt");
			 
			 //항목명
			 var accumulationLclsNm = th.html( item["accumulationLclsNm"] )
			 //본인부담금
			 ,patientAmt = td.clone().html( item["patientAmt"]  )
			 //공단부담금
			 ,insuranceAmt =  td.clone().html( item["insuranceAmt"] )
			 //전액본인부담
			 ,fullPatientAmt = td.clone().html( item["fullPatientAmt"] )
			 //선택진료료
			 ,selectMedicalAmt = td.clone().html( item["selectMedicalAmt"] )
			 //선택진료료이외
			 ,exceptSelectMedicalAmt = td.clone().html( item["exceptSelectMedicalAmt"] );
			 
			 tr.html( accumulationLclsNm ).append( patientAmt ).append( insuranceAmt ).append( fullPatientAmt ).append( selectMedicalAmt ).append( exceptSelectMedicalAmt );
			 $paymentTable.append( tr );
			 
			 //합계 설정
			 totObj["patientAmt"] += parseInt( item["patientAmt"] );
			 totObj["insuranceAmt"] += parseInt( item["insuranceAmt"] );
			 totObj["fullPatientAmt"] += parseInt( item["fullPatientAmt"] );
			 totObj["selectMedicalAmt"] += parseInt( item["selectMedicalAmt"] );
			 totObj["exceptSelectMedicalAmt"] += parseInt( item["exceptSelectMedicalAmt"] );
		}
		//합계화면 구성 함수 호출
		displayTotal( totObj );
	};
	/**
	 * 합계 display
	 */
	var displayTotal = function( totObj ){
		var  tr = $("<tr></tr>"),
		 	 th = $("<th></th>"),
		 	 td = $("<td></td>").addClass("amt");
		 
		 //합계
		 var accumulationLclsNm = th.html( self.getI18n("paymentDetail009") )
		 //본인부담금
		 ,patientAmt = td.clone().html( (totObj["patientAmt"]).toString() )
		 //공단부담금
		 ,insuranceAmt =  td.clone().html( (totObj["insuranceAmt"]).toString() )
		 //전액본인부담
		 ,fullPatientAmt = td.clone().html( (totObj["fullPatientAmt"]).toString() )
		 //선택진료료
		 ,selectMedicalAmt = td.clone().html( (totObj["selectMedicalAmt"]).toString() )
		 //선택진료료이외
		 ,exceptSelectMedicalAmt = td.clone().html( (totObj["exceptSelectMedicalAmt"]).toString() );
		 
		 tr.html( accumulationLclsNm ).append( patientAmt ).append( insuranceAmt ).append( fullPatientAmt ).append( selectMedicalAmt ).append( exceptSelectMedicalAmt );
	
		 $paymentTable.append( tr );
	};
	/**
	 * 테이블 헤더 템플릿
	 */
	var getTableHeader = function(){
		var text = '<tr>';
		text += '<th rowspan="2">' + self.getI18n("paymentDetail001") + '</th>';
		text += '<th colspan="3">' + self.getI18n("paymentDetail002") + '</th>';
		text += '<th colspan="2">' + self.getI18n("paymentDetail003") + '</th>';
		text += '</tr>';
		text += '<tr>';
		text += '<th>'+ self.getI18n("paymentDetail004") + '</th>';
		text += '<th>'+ self.getI18n("paymentDetail005") + '</th>';
		text += '<th>'+ self.getI18n("paymentDetail006") + '</th>';
		text += '<th>'+ self.getI18n("paymentDetail007") + '</th>';
		text += '<th>'+ self.getI18n("paymentDetail008") + '</th>';
		text += '</tr>';
		return text;
	};
};