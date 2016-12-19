/**
 * admin/userAge
 */
var mcare_admin_userAge = function(){
	//상속
	mcare_admin.call(this);
	
	var self = this;
	//변수
	var $grid = $("#grid"),
		$chart = $("#chart"),
		$legend = $("#chart_legend"),
		$csvBtn = $(".csvBtn"),
		$crudServiceBaseUrl = contextPath + "/admin/stats";
		
	/**
	 * 초기화
	 */
	this.init = function(){
		initGrid();
		//initChart();
		addEvent();
	};
	/**
	 * 이벤트 등록
	 */
	var addEvent = function(){
		//csv
		$csvBtn.on("click",function(e){
			e.preventDefault();
			csvSave(e);
		});
	};

	//데이터소스
	var ageOrder = [],
		dataCnt = [];
	/**
	 * 데이터소스 설정
	 */
	var setDataSource = function(){
		
		var dataSource = new kendo.data.DataSource({
			transport: {
				read: {
					url: $crudServiceBaseUrl + "/getUserAgeList.json",
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
			var today = new Date();
			today.setDate( today.getDate() - 1 );
			var param = $.extend(true, {}, options, {
				strDate : new Date( today ).setHours(0, 0, 0, 0),
				endDate : new Date( today ).setHours(0, 0, 0, 0),
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
			var userAgeList = e.aggUserAgeList;
			
			var ageOrderArr = ageOrder = new Array(),
				dataCntArr = dataCnt = new Array();
			
			var param = [];
			
			if( userAgeList.length <= 0 ){
				ageOrderArr.push( "없음" );
				dataCntArr.push( 0 );
				
				param.push({
					"ageOrder" : "없음",
					"dataCnt" : 0
				});
			} else {				
				for( var i = 0 ; i < userAgeList.length; i++ ) {
					var item = userAgeList[i];
					
					ageOrderArr.push( item["ageOrder"] );
					dataCntArr.push( item["dataCnt"] );
					
					param.push({
						"ageOrder" : item["ageOrder"],
						"dataCnt" : item["dataCnt"]
					});
				}
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
		        		field : "ageOrder",
		        		title : "연령대",
		        		width: 352,
		        		attributes: {
					      "class": "table-cell",
					      style: "text-align: center;"
					    }
		        	},
		        	{
		        		field : "dataCnt",
		        		title : "인원수",
		        		width: 352,
		        		template: "#= kendo.toString(data.dataCnt,'n0')# 명",
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
		var data = { labels: ageOrder,
				datasets: [
							{
							    label: "연령대",
							    fillColor: "rgba(151,187,205,0.0)",
							    strokeColor: "rgba(151,187,205,1)",
							    pointColor: "rgba(151,187,205,1)",
							    pointStrokeColor: "#fff",
							    pointHighlightFill: "#fff",
							    pointHighlightStroke: "rgba(151,187,205,1)",
							    data: dataCnt
							}      
				]
		};
		var option = {
				tooltipTemplate: "<%= value %> 명"
		};
		//차트 초기화 mcare_admin
		self.chart( $chart, "line", option, data, $legend );
	};
	/**
	 * 
	 */
	var csvSave = function(e){
		var today = new Date();
		today.setDate( today.getDate() - 1 );
		var params = {
				strDate : new Date( today ).setHours(0, 0, 0, 0),
				endDate : new Date( today ).setHours(0, 0, 0, 0),
				exportTitle : encodeURIComponent("연령별 가입통계"),
				exportTitleList : [encodeURIComponent("연령대"),encodeURIComponent("인원수")],
				exportDataKeyList : ["ageOrder","dataCnt"],
				type:"userAge"
		},
		url = $crudServiceBaseUrl + "/exportCSV.json";
		self.csvSave(url,params);
	};
};