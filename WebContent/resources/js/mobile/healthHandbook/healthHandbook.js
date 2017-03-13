/**
 * mcare_mobile_healthHandbook
 */
var mcare_mobile_healthHandbook = function(){
	//상속
	mcare_mobile.call(this);
	//super
	var self = this;
	//변수
	var $table = $("#healthHandbook_retrieve"),
		$retrieve =$(".retrieve_type a.ui-btn"),
		$insert =$("a.insert"),
		$bring =$("a.bring"),
		$sDate = $("#sDate"),
		$eDate = $("#eDate"),
		$strDate = $("#sDateVal"),
		$endDate = $("#eDateVal"),
		$chart = $("#handbookChart"),
		$legend =$("#chart_legend"), 
		$pId = $("#pId"), 
		$cipherKey = $("#cipherKey"); 
	//차트
	var chart = null;
	//이전 화면에서 전달 받은 파라미터 
	var parameterMap = null;
	
	/**
	 * 초기화
	 */
	this.init = function(){
		//파라미터 설정
		setParameterMap();
		
		initDate();
		addEvent();
		self.headerFix();
	};
	
	/**
	 * 이벤트 등록
	 */
	var addEvent = function(){
		//조회 버튼 터치 이벤트
		$retrieve.on("click", function(e){
			if( nullCheckDate() ){				
				retrieveData();
			}
		});
		//추가 버튼 터치 이벤트
		$insert.on("click", function(e){
			//버튼 클릭 배경
			$(this).addClass("active");
			self.changePage( contextPath + "/mobile/healthHandbook/healthHandbookInsert.page" );
		});
		//시작일 클릭 이벤트
		$sDate.on("click", function(e){
			if( self.isAndroid() ){				
				$strDate.trigger("click");
			} else {
				$strDate.focus();
			}
		});
		//종료일 클릭 이벤트
		$eDate.on("click", function(e){
			if( self.isAndroid() ){				
				$endDate.trigger("click");
			} else {
				$endDate.focus();
			}
		});
		//가져오기 버튼 클릭 이벤트
		$bring.on("click",function(e){
			//버튼 클릭 배경
			$(this).addClass("active");
			bring();
		});
	};
	/**
	 * 파라미터 맵 초기화
	 */
	var setParameterMap = function(){
		parameterMap = self.util.getParameter();
	};
	/**
	 * 날짜값 초기화
	 */
	var initDate = function(){
		//종료일 오늘
		var startDate = new Date(),
			endDate = new Date();
		$endDate[0].valueAsDate = endDate;
		//menuparam 날짜 차이 기본값
		var defaultPeriod = self.util.getMenuParam("periodDay");
		//시작일 15일 전 설정
		startDate.setDate ( startDate.getDate() - (defaultPeriod!==null? defaultPeriod : 15) ); //15일 전
		$strDate[0].valueAsDate = startDate;
	};
	/**
	 * 데이터 조회
	 * 
	 */
	var retrieveData = function(){
		//조회 일자 확인
		if( !validateDate() ){
			self.alert( self.getI18n("healthHandbook016") );
			return;
		}
		if( !self.dateValidation( $strDate, $endDate ) ){
			return;
		}
		// 메시지 만들고 
		var jsonMsg = {
			type : "command", 
			functionType : "action", 
			value : { 
				actionName : "selectHealth", 
				callbackFn : "window.activeObj.healthHandbookCallBack"
			},
			actionValue : {
				"startDt" : $strDate.val(), 
				"endDt" : $endDate.val(),
				"pId" : $pId.val(), 
				"cipherKey" : $cipherKey.val()
			}
		}; 
		// 전달 
		self.toNative( jsonMsg ); 
	};
	/**
	 * 조회된 데이터 화면에 표시
	 * @param type {string} 조회타입 혈압,혈당,체질량
	 * @param data {array} 조회된 결과 데이터 
	 */
	var displayData = function( data ){
		var type = $(".retrieve_type a.ui-btn-active").attr("data-type");
		
		var head = createTableHeader(type),
			cellLength = createTableHeader(type).children().length;
		//테이블 헤더
		$table.html( head );
		
		//데이터 없을 때 예외처리
		if( data !== undefined && data !== null && data.length === 0 ){
			var msg = $("<div><span>"+ self.getI18n("common005") +"</span></div>");
			$table.append( msg );
			return;
		}
		var keySet =  {
				"BP":["regDt","regTime","pressMinValue","pressMaxValue"],
				"BS":["regDt","regTime","fastingSugarValue","postSugarValue"],
//				"BS":["regDt","regTime","fastingSugarValue"],
				"BM":["regDt","regTime","heightValue","weightValue","bmiValue"]
		};
		//테이블 바디
		var set = keySet[type];
		for( var i = 0; i < data.length; i++ ){
			 var item = data[i],
			 	 bodyDiv = $("<div></div>").addClass("resultBody").attr("data-seq",item["healthSeq"]),
			 	 div = $("<div></div>");
			 
			 if( type === "BM" ){
				 bodyDiv.addClass("num_5");
			 } else {
				 bodyDiv.removeClass("num_5");
			 }
			 //데이터 key value로 올 때 처리함
			 for( var key = 0; key < set.length; key++ ){
				  var span = $("<span></span");
				  
				  if( set[key] === "regDt" ){
					  bodyDiv.append( div.clone().html( span.text( (item["regDt"]).split(" ")[0].replace(/-/gi,".") ) ) );
				  } else if( set[key] === "regTime" ){
					  bodyDiv.append( div.clone().html( span.text( (item["regDt"]).split(" ")[1] ) ) );
				  } else if( set[key] === "bmiValue" ){
					  bodyDiv.append( div.clone().html( span.text( (item[set[key]]===undefined || item[set[key]]==="")?"":Math.ceil( parseFloat( item[ set[key] ] ) ) ) ) );
				  } else {
					  bodyDiv.append( div.clone().html( span.text( item[ set[key] ]) ) );
				  }
			 }
			 $table.append( bodyDiv );
		};
		
		//수정 이벤트 콜백
		var moveUpdate = function(e){
			var seq = $(this).attr("data-seq");
			self.changePage( contextPath + "/mobile/healthHandbook/healthHandbookUpdate.page?healthSeq="+seq );
		};
		//수정 이벤트 등록 - 동적 객체는 초기화할 때 등록해도 소용없음
		self.event.addEvent( $(".resultBody"), "click", moveUpdate );		
	};
	/**
	 * 타입별로 테이블 헤더가 달라서 설정
	 */
	var createTableHeader = function( type ){
		var option = {
				"BP" : [ self.getI18n("healthHandbook007") ,self.getI18n("healthHandbook008"),self.getI18n("healthHandbook009"),self.getI18n("healthHandbook010")],
				"BS" : [ self.getI18n("healthHandbook007") ,self.getI18n("healthHandbook008"),self.getI18n("healthHandbook011"),self.getI18n("healthHandbook012")],
//				"BS" : [ self.getI18n("healthHandbook007") ,self.getI18n("healthHandbook008"),self.getI18n("healthHandbook011")],
				"BM" : [ self.getI18n("healthHandbook007") ,self.getI18n("healthHandbook008"),self.getI18n("healthHandbook013"),self.getI18n("healthHandbook014"),self.getI18n("healthHandbook015")]
		};
		var data = option[type],
			headDiv = $("<div></div>");
		
		for( var i = 0; i < data.length; i++ ){
			 var div = $("<div></div>").addClass("resultHeader"),
			 	 span = $("<span></span>").text( data[i] );
			 
			 if( type === "BM" ){
				 div.addClass("num_5");
			 }
			 headDiv.append( div.html(span) );
		}
		
		return headDiv;
		
	};
	/**
	 * 조회 일자 확인
	 */
	var validateDate = function(){
		var strDate = new Date( $strDate.val() ),
			endDate = new Date( $endDate.val() );
		//종료일이 시작일보다 같거나 커야함
		return ( strDate <= endDate );
			
	};
	/**
	 * 조회 일자 null 체크
	 */
	var nullCheckDate = function(){
		if( $strDate.val() === "" || $strDate.val() === null ){
			self.alert( self.getI18n("healthHandbook017"),function(){				
				$strDate.focus();
			});
			return false;
		} else if( $endDate.val() === "" || $endDate.val() === null ){
			self.alert( self.getI18n("healthHandbook018"),function(){				
				$endDate.focus();
			});
			return false;
		}		
		return true;
	};
	
	/**
	 * toNative 호출 결과가 들어오는 곳 
	 * 
	 */
	this.healthHandbookCallBack = function( msg ) {
		//TODO: 불필요 코드 정리
		console.log("callBack : " + msg ); 

		if( typeof msg === "string" ){			
			msg = self.util.parseJson( msg );
		}
		if( typeof msg === "object"){
			//self.alert( self.util.stringifyJson(msg) );
		}
		if( msg.success !== undefined && msg.success ){
			displayData( msg.result );//표
			displayChart( msg.result );//차트
		}
	};
	/**
	 * 차트 표시
	 */
	var displayChart = function( data ){
		var ctx = $chart.get(0).getContext("2d");
		if( chart !== null && typeof chart.destroy === "function" ){
			chart.destroy();
		}
		data = restoreChartData( data );
		chart = new Chart(ctx).Line( data );
		
		if( $legend !== undefined ){				
			$legend.html( chart.generateLegend() );
		}
	};
	/**
	 * 차트로 사용하도록 데이터 변환
	 */
	var restoreChartData = function(data){
		var type = $(".retrieve_type a.ui-btn-active").attr("data-type"),
			keySet =  {
				"BP":["pressMinValue","pressMaxValue"],
				"BS":["fastingSugarValue","postSugarValue"],
//				"BS":["fastingSugarValue"],
				"BM":["bmiValue"]
			},
			titleSet = {
				"BP":[self.getI18n("healthHandbook009"),self.getI18n("healthHandbook010")],
				"BS":[self.getI18n("healthHandbook011"),self.getI18n("healthHandbook012")],
//				"BS":[self.getI18n("healthHandbook011")],
				"BM":[self.getI18n("healthHandbook015")]
			},
			objSet = [
				{
				    label: "",
				    fillColor: "rgba(151,187,205,0.2)",
		            strokeColor: "rgba(151,187,205,1)",
		            pointColor: "rgba(151,187,205,1)",
		            pointStrokeColor: "#fff",
		            pointHighlightFill: "#fff",
		            pointHighlightStroke: "rgba(151,187,205,1)",
				    data: ""
				} ,
				{
				    label: "",
				    fillColor: "rgba(220,220,220,0.2)",
		            strokeColor: "rgba(220,220,220,1)",
		            pointColor: "rgba(220,220,220,1)",
		            pointStrokeColor: "#fff",
		            pointHighlightFill: "#fff",
		            pointHighlightStroke: "rgba(220,220,220,1)",
				    data: ""
				}
			],
			set = keySet[type],
			labelArr = [],
			datasetArr = [],
			lineArr = [];
		
		for( var j = 0; j < set.length; j++ ){
			 objSet[j]["label"] = titleSet[type][j];
			 datasetArr.push( objSet[j] );
			 lineArr.push( new Array() );
		}
		
		for( var i = 0; i < data.length; i++ ) {
			 labelArr.push("");
			 var item = data[i];
			 
			 for( var j = 0; j < set.length; j++ ){
				  
				  lineArr[j].push( item[ set[j] ] === undefined ? "0":item[set[j]] );
			 }
		}
		
		for( var j = 0; j < datasetArr.length; j++ ){
			 datasetArr[j]["data"] = lineArr[j];
		}
		
		var chartData = { labels: labelArr,
				datasets: datasetArr
		};
		return chartData;
	};
	/**
	 * 가져오기 이벤트
	 */
	var bring = function(){
		self.loading("show");
//		var today = new Date(),
//			toText = self.util.getDateText(today);
//		var param = {
//			startDate : toText,
//			endDate : toText
//		};
		
		$.ajax({
			url : contextPath + "/mobile/healthHandbook/personalInfo.json",
			method : "POST",
			data : self.util.stringifyJson({}),
			dataType : "json",
			contentType :"application/json",
			success: function(data){
				try{
					
					if( data.msg !== undefined ){
						self.alert( data.msg );
						return;
					}
					if( $.isEmptyObject(data.result) || ( data.result.heightValue.length == 0 && data.result.sugarValue.length == 0 && data.result.weightValue.length == 0 ) ){
						self.alert( self.getI18n("healthHandbook020"),function(){
							return false;
						} );
						return;
					}
					var data = data.result;
					//TODO: 등록화면으로 데이터를 넘겨주도록 변경
					var sugar = typeof data["sugarValue"] === "string" ? data["sugarValue"] : "",
							height = typeof data["heightValue"] === "string" ? data["heightValue"] : "",
									weight = typeof data["weightValue"] === "string" ? data["weightValue"] : "",
											dateString = "",
											timeString = "";
									
									if( typeof data["infoDate"] === "string" ){
										var obj = data["infoDate"].split(" ");
										dateString = obj[0];
										timeString = obj[1];
									}
									if( typeof data["sugarDate"] === "string" ){
										var obj = data["sugarDate"].split(" ");
										dateString = obj[0];
										timeString = obj[1];
									} else {
										dateString = "";
										timeString = "";
									}
									
									self.changePage( contextPath + "/mobile/healthHandbook/healthHandbookInsert.page?sugar="+sugar+"&height="+height+"&weight="+weight + "&dateString="+dateString+"&timeString="+timeString);
				} catch(e) {
					
				}
			},
			error: function(xhr){
				console.log(xhr);
			},
			complete: function(){
				self.loading("hide");
				$bring.removeClass("active");
			}
		});
	};
	/**
	 * bmi 계산
	 * @description
	 *  공식 : 체중 / (키 * 키) 
	 *	18.5이하 : 저체중  
	 *	18.5 ~ 23 : 정상  
	 *	23 ~ 25 : 위험체중 
	 *	25 ~ 30 : 1단계 비만  
	 *	30 ~  : 2단계 비만  
	 *	출처 : 대한비만학회, 비만치료지침 2012년 버전
	 */
	var calculateBmi = function( weightValue, heightValue ) { 
		var weight = weightValue,
			height = heightValue; 
		
		if( weight === "" || height === "" ){
			return;
		}
		
		if( weight > 0 && height > 0 ) {
			var bmi = (weight) / (height * height); 
			
			// 소수점 둘째로 자르기 
			bmi = bmi * 1000000; 
			bmi = parseInt(bmi); 
			bmi = bmi / 100; 
			return bmi;
		}
	};
};

