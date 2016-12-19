<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/mobile/user/register.css"/>" />
<script type="text/javascript" src="<c:url value='/resources/js/mobile/user/register.js' />"></script>
<!-- 사용등록 -->
<div data-role="content">
	<h3 class="subTitle"> <i class="fa fa-info-circle"></i> <s:message code="mobile.view.register001" /></h3>
	<div>
		<div>
			<div class="formDiv">
				<input type="number" pattern="\d*" id="pId" data-clear-btn="turn" value="" />
				<a href="#" id="search" class="ui-btn ui-shadow ui-corner-all ui-icon-search ui-btn-icon-notext ui-btn-inline" ></a>
			</div>
			<!-- <div class="formDiv">
				<a href="#" id="search" class="ui-btn ui-shadow ui-corner-all ui-icon-search ui-btn-icon-notext ui-btn-inline" ></a>
			</div> -->
		</div>
	</div>
    <button id="next" class="ui-btn ui-btn-b"><s:message code="mobile.view.register002" /></button>
    <div class="example">
    	
    	<h2><i class="fa fa-flag" ></i>&nbsp;<s:message code="mobile.view.register008" /></h2>
    	<div class="example_contents">
    		<div data-role="collapsible" data-collapsed="false" data-expanded-icon="carat-u" data-collapsed-icon="carat-d" data-iconpos="right">
    			<h3><s:message code="mobile.view.register009" /></h3>
    			<ul>
    				<li><span>-&nbsp;<s:message code="mobile.view.register010" /></span></li>
    				<li>
    					<span>-&nbsp;<s:message code="mobile.view.register011" />&nbsp;</span>
    					<span>
    						<i class="fa fa-search"></i>
    					</span>
    					<span><s:message code="mobile.view.register012" /></span>
    				</li>
    			</ul>
    		</div>
    		<div>
	    		<img src="<c:url value="/resources/css/images/mobile/main/medicalIdCard.png"/>" alt="진료카드이미지" />
	    	</div>
	    	<div>
	    		<p><s:message code="mobile.view.register006" /></p>
	    	</div>
    		<div data-role="collapsible" data-expanded-icon="carat-u" data-collapsed-icon="carat-d" data-iconpos="right">
    			<h3><s:message code="mobile.view.register013" /></h3>
    			<ul>
    				<li><span>-&nbsp;<s:message code="mobile.view.register014" /></span></li>
    				<li><span>-&nbsp;<s:message code="mobile.view.register015" /></span></li>
    			</ul>
    		</div>
    		<div data-role="collapsible" data-expanded-icon="carat-u" data-collapsed-icon="carat-d" data-iconpos="right">
    			<h3><s:message code="mobile.view.register016" /></h3>
    			<ul>
    				<li><span>-&nbsp;<s:message code="mobile.view.register017" /></span></li>
    			</ul>
    		</div>
    		<div data-role="collapsible" data-expanded-icon="carat-u" data-collapsed-icon="carat-d" data-iconpos="right">
    			<h3><s:message code="mobile.view.register018" /></h3>
    			<ul>
    				<li><span>-&nbsp;<s:message code="mobile.view.register019" /></span></li>
    			</ul>
    		</div>
    		<div data-role="collapsible" data-expanded-icon="carat-u" data-collapsed-icon="carat-d" data-iconpos="right">
    			<h3><s:message code="mobile.view.register020" /></h3>
    			<ul>
    				<li><span>-&nbsp;<s:message code="mobile.view.register021" /></span></li>
    			</ul>
    		</div>
    	</div> 
    </div>
    <input type="hidden" id="hPid" value="${pId}"/>
</div>

<script type="text/javascript">
//다국어 사용
var i18n = function(){
	var message = {
		"common006" : "<s:message code='mobile.message.common006'/>",
		"register003" : "<s:message code='mobile.message.register003'/>",
		"register007" : "<s:message code='mobile.message.register007'/>"
	};
	this.getMessage = function( code ){
		return message[code];
	};
};
$(document).on("pagebeforeshow", function(e, ui) {
	var register = new mcare_mobile_register();
	register.init();
});
</script>

