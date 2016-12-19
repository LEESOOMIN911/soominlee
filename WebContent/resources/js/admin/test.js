/**
 * admin/test
 */
var mcare_admin_test = function( language_code ){
	//상속
	mcare_admin.call(this);
	
	var self = this;
	//변수
	var $wrapper = $(".main-wrapper #wrapper"),
		$saveAgreementItem = $("#save-agreement-item"),
		$selectAllAgreementItem = $("#select-all-agreement-item"),
		$updateAgreementItem = $("#update-agreement-item"),
		$deleteAgreementItem = $("#delete-agreement-item"),
		$saveTelNoItem = $("#save-telNo-item"),
		$selectAllTelNoItem = $("#select-all-talNo-item"),
		$updateTelNoItem = $("#update-telNo-item"),
		$deleteTelNoItem = $("#delete-telNo-item"),
		$apiReqTest = $("#api-req-test"),
		$getAppointmentList = $("#appointment-list");
		$getGetoutpatientlistList = $("#getoutpatientlist-list");
		$getInpatientlistList = $("#inpatientlist-list");
		$getHealthchecklistList = $("#healthchecklist-list");
		$crudServiceBaseUrl = contextPath + "/admin/test";
		
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
		//동의서 저장 이벤트
		$saveAgreementItem.on("click", function(e) {
			saveAgreementItem(e);
		});
		// 동의서 리스트 이벤트
		$selectAllAgreementItem.on("click", function(e) {
			selectAllAgreementItem(e);
		});
		// 동의서 수정 이벤트 
		$updateAgreementItem.on("click", function(e) {
			updateAgreementItem(e);
		});
		// 동의서 삭제 이벤트 
		$deleteAgreementItem.on("click", function(e) {
			deleteAgreementItem(e);
		});
		//전화번호 저장
		$saveTelNoItem.on("click", function(e) {
			saveTelNoItem(e);
		});
		//전화번호 리스트
		$selectAllTelNoItem.on("click", function(e) {
			selectAllTelNoItem(e);
		});
		//전화번호 수정
		$updateTelNoItem.on("click", function(e) {
			updateTelNoItem(e);
		});
		//전화번호 삭제
		$deleteTelNoItem.on("click", function(e) {
			deleteTelNoItem(e);
		});
		$apiReqTest.on("click", function(e) {
			apiReqTest(e);
		});
		$getAppointmentList.on("click", function(e) {
			getAppointmentList("/history/offlinereservation");
		});
		$getGetoutpatientlistList.on("click", function(e) {
			getAppointmentList("/history/getoutpatientlist");
		});
		$getInpatientlistList.on("click", function(e) {
			getAppointmentList("/history/inpatientlist");
		});
		$getHealthchecklistList.on("click", function(e) {
			getAppointmentList("/history/healthchecklist");
		});
	};
	
	var apiReqTest = function() {
		
		var item = {}
//		item['telNoSeq'] = "8";
		item['name'] = $("#name").val();
		
		$.ajax({
			url : "/admin/agreement/apiReqTester.json",
			type : "GET",
			data : "name="+$("#name").val(),
			dataType : "json",
			contentType: "application/json; charset=UTF-8",
			success :  function(data){
				if( data.msg !== undefined ) {
					alert("수행 중 에러가 발생하였습니다");
					console.log(data.msg);
					return false;
				}
	    		alert("호출 되었습니다");
	    		location.reload();
			}
		});
	}
	
	var getAppointmentList = function(apiUrl) {
		alert(apiUrl);
//		var apiUrl = $("#apiUrl").val();
		var ptFrrn = $("#ptFrrn").val();
		var ptSrrn = $("#ptSrrn").val();
		var eiInterface = $("#eiIterface").val();
		
		$.ajax({
			url : "/mobile/history.json",
			type : "GET",
			data : "apiUrl="+apiUrl+"&pt_frrn="+ptFrrn+"&pt_srrn="+ptSrrn+"&ei_interface="+eiInterface,
			dataType : "json",
			contentType: "application/json; charset=UTF-8",
			success :  function(data){
				if( data.msg !== undefined ) {
					alert("수행 중 에러가 발생하였습니다");
					console.log(data.msg);
					return false;
				}
				alert(JSON.stringify(data));
	    		alert("호출 되었습니다");
	    		location.reload();
			}
		});
	}
	
	/**
	 * 동의서 저장
	 */
	var saveAgreementItem = function(e) {
		var item = {};
    	
    	item['agreementOrder'] = "2"; 
    	item['agreementName'] = "시술 동의"; 
    	item['agreementContents'] = "시술에 동의해 주세요."; 
    	
    	alert(JSON.stringify(item)); 
    	
		$.ajax({
				url : '/admin/agreement/save.json', 
				type : "POST", 
				data : JSON.stringify(item),
				contentType:"application/json; charset=utf-8", 
				dataType:"json", 
				success : function(data) { 
					if (data.msg != null) {
						alert('수행 중 에러가 발생하였습니다');
						console.log(data.msg);
						return false;
					}
		    		alert('저장 되었습니다');
		    		location.reload(true);
				}
		});
	}
	/**
	 * 동의서 리스트
	 */
	var selectAllAgreementItem = function(e) {
		
		$.ajax({
			url : '/admin/agreement/getList.json',
			type : "GET",
			contentType:"application/json; charset=utf-8",
			success : function(data) {
				if(data.msg != null) {
					alert("수행 중 에러가 발생하였습니다.");
				}
				alert(JSON.stringify(data));
			}
		});
	}
	/**
	 * 동의서 수정
	 */
	var updateAgreementItem = function(e) {
		
		alert("동의서 수정");
		
		var item = {};
		
		item['agreementSeq'] = "5";
    	item['agreementOrder'] = "2"; 
    	item['agreementName'] = "성형 수술 동의"; 
    	item['agreementContents'] = "성형 수술에 동의해 주세요."; 
    	
    	alert(JSON.stringify(item)); 
		
		$.ajax({
			url : '/admin/agreement/update.json',
			type : "POST", 
			data : JSON.stringify(item),
			contentType:"application/json; charset=utf-8", 
			dataType:"json",
			success : function(data) {
				if(data.msg != null) {
					alert("수행 중 에러가 발생하였습니다. ");
				}
				alert("수정 되었습니다.");
			}
		});
	}
	var deleteAgreementItem = function(e) {
		
		alert("동의서 삭제");
		
		var item = {}
		item['agreementSeq'] = "4";
		alert(JSON.stringify(item)); 
		
		$.ajax({
			url : '/admin/agreement/remove.json',
			type : "POST",
			data : JSON.stringify(item),
			contentType:"application/json; charset=utf-8",
			dataType:"json",
			success : function(data) {
				if(data.msg != null) {
					alert("수행 중 에러가 발생하였습니다. ");
				}
				alert("삭제 되었습니다.");
			}
		});
		
	}
	
	var saveTelNoItem = function(e) {
		
		var item = {};
    	
    	item['buildingDesc'] = "테스트1"; 
    	item['roomDesc'] = "비만센터";
    	item['telValue'] = "051-888-8888";
    	item['telNoOrder'] = 1;
    	
    	alert(JSON.stringify(item)); 
    	
		$.ajax({
				url : '/admin/telno/save.json', 
				type : "POST", 
				data : JSON.stringify(item),
				contentType:"application/json; charset=utf-8", 
				dataType:"json", 
				success : function(data) { 
					if (data.msg != null) {
						alert('수행 중 에러가 발생하였습니다');
						console.log(data.msg);
						return false;
					}
		    		alert('저장 되었습니다');
		    		location.reload(true);
				}
		});
	}
	/**
	 * 
	 */
	var selectAllTelNoItem = function(e) {
		
		alert("전화번화 리스트 조회");
		
		$.ajax({
			url : '/admin/telno/getList.json',
			type : "GET",
			contentType:"application/json; charset=utf-8",
			success : function(data) {
				if(data.msg != null) {
					alert("수행 중 에러가 발생하였습니다.");
				}
				alert(JSON.stringify(data));
			}
		});
	}
	var updateTelNoItem = function(e) {
		
		alert("동의서 수정");
		
		var item = {};
    	
		item['telNoSeq'] = "8";
    	item['buildingDesc'] = "테스트1"; 
    	item['roomDesc'] = "비만센터";
    	item['telValue'] = "051-111-1111";
    	item['telNoOrder'] = 1;
    	
    	alert(JSON.stringify(item)); 
		
		$.ajax({
			url : '/admin/telno/update.json',
			type : "POST", 
			data : JSON.stringify(item),
			contentType:"application/json; charset=utf-8", 
			dataType:"json",
			success : function(data) {
				if(data.msg != null) {
					alert("수행 중 에러가 발생하였습니다. ");
				}
				alert("수정 되었습니다.");
			}
		});
	}
	var deleteTelNoItem = function(e) {
		
		alert("전화번호 삭제");
		
		var item = {}
		item['telNoSeq'] = "8";
		item['telNoValue'] = "051-111-1111";
		alert(JSON.stringify(item)); 
		
		$.ajax({
			url : '/admin/telno/remove.json',
			type : "POST",
			data : JSON.stringify(item),
			contentType:"application/json; charset=utf-8",
			dataType:"json",
			success : function(data) {
				if(data.msg != null) {
					alert("수행 중 에러가 발생하였습니다. ");
				}
				alert("삭제 되었습니다.");
			}
		});
		
	}
}; 