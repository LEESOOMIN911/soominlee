/**
 * mcare_mobile_healthHandbookInsert
 */
var mcare_mobile_healthHandbookInsert = function(){
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
		//파라미터 설정
		setParameterMap();
		initDate();
		initValue();
		addEvent();
		self.headerFix();
	};
	/**
	 * 파라미터 맵 초기화
	 */
	var setParameterMap = function(){
		parameterMap = self.util.getParameter();
	};

	/**
	 * 이벤트 등록
	 */
	var addEvent = function(){
		//저장버튼 클릭 이벤트
		$save.on("click",function(e){
			e.preventDefault();
			//버튼 클릭 배경
			$(this).addClass("active");
			saveData(e);
		});
		//키 값 포커스 아웃 이벤트
		$heightValue.on("blur",function(e) {			
			getBmiText( calculateBmi() ); 
		});
		//체중 값 포커스 아웃 이벤트
		$weightValue.on("blur",function(){
			getBmiText( calculateBmi() ); 
		});
		//삭제 버튼 클릭 이벤트
		$remove.on("click",function(e){
			e.preventDefault();
			clearForm();
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
	var initValue = function(){
		$fastingSugarValue.val( parameterMap["sugar"] );
		$heightValue.val( parameterMap["height"] );
		$weightValue.val( parameterMap["weight"] );
		getBmiText( calculateBmi() ); 
		
		if( parameterMap["dateString"] !== undefined && parameterMap["dateString"] !== "" ){			
			$regDt[0].valueAsDate = new Date( parameterMap["dateString"] );
		}
		if( parameterMap["timeString"] !== undefined && parameterMap["timeString"] !== ""){
			$regTime.val( parameterMap["timeString"] );
		}
	};
	/**
	 * 
	 */
	var initDate = function(){
		var today = new Date(),
			todayText = self.util.simpleDateFormat( today );
		$regDt[0].valueAsDate = today;
		
		var hour = today.getHours().toString(),
			minutes = today.getMinutes().toString();
		
		$regTime.val( (hour[1]?hour:"0"+hour[0]) +":"+ (minutes[1]?minutes:"0"+minutes[0]) );
	};
	/**
	 * 폼데이터 저장
	 */
	var saveData = function(){
		if( !validateData() ){
			return false;
		}
		// json 메시지 만들어서 
		var jsonMsg = {
			type : "command", 
			functionType : "action",   // 처음엔 function이었으나 예약어라서 functionType으로 변경 
			value : {
				actionName : "insertHealth", 
				//core에서 global 객체로 현재 페이지 객체를 넣는 함수를 통한 방법
				callbackFn : "window.activeObj.healthHandbookInsertCallBack"
			}, 
			actionValue : {
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
		// 전달 
		self.toNative(jsonMsg); 
	};
	
	/**
	 * null 체크, 필수 입력값만 체크하도록
	 */
	var validateData = function(){
		//날짜
		if( $regDt.val() === "" ){
			self.alert( self.getI18n("healthHandbookInsert016"),function(){				
				$regDt.focus();
			});
			return false;
		//시간
		}if( $regTime.val() === "" ){
			self.alert( self.getI18n("healthHandbookInsert017"),function(){				
				$regTime.focus();
			});
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
			self.alert( self.getI18n("healthHandbookInsert019"),function(){				
				$pressMinValue.focus();
			});
			return false;
		//혈압 - 최고 혈압이 빈 값이고 최저가 빈 값이 아니면
		} else if( $pressMinValue.val() !== "" && $pressMaxValue.val() === "") {
			self.alert( self.getI18n("healthHandbookInsert020"),function(){				
				$pressMaxValue.focus();
			});
			return false;
		} 
		//키가 빈 값이고 체중이 빈 값이 아니면
		if( $heightValue.val() === "" && $weightValue.val() !== "") {
			self.alert( self.getI18n("healthHandbookInsert021"),function(){				
				$heightValue.focus();
			});
			return false;
		//체중이 빈 값이고 키가 빈 값이 아니면
		} else if( $heightValue.val() !== "" && $weightValue.val() === "" ) {
			self.alert( self.getI18n("healthHandbookInsert022"),function(){				
				$weightValue.focus();
			});
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
		$bmiResult.val( ( bmi>50? 50: bmi ) );
		$bmiResult.slider("refresh");
	};
	/**
	 * toNative 호출 결과가 들어오는 곳 
	 */
	this.healthHandbookInsertCallBack = function( msg ) {
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
			clearForm();
			self.alert( self.getI18n("healthHandbookInsert023"), function(){
				self.changePage( contextPath + "/mobile/healthHandbook/healthHandbook.page?menuId=healthHandbook");
			} );
		}else{
			self.alert( self.getI18n("healthHandbookInsert024") );
		}
	};
	/**
	 * 입력 폼 클리어
	 */
	var clearForm = function(){
		var today = new Date();
		$regDt[0].valueAsDate = today; 
		var hour = today.getHours().toString(),
		minutes = today.getMinutes().toString();
		$regTime.val( (hour[1]?hour:"0"+hour[0]) +":"+ (minutes[1]?minutes:"0"+minutes[0]) );
		
		$fastingSugarValue.val("");
		$postSugarValue.val("");
		$pressMinValue.val("");
		$pressMaxValue.val("");
		$heightValue.val("");
		$weightValue.val("");
		$bmiValue.val("");
		$bmiResult.val(0);
		$bmiResult.slider("refresh");
	};
};


