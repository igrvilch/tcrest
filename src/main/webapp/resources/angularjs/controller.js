angular.module('coursesApp', []).controller(
		'CoursesController',
		function($scope, $http, $window, $location) {
			$scope.description = {
				text : ""
			};
			var host = $location.host().toString();
			var port = $location.port().toString();
			var path1 = "http://" + host + ":" + port + _contextPath
					+ "/rest/api/user";
			var path2 = "http://" + host + ":" + port + _contextPath
					+ "/rest/api/addcourse";
			var path3 = "http://" + host + ":" + port + _contextPath
					+ "/rest/api/courses";
			
			var userEml;
			$http.get(path1).success(function(response) {
				$scope.userEmail = response.userEmail;
				userEml = response.userEmail;
			});
			$http.get(path3).success(function(response) {
				$scope.courses = response.course;
				var i;
				
				for (i = 0; i < response.course.length; i++) { 
					if ((response.course[i].subscribers == null && response.course[i].attenders == null) || 
							(response.course[i].subscribers != null &&
							response.course[i].subscribers.toString().indexOf(userEml) == -1) ||
							(response.course[i].attenders != null &&
							response.course[i].attenders.toString().indexOf(userEml) == -1)) {
						$scope.courses[i].userCourseButton = "subscribe";
					} else if (response.course[i].subscribers != null &&
							response.course[i].subscribers.toString().indexOf(userEml) > -1) {
						    $scope.courses[i].userCourseButton = "attend";
					}
					 
				}
				
				
			});
			$scope.submitMyForm = function() {
				var data = $scope.coursesPost;
				$http.post(path2, data).success(function(response) {
					$scope.courses.push(response);
				});
			}
		});