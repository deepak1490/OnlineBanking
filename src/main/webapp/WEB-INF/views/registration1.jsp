<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
<!-- Optional theme -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap-theme.min.css">
<!-- Latest compiled and minified JavaScript -->
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js">
	
</script>
<link href="<c:url value="/resources/css/login.css" />" rel="stylesheet">
<html>
<head>
<script type="text/javascript">
	window.history.forward();
	function noBack() {
		window.history.forward();
	}
</script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script>
	$(document)
			.ready(
					function() {
						$("#submit").prop("disabled", true);
						$("#username")
								.keyup(
										function() {
											var username = $("#username").val();
											$
													.ajax({
														type : 'GET',
														data : 'username='
																+ username,
														url : 'checkAvailability',
														success : function(
																response) {
															if (response == "0") {
																$("#msg")
																		.html(
																				"Username is available");
																$("#msg")
																		.css(
																				"color",
																				"green");
															} else {
																$("#msg")
																		.html(
																				"Username is not available");
																$("#msg")
																		.css(
																				"color",
																				"red");
																$("#submit")
																		.prop(
																				"disabled",
																				true);
															}
														}
													});
										});
						$("#accno")
						.keyup(
								function() {
									var accno = $("#accno").val();
									$
											.ajax({
												type : 'GET',
												data : 'accno='
														+ accno,
												url : 'checkAccAvailability',
												success : function(
														response) {
													if (response == "0") {
														$("#msg3")
																.html(
																		"Account Number is available");
														$("#msg3")
																.css(
																		"color",
																		"green");
													} else {
														$("#msg3")
																.html(
																		"Account number is not available");
														$("#msg3")
																.css(
																		"color",
																		"red");
													}
												}
											});
								});
						$("#registration").submit(
								function() {
									var first = $("#first").val();
									var last = $("#last").val();
									var accno = $("#accno").val();
									var mail = $("#mail").val();
									var mobile = $("#mobile").val();
									var bal = $("#bal").val();
									var add = $("#add").val();
									var pin = $("#pin").val();
									if (first == "" || last == ""
											|| accno == "" || bal == ""
											|| pin == "" || add == ""
											|| mail == "" || mobile == "") {
										alert('Fill all the required fields');
										return false;
									}
								});
						$("#mobile").keyup(function() {
							if ($("#mobile").val().length == 10) {
								$("#msg1").html("Mobile number is vaild");
								$("#msg1").css("color", "green");
							} else {
								$("#msg1").html("Invalid Mobile Number");
								$("#msg1").css("color", "red");
							}

						});
						$("#accno")
								.keyup(
										function() {
											if ($("#accno").val().length == 6) {
												$("#msg2")
														.html(
																"Account number is vaild");
												$("#msg2")
														.css("color", "green");
												
											} else {
												$("#msg2")
														.html(
																"Invalid Account Number. It should be of six digits");
												$("#msg2").css("color", "red");
											}

										});
						$("input[type=password]")
								.keyup(
										function() {
											var ucase = new RegExp("[A-Z]+");
											var lcase = new RegExp("[a-z]+");
											var num = new RegExp("[0-9]+");

											if ($("#p1").val().length >= 8) {
												$("#8char").removeClass(
														"glyphicon-remove");
												$("#8char").addClass(
														"glyphicon-ok");
												$("#8char").css("color",
														"#00A41E");
											} else {
												$("#8char").removeClass(
														"glyphicon-ok");
												$("#8char").addClass(
														"glyphicon-remove");
												$("#8char").css("color",
														"#FF0004");
											}

											if (ucase.test($("#p1")
													.val())) {
												$("#ucase").removeClass(
														"glyphicon-remove");
												$("#ucase").addClass(
														"glyphicon-ok");
												$("#ucase").css("color",
														"#00A41E");
											} else {
												$("#ucase").removeClass(
														"glyphicon-ok");
												$("#ucase").addClass(
														"glyphicon-remove");
												$("#ucase").css("color",
														"#FF0004");
											}

											if (lcase.test($("#p1")
													.val())) {
												$("#lcase").removeClass(
														"glyphicon-remove");
												$("#lcase").addClass(
														"glyphicon-ok");
												$("#lcase").css("color",
														"#00A41E");
											} else {
												$("#lcase").removeClass(
														"glyphicon-ok");
												$("#lcase").addClass(
														"glyphicon-remove");
												$("#lcase").css("color",
														"#FF0004");
											}

											if (num.test($("#p1").val())) {
												$("#num").removeClass(
														"glyphicon-remove");
												$("#num").addClass(
														"glyphicon-ok");
												$("#num").css("color",
														"#00A41E");
											} else {
												$("#num").removeClass(
														"glyphicon-ok");
												$("#num").addClass(
														"glyphicon-remove");
												$("#num").css("color",
														"#FF0004");
											}

											if ($("#p1").val() == $(
													"#p2").val()) {
												$("#pwmatch").removeClass(
														"glyphicon-remove");
												$("#pwmatch").addClass(
														"glyphicon-ok");
												$("#pwmatch").css("color",
														"#00A41E");
												$("#submit").prop("disabled", false);
											} else {
												$("#pwmatch").removeClass(
														"glyphicon-ok");
												$("#pwmatch").addClass(
														"glyphicon-remove");
												$("#pwmatch").css("color",
														"#FF0004");
												$("#submit").prop("disabled", true);
											}
										});
					});
