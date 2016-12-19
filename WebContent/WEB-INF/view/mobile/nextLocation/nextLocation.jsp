<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/mobile/nextLocation/nextLocation.css"/>" />
<script type="text/javascript" src="<c:url value='/resources/js/mobile/nextLocation/nextLocation.js' />"></script>
<!-- 가셔야 할 곳 -->
<div data-role="content">
	<div class="mainContainer">
		<div class="resultWrapper">
			<div class="noResult" style="display:none;">
				<div class="msgBox">
					<div class="item">
						<h3>
							<s:message code='mobile.view.nextLocation001'/>
						</h3>
					</div>
				</div>
			</div>
			<div class="resultData" data-role="collapsibleset" data-iconpos="right" id="resultSet">
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
//다국어 사용
var i18n = function(){
	var message = {
			"common005":"<s:message code='mobile.message.common005'/> ",
			"common006" : "<s:message code='mobile.message.common006'/>",
			"nextLocation001" : "<s:message code='mobile.view.nextLocation001'/>",
			"nextLocation002" : "<s:message code='mobile.view.nextLocation002'/>"
	};
	
	this.getMessage = function( code ){
		return message[code];
	};
};
$(document).on("pagebeforeshow",function(e){
	var nextLocation = new mcare_mobile_nextLocation();
	nextLocation.init();
});
$(document).on("pageshow",function(e){
	$.mobile.resetActivePageHeight(); 
});
</script>
