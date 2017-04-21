<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div data-role="header" data-position="fixed" data-tap-toggle="false">
	<h2>${menuInfo.menuName}</h2>
	<a href="#sidebar"  id="menuBars_btn" class="ui-btn-right"><i class="fa fa-bars"></i><span style="display:none;">side</span></a>
	<c:choose>
		<c:when test="${menuInfo.parentMenuId eq null}">
			<a href="#"  data-direction="reverse" class="ui-btn-left" id="headerArrowLeft_btn"><i class="fa fa-arrow-left"></i></a>
		</c:when>
		<c:otherwise>
			<a href="#"  data-direction="reverse" class="ui-btn-left" id="headerArrowLeft_btn"><i class="fa fa-arrow-left"></i></a>
		</c:otherwise>
	</c:choose>

</div>

<script type="text/javascript">
//메뉴 param obj	
var menuParamObj = new Object();

//서버에서 가져온 메뉴 param 설정 
<c:forEach var="menuParam" items="${menuInfo.menuParam }" varStatus="status">
	menuParamObj['${menuParam.paramName}'] = {"type":'${menuParam.dataType}',"value" :'${menuParam.paramValue}'}
</c:forEach>
	
$(document).one("pagebeforeshow", function(e, ui) {
	var $t = $(e.target);
	
	var header = $(':jqmData(role=header)', $t),
		/* a = $('a:eq(0)', header), */
		a2 = $('a:eq(1)', header),
		parentMenuId = '${menuInfo.parentMenuId}'; 
		
	var paramObj = new Object();
	//url get 방식으로 넘어온 param 설정
	var query = window.location.search.substring(1),
		vars = query.split("&");	
	for( var i=0; i < vars.length; i++ ){
	     var pair = vars[i].split("=");

	     paramObj[ pair[0] ] = pair[1];
	}
	
	if (parentMenuId == '') {
		if ('${menuInfo.enabledYn}' == 'N' && '${menuInfo.authYn}' == 'N' ) {
				//부모가 없고 권한 없는 메뉴는 index로
				a2.attr({'href': contextPath + '/index.page', 'data-ajax' : false });
		} else {
			if( '${menuInfo.menuId}' === 'userRegister' ){
				//사용등록이면 별도 함수를 만든다.
				a2.attr({'href':'javascript:exitRegister();'});
			} else if( paramObj["info"] !== undefined){
				if( paramObj["info"] === "helper" ){
					a2.attr({'href': contextPath + '/mobile/helper/helper.page?menuId=helper', 'data-ajax' : false });
				//푸시로 강제로 페이지 이동했을때는 뒤로가기 했을 때 시작화면으로 가도록 
				} else if( paramObj["info"] === "index" ){
					a2.attr({'href': contextPath + '/index.page', 'data-ajax' : false });	
				}
			} else {			
				a2.attr({'href': contextPath + '/index.page', 'data-ajax' : false });
			}
		}
	} else if( paramObj["info"] !== undefined ){
		if( paramObj["info"] === "helper" ){
			a2.attr({'href': contextPath + '/mobile/helper/helper.page?menuId=helper', 'data-ajax' : false });		
		//푸시로 강제로 페이지 이동했을때는 뒤로가기 했을 때 시작화면으로 가도록 
		} else if( paramObj["info"] === "index" ){
			a2.attr({'href': contextPath + '/index.page', 'data-ajax' : false });	
		} 
	//부모 메뉴가 있고, 바로 앞에 화면은 로그인이면, 세션아웃으로 로그인화면을 거쳐서 왔을 경우로 판단됨 
	//그럼 바로 앞( 로그인 )의 앞 화면으로 가시오.
	} else if( parentMenuId !== '' && document.referrer.indexOf("login") > 0 ){
		a2.attr({'href' : "javascript:history.go(-2);", 'data-ajax': false })

	} else {
		a2.attr({'href': "javascript:history.back();", 'data-ajax': false});
		
// 		if (parentMenuType == 'NAVI') {
// 			a2.attr({'href': a2.attr('href') + '?menuId=' + parentMenuId });
// 		}
	}
	//본인인증 헤더 버튼 없앰
	if( '${menuInfo.menuId}' === "certification" || '${menuInfo.parentMenuId}' === "certification" 
			|| '${menuInfo.menuId}'=== "registerPWD"|| '${menuInfo.menuId}'=== "userAgreement" || '${menuInfo.menuId}'=== "under14"){
		$("#menuBars_btn,#headerArrowLeft_btn").hide();
	}
	if( '${menuInfo.menuId}' === "searchPNumber" || '${menuInfo.menuId}' === "searchPWD"){
		a2.attr({'href': contextPath + '/login.page', 'data-ajax' : false });
	}
	if( '${menuInfo.menuId}' === "payPolling" || '${menuInfo.menuId}' === "receipt" ){
		$("#menuBars_btn,#headerArrowLeft_btn").hide();
	}
	
	//비밀번호 유도시 헤더 버튼 없앰
	if( '${menuInfo.menuId}' === "changeMyPwd" && document.referrer.indexOf("login") > 0 && window.location.href.indexOf("over") > 0 ){
		$("#menuBars_btn,#headerArrowLeft_btn").hide();
	}
});
function exitRegister(){
	var mcare = new mcare_mobile();
	
	var options = {
			content : "정말 좋은 서비스인데 가입을 중단하시겠습니까?",
			callback : function(e){
				mcare.changePage(contextPath + '/index.page');
			}
	};
	mcare.popup( options );
};
</script>

