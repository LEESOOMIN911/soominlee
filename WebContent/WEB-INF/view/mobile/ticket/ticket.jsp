<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/mobile/ticket/ticket.css"/>" />
<script type="text/javascript" src="<c:url value='/resources/js/mobile/ticket/ticket.js' />"></script>
<!-- 번호표발급 - 발급기 목록 -->
<div data-role="content">
	<div class="mainContainer">
		<div class="fir_con">
			<div class="form_div">
				<div class="type" data-role="navbar">
					<ul>
						<li>
							<a href="#" class="r_type ui-btn ui-btn-active" data-type="0">
								<s:message code="mobile.view.ticket001" />
							</a>
						</li>				
						<li>
							<a href="#" class="r_type ui-btn" data-type="1">
								<s:message code="mobile.view.ticket002" />
							</a>
						</li>
					</ul>
				</div>
			</div>
		</div>
		<div class="sec_con">
			<div class="ticketContainer">
				<div class="btnContainer">
					<a class="ui-btn ui-btn-b refreshTicketBtn">
						<s:message code = "mobile.source.ticket005"/>
					</a>
				</div>
				<div class="myTicketContainer"></div>
			</div>
			<div class="machineContainer" style="display:none;">
				<ul class="machine_list">
				
				</ul>
			</div>
			<div class="issueContainer" style="display:none;">
				<div class="machine_location">
					<h3><i class="fa fa-hospital-o"></i>&nbsp; <span class="loc"></span></h3>
				</div>
				<div>
					<ul class="issue_btn_wrapper">
						<!--
						<li class="issue_btn_li">
							<div>
								<a href="#" class="issue_btn" data-issue-type="0">
									<span><s:message code="mobile.view.ticket008" /></span>
									<br/>
									<span><s:message code="mobile.source.ticket003" /></span>
									&nbsp;
									<span class="wait_issue"></span>
									&nbsp;
									<span><s:message code="mobile.source.ticket006" /></span>
								</a>
							</div>
						</li>
						<li class="issue_btn_li">
							<div>
								<a href="#" class="issue_btn" data-issue-type="1">
									<span><s:message code="mobile.view.ticket009" /></span>
									<br/>
									<span><s:message code="mobile.source.ticket003" /></span>
									&nbsp;
									<span class="wait_issue"></span>
									&nbsp;
									<span><s:message code="mobile.source.ticket006" /></span>
								</a>
							</div>
						</li>
						<li class="issue_btn_li">
							<div>
								<a href="#" class="issue_btn" data-issue-type="2">
									<span><s:message code="mobile.view.ticket010" /></span>
									<br/>
									<span><s:message code="mobile.source.ticket003" /></span>
									&nbsp;
									<span class="wait_issue"></span>
									&nbsp;
									<span><s:message code="mobile.source.ticket006" /></span>
								</a>
							</div>
						</li>
						<li class="issue_btn_li">
							<div>
								<a href="#" class="issue_btn" data-issue-type="3">
									<span><s:message code="mobile.view.ticket011" /></span>
									<br/>
									<span><s:message code="mobile.source.ticket003" /></span>
									&nbsp;
									<span class="wait_issue"></span>
									&nbsp;
									<span><s:message code="mobile.source.ticket006" /></span>
								</a>
							</div>
						</li>
						<li class="issue_btn_li">
							<div>
								<a href="#" class="issue_btn" data-issue-type="4">
									<span><s:message code="mobile.view.ticket012" /></span>
									<br/>
									<span><s:message code="mobile.source.ticket003" /></span>
									&nbsp;
									<span class="wait_issue"></span>
									&nbsp;
									<span><s:message code="mobile.source.ticket006" /></span>
								</a>
							</div>
						</li>
						<li class="issue_btn_li">
							<div>
								<a href="#" class="issue_btn" data-issue-type="5">
									<span><s:message code="mobile.view.ticket013" /></span>
									<br/>
									<span><s:message code="mobile.source.ticket003" /></span>
									&nbsp;
									<span class="wait_issue"></span>
									&nbsp;
									<span><s:message code="mobile.source.ticket006" /></span>
								</a>
							</div>
						</li>
						-->
					</ul>
				</div>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
//다국어 사용
var i18n = function(){
	var message = {
		"common006" : "<s:message code='mobile.message.common006'/>",
		"ticket003" : "<s:message code='mobile.source.ticket003'/>",
		"ticket004" : "<s:message code='mobile.source.ticket004'/>",
		"ticket005" : "<s:message code='mobile.source.ticket005'/>",
		"ticket006" : "<s:message code='mobile.source.ticket006'/>",
		"ticket007" : "<s:message code='mobile.source.ticket007'/>",
		"ticket014" : "<s:message code='mobile.message.ticket014'/>",
		"ticket015" : "<s:message code='mobile.message.ticket015'/>"
		
	};
	
	this.getMessage = function( code ){
		return message[code];
	};
};
$(document).on("pagebeforeshow",function(e){
	var ticket = new mcare_mobile_ticket();
	ticket.init();
});
$(document).on("pageshow",function(e){
	$.mobile.resetActivePageHeight(); 
});
</script>
