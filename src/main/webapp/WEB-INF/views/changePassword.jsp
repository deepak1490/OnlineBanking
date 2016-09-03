
<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page session="false"%>
<html lang="en">

<head>

<title>Change Password</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link
	href="http://netdna.bootstrapcdn.com/bootstrap/3.1.0/css/bootstrap.min.css"
	rel="stylesheet">
<style type="text/css">
</style>
<script type="text/javascript">
	window.history.forward();
	function noBack() {
		window.history.forward();
	}
</script>
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
<script
	src="http://netdna.bootstrapcdn.com/bootstrap/3.1.0/js/bootstrap.min.js"></script>
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css" />
<link href="resources/css/simple-sidebar.css" rel="stylesheet">
<script>
	$(document).ready(function() {
		$('#sub-menu-trans').hide();
		$('#sub-menu-trans1').hide();
		$('#sub-menu-transfer1').hide();
		$('#sub-menu-transfer2').hide();
		$('#sub-menu-transfer3').hide();
		$('#sub-menu-transfer').hide();

		$('#trans-menu').click(function() {
			$('#sub-menu-trans').toggle("slow");
			$('#sub-menu-trans1').toggle("slow");
		});
		$('#transfer-menu').click(function() {
			$('#sub-menu-transfer').toggle("slow");
			$('#sub-menu-transfer1').toggle("slow");
			$('#sub-menu-transfer2').toggle("slow");
			$('#sub-menu-transfer3').toggle("slow");
		});
	});
</script>
<style type="text/css">
.sub-menu-trans-style {
	padding-left: 15px;
}
</style>
</head>

<body>
	<div id="wrapper">
		<div id="sidebar-wrapper">
			<ul class="sidebar-nav">
				<li class="sidebar-brand"><a href="#"> Welcome
						${userProfile.firstName}!! </a></li>
				<li><a id="dashboard" href="Dashboard">Dashboard</a></li>
				<li><a id="deposit" href="Deposit">Deposit</a></li>
				<li><a id="withdraw" href="Withdraw">Withdraw</a></li>
				<li><a id='transfer-menu'>Transfer Money</a>
					<div class="sub-menu-trans-style" id="sub-menu-transfer1">
						<a href="Transfer">Transfer Using Account Number</a>
					</div>
					<div class="sub-menu-trans-style" id="sub-menu-transfer2">
						<a href="MobileTransfer">Transfer Using Mobile Number</a>
					</div>
					<div class="sub-menu-trans-style" id="sub-menu-transfer">
						<a href="EmailTransfer">Transfer Using Email</a>
					</div>
					<div class="sub-menu-trans-style" id="sub-menu-transfer3">
						<a href="AddPayee">Add Payee</a>
					</div></li>
				<li><a id='trans-menu'>Transaction Details</a>
					<div class="sub-menu-trans-style" id="sub-menu-trans1">
						<a href="last5Transactions">Last 5 Transactions</a>
					</div>
					<div class="sub-menu-trans-style" id="sub-menu-trans">
						<a href="TransactionDetails">All Transactions</a>
					</div></li>
				<li><a id="bill" href="BillPay">BillPay</a></li>
				<li><a id="profile" href="Profile">Profile</a></li>
				<li><a id="chanegpass" href="#">Change password</a></li>
				<li><a id = "contact" href="ContactUs">Contact us</a></li>
				<li><a href="logout">Log Out</a></li>
			</ul>
		</div>
		<div id="page-content-wrapper">
			<div class="container">
				<div class="row">
					<div class="col-sm-12">
						<h1>Change Password</h1>
					</div>
				</div>
				<div class="row">
					<div class="col-sm-6 col-sm-offset-3">
						<input type="password" class="input-lg form-control"
							name="oldpassword1" id="oldpassword" placeholder="Old Password" required="required">
						<br />
						<p id="failure" style="display: none; font-family: cursive;">Invalid
							Old Password</p>

						<input type="password" class="input-lg form-control"
							name="password1" id="password1" placeholder="New Password"
							autocomplete="off" required="required">
						<div class="row">
							<div class="col-sm-6">
								<span id="8char" class="glyphicon glyphicon-remove"
									style="color: #FF0004;"></span>
								<p id="8p">8 Characters Long</p>
								<br> <span id="ucase" class="glyphicon glyphicon-remove"
									style="color: #FF0004;"></span>
								<p id="8u">One Uppercase Letter</p>
							</div>
							<div class="col-sm-6">
								<span id="lcase" class="glyphicon glyphicon-remove"
									style="color: #FF0004;"></span>
								<p id="8l">One Lowercase Letter</p>
								<br> <span id="num" class="glyphicon glyphicon-remove"
									style="color: #FF0004;"></span>
								<p id="8n">One Number
							</div>
						</div>
						<input type="password" class="input-lg form-control"
							name="password2" id="password2" placeholder="Repeat Password"
							autocomplete="off" required="required">
						<div class="row">
							<div class="col-sm-12">
								<span id="pwmatch" class="glyphicon glyphicon-remove"
									style="color: #FF0004;"></span>
								<p id="pass">Passwords Match</p>
							</div>
						</div>
						<p id="success" style="display: none; font-family: cursive;">Password
							updated Successfully</p>
						<input type="button"
							class="col-xs-12 btn btn-primary btn-load btn-lg" id="change1"
							data-loading-text="Changing Password..." value="Change Password"
							onclick="update()">
					</div>
					<!--/col-sm-6-->
				</div>
				<div class="login" id="login"
					style="display: none; padding-left: 100px;">
					<form action="logout" method="post">
						<p id="success" style="display: none; font-family: cursive;">Password
							updated Successfully</p>
						<input type="submit"
							class="col-xs-6 btn btn-primary btn-load btn-lg"
							value="Go To Login Page">
					</form>
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
					var oldPassword = $("#oldpassword").val();
					var newPassword = $("#password2").val();

					$.ajax({
						type : "POST",
						url : "updatePassword",
						data : "oldPassword=" + oldPassword + "&newPassword="
								+ newPassword,
						success : function(response) {
							if (response == "success") {
								$('#success').show();
								$("#success").css("color", "#00FF00");
								$('#failure').hide();
								$('#oldpassword').removeClass();
								$('#oldpassword').hide();
								$('#password1').hide();
								$('#password2').hide();
								$('#change1').hide();
								$("#8char").hide();
								$("#pwmatch").hide();
								$("#num").hide();
								$("#lcase").hide();
								$("#ucase").hide();
								$('#login').show();
								$("#8p").hide();
								$("#8u").hide();
								$("#8l").hide();
								$("#8n").hide();
								$("#pass").hide();
								$('#deposit').bind('click', false);
								$('#dashboard').bind('click', false);
								$('#withdraw').bind('click', false);
								$('#sub-menu-trans1').bind('click', false);
								$('#sub-menu-trans').bind('click', false);
								$('#sub-menu-transfer').bind('click', false);
								$('#sub-menu-transfer1').bind('click', false);
								$('#sub-menu-transfer2').bind('click', false);
								$('#sub-menu-transfer3').bind('click', false);
								$('#bill').bind('click', false);
								$('#profile').bind('click', false);
								$('#changepass').bind('click', false);
								$('#contact').bind('click', false);
							} else if (response == "failure") {
								$('#oldpassword').addClass("ui-state-error");
								$("#failure").css("color", "#FF0000");
								$('#oldpassword').val("");
								$('#failure').show();
							}
						}
					});
				}
			</script>
		</div>
	</div>
</body>
</html>
