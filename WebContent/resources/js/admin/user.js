/**
 * admin/user
 */
var mcare_admin_user = function(){
	//상속
	mcare_admin.call(this);
	
	var self = this;
	//변수
	var $grid = $("#grid"),
		$userDelBtn = $(".userDelBtn"),
		$newUserBtn = $(".newUserBtn"),
		$newForm = $("#newForm"),
		$newPId = $("#newPId"),
		$modalClose = $(".modalClose"),
		$modalSubmit = $(".modalSubmit"),
		$checkUserPId = $(".checkUserPId"),
		$crudServiceBaseUrl = contextPath + "/admin/user";
	
	var newUserCheck = false;
	
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
		$userDelBtn.on("click",function(e){
			e.preventDefault();
			var num = function(){
				 var size = 0, key;
				 for( key in checkedIds ) {
				      if (checkedIds.hasOwnProperty(key)) size++;
				 }
				 return size;
			};
			if( num() === 0 ){
				alert("탈퇴처리할 사용자를 선택하세요");
				return;
			}
			if( confirm( num() + "명의 사용자를 탈퇴처리 하시겠습니까?") ){				
				userDelete(e);
			} else {
				return;
			}
		});
		/**
		 * 사용등록 버튼 이벤트 
		 */
		$newUserBtn.on("click",function(e){
			e.preventDefault();
			$newForm.kendoWindow({
				width : 400,
				height : 100,
				title : "사용등록",
				modal:true,
				close : function() {
					$newPId.val("");
				}
			}).data("kendoWindow").open().center();
		});
		/**
		 * 사용등록 등록 폼 취소 버튼 이벤트
		 */
		$modalClose.on("click",function(e){
			e.preventDefault();
			$newForm.data("kendoWindow").close();
			newUserCheck = false;
		});
		/**
		 * 사용등록 환자번호 확인 버튼 클릭 이벤트
		 */
		$checkUserPId.on("click",function(e){
			e.preventDefault();
			if( validateNewUser() ){
				checkNewUser();
			}
		});
		/**
		 * 사용등록 등록 폼 저장 버튼 이벤트 
		 */
		$modalSubmit.on("click",function(e){
			e.preventDefault();
			if( validateNewUser() ){
				if( !newUserCheck ){
					alert("등록할 환자번호 가입여부를 확인하세요.");
					return false;
				} else {				
					sendNewUser();
					$newForm.data("kendoWindow").close();
				}
			}
		});
	};
	/**
	 * 사용등록 유효성 검사 
	 */
	var validateNewUser = function(){
		var pId = $newPId.val();
		if( pId === "" ){
			alert("등록할 환자번호를 입력하세요.");
			return false;
		}
		return true;
	};
	/**
	 * 사용자 등록 가능여부 체크 
	 */
	var checkNewUser = function(){
		var pId = $newPId.val();
		var option = {
    			url: $crudServiceBaseUrl + "/checkUserPid.json",
				method: "POST",
				dataType: "JSON",
				data : self.util.stringifyJson( { "pId":pId } )
    	};
    	//성공
    	var sFn = function(data){
			if( data.msg !== undefined ){
				alert( data.msg );
				newUserCheck = false;
			} else {
				//성공하고 결과
				if( data.result["success"] !== undefined ){
					alert( "가입가능합니다." );
					newUserCheck = true;
				//실패 결과
				} else {
					alert( data.result.msg );
					newUserCheck = false;
				}
			}
		};
		//에러
		var eFn = function(e,xhr,t){
			newUserCheck = false;
			console.log("error");
			console.log(e);
			console.log(xhr);
		};
    	
    	self.ajaxAdmin( option, sFn, eFn );
	};
	/**
	 * 사용자 등록 요청 
	 */
	var sendNewUser = function(){
		var pId = $newPId.val();
		var option = {
    			url: $crudServiceBaseUrl + "/registerUser.json",
				method: "POST",
				dataType: "JSON",
				data : self.util.stringifyJson( { "pId":pId } )
    	};
    	//성공
    	var sFn = function(data){
			if( data.msg !== undefined ){
				alert( data.msg );
			} else {
				//성공하고 결과
				if( data.result !== undefined && data.result.success ){
					alert( "사용자가 등록되었습니다." );
					$grid.data("kendoGrid").dataSource.page(1); 
				//실패 결과
				} else if( data.result !== undefined && !data.result.success ){
					alert( "사용자등록에 실패하였습니다." );
				}
			}
		};
		//에러
		var eFn = function(e,xhr,t){
			console.log("error");
			console.log(e);
			console.log(xhr);
		};
    	
    	self.ajaxAdmin( option, sFn, eFn );
	};
	/**
	 * 
	 */
	var setDataSource = function(){		
		// 데이터소스	
		var dataSource = new kendo.data.DataSource({
			transport: {
				read:  {
					url: $crudServiceBaseUrl + "/getList.json",
					method: "post",
					dataType: "json",
					contentType: "application/json",
					complete:"gridReadComplete"
				},
				create: {
					url: $crudServiceBaseUrl + "/save.json",
					method: "post",
					dataType: "json",
					complete: gridActionComplete
				},
				update: {
					url: $crudServiceBaseUrl + "/clear.json",
					method: "post",
					dataType: "json",
					complete: gridActionComplete
				},
				destroy: {
					url: $crudServiceBaseUrl + "/remove.json",
					method: "post",
					dataType: "json",
					complete: gridActionComplete
				},
				parameterMap: function( options, operation ) {
					if( operation === "destroy" ){
						return self.util.stringifyJson( checkedIds );
					}else if( operation !== "destroy" && operation !== "read" && options.models ) {
						return self.util.stringifyJson( options.models[0] );
					} else if( operation === "read" ){
						return self.util.stringifyJson( options );
					}
				}
			},
			batch: true,
			pageSize: 15,
			serverPaging : true,
			serverSorting: true,
			schema: {	
				data : function(result){
					var $withdrawalDate = parseFloat( $("#withdrawalDate").val() );
					if( result.msg !== undefined ){
						alert(result.msg);
						return [];
					} else if( typeof result === "object" && result.data === undefined ){
						var cnt = result.lastAccessDay;
			        	result["withdrawalYn"] = (cnt > $withdrawalDate ? "Y" : "N");
						return result;
					}else {						
						var resultList = result.data;
						
						for( var i = 0; i < resultList.length; i++ ){
							var item = resultList[i];
							var cnt = item.lastAccessDay;
				        	item["withdrawalYn"] = (cnt > $withdrawalDate ? "Y" : "N");
						}
						return resultList;
					}
					
				},
				total : "totalCount",
					model: {
						id: "rnum",
						fields: {
							rnum: { type: "number", editable: false},
							pId: { type: "string", editable: false},
							loginFailCnt : { type:"number"},
							accessDt : {type:"number",editable:false},
							lastAccessDay : {type:"number",editable:false},
							registerDt : {type:"number",editable:false},
							registerDay : {type:"number",editable:false},
							withdrawalYn : {type:"string",editable:false}
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
	var checkedIds = {};
    var initGrid = function(){
    	// 그리드 옵션
        var option = {
            dataSource: dataSource,
            pageable: true,
            sortable: true,
            resizable: true,
            height: 580,
            columns: [
                 { field: "check",title:"",sortable:false, filterable:false, template: "<input type='checkbox' class='checkbox' />" , width: 20, attributes: {style: "text-align: center;"}}
                ,{ field: "rnum", title: "",hidden:true, width: 50, attributes: {style: "text-align: center;"}}      
                ,{ field: "pId", title: "환자번호", width: 100, attributes: {style: "text-align: center;"}}
                ,{ field: "loginFailCnt",filterable:false, title: "로그인실패횟수", width: 50, attributes: {style: "text-align: center;"}}
                ,{ field: "accessDt", title: "최근 접속일",filterable:false, width: 80, attributes: {style: "text-align: center;"},
                   template:function(dataItem) {
                		return dateTemplate(dataItem.accessDt);
                	}
                 }
                ,{ field: "lastAccessDay", title: "비접속기간",filterable:false,sortable:true, width: 80, attributes: {style: "text-align: center;"}}
                ,{ field: "registerDt",filterable:false, title: "가입일자", width: 100, attributes: {style: "text-align: center;"},
                   template:function(dataItem) {
                 		return dateTemplate(dataItem.registerDt);
                 	}
                 }
                ,{ field: "registerDay", title: "가입기간",filterable:false,sortable:true, width: 80, attributes: {style: "text-align: center;"}}
                ,{ field: "withdrawalYn", title: "탈퇴대상여부",sortable:false, width: 50, attributes: {style: "text-align: center;"}}
//                ,{ command: [/*{name:"edit",text:"Block 해제"},*/{name:"destroy",text:"탈퇴처리"}], title: "&nbsp;", width: 100, attributes: {style: "text-align: center;"}}
            ],
    		filterable:  {
    			extra : false, 
    			operators : {
    				string : {
    					contains : " 포함 "
    				}
    			}
    		}, 
            editable: {
            	mode:"popup",
            	confirmation:"정말 삭제하시겠습니까?"
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
                //선택된 row 정보 binding
                var view = this.dataSource.view();
                for(var i = 0; i < view.length;i++){
                    if(checkedIds[view[i].id]){
                        this.tbody.find("tr[data-uid='" + view[i].uid + "']")
                        .find(".checkbox")
                        .attr("checked","checked");
                    }
                }
                //전체 선택 부분 체크박스 생성 및 이벤트
                var $check = $grid.find( "th.k-header[data-field=check]" ),
                	allCheck = $("<input></input>").attr({"type":"checkbox","id":"allCheck"});
                
                $check.html(allCheck);
                var callback = function(e){
                	var $check = $("input.checkbox");
                	var grid = $grid.data("kendoGrid");
                	//전체 선택
                	if( this.checked ){
                		$check.prop("checked",true);
                		
                		$.each( $check, function(idx){
                			var row = $($check[idx]).closest("tr"),
                				dataItem = grid.dataItem(row);
                			checkedIds[dataItem.id] = dataItem.pId;
                		});
                	//전체 선택 해제	
                	} else {                		
                		$check.prop("checked",false);
                		$.each( $check, function(idx){
                			var row = $($check[idx]).closest("tr"),
                				dataItem = grid.dataItem(row);
                			delete checkedIds[dataItem.id];
                		});
                	}
                	
                };
                self.event.addEvent( allCheck, "change", callback );
                //row 선택 이벤트
                var selectRow = function(e){
                	var checked = this.checked,
                    	row = $(this).closest("tr"),
                    	grid = $grid.data("kendoGrid"),
                    	dataItem = grid.dataItem(row);

                    checkedIds[dataItem.id] = dataItem.pId;
                    //선택되면 강조색
//                    if (checked) {
//                        //-select the row
//                        row.addClass("k-state-selected");
//                        } else {
//                        //-remove selection
//                        row.removeClass("k-state-selected");
//                    }
                };
                self.event.addEvent( $("input.checkbox"), "click", selectRow );
            }
//            ,
//            edit: function(e){
//            	e.model.dirty = true;
//            	e.container.children().find("[class*=edit]").hide();
//            	$(e.container.children().children()[0]).before("<div><p>Block을 해제하시겠습니까?</p></div>");
//            }
        };

        function dateTemplate(e){
        	return e!==null?kendo.toString( new Date(e), 'yyyy-MM-dd HH:mm:ss') : "";
        }
        //그리드 초기화
        self.grid( $grid, option );
    };
    /**
     * 사용자 삭제
     */
    var userDelete = function(e){
    	var option = {
    			url: $crudServiceBaseUrl + "/remove.json",
				method: "POST",
				dataType: "JSON",
				data : self.util.stringifyJson( checkedIds )
    	};
    	//성공
    	var sFn = function(data){
			if( data.msg !== undefined ){
				alert( data.msg );
			} else {
				//성공하고 결과
				if( data.result !== undefined && data.result.type === "success" ){
					alert( data.result.msg );
					checkedIds= {};
					$grid.data("kendoGrid").dataSource.page(1); 
				//실패 결과
				} else if( data.result !== undefined && data.result.type === "fail" ){
					alert( data.result.msg );
				}
			}
		};
		//에러
		var eFn = function(e,xhr,t){
			console.log("error");
			console.log(e);
			console.log(xhr);
		};
    	
    	self.ajaxAdmin( option, sFn, eFn );
    };
};