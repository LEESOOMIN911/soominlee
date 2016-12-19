<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/mobile/user/authUserInfo.css"/>" />
<script type="text/javascript" src="<c:url value='/resources/js/mobile/user/authUserInfo.js' />"></script>
<!-- 비밀번호 찾기 -->
<div data-role="content">
	<div class="mainContainer">
		<div class="fir_con">
			<div>
				<h3 class="subTitle"> <i class="fa fa-info-circle"></i> <s:message code="mobile.view.authUserInfo002" /></h3>
			</div>
			<input type="number" pattern="\d*" class="for_pid" placeholder="<s:message code="mobile.message.authUserInfo007" />" />
			<div>
				<h3 class="subTitle"> <i class="fa fa-info-circle"></i> <s:message code="mobile.view.authUserInfo003" /></h3>
			</div>
			<input type="text" class="for_name" placeholder="<s:message code="mobile.message.authUserInfo008" />" />
		</div>
		<div class="sec_con">
			<div>
				<a href="#" class="ui-btn ui-btn-b sendSMS">
					<s:message code="mobile.view.authUserInfo006" />
				</a> 
			</div>
		</div>
	</div>
</div>

<script type="text/javascript"> 
var i18n = function(){
	var message = {
		"common006" : "<s:message code='mobile.message.common006'/>",
		"authUserInfo007" : "<s:message code='mobile.message.authUserInfo007'/>",
		"authUserInfo008" : "<s:message code='mobile.message.authUserInfo008'/>",
		"authUserInfo009" : "<s:message code='mobile.message.authUserInfo009'/>",
		"authUserInfo010" : "<s:message code='mobile.message.authUserInfo010'/>",
		"authUserInfo011" : "<s:message code='mobile.message.authUserInfo011'/>",
		"authUserInfo014" : "<s:message code='mobile.message.authUserInfo014'/>",
		"authUserInfo015" : "<s:message code='mobile.message.authUserInfo015'/>",
		"authUserInfo017" : "<s:message code='mobile.message.authUserInfo017'/>"
	};
	
	this.getMessage = function( code ){
		return message[code];
	};
};

$(document).on("pagebeforeshow", function(e, ui) {
	var authUserInfo = new mcare_mobile_authUserInfo();
	authUserInfo.init();
});
</script>

