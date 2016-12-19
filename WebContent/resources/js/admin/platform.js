/**
 * admin/platform
 */
var mcare_admin_platform = function(){
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
		$search.on( "click",function(e){
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
					url: $crudServiceBaseUrl + "/getPlatformList.json",
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
			var aggPlatformList = e.aggPlatformList;
			
			var userOrdArr = new Array(),
			userCntArr = new Array(),
			platformArr = new Array(),
			totalCount = 0;
			
			//내려온 데이터 순서대로 
			for( var n = 0 ; n < aggPlatformList.length ; n ++ ) {
				//순위 배열
				userOrdArr.push( n+1 );
				//플랫폼 타입 배열
				platformArr.push( aggPlatformList[n].platformType );
				//방문횟수 배열
				userCntArr.push( aggPlatformList[n].hitCnt );
				//비율을 위한 전체 횟수
				totalCount += aggPlatformList[n].hitCnt;
			}
			
			var percentage = new Array();
			
			for( var n = 0 ; n < userCntArr.length ; n ++ ) {
				 var cnt = userCntArr[n]/totalCount*100,
				 str_per = ( Math.round(cnt*100) ) / 100;
				 //비율 계산해서 비율 배열에 넣기
				 percentage.push( str_per );
				 //플랫폼 타입을 key로 비율값 value - 차트를 위해서
				 pieData[ platformArr[n] ] = str_per;
			}
			
			var param = [];
			for( var n = 0, size = platformArr.length ; n < size ; n ++ ) {
				param.push({
					"userOrdArr" : userOrdArr[n],
					"platformType" : platformArr[n],
					"hitCnt" : userCntArr[n],
					"percentage" : percentage[n],
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
	        		field : "userOrdArr",
	        		title : "No.",
	        		width: 80,
	        		template: "#:data.userOrdArr#",
	        		attributes: {
				      "class": "table-cell",
				      style: "text-align: center;"
				    }
	        	},
	        	{
	        		field : "platformType",
	        		title : "플랫폼 타입",
	        		width: 320,
	        		attributes: {
				      "class": "table-cell",
				      style: "text-align: center;"
				    }
	        	},
	        	{
	        		field : "hitCnt",
	        		title : "방문횟수",
	        		width: 320,
	        		template: "#= kendo.toString(data.hitCnt,'n0')# 건",
	        		attributes: {
				      "class": "table-cell",
				      style: "text-align: center;"
				    }
	        	},
	        	{
	        		field : "percentage",
	        		title : "비율",
	        		width: 320,
	        		template: "#:data.percentage# %",
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
	
    	var data = setChartData( pieData );
    	
    	//데이터 없으면 차트 표시하지 않기
    	if( data.length === 0 ){
    		var cl = setColor();
    		data.push({
				 value : 0,
				 label : "데이터없음",
				 color : cl,
				 highlight : cl
			});
    	}

		var option = {
				tooltipTemplate: "<%= value %>%"
		};
		//차트 초기화 mcare_admin
		self.chart( $chart, "pie", option, data, $legend );
		
		function setChartData( data ){
			var restore = [];
			for( var key in data ) {
				 var cl = setColor();
				 var item = {
					 value : data[key],
					 label : key,
					 color : cl,
					 highlight : cl
				 };
				 restore.push( item );
			}
			
			return restore;
		}
		
		function setColor( ){
			var colorArr = [
			       "#F7464A","#46BFBD","#FDB45C" ,"#FFC870",
			       "#008000","#3f0080","#800040","#007f80","#7f8000",
			       "#804000","#003f80","#800000","#801900","#ff007f",
			       "#4c8000","#00804c","#001980","#ff0000","#00ff66","#00ff7f",
			       "#7f00ff","#feff00","#003fff","#00ffef","#7fffd4",
			       "#4b0082","#087830","#3200ff","#7fff00","#0000ff"
			];
			var random = Math.floor( Math.random() * ( (colorArr.length-1) + 1 ) );
			
			return colorArr[random];
		}
	};
	/**
	 * 
	 */
	var csvSave = function(e){
		var params = {
				strDate : new Date( self.getStartDatePickerValue() ).setHours(0, 0, 0, 0),
				endDate : new Date( +self.getEndDatePickerValue() ).setHours(0, 0, 0, 0),
				exportTitle : encodeURIComponent("플랫폼별 방문현황"),
				exportTitleList : [encodeURIComponent("플랫폼타입"),encodeURIComponent("방문현황")],
				exportDataKeyList : ["platformType","hitCnt"],
				type:"platform"
		},
		url = $crudServiceBaseUrl + "/exportCSV.json";
		self.csvSave(url,params);
	};
};