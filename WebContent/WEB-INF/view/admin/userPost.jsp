<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript" src="<c:url value='/resources/js/admin/userPost.js' />"></script>
<!-- 시군구별 가입통계 -->
<div class="main-wrapper">
	<div class="k-header title-bar">
    	<span class="k-icon k-i-group"></span>
    	<span class="title">지역별 가입통계</span>
    	<span class="category"></span>
    </div>
    <div class="select-bar" class="k-widget k-header">
        <a class="k-button csvBtn" href="#" id="csvSave" >csv저장</a>
    </div>
    <div>
    	<h3> * 통계생성일 기준 가입통계정보입니다.</h3>
    </div>
    <div class="chartContainer">
		<div class="chart">
			<canvas id="chart" style="width:100%; height: 400px;"></canvas>
		</div>
		<div id="chart_legend" class="chart_legend"></div>
    </div>
	<br/>
	<div class="gridContainer">
	    <div id="grid"></div>
	</div>
</div>
<script type="text/javascript">
$(document).ready(function () {
	var userPost = new mcare_admin_userPost();
	userPost.init();
});
</script>
