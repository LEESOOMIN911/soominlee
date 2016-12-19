/**
 * admin/pushForm
 */
var mcare_admin_pushForm = function(){
	//상속
	mcare_admin.call(this);
	
	var self = this;
	//변수
	var $grid = $("#grid"),
		$crudServiceBaseUrl = contextPath + "/admin/pushform";
	/**
	 * 초기화
	 */
	this.init = function(){
		initGrid();
		addEvent();
	};
	/**
	 * 이벤트 등록
	 */
	var addEvent = function(){
		
	};

	// 데이터소스	
	var dataSource = new kendo.data.DataSource({
		transport: {
			read:  {
				url: $crudServiceBaseUrl + "/getList.json",
				method: "post",
				dataType: "json",
				contentType: "application/json",
				complete: gridReadComplete
			},
			create: {
				url: $crudServiceBaseUrl + "/insert.json",
				method: "post",
				dataType: "json",
				contentType: "application/json",
				complete: gridActionComplete
			},
			update: {
				url: $crudServiceBaseUrl + "/update.json",
				method: "post",
				dataType: "json",
				contentType: "application/json",
				complete: gridActionComplete
			},
			parameterMap: function( options, operation ) {
				if( operation !== "read" && options.models ) {
					options.models[0].formType = $("select[data-id=formTypeEditor]").val();
					
					if( options.models[0].formType === "PAGE" ){						
						options.models[0].menuId = $("select[data-id=menuIdEditor]").val();
						options.models[0].menuName = $("select[data-id=menuIdEditor] option:selected").text();
					} else {
						options.models[0].menuId = "";
						options.models[0].menuName = "";
					}
					var id = options.models[0].formId,
						rex1 = /[~!@\#$%<>^&*\()\-=+\’]/gi,
						rex2 = /\s/gi;
					if( rex1.test(id) ){
						alert("특수문자는 사용할 수 없습니다.");
						return false;
					} else if( rex2.test(id) ){
						alert("공백은 포함할 수 없습니다." );
						return false;
					}
					
					return self.util.stringifyJson( options.models[0] );
				} else if( operation === "read" ){
					return self.util.stringifyJson( options );
				}
			}
		},
		batch: true,
		pageSize: 13,
		serverPaging: true,
		serverSorting: true,
		schema: {
			data: "data",
			total: "totalCount",
			model: {
				id: "formId",
				fields: {
					formId: {type:"string"},
					formType: { type: "string"},
					useYn : {type:"string",defaultValue:"N"},
					menuId: { type: "string"},
					menuName: { type: "string"},
					includeNameYn: { type: "string", defaultValue:"N"},
					formDesc : { type:"string" }
				}
			}
		}
	});
	/**
	 * 그리드 이벤트 동작 complate
	 * @private
	 */
	function gridActionComplete( e ){
		if( e.responseText !== "" ){			
			var result = self.util.parseJson( e.responseText );
			if( result.msg !== undefined && result.msg !== "" ){				
				alert( result.msg );
			}
		}
		$grid.data("kendoGrid").dataSource.read(); 
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
    /**
     * 그리드 초기화
     */
    var initGrid = function(){
    	// 그리드 옵션
        var option = {
            dataSource: dataSource,
            pageable: true,
            sortable: true,
            resizable: true,
            height: 580,
            toolbar: [{ name : "create", text: "추가", complete: function(e) {
    			$grid.data("kendoGrid").dataSource.read(); 
    		} }],
            columns: [
                 { field: "formId", title: "이름",filterable:false, width: 120, attributes: {style: "text-align: center;"}}
                ,{ field: "formType", title: "타입",filterable:false, width: 50,editor:formTypeEditor, attributes: {style: "text-align: center;"}}
                ,{ field: "useYn", title: "사용여부",filterable:false, width: 50,editor:useYnEditor, attributes: {style: "text-align: center;"}}
                ,{ field: "menuId", title: "메뉴명",filterable:false,hidden:true,editor:menuIdEditor, width: 80, attributes: {style: "text-align: center;"}}
                ,{ field: "menuName", title: "메뉴명", width: 80, attributes: {style: "text-align: center;"}}
                ,{ field: "includeNameYn", title: "이름포함여부",filterable:false, width:50, editor:includeNameYnEditor,attributes: {style: "text-align: center;"}}
                ,{ field: "formDesc", title: "설명", width: 120,filterable:false, attributes: {style: "text-align: center;"}}
                ,{ command: [{name:"edit",text:"수정"}], title: "&nbsp;", width: 80, attributes: {style: "text-align: center;"}}
            ],
            editable: "popup",
            edit: function(e) {
            	$("label[for=menuName]").parent().hide();
            	$("div[data-container-for=menuName]").hide();
            	if (!e.model.isNew()) {  
            		 $("input[name=formId]").prop("readonly",true);
            		 $("label[for=formType]").parent().hide();
            		 $("div[data-container-for=formType]").hide();
            		 $("label[for=menuId]").parent().hide();
            		 $("div[data-container-for=menuId]").hide();
            	}
            },
            filterable:  {
    			extra : false, 
    			operators : {
    				string : {
    					contains : " 포함 "
    				}
    			}
    		}, 
            dataBound: function () {
                var rowCount = $grid.find( ".k-grid-content tbody tr" ).length;
                if( rowCount < dataSource._take ) {
                    var addRows = dataSource._take - rowCount;
                    for( var i = 0; i < addRows; i++ ) {
                         $grid.find( ".k-grid-content tbody" )
                            .append( "<tr class='kendo-data-row'><td>&nbsp;</td></tr>" );
                    }
                }
            }
        };
        function formTypeEditor(container,options){
        	var select = $("<select></select>").attr({"data-id":"formTypeEditor","data-bind":"value:"+options.field});
			select.html("<option value='PAGE'>PAGE</option>");
			select.append("<option value='MAP'>MAP</option>");	
			select.append("<option value='GUIDE'>GUIDE</option>");
			select.val(options.model.formType).appendTo(container).kendoDropDownList({autoBind: true,
				change : function(e){
					if( this.value() !== "PAGE" ){
						$("label[for=menuId]").parent().hide();
		            	$("div[data-container-for=menuId]").hide();
					} else {
						$("label[for=menuId]").parent().show();
		            	$("div[data-container-for=menuId]").show();
					}
				}});
        }
        function useYnEditor(container,options){
        	var select = $("<select></select>").attr({"data-id":"useYnEditor","data-bind":"value:"+options.field});
			select.html("<option value='Y'>Y</option>");
			select.append("<option value='N'>N</option>");		
			select.val(options.model.useYn).appendTo(container).kendoDropDownList({autoBind: true});
        }
        function menuIdEditor(container,options){
//        	$.ajax({
//        		url: contextPath + "/admin/menu/getList.json",
//        		method : "POST",
//        		dataType : "json",
//        		success: function(data){        			
//        			var select = $("<select></select>").attr({"data-id":"menuIdEditor","data-bind":"value:"+options.field});
//        			for( var i = 0; i < data.length; i++ ){    
//        				 var item = data[i];
//        				 
//        				 if( (item.accessUriAddr).indexOf(".page") > 0 ){
//        					 
//        					 select.append("<option value='"+ item.menuId+"'>"+item.menuName+"</option>");		
//        				 }
//        			}
//        			select.val(options.model.menuId).appendTo(container).kendoDropDownList({autoBind: true});
//        		},
//        		error: function(xhr){
//        			
//        		},
//        		complete: function(){
//        			
//        		}
//        	});
        	var opt = {
        			url: contextPath + "/admin/menu/getList.json",
            		method : "POST",
            		dataType : "json",
   	        	 	complete : function(){
   	        		
   	        	 	}
	   	    };
	   	    var success = function(data){
	   	    	var select = $("<select></select>").attr({"data-id":"menuIdEditor","data-bind":"value:"+options.field});
    			for( var i = 0; i < data.length; i++ ){    
    				 var item = data[i];
    				 
    				 if( (item.accessUriAddr).indexOf(".page") > 0 ){
    					 
    					 select.append("<option value='"+ item.menuId+"'>"+item.menuName+"</option>");		
    				 }
    			}
    			select.val(options.model.menuId).appendTo(container).kendoDropDownList({autoBind: true});
	   	    };
	   	    var error = function(xhr,d,t){
	   	    	
	   	    };
	   	    self.ajaxAdmin( opt, success, error );
        }
        function includeNameYnEditor(container,options){
        	var select = $("<select></select>").attr({"data-id":"includeNameYnEditor","data-bind":"value:"+options.field});
			select.html("<option value='Y'>Y</option>");
			select.append("<option value='N'>N</option>");		
			select.val(options.model.includeNameYn).appendTo(container).kendoDropDownList({autoBind: true});
        }
        //그리드 초기화
        self.grid( $grid, option );
    };
};