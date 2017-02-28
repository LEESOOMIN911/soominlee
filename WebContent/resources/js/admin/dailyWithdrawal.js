/**
 * admin/dailyWithdrawal
 */
var mcare_admin_dailyWithdrawal = function(){
	//상속
	mcare_admin.call(this);
	
	var self = this;
	//변수
	var $search = $("#search"),
		$endDate = $("#endDate"),
		$selectOption = $("#select-option"),
		$strDate = $("#strDate"),
		$grid = $("#grid"),
		$chart = $("#chart"),
		$legend = $("#chart_legend"),
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
		$search.on("click", function(e){
			//mcare_admin 상속
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
		//mcare_admin 상속
		self.datePicker( $endDate, $strDate, $selectOption );
	};
	/**
	 * 검색 조건 dropdown 초기화
	 */
	var initDropDownList = function(){
		//mcare_admin 상속
		self.dropDownList( $selectOption );
	};
	//데이터소스
	var aggDt = [],
		regCnt = [];
	/**
	 * 데이터소스 설정
	 */
	var setDataSource = function(){
		
		var dataSource = new kendo.data.DataSource({
			transport: {
				read: {
					url: $crudServiceBaseUrl + "/getUserWithdrawalList.json",
					method: "post",
					dataType: "json",
					contentType: "application/json"
				},
				parameterMap: getParameterMap
			},
			schema: { 
				data : getSchemaData
			},
			change: function(e) {
				// update chart
			}
		});
		/**
		 * 데이터소스 파라미터맵
		 * @private
		 */
		function getParameterMap( options, operation ){
			var param = $.extend(true, {}, options, {
				strDate : new Date( self.getStartDatePickerValue() ).setHours(0, 0, 0, 0),
				endDate : new Date( +self.getEndDatePickerValue() ).setHours(0, 0, 0, 0),
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
			var aggUserWithdrawalList = e.aggUserWithdrawalList;
			
			var accessDateArr = aggDt = new Array(),
				withdrawalCntArr = withdrawalCnt = new Array();
			
			var startNum = Number( self.getStartDatePickerValue() ),
				endNum = Number( +self.getEndDatePickerValue() + (60*60*24*1000));
			
			for( var d = startNum, i = 0 ; d < endNum ; d += (60*60*24*1000), i++ ) {
				 var strDate = kendo.toString( new Date(d), "MM-dd" );
				 accessDateArr.push( strDate );
				 withdrawalCntArr.push(0);
				
				 for( var n = 0 ; n < aggUserWithdrawalList.length ; n ++ ) {
					  var dt = (aggUserWithdrawalList[n].aggDt.split(" ")[0]).split("-");
					  if( strDate === dt[1]+"-"+dt[2] ) {
						  withdrawalCntArr[i] = aggUserWithdrawalList[n].withdrawalCnt;
					  }
				 }
			}
			
			var param = [];
			for( var n = 0, size = accessDateArr.length ; n < size ; n ++ ) {
				param.push({
					"accessDate" : accessDateArr[n],
					"withdrawalCnt" : withdrawalCntArr[n]
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
		        		width: 352,
		        		attributes: {
					      "class": "table-cell",
					      style: "text-align: center;"
					    }
		        	},
		        	{
		        		field : "withdrawalCnt",
		        		title : "탈퇴인원수",
		        		width: 352,
		        		template: "#= kendo.toString(data.withdrawalCnt,'n0')# 명",
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
		// 차트 옵션 TODO: chartjs로 변경한다
		var data = { labels: aggDt,
				datasets: [
							{
							    label: "탈퇴인원수",
							    fillColor: "rgba(151,187,205,0.0)",
							    strokeColor: "rgba(151,187,205,1)",
							    pointColor: "rgba(151,187,205,1)",
							    pointStrokeColor: "#fff",
							    pointHighlightFill: "#fff",
							    pointHighlightStroke: "rgba(151,187,205,1)",
							    data: withdrawalCnt
							}      
				]
		};
		var option = {
				tooltipTemplate: "<%= value %>명",
				//tooltip 표시 감지 거리 px
				pointHitDetectionRadius : 1
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
				exportTitle : encodeURIComponent("일자별 탈퇴현황"),
				exportTitleList : [encodeURIComponent("일자"),encodeURIComponent("탈퇴현황")],
				exportDataKeyList : ["aggDt","withdrawalCnt"],
				type:"dailyWithdrawal"
		},
		url = $crudServiceBaseUrl + "/exportCSV.json";
		self.csvSave(url,params);
	};
};