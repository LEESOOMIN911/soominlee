/**
 * admin/api
 */
var mcare_admin_api = function(){
	//상속
	mcare_admin.call(this);
	
	var self = this;
	//변수
	var $wrapper = $(".main-wrapper #wrapper"),
		$apiType = $("#apiType"),
		$httpMethodType = $("#httpMethodType"),
		$dataSourceName = $("#dataSourceName"),
		$resultType = $("#resultType"),
		$addItem = $("#add-item"),
		$baseInfo = $("#baseInfo"),
		$removeItem = $("#remove-item")
		$saveItem = $("#save-item"),
		$reloadItem = $('#reload-item'),
		$deselectItem = $("#deselect-item"),
		$reqParams = $("#reqParams"),
		$wsHeaders = $("#wsHeaders"),
		$execute_api = $("#execute_api"),
		$treeView = $("#tree-view"),
		$apiTabStrip = $("#apiTabStrip"),
		$apiTypeHelp = $(".apiTypeHelp"),
		$helpContents = $("#helpContents"),
		$apiDesc = $("#apiDesc"),
		$resSampleCl = $("#resSampleCl"),
		$crudServiceBaseUrl = contextPath + "/admin/api";
	
	this.treeView = null;
	this.reqParamsGrid = null;
	this.wsHeadersGrid = null;
	/**
	 * 초기화
	 */
	this.init = function(){
		
		$wrapper.kendoSplitter({
			panes : [ {
				collapsible : true,
				resizable : true,
				size : '280px'
			}, {
				collapsible : false
			} ]
		});

		initTree();
		initGrid();
		initDropDownList();
		//설명 글자수 이벤트 - core에 선언
		self.checkDescBytes($apiDesc);
		addEvent();
		
	};
	/**
	 * 이벤트 등록
	 */
	var addEvent = function(){
		// api type 변경 이벤트
		$apiType.on("change",function(e) {
			var treeView = self.treeView;
			var node = treeView.select(),
				item = treeView.dataItem(node),
				observer = new kendo.observable( item );
			
	    	enableApiTypeRow( observer, this.value );
	    });
	    
	    // http method 필드 변경 이벤트
		$httpMethodType.on("change",function(e) {
			httpMethodType( this.value, e );
		});
		
		// 아이템 추가
		$addItem.on("click", function(e) {
			addItem(e);
	    });
		
		// 아이템 삭제
	    $removeItem.on("click",function(e) {
	    	removeItem(e);	
	    });
		
	 	// 아이템 저장
		$saveItem.on("click",function(e) {
			saveItem(e);
	    });

		// 아이템 갱신 (to 모바일 서버)
	    $reloadItem.on("click",function(e) {
	    	reloadItem(e);
	    }); 
	 	
	 	// 실행 테스트 이벤트
//		$test.on("click",function(e) {
//			testApi(e);
//		});
		//아이템 선택 해제
		$deselectItem.on("click",function(e){
			self.treeView.select( null );
		});
		//도움말 클릭 이벤트
		$apiTypeHelp.on("click",function(e){
			showHelp(e);
		});
	};
	/**
	 * http method 필드 변경 이벤트
	 */
	var httpMethodType = function( value, e ){
		var treeView = self.treeView;
		
		var node = treeView.select(),
			item = treeView.dataItem(node);
		
		var apiType = item.data.apiType;
		
		if( apiType === "SQL" ){			
			if( value !== "GET" && value !== "" ) {
				//POST,PUT,DELETE 라면 INT만
				$resultType.html("<option value='INT'>INT</option>").kendoDropDownList().data("kendoDropDownList");
				item.data.resultType = "INT";
			} else {
				//GET 이면 MAP, LIST만
				$resultType.html("<option value='MAP'>MAP</option><option value='LIST'>LIST</option>").kendoDropDownList().data("kendoDropDownList");
				item.data.resultType = "MAP";
			}
		} else {
			//웹서비스, procedure면 전부다
			$resultType.html("<option value='MAP'>MAP</option><option value='LIST'>LIST</option><option value='INT'>INT</option>").kendoDropDownList().data("kendoDropDownList");
			item.data.resultType = "MAP";
		}
	};
	/**
	 * 아이템 추가
	 */
	var addItem = function(e){
		var treeView = self.treeView;
		
		addedNodeCheck( e, treeView.dataSource.data().toJSON() );
		
		if( addedNodeArr.length > 0 ) {
        	alert("저장하지 않은 아이템 [ " + addedNodeArr[0].apiName + " ] (이)가 있습니다");
        	addedNodeArr = [];
        	return false;
        }
		
		var node = treeView.select(),
			item = treeView.dataItem(node);
		if( node.length === 0) {
			alert( "아이템을 선택하세요" );
			return false;
		}
		if( item.nodeType === "API" ){
			alert("카테고리를 선택하여 추가하세요.");
			return false;
		}
		var cat_seq = item.data.catSeq;
		var cat_name = item.data.catName;
		
		var arr = new Array();
		
		arr.push( item.data.pathName );
		while ( item.parentNode() ) {
			item = item.parentNode();
			arr.push(item.data.pathName);
		}
		
		var cat_path_name = "/";
		for( var n = arr.length; n > 0; n-- ) {
			cat_path_name += (arr[n-1] + "/");
		}
		
		treeView.append({
			id: "",
			name: "New api",
			nodeType: "API",
			addedNode: true,
			data: {
				"apiType": $apiType.val(),
				"catSeq": cat_seq,
				"catName": cat_name,
				"catPathName": cat_path_name,
				"dataSourceName": "",
				"apiDesc": "",
				"httpMethodType": "",
				"targetName": "",
				"queryMsg": "",
				"resultType": "",
				"reqUrlAddr": "",
				"reqUrlName": "",
				"targetUrlAddr":"",
				"useYn": 1,
				"reqParams": [],
				"wsUrl" : "",
				"wsHeaders": [],
				"resSampleCl":""
			}
		}, node);
		
//	    $apiTabStrip.kendoTabStrip().data("kendoTabStrip").activateTab( $baseInfo );
	};
	/**
	 * 아이템 삭제
	 */
	var removeItem = function(e){
		var treeView = self.treeView;
		
		kendo.unbind( $wrapper );
    	
        var node = treeView.select(),
        	item = treeView.dataItem(node);
        
        if( node.length === 0 ) {
        	alert( "선택한 아이템이 없습니다" );
            return false;
        }
        if( item.nodeType === "CATEGORY" ) {
        	alert( "카테고리는 카테고리 관리에서 삭제가능합니다." );
         	return false;
        }
        if( item.addedNode ) {
        	treeView.remove( node );
         	return false;
        }
        if( item.childCount > 0 ) {
			alert( "하위 카테고리가 존재하여 삭제할 수 없습니다" );
			return false;
		}

		if( item.apiCount > 0 ) {
			alert( "하위 API가 존재하여 삭제할 수 없습니다" );
			return false;
		}
		
    	if( !confirm("삭제 하시겠습니까?") ) {
    		return false;
    	}
    	
    	kendo.ui.progress( $wrapper, true );
    	
//    	$.ajax({
//    		url : $crudServiceBaseUrl + "/remove.json",
//    		data : { apiSeq : item.id },
//    		type : "POST",
//    		//dataType : "json",
//    		//contentType : "application/json;charset=UTF-8;",
//    		success :  function(data){
//    			if( data.msg !== undefined ){
//    				alert( "수행 중 에러가 발생하였습니다." );
//    				console.log( data.msg );
//    				return false;
//    			}
//    			alert( "삭제 되었습니다." );
//    			location.reload(true);
//    		},
//    		error :  function(xhr,d,t){
//    			console.log(xhr);
//    			alert(xhr.responseText);
//    		},
//    		complete : function(){
//    			kendo.ui.progress( $wrapper, false );
//    		}
//    	});
    	var opt = {
    			url : $crudServiceBaseUrl + "/remove.json",
        		data : { apiSeq : item.id },
        		type : "POST",
        		complete : function(){
        			kendo.ui.progress( $wrapper, false );
        		}
    	};
    	var success = function(data){
    		alert( "삭제 되었습니다." );
			location.reload(true);
    	};
    	var error = function(xhr,d,t){
    		console.log(xhr);
			alert(xhr.responseText);
    	};
    	self.ajaxAdmin( opt, success, error );
	};
	/**
	 * 아이템 저장
	 */
	var saveItem = function(e){
		var treeView = self.treeView;
		
		var node = treeView.select(),
			item = treeView.dataItem(node);
		
    	if( node.length === 0 || item.nodeType === "CATEGORY" ) {
    		alert( "선택한 아이템이 없습니다" );
    		return false;
    	}
		
    	if( !fieldValidation(item) ) {
    		return false;
    	}
    	
    	if( item.data.dataSourceName !== null && typeof item.data.dataSourceName === "object" && item.data.dataSourceName.value !== undefined ){
			item.data.dataSourceName = item.data.dataSourceName.value;
		}
    	var reqParamsArr = $reqParams.data("kendoGrid").dataSource.data();
    	
    	for( var i = 0; i < reqParamsArr.length; i++ ){
    		 var it = reqParamsArr[i],
    		 	 paramName = it.paramName;
    		 
    		 reqParamsArr[i].paramName = paramName.replace(/\s/gi,'');
    	};
    	
		var param = $.extend(true, {}, item.data.toJSON(), {
        	apiSeq: item.id,
        	apiName: item.name,
    		nodeType: "API",
    		reqParams: reqParamsArr,
    		wsHeaders: $wsHeaders.data("kendoGrid").dataSource.data(),
    		url: item.data.catPathName
        }, null);
		
		kendo.ui.progress($wrapper, true);

//    	$.ajax({
//    		url : $crudServiceBaseUrl + "/save.json",
//    		data : self.util.stringifyJson( param ),
//    		type : "POST",
//    		dataType : "json",
//    		contentType : "application/json;charset=UTF-8;",
//    		success :  function(data){
//    			if( data.msg !== undefined ){
//    				alert( "수행 중 에러가 발생하였습니다." );
//    				console.log( data.msg );
//    				return false;
//    			}
//    			alert( "저장 되었습니다." );
//    			//location.reload();
//    		},
//    		error :  function(xhr,d,t){
//    			console.log(xhr);
//    			alert(xhr.reponseText);
//    		},
//    		complete : function(){
//    			kendo.ui.progress( $wrapper, false );
//    		}
//    	});
    	var opt = {
    			url : $crudServiceBaseUrl + "/save.json",
        		data : self.util.stringifyJson( param ),
        		type : "POST",
        		dataType : "json",
        		contentType : "application/json;charset=UTF-8;",
        		complete : function(e){
        			kendo.ui.progress( $wrapper, false );
        		}
    	};
    	var success = function(data){
    		kendo.ui.progress( $wrapper, false );
    		alert( "저장 되었습니다." );
    		if (item.addedNode === true) { // 신규 노드만 리로딩
				location.reload(true);
			}
//    		var node = self.treeView.select(),
//    			item = self.treeView.dataItem( node ),
//    			observer = new kendo.observable( item );
//    			observer.id = data.apiSeq
//    			if( observer.addedNode ){
//    				delete observer.addedNode;
//    			}
//    			kendo.bind( $wrapper, observer );
    		//location.reload(true);
    	};
    	var error = function(xhr,d,t){
    		console.log(xhr);
			alert(xhr.responseText);
    	};
    	self.ajaxAdmin( opt, success, error );
	};
	/**
	 * 트리 아이템 reload 이벤트
	 * @private
	 */
	var reloadItem = function(e){
		kendo.ui.progress( $wrapper, true );
//		
//		$.ajax({
//			url : $crudServiceBaseUrl + "/cacheReload.json",
//			type : "POST",
//			contentType: "application/json; charset=UTF-8",
//			success :  function(data){
//				if( data === 1 ) {
//					alert("갱신 되었습니다");
//				} else {
//					alert( "수행 중 에러가 발생하였습니다" );
//				}
//			},
//			error : function(xhr,d,t){
//				console.log(xhr);
//				console.log(t);
//			},
//			complete : function(){
//				kendo.ui.progress( $wrapper, false );
//			}
//		});
		var opt = {
				url : $crudServiceBaseUrl + "/cacheReload.json",
				type : "POST",
				contentType: "application/json; charset=UTF-8",
        		complete : function(){
        			kendo.ui.progress( $wrapper, false );
        		}
    	};
    	var success = function(data){
    		if( data === 1 ) {
				alert("갱신 되었습니다");
			} else {
				alert( "수행 중 에러가 발생하였습니다" );
			}
    	};
    	var error = function(xhr,d,t){
    		console.log(xhr);
			console.log(t);
    	};
    	self.ajaxAdmin( opt, success, error );
	};
	/**
	 * 실행 테스트 이벤트
	 */
	var testApi = function(e){
		var treeView = self.treeView;
		
		var node = treeView.select(),
			item = treeView.dataItem(node);
		
		item.data.reqParams = $reqParams.data("kendoGrid").dataSource.data();
		item.data.wsHeaders = $wsHeaders.data("kendoGrid").dataSource.data();
		
		if( item.data.dataSourceName !== null && typeof item.data.dataSourceName === "object" && item.data.dataSourceName.value !== undefined ){
			item.data.dataSourceName = item.data.dataSourceName.value;
		}
		
		kendo.ui.progress( $wrapper, true );
		
//		$.ajax({
//    		url : $crudServiceBaseUrl + "/test.json",
//    		data : self.util.stringifyJson( item.data.toJSON() ),
//    		type : "POST",
//    		dataType : "json",
//    		contentType : "application/json;charset=UTF-8;",
//    		success :  function(data){
//    			if( data.msg !== undefined ){
//    				alert( data.msg );
//    				return false;
//    			}
//    			if( $.isEmptyObject(data) ) {
//    				alert( "테스트에 대한 결과 데이터가 없습니다." );
//    				return false;
//    			}
//
//    			var responseData = self.util.stringifyJson(data, null, 2);
//    			
//    	    	$( "<div></div>" ).append( $("<pre></pre>" ).html(responseData) ).kendoWindow({
//    				width : 600,
//    				height : 400,
//    				title : "응답결과",
//    				close : function() {
//    					this.destroy();
//    				}
//    			}).data("kendoWindow").open().center();
//    		},
//    		error :  function(xhr,d,t){
//    			console.log(xhr);
//    			alert("[" + xhr.statusText + "] " + xhr.responseText);
//    		},
//    		complete: function(){
//    			kendo.ui.progress( $wrapper, false );
//    		}
//    	});
		var opt = {
				url : $crudServiceBaseUrl + "/test.json",
	    		data : self.util.stringifyJson( item.data.toJSON() ),
	    		type : "POST",
	    		dataType : "json",
	    		contentType : "application/json;charset=UTF-8;",
        		complete : function(){
        			kendo.ui.progress( $wrapper, false );
        		}
    	};
    	var success = function(data){
    		if( $.isEmptyObject(data) ) {
				alert( "테스트에 대한 결과 데이터가 없습니다." );
				return false;
			}

			var responseData = self.util.stringifyJson(data, null, 2);
			
	    	$( "<div></div>" ).append( $("<pre></pre>" ).html(responseData) ).kendoWindow({
				width : 600,
				height : 400,
				title : "응답결과",
				close : function() {
					this.destroy();
				}
			}).data("kendoWindow").open().center();
    	};
    	var error = function(xhr,d,t){
    		console.log(xhr);
			alert("[" + xhr.statusText + "] " + xhr.responseText);
    	};
    	self.ajaxAdmin( opt, success, error );
	};
	/**
	 * 트리 초기화
	 */
	var initTree = function(){
		// 데이터소스	
		var treeDataSource = new kendo.data.HierarchicalDataSource({
	        transport: {
	            read: {
	                url: $crudServiceBaseUrl + "/getList.json",
	                method: "get",
	                dataType: "json",
	                contentType: "application/json"
	            }
	        },
	        schema : {
	        	model : {
	        		id : "id",
					hasChildren : function() {
						if (this.nodeType === "API") {
							return false;
						} else {
							return (this.data.childCount > 0 || this.data.apiCount > 0);
						}
					}
				}
	        }
	    });
		var option = {
				loadOnDemand: false,
				dataSource : treeDataSource,
				dataTextField : "name",
				template : treeTemplate,
				select : treeSelect
			}
		// 트리
		self.treeView = self.tree( $treeView, option );
		/**
		 * 트리 template
		 */
		function treeTemplate(o){
			var span = $("<span></span>").addClass("k-sprite").attr("data-id", o.item.id );
			if (o.item.nodeType == "API") {
				span.addClass("html");
				return span[0].outerHTML + o.item.name;
			} else { 
				return span[0].outerHTML + o.item.name +' ['+o.item.data.pathName+']';
			}
		};
		/**
		 * 트리 선택 이벤트
		 */
		function treeSelect(o){
			kendo.bind( $wrapper, null );
			
			var item = this.dataItem( o.node ),
				observer = new kendo.observable( item );
			
			if( observer.nodeType === "CATEGORY" ) {				
				$( ".right-pane", $wrapper ).hide();
				return;
			}
			$(".right-pane", $wrapper).show();
			enableApiTypeRow( observer,observer.data.apiType );
			
			observer.getCreateDate = function() {
				if( this.data.createDt ) return new Date(this.data.createDt).toLocaleString();
				return "";
			};
			
			observer.getUpdateDate = function() {
				if(this.data.updateDt) return new Date(this.data.updateDt).toLocaleString();
				return "";
			};
			
			self.reqParamsGrid.dataSource.read({"apiSeq": item.id});
			self.wsHeadersGrid.dataSource.read({"apiSeq": item.id});
			
            kendo.bind( $wrapper, observer );
            
            if( observer.data.httpMethodType === "GET" ){
            	$resultType.html("<option value='MAP'>MAP</option><option value='LIST'>LIST</option>").kendoDropDownList()
            	.data("kendoDropDownList").select(function(dataItem) {
    			    return dataItem.text === observer.data.resultType;
        		});
            } else {
            	$resultType.html("<option value='INT'>INT</option>").kendoDropDownList().data("kendoDropDownList")
            	.select(function(dataItem) {
    			    return dataItem.text === observer.data.resultType;
        		});
            }
            //설명 글자수제한 초기화
            $apiDesc.trigger("keyup");
		};
	};
	/**
	 * 그리드 초기화
	 */
	var initGrid = function(){
		
		// 파라미터 그리드
		var paramOption = {
				dataSource : {
					transport: {
			            read: {
			                url: $crudServiceBaseUrl + "/getParamList.json",
			                method: "get",
			                dataType: "json",
			                contentType: "application/json"
			            }
			        },
					schema : {
						model : {
							id : "paramSeq",
							fields : {
								paramName : {type : "string", validation : { required : true }},
								dataType : {type : "string", defaultValue : "STRING"},
								sampleValue : {type : "string"},
								paramDesc : {type : "string"}
							}
						}
					}
				},
				toolbar : [ {name: "create", text: "추가"},{template : "<button type='button' class='k-button' id='execute_api'>테스트</button>"} ],
				editable : { createAt : "bottom" },
				resizable : true,
				sortable : true,
				columns : [ 
				     {title:"no",template:"#= getNum()#",width:30,attributes: {style: "text-align: center;"}}      
					,{field : "paramName", title : "이름", attributes: {style: "text-align: center;"}}
					,{field : "dataType", title : "타입", editor : reqParamTypeDropDownEditor, template: "#=dataType#", attributes: {style: "text-align: center;"}}
					,{field : "sampleValue", title : "샘플 파라미터", attributes: {style: "text-align: center;"}}
					,{field : "paramDesc", title : "설명", attributes: {style: "text-align: center;"}}
					,{command : "destroy", title : "", attributes: {style: "text-align: center;"}} 
					],
				dataBound : function(){
					resetRowNum();
				}
			};
		self.reqParamsGrid = self.grid ( $reqParams, paramOption );
		
		//테스트 버튼 클릭 이벤트
		$("#execute_api").on("click",function(e){
			testApi(e);
		});
	      
		// 헤더정보 그리드
		var wsHeaderOption = {
				dataSource : {
					transport: {
			            read: {
			                url: $crudServiceBaseUrl + "/getHeaderList.json",
			                method: "get",
			                dataType: "json",
			                contentType: "application/json"
			            
			            }
			        },
					schema : {
						model : {
							id : "headerSeq",
							fields : {
								headerName : {type : "string", defaultValue : "Accept"},
								headerValue : {type : "string", editable : true}
							}
						}
					}
				},
				toolbar : [ {name: "create", text: "추가"} ],
				editable : { createAt : "bottom" },
				resizable : true,
				sortable : true,
				columns : [ 
					 {field : "headerName", title : "이름", editor : wsHeaderTypeDropDownEditor, template: "#=headerName#", attributes: {style: "text-align: center;"}}
					,{field : "headerValue", title : "값", attributes: {style: "text-align: center;"}}
					,{command : "destroy", title : "", attributes: {style: "text-align: center;"}} 
					]
			};
		self.wsHeadersGrid = self.grid( $wsHeaders, wsHeaderOption );
		
		/**
		 * 파라미터 LOV
		 */
		function reqParamTypeDropDownEditor(container, options) {
			$("[data-id='reqParamTypeDropDownEditor']:first").clone().attr("data-bind", "value:" + options.field).appendTo(container).kendoDropDownList();
		}
		
		// 헤더정보 LOV
		/**
		 * 
		 */
		function wsHeaderTypeDropDownEditor(container, options) {
			$("[data-id='wsHeaderTypeDropDownEditor']:first").clone().attr("data-bind", "value:" + options.field).appendTo(container).kendoDropDownList();
		}
	};
	/**
	 * dropdownlist 초기화
	 */
	var initDropDownList = function(){
		// 설명 에디터
		self.dropDownList( $apiType, {} );
		self.dropDownList( $dataSourceName, {} );
		self.dropDownList( $httpMethodType, {} );
		self.dropDownList( $resultType, {} );
	};
	// 저장하지 않은 아이템 배열
	var addedNodeArr = [];
	/**
	 * 저장하지 않은 아이템 확인
	 */
	var addedNodeCheck = function(e, treeViewData) {
		if (treeViewData) {
	    	for (var n = 0; n < treeViewData.length; n++ ) {
				var childData = treeViewData[n];
	   			if (childData.addedNode === true) {
	   				addedNodeArr.push(childData);
	   				return;
	 			} else {
		   			var child = childData.items;
		   			if (child) {
			   			if (child.length > 0) {
			   				addedNodeCheck(e, child);
			   			}
		   			}
	 			}
	   		}
		}
	};

	/**
	 * api type 변경 처리
	 */
 	function enableApiTypeRow(o,v) {
 		if( v === "WEB_SERVICE" ) {
 			var dropDown = $dataSourceName.data("kendoDropDownList");
 			dropDown.select(0);
 			o.data.dataSourceName = null;
 			dropDown.enable(false);
 			
 			$("#query-row").hide();
    		$("#procd-row").hide();
    		$("#ws-row").show();
 		} else {
 			$dataSourceName.data("kendoDropDownList").enable(true);
 			
	 		$("#ws-row").hide();
	 		if( v === "PROCEDURE") {
	    		$("#query-row").hide();
	    		$("#procd-row").show();
	    	} else {
	    		$("#procd-row").hide();
	    		$("#query-row").show();
	    	}
 		}
 	}

 	/**
 	 * 필수값 체크
 	 */
	function fieldValidation(item) {
		if(item.name === "" ) {
			alert( "이름을 입력하세요" );
			return false;
		}
		if( item.data.reqUrlAddr === "" ) {
			alert( "API URI를 입력하세요" );
			return false;
		}
		if( item.data.apiType === "" ) {
			alert( "API 타입을 선택하세요" );
			return false;
		} 
		if( item.data.httpMethodType === "") {
			alert( "HttpMethod 타입을 선택하세요" );
			return false;
		} else {
			if( item.data.httpMethodType === "GET" && item.data.resultType === "INT" ) {
				alert( "HttpMethod 타입이 GET일 경우 응답결과가 [INT] 일 수 없습니다" );
				return false;
			}
		}
		if( item.data.resultType == "" ) {
			alert( "응답결과 타입을 선택하세요" );
			return false;
		} else {
			if( item.data.apiType === "WEB_SERVICE" ) {
				if( item.data.targetUrlAddr === "" ) {
					alert( "URL을 등록하세요" );
					return false;
				}
				if( item.data.resultType === "POST" || item.data.resultType === "DELETE" ) {
					alert( "웹서비스는 POST, DELETE 타입을 지원하지 않습니다" );
					return false;
				}
			} else {
				if( item.data.dataSourceName === "" ) {
					alert( "데이터소스를 선택하세요" );
					return false;
				}
				if( item.data.apiType === "PROCEDURE" ) {
					if( item.data.targetName === "" ) {
						alert( "Procedure를 등록하세요" );
						return false;
					}
				} else { // SQL, MYBATIS, SQL_GROOVY_TEMPLATE, VELOCITY
					if( item.data.queryMsg === "" ) {
						alert( "SQL을 등록하세요" );
						return false;
					}
				}
			}
		}
		if( item.data.resultType === "" ) {
			alert( "응답결과 타입을 선택하세요" );
			return false;
		}
		return true;
	};
	/**
	 * 도움말 보여주기
	 */
	var showHelp = function(e){
		var helpContents = $helpContents.html();
		$( "<div></div>" ).html(  helpContents  ).kendoWindow({
			width : 800,
			height : 600,
			title : "api타입 도움말",
			close : function() {
				this.destroy();
			}
		}).data("kendoWindow").open().center();
	}
};
var rowNum = 0;
function resetRowNum(){
	rowNum = 0;
}
function getNum(){
	return ++rowNum;
}