<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/mobile/history/admission.css"/>" />
<script type="text/javascript" src="<c:url value='/resources/js/mobile/history/treatmentHistory.js' />"></script>
<script type="text/javascript" src="<c:url value='/resources/js/mobile/history/admission.js' />"></script>
<!-- 휴대폰 인증 실패-->
<div data-role="content">
	<div >
		<p class="errormsg">
			본인인증에 실패하였습니다.<br/>
			<c:if test="${not empty sRequestNumber}">
				<span style="font-size: 12px;">
					(코드: ${fn:substring(sRequestNumber, 10, 25)})
				</span>
			</c:if>
		</p>
	</div>
</div>

<script type="text/javascript">
//다국어 사용
var i18n = function(){
	var message = {
		"common006" : "<s:message code='mobile.message.common006'/>"
	};
	
	this.getMessage = function( code ){
		return message[code];
	};
};
$(document).on("pagebeforeshow", function(e, ui) {
	
});
</script>

