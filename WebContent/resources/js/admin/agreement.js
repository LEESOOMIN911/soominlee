/**
 * admin/agreement
 */
var mcare_admin_agreement = function(){
	//상속
	mcare_admin.call(this);
	
	var self = this;
	//변수
	var $grid = $("#grid"),
		$crudServiceBaseUrl = contextPath + "/admin/agreement";
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
					url: $crudServiceBaseUrl + "/addVersion.json",
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
						options.models[0].requiredYn = $("select[data-id=requireDropDownEditor]").data("kendoDropDownList").value();
						options.models[0].enableddYn = $("select[data-id=enabledDropDownEditor]").data("kendoDropDownList").value();
						options.models[0].agreementCl = $("textarea[data-id=contentEditor]").val();
						options.models[0].typeName = $("select[data-id=typeNameEditor]").data("kendoDropDownList").value();
						return self.util.stringifyJson( options.models[0] );
					}else if( operation === "read" ){
						return self.util.stringifyJson( options );
					}
				}
			},
			batch: true,
			pageSize: 14,
			serverPaging: true,
			serverSorting: true,
			schema: {
				data : function(result){
					if( result.msg !== undefined ){
						alert(result.msg);
						return [];
					} else if( typeof result === "object" && result.data === undefined ){
						if( result["typeName"]==="ALL" ){
							result["typeName"] = "일반";
						} else {
							result["typeName"] = "14세이하";
						}
						return result;
					}else {						
						var resultList = result.data;
						var strSet = {"ALL":"일반","UNDER14":"14세이하"};
						for( var i = 0; i < resultList.length; i++ ){
							if( resultList[i]["typeName"] === "ALL" || resultList[i]["typeName"] === "UNDER14" ){							 
								resultList[i]["typeName"] = strSet[ resultList[i]["typeName"] ];
							}
						}
						return resultList;
					}
				},
				total: "totalCount",
				model: {
					id: "agreementSeq",
					fields: {
						agreementSeq: { type: "number"},
						agreementOrder: { type: "string", nullable: false, validation: { required: true }},
						agreementId: { type: "string", nullable: false, validation: { required: true }},
						versionNumber: { type: "string", nullable: false,editable:false,defaultValue:"1", validation: { required: true }},
						agreementName: { type: "string", nullable: false, validation: { required: true }},
						agreementCl: { type: "string", nullable: false, validation: { required: true }},
						requiredYn : { type: "string", defaultValue:"N", nullable: false, validation: { required: true }},
						enabledYn : { type: "string", defaultValue:"N",nullable: false, validation: { required: true }},
						typeName : { type : "string", defaultValue:"ALL"}
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
            height: 620,
            toolbar: [{ name : "create", text: "추가", complete: function(e) {
    			$grid.data("kendoGrid").dataSource.read(); 
    		} }],
            columns: [
                 { field: "agreementSeq", title: "약관seq",hidden:true, width: 50, attributes: {style: "text-align: center;"}}      
                ,{ field: "agreementOrder", title: "약관순서", width: 50, attributes: {style: "text-align: center;"}}
                ,{ field: "agreementId", title: "약관 Id", width: 70, attributes: {style: "text-align: center;"}}
                ,{ field: "versionNumber", title: "약관 버전", width: 50, attributes: {style: "text-align: center;"}}
                ,{ field: "agreementName", title: "약관 이름", width: 100, attributes: {style: "text-align: center;"}}
                ,{ field: "agreementCl", title: "약관 내용", width: 170,sortable:false, attributes: {style: "text-align: center;"}, editor:contentEditor}
                ,{ field: "requiredYn", title: "필수여부", width: 50, editor: requireDropDownEditor,attributes: {style: "text-align: center;"}}
                ,{ field: "enabledYn", title: "활성화 여부", width: 50, editor: enabledDropDownEditor,attributes: {style: "text-align: center;"}}
                ,{ field: "typeName", title: "동의서유형", width: 70, editor: typeNameEditor,attributes: {style: "text-align: center;"}}
                ,{ command: [{name:"edit",text:"수정"}], title: "&nbsp;", width: 100, attributes: {style: "text-align: center;"}}
            ],
            editable: "popup",
//            dataBound: function (con,opt) {
//                var rowCount = $grid.find( ".k-grid-content tbody tr" ).length;
//                if( rowCount < dataSource._take ) {
//                    var addRows = dataSource._take - rowCount;
//                    for( var i = 0; i < addRows; i++ ) {
//                         $grid.find( ".k-grid-content tbody" )
//                            .append( "<tr class='kendo-data-row'><td>&nbsp;</td></tr>" );
//                    }
//                }
//            },
            edit: function(e){
            	//수정일때
            	if( !e.model.isNew() ){
            		 e.container.kendoWindow("title","수정");
            		//동의서id readonly
            		$("input[name=agreementId]").prop("readonly",true);
            		$("select[data-id=typeNameEditor]").data("kendoDropDownList").readonly(true);
            		//약관seq
            		$("div[data-container-for=agreementSeq]").prev("div").hide();
            		$("div[data-container-for=agreementSeq]").hide();
            		//약관id
            		$("div[data-container-for=agreementId]").prev("div").hide();
            		$("div[data-container-for=agreementId]").hide();
            		//버전번호
            		$("div.k-edit-form-container label[for=versionNumber]").parent().hide();
            		$("div.k-edit-form-container label[for=versionNumber]").parent().next("div").hide();
            		//약관id
            		$("div[data-container-for=typeName]").prev("div").hide();
            		$("div[data-container-for=typeName]").hide();
            		//활성화는 Y이면 못고치게
            		if( e.model.enabledYn === "Y" ){
            			$("select[data-id=enabledDropDownEditor]").data("kendoDropDownList").readonly(true);
            		}
            		$("div[data-container-for=agreementCl]").prev("div").css("vertical-align","top");
            	} else {
            		 e.container.kendoWindow("title","추가");
            		//약관seq
            		$("div.k-edit-form-container label[for=agreementSeq]").parent().hide();
            		$("div[data-container-for=agreementSeq]").hide();
            		//버전번호
            		$("div.k-edit-form-container label[for=versionNumber]").parent().hide();
            		$("div.k-edit-form-container label[for=versionNumber]").parent().next("div").hide();
            		$("div.k-edit-label label[for=agreementCl]").parent().css("vertical-align","top");
            	}
            }
        };
        function requireDropDownEditor(container, options) {
			var select = $("<select></select>").attr({"data-id":"requireDropDownEditor","data-bind":"value:"+options.field});
			select.html("<option value='Y'>Y</option>");
			select.append("<option value='N'>N</option>");		
			select.val(options.model.requiredYn).appendTo(container).kendoDropDownList({autoBind: false});
		}
        function enabledDropDownEditor(container, options) {
			var select = $("<select></select>").attr({"data-id":"enabledDropDownEditor","data-bind":"value:"+options.field});
			select.html("<option value='Y'>Y</option>");
			select.append("<option value='N'>N</option>");		
			select.val(options.model.requiredYn).appendTo(container).kendoDropDownList({autoBind: false});
		}
        function contentEditor(container, options){
        	var textArea = $("<textarea></textarea").attr({"data-id":"contentEditor","data-bind":"value:"+options.field});
        	textArea.css({"height":"300px","width":"98%"});
        	textArea.val(options.model.agreementCl).appendTo(container);
        }
        function typeNameEditor(container, options){
        	var select = $("<select></select>").attr({"data-id":"typeNameEditor","data-bind":"value:"+options.field});
			select.html("<option value='ALL'>일반</option>");
			select.append("<option value='UNDER14'>14세이하</option>");	
			options.model.typeName = (options.model.typeName==="일반"?"ALL":"UNDER14");
			select.val(options.model.typeName).appendTo(container).kendoDropDownList({autoBind: false});
        }
        //그리드 초기화
        self.grid( $grid, option );
    };
};