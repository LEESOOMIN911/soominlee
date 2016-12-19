<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/mobile/prescription/prescription.css"/>" />
<script type="text/javascript" src="<c:url value='/resources/js/mobile/prescription/prescription.js' />"></script>
<!-- 투약처방조회 -->
<div data-role="content">
	<div class="mainContainer">
		<div class="fir_con">
			<div class="form_div">
				<div class="form_label">
					<span>
						<s:message code="mobile.view.prescription001" />
					</span>
				</div>
				<div class="form_input">
					<input type="date" class="for_strDate" /><a href="#" class="dateBtn sDate"><i class="fa fa-calendar"></i></a>
				</div>
			</div>
			<div class="form_div">
				<div class="form_label">
					<span>
						<s:message code="mobile.view.prescription002" />
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
			<h3 class="resulttitle" >&bull;&nbsp;<s:message code="mobile.view.common010"/></h3>
			<div class="resultContainer">
					
			</div>
		</div>  
		<div data-role="popup" id="effectPopup" data-overlay-theme="b" data-dismissible="false" style="min-height:100px;">
			<div data-role="content">
				<div class="popuptitle">
					<s:message code="mobile.view.prescription010"/>
					<a href="#" data-rel="back" class="ui-btn ui-corner-all ui-shadow ui-btn-a ui-icon-delete ui-btn-icon-notext ui-btn-right">close</a>
				</div>
				<div class="popupcontent">
					<div>
						<span>&bull;&nbsp;<b><s:message code="mobile.view.prescription008"/></b> : </span>
						<span class="effectCategoryNm"></span>
					</div>
					<div>
						<span>&bull;&nbsp;<b><s:message code="mobile.view.prescription009"/></b></span>
						<br/>
						<p class="effectNm"></p> 
					</div>
				</div>
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
		"prescription003" : "<s:message code='mobile.source.prescription003'/>",
		"prescription004" : "<s:message code='mobile.source.prescription004'/>",	
		"prescription005" : "<s:message code='mobile.source.prescription005'/>",
		"prescription006" : "<s:message code='mobile.source.prescription006'/>",
		"prescription007" : "<s:message code='mobile.source.prescription007'/>",
		"prescription011" : "<s:message code='mobile.source.prescription011'/>"
	};
	
	this.getMessage = function( code ){
		return message[code];
	};
};
$(document).on("pagebeforeshow", function(e, ui) {
	var prescription = new mcare_mobile_prescription();
	prescription.init();	
	$(":jqmData(role=header)").toolbar({position:"fixed",tapToggle: false});
});
$(document).on("pageshow",function(e){
	$.mobile.resetActivePageHeight(); 
});
</script>

