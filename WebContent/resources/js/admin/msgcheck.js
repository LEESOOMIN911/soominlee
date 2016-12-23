/**
 * mcare_admin/ msgcheck
 */
var mcare_admin_msgcheck = function(){
	mcare_admin.call(this);
	
	var self = this;
	
	var $searchText = $("#search-text"),
		$search = $("#search"),
		$endDate = $("#endDate"),
		$selectOption = $("#select-option"),
		$strDate = $("#strDate"),
		$grid = $("#grid"),
		$crudServiceBaseUrl = contextPath + "/admin/msgcheck";
	
	var gridDataSource = null;
	this.page = 1;
	this.skip = 13;
	
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
	                url: $crudServiceBaseUrl + "/getMsgList.json",
	                method: "post",
	                dataType: "json",
	                contentType: "application/json",
	                complete: gridActionComplete
	            },
	            parameterMap: parameterMap
	        },
	        pageSize: 13,
	        serverPaging: true,
	        serverSorting: true,
	        schema: {
	            data: "data",
	            total: "totalCount",
	            model: {
					id: "contentsSeq",
					fields: {
						contentsSeq: { type: "number"},
						formId: { type: "string"},
						pId: { type: "string"},
						messageValue: { type: "string"},
						logDt: { type: "string"}
					}
				}
	        }
	    });
		dataSource.query({page: 1, pageSize: 13});
		
		function parameterMap( options, operation ){
			kendo.ui.progress($(".main-wrapper"), true);
			var param = $.extend(true, {}, options, {
        		strDate : new Date( self.getStartDatePickerValue() ).setHours(0, 0, 0, 0),
				endDate : new Date( +self.getEndDatePickerValue()).setHours(0, 0, 0, 0),
				pId : $searchText.val(),
            }, null);
			if( self.page != 1 ){
				param.page = self.page;
				param.skip = self.skip;
			}
        	return self.util.stringifyJson( param );
		}
		/**
		 * 그리드 이벤트 동작 complate
		 * @private
		 */
		function gridActionComplete( e ){
			kendo.ui.progress($(".main-wrapper"), false);
			var result = self.util.parseJson( e.responseText ),
				current = self.util.parseJson( this.data );
			if( result.msg ){
				alert( result.msg );
				if( result.type == "AuthException" ){
					window.location.href = contextPath + "/admin/logout.page";
					return;
				}	
			}
			self.page = current.page;
			self.skip =  current.skip;
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
//		        filterable: {
//                    extra: false,
//                    operators: {
//                        string: {
//                            eq: "일치",
//                            neq: "불일치",
//                            contains:"포함"
//                        }
//                    }
//                },
		        filterable:false,
		        detailInit: detailInit,
		        dataBound: function() {
                    //this.expandRow(this.tbody.find("tr.k-master-row").first());
                },
		        height : 550,
		        columns : [
		             { field: "contentsSeq", title: "시퀀스", hidden:true, width: 30 }
		            ,{ field: "pId", title: "환자번호", hidden:false, width: 50 }
					,{ field: "formId", title: "유형", width: 70, attributes: {style: "text-align: center;"},filterable: {cell: {enabled: true,delay: 1500}},}
					,{ field: "messageValue", title: "메시지", width: 140, attributes: {style: "text-align: center;"},filterable:false}
					,{ field: "logDt", title: "전송일시", width: 50, attributes: {style: "text-align: center;"},filterable:false}
					// 사용안하고 있어서 주석처리함 
					//,{ title: "관리", width: 80, template: "<button type='button' class='k-button detailBtn' data-pId='#=pId#' data-seq='#=contentsSeq#'>상세보기</button>", attributes: {"class": "detailBtn", style: "text-align: center;"}}
		        ]	
		};
		self.historyGrid = self.grid( $grid, option );
		
		
		 function detailInit(e) {
			 kendo.ui.progress($(".main-wrapper"), true);
             $("<div/>").appendTo(e.detailCell).kendoGrid({
                 dataSource: {
                     transport: {
                         read: 
                        	 function(options) {
//                             $.ajax({
//                               url: $crudServiceBaseUrl + "/getMsgDetailInfoList.json",
//                               method: "post",
//                               dataType: "json", 
//                               contentType: "application/json",
//                               data:self.util.stringifyJson({contentsSeq:e.data.contentsSeq,receiverId:e.data.pId}),
//                               success: function(result) {
//                                 
//                                 options.success(result);
//                               },
//                               error: function(result) {
//                                 
//                                 options.error(result);
//                                 alert("상세 정보 조회 실패");
//                               },
//                               complete:function(){
//                            	   kendo.ui.progress($(".main-wrapper"), false);
//                               }
//                             });
                             var opt = {
                            		 url: $crudServiceBaseUrl + "/getMsgDetailInfoList.json",
                                     method: "post",
                                     dataType: "json", 
                                     contentType: "application/json",
                                     data:self.util.stringifyJson({contentsSeq:e.data.contentsSeq,receiverId:e.data.pId}),
                 	        		complete : function(){
                 	        			kendo.ui.progress( $(".main-wrapper"), false );
                 	        		}
                 	    	};
                 	    	var success = function(data){
                 	    		 options.success(data);
                 	    	};
                 	    	var error = function(xhr,d,t){
                 	    		options.error(xhr);
                                alert("상세 정보 조회 실패");
                 	    	};
                 	    	self.ajaxAdmin( opt, success, error );
                           }
                     },
                     //pageSize: 10
                 },
                 scrollable: false,
                 sortable: true,
                 //pageable: true,
                 columns: [
                     { field: "cotentsSeq",hidden:true },
                     { field: "receiverId", hidden:true },
                     { field: "deviceTokenId", hidden:true },
                     { field: "platformType", title: "플랫폼",template:platformTemplete, width: "100px",attributes: {style: "text-align: center;"} },
                     { field: "successYn", title: "성공여부",template:successYnTemplete, width: "100px",attributes: {style: "text-align: center;"} },
                     { field: "errorType", title: "에러유형", width: "200px" ,attributes: {style: "text-align: center;"}}
                 ]
             });
             
             function platformTemplete(e){
            	 if(e == null || e.platformType == null) {
            		 return "UNKNOWN"; 
            	 }
            	 
            	 if(e.platformType == "A") {
            		 return "Android"; 
            	 }
            	 if(e.platformType == "I") {
            		 return "iOS"; 
            	 }
            	 
            	 return e.platformType; 
             };
             function successYnTemplete(e){
            	 if(e == null || e.successYn == null) { 
            		 return "UNKNOWN"; 
            	 }
            	 
            	 if(e.successYn == "Y") {
            		 return "성공"; 
            	 }
            	 
            	 return "실패";
             };
         };
	};
};
