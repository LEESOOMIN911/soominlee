/**
 * mcare_mobile_announceArrival
 */
var mcare_mobile_announceArrival = function(){
	//상속
	mcare_mobile.call(this);
	//super
	var self = this;
	//변수
	var $targetContainer = $(".targetContainer"),
		$nextContainer = $(".next_container"),
		$arrivalBtn = $(".arrivalBtn");
	
	//도착확인 대상 목록
	var announceList = new Array(),
		completeList = new Array();
	/**
	 * 초기화
	 */
	this.init = function(){
		$arrivalBtn.hide();
		isHospital();
		//initReservation();
		addEvent();
	};
	/**
	 * 이벤트 등록
	 */
	var addEvent = function(){
		//도착알림 버튼 클릭 이벤트
		$arrivalBtn.on("click",function(e){
			//버튼 클릭 배경
			$(this).addClass("active");
			var receiptNo = $(this).attr("data-receiptNo"),
				crmCd = $(this).attr("data-crmCd");
			announce( receiptNo, crmCd );
		});
	};
	/**
	 * 
	 */
	var isHospital = function(){
		self.loading("show");
		var msg = {
				type: "command",
				functionType : "isInHospital",
				value : { callbackFn : "window.activeObj.callbackCheckBeacon" }
		};
		try{			
			self.toNative( msg );
		} catch(e){
			console.log( e );
			self.loading("hide");
		}
	};
	/**
	 * 
	 */
	var cIndex = 0;
	this.callbackCheckBeacon = function(data){
		if( typeof data === "string" ){			
			data = self.util.parseJson( data );
		}
		if( data["success"] === "true" ){
			initReservation();
		} else if( data["success"] === "notSupported" ){
			self.alert( self.getI18n( "announceArrival010" ),function(){
				$("#headerArrowLeft_btn")[0].click();
			});
		} else if( cIndex <= 2 ){
			setTimeout(function(){
				isHospital();
			},800);
			cIndex++;
		} else {
			self.alert( self.getI18n( "announceArrival009" ), function(){
				$("#headerArrowLeft_btn")[0].click();
			});
		}
	};
	/**
	 * 도착확인 대상 초기화
	 */
	var initReservation = function(){
		var today = new Date(),
			text = self.util.getDateText( today );
		var param = {
				date : text
		};

		$.ajax({
			url : contextPath + "/mobile/arrival/arrivedConfirmTargetList.json",
			type : "POST",
			data : self.util.stringifyJson(param),
			contentType:"application/json",
			success : function( data ){
				try{
					if( data.msg !== undefined ){
						self.alert( data.msg );
						return false;
					} else {
						obj = restoreData( data.result );
						announceList = obj["target"];
						completeList = obj["complete"];
						displayReservation( data.result );
					} 
					
				} catch(e) {
					
				}
			},
			error : function( xhr, d, t ){
				self.loading("hide");
				console.log( xhr );
			},
			complete : function(){
				//self.loading("hide");
				$arrivalBtn.removeClass("active");
			}
		});
	};
	/**
	 * 도착확인 대상 - 예약 리스트 표시
	 */
	var displayReservation = function(data){
		if( data === undefined || data === null || data.length === 0 || $.isEmptyObject(data) ){
			var bodyDiv = $("<div></div>");
			 self.alert( self.getI18n( "announceArrival008" ),function(){
//				 self.changePage( contextPath + "/index.page" );
				 $("#headerArrowLeft_btn")[0].click();
			 });			 
			return;
		}else{	
			try{				
				displayTarget();
				displayComplete();	
				self.loading("hide");
			} catch(e){
				self.loading("hide");
			}
		};

	};
	/**
	 * 도착확인 알림 대상
	 */
	var displayTarget = function(){
		$targetContainer.html("");
		
		for( var i = 0; i < announceList.length; i++ ){
			 var item = announceList[i],
			 	 div = $("<div></div>"),
			 	 h2 = $("<h2></h2>").addClass("for_dept"),
			 	 icon = $("<i></i>"),
			 	 span = $("<span></span>")
			 	 
			 var dept = div.clone().html( h2.html( item["departmentNm"] ) );
			 var time = div.clone().addClass("item").html( icon.clone().addClass("fa fa-clock-o") ).append( span.clone().addClass("code").html( self.getI18n("announceArrival001")+"&nbsp;&#58;&nbsp;"))
			 			.append( span.clone().addClass("for_time code_txt").html( self.util.restoreTime(item["time"]) ) );
			 var doctor = div.clone().addClass("item").html( icon.clone().addClass("fa fa-info-circle") ).append( span.clone().addClass("code").html( self.getI18n("announceArrival002")+"&nbsp;&#58;&nbsp;"))
	 			.append( span.clone().addClass("for_notice item_txt").html( item["doctorNm"] ) );
			 
			 $targetContainer.append( dept ).append( div.clone().addClass("msgBox").html( time ).append( doctor ) );
			 
		}
		//대상있으면 버튼 보여주기 없으면 숨기고
		if( announceList.length > 0 ){			
			$arrivalBtn.show();
		} else {
			$arrivalBtn.hide();
		}
	};
	/**
	 * 도착확인 완료 대상
	 */
	var displayComplete = function(){
		$nextContainer.html("");
		if( completeList.length > 0 ){
			var icon = $("<div></div>").addClass("next_r").html(  $("<i></i>").addClass("fa fa-file-text-o") )
						.append( $("<span></span>").html( "&nbsp;"+self.getI18n("announceArrival004") ));
			$nextContainer.html( icon );
		}
		for( var i = 0; i < completeList.length; i++ ){			
			 var div = $("<div></div>"),
			 	 span = $("<span></span>"),
			 	 item = completeList[i];

			 	//다음 진료과
			 var next_dept = div.clone().addClass("for_next_deptwrap").html("&bull;&nbsp;")
			 	 	.append( span.clone().addClass("for_next_dept").text(item["departmentNm"])),
			 	 //다음 예약 시간
			 	 next_time = div.clone().addClass("item").html( span.clone().addClass("code").html("&bull;&nbsp;"+ self.getI18n("announceArrival001") +"&nbsp;&#58;&nbsp;"))
			 	 .append( span.clone().addClass("for_next_time").html( self.util.restoreTime(item["time"]) )),
			 	 //다음 예약 의사
			 	 next_notice = div.clone().addClass("item").html( span.clone().addClass("code").html("&bull;&nbsp;"+ self.getI18n("announceArrival002") +"&nbsp;&#58;&nbsp;"))
			 	 .append( span.clone().addClass("for_next_time").html( item["doctorNm"] )),
			 	 //예약 wrapper
			 	 next_reservation = div.clone().addClass("next_reservation");
			 
			 next_reservation.html( next_dept ).append( next_time ).append( next_notice );
			 
			 $nextContainer.append( next_reservation );
		}
	};
	/**
	 * 데이터 가공
	 */
	var restoreData = function( data ){
		var target = new Array(),
			complete = new Array();
		
		for( var i = 0; i < data.length ; i++ ){
			 var item = data[i],
			 	 status = item["progressStatusCd"];
			 //상태가 R 또는 W인 데이터만 추출하도록 변경
			 if( $.trim(status) === "R" || $.trim(status) === "W"){
				 target.push( item );
			 } else {
				 complete.push( item );
			 };
		}
		
		return {"target":target, "complete":complete};
	};
	/**
	 * 도착 알림
	 */
	var announce = function(){			
		self.loading("show");
	
		$.ajax({
			url : contextPath + "/mobile/arrival/arrivedConfirm.json",
			type : "POST",
			data : self.util.stringifyJson(announceList),
			contentType:"application/json",
			success : function( data ){
				try{
					if( data.msg !== undefined ){
						self.alert( data.msg );
						return false;
					} else {
						self.alert( data.result.message ,function(){						
							//도착확인 대상 다시 조회
							initReservation();
						});
					}
					
				} catch(e) {
					
				}
			},
			error : function( xhr, d, t ){
				console.log( xhr );
			},
			complete : function(){
				self.loading("hide");
			}
		});
	};
};