
<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page session="false"%>
<html lang="en">

<head>

<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<title></title>
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
						<a href="#">Add Payee</a>
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
				<li><a href="ContactUs">Contact us</a></li>
				<li><a href="logout">Log Out</a></li>
			</ul>
		</div>
		<div id="page-content-wrapper">
			<form method="post" action="doAddPayee">

				<table width="70%" border="0" align="center" cellpadding="2"
					cellspacing="2">
					<tr>
						<th colspan="3" bgcolor="#333333" scope="col"><font
							color="#FFFFFF">Add Payee</font></th>
					</tr>

					<tr>
						<td width="39%">&nbsp;</td>
						<td width="3%">&nbsp;</td>
						<td width="58%">&nbsp;</td>
					</tr>

					<tr>

						<td><div align="right">Payee Name</div></td>
						<td>:</td>
						<td><input name="payeeUserName" type="text" id="uname"
							required /></td>
					</tr>
					<tr>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td><div align="right">Payee Account Number</div></td>
						<td>:</td>
						<td><input name="payeeAccountNumber" type="number" id="accno"
							required /></td>
					</tr>
					<tr>
						<td width="39%">&nbsp;</td>
						<td width="3%">&nbsp;</td>
						<td width="58%">&nbsp;</td>
					</tr>
					<tr>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td><label> <input type="hidden" name="user"
								value="${user.userName }" /> 
								<input name="Submit" type="submit"
								value="Add Payee" />
						</label></td>
					</tr>
					<tr>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td colspan="3" bgcolor="#333333">&nbsp;</td>
					</tr>

				</table>
			</form>
		</div>
	</div>
</body>
</html>