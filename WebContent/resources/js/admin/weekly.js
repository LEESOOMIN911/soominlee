/**
 * admin/hourly
 */
var mcare_admin_weekly = function(){
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
	var weekOrder,
		hitCnt;
	/**
	 * 데이터소스
	 */
	var setDataSource = function(){
		
		var dataSource = new kendo.data.DataSource({
			transport: {
				read: {
					url: $crudServiceBaseUrl + "/getWeeklyList.json",
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
			},
			pageSize: 7
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
			var aggWeeklyList = e.aggAccessDailyList;
			
			var weekNumArr = weekOrder =  new Array(),
				pageCntArr = hitCnt= new Array();
			
	    	var weekArr = ["일","월","화","수","목","금","토"];
	    	for (var n = 0 ; n < 7 ; n ++ ) {
	    		weekNumArr.push(weekArr[n] + "요일");
	    		pageCntArr.push(0);
	    	}
	    	for (var n = 0 ; n < aggWeeklyList.length ; n ++ ) {
	    		pageCntArr[Number( aggWeeklyList[n].weekOrder )-1] = aggWeeklyList[n].hitCnt; 
	    	}
	    	
	    	var param = [];
	    	for(var n = 0, size = weekNumArr.length ; n < size ; n ++ ) {
	    		param.push({
	    			"weekNumArr" : weekNumArr[n],
	    			"pageCntArr" : pageCntArr[n]
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
	       	      		field : "weekNumArr",
	       	      		title : "요일",
	       	      		width: 352,
	       	      		attributes: {
	       		      		"class": "table-cell",
	       		      		style: "text-align: center;"
	       		    	}
	       	      	},
	       	      	{
	       	      		field : "pageCntArr",
	       	      		title : "방문횟수",
	       	      		width: 352,
	       	      		template: "#= kendo.toString(data.pageCntArr,'n0')# 건",
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
		var data = { labels: weekOrder,
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
				exportTitle : encodeURIComponent("요일별 방문현황"),
				exportTitleList : [encodeURIComponent("요일"),encodeURIComponent("방문현황")],
				exportDataKeyList : ["weekOrder","hitCnt"],
				type:"weekly"
		},
		url = $crudServiceBaseUrl + "/exportCSV.json";
		self.csvSave(url,params);
	};
};
