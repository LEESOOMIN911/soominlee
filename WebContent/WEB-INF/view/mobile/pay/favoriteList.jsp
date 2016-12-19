<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/mobile/pay/favoriteList.css"/>" />
<script type="text/javascript" src="<c:url value='/resources/js/mobile/pay/favoriteList.js' />"></script>
<!-- 자주쓰는 카드 관리 목록 -->
<div data-role="content">
	<div class="mainContainer">
		<div class="container">
			<ul style="list-style:none;">
				<li>&nbsp;-&nbsp;<s:message code="mobile.view.favoriteList001"/></li>
				<li>&nbsp;-&nbsp;<s:message code="mobile.view.favoriteList002"/>&nbsp;
					<span id="maxCardNum" style="font-weight:bold;"></span>
					<s:message code="mobile.view.favoriteList003"/>
				</li>
			</ul>
		</div>
		<div class="container">
			<button class="ui-btn ui-btn-b registerBtn"><s:message code="mobile.view.favoriteList004"/></button>
		</div>
		<div class="container" >
			<fieldset>		
				<legend>
					<i class="fa fa-dot-circle-o"></i>&nbsp;<s:message code="mobile.view.favoriteList005"/>
				</legend>
				<div class="cardContainer">
					<div class="cardList">
						
					</div>
				</div>
			</fieldset>
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
		"favoriteList006" : "<s:message code='mobile.message.favoriteList006'/>",
		"favoriteList007" : "<s:message code='mobile.message.favoriteList007'/>"
	};
	this.getMessage = function( code ){
		return message[code];
	};
};
$(document).on("pagebeforeshow", function(e, ui) {
	var favoriteList = new mcare_mobile_favoriteList();
	favoriteList.setGlobal(favoriteList);
	favoriteList.init(); 
});
$(document).on("pageshow",function(e){
	$.mobile.resetActivePageHeight(); 
});
</script>