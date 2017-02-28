/**
 * admin/userPlatform
 */
var mcare_admin_userPlatform = function(){
	//상속
	mcare_admin.call(this);
	
	var self = this;
	//변수
	var $chart = $("#chart"),
		$legend = $("#chart_legend"),
		$grid = $("#grid"),
		$totalUserCnt = $("#totalUserCnt"),
		$validUserCnt = $("#validUserCnt"),
		$cntPersent = $("#cntPersent"),
		$registeredTokenCnt = $("#registeredTokenCnt"),
		$cntPersentUser = $("#cntPersentUser"),
		$crudServiceBaseUrl = contextPath + "/admin/stats";
	/**
	 * 초기화
	 */	
	this.init = function(){
		initTotalInfo();
		//initGrid();
		//initChart();
		addEvent();
	};
	this.totalUserCnt = 0;
	this.validUserCnt = 0;
	this.registeredTokenCnt = 0;
	/**
	 * 이벤트 등록
	 */
	var addEvent = function(){

	};
	var initTotalInfo = function(){
		var opt = {
    			url : $crudServiceBaseUrl + "/getUserTokenInfo.json",
    			data: self.util.stringifyJson( {} ),
        		type : "POST",
        		contentType : "application/json",
        		complete : function(){
        			initGrid();
        		}
    	};
    	var success = function(data){
    		if( data.msg !== undefined ){
    			alert( data.msg );
    		} else {    			
    			self.totalUserCnt = data.totalUserCnt;
    			self.validUserCnt = data.validUserCnt;
    			self.registeredTokenCnt = data.registeredTokenCnt;
    			
    			$totalUserCnt.html( "&nbsp;" + self.totalUserCnt );
    			$validUserCnt.html( "&nbsp;" + self.validUserCnt + "&nbsp;" );
    			$cntPersent.html( "( " + ((self.validUserCnt/self.totalUserCnt)*100).toFixed(2) + "% )" );
    			$registeredTokenCnt.html( "&nbsp;" + self.registeredTokenCnt );
    			$cntPersentUser.html( ( self.registeredTokenCnt / self.validUserCnt).toFixed(2) );
    		}
    	};
    	var error = function(xhr,d,t){
    		console.log(xhr);
			alert(xhr.responseText);
    	};
    	self.ajaxAdmin( opt, success, error );
	}
	// 데이타소스
	var pieData = {};
	/**
	 * 데이터소스 설정
	 */
	var setDataSource = function(){
		
		var dataSource = new kendo.data.DataSource({
			transport: {
				read: {
					url: $crudServiceBaseUrl + "/getUserPlatformList.json",
					method: "post",
					dataType: "json",
					data : "{'':''}",
					contentType: "application/json"
				}
			},
			schema: {
				data : getSchemaData
			}
		});
		/**
		 * 데이터소스 파라미터맵
		 * @private
		 */
		function getParameterMap( options, operation ){
			var param = $.extend(true, {}, options, {

			}, null);
			return self.util.stringifyJson( param );
		};
		/**
		 * 데이터소스 스키마설정
		 * @private
		 */
		function getSchemaData(e){
			if( e.msg !== undefined ){
				alert(e.msg);
				return [];
			}
			var aggPlatformList = e.aggUserPlatformList;
			
			var userOrdArr = new Array(),
				userCntArr = new Array(),
				platformArr = new Array(),
				totalCount = 0;
			
			//내려온 데이터 순서대로 
			for( var n = 0 ; n < aggPlatformList.length ; n ++ ) {
				//순위 배열
				userOrdArr.push( n+1 );
				//플랫폼 타입 배열
				platformArr.push( aggPlatformList[n].platformType );
				//가입자 수 배열
				userCntArr.push( aggPlatformList[n].dataCnt );
				//비율을 위한 전체 횟수
				totalCount += aggPlatformList[n].dataCnt;
			}
			
			var percentage = new Array();
			
			for( var n = 0 ; n < userCntArr.length ; n ++ ) {
				 var cnt = userCntArr[n]/totalCount*100,
				 str_per = ( Math.round(cnt*100) ) / 100;
				 //비율 계산해서 비율 배열에 넣기
				 percentage.push( str_per );
				 //플랫폼 타입을 key로 비율값 value - 차트를 위해서
				 pieData[ platformArr[n] ] = str_per;
			}
			
			var param = [];
			for( var n = 0, size = platformArr.length ; n < size ; n ++ ) {
				param.push({
					"userOrdArr" : userOrdArr[n],
					"platformType" : platformArr[n],
					"dataCnt" : userCntArr[n],
					"percentage" : percentage[n],
				});
			}
			
			return param;
		};
		return dataSource;
	};
	var dataSource = setDataSource();
	/**
	 * 그리드 초기화
	 */
	var initGrid = function(){
		// 그리드 옵션
		var option = {
			dataSource: dataSource,
			//그리드 데이터 받아온 뒤 차트 초기화
			dataBound: initChart,
	        pageable: false,
	        selectable: true,
	        height : 215,
	        columns : [
//	        	{
//	        		field : "userOrdArr",
//	        		title : "No.",
//	        		width: 80,
//	        		template: "#:data.userOrdArr#",
//	        		attributes: {
//				      "class": "table-cell",
//				      style: "text-align: center;"
//				    }
//	        	},
	        	{
	        		field : "platformType",
	        		title : "플랫폼 타입",
	        		width: 50,
	        		attributes: {
				      "class": "table-cell",
				      style: "text-align: center;"
				    }
	        	},
	        	{
	        		field : "dataCnt",
	        		title : "토큰 수",
	        		width: 50,
	        		template: "#= kendo.toString(data.dataCnt,'n0')# 건",
	        		attributes: {
				      "class": "table-cell",
				      style: "text-align: center;"
				    }
	        	}
//	        	,
//	        	{
//	        		field : "percentage",
//	        		title : "비율",
//	        		width: 320,
//	        		template: "#:data.percentage# %",
//	        		attributes: {
//				      "class": "table-cell",
//				      style: "text-align: center;"
//				    }
//	        	}
	        ]
	    };
		//그리드 초기화 mcare_admin
		self.grid( $grid, option );
	};
	/**
	 * 차트 초기화
	 */
	var initChart = function(){
	
    	var data = setChartData( pieData );
    	
    	//데이터 없으면 차트 표시하지 않기
    	if( data.length === 0 ){
    		var cl = setColor();
    		data.push({
				 value : 0,
				 label : "데이터없음",
				 color : cl,
				 highlight : cl
			});
    	}

		var option = {
				tooltipTemplate: "<%= value %>%",
				onAnimationComplete : function(){
					this.showTooltip(this.segments, true);
				},
				tooltipEvents : [],
				showTooltips : true
		};
		//차트 초기화 mcare_admin
		self.chart( $chart, "pie", option, data, $legend );
		
		function setChartData( data ){
			var restore = [];
			for( var key in data ) {
				 var cl = setColor();
				 var item = {
					 value : data[key],
					 label : key,
					 color : cl,
					 highlight : cl
				 };
				 restore.push( item );
			}
			
			return restore;
		}
		
		function setColor( ){
			var colorArr = [
			       "#F7464A","#46BFBD","#FDB45C" ,
			       "#008000","#3f0080","#800040","#007f80","#7f8000",
			       "#003f80","#ff007f",
			       "#4c8000","#00804c","#ff0000","#00ff66",
			       "#7f00ff","#feff00","#003fff","#00ffef","#7fffd4",
			       "#4b0082","#087830","#3200ff","#7fff00","#0000ff"
			];
			var random = Math.floor( Math.random() * ( (colorArr.length-1) + 1 ) );
			
			return colorArr[random];
		}
	};
};