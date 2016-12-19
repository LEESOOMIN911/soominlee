/**
 * mcare_mobile_index
 * @param menulist  메뉴 정보 리스트
 */
var mcare_mobile_index = function( menulist, id ){
	//상속
	mcare_mobile.call(this);
	//super
	var self = this;
	//변수
	var $swiperWrapper = $( ".swiper-wrapper" ),
		$helperWrapper = $(".helper_wrapper");
	
	var menuSwiper = null;
	//index.jsp에 정의한 메뉴 정보를 담은 리스트를 초기화에서 생성자로 사용
	var menuList = menulist,
		pId = id;
	
	/**
	 * 초기화
	 */
	this.init = function(){
		initMenuList();
		initSwipe();
		//로그인 했으면 최근 도우미 메시지 가져오기
		if( pId !== "" ){			
			getRecentMessage();
		}
		addEvent();
	};
	/**
	 * 이벤트 등록
	 */
	var addEvent = function(){
		//swipe되면 hash에 저장
		$swiperWrapper.on("afterChange",function(e,slick,cs){
			sessionStorage.setItem("nowMenu","#menu" + cs);
		});
		//swipe 초기화하고 위치 잡으면 높이와 버튼 반전 이벤트
		$swiperWrapper.on("setPosition",function(e,slick){
			$.mobile.resetActivePageHeight();
			var callback = function(e){
				$(this).addClass("active");
			};
			self.event.addEvent($(".menu_btn_wrapper"),"click",callback);
		});
		//도우미 버튼 반전
		$helperWrapper.on("click",function(e){
			$(this).addClass("active");
		});
	};
	/**
	 * 메뉴 구성
	 * jstl을 사용하던것을 swipe를 붙이면서 동적 구성이 필요함에 따라 스크립트로 변경함
	 * 데이터는 jsp에서 객체를 스크립트로 변경하여 사용함.
	 * index 페이지에서 menuList 전역변수는 메뉴 구성에 필요한 정보를 담고 있음.
	 */
	var initMenuList = function(){
		for( var i = 0; i < menuList.length; i++ ){
			 var item = menuList[i];
			
			 var position = $("<div></div>"),
				 btnWrapper = $("<div></div>").addClass( "menu_btn_wrapper" ),
				 menuLink = (item.accessUriAddr).indexOf("javascript")>=0?item.accessUriAddr:contextPath + item.accessUriAddr+"?menuId="+item.menuId+"&info=index",
			 	 a = $( "<a></a>" ).addClass( "menu_link" ).attr( {"href": menuLink,"data-ajax":false} ),
			 	 img = $( "<img></img>" ).addClass( "menu_icon" ).attr( { "src":item.imageUriAddr, "alt" : item.menuName } ),
			 	 span = $( "<span></span>" ).addClass( "menu_name" ).text( item.menuName );
			 //2개씩 좌우로 배치
			 if( i%2 === 0 ){
				 position.addClass( "menu_left" );
				 	 
			 } else {
				 position.addClass( "menu_right" );
			 }
			 a.html( img ).append( span ).appendTo( btnWrapper );
			 position.html( btnWrapper );
			 //현재 마지막으로 붙어 있는 slide에 메뉴를 붙인다
			 $swiperWrapper.append( position );
		};
		$(".menu_left").each(function(i){
			$(this).find('.menu_btn_wrapper').addClass('bgColorL'+i);
		});
		$(".menu_right").each(function(i){
			$(this).find('.menu_btn_wrapper').addClass('bgColorR'+i);
		});
		
	};
	/**
	 * swipe 초기화
	 */
	var initSwipe = function(){
		var hash = sessionStorage.getItem("nowMenu");
		if( hash === "" || hash === null || hash === undefined ){
			hash = 0;
		} else {
			hash = parseInt( hash.replace("#menu","") );
		}
		var option = {
				  dots: true,
				  infinite: false,
				  speed: 300,
				  arrows:false,
				  slidesPerRow:3,
			      slidesToShow: 1,
			      slidesToScroll: 1,
			      initialSlide:hash,
				  rows:3,
				  responsive: [
				    {
				      breakpoint: 1024,
				      settings: {
				    	  rows:3,
				    	  slidesPerRow:3,
					      slidesToShow: 1,
					      slidesToScroll: 1
				      }
				    },
				    {
				      breakpoint: 801,
				      settings: {
						  rows:3,
						slidesPerRow:3,
				        slidesToShow: 1,
				        slidesToScroll: 1
				      }
				    },
				    {
				      breakpoint: 481,
				      settings: {
						rows:3,
						slidesPerRow:2,
				        slidesToShow: 1,
				        slidesToScroll: 1
				      }
				    }
				  ]
			};
		self.swipe( $swiperWrapper, option );
	};
	/**
	 * 최근 메시지 가져오기
	 */
	var getRecentMessage = function(){
		var jsonMsg = {
				type : "command",
				functionType : "getRecentMessage",
				value : {
					callbackFn : "window.activeObj.callbackRecentMessage",
					pId : pId //스코프안에서 전역선언된 환자번호
				}
		};
		self.toNative( jsonMsg );
	};
	/**
	 * 최근 메시지 콜백
	 */
	this.callbackRecentMessage = function(msg){
		console.log("callBack : " + msg ); 

		if( typeof msg === "string" ){			
			msg = self.util.parseJson( msg );
		}
		if( msg.success !== undefined && msg.success ){
			displayRecentMessage( msg.result );
		}
		if( msg.success !== undefined && !msg.success ){
			displayRecentMessage( "default" );
		}
	};
	/**
	 * 최근 메시지 표시
	 */
	var displayRecentMessage = function(data){
		//$(".helper_wrap .msg .time").text();
		
		if( data === "default"  || data === "" ){
			$(".helper_wrap .msg .recent_text").text( self.getI18n("index001") );			
		} else {
			$(".helper_wrap .msg .recent_text").text(data);			
		}
	};
};