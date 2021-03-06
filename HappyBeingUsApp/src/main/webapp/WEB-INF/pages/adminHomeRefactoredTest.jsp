<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Admin Home Page</title>

<!-- Bootstrap Core CSS -->
<link href="css/bootstrap.min.css" rel="stylesheet">

<!-- jQuery -->
<script src="js/jquery.js"></script>

<!-- Bootstrap Core JavaScript -->
<script src="js/bootstrap.min.js"></script>

<!-- <link rel="stylesheet" -->
<!-- 	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css"> -->
<!-- <script -->
<!-- 	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script> -->
<!-- <script -->
<!-- 	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script> -->
<style>
body {
	counter-reset: section;
}

h6:before {
	counter-increment: section;
	content: counter(section);
}
/* Remove the navbar's default margin-bottom and rounded borders */
.navbar {
	margin-bottom: 0;
	border-radius: 0;
}
/* Add a gray background color and some padding to the footer */
/*footer {
	background-color: #f2f2f2;
	padding: 25px;
}*/
.topic_holder .topic_name:after {
	/* symbol for "opening" panels */
	font-family: 'Glyphicons Halflings';
	/* essential for enabling glyphicon */
	content: "\e114"; /* adjust as needed, taken from bootstrap.css */
	float: right; /* adjust as needed */
	color: grey; /* adjust as needed */
}

.topic_holder .topic_name.collapsed:after {
	/* symbol for "collapsed" panels */
	content: "\e080"; /* adjust as needed, taken from bootstrap.css */
}

.topic-container {
	position: fixed;
	top: 50px;
	width: 100%;
	height: 200px;
	z-index: 50;
}

