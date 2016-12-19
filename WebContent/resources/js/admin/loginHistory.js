/**
 * mcare_admin/ loginHistory
 */
var mcare_admin_loginHistory = function(){
	mcare_admin.call(this);
	
	var self = this;
	
	var $searchText = $("#search-text"),
		$search = $("#search"),
		$endDate = $("#endDate"),
		$selectOption = $("#select-option"),
		$strDate = $("#strDate"),
		$grid = $("#grid"),
		$chart = $("#chart"),
		$legend = $("#chart_legend"),
		$crudServiceBaseUrl = contextPath + "/admin/loginhistory";
	
	var gridDataSource = null;
	
	this.init = function(){
		initDropDownList();
		initDatePicker();
		gridDataSource = setDataSource();
		initGrid();
		addEvent();
	};
	
	var addEvent = function(){
		// 검색 이벤트
		$search.on("click", function(e){
			//mcare_admin 상속
			self.search( gridDataSource, true );
		});
	};
	
	var initDropDownList = function(){
		//mcare_admin 상속
		self.dropDownList( $selectOption );
	};
	var initDatePicker = function(){
		//mcare_admin 상속
		self.datePicker( $endDate, $strDate, $selectOption );
	};
	var setDataSource = function(){
		var dataSource = new kendo.data.DataSource({
	        transport: {
	            read: {
	                url: $crudServiceBaseUrl + "/getLoginList.json",
	                method: "post",
	                dataType: "json",
	                contentType: "application/json",
	                complete: gridReadComplete
	            },
	            parameterMap: parameterMap
	        },
	        pageSize: 17,
	        serverPaging: true,
	        serverSorting: true,
	        schema: {
	            data: "data",
	            total : "totalCount",
	            model: {
					id: "loginSeq",
					fields: {
						loginSeq: { type: "number"},
						loginDt: { type: "string"},
						userId: { type: "string"},
						rememberMeYn: { type: "string"}
					}
				}
	        }
	    });
		dataSource.query({page: 1, pageSize: 17});
		
		function parameterMap( options, operation ){
			var param = $.extend(true, {}, options, {
        		strDate : new Date( self.getStartDatePickerValue() ).setHours(0, 0, 0, 0),
				endDate : new Date( +self.getEndDatePickerValue()).setHours(0, 0, 0, 0),
				userId : $searchText.val(),
            }, null);
        	return self.util.stringifyJson( param );
		};
		/**
		 * 그리드 이벤트 동작 complate
		 * @private
		 */
		function gridReadComplete( e ){
			var result = self.util.parseJson( e.responseText );
			if( result.msg ){
				alert( result.msg );
				if( result.type == "AuthException" ){
					window.location.href = contextPath + "/admin/logout.page";
					return;
				}	
			}
		};
		return dataSource;
	};

	var initGrid = function(){

		var option = {
				dataSource: gridDataSource,
				pageable: true,
		        sortable: true,
		        resizable: true,
		        autoBind: false,
		        selectable: true,
		        height: 580,
		        columns : [
		             { field: "loginSeq", title: "시퀀스",hidden:true, width: 30, }
					,{ field: "loginDt", title: "접속일시", width: 150, template: '#:kendo.toString(new Date(loginDt), "yyyy-MM-dd HH:mm:ss")#', attributes: {"class": "table-cell", style: "text-align: center;"}}
					,{ field: "userId", title: "아이디", width: 100, attributes: {"class": "table-cell", style: "text-align: center;"}}
					,{ field: "rememberMeYn", title: "자동로그인 여부", width: 130, template: '#if(rememberMeYn === "Y"){#<span>Y</span>#}else{#<span>N</span>#}# ', attributes: {"class": "table-cell", style: "text-align: center;"}}
		        ]	
		};
		self.historyGrid = self.grid( $grid, option );
		
	};
};
