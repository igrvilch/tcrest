<!DOCTYPE html>
<html lang="en" ng-app="coursesApp">
<head>
<meta charset="utf-8" />
<link rel="stylesheet" type="text/css" href="resources/css/style.css" />
<script type="text/javascript">
	var _contextPath = "${pageContext.request.contextPath}";
</script>
<script
	src="http://ajax.googleapis.com/ajax/libs/angularjs/1.3.14/angular.min.js"></script>
	<script src="//ajax.googleapis.com/ajax/libs/angularjs/1.2.16/angular-resource.min.js"></script>
<script src="resources/angularjs/controller.js"></script>
</head>
<body ng-controller="CoursesController">
	<div class="container">
		<header>
			<h1>
				Courses
				<div class="logout">
					<span id="currentUserLogin"> {{ userEmail }} </span> <a
						href="logout.html"> <i class="icon-off"></i>
					</a>
				</div>
			</h1>
		</header>
		<div class="courses-top-control">
			<a class="btn" href="mycourses.html"></a> <a
				id="createCourseButton" class="btn btn-primary" href="create.html">Create</a>
			<div class="search-box">
				<form class="form-search">
					<select class="span3">
						<option>All</option>
						<option>Project Management 101</option>
						<option>NET Technology</option>
					</select>
					<button class="btn" type="submit">Apply</button>
				</form>
			</div>
		</div>
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
			<tbody ng-repeat="x in courses">
				<tr>
					<td>{{ x.courseId }}</td>
					<td><a href="detail.html">{{ x.courseName }}</a> <span
						class="label">{{ x.courseState }}</span></td>
					<td>{{ x.courseCategory.categoryName }}</td>
					<td></td>
					<td></td>
					<td><a ng-hide="hideOption" class="btn btn-mini" ng-click="submitMyForm()">{{ x.userCourseButton }}</a></td>
				</tr>
			</tbody>
		</table>
	</div>
</body>
</html>
