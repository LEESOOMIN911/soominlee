<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/mobile/user/resetPWD.css"/>" />
<script type="text/javascript" src="<c:url value='/resources/js/mobile/user/resetPWD.js' />"></script>

<div data-role="content">
	환자번호    : <div id="userId">${userId}</div>
	이름       : <div id="sName">${userName}</div>
	생년월일    : <div id="userBirthDate">${userBirthDate}</div>
	성별       : <div id="sGenderCode">${userGenderCode}</div>
</div>

<script type="text/javascript"> 

</script>
