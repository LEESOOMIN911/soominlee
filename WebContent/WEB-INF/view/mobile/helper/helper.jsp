<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/mobile/helper/helper.css"/>" />
<script type="text/javascript" src="<c:url value='/resources/js/mobile/helper/helper.js' />"></script>
<!-- 도우미 -->
<div data-role="content">
	<div class="mainContainer">
		<div class="fir_con">
			<div class="statusContainer">
				<ul class="status-breadcrumb">
					<li class="current">
						<a href="#">
							<s:message code="mobile.view.helper001" />
							<span class="bc-ar-btn"></span>
						</a>
					</li>
					<li >
						<a href="#">
							<s:message code="mobile.view.helper002" />
							<span class="bc-ar-btn"></span>
						</a>
					</li>
					<li >
						<a href="#">
							<s:message code="mobile.view.helper003" />
							<span class="bc-ar-btn"></span>
						</a>
					</li>
					<li class="last ">
						<a href="#">
							<s:message code="mobile.view.helper004" />
						</a>
					</li>
				</ul>
			</div>
		</div>
		<div class="sec_con">
			<!-- 채팅 컨테이너 -->
			<div class="messageContainer">
				
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">
//다국어 사용
var i18n = function(){
	var message = {
		"common006" : "<s:message code='mobile.message.common006'/>",
		"helper005" : "<s:message code='mobile.message.helper005'/>"
	};
	
	this.getMessage = function( code ){
		return message[code];
	};
};
$(document).on("pagebeforeshow", function(e, ui) {
	var helper = new mcare_mobile_helper();
	helper.init();
});


</script>

