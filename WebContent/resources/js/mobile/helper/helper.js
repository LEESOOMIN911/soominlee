/**
 * mcare_mobile_helper
 */
var mcare_mobile_helper = function(){
	//상속
	mcare_mobile.call(this);
	//super
	var self = this;
	//변수
	var $messageContainer = $(".messageContainer"),
		$statusBreadcrumb = $(".status-breadcrumb");
	/**
	 * 
	 */
	var dataArray = null;
	/**
	 * 초기화
	 */
	this.init = function(){
		retrieve();
		addEvent();
	};
	/**
	 * 이벤트 등록
	 */
	var addEvent = function(){
		
	};
	/**
	 * 도우미 정보 조회
	 */
	var retrieve = function(){
		self.loading("show");
		var today = new Date(),
			param = {
			startDate : self.util.getDateText( today ),
			endDate : self.util.getDateText( today )
		};
		$.ajax({
			url :  contextPath + "/mobile/helper/getList.json",
			method : "POST",
			data : self.util.stringifyJson(param),
			dataType:"json",
			contentType:"application/json",
			success:function(data){
				try{
					if( data.msg !== undefined ){
						self.alert(data.msg);
						return;
					} else {					
						initHelper( data.result );
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
	 * 현재 진료 상태 표시
	 */
	var setStatus = function(){
		
		var status = parseInt( dataArray[dataArray.length-1]["stage"] );
		var li = $statusBreadcrumb.find("li");
		$(li).removeClass("current");
		switch( status ){
			case 10 : $(li[0]).addClass("current");break;
			case 20 : $(li[1]).addClass("current");break;
			case 30 : $(li[2]).addClass("current");break;
			case 40 : $(li[3]).addClass("current");break;
		}
	};
	/**
	 * 도우미 초기화
	 */
	var initHelper = function( data ){
		$messageContainer.html("");
		var msgArray = dataArray = restoreData( data );
		
		if( msgArray.length === 0 ){
			addMessage( {title: self.getI18n("helper005"),time:""}, 0 );
			return;
		}
		//메시지 추가
		for( var i = 0; i < msgArray.length; i++ ){			
			 addMessage( msgArray[i], i );
		}
		setTimeout(function(){			
			$messageContainer.scrollTop( $messageContainer[0].scrollHeight );
		},500);
		//링크 클릭 이벤트
		var callback = function(e){
			$(this).addClass("active");
			var index = $(this).attr("data-index"),
				param = dataArray[parseInt(index)];
			
			self.toNative(param["menuAction"]);
		};
		self.event.addEvent( $(".goBtn"), "click", callback );
		setStatus();
	};
	/**
	 * 디바이스 저장 데이터를 사용하도록 가공
	 */
	var restoreData = function( data ){
		var returnArray = new Array();
		for( var i = 0; i < data.length; i++ ){
			 var obj = {
					 date : "",
					 time : "",
					 title : "",
					 menuName : "",
					 link : "",
					 hospitalName : ""
			 	},
			 	 item = data[i],
			 	 msg = self.util.parseJson( item["userMsg"] );
			 
			 //날짜
			 obj["date"] = item["sendDt"].split(" ")[0];
			 //시간
			 obj["time"] = (item["sendDt"].split(" ")[1]).substr(0,5);
			 if( msg["title"] !== undefined ){				 
				 //title
				 obj["title"] = msg["title"];
			 }
			 if( msg["menuName"] !== undefined ){
				 //menuName
				 obj["menuName"] = msg["menuName"];				 
			 }
			 if( msg["menuAction"] !== undefined ){				 
				 //menuAction
				 obj["menuAction"] = msg["menuAction"];
			 } 
			 if( msg["menuAction"] !== undefined && msg["menuAction"]["value"] !== undefined && msg["menuAction"]["value"]["url"] !== undefined ){				 
				 //link
				 obj["link"] = msg["menuAction"]["value"]["url"];
			 }
			 if( msg["stage"] !== undefined ){
				 obj["stage"] = msg["stage"];
			 }
			 if( msg["hospitalName"] !== undefined ){
				 obj["hospitalName"] = msg["hospitalName"];
			 }
			 returnArray.push( obj );
		}
		
		return returnArray;
	};
	/**
	 * 메시지 화면 구성
	 */
	var addMessage = function( data, i ){
		
		var template = getTemplate( data, i );
		
		$messageContainer.append( template );
	};
	/**
	 * 메시지 템플릿
	 */
	var getTemplate = function( data, index ){
		var text = '';
		text += '<div class="message_contents">';
		text += '<div class="message_wrapper doctor">';
		text += '<div class="message_main">';
		text += '<p class="message_body">';
		text += data["title"];
		if( data["menuAction"] !== undefined ){
			text += '<a href="#" data-index="'+ index +'" class="goBtn">'+ data["menuName"] +'</a>';
		}
		text += '<span class="bArrow"></span>';
		text += '</p>';
		text += '</div>';
		text += '</div>';
		text += '<span class="message_aside">';
		if( data["hospitalName"] !== "" ){			
			text += '<span class="message_hospital">';
			text += data["hospitalName"];
			text += '</span>';
		}
		text += '<span class="message_time">';
		text += data["time"];
		text += '</span>';
		text += '</span>';
		text += '</div>';
		
		return text;
	}
}