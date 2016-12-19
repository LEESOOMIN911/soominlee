<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/mobile/help/help.css"/>" />
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/plugins/slick/slick/slick.css"/>" />
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/plugins/slick/slick/slick-theme.css"/>" />
<script type="text/javascript" src="<c:url value='/resources/js/mobile/help/help.js' />"></script>
<script type="text/javascript" src="<c:url value="/resources/plugins/slick/slick/slick.min.js"/>"></script> 
<!-- 도움말 -->
<div data-role="content">
	<div class="mainContainer">
		<div class="help">
			<div class="page1">
				<ul data-role="listview"  id="help_menu" class="help_menu" >
					<li>
						<img src="<c:url value='/resources/css/images/mobile/help/mobileCard.png' />" alt="진료카드" />
						<h3>진료카드</h3>
						<p>진료카드를 확인할수 있습니다.</p>
					</li>
					<li>
						<img src="<c:url value='/resources/css/images/mobile/help/ticket.png' />" alt="번호표발급" />
						<h3>번호표발급</h3>
						<p>진료대기 번호표를 발급 받을 수 있습니다.</p>
					</li>

					<li>
						<img src="<c:url value='/resources/css/images/mobile/help/waitingTime.png' />" alt="진료대기순서" />
						<h3>진료대기순서</h3>
						<p>진료대기 상황을 알아볼 수 있습니다.</p>
					</li>
					<li>
						<img src="<c:url value='/resources/css/images/mobile/help/navigation.png' />" alt="가야할곳" />
						<h3>가야할곳</h3>
						<p>진료를 위해 가야할 곳을 알아볼 수 있습니다.</p>
					</li>
					<li>
						<img src="<c:url value='/resources/css/images/mobile/help/openMap.png' />" alt="길찾기" />
						<h3>길찾기</h3>
						<p>병원내 길안내를 사용할 수 있습니다.</p>
					</li>
					<li>
						<img src="<c:url value='/resources/css/images/mobile/help/treatmentHistory.png' />" alt="진료이력" />
						<h3>진료이력</h3>
						<p>진료이력을 확인할 수 있습니다.</p>
					</li>
				</ul>
			 </div>
			 <div class="page2">
				<ul data-role="listview"  id="help_menu2" class="help_menu"  >
					<li>
						<img src="<c:url value='/resources/css/images/mobile/help/reservation.png' />" alt="진료예약" />
						<h3>진료예약</h3>
						<p>진료예약을 할 수 있습니다.</p>
					</li>
					<li>
						<img src="<c:url value='/resources/css/images/mobile/help/appointmentSearch.png' />" alt="예약조회" />
						<h3>예약조회</h3>
						<p>진료예약을 조회할 수 있습니다.</p>
					</li>
					<li>
						<img src="<c:url value='/resources/css/images/mobile/help/healthHandbook.png' />" alt="건강수첩" />
						<h3>건강수첩</h3>
						<p>혈압, 혈당, 키, 체중 정보를 저장하고 살펴 볼 수 있습니다.</p>
					</li>
					<li>
						<img src="<c:url value='/resources/css/images/mobile/help/parking.png' />" alt="주차관리" />
						<h3>주차관리</h3>
						<p>차량정보를 저장 및 수정할 수 있습니다.</p>
					</li>
					<li>
						<img src="<c:url value='/resources/css/images/mobile/help/telNo.png' />" alt="주요전화번호" />
						<h3>주요전화번호</h3>
						<p>병원 주요 전화번호를 알아 볼 수 있습니다.</p>
					</li>
					<li>
						<img src="<c:url value='/resources/css/images/mobile/help/register.png' />" alt="내정보관리" />
						<h3>내 정보관리</h3>
						<p>서비스약관, 비밀번호 변경 등 내 정보를 관리할 수 있습니다.</p>
					</li>
				</ul>
			 </div>
			 <div class="page3">
			 	<img src="<c:url value='/resources/css/images/mobile/help/mark1.png' />" alt="" />
			 </div>

			 <div class="page4">
			 	<img src="<c:url value='/resources/css/images/mobile/help/mark2.png' />" alt="" />
			 </div>
			 
		</div>
	</div><!--mainContainer 종료  -->
</div>

<script type="text/javascript">
//다국어 사용
var i18n = function(){
	var message = {
		"common006" : "<s:message code='mobile.message.common006'/>",
		"helper001" : "<s:message code='mobile.view.helper001'/>"
	};
	
	this.getMessage = function( code ){
		return message[code];
	};
};
$(document).on("pagebeforeshow", function(e, ui) {
	var max=$(window).height()
	$('.overlay,.helpwrap,.backImg').height(max);
	var help = new mcare_mobile_help();
	help.init();
});
</script>
