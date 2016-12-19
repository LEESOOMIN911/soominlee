<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<!doctype html>
<html>
<head>
<title><tiles:getAsString name="title" /></title>
<tiles:insertAttribute name="include" />
</head>
<body>
    <tiles:insertAttribute name="content" />
</body>
</html>
