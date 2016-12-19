<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<div data-role="panel" id="sidebar" data-display="reveal" data-position="right">
		<div class="loginArea">
			<c:choose>
				<c:when test="${empty sessionScope.MCARE_USER_ID }">
					<a href="${pageContext.request.contextPath}/login.page"  class="logBtn ui-btn b_l">
						<span class="id b_l" >&nbsp;&nbsp; <s:message code="mobile.view.common011"  /></span>
						<i class="fa fa-sign-in"></i>
					</a>								
				</c:when>
				<c:otherwise>
					<div class="logoutIDWrap">
						<span class="id a_l">${sessionScope.MCARE_USER_NAME }</span>&nbsp;<span class="d_t"><s:message code="mobile.view.common012" /></span>
					</div>
					<div class="logoutBtnWrap">
						<a href="${pageContext.request.contextPath}/logout.page" class="logBtn ui-btn a_l">	
							<i class="fa fa-power-off fa-2x"></i><br/><span class="l_t"><s:message code="mobile.view.common025" /></span>
						</a>
					</div>
				</c:otherwise>
			</c:choose>
		</div>
	<ul data-role="listview" data-inset="false">
		<!-- <li>
			<c:choose>
				<c:when test="${empty sessionScope.MCARE_USER_ID }">				
					<a href="${pageContext.request.contextPath}/login.page" >
						<h2><s:message code="main.auth.Login" text="로그인" /></h2>
					</a>
				</c:when>
				<c:otherwise>
					<a href="${pageContext.request.contextPath}/logout.page" >
						<h2><s:message code="main.auth.Logout" text="로그아웃" /></h2>
					</a>
				</c:otherwise>
			</c:choose>
		</li> -->
		<c:if test="${not empty sessionScope.MCARE_USER_ID }">
			<li>
				<a href="${pageContext.request.contextPath}/mobile/helper/helper.page?menuId=helper" class="sidemenu">
					<h2>도우미</h2>
				</a>
			</li>
		</c:if>
		<c:forEach var="menu" items="${sideMenu }" varStatus="status">
			<li>
				<c:choose>
					<c:when test="${menu.menuType eq 'NAVI'}">
						<a href="${pageContext.request.contextPath}${menu.accessUriAddr }?menuId=${menu.menuId }" class="sidemenu" >
							<h2>${menu.menuName }</h2>
						</a>
					</c:when>
					<c:otherwise>
						<c:set var="uri" value='${menu.accessUriAddr }'/>
						<c:choose>
							<c:when test="${fn:contains(uri , 'javascript') }">
								<a href="${menu.accessUriAddr }"  class="sidemenu">
									<h2>${menu.menuName }</h2>
								</a>
							</c:when>
							<c:otherwise>						
								<a href="${pageContext.request.contextPath}${menu.accessUriAddr }?menuId=${menu.menuId }" class="sidemenu" >
									<h2>${menu.menuName }</h2>
								</a>
							</c:otherwise>
						</c:choose>
					</c:otherwise>
				</c:choose>
			</li>
		</c:forEach>
		
		<!-- 다국어 테스트 -->
		<!-- 
		<li>
			<c:if test="${mcare_locale != 'ko'}">
				<a href="javascript:changeLocale('ko')" >
	    			<h2>언어변경(KO) 테스트</h2>
	    		</a>
			</c:if>
			<c:if test="${mcare_locale != 'en'}">
				<a href="javascript:changeLocale('en')" >
	    			<h2>언어변경(EN) 테스트</h2>
	    		</a>
			</c:if>
		</li>
		 -->
	</ul>
</div>

<script type="text/javascript">
$(document).on("pagebeforeshow",function(e){
	$(".sidemenu").on("click",function(e){
		$(this).parent().removeClass("active");
		$(this).parent().addClass("active");
	});
	$(".loginArea .logBtn").on("click",function(e){
		$(this).removeClass("active");
		$(this).addClass("active");
	});
});
function changeLocale(lang) {
	location.href = location.pathname + '?' + location.search.replace(/^\?|&?_locale=../g, '').concat('&_locale=' + lang);
};
/**
 * 부산대 모바일 홈페이지 오픈
 */
function goHomepage(strUrl){
	$(".sidemenu").parent().removeClass("active");
	// 사이드바 panel 열려있으면 닫기
	if( $(":jqmData(role=panel)").hasClass("ui-panel-open") ){
		$(":jqmData(role=panel)").panel("close");
	}
	var mcare = new mcare_mobile();
	var jsonMsg = {
		type : "command",
		functionType : "systemBrowsing",
		value : {
			url : strUrl
		}
	};
	mcare.toNative( jsonMsg );
};
/**
 * 지도 오픈
 */
