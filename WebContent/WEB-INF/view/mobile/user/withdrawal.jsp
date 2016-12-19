<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/mobile/user/withdrawal.css"/>" />
<script type="text/javascript" src="<c:url value='/resources/js/mobile/user/withdrawal.js' />"></script>
<!-- 회원탈퇴 -->
<div data-role="content">
	<div class="mainContainer">
		<div class="fir_con">
			<ul>
				<li><s:message code="mobile.view.withdrawal001"/></li>
				<li><s:message code="mobile.view.withdrawal002"/></li>
				<li><s:message code="mobile.view.withdrawal008"/></li>
				<li><s:message code="mobile.view.withdrawal009"/></li>
			</ul>
		</div>
		<div class="sec_con">
			<input type="password" id="pwdInput" placeholder="<s:message code="mobile.view.withdrawal003"/>" />
			<button id="withdrawalBtn" class=" ui-btn ui-btn-b"><span><s:message code="mobile.view.withdrawal004"/></span></button>
		</div>
	</div>	
</div>
<script type="text/javascript">
//다국어 사용
var i18n = function(){
	var message = {
		"common006" : "<s:message code='mobile.message.common006'/>",
		"withdrawal005" : "<s:message code='mobile.message.withdrawal005'/>",
		"withdrawal006" : "<s:message code='mobile.message.withdrawal006'/>",
		"withdrawal007" : "<s:message code='mobile.message.withdrawal007'/>",
		"withdrawal010" : "<s:message code='mobile.message.withdrawal010'/>"
	};
	this.getMessage = function( code ){
		return message[code];
	};
};
$(document).on("pagebeforeshow", function(e, ui) {
	var withdrawal = new mcare_mobile_withdrawal();
	withdrawal.init(); 
});
</script>