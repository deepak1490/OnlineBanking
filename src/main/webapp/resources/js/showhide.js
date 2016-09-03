$(document)
		.ready(
				function() {
					// on click SignIn Button checks for valid email and all
					// field should be filled
					$("#login")
							.click(
									function() {
										var email = new RegExp(
												/^[+a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/i);

										if ($("#loginemail").val() == ''
												|| $("#loginpassword").val() == '') {
											alert("Please fill all fields...!!!!!!");
										}

										else if (!($("#loginemail").val())
												.match(email)) {
											alert("Please enter valid Email...!!!!!!");
										}

										else {
											alert("You have successfully Logged in...!!!!!!");
											$("form")[0].reset();
										}

									});

					$("#register")
							.click(
									function() {
										var email = new RegExp(
												/^[+a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/i);

										if ($("#name").val() == ''
												|| $("#registeremail").val() == ''
												|| $("#registerpassword").val() == ''
												|| $("#contact").val() == '') {
											alert("Please fill all fields...!!!!!!");
										}

										else if (!($("#registeremail").val())
												.match(email)) {
											alert("Please enter valid Email...!!!!!!");
										}

										else {
											alert("You have successfully Sign Up, Now you can login...!!!!!!");
											$("#form")[0].reset();
											$("#second").slideUp(
													"slow",
													function() {
														$("#first").slideDown(
																"slow");
													});
										}

									});

					$("#updateAddress").click(function() {
						$("#first").slideUp("slow", function() {
							$("#second").slideDown("slow");
						});
					});

					$("#profile").click(function() {
						$("#second").slideUp("slow", function() {
							$("#first").slideDown("slow");
						});
					});

					$("#updateMobile").click(function() {
						$("#first").slideUp("slow", function() {
							$("#success").hide();
							$("#failure").hide();
							$('#oldmobile').removeClass();
							$('#oldmobile').val("");
							$('#newmobile').val("");
							$("#third").slideDown("slow");

						});
					});

					$("#profile1").click(function() {
						$("#third").slideUp("slow", function() {
							$("#first").slideDown("slow");
						});
					});

					$("#updateEmail").click(function() {
						$("#first").slideUp("slow", function() {
							$("#success1").hide();
							$("#failure1").hide();
							$('#oldEmail').removeClass();
							$('#oldEmail').val("");
							$('#newEmail').val("");
							$("#fourth").slideDown("slow");
						});
					});

					$("#profile2").click(function() {
						$("#fourth").slideUp("slow", function() {
							$("#first").slideDown("slow");
						});
					});

					$("#firstName[title]").qtip();
					$("#lastName[title]").qtip();
					$("#email[title]").qtip();
					$("#mobile[title]").qtip();
					$("#address[title]").qtip();
				});