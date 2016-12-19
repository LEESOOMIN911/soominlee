/**
 * admin/vers
 */
var mcare_admin_manager = function(){
	//상속
	mcare_admin.call(this);
	
	var self = this;
	//변수
	var $grid = $("#grid"),
		$crudServiceBaseUrl = contextPath + "/admin/manager";
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
				url: $crudServiceBaseUrl + "/save.json",
				method: "post",
				dataType: "json",
				contentType: "application/json",
				complete: gridActionComplete
			},
			update: {
				url: $crudServiceBaseUrl + "/save.json",
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
				}else if( operation === "read" ){
					return self.util.stringifyJson( options );
				}
			}
		},
		batch: true,
		pageSize: 14,
		serverPaging : true,
		serverSorting: true,
		schema: {
			data : "data",
			total : "totalCount",
			model: {
				id: "userId",
				fields: {
					userId: { type: "string", nullable: false, validation: { required: true }},
					userName: { type: "string", nullable: false, validation: { required: true }},
					pwdValue: { type: "string", nullable: false, validation: { required: true }},
					deptName: { type: "string", nullable: false},
					enabledYn: { type: "string", nullable: false, defaultValue:"Y"},
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
                 { field: "userId", title: "아이디", width: 80, attributes: {style: "text-align: center;"}}
                ,{ field: "userName", title: "이름", width: 100, attributes: {style: "text-align: center;"}}
                ,{ field: "pwdValue", title: "비밀번호", width: 120, attributes: {style: "text-align: center;"},editor:passwordEditor}
                ,{ field: "deptName", title: "소속", width: 120, attributes: {style: "text-align: center;"}}
                ,{ field: "enabledYn", title: "활성화여부", width: 80, attributes: {style: "text-align: center;"}}
                ,{ command: ["edit", "destroy"], title: "&nbsp;", width: 170, attributes: {style: "text-align: center;"}}
            ],
            editable: {
            	mode:"inline",
            	confirmation : "정말 삭제하시겠습니까?"
            },
            edit: function(e) {
                if (!e.model.isNew()) {
                  	e.container.find( "input[name=userId]" ).attr("readonly", true);
                  	e.container.find( "input[name=password]" ).val("");
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
    function passwordEditor(container,options){
    	$('<input class="k-input k-textbox" type="password" name="password" required data-required-msg="비밀번호를 재입력하세요."  data-bind="value:' + options.field + '"/>').appendTo(container);
    }
};
