<%@page import="com.dbs.mcare.MCareConstants"%>
<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<script type="text/javascript" src="<c:url value='/resources/js/admin/login.js' />"></script>
<style type="text/css">
	.lineContainer{padding:10px;}
	.lineContainer input{ height:32px; width: 95%;}
</style>
<div class="mainContainer" id="loginForm">
	<div class="formContainer" >
		<div class="lineContainer">
			<img src="<c:url value='/resources/css/images/mobile/main/toplogo.png' />" width="270">
		</div>
		<div class="lineContainer">
			<i class="material-icons">perm_identity</i>
			<input type="text" id="_username" placeholder="아이디를 입력하세요" />
		</div>
		<div class="lineContainer">
			<i class="material-icons">lock_outline</i>
			<input type="password" id="_password" placeholder="비밀번호를 입력하세요" />
		</div>
		<div class="lineContainer">
			<input type="button" id="login_btn" class="k-button"  value="로그인" />
		</div>
	</div>
</div>
<script type="text/javascript">
$(document).ready(function() {
	var login = new mcare_admin_login();
	login.init();
});
</script>