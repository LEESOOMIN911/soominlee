<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/mobile/user/payManagement.css"/>" />
<script type="text/javascript" src="<c:url value='/resources/js/mobile/user/payManagement.js' />"></script>
<!-- 결제 정보 관리 -->
<div data-role="content">
	<div class="mainContainer">
		<div class="scheduleWrapper">
			<!-- 
			<div id="pay1Cont" class="paySubWrapper">
				<div class="wrap-header">
					<h3><s:message code="mobile.view.payManagement001" /></h3>
				</div>
				<div class="wrap-body">
					<div class="resultContainer" >
						<div style="text-align:center;">
							<span>언제어디서나 설치없이 간편하게 ~ </span>
						</div>
					</div>
				</div>
			</div>
			-->
			<div id="pay2Cont" class="paySubWrapper">
				<div class="wrap-header">
					<h3>
						<s:message code="mobile.view.payManagement002" />
						<span style="float:right;"><i class="fa fa-external-link" aria-hidden="true"></i></span>
					</h3>
				</div>
				<div class="wrap-body">
					<!-- 동적 구현 -->
					<div class="resultContainer">
						<div style="text-align:center;">
							<span>단 하나의 결제, 단 한번의 SSGPay</span>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
//다국어 사용
var i18n = function(){
	var message = {
			"common005":"<s:message code='mobile.message.common005'/> ",
			"common006" : "<s:message code='mobile.message.common006'/>",
			"payManagement003" : "<s:message code='mobile.view.payManagement003'/>"
	};
	
	this.getMessage = function( code ){
		return message[code];
	};
};
$(document).on("pagebeforeshow",function(e){
	var payManagement = new mcare_mobile_payManagement();
	payManagement.init();
});
$(document).on("pageshow",function(e){
	$.mobile.resetActivePageHeight(); 
});
</script>
