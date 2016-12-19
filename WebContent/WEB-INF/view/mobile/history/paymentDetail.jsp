<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/mobile/history/paymentDetail.css"/>" />
<script type="text/javascript" src="<c:url value='/resources/js/mobile/history/paymentDetail.js' />"></script>
<!-- 이력조회 진료비내역 상세 -->
<div data-role="content">
	<div class="mainContainer">
		<div class="fir_con">
			<div class="paymentContainer">
				<table class="paymentTable"></table>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">
//다국어 사용
var i18n = function(){
	var message = {
		"common006" : "<s:message code='mobile.message.common006'/>",
		"paymentDetail001" : "<s:message code='mobile.view.paymentDetail001'/>",
		"paymentDetail002" : "<s:message code='mobile.view.paymentDetail002'/>",
		"paymentDetail003" : "<s:message code='mobile.view.paymentDetail003'/>",
		"paymentDetail004" : "<s:message code='mobile.view.paymentDetail004'/>",
		"paymentDetail005" : "<s:message code='mobile.view.paymentDetail005'/>",
		"paymentDetail006" : "<s:message code='mobile.view.paymentDetail006'/>",
		"paymentDetail007" : "<s:message code='mobile.view.paymentDetail007'/>",
		"paymentDetail008" : "<s:message code='mobile.view.paymentDetail008'/>",
		"paymentDetail009" : "<s:message code='mobile.source.paymentDetail009'/>"
	};
	
	this.getMessage = function( code ){
		return message[code];
	};
};
$(document).on("pagebeforecreate", function(e, ui) {
	var paymentDetail = new mcare_mobile_paymentDetail();
	paymentDetail.setGlobal( paymentDetail );
	paymentDetail.changeOrientation("landscape");
});
$(document).one("pageshow",function(e,ui){
	window.activeObj.init();
});
</script>

