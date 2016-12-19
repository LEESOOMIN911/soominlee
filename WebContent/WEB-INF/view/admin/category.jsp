<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript" src="<c:url value='/resources/js/admin/category.js' />"></script>
<!-- 카테고리 관리 -->
<div class="main-wrapper">
	<div class="k-header title-bar">
    	<span class="k-icon k-i-group"></span><span class="title">카테고리 관리</span>
    	<span class="category"></span>
    </div>
	<div id="wrapper">
		<div class="left-pane">
			<div style="padding: 10px;">
				<button type="button" class="k-button" id="add-item">추가</button>
                <button type="button" class="k-button" id="remove-item">삭제</button>          	
                <button type="button" class="k-button" id="deselect-item">해제</button>
			</div>
			<div id="tree-view" class="k-content"></div>
		</div>
		<div class="right-pane">
			<div style="padding: 10px;">
				<button type="button" class="k-button" id="save-item">저장</button>
			</div>
			<div class="edit-view" class="k-content">
				<table style="width: 740px; margin: 0 auto;">
					<colgroup>
						<col width="150">
						<col width="*">
						<col width="150">
						<col width="*">
					</colgroup>
					<tbody>
						<tr>
							<th class="k-header">아이디</th>
							<td class="k-content"><input class="k-textbox wid100" disabled="disabled" data-bind="value:catSeq" placeholder="아이디 자동 생성" /> <input
								type="hidden" data-bind="value:parentCatSeq" /></td>
							<th class="k-header">이름</th>
							<td class="k-content"><input class="k-textbox" id="catName" data-bind="value:catName"></td>
						</tr>
						<tr>
							<th class="k-header">카테고리 URI</th>
							<td class="k-content" colspan="3"><label data-bind="text:parentPath"></label><input class="k-textbox" id="pathName" data-bind="value:pathName"><label>/</label>
							</td>
						</tr>
						<tr>
							<th class="k-header"><span>설명</span></th>
							<td class="k-content" colspan="3"><textarea id="catDesc" data-bind="value:catDesc" style="width: 100%; height: 100px;"></textarea></td>
						</tr>
						<tr>
							<th class="k-header">최초 생성일</th>
							<td class="k-content"><span data-bind="text:getCreateDate"></span></td>
							<th class="k-header">최초 생성자</th>
							<td class="k-content"><span data-bind="text:createId"></span></td>
						</tr>
						<tr>
							<th class="k-header">최종 변경일</th>
							<td class="k-content"><span data-bind="text:getUpdateDate"></span></td>
							<th class="k-header">최종 변경자</th>
							<td class="k-content"><span data-bind="text:updateId"></span></td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
	$(document).ready(function(){
		var category = new mcare_admin_category();
		category.init();
	});
</script>


