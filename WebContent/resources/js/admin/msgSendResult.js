/**
 * admin/msgSendResult
 */
var mcare_admin_msgSendResult = function(){
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
		totalCnt = [],
		successCnt = [];
	/**
	 * 데이터소스 설정
	 */
	var setDataSource = function(){
		
		var dataSource = new kendo.data.DataSource({
			transport: {
				read: {
					url: $crudServiceBaseUrl + "/getMsgResultLogList.json",
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
			var dataList = e.aggMsgSendResultLogList;
			
			var accessDateArr = aggDt = new Array(),
				totalCntArr = totalCnt = new Array(),
				successCntArr = successCnt = new Array();
			
			var startNum = Number( self.getStartDatePickerValue() ),
				endNum = Number( +self.getEndDatePickerValue() + (60*60*24*1000) );
			
			for( var d = startNum, i = 0 ; d < endNum ; d += (60*60*24*1000), i++ ) {
				 var strDate = kendo.toString( new Date(d), "yyyy-MM-dd" );
				 accessDateArr.push( strDate );
				 totalCntArr.push(0);
				 successCntArr.push(0);
				
				 for( var n = 0 ; n < dataList.length ; n ++ ) {
					  if( strDate === dataList[n].aggDt.split(" ")[0] ) {
						  totalCntArr[i] = dataList[n].totalCnt;
						  successCntArr[i] = dataList[n].successCnt;
					  }
				 }
			}
			
			var param = [];
			for( var n = 0, size = accessDateArr.length ; n < size ; n ++ ) {
				param.push({
					"accessDate" : accessDateArr[n],
					"totalCnt" : totalCntArr[n],
					"successCnt" : successCntArr[n]
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
				dataBound: function(){
					initChart();
					//전송실패 없으면 실패 원인 버튼을 임의로 숨기기 - kendo template 으로 처리가 안되서 임의로 숨김
					var failBtnSet = $(".failBtn");
					
					for( var i = 0; i < failBtnSet.length; i++ ){
						 var item = $( failBtnSet[i] ),
						 	 data = item.data(),
						 	 total = data["total"],
						 	 success = data["success"];
						 
						if( total === success ){
							item.hide();
						}
					}
					//버튼 이벤트 등록 - 동적으로 생성했으므로 
					$(".failBtn").on("click",function(e){
						var date = $(this).attr("data-date");
						//동적으로 그리드 초기화 시켜서 화면에 표시 - api 트리 선택하면 그리드 생기는 것 처럼
						viewDetail(date);
					});
				},
		        pageable: false,
		        selectable: false,
		        height : 215,
		        columns : [
		        	{
		        		field : "accessDate",
		        		title : "날짜",
		        		width: 200,
		        		attributes: {
					      "class": "table-cell",
					      style: "text-align: center;"
					    }
		        	},
		        	{
		        		field : "totalCnt",
		        		title : "전체전송수",
		        		width: 200,
		        		template: "#= kendo.toString(data.totalCnt,'n0')# 건",
		        		attributes: {
					      "class": "table-cell",
					      style: "text-align: center;"
					    }
		        	},
		        	{
		        		field : "successCnt",
		        		title : "성공전송수",
		        		width: 200,
		        		template: "#= kendo.toString(data.successCnt,'n0')# 건",
		        		attributes: {
					      "class": "table-cell",
					      style: "text-align: center;"
					    }
		        	},
		        	{
		        		title : "",
		        		width: 100,
		        		template: "<button class='failBtn k-button' data-total='#:data.totalCnt#' data-success='#:data.successCnt#' data-date='#=accessDate#' >실패원인</button>",
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
							    label: "전체전송수",
							    fillColor: "rgba(220,220,220,0.2)",
							    strokeColor: "rgba(220,220,220,1)",
							    pointColor: "rgba(220,220,220,1)",
							    pointStrokeColor: "#fff",
							    pointHighlightFill: "#fff",
							    pointHighlightStroke: "rgba(220,220,220,1)",
							    data: totalCnt
							},
							{
							    label: "성공전송수",
							    fillColor: "rgba(151,187,205,0.0)",
							    strokeColor: "rgba(151,187,205,1)",
							    pointColor: "rgba(151,187,205,1)",
							    pointStrokeColor: "#fff",
							    pointHighlightFill: "#fff",
							    pointHighlightStroke: "rgba(151,187,205,1)",
							    data: successCnt
							}  
				]
		};
		var option = {
				tooltipTemplate: "<%= value %>건"
		};
		//차트 초기화 mcare_admin
		self.chart( $chart, "line", option, data, $legend );
	};
	
	var viewDetail = function(date){
		kendo.ui.progress($(".main-wrapper"), true);

		var opt = {
				 url : $crudServiceBaseUrl + "/getMsgResultErrorTypeList.json",
				 method : "POST",
				 data : self.util.stringifyJson({strDate:date}),
				 contentType : "application/json",
	        	 complete : function(){
	        		kendo.ui.progress( $(".main-wrapper"), false );
	        	 }
	    };
	    var success = function(data){
	    	displayErrorType(data.aggDailyMsgSendErrorLogList, date);
	    };
	    var error = function(xhr,d,t){
	    	console.log( xhr );
	    };
	    self.ajaxAdmin( opt, success, error );
	};
	
	var displayErrorType = function(result, date){
		var option = {
				dataSource: {
					data: result,
					schema : {
						model : {
							fields : {
								errorType : { type : "string" },
								failCnt : { type : "number" }
							}
						}
					},
					pageSize : 20
				},
				dataBound: function(){
					
				},
		        pageable: true,
		        selectable: false,
		        height : 300,
		        columns : [
		        	{
		        		field : "errorType",
		        		title : "유형",
		        		width: 200,
		        		attributes: {
					      "class": "table-cell",
					      style: "text-align: center;"
					    }
		        	},
		        	{
		        		field : "failCnt",
		        		title : "건수",
		        		width: 200,
		        		attributes: {
					      "class": "table-cell",
					      style: "text-align: center;"
					    }
		        	}
		        ]
		    };
		
		$( "<div></div>" ).html( "<div style='font-weight:bold;margin:10px;'>"+date+"</div>" ).append( $("<div class='errorGrid'></div>" )).kendoWindow({
			width : 600,
			height : 400,
			title : "메시지 전송 실패원인",
			close : function() {
				this.destroy();
			}
		}).data("kendoWindow").open().center();
		
		$(".errorGrid").kendoGrid(option).data("kendoGrid") ;
	};
	/**
	 * 
	 */
	var csvSave = function(e){
		var params = {
				strDate : new Date( self.getStartDatePickerValue() ).setHours(0, 0, 0, 0),
				endDate : new Date( +self.getEndDatePickerValue() ).setHours(0, 0, 0, 0),
				exportTitle : encodeURIComponent("메시지 전송현황"),
				exportTitleList : [encodeURIComponent("날짜"),encodeURIComponent("전체전송수"),encodeURIComponent("성공전송수")],
				exportDataKeyList : ["aggDt","totalCnt","succeessCnt"],
				type:"msgSendResultLog"
		},
		url = $crudServiceBaseUrl + "/exportCSV.json";
		self.csvSave(url,params);
	};
};