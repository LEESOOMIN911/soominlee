/**
 * mcare_mobile_treatmentHistory
 */
var mcare_mobile_treatmentHistory = function(){
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
		$retreiveBtn = $(".retreiveBtn"),
		$resultTitle = $(".resultTitle");
	
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
			$gubun.filter("[data-type="+ parameterMap["gubun"]+"]")[0].click();
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
		$sDate.on("click",function(e){
			if( self.isAndroid() ){				
				$strDate.trigger("click");
			} else {
				$strDate.focus();
			}
		});
		$eDate.on("click",function(e){
			if( self.isAndroid() ){				
				$endDate.trigger("click");
			} else {
				$endDate.focus();
			}
		});
		
		$gubun.on("click",function(e){
			$(".thi_con").hide();
			$resultContainer.html("");
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
		
		//종료일 오늘
		var startDate = new Date(),
			endDate = new Date();
		$endDate[0].valueAsDate = endDate;
		//menuparam 날짜 차이 기본값
		var defaultPeriod = self.util.getMenuParam("periodDay");
		//시작일 한달 전 설정
		startDate.setDate ( startDate.getDate() - (defaultPeriod!==null? defaultPeriod : 30) ); //한달 전
		$strDate[0].valueAsDate = startDate;

	};
	/**
	 * 데이터 조회
	 */
	var retreive = function( event ){		
		var gubun = parseInt( $gubun.filter(".ui-btn-active").attr("data-type") );
		var apiUrl = ""; 

		switch(gubun){
			// 외래 
			case 0 : apiUrl = "/mobile/history/getOutList.json"; break;
			// 입원 
			case 1 : apiUrl = "/mobile/history/getInList.json"; break;
		}
		
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
		self.stateChange( window.location.href , "&gubun="+gubun+"&startDt="+$strDate.val()+"&endDt="+$endDate.val());
		self.loading("show");
		
		$.ajax({
			url : contextPath + apiUrl,
			method : "POST",
			data : self.util.stringifyJson(param),
			dataType : "json",
			contentType: "application/json",
			success : function( data ){
				try{
					if( data.msg !== undefined ){
						self.alert( data.msg );
						return false;
					} else {					
						if( gubun === 1 ){
							displayDischarged( data.result );
						} else {					
							displayResult( data.result );
						}
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
	 * 외래 결과 표시
	 */
	var displayResult = function( data ){
		$(".thi_con").show();
		$resultContainer.html("");
		
		if( data === undefined || data.length === 0){
			var bodyDiv = $("<div></div>");
			 bodyDiv.text( self.getI18n( "common005" )).css("text-align","center");
			 $resultContainer.append( bodyDiv );
		} else {			
			for( var j = 0; j < data.length; j++ ) {
				 var item = data[j],
				 	 ul = $( "<ul></ul>" ).addClass( "resultData" ).attr({"data-receiptNo":item["receiptNo"],"data-date":item["date"],"data-time":item["time"],"data-doctor":item["doctorNm"]}),
				 	 li = $( "<li></li>" ),
				 	 span = $( "<span></span>" ),
				 	 a = $( "<a></a>" ),
				 	 i = $( "<i></i>" ).addClass( "fa fa-plus-circle" );
				 //일시링크 - 상세로 들어가는 
				 var dateLink = a.clone().text( self.util.restoreDate(item["date"]) + "  "+ self.util.restoreTime(item["time"]) ).append("&nbsp;&nbsp;").append( i ),
				 //진료일시;
				 date = li.clone().html( span.clone().text( self.getI18n( "treatmentHistory003" ) ) ).append( span.clone().html( dateLink ) ),
				 //진료과
				 dept = li.clone().html( span.clone().text( self.getI18n( "treatmentHistory004" ) ) ).append( span.clone().html( item["departmentNm"] ) ),
				 //의사명;
				 doctor = li.clone().html( span.clone().text( self.getI18n( "treatmentHistory005" ) ) ).append( span.clone().html( item["doctorNm"] ) );
				
				 ul.html( date ).append( dept ).append( doctor );
				 
				 $resultContainer.append( ul );
			}
			//조회된 내용 클릭 이벤트 - 상세보기로 이동
			var callback = function(e){
				e.preventDefault();
				var receiptNo = $(this).attr( "data-receiptNo" ),
					date = $(this).attr( "data-date" ),
					time = $(this).attr( "data-time" ),
					doctor = $(this).attr( "data-doctor" );
				self.changePage( contextPath + "/mobile/history/treatmentHistoryDetail.page?menuId=treatmentHistoryDetail&receiptNo=" + receiptNo +"&gubun=0&date="+date+"&time="+time+"&doctorNm="+doctor );
			};
			//이벤트 등록 - 조회된 내용 클릭
			self.event.addEvent( $("ul.resultData"), "click", callback );
		}
	};
	/**
	 * 입원 결과 표시
	 */
	var displayDischarged = function( data ){
		$(".thi_con").show();
		$resultContainer.html("");
		
		if( data === undefined || data.length === 0){
			 var bodyDiv = $("<div></div>");
			 bodyDiv.text( self.getI18n( "common005" )).css("text-align","center");
			 $resultContainer.append( bodyDiv );
		} else {			
			for( var j = 0; j < data.length; j++ ) {
				 var item = data[j],
				 	 ul = $( "<ul></ul>" ).addClass( "resultData" ).attr({"data-receiptNo":item["receiptNo"],"data-date":item["admissionDt"],"data-time":item["dischargeDt"],"data-doctor":item["attendDoctorNm"]}),
				 	 li = $( "<li></li>" ),
				 	 span = $( "<span></span>" ),
				 	 a = $( "<a></a>" ),
				 	 i = $( "<i></i>" ).addClass( "fa fa-plus-circle" );
				 //일시링크 - 상세로 들어가는 
				 var dateLink = a.clone().text( self.util.restoreDischarged(item["admissionDt"],item["dischargeDt"]) ).append( "&nbsp;&nbsp;" ).append(i),
				 //입퇴원일시;
				 date = li.clone().html( span.clone().text( self.getI18n( "treatmentHistory006" ) ) ).append( span.clone().html( dateLink ) ),
				 //진료과
				 dept = li.clone().html( span.clone().text( self.getI18n( "treatmentHistory004" ) ) ).append( span.clone().html( item["departmentNm"] ) ),
				 //의사명;
				 doctor = li.clone().html( span.clone().text( self.getI18n( "treatmentHistory005" ) ) ).append( span.clone().html( item["attendDoctorNm"] ) );
				
				 ul.html( date ).append( dept ).append( doctor );
				 
				 $resultContainer.append( ul );
			}
			//조회된 내용 클릭 이벤트 - 상세보기로 이동
			var callback = function(e){
				var receiptNo = $(this).attr( "data-receiptNo" ),
				date = $(this).attr( "data-date" ),
				time = $(this).attr( "data-time" ),
				doctor = $(this).attr( "data-doctor" );
			self.changePage( contextPath + "/mobile/history/treatmentHistoryDetail.page?menuId=treatmentHistoryDetail&receiptNo=" + receiptNo +"&gubun=1&date="+date+"&time="+time+"&doctorNm="+doctor );
			};
			//이벤트 등록 - 조회된 내용 클릭
			self.event.addEvent( $("ul.resultData"), "click", callback );
		}
	};
};

