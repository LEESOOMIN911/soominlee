<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/mobile/user/registerWithPId.css"/>" />
<script type="text/javascript" src="<c:url value='/resources/js/mobile/user/registerWithPId.js' />"></script>
<!-- 환자번호로 사용등록 -->
<div data-role="content">
	<h3 class="subTitle"> <i class="fa fa-info-circle"></i> <s:message code="mobile.view.registerWithPId001" /></h3>
	<div>
		<div>
			<div class="formDiv">
				<input type="number" pattern="\d*" id="pId" data-clear-btn="turn" value="" placeholder=" <s:message code="mobile.message.registerWithPId002" />"/>
			</div>
		</div>
	</div>
    <button id="next" class="ui-btn ui-btn-b"><s:message code="mobile.view.registerWithPId003" /></button>
	<div class="mt">
		<ul style="padding:0 15px;">
			<li><s:message code="mobile.view.registerWithPId004" /></li>
		</ul>
	</div>
    <input type="hidden" id="hPid" value="${pId}"/>
</div>

<script type="text/javascript">
//다국어 사용
var i18n = function(){
	var message = {
		"common006" : "<s:message code='mobile.message.common006'/>",
		"registerWithPId002" : "<s:message code='mobile.message.registerWithPId002' />",
		"register015" : "<s:message code='mobile.message.register015' />",
		"register016" : "<s:message code='mobile.message.register016' />",
		"register017" : "<s:message code='mobile.message.register017' />"
	};
	this.getMessage = function( code ){
		return message[code];
	};
};
$(document).on("pagebeforeshow", function(e, ui) {
	var registerWithPId = new mcare_mobile_registerWithPId();
	registerWithPId.init();
});
</script>

