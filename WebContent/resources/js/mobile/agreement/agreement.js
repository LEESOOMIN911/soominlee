/**
 * mcare_mobile_agreement
 */
var mcare_mobile_agreement = function(){
	//상속
	mcare_mobile.call(this);
	//super
	var self = this;
	
	//변수
	var $title = $("span.title"),
		$contents = $("p.contents"),
		$question = $("p.question"),
		$chartNoValue = $("#chartNoValue"),
		$radio = $("input[name='agreement']"),
		$mainContainer = $(".mainContainer"),
		$saveUserAgreement = $("#saveUserAgreement"),
		$crudServiceBaseUrl = contextPath + "/mobile/user";
	
	/**
	 * 초기화
	 */
	this.init = function(){
		initAgreement();
		addEvent();
	};
	/**
	 * 이벤트 등록
	 */
	var addEvent = function(){
		//사용하지는 않음
		$saveUserAgreement.on( "click", function(e){
			//버튼 클릭 배경
			$(this).addClass("active");
			saveUserAgreement(e);
		});
	};
	
	var initAgreement = function(){
		//TODO: id를 url에 붙힐건지 데이터 처리 방법 생각 필요.
		var id = "";
		getAgreement( id );
	};
	/**
	 * 동의서 데이터 가져오기
	 * @param id
	 */
	var getAgreement = function( id ){
		self.loading("show");
		$.ajax({
			url: $crudServiceBaseUrl + "/getUserAgreementList.json",
			type: "GET",
			dataType : "json",
			success : function(data){
				try{
					if( data.msg !== undefined ){
						self.alert( data.msg );
						return;
					} else {
						displayData( data.result );
					}
					
				} catch(e) {
					
				}
			},
			error : function(xhr){
				console.log( xhr );
			},
			complete : function(){
				self.loading("hide");
			}
		});
	};
	/**
	 * 화면에 표시
	 */
	var displayData = function(data){
		$mainContainer.html("");
		
		if( data === undefined || data === null || data.length === 0 || $.isEmptyObject(data) ){
			var div = $( "<div></div>" ).addClass("msgBox"),
				div2 = $( "<div></div>" ).addClass("item"),
				h2 = $( "<h3></h3>" );
			div.html( div2.html( h2.html( self.getI18n("agreement010") ) ) );
			$mainContainer.append( div );
		} else {
			
			var div = $( "<div></div>" ),
				span = $( "<span></span>" ),
				h3 = $( "<h3></h3>" ),
				p = $( "<p></p>" ),
				input = $( "<input></input>" ).attr( {"type" : "checkbox" } ),
				label = $( "<label></label>" );
			var under14 = {};
			
			for( var i = 0; i < data.length; i++ ){
				var item = data[i],
					agree = div.clone().addClass( "agreement" )
					.attr( {"id" : item["agreementId"].replace(/(\s*)/g,""),"data-seq":item["agreementSeq"], "data-version" : item["versionNumber"], "data-required" : item["requiredYn"]} );
				 if( item["typeName"] === "UNDER14" ){
					 under14 = item;
					 continue;
				 }
				 
				var collapse = div.clone().addClass("agreement_collapse gray");
				//타이틀 동의서 명 + 버전 + 필수여부
				var title = h3.clone().addClass( "title" ).html( item["agreementName"]+"&nbsp;"+(item["requiredYn"]=="Y"?"["+self.getI18n("agreement008")+"]":"") ),
				contents = div.clone().html( p.clone().addClass( "contents" ).html( item["agreementCl"] ) );
				var agreeInput = input.clone().attr( 
						{
							"value":"0",
							"id":item["agreementId"].replace(/(\s*)/g,"")+"_"+item["versionNumber"]+"_agree",
							"name":item["agreementId"].replace(/(\s*)/g,"")+"_"+item["versionNumber"]+"_agreement"
						} ),
						agreeLabel = label.clone().attr("for",item["agreementId"].replace(/(\s*)/g,"")+"_"+item["versionNumber"]+"_agree").text( self.getI18n("agreement001") );
				
				if( item["agreementYn"] === "Y" ){
					agreeInput.prop("checked",true);
				}
				
				var checkbox = div.clone().html(agreeInput).append(agreeLabel);
				
				collapse.html( title ).append( contents );
				agree.html( collapse ).append( checkbox );
				
				$mainContainer.append( agree );
			}
			if( !$.isEmptyObject(under14) ){	
				//14세 이하 동의
				var under14Div = $("<div></div>").addClass("agreement").attr( {"id" : under14["agreementId"].replace(/(\s*)/g,""),"data-seq":under14["agreementSeq"], "data-version" : under14["versionNumber"], "data-required" : under14["requiredYn"]} ),
					under14Label = $("<label></label>").attr("for",under14["agreementId"].replace(/(\s*)/g,"")+"_"+under14["versionNumber"]+"_agree").text( self.getI18n("agreement001") ),
					under14Input = $("<input type='checkbox'></input>").attr( 
							 {
								 "value":"0",
								 "id":under14["agreementId"].replace(/(\s*)/g,"")+"_"+under14["versionNumber"]+"_agree",
								 "name":under14["agreementId"].replace(/(\s*)/g,"")+"_"+under14["versionNumber"]+"_agreement"
							 } ),
					under14Checkbox = $("<div></div>").html(under14Input).append(under14Label),
					under14Content = $("<div></div>").html(under14["agreementCl"]),
					under14title = $("<div></div>").addClass("title").html(under14["agreementName"]+"&nbsp;"+(under14["requiredYn"]=="Y"?"["+self.getI18n("agreement008")+"]":"") ).css("display","none");
				if( under14["agreementYn"] === "Y" ){
					under14Input.prop("checked",true);
				}
				under14Div.html( under14Content ).append(under14Checkbox).append(under14title);
				$mainContainer.append(under14Div);	
			}
			$("div.agreement_collapse").collapsible({
				collapsedIcon : "carat-d",
				expandedIcon : "carat-u",
				iconpos : "right",
				collapseCueText:"",
				expandCueText:""
			});
			$("input[name$=agreement]").checkboxradio();
		}
	};
	
	/**
	 * 동의서 동의 여부 저장
	 */
	var saveUserAgreement = function(e) {
		
		var userData  = validateAgreement();
		
		if( userData === null || userData === undefined ){
			return;
		}		
		
		self.loading("show");
		$.ajax({
			url : contextPath + "/mobile/user/saveUserAgreement.json",
			type : "POST",
			data : self.util.stringifyJson(userData), 
			dataType : "json",
			contentType : "application/json",
			success : function(data){
				try{
					if( data.msg !== undefined ){
						self.alert( data.msg );
						return;
					} else {
						showMain();
					}
					
				} catch(e) {
					
				}
			},
			error : function(xhr,d,t){
				self.alert("에러");
				console.log( xhr );
			},
			complete: function(){
				self.loading("hide");
				//버튼 클릭 배경 제거
				$saveUserAgreement.removeClass("active");
			}
		});
	};
	/**
	 * 동의서 유효성 검사 및 전송 데이터 구성
	 */
	var validateAgreement = function(){
		var userData  = {
				agreementList : []
			},
			agreements = $(".agreement"),
			agreementArray = new Array();
		
		for( var i = 0; i < agreements.length; i++ ){
			 var item = $( agreements[i] ),
			 	 id = item.attr("id"),
			 	 required = item.attr("data-required"),
			 	 version = item.attr("data-version"),
			 	 seq = item.attr("data-seq");
			 
			 var input = $( "#"+id+"[data-version="+version+"] input:checked" ).val();
			 //필수 동의면
			 if( required === "Y" ){			 
				 if( input === "0" ){

					 var obj = {
							 agreementSeq : seq,
							 agreementId : id,
							 versionNumber : version,
							 requiredYn : required,
							 agreementYn : input==="0"?"Y":"N"
					 };
					 
					 agreementArray.push( obj );
				 //필수니까 동의하세요
				 } else {
					 var title = $( "#"+id+"[data-version="+version+"] .title" ).text().split("[");
					 self.alert( title[0] + self.getI18n("agreement006") );
					//버튼 클릭 배경 제거
					$saveUserAgreement.removeClass("active");
					 return;
				 }
			// 필수 동의 아니면
			 } else {
				 //선택한 값을 보내주세요.
				 var obj = {
						 agreementSeq : seq,
						 agreementId : id,
						 versionNumber : version,
						 requiredYn : required,
						 agreementYn : input==="0"?"Y":"N"
				 };
				 agreementArray.push( obj );
			 }
		}
		userData.agreementList = agreementArray;
		
		return userData;
	};
	
	/**
	 * 메인 화면(login) 요청
	 */
	var showMain = function (){
		self.alert( self.getI18n("agreement009"),function(e){			
			self.changePage( contextPath + "/index.page");
		});
	}
	
};