<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/mobile/user/under14.css"/>" />
<script type="text/javascript" src="<c:url value='/resources/js/mobile/user/under14.js' />"></script>
<!-- 14세 미만 인증 -->
<div data-role="content">
	<div class="mainContainer">
		<div class="fir_con">
			<div class="notice">
    			<p class="ps"><s:message code="mobile.view.under14001" /></p>
    		</div>
		</div>
		<div class="sec_con">
    		<div class="titleDiv">
				<h3 class="subTitle"> <i class="fa fa-info-circle"></i> <s:message code="mobile.view.under14012" /></h3>
			</div>
			<div>
				<input type="text" id="pName" class="for_name" placeholder="<s:message code="mobile.view.under14013" />" />
			</div>
    		<div>
				<a href="#" class="ui-btn ui-btn-b sendSMS">
					<s:message code="mobile.view.under14003" />
				</a> 
			</div>
			<div>
    			<p><s:message code="mobile.view.under14002" /></p>
    		</div>
			<div class="titleDiv">
				<h3 class="subTitle"> <i class="fa fa-info-circle"></i> <s:message code="mobile.view.under14004" /></h3>
			</div>
			<div>
				<input type="number" pattern="\d*" id="smsCode" class="for_name" placeholder="<s:message code="mobile.view.under14005" />" />
			</div>
			<div>
				<a href="#" class="ui-btn ui-btn-b checkCode">
					<s:message code="mobile.view.under14006" />
				</a> 
			</div>
		</div>
	</div>
</div>
<input type="hidden" name="param_r3" id="pId" value="${param.pId}">
<input type="hidden" name="certReqType" id="certReqType" value="${param.certReqType}">
<input type="hidden" name="phoneNo" id="phoneNo" value="${param.phoneNo}">
<input type="hidden" name="encodePid" id="encodePid">
<input type="hidden" name="encodePname" id="encodePname">
<script type="text/javascript"> 
var i18n = function(){
	var message = {
		"common006" : "<s:message code='mobile.message.common006'/>",
		"under14001" : "<s:message code='mobile.view.under14001'/>",
		"under14002" : "<s:message code='mobile.view.under14002'/>",
		"under14003" : "<s:message code='mobile.view.under14003'/>",
		"under14004" : "<s:message code='mobile.view.under14004'/>",
		"under14005" : "<s:message code='mobile.view.under14005'/>",
		"under14006" : "<s:message code='mobile.view.under14006'/>",
		"under14007" : "<s:message code='mobile.view.under14007'/>",
		"under14008" : "<s:message code='mobile.view.under14008'/>",
		"under14009" : "<s:message code='mobile.view.under14009'/>",
		"under14013" : "<s:message code='mobile.view.under14013'/>"
	};
	
	this.getMessage = function( code ){
		return message[code];
	};
};

$(document).on("pagebeforeshow", function(e, ui) {
	var under14 = new mcare_mobile_under14();
	under14.init();
});
</script>

