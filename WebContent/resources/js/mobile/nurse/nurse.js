/**
 * mcare_mobile_nurse
 */
var mcare_mobile_nurse = function(){
	//상속
	mcare_mobile.call(this);
	//super
	var self = this;
	//변수
	var $subTitle = $(".subTitle"),
		$requestBtn = $(".requestBtn"),
		$check_1 = $("#check_1"),
		$check_2 = $("#check_2"),
		$check_3 = $("#check_3"),
		$etc = $("#etc_request"),
		$searchBtn = $(".searchBtn");
	/**
	 * 초기화
	 */
	this.init = function(){
		preRequest();
		addEvent();
	};
	/**
	 * 이벤트 등록
	 */
	var addEvent = function(){
		//요청버튼 클릭 이벤트
		$requestBtn.on("click",function(e){
			$(this).addClass("active");
			if( $(this).hasClass("disable") ){
				self.alert( self.getI18n("nurse007") );
				$(this).removeClass("active");
				return false;
			}
			request(e);
		});
		//요청내역보기 버튼 - 현재는 없음
		$searchBtn.on("click",function(e){
			$(this).addClass("active");
			//TODO: 요청내역보기 버튼 누른다음에 어떻게 화면 구성해줄지 구현필요
			preRequest(e);
		});
	};
	/**
	 * 이전 요청내역
	 */
	var preRequest = function(e){
		self.loading("show");
		var param = {
				
		};
		$.ajax({
			url: contextPath + "/mobile/call/prevCall.json",
			method:"POST",
			data: self.util.stringifyJson(param),
//			dataType:"json",
			contentType:"application/json",
			success:function(data){
				try{
					if(data.msg !== undefined ){
						self.alert(data.msg,function(){
//						self.changePage( contextPath + "/index.page" );
							$("#headerArrowLeft_btn")[0].click();
						});					
						return;
						//재원중이 아니면
					} else if( data.result.isHospitalYn !== undefined && !data.result.isHospitalYn ){
						// 알림창으로 에러가 표시되므로 제목은 바꾸지 않음 
						//$subTitle.text( self.getI18n("nurse010") );
						$requestBtn.addClass("disable");
						self.alert( self.getI18n("nurse010"), function(){
//						self.changePage( contextPath + "/index.page" ); 
							$("#headerArrowLeft_btn")[0].click();
						});
						return false;
					} else {					
						setSubTitle(data.result);
					}
					
				} catch(e) {
					
				}
			},
			error: function(xhr){
				console.log(xhr);
			},
			complete: function(){
				self.loading("hide");
			}
		});
	};
	/**
	 * 요청
	 */
	var request  = function(e){
		if( !validateData() ){
			self.alert( self.getI18n("nurse006") );
			$requestBtn.removeClass("active");
			return;
		}
		var param= {
				reqCodeValue :  getReqCodeValue(),
				reqValue : getReqValue(),
				reqEtcValue : $etc.val()
		};
		function getReqCodeValue(){
			return ( $check_1.prop("checked")?"1,":"" ) + ( $check_2.prop("checked")?"2,":"" ) + ( $check_3.prop("checked")?"3":"" )
		};
		function getReqValue(){
			return  ( $check_1.prop("checked")?$("label[for='check_1']").text()+",":"" ) +
					( $check_2.prop("checked")?$("label[for='check_2']").text()+",":"" ) +
					( $check_3.prop("checked")?$("label[for='check_3']").text():"" )
		};
		
		
		self.loading("show");
		$.ajax({
			url: contextPath + "/mobile/call/nurse.json",
			method:"POST",
			data: self.util.stringifyJson(param),
			dataType:"json",
			contentType:"application/json",
			success:function(data){
				try{
					if(data.msg !== undefined ){
						self.alert(data.msg);
						return;
					} else{						
						//				$check_1.prop("checked",false).checkboxradio( "refresh" );
						//				$check_2.prop("checked",false).checkboxradio( "refresh" );
						//				$check_3.prop("checked",false).checkboxradio( "refresh" );
						//				$etc.val("");
						//				$requestBtn.addClass("disable");
						self.alert( self.getI18n("nurse009"), function(){
							location.reload();
						});
					}
					
				} catch(e) {
					
				}
			},
			error: function(xhr){
				console.log(xhr);
			},
			complete: function(){
				self.loading("hide");
				$requestBtn.removeClass("active");
			}
		});
	};
	/**
	 * 이전 요청 내역 또는 요청 버튼 disable 표시
	 */
	var setSubTitle = function(pre){
		if( pre !== undefined && pre !== null && pre !== "" ){
			$subTitle.text( self.getI18n("nurse007") );
			$requestBtn.addClass("disable");
			$requestBtn.hide();
			var code = pre["reqCodeValue"],
			etc = pre["reqEtcValue"];
			
			code = code!= null? code.split(","):("").split(",");
			
			$etc.val(etc).textinput( "disable" );
			for( var i = 0; i < code.length; i++ ){
				$( "#check_" + code[i] ).prop("checked",true).checkboxradio( "refresh" ).checkboxradio( "disable" );
			}
			return;
		} else {
			$subTitle.text( self.getI18n("nurse006") );
			$requestBtn.show();
			$requestBtn.removeClass("disable");
		}
	}
	/**
	 * 유효성 검사
	 */
	var validateData = function(){
		if( !$check_1.prop("checked") && !$check_2.prop("checked") && !$check_3.prop("checked") && $etc.val()=== "" ){
			return false;
		}
		return true;
	};
};