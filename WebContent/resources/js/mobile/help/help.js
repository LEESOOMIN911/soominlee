/**
 * mcare_mobile_help
 */
var mcare_mobile_help = function(){
	//상속
	mcare_mobile.call(this);
	//super
	var self = this;
	//변수
	var $swiperWrapper = $(".help");
	/**
	 * 초기화
	 */
	this.init = function(){
		var option = {
				  dots: true,
				  infinite: false,
				  speed: 300,
				  arrows:false,
				  slidesPerRow:1,
			      slidesToShow: 1,
			      slidesToScroll: 1,
			      initialSlide:0,
				  rows:1
			};
		//coach mark swipe
		self.swipe( $swiperWrapper, option );
	};
};