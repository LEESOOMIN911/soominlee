"use strict";

/**
 * mcare core
 * @extend Object
 * @description mcare 최상위
 */
var mcare = function(){
	
	var self = this;
	/**
	 * mcare core util
	 */
	var mcareUtil = this.util = {
			/**
			 *  JSON.parse
			 */
			parseJson : function(string){
				try{					
					return JSON.parse(string);
				}catch(e){
					self.log(e, "mcare_util_parseJson" );
				}
			},
			/**
			 * JSON.stringify
			 */
			stringifyJson : function(jsonObj,replacer,space){
				try{					
					return JSON.stringify(jsonObj,replacer,space);
				} catch(e){
					self.log(e, "mcare_util_parseJson" );
				}
			},
			/**
			 * 날짜 형식 YYYY-MM-DD 텍스트로 포맷을 바꿔주는 유틸
			 * @param date {date}
			 * @return text { string } - ex)2015-08-21
			 */
			simpleDateFormat : function( date ){
				try{
					var yyyy = date.getFullYear().toString(),
						mm = (date.getMonth()+1).toString(),
						dd  = date.getDate().toString(),
						text =  yyyy + "-" + (mm[1]?mm:"0"+mm[0]) + "-" + (dd[1]?dd:"0"+dd[0]); 
					
					return text;
				} catch(e) {
					self.log(e, "mcare_util_simpleDateFormat" );
				}
			},
			/**
			 * 날짜 형식 YYYY-MM-DD HH:MI 텍스트로 포맷을 바꿔주는 유틸
			 * @param date {date}
			 * @return text { string } - ex)2015-08-21 09:00
			 */
			simpleDateTimeFormat : function( date ){
				try{
					var yyyy = date.getFullYear().toString(),
					mm = (date.getMonth()+1).toString(),
					dd  = date.getDate().toString(),
					hh = date.getHours(),
					mi = date.getMinutes(),
					text =  yyyy + "-" + (mm[1]?mm:"0"+mm[0]) + "-" + (dd[1]?dd:"0"+dd[0]) + " " + (hh<10?"0"+hh:hh)+":"+(mi<10?"0"+mi:mi); 
				
					return text;
				} catch(e) {
					self.log(e, "mcare_util_simpleDateTimeFormat" );
				}
			},
			/**
			 * get 방식으로 넘어오는 파라미터 처리 유틸
			 * @return paramObj {object} key:value object로 리턴
			 */
			getParameter :  function(){
				try{
					var paramObj = new Object();
					
					var query = window.location.search.substring(1),
						vars = query.split("&");
					
					for( var i=0; i < vars.length; i++ ){
					     var pair = vars[i].split("=");

					     paramObj[ pair[0] ] = pair[1];
					}
					
					return paramObj;
				} catch(e) {
					self.log(e, "mcare_util_getParameter" );
				}
			},
			/**
			 * 
			 */
			getPrefixDomain : function() {
				try{					
					var domain=location.href; 
					domain=domain.split("//");
					domain = "http://"+domain[1].substr(0,domain[1].indexOf("/"));  // http:// 를 포함해서 return;
					
					if(contextPath == "" || contextPath == null){
						domain = domain;
					} else {
						domain = domain + "/" + contextPath;
					}
					
					return domain; 
				} catch(e){
					self.log(e, "mcare_util_getPrefixDomain" );
				}
			},
			/**
			 * 날짜 string restore
			 * @param string{string} ex)20151028  날짜 형태 YYYYMMDD 문자열
			 * @return {string} YYYY-MM-DD 형태로 리턴
			 */
			restoreDate : function( string ){
				try{					
					return string.substr(0,4) + "-" + string.substr(4,2) + "-" + string.substr(6,2);
				} catch(e){
					self.log(e, "mcare_util_restoreDate" );
				}
			},
			/**
			 * 입퇴원 string restore
			 * @param string1 {string} ex)20151028  날짜 형태 YYYYMMDD 문자열
			 * @param string2 {string} ex)20151028  날짜 형태 YYYYMMDD 문자열
			 * @return {string} YYYY-MM-DD ~ YYYY-MM-DD 형태로 리턴
			 */
			restoreDischarged : function( string1, string2 ){
				try{					
					return this.restoreDate( string1 ) + " ~ " + this.restoreDate( string2 );
				} catch(e) {
					self.log(e, "mcare_util_restoreDischarged" );
				}
			},
			/**
			 * 시간 string restore
			 * @param string1 {string} ex)1500  시간 형태 HHMM 문자열
			 * @return {string} HH:MM 형태로 리턴
			 */
			restoreTime : function( string ){
				try{
					return string.substr(0,2)+":"+string.substr(2,2);					
				} catch(e) {
					self.log(e, "mcare_util_restoreTime" );
				}
			},
			/**
			 * 진료순번대기 확인 시간 가공
			 * @param string {string} ex)20160118143323000 시간 형태 YYYYMMDDHHMMSSMI
			 * @return {string} YYYY-MM-DD HH:MM 형태로 리턴
			 */
			restoreCheckTime : function( string ){
				try{
					return string.substr(0,4) + "-" + string.substr(4,2) + "-" + string.substr(6,2) + " "+ string.substr(8,2)+":"+string.substr(10,2);
				} catch(e) {
					self.log(e, "mcare_util_restoreCheckTime" );
				}
			},
			/**
			 * 번호표 발급 시간 가공
			 * @param string {string} ex)201601181433 시간 형태 YYYYMMDDHHMM
			 * @return {string} 오전/오후 HH:MM 형태로 리턴
			 */
			restoreTicketTime : function( string ){
				try{
					var hh = parseInt(string.substr(8,2)),
						mm = parseInt(string.substr(10,2)),
						hour = (hh==0?'12':(hh>12?hh-12:hh));
					return (hh < 12 ? '오전' : '오후') + " "+ (hour<10?"0"+hour:hour) + "시 "+ (mm<10?"0"+mm:mm) + "분 발급";
				} catch(e) {
					self.log(e, "mcare_util_restoreTicketTime" );
				}
			},
			/**
			 * 비밀번호 패턴 체크 정규식
			 * @param password {string} 비밀번호 문자열
			 * @return {object} result: boolean 결과, code(optional) 에러메시지 코드
			 */
			validatePWD : function( password ){
				try{					
					// javascript
					var patt_k = /([가-힣ㄱ-ㅎㅏ-ㅣ\x20])/i; // 한글 정규식
					var patt = /^(?=.*\d)(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9]).*$/; // 혼합 정규식
					var patt_3num1 = /(\w)\1\1/; // 같은 영문자&숫자 연속 3번 정규식
					var patt_3num2 = /([\{\}\[\]\/?.,;:|\)*~`!^\-_+<>@\#$%&\\\=\(\'\"])\1\1/; // 같은 특수문자 연속 3번 정규식
					var patt_cont = /(012)|(123)|(234)|(345)|(456)|(567)|(678)|(789)|(890)|(901)/; // 연속된 숫자 정규식
					var patt_eng = /(abc)|(bcd)|(cde)|(def)|(efg)|(fgh)|(ghi)|(hij)|(ijk)|(jkl)|(klm)|(lmn)|(mno)|(nop)|(opq)|(pqr)|(qrs)|(rst)|(stu)|(tuv)|(uvw)|(vwx)|(wxy)|(xyz)|(yxa)|(ABC)|(BCD)|(CDE)|(DEF)|(EFG)|(FGH)|(GHI)|(HIJ)|(IJK)|(JKL)|(KLM)|(LMN)|(MNO)|(NOP)|(OPQ)|(PQR)|(QRS)|(RST)|(STU)|(TUV)|(UVW)|(VWX)|(WXY)|(XYZ)|(YZA)/;
					//사용가능한 특수문자가 아닌 다른 걸 사용했을 때 확인하기 위해 특수문자만, 추출하는 변수
					var ext = (password.replace(/[a-zA-Z0-9]/gi,"")).replace( /[!@#$%^&*?_~]/gi, "");
					//한글
					if( patt_k.test( password ) ){
						return {"result":false,"code":"common013"};
					//문자 혼합
					} else if( !patt.test( password ) ){
						return {"result":false,"code":"common014"};
					//지정된 특수문자외 다른 문자 사용하면 공백이 아님
					} else if( ext !== "" ){
						return {"result":false,"code":"common024"};
					//같은 숫자, 문자 3자리
					} else if( patt_3num1.test( password ) ){
						return {"result":false,"code":"common015"};
					//같은 특수 문자 3자리
					} else if( patt_3num2.test( password ) ){
						return {"result":false,"code":"common016"};
					//연속 숫자3자리
					} else if( patt_cont.test( password ) ){
						return {"result":false,"code":"common017"};
					//연속 문자 3자리
					} else if( patt_eng.test( password ) ){
						return {"result":false,"code":"common018"};
					//8자리 이상
					} else if( password.length < 8 ){
						return {"result":false,"code":"common019"};
					} 
					
					return {"result":true};
				
				} catch(e) {
					self.log(e, "mcare_util_validatePWD" );
				}
			},
			/**
			 * 연도 텍스트 가져오기
			 * @param date{date}
			 * @return {string} YYYY 연도 텍스트
			 */
			getYearText : function( date ){
				try{
					return date.getFullYear().toString();					
				} catch(e) {
					self.log(e, "mcare_util_getYearText" );
				}

			},
			/**
			 * 월 텍스트 가져오기
			 * @param date {date}
			 * @return {string} MM 월 텍스트
			 */
			getMonthText : function( date ){
				try{
					var mm = (date.getMonth()+1).toString();
					return (mm[1]?mm:"0"+mm[0]);					
				} catch(e) {
					self.log(e, "mcare_util_getMonthText" );
				}
			},
			/**
			 * 일 텍스트 가져오기
			 * @param date {date}
			 * @return {string} DD 일 텍스트
			 */
			getDayText : function( date ){
				try{					
					var dd  = date.getDate().toString();
					return (dd[1]?dd:"0"+dd[0]); 
				} catch(e) {
					self.log(e, "mcare_util_getDayText" );
				}
			},
			/**
			 * 일자 텍스트
			 * @param date{date}
			 * @return {string} YYYYMMDD 텍스트
			 */
			getDateText : function( date ){
				try{					
					return this.getYearText(date) + this.getMonthText(date) + this.getDayText(date);
				} catch(e) {
					self.log(e, "mcare_util_getDateText" );
				}
			},
			/**
			 * 시간 텍스트
			 * @param date{date}
			 * @return {string} HHMISS 텍스트
			 */
			getTimeText : function( date ){
				try{
					var date = new Date(),
						hh = date.getHours(),
						mi = date.getMinutes(),
						ss = date.getSeconds(),
						hour = (hh<10?"0"+hh:hh).toString(),
						min = (mi<10?"0"+mi:mi).toString(),
						sec = (ss<10?"0"+ss:ss).toString();
					
					return hour + min + sec; 
				} catch(e){
					console.log(e, "getTimeText" );
				}
			},
			/**
			 * 금액 숫자 포맷변환
			 * @param obj{object} 텍스트 변환을 적용할 jqeury object  ex) $(obj)
			 * @param type {object} 라이브러리 options ( 아직 별도 구현안함 - 사용안할 수 있음)
			 */
			numberFormat : function( obj, type ){
				try{					
					//소수점 없이 3자리마다 , 적용
					$.each( obj, function(i){
						var item = obj[i];
						var num = $.number( $(item).text(),0,'.',',' );
						$(item).text(num);
					});
				} catch(e) {
					self.log(e, "mcare_util_numberFormat" );
				}
			},
			/**
			 * menu param 을 사용하기 위해서 util 함수로 지정함
			 */
			getMenuParam : function(key){
				try{
					if( menuParamObj[key] == undefined ){
						return null;
					}
					var obj = menuParamObj[key];
					if( obj["type"] === "STRING" ){
						return obj["value"].toString();
					} else  if( obj["type"] === "NUMBER" ){
						return parseInt( obj["value"] );
					} else {
						return ( obj["value"] === "true" )? true : false;
					}				
				} catch(e) {
					self.log(e, "mcare_util_getMenuParam" );
					return null;
				}
			}
	};
	/**
	 * 이벤트 사용 객체
	 */
	var mcareEvent = this.event = {
			/**
			 * 동적 이벤트 등록
			 * @param object {jquery object} 이벤트 등록할 jquery object  - ex)$("#event")
			 * @param type {string} event type - ex) click
			 * @param callback {function} 이벤트 콜백 함수
			 * @description 동적으로 dom 을 수정하거나 추가할 때, 이벤트 바인딩
			 */
			addEvent : function( object, type, callback ){
				try {
					object.on( type, callback );
				} catch (e) {
					self.log(e, "mcare_event_addEvent" );
				}
			}
	};
	
	//다국어 처리를 위한 변수처리 - 각 페이지 jsp 별로 i18n을 선언. 없으면 그냥 객체
	var message = (typeof(i18n) !== "undefined")? new i18n() : {};
	
	/**
	 * 다국어 메시지 가져오기
	 * @param code 메시지 코드
	 */
	this.getI18n = function( code ){
		try {			
			return message.getMessage( code );
		} catch (e) {
			self.log(e, "mcare_message_getI18n" );
		}
		
	}
	
	/**
	 * mcare core log function
	 * @param exception
	 * @param msg
	 * @description 에러 로그
	 */
	this.log = function(exception, msg){
		if( msg !== undefined ){
			console.log( msg );
		}
		console.log( "Error Name: " + exception.name + ", Error Msg: "+ exception.message );
		console.log( exception.stack );
	};
	/**
	 * native 호출 뒤에 callback으로 script를 호출하기 위해 객체를 global로 설정
	 * @param obj {function} 현재 페이지 스크립트 function
	 */
	this.setGlobal = function( obj ){
		window.activeObj = obj;
	};
	/**
	 * 통신 객체
	 */
	var mcareAjax = this.ajax = {
		
		send : function(opt,successFn,errorFn){
			var sendOpt = $.extend(opt,{
				success: function(data){
					successFn(data);
				},
				error : function(xhr,d,t){
					errorFn(xhr,d,t);
				}
			})
			try{				
				$.ajax(sendOpt);
			}catch(e){
				self.log(e, "mcare_ajax_send" );
			}
		}
	};
};

/**
 * mcare/core/admin
 * @extend mcare
 * @description mcare 관리자 
 */
var mcare_admin = function(){
	mcare.call(this);
	
	var self = this;
	//변수
		//종료 datepicker
	var endDatePicker = null,
		//시작 datepicker
		startDatePicker = null,
		//dropdown
		dropDownList = null,
		//chart
		chart = null;
	/**
	 * kendo datepicker
	 * @param $endDate 종료 datepicker 위치한 jquery object : ex) $("#endDate")
	 * @param $strDate 시작 datepicker 위치한 jquery object : ex) $("#strDate")
	 * @param $selectOption 검색 조건 dropdown 이 위치한 jquery object : ex) $("#select-option")
	 * @description 관리자 화면에서 사용하는 공통된 datepicker생성 함수를 하나로 사용하도록 함
	 */
	this.datePicker = function( $endDate, $strDate, $selectOption){
		try {
			var endValue = new Date(),
				startValue = new Date();
				
			endValue.setDate( endValue.getDate()-1 );
			startValue.setDate( startValue.getDate()-1 );
			
			endDatePicker = $endDate.kendoDatePicker({
		        change: function(e) {
		            var endDate = endDatePicker.value(),
		            	strDate = startDatePicker.value();
		            if( endDate ) {
		                endDate = new Date( endDate );
		                endDate.setDate( endDate.getDate() );
		            } else if( startDate ) {
		            	endDatePicker.min( new Date(startDate) );
		            } else {
		                endDate = new Date();
		            }
		        },
		        value : endValue
		    }).data("kendoDatePicker");
			
			startValue.setDate( startValue.getDate() - $selectOption.val() );
		    startDatePicker = $strDate.kendoDatePicker({
		        change: function(e) {
		            var strDate = startDatePicker.value(),
		            	endDate = endDatePicker.value();
		            
		            if( strDate ) {
		                strDate = new Date( strDate );
		                strDate.setDate( strDate.getDate() );
		                endDatePicker.min( strDate );
		            } else if( endDate ) {
		            } else {
		                endDate = new Date();
		            }
		        },
		        value : startValue
		    }).data("kendoDatePicker");
		} catch (e) {
			self.log(e, "mcare_admin_datePicker" );
		}
		
	};
	/**
	 * kendo dropdown
	 * @param $selectOption 검색 조건 dropdown 이 위치한 jquery object : ex) $("#select-option")
	 * @description 관리자 화면에서 공통적으로 사용하는 검색조건 dropdown 생성함수를 하나로 사용하도록 함
	 */
	this.dropDownList = function( $selectOption, options ){
		try {
			var option = {
					change: function(e) {
						var endDate = new Date();
						endDate.setDate( endDate.getDate() - 1 );
						endDatePicker.value( endDate );
						var startValue = new Date();
						startValue.setDate( startValue.getDate() - 1 - Number( e.sender._selectedValue ) ); 
						startDatePicker.value( startValue );
					}
			};
			
			if( options ){
				option = options;
			}
			// 검색조건
			dropDownList = $selectOption.kendoDropDownList(option).data("kendoDropDownList");
		} catch (e) {
			self.log(e, "mcare_admin_dropDownList" );
		}
	};
	/**
	 * 검색 조건 변경 이벤트 콜백
	 * @param dataSource 검색 조건에 해당하는 내용을 불러올 데이터 소스
	 * @description 관리자 화면에서 공통적으로 사용하는 검색 조건 변경 이벤트 콜백
	 */
	this.search = function( dataSource, isPage ){
		try {			
			var strDate = kendo.toString( startDatePicker.value(), "yyyyMMdd" ),
			endDate = kendo.toString( new Date( endDatePicker.value() + (60*60*24*1000) ), "yyyyMMdd");
			if( strDate <= endDate ) {
				if( isPage ){					
					dataSource.page(1);
				} else {					
					dataSource.read();
				}
			} else {
				alert( "조회할 수 없습니다" );
				return;
			}
		} catch (e) {
			self.log(e, "mcare_admin_search" );
		}
	};
	/**
	 * chart
	 * @param $chart 차트가 위치한 jquery object : ex) $("#chart")
	 * @param type 차트 타입
	 * @param option {object} 차트 옵션
	 * @param data 차트데이터
	 * @description 관리자 화면에서 사용하는 chart 생성함수
	 */
	this.chart = function( $chart, type, options, data, $legend ){
		try {			
			var ctx = $chart.get(0).getContext("2d");
			//차트 객체가 있으면 destroy 하고 다시 생성한다
			if( chart !== null && typeof chart.destroy === "function" ){
				chart.destroy();
			}
		
			switch( type.toLowerCase() ){
				case "line" : chart = new Chart(ctx).Line( data, options ); break;
				case "bar" : chart = new Chart(ctx).Bar( data, options ); break;
				case "radar" : chart = new Chart(ctx).Radar( data, options ); break;
				case "polararea" : chart =  new Chart(ctx).PolarArea( data, options );break;
				case "pie" :  chart = new Chart(ctx).Pie( data, options );break;
				case "doughnut" :  chart = new Chart(ctx).Doughnut( data, options );break;
				default : chart = new Chart(ctx).Line( data, options ); break;
			}
			
			if( $legend !== undefined ){				
				$legend.html( chart.generateLegend() );
			}

		} catch (e) {
			self.log(e, "mcare_admin_chart" );
		}
	};
	/**
	 * 
	 */
	this.chartUpdate = function( labels,data ){
		chart.clear();
		chart.render( data );
	};
	/**
	 * kendo grid
	 * @param $grid 그리드가 위치한 jquery object : ex) $("#grid")
	 * @param option 그리드 옵션
	 * @description 관리자 화면에서 사용하는 kendo grid 생성함수
	 */
	this.grid = function( $grid, option ){
		try {			
			// 그리드
			return $grid.kendoGrid(option).data("kendoGrid");
		} catch (e) {
			self.log(e, "mcare_admin_grid" );
		}
	};
	/**
	 * start datepicker 값 
	 * @return date object
	 */
	this.getStartDatePickerValue = function(){
		try {
			return startDatePicker.value();			
		} catch (e) {
			self.log(e, "mcare_admin_getStartDatePickerValue" );
		}
	};
	/**
	 * end datepicker 값
	 * @return date object
	 */
	this.getEndDatePickerValue = function(){
		try {			
			return endDatePicker.value();
		} catch (e) {
			self.log(e, "mcare_admin_getEndDatePickerValue" );
		}
	};
	/**
	 * 트리 사용
	 * @param $tree {jquery object} tree 가 위치할 jquery object ex $("#tree")
	 * @param option {object} kendo tree option 정보 
	 */
	this.tree = function( $tree, option ){
		try {
			return $tree.kendoTreeView(option).data("kendoTreeView");
		} catch (e) {
			self.log(e, "mcare_admin_tree" );
		}
	};
	/**
	 * 
	 */
	this.ajaxAdmin = function( opt, successFn, errorFn ){
		var sFn = function(data){
			if( data.msg !== undefined ){
				if( data.type !== undefined && data.type === "AuthException" ){
					window.location.href = contextPath + "/admin/login.page";
				} else {
					alert(data.msg);
				}
			} else {
				successFn(data);
			}
		};
		var eFn = function(xhr,d,t){
			errorFn(xhr,d,t);
		};
		
		try{			
			self.ajax.send( opt, sFn, eFn );
		} catch(e) {
			self.log(e, "mcare_admin_ajaxAdmin" );
		}
	};
	/**
	 * 글자수 bytes 체크
	 */
	this.checkDescBytes = function(desc){
		desc.on("keyup paste",function(e){
			var length = getBytes($(this));
			$("#descLength").text( length );
		});
		function getBytes(obj){
			var limitByte = 500; //바이트의 최대크기, limitByte 를 초과할 수 없슴
			var totalByte = 0;
		    var message = obj.val();

		    for( var i =0; i < message.length; i++ ) {
		         var currentByte = message.charCodeAt(i);
		         if( currentByte > 128 ){
		        	 totalByte += 2;
		         } else {
		        	 totalByte++;
		         }
		   }
		   return totalByte; 
		}  
	};
	/**
	 * csv 저장 기능 
	 */
	this.csvSave = function( url,params ){
		
		var form = document.createElement("form");
	    form.setAttribute("method", "POST");
	    form.setAttribute("action", url);
	    form.setAttribute("target","_blank");
	    for(var key in params ) {
	        var hiddenField = document.createElement("input");
	        hiddenField.setAttribute("type", "hidden");
	        hiddenField.setAttribute("name", key);
	        hiddenField.setAttribute("value", params[key]);
	        form.appendChild(hiddenField);
	    }
	    document.body.appendChild(form);
	    form.submit();
	    document.body.removeChild(form);
	};
}

/**
 * mcare/core/mobile
 * @extend mcare
 * @description mcare 모바일 어플리케이션
 */
var mcare_mobile = function(){
	mcare.call(this);
	
	var self = this;
	//swipe
	var swipe = null;
	/**
	 * android 단말기 인지 판별한다.
	 */
	this.isAndroid = function() {
		try{			
			return (/android/i.test(navigator.userAgent.toLowerCase()));
		} catch(e){
			self.log(e,"mcare_mobile_isAndroid");
		}
	}

	/**
	 * iOS 단말기 인지 확인
	 */
	this.isIos = function() {
		try{
			return (/iphone|ipad|ipod/i.test(navigator.userAgent.toLowerCase()));			
		} catch(e) {
			self.log(e,"mcare_mobile_isIos");
		}
	}

	/**
	 * 모바일인지 판별한다.
	 */
	this.isMobile = function() {
		try {
			return (/iphone|ipad|ipod|android|opera\smini|opera\smobi|symbian|blackberry|mini|windows\sce|palm/i.test(navigator.userAgent.toLowerCase()));			
		} catch (e) {
			self.log(e,"mcare_mobile_isMobile");
		}
	}

	/**
	 * 앱인지 판단한다.
	 */
	this.isApp = function() {
		try {			
			return (/mobile:Y/i.test(navigator.userAgent.toLowerCase()));
		} catch (e) {
			self.log(e,"mcare_mobile_isApp");
		}
	}

	/**
	 * Native App 호출
	 * javascripte -> native
	 * @param reqParam {object} native에 전달할 데이터 정보가 담긴 객체
	 */
	this.toNative = function( reqParam ){
		try {			
			var data = self.util.stringifyJson( reqParam );
			if( self.isMobile() ){
				// 앱이 안드로이드일 경우
				if( self.isAndroid() ){
					window.Android.toNative( data );
				} else if( self.isIos() ){
					data = "jscall://toNative?" + data;
					location.href = data;
//					iframe으로 파라미터를 넘기니까 iOS에 한글 파라미터 전달씨 깨지는 현상이 발생한다.
//					그래서 ?를 구분으로 파라미터 시작을 알리도록 수정하는데 만일을 위해 frame영역은 일단 주석 처리 한다.
//					data = "jscall://toNative:" + data;
//					var iframe = document.createElement("iframe");
//					iframe.setAttribute("src", data);
//					document.documentElement.appendChild(iframe);
//					iframe.parentNode.removeChild(iframe);
//					iframe = null;
				}
			}else{
				alert("App에서만 사용가능한 기능입니다.");
			}
		} catch (e) {
			self.log(e,"mcare_mobile_toNative");
		}
	};
	/**
	 * 로딩 이미지 표시
	 * @param order {string} show/hide
	 * @description jqm loading wrapping
	 */
	this.loading = function( order ){
		try{	
			$.mobile.loading( order );
			if( order === "show" ){				
				$("div.ui-panel-dismiss").css("z-index",9999900);
			} else {
				$("div.ui-panel-dismiss").css("z-index","");
			}
		} catch(e) {
			self.log(e,"mcare_mobile_loading");
		}
	};
	/**
	 * 페이지 이동 메소드
	 * @param address 이동할 주소
	 * @description jqm changePage wrapping
	 */
	this.changePage = function( address ){
		try {			
//			$(":mobile-pagecontainer").pagecontainer();
//			$(":mobile-pagecontainer").pagecontainer("change",address);
			location.href = address;
		} catch (e) {
			self.log(e,"mcare_mobile_changePage");
		}
	};
	/**
	 * 날짜 유효성 체크
	 * @param $strData {object} 시작일 input jquery obj
	 * @param $endDate {object} 종료일 input jquery obj
	 * @description 날짜 유효성 체크를 하나로 일원화해서 상속받아 사용하도록함 
	 */
	this.dateValidation = function( $strDate, $endDate ){
		try{
			if( $strDate.val() === "" || $endDate.val() === "" ){
				self.alert( self.getI18n( "common007" ) ); //조회일자를 선택하세요
				return false;
			} else if( $strDate.val() > $endDate.val() ){
				self.alert( self.getI18n( "common008" ) ); //종료일이 시작일 이전일 수 없습니다.
				$endDate.focus();
				return false;
			} else if( !checkDateDiff( $strDate.val(), $endDate.val() ) ){			
				self.alert( self.getI18n( "common009" ) ); //1년이상을 조회할 수 없습니다.
				return false;
			}
			return true;
		} catch(e) {
			self.log(e, "mcare_mobile_dateValidation" );
		}
		
	};
	/**
	 * 날짜간 차이 계산 - 1년 기준
	 * @parivate
	 * @param strDate {string} strDate value
	 * @param endDate {string} endDate value 
	 */
	var checkDateDiff = function( strDate, endDate ){
		try{			
			var diff = getDateDiff( strDate, endDate );
			if( Math.abs(diff) > 1 ){
				return false;
			}
			return true;
		} catch(e) {
			self.log(e,"mcare_mobile_checkDateDiff");
		}
		
		// 날짜 차이 계산 함수 1년단위
	    // date1 : 기준 날짜(YYYY-MM-DD), date2 : 대상 날짜(YYYY-MM-DD)
	    function getDateDiff( date1,date2 ){
	        var arrDate1 = date1.split("-");
	        var getDate1 = new Date(parseInt(arrDate1[0]),parseInt(arrDate1[1])-1,parseInt(arrDate1[2]));
	        var arrDate2 = date2.split("-");
	        var getDate2 = new Date(parseInt(arrDate2[0]),parseInt(arrDate2[1])-1,parseInt(arrDate2[2]));
	        
	        var getDiffTime = getDate1.getTime() - getDate2.getTime();
	        
	        return Math.floor(getDiffTime / (1000 * 60 * 60 * 24 * 365));
	    }
	};
	/**
	 * 메인 화면 swipe
	 * slick 라이브러리 사용
	 * @param container {string} swipe container selector  - ex) .swiper-container
	 * @param option {object} 추가로 설정할 option  - 참고 주소 : http://kenwheeler.github.io/slick/
	 * @history 2015-10-01 slick 라이브러리로 교체 - https://github.com/kenwheeler/slick
	 */
	this.swipe = function( container, option ){
		try{			
			container.slick( option );

		} catch(e) {
			self.log(e,"mcare_mobile_swipe");
		}
	};
	/**
	 * orientation 전환 함수 호출
	 * @param mode {string} portrait | landscape 
	 */
	this.changeOrientation = function( mode ){
		var reqParam = {
			"type" : "command",
			"functionType" : "screen",
			"value" : {
				"orientation" : mode,
				"callbackFn":"window.activeObj.init"
			}
		};
		try{			
			this.toNative( reqParam);
		} catch(e) {
			self.log(e,"mcare_mobile_changeOrientation");
		}
	};
	/**
	 * 모바일 컨펌
	 */
	this.popup = function( options ){
		try{			
			var popup = $("#popupDialog"),
				content = popup.find(".popupcontent"),
				callback = popup.find(".popupCallback");		
			
			if( options["content"] === "" || options["content"] === undefined ){
				console.log( "text empty or undefined" );
			}
			content.html( options["content"] );
			
			callback.off();
			callback.on("click",function(e){
				e.preventDefault();
				options["callback"]();
				popup.popup("close");
			});
			
			setTimeout(function(){				
				popup.popup("open",{});
				self.loading("hide");
			},300);
		} catch(e) {
			self.log(e,"mcare_mobile_popup");
		}
	};
	/**
	 * 모바일 alert
	 */
	this.alert = function( text, callbackFn ){
		try{			
			var alert = $("#alertDialog"),
				content = alert.find(".popupcontent"),
				callback = alert.find(".alertCallback");		
			if( text === "" || text === undefined ){
				console.log( "text empty or undefined" );
			}
			content.html( text );
			
			callback.off();
			callback.on("click",function(e){
				e.preventDefault();
				alert.popup("close");
				if( callbackFn !== undefined && typeof callbackFn == "function" ){					
					callbackFn();
				}
			});
			setTimeout(function(){				
				alert.popup("open",{});
				self.loading("hide");
			},300);
		} catch(e) {
			self.log(e,"mcare_mobile_alert");
		}
	};
	/**
	 * history state change
	 */
	this.stateChange = function( href, data ){
		try{
			var path = href.indexOf("&change=") >=0? href.substr(0,href.indexOf("&change=")) : href;
			var ua = navigator.userAgent.toLowerCase(),
				version = 0;
			if( this.isAndroid() ){
				version = parseFloat(ua.substr(ua.search(/android/) + 7, 4));
				if( version >= 5 ){
					window.history.replaceState({},document.title, path + "&change=true" + data );
				}
			}else if( this.isIos() ){
				version = parseFloat(ua.substr(ua.search(/ipad|iphone/), 30).match(/\d+_+\d/)[0].replace('_', '.'));
				if( version >= 9.3 ){
					window.history.replaceState({},document.title, path + "&change=true" + data );
				}
			}
		} catch(e) {
			self.log(e,"mcare_mobile_stateChange");
		}
	}
	this.getDomain = function() {
        var cmaDns=location.href;
        cmaDns=cmaDns.split("//");
        cmaDns = cmaDns[0] + "//"+cmaDns[1].substr(0,cmaDns[1].indexOf("/"));  // http:// 를 포함해서 return;
        return cmaDns;
    };
    
    /**
     * fixed header가 ios에서 키보드로 인해서 바르게 동작하지 않는 문제
     * 
     */
    this.headerFix = function(){
    	$(".ui-page").off("focusin focusout");
		
		$("input").on("focus", function(){			
			$("div[data-role=header]").css("position", "absolute");			
		});
				
		$("input").on("blur",function(){
			$("div[data-role=header]").css("position", "fixed");
		});
    };
}


