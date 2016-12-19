<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<!doctype html>
<html>
<head>
<meta name="format-detection" content="telephone=no">
<title><tiles:getAsString name="title" /></title>
<tiles:insertAttribute name="include" />
</head>
<body>
	<div data-role="page" class="ui-noboxshadow pnuh">
		<%@include file="./panel.jsp"%>
	    <tiles:insertAttribute name="header" />
	    <tiles:insertAttribute name="content" />
	    <!-- 컨펌 -->
		<div data-role="popup" id="popupDialog" data-dismissible="false" class="ui-corner-all">
			<div role="content" >
				<div class="popuptitle"><s:message code="mobile.view.common023" /></div>
				<div class="popupcontent">

				</div>
				<div class="ui-grid-a">
					<div class="ui-block-a">
						<a href="#"  class="popupCallback ui-btn ui-btn-b"><s:message code="mobile.view.common022" /></a>
					</div>
					<div class="ui-block-b">
						<a href="#" data-rel="back" class="ui-btn ui-btn-b ui-shadow"><s:message code="mobile.view.common021" /></a>
					</div>
				</div>				
			</div>
		</div>
		<!-- alert -->
		<div data-role="popup" id="alertDialog" data-dismissible="false" class="ui-corner-all">
			<div role="content" >
				<div class="popuptitle"><s:message code="mobile.view.common023" /></div>
				<div class="popupcontent">

				</div>
				<div class="buttonwrapper">
					<a href="#" class="ui-btn ui-btn-b ui-shadow alertCallback"><s:message code="mobile.view.common022" /></a>
				</div>				
			</div>
		</div>
		<!-- alert -->
		<div data-role="popup" id="programDialog" data-dismissible="false" class="ui-corner-all">
			<div role="content" >
				<div class="popuptitle"><s:message code="mobile.view.common023" /></div>
				<div class="popupcontent">

				</div>
				<div class="buttonwrapper">
					<a href="#" class="ui-btn ui-btn-b ui-shadow alertCallback"><s:message code="mobile.view.common022" /></a>
				</div>				
			</div>
		</div>
	</div>
</body>
</html>
