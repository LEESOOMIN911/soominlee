/**
 * mcare_mobile_payManagement
 */
var mcare_mobile_payManagement = function(){
	//상속
	mcare_mobile.call(this);
	//super
	var self = this;
	//변수
	var $pay1Cont = $("#pay1Cont"),
		$pay2Cont = $("#pay2Cont");
	
	var dataObj = {};
	/**
	 * 초기화
	 */
	this.init = function(){
		retreive();
		addEvent();
	};
	/**
	 * 이벤트 등록
	 */
	var addEvent = function(){
//		$pay1Cont.on("click",function(e){
//			$(this).addClass("active");
//			setTimeout(function(){
//				$pay1Cont.removeClass("active");
//			},1000);
//			callRing2Pay(e);
//		});
		$pay2Cont.on("click",function(e){
			$(this).addClass("active");
			setTimeout(function(){
				$pay2Cont.removeClass("active");
			},1000);
			callSSGPay(e);
		});
	};

	/**
	 * 조회
	 */
	var retreive = function(){

//		self.loading("show");
//		$.ajax({
//			url:contextPath + "/mobile/smartpay/getR2pManagement.json",
//			method:"POST",
//			data:self.util.stringifyJson({}),
//			dataType:"json",
//			contentType:"application/json",
//			success:function(data){
//				if(data.msg !== undefined){
//					self.alert(data.msg,function(){
//						
//					});
//					return false;
//				} else if( data.extraMsg !== undefined ){
//					self.alert( data.extraMsg );
//					return;
//				} else {					
//					dataObj = data.result;
//				}
//			},
//			error: function(xhr){
//				console.log(xhr);
//			},
//			complete: function(){
//				self.loading("hide");
//			}
//		});
	};
	var callRing2Pay = function(e){
		var json = {
				"type" : "command", 
			    "functionType" : "payment", 
			    "value" : {
			        "actionName" : "management",
			        "paymentType" : "R2P",
			    }, 
			    "actionValue" : {     
			    	"requestPage" : "https://test1.Ring2Pay.com:15000/O2O/infoProc.emdo", // 오픈전 수정예정 
			    	// 
			    	//"requestNumber" : "111", // 일련번호 
			    	"mallId" : dataObj["mallId"] , // 임시. R2Pay에서 주는 값을 써야함  
			    	"merchantKey" : dataObj["merchantKey"], // 031-본원, 032-칠곡 
			    	"pId" : dataObj["pId"], // 환자번호 
			    	"cellphoneNo" : dataObj["cellphoneNo"], // 전화번호 
			    	"birthDt" : dataObj["birthDt"] // 생년월일 
			    }
		}
		
		self.toNative(json);
	};
	var callSSGPay = function(e){
		var json = {
				"type" : "command", 
			    "functionType" : "payment", 
			    "value" : {
			        "actionName" : "management",
			        "paymentType" : "SSG",
			    }, 
			    "actionValue" : { 
			      
			    }
		}
		
		self.toNative(json);
	};
};