<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript" src="<c:url value='/resources/js/admin/loginHistory.js' />"></script>
<!-- 로그인 현황 -->
<div class="main-wrapper">
	<div class="k-header title-bar">
    	<span class="k-icon k-i-group"></span>
    	<span class="title">로그인 현황</span>
    	<span class="category"></span>
    </div>
	<div class="select-bar" class="k-widget k-header">
		<select id="select-option">
			<option value="0">어제</option>
            <option value="6">최근  &nbsp;7 일</option>
            <option value="29">최근 30 일</option>
        </select>
        <input id="strDate" value="" /> ~ <input id="endDate" value="" />
        <input type="text" class="k-textbox" value="" id="search-text" placeholder="로그인 아이디"/>
        <a class="k-button"><span class="k-icon k-i-search" id="search"></span></a>
    </div>
	<div class="gridContainer">
	    <div id="grid"></div>
	</div>
    <div id="details"></div>
</div>
<script lang="javascript" type="text/javascript">
	$(document).ready(function(){
		var loginHistory = new mcare_admin_loginHistory();
		loginHistory.init();
	});
</script>