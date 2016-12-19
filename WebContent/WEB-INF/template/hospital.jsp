<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Map"%>
<%@page import="com.dbs.mcare.MCareConstants"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<!doctype html>
<html>
<head>
<meta name="format-detection" content="telephone=no"/>
<title>부산대학교병원 M-Care</title>
<tiles:insertAttribute name="include" />
<style type="text/css">
	ul#hospitalListview{list-style:none;}
	ul#hospitalListview li.linkImg.active{background:#e3e3e3 !important;}
	.linkImg{padding:2em;margin-bottom:1em;background: #fff;border: 1px solid #ccc;border-radius:4px;}
	.linkImg img{width: 100%;}
</style>
<script type="text/javascript">
	var strCode = "";
	$(function(){
		$("li.linkImg a").on("click",function(e){
			if( $(this).hasClass("main") ){				
				$(this).parent().addClass("active");
				setDomain('<%=MCareConstants.HOSPITAL.PNUH_PUSAN.getDomain()+MCareConstants.HOSPITAL.PNUH_PUSAN.getContextName()%>','<%=MCareConstants.HOSPITAL.PNUH_PUSAN.getCode()%>');		
			} 

		});
	});
	/**
	 * 앱 종료
	 */
	function exitApp() { 
		$(".sidemenu").parent().removeClass("active");
		var mcare = new mcare_mobile(); 
		// 사이드바 panel 열려있으면 닫기
		if( $(":jqmData(role=panel)").hasClass("ui-panel-open") ){
			$(":jqmData(role=panel)").panel("close");
		}
		mcare.popup(
				{
				"content": mcare.getI18n("common006"),
				"callback":function(){
					var jsonMsg = {
							type : "command", 
							functionType : "exit"
						}; 
					mcare.toNative(jsonMsg); 
				}
		});
		
	};
	/**
	 * 이전 버튼 이벤트
	 */
	function triggerBackBtn(){
		var mcare = new mcare_mobile(); 
		if( mcare.isAndroid() ){
			//헤더 백버튼 숨어 있으면 리턴
			if( $("#headerArrowLeft_btn").css("display") === "none" ){
				return;
			}
			// 사이드바 panel 열려있으면 닫기
			if( $(":jqmData(role=panel)").hasClass("ui-panel-open") ){
				$(":jqmData(role=panel)").panel("close");
				return;
			}
			//dialog 열려있으면 닫기
			if($(":jqmData(role=dialog)").hasClass("ui-dialog-open")){
				$(":jqmData(role=dialog)").dialog("close");
				return;
			}
			//dialog 열려있으면 닫기
			if( $(":jqmData(role=popup)").parent().hasClass("ui-popup-active") ){
				$(":jqmData(role=popup)").popup("close");
				return;
			}
			//index 면 종료
			if( (window.location.href).indexOf("hospital.page") > 0 ){
				exitApp();
				return;
			}
			//회원 탈퇴가 성공하면 로그 아웃페이지로 이동하게 한다.
			if( $(":jqmData(role=popup)").find("input#withdrawal").length > 0 ){
				window.location.href = contextPath + "/logout.page";
				return;
			}
			$("a#headerArrowLeft_btn")[0].click();
			
			
		}
	};
	/**
	* domain 저장
	*/
	function setDomain( strUrl, code ){
		strCode = code;
		var obj = {
				"type" : "command", 
			    "functionType" : "setRecentDomain",
			    "value" : {
			        "url" : strUrl,
			        "callbackFn": "window.redirectPage"
			    }	
		};
		try{		
			var mcare = new mcare_mobile();
			mcare.toNative( obj );
		} catch(e) {
			console.log(e);
		}
	};
	/**
	* callback
	**/
	window.redirectPage = function(data){
		if( typeof data === "string" ){			
			data = JSON.parse( data );
		}
		
		if( data["success"] ){
			if( strCode.indexOf('<%=MCareConstants.HOSPITAL.PNUH_PUSAN.getCode()%>') >= 0 ){
				window.location.href = '<%=MCareConstants.HOSPITAL.PNUH_PUSAN.getDomain() + MCareConstants.HOSPITAL.PNUH_PUSAN.getContextName() + MCareConstants.HOSPITAL.PNUH_PUSAN.getStartPage()%>';
			} 
		} else {
			mcare.alert( data["result"] );
		}
	};
	//다국어
	var i18n = function(){
		var message = {
			"common006" : "<s:message code='mobile.message.common006'/>"
		};
		this.getMessage = function( code ){
			return message[code];
		};
	};
</script>
</head>
<body>
	<div data-role="page" class="ui-noboxshadow hyumc">
	    <div data-role="header" data-position="fixed" data-tap-toggle="false">
			<h2><s:message code="mobile.view.hospital001"/></h2>
		</div>
	    <div data-role="content">
			<ul id="hospitalListview">
				<li class="linkImg">
					<a href="#" class="main">
						<img src="<c:url value="/resources/css/images/mobile/main/toplogo.png"/>"alt="<s:message code="mobile.view.hospital002"/>"/>
					</a>
				</li>
				<!--  
				<li class="linkImg">
					<a href="#" class="sub">
						<img src="<c:url value="/resources/css/images/mobile/main/toplogoSub.png"/>" alt="<s:message code="mobile.view.hospital002"/>"/>
					</a>
				</li>
				-->
			</ul>
		</div>
	    <!-- 컨펌 -->
		<div data-role="popup" id="popupDialog" data-dismissible="false" class="ui-corner-all">
			<div role="content" >
				<div class="popuptitle"><s:message code="mobile.view.common023" /></div>
				<div class="popupcontent">

				</div>
				<div class="ui-grid-a">
					<div class="ui-block-a">
						<a href="#"  class="popupCallback ui-btn ui-btn-b"><s:message code="mobile.view.common022" /></a>
					</div>
					<div class="ui-block-b">
						<a href="#" data-rel="back" class="ui-btn ui-btn-b ui-shadow"><s:message code="mobile.view.common021" /></a>
					</div>
				</div>				
			</div>
		</div>
		<!-- alert -->
		<div data-role="popup" id="alertDialog" data-dismissible="false" class="ui-corner-all">
			<div role="content" >
				<div class="popuptitle"><s:message code="mobile.view.common023" /></div>
				<div class="popupcontent">

				</div>
				<div class="buttonwrapper">
					<a href="#" class="ui-btn ui-btn-b ui-shadow alertCallback"><s:message code="mobile.view.common022" /></a>
				</div>				
			</div>
		</div>
	</div>
</body>
</html>
