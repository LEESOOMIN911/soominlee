<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/mobile/user/searchPId.css"/>" />
<script type="text/javascript" src="<c:url value='/resources/js/mobile/user/searchPId.js' />"></script>
<!-- 환자번호 찾기 -->
<div data-role="content">
	<div class="mainContainer">
		<div class="fir_con">
			<div>
				<h3 class="subTitle"> <i class="fa fa-info-circle"></i> <s:message code="mobile.view.searchPId002" /></h3>
			</div>
			<input type="text" class="for_name" placeholder="<s:message code="mobile.message.searchPId006" />" />
			<div>
				<h3 class="subTitle"> <i class="fa fa-info-circle"></i> <s:message code="mobile.view.searchPId003" /></h3>
			</div>
			<input type="tel" class="for_tel" placeholder="<s:message code="mobile.message.searchPId007" />"/>
			<div>
				<p class="ps mt">
					<s:message code="mobile.view.searchPId004" />
				</p>
			</div>
		</div>
		<div class="sec_con">
			<div>
				<a href="#" class="ui-btn ui-btn-b sendSMS">
					<s:message code="mobile.view.searchPId005" />
				</a> 
			</div>
		</div>
	</div>
</div>

<script type="text/javascript"> 
var i18n = function(){
	var message = {
		"common006" : "<s:message code='mobile.message.common006'/>",
		"searchPId006" : "<s:message code='mobile.message.searchPId006'/>",
		"searchPId007" : "<s:message code='mobile.message.searchPId007'/>",
		"searchPId008" : "<s:message code='mobile.message.searchPId008'/>",
		"searchPId012" : "<s:message code='mobile.message.searchPId012'/>"
	};
	
	this.getMessage = function( code ){
		return message[code];
	};
};
$(document).on("pagebeforeshow", function(e, ui) {
	var searchPId = new mcare_mobile_searchPId();
	searchPId.init();
});
</script>

