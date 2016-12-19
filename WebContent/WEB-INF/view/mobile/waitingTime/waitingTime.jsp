<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/mobile/waitingTime/waitingTime.css"/>" />
<script type="text/javascript" src="<c:url value='/resources/js/mobile/waitingTime/waitingTime.js' />"></script>
<!-- 진료대기시간조회 -->
<div data-role="content">
	<div class="mainContainer">
		<div class="zero_con">
			<div class="timeWrapper">
				<div class="rTime" style="text-align:right;">
					<span><s:message code='mobile.view.waitingTime010'/>&nbsp;&#58;&nbsp;<span id="nowTime"></span></span>
				</div>
			</div>
		</div>
		<div class="fir_con">
			
		</div>
		<div class="sec_con">
			<div class="btnArea">
				<a href="#" class="ui-btn ui-btn-b refreshBtn">
					<s:message code="mobile.view.waitingTime004" />
				</a>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">
//다국어 사용
var i18n = function(){
	var message = {
			"common006" : "<s:message code='mobile.message.common006'/>",
			"waitingTime001":"<s:message code='mobile.view.waitingTime001'/> ",
			"waitingTime002":"<s:message code='mobile.view.waitingTime002'/> ",
			"waitingTime003":"<s:message code='mobile.view.waitingTime003'/> ",
			"waitingTime004":"<s:message code='mobile.view.waitingTime004'/> ",
			"waitingTime005":"<s:message code='mobile.message.waitingTime005'/> ",
			"waitingTime006":"<s:message code='mobile.message.waitingTime006'/> ",
			"waitingTime007":"<s:message code='mobile.view.waitingTime007'/> ",
			"waitingTime008":"<s:message code='mobile.view.waitingTime008'/> ",
			"waitingTime009":"<s:message code='mobile.view.waitingTime009'/> ",
			"waitingTime011":"<s:message code='mobile.view.waitingTime011'/> ",
			"waitingTime012":"<s:message code='mobile.view.waitingTime012'/> ",
			"waitingTime013":"<s:message code='mobile.view.waitingTime013'/> "
	};
	
	this.getMessage = function( code ){
		return message[code];
	};
};
$(document).on("pagebeforeshow", function(e, ui) {
	var waitingTime = new mcare_mobile_waitingTime();
	waitingTime.init();
});
$(document).on("pageshow",function(e){
	$.mobile.resetActivePageHeight(); 
});
</script>

