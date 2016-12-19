/**
 * mcare_mobile_parking
 */
var mcare_mobile_parking = function(){
	//상속
	mcare_mobile.call(this);
	//super
	var self = this;
	//변수
	var $update = $(".update"),
		$msg_con = $(".msg_con"),
		$carNum = $(".carNum");
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
		//수정버튼 클릭 이벤트
		$update.on( "click", function(e){
			//버튼 클릭 배경
	    	$(this).addClass("active");
			update(e);
		});
	};
	/**
	 * 조회
	 */
	var retreive = function(){

		self.loading("show");
		$.ajax({
			url:contextPath + "/mobile/user/getCarNo.json",
			method:"POST",
			dataType:"json",
			data:"{}",
			contentType:"application/json",
			success: function(data){
				try{
					if( data.msg !== undefined ){
						self.alert( data.msg );
						return;
					} else {
						display( data.result );
					}
					
				} catch(e) {
					
				}
			},
			error : function(xhr){
				console.log(xhr);
			},
			complete : function(){
				self.loading("hide");
			}
		});
	}
	/**
	 * 차량번호 표시
	 */
	var display = function( data ){
		$carNum.val("").attr("placeholder", self.getI18n("parking004") );
		
		if( data["vehicleNo"] === null || data["vehicleNo"].length === 0 || data === undefined ){
			$msg_con.find("p").text( self.getI18n("parking003") );
			return;
		}
		
		var vehicleNo = data["vehicleNo"],
			frontNo = vehicleNo.substr( 0, vehicleNo.length - 4 ),
			backNo = vehicleNo.substr( vehicleNo.length - 4 );
		$msg_con.find("p").text( frontNo + " " + backNo );
	};
	
	/**
	 * 차량번호 수정
	 */
	var update = function(e){
		if( !validateData() ){
			//버튼 클릭 배경제거
			$update.removeClass("active");
			return;
		}
		var param = {
			vehicleNo : $carNum.val().replace(/s/gi,"")
		};
		self.loading("show");
		$.ajax({
			url:contextPath + "/mobile/user/changeCarNo.json",
			method:"POST",
			data: self.util.stringifyJson( param ),
			dataType:"json",
			contentType : "application/json",
			success: function(data){
				try{
					if( data.msg !== undefined ){
						self.alert( data.msg );
						return;
					} else {		
						self.alert( self.getI18n("parking006"),function(){
							retreive();
						});
					}
					
				} catch(e) {
					
				}
			},
			error : function(xhr){
				console.log(xhr);
			},
			complete : function(){
				self.loading("hide");
				//버튼 클릭 배경제거
				$update.removeClass("active");
			}
		});
	};
	/**
	 * 유효성 체크
	 */
	var validateData = function(){
		var car = $carNum.val();
		
		var pattern1 = /^[0-9]{2}[\s]*[가-힣]{1}[\s]*[0-9]{4}$/gi; // 12저1234 
		var pattern2 = /^[가-힣]{2}[\s]*[0-9]{2}[가-힣]{1}[\s]*[0-9]{4}$/gi; // 서울12치1233 
		
		if( car === null || car === "" ){
			self.alert( self.getI18n("parking005"),function(){				
				$carNum.focus();
			});
			return false;
		} 

	    if (!pattern1.test(car)) { 
	        if (!pattern2.test(car)) { 
	        	self.alert( self.getI18n("parking007"),function(){
					$carNum.focus();
				});
	        	return false;
	        } 
	        else { 
	           return true;
	        } 
	    } else {
	    	return true;
	    } 
	};
};