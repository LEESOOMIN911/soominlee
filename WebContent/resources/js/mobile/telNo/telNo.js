/**
 * mobile/telNo
 */
var mcare_mobile_telNo = function(){
	//상속
	mcare_mobile.call(this);
	//super
	var self = this;
	//변수
	var $telNoList = $(".telNoList"),
		$crudServiceBaseUrl = contextPath + "/mobile/telno";
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
		
	};
	/**
	 * 전화번호 목록 조회
	 */
	var retreive = function(){
		self.loading("show");
		
		$.ajax({
			url : $crudServiceBaseUrl + "/getList.json",
			type : "GET",
			dataType : "json",
			contentType : "application/json",
			success : function(data){
				try{
					if( data.msg !== undefined ){
						self.alert(data.msg);
						return false;
					} else {					
						displayData( data.result );
					}
					
				} catch(e) {
					
				}
			},
			error : function(xhr,d,t){
				console.log( xhr );
			},
			complete : function(){
				self.loading("hide");
			}
		})
	};
	/**
	 * 전화번호 display
	 */
	var displayData = function( data ){
		$telNoList.html("");
		if( data === undefined || data === null || data.length === 0 ){
			var li = $("<li></li>"),
			 	div = $("<div></div>");
				li.html( div.text( self.getI18n( "common005" ) ) );
				$telNoList.html( li );
		} else {			
			for( var i = 0; i < data.length; i++ ){
				 var li = $("<li></li>"),
					 div = $("<div></div>"),
					 div1 = $("<div class='teltitle'></div>"),
					 div2 = $("<div class='telNo'></div>"),
					 span = $("<span></span>"),
					 a = $("<a></a>").removeClass('ui-btn-icon-right ui-icon-carat-r'),
					 //imgSpan = $("<span></span>").addClass("ui-icon-phone ui-btn-icon-notext"),
					 item = data[i],
					 buildingDesc = ( item.buildingDesc===null )?"":item["buildingDesc"],
					 roomDesc = ( item.roomDesc===null )?"":item["roomDesc"];
		
				li.attr("data-filtertext",buildingDesc + " " + roomDesc + "," + item["telValue"] );
				span.text( buildingDesc + " " + roomDesc );
				a.attr("href" , "tel:"+ (item["telValue"]).replace(/\-/gi,"") );
				div1.text( buildingDesc + " " + roomDesc );
				div2.text( restoreNum(item["telValue"]) ); 
				//div.html( span ).append( a );
				//li.html( div );
				a.prepend(div1);
				a.append(div2)
				li.append( a );
				
				$telNoList.append( li );
			}
		}
		$telNoList.listview();
		$telNoList.listview("refresh");
		
			
	};
	/**
	 * 번호 뒷자리만 보이도록
	 */
	var restoreNum = function(number){
		return number.split("-")[2];
	};
};