/**
 * mcare_mobile_card 
 */
var mcare_mobile_card = function(pId){
	//상속
	mcare_mobile.call(this);
	//super
	var self = this;
	//변수
	var $barcode = $("#barcode"),
		$patientName = $(".patientName"),
		$patientNum = $(".patientNum"),
		$dept = $(".dept");
	/**
	 * 초기화
	 */
	this.init = function(){
		initBarcode();
		addEvent();
	};	
	/**
	 * 이벤트 등록
	 */
	var addEvent = function(){
		//화면 방향 이벤트
		$(window).on("orientationchange",function(e){
			// 세로화면 
			if($(document).width() < $(document).height()) {
				// 돌려서 오른쪽에 붙게 
				$barcode.removeClass("moveBottom");
				$barcode.addClass("transform90");
				$(".sec_con").removeClass("moveBottom");
			}
			// 가로화면 
			else {
				// 화면 아랫쪽에 붙게 
				$barcode.removeClass("transform90");
				$barcode.addClass("moveBottom"); 
				$(".sec_con").addClass("moveBottom");
			}
		});
	};
	/**
	 * 바코드 초기화 - 라이브러리 호출
	 */
	var initBarcode = function(){
		$barcode.barcode(pId,"code39",{barWidth:2, barHeight:200}); 
	};
};