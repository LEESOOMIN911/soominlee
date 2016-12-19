<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript" src="<c:url value='/resources/js/admin/api.js' />"></script>
<!-- API 관리 -->
<div class="main-wrapper">
	<div class="k-header title-bar">
    	<span class="k-icon k-i-group"></span><span class="title">API 관리</span>
    	<span class="category"></span>
    </div>
	<div id="wrapper">
		<div class="left-pane">
			<div style="padding: 10px;">
				<button type="button" class="k-button" id="add-item">추가</button>
                <button type="button" class="k-button" id="remove-item">삭제</button>          	
                <button type="button" class="k-button" id="deselect-item">해제</button>
				<button type="button" class="k-button" id="reload-item">캐시 초기화</button>
			</div>
			<div id="tree-view" class="k-content"></div>
		</div>
		<div class="right-pane" style="display: none;">
			<div style="padding: 10px;">
				<button type="button" class="k-button" id="save-item">저장</button>
			</div>
			<div class="edit-view" class="k-content">
				<table style="width: 740px; margin: 0 auto;">
					<tbody>
						<tr>
							<th class="k-header"><span>아이디</span></th>
							<td class="k-content"><input class="k-textbox wid100" disabled="disabled" data-bind="value:id" placeholder="아이디 자동 생성" /></td>
							<th class="k-header"><span>이름</span></th>
							<td class="k-content"><input class="k-textbox wid100" data-bind="value:name" /></td>
						</tr>
						<tr>
							<th class="k-header"><span>API URI</span></th>
							<td class="k-content" colspan="3"><span id="categoryPaths" data-bind="text:data.catPathName"></span> <input class="k-textbox"
								data-bind="value:data.reqUrlAddr" style="width: 223px;" placeholder="특수문자를 제외한 문자와 숫자 조합"></td>
						</tr>
						<tr>
							<th class="k-header"><span>API 타입</span></th>
							<td class="k-content">
								<select id="apiType" data-bind="value:data.apiType" style="width: 70%;">
									<c:forEach var="item" items="${apiTypes}">
										<option value="${item}">${item}</option>
									</c:forEach>
								</select>
								<span class="k-button apiTypeHelp">도움말</span>
							</td>
							<th class="k-header"><span>연산유형</span></th>
							<td class="k-content">
								<select id="httpMethodType" data-bind="value:data.httpMethodType" style="width: 100%;">
									<option value="">==선택==</option>
									<c:forEach var="item" items="${httpMethodTypes}">
										<option value="${item}">${item}
											<%-- (${item.value}) --%></option>
									</c:forEach>
								</select>
							</td>
						</tr>
						<tr>
							<th class="k-header"><span>응답결과 타입</span></th>
							<td class="k-content">
								<select id="resultType" data-bind="value:data.resultType" style="width: 100%;">
									<option value="">==선택==</option>
									<c:forEach var="item" items="${resultTypes}">
										<option value="${item}">${item}</option>
									</c:forEach>
								</select>
							</td>
							<th class="k-header"><span>데이터소스</span></th>
							<td class="k-content">
								<select id="dataSourceName" data-bind="value:data.dataSourceName" style="width: 100%;">
									<option value="">==선택==</option>
									<c:forEach var="item" items="${dataSourceNames}">
										<option value="${item}">${item}</option>
									</c:forEach>
								</select>
							</td>
						</tr>
						<tr id="ws-row" style="display: none;">
							<th class="k-header"><span>웹서비스 요청</span></th>
							<td class="k-content" colspan="3">
								<div>
									<table>
										<colgroup>
											<col width="24%" />
											<col width="76%" />
										</colgroup>
										<tbody>
											<tr>
												<th class="k-header"><span>URL</span></th>
												<td class="k-content"><textarea class="k-textbox" data-bind="value:data.targetUrlAddr" style="width: 100%;height: 100px;"></textarea></td>
											</tr>
											<tr>
												<th class="k-header"><span>헤더 정보</span></th>
												<td class="k-content" style="padding: 0;">
													<div id="wsHeaders"></div>
												</td>
											</tr>
										</tbody>
									</table>
								</div>
							</td>
						</tr>
						<tr id="query-row">
							<th class="k-header"><span>SQL</span></th>
							<td class="k-content" colspan="3"><textarea id="queryMsg" data-bind="value:data.queryMsg" style="width: 100%; height: 100px;"></textarea></td>
						</tr>
						<tr id="procd-row" style="display: none;">
							<th class="k-header">Procedure</th>
							<td class="k-content" colspan="3"><input class="k-textbox wid100" data-bind="value:data.targetName"></td>
						</tr>
						<tr>
							<th class="k-header">파라미터</th>
							<td class="k-content" colspan="3">
								<div id="reqParams"></div>
							</td>
						</tr>
						<tr>
							<th class="k-header">결과샘플</th>
							<td class="k-content" colspan="3">
								<textarea id="resSampleCl" data-bind="value:data.resSampleCl" style="width: 100%; height: 100px;"></textarea>
							</td>
						</tr>
						<tr>
							<th class="k-header">설명<br/>(<span id="descLength">0</span>/<span>500bytes</span>)</th>
							<td class="k-content" colspan="3"><textarea id="apiDesc" data-bind="value:data.apiDesc" style="width: 100%; height: 100px;"></textarea></td>
						</tr>
						<tr>
							<th class="k-header">최초 생성일</th>
							<td class="k-content"><span data-bind="text:getCreateDate"></span></td>
							<th class="k-header">최초 생성자</th>
							<td class="k-content"><span data-bind="text:data.createId"></span></td>
						</tr>
						<tr>
							<th class="k-header">최종 변경일</th>
							<td class="k-content"><span data-bind="text:getUpdateDate"></span></td>
							<th class="k-header">최종 변경자</th>
							<td class="k-content"><span data-bind="text:data.updateId"></span></td>
						</tr>
					</tbody>
				</table>
			</div>
			<!-- 도움말 -->
			<div id="helpContents" style="display:none;">
				<blockquote>
				  <ul style="list-style:none;padding:0;margin:0;">
				  <li style="padding:15px 0;line-height: 1.5em;">
				  	<strong>* webservice *</strong>
				  	<br/> 
				  	&nbsp;응답결과는 요청헤더의 Content-Type, Accept 순으로 확인해서 마지막으로 만나는 것이 최종 응답유형이된다. 
					예를들어 Content-Type이 XML이고, Accept는 JSON이라면 최종적으로 JSON이 응답유형이 된다.  <br/><br/>
					이 응답 유형이 JSON인 경우에 한해서 응답결과타입(MAP, LIST, INT)이 유효한데, INT는 사용되지 않는다. <br/><br/> 
					응답결과는 XML, JSON, URL-ENCODE가 준비되어 있으며 이외의 경우에는 Map에 key를 text로 하여 응답결과를 붙여서 반환한다. 
					응답결과가 XML이면 JSON객체로 반환한다. <br/><br/> 
					응답결과가 JSON이고, 응답결과타입이 LIST인 경우 리턴타입은 List&lt;Map&lt;String, Object&gt;&gt; 이다. <br/>
					응답결과가 JSON이고, 응답결과타입이 MAP인 경우 리턴타입은 Map&lt;String, Object&gt;이다. <br/>
					URL-ENCODE의 경우 리턴타입은 MultiValueMap&lt;String, String&gt; 이다. <br/>
					</li>
				  <li style="padding:15px 0;line-height: 1.5em;">
				  <strong>* sql *</strong> 
				  <br/>
				  &nbsp;HttpMethod타입에 따라서 질의문이 실행되는데 HttpMethod타입이 GET인 경우와 그렇지 않은 경우로 분류된다. <br/>
					GET인 경우 응답결과타입이 MAP인 경우, queryForMap형태로 질의문이 실행된다. 즉, 결과가 1개인 SELECT문이 적합하다. <br/>
					LIST인 경우에는 queryForList형태로 질의문이 실행되므로, 결과가 2개 이상인 SELECT문에 적합하다. INT는 부적절한 응답결과타입이다. <br/><br/>
					HttpMethod타입이 GET이 아닌경우, INT가 아니면 부적절한 응답결과 타입이며 질의문은 INSERT, UPDATE, DELETE문이 적합한다. 반환되는 값은 해당 질의문에 의해 영향받은 row의 갯수이다. 		
				  </li>
				  <li style="padding:15px 0;line-height: 1.5em;">
				  <strong>* procedure *</strong> 
				  <br/>
				  &nbsp;Procedure 입력란(Api.targetName) 작성이 필요한다. 지정하는 형태는 다음과 같다.<br/> 
					(1) {procedure name}<br/> 
					(2) {schema name}.{procedure name}<br/> 
					(3) {schema name}.{catalog name}.{procedure name}<br/> 
					이외의 경우는 부적절한 형태이다. 
					실행결과는 응답결과타입에 상관없이 Map&lt;String, Object&gt;이다. 
				  </li>
				 </ul>
				</blockquote>
			</div>
		</div>
	</div>
</div>
<div style="display: none;">
	<select data-id="reqParamTypeDropDownEditor">
		<c:forEach var="item" items="${reqParamTypes}">
            <option value="${item}">${item}</option>
        </c:forEach>
	</select>
	<select data-id="wsHeaderTypeDropDownEditor">
		<c:forEach var="item" items="${wsHeaderTypes}">
			<option value="${item.value}">${item.value}</option>
		</c:forEach>
	</select>
</div>
<script type="text/javascript">
$(document).ready(function() {
	var api = new mcare_admin_api();
	api.init();	
});
</script>