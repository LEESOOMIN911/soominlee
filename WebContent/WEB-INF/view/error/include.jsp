<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
response.setHeader("Cache-Control","no-store");
response.setHeader("Pragma","no-cache");
response.setDateHeader("Expires",0);
if (request.getProtocol().equals("HTTP/1.1")){	
        response.setHeader("Cache-Control", "no-cache");
}
%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0">
<meta http-equiv="Expires" content="0" />
<meta http-equiv="Cache-Control" content="no-cache" />
<meta http-equiv="Pragma" content="no-cache" />

<link rel="stylesheet" type="text/css" href="<c:url value="/resources/plugins/jquery.mobile-1.4.5/jquery.mobile-1.4.5.min.css" />" />
<link rel="alternate stylesheet" type="text/css" href="<c:url value="/resources/plugins/jquery.mobile-1.4.5/jquery.mobile-1.4.5.min.css"/>" title="style1" media="screen"/>

<script type="text/javascript" src="<c:url value="/resources/plugins/jquery/jquery-2.1.4.min.js" />"></script>
<script type="text/javascript">
var contextPath = '${pageContext.request.contextPath}';
$(document).on('mobileinit', function() {
	$.mobile.ajaxEnabled = false;
	$.mobile.defaultPageTransition = 'none';
	$.mobile.page.prototype.options.domCache = false;
	$.mobile.selectmenu.prototype.options.nativeMenu = true;
	$.fn.buttonMarkup.defaults.corners = false;
});
$(document).on('pageloadfailed', function(e, data) {
	e.preventDefault();
    var page = $(data.xhr.responseText);
    $.mobile.pageContainer.empty();
    $.mobile.pageContainer.append(page);
    $.mobile.pageContainer.trigger('create');
});
$(document).on('pageshow', function(e, ui) {
    ui.prevPage.remove();
});
//pc 접근 제어
// $(document).ready(function(){
// 	if (/win32|win64|mac/i.test(navigator.platform.toLowerCase())) { 
// 		window.location.href = "http://www.pnuh.or.kr/"; 
// 	} 
// });
</script>
<script type="text/javascript" src="<c:url value="/resources/plugins/jquery.mobile-1.4.5/jquery.mobile-1.4.5.min.js"/>"></script>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/font-awesome.css" />" />
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/mobile/mcare.mobile.css" /> "/>
<script type="text/javascript" src="<c:url value="/resources/plugins/jquery-number-2.1.6/jquery.number.js" /> "></script>
<script type="text/javascript" src="<c:url value="/resources/js/core/mcare.core.js" /> "></script>
<script type="text/javascript" src="<c:url value="/resources/js/core/mcare.common.js" /> "></script>
