/**
 * mcare_mobile_pushToken
 */
var mcare_mobile_pushToken = function(){
	//상속
	mcare_mobile.call(this);
	//super
	var self = this;
	//변수
	var $targetContainer = $(".targetContainer"),
		$lhContainer = $(".lhContainer");

	var deviceToken = "";
	/**
	 * 초기화
	 */
	this.init = function(){
		initDeviceToken();
		initDeviceHistory();
		addEvent();
	};
	/**
	 * 이벤트 등록
	 */
	var addEvent = function(){

	};
	/**
	 * device token 설정
	 */
	var initDeviceToken = function(){
		var msg = {
				type : "command", 
				functionType : "token", 
				value : {
					callbackFn : "window.activeObj.getDeviceTokenCallBack"
				}
		}; 
		self.toNative( msg );
	};
	/**
	 * 
	 */
	this.getDeviceTokenCallBack = function(token){
		deviceToken = token; 	
		initList();
	};
	/**
	 * 리스트 가져오기
	 */
	var initList = function(){

		$.ajax({
			url : contextPath + "/mobile/pushToken/getList.json",
			type : "POST",
			data : self.util.stringifyJson({"deviceTokenId" : deviceToken}),
			contentType:"application/json",
			success : function( data ){
				try{
					if( data.msg !== undefined ){
						self.alert( data.msg );
						return false;
					} else if( data.extraMsg !== undefined ){
						self.alert( data.extraMsg );
						if( data.result !== undefined ){
							displayTokenList( data.result );
						}
						return;
					} else {
						displayTokenList( data.result );
					}
					
				} catch(e) {
					
				}
			},
			error : function( xhr, d, t ){
				self.loading("hide");
				console.log( xhr );
			},
			complete : function(){

			}
		});
	};
	/**
	 * push token 리스트 표시
	 */
	var displayTokenList = function(data){
		if( data === undefined || data === null || data.length === 0 || $.isEmptyObject(data) ){
			var bodyDiv = $("<div></div>");
			 self.alert( self.getI18n( "pushToken004" ),function(){
				 $("#headerArrowLeft_btn")[0].click();
			 });			 
			return;
		}else{	
			try{				
				displayTarget(data);
				self.loading("hide");
			} catch(e){
				self.loading("hide");
			}
		};

	};
	/**
	 * push token 관리 대상
	 */
	var displayTarget = function(data){
		$targetContainer.html("");
		
		for( var i = 0; i < data.length; i++ ){
			 var item = data[i],
				 div = $("<div></div>"),
				 icon = $("<i></i>"),
				 span = $("<span></span>"),
				 a = $("<a></a>");
			var msgBox = div.clone().addClass("msgBox");
			
			var receiverId = div.clone().addClass("item infotxt").html( 
					span.clone().addClass("code").html( icon.clone().addClass("fa font14 fa-user") )
					.append( "&nbsp;" + self.getI18n("pushToken001")+"&nbsp;&#58;&nbsp;")
				).append( 
						span.clone().addClass("for_receiver code_txt").html( item["receiverId"] ) 
				);
			
			
			msgBox.html( receiverId );
			
			if( item["currentUser"] === "Y" ){
				var current = div.clone().addClass("item infotxt").html( 
						span.clone().addClass("code").html( icon.clone().addClass("fa font14 fa-comment-o") )
						.append( "&nbsp;" + self.getI18n("pushToken006")+"&nbsp;&#58;&nbsp;")
					).append( 
							span.clone().addClass("for_current code_txt").html( self.getI18n("pushToken007") ) 
					);
				msgBox.append( current );
			} else {
				var btn = div.clone().addClass("item").html(
						a.clone().addClass("ui-btn ui-btn-b deleteBtn").attr(
								{"data-token":item["deviceTokenId"],"data-platformType":item["platformType"],"data-receiverId":item["receiverId"]}
						).html(self.getI18n("pushToken002")) 
					); 
				msgBox.append( btn );
			}
			
			$targetContainer.append( msgBox );
			
		}
		//수신중단 버튼 이벤트
		var btnCallback = function(e){
			$(this).addClass("active");
			var tokenId = $(this).attr("data-token"),
				type = $(this).attr("data-platformType"),
				receiverId = $(this).attr("data-receiverId");
			var options = {
					content : self.getI18n("pushToken003"),
					callback : function(e){
						deleteToken(tokenId, type, receiverId);
					}
			};
			self.popup(options);
			$(this).removeClass("active");
		};
		self.event.addEvent( $(".deleteBtn"), "click", btnCallback );

	};
	
	/**
	 * 수신중단 - push token 삭제
	 */
	var deleteToken = function(tokenId, type, receiverId){			
		self.loading("show");
		
		var param = {
				"deviceTokenId": tokenId,
				"platformType" : type,
				"receiverId" : receiverId
		};
		$.ajax({
			url : contextPath + "/mobile/pushToken/remove.json",
			type : "POST",
			data : self.util.stringifyJson(param),
			contentType:"application/json",
			success : function( data ){
				try{
					if( data.msg !== undefined ){
						self.alert( data.msg );
						return false;
					} else if( data.extraMsg !== undefined ){
						self.alert( data.extraMsg );
						return;
					} else {
						self.alert( self.getI18n("pushToken005") ,function(){						
							//대상 다시 조회
							initList();
						});
					}
					
				} catch(e) {
					
				}
			},
			error : function( xhr, d, t ){
				console.log( xhr );
			},
			complete : function(){
				$(".deleteBtn").removeClass("active");
				self.loading("hide");
			}
		});
	};
	/**
	 * 사용자의 단말기 로그인 이력 
	 */
	var initDeviceHistory = function(){
		$.ajax({
			url : contextPath + "/mobile/pushToken/getUserTokenList.json",
			type : "POST",
			data : self.util.stringifyJson({}),
			contentType:"application/json",
			success : function( data ){
				try{
					if( data.msg !== undefined ){
						self.alert( data.msg );
						return false;
					} else if( data.extraMsg !== undefined ){
						self.alert( data.extraMsg );
						if( data.result !== undefined ){
							displayDeviceHistory( data.result );
						}
						return;
					} else {
						displayDeviceHistory( data.result );
					}
					
				} catch(e) {
					
				}
			},
			error : function( xhr, d, t ){
				console.log( xhr );
			},
			complete : function(){

			}
		});
	};
	/**
	 * 단말기 로그인 이력 표시 
	 */
	var displayDeviceHistory = function(data){
		$lhContainer.html("");
		//값이 없을 수는 없다. 지금도 로그인 했으므로, 적어도 1개는 있어야만 하는 데이터 
		if( data.length > 0 ){
			var msgBox = $("<div></div>").addClass("msgBox");
			for( var i = 0; i < data.length; i++ ){
				 var item = data[i],
				 	 div = $("<div></div>"),
				 	 icon = $("<i></i>"),
				 	 span = $("<span></span>"),
				 	 a = $("<a></a>");
			
				 var device = div.clone().addClass("item infotxt").html( 
						 span.clone().addClass("code").html( icon.clone().addClass("fa font14 fa-mobile") )
						 .append( "&nbsp;" + (item["platformType"]=="A"?"Android":"iPhone") +"&nbsp;&#58;&nbsp;")
				 ).append( 
						 span.clone().addClass("for_receiver code_txt").html( item["updateDt"] ) 
				 );
				
				 msgBox.append( device );
			}
			$lhContainer.append( msgBox );
		} 
	};
};