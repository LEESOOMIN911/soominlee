<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/mobile/pay/payment.css"/>" />
<script type="text/javascript" src="<c:url value='/resources/js/mobile/pay/payment.js' />"></script>
<!-- 자주쓰는 카드 관리 -->
<div data-role="content">
	<div class="mainContainer">
		<div class="container">
			<div>
				<h3><i class="fa fa-dot-circle-o"></i>&nbsp;<s:message code="mobile.view.payment001"/></h3>
			</div>
			<div class="payAmountWrap">
				<div class="payAmount">
					<label><span id="payAmount"></span>&nbsp;<s:message code="mobile.view.payment002"/></label>
				</div>
			</div>
		</div>
		<div class="container">
			<div>
				<h3><i class="fa fa-dot-circle-o"></i>&nbsp;<s:message code="mobile.view.payment003"/></h3>
			</div>
			<div class="cardNameWrap">
				<select class="cardName" id="cardName">
					
				</select>
			</div>
		</div>
		<div class="container">
			<div>
				<h3><i class="fa fa-dot-circle-o"></i>&nbsp;<s:message code="mobile.view.payment004"/></h3>
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
				<h3><i class="fa fa-dot-circle-o"></i>&nbsp;<s:message code="mobile.view.payment005"/></h3>
			</div>
			<div class="cardNumWrap">
				<div class="ui-grid-c">
					<div class="ui-block-a cardNum">
						<input type="tel" pattern="\d*" maxlength="4"  id="cardNum_1" title="<s:message code="mobile.message.payment018"/>"/>
					</div>
					<div class="ui-block-b cardNum">
						<input type="tel" pattern="\d*"  id="cardNum_2"  maxlength="4" title="<s:message code="mobile.message.payment018"/>" />
					</div>
					<div class="ui-block-c cardNum">
						<input type="password" pattern="\d*"  id="cardNum_3" maxlength="4" title="<s:message code="mobile.message.payment018"/>" />
					</div>
					<div class="ui-block-d cardNum">
						<input type="tel" pattern="\d*"  id="cardNum_4" maxlength="4" title="<s:message code="mobile.message.payment018"/>" />
					</div>
				</div>
			</div>
		</div>
		<div class="container" >
			<div>
				<h3><i class="fa fa-dot-circle-o"></i>&nbsp;<s:message code="mobile.view.payment006"/></h3>
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
			<div>
				<h3><i class="fa fa-dot-circle-o"></i>&nbsp;CVC</h3>
			</div>
			<div class="cardCVCWrap">
				<div class="cardCVC">
					<input type="tel" pattern="\d*"  id="cardCVC" maxlength="3" />
				</div>
			</div>
		</div>
		<div class="container">
			<div>
				<h3><i class="fa fa-dot-circle-o"></i>&nbsp;<s:message code="mobile.view.payment007"/></h3>
			</div>
			<div class="cardPassWrap">
				<div  class="cardPass">
					<input type="password" id="cardPass" maxlength="2" />
				</div>
				<div class="cardPass">
					<label>**</label>
				</div>
			</div>
		</div>
		<div class="container">
			<div>
				<h3><i class="fa fa-dot-circle-o"></i>&nbsp;<s:message code="mobile.view.payment008"/></h3>
			</div>
			<div class="cardTypeWrap">
				<div class="cardType">
					<select class="" id="cardType" data-role="flipswitch" data-theme="b">
						<option value="0"><s:message code="mobile.view.payment009"/></option>
						<option value="1"><s:message code="mobile.view.payment010"/></option>
					</select>
				</div>
			</div>
		</div>
		<div class="container">
			<div>
				<h3><i class="fa fa-dot-circle-o"></i>&nbsp;<s:message code="mobile.view.payment011"/></h3>
			</div>
			<div class="checkNumWrap">
				<div class="checkNum">
					<input type="tel" pattern="\d*"  id="checkNum" placeholder="<s:message code="mobile.source.payment016"/>" maxlength="6" />
				</div>
			</div>
		</div>
		<div class="container">
			<div>
				<h3><i class="fa fa-dot-circle-o"></i>&nbsp;<s:message code="mobile.view.payment012"/></h3>
			</div>
			<div class="payPeriodWrap">
				<div class="payPeriod">
					<select class="payPeriodSel" id="payPeriod">
						<option value="0"><s:message code="mobile.view.payment013"/></option>
						<option value="2">2<s:message code="mobile.view.payment014"/></option>
						<option value="3">3<s:message code="mobile.view.payment014"/></option>
						<option value="6">6<s:message code="mobile.view.payment014"/></option>
						<option value="12">12<s:message code="mobile.view.payment014"/></option>
						<option value="24">24<s:message code="mobile.view.payment014"/></option>
					</select>
				</div>
			</div>
		</div>
		<div class="container">
			<div class="confirmBtnWrap">
				<button class="ui-btn ui-btn-b confirmBtn"><s:message code="mobile.view.payment015"/></button>
			</div>
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
		"payment016" :"<s:message code='mobile.source.payment016'/>",
		"payment017" :"<s:message code='mobile.source.payment017'/>",
		"payment018" :"<s:message code='mobile.message.payment018'/>",
		"payment019" :"<s:message code='mobile.message.payment019'/>",
		"payment020" :"<s:message code='mobile.message.payment020'/>",
		"payment021" :"<s:message code='mobile.message.payment021'/>",
		"payment022" :"<s:message code='mobile.message.payment022'/>",
		"payment023" :"<s:message code='mobile.message.payment023'/>",
		"payment024" :"<s:message code='mobile.message.payment024'/>",
		"payment025" :"<s:message code='mobile.message.payment025'/>",
		"payment026" :"<s:message code='mobile.message.payment026'/>",
		"payment027" :"<s:message code='mobile.message.payment027'/>",
		"payment028" :"<s:message code='mobile.source.payment028'/>"
	};
	this.getMessage = function( code ){
		return message[code];
	};
};
$(document).on("pagebeforeshow", function(e, ui) {
	var payment = new mcare_mobile_payment();
	payment.setGlobal( payment );
	payment.init(); 
});
$(document).on("pageshow",function(e){
	$.mobile.resetActivePageHeight(); 
});
</script>