/* For Circles being Displayed */
/* .btn-circle.btn-xl {
  width: 250px;
  height: 250px;
  padding: 40px 56px;
  font-size: 24px;
  line-height: 1.33;
  border-radius: 35px;
} */
.circlemother{width:200px;text-align:center;height:200px;border-radius:50px;font-size:20px;color:#fff;line-height:100px;text-align:center;background:pink}
.circledaughter{width:200px;text-align:center;height:200px;border-radius:50px;font-size:20px;color:#fff;line-height:100px;text-align:center;background:#BCED91}

</style>


<script type="text/javascript">
	function editContainer(id) {
		console.log("The id fetched is: " + id);
		var form = document.getElementById("editForm");
		form.action = "editActivityContainer.action";
		form.children.namedItem("id").value = id;
		form.submit();
	}
	function renameTopic(button) {
		var topicName = button.name;
		var topicId = button.id;
		$('#renameTopic input[name=renameTopicName]').val(topicName);
		$('#renameTopic input[name=renameTopicId]').val(topicId);
		$("#renameTopic").modal("toggle");
	}

	function addContainer(button) {
		var topicId = button.id.split("-")[1];
		$('#topicId').val(topicId);
	}

	function deleteTopic(deletedTag) {

		var deleteId = deletedTag.id.split("_")[1];
		var topicNotEmpty = $("#topicNotEmpty_" + deleteId).val();

		if (topicNotEmpty == "true") {
			$("#warningDialog").modal("toggle");
		} else {
			var form = document.getElementById("confirmationForm");
			form.action = "deleteTopic.action";
			$("#deletableId").val(deleteId);
			$("#confirmationDialog").modal("toggle");
		}
	}

	function deleteActivityContainer(deletedTag) {

		var deleteId = deletedTag.id.split("_")[1];
		var form = document.getElementById("confirmationForm");
		form.action = "deleteActivityContainer.action";
		$("#deletableId").val(deleteId);
	}

	$(document).ready(function() {
		// 		Ajax for renaming the topic name
		$("#changeTopicName").click(function() {
			topicName = $('#renameTopic input[name=renameTopicName]').val();
			topicId = $('#renameTopic input[name=renameTopicId]').val();
			$("#loadingDiv").modal("toggle");
			$("#renameTopic").modal("toggle");
			$.ajax({
				type : "POST",
				url : "renameTopic.action",
				data : "topicName=" + topicName + "&topicId=" + topicId,
				success : function(data) {
					$("#loadingDiv").modal("toggle");
					$("#topic_name_" + topicId)[0].innerHTML = topicName;
					$('#' + topicId).attr('name', topicName);
				}
			});
		});

		$('.Topics').on('click', function(e) {

			$(this).parent().addClass('active');
			var id = $(this)[0].id.split('#')[1];
			$('.topiccontentcontainer').each(function() {
				$(this).css("display", "none");
			});
			$('#' + id).css("display", "block");
			e.preventDefault(); // cancel the link itself
		});

		$(".goBack").on("click", function(e) {
			e.preventDefault(); // cancel the link itself
			$("#editForm").attr('action', this.href);
			$("#editForm").submit();
		});
		$(".nav li").click(function() {
			$(".nav li").removeClass('active');
			$(this).addClass('active');
			$('.nav-tabs a:first').tab('show')

		});

		$('.nav-tabs li:first-child a').tab('show');

	});
</script>

</head>
<body>

	<nav class="navbar navbar-inverse navbar-fixed-top">
		<div class="container-fluid">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target="#myNavbar">
					<span class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#" class="goBack">Admin</a>
			</div>
			<div class="collapse navbar-collapse" id="myNavbar">
				<ul class="nav navbar-nav">

					<li><a data-toggle="modal" id="addAdmin" href="#addNewAdmin">Add
							New Admin</a></li>
							
							

				</ul>
				<ul class="nav navbar-nav">
					<li><a href="adminDiagnostic.action">Diagnostic Questions</a></li>
				</ul>
				<ul class="nav navbar-nav navbar-right">
				<li><a><span class="glyphicon glyphicon-user"></span>  Welcome Admin</a></li>
					<li><a href="adminLogout.action"><span
							class="glyphicon glyphicon-log-out"></span> Logout </a></li>
				</ul>
			</div>
		</div>
	</nav>

	<div>
		<div class="jumbotron">
			<div class="container text-center">
				<h1>Modules and Blocks</h1>
				<p>Add-Remove-Edit Modules and Blocks, all at one place.</p>
			</div>
		</div>
	</div>

	<div class="container-fluid bg-3 text-left">

		<div class="row">
			<div class="col-sm-12">


					
				<!-- <button type="button" class="btn btn-default btn-circle btn-xl"></button> -->
				<div class="col-sm-6">
					<div class="circlemother" style= "margin: auto;">
						<a href="mother.action" style="color: black;text-align:center'">Mother Module</a>
					</div>
				</div>

				<div class="col-sm-6">
					<div class="circledaughter" style= "margin: auto;">
						<a href="daughter.action" style="color: black">Daughter Module</a>
					</div>
				</div>

				

				</br> </br>
				
				<div class="modal fade" id="renameTopic" role="dialog">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<h4 class="modal-title">Rename Topic</h4>
							</div>
							<div class="modal-body">
								<input type="text" class="form-control" id="renameTopicName"
									name="renameTopicName" placeholder="Enter new topic name" /> <input
									type="hidden" name="renameTopicId" id="renameTopicId" />
							</div>
							<div class="modal-footer">
								<input type="button" id="changeTopicName"
									class="btn btn-success" role="button" value="Change Name" />
							</div>
						</div>
					</div>
				</div>
				<!-- 			Renaming the topic pop up modal  END-->

				<!-- 			Adding new Topic START -->
				<%-- <div class="modal fade" id="addNewTopic" role="dialog">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<h4 class="modal-title">Add New Topic</h4>
						</div>
						<form action="addNewTopic.action" method="post">
							<div class="modal-body">
								<div class="btn-group">
									<button type="button" class="btn btn-default dropdown-toggle"
										data-toggle="dropdown" aria-haspopup="true"
										aria-expanded="false">
										Select Topic <span class="caret"></span>
									</button>
									<ul class="dropdown-menu">
										<c:forEach items="${topics}" var="topic">
											<li><a href="#">${topic.topicName}</a></li>
										</c:forEach>
										
									</ul>
									</div>

										<!-- <input type="text" class="form-control" id="topicName"
									name="topicName" placeholder="Enter new topic name" required /> -->
								</div>
								<div class="modal-body">
							<c:forEach items="${versions}" var="version">
								<span><input type="checkbox" name="versionIds" value="${version.id}"/> ${version.versionName}</span>
							</c:forEach>
							</div>
							<div class="modal-footer">
								<input type="submit" class="btn btn-success" role="button"
									value="Add" />
							</div>
						</form>
					</div>
				</div>
			</div> --%>
				<!-- 			Adding new Topic END -->

				<!-- Previous way of adding new Topic with Text  -->
				<!-- 			Adding new Topic START -->
				<div class="modal fade" id="addNewTopic" role="dialog">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<h4 class="modal-title">Add New Topic</h4>
							</div>
							<form action="addNewTopic.action" method="post">
								<div class="modal-body">
									<input type="text" class="form-control" id="topicName"
										name="topicName" placeholder="Enter new topic name" required />
								</div>
								
								<div class="modal-footer">
									<input type="submit" class="btn btn-success" role="button"
										value="Add" />
								</div>
							</form>
						</div>
					</div>
				</div>
				<!-- 			Adding new Topic END -->

				<!-- 			Adding new Activity container START -->
				<div class="modal fade" id="addNewContainer" role="dialog">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<h4 class="modal-title">Add New Activity Container</h4>
							</div>
							<form action="addNewActivityContainer.action" method="post">
								<div class="modal-body">
									<input type="text" class="form-control" id="containerName"
										name="containerName"
										placeholder="Enter new Activity container name" required /> <input
										type="hidden" name="topicId" id="topicId" />
								</div>
								<div class="modal-body">
									<c:forEach items="${versions}" var="version">
										<span><input type="checkbox" name="versionIds"
											value="${version.id}" /> ${version.versionName}</span>
									</c:forEach>
								</div>
								<div class="modal-footer">
									<input type="submit" class="btn btn-success" role="button"
										value="Add" />
								</div>
							</form>
						</div>
					</div>
				</div>
				<!-- 			Adding new Activity container END -->

				<!-- 			Confirmation dialog before delete START -->
				<div class="modal fade" id="confirmationDialog" role="dialog">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<h4 class="modal-title">Please confirm!</h4>
							</div>
							<form id="confirmationForm" name="confirmationForm" method="post">
								<div class="modal-body">
									<h4 class="modal-title">Do you really want to remove this?</h4>
									<input type="hidden" class="form-control" id="deletableId"
										name="deletableId" />
								</div>
								<div class="modal-footer">
									<a class="btn btn-default" data-dismiss="modal">No</a> <input
										type="submit" class="btn btn-success" role="button"
										value="Yes" />
								</div>
							</form>
						</div>
					</div>
				</div>
				<!-- 			Confirmation dialog before delete END -->

				<!-- 		Cannot delete warning START -->
				<div class="modal fade" id="warningDialog" role="dialog">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<h4 class="modal-title">No can do!!!</h4>
							</div>
							<div class="modal-body">
								<h4 class="modal-title">It seems that the topic is not
									empty. You cannot delete this topic.</h4>
							</div>
							<div class="modal-footer">
								<a class="btn btn-danger" data-dismiss="modal">OK</a>
							</div>
						</div>
					</div>
				</div>
				<!-- 		Cannot delete warning  END -->



				<!-- 			Loading image Under progress -->
				<div id="loadingDiv" class="modal">
					<img alt="loading" src="Images/loading.gif">
				</div>
			</div>
		</div>
		<form name="editForm" id="editForm" action="#" method="post">
			<input type="hidden" id="id" name="id" value="" />
		</form>

		<div class="container-fluid bg-3 text-right">

		
			<!-- 		Add Admin START -->
			<div class="modal fade" id="addNewAdmin" role="dialog">
				<div class="modal-dialog">
					<div class="modal-content col-lg-10">

						<form:form action="addNewAdmin.action" method="post"
							modelAttribute="userAuthentication">
							<div class="modal-header">
								<h4>Add new Admin</h4>
							</div>

							<div class="modal-body">
								<div class="form-group">
									<div class="col-lg-5">
										<form:input type="text" path="user.firstName" maxlength="80"
											class="form-control" name="Firstname"
											placeholder="First name" required="true" />
									</div>
									<div class="col-lg-5">
										<form:input type="text" path="user.lastName" maxlength="80"
											class="form-control" name="Lastname" placeholder="Last Name"
											required="true" />
									</div>
									<br></br>
								</div>
								<div class="form-group left-inner-addon ">
									<div class="col-lg-10 ">
										<i class="glyphicon glyphicon-envelope"></i>
										<form:input type="email" path="user.email" maxlength="80"
											class="form-control" id="adminEmail" name="emailID"
											placeholder="Email" required="true" />
									</div>
									<span id="adminEmailMsg"></span> <br></br>
								</div>
								<div class="form-group left-inner-addon">
									<div class="col-lg-10">
										<i class="glyphicon glyphicon-user"></i>
										<form:input type="text" path="username" class="form-control"
											maxlength="80" minlength="6" name="userName" id="adminUname"
											placeholder="Username" required="true" />
										<div id="loadingDiv" class="modal">
											<img alt="loading" src="Images/loading.gif">
										</div>
										<span id="usernameMsg"></span>
									</div>
									<br></br>
								</div>
								<div class="form-group left-inner-addon">
									<div class="col-lg-10">
										<i class="glyphicon glyphicon-lock"></i>
										<form:input type="password" path="password"
											class="form-control" maxlength="80" minlength="6"
											name="password" placeholder="password" required="true" />
									</div>
								</div>
								<br></br>
								</br>
								<div class="modal-footer">
									<a class="btn btn-danger" data-dismiss="modal">Cancel</a> <input
										class="btn btn-success" type="submit" value="Register" />

								</div>
							</div>
						</form:form>
					</div>
				</div>
			</div>
			<!-- 		Add Admin  END -->
			<!-- Footer -->
			<%@ include file="footer.jsp"%>
			<%-- 	<jsp:include page="footer.jsp"> --%>

			<!-- jQuery -->
			<script src="js/jquery.js"></script>

			<!-- Bootstrap Core JavaScript -->
			<script src="js/bootstrap.min.js"></script>
			<script>
				$("#adminUname")
						.change(
								function() {
									userName = $('#adminUname').val();
									$("#loadingDiv").modal("toggle");
									$("#usernameMsg")[0].innerHTML = "Checking username availability.";
									$
											.ajax({
												type : "POST",
												url : "checkUsernameAvailability.action",
												data : "userName=" + userName,
												success : function(data) {
													$("#loadingDiv").modal(
															"toggle");
													$("#usernameMsg")[0].innerHTML = data;

													if (data
															.indexOf('available') == -1) {
														$("#adminUname")
																.val('');
													} else {

													}
												}
											})
								});

				$("#newUserName")
						.change(
								function() {
									userName = $('#newUserName').val();
									$("#loadingDiv").modal("toggle");
									$("#newusernameMsg")[0].innerHTML = "Checking username availability.";
									$
											.ajax({
												type : "POST",
												url : "checkUsernameAvailability.action",
												data : "userName=" + userName,
												success : function(data) {
													$("#loadingDiv").modal(
															"toggle");
													$("#newusernameMsg")[0].innerHTML = data;

													if (data
															.indexOf('available') == -1) {
														$("#newUserName").val(
																'');
													} else {

													}
												}
											})
									//			    });

									// 				$("#adminEmail").change(function() {
									// 					email = $('#adminEmail').val();
									// 					$("#loadingDiv").modal("toggle");
									// 					$("#adminEmailMsg")[0].innerHTML = "";
									// 					$.ajax({
									// 			            type : "POST",
									// 			            url : "checkEmailExists.action",
									// 			            data : "email=" + email,
									// 			            success : function(data) {
									// 			            	$("#loadingDiv").modal("toggle");
									// 			            	$("#adminEmailMsg")[0].innerHTML = data;

									// 			            	if(data != ""){
									// 			            		$("#adminEmail").val('');
									// 			            	}else{

									// 			            	}
									// 			            }
									// 			        })
								});
			</script>
</body>
</html>
