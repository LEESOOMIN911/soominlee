/**
 * mcare_mobile_prescription
 */
var mcare_mobile_prescription = function(){
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
			retreive();
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
	 * 처방조회
	 */
	var retreive = function(){
		
		//날짜 유효성 체크
		if( !self.dateValidation( $strDate, $endDate ) ){
			//버튼 클릭 배경제거
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
			url : contextPath + "/mobile/prescription/search.json",
			method : "POST",
			data : self.util.stringifyJson(param),
			dataType : "json",
			contentType : "application/json",
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
				//버튼 클릭 배경제거
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
				 	 a = $("<a></a>"),
				 	 span = $( "<span></span>" );
				 //진료과
				 var departmentNm = li.clone().html( span.clone().text( self.getI18n("prescription003") ) ).append( span.clone().text( item["departmentNm"] ) ),
				 //처방일
				 prescriptionDt = li.clone().html( span.clone().text( self.getI18n("prescription004") ) ).append( span.clone().text( self.util.restoreDate(item["prescriptionDt"] )) ),
				 //약품명
				 medicineNm = li.clone().html( span.clone().text( self.getI18n("prescription011") ) ).append( span.clone().text( item["medicineNm"] ) ),
				 //처방량
				 dose = li.clone().html( span.clone().text( self.getI18n("prescription005") ) ).append( span.clone().text( item["dose"] ) ),
				 //횟수/일수
				 doseTmDay = li.clone().html( span.clone().text( self.getI18n("prescription006") ) ).append( span.clone().text( item["doseTm"]+" / "+item["doseDay"] ) ),
				 //용법	 
				 usageNm = li.clone().addClass("effPop").html( span.clone().text( self.getI18n("prescription007") ) ).append( a.clone().html( span.clone().text( item["usageNm"] ).append("&nbsp;<i data class='effect fa fa-info-circle'></i>") ) ),
				//효능, 효능분류 - 안보이는 아이들
				 effectNm = li.clone().html( span.clone().addClass("hidden_effectNm").text( item["effectNm"] )).hide(),
				 effectCategoryNm = li.clone().html( span.clone().addClass("hidden_effectCategoryNm").text( item["effectCategoryNm"] )).hide();

				 ul.append( departmentNm ).append( prescriptionDt ).append( medicineNm ).append( dose ).append( doseTmDay ).append( usageNm ).append( effectNm ).append( effectCategoryNm );
				 
				 $resultContainer.append( ul );
			}
		}
		//용법 클릭 이벤트
		var callback = function(e){
			var ul = $(this).parents(".resultData");
			var effectNm = ul.find(".hidden_effectNm").text(),
				effectCategoryNm = ul.find(".hidden_effectCategoryNm").text();
			
			$("#effectPopup .effectCategoryNm").text( effectCategoryNm );
			$("#effectPopup .effectNm").html( effectNm );
			
			$("#effectPopup").popup("open",{
				positionTo:"window",
				overlayTheme:"b"
			});
		};
		self.event.addEvent($("li.effPop"),"click",callback);
	};
};