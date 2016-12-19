/**
 * 카드 등록
 */
var mcare_mobile_favoriteForm = function(){
	mcare_mobile.call(this);
	
	var self = this;
	
	var $cardName = $("#cardName"),
		$cardCorp = $("#cardCorp"),
		$cardNum_1 = $("#cardNum_1"),
		$cardNum_2 = $("#cardNum_2"),
		$cardNum_3 = $("#cardNum_3"),
		$cardNum_4 = $("#cardNum_4"),
		$cardValiDt_1 = $("#cardValiDt_1"),
		$cardValiDt_2 = $("#cardValiDt_2"),
		$saveBtn = $(".saveBtn"),
		$pId = $("#pId"),
		$cipherKey = $("#cipherKey");
		
	this.init = function(){
		//파라미터 설정
		setParameterMap();
		initValiDt();
		//수정이면
		if( parameterMap["cardSeq"] !== undefined && parameterMap["cardSeq"] !== "" ){
			setData( parameterMap["cardSeq"] );
		};
		addEvent();
	};
	
	var addEvent = function(){
		$saveBtn.on("click",function(e){
			//버튼 클릭 배경
			$(this).addClass("active");
			saveCard();
		});
	};
	//이전 화면에서 전달 받은 파라미터 
	var parameterMap = null;
	/**
	 * 파라미터 맵 초기화
	 */
	var setParameterMap = function(){
		parameterMap = self.util.getParameter();
	};
	/**
	 * 
	 */
	var setData = function(cardSeq){
		var sendObj = { 
			    "type" : "command", 
			    "functionType" : "action", 
			    "value" : {
			        "actionName" : "selectCard",
			        "callbackFn" : "window.activeObj.callbackSelect" 
			    }, 
			    "actionValue" : {
			        "cardSeq" : cardSeq,
			        "cipherKey" : $cipherKey.val(),
			        "pId" : $pId.val()
			    }
			};
		self.toNative( sendObj );
	};
	/**
	 * 
	 */
	this.callbackSelect = function(data){
		console.log("callBack : " + data ); 

		if( typeof data === "string" ){			
			data = self.util.parseJson( data );
		}

		if( data.success !== undefined && data.success ){
			setDataInput( data.result[0] );
		}
	};
	/**
	 * 
	 */
	var setDataInput = function(data){
		$cardName.val( data["name"] );
		$cardNum_1.val( data["cardNo1"] );
		$cardNum_2.val( data["cardNo2"] );
		$cardNum_3.val( data["cardNo3"] );
		$cardNum_4.val( data["cardNo4"] );
		$cardValiDt_1.val( data["month"] );
		$cardValiDt_2.val( data["year"] );
		
		$cardValiDt_1.selectmenu("refresh");
		$cardValiDt_2.selectmenu("refresh");
	};
	/**
	 * 저장
	 */
	var saveCard = function(){
		
		var param = {
				cName : $cardName.val(),
				cCorp : $cardCorp.val(),
				cNum1 : $cardNum_1.val(),
				cNum2 : $cardNum_2.val(),
				cNum3 : $cardNum_3.val(),
				cNum4 : $cardNum_4.val(),
				cVDt1 : $cardValiDt_1.val(),
				cVDt2 : $cardValiDt_2.val()
		}
		
		if( !checkData( param ) ){
			return;
		} else {
			//save
			var sendObj = { 
			    "type" : "command", 
			    "functionType" : "action", 
			    "value" : {
			        "actionName" : "insertCard",
			        "callbackFn" : "window.activeObj.callbackSave" 
			    }, 
			    "actionValue" : {
			        "cipherKey" : $cipherKey.val(),
			        "pId" : $pId.val(),
			        "name" : param["cName"], 
			        "cardNo1" : param["cNum1"], 
			        "cardNo2" : param["cNum2"], 
			        "cardNo3" : param["cNum3"], 
			        "cardNo4" : param["cNum4"], 
			        "year" : param["cVDt2"], 
			        "month" : param["cVDt1"] 
			    }
			};
			//수정이면
			if( parameterMap["cardSeq"] !== undefined && parameterMap["cardSeq"] !== "" ){
				sendObj["value"]["actionName"] = "updateCard";
				sendObj["actionValue"]["cardSeq"] = parameterMap["cardSeq"];
			};
			self.toNative( sendObj );
		}
	};
	/**
	 * 
	 */
	this.callbackSave = function(data){
		console.log("callBack : " + data ); 

		if( typeof data === "string" ){			
			data = self.util.parseJson( data );
		}

		if( data.success !== undefined && data.success ){
//			self.changePage( contextPath + "/mobile/pay/favoriteList.page?menuId=favoriteList" );
			window.history.back();
		}
	};
	/**
	 * 유효성체크
	 */
	var checkData = function( param ){
		//카드별칭 입력
		if( param["cName"] === "" ){
			//카드별칭을 입력하세요.
			self.alert( self.getI18n("favoriteForm009"),function(){
				$cardName.focus();
			});
			return false;
		} else if( param["cName"].length > 10 ){
			//별칭은 10자 이내입니다.
			self.alert(self.getI18n("favoriteForm010"),function(){
				$cardName.focus();
			});
		}
		//카드번호1
		if( param["cNum1"].length < 4 ){
			//올바른 카드번호를 입력하세요.
			self.alert(self.getI18n("favoriteForm011"),function(){
				$cardNum_1.focus();
			});
			return false;
		} else if( param["cNum2"].length < 4 ){
			self.alert(self.getI18n("favoriteForm011"),function(){
				$cardNum_2.focus();
			});
			return false;
		} else if( param["cNum3"].length < 4 ){
			self.alert(self.getI18n("favoriteForm011"),function(){
				$cardNum_3.focus();
			});
			return false;
		} else if( param["cNum4"].length < 4 ){
			self.alert(self.getI18n("favoriteForm011"),function(){
				$cardNum_4.focus();
			});
			return false;
		}
		//카드번호 숫자 체크
		var num_check=/^[0-9]*$/;
		if( !num_check.test( param["cNum1"] ) ){
			//숫자만 입력할 수 있습니다.
			self.alert(self.getI18n("favoriteForm012"),function(){
				$cardNum_1.focus();
			});
			return false;
		} else if( !num_check.test( param["cNum2"] ) ){
			self.alert(self.getI18n("favoriteForm012"),function(){
				$cardNum_2.focus();
			});
			return false;
		} else if( !num_check.test( param["cNum3"] ) ){
			self.alert(self.getI18n("favoriteForm012"),function(){
				$cardNum_3.focus();
			});
			return false;
		} else if( !num_check.test( param["cNum4"] ) ){
			self.alert(self.getI18n("favoriteForm012"),function(){
				$cardNum_4.focus();
			});
			return false;
		}
		
		
		return true;
	}
	/**
	 * 
	 */
	var initValiDt = function(){
		var date = new Date(),
			year = date.getFullYear() - 2000,
			initYear = date.getFullYear() - 2000 ;
		
		$cardValiDt_2.html("");
		
		for( var i = 0; i < 5; i++ ){
			 var opt = $("<option></option>").val(year).text(year);
			 
			 $cardValiDt_2.append(opt);
			 year++;
		}
		$cardValiDt_2.val(initYear);
		$cardValiDt_2.selectmenu("refresh");
	};
};