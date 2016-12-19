<%@page import="com.dbs.mcare.framework.util.SessionUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/mobile/user/pushToken.css"/>" />
<script type="text/javascript" src="<c:url value='/resources/js/mobile/user/pushToken.js' />"></script>
<%
	String pName = SessionUtil.getUserName(request);
%>
<!-- push token 관리 -->
<div data-role="content">
	<div class="mainContainer">
		<div class="fir_con">
			<h4 class="for_history"><i class="fa font14 fa-dot-circle-o"></i>&nbsp;<span><s:message code="mobile.view.pushToken010" /></span></h4>
			<div class="targetContainer"></div>
		</div>
		<div class="sub_con">
			<h4 class="for_history"><i class="fa font14 fa-dot-circle-o"></i>&nbsp;<span><%=pName %></span>&nbsp;<span><s:message code="mobile.view.pushToken008" /></span></h4>
			<div class="lhContainer"></div>
			<div>
				<ul class="item_ul">
					<li><s:message code="mobile.view.pushToken009" /></li>
				</ul>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">
//다국어 사용
var i18n = function(){
	var message = {
			"common005" : "<s:message code='mobile.message.common005'/>",
			"common006" : "<s:message code='mobile.message.common006'/>",
			"pushToken001":"<s:message code='mobile.view.pushToken001'/> ",
			"pushToken002":"<s:message code='mobile.view.pushToken002'/> ",
			"pushToken003":"<s:message code='mobile.view.pushToken003'/> ",
			"pushToken004":"<s:message code='mobile.view.pushToken004'/> ",
			"pushToken005":"<s:message code='mobile.view.pushToken005'/> ",
			"pushToken006":"<s:message code='mobile.view.pushToken006'/> ",
			"pushToken007":"<s:message code='mobile.view.pushToken007'/> "
	};
	
	this.getMessage = function( code ){
		return message[code];
	};
};
$(document).on("pagebeforeshow", function(e, ui) {
	var pushToken = new mcare_mobile_pushToken();
	pushToken.setGlobal( pushToken );
	pushToken.init();
});
$(document).on("pageshow",function(e){
	$.mobile.resetActivePageHeight(); 
});
</script>

