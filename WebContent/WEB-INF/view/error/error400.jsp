<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@include file="./include.jsp"%>

<div id="mcareError">
	<div data-role="header">
		<h2>에러안내</h2>
	</div>
	<div data-role="content" data-type="400">
		<div >
			<p class="errormsg">
				<s:message code="mcare.error.400" /><br/>
				오류코드 : <c:out value="${requestScope['javax.servlet.error.status_code'] }"/> <br/>
				에러메세지 : <c:out value="${requestScope['javax.servlet.error.message'] }"/> <br/>
				요청주소 : <c:out value="${requestScope['javax.servlet.error.request_uri'] }"/> <br/>
			</p>
		</div>
		<a class="ui-btn ui-btn-b" href="${pageContext.request.contextPath}/index.page" > 시작페이지로 이동 </a>
	</div>
</div>
