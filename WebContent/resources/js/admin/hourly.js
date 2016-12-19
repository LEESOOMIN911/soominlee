/**
 * admin/hourly
 */
var mcare_admin_hourly = function(){
	//상속
	mcare_admin.call(this);
	
	var self = this;
	//변수
	var $search = $("#search"),
		$endDate = $("#endDate"),
		$strDate = $("#strDate"),
		$selectOption = $("#select-option"),
		$chart = $("#chart"),
		$legend = $("#chart_legend"),
		$grid = $("#grid"),
		$csvBtn = $(".csvBtn"),
		$crudServiceBaseUrl = contextPath + "/admin/stats";
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
		$search.on( "click", function(e){
			//mcare_admin
			self.search( dataSource, false );
		});
		//csv
		$csvBtn.on("click",function(e){
			e.preventDefault();
			csvSave(e);
		});
	};
	/**
	 * datepicker 초기화
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
	};
	// 데이터소스
	var timeOrder = [],
		hitCnt = [];
	/**
	 * 데이터소스
	 */
	var setDataSource = function(){
		
		var dataSource = new kendo.data.DataSource({
			transport: {
				read: {
					url: $crudServiceBaseUrl + "/getHourlyList.json",
					method: "post",
					dataType: "json",
					contentType: "application/json"
				},
				parameterMap: getParameterMap
			},
			schema: {
				data: getSchemaData
			},
			change: function(e) {
				// update chart
			}
		});
		/**
		 * 데이터소스 파라미터맵
		 */
		function getParameterMap( options, operation ){
			var param = $.extend(true, {}, options, {
				strDate : new Date( self.getStartDatePickerValue() ).setHours(0, 0, 0, 0),
				endDate : new Date( +self.getEndDatePickerValue() ).setHours(0, 0, 0, 0),
			}, null);
			return self.util.stringifyJson(param);
		};
		/**
		 * 데이터소스 스키마설정
		 */
		function getSchemaData(e){
			if( e.msg !== undefined ){
				alert(e.msg);
				return [];
			}
			var aggHourlyList = e.aggAccessHourlyList;
			//console.log(e);
			var timeNumArr = timeOrder = new Array(),
				pageCntArr = hitCnt = new Array();
			
			for( var n = 0 ; n < 24 ; n ++ ) {
				timeNumArr.push(n);
				pageCntArr.push(0);
			}
			
			for( var n = 0 ; n < aggHourlyList.length ; n ++ ) {
				pageCntArr[ Number(aggHourlyList[n].timeOrder) ] = Number( aggHourlyList[n].hitCnt );
			}
			
			
			
			var param = [];
			for( var n = 0, size = timeNumArr.length ; n < size ; n ++ ) {
				param.push({
					"timeNum" : timeNumArr[n],
					"pageCnt" : pageCntArr[n]
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
			dataBound : initChart,
			pageable: false,
			selectable: true,
	        height : 215,
	        columns : [
	        	{
	        		field : "timeNum",
	        		title : "시간",
	        		width: 352,
	        		template: "#:data.timeNum# 시",
	        		attributes: {
				      "class": "table-cell",
				      style: "text-align: center;"
				    }
	        	},
	        	{
	        		field : "pageCnt",
	        		title : "방문횟수",
	        		width: 352,
	        		template: "#= kendo.toString(data.pageCnt,'n0')# 건",
	        		attributes: {
				      "class": "table-cell",
				      style: "text-align: center;"
				    }
	        	}
	        ]
	    };
		//그리드 초기화 mcare_admin
		self.grid( $grid, option );
	};
	/**
	 * 차트 초기화
	 */
	var initChart = function(){
		// 차트 옵션 TODO: chartjs로 변환
		var data = { labels: timeOrder,
				datasets: [
							{
							    label: "방문횟수",
							    fillColor: "rgba(151,187,205,0.0)",
							    strokeColor: "rgba(151,187,205,1)",
							    pointColor: "rgba(151,187,205,1)",
							    pointStrokeColor: "#fff",
							    pointHighlightFill: "#fff",
							    pointHighlightStroke: "rgba(151,187,205,1)",
							    data: hitCnt
							}      
				]
		};

		var option = {
				tooltipTemplate: "<%= value %>건"
		};
		//차트 초기화 mcare_admin
		self.chart( $chart, "line", option, data, $legend );
	};
	/**
	 * 
	 */
	var csvSave = function(e){
		var params = {
				strDate : new Date( self.getStartDatePickerValue() ).setHours(0, 0, 0, 0),
				endDate : new Date( +self.getEndDatePickerValue() ).setHours(0, 0, 0, 0),
				exportTitle : encodeURIComponent("시간대별 방문현황"),
				exportTitleList : [encodeURIComponent("시간"),encodeURIComponent("방문현황")],
				exportDataKeyList : ["timeOrder","hitCnt"],
				type:"hourly"
		},
		url = $crudServiceBaseUrl + "/exportCSV.json";
		self.csvSave(url,params);
	};
};