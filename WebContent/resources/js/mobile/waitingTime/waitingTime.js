/**
 * mcare_mobile_waitingTime
 */
var mcare_mobile_waitingTime = function(){
	//상속
	mcare_mobile.call(this);
	//super
	var self = this;
	//변수
	var $firCon = $(".fir_con"),
		$refreshBtn = $(".refreshBtn"),
		$btnArea = $(".btnArea"),
		$nowTime = $("#nowTime");;
	/**
	 * 초기화
	 */
	this.init = function(){
		$refreshBtn.hide();
		refresh();
		addEvent();
		var hours = new Date().getHours().toString(),
			minutes = new Date().getMinutes().toString();
		$nowTime.html( (hours[1]?hours:"0"+hours) + " "+ self.getI18n("waitingTime013") + (minutes[1]?minutes:"0"+minutes) +" " + self.getI18n("waitingTime011") );
	};
	/**
	 * 이벤트 등록
	 */
	var addEvent = function(){
		//다시조회 버튼 클릭
		$refreshBtn.on( "click",function(e){
			//버튼 클릭 배경
			$(this).addClass("active");
			refresh(e);
		});
	};
	/**
	 * 진료대기 조회
	 */
	var refresh = function(e){
		self.loading("show");
		var date = new Date(),
			param = {
				"date" : self.util.getDateText( date ),
				"departmentCd":"" //TODO:여러개의 도착확인을 했다면?
			};
		
		$.ajax({
			url: contextPath + "/mobile/waitingTime/waitingTime.json",
			method: "POST",
			dataType: "json",
			data:self.util.stringifyJson(param),
			contentType:"application/json",
			success: function(data){
				try{
					if( data.msg !== undefined ){
						self.alert( data.msg );
						return;
					} else {					
						self.loading("show");
						displayData(data.result);
					}
					
				} catch(e) {
					
				}
			},
			error: function(xhr){
				self.loading("hide");
				console.log(xhr);
				//버튼 클릭 배경 제거
				$refreshBtn.removeClass("active");
			},
			complete : function(){
				//self.loading("hide");
				//버튼 클릭 배경 제거
				$refreshBtn.removeClass("active");
			}
		});
	};
	/**
	 * 화면표시
	 */
	var displayData = function(data){
		$firCon.html("");
		//버튼 클릭 배경 제거
		$refreshBtn.removeClass("active");
		
		if( data === undefined || data === null || data === "" || data.length === 0 || $.isEmptyObject(data) ){
			$btnArea.hide();
			self.alert( self.getI18n("waitingTime006"), function(){				
				$("#headerArrowLeft_btn")[0].click();
			});
		} else {	
			$btnArea.show();
			for( var i = 0; i < data.length; i++ ){
				 var item = data[i],
				 	 div = $("<div></div>"),
				 	 span = $("<span></span>"),
				 	 icon = $("<i></i>");
				 
				 //진료과
				 var dept = div.clone().addClass("item infotxt").html( span.clone().html( icon.clone().addClass("fa fa-dot-circle-o") ).append( "&nbsp;" + self.getI18n("waitingTime007") + "&nbsp;&#58;&nbsp;" ) )
				 .append( span.clone().addClass("for_dept").html( item["departmentNm"] ) );
				 //예약시간
				 var time = div.clone().addClass("item infotxt").html( span.clone().html( icon.clone().addClass("fa fa-clock-o") ).append( "&nbsp;" + self.getI18n("waitingTime001") + "&nbsp;&#58;&nbsp;" ) )
				 .append( span.clone().addClass("for_time").html( item["date"] + " " + item["time"] ) );
				 //확인 시간
				 var checkTime = div.clone().addClass("item infotxt").html( span.clone().html( icon.clone().addClass("fa fa-clock-o") ).append( "&nbsp;" + self.getI18n("waitingTime008") + "&nbsp;&#58;&nbsp;" ) )
				 .append( span.clone().addClass("for_time").html( self.util.restoreCheckTime(item["checkTime"]) ) );
				 //대기
				 var rank = div.clone().addClass("item infotxt").html( span.clone().html( icon.clone().addClass("fa fa-times-circle-o") ).append( "&nbsp;" + self.getI18n("waitingTime002") + "&nbsp;&#58;&nbsp;" ) )
				 .append( span.clone().addClass("for_wait_time").html(item["ranks"]) );
				 //상태
				 var state = div.clone().addClass("item infotxt").html( span.clone().html( icon.clone().addClass("fa fa-info-circle") ).append( "&nbsp;" + self.getI18n("waitingTime003") + "&nbsp;&#58;&nbsp;" ) )
				 .append( span.clone().addClass("for_notice").html(item["progressStatusNm"]) );
				 
				 var each = div.clone().addClass("msgBox").html(dept).append(time).append(checkTime).append(rank).append(state);
				 
				 $firCon.append(each);
			}

			$refreshBtn.show();
			self.loading("hide");
		}
	};
};