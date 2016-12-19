<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<div data-role="content">
	<ul data-role="listview" data-inset="true">
		<c:forEach var="menu" items="${menuList }">
			<c:choose>
				<c:when test="${menu.MENU_YN eq 'Y'}">
				<li>
					<h2><a href="<c:url value="${menu.URI }"/>?subjectId=${menu.ID }" >
						${menu.TEXT }</a>
					</h2>
				</li>
				</c:when>
				<c:otherwise>
				<li data-icon="action">
					<h2><a href="<c:url value="${menu.URI }"/>">
						${menu.TEXT }</a>
					</h2>
				</li>
				</c:otherwise>
			</c:choose>
		</c:forEach>
	</ul>
</div>

<script type="text/javascript">
$(document).one('pagebeforeshow', function(e, ui) {
	var page = $.mobile.activePage;
});
</script>

