<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript" src="<c:url value='/resources/js/admin/telNo.js' />"></script>
<!-- 주요 전화번호 관리 -->
<div class="main-wrapper">
    <div class="k-header title-bar">
    	<span class="k-icon k-i-group"></span><span class="title">주요 전화번호 관리</span>
    	<span class="category"></span>
    </div>
    <div class="gridContainer">
    	<div id="grid"></div>
    </div>
</div>
<script type="text/javascript">
$(document).ready(function () {
	var telNo = new mcare_admin_telNo();
	telNo.init();	
});
</script>

