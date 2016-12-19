/**
 * mcare_admin_push
 */
var mcare_admin_push = function(){
	//상속
	mcare_admin.call(this);
	
	var self = this;
	//변수
	var $pId = $("#pId"),
		$msgNoValue = $("#msgNoValue"), 
		$whenValue = $("#whenValue"), 
		$whereValue = $("#whereValue"), 
		$whatValue = $("#whatValue"), 
		$howValue = $("#howValue"), 
		$resultMsg = $("#resultMsg"), 
		$sendPushBtn = $("#sendPush");
		$crudServiceBaseUrl = contextPath + "/admin/push";
		
	/**
	 * 객체 초기화
	 */
	this.init = function(){
		addEvent();
	};
	/**
	 * 이벤트 등록
	 */
	var addEvent = function(){
		// 메시지 보내기 이벤트 
		$sendPushBtn.on("click", function(e) {
			sendPushMessage(e);
		});
	
	};
	
	/**
	 * 문자열 처리용 
	 */
	String.format = function() {
	    // The string containing the format items (e.g. "{0}")
	    // will and always has to be the first argument.
	    var theString = arguments[0];
	    
	    // start with the second argument (i = 1)
	    for (var i = 1; i < arguments.length; i++) {
	        // "gm" = RegEx options for Global search (more than one instance)
	        // and for Multiline search
	        var regEx = new RegExp("\\{" + (i - 1) + "\\}", "gm");
	        theString = theString.replace(regEx, arguments[i]);
	    }
	    
	    return theString;
	}; 
	
	
	var sendPushMessage = function(e) {
		var item = {}; 
		var message = ""; 
		
		
		item['pId'] = $pId.val(); 
		item['msgNoValue'] = $msgNoValue.val(); 
		item['whenValue'] = $whenValue.val(); 
		item['whereValue'] = $whereValue.val(); 
		item['whatValue'] = $whatValue.val();
		item['howValue'] = $howValue.val();
		
		// 접수단계 - 01 =================================================== 
		// 기간계/접수/예약/상황안내 
		if(item['msgNoValue'] == 'MA-10-05-40') {  
			// 10시, 내과에 예약 있습니다 
			message = String.format("{0}, {1}에 {2} 있습니다.", item['whenValue'], item['whereValue'], item['whatValue']); 
		}
		// 모바일/접수/도착/상황안내 
		else if(item['msgNoValue'] == 'MM-10-01-40') { 
			// 내과에 도착확인 되었습니다. 
			message = String.format("{0}에 도착확인 되었습니다.", item['whereValue']); 
		}
		// 모바일/접수/도착/요청 
		else if(item['msgNoValue'] == 'MM-10-01-10') { 
			// 내과에 오셔서 도착을 알려주세요 
			message = String.format("{0}에 오셔서 도착을 알려주세요.", item['whereValue']); 
		}
		// 앱/접수/번호표/선택
		else if(item['msgNoValue'] == 'MA-10-02-20') { 
			// 진료를 위해 원무과 접수창구의 번호표를 발급받으시겠습니까? [번호표발급] 
			message = String.format("진료를 위해 {0} {1} 창구의 번호표를 발급받으시겠습니까? [번호표발급]", item['whereValue'], item['whatValue']); 
		}
		// 모바일/접수/번호표/상황안내 
		else if(item['msgNoValue'] == 'MM-10-02-40') {  
			// 대기번호 xx번, 현재 대기인원 yy명 입니다. 
			message = String.format("대기번호 {0}번, 현재 대기인원 {1}명 입니다.", item['whatValue'], item['howValue']); 
		}
		// 기간계/접수/번호표/세부위치 
		else if(item['msgNoValue'] == 'ML-10-02-31') { 
			// n번 창구로 방문하세요 
			message = String.format("{0}님, {1}번 창구로 방문하세요.", item['pId'], item['whereValue']); 
		}
		// 
		// 진료단계 - 02 =================================================== 
		// 기간계/진료/진료/길안내 
		else if(item['msgNoValue'] == 'ML-20-03-30') { 
			// 내과로 가세요 
			message = String.format("{0}로 가세요. [길안내]", item['whereValue']); 
		}
		// 기간계/진료/진료/상황안내 
		else if(item['msgNoValue'] == 'ML-20-03-40') { 
			// 내과는 현재 10분 지연되고 있습니다 
			message = String.format("{0}는 현재 {1}분 지연되고 있습니다. [진료대기시간조회]", item['whereValue'], item['whenValue']); 
		}
		// 기간계/진료/진료/세부위치 
		else if(item['msgNoValue'] == 'ML-20-03-31') {
			// 홍길동님, 내과 5번방으로 오세요 
			message = String.format("{0}님, {1}에 오세요.", item['pId'], item['whereValue']); 
		}
		// 기간계/진료/수납/길안내 
		else if(item['msgNoValue'] == 'ML-20-04-30') {
			// 내시경실로 가세요 
			message = String.format("수납 후 {0}으로 가세요. [길안내]", item['whereValue']); // ==> 여기에 도착알림 해야하나? 안함 
		}
		// 
		// 수납단계 - 03 =================================================== 
		// 기간계/수납/수납/길안내 	
		else if(item['msgNoValue'] == 'ML-30-04-30') {
			// 진료가 완료되었습니다. 수납해주세요. 
			message = String.format("진료가 완료되었습니다. 수납해주세요. [수납위치안내]", item['whereValue']); 
		}
		// 
		// 귀가단계 - 04 =================================================== 
		// 앱/귀가/주차/상황안내 
		else if(item['msgNoValue'] == 'MA-40-07-40') {
			// 등록된 차량번호는 xxx입니다. 
			message = String.format("등록된 차량번호는 {0}입니다. [차량번호관리]", item['whatValue']); 
		}
		// 모바일/귀가/예약/상황안내 
		else if(item['msgNoValue'] == 'MA-40-05-40') {
			// 다음 내원일은 xxxx년 xx월 xx일 입니다. 
			message = String.format("다음 내원일은 {0}입니다. [예약내역조회]", item['whenValue']); 
		}
		// 기간계/귀가/조제/상황안내 
		else if(item['msgNoValue'] == 'ML-40-06-40') {
			// 원내약국에 약이 조제완료되었습니다. 
			message = String.format("{0}에 약이 조제완료되었습니다. [길안내]", item['whereValue']); 
		}
		else {
			message = String.format("{0} {1} {2} {3} {4} {5}", 
					item['pId'], item['msgNoValue'], item['whenValue'], item['whereValue'], item['whatValue'], item['howValue']); 
		}

		$resultMsg.text(message); 
		console.log(message); 
		
		/* 
		$.ajax({
			url : $crudServiceBaseUrl + '/sendPush.json',
			type : "POST",
			data : JSON.stringify(item),
			contentType:"application/json; charset=utf-8",
			dataType:"json",
			success : function(data) {
				if(data.msg != null) {
					alert("수행 중 에러가 발생하였습니다. ");
				}
				alert("염려마세요. 잘 끝났습니다. ");
			}
		});		
		*/ 
	}; 
};