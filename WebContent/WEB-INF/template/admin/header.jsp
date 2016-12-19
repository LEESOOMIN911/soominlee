<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="k-header">
	<div class="top">
		<table style="width:100%">
			<tr>
				<td align="left"><img src="<c:url value='/resources/css/images/mobile/main/toplogo.png' />" width="100"> &nbsp;<img src="<c:url value='/resources/css/images/admin/mcare.png' />" height="18"></td>
				<td align="right"><button class="k-button" onclick="javascript:location.href='<c:url value="/admin/logout.page"/>';">로그아웃</button></td>
			</tr>
		</table>
	</div>
	<div class="menu-wrapper">
		<ul class="menu" id="nav">
			<li><i class="material-icons">insert_chart</i> <span class="txt">통계</span>
                <ul>
                	<li>
		            	<a href="${pageContext.request.contextPath}/admin/daily.page">
		                    <span>날짜별 방문현황</span>
		                </a>
		            </li>
					<li>
						<a href="${pageContext.request.contextPath}/admin/weekly.page">
							<span>요일별 방문현황</span>
						</a>
					</li>
					<li>
						<a href="${pageContext.request.contextPath}/admin/hourly.page">
							<span>시간대별 방문현황</span>
						</a>
					</li>
					<li>
						<a href="${pageContext.request.contextPath}/admin/platform.page">
							<span>플랫폼별 방문현황</span>
						</a>
					</li>
					<li>
						<a href="${pageContext.request.contextPath}/admin/access.page">
							<span>페이지별 방문현황</span>
						</a>
					</li>
					<li>
						<a href="${pageContext.request.contextPath}/admin/eventLog.page">
							<span>이벤트 발생현황</span>
						</a>
					</li>
					<li>
						<a href="${pageContext.request.contextPath}/admin/msgSendResultLog.page">
							<span>메시지 전송 현황</span>
						</a>
					</li>
					<li>
						<a href="${pageContext.request.contextPath}/admin/dailyRegister.page">
							<span>일자별 가입 현황</span>
						</a>
					</li>
					<li>
						<a href="${pageContext.request.contextPath}/admin/userAge.page">
							<span>연령별 가입 현황</span>
						</a>
					</li>
					<li>
						<a href="${pageContext.request.contextPath}/admin/userPost.page">
							<span>지역별 가입 현황</span>
						</a>
					</li>
					<li>
						<a href="${pageContext.request.contextPath}/admin/userPlatform.page">
							<span>사용자 플랫폼 정보 현황</span>
						</a>
					</li>
                </ul>
			</li>		
			<li><i class="material-icons">extension</i><span class="txt">API </span> 
				<ul>
		            <li>
						<a href="${pageContext.request.contextPath}/admin/api.page">
							<span>API 관리</span>
						</a>
					</li>		
					<li>
						<a href="${pageContext.request.contextPath}/admin/category.page">
							<span>카테고리 관리</span>
						</a>
					</li>									
				</ul>
			</li>
			<li><i class="material-icons">account_circle</i><span class="txt">계정</span>
				<ul>				
					<li>
						<a href="${pageContext.request.contextPath}/admin/user.page">
							<span>사용자관리</span>
						</a>
					</li>	
					<!-- 							
					<li>
						<a href="${pageContext.request.contextPath}/admin/loginHistory.page">
							<span>로그인 이력조회</span>
						</a>
					</li>
					 -->	
					<li>
						<a href="${pageContext.request.contextPath}/admin/manager.page">
							<span>관리자 계정관리</span>
						</a>
					</li>																	
				</ul>
			</li>
			<li><i class="material-icons">settings_applications</i><span class="txt">서비스 관리</span>
				<ul>
		            <li>
						<a href="${pageContext.request.contextPath}/admin/menu.page">
							<span>메뉴</span>
						</a>
					</li>
					<li>
						<a href="${pageContext.request.contextPath}/admin/version.page">
							<span>앱 버전</span>
						</a>
					</li>										
					<li>
						<a href="${pageContext.request.contextPath}/admin/telNo.page">
							<span>주요 전화번호</span>
						</a>
					</li>
					<li>
						<a href="${pageContext.request.contextPath}/admin/agreement.page">
							<span>서비스약관</span>
						</a>
					</li>
					<li>
						<a href="${pageContext.request.contextPath}/admin/poiMapping.page">
							<span>위치맵핑</span>
						</a>
					</li>					
				</ul>
			</li>
			<li><i class="material-icons">open_in_browser</i><span class="txt">PUSH</span>
                <ul>
					<li>
						<a href="${pageContext.request.contextPath}/admin/pushForm.page">
							<span>메시지형식</span>
						</a>
					</li>
					<li>
						<a href="${pageContext.request.contextPath}/admin/msgcheck.page">
							<span>전송확인</span>
						</a>
					</li>
				</ul>
			</li>
		</ul>
	</div>
</div>

<style type="text/css">
div.top {
	text-align: right;
}

div.top .k-button {
	margin: 10px;
}
div.menu-wrapper{
	text-align: center;
}

</style>
<script type="text/javascript">
	$(document).ready(function(){
		$(".menu").kendoMenu({openOnClick:true});
	});
</script>