<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/mobile/index/index.css"/>" />
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/plugins/slick/slick/slick.css"/>" />
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/plugins/slick/slick/slick-theme.css"/>" />
<script type="text/javascript" src="<c:url value="/resources/js/mobile/index/index.js"/>"></script> 
<script type="text/javascript" src="<c:url value="/resources/plugins/slick/slick/slick.min.js"/>"></script> 
<div data-role="content">
	<div class="mainheader">
		<h1 id="toplogo">부산대학병원로고</h1>

		<div  class="menuwrap">
			<a href="#sidebar"  id="menuBars_btn" class="ui-btn-right"><i class="fa fa-bars"></i></a>
		</div>
		<!-- <a href="#sidebar" class="ui-btn ui-btn-right ui-icon-bullets ui-btn-icon-notext"></a> -->
		<!-- <span id="logo">
			부산대학교 병원
		</span> -->
	</div>
	<div id="menuList">
		<!-- 로그인 세션이 있을 때만 도우미가 보이도록 -->
		<c:if test="${not empty sessionScope.MCARE_USER_ID }">
			<div  class="helper_wrap">
				<!-- 도우미는 고정값으로 넣었음 -->
				<a href="${pageContext.request.contextPath}/mobile/helper/helper.page?menuId=helper" data-ajax="false" class="menu_link">
					<div class="helper_wrapper">
						<span class="helper_icon">도우미</span>
						<div class="msg">
							<span class="recent_text"></span>
						</div>
					</div>
				</a>
			</div>
			<div class="hidden_go hide"></div>
			<div class="hidden_gpattern hide"></div>
		</c:if>
		<c:if test="${empty sessionScope.MCARE_USER_ID }">
			<script type="text/javascript">
				sessionStorage.clear();
			</script>
		</c:if>
		<div class="menu_wrapper">
			<div class="swiper-container">
				<div class="swiper-wrapper" ></div>
			</div>
		</div>
	</div>
</div>
<!-- jstl로 하려다가 swipe로 인해서 스크립트로 변환함. 데이터는 스크립트로 뽑아냄 -->
<script type="text/javascript">
//다국어
var i18n = function(){
	var message = {
		"common006" : "<s:message code='mobile.message.common006'/>",
		"index001" : "<s:message code='mobile.message.index001'/>"
	};
	this.getMessage = function( code ){
		return message[code];
	};
};
var menulist = new Array();	 

<c:forEach var="menu" items="${mainMenu }" varStatus="status">
	menulist.push({
		"menuType" : '${menu.menuType}',
		"accessUriAddr" : '${menu.accessUriAddr }',
		"menuId" : '${menu.menuId }',
		"imageUriAddr" : '${pageContext.request.contextPath}${menu.imageUriAddr }',
		"menuName" : '${menu.menuName }'
	});
</c:forEach>

 $(document).on("pagebeforecreate",function(e){
	var pId = '${sessionScope.MCARE_USER_ID }';
		
	var index = new mcare_mobile_index( menulist, pId );
	index.setGlobal( index );
	index.init();
 });
</script>