<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/mobile/nurse/nurse.css"/>" />
<script type="text/javascript" src="<c:url value='/resources/js/mobile/nurse/nurse.js' />"></script>
<!-- 간호호출 -->
<div data-role="content">
	<div class="mainContainer">
		<div class="fir_con">
			<h3><i class="fa fa-info-circle"></i>&nbsp;<span class="subTitle"><s:message code="mobile.view.nurse006"/></span></h3>	
		</div>
		<div class="sec_con">
			<div>
				<fieldset data-role="controlgroup" data-type="horizontal">
					<input type="checkbox" id="check_1" name="check"/>
					<label for="check_1"><s:message  code="mobile.view.nurse001"/></label>
					<input type="checkbox" id="check_2" name="check"/>
					<label for="check_2"><s:message  code="mobile.view.nurse002"/></label>
					<input type="checkbox" id="check_3" name="check"/>
					<label for="check_3"><s:message  code="mobile.view.nurse003"/></label>
				</fieldset>
				
			</div>
			<div>
				<textarea placeholder="<s:message  code="mobile.view.nurse005"/>" id="etc_request" style="height:150px;"></textarea>
			</div>
		</div>
		<div class="thi_con">
			<!-- 
			<div class="ui-grid-a">
				<div class="ui-block-a">
					<a href="#" class="ui-btn ui-btn-b requestBtn"><s:message  code="mobile.view.nurse004"/></a>
				</div>
				<div class="ui-block-b">
					<a href="#" class="ui-btn ui-btn-b searchBtn"><s:message  code="mobile.view.nurse008"/></a>
				</div>
			</div>
			-->
			<a href="#" class="ui-btn ui-btn-b requestBtn"><s:message  code="mobile.view.nurse004"/></a>
		</div>
	</div>
</div>

<script type="text/javascript">
//다국어 사용
var i18n = function(){
	var message = {
		"common006" : "<s:message code='mobile.message.common006'/>",
		"nurse006":"<s:message  code='mobile.source.nurse006'/>",
		"nurse007":"<s:message  code='mobile.source.nurse007'/>",
		"nurse009":"<s:message  code='mobile.message.nurse009'/>",
		"nurse010":"<s:message  code='mobile.message.nurse010'/>"
	};
	
	this.getMessage = function( code ){
		return message[code];
	};
};
$(document).on("pagebeforeshow", function(e, ui) {
	var nurse = new mcare_mobile_nurse();
	nurse.init();
});
$(document).on("pageshow",function(e){
	$.mobile.resetActivePageHeight(); 
});
</script>

