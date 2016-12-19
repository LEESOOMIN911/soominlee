<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript" src="<c:url value='/resources/js/admin/dailyRegister.js' />"></script>
<!-- 날짜별 가입현황 -->
<div class="main-wrapper">
	<div class="k-header title-bar">
    	<span class="k-icon k-i-group"></span>
    	<span class="title">일자별 가입현황</span>
    	<span class="category"></span>
    </div>
	<div class="select-bar" class="k-widget k-header">
		<select id="select-option">
            <option value="6">최근 7 일</option>
            <option value="13">최근 14 일</option>
            <option value="29">최근 30 일</option>
            <option value="59">최근 60 일</option>
            <option value="89">최근 90 일</option>
        </select>
        <input id="strDate" value="" /> ~ <input id="endDate" value="" />
        <a class="k-button"><span class="k-icon k-i-search" id="search"></span></a>
        <a class="k-button csvBtn" href="#" id="csvSave" >csv저장</a>
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
	var dailyRegister = new mcare_admin_dailyRegister();
	dailyRegister.init();
});
</script>
