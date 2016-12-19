<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript" src="<c:url value='/resources/js/admin/userPlatform.js' />"></script>
<!-- 사용자 플랫폼 정보 현황 -->
<style type="text/css">
	ul.infoList  * {font-size : 1.5em;} 
</style>
<div class="main-wrapper">
	<div class="k-header title-bar">
    	<span class="k-icon k-i-group"></span>
    	<span class="title">사용자 플랫폼 정보 현황</span>
    	<span class="category"></span>
    </div>
	<div style="margin-top:15px;">
    	<span style="padding:10px;font-size:15px;">
    		* 실시간 상태입니다.
    	</span>
    </div>
    <div class="chartContainer">
    	<div class="subContainer" style="float:left;width:50%;">
			<div class="chart">
				<canvas id="chart" style="width:100%; height: 250px;"></canvas>
			</div>
			<div id="chart_legend" class="chart_legend"></div>
    	</div>
    	<div class="subContainer" style="float:left;width:50%;">
			<div id="grid"></div>
    	</div>
    </div>
	<br/>
	<div class="infoContainer" style="clear:both;">
	    <ul class="infoList" >
	    	<li> 총 사용자 : <span id="totalUserCnt" style="font-weight:bold;"></span></li>
	    	<li> Push 메시지 수신 가능한 사용자 : <span id="validUserCnt" style="font-weight:bold;"></span><span id="cntPersent" style="font-weight:bold;"></span></li>
	    	<li> 등록된 토큰 수 : <span id="registeredTokenCnt" style="font-weight:bold;"></span></li>
	    	<li> 1인당 평균 토큰 수 : <span id="cntPersentUser" style="font-weight:bold;"></span> 개</li>
	    </ul>
	</div>
</div>
<script type="text/javascript">
$(document).ready(function () {
    var userPlatform = new mcare_admin_userPlatform();
    userPlatform.init();
});
</script>