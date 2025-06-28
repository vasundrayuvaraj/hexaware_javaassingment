CREATE DATABASE SISDB;
USE SISDB;
CREATE TABLE Students
(students
student_id int primary key,
first_name varchar(50),
last_name varchar(50),
date_of_birth date,
email varchar(100) unique,
phone_number varchar(15)
);

CREATE TABLE Teacher
(
teacher_id int primary key,
first_name varchar(50) not null,
last_name varchar(50),
email varchar(50) unique
);

CREATE TABLE Courses
(
course_id int primary key,
course_name  varchar(100) not null,
credits int,
teacher_id int,
foreign key(teacher_id) references Teacher(teacher_id)
);

create table Enrollments
(
enrollment_id int primary key not null,
student_id int not null,
course_id int,
enrollment_date date,
foreign key(student_id) references Students(student_id) on delete cascade on update cascade,
foreign key(course_id) references Courses(course_id)
);

CREATE TABLE Payments
(
payment_id int primary key,
student_id int not null,
amount decimal(10,2),
payment_date date,
foreign key(student_id) references Students(student_id) on delete cascade on update cascade
);
desc Teacher;
desc Students;

INSERT INTO Students (student_id,first_name, last_name, date_of_birth, email, phone_number) 
VALUES(1,'vasundra','yuvaraj','2003-09-17','vasundrayuvaraj@gmail.com','+91-6374721566');

INSERT INTO Students (student_id,first_name, last_name, date_of_birth, email, phone_number) 
VALUES(2,'dhanu','shree','2003-08-08','dhanushree@gmail.com','+91-8842567800'),courses
(3,'Kavya','shree','2001-04-06','kavyashree@gmail.com','+91-8963745268');

desc students;
select* from students;
INSERT INTO Students (student_id,first_name, last_name, date_of_birth, email, phone_number) VALUES
(4,'Alice', 'Brown', '2000-04-10', 'alice@gmail.com', '+91-9876543210'),
(5,'Bob', 'Smith', '1999-12-20', 'bob@gmail.com.com', '+91-8765432109'),
(6,'Carol', 'Taylor', '2001-06-18', 'carol@example.com', '+91-7654321098'),
(7,'David', 'Lee', '2000-09-12', 'david@example.com', '+91-6543210987'),
(8,'Eva', 'Clark', '1998-11-30', 'eva@example.com', '+91-5432109876'),
(9,'Frank', 'Wright', '2002-01-01', 'frank@example.com', '+91-4321098765'),
(10,'Grace', 'Hill', '2000-07-25', 'grace@example.com', '+91-3210987654');

select *from students;

INSERT INTO Teacher (teacher_id,first_name, last_name, email) VALUES
(01,'Tom', 'Hanks', 'tom.hanks@gmail.com'),
(02,'Sara', 'Parker', 'sara.parker@gmail.com'),
(03,'John', 'Doe', 'john.doe@gmail.com'),
(04,'Maya', 'Angelou', 'maya.angelou@gmail.com'),
(05,'Chris', 'Evans', 'chris.evans@gmail.com'),
(06,'Linda', 'Scott', 'linda.scott@gmail.com'),
(07,'Mark', 'Twain', 'mark.twain@gmail.com'),
(08,'Anne', 'Rice', 'anne.rice@gmail.com'),
(09,'Jane', 'Austen', 'jane.austen@gmail.com'),
(10,'Isaac', 'Newton', 'isaac.newton@gmail.com');

select* from teacher;

INSERT INTO Courses (course_id,course_name, credits, teacher_id) VALUES
(21,'SQL', 4, 01),
(22,'Postgresql', 3, 02),
(23,'Django', 4, 03),
(24,'MYSQL', 3, 04),
(25,'Computer Science', 5, 05),
(26,'cloud computing', 2, 06),
(27,'dsa', 3, 07),
(28,'javascript', 3, 08),
(29,'Python', 2, 09),
(30,'csharp', 3, 10);

INSERT INTO Enrollments (enrollment_id, student_id, course_id, enrollment_date) VALUES
(1, 1, 21, '2025-06-01'),
(2, 2, 22, '2025-07-02'),
(3, 3, 23, '2025-06-03'),
(4, 4, 24, '2025-06-04'),
(5, 5, 25, '2025-06-05'),
(6, 6, 26, '2025-06-06'),
(7, 7, 27, '2025-06-07'),
(8, 8, 28, '2025-06-08'),
(9, 9, 29, '2025-06-09'),
(10, 10, 30, '2025-06-10');

INSERT INTO Payments (payment_id, student_id, amount, payment_date) VALUES
(111, 1, 5000.00, '2025-06-11'),
(112, 2, 4500.00, '2025-06-11'),
(113, 3, 4700.00, '2025-06-11'),
(114, 4, 5200.00, '2025-06-11'),
(115, 5, 5500.00, '2025-06-11'),
(116, 6, 4300.00, '2025-06-11'),
(117, 7, 4900.00, '2025-06-11'),
(118, 8, 5100.00, '2025-06-11'),
(119, 9, 4800.00, '2025-06-11'),
(110, 10, 5000.00, '2025-06-11');

select* from teacher;
select* from students;
select*from courses;
select*from enrollments;
select*from payments;

show tables;

















