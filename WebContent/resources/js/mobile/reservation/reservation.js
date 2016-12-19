/**
 * mcare_mobile_reservation
 */
var mcare_mobile_reservation = function(){
	//상속
	mcare_mobile.call(this);
	//super
	var self = this;
	//변수
	var $gubun = $("select.r_gubun"),
		$doctor = $("select.r_doctor"),
		$date = $("select.r_date"),
		$time = $("select.r_time"),
		$symptom = $(".r_symptom"),
		$reservationBtn = $("#reservationBtn");
	/**
	 * 초기화
	 */
	this.init = function(){
		initGubun();
		addEvent();
	};
	/**
	 * 이벤트 등록
	 */
	var addEvent = function(){
		//진료과 선택
		$gubun.on( "change", function(e){
			if( $(this).val() === "" ){
				return;
			}
			initDoctor( $(this).val() );
			clearSelect("gubun");
		});
		//의사 선택
		$doctor.on( "change", function(e){
			if( $(this).val() === "" ){
				return;
			}
			initDate( $(this).val() );
			clearSelect("doctor");
		});
		//날짜 선택
		$date.on( "change", function(e){
			if( $(this).val() === "" ){
				return;
			}
			initTime( $(this).val() );
			clearSelect("date");
		});
		//예약 버튼 클릭
		$reservationBtn.on( "click", function(e){
			//버튼 클릭 배경
			$(this).addClass("active");
			checkReservation(e);
		});
	};
	/**
	 * 진료과 선택 초기화
	 * @param type {string} 조회 타입
	 */
	var initGubun = function(){
		self.loading("show");
		$.ajax({
			url: contextPath + "/mobile/reservation/getDepartment.json",
			method: "POST",
			data : self.util.stringifyJson({}),
			dataType : "json",
			contentType : "application/json",
			success: function(data){
				try{
					if( data.msg !== undefined ){
						self.alert( data.msg );
						return;
					} else {					
						displaySelect( data.result, $gubun, "gubun" );
					}
					
				} catch(e) {
					
				}
			},
			error : function(xhr){
				console.log( xhr );
			},
			complete : function(){
				self.loading("hide");
			}
		});
	};
	/**
	 * 의사 선택 초기화
	 */
	var initDoctor = function( doctor ){
		self.loading("show");
		$.ajax({
			url: contextPath + "/mobile/reservation/getDoctor.json",
			method: "POST",
			data : self.util.stringifyJson({departmentCd : $gubun.val()}),
			dataType : "json",
			contentType : "application/json",
			success: function(data){
				if( data.msg !== undefined ){
					self.alert( data.msg );
					return;
				} else {					
					displaySelect( data.result, $doctor, "doctor" );
				}
			},
			error : function(xhr){
				console.log( xhr );
			},
			complete : function(){
				self.loading("hide");
			}
		});
	};
	/**
	 * 날짜 선택 초기화
	 */
	var initDate = function( date ){
		//3개월 뒤로
		var today = new Date(),
			nextYear = new Date( new Date().setMonth(today.getMonth() + 3) );
		var param = {
				departmentCd : $gubun.val(),
				doctorId:$doctor.val(),
				startYearMonth: self.util.getYearText(today) + self.util.getMonthText(today),
				endYearMonth: self.util.getYearText(nextYear) + self.util.getMonthText(nextYear)
		};
		self.loading("show");
		$.ajax({
			url: contextPath + "/mobile/reservation/getDoctorDate.json",
			method: "POST",
			data : self.util.stringifyJson(param),
			dataType : "json",
			contentType : "application/json",
			success: function(data){
				try{
					if( data.msg !== undefined ){
						self.alert( data.msg );
						return;
					} else {					
						displaySelect( data.result, $date, "date" );
					}
					
				} catch(e) {
					
				}
			},
			error : function(xhr){
				console.log( xhr );
			},
			complete : function(){
				self.loading("hide");
			}
		});
	};
	/**
	 * 시간 선택 초기화
	 */
	var initTime = function( time ){
		self.loading("show");
		$.ajax({
			url: contextPath + "/mobile/reservation/getDoctorDateTime.json",
			method: "POST",
			data : self.util.stringifyJson({departmentCd : $gubun.val(),doctorId:$doctor.val(),date:$date.val()}),
			dataType : "json",
			contentType : "application/json",
			success: function(data){
				try{
					if( data.msg !== undefined ){
						self.alert( data.msg );
						return;
					} else {					
						displaySelect( data.result, $time, "time" );
					}
					
				} catch(e) {
					
				}
			},
			error : function(xhr){
				console.log( xhr );
			},
			complete : function(){
				self.loading("hide");
			}
		});
	};
	/**
	 * select 동적 생성
	 */
	var displaySelect = function( data, target, type ){
		var option = $("<option></option>");
		target.html("");
		if( data === undefined || data === null || data.length === 0 ){
			var item = option.clone().text( self.getI18n("reservation016") ).val( "" );
			target.append( item );
		} else {			
			target.html( option.clone().attr("selected","selected").text( self.getI18n("reservation010") ).val("") );
			for( var i = 0; i < data.length; i++ ) {
				switch( type ){
				case "gubun" : 
					var item = option.clone().text( data[i].departmentNm ).val( data[i].departmentCd );
					target.append( item );
					break;
				case "doctor" : 
					var item = option.clone().text( data[i].doctorNm ).val( data[i].doctorId );
					target.append( item );
					break;
				case "date" : 
					var item = option.clone().text( self.util.restoreDate(data[i].date) + "(" + data[i].weekNm + ")" ).val( data[i].date );
					target.append( item );
					break;
				case "time" :
					var item = option.clone().text( self.util.restoreTime(data[i].time) ).val( data[i].time );
					target.append( item );
					break;
				}
			}
		}
		target.selectmenu("enable" );
		target.selectmenu("refresh");
	};
	/**
	 * 예약 버튼 클릭 이벤트
	 */
	var reservation = function(e){

		var reqParam = {
			"departmentCd" : $gubun.val(),
			"departmentNm" : $gubun.find("[value="+$gubun.val()+"]").text(),
			"doctorId" : $doctor.val(),
			"doctorNm" : $doctor.find("[value="+$doctor.val()+"]").text(),
			"date" : $date.val(),
			"time" : $time.val(),
			"symptom" : $symptom.val()
		};
		self.loading("show");
		
		$.ajax({
			url: contextPath + "/mobile/reservation/reservation.json",
			type: "POST",
			data : self.util.stringifyJson(reqParam),
			dataType : "json",
			contentType :"application/json",
			success: function(data){
				try{
					if( data.msg !== undefined ){
						self.alert( data.msg );
						return;
					} else {					
						self.alert(self.getI18n( "reservation017" ),function(){
							//버튼 클릭 배경제거
							$reservationBtn.removeClass("active");
//					self.changePage( contextPath + "/index.page" );
							$("#headerArrowLeft_btn")[0].click();
						});
					}
					
				} catch(e) {
					
				}
			},
			error : function(xhr){
				console.log( xhr );
			},
			complete : function(){
				self.loading("hide");
				//버튼 클릭 배경제거
				$reservationBtn.removeClass("active");
			}
		});
	};
	/**
	 * 예약 내역 확인
	 */
	var checkReservation = function(){
		if( !validateForm() ){
			//버튼 클릭 배경제거
			$reservationBtn.removeClass("active");
			return false;
		}
		var text = "";
		text += createText( "reservation002", $gubun );
		text += createText( "reservation003", $doctor ); 
		text += createText( "reservation004", $date ); 
		text += createText( "reservation005", $time ); 
		text += "<span style='font-weight:bold;'>"+ self.getI18n( "reservation006" ) + " :&nbsp;</span> " + $symptom.val() + "<br/>";  
		text += "<span style='font-weight:bold;'>" + self.getI18n( "reservation011" ) + "</span>";
		
		var patt = /(script)/gi; // script 찾기 정규식
		if( patt.test( $symptom.val() ) ){
			self.alert( self.getI18n("reservation018"), function(){				
				$symptom.focus();
				//버튼 클릭 배경제거
				$reservationBtn.removeClass("active");
			});
			return;
		}
		
		var options = {
				content : text,
				callback : function(e){
					reservation();
				}
		};
		//버튼 클릭 배경제거
		$reservationBtn.removeClass("active");
		
		return self.popup( options );
		
		function createText( code, target ){
			return "<span style='font-weight:bold;'>"+self.getI18n( code ) + " :&nbsp;</span> " + 
				   target.find("option:selected").text() + "<br/>";
		}
	};
	/**
	 * 유효성 체크
	 */
	var validateForm = function(){
		if( $gubun.val() === null || $gubun.val() === ""  ){
			self.alert( self.getI18n( "reservation012" ) );
			$gubun.focus();
			return false;
		} else if( $doctor.val() === null || $doctor.val() === "" ){
			self.alert( self.getI18n( "reservation013" ) );
			$doctor.focus();
			return false;
		} else if( $date.val() === null || $date.val() === "" ){
			self.alert( self.getI18n( "reservation014" ) );
			$date.focus();
			return false;
		} else if( $time.val() === null || $time.val() === "" ){
			self.alert( self.getI18n( "reservation015" ) );
			$time.focus();
			return false;
		}
		return true;
	};

	var clearSelect = function(type){

		var option = $("<option></option>");
		
		switch( type ){
		case "gubun" : 
			$date.html( option.clone().attr("selected","selected").text( self.getI18n("reservation010") ).val("") );
			$time.html( option.clone().attr("selected","selected").text( self.getI18n("reservation010") ).val("") );
			$date.selectmenu("refresh" );
			$time.selectmenu("refresh" );
			break;
		case "doctor" : 
			$time.html( option.clone().attr("selected","selected").text( self.getI18n("reservation010") ).val("") );
			$time.selectmenu("refresh" );
			break;
		};
	};
};