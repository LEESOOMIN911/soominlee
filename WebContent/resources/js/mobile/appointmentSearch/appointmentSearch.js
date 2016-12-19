/**
 * mcare_mobile_appointmentSearch
 */
var mcare_mobile_appointmentSearch = function(){
	//상속
	mcare_mobile.call(this);
	//super
	var self = this;
	//변수
	var $gubun = $(".for_gubun"),
		$strDate = $(".for_strDate"),
		$sDate = $(".sDate"),
		$endDate = $(".for_endDate"),
		$eDate = $(".eDate"),
		$resultContainer = $(".resultContainer"),
		$retreiveBtn = $(".retreiveBtn");
	
	//이전 화면에서 전달 받은 파라미터 
	var parameterMap = null;
	
	/**
	 * 초기화
	 */
	this.init = function(){
		//파라미터 설정
		setParameterMap();
		
		if( parameterMap["change"] !== undefined && Boolean( parameterMap["change"] ) ){
			$endDate.val( parameterMap["endDt"] );
			$strDate.val( parameterMap["startDt"] );
			$endDate[0].valueAsDate = new Date( parameterMap["endDt"] );
			$strDate[0].valueAsDate = new Date( parameterMap["startDt"] );
			retreive();
		} else {			
			initDateInput();
		}
		addEvent();
		self.headerFix();
	};
	/**
	 * 이벤트 등록
	 */
	var addEvent = function(){
		//조회버튼 클릭 이벤트
		$retreiveBtn.on("click",function(e){
			//버튼 클릭 배경
			$(this).addClass("active");
			retreive( e );
		});
		//달력버튼 클릭
		$sDate.on("click",function(e){
			if( self.isAndroid() ){				
				$strDate.trigger("click");
			} else {
				$strDate.focus();
			}
		});
		//달력버튼 클릭
		$eDate.on("click",function(e){
			if( self.isAndroid() ){				
				$endDate.trigger("click");
			} else {
				$endDate.focus();
			}
		});
	};
	/**
	 * 파라미터 맵 초기화
	 */
	var setParameterMap = function(){
		parameterMap = self.util.getParameter();
	};
	/**
	 * 날짜입력 초기화
	 */
	var initDateInput = function(){
		//시작일 오늘
		var startDate = new Date(),
			endDate = new Date();
		$strDate[0].valueAsDate = startDate;
		//menuparam 날짜 차이 기본값
		var defaultPeriod = self.util.getMenuParam("periodDay");
		//종료일 한달 후 설정
		endDate.setDate ( endDate.getDate() + (defaultPeriod!==null? defaultPeriod : 30) ); //한달 후
		$endDate[0].valueAsDate = endDate;

		
	};
	/**
	 * 데이터 조회
	 */
	var retreive = function( event ){
		//날짜 유효성 체크
		if( !self.dateValidation( $strDate, $endDate ) ){
			//버튼 클릭 배경 제거
			$retreiveBtn.removeClass("active");
			return;
		}
		var param = {
				startDt : ($strDate.val()).replace(/-/gi,""),
				endDt : ($endDate.val()).replace(/-/gi,""),
		};
		//조회후에 다시 되돌아올때를 위해서
		self.stateChange( window.location.href ,"&startDt="+$strDate.val()+"&endDt="+$endDate.val());	
		self.loading("show");

		$.ajax({
			url : contextPath + "/mobile/appointmentSearch/getList.json",
			type : "POST",
			data : self.util.stringifyJson(param),
			contentType:"application/json",
			success : function( data ){
				try{
					if( data.msg !== undefined ){
						self.alert( data.msg );
						return false;
					} else {					
						displayResult( data.result );
					}
					
				} catch(e) {
					
				}
			},
			error : function( xhr, d, t ){
				console.log( xhr );
			},
			complete : function(){
				self.loading("hide");
				//버튼 클릭 배경 제거
				$retreiveBtn.removeClass("active");
			}
		});
	};

	/**
	 * 결과 표시
	 */
	var displayResult = function( data ){
		$(".thi_con").show();
		$resultContainer.html("");
		
		if( data === undefined || data.length === 0){
			 var bodyDiv = $("<div></div>");
			 bodyDiv.text( self.getI18n( "common005" )).css("text-align","center");
			 $resultContainer.append( bodyDiv );
		} else {
			/**
			 * 운영 코드 시작
			 */
//			for( var j = 0; j < data.length; j++ ) {
//				 var item = data[j],
//				 	 ul = $( "<ul></ul>" ).addClass( "resultData" ).attr("data-receiptNo",item["receiptNo"]),
//				 	 li = $( "<li></li>" ),
//				 	 span = $( "<span></span>" ),
//				 	 a = $( "<a></a>" ).addClass("cancelLink"),
//				 	 i = $( "<i></i>" ).addClass( "fa fa-plus-circle" );
//				 //일시링크 - 상세로 들어가는 
//				 var dateLink = span.clone().text( self.util.restoreDate(item["date"]) + "  "+ self.util.restoreTime(item["time"]) ),
//				 //예약일시;
//				 date = li.clone().html( span.clone().text( self.getI18n( "appointmentSearch003" ) ) ).append( dateLink );
//				 //구분
//				 var gubun = "";
//				 if( $.trim(item["receiptGubunKindNm"]) === self.getI18n("appointmentSearch008") ) {
//					 if( $.trim(item["status"]) === self.getI18n("appointmentSearch012") ){
//						 gubun = li.clone().html( span.clone().text( self.getI18n( "appointmentSearch004" ) ) ).append( span.clone().html( item["receiptGubunKindNm"] ) );
//					 } else {						 
//						 gubun = li.clone().html( span.clone().text( self.getI18n( "appointmentSearch004" ) ) ).append( span.clone().addClass("link_span").html( item["receiptGubunKindNm"] + "&nbsp;&nbsp;" ).append( a.attr("idx",item["bookingIdx"]).html( span.clone().html( self.getI18n("appointmentSearch009")  )) ) );
//					 }
//				 } else {
//					 gubun = li.clone().html( span.clone().text( self.getI18n( "appointmentSearch004" ) ) ).append( span.clone().html( item["receiptGubunKindNm"] ) );
//				 }
//				 
//				 //진료과
//				 var dept = li.clone().html( span.clone().text( self.getI18n( "appointmentSearch005" ) ) ).append( span.clone().html( item["departmentNm"] ) ),
//				 //의사명;
//				 doctor = li.clone().html( span.clone().text( self.getI18n( "appointmentSearch006" ) ) ).append( span.clone().html( item["doctorNm"] ) ),
//				 //상태
//				 status = li.clone().html( span.clone().text( self.getI18n( "appointmentSearch007"  ) ) ).append( span.clone().html( item["status"] ) );
//				
//				 ul.html( date ).append( gubun ).append( dept ).append( doctor ).append( status );
//				 
//				 $resultContainer.append( ul );
//			}
//			//취소 이벤트 등록
//			var callback = function(e){
//				var idx = $(this).attr("idx");
//				var options = {
//						content : self.getI18n( "appointmentSearch010" ),
//						callback : function(e){
//							cancelResevation(idx);
//						}
//				};
//				self.popup( options );
//			}
//			self.event.addEvent( $(".cancelLink"),"click",callback );
			/**
			 * 운영 코드 끝
			 */
			/**
			 * 테스트 코드 시작
			 */
			for( var j = 0; j < data.length; j++ ) {
				 var item = data[j],
				 	 ul = $( "<ul></ul>" ).addClass( "resultData" ).attr({"data-receiptNo":item["receiptNo"],"data-docName":item["doctorNm"],"data-dept":item["departmentNm"]}),
				 	 li = $( "<li></li>" ),
				 	 span = $( "<span></span>" ),
				 	 a = $( "<a></a>" ),
				 	 i = $( "<i></i>" ).addClass( "fa fa-calendar-check-o" );
				 //일시링크 - 상세로 들어가는 
				 var dateLink = a.clone().addClass("calender").text( self.util.restoreDate(item["date"]) + "  "+ self.util.restoreTime(item["time"]) ).append("&nbsp;&nbsp;").append(i),
				 //예약일시;
				 date = li.clone().html( span.clone().text( self.getI18n( "appointmentSearch003" ) ) ).append( span.clone().html(dateLink) );
				 //구분
				 var gubun = "";
				 if( $.trim(item["receiptGubunKindNm"]) === self.getI18n("appointmentSearch008") ) {
					 if( $.trim(item["status"]) === self.getI18n("appointmentSearch012") ){
						 gubun = li.clone().html( span.clone().text( self.getI18n( "appointmentSearch004" ) ) ).append( span.clone().html( item["receiptGubunKindNm"] ) );
					 } else {						 
						 gubun = li.clone().html( span.clone().text( self.getI18n( "appointmentSearch004" ) ) ).append( span.clone().addClass("link_span").html( item["receiptGubunKindNm"] + "&nbsp;&nbsp;" ).append( a.addClass("cancelLink").attr("idx",item["bookingIdx"]).html( span.clone().html( self.getI18n("appointmentSearch009")  )) ) );
					 }
				 } else {
					 gubun = li.clone().html( span.clone().text( self.getI18n( "appointmentSearch004" ) ) ).append( span.clone().html( item["receiptGubunKindNm"] ) );
				 }
				 
				 //진료과
				 var dept = li.clone().html( span.clone().text( self.getI18n( "appointmentSearch005" ) ) ).append( span.clone().html( item["departmentNm"] ) ),
				 //의사명;
				 doctor = li.clone().html( span.clone().text( self.getI18n( "appointmentSearch006" ) ) ).append( span.clone().html( item["doctorNm"] ) ),
				 //상태
				 status = li.clone().html( span.clone().text( self.getI18n( "appointmentSearch007"  ) ) ).append( span.clone().html( item["status"] ) );
				
				 ul.html( date ).append( gubun ).append( dept ).append( doctor ).append( status );
				 
				 $resultContainer.append( ul );
			}
			//취소 이벤트 등록
			var callback = function(e){
				var idx = $(this).attr("idx");
				var options = {
						content : self.getI18n( "appointmentSearch010" ),
						callback : function(e){
							cancelResevation(idx);
						}
				};
				self.popup( options );
			};
			//일정 연동 테스트 이벤트
			var calendarCallback = function(e){
				var date = $(this).text();
				var info = $(this).parents(".resultData").data();
				
				var jsonMsg = {
						type : "command", 
						functionType : "addEvent",
						value : {
							dateTime : date,
							doctorName : info["docname"], 
							deptName : info["dept"],
							hospitalName : "부산대학교병원"
						}
					}; 
				self.toNative(jsonMsg);
			};
			self.event.addEvent( $(".cancelLink"),"click",callback );
			self.event.addEvent( $(".calender"),"click",calendarCallback );
			/**
			 * 테스트 코드 끝
			 */
		}
		
	};	
	
	var cancelResevation = function(idx){
		self.loading("show");
		
		var param = {
				"bookingIdx" : idx
		};
		
		$.ajax({
			url : contextPath + "/mobile/appointmentSearch/cancel.json",
			method : "POST",
			data : self.util.stringifyJson(param),
			contentType:"application/json",
			success : function( data ){
				try{
					console.log(data); 
					if( data.msg !== undefined ){
						self.alert( data.msg );
						return false;
					} else {
						self.alert( self.getI18n("appointmentSearch011"),function(){
							window.location.reload();
							return;
						});
					} 
					
				} catch(e) {
					
				}
			},
			error : function( xhr, d, t ){
				console.log( xhr );
			},
			complete : function(){
				self.loading("hide");
				
			}
		});
	};
};

