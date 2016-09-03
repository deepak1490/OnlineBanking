<!DOCTYPE html>
<html lang="en">
<head>
<script type="text/javascript">
	window.history.forward();
	function noBack() {
		window.history.forward();
	}
</script>
<meta charset="utf-8">
<title>Change Password</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link
	href="http://netdna.bootstrapcdn.com/bootstrap/3.1.0/css/bootstrap.min.css"
	rel="stylesheet">
<style type="text/css">
</style>
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
<script
	src="http://netdna.bootstrapcdn.com/bootstrap/3.1.0/js/bootstrap.min.js"></script>
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css" />
	
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col-sm-12">
				<h1>Forgot Password</h1>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-6 col-sm-offset-3">
				<div class="userName" id="userNameDiv">
					<input type="password" class="input-lg form-control"
						name="username" id="userName" placeholder="User Name"> <br />
					<p id="failure" style="display: none; font-family: cursive;">Invalid
						User Name. Not Found in Database!!</p>
					<input type="button"
						class="col-xs-12 btn btn-primary btn-load btn-lg" id="generateid"
						value="Generate OTP" onclick="update()" required>
				</div>
				<div class="otp" id="otp" style="display: none;">
					<input type="password" class="input-lg form-control" name="otp1"
						id="otp1" placeholder="OTP"> <br />
					<p id="failure1" style="display: none; font-family: cursive;">Invalid
						OTP!!</p>
					<input type="password" class="input-lg form-control"
						name="password1" id="password1" placeholder="New Password"
						autocomplete="off" required>
					<div class="row">
						<div class="col-sm-6">
							<span id="8char" class="glyphicon glyphicon-remove"
								style="color: #FF0004;"></span> 8 Characters Long<br> <span
								id="ucase" class="glyphicon glyphicon-remove"
								style="color: #FF0004;"></span> One Uppercase Letter
						</div>
						<div class="col-sm-6">
							<span id="lcase" class="glyphicon glyphicon-remove"
								style="color: #FF0004;"></span> One Lowercase Letter<br> <span
								id="num" class="glyphicon glyphicon-remove"
								style="color: #FF0004;"></span> One Number
						</div>
					</div>
					<input type="password" class="input-lg form-control"
						name="password2" id="password2" placeholder="Repeat Password"
						autocomplete="off" required>
					<div class="row">
						<div class="col-sm-12">
							<span id="pwmatch" class="glyphicon glyphicon-remove"
								style="color: #FF0004;"></span> Passwords Match
						</div>
					</div>
					<input type="button"
						class="col-xs-12 btn btn-primary btn-load btn-lg"
						data-loading-text="Changing Password..." value="Change Password"
						onclick="change()">
				</div>
				<div class="login" id="login" style="display: none;">
					<form action="logout" method="post">
						<p id="success" style="display: none; font-family: cursive;">Password
							updated Successfully</p>
						<input type="submit"
							class="col-xs-12 btn btn-primary btn-load btn-lg"
							value="Go To Login Page">
					</form>
				</div>

			</div>
			<!--/col-sm-6-->
		</div>
		<!--/row-->
	</div>
	<script type="text/javascript">
		$("input[type=password]").keyup(function() {
			var ucase = new RegExp("[A-Z]+");
			var lcase = new RegExp("[a-z]+");
			var num = new RegExp("[0-9]+");

			if ($("#password1").val().length >= 8) {
				$("#8char").removeClass("glyphicon-remove");
				$("#8char").addClass("glyphicon-ok");
				$("#8char").css("color", "#00A41E");
			} else {
				$("#8char").removeClass("glyphicon-ok");
				$("#8char").addClass("glyphicon-remove");
				$("#8char").css("color", "#FF0004");
			}

			if (ucase.test($("#password1").val())) {
				$("#ucase").removeClass("glyphicon-remove");
				$("#ucase").addClass("glyphicon-ok");
				$("#ucase").css("color", "#00A41E");
			} else {
				$("#ucase").removeClass("glyphicon-ok");
				$("#ucase").addClass("glyphicon-remove");
				$("#ucase").css("color", "#FF0004");
			}

			if (lcase.test($("#password1").val())) {
				$("#lcase").removeClass("glyphicon-remove");
				$("#lcase").addClass("glyphicon-ok");
				$("#lcase").css("color", "#00A41E");
			} else {
				$("#lcase").removeClass("glyphicon-ok");
				$("#lcase").addClass("glyphicon-remove");
				$("#lcase").css("color", "#FF0004");
			}

			if (num.test($("#password1").val())) {
				$("#num").removeClass("glyphicon-remove");
				$("#num").addClass("glyphicon-ok");
				$("#num").css("color", "#00A41E");
			} else {
				$("#num").removeClass("glyphicon-ok");
				$("#num").addClass("glyphicon-remove");
				$("#num").css("color", "#FF0004");
			}

			if ($("#password1").val() == $("#password2").val()) {
				$("#pwmatch").removeClass("glyphicon-remove");
				$("#pwmatch").addClass("glyphicon-ok");
				$("#pwmatch").css("color", "#00A41E");
			} else {
				$("#pwmatch").removeClass("glyphicon-ok");
				$("#pwmatch").addClass("glyphicon-remove");
				$("#pwmatch").css("color", "#FF0004");
			}
		});
		function update() {
			var userName = $("#userName").val();

			$.ajax({
				type : "POST",
				url : "forgotPassword",
				data : "userName=" + userName,
				success : function(response) {
					if (response == "success") {
						$('#otp').show();
						$('#failure').hide();
						$('#userName').removeClass();
						$('#userName').hide();
						$('#generateid').hide();
					} else if (response == "failure") {
						$('#userName').addClass("ui-state-error");
						$("#failure").css("color", "#FF0000");
						$('#userName').val("");
						$('#failure').show();

					}
				}
			});
		}
		function change() {
			var userName = $("#userName").val();
			var otp = $("#otp1").val();
			var password = $("#password1").val();

			$.ajax({
				type : "POST",
				url : "forgotPassword1",
				data : "userName=" + userName + "&otp=" + otp + "&password="
						+ password,
				success : function(response) {
					if (response == "success") {
						$('#success').show();
						$("#success").css("color", "#00FF00");
						$('#failure1').hide();
						$('#otp1').removeClass();
						$('#userNameDiv').hide();
						$('#otp').hide();
						$('#login').show();

					} else if (response == "failure") {
						$('#otp1').addClass("ui-state-error");
						$("#failure1").css("color", "#FF0000");
						$('#otp1').val("");
						$('#password1').val("");
						$('#password2').val("");
						$('#failure1').show();
					}
				}
			});
		}
	</script>
</body>
</html>
