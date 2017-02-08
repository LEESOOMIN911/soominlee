<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<script type="text/javascript" src="<c:url value='/resources/js/mobile/user/certificationResult.js' />"></script>
<!-- 휴대폰 인증 성공-->
<div data-role="content">
	<div class="mainContainer">
		<input type="hidden" name="pName" id="pName" value="${sName}">
		<input type="hidden" name="userBirthDate" id="userBirthDate" value="${sBirthDate}">
		<input type="hidden" name="userGenderCode" id="userGenderCode" value="${sGenderCode}">
		<input type="hidden" name="reservedParam1" id="reservedParam1" value="${sReserved1}">
		<input type="hidden" name="reservedParam2" id="reservedParam2" value="${sReserved2}">
		<input type="hidden" name="reservedParam3" id="reservedParam3" value="${sReserved3}">
		<input type="hidden" name="errorMsgCode" id="errorMsgCode" value="${errorMsgCode}">
	</div>
</div>

<script type="text/javascript">
//다국어 사용
var i18n = function(){
	var message = {
		"common006" : "<s:message code='mobile.message.common006'/>",
		"iPinCertification001" : "<s:message code='mobile.message.iPinCertification001'/>",
		"iPinCertification002" : "<s:message code='mobile.message.iPinCertification002'/>",
		"iPinCertification003" : "<s:message code='mobile.message.iPinCertification003'/>",
		"iPinCertification004" : "<s:message code='mobile.message.iPinCertification004'/>",
		"iPinCertification005" : "<s:message code='mobile.message.iPinCertification005'/>",
		"iPinCertification006" : "<s:message code='mobile.message.iPinCertification006'/>",
		"iPinCertification007" : "<s:message code='mobile.message.iPinCertification007'/>",
		"checkPlusCertification001" : "<s:message code='mobile.message.checkPlusCertification001'/>",
		"checkPlusCertification002" : "<s:message code='mobile.message.checkPlusCertification002'/>",
		"checkPlusCertification003" : "<s:message code='mobile.message.checkPlusCertification003'/>",
		"checkPlusCertification004" : "<s:message code='mobile.message.checkPlusCertification004'/>",
		"checkPlusCertification005" : "<s:message code='mobile.message.checkPlusCertification005'/>",
		"checkPlusCertification006" : "<s:message code='mobile.message.checkPlusCertification006'/>",
		"checkPlusCertification007" : "<s:message code='mobile.message.checkPlusCertification007'/>",
		"checkPlusCertification008" : "<s:message code='mobile.message.checkPlusCertification008'/>"
	};
	
	this.getMessage = function( code ){
		return message[code];
	};
};
$(document).on("pagebeforeshow", function(e, ui) {
	certificationResult = new mcare_mobile_certification_result();
	certificationResult.init();
});

</script>

