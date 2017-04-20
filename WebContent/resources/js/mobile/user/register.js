/**
 * mcare_mobile_register
 * 모바일 사용등록
 * 
 */
var mcare_mobile_register = function() {
	//상속
	mcare_mobile.call(this);
	//super
	var self = this;
	//변수
	
	var parameterMap = null;
	/**
	 * 초기화
	 */
	this.init = function() {

		addEvent();
	};
	/**
	 * 파라미터 맵 초기화
	 */
	var setParameterMap = function(){
		parameterMap = self.util.getParameter();
	};
	/**
	 * 이벤트 등록
	 */
	var addEvent = function() {

	};
};