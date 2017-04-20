<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<%@include file="./include.jsp"%>

<div id="mcareError">
	<div data-role="header">
		<h2>에러안내</h2>
	</div>
	<div data-role="content" data-type="500">
		<div >
			<p class="errormsg">
				<s:message code="mcare.error.500" text="서비스 에러가 발생했습니다. 다시 시도해도 안되면, 관리자에게 연락해주세요."/>
			</p>
		</div>
		<a class="ui-btn ui-btn-b" href="${pageContext.request.contextPath}/index.page" > 시작페이지로 이동 </a>
	</div>
</div>

 