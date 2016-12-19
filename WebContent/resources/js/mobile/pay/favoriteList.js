/**
 * 카드 등록
 */
var mcare_mobile_favoriteList = function(){
	mcare_mobile.call(this);
	
	var self = this;
	
	var $registerBtn = $(".registerBtn"),
		$cardList = $(".cardList"),
		$pId = $("#pId"),
		$cipherKey = $("#cipherKey"),
		$maxCardNum = $("#maxCardNum");
		
	this.init = function(){
		initCardList();
		addEvent();
	};
	
	var addEvent = function(){
		$registerBtn.on("click",function(e){
			//버튼 클릭 배경
			$(this).addClass("active");
			registerCard();
		});
	};
	/**
	 * 새로운 카드 저장
	 */
	var registerCard = function(){
		self.changePage( contextPath + "/mobile/pay/favoriteForm.page?menuId=favoriteForm");
	};
	/**
	 * 유효성체크
	 */
	var initCardList = function( ){
		getCardList();
	}
	/**
	 * 
	 */
	var getCardList = function(){
		var sendObj = { 
		    "type" : "command", 
		    "functionType" : "action", 
		    "value" : {
		        "actionName" : "selectCard",
		        "callbackFn" : "window.activeObj.restoreData" 
		    }, 
		    "actionValue" : {
		        "cipherKey" : $cipherKey.val(),
		        "pId" : $pId.val()
		    }
		} 
		self.toNative(sendObj);
	};
	/**
	 * 
	 */
	this.restoreData = function(data){

		console.log("callBack : " + data ); 

		if( typeof data === "string" ){			
			data = self.util.parseJson( data );
		}

		if( data.success !== undefined && data.success ){
			displayCardList( data.result );
		}
	};
	/**
	 * 
	 */
	var displayCardList = function(data){
		$cardList.html("");
		
		if( data !== undefined && data !== null && data.length === 0 ){
			var div = $("<div style='padding:10px;font-weight:bold'></div>").html( self.getI18n("favoriteList006") );
			$cardList.append( div );
		} else {
			for( var i = 0; i < data.length; i++ ){
				 var item = data[i],
				 	 div = $("<div></div>"),
				 	 span = $("<span></span>"),
				 	 eIcon = $("<i></i>").addClass("fa fa-lg fa-pencil-square").css("color","#fff"),
				 	 rIcon = $("<i></i>").addClass("fa fa-lg fa-trash").css("color","#fff");
				 
				 //카드 이름
				 var cardName = div.clone().addClass("cardName cardContent").html( div.clone().html( span.html(item["name"]) ) ),
				 //수정 버튼
				 	 editBtn = div.clone().addClass("cardEdit cardContent").html( div.clone().html( eIcon ) ),
				 //삭제 버튼
				 	removeBtn = div.clone().addClass("cardRemove cardContent").html( div.clone().html( rIcon ) );
				 
				 var eachCard = div.clone().addClass("eachCard").attr("data-seq",item["cardSeq"]).html( cardName ).append( editBtn ).append( removeBtn );
				 $cardList.append( eachCard );
			}
			//최대 설정 갯수 등록되어 있으면 등록 버튼 disabled - menu param
			var defaultCardNum = self.util.getMenuParam("maxCardNum");
			defaultCardNum = (defaultCardNum!==null? defaultCardNum : 3);
			//최대 갯수 텍스트 삽입
			$maxCardNum.html(defaultCardNum);
			
			if( data.length === defaultCardNum ){
				$registerBtn.prop("disabled",true);
			} else {
				$registerBtn.prop("disabled",false);
			}
			//수정버튼 이벤트
			var editFn = function(e){
				var cardSeq = $(this).parent().data()["seq"];
				self.changePage( contextPath + "/mobile/pay/favoriteForm.page?menuId=favoriteForm&cardSeq="+ cardSeq );
			};
			//삭제버튼 이벤트
			var removeFn = function(e){
				var cardSeq = $(this).parent().data()["seq"];
				var options = {
						content : self.getI18n( "favoriteList007" ),
						callback : function(e){
							removeCard(cardSeq);
						}
				};
				self.popup(options);
			};
			//수정이벤트
			self.event.addEvent( $(".cardEdit"), "click", editFn );
			//삭제이벤트
			self.event.addEvent( $(".cardRemove"), "click", removeFn );
		}
	};
	/**
	 * 
	 */
	var removeCard = function(cardSeq){

		var sendObj = { 
		    "type" : "command", 
		    "functionType" : "action", 
		    "value" : {
		        "actionName" : "deleteCard",
		        "callbackFn" : "window.activeObj.removeCardDisplay" 
		    }, 
		    "actionValue" : {
		        "cardSeq" : cardSeq,
		        "cipherKey" : $cipherKey.val(),
		        "pId" : $pId.val()
		    }
		}; 
		self.toNative(sendObj);

	};
	/**
	 * 
	 */
	this.removeCardDisplay = function(data){
		console.log("callBack : " + data ); 

		if( typeof data === "string" ){			
			data = self.util.parseJson( data );
		}

		if( data.success !== undefined && data.success ){
			initCardList();
		}
		
	}
};