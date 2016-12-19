/**
 * admin/access
 */
var mcare_admin_access = function(){
	//상속
	mcare_admin.call(this);
	
	var self = this;
	//변수설정
	var $search = $("#search"),
		$endDate = $("#endDate"),
		$selectOption = $("#select-option"),
		$strDate = $("#strDate"),
		$grid = $("#grid"),
		$chart = $("#chart"),
		$csvBtn = $(".csvBtn"),
		$crudServiceBaseUrl = contextPath + "/admin/stats";
		

	/**
	 * 초기화
	 */
	this.init = function(){
		initDatePicker();
		initDropDownList();
		initGrid();
		addEvent();
	};
	/**
	 * 이벤트 등록
	 */
	var addEvent = function(){
		$search.on("click", function(e){
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
		self.datePicker( $endDate, $strDate, $selectOption );
	};
	/**
	 * 검색 조건 드롭다운 초기화
	 */
	var initDropDownList = function(){
		self.dropDownList( $selectOption );
	};
	
	/**
	 * 데이터 소스 설정
	 */
	var setDataSource = function(){
		
		var dataSource = new kendo.data.DataSource({
			transport: {
				read: {
					url: $crudServiceBaseUrl + "/getAccessList.json",
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
			},
			pageSize: 20
		});
		/**
		 * 파라미터 맵 설정
		 */
		function getParameterMap( options, operation ){
			var param = $.extend(true, {}, options, {
				strDate : new Date( self.getStartDatePickerValue() ).setHours(0, 0, 0, 0),
				endDate : new Date( +self.getEndDatePickerValue()).setHours(0, 0, 0, 0),
			}, null);
			return self.util.stringifyJson( param );
		};
		/**
		 * 데이터 소스 스키마 설정
		 */
		function getSchemaData(e){
			if( e.msg !== undefined ){
				alert(e.msg);
				return [];
			}
			var aggAccessList = e.aggMenuList;
			var totalCount = 0;
			for( var n = 0 ; n < aggAccessList.length ; n ++ ) {
				totalCount += aggAccessList[n].hitCnt;
			}
			
			var param = [];
			for( var n = 0, size = aggAccessList.length ; n < size ; n ++ ) {
				var cnt = aggAccessList[n].hitCnt/totalCount*100,
				str_per = ( Math.round(cnt*1000) ) / 1000;
				param.push({
					"pageOrd" : n+1,
					"menuId" : aggAccessList[n].menuId,
					"menuName" : aggAccessList[n].menuName,
					"reqUriAddr" : aggAccessList[n].reqUriAddr,
					"hitCnt" : aggAccessList[n].hitCnt,
					"percentage" : str_per
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
	        pageable: true,
	        resizable: true,
	        sortable: true,
	        selectable: true,
	        height : 460,
	        columns : [
	        	{
	        		field : "pageOrd",
	        		title : "No.",
	        		width: 80,
	        		template: "#:data.pageOrd#",
	        		attributes: {
				      "class": "table-cell",
				      style: "text-align: center;"
				    }
	        	},
	        	{
	        		field : "menuName",
	        		title : "메뉴 이름",
	        		width: 100,
	        		attributes: {
	  			      "class": "table-cell",
	  			      style: "text-align: left;"
	  			    }
	        	},
	        	{
	        		field : "menuId",
	        		title : "메뉴 아이디",
	        		width: 100,
	        		attributes: {
	  			      "class": "table-cell",
	  			      style: "text-align: left;"
	  			    }
	        	},
	        	{
	        		field : "hitCnt",
	        		title : "페이지 뷰",
	        		width: 100,
	        		template: "#= kendo.toString(data.hitCnt,'n0')# 건",
	        		attributes: {
				      "class": "table-cell",
				      style: "text-align: center;"
				    }
	        	},
	        	{
	        		field : "percentage",
	        		title : "비율",
	        		width: 100,
	        		template: "#:data.percentage# %",
	        		attributes: {
				      "class": "table-cell",
				      style: "text-align: center;"
				    }
	        	},
	        	{
	        		field : "reqUriAddr",
	        		title : "요청 URI",
	        		width: 200,
	        		attributes: {
	  			      "class": "table-cell",
	  			      style: "text-align: left;"
	  			    }
	        	}
	        ]
	    };
		// 그리드 초기화
		self.grid( $grid, option );
	};
	/**
	 * 
	 */
	var csvSave = function(e){
		var params = {
				strDate : new Date( self.getStartDatePickerValue() ).setHours(0, 0, 0, 0),
				endDate : new Date( +self.getEndDatePickerValue() ).setHours(0, 0, 0, 0),
				exportTitle : encodeURIComponent("페이지별 방문현황"),
				exportTitleList : [encodeURIComponent("메뉴이름"),encodeURIComponent("메뉴아이디"),encodeURIComponent("페이지뷰"),encodeURIComponent("요청URI")],
				exportDataKeyList : ["menuName","menuId","hitCnt","reqUriAddr"],
				type:"access"
		},
		url = $crudServiceBaseUrl + "/exportCSV.json";
		self.csvSave(url,params);
	};
};
