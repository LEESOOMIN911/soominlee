<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript" src="<c:url value='/resources/js/admin/msgcheck.js' />"></script>
<!-- 메시지 전송확인 -->
<div class="main-wrapper">
	<div class="k-header title-bar">
    	<span class="k-icon k-i-group"></span>
    	<span class="title">메시지 전송확인</span>
    	<span class="category"></span>
    </div>
	<div class="select-bar" class="k-widget k-header">
		<select id="select-option">
			<option value="2">최근 &nbsp;3 일</option>
            <option value="6">최근 &nbsp;7 일</option>
            <option value="14">최근 15 일</option>
            <option value="29">최근 30 일</option>
        </select>
        <input id="strDate" value="" /> ~ <input id="endDate" value="" />
        <input type="text" class="k-textbox" value="" id="search-text" placeholder="환자번호"/>
        <a class="k-button"><span class="k-icon k-i-search" id="search"></span></a>
    </div>
	<div class="gridContainer">
	    <div id="grid"></div>
	</div>
    <div id="details"></div>
    <br/>
    <div class="notice">
    	<span style="padding:10px;font-size:15px;">*서비스 사용등록을 하지 않은 사용자에게는 PUSH를 전송할 수 없습니다. 전송결과가 조회되지 않는 사용자는 서비스 가입여부를 먼저 확인해보세요.</span>
    	<br/>
    	<span style="padding:10px;font-size:15px;">*기본적으로 서비스 사용등록을 하지 않은 사용자에게 전달되는 PUSH메시지는, 기간계에서 전송요청을 하더라도 무시됩니다.</span>
    </div>
</div>
<script lang="javascript" type="text/javascript">
	$(document).ready(function(){
		var msgcheck = new mcare_admin_msgcheck();
		msgcheck.init();
	});
</script>