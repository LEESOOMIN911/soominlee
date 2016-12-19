<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript" src="<c:url value='/resources/js/admin/menu.js' />"></script>
<!-- 메뉴 관리 -->
<div class="main-wrapper">
	<div class="k-header title-bar">
    	<span class="k-icon k-i-group"></span><span class="title">메뉴 관리</span>
    	<span class="category"></span>
    </div>
	<div id="wrapper" >
		<div class="left-pane">
			<div style="padding: 10px;">
				<button type="button" class="k-button" id="add-item">추가</button>
                <button type="button" class="k-button" id="remove-item">삭제</button>          	
                <button type="button" class="k-button" id="deselect-item">해제</button>
				<button type="button" class="k-button" id="reload-item">캐시 초기화</button>
			</div>
			<div id="tree-view" class="k-content"></div>
		</div>
		<div class="right-pane">
			<div style="padding: 10px;">
				<button type="button" class="k-button" id="save-item">저장</button>
			</div>
			<div data-id="tree-data" class="k-content" style="padding: 10px;" id="tree-data">
				<table style="width: 720px; margin: 0 auto;">
					<colgroup>
                        <col width="200">
                        <col width="*">
                        <col width="200">
                        <col width="*">
                    </colgroup>
                    <tbody>
                    	<tr>
                    		<th class="k-header">아이디</th>
                    		<td class="k-content">
                    			<input class="k-textbox" id="menuId" data-bind="value:menuId">
                    		</td>
                    		<th class="k-header">메뉴명</th>
                    		<td class="k-content">
                    			<input type="text" class="k-textbox" data-bind="value:menuName">
                    			<input type="hidden" data-bind="value:parentMenuId">
                    		</td>
                    	</tr>
                    	<tr>
                    		<th class="k-header">순서</th>
                    		<td class="k-content">
                    			<input type="number" id="menuOrder" max="3000" value="" data-bind="value:menuOrder"/>
                    		</td>
                    		<th class="k-header">메뉴 타입</th>
                    		<td class="k-content">
                            	<select id="menuType" data-bind="value:menuType">
                               		<option value="SIDE">사이드</option>
                               		<option value="NAVI">네비게이션</option>
                               		<option value="CONT">콘텐츠</option>
                               	</select>
                            </td>
                    	</tr>
                    	<tr>
                    		<th class="k-header">화면표시 여부</th>
                    		<td class="k-content">
                           		<label>&nbsp;<input type="radio" name="enabledYn" value="Y" data-bind="checked:enabledYn" > 예</label>
                                <label>&nbsp;<input type="radio" name="enabledYn" value="N" data-bind="checked:enabledYn" > 아니오</label>
                            </td>
                            <th class="k-header">통계여부</th>
                    		<td class="k-content">
                           		<label>&nbsp;<input type="radio" name="aggYn" value="Y" data-bind="checked:aggYn" > 예</label>
                                <label>&nbsp;<input type="radio" name="aggYn" value="N" data-bind="checked:aggYn" > 아니오</label>
                            </td>
                       </tr>
                       <tr>
                    		<th class="k-header">인증 여부</th>
                    		<td class="k-content">
                           		<label>&nbsp;<input type="radio" name="authYn" value="Y" data-bind="checked:authYn" > 예</label>
                                <label>&nbsp;<input type="radio" name="authYn" value="N" data-bind="checked:authYn" > 아니오</label>
                            </td>
                            <th class="k-header">인증 후 표시 여부</th>
                    		<td class="k-content">
                           		<label>&nbsp;<input type="radio" name="authViewYn" value="Y" data-bind="checked:authViewYn" > 예</label>
                                <label>&nbsp;<input type="radio" name="authViewYn" value="N" data-bind="checked:authViewYn" > 아니오</label>
                            </td>
                    	</tr>
                    	<!-- 
                    	<tr>
                        	<th class="k-header">
                        		<span>서비스약관</span>
                        	</th>
                            <td colspan="3">
                            	<div id="agreementsGrid"></div>
                            </td>
                        </tr>
                         -->
                    	<tr>
                    		<th class="k-header">페이지 URI</th>
                    		<td class="k-content" colspan="3">
                    			<input class="k-textbox" id="accessUriAddr" data-bind="value:accessUriAddr" style="width:100%">
                    		</td>
                    	</tr>
                    	<tr>
                    		<th class="k-header">이미지 URI</th>
                    		<td class="k-content" colspan="3">
                    			<input class="k-textbox" data-bind="value:imageUriAddr" style="width:100%">
                    		</td>
                    	</tr>
                    	<tr>
                        	<th class="k-header">설명<br/>(<span id="descLength">0</span>/<span>500bytes</span>)</th>
							<td class="k-content" colspan="3">
								<textarea id="menuDesc" data-bind="value:menuDesc" style="width: 100%; height: 150px;"></textarea>
							</td>
						</tr>
						<tr>
                        	<th class="k-header">
                        		<span>다국어</span>
                        		<br/>
                        		<span style="font-size: smaller;">메뉴명보다 우선적용됩니다</span>
                        	</th>
                            <td colspan="3">
                            	<div id="i18ns"></div>
                            </td>
                        </tr>
                        <tr>
							<th class="k-header">파라미터</th>
							<td class="k-content" colspan="3">
								<div id="reqParams"></div>
							</td>
						</tr>
                    </tbody>
				</table>
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
	<select data-id="reqParamTypeDropDownEditor">
		<c:forEach var="item" items="${reqParamTypes}">
            <option value="${item}">${item}</option>
        </c:forEach>
	</select>
</div>
<script type="text/javascript">
$(document).ready(function() { 
    var menu = new mcare_admin_menu( "${defaultLanguage}" );
    menu.init();
});
</script>
