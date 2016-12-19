/**
 * mcare_mobile_healthHandbookUpdate
 */
var mcare_mobile_healthHandbookUpdate = function(){
	//상속
	mcare_mobile.call(this);
	//super
	var self = this;
	//변수
	var $regDt = $("#regDt"),
		$regDate = $("#regDate"),
		$regTime = $("#regTime"),
		$fastingSugarValue = $("#fastingSugarValue"),
		$postSugarValue = $("#postSugarValue"), 
		$pressMinValue = $("#pressMinValue"),
		$pressMaxValue = $("#pressMaxValue"),
		$heightValue = $("#heightValue"),
		$weightValue = $("#weightValue"),
		$bmiValue = $("#bmiValue"),
		$save = $("#save"),
		$bmiResult = $("#resultBmi"),
		$remove = $("#remove"),
		$pId = $("#pId"), 
		$cipherKey = $("#cipherKey"); 
	
	var parameterMap = null;
	/**
	 * 초기화
	 */
	this.init = function(){
		setParameterMap();
		setData();
		addEvent();
		self.headerFix();
	};
	

	/**
	 * 이벤트 등록
	 */
	var addEvent = function(){
		//저장버튼 클릭 이벤트
		$save.on("click",function(e){
			//버튼 클릭 배경
			$save.addClass("active");
			saveData(e);
		});
		//키 값 포커스 아웃 이벤트
		$heightValue.on("blur",function() {			
			getBmiText( calculateBmi() ); 
		});
		//체중 값 포커스 아웃 이벤트
		$weightValue.on("blur",function(){
			getBmiText( calculateBmi() ); 
		});
		//삭제 버튼 클릭 이벤트
		$remove.on("click",function(e){
			//버튼 클릭 배경
			$remove.addClass("active");
			var options = {
					content : self.getI18n("healthHandbookInsert029"),
					callback : function(e){
						deleteData();
					}
			};
			self.popup(options);
		});
		//날짜 입력 클릭 이벤트
		$regDate.on("click",function(e){
			if( self.isAndroid() ){				
				$regDt.trigger("click");
			} else {
				$regDt.focus();
			}
		});
	};
	/**
	 * 
	 */
	var setParameterMap = function(){
		parameterMap = self.util.getParameter();
	};
	/**
	 * 
	 */
	var setData  = function(){
		//수정화면으로 넘어올 때, seq 정보도 같이 넘어와야함
		var id = parameterMap["healthSeq"];
		// json 메시지 만들어서 조회 호출
		var jsonMsg = {
			type : "command", 
			functionType : "action",   // 처음엔 function이었으나 예약어라서 functionType으로 변경 
			value : {
				actionName : "selectHealth", 
				//core에서 global 객체로 현재 페이지 객체를 넣는 함수를 통한 방법
				callbackFn : "window.activeObj.setDataCallback"
			}, 
			actionValue : {
				healthSeq: id,
				pId : $pId.val(), 
				cipherKey : $cipherKey.val()				
			}
		};
		// 전달 
		self.toNative(jsonMsg); 
	};
	/**
	 * 
	 */
	this.setDataCallback = function( msg ){
		console.log("callBack : " + msg); 
		if( typeof msg === "string" ){			
			msg = self.util.parseJson( msg );
		}
		if( typeof msg === "object"){
			//self.alert( self.util.stringifyJson(msg) );
		}
		if( msg.success !== undefined && msg.success ){
			setDataForm( msg.result );
		}
	};
	/**
	 * 
	 */
	var setDataForm = function(data){
		var item = data[0];
		
		$regDt.val( (item.regDt).split(" ")[0] );
		$regTime.val( (item.regDt).split(" ")[1] );
		$fastingSugarValue.val( item.fastingSugarValue );
		$postSugarValue.val( item.postSugarValue );
		$pressMinValue.val( item.pressMinValue );
		$pressMaxValue.val( item.pressMaxValue );
		$heightValue.val( item.heightValue );
		$weightValue.val( item.weightValue );
		$bmiValue.val( item.bmiValue );
		$bmiResult.val( Math.ceil( parseFloat(item.bmiValue) ) );
		$bmiResult.slider( "refresh" );
	};
	/**
	 * 폼데이터 저장
	 */
	var saveData = function(){
		if( !validateData() ){
			return false;
		}
		var id = parameterMap["healthSeq"];
		// json 메시지 만들어서 
		var jsonMsg = {
			type : "command", 
			functionType : "action",   // 처음엔 function이었으나 예약어라서 functionType으로 변경 
			value : {
				actionName : "updateHealth", 
				//core에서 global 객체로 현재 페이지 객체를 넣는 함수를 통한 방법
				callbackFn : "window.activeObj.healthHandbookUpdateCallBack"
			}, 
			actionValue : {
				healthSeq : id, 
				regDt : $regDt.val() + " " + $regTime.val(),
				fastingSugarValue : $fastingSugarValue.val(), 
				postSugarValue :$postSugarValue.val(),
				pressMinValue : $pressMinValue.val(),
				pressMaxValue : $pressMaxValue.val(),
				heightValue : $heightValue.val(),
				weightValue : $weightValue.val(),
				bmiValue : calculateBmi(),
				pId : $pId.val(), 
				cipherKey : $cipherKey.val()				
			}
		};

		//console.log("jsonMsg : " + jsonMsg); 
		// 전달 
		self.toNative(jsonMsg); 
	};
	
	/**
	 * null 체크, 필수 입력값만 체크하도록
	 */
	var validateData = function(){
		//날짜
		if( $regDt.val() === "" ){
			self.alert( self.getI18n("healthHandbookInsert016") );
			$regDt.focus();
			return false;
		//시간
		}if( $regTime.val() === "" ){
			self.alert( self.getI18n("healthHandbookInsert017") );
			$regTime.focus();
			return false;
		} 
		
		//전체 공백이면 안됨
		if( $fastingSugarValue.val() === "" && $postSugarValue.val() === "" && $pressMinValue.val() === "" 
			&& $pressMaxValue.val() === "" && $heightValue.val() === ""&& $weightValue.val() === "" ) {
			self.alert( self.getI18n("healthHandbookInsert018") );
			return false;
		}
		
		//혈압 - 최저 혈압이 빈 값이고 최고가 빈 값이 아니면
		if( $pressMinValue.val() === "" && $pressMaxValue.val() !== "") {
			self.alert( self.getI18n("healthHandbookInsert019") );
			$pressMinValue.focus();
			return false;
		//혈압 - 최고 혈압이 빈 값이고 최저가 빈 값이 아니면
		} else if( $pressMinValue.val() !== "" && $pressMaxValue.val() === "") {
			self.alert( self.getI18n("healthHandbookInsert020") );
			$pressMaxValue.focus();
			return false;
		} 
		//키가 빈 값이고 체중이 빈 값이 아니면
		if( $heightValue.val() === "" && $weightValue.val() !== "") {
			self.alert( self.getI18n("healthHandbookInsert021") );
			$heightValue.focus();
			return false;
		//체중이 빈 값이고 키가 빈 값이 아니면
		} else if( $heightValue.val() !== "" && $weightValue.val() === "" ) {
			self.alert( self.getI18n("healthHandbookInsert022") );
			$weightValue.focus();
			return false;
		}
		//0보다 큰값을 넣어라
		if( ($fastingSugarValue.val()!=="" && $fastingSugarValue.val() <= 0) || 
			($postSugarValue.val() !== "" && $postSugarValue.val() <= 0) || 
			($pressMinValue.val() !== "" && $pressMinValue.val() <= 0) || 
			($pressMaxValue.val() !== "" && $pressMaxValue.val() <= 0) || 
			($heightValue.val() !== "" && $heightValue.val() <= 0) || 
			($weightValue.val() !== "" && $weightValue.val() <= 0)
		){
			self.alert( self.getI18n("healthHandbookInsert028") );
			return false;
		}
		return true;
	};

	/**
	 * bmi 계산
	 * @description
	 *  공식 : 체중 / (키 * 키) 
	 *	18.5이하 : 저체중  
	 *	18.5 ~ 23 : 정상  
	 *	23 ~ 25 : 위험체중 
	 *	25 ~ 30 : 1단계 비만  
	 *	30 ~  : 2단계 비만  
	 *	출처 : 대한비만학회, 비만치료지침 2012년 버전
	 */
	var calculateBmi = function() { 
		var weight = $weightValue.val(),
			height = $heightValue.val(); 
		
		if( weight === "" || height === "" ){
			return "";
		}
		
		if( weight > 0 && height > 0 ) {
			var bmi = (weight) / (height * height); 
			
			// 소수점 둘째로 자르기 
			bmi = bmi * 1000000; 
			bmi = parseInt(bmi); 
			bmi = bmi / 100; 
			return bmi;
		}
	};
	/**
	 * bmi 텍스트 보여주기
	 */
	var getBmiText = function( bmi ){
		try{
			
			if( $heightValue.val() === "" || $weightValue.val() === "" ){
				return;
			}
			// 수치 보여주고 
			var str = bmi;
			
			// 결과 보여주기 
			if(bmi < 18.5) {
				str = str + " (" + self.getI18n("healthHandbookInsert011") +")";
			}
			else if(bmi < 23) { 
				str = str + " (" + self.getI18n("healthHandbookInsert012") +")";
			}
			else if(bmi < 25) {
				str = str + " (" + self.getI18n("healthHandbookInsert013") +")";
			}
			else if(bmi < 30) { 
				str = str + " (" + self.getI18n("healthHandbookInsert014") +")";
			}
			else {
				str = str + " (" + self.getI18n("healthHandbookInsert015") +")";
			}
			
			$bmiValue.val( str );
			$bmiResult.val( bmi>50? 50: Math.round( parseFloat(bmi)) );
			$bmiResult.slider();
			$bmiResult.slider("refresh");
		}catch(e){
			self.alert(e);
		}
	};
	/**
	 * toNative 호출 결과가 들어오는 곳 - 수정
	 */
	this.healthHandbookUpdateCallBack = function( msg ) {
		console.log("callBack : " + msg); 
		//버튼 클릭 배경 제거
		$save.removeClass("active");
		
		if( typeof msg === "string" ){			
			msg = self.util.parseJson( msg );
		}
		if( typeof msg === "object"){
			//self.alert( self.util.stringifyJson(msg) );
		}
		if( msg.success !== undefined && msg.success ){
			self.alert( self.getI18n("healthHandbookInsert023") );
		}else{
			self.alert( self.getI18n("healthHandbookInsert024") );
		}
	};
	/**
	 * 삭제
	 */
	var deleteData = function(){
		// json 메시지 만들어서 
		var id = parameterMap["healthSeq"];
		var jsonMsg = {
			type : "command", 
			functionType : "action",   // 처음엔 function이었으나 예약어라서 functionType으로 변경 
			value : {
				actionName : "deleteHealth",
				callbackFn : "window.activeObj.healthHandbookDeleteCallBack"
			}, 
			actionValue : {
				healthSeq : id, //TODO: 시퀀스값 가져와서 설정
				pId : $pId.val(), 
				cipherKey : $cipherKey.val()				
			}
		};

		// 전달 
		self.toNative(jsonMsg); 
	};
	/**
	 * toNative 호출 결과가 들어오는 곳 - 삭제
	 */
	this.healthHandbookDeleteCallBack = function( msg ){
		console.log("callBack : " + msg); 
		//버튼 클릭 배경 제거
		$remove.removeClass("active");
		
		if( typeof msg === "string" ){			
			msg = self.util.parseJson( msg );
		}
		if( typeof msg === "object"){
			//self.alert( self.util.stringifyJson(msg) );
		}
		if( msg.success !== undefined && msg.success ){
			self.alert( self.getI18n("healthHandbookInsert025"), function(){				
				self.changePage( contextPath + "/mobile/healthHandbook/healthHandbook.page?menuId=healthHandbook");
			} );
		}else{
			self.alert( self.getI18n("healthHandbookInsert026") );
		}
	};
};


