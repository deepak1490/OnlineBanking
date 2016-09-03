<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page session="false"%>
<html lang="en">

<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<title></title>
<link href="resources/css/bootstrap.min.css" rel="stylesheet">
<link href="resources/css/simple-sidebar.css" rel="stylesheet">
<script src="resources/js/jquery.js"></script>
<script src="resources/js/bootstrap.min.js"></script>
<meta name="robots" content="noindex, nofollow" />

<link rel="stylesheet" href="resources/css/showhide.css" />

<link rel="stylesheet" href="resources/css/tooltip.css" />

<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
<script src="resources/js/showhide.js"></script>
<script src=resources/js/tooltip.js></script>
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css" />
<script>
	function update() {
		var oldMobile = $("#oldmobile").val();
		var newMobile = $("#newmobile").val();
		$.ajax({
			type : "POST",
			url : "updateMobile",
			data : "oldMobile=" + oldMobile + "&newMobile=" + newMobile,
			success : function(response) {
				if (response == "success") {
					$('#success').show();
					$("#success").css("color", "#00FF00");
					$('#failure').hide();
					$('#oldmobile').removeClass();
				} else if (response == "failure") {

					$('#oldmobile').addClass("ui-state-error");
					$('#oldmobile').val("");
					$('#failure').show();
				}
			}
		});
	}
	function updateEmail() {
		var oldEmail = $("#oldEmail").val();
		var newEmail = $("#newEmail").val();
		$.ajax({
			type : "POST",
			url : "updateEmail",
			data : "oldEmail=" + oldEmail + "&newEmail=" + newEmail,
			success : function(response) {
				if (response == "success") {
					$('#success1').show();
					$("#success1").css("color", "#00FF00");
					$('#failure1').hide();
					$('#oldEmail').removeClass();
				} else if (response == "failure") {

					$('#oldEmail').addClass("ui-state-error");
					$('#oldEmail').val("");
					$('#failure1').show();
				}
			}
		});
	}
</script>
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
				<li><a href="#">Profile</a></li>
				<li><a href="changePassword">Change password</a></li>
				<li><a href="ContactUs">Contact us</a></li>
				<li><a href="logout">Log Out</a></li>
			</ul>
		</div>
		<div id="main">
			<div id="page-content-wrapper">
				<div id="first">
					<form action="" method="post">
						<h3>Profile Details.</h3>
						<img id="divider" src="resources/images/divider.png "> <input
							type="text" id="firstName" name="firstName" title="First Name"
							value="${userProfile.firstName}" readonly="readonly" /> <input
							type="text" id="lastName" name="lastName" title="Last Name"
							value="${userProfile.lastName}" readonly="readonly" /> <input
							type="text" id="email" name="email" title="Email"
							value="${userProfile.email}" readonly="readonly" /> <input
							type="text" id="mobile" name="mobile" title="Mobile Number"
							value="${userProfile.mobileNumber}" readonly="readonly" />
						<textarea id="address" name="address" title="Address"
							readonly="readonly"><c:out
								value="${userProfile.address}" /></textarea>
						<input type="button" id="updateAddress" value="Update Address" />
						<input type="button" id="updateMobile"
							value="Update Mobile Number" /> <input type="button"
							id="updateEmail" value="Update Email ID" />
					</form>
				</div>


				<div id="second">
					<form action="updateAddress" method="post" id="form">
						<h3>Update Address</h3>
						<input type="text" name="address" id="address"
							placeholder="New Address" /> <input type="submit"
							id="updateAddress1" value="Update Address" required="required" />
						<p id="two">
							<a href="#" id="profile" class="profile">Back to Profile
								Details</a>
						</p>
					</form>
				</div>
				<div id="third">

					<h3>Update Mobile Number</h3>
					<input type="text" name="oldmobile" id="oldmobile"
						placeholder="Old Mobile Number" required="required" />
					<p id="failure">Invalid Old Number</p>
					<input type="text" name="newmobile" id="newmobile"
						placeholder="New Mobile Number" required="required"/>
					<p id="success">Mobile number updated Successfully</p>
					<input type="button" id="updateMobile" value="Update Mobile"
						onclick="update()" />
					<p id="two">
						<a href="Profile" id="profile1" class="profile">Back to
							Profile Details</a>
					</p>

				</div>
				<div id="fourth">
					<h3>Update Email ID</h3>
					<input type="email" name="oldEmail" id="oldEmail"
						placeholder="Old Email ID" required="required" />
					<p id="failure1">Invalid Old EmailID</p>
					<input type="email" name="newEmail" id="newEmail"
						placeholder="New Email ID" required="required"/>
					<p id="success1">Email ID updated Successfully</p>
					<input type="button" id="updateEmail" value="Update Email"
						onclick="updateEmail()" />
					<p id="two">
						<a href="Profile" id="profile2" class="profile">Back to
							Profile Details</a>
					</p>
				</div>
			</div>
		</div>
	</div>
</body>

</html>