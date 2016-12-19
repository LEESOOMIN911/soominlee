<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/mobile/appointmentSearch/appointmentSearch.css"/>" />
<script type="text/javascript" src="<c:url value='/resources/js/mobile/appointmentSearch/appointmentSearch.js' />"></script>
<!-- 예약조회 -->
<div data-role="content">
	<div class="mainContainer">
		<div class="fir_con">
			<div class="form_div">
				<div class="form_label">
					<span>
						<s:message code="mobile.view.appointmentSearch001"/>
					</span>
				</div>
				<div class="form_input">
					<input type="date" class="for_strDate"/><a href="#" class="dateBtn sDate"><i class="fa fa-calendar"></i></a>
				</div>
			</div>
			<div class="form_div">
				<div class="form_label">
					<span>
						<s:message code="mobile.view.appointmentSearch002"/>
					</span>
				</div>
				<div class="form_input">
					<input type="date" class="for_endDate" /><a href="#" class="dateBtn eDate"><i class="fa fa-calendar"></i></a>
				</div>
			</div>
		</div>
		<div class="sec_con">
			<div class="mt">
				<a href="#" class="ui-btn ui-btn-b retreiveBtn">
					<s:message code="mobile.view.common003"/>
				</a>
			</div>
		</div>
		<div class="thi_con" style="display:none;">
			<h3 class="resulttitle" >&bull; <s:message code="mobile.view.common010" /></h3>
			<div class="resultContainer">
					
			</div>
		</div>
		<div class="fou_con">
			<div class="moreContainer" style="display:none;">
				<a href="#" class="moreBtn ui-btn ui-btn-b"><s:message code="mobile.view.common004"/></a>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">
//다국어 사용
var i18n = function(){
	var message = {
			"common005":"<s:message code='mobile.message.common005'/> ",
			"common006" : "<s:message code='mobile.message.common006'/>",
			"common007":"<s:message code='mobile.message.common007'/> ",
			"common008":"<s:message code='mobile.message.common008'/> ",
			"common009":"<s:message code='mobile.message.common009'/> ",
			"appointmentSearch003" : "<s:message code='mobile.source.appointmentSearch003'/>",
			"appointmentSearch004" : "<s:message code='mobile.source.appointmentSearch004'/>",
			"appointmentSearch005" : "<s:message code='mobile.source.appointmentSearch005'/>",
			"appointmentSearch006" : "<s:message code='mobile.source.appointmentSearch006'/>",
			"appointmentSearch007" : "<s:message code='mobile.source.appointmentSearch007'/>",
			"appointmentSearch008" : "<s:message code='mobile.source.appointmentSearch008'/>",
			"appointmentSearch009" : "<s:message code='mobile.source.appointmentSearch009'/>",
			"appointmentSearch010" : "<s:message code='mobile.message.appointmentSearch010'/>",
			"appointmentSearch011" : "<s:message code='mobile.message.appointmentSearch011'/>",
			"appointmentSearch012" : "<s:message code='mobile.source.appointmentSearch012'/>"
	};
	
	this.getMessage = function( code ){
		return message[code];
	};
};
$(document).on("pagebeforeshow", function(e, ui) {
	var appointmentSearch = new mcare_mobile_appointmentSearch();
	appointmentSearch.init();
	$(":jqmData(role=header)").toolbar({position:"fixed",tapToggle: false});
});
$(document).on("pageshow",function(e){
	$.mobile.resetActivePageHeight(); 
});
</script>

