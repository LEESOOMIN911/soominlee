<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/mobile/announceArrival/announceArrival.css"/>" />
<script type="text/javascript" src="<c:url value='/resources/js/mobile/announceArrival/announceArrival.js' />"></script>
<!-- 도착알림 -->
<div data-role="content">
	<div class="mainContainer">
		<div class="fir_con">
			<div class="targetContainer"></div>
			
			<div class="btnArea">
				<a href="#" class="ui-btn ui-btn-b arrivalBtn">
					<s:message code="mobile.source.announceArrival007" />
				</a>
			</div>
		</div>
		<div class="sec_con" style="margin:10px 0;">
			<div class="next_container">
				
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">
//다국어 사용
var i18n = function(){
	var message = {
			"common005" : "<s:message code='mobile.message.common005'/>",
			"common006" : "<s:message code='mobile.message.common006'/>",
			"announceArrival001":"<s:message code='mobile.view.announceArrival001'/> ",
			"announceArrival002":"<s:message code='mobile.view.announceArrival002'/> ",
			"announceArrival003":"<s:message code='mobile.view.announceArrival003'/> ",
			"announceArrival004":"<s:message code='mobile.view.announceArrival004'/> ",
			"announceArrival005":"<s:message code='mobile.message.announceArrival005'/> ",
			"announceArrival006":"<s:message code='mobile.source.announceArrival006'/> ",
			"announceArrival007":"<s:message code='mobile.source.announceArrival007'/> ",
			"announceArrival008":"<s:message code='mobile.message.announceArrival008'/> ",
			"announceArrival009":"<s:message code='mobile.message.announceArrival009'/> ",
			"announceArrival010":"<s:message code='mobile.message.announceArrival010'/> "
	};
	
	this.getMessage = function( code ){
		return message[code];
	};
};
$(document).on("pagebeforeshow", function(e, ui) {
	var announceArrival = new mcare_mobile_announceArrival();
	announceArrival.setGlobal( announceArrival );
	announceArrival.init();
});
$(document).on("pageshow",function(e){
	$.mobile.resetActivePageHeight(); 
});
</script>

