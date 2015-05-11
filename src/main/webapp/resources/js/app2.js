	var course;
	var user;
	var subscribers = "";
	var categories;
	var isAfterCreate = false;

	$(document).ready(
		function() {

			$.getJSON("rest/user", function(result) {
				user = result;		
			});
			
			$.getJSON("rest/categories", function(result) {
				categories = result.category;
				
			});

			popUpHide();
			popUpDialogHide();
			popUpCreateHide();
			popUpUpdateHide();
			getCourses();
			
});
			
			
			function getCourses() {

				$.getJSON("rest/courses", function(result) {
				if (user.userRole == 'USER') {
					$("#btnCreate").show().hide();
				}

				$("#currentUserLogin").text(user.userEmail);



				$.each(result.course, function(index, value) {
					

					if (user.userRole == 'USER' &&  
						(value.subscribers == null ||
							value.subscribers.toString().indexOf(user.userEmail) == -1) && (value.attenders == null ||
							value.attenders.toString().indexOf(user.userEmail) == -1) && (value.evaluators == null ||
							value.evaluators.toString().indexOf(user.userEmail) == -1) && (value.courseState == 'NEW' || value.courseState == 'OPEN' 
							|| value.courseState == 'READY'))
					{
						var button = '<a class="btn btn-mini" onclick="popUpDialogShow(\'Subscribe\', ' + index + ')">Subscribe</a>';

					} else if (user.userRole == 'USER' && result.course[index].subscribers != null && 
						result.course[index].subscribers.toString().indexOf(user.userEmail) > -1 && (value.courseState == 'OPEN' 
							|| value.courseState == 'READY')) {

						var button = '<a class="btn btn-mini" onclick="popUpDialogShow(\'Attend\', ' + index + ')">Attend</a>';

					} else if (user.userRole == 'LECTOR' && value.courseOwner.userEmail == user.userEmail && value.courseState == 'DRAFT') {
						
						var button = '<a class="btn btn-mini" onclick="popUpUpdateShow('+ index + ')">Update</a>';
						
					}
					else {

						button ='';
					}

					if(!isAfterCreate) {
						if((user.userRole == "USER" && value.courseState != 'DRAFT') || ((user.userRole == "LECTOR" && value.courseOwner.userEmail == user.userEmail)
								|| (user.userRole == "LECTOR" && value.courseOwner.userEmail != user.userEmail && (value.courseState == 'NEW' || value.courseState == 'OPEN' || 
										value.courseState == 'READY' || value.courseState == 'INPROGRESS' || value.courseState == 'FINISHED')))) {
					$("tbody").append(
						'<tr>' + '<td>' + value.courseId + '</td>'
						+ '<td><a onclick="popUpShow(' + index
							+ ')">' + value.courseName + ' </a> <span class="label"> ' + value.courseState + '</span></td>'
					+ '<td>'
					+ value.courseCategory.categoryName
					+ '</td>' + '<td>' + '</td>' + '<td>'
					+ '</td>' + '<td>' + button + '</td>' + '</tr>');
					}}
					course = result.course;
				});

});
			}

	function popUpShow(index) {
		$("#titleField").text(course[index].courseName);
		$("#ownerField").text(course[index].courseCreator);
		$("#categoryField").text(course[index].courseCategory.categoryName);
		$("#descriptionField").text(course[index].courseDescription);
		$("#linksField").text(course[index].courseLinks);
		$("#detail").show();
	}

	function popUpHide() {
		$("#detail").hide();
	}

	function popUpDialogShow(x, index) {
		$("#dialogButton").attr("onclick", "goCourse(" + course[index].courseId + ", '" + x + "')");
		if(course[index].subscribers != null) {
			subscribers = course[index].subscribers;
		}
		$("#titleFieldDialog").text(course[index].courseName);
		$("#currentUserLoginDialog").text(user.userEmail);
		$("#ownerFieldDialog").text(course[index].courseCreator);
		$("#dialogButton").text(x);
		$("#currentDialogName").text(x);
		$("#dialog").show();
	}

	function popUpDialogHide() {
		$("#dialog").hide();
	}

	function goCourse(id, x) {
		$.ajax({
			url: 'courses/' + id + '/' + x.toLowerCase(),
			type: 'POST',
		});
	}

	function popUpCreateShow() {
		$("#courseCategory").append(
				'<option ></option>');
		$.each(categories, function(index, value) {
			
		$("#courseCategory").append(
				'<option id="courseCategory">'  + categories[index].categoryName + '</option>');
		});
		$("#createButton").attr("onclick", "createCourse(document.getElementById('courseName').value, document.getElementById('courseDescription').value, document.getElementById('courseLinks').value, document.getElementById('courseCategory').value)");
		
		
		$("#createPopUp").show();
	
		}
	
	function popUpCreateHide() {
		$("#createPopUp").hide();
	}
	
	function createCourse(name, description, links, category) {
		
		var categoryJs = {'categoryName' : category};
		 var data = {'courseName': name, 'courseDescription': description, 'courseLinks': links, 'courseCategory' : categoryJs};
		 
		 $.ajax({
		   url: 'rest/courses',
		   type: 'POST',
		   contentType:'application/json',
		   data: JSON.stringify(data),
		   dataType:'json'
		 });
		 
		 isAfterCreate = true;
		 getCourses();
		 location.reload();
		 popUpCreateHide();


		 
	}
	
	function popUpUpdateShow(indexCourse) {
		
		$.each(categories, function(index, value) {
			
		$("#courseCategoryUpdate").append(
				'<option id="courseCategory" selected="selected">'  + categories[index].categoryName + '</option>');
		});
		$("#updateButton").attr("onclick", "updateCourse(document.getElementById('courseNameUpdate').value, document.getElementById('courseDescriptionUpdate').value, document.getElementById('courseLinksUpdate').value, document.getElementById('courseCategoryUpdate').value," + course[indexCourse].courseId + ")");
		$("#courseNameUpdate").attr("value", course[indexCourse].courseName);
		$("#courseDescriptionUpdate").text(course[indexCourse].courseDescription);
		$("#courseLinksUpdate").text(course[indexCourse].courseLinks);
		
		$("#updatePopUp").show();
	
		}
	
	function popUpUpdateHide() {
		$("#updatePopUp").hide();
	}
	
function updateCourse(name, description, links, category, id) {
		
		var categoryJs = {'categoryName' : category};
		 var data = {'courseName': name, 'courseDescription': description, 'courseLinks': links, 'courseCategory' : categoryJs};
		 
		 $.ajax({
		   url: 'rest/courses/' + id,
		   type: 'POST',
		   contentType:'application/json',
		   data: JSON.stringify(data),
		   dataType:'json'
		 });
		 
		 isAfterCreate = true;
		 getCourses();
		 location.reload();
		 popUpUpdateHide();
	
}
	
