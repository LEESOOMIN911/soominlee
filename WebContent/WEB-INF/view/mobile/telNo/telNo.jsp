<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/mobile/telNo/telNo.css"/>" />
<script type="text/javascript" src="<c:url value='/resources/js/mobile/telNo/telNo.js' />"></script>
<!-- 주요전화번호 -->
<div data-role="content">
	<div class="mainContainer">
		<div class="fir_con">
			<form class="ui-filterable">
				<input id="telNo_search" data-type="search" placeholder="search"  />
			</form>
			<ul class="telNoList" data-role="listview" data-filter="true" data-inset="true" data-input="#telNo_search">
			
			</ul>
		</div>
		<div class="sec_con hide">
			<a href="#" class="moreBtn ui-btn ui-btn-b"><s:message code="mobile.view.common004"/></a>
		</div>
	</div>
</div>
<script type="text/javascript">
//다국어 사용
var i18n = function(){
	var message = {
		"common005":"<s:message code='mobile.message.common005'/> ",
		"common006" : "<s:message code='mobile.message.common006'/>"
	};
	
	this.getMessage = function( code ){
		return message[code];
	};
};
$(document).on("pagebeforeshow",function(e){
	var telNo = new mcare_mobile_telNo();
	telNo.init();
	$(":jqmData(role=header)").toolbar({position:"fixed",tapToggle: false});
});
$(document).on("pageshow",function(e){
	$.mobile.resetActivePageHeight(); 
});
</script>