<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/mobile/user/sms.css"/>" />
<script type="text/javascript" src="<c:url value='/resources/js/mobile/user/sms.js' />"></script>
<div data-role="content">
	<div class="mainContainer">
		<div class="fir_con">
			<div>
				<h3 class="subTitle"> <i class="fa fa-info-circle"></i> <s:message code="mobile.view.smsCertification003" /></h3>
			</div>
			<div>
				<input type="text" id="pNm" class="for_name" placeholder="<s:message code="mobile.view.smsCertification001" />" />
			</div>
		</div>
		<div class="sec_con">
			<div>
				<a href="#" id="btn" class="ui-btn ui-btn-b sendSMS">
					<s:message code="mobile.view.smsCertification003" />
				</a> 
			</div>
		</div>
		<div class="fir_con">
			<div>
				<h3 class="subTitle"> <i class="fa fa-info-circle"></i> <s:message code="mobile.view.smsCertification005" /></h3>
			</div>
			<div>
				<input type="number" pattern="\d*" id="smsCode" class="for_name" placeholder="<s:message code="mobile.view.smsCertification004" />" />
			</div>
		</div>
		<div class="sec_con">
			<div>
				<a href="#" id="btn2" class="ui-btn ui-btn-b sendSMS">
					<s:message code="mobile.view.smsCertification005" />
				</a> 
			</div>
		</div>
	</div>
	<input type="hidden" name="param_r3" id="pid" value="${param.pid}">
	<input type="hidden" name="certReqType" id="certReqType" value="${param.certReqType}">
	<input type="hidden" name="encodePid" id="encodePid">
	<input type="hidden" name="encodePname" id="encodePname">
</div>

<script type="text/javascript"> 

var i18n = function(){
	var message = {
		"common006" : "<s:message code='mobile.message.common006'/>",
		"smsCertification006" : "<s:message code='mobile.message.smsCertification006'/>",
		"smsCertification007" : "<s:message code='mobile.message.smsCertification007'/>",
		"smsCertification008" : "<s:message code='mobile.message.smsCertification008'/>",
		"smsCertification009" : "<s:message code='mobile.message.smsCertification009'/>",
		"smsCertification010" : "<s:message code='mobile.message.smsCertification010'/>",
		"smsCertification013" : "<s:message code='mobile.message.smsCertification013'/>",
		"smsCertification014" : "<s:message code='mobile.message.smsCertification014'/>"
	};
	
	this.getMessage = function( code ){
		return message[code];
	};
};

$(document).on("pagebeforeshow", function(e, ui) {
	var sms = new mcare_mobile_sms();
	sms.init();
});
</script>

