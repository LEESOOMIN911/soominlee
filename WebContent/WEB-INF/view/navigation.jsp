<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<div data-role="content">
	<ul data-role="listview" id="naviListview">
		<c:forEach var="menu" items="${naviMenu }">
			<c:choose>
				<c:when test="${menu.menuType eq 'NAVI'}">
				<li class="naviLink">
					<a href="${pageContext.request.contextPath}${menu.accessUriAddr }?menuId=${menu.menuId}" >
						<h2>${menu.menuName }</h2>
					</a>
				</li>
				</c:when>
				<c:otherwise>
					<li class="naviLink">
						<c:set var="uri" value='${menu.accessUriAddr }'/>
						<c:choose>
							<c:when test="${fn:contains(uri , 'javascript') }">
								<a href="${menu.accessUriAddr }"  >
									<h2>${menu.menuName }</h2>
								</a>
							</c:when>
							<c:otherwise>						
								<a href="${pageContext.request.contextPath}${menu.accessUriAddr }?menuId=${menu.menuId }">
									<h2>${menu.menuName }</h2>
								</a>
							</c:otherwise>
						</c:choose>
					</li>
				</c:otherwise>
			</c:choose>
		</c:forEach>
	</ul>
</div>

<script type="text/javascript">
$(document).one('pagebeforeshow', function(e, ui) {
	var page = $.mobile.activePage;
	
	$(".naviLink").on("click",function(e){
		$(".naviLink").removeClass("active");
		$(this).addClass("active");
	});
});
</script>

