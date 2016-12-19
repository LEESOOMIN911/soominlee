/**
 * admin/category
 */
var mcare_admin_category = function(){
	//상속
	mcare_admin.call(this);
	
	var self = this;
	//변수
	var $wrapper = $(".main-wrapper #wrapper"),
		$addItem = $("#add-item"),
		$saveItem = $("#save-item"),
		$removeItem = $("#remove-item"),
		$deselectItem = $("#deselect-item"),
		$treeView = $("#tree-view"),
		$crudServiceBaseUrl = contextPath + "/admin/category";
	//tree
	this.treeView = null;
	/**
	 * 초기화
	 */
	this.init = function(){
		
		$wrapper.kendoSplitter({
			panes : [ {
				collapsible : true,
				resizable : true,
				size : '240px'
			}, {
				collapsible : false
			} ]
		});
		
		initTree();
		addEvent();
	};
	/**
	 * 이벤트 등록
	 */
	var addEvent = function(){
		//아이템 추가
		$addItem.on("click",function(e){
			addItem(e);
		});
		//아이템 저장
		$saveItem.on("click",function(e){
			saveItem(e);
		});
		//아이템 삭제
		$removeItem.on("click",function(e){
			removeItem(e);
		});
		//아이템 선택 해제
		$deselectItem.on("click",function(e){
			self.treeView.select( null );
		});
	};
	/**
	 * 아이템 추가 이벤트
	 */
	var addItem = function(e){
		var treeView = self.treeView;
		
		addedNodeCheck( e, treeView.dataSource.data().toJSON() );
		
		if( addedNodeArr.length > 0 ) {
			alert( "저장하지 않은 아이템 [ " + addedNodeArr[0].catName + " ] (이)가 있습니다" );
			addedNodeArr = [];
			return false;
		}

		var parentCatSeq = "",
			node = treeView.select();
		
		if( node.length === 0 ) {
			node = null;
		} else {
			var parentData = treeView.dataItem( node );
			parentCatSeq = parentData.catSeq;
		}

		treeView.append({
			catSeq : null,
			parentCatSeq : parentCatSeq,
			catName : 'NewItem',
			catDesc : null,
			childCount : 0,
			addedNode : true,
			pathName : ""
		}, node);
	};
	/**
	 * 아이템 저장 이벤트
	 */
	var saveItem = function(e){
		var treeView = self.treeView;
		
		var node = treeView.select(),
			item = treeView.dataItem( node );

		if( node.length === 0 ) {
			alert( "선택한 아이템이 없습니다" );
			kendo.ui.progress( $wrapper, false );
			return false;
		}
//		if( item.childCount > 0 ) {
//			alert( "하위 카테고리가 존재하여 수정할 수 없습니다" );
//			return false;
//		}
//
//		if( item.apiCount > 0 ) {
//			alert( "하위 API가 존재하여 수정할 수 없습니다" );
//			return false;
//		}
		if( item.pathName === null || item.pathName === "" ) {
			alert( "카테고리 경로는 필수 항목입니다" );
			kendo.ui.progress($wrapper, false);
			return false;
		}

		var regExp = /^[a-z0-9_-]{2,16}$/;
		if( !regExp.test( item.pathName ) ) {
			alert( "카테고리 URI는 소문자나 숫자, 언더스코어, 하이픈을 포함할 수 있고 2byte이상 15byte이하만 가능합니다" );
			kendo.ui.progress($wrapper, false);
			return false;
		}

		kendo.ui.progress($wrapper, true);
		
//		$.ajax({
//			url : $crudServiceBaseUrl + "/save.json",
//			type : "POST",
//			data : self.util.stringifyJson( item ),
//			dataType : "json",
//			contentType : "application/json;charset=utf-8",
//			success : function(data){
//				if( data.msg !== undefined ) {
//					alert( "수행 중 에러가 발생하였습니다" );
//					console.log( data.msg );
//					return false;
//				}
//				alert("저장 되었습니다");
//				if (item.addedNode === true) { // 신규 노드만 리로딩
//					location.reload(true);
//				}
//			},
//			error :  function(xhr,d,t){
//				console.log(xhr);
//				console.log(xhr.responseText);
//			},
//    		complete : function(){
//    			kendo.ui.progress( $wrapper, false );
//    		}
//		});
		var opt = {
				url : $crudServiceBaseUrl + "/save.json",
				type : "POST",
				data : self.util.stringifyJson( item ),
				dataType : "json",
				contentType : "application/json;charset=utf-8",
        		complete : function(){
        			kendo.ui.progress( $wrapper, false );
        		}
    	};
    	var success = function(data){
    		alert("저장 되었습니다");
			if (item.addedNode === true) { // 신규 노드만 리로딩
				location.reload(true);
			}
    	};
    	var error = function(xhr,d,t){
    		console.log(xhr);
			console.log(xhr.responseText);
    	};
    	self.ajaxAdmin( opt, success, error );
	};
	/**
	 * 아이템 삭제 이벤트
	 */
	var removeItem = function(e){
		var treeView = self.treeView;
		
		kendo.unbind($wrapper);

		var node = treeView.select(),
			item = treeView.dataItem(node);
		
		if( node.length === 0 ) {
			alert( "선택한 아이템이 없습니다" );
			return false;
		}

		if( item.addedNode === true ) {
			treeView.remove(node);
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

		kendo.ui.progress($wrapper, true);

//		$.ajax({
//			url : $crudServiceBaseUrl + "/remove.json",
//			type : "POST",
//			data : { catSeq : item.catSeq },
//			//dataType : "json",
//			//contentType : "application/json;charset=utf-8",
//			success : function(data){
//				if( data.msg !== undefined ) {
//					alert( "수행 중 에러가 발생하였습니다" );
//					console.log( data.msg );
//					return false;
//				}
//				alert("삭제 되었습니다");
//				location.reload(true);
//			},
//			error :  function(xhr,d,t){
//				console.log(xhr);
//				console.log(xhr.responseText);
//			},
//    		complete : function(){
//    			kendo.ui.progress( $wrapper, false );
//    		}
//		});
		var opt = {
				url : $crudServiceBaseUrl + "/remove.json",
				type : "POST",
				data : { catSeq : item.catSeq },
        		complete : function(){
        			kendo.ui.progress( $wrapper, false );
        		}
    	};
    	var success = function(data){
    		alert("삭제 되었습니다");
			location.reload(true);
    	};
    	var error = function(xhr,d,t){
    		console.log(xhr);
			console.log(xhr.responseText);
    	};
    	self.ajaxAdmin( opt, success, error );
	};
	/**
	 * 트리 초기화
	 */
	var initTree = function(){
		var treeDataSource = new kendo.data.HierarchicalDataSource({
			transport : {
				read : {
					url : $crudServiceBaseUrl + "/getList.json",
					method : "post",
					dataType : "json"
				}
			},
			schema : {
				model : {
					id : "catSeq",
					hasChildren : function() {
						return (this.get("childCount") > 0);
					}
				}
			}
		});
		var option = {
				loadOnDemand : false,
				dataSource : treeDataSource,
				dataTextField : "catName",
				template : treeTemplate,
				select : treeSelect
		};
		
		// 트리
		self.treeView = self.tree( $treeView, option );

		
		function treeTemplate(o){
			return '<span class="k-sprite folder"></span>' + o.item.catName;
		}
		
		function treeSelect(o){
			kendo.unbind($wrapper);
			var totalPath = getTotalPath(o.node);
			
			var node = $.extend( this.dataItem(o.node), {
				parentPath : totalPath
			});
			var observer = new kendo.observable(node);
			observer.getCreateDate = function() {
				if (this.createDt)
					return new Date(this.createDt).toLocaleString();
				return "";
			};
			observer.getUpdateDate = function() {
				if (this.updateDt)
					return new Date(this.updateDt).toLocaleString();
				return "";
			};
			kendo.bind( $wrapper, observer );
			
			var treeView = self.treeView;
			var item = treeView.dataItem(o.node);
			//하위 카테고리, api 있으면 readonly - 수정할 수 없도록
			if( item.childCount > 0 || item.apiCount > 0 ) {
//				$("#catName").prop("readonly",true);
				$("#pathName").prop("readonly",true);
//				$("#catDesc").prop("readonly",true);
			} else {
//				$("#catName").prop("readonly",false);
				$("#pathName").prop("readonly",false);
//				$("#catDesc").prop("readonly",false);
			}
		}
	};
	// 저장하지 않은 아이템 확인
	var addedNodeArr = [];
	function addedNodeCheck( e, treeViewData ) {
		if( treeViewData ) {
			for( var n = 0; n < treeViewData.length; n++) {
				 var childData = treeViewData[n];
				 if( childData.addedNode === true ) {
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
	 *  선택된 노드의 부모경로 가져오기
	 */
	var getTotalPath = function(node){
		var treeView = self.treeView,
			totalPathName = "",
			//현재 선택된 노드 모델 정보
			item = treeView.dataItem( node );

		var cat_seq = item.catSeq;
		var cat_name = item.catName;
		var cat_path_name = "";
		
		cat_path_name = "";
		//부모있으면
		while ( item.parentNode() ) {
			//있을 때까지 부모로 설정해서 경로를 만듬
			item = item.parentNode();
			cat_path_name = item.pathName + "/" + cat_path_name;
		}
		//부모경로가 없으면 '/' 있으면 '/ + 부모경로'
		return totalPathName = (cat_path_name !== ""?"/" + cat_path_name : "/");
	};
};
