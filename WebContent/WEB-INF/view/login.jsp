<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/mobile/login/login.css"/>" />
<script type="text/javascript" src="<c:url value='/resources/js/mobile/login/login.js' />"></script>
<div data-role="header">
	<h2><s:message code="mobile.view.login001"/></h2>
	<a href="<c:url value="/index.page"/>"  data-direction="reverse" class="ui-btn-left" id="headerArrowLeft_btn">&nbsp;</a>
	<a href="#sidebar" class="ui-btn-right" id="menuBars_btn">&nbsp;</a>
</div>
<div data-role="content">
	<div class="mainContainer">
		<div class="fir_con">
			<div class="form_input">
				<input type="number" pattern="\d*" id="pId" data-clear-btn="turn" placeholder="<s:message code="mobile.message.login005"/>" value="" />
				<a href="#" id="currentUser" class="ui-btn ui-shadow ui-corner-all ui-icon-user ui-btn-icon-notext ui-btn-inline" ></a>
			</div>
		</div>
		<div class="sec_con">
			<div class="form_input">
				<input type="password" id="password" data-clear-btn="turn" placeholder="<s:message code="mobile.message.login006"/>" value="" />
			</div>
		</div>
		<div class="thi_con">
			<label><input type="checkbox" id="rememberMe" checked="checked"/><s:message code="mobile.view.login018"/></label>
			<button id="login_btn" data-theme="b"><s:message code="mobile.view.login001"/></button>
		</div>
		<div class="fif_con">
			<input type="hidden" id="_deviceToken" />
		</div>
	</div>
	<div class="login-thumbs">
	  <div class="ui-grid-b">
			<div class="ui-block-a"><div class="ui-bar ui-bar-a" ><a href="#" id="login_btn1"><i class="fa fa-user"></i><span class=""><s:message code="mobile.view.login009"/></span></a></div></div>
			<div class="ui-block-b"><div class="ui-bar ui-bar-a" ><a href="#" id="login_btn2"><i class="fa fa-unlock-alt"></i><span class=""><s:message code="mobile.view.login010"/></span></a></div></div>
			<div class="ui-block-c"><div class="ui-bar ui-bar-a" ><a href="#" id="reg_btn"><i class="fa fa-pencil"></i><span class=""><s:message code="mobile.view.login011"/></span></a></div></div>
	  </div>
	</div>
	<div class="noticeWrap">
		<ul style="padding:0 15px;font-size:13px;">
			<li>
				<span><s:message code="mobile.view.login012"/></span>
				<button class="noticeIcon ui-btn ui-shadow ui-corner-all ui-icon-user ui-btn-icon-notext ui-btn-inline" style="margin:0;outline: none;"></button> 
				<span><s:message code="mobile.view.login013"/></span>
			</li>
		</ul>
	</div>
</div>
<!-- 컨펌 -->
<div data-role="popup" id="confirmDialog" data-dismissible="false" class="ui-corner-all">
	<div role="content" >
		<div class="confirmtitle"><s:message code="mobile.view.login014" /></div>
		<div class="confirmcontent">
	
		</div>
		<div class="ui-grid-a">
			<div class="ui-block-a">
				<a href="#" class="pwdChange ui-btn ui-btn-b"><s:message code="mobile.view.login015" /></a>
			</div>
			<div class="ui-block-b">
				<a href="#" class="laterChange ui-btn ui-btn-b ui-shadow"><s:message code="mobile.view.login016" /></a>
			</div>
		</div>				
	</div>
</div>
<input type="hidden" id="hPid" value="${pId}"/>

<script type="text/javascript">	
//다국어
var i18n = function(){
	var message = {
		"common006" : "<s:message code='mobile.message.common006'/>",
		"login005" : "<s:message code='mobile.message.login005'/>",
		"login006" : "<s:message code='mobile.message.login006'/>",
		"login017" : "<s:message code='mobile.message.login017'/>"
	};
	this.getMessage = function( code ){
		return message[code];
	};
};
$(document).on("pagebeforeshow", function(e, ui) {
	sessionStorage.clear();
	var login = new mcare_mobile_login();
	login.setGlobal( login );
	login.init();
});
$(document).on("pageshow",function(e){
	$.mobile.resetActivePageHeight(); 
 });
</script>