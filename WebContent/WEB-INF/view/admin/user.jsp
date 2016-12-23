<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<script type="text/javascript" src="<c:url value='/resources/js/admin/user.js' />"></script>
<!-- 사용자 관리 -->
<div class="main-wrapper">
    <div class="k-header title-bar">
    	<span class="k-icon k-i-group"></span><span class="title">사용자 관리</span>
    	<span class="category"></span>
    </div>
    <div class="btnWrap" style="overflow:auto;">
    	<div style="width:50%;text-align:left;float:left;">
    		<input type="text" class="k-textbox" value="" id="searchPId" placeholder="환자번호"/>
        	<a class="k-button"><span class="k-icon k-i-search" id="search"></span></a>
    	</div>
    	<div style="width:50%;text-align:right;float:left;">
    		<a class="k-button newUserBtn"><span>사용등록</span></a>
	    	<a class="k-button userDelBtn"><span>선택된 사용자 탈퇴처리</span></a>
    	</div>
    </div>
    <div class="gridContainer" style="clear:both;">
    	<div id="grid"></div>
    </div>
</div>
<div id="newForm" style="display:none;">
	<div style="text-align:center;position:relative;">
		<div>
			<div class="k-edit-label" style="float:left;width:25%;">
				<label for="newPId" style="line-height: 30px;">환자번호</label>
			</div>
			<div class="k-edit-field"style="float:left;width:50%;">
				<input type="number" class="k-input k-textbox" id="newPId" placeholder="환자번호를 입력하세요." style="width:100%;"/>
			</div>
			<a href="#" class="k-button checkUserPId" >확인</a>
		</div>
		<div style="clear:both;">
			<a href="#" class="k-button k-button-icontext k-grid-update modalSubmit" ><span class="k-icon k-update"></span>저장</a>
			<a href="#" class="k-button k-button-icontext k-grid-cancel modalClose" ><span class="k-icon k-cancel"></span>취소</a>
		</div>
	</div>
</div>
<input type="hidden" id="withdrawalDate" value='<s:eval expression="@mcareConfig['withdrawal.deadline.date']"/>' />
<script type="text/javascript">
$(document).ready(function () {
	var user = new mcare_admin_user();
	user.init();	
});
</script>

