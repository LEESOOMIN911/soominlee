<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/mobile/user/registerWithoutPId.css"/>" />
<script type="text/javascript" src="<c:url value='/resources/js/mobile/user/registerWithoutPId.js' />"></script>
<!-- 신상정보로 사용등록 -->
<div data-role="content">
	<h3 class="subTitle"> <i class="fa fa-info-circle"></i> <s:message code="mobile.view.registerWithoutPId001" /></h3>
	<div>
		<div>
			<div class="formDiv">
				<input type="text" id="pName" data-clear-btn="turn" value="" placeholder=" <s:message code="mobile.message.registerWithoutPId002" />" />
			</div>
		</div>
	</div>
	<h3 class="subTitle"> <i class="fa fa-info-circle"></i> <s:message code="mobile.view.registerWithoutPId003" /></h3>
	<div>
		<div>
			<div class="formDiv">
				<input type="tel" pattern="[0-9]*" maxlength="6" id="birth" data-clear-btn="turn" value="" placeholder=" <s:message code="mobile.message.registerWithoutPId004" />" />
			</div>
		</div>
	</div>
	<h3 class="subTitle"> <i class="fa fa-info-circle"></i> <s:message code="mobile.view.registerWithoutPId005" /></h3>
	<div>
		<div>
			<div class="formDiv">
				<input type="tel" pattern="\d*" id="phoneNum" data-clear-btn="turn" value="" placeholder=" <s:message code="mobile.message.registerWithoutPId006" />" />
			</div>
		</div>
	</div>
    <button id="next" class="ui-btn ui-btn-b"><s:message code="mobile.view.registerWithoutPId007" /></button>
    <div class="mt">
		<ul style="padding:0 15px;">
			<li><s:message code="mobile.view.registerWithoutPId008" /></li>
			<li><s:message code="mobile.view.registerWithoutPId009" /></li>
		</ul>
	</div>
</div>

<script type="text/javascript">
//다국어 사용
var i18n = function(){
	var message = {
		"common006" : "<s:message code='mobile.message.common006'/>",
		"registerWithoutPId002" : "<s:message code='mobile.message.registerWithoutPId002' />",
		"registerWithoutPId004" : "<s:message code='mobile.message.registerWithoutPId004' />",
		"registerWithoutPId006" : "<s:message code='mobile.message.registerWithoutPId006' />",
		"register015" : "<s:message code='mobile.message.register015'/>",
		"register016" : "<s:message code='mobile.message.register016'/>",
		"register017" : "<s:message code='mobile.message.register017'/>"
	};
	this.getMessage = function( code ){
		return message[code];
	};
};
$(document).on("pagebeforeshow", function(e, ui) {
	var registerWithoutPId = new mcare_mobile_registerWithoutPId();
	registerWithoutPId.init();
});
</script>

