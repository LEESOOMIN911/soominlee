<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/mobile/mobileCard/mobileCard.css"/>" />
<script type="text/javascript" src="<c:url value='/resources/js/mobile/mobileCard/mobileCard.js' />"></script>
<script type="text/javascript" src="<c:url value='/resources/plugins/barcode/jquery-barcode.min.js' />"></script>
<!-- 모바일바코드 -->
<div data-role="content">
	<div class="mainContainer mobilecard">
		<!-- 바코드 transform90 -->
		<div class="fir_con">
			<div class="cardContainer">
				<div id="barcode" class="transform90"></div>
			</div>		 
		</div>	
		<!-- 환자정보 -->
		<div class="sec_con">
			<div class="mh">
				<s:message code="mobile.view.mobileCard001" />
			</div>
			<div class="mdata" style="padding-top:25px;">
				<span class="patientNum">
					${sessionScope.MCARE_USER_ID }
				</span>
			</div>
			<div class="mh">
				<s:message code="mobile.view.mobileCard002" />
			</div>
			<div class="mdata" style="padding-bottom:25px;">
				<span class="patientName">
					${sessionScope.MCARE_USER_NAME }			
				</span>			
			</div>
		</div>		
	</div>
</div>

<script type="text/javascript">
//다국어 사용
var i18n = function(){
	var message = {
		"common006" : "<s:message code='mobile.message.common006'/>"
	};
	
	this.getMessage = function( code ){
		return message[code];
	};
};
$(document).on("pagebeforeshow", function(e, ui) {
	var mobileCard = new mcare_mobile_card('${sessionScope.MCARE_USER_ID }');
	mobileCard.init();
});
$(document).on("pageshow",function(e){
	$.mobile.resetActivePageHeight(); 
});
</script>