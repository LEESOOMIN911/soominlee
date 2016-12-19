<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/mobile/user/userAgreement.css"/>" />
<script type="text/javascript" src="<c:url value='/resources/js/mobile/user/userAgreement.js' />"></script>
<!-- 동의서 -->
<div data-role="content">
	<div class="mainContainer">
	</div>	
	<div class="btnContainer">
		<div>
			<button id="saveUserAgreement" class="ui-btn ui-btn-b">
				<span><s:message code="mobile.view.agreement001"/></span>
			</button>
		</div>
	</div>
</div>
<script type="text/javascript">
//다국어 사용
var i18n = function(){
	var message = {
		"common006" : "<s:message code='mobile.message.common006'/>",
		"agreement001" : "<s:message code='mobile.view.agreement001'/>",
		"agreement002" : "<s:message code='mobile.view.agreement002'/>",
		"agreement005" : "<s:message code='mobile.source.agreement005'/>",
		"agreement006" : "<s:message code='mobile.source.agreement006'/>",
		"agreement008" : "<s:message code='mobile.source.agreement008'/>",
		"agreement009" : "<s:message code='mobile.message.agreement009'/>",
		"agreement010" : "<s:message code='mobile.message.agreement010'/>",
		"agreement011" : "<s:message code='mobile.message.agreement011'/>"
	};
	this.getMessage = function( code ){
		return message[code];
	};
};
$(document).on("pagebeforeshow", function(e, ui) {
	var userAgreement = new mcare_mobile_user_agreement();
	userAgreement.init(); 
});
</script>