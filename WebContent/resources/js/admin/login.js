/**
 * admin/login
 */
var mcare_admin_login = function(){
	//상속
	mcare_admin.call(this);
	
	var self = this;
	//변수
	var $username = $("#_username"),
		$password = $("#_password"),
		$loginBtn = $("#login_btn");

	/**
	 * 초기화
	 */
	this.init = function(){
		$username.focus();
		addEvent();
	};
	/**
	 * 이벤트 등록
	 */
	var addEvent = function(){
		//로그인 버튼 클릭 이벤트
		$loginBtn.on("click", function(e) {
			login(e);
		});
		//엔터 키 이벤트
		$(document).on("keyup",function(e){
			if( e.keyCode === 13 ){
				login(e);
			}
		});
	};
	/**
	 * 로그인
	 */
	var login = function(){
		if( $username.val() === "" ) {
			alert( "아이디를 입력하세요" );
			$username.focus();
	        return;
		} else if ( $password.val() === "" ) {
			alert( "비밀번호를 입력하세요" );
			$password.focus();
	        return;
		}
		
		$.ajax({
		    type: "POST",
		    url: contextPath + "/admin/loginSubmit.json",
		    cache: false,
		    data: {
		    	username : $username.val(),
		        password : $password.val() 
		    },
		    dataType: "json",
		    success: function(e) {
				if (e.msg) {
					alert(e.msg);
					return;
		        } else {
		        	location.replace(contextPath + "/admin/api.page");
		        }
		    }
		});
	};
	
};