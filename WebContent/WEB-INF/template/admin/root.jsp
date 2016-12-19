<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<!doctype html>
<html>
<head>
<meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<title><tiles:getAsString name="title" /></title>
<tiles:insertAttribute name="include" />
<style type="text/css">
#page {
	width: 1148px;
	min-height: 790px;
}
</style>
</head>
<body>
	 <div id="page">
    	<div id="header">
    		<tiles:insertAttribute name="header" />
    	</div>
    	<div id="center">
        	<tiles:insertAttribute name="content" />
        </div>
    </div>
</body>
<script type="text/javascript">
	$.ajaxPrefilter(function(options, original) {
        if (original.contentType == null && typeof original.data === 'string') {
            options.contentType = 'application/json; charset=UTF-8';
        }
    });
</script>
</html>