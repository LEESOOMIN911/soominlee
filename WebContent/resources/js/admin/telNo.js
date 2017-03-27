/**
 * admin/telNo
 */
var mcare_admin_telNo = function(){
	//상속
	mcare_admin.call(this);
	
	var self = this;
	//변수
	var $grid = $("#grid"),
		$crudServiceBaseUrl = contextPath + "/admin/telno";
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
				method: "POST",
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
				id: "telnoSeq",
				fields: {
					telnoSeq: { type: "long", nullable: true,editable:false},
					buildingDesc: { type: "string", nullable: false, validation: { required: true }},
					roomDesc: { type: "string", nullable: true},
					telValue: { type: "string", nullable: false,validation: { required: true }},
					telnoOrder: { type: "number", nullable: true},
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
		if( result.msg ){
			if(result.type === "AdminControllerException"){
				alert("전화번호 형식이 유효하지 않습니다. 지역번호-국번-전화번호 형태로 입력해주세요")
			}else{
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
            height: 620,
            toolbar: [{ name : "create", text: "추가", complete: function(e) {
    			$grid.data("kendoGrid").dataSource.read(); 
    		} }],
            columns: [
                 { field: "telnoSeq", title: "구분", width: 50, attributes: {style: "text-align: center;"}}
                ,{ field: "buildingDesc", title: "건물명", width: 120, attributes: {style: "text-align: center;"}}
                ,{ field: "roomDesc", title: "호수", width: 120, attributes: {style: "text-align: center;"}}
                ,{ field: "telnoOrder", title: "순서", width: 50, attributes: {style: "text-align: center;"}}
                ,{ field: "telValue", title: "전화번호", width: 150, attributes: {style: "text-align: center;"},
                 	editor : function(container , options){
                    	var input = $("<input/>");
                    	input.attr({"name" : options.field , "data-bind" : "value:telValue" , "placeholder" : "지역번호-국번-전화번호"}).addClass("k-input k-textbox");
                    	input.appendTo(container);
                    }
                 }
                ,{ command: ["edit", "destroy"], title: "&nbsp;", width: 120, attributes: {style: "text-align: center;"}}
            ],
            editable: {
            	mode:"inline",
            	confirmation:"정말 삭제하시겠습니까?"
            },
            edit: function(e) {
            	if (!e.model.isNew()) {            		
            		e.container.find( "input[name='telNoSeq']" ).attr("readonly", true);
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
        //그리드 초기화
        self.grid( $grid, option );
    };
};