<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page session="false"%>
<html lang="en">

<head>

<title>Contact Us</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />

<link rel="stylesheet" type="text/css"
	href="resources/css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css"
	href="resources/css/font-awesome.min.css" />

<script type="text/javascript" src="resources/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="resources/js/bootstrap.min.js"></script>
<style>
.header {
	color: #36A0FF;
	font-size: 27px;
	padding: 10px;
}

.bigicon {
	font-size: 35px;
	color: #36A0FF;
}
</style>
<link href="resources/css/bootstrap.min.css" rel="stylesheet">
<link href="resources/css/simple-sidebar.css" rel="stylesheet">
<script src="resources/js/jquery.js"></script>
<script src="resources/js/bootstrap.min.js"></script>
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
				<li><a href="Dashboard">Dashboard</a></li>
				<li><a href="Deposit">Deposit</a></li>
				<li><a href="Withdraw">Withdraw</a></li>
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
				<li><a href="BillPay">BillPay</a></li>
				<li><a href="Profile">Profile</a></li>
				<li><a href="changePassword">Change password</a></li>
				<li><a href="#">Contact us</a></li>
				<li><a href="logout">Log Out</a></li>
			</ul>
		</div>
		<div id="page-content-wrapper">
			<div class="container">
				<div class="row">
					<div class="col-md-12">
						<div class="well well-sm">
							<form class="form-horizontal" action = "doContact"method="post">
								<fieldset>
									<legend class="text-center header">Hi
										${userProfile.firstName}!!! Contact us</legend>

									<div class="form-group">
										<span class="col-md-1 col-md-offset-2 text-center"><i
											class="fa fa-user bigicon"></i></span>
										<div class="col-md-8">
											<input id="fname" name="name" type="text"
												placeholder="First Name" class="form-control" required="required">
										</div>
									</div>
									<div class="form-group">
										<span class="col-md-1 col-md-offset-2 text-center"><i
											class="fa fa-user bigicon"></i></span>
										<div class="col-md-8">
											<input id="lname" name="name" type="text"
												placeholder="Last Name" class="form-control" required="required">
										</div>
									</div>

									<div class="form-group">
										<span class="col-md-1 col-md-offset-2 text-center"><i
											class="fa fa-envelope-o bigicon"></i></span>
										<div class="col-md-8">
											<input id="email" name="email" type="text"
												placeholder="Email Address" class="form-control" required="required">
										</div>
									</div>

									<div class="form-group">
										<span class="col-md-1 col-md-offset-2 text-center"><i
											class="fa fa-phone-square bigicon"></i></span>
										<div class="col-md-8">
											<input id="phone" name="phone" type="text"
												placeholder="Phone" class="form-control" required="required">
										</div>
									</div>

									<div class="form-group">
										<span class="col-md-1 col-md-offset-2 text-center"><i
											class="fa fa-pencil-square-o bigicon"></i></span>
										<div class="col-md-8">
											<textarea class="form-control" id="message" name="message"
												placeholder="Enter your massage for us here. We will get back to you within 2 business days."
												rows="7" required="required"></textarea>
										</div>
									</div>
									<div class="form-group">
										<div class="col-md-12 text-center">
											<button type="submit" class="btn btn-primary btn-lg">Submit</button>
										</div>
									</div>
								</fieldset>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>