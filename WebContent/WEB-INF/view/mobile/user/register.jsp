<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/mobile/user/register.css"/>" />
<script type="text/javascript" src="<c:url value='/resources/js/mobile/user/register.js' />"></script>
<!-- 사용등록 -->
<div data-role="content">
	<div class="mainCont">
		<ul data-role="listview" data-inset="true" id="naviListview">
			<li class="naviLink">
				<a href="<c:url value="/mobile/user/registerWithoutPId.page?menuId=registerWithoutPId"/>" id="without">
					<span><s:message code="mobile.view.register001" /></span><br/>
					<span class="described"><s:message code="mobile.view.register002" /></span>
				</a>
			</li>
			<li class="naviLink">
				<a href="<c:url value="/mobile/user/registerWithPId.page?menuId=registerWithPId"/>" id="with">
					<span><s:message code="mobile.view.register003" /></span><br/>
					<span class="described"><s:message code="mobile.view.register004" /></span>
				</a>
			</li>
		</ul>
	</div>

    <div class="example">
    	
    	<h2><i class="fa fa-flag" ></i>&nbsp;<s:message code="mobile.view.register005" /></h2>
    	<div class="example_contents">
    		<div data-role="collapsible" data-expanded-icon="carat-u" data-collapsed-icon="carat-d" data-iconpos="right">
    			<h3><s:message code="mobile.view.register006" /></h3>
    			<ul>
    				<li><span>-&nbsp;<s:message code="mobile.view.register007" /></span></li>
    				<li><span>-&nbsp;<s:message code="mobile.view.register008" /></span></li>
    			</ul>
    		</div>
    		<div data-role="collapsible" data-expanded-icon="carat-u" data-collapsed-icon="carat-d" data-iconpos="right">
    			<h3><s:message code="mobile.view.register009" /></h3>
    			<ul>
    				<li><span>-&nbsp;<s:message code="mobile.view.register010" /></span></li>
    			</ul>
    		</div>
    		<div data-role="collapsible" data-expanded-icon="carat-u" data-collapsed-icon="carat-d" data-iconpos="right">
    			<h3><s:message code="mobile.view.register011" /></h3>
    			<ul>
    				<li><span>-&nbsp;<s:message code="mobile.view.register012" /></span></li>
    			</ul>
    		</div>
    		<div data-role="collapsible" data-expanded-icon="carat-u" data-collapsed-icon="carat-d" data-iconpos="right">
    			<h3><s:message code="mobile.view.register013" /></h3>
    			<ul>
    				<li><span>-&nbsp;<s:message code="mobile.view.register014" /></span></li>
    			</ul>
    		</div>
    	</div> 
    </div>
</div>

<script type="text/javascript">
//다국어 사용
var i18n = function(){
	var message = {
		"common006" : "<s:message code='mobile.message.common006'/>"
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

