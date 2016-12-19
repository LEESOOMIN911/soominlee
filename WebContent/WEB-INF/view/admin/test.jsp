<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript" src="<c:url value='/resources/js/admin/test.js' />"></script>
<!-- 메뉴 관리 -->
<div class="main-wrapper">
	<div class="k-header title-bar">
    	<span class="k-icon k-i-group"></span><span class="title">테스트 관리</span>
    	<span class="category"></span>
    </div>
	<div id="wrapper" >
		<div class="left-pane">
		<!--
			<div style="padding: 10px;">
				<button type="button" class="k-button" id="add-item">노드추가</button>
                <button type="button" class="k-button" id="remove-item">노드삭제</button>          	
                <button type="button" class="k-button" id="deselect-item">선택해제</button>
			</div>
			<div id="tree-view" class="k-content"></div> 
		-->
		</div>
		<div class="right-pane">
			<div style="padding: 10px;">
				<button type="button" class="k-button" id="save-agreement-item">동의서 저장</button>
				<button type="button" class="k-button" id="select-all-agreement-item">동의서 조회</button>
				<button type="button" class="k-button" id="update-agreement-item">동의서 수정</button>
				<button type="button" class="k-button" id="delete-agreement-item">동의서 삭제</button>
			</div>
			<div style="padding: 10px;">
				<button type="button" class="k-button" id="save-telNo-item">전화번호 저장</button>
				<button type="button" class="k-button" id="select-all-talNo-item">전화번호 조회</button>
				<button type="button" class="k-button" id="update-telNo-item">전화번호 수정</button>
				<button type="button" class="k-button" id="delete-telNo-item">전화번호 삭제</button>
			</div>
			<div style="padding: 10px;">
				<button type="button" class="k-button" id="api-req-test">API TEST</button>
				<input type="text" name="name" id="name">
			</div>
			<div style="padding: 10px;">
				<input type="hidden" name="apiUrl" id="getoutpatientlist" value="/history/getoutpatientlist">
				<input type="hidden" name="apiUrl" id="inpatientlist" value="/history/inpatientlist">
				<input type="hidden" name="apiUrl" id="healthchecklist" value="/history/healthchecklist">
				<input type="hidden" name="apiUrl" id="offlinereservation" value="/history/offlinereservation">
				<input type="text" name="ptFrrn" id="ptFrrn" value="781201">
				<input type="text" name="ptSrrn" id="ptSrrn" value="2792414">
				<input type="text" name="eiInterface" id="eiInterface" value="111|1">
				<button type="button" class="k-button" id="appointment-list">진료 이력 조회</button>
				<button type="button" class="k-button" id="getoutpatientlist-list">내원 이력 조회</button>
				<button type="button" class="k-button" id="inpatientlist-list">입/태원 이력 조회</button>
				<button type="button" class="k-button" id="healthchecklist-list">종합건진 이력 조회</button>
			</div>
		</div>
	</div>
</div>
<div style="display: none;">
	<select data-id="i18nDropDownEditor">
		<c:forEach var="item" items="${supportedLanguages}">
            <option value="${item}">${item}</option>
        </c:forEach>
	</select>
</div>
<script type="text/javascript">
$(document).ready(function() { 
    var tester = new mcare_admin_test( "${defaultLanguage}" );
    tester.init();
});
</script>
