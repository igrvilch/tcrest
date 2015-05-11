CREATE TABLE IF NOT EXISTS COURSE (
courseId INT IDENTITY PRIMARY KEY,
courseName VARCHAR(255),
courseDescription VARCHAR(255),
courseLinks VARCHAR(255),
courseCreator VARCHAR(255),
courseState INT,
categoryId INT,
ownerId INT,
DmDecision VARCHAR(255),
KmDecision VARCHAR(255),
DmReason VARCHAR(255),
KmReason VARCHAR(255),
courseMinSubs INT,
courseMinAttenders INT);

CREATE TABLE IF NOT EXISTS USER (
userID INT IDENTITY PRIMARY KEY,
userLogin VARCHAR(255),
userEmail VARCHAR(255),
userPassword VARCHAR(255),
userRole INT);


CREATE TABLE IF NOT EXISTS USERCOURSE (
userCourseId INT IDENTITY PRIMARY KEY,
userCourseState VARCHAR(255),
userCourseMark INT,
COURSE_COURSEID INT,
USER_USERID INT);


CREATE TABLE IF NOT EXISTS CATEGORY (
categoryId INT IDENTITY PRIMARY KEY,
categoryName VARCHAR(255));


