<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/mobile/user/smsCertification.css"/>" />
<script type="text/javascript" src="<c:url value='/resources/js/mobile/user/smsCertification.js' />"></script>
<!-- 환자번호 찾기 -->
<div data-role="content">
	<div class="mainContainer">
		<div class="fir_con">
			<div>
				<h3 class="subTitle"> <i class="fa fa-info-circle"></i> <s:message code="mobile.view.smsCertification005" /></h3>
			</div>
			<div>
				<input type="text" id="smsCode" class="for_name" placeholder="<s:message code="mobile.view.smsCertification004" />" />
				<input type="hidden" id="userName" value="${userName}" class="for_name" />
				<input type="hidden" id="reservedParam3" value="${reservedParam3}" class="for_name" />
			</div>
		</div>
		<div class="sec_con">
			<div>
				<a href="#" id="btn" class="ui-btn ui-btn-b sendSMS">
					<s:message code="mobile.view.smsCertification005" />
				</a> 
			</div>
		</div>
	</div>
</div>

<script type="text/javascript"> 

var i18n = function(){
	var message = {
		"common006" : "<s:message code='mobile.message.common006'/>",
		"smsCertification008" : "<s:message code='mobile.message.smsCertification008'/>",
		"smsCertification009" : "<s:message code='mobile.message.smsCertification009'/>",
		"smsCertification010" : "<s:message code='mobile.message.smsCertification010'/>"
	};
	
	this.getMessage = function( code ){
		return message[code];
	};
};

$(document).on("pagebeforeshow", function(e, ui) {
	var smsCertification = new mcare_mobile_smsCertification();
	smsCertification.init();
});
</script>

