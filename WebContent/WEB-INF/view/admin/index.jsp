<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript" src="<c:url value='/resources/js/admin/index.js' />"></script>
<div>
</div>
<script type="text/javascript">
$(document).ready(function(){
	var index = new mcare_admin_index();
	index.init();
});
</script>