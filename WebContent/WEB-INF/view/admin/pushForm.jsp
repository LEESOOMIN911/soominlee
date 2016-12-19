<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript" src="<c:url value='/resources/js/admin/pushForm.js' />"></script>
<!-- 메시지형식 관리 -->
<div class="main-wrapper">
    <div class="k-header title-bar">
    	<span class="k-icon k-i-group"></span><span class="title">메시지형식 관리</span>
    	<span class="category"></span>
    </div>
    <div class="gridContainer">
    	<div id="grid"></div>
    </div>
    <div style="margin-top:15px;">
    	<span style="padding:10px;font-size:15px;">
    		* 메시지 형식이 변경되면 Message Notification Server를 재구동해야 합니다.
    	</span>
    	<br/>
    	<span style="padding:10px;font-size:15px;">
    		* 서버를 재구동 하더라도 유실되는 메시지는 없습니다.
    	</span>
    </div>
</div>
<script type="text/javascript">
$(document).ready(function () {
	var pushForm = new mcare_admin_pushForm();
	pushForm.init();	
});
</script>

