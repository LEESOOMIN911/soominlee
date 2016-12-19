<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/mobile/reservation/reservation.css"/>" />
<script type="text/javascript" src="<c:url value='/resources/js/mobile/reservation/reservation.js' />"></script>
<!-- 예약 -->
<div data-role="content">
	<div class="mainContainer">
		<div class="fir_con">
			<div class="form_div">
				<div class="form_label">
					<span>
						<s:message code="mobile.view.reservation002" />
					</span>
				</div>
				<div class="form_input mt">
					<select class="r_gubun" id="r_gubun">
					</select>
				</div>
			</div>
			<div class="form_div">
				<div class="form_label">
					<span>
						<s:message code="mobile.view.reservation003" />
					</span>
				</div>
				<div class="form_input">
					<select class="r_doctor" id="r_doctor" disabled="disabled">
					</select>
				</div>
			</div>
			<div class="form_div">
				<div class="form_label">
					<span>
						<s:message code="mobile.view.reservation004" />
					</span>
				</div>
				<div class="form_input">
					<select class="r_date" id="r_date" disabled="disabled"></select>
				</div>
			</div>
			<div class="form_div">
				<div class="form_label">
					<span>
						<s:message code="mobile.view.reservation005" />
					</span>
				</div>
				<div class="form_input">
					<select class="r_time" id="r_time" disabled="disabled"></select>
				</div>
			</div>
			<div class="form_div">
				<div class="form_label">
					<span>
						<s:message code="mobile.view.reservation006" />
					</span>
				</div>
				<div class="form_input">
					<textarea class="r_symptom"></textarea>
				</div>
			</div>
		</div>
		<div class="sec_con">
			<div>
				<a href="#" id="reservationBtn" class="ui-btn ui-btn-b">
					<s:message code="mobile.view.reservation007" />
				</a>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
//다국어 사용
var i18n = function(){
	var message = {
		"common006" : "<s:message code='mobile.message.common006'/>",
		"reservation001" : "<s:message code='mobile.view.reservation001'/>",
		"reservation002" : "<s:message code='mobile.view.reservation002'/>",	
		"reservation003" : "<s:message code='mobile.view.reservation003'/>",
		"reservation004" : "<s:message code='mobile.view.reservation004'/>",
		"reservation005" : "<s:message code='mobile.view.reservation005'/>",
		"reservation006" : "<s:message code='mobile.view.reservation006'/>",
		"reservation008" : "<s:message code='mobile.source.reservation008'/>",
		"reservation009" : "<s:message code='mobile.source.reservation009'/>",
		"reservation010" : "<s:message code='mobile.source.reservation010'/>",
		"reservation011" : "<s:message code='mobile.message.reservation011'/>",
		"reservation012" : "<s:message code='mobile.message.reservation012'/>",
		"reservation013" : "<s:message code='mobile.message.reservation013'/>",
		"reservation014" : "<s:message code='mobile.message.reservation014'/>",
		"reservation015" : "<s:message code='mobile.message.reservation015'/>",
		"reservation016" : "<s:message code='mobile.message.reservation016'/>",
		"reservation017" : "<s:message code='mobile.message.reservation017'/>",
		"reservation018" : "<s:message code='mobile.message.reservation018'/>"
	};
	
	this.getMessage = function( code ){
		return message[code];
	};
};
$(document).on("pagebeforeshow", function(e, ui) {
	if( e.target.dataset.role !== "dialog"){		
		var reservation = new mcare_mobile_reservation();
		reservation.init();	
	}
});
$(document).on("pageshow",function(e){
	$.mobile.resetActivePageHeight(); 
});
</script>

