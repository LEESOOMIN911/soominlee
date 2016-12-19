<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<div id="mcareError">
	<div data-role="header">
		<h2>에러안내</h2>
	</div>
	<div data-role="content">
		<div>
			<p class="errormsg">
				${msg}
			</p>
		</div>
		<a class="ui-btn ui-btn-b" href="${pageContext.request.contextPath}/index.page" >확인</a>
	</div>
</div>

<div id="adminError">
	<div >
		<p class="errormsg">
			${msg}
		</p>
	</div>
	<a class="ui-btn ui-btn-b" href="${pageContext.request.contextPath}/index.page" >확인</a>
</div>

<script type="text/javascript">
$(document).on('pagebeforeshow', function(e, ui) {
	var mcare = new mcare_mobile();
	if (mcare.isMobile() == true) {
		$('#adminError').hide();
	} else {
		$('#mcareError').hide();
	}
});
</script>

