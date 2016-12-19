<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript" src="<c:url value='/resources/js/admin/version.js' />"></script>
<!-- 앱버전 관리 -->
<div class="main-wrapper">
    <div class="k-header title-bar">
    	<span class="k-icon k-i-group"></span><span class="title">앱버전 관리</span>
    	<span class="category"></span>
    </div>
    <div class="gridContainer">
    	<div id="grid"></div>
    </div>
    <br/>
    <div class="notice">
    	<span style="padding:10px;font-size:15px;">* 타입, 인증서, 이름은 유일해야 합니다.</span>
    </div>
</div>
<script type="text/javascript">
$(document).ready(function () {
	var version = new mcare_admin_version();
	version.init();	
});
</script>

