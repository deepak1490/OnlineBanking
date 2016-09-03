<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Smart Health Login</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" type="text/css"
	href="resources/css/registration.css">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap-theme.min.css">
<link
	href="//maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css"
	rel="stylesheet">
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
<script>
	function validateForm() {
		var password = document.myForm.password.value;
		var confirmPassword = document.myForm.confirmPassword.value;
		var email = document.myForm.email.value;
		if (password != confirmPassword) {
			document.getElementById("notmatch").innerHTML = "Re-Enter password dosent match";
			return false;
		}

		if (!(/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(email))) {
			document.getElementById("notemail").innerHTML = "Invalid email! Re-Enter Email Id";
			return false;
		}

		return true;
	}

	function reload() {
		location.reload();
	}
</script>
</head>
<body>
	<div class="container">
		<div class="row centered-form">
			<div
				class="col-xs-12 col-sm-8 col-md-8 col-sm-offset-2 col-md-offset-2">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">User Registration</h3>
					</div>
					<div class="panel-body">
						<form:form action="userRegistration" method="POST"
							commandName="user" role="form" name="myForm"
							onsubmit="return(validateForm())">
							<div class="row">
								<div class="col-xs-6 col-sm-6 col-md-6">
									<div class="form-group">
										<form:input path="userProfile.firstName" name="first_name" id="first_name"
											class="form-control input-sm" placeholder="First Name"  required="required" />
										<form:errors path="userProfile.firstName" cssStyle="color:#ff0000" />
									</div>
								</div>
								<div class="col-xs-6 col-sm-6 col-md-6">
									<div class="form-group">
										<form:input path="userProfile.lastName" type="text" name="last_name"
											id="last_name" class="form-control input-sm"
											placeholder="Last Name"  required="required"/>
										<form:errors path="userProfile.lastName" cssStyle="color:#ff0000" />
									</div>
								</div>
							</div>

							<div class="form-group">
								<form:input path="userName" type="text" name="username"
									class="form-control input-sm" placeholder="User Name"  required="required"/>
								<form:errors path="userName" cssStyle="color:#ff0000" />
							</div>
							<div id="username"
								style="color: red; font-weight: bole; text-align: center; margin-bottom: 10px;">${error}</div>
							<div class="row">
								<div class="col-xs-6 col-sm-6 col-md-6">
									<div class="form-group">
										<form:password path="userPassword" name="password"
											id="password" class="form-control input-sm"
											placeholder="Password"  required="required" />
										<form:errors path="userPassword" cssStyle="color:#ff0000" />
									</div>
								</div>
								<div class="col-xs-6 col-sm-6 col-md-6">
									<div class="form-group">
										<input type="password" name="confirmPassword"
											class="form-control input-sm" placeholder="Confirm Password" required>
									</div>
								</div>
								<div id="notmatch"
									style="color: red; font-weight: bole; text-align: center; margin-bottom: 10px;"></div>
							</div>

							<div class="row">
								<div class="col-xs-6 col-sm-6 col-md-6">
									<div class="form-group">
										<form:input path="userProfile.email" name="email"
											class="form-control input-sm" placeholder="Email Address"  required="required"/>
										<form:errors path="userProfile.email" cssStyle="color:#ff0000" />
									</div>
									<div id="notemail"
										style="color: red; font-weight: bole; text-align: left; margin-bottom: 10px;"></div>
								</div>
								<div class="col-xs-6 col-sm-6 col-md-6">
									<div class="form-group">
										<form:input path="userProfile.mobileNumber" type="number" name="Mobile"
											class="form-control input-sm" placeholder="Mobile Number"  required="required" />
										<form:errors path="userProfile.mobileNumber" cssStyle="color:#ff0000" />
									</div>
								</div>
							</div>
							
							<div class="form-group">
			    				<form:input path="userProfile.address" name="address" class="form-control input-sm" placeholder="Address"  required="required"/>
			    				<form:errors path="userProfile.address" cssStyle="color:#ff0000"/>
			    			</div>
			    			
			    			<div class="form-group">
			    				<form:input path="account.accountNumber" name="accno" class="form-control input-sm" placeholder="Account Number"  required="required" />
			    				<form:errors path="account.accountNumber" cssStyle="color:#ff0000"/>
			    			</div>
			    			
			    			<div class="form-group">
			    				<form:input path="account.balance" name="balnce" class="form-control input-sm" placeholder="Initial Balance"  required="required"/>
			    				<form:errors path="account.balance" cssStyle="color:#ff0000"/>
			    			</div>
			    			
			    			<div class="form-group">
			    				<form:input path="account.pin" name="pin" class="form-control input-sm" placeholder="Account Pin"   required="required"/>
			    				<form:errors path="account.pin" cssStyle="color:#ff0000"/>
			    			</div>
						
							<input type="submit" value="Register"
								class="btn btn-info btn-block">
							<br />

						</form:form>
						<input type="submit" value="Clear Form"
							class="btn btn-info btn-block" onclick="reload()">
					</div>
				</div>
			</div>
		</div>
	</div>

</body>
</html>