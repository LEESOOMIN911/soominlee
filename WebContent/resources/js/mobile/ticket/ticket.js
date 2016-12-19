/**
 * mcare_mobile_ticket
 */
var mcare_mobile_ticket = function(){
	//상속
	mcare_mobile.call(this);
	
	//super
	var self = this;
	//변수
	var $type = $( ".r_type" ),
		$ticketContainer = $( ".ticketContainer" ),
		$myTicketContainer = $( ".myTicketContainer" ),
		$refreshTicketBtn = $( ".refreshTicketBtn" ),
		$machineContainer = $( ".machineContainer" ),
		$machineList = $("ul.machine_list"),
		$issueContainer = $(".issueContainer"),
		$issueBtn = $(".issue_btn"),
		$waitIssue = $(".wait_issue"),
		$loc = $(".machine_location span.loc");
	
	//발급 요청할 번호표 기기 id
	var issueMachine = {
			id : "",
			sort : "",
			hospitalCd : ""
	},
	//번호표 발급기 목록 메모리에 저장하기 위해서
	machineArray = new Array();
	
	var flag = true;
	/**
	 * 초기화
	 */
	this.init = function(){
		myTicket();
		initMachine();
		addEvent();
	};
	/**
	 * 이벤트 등록
	 */
	var addEvent = function(){
		//내번호표/ 발급받기 타입 클릭 이벤트
		$type.on("click",function(e){
			type = $(this).attr("data-type");
			
			if( type === "0" ){
				$machineContainer.hide();
				$issueContainer.hide();
				$.mobile.resetActivePageHeight(); 
				$ticketContainer.show();
				myTicket();
			} else {
				$ticketContainer.hide();
				$issueContainer.hide();
				$.mobile.resetActivePageHeight(); 
				$machineContainer.show();
			}
		});
		//내 번호표 새로고침
		$refreshTicketBtn.on("click",function(e){
			//서비스 준비중 2016-01-18
//			self.alert("서비스 준비중입니다.",function(){
//				
//			});
			myTicket();
		});
	};
	/**
	 * 내 번호표 이벤트
	 */
	var myTicket = function(){
		self.loading("show");
		$.ajax({
			url: contextPath + "/mobile/ticket/myTicket.json",
			method:"POST",
			data: self.util.stringifyJson({}),
			contentType : "application/json",
			dataType:"json",
			success: function(data){
				try{
					if( data.msg !== undefined ){
						self.alert(data.msg);
						return;
					} else {					
						displayTicket(data.result);
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
	 * 티켓  display
	 */
	var displayTicket = function(data){
		$myTicketContainer.html("");
		for( var i=0; i < data.length; i++){
			 var item = data[i];
			 if( item["balNo"] === null || item["balNo"] === "0" ){
				 data.splice(i,1);
			 }
		}
		
		//발급한 번호표가 없으면
		if( data === undefined || data === null || data.length == 0 ){
			var ticket = $( "<div></div>" ).addClass( "each_ticket" ),
				msg = $( "<span></span>" ).text( self.getI18n( "ticket007" ) );
			
			$myTicketContainer.append( ticket.html(msg) );
			
			//없으면 발급받기로 바로 이동
			if( flag ){
				flag = false;
				$(".r_type[data-type=1]").trigger("click");
				$ticketContainer.hide();
				$issueContainer.hide();
				$machineContainer.show();
			}
		//번호표 있으면 - 동적으로 구성
		} else {
			
			for( var i=0; i < data.length; i++ ){
				 var item = data[i],
				 	 ticket = $( "<div></div>" ).addClass( "each_ticket" ).attr( "id",item["seqCd"] ),
				 	 div = $( "<div></div>" ),
				 	 span = $( "<span></span>" );
				 //발급기 - 업무
				 var machine = span.clone().addClass( "for_machine" ).text( item["locationNm"] + " - " + item["jobNm"] );			 
				 ticket.append( div.clone().html( machine ) );
				 
				 //번호표
				 var number = span.clone().addClass( "for_ticekt" ).text( item["balNo"] );
				 ticket.append( div.clone().html( number ) );
				 
				 //대기
				 var waitLabel = span.clone().addClass( "for_wait label" ).html( self.getI18n( "ticket003" )+ "&nbsp;&#58;&nbsp;" ),
					 wait = span.clone().addClass( "for_wait input" ).html( item["delay"] ),
					 waitLabel2 = span.clone().addClass( "for_wait label" ).html( "&nbsp;&nbsp;"+ self.getI18n( "ticket006" ) );
				 ticket.append( div.clone().html( waitLabel ).append( wait ).append( waitLabel2 ) );
				 
				 //시간
				 var timeLabel = span.clone().addClass( "for_time label" ).html( self.getI18n( "ticket004" )+ "&nbsp;&#58;&nbsp;" ),
					 time = span.clone().addClass( "for_time input" ).text( getTimeString(item["date"]));
				 
				 ticket.append( div.clone().html( timeLabel ).append( time ) );
				 
				 var msg = span.clone().addClass( "" ).text( item["msg"] );
			 
				 ticket.append( div.clone().html( msg ) );				 
				 
				 $myTicketContainer.append( ticket ); 
			}
			self.util.numberFormat($(".for_wait.input"));
		}
		
		function getTimeString(timeStamp){
			var strTxt = "";
			
			var date = new Date(timeStamp),
				h = date.getHours(),
				min = date.getMinutes(),
				sec = date.getSeconds();
			hour = (h==0?'12':(h>12?h-12:h));
			
			return (h < 12 ? '오전' : '오후') + " "+ (hour<10?"0"+hour:hour) + ":"+ (min<10?"0"+min:min) + ":"+ (sec<10?"0"+sec:sec);
		}
	};
	
	
	/**
	 * 발급기 목록 리스트 초기화
	 * @description 순번기 목록 ( 암센터, B동 1층 등 )
	 */
	var initMachine = function(){
		self.loading( "show" );
		$.ajax({
			url: contextPath + "/mobile/ticket/getList.json",
			type: "POST",
			data:self.util.stringifyJson({}),
			dataType: "json",
			contentType: "application/json",
			success: function(data){
				try{
					if( data.msg !== undefined ){
						self.alert( data.msg );
						return;
					} else {
						//발급기 목록 메모리에 저장		
						machineArray = data.result;
						displayMachine( data.result );
					}
					
				} catch(e) {
					
				}
			},
			error:function(xhr){
				console.log(xhr);
			},
			complete: function(){
				self.loading( "hide" );
			}
		});
	};
	/**
	 * 발급기 목록 화면에 생성
	 * @description list 형태로 생성
	 */
	var displayMachine = function( data ){
		for( var i = 0; i < data.length; i++ ){
			 item = data[i],
			 li = $( "<li></li>" ),
			 a = $( "<a></a>" ).addClass( "machine_link" ).attr( {"data-seqCd": item["seqCd"], "data-sort":item["sort"], "data-hospitalCd":item["hospitalCd"] }),
			 span= $( "<span></span>" ).text( item["locationNm"] );
			 //display가 Y 면
			 if( item["display"] === "Y" ){				 
				 a.html( span ).appendTo( li );
				 $machineList.append( li );
			 }
		}
		$machineList.listview({inset:true});
		
		//발급기 선택 이벤트 콜백
		var callback = function(e){
			var id = $(this).attr( "data-seqCd" ),
				sort = $(this).attr( "data-sort" ),
				hospitalCd = $(this).attr( "data-hospitalCd" );
			setMachineId( id, sort, hospitalCd );
			showIssueBtn();
		}
		self.event.addEvent( $(".machine_link"),"click", callback );
	};
	/**
	 * 현재 선택된 발급기 메모리에 저장
	 * @param id 발급기 id
	 */
	var setMachineId = function( id, sort, hospitalCd ){
		issueMachine["id"] = id;
		issueMachine["sort"] = sort;
		issueMachine["hospitalCd"] = hospitalCd;
	};
	/**
	 * 발급기 목록에서 발급기 선택하면 발급 각 종류별 발급 버튼 보여주기
	 */
	var showIssueBtn = function(){
		$machineContainer.hide();
		$issueContainer.show();
		initIssueForm();
	};
	/**
	 * 각 종류별 발급 버튼 보여줄때 현재 대기 상황 설정
	 * @description 선택된 순번기의 현재 상황 및 발급 가능한 업무 버튼
	 */
	var initIssueForm = function(){
		self.loading( "show" );
		$.ajax({
			url:contextPath + "/mobile/ticket/getDeskList.json",
			method:"POST",
			data:self.util.stringifyJson({seqCd:issueMachine["id"]}),
			dataType:"json",
			contentType : "application/json",
			success:function(data){
				try{
					if( data.msg !== undefined ){
						self.alert( data.msg );
						return;
					} else {					
						setMachineName();
						displayIssueButton( data.result );
					}
					
				} catch(e) {
					
				}
			},
			error:function(xhr){
				console.log(xhr);
			},
			complete: function(){
				self.loading( "hide" );
			}
		});
	};
	/**
	 * 현재 선택된 순번기 이름 설정
	 */
	var setMachineName = function(){
		var loc = "";
		for( var i = 0; i < machineArray.length; i++ ){
			 if( issueMachine["id"] === machineArray[i]["seqCd"] ){
				 loc = machineArray[i]["locationNm"];
			 }
		}
		$loc.text( loc );
	};
	/**
	 * 발급 버튼 동적 생성
	 */
	var displayIssueButton = function( data ){
		if( data === null || data.length === 0 ){
			$(".issue_btn_wrapper").html("");
		} else {
			$(".issue_btn_wrapper").html("");
			for (var i = 0; i < data.length; i++) {
				 var item = data[i],
				 	 li = $("<li></li>").addClass("issue_btn_li"),
				 	 div = $("<div></div>"),
				 	 a = $("<a></a>").addClass("issue_btn ui-link")
				 	 .attr({
				 				 "data-jobCd":item["jobCd"],
				 				 "data-sort":item["sort"],
				 				 "data-hospitalCd":item["hospitalCd"],
				 				 "data-state" : item["state"]
				 			 }),
				 	 span = $("<span></span>");
				 
				 var issueType = span.clone().html( item["jobNm"] ),
				 	 wait = span.clone().html( self.getI18n("ticket003") + "&nbsp;" ),
				 	 waitNum = span.clone().addClass("wait_issue").html(item["delay"]!=null?item["delay"]+"&nbsp;":"0" + "&nbsp;"),
				 	 person = span.clone().html( self.getI18n("ticket006") );
				 
				var btn = li.html( div.html( a.html( issueType ).append( "<br/>" ).append( wait ).append( waitNum ).append( person ) ) );
				
				$(".issue_btn_wrapper").append(btn);
			}
			//번호표 발급 이벤트
			var callback = function(e){
				//서비스 준비중 2016-01-18
//				self.alert("서비스 준비중입니다.",function(){
//					
//				});
				var jobCd = $(this).attr("data-jobCd"),
					sort = $(this).attr("data-sort"),
					hospitalCd = $(this).attr("data-hospitalCd"),
					state = $(this).attr("data-state");
				//이전 발급 내역이 있으면 재발급할지 발급 못함 메시지를 던져 줄지
				if( state === "Y" ){
					isValidTicket(e, jobCd, sort, hospitalCd);
				//이전 내역 없으면 바로 발급 신청
				} else {					
					issueTicket(e, jobCd, sort, hospitalCd);
				}
			};
			self.event.addEvent( $(".issue_btn"),"click", callback );
		}
		
		$issueContainer.show();
	};
	/**
	 * 재발급 유무 체크
	 * @description 이전 발급 받은 번호표가 있으면 유효한지 재발급해야 하는지 확인
	 */
	var isValidTicket = function(e, jobCd, sort, hospitalCd){
		var param = {
				in_instcd: hospitalCd,
				in_seq_no: issueMachine["id"],
				in_chu_gubn: jobCd,
				in_sort: sort
		};
		self.loading( "show" );
		$.ajax({
			url:contextPath + "/mobile/ticket/isValidTicket.json",
			method:"POST",
			data:self.util.stringifyJson(param),
			contentType:"application/json",
			dataType:"json",
			success: function(data){
				try{
					if( data.msg !== undefined ){
						self.alert( data.msg );
						return;
					} else if( data["result"]["state"] === "Y" ){
						issueTicket(e, jobCd, sort, hospitalCd);
					} else {
						self.alert( self.getI18n("ticket014"), function(){
							//내번호표 버튼 클릭 이벤트 트리거
							$type.filter("[data-type=0]").trigger("click");
						});
						return;
					}
					
				} catch(e) {
					
				}
			},
			error: function(xhr){
				console.log( xhr );
				self.loading( "hide" );
			}
		});
	};
	/**
	 * 발급 실행
	 * @description 발급 실행
	 */
	var issueTicket = function(e, jobCd, sort, hospitalCd ){
		var param = {
				in_instcd: hospitalCd,
				in_seq_no: issueMachine["id"],
				in_chu_gubn: jobCd,
				in_sort: sort
		};
		self.loading( "show" );
		$.ajax({
			url:contextPath + "/mobile/ticket/issueTicket.json",
			method:"POST",
			data:self.util.stringifyJson(param),
			contentType:"application/json",
			dataType:"json",
			success: function(data){
				try{
					if( data.type !== undefined && data.type === "TimeLimitException" ){
						self.alert( data.msg , function(){
							//self.changePage( contextPath + data.nextPage );
						});
						return;
					}
					if( data.msg !== undefined ){
						self.alert( data.msg );
						return;
					} else {
						checkIssue();
					}
					
				} catch(e) {
					
				}
			},
			error: function(xhr){
				console.log( xhr );
				self.loading( "hide" );
			}
		});
	};
	/**
	 * 발급 여부 체크 로직
	 * @description 번호표 발급 후 실제 발급까지 딜레이가 있어서 10초 정도 발급 여부를 체크함 (3초 3번)
	 * 발급되면 내 번호표를 보여줌
	 */
	var checkIssue = function(){
		var index = 0;
		callCheckFn();
		/**
		 * 
		 */
		function callCheckFn(){
			$.ajax({
				url:contextPath + "/mobile/ticket/checkIssue.json",
				method:"GET",
				contentType:"application/json",
				dataType:"json",
				async:false,
				success: function(data){
					try{
						if( data.msg !== undefined ){
							self.alert( data.msg );
							return;
						} else if(data.result !== undefined && data.result.length === 0){
							//내번호표 버튼 클릭 이벤트 트리거
							$type.filter("[data-type=0]").trigger("click");
							self.loading( "hide" );
						} else if( index === 3 ){
							self.alert( self.getI18n("ticket015"),function(){
								//내번호표 버튼 클릭 이벤트 트리거
								$type.filter("[data-type=1]").trigger("click");
							});
							return;
						} else {
							index++;
							setTimeout(function(){							
								callCheckFn();
							},3000);
						}
						
					} catch(e) {
						
					}
				},
				error: function(xhr){
					console.log( xhr );
					self.loading( "hide" );
				}
			});
		}
	};
};