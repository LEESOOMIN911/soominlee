/**
 * admin/code
 */
var mcare_admin_code = function(){
	//상속
	mcare_admin.call(this);
	
	var self = this;
	//변수
	var $grid = $("#grid"),
		$crudServiceBaseUrl = contextPath + "/admin/code";
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
	
	var setDataSource = function(){		
		// 데이터소스	
		var dataSource = new kendo.data.DataSource({
			transport: {
				read:  {
					url: $crudServiceBaseUrl + "/getCodeList.json",
					method: "post",
					dataType: "json",
					contentType: "application/json"
				},
				create: {
					url: $crudServiceBaseUrl + "/saveCode.json",
					method: "post",
					dataType: "json",
					contentType: "application/json",
					complete: gridActionComplete
				},
				update: {
					url: $crudServiceBaseUrl + "/saveCode.json",
					method: "post",
					dataType: "json",
					contentType: "application/json",
					complete: gridActionComplete
				},
				destroy: {
					url: $crudServiceBaseUrl + "/removeCode.json",
					method: "post",
					dataType: "json",
					contentType: "application/json",
					complete: gridActionComplete
				},
				parameterMap: function( options, operation ) {
					if( operation !== "read" && options.models ) {
						return self.util.stringifyJson( options.models[0] );
					}
				}
			},
			batch: true,
			pageSize: 10,
			schema: {
				model: {
					platformType: "platformType",
					versionValue: "versionValue",
					appName: "appName",
					fields: {
						platformType: { type: "string", nullable: false, validation: { required: true }},
						appName: { type: "string", nullable: false, validation: { required: true }},
						versionValue: { type: "string", nullable: false, validation: { required: true }},
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
            sortable: true,
            resizable: true,
            height: 420,
            toolbar: [{ name : "create", text: "추가", complete: function(e) {
    			$grid.data("kendoGrid").dataSource.read(); 
    		} }],
            columns: [
                 { field: "platform", title: "아이디", width: 120, attributes: {style: "text-align: center;"}}
                ,{ field: "appName", title: "이름", width: 240, attributes: {style: "text-align: center;"}}
                ,{ field: "versionValue", title: "버전", width: 80, attributes: {style: "text-align: center;"}}
                ,{ command: ["edit", "destroy"], title: "&nbsp;", width: 170, attributes: {style: "text-align: center;"}}
            ],
            editable: "popup",
            edit: function(e) {
                if (!e.model.isNew()) {
                  	e.container.find( "input[name=id]" ).attr("readonly", true);
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