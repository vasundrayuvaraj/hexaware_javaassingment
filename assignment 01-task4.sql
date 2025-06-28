use sisdb;
select* from students;
select* from courses;
select* from payments;
select * from enrollments;
select * from teacher;
-- Write an SQL query to calculate the average number of students enrolled in each course. Use
-- aggregate functions and subqueries to achieve this.
SELECT 
    AVG(student_count) AS avg_students
FROM (
    SELECT 
        course_id, 
        COUNT(student_id) AS student_count
    FROM enrollments
    GROUP BY course_id
) AS course_enrollments;


-- Identify the student(s) who made the highest payment. Use a subquery to find the maximum
-- payment amount and then retrieve the student(s) associated with that amount.

select student_id,amount from payments where  amount=(select max(amount) from payments);

-- Retrieve a list of courses with the highest number of enrollments. Use subqueries to find the
-- course(s) with the maximum enrollment count.

SELECT c.course_name, COUNT(e.course_id) ecount
FROM courses c
JOIN enrollments e ON c.course_id = e.course_id
GROUP BY c.course_id, c.course_name
HAVING COUNT(e.course_id) = (
    SELECT MAX(enrollment_count)
    FROM (
        SELECT COUNT(*) AS enrollment_count
        FROM enrollments
        GROUP BY course_id
    ) AS subquery
);
-- Calculate the total payments made to courses taught by each teacher. Use subqueries to sum
-- payments for each teacher's courses.

SELECT 
    CONCAT(t.first_name, ' ', t.last_name) AS teacher_name,
    SUM(p.amount) AS total_payments
FROM 
    Teacher t
JOIN 
    Courses c ON t.teacher_id = c.teacher_id
JOIN 
    Enrollments e ON c.course_id = e.course_id
JOIN 
    Payments p ON e.student_id = p.student_id
GROUP BY 
    t.teacher_id, teacher_name;

-- Identify students who are enrolled in all available courses. Use subqueries to compare a
-- student's enrollments with the total number of courses.

select s.student_id , concat('first_name'," ",'last_name') name
From students  s
join courses c  on c.course_id=s.student_id
join enrollments e on e.enrollment_id=c.course_id;

-- Retrieve the names of teachers who have not been assigned to any courses. Use subqueries to
-- find teachers with no course assignments.

select concat('first_name'," ",'last_name')name from teacher t where not exists(select teacher_id from courses c  group by course_id);

-- Calculate the average age of all students. Use subqueries to calculate the age of each student
-- based on their date of birth.

select avg(age) average_age from
(select timestampdiff(year,date_of_birth,curdate()) age from students) age_table;

-- Identify courses with no enrollments. Use subqueries to find courses without enrollment
-- records.

select * from courses where course_id not in(select distinct course_id from enrollments);

-- Calculate the total payments made by each student for each course they are enrolled in. Use
-- subqueries and aggregate functions to sum payments.

SELECT 
    student_id,
    course_id,
    (SELECT SUM(amount) 
     FROM payments p 
     WHERE p.student_id = e.student_id AND p.student_id = e.student_id) AS total_payment
FROM enrollments e;

-- Identify students who have made more than one payment. Use subqueries and aggregate
-- functions to count payments per student and filter for those with counts greater than one.

SELECT student_id
FROM (
    SELECT student_id, COUNT(*) AS payment_count
    FROM payments
    GROUP BY student_id
) AS sub
WHERE payment_count > 1;

-- Write an SQL query to calculate the total payments made by each student. Join the "Students"
-- table with the "Payments" table and use GROUP BY to calculate the sum of payments for each
-- student.

SELECT 
    s.student_id,
    CONCAT(s.first_name, ' ', s.last_name) AS student_name,
    p.total_payments FROM students s
JOIN 
(SELECT student_id, SUM(amount) AS total_payments FROM payments GROUP BY student_id) p ON s.student_id = p.student_id;

-- Retrieve a list of course names along with the count of students enrolled in each course. Use
-- JOIN operations between the "Courses" table and the "Enrollments" table and GROUP BY to
-- count enrollments.
 
 SELECT 
    c.course_name,
    (
        SELECT COUNT(*) 
        FROM enrollments e 
        WHERE e.course_id = c.course_id
    ) AS student_count
FROM 
    courses c
JOIN 
    enrollments e ON c.course_id = e.course_id
GROUP BY 
    c.course_id;

-- 13. Calculate the average payment amount made by students. Use JOIN operations between the
-- "Students" table and the "Payments" table and GROUP BY to calculate the average.

 SELECT 
    s.student_id,
    s.first_name,
    (
        SELECT AVG(p.amount)
        FROM payments p
        where p.student_id=s.student_id
    ) AS average_payment
FROM 
    students s
JOIN 
    payments p ON s.student_id = p.student_id
GROUP BY 
    s.student_id;

