/**
 * mcare_mobile_registerPWD
 * @author heesungkim
 */
var mcare_mobile_registerPWD = function(){
	//상속
	mcare_mobile.call(this);
	//super
	var self = this;
	//변수
	var $pass2 = $("#pass2"),
		$pass3 = $("#pass3"),
		$pId = $("#pId"),
		$pName = $("#pName"),
		$userBirth = $("#userBirthDate"),
		$userGender = $("#userGenderCode"),
//		$employee = $("#employee"),
//		$hEmployee = $("#hEmployee"),
//		$elCont = $(".elCont"),
//		$elList = $("ul.elList"),
//		$elScreen = $(".elScreen"),
		$btn1 = $("#btn1");
	
	var elText = "";
	
	/**
	 * 초기화
	 */	
	this.init = function(){
		addEvent();
	};
	/**
	 * 이벤트 등록
	 */
	var addEvent = function(){
		$btn1.on("click", function(e) {
			//버튼 클릭 배경
			$(this).addClass("active");
			
			if( validateData() ) {
				self.loading("show");
				
                var paramMap  = {
                    pId : $pId.val(), 
                    pName : $pName.val(), 
                    passWordValue : $pass2.val(), 
                    agentEmpId : $hEmployee.val()
                };
                
                self.ajaxMobile({
                    url : contextPath + "/mobile/user/registerCertifiedUser.json", 
                    contentType: "application/json; charset=UTF-8",
                    data : self.util.stringifyJson(paramMap), 
                    method : "POST", 
                    success : function(data) {
                    	try{                    		
                    		if( data.msg !== undefined ){
                    			self.alert( data.msg );
                    			return false;
                    		} else if( data.extraMsg !== undefined ){
                    			self.alert( data.extraMsg );
                    			if( data.result !== undefined ){
                    				showUserAgreement( data.result );
                    			}
                    			return;
                    		} else {                    		
                    			showUserAgreement(data.result);
                    		}
                    	} catch(e) {
                    		
                    	}
                    },
                    error : function(xhr) {
                    	console.log(xhr);
                    },
                    complete: function(){
                    	//버튼 클릭 배경 제거
                    	$btn1.removeClass("active");
                    	self.loading("hide");
                    }
                });
			}
			//버튼 클릭 배경 제거
			$(this).removeClass("active");
		});
		//추천직원 기능 주석 
//		$employee.on("focusout",function(e){
//			if( elText !== $employee.val() ){				
//				getEmployeeList();
//			}
//		});
//		$employee.on("focusin",function(e){
//			clearEmployeeList();
//		});
	};

	/**
	 * 
	 */
	var getEmployeeList = function(){
		if( $employee.val() === "" ){
			return;
		}
		var userData  = {
                empNameOrId : $employee.val()
        };
		self.ajaxMobile({
            url : contextPath + "/mobile/user/getEmployeeList.json", 
            //contentType: "application/json; charset=UTF-8",
            data : $.param(userData), 
            method : "POST", 
            success : function(data) {
            	try{                		
            		if( data.msg !== undefined ){
            			self.alert(data.msg);
            			return false;
            		} else if( data.extraMsg !== undefined ){
            			self.alert( data.extraMsg );
            			return;
            		} else {    					
            			displayEmployee(data.result);
            		}
            	} catch(e) {
            		
            	}
            },
            error : function(xhr) {
            	console.log(xhr);
            },
            complete: function(){
            	self.loading("hide");
            }
        });
//		
//		var test = [{"employeeId":"00000001","deptName":"본관12층","name":"심상정"},{"employeeId":"00000002","deptName":"보험심사팀","name":"노회찬"}];
//		displayEmployee( test );
	};
	/**
	 * 
	 */
	var displayEmployee = function(data){
		$elList.html("");
		if( data === undefined || data === null || data.length == 0 || $.isEmptyObject( data ) ){
			var li = $("<li></li>").addClass("elItem"),
				a = $("<a></a>").html("검색된 직원이 없습니다.").appendTo(li);
			$elList.html( li );
		} else {
			for( var i = 0; i < data.length; i++ ){
				var item= data[i],
					li = $("<li></li>").addClass("elItem"),
					a = $("<a></a>");
				
				a.addClass("elId").attr({"data-eId": item["employeeId"],"data-nm":item["name"] +" ( " + item["deptName"] + " )"})
				.html( item["name"] +"( " + item["employeeId"] + " )" + " - " + item["deptName"]);
				li.html( a );
				if( i == (data.length - 1) ){
					li.addClass("last");
				}
				$elList.append( li );
			}
		}
		var elCallback = function(e){
			e.preventDefault();
			var target = $(e.target);
			if( target.is("a.elId") ){
				var id = target.attr("data-eId"),
				nm = target.attr("data-nm");
				elText = nm;
				
				$employee.val( nm );
				$hEmployee.val( id );
				
				clearEmployeeList();	
			} else {
				clearEmployeeList();	
			}
		};
		$("input#pass2, input#pass3").blur();
		
		$elCont.show().animate({
			'opacity' : "1"
		},{
			'complete' : function(){
				self.event.addEvent( $("a.elId"), "click", elCallback );
			}
		});
		
		$elScreen.show().animate({
			'opacity' : ".5"
		},{
			'complete' : function(){
				$elScreen.off().on("click",function(e){
					$employee.val( elText );
					clearEmployeeList();
				});
			}
		})
		
	};
	/**
	 * 
	 */
	var clearEmployeeList = function(){
		$elCont.stop().animate({
			'opacity' : "0"
		},{
			'complete' : function(){
				$elCont.hide();
				$elList.html("");
			}
		});
		$elScreen.stop().animate({
			'opacity' : '0'
		},{
			'complete' : function(){
				$elScreen.hide();
			}
		});
	};
	/**
	 * 유효성 체크
	 */
	var validateData = function(){
		if( $pass2.val() === "" ) {
			self.alert( self.getI18n("registerPWD005"),function(){				
				$pass2.focus();
			});
			return false;
		}
		
		if( $pass3.val() === "" ) {
			self.alert( self.getI18n("registerPWD006"),function(){				
				$pass3.focus();
			});
			return false;
		}
		if( $pass2.val() != $pass3.val() ){
			self.alert( self.getI18n("registerPWD008"),function(){				
				$pass3.focus();
			});
			return false;
		}
		
//		패스워드 검증은 서버단에서 하는걸로
//		var testPwd = self.util.validatePWD( $pass2.val() );
//		//비밀번호 규칙
//		if( !testPwd.result ){
//			self.alert( self.getI18n( testPwd.code ) );
//			return false;
//		}
		return true;
	};
	/**
	 * 동의서 화면 요청
	 */
	var showUserAgreement = function (data){
		var pId = data.pId;
//		self.alert(pId + ""+ self.getI18n("registerPWD007"),function(){			
//			location.href = contextPath + "/login.page";
//		});
		self.changePage(contextPath + "/mobile/user/userAgreement.page?menuId=userAgreement&force=true&pId="+$pId.val());
	}
};