</script>
<title>User Registration</title>
</head>
<body>
	<div class="container-fluid">
		<section class="container">
			<div class="container-page">
			<div class="login" id="login" style="float: right; padding-top: 20px;">
					<form action="logout" method="post">
						<input type="submit"
							class="col-xs-12 btn btn-primary btn-load btn-lg"
							value="Logout">
					</form>
				</div>
				<div class="col-md-6">
					<form:form method="POST" commandName="user"
						action="userRegistration" id="registration">
						<h1 class="dark-grey">Customer Registration</h1>
						<div class="form-group col-md-12">
							<label>Username</label>
							<form:input type="text" path="userName" id="username"
								class="form-control" placeholder="Username" />
						</div>

						<div class="form-group col-md-12" id="msg"></div>

						<div class="form-group col-md-12">
							<label>Password</label>
							<form:input type="password" name="password" path="userPassword"
								class="form-control" placeholder="Password" id="p1"  />
						</div>
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
						<div class="form-group col-md-6">
							<label>Repeat Password</label> <input type="password"
								class="form-control" placeholder="Confirm Password" id="p2"  />
						</div>
						<div class="row">
							<div class="col-sm-12">
								<span id="pwmatch" class="glyphicon glyphicon-remove"
									style="color: #FF0004;"></span> Passwords Match
							</div>
						</div>
						<div class="form-group col-md-12" id="cnfpwd"></div>
						<div class="form-group col-md-6">
							<label>First Name</label>
							<form:input type="text" path="userProfile.firstName"
								class="form-control" placeholder="First Name" id="first" />
						</div>
						<div class="form-group col-md-6">
							<label>Last Name</label>
							<form:input type="text" path="userProfile.lastName"
								class="form-control" placeholder="Last Name" id="last"  />
						</div>
						<div class="form-group col-md-12">
							<label>E-mail</label>
							<form:input type="email" path="userProfile.email"
								class="form-control" placeholder="E-mail" id="mail"  />
						</div>

						<div class="form-group col-md-12">
							<label>Mobile Number</label>
							<form:input type="number" pattern="\d{3}[\-]\d{3}[\-]\d{4}"
								path="userProfile.mobileNumber" class="form-control"
								placeholder="Mobile Number" id="mobile" />
						</div>

						<div class="form-group col-md-12" id="msg1"></div>

						<div class="form-group col-md-12">
							<label>Address</label>
							<form:input type="text" path="userProfile.address"
								class="form-control" placeholder="Address" id="add"  />
						</div>
						<div class="form-group col-md-5">
							<label>Account number</label>
							<form:input type="number" path="account.accountNumber"
								class="form-control" placeholder="Account Number" id="accno" />
						</div>
						<div class="form-group col-md-7" id="msg3"></div>
						<div class="form-group col-md-7" id="msg2"></div>
						<div class="form-group col-md-4">
							<label>Account Balance</label>
							<form:input type="number" path="account.balance"
								class="form-control" placeholder="Account Balance" id="bal"  />
						</div>
						<div class="form-group col-md-3">
							<label>Account Pin</label>
							<form:input type="number" path="account.pin" class="form-control"
								placeholder="Account Pin" id="pin"  />
						</div>
						<div class="form-group col-md-6">
							<button type="submit" class="btn btn-primary" id="submit">Submit</button>
						</div>
						<div class="form-group col-md-6" style="float: right;">
							<button type="reset" class="btn btn-primary">Clear</button>
						</div>
					</form:form>
				</div>
			</div>
		</section>
	</div>
</body>
</html>