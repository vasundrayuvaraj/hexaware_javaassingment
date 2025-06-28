use sisdb;
select * from teacher;
select * from students;
select * from enrollments;
select * from courses;
select * from payments;

alter table students modify student_id int auto_increment;

set foreign_key_checks=1;

alter table students modify student_id int auto_increment;
ALTER TABLE Payments 
MODIFY payment_id INT AUTO_INCREMENT;


set foreign_key_checks=1;

insert into students(first_name,last_name,date_of_birth, email,phone_number)
values('John','Doe','1995-08-15','john.doe@example.com','1234567890');

select* from students;

ALTER TABLE Enrollments MODIFY enrollment_id INT AUTO_INCREMENT;

INSERT INTO Enrollments (student_id, course_id, enrollment_date)
VALUES (11, 31, current_date());

INSERT INTO Enrollments (student_id, course_id, enrollment_date)
VALUES (8, 28, current_date());

UPDATE Teacher
SET email = 'john.doe@updatedemail.com'
WHERE teacher_id = 3;

DELETE FROM Enrollments
WHERE student_id = 2 AND course_id = 22;

UPDATE Courses
SET teacher_id = 5
WHERE course_id = 29;

UPDATE Courses
SET course_name = 'c'
WHERE course_id = 29;

DELETE FROM Students
WHERE student_id = 5;

UPDATE Payments
SET amount = 6000.00
WHERE payment_id = 114;






