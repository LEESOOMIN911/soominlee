<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/mobile/pay/favoriteForm.css"/>" />
<script type="text/javascript" src="<c:url value='/resources/js/mobile/pay/favoriteForm.js' />"></script>
<!-- 자주쓰는 카드 등록/수정 -->
<div data-role="content">
	<div class="mainContainer">
		<div class="container">
			<div>
				<h3><i class="fa fa-dot-circle-o"></i>&nbsp;<s:message code="mobile.view.favoriteForm001"/></h3>
			</div>
			<div class="cardNameWrap">
				<div class="cardName">
					<input type="text" id="cardName" placeholder="<s:message code="mobile.view.favoriteForm002"/>">
				</div>
				<div class="cardNameHelp">
					<ul style="list-style:none;">
						<li><s:message code="mobile.view.favoriteForm003"/></li>
					</ul>
				</div>
			</div>
		</div>
		<div class="container">
			<div>
				<h3><i class="fa fa-dot-circle-o"></i>&nbsp;<s:message code="mobile.view.favoriteForm004"/></h3>
			</div>
			<div class="cardCorpWrap">
				<select class="cardCorp" id="cardCorp">
					<option value="01">비씨</option>
					<option value="02">KB국민</option>
					<option value="03">하나(외환)</option>
					<option value="04">삼성</option>
					<option value="06">신한</option>
					<option value="07">현대</option>
					<option value="08">롯데</option>
					<option value="11">씨티</option>
					<option value="12">NH채움</option>
					<option value="13">수협</option>
					<option value="14">신협</option>
					<option value="15">우리카드</option>
					<option value="21">광주</option>
					<option value="22">전북</option>
					<option value="23">제주</option>
					<option value="24">산은캐피탈</option>
					<option value="25">해외 비자</option>
					<option value="26">해외 마스터</option>
					<option value="27">해외 다이너스</option>
					<option value="28">해외 AMX</option>
					<option value="29">해외 JCB</option>
					<option value="31">SK-OKCashBag</option>
					<option value="32">우체국</option>
					<option value="33">MG 새마을체크카드</option>
					<option value="34">중국은행체크카드</option>
					<option value="35">KDB체크카드</option>
					<option value="36">현대증권체크카드</option>
					<option value="37">저축은행</option>
				</select>
			</div>
		</div>
		<div class="container" >
			<div>
				<h3><i class="fa fa-dot-circle-o"></i>&nbsp;<s:message code="mobile.view.favoriteForm005"/></h3>
			</div>
			<div class="cardNumWrap">
				<div class="ui-grid-c">
					<div class="ui-block-a cardNum">
						<input type="tel" pattern="\d*" maxlength="4"  id="cardNum_1" title="<s:message code="mobile.view.favoriteForm006"/>"/>
					</div>
					<div class="ui-block-b cardNum">
						<input type="tel" pattern="\d*"  id="cardNum_2"  maxlength="4" title="<s:message code="mobile.view.favoriteForm006"/>" />
					</div>
					<div class="ui-block-c cardNum">
						<input type="password" pattern="\d*"  id="cardNum_3" maxlength="4" title="<s:message code="mobile.view.favoriteForm006"/>" />
					</div>
					<div class="ui-block-d cardNum">
						<input type="tel" pattern="\d*"  id="cardNum_4" maxlength="4" title="<s:message code="mobile.view.favoriteForm006"/>" />
					</div>
				</div>
			</div>
		</div>
		<div class="container" >
			<div>
				<h3><i class="fa fa-dot-circle-o"></i>&nbsp;<s:message code="mobile.view.favoriteForm007"/></h3>
			</div>
			<div class="cardValiDtWrap">
				<div class="cardValiDt">
					<select  id="cardValiDt_1">
						<option value="01">01</option>
						<option value="02">02</option>
						<option value="03">03</option>
						<option value="04">04</option>
						<option value="05">05</option>
						<option value="06">06</option>
						<option value="07">07</option>
						<option value="08">08</option>
						<option value="09">09</option>
						<option value="10">10</option>
						<option value="11">11</option>
						<option value="12">12</option>
					</select>
				</div>
				<div class="cardValiDt sec">
					<div><label>/</label></div>
				</div>
				<div class="cardValiDt">
					<select  id="cardValiDt_2">
						
					</select>
				</div>
			</div>
		</div>
		<div class="container">
			<button class="ui-btn ui-btn-b saveBtn"><s:message code="mobile.view.favoriteForm008"/></button>
		</div>
	</div>	
</div>
<input type="hidden" id="pId" value="${sessionScope.MCARE_USER_ID }"/>
<input type="hidden" id="cipherKey" value="${sessionScope.MCARE_USER_CIPHER_KEY }"/> 
<script type="text/javascript">
//다국어 사용
var i18n = function(){
	var message = {
		"common006" : "<s:message code='mobile.message.common006'/>",
		"favoriteForm009" : "<s:message code='mobile.message.favoriteForm009'/>",
		"favoriteForm010" : "<s:message code='mobile.message.favoriteForm010'/>",
		"favoriteForm011" : "<s:message code='mobile.message.favoriteForm011'/>",
		"favoriteForm012" : "<s:message code='mobile.message.favoriteForm012'/>"
	};
	this.getMessage = function( code ){
		return message[code];
	};
};
$(document).on("pagebeforeshow", function(e, ui) {
	var favoriteForm = new mcare_mobile_favoriteForm();
	favoriteForm.setGlobal( favoriteForm );
	favoriteForm.init(); 
});
$(document).on("pageshow",function(e){
	$.mobile.resetActivePageHeight(); 
});
</script>