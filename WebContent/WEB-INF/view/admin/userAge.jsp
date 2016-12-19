<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript" src="<c:url value='/resources/js/admin/userAge.js' />"></script>
<!-- 연령별 가입통계 -->
<div class="main-wrapper">
	<div class="k-header title-bar">
    	<span class="k-icon k-i-group"></span>
    	<span class="title">연령별 가입통계</span>
    	<span class="category"></span>
    </div>
    <div class="select-bar" class="k-widget k-header">
        <a class="k-button csvBtn" href="#" id="csvSave" >csv저장</a>
    </div>
    <div>
    	<h3> * 통계 생성 전일 기준 가입 현황 정보입니다.</h3>
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
	<div class="bottomContainer" style="padding-left:5px;padding-top:8px;">
		<span style="font-weight:bold;"> * 연령대 : 0(0~9세), 10(10~19세), 20(20~29세) 등</span>
	</div>
</div>
<script type="text/javascript">
$(document).ready(function () {
	var userAge = new mcare_admin_userAge();
	userAge.init();
});
</script>
