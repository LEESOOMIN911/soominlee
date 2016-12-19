/**
 * mcare_mobile_nextLocation
 */
var mcare_mobile_nextLocation = function(){
	//상속
	mcare_mobile.call(this);
	//super
	var self = this;
	//변수
	var $resultData = $(".resultData");
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
	 * 조회
	 */
	var retreive = function(){
		var today = new Date(),
			text = self.util.getDateText( today );
		var param = {
			"date" : text
		};
		self.loading("show");
		$.ajax({
			url:contextPath + "/mobile/nextLocation/next.json",
			method:"POST",
			data:self.util.stringifyJson(param),
			dataType:"json",
			contentType:"application/json",
			success:function(data){
				try{
					if(data.msg !== undefined){
						self.alert(data.msg,function(){
							$resultData.html("").text("시스템 에러가 발생했습니다.");
						});
						return false;
					} else {					
						displayData(data.result);
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
	 * 화면 표시
	 */
	var displayData = function(data){

		$resultData.html("");

		if( data === undefined ||data === null || data.length == 0 || $.isEmptyObject(data) ){

			self.alert( self.getI18n("nextLocation001"), function(){
//				self.changePage( contextPath + "/index.page" );
				$("#headerArrowLeft_btn")[0].click();
			});
		} else {
			
			$(".noResult").hide();
			for( var i = 0; i < data.length; i++ ){
				var item = data[i],
				div = $("<div></div>"),
				h3 = $("<h3></h3>"),
				ul = $( "<ul></ul>" ).addClass( "nextList" );
				
				var next = div.clone().attr({"id":"next_"+i, "data-role":"collapsible"} ).html( h3.html( item["departmentNm"] +" - " + item["doctorNm"] ) );
				if( i === 0 ){
					next.attr("data-collapsed",false);
				} else {
					next.attr("data-collapsed",true);
				}
				var nextLocationList = item["nextLocationList"][0],
				nextListWrapper = div.clone().addClass("nextListWrapper");
				for( var key in nextLocationList ){
					var dept = nextLocationList[key],
					li = $( "<li></li>" ).addClass("navi"),
					span = $( "<span></span>" ),
					icon = $( "<i></i>" ).addClass( "fa fa-map-marker" ).css("float","right");
					li.html ( span.attr("data-loc",dept).html( key ) ).append( icon );
					
					ul.append( li );
				}
				next.append( nextListWrapper.html( ul ) );
				$resultData.append( next );
			}
			$(".nextList").listview();
			$(".nextList").listview( "refresh" );
			$resultData.collapsibleset( "refresh" );
			var callback = function(e){ 
				openMap(self.getI18n("nextLocation002"), "", $(this).find("span").attr("data-loc")); 
			};
			self.event.addEvent($(".navi"),"click", callback);
		}
	};
};