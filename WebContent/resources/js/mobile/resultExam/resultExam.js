/**
 * mcare_mobile_resultExam
 */
var mcare_mobile_resultExam = function(){
	//상속
	mcare_mobile.call(this);
	//super
	var self = this;
	//변수
	var $strDate = $(".for_strDate"),
		$sDate = $(".sDate"),
		$endDate = $(".for_endDate"),
		$eDate = $(".eDate"),
		$retreiveBtn = $(".retreiveBtn"),
		$resultContainer = $(".resultContainer");
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
			retrieve();
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
	 * 검사결과 조회
	 */
	var retrieve = function(){
		
		//날짜 유효성 체크
		if( !self.dateValidation( $strDate, $endDate ) ){
			//버튼 클릭 배경 제거
			$retreiveBtn.removeClass("active");
			return;
		}
		var param = {
				startDt : ($strDate.val()).replace(/-/gi,""),
				endDt : ($endDate.val()).replace(/-/gi,"")
		};
		//조회후에 다시 되돌아올때를 위해서
		self.stateChange( window.location.href , "&startDt="+$strDate.val()+"&endDt="+$endDate.val());
		self.loading("show");
		
		$.ajax({
			url : contextPath + "/mobile/resultExam/search.json",
			method : "POST",
			data : self.util.stringifyJson(param), 
			dataType : "json",
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
		
		if( data.length === undefined || data.length === 0 ){
			var bodyDiv = $("<div></div>");
			 bodyDiv.text( self.getI18n( "common005" )).css("text-align","center");
			 $resultContainer.append( bodyDiv );
		} else {			
			for( var i = 0; i < data.length; i++ ){
				 var item = data[i],
				 	 ul = $( "<ul></ul>" ).addClass("resultData"),
				 	 li = $( "<li></li>" ),
				 	 span = $( "<span></span>" );
				 //검사일자
				 var exam_dt = li.clone().html( span.clone().text( self.getI18n("resultExam003") ) ).append( span.clone().text( item["date"] ) ),
				 //검사명
				 exam_nm = li.clone().html( span.clone().text( self.getI18n("resultExam004") ) ).append( span.clone().text( item["examNm"] ) ),
				 //검사결과	 
				 exam_result = li.clone().html( span.clone().text( self.getI18n("resultExam005") ) ).append( span.clone().text( item["examResult"]+" "+item["unit"] ) ),
				 //단위	 
				 //unit = li.clone().html( span.clone().text( self.getI18n("resultExam006") ) ).append( span.clone().text( item["unit"] ) ),
				 //참고치	 
				 reference_val = li.clone().html( span.clone().text( self.getI18n("resultExam007") ) ).append( span.clone().text( item["referenceVal"] + " "+ item["unit"] ) );
				 
//				 ul.append( exam_dt ).append( exam_nm ).append( exam_result ).append( unit ).append( reference_val );
				 ul.append( exam_dt ).append( exam_nm ).append( exam_result ).append( reference_val );
				 
				 $resultContainer.append( ul );
			}
		}
		
	};
};