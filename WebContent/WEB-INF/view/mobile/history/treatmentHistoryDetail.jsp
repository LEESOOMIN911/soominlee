<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/mobile/history/treatmentHistoryDetail.css"/>" />
<script type="text/javascript" src="<c:url value='/resources/js/mobile/history/treatmentHistoryDetail.js' />"></script>
<!-- 이력조회 상세 -->
<div data-role="content">
	<div class="mainContainer">
		<div class="fir_con">
			<div class="resultContainer">
				<div class="for_gubun"></div>
				<ul class="resultData">
					<li>
						<span class="for_type">
							
						</span>
						<span class="for_datetime">
							
						</span>
					</li>
					<li>
						<span>
							<s:message code="mobile.view.treatmentHistoryDetail002" />
						</span>
						<span class="for_dept">
							
						</span>
					</li>
					<li>
						<span>
							<s:message code="mobile.view.treatmentHistoryDetail003" />
						</span>
						<span class="for_doctor">
							
						</span>
					</li>
				</ul>
			</div>
		</div>
		<div class="sec_con">
			<!-- 
			<div class="resultContainer">
				<div>&bull;&nbsp;<s:message code="mobile.view.treatmentHistoryDetail004" /></div>
				<ul class="resultData">
					<li>
						<span>
							<s:message code="mobile.view.treatmentHistoryDetail005" />
						</span>
						<span class="for_totalAmt amt">
							
						</span>
					</li>
					<li>
						<span>
							<s:message code="mobile.view.treatmentHistoryDetail006" />
						</span>
						<span class="for_insuranceAmt amt">
							
						</span>
					</li>
					<li>
						<span>
							<s:message code="mobile.view.treatmentHistoryDetail007" />
						</span>
						<span class="for_patientAmt amt">
							
						</span>
					</li>
					<li>
						<span>
							<s:message code="mobile.view.treatmentHistoryDetail008" />
						</span>
						<span class="for_receiptAmt amt">
							
						</span>
					</li>
					<li>
						<span>
							<s:message code="mobile.view.treatmentHistoryDetail009" />
						</span>
						<span class="for_outstandingAmt amt">
							
						</span>
					</li>
				</ul>
				<div>
					<a href="#" id="detailPayment" class="ui-btn ui-btn-b">
						<s:message code="mobile.view.treatmentHistoryDetail012"/>
					</a>
				</div>
			</div>
			-->
		</div>
		
	</div>
</div>
<input type="hidden" id="pId" value="${sessionScope.MCARE_USER_ID }"/>
<script type="text/javascript">
//다국어 사용
var i18n = function(){
	var message = {
		"common006" : "<s:message code='mobile.message.common006'/>",
		"treatmentHistoryDetail001" : "<s:message code='mobile.source.treatmentHistoryDetail001'/>",
		"treatmentHistoryDetail004" : "<s:message code='mobile.view.treatmentHistoryDetail004'/>",
		"treatmentHistoryDetail005" : "<s:message code='mobile.view.treatmentHistoryDetail005'/>",
		"treatmentHistoryDetail006" : "<s:message code='mobile.view.treatmentHistoryDetail006'/>",
		"treatmentHistoryDetail007" : "<s:message code='mobile.view.treatmentHistoryDetail007'/>",
		"treatmentHistoryDetail008" : "<s:message code='mobile.view.treatmentHistoryDetail008'/>",
		"treatmentHistoryDetail009" : "<s:message code='mobile.view.treatmentHistoryDetail009'/>",
		"treatmentHistoryDetail012" : "<s:message code='mobile.view.treatmentHistoryDetail012'/>",
		"treatmentHistoryDetail013" : "<s:message code='mobile.source.treatmentHistoryDetail013'/>",
		"treatmentHistoryDetail010" : "<s:message code='mobile.source.treatmentHistoryDetail010'/>",
		"treatmentHistoryDetail011" : "<s:message code='mobile.source.treatmentHistoryDetail011'/>"
	};
	
	this.getMessage = function( code ){
		return message[code];
	};
};
$(document).on("pagebeforecreate", function(e, ui) {
	var treatmentHistoryDetail = new mcare_mobile_treatmentHistoryDetail();
	treatmentHistoryDetail.setGlobal( treatmentHistoryDetail );
	treatmentHistoryDetail.init();
});
$(document).on("pageshow",function(e){
	$.mobile.resetActivePageHeight(); 
});
</script>

