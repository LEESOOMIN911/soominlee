/**
 * 결제
 */
var mcare_mobile_receipt = function(){
	mcare_mobile.call(this);
	
	var self = this;
	var $pTime = $(".pTime"),
		$quota = $(".quota");
	
	
	//이전 화면에서 전달 받은 파라미터 
	var parameterMap = null;
	
	this.init = function(){
		//파라미터 설정
		setParameterMap();
		restoreData();
		addEvent();
	};
	
	var addEvent = function(){
		
	};
	/**
	 * 파라미터 맵 초기화
	 */
	var setParameterMap = function(){
		parameterMap = self.util.getParameter();
	};
	var restoreData = function(){
		//승인일시
		var str = $pTime.text(),
			yy = str.substr(0,2),
			MM = str.substr(2,2),
			dd = str.substr(4,2),
			hh = str.substr(6,2),
			mm = str.substr(8,2),
			ss = str.substr(10,2);
		$pTime.text( "20"+yy+"-"+MM+"-"+dd+" "+hh+":"+mm+":"+ss );
		
		//할부개월
		var quota = $quota.text();
		if( quota === "00" ){
			$quota.text( self.getI18n("receipt016") );
		} else {
			$quota.text( quota + self.getI18n("receipt017") );
		}
		
		//금액
		self.util.numberFormat($("span.amt"));
	};
};