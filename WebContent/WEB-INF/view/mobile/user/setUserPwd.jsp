<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/mobile/user/resetPWD.css"/>" />
<script type="text/javascript" src="<c:url value='/resources/js/mobile/user/resetPWD.js' />"></script>
<div data-role="content">
	<!-- 사용자 인증이 완료된 정보 -->
	<input type="hidden" id="pId" value="${pId}"/>
	<input type="hidden" id="pName" value="${pName}"/>
	<input type="hidden" id="userBirthDate" value="${userBirthDate}"/>
	<input type="hidden" id="userGenderCode" value="${userGenderCode}"/>
	<input type="hidden" id="resetPWDType" value="setUserPwd"/>
	
	<!-- 현재비밀번호가 없으면 표시하지 않음 -->
	<div>
		<h3 class="subTitle"> <i class="fa fa-info-circle"></i>&nbsp;<s:message code="mobile.view.resetPWD017"/></h3>
	</div>
	<input type="password" id="pass2" placeholder="<s:message code="mobile.view.resetPWD002"/>"/>
	<div>
		<h3 class="subTitle"> <i class="fa fa-info-circle"></i>&nbsp;<s:message code="mobile.view.resetPWD018"/></h3>
	</div>
	<input type="password" id="pass3" placeholder="<s:message code="mobile.view.resetPWD003"/>"/>
	<div class="mt" >
		<ul class="ps" style="padding:0 15px;">
			<li><s:message code="mobile.view.resetPWD004"/></li>
			<li><s:message code="mobile.view.resetPWD012"/></li>
			<li><s:message code="mobile.view.resetPWD013"/></li>
			<li><s:message code="mobile.view.resetPWD014"/></li>
			<li><s:message code="mobile.view.resetPWD015"/></li>
		</ul>
	</div>
	<button id="btn1" class=" ui-btn ui-btn-b"><span><s:message code="mobile.view.common001"/></span></button>
</div>

<script type="text/javascript"> 
//다국어 사용
var i18n = function(){
	var message = {
		"common001" : "<s:message code='mobile.view.common001'/>",
		"common006" : "<s:message code='mobile.message.common006'/>",
		"common013" : "<s:message code='mobile.message.common013'/>",
		"common014" : "<s:message code='mobile.message.common014'/>",
		"common015" : "<s:message code='mobile.message.common015'/>",
		"common016" : "<s:message code='mobile.message.common016'/>",
		"common017" : "<s:message code='mobile.message.common017'/>",
		"common018" : "<s:message code='mobile.message.common018'/>",
		"common019" : "<s:message code='mobile.message.common019'/>",
		"common024" : "<s:message code='mobile.message.common024'/>",
		"resetPWD005" : "<s:message code='mobile.message.resetPWD005'/>",
		"resetPWD006" : "<s:message code='mobile.message.resetPWD006'/>",
		"resetPWD007" : "<s:message code='mobile.message.resetPWD007'/>",
		"resetPWD008" : "<s:message code='mobile.message.resetPWD008'/>",
		"resetPWD009" : "<s:message code='mobile.message.resetPWD009'/>",
		"resetPWD010" : "<s:message code='mobile.message.resetPWD010'/>",
		"resetPWD011" : "<s:message code='mobile.message.resetPWD011'/>"
	};
	this.getMessage = function( code ){
		return message[code];
	};
};

$(document).on("pagebeforeshow", function(e, ui) {
	var resetPWD = new mcare_mobile_resetPWD();
	resetPWD.init();
});
</script>
