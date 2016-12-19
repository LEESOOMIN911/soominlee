/**
 * 
 */
$(document).ready(function(){
	/**
	 * ajax 요청시 409 에러가 발생하면 명시적으로 이동시키는 부분을 정의 
	 */
	$(document).ajaxSuccess(function(event,xhr,opts,data){
		
		if( $("[data-type=409]", data ).length > 0 ){
			window.location.href = contextPath + "/error409.page";
		}
	});
	$(document).ajaxError(function(event,xhr,opts,e){
		
		if( $("[data-type=409]", xhr.responseText ).length > 0 ){
			window.location.href = contextPath + "/error409.page";
		}
	});
});