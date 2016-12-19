<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/mobile/healthHandbook/healthHandbookInsert.css"/>" />
<script type="text/javascript" src="<c:url value='/resources/js/mobile/healthHandbook/healthHandbookInsert.js' />"></script>
<!-- 건강수첩 -->
<div data-role="content">
	<div class="mainContainer">
		<div class="fir_con">
			<div class="formTable" id="saveForm">         
	            <div id="" class="row">
					<div class="item">                
	                <div class="itemtitle"><s:message code='mobile.view.healthHandbookInsert001'/></div>                
	                <div class="itemvalue">
	                	<div class="formInputDiv">
		                    <input name="regDt" id="regDt" type="date"/><a href="#" class="dateBtn" id="regDate"><i class="fa fa-calendar"></i></a>
	                	</div>
	                </div>            
	            </div><!-- item end 측정일자-->

	             <div class="item">                
	                <div class="itemtitle"><s:message code='mobile.view.healthHandbookInsert002'/></div>                
	                  <div class="itemvalue">
	                	<div class="formInputDiv">
		                    <input name="regTime" id="regTime" type="time"/>
	                	</div>
	                </div>            
	            </div><!-- item end 측정시간-->
	           </div>
	            <div id="" class="row">
					<div class="item">                
	                <div class="itemtitle max"><s:message code='mobile.view.healthHandbookInsert006'/> </div>                
	                  <div class="itemvalue">
	                	<div class="formInputDiv">
		                    <div class="formDiv">
		                    	<label for="pressMinValue" class="form_inline"></label>
		                        <input type="number" pattern="\d*" id="pressMinValue"  name="pressMinValue" placeholder="<s:message code='mobile.view.healthHandbookInsert008'/>"> <span>mmHg</span>
		                    </div>                                
		                    <div class="formDiv">
		                    	<label for="pressMaxValue" class="form_inline"></label>
		                        <input type="number" pattern="\d*" id="pressMaxValue" name="pressMaxValue" placeholder="<s:message code='mobile.view.healthHandbookInsert007'/>"> <span>mmHg</span>
		                    </div>                    
	                	</div>
	                </div>          
	            </div> <!-- item end 혈압--> 
	            </div> 
				
	            <div id="" class="row">
					<div class="item">                
	                <div class="itemtitle max"><s:message code='mobile.view.healthHandbookInsert003'/></div>             
	                  <div class="itemvalue">
	                	<div class="formInputDiv">
		                    <div class="formDiv">
		                    	<label for="fastingSugarValue" class="form_inline"></label>
		                        <input type="number"  pattern="\d*" id="fastingSugarValue" name="fastingSugarValue" placeholder="<s:message code='mobile.view.healthHandbookInsert004'/>"> <span>mg/dl</span>
		                    </div>      
		               		<div class="formDiv">
		                    	<label for="postSugarValue" class="form_inline"></label>
		                        <input type="number" pattern="\d*"  id="postSugarValue" name="postSugarValue" placeholder="<s:message code='mobile.view.healthHandbookInsert005'/>"> <span>mg/dl</span>
		                    </div>                     
	                	</div>
	                </div>         
	            </div> <!-- item end 혈당-->
	            </div>
				
	            <div id="" class="row">
					<div class="item">                
	                <div class="itemtitle"><s:message code='mobile.view.healthHandbookInsert009'/> </div>             
	                  <div class="itemvalue">
	                	<div class="formInputDiv">
		                    <div class="formDiv">
		                        <input type="number" pattern="\d*" id="heightValue" name="heightValue"> <span>cm</span>
		                    </div>                    
	                	</div>
	                </div>            
	            </div><!-- item end 키-->           
	            <div class="item">                
	                <div class="itemtitle"><s:message code='mobile.view.healthHandbookInsert010'/></div>             
	                  <div class="itemvalue">
	                	<div class="formInputDiv">
		                    <div class="formDiv">
		                        <input type="number" pattern="\d*" id="weightValue" name="weightValue"> <span>kg</span>
		                    </div>                 
	                	</div>
	                </div>            
	            </div>  <!-- item end 체중-->         
	            <div class="item">                
	                <div class="itemtitle">BMI</div>              
	                <div class="itemvalue">
	                	<div class="formInputDiv">
		                   	<div class="formDiv for_bmi">
		                   		<input type="text" id="bmiValue" disabled="disabled"/>
		                   	</div>
	                	</div>
	                </div>          
	            </div> <!-- item end bmi-->
	            </div>

	            <div id="" class="row">
					<div class="item">
	            		<div>
	            			<input type="range" name="resultBmi" id="resultBmi" data-highlight="true" class="" min="0" max="50" disabled="disabled"/>
	            		</div>
	            </div>    
	            </div>  
	        </div><!-- formTable end -->
		</div>
        <div class="sec_con">
	        <div class="ui-grid-a">
	            <div class="ui-block-a">
	                <a href="#" id="save" class="ui-btn ui-corner-all ui-btn-b" ><s:message code='mobile.view.common001'/></a>
	            </div>
	            <div class="ui-block-b">
	                <a href="#" id="remove" class="ui-btn ui-corner-all ui-btn-b" ><s:message code='mobile.view.healthHandbookInsert027'/></a>
	            </div>
	        </div>
        </div>        
	</div>
</div>
<!-- 실험용 시작 -->
<input type="hidden" id="pId" value="${sessionScope.MCARE_USER_ID }"/> 
<input type="hidden" id="cipherKey" value="${sessionScope.MCARE_USER_CIPHER_KEY }"/> 
<!-- 실험용 끝 -->
<script type="text/javascript">
var i18n = function(){
	var message = {
		"common006" : "<s:message code='mobile.message.common006'/>",
		"healthHandbookInsert011" : "<s:message code='mobile.source.healthHandbookInsert011'/>",
		"healthHandbookInsert012" : "<s:message code='mobile.source.healthHandbookInsert012'/>",
		"healthHandbookInsert013" : "<s:message code='mobile.source.healthHandbookInsert013'/>",
		"healthHandbookInsert014" : "<s:message code='mobile.source.healthHandbookInsert014'/>",
		"healthHandbookInsert015" : "<s:message code='mobile.source.healthHandbookInsert015'/>",
		"healthHandbookInsert016" : "<s:message code='mobile.message.healthHandbookInsert016'/>",
		"healthHandbookInsert017" : "<s:message code='mobile.message.healthHandbookInsert017'/>",
		"healthHandbookInsert018" : "<s:message code='mobile.message.healthHandbookInsert018'/>",
		"healthHandbookInsert019" : "<s:message code='mobile.message.healthHandbookInsert019'/>",
		"healthHandbookInsert020" : "<s:message code='mobile.message.healthHandbookInsert020'/>",
		"healthHandbookInsert021" : "<s:message code='mobile.message.healthHandbookInsert021'/>",
		"healthHandbookInsert022" : "<s:message code='mobile.message.healthHandbookInsert022'/>",
		"healthHandbookInsert023" : "<s:message code='mobile.message.healthHandbookInsert023'/>",
		"healthHandbookInsert024" : "<s:message code='mobile.message.healthHandbookInsert024'/>",
		"healthHandbookInsert025" : "<s:message code='mobile.message.healthHandbookInsert025'/>",
		"healthHandbookInsert026" : "<s:message code='mobile.message.healthHandbookInsert026'/>",
		"healthHandbookInsert028" : "<s:message code='mobile.message.healthHandbookInsert028'/>"
	};
	
	this.getMessage = function( code ){
		return message[code];
	};
};

$(document).on("pagebeforeshow", function(e, ui) {
	var healthHandbookInsert = new mcare_mobile_healthHandbookInsert();
	healthHandbookInsert.setGlobal( healthHandbookInsert );
	healthHandbookInsert.init();
});
$(document).on("pageshow",function(e){
	$.mobile.resetActivePageHeight(); 
});
</script>

