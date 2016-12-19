/**
 * admin/menu
 */
var mcare_admin_menu = function( language_code ){
	//상속
	mcare_admin.call(this);
	
	var self = this;
	//언어코드
	var DEFAULT_LANGUAGE = language_code;
	//변수
	var $wrapper = $(".main-wrapper #wrapper"),
		$addItem = $("#add-item"),
		$saveItem = $("#save-item"),
		$reloadItem = $("#reload-item"),
		$removeItem = $("#remove-item"),
		$deselectItem = $("#deselect-item"),
		$treeView = $("#tree-view"),
		$i18nGrid = $("#i18ns"),
		$menuOrder = $("#menuOrder"),
		$menuType = $("#menuType"),
		$reqParams = $("#reqParams"),
		$menuDesc = $("#menuDesc"),
		$crudServiceBaseUrl = contextPath + "/admin/menu";
		
	//트리
	this.treeView;
	this.i18nGrid;
	this.reqParamsGrid = null;
	/**
	 * 객체 초기화
	 */
	this.init = function(){
		initSplitter();
		initTree();
		initGrid();
		initNumBox();
		initDropDownList();
		//설명 글자수 이벤트 - core에 선언
		self.checkDescBytes($menuDesc);
		addEvent();
	};
	/**
	 * 이벤트 등록
	 */
	var addEvent = function(){
		// 아이템 생성
		$addItem.on("click", function(e) {
	        addItem(e);
	    });	    
	    // 아이템 저장
		$saveItem.on("click",function(e) {
	    	saveItem(e);
	    });	    
	 	// 아이템 갱신 (to 모바일 서버)
	    $reloadItem.on("click",function(e) {
	    	reloadItem(e);
	    }); 
	 	// 아이템 삭제
		$removeItem.on("click",function(e) {
			removeItem(e);
	    }); 
		// 아이템 선택 해제
	    $deselectItem.on("click",function(e) {
	    	deselectItem(e);
	    });
	};
	var initSplitter = function(){

		$wrapper.kendoSplitter({
	        panes: [
	            { collapsible: true, resizable: true, size: '280px' },
	            { collapsible: false }
	        ]
	    });
		
		$wrapper.data("kendoSplitter").trigger("resize");
	};
	/**
	 * 트리 아이템 신규 이벤트
	 * @private
	 */
	var addItem = function(e){
		var treeView = self.treeView;
		
		addedNodeCheck(e, treeView.dataSource.data().toJSON());
		
        if (addedNodeArr.length > 0) {
        	alert("저장하지 않은 아이템 [ " + addedNodeArr[0].menuName + " ] (이)가 있습니다");
        	addedNodeArr = [];
        	return false;
        }
        
    	var node = treeView.select(),
    		item = treeView.dataItem(node);
    	
    	var nodeData = null,
    		defData = {
			menuType: "CONT",
 			menuName: "NewItem",
 			menuOrder: 0,
 			enabledYn: "Y",
 			authYn: "Y",
 			authViewYn: "N",
 			aggYn: "N",
 			accessUriAddr: "",
 			imageUriAddr: "",
 			menuDesc: "",
 			childCount: 0,
 			i18ns: [],
 			addedNode: true,
 			selected: false,
 			menuParam : []
  		};
        
     	if( node.length === 0 ) {
 			node = null;
 			
 			nodeData = $.extend(true, {}, {
				menuType: 'MAIN'
			}, defData);
 			
         } else {
     		if( item.childCount === 0 ) {
     			item.loaded(true);
            }
     		
     		nodeData = $.extend( true, {}, item.toJSON(), {
     			menuId: null,
     			parentMenuId: item.menuId
 			}, defData);
     	}
     	
     	treeView.append(nodeData, node);
	};
	// 다국어 코드 중복 체크
	var i18nArr = [];
	/**
	 * 트리 아이템 저장 이벤트
	 * @private
	 */
	var saveItem = function(e){
		var treeView = self.treeView;
		
		var node = treeView.select(),
			item = treeView.dataItem(node);
    	
    	if( node.length === 0 ) {
    		alert("선택한 아이템이 없습니다");
    		return false;
    	}
    	
    	if( item.menuId === null || item.menuId === "" ) {
    		alert( "아이디가 누락되었습니다" );
    		return false;
    	}
    	
    	var pnode = item.parentNode();
    	
    	if( pnode ) {
	    	if( pnode.menuType === "CONT" && item.menuType !== "CONT" ) {
	    		alert( "콘텐츠 하위에는 콘텐츠만 등록할 수 있습니다" );
	    		return false;
	    	}
	    	
	    	if( item.menuType === "SIDE" ){
	    		alert( "메뉴타입을 사이드바로 지정할 수 없습니다." );
	    		return false;
	    	}
    	}
    	var param = $.extend(true, {}, item, {
    		i18ns: $i18nGrid.data("kendoGrid").dataSource.data(),
    		menuParam : $reqParams.data("kendoGrid").dataSource.data()
        }, null);
    	// 다국어 코드 중복 체크
    	
    	for( var i = 0; i < param.i18ns.length; i++ ) {
    		 i18nCd = param.i18ns[i].i18nCd;
    		 if( $.inArray( i18nCd, i18nArr ) > -1 ) {
    			alert("다국어 코드는 중복될 수 없습니다");
	    		return false;
    		} else {
	    		i18nArr.push( i18nCd );
    		}
    	}
    	param.accessUriAddr = $("input#accessUriAddr.k-textbox").val();
    	
    	if( param.accessUriAddr.indexOf("javascript:") > -1 && param.aggYn === "Y" ){
    		alert( "스크립트 메뉴는 통계여부를 '아니오'로 설정해주세요.")
    		return false;
    	}
    	kendo.ui.progress( $wrapper, true );
    			
//		$.ajax({
//			url : $crudServiceBaseUrl + "/save.json",
//			method : "POST",
//			data : self.util.stringifyJson(param),
//			dataType : "json",
//			contentType: "application/json; charset=UTF-8",
//			success :  function(data){
//				if( data.msg !== undefined ) {
//					alert("수행 중 에러가 발생하였습니다");
//					console.log(data.msg);
//					return false;
//				}
//	    		alert("저장 되었습니다");
//	    		location.reload();
//			},
//			error : function(xhr,d,t){
//				console.log(xhr);
//				console.log(t);
//			},
//    		complete : function(){
//    			kendo.ui.progress( $wrapper, false );
//    		}
//		});
		var opt = {
				url : $crudServiceBaseUrl + "/save.json",
				method : "POST",
				data : self.util.stringifyJson(param),
				dataType : "json",
				contentType: "application/json; charset=UTF-8",
        		complete : function(){
        			kendo.ui.progress( $wrapper, false );
        		}
    	};
    	var success = function(data){
    		alert("저장 되었습니다");
    		if (item.addedNode === true) { // 신규 노드만 리로딩
				location.reload(true);
			}
    		self.i18nGrid.refresh();
    		self.reqParamsGrid.refresh();
    		//다국어 중복 체크 다시 리셋
    		i18nArr = [];
    		//location.reload(true);
    	};
    	var error = function(xhr,d,t){
    		console.log(xhr);
			console.log(t);
    	};
    	self.ajaxAdmin( opt, success, error );
	};
	/**
	 * 트리 아이템 reload 이벤트
	 * @private
	 */
	var reloadItem = function(e){
		kendo.ui.progress( $wrapper, true );
		
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
	 * 트리 아이템 삭제 이벤트
	 * @private
	 */
	var removeItem = function(e){
		var treeView = self.treeView;
		
		var node = treeView.select(),
			item = treeView.dataItem(node);
		
    	if( node.length === 0 ) {
            alert( "선택한 아이템이 없습니다." );
            return false;
        }

    	kendo.unbind( $wrapper );
    	
		if( item.addedNode === true ) {
        	treeView.remove(node);
        } else {
		
			if( item.childCount > 0 ) {
	    		alert( "하위 아이템이 존재하여 삭제할 수 없습니다." );
	    		return false;
	        }
			
			if( !confirm( "삭제 하시겠습니까?" ) ) {
	    		return false;
	    	}
			
			kendo.ui.progress( $wrapper, true );
			
//			$.ajax({
//				url : $crudServiceBaseUrl + "/remove.json",
//				type : "POST",
//				data : { menuId : item.menuId },
//				dataType : "json",
//				success :  function(data){
//					if( data.msg !== undefined ) {
//						alert( "수행 중 에러가 발생하였습니다" );
//						console.log( data.msg );
//						return false;
//					}
//		    		alert( "삭제 되었습니다" );
//		    		location.reload();
//				},
//				error : function(xhr,d,t){
//					console.log(xhr);
//					console.log(t);
//				},
//	    		complete : function(){
//	    			kendo.ui.progress( $wrapper, false );
//	    		}
//			});
			var opt = {
					url : $crudServiceBaseUrl + "/remove.json",
					type : "POST",
					data : { menuId : item.menuId },
					dataType : "json",
	        		complete : function(){
	        			kendo.ui.progress( $wrapper, false );
	        		}
	    	};
	    	var success = function(data){
	    		alert( "삭제 되었습니다" );
	    		location.reload(true);
	    	};
	    	var error = function(xhr,d,t){
	    		console.log(xhr);
				console.log(t);
	    	};
	    	self.ajaxAdmin( opt, success, error );
        }
	};
	/**
	 * 트리 아이템 선택 해제 이벤트
	 * @private
	 */
	var deselectItem = function(e){
		self.treeView.select( null );
	};
	
	var initNumBox = function(){
		$menuOrder.kendoNumericTextBox({
			format: '0',
			min: 0,
			max: 1000,
			step: 1
		});
	};
	var initDropDownList = function(){
		self.dropDownList( $menuType, {
			change:function(e){
				//네비게이션이면
				if( e.sender.value() === "NAVI" ){
					$("input#accessUriAddr.k-textbox").val("/navigation.page");
				} else {
					var text = $("input#accessUriAddr.k-textbox").val();
					text = (text!==""&& text !=="/navigation.page")?text:"";
					$("input#accessUriAddr.k-textbox").val(text);
				}
			}
		} );
	};
	/**
	 * 데이터소스 설정
	 */
	// tree 데이터소스
    var treeDataSource = new kendo.data.HierarchicalDataSource({
        transport: {
            read: {
                url: $crudServiceBaseUrl + "/getList.json",
            	method : "post",
                dataType: "json"
            }
        },
        schema : {
            model : {
                id: "menuId",
                hasChildren: function(){
                	return (this.get("childCount") > 0);
                }
            }
        }
    });
    //grid 데이터 소스
    var gridDataSource = {
			transport: {
	            read: {
	                url: $crudServiceBaseUrl + "/getI18nList.json",
	                method: "get",
	                dataType: "json"
	            }
	        },
			schema: {
				data : function(d){
					if( d.msg !== undefined ){
						return [];
					} else {							
						return d;
					}
				},
				model: {
					id: "menuId",
					fields: {
						i18nCd: {type: "string", defaultValue : DEFAULT_LANGUAGE},
						cdText: {type: "string", editable : true}
					}
				}
			}
		};

	/**
	 * 트리 초기화
	 */
	var initTree = function(){	    
	    //kendo tree
		var option = {
		    	loadOnDemand: false,
		        dataSource : treeDataSource,
		        dataTextField : "menuName",
		        template : treeTemplate,
		        select : treeSelect,
		        expand : treeExpand
		};
	    self.treeView = self.tree( $treeView, option );

	    
	    /**
	     * tree template
	     * @private
	     * @description 트리 구성 
	     */
	    function treeTemplate(o){
        	var span = $("<span></span>").addClass("k-sprite");
        	
        	if( o.item.menuType === "NAVI" ){
        		span.addClass("folder");
        	} else if( o.item.menuType === "CONT" ){
        		span.addClass("html");
			} else {
				span.addClass("rootfolder");
			}
			
			return span[0].outerHTML + o.item.menuName;
	    };
	    /**
	     * tree select
	     * @private
	     * @description 트리 선택
	     */
	    function treeSelect(o){
	    	kendo.unbind( $wrapper );
        	
        	var item = this.dataItem( o.node );
        	
            var observer = new kendo.observable( item );
            observer.bind("change", function(o) {
            	
            });
            
            self.i18nGrid.dataSource.read({ "menuId" :  item.menuId });
            self.reqParamsGrid.dataSource.read({"menuId" : item.menuId} );
            kendo.bind( $wrapper, observer );
            if( observer.menuId !== undefined && observer.menuId !== null ){
            	$("#menuId").prop("disabled",true);
            } else {
            	$("#menuId").prop("disabled",false);
            }
           //설명 글자수제한 초기화
           $menuDesc.trigger("keyup");
	    };
	    /**
	     * tree expand
	     * @private
	     * @description 트리 펼치기
	     */
	    function treeExpand(o){
//	    	var sourceData = this.dataItem(o.node);
//            if( !sourceData.get("enabled") ) {
//                return o.preventDefault();
//            }
	    };
	};
	/**
	 * 그리드 초기화
	 */
	var initGrid = function(){
		var option = {
			dataSource : gridDataSource,	
			toolbar : [ {name: "create", text: "추가"} ],
			editable : { createAt : "bottom" },
			columns : [ 
				{field: "i18nCd", title: "다국어 코드", width: 100, editor: i18nDropDownEditor, template: "#=i18nCd#"},
				{field: "cdText", title: "코드 텍스트", width: 200, attributes: {style: "text-align: center;"}},
				{command: "destroy", title: '', width: 92, attributes: {style: "text-align: center;"}}
			]	
		};
		var reqParamOption =  {
			dataSource : {
				transport : {
					read : {
						url : $crudServiceBaseUrl + "/getParamList.json",
						method : "get",
						dataType : "json",
						contentType : "application/json"
					}
				},
				schema : {
					data : function(d){
						if( d.msg !== undefined ){
							return [];
						} else {							
							return d;
						}
					},
					model : {
						id : "menuParamSeq",
						fields : {
							menuParamName : {type : "string", validation : { required : true}},
							dataType : {type : "string",defaultValue : "STRING"},
							paramValue : {type : "string"},
							paramDesc : {type : "string"}
						}
					}
				}
			},
			toolbar : [{name: "create", text: "추가"}],
			editable : { createAt : "bottom"},
			columns : [
			           	{title:"no",template:"#= getNum()#",width:30,attributes: {style: "text-align: center;"}}
						,{field : "paramName", title : "이름", attributes: {style: "text-align: center;"}}
						,{field : "dataType", title : "타입", editor : reqParamTypeDropDownEditor, template: "#=dataType#"}
						,{field : "paramValue", title : "파라미터값", attributes: {style: "text-align: center;"}}
						,{field : "paramDesc", title : "설명", attributes: {style: "text-align: center;"}}
						,{command : "destroy", title : "", attributes: {style: "text-align: center;"}} 
			           ]
		};
		function i18nDropDownEditor(container, options) {
			$("[data-id='i18nDropDownEditor']:first").clone().attr("data-bind", "value:" + options.field).appendTo(container).kendoDropDownList({autoBind: false});
		}
		/**
		 * 파라미터 LOV
		 */
		function reqParamTypeDropDownEditor(container, options) {
			$("[data-id='reqParamTypeDropDownEditor']:first").clone().attr("data-bind", "value:" + options.field).appendTo(container).kendoDropDownList({autoBind: false});
		}
		self.i18nGrid = self.grid( $i18nGrid , option );
		self.reqParamsGrid = self.grid ( $reqParams, reqParamOption );
	};
	
	// 저장하지 않은 아이템 배열
    var addedNodeArr = [];
    /**
     * 트리 저장하지 않은 아이템 확인
     */
	var addedNodeCheck = function(e, treeViewData) {
    	if( treeViewData ) {
	    	for( var n = 0; n < treeViewData.length; n++ ) {
				 var childData = treeViewData[n];
	   			 if( childData.addedNode === true ){
	   				 addedNodeArr.push( childData );
	   				 return;
	   				
	 			 } else {
		   			var child = childData.items;
		   			if( child ) {
			   			if( child.length > 0 ){
			   				addedNodeCheck( e, child );
			   			}
		   			}
	 			}
	   		}
    	}
    };
}; 
var rowNum = 0;
function resetRowNum(){
	rowNum = 0;
}
function getNum(){
	return ++rowNum;
}