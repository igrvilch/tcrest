<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8" />
<link rel="stylesheet" type="text/css" href="resources/css/style.css" />
<link rel="stylesheet" type="text/css" href="resources/css/app.css" />
<script type="text/javascript">
	var _contextPath = "${pageContext.request.contextPath}";
</script>
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="resources/js/app2.js"></script>
</head>
<body>
	<div class="container">
		<header>
			<h1>
				Courses
				<div class="logout">
					<span id="currentUserLogin"></span> <a href="logout.html"> <i
						class="icon-off"></i>
					</a>
				</div>
			</h1>
		</header>
		<div id="btnCreate">
			<a id="createCourseButton" class="btn btn-primary"
				onclick="popUpCreateShow()">Create</a>
		</div>
		<br>
		<table class="table table-striped table-bordered">
			<thead>
				<tr>
					<th class="span1">Id</th>
					<th>Course</th>
					<th class="span3">Category</th>
					<th class="span1">S/A</th>
					<th class="span1">Grade</th>
					<th class="span2">Action</th>
				</tr>
			</thead>
			<tbody>
			</tbody>
		</table>
	</div>
	<div id="detail" class="popup">
		<div class="container">
			<header>
				<h1>
					Course Details
					<div class="logout">
						<span id="currentUserLogin"></span> <a href="logout.html"> <i
							class="icon-off"></i>
						</a>
					</div>
				</h1>
			</header>
			<div class="form-horizontal">
				<div class="control-group">
					<div class="control-label">Name</div>
					<div id="titleField" class="controls text"></div>
				</div>
				<div class="control-group">
					<div class="control-label">Lecturer</div>
					<div id="ownerField" class="controls text"></div>
				</div>
				<div class="control-group">
					<div class="control-label">Category</div>
					<div id="categoryField" class="controls text"></div>
				</div>
				<div class="control-group">
					<div class="control-label">Description</div>
					<div id="descriptionField" class="controls text"></div>
				</div>
				<div class="control-group">
					<div class="control-label">Links</div>
					<div id="linksField" class="controls text"></div>
				</div>
				<div>
					<a class="btn" onclick="popUpHide()">Back</a>
				</div>
			</div>
		</div>
	</div>
	<div id="dialog" class="popup">
		<div class="container">
			<header>
				<h1>
					<span id="currentDialogName"></span>
					<div class="logout">
						<span id="currentUserLoginDialog"></span> <a href="logout.html">
							<i class="icon-off"></i>
						</a>
					</div>
				</h1>
			</header>
			<form class="form-horizontal">
				<fieldset>
					<div class="control-group">
						<div class="control-label">Course</div>
						<div id="titleFieldDialog" class="controls text"></div>
					</div>
					<div class="control-group">
						<div class="control-label">Lecturer</div>
						<div id="ownerFieldDialog" class="controls text">hi</div>
					</div>
					<div>
						<button id="dialogButton" class="btn btn-success" type="submit"></button>
						<a class="btn" onclick="popUpDialogHide()">Cancel</a>
					</div>
				</fieldset>
			</form>
		</div>
	</div>
	<div class="popup" id="createPopUp">
		<header>
			<h1>
				Create Course
				<div class="logout">
					<span id="currentUserLogin"> </span> <a href="logout.html"> <i
						class="icon-off"></i>
					</a>
				</div>
			</h1>
		</header>
		<form class="form-horizontal">
			<fieldset>
				<div class="control-group">
					<label class="control-label">Name</label>
					<div class="controls">
						<input id="courseName" class="span5" type="text" />
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">Category</label>
					<div class="controls">
						<select id="courseCategory" class="span5">


						</select>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">Description</label>
					<div class="controls">
						<textarea id="courseDescription" class="span5" rows="3"></textarea>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">Links</label>
					<div class="controls">
						<textarea id="courseLinks" class="span5" rows="3"></textarea>
					</div>
				</div>

			</fieldset>
		</form>
		<div class="form-actions">
			<button id="createButton" class="btn btn-primary">Create</button>
			<a class="btn" onclick="popUpCreateHide()">Cancel</a>
		</div>
	</div>
	<div class="popup" id="updatePopUp">
		<header>
			<h1>
				Update Course
				<div class="logout">
					<span id="currentUserLogin"> </span> <a href="logout.html"> <i
						class="icon-off"></i>
					</a>
				</div>
			</h1>
		</header>
		<form class="form-horizontal">
			<fieldset>
				<div class="control-group">
					<label class="control-label">Name</label>
					<div class="controls">
						<input id="courseNameUpdate" class="span5" type="text"
							 />
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">Category</label>
					<div class="controls">
						<select id="courseCategoryUpdate" class="span5">
							
						</select>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">Description</label>
					<div class="controls">
						<textarea id="courseDescriptionUpdate" class="span5" rows="3"></textarea>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">Links</label>
					<div class="controls">
						<textarea id="courseLinksUpdate" class="span5" rows="3"></textarea>
					</div>
				</div>
				
			</fieldset>
		</form>
		<div class="form-actions">
					<button id="updateButton" class="btn btn-primary" type="submit">Update</button>
					<button id="reviewButton" class="btn btn-warning" type="submit">Review</button>
					<a class="btn" onclick="popUpUpdateHide()">Cancel</a>
				</div>
	</div>
</body>
</html>