function openMap(title, source, destination) {
	$(".sidemenu").parent().removeClass("active");
	$(".menu_btn_wrapper").removeClass("active");
	// 사이드바 panel 열려있으면 닫기
	if( $(":jqmData(role=panel)").hasClass("ui-panel-open") ){
		$(":jqmData(role=panel)").panel("close");
	}
	var jsonMsg = {
		type : "command", 
		functionType : "openMap",
		value : {
			title : "길찾기",//  <-- default 입력 
			source : "", // <-- default 입력
			dest : "" // <-- default 입력
		}
	}; 
	
	if( title !== undefined ){
		jsonMsg["value"]["title"] = title;
	}
	if( source !== undefined ){
		jsonMsg["value"]["source"] = source;
	}
	if( destination !== undefined ){
		jsonMsg["value"]["dest"] = destination;
	}
	
	var mcare = new mcare_mobile(); 
	mcare.toNative(jsonMsg); 
};

/**
 * 앱 종료
 */
function exitApp() { 
	$(".sidemenu").parent().removeClass("active");
	var mcare = new mcare_mobile(); 
	// 사이드바 panel 열려있으면 닫기
	if( $(":jqmData(role=panel)").hasClass("ui-panel-open") ){
		$(":jqmData(role=panel)").panel("close");
	}
	mcare.popup(
			{
			"content": mcare.getI18n("common006"),
			"callback":function(){
				var jsonMsg = {
						type : "command", 
						functionType : "exit"
					}; 
				mcare.toNative(jsonMsg); 
			}
	});
	
};
/**
 * 이전 버튼 이벤트
 */
function triggerBackBtn(){
	var mcare = new mcare_mobile(); 
	if( mcare.isAndroid() ){
		//헤더 백버튼 숨어 있으면 리턴
		if( $("#headerArrowLeft_btn").css("display") === "none" ){
			return;
		}
		// 사이드바 panel 열려있으면 닫기
		if( $(":jqmData(role=panel)").hasClass("ui-panel-open") ){
			$(":jqmData(role=panel)").panel("close");
			return;
		}
		//dialog 열려있으면 닫기
		if($(":jqmData(role=dialog)").hasClass("ui-dialog-open")){
			$(":jqmData(role=dialog)").dialog("close");
			return;
		}
		//dialog 열려있으면 닫기
		if( $(":jqmData(role=popup)").parent().hasClass("ui-popup-active") ){
			$(":jqmData(role=popup)").popup("close");
			return;
		}
		//index 면 종료
		if( (window.location.href).indexOf("index.page") > 0 ){
			exitApp();
			return;
		}
		//회원 탈퇴가 성공하면 로그 아웃페이지로 이동하게 한다.
		if( $(":jqmData(role=popup)").find("input#withdrawal").length > 0 ){
			window.location.href = contextPath + "/logout.page";
			return;
		}
		$("a#headerArrowLeft_btn")[0].click();
		
		
	}
};
/**
 * 홈버튼 이벤트
 */
function triggerSidebarBtn(){
	var mcare = new mcare_mobile();
	//헤더 백,메뉴버튼 숨어 있으면 리턴
	if( $("#menuBars_btn,#headerArrowLeft_btn").css("display") === "none" ){
		return;
	}
	if( mcare.isAndroid() ){		
		$("a#menuBars_btn").trigger("click");
	}
}

/**
 * 프로그램 버전 확인
 */
function checkProgramVersion(){
	$(".naviLink").removeClass("active");
	var jsonMsg = {
			type : "command", 
			functionType : "version",
			value : {
				callbackFn : "window.versionCallback"
			}
		}; 
		
		var mcare = new mcare_mobile(); 
		mcare.toNative(jsonMsg); 
};
window.versionCallback = function(data){
// 	if( typeof data === "string" ){			
// 		data = JSON.parse( data );
// 	};
	// 사이드바 panel 열려있으면 닫기
	if( $(":jqmData(role=panel)").hasClass("ui-panel-open") ){
		$(":jqmData(role=panel)").panel("close");
	}
	var alert = $("#programDialog"),
		content = alert.find(".popupcontent"),
		callback = alert.find(".alertCallback");	
	content.html( data );
	callback.off();
	callback.on("click",function(e){
		e.preventDefault();
		alert.popup("close");
	});
	setTimeout(function(){				
		alert.popup("open",{});
	},300);
};

/**
 * 의약품백과사전
 **/
 function medicineSearch(){
	 $("#sidebar .ui-listview li").removeClass("active");
	 $(".sidemenu").parent().removeClass("active");
		// 사이드바 panel 열려있으면 닫기
		if( $(":jqmData(role=panel)").hasClass("ui-panel-open") ){
			$(":jqmData(role=panel)").panel("close");
		}
	 var jsonMsg = {
			type : "command", 
			functionType : "popUpBrowsing",
			value : {
				url : "http://terms.naver.com/medicineSearch.nhn"
			}
		}; 
			
		var mcare = new mcare_mobile(); 
		mcare.toNative(jsonMsg); 
};
/**
 * 실험용
 */
 function goMcare(){
	window.location.href = "https://mcare-demo.idatabank.com/mcare_demo/hospital.page";
}
</script>



