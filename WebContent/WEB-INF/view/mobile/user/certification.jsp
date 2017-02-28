<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/mobile/user/certification.css"/>" />
<script type="text/javascript" src="<c:url value='/resources/js/mobile/user/certification.js' />"></script>
<div data-role="content">
	<ul data-role="listview" data-inset="true">
		<li>
			<a href="#" id="chechPlus">
				<span><s:message code="mobile.view.certification001"/></span><br/>
				<span class="described"><s:message code="mobile.view.certification002"/></span>
			</a>
		</li>
		<li>
			<a href="#" id="sms">
				<span>SMS</span><br/>
				<span class="described"><s:message code="mobile.view.certification003"/></span>
			</a>
		</li>
		<li>
			<a href="#" id="iPin">
				<span>i-Pin</span><br/>
				<span class="described"><s:message code="mobile.view.certification004"/></span>
			</a>
		</li>
	</ul>
	<div id="certi">
	</div>
	<div class="mt">
		<i class="fa fa-comment"></i>&nbsp;
		<span>
			<s:message code="mobile.view.certification005"/>&nbsp;
		</span>
		<span class="issueIpin">
			<s:message code="mobile.view.certification006"/>
		</span>
		<span>
			<s:message code="mobile.view.certification007"/>
		</span>
	</div>
	<div class="mn">
		<i class="fa fa-times-circle" style="width:15px;"></i>&nbsp;
		<span class="ps">
			<s:message code="mobile.view.certification008"/>
		</span>
	</div>
</div>

<form name="form_chk" id="form_check" method="post">
	<input type="hidden" name="m" id="m" value="">
	<input type="hidden" name="EncodeData" id="EncodeData" value="">	<!-- 인증 기관에서 EncodeData로 사용하기 때문에 이대로 둬야함. -->
	<input type="hidden" name="enc_data" id="enc_data" value="">
	<!-- param_1을 인증 타입 구분 값으로 사용 (ex:checkPlus = 휴대폰인증, iPin = iPin인증)-->
	<input type="hidden" name="param_r1" id=certType value="">
	<!-- param_2를 인증 요청 타입입을 사용 (ex:registerUser = 사용자 등록, searchId = 사용자 ID 찾기, resetPWD = 패스워드 재등록) -->
	<input type="hidden" name="param_r2" id="certReqType" value="${param.certReqType}">
	<input type="hidden" name="param_r3" id="pid" value="${param.pId}">
	<!-- 핸드폰 번호 -->
	<input type="hidden" name="phoneNo" id="phoneNo" value="${param.phoneNo}">
</form>

<script type="text/javascript">
//다국어 사용
var i18n = function(){
	var message = {
		"common006" : "<s:message code='mobile.message.common006'/>"
	};
	
	this.getMessage = function( code ){
		return message[code];
	};
};
$(document).on("pagebeforeshow", function(e, ui) {
	var certification = new mcare_mobile_certification();
	certification.init();
});
</script>

