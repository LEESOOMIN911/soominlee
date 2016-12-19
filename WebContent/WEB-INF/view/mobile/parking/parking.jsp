<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/mobile/parking/parking.css"/>" />
<script type="text/javascript" src="<c:url value='/resources/js/mobile/parking/parking.js' />"></script>
<!-- 차량번호표시 -->
<div data-role="content">
	<div class="mainContainer">
		<div class="fir_con"> 
			<div class="titlewrap">
				<i class="fa fa-info-circle"></i>
				<span>
					<s:message code="mobile.view.parking001"/>
				</span>
			</div>
			<div class="msg_con">
				<div class="left_point"></div>
				<p class="ps mt" style="font-size:2.5pc;"></p>
				<div class="right_point"></div>
			</div>
		</div>
		<div class="sec_con">
			<h4 class="p_notice">
				<s:message  code="mobile.view.parking008"/>
			</h4>
			<div class="btnCon">
				<input type="text" class="carNum" maxlength="9"/>
			</div>
		</div>
		<div class="thi_con mt10">
			<a href="#" class="update ui-btn ui-btn-b" ><s:message code="mobile.view.common020"/></a>
		</div>
	</div>
</div>
<script type="text/javascript">
//다국어 사용
var i18n = function(){
	var message = {
		"common006" : "<s:message code='mobile.message.common006'/>",
		"parking002" : "<s:message code='mobile.source.parking002'/>",
		"parking003" : "<s:message code='mobile.source.parking003'/>",
		"parking004" : "<s:message code='mobile.source.parking004'/>",
		"parking005" : "<s:message code='mobile.message.parking005'/>",
		"parking006" : "<s:message code='mobile.message.parking006'/>",
		"parking007" : "<s:message code='mobile.message.parking007'/>"
	};
	
	this.getMessage = function( code ){
		return message[code];
	};
};
$(document).on("pagebeforeshow",function(e){
	var parking = new mcare_mobile_parking();
	parking.init();
});
$(document).on("pageshow",function(e){
	$.mobile.resetActivePageHeight(); 
});
</script>
