<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/mobile/user/registerPWD.css"/>" />
<script type="text/javascript" src="<c:url value='/resources/js/mobile/user/registerPWD.js' />"></script>
<div data-role="content">
	<!-- 사용자 인증이 완료된 정보 -->
	<input type="hidden" id="pId" value="${pId}"/>
	<input type="hidden" id="pName" value="${pName}"/>
	<input type="hidden" id="userBirthDate" value="${userBirthDate}"/>
	<input type="hidden" id="userGenderCode" value="${userGenderCode}"/>
	<div class="userWrap">
		<h3 class="subTitle"> <i class="fa fa-user"></i>&nbsp;${pName } ( ${pId } )</h3>
	</div>
	<!--
	<h3 class="subTitle"> <i class="fa fa-thumbs-o-up"></i> <s:message code="mobile.view.registerPWD017" /></h3>
	<div>
		<div class="elWrap">
			<div class="formDiv">
				<input type="text" id="employee" data-clear-btn="turn" value="" placeholder=" <s:message code="mobile.message.registerPWD018" />" />
				<input type="hidden" id="hEmployee" value="" />
			</div>
			<div class="elCont">
				<ul class="elList">
					
				</ul>
			</div>
			<div class="elScreen"></div>
		</div>
	</div>
	-->
	<div>
		<h3 class="subTitle"> <i class="fa fa-info-circle"></i>&nbsp;<s:message code="mobile.view.registerPWD013"/></h3>
	</div>
	<input type="password" id="pass2" placeholder="<s:message code="mobile.view.registerPWD002"/>"/>
	<div>
		<h3 class="subTitle"> <i class="fa fa-info-circle"></i>&nbsp;<s:message code="mobile.view.registerPWD014"/></h3>
	</div>
	<input type="password" id="pass3" placeholder="<s:message code="mobile.view.registerPWD003"/>"/>
	<button id="btn1"  class="ui-btn ui-btn-b"><span><s:message code="mobile.view.common001"/></span></button>
	<div class="mt" >
		<ul class="ps" style="padding:0 15px;">
			<li><s:message code="mobile.view.registerPWD004"/></li>
			<li><s:message code="mobile.view.registerPWD009"/></li>
			<li><s:message code="mobile.view.registerPWD010"/></li>
			<li><s:message code="mobile.view.registerPWD011"/></li>
			<li><s:message code="mobile.view.registerPWD012"/></li>
		</ul>
	</div>
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
		"registerPWD005" : "<s:message code='mobile.message.registerPWD005'/>",
		"registerPWD006" : "<s:message code='mobile.message.registerPWD006'/>",
		"registerPWD007" : "<s:message code='mobile.message.registerPWD007'/>",
		"registerPWD008" : "<s:message code='mobile.message.registerPWD008'/>"
	};
	this.getMessage = function( code ){
		return message[code];
	};
};

$(document).on("pagebeforeshow", function(e, ui) {
	var registerPWD = new mcare_mobile_registerPWD();
	registerPWD.init();
});
</script>
