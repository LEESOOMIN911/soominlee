/**
 * admin/vers
 */
var mcare_admin_version = function(){
	//상속
	mcare_admin.call(this);
	
	var self = this;
	//변수
	var $grid = $("#grid"),
		$crudServiceBaseUrl = contextPath + "/admin/version";
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
				url: $crudServiceBaseUrl + "/getAllVersionList.json",
				method: "post",
				dataType: "json",
				contentType: "application/json",
				complete: gridReadComplete
			},
			create: {
				url: $crudServiceBaseUrl + "/insertVersion.json",
				method: "post",
				dataType: "json",
				contentType: "application/json",
				complete: gridActionComplete
			},
			update: {
				url: $crudServiceBaseUrl + "/updateVersion.json",
				method: "post",
				dataType: "json",
				contentType: "application/json",
				complete: gridActionComplete
			},
			destroy: {
				url: $crudServiceBaseUrl + "/removeVersion.json",
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
			data: function(response) {
				//구분할수 있는 id가 없어서 그리드 오류가 생김. 그래서 seq를 인위적으로 넣음
				var list = response.data;
				if( list !== undefined ){
					for (var i = 0; i < list.length; i++) {
					 var obj = list[i];
					 obj["seq"] = (i+1);
					}
				}else {
					list = response.data;
				}
				
	      	      return list;
			},
			total: "totalCount",
			model: {
				id: "seq",
				fields: {
					seq : { type: "number", editable: false, nullable:true},
					platformType: { type: "string", nullable: false, validation: { required: true },defaultValue:"A"},
					certType : { type: "string", nullable: false, validation:{required: true}, defaultValue:"InHouse"},
					appName: { type: "string", nullable: false, validation: { required: true }},
					versionOrder: { type: "number", nullable: false, validation: { required: true }},
					marketUrl: { type: "string", nullable: false, validation: { required: true }},
					appHashValue : { type: "string", nullable: true,
						validation:{
							apphashvalueValidation: function(input){							
								if( input[0].name === "appHashValue" ){									
									var platform = $("select[data-bind='value:platformType']").val(),
									appHash = $("input[data-bind='value:appHashValue']").val();
									
									if(platform === "A" && appHash === ""){
										alert("안드로이드는 해쉬값이 필수입니다.");
										$("input[data-bind='value:appHashValue']").focus();
										return false;
									} 
								}
								return true;
							}
						}
					}
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
                 { field: "seq", title: "seq",hidden:true, width: 30, attributes: {style: "text-align: center;"}}
                ,{ field: "platformType", title: "타입", width: 80, attributes: {style: "text-align: center;"}, editor:platformEditor,
                   template:function(dataItem) {
                		if(dataItem.platformType === "A"){
                			return "Android";
                		} else if(dataItem.platformType === "I"){
                			return "IOS";
                		} else {
                			return "ETC";
                		}
                	}
                }
                ,{ field: "certType", title: "인증서", width: 100, attributes: {style: "text-align: center;"}, editor:certTypeEditor}
                ,{ field: "appName", title: "이름", width: 120, attributes: {style: "text-align: center;"}}
                ,{ field: "versionOrder", title: "버전", width: 80, attributes: {style: "text-align: center;"}}
                ,{ field: "marketUrl", title: "마켓주소", width: 200, attributes: {style: "text-align: center;"}}
                ,{ field: "appHashValue", title: "해쉬값", width: 200, attributes: {style: "text-align: center;"}}
                ,{  command: [{name:"edit",text:"수정"},{name:"destroy",text:"삭제"}], title: "&nbsp;", width: 100, attributes: {style: "text-align: center;"}}
            ],
            editable: {
            	mode:"inline",
            	confirmation:"정말 삭제하시겠습니까?"
            },
            edit: function(e) {
            	if (!e.model.isNew()) {            		
            		$("select[data-id=platformEditor]").data("kendoDropDownList").readonly(true);
            		e.container.find( "input[name='appName']" ).attr("readonly", true);
            		$("select[data-id=certTypeEditor]").data("kendoDropDownList").readonly(true);
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
        function certTypeEditor(container, options){
        	var select = $("<select></select>").attr({"data-id":"certTypeEditor","data-bind":"value:"+options.field});
			select.html("<option value='InHouse'>InHouse</option>");
			select.append("<option value='AppStore'>AppStore</option>");
			select.append("<option value='Developer'>Developer</option>");
			select.val(options.model.certType).appendTo(container).kendoDropDownList({autoBind: false});
        };
        function platformEditor(container, options){
        	var select = $("<select></select>").attr({"data-id":"platformEditor","data-bind":"value:"+options.field});
			select.html("<option value='A'>Android</option>");
			select.append("<option value='I'>IOS</option>");	
			select.val(options.model.platformType).appendTo(container).kendoDropDownList({autoBind: false});
        }
        //그리드 초기화
        self.grid( $grid, option );
    };
};