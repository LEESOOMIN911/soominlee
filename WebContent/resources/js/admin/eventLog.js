/**
 * admin/eventLog
 */
var mcare_admin_eventLog = function(){
	//상속
	mcare_admin.call(this);
	
	var self = this;
	//변수
	var $search = $("#search"),
		$endDate = $("#endDate"),
		$strDate = $("#strDate"),
		$selectEvent = $("#select-event"),
		$selectOption = $("#select-option"),
		$chart = $("#chart"),
		$legend = $("#chart_legend"),
		$grid = $("#grid"),
		$csvBtn = $(".csvBtn"),
		$crudServiceBaseUrl = contextPath + "/admin/stats";
	
	var aggDt = [],
		welcomeCnt = [],
		welcomeColor = "",
		inCnt = [],
		inColor = "",
		outCnt = [],
		outColor = "",
		goodByeCnt = [],
		goodByeColor = "",
		unknownCnt = [],
		unknownColor = "";
	
	this.searchFlag = false;
	var getSearchFlag = function(){
		return this.searchFlag;
	};
	var setSearchFlag = function( flag ){
		this.searchFlag = flag;
	};
	/**
	 * 초기화
	 */	
	this.init = function(){
		initDatePicker();
		initDropDownList();
		initGrid();
		//initChart();
		addEvent();
	};
	/**
	 * 이벤트 등록
	 */
	var addEvent = function(){
		// 검색 이벤트
		$search.on( "click",function(e){
			//mcare_admin
			dataSource.read();
		});
		//csv
		$csvBtn.on("click",function(e){
			e.preventDefault();
			csvSave(e);
		});
	};
	/**
	 * datapicker 초기화
	 */
	var initDatePicker = function(){
		//mcare_admin
		self.datePicker( $endDate, $strDate, $selectOption );
	};
	/**
	 * 검색조건 dropdown 초기화
	 */
	var initDropDownList = function(){
		// mcare_admin
		self.dropDownList( $selectOption );
		self.dropDownList( $selectEvent,{} );
	};
	
	// 데이타소스
	var pieData = {};
	/**
	 * 데이터소스 설정
	 */
	var setDataSource = function(){
		
		var dataSource = new kendo.data.DataSource({
			transport: {
				read: {
					url: $crudServiceBaseUrl + "/getEventLogList.json",
					method: "post",
					dataType: "json",
					contentType: "application/json"
				},
				parameterMap: getParameterMap
			},
			schema: {
				data : getSchemaData
			}
		});
		/**
		 * 데이터소스 파라미터맵
		 * @private
		 */
		function getParameterMap( options, operation ){
			if( $selectEvent.val() !== "" ){
				setSearchFlag(true);
			} else {
				setSearchFlag(false);
			}
			
			var param = $.extend(true, {}, options, {
				strDate : new Date( self.getStartDatePickerValue() ).setHours(0, 0, 0, 0),
				endDate : new Date( +self.getEndDatePickerValue() ).setHours(0, 0, 0, 0),
				searchEvent : $selectEvent.val()
			}, null);
			return self.util.stringifyJson( param );
		};
		/**
		 * 데이터소스 스키마설정
		 * @private
		 */
		function getSchemaData(e){
			if( e.msg !== undefined ){
				alert(e.msg);
				return [];
			}
			
			var aggEventLogList = e.aggEventLogList;
			
			var accessDateArr = aggDt = new Array(),
				welcomeCntArr = welcomeCnt = new Array(),
				inCntArr = inCnt = new Array(),
				outCntArr = outCnt = new Array(),
				goodByeCntArr = goodByeCnt = new Array(),
				unknownCntArr = unknownCnt = new Array();
			
			var startNum = Number( self.getStartDatePickerValue() ),
				endNum = Number( +self.getEndDatePickerValue() + (60*60*24*1000) );
			
			for( var d = startNum, i = 0 ; d < endNum ; d += (60*60*24*1000), i++ ) {
				 var strDate = kendo.toString( new Date(d), "yyyy-MM-dd" );
				 accessDateArr.push( strDate );
				 welcomeCntArr.push(0);
				 inCntArr.push(0);
				 outCntArr.push(0);
				 goodByeCntArr.push(0);
				 unknownCntArr.push(0);
				
				 for( var n = 0 ; n < aggEventLogList.length ; n ++ ) {
					  if( strDate === aggEventLogList[n].aggDt.split(" ")[0] ) {
						  switch(aggEventLogList[n].eventName){
						  case "WELCOME": welcomeCntArr[i] = aggEventLogList[n].hitCnt;break;
						  case "IN": inCntArr[i] = aggEventLogList[n].hitCnt;break;
						  case "OUT": outCntArr[i] = aggEventLogList[n].hitCnt;break;
						  case "GOODBYE": goodByeCntArr[i] = aggEventLogList[n].hitCnt;break;
						  case "UNKNOWN": unknownCntArr[i] = aggEventLogList[n].hitCnt;break;
						  }
					  }
				 }
			}
			
			var param = [];
			for( var n = 0, size = accessDateArr.length ; n < size ; n ++ ) {
				param.push({
					"accessDate" : accessDateArr[n],
					"welcomeCnt" : welcomeCntArr[n],
					"inCnt" : inCntArr[n],
					"outCnt" : outCntArr[n],
					"goodByeCnt" : goodByeCntArr[n],
					"unknownCnt" : unknownCntArr[n]
				});
			}
			
			return param;
		};
		return dataSource;
	};
	var dataSource = setDataSource();
	/**
	 * 그리드 초기화
	 */
	var initGrid = function(){
	    // 그리드 옵션
		var option = {
				dataSource: dataSource,
				//그리드 데이터 받아온 뒤 차트 초기화
				dataBound: initChart,
		        pageable: false,
		        selectable: true,
		        height : 215,
		        columns : [
		        	{
		        		field : "accessDate",
		        		title : "날짜",
		        		width: 100,
		        		attributes: {
					      "class": "table-cell",
					      style: "text-align: center;"
					    }
		        	},
		        	{
		        		field : "welcomeCnt",
		        		title : "WELCOME",
		        		width: 100,
		        		template: "#= kendo.toString(data.welcomeCnt,'n0')# 건",
		        		attributes: {
					      "class": "table-cell",
					      style: "text-align: center;"
					    }
		        	},
		        	{
		        		field : "inCnt",
		        		title : "IN",
		        		width: 100,
		        		template: "#= kendo.toString(data.inCnt,'n0')# 건",
		        		attributes: {
					      "class": "table-cell",
					      style: "text-align: center;"
					    }
		        	},
		        	{
		        		field : "outCnt",
		        		title : "OUT",
		        		width: 100,
		        		template: "#= kendo.toString(data.outCnt,'n0')# 건",
		        		attributes: {
					      "class": "table-cell",
					      style: "text-align: center;"
					    }
		        	},
		        	{
		        		field : "goodByeCnt",
		        		title : "GOODBYE",
		        		width: 100,
		        		template: "#= kendo.toString(data.goodByeCnt,'n0')# 건",
		        		attributes: {
					      "class": "table-cell",
					      style: "text-align: center;"
					    }
		        	},
		        	{
		        		field : "unknownCnt",
		        		title : "UNKNOWN",
		        		width: 100,
		        		template: "#= kendo.toString(data.unknownCnt,'n0')# 건",
		        		attributes: {
					      "class": "table-cell",
					      style: "text-align: center;"
					    }
		        	}
		        ]
		    };
		//그리드 초기화 - mcare_admin
		self.grid( $grid, option );
	};
	/**
	 * 차트 초기화
	 */
	var initChart = function(){
		
		var data = { labels: aggDt,
				datasets: getDataSet()
		};
		var option = {
				tooltipTemplate: "<%= value %>건"
		};
		//차트 초기화 mcare_admin
		self.chart( $chart, "line", option, data, $legend );
		
	};
	
	var getDataSet = function(){
		var colorArr = setColor( 5 );
		welcomeColor = colorArr[0];
		inColor = colorArr[1];
		outColor = colorArr[2];
		goodByeColor = colorArr[3];
		unknownColor = colorArr[4];
		
		
		var map = {
				"WELCOME":{
				    label: "WELCOME",
				    fillColor: "rgba(151,187,205,0.0)",
				    strokeColor: welcomeColor,
				    pointColor: welcomeColor,
				    pointStrokeColor: "#fff",
				    pointHighlightFill: "#fff",
				    pointHighlightStroke: "rgba(151,187,205,1)",
				    data: welcomeCnt
				},
				"IN":{
				    label: "IN",
				    fillColor: "rgba(151,187,205,0.0)",
				    strokeColor: inColor,
				    pointColor: inColor,
				    pointStrokeColor: "#fff",
				    pointHighlightFill: "#fff",
				    pointHighlightStroke: "rgba(151,187,205,1)",
				    data: inCnt
				},
				"OUT":{
				    label: "OUT",
				    fillColor: "rgba(151,187,205,0.0)",
				    strokeColor: outColor,
				    pointColor: outColor,
				    pointStrokeColor: "#fff",
				    pointHighlightFill: "#fff",
				    pointHighlightStroke: "rgba(151,187,205,1)",
				    data: outCnt
				},
				"GOODBYE":{
				    label: "GOODBYE",
				    fillColor: "rgba(151,187,205,0.0)",
				    strokeColor: goodByeColor,
				    pointColor: goodByeColor,
				    pointStrokeColor: "#fff",
				    pointHighlightFill: "#fff",
				    pointHighlightStroke: "rgba(151,187,205,1)",
				    data: goodByeCnt
				},
				"UNKNOWN":{
				    label: "UNKNOWN",
				    fillColor: "rgba(151,187,205,0.0)",
				    strokeColor: unknownColor,
				    pointColor: unknownColor,
				    pointStrokeColor: "#fff",
				    pointHighlightFill: "#fff",
				    pointHighlightStroke: "rgba(151,187,205,1)",
				    data: unknownCnt
				}	
		};
		function setColor( num ){
			var colorArr = [
			                "#F7464A","#46BFBD","#FDB45C" ,"#FFC870",
						    "#008000","#3f0080","#800040","#007f80","#7f8000",
						    "#804000","#003f80","#800000","#801900","#ff007f",
						    "#4c8000","#00804c","#001980","#ff0000","#00ff66","#00ff7f",
						    "#7f00ff","#feff00","#003fff","#00ffef","#7fffd4",
						    "#4b0082","#087830","#3200ff","#7fff00","#0000ff"
			],
			returnArray = [];
			for( var i = 0; i < num; i++ ){				
				 var random = Math.floor( Math.random() * ( (colorArr.length-1) + 1 ) );
				 returnArray.push( colorArr[random] );
				 colorArr.splice(random, 1);
			};
			
			return returnArray;
		};
		
		var arr = [];
		
		if( getSearchFlag() ){
			arr.push( map[$selectEvent.val()] );
		} else {
			for( var key in map) {
				 arr.push( map[key] );
			}
		}
		
		return arr;
	};
	/**
	 * 
	 */
	var csvSave = function(e){
		var params = {
				strDate : new Date( self.getStartDatePickerValue() ).setHours(0, 0, 0, 0),
				endDate : new Date( +self.getEndDatePickerValue() ).setHours(0, 0, 0, 0),
				searchEvent : $selectEvent.val(),
				exportTitle : encodeURIComponent("이벤트 발생현황"),
				exportTitleList : [encodeURIComponent("날짜"),encodeURIComponent("이벤트타입"),encodeURIComponent("발생횟수")],
				exportDataKeyList : ["aggDt","eventName","hitCnt"],
				type:"eventLog"
		},
		url = $crudServiceBaseUrl + "/exportCSV.json";
		self.csvSave(url,params);
	};
};