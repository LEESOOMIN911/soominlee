<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript" src="<c:url value='/resources/js/admin/agreement.js' />"></script>
<!-- 동의서 관리 -->
<div class="main-wrapper">
    <div class="k-header title-bar">
    	<span class="k-icon k-i-group"></span><span class="title">서비스 약관 관리</span>
    	<span class="category"></span>
    </div>
    <div class="gridContainer">
    	<div id="grid"></div>
    </div>
</div>
<script type="text/javascript">
$(document).ready(function () {
	var agreement = new mcare_admin_agreement();
	agreement.init();	
});
</script>
<style type="text/css">
	.k-grid td {
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
	}
.k-edit-form-container
{
    width: 700px;
}
.k-edit-form-container .k-edit-label
{
    width: 15%;
    text-align: left;
}
.k-edit-form-container .k-edit-field
{
    width: 75%;
}
.k-edit-form-container .k-edit-field > .k-textbox,
.k-edit-form-container .k-edit-field > .k-widget
{
    width: 98%;
}
.k-edit-form-container .k-button.k-grid-update{margin-left:37%;}
</style>

