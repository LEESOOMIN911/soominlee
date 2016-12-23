/**
 * admin/poiMapping
 */
var mcare_admin_poiMapping = function(){
	//상속
	mcare_admin.call(this);
	
	var self = this;
	//변수
	var $grid = $("#grid"),
		$crudServiceBaseUrl = contextPath + "/admin/poi";
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
				complete : gridReadComplete
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
			destroy: {
				url: $crudServiceBaseUrl + "/remove.json",
				method: "post",
				dataType: "json",
				contentType: "application/json",
				complete: gridActionComplete
			},
			parameterMap: function( options, operation ) {
				if( operation !== "read" && options.models ) {
					return self.util.stringifyJson( options.models[0] );
				} else if( operation === "read" ){
					return self.util.stringifyJson( options );
				}
			}
		},
		batch: true,
		pageSize: 14,
		serverPaging: true,
		serverSorting: true,
		schema: {
			data: "data",
			total: "totalCount",
			model: {
				id: "poiSeq",
				fields: {
					poiSeq: {type:"number", editable:false},
					legacyName: { type: "string", nullable: false, validation: { required: true }},
					mapName: { type: "string"},
					mappingDesc: { type: "string"},
					useYn : {type:"string",defaultValue:"N"}
				}
			}
		}
	});
	/**
	 * 그리드 이벤트 동작 complate
	 * @private
	 */
	function gridActionComplete( e ){
		var result = self.util.parseJson( e.responseText );
		if( result.msg )
			alert( result.msg );
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
            height: 620,
            toolbar: [{ name : "create", text: "추가", complete: function(e) {
    			$grid.data("kendoGrid").dataSource.read(); 
    		} }],
            columns: [
                 { field: "poiSeq",hidden:true, title: "시퀀스", width: 20, attributes: {style: "text-align: center;"}}
                ,{ field: "legacyName", title: "기간계", width: 180, attributes: {style: "text-align: center;"}}
                ,{ field: "mapName", title: "지도", width: 140, attributes: {style: "text-align: center;"}}
                ,{ field: "mappingDesc", title: "설명", width: 110, attributes: {style: "text-align: center;"}}
                ,{ field: "useYn", title: "사용여부", width:50, editor:useYnEditor,attributes: {style: "text-align: center;"}}
                ,{ command: [{name:"edit",text:"수정"},{name:"destroy",text:"삭제"}], title: "&nbsp;", width: 100, attributes: {style: "text-align: center;"}}
            ],
            editable: "inline",
            edit: function(e) {
            	if (!e.model.isNew()) {            		
            		
            	}
            },
//            filterable:  {
//    			extra : false, 
//    			operators : {
//    				string : {
//    					contains : " 포함 "
//    				}
//    			}
//    		}, 
            filterable:false,
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
        function useYnEditor(container,options){
        	var select = $("<select></select>").attr({"data-id":"useYnEditor","data-bind":"value:"+options.field});
			select.html("<option value='Y'>Y</option>");
			select.append("<option value='N'>N</option>");		
			select.val(options.model.useYn).appendTo(container).kendoDropDownList({autoBind: false});
        }
        //그리드 초기화
        self.grid( $grid, option );
    };
};