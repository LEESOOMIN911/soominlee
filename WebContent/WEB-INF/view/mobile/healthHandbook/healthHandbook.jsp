<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/mobile/healthHandbook/healthHandbook.css"/>" />
<script type="text/javascript" src="<c:url value='/resources/js/mobile/healthHandbook/healthHandbook.js' />"></script>
<script type="text/javascript" src="<c:url value='/resources/plugins/chartjs/Chart.js' />"></script>
<!-- 건강수첩 -->
<div data-role="content">
	<div class="mainContainer">
		<!-- 정보 추가 버튼 -->
		<div class="fir_con">
			<div class="ui-grid-a">
				<div class="ui-block-a">
					<a href="#" class="insert ui-btn ui-btn-b">
						<s:message code="mobile.view.healthHandbook001"/>
					</a>
				</div>
				<div class="ui-block-b">
					<a href="#" class="bring ui-btn ui-btn-b ">
						<s:message code="mobile.view.healthHandbook019"/>
					</a>
				</div>
			</div>
		</div>
		<!-- 날짜 입력부분 -->
		<div class="sec_con">
			<div class="form_div">
				<div class="form_label">
					<span><s:message code="mobile.view.healthHandbook002"/></span>
				</div>
				<div class="form_input">				
					<input type="date" data-role="date" name="sDateVal" id="sDateVal" /><a href="#" class="dateBtn" id="sDate"><i class="fa fa-calendar"></i></a>
				</div>
			</div>
			<div class="form_div">
				<div class="form_label">
					<span><s:message code="mobile.view.healthHandbook003"/></span>
				</div>
				<div class="form_input">
		        	<input type="date" data-role="date"  name="eDateVal" id="eDateVal" /><a href="#" class="dateBtn" id="eDate"><i class="fa fa-calendar"></i></a>
				</div>
			</div> 
		</div>
		<!-- 타입 선택 버튼 부분 -->
		<div class="thi_con">
			<div data-role="navbar" class="retrieve_type mt">
				<ul>
					<li><a href="#" data-type="BP" class="ui-btn ui-btn-active"><s:message code="mobile.view.healthHandbook004"/></a></li>
					<li><a href="#" data-type="BS" class="ui-btn"><s:message code="mobile.view.healthHandbook005"/></a></li>
					<li><a href="#" data-type="BM" class="ui-btn"><s:message code="mobile.view.healthHandbook006"/></a></li>
				</ul>
			</div>
		</div>
		<!-- 차트 부분 -->
		<div class="fou_con">
			<canvas id="handbookChart" style="width:100%; height: 100px; margin-top: 10px;"></canvas>
			<div id="chart_legend" class="chart_legend"></div>
		</div>
		<!-- 데이터 표시 부분 -->
		<div class="fif_con">
			<div id="healthHandbook_retrieve" class="retreive_table">
			</div>
		</div>		
		<!-- 더보기 페이징 구현하면 기능 접목해야함  -->
		<div class="six_con hide" style="display:none;">
			<a href="#" class="moreBtn ui-btn ui-btn-b"><s:message code="mobile.view.common004"/></a>
		</div>
	</div>
</div>
<!-- 실험용 시작 -->
<input type="hidden" id="pId" value="${sessionScope.MCARE_USER_ID }"/> 
<input type="hidden" id="cipherKey" value="${sessionScope.MCARE_USER_CIPHER_KEY }"/> 
<!-- 실험용 끝 -->
<script type="text/javascript">
//다국어 사용
var i18n = function(){
	var message = {
		"common005":"<s:message code='mobile.message.common005'/> ",
		"common006" : "<s:message code='mobile.message.common006'/>",
		"common007":"<s:message code='mobile.message.common007'/> ",
		"common008":"<s:message code='mobile.message.common008'/> ",
		"common009":"<s:message code='mobile.message.common009'/> ",
		"healthHandbook007" : "<s:message code='mobile.source.healthHandbook007'/>",
		"healthHandbook008" : "<s:message code='mobile.source.healthHandbook008'/>",
		"healthHandbook009" : "<s:message code='mobile.source.healthHandbook009'/>",
		"healthHandbook010" : "<s:message code='mobile.source.healthHandbook010'/>",
		"healthHandbook011" : "<s:message code='mobile.source.healthHandbook011'/>",
		"healthHandbook012" : "<s:message code='mobile.source.healthHandbook012'/>",
		"healthHandbook013" : "<s:message code='mobile.source.healthHandbook013'/>",
		"healthHandbook014" : "<s:message code='mobile.source.healthHandbook014'/>",
		"healthHandbook015" : "<s:message code='mobile.source.healthHandbook015'/>",
		"healthHandbook016" : "<s:message code='mobile.message.healthHandbook016'/>",
		"healthHandbook017" : "<s:message code='mobile.message.healthHandbook017'/>",
		"healthHandbook018" : "<s:message code='mobile.message.healthHandbook018'/>",
		"healthHandbook020" : "<s:message code='mobile.message.healthHandbook020'/>"
	};
	this.getMessage = function( code ){
		return message[code];
	};
};
$(document).on("pagebeforeshow", function(e, ui) {
	var healthHandbook = new mcare_mobile_healthHandbook();
	healthHandbook.setGlobal( healthHandbook );
	healthHandbook.init();
});
$(document).on("pageshow", function(e, ui) {
	$(".retrieve_type a[data-type=BP]").trigger("click");
	$.mobile.resetActivePageHeight(); 
});
</script>

