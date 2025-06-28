use sisdb;
select * from students;
select * from teacher;
select* from payments;
select * from enrollments;
select * from  courses;

-- Write an SQL query to calculate the total payments made by a specific student. You will need to
-- join the "Payments" table with the "Students" table based on the student's ID.

SELECT 
    s.student_id,
    CONCAT(s.first_name, ' ', s.last_name) AS name,
    SUM(p.amount) AS total_payments
FROM 
    Students s
LEFT JOIN 
    Payments p ON s.student_id = p.student_id
WHERE 
    s.student_id = 1
GROUP BY 
    s.student_id, name;


-- Write an SQL query to retrieve a list of courses along with the count of students enrolled in each
-- course. Use a JOIN operation between the "Courses" table and the "Enrollments" table.

SET SESSION sql_mode=(SELECT REPLACE(@@sql_mode,'ONLY_FULL_GROUP_BY',''));
SELECT 
    c.course_name,
    COUNT(e.student_id) AS enrolled_students
FROM 
    Courses c
LEFT JOIN 
    Enrollments e ON c.course_id = e.course_id
GROUP BY 
    c.course_id, c.course_name;


-- Write an SQL query to find the names of students who have not enrolled in any course. Use a
-- LEFT JOIN between the "Students" table and the "Enrollments" table to identify students
-- without enrollments.


SELECT 
    s.student_id,
    CONCAT(s.first_name, ' ', s.last_name) AS name
FROM 
    Students s
LEFT JOIN 
    Enrollments e ON s.student_id = e.student_id
WHERE 
    e.enrollment_id IS NULL;

-- Write an SQL query to retrieve the first name, last name of students, and the names of the
-- courses they are enrolled in. Use JOIN operations between the "Students" table and the
-- "Enrollments" and "Courses" tables.

SELECT
    s.first_name,
    s.last_name,
    c.course_name
FROM 
    Students s
JOIN 
    Enrollments e ON s.student_id = e.student_id
JOIN 
    Courses c ON e.course_id = c.course_id
ORDER BY 
    s.student_id;
    
    
-- Create a query to list the names of teachers and the courses they are assigned to. Join the
-- "Teacher" table with the "Courses" table.

SELECT CONCAT(first_name,' ' ,last_name) as teacher_name ,course_name from teacher t join courses c on t.teacher_id=c.teacher_id;

-- Retrieve a list of students and their enrollment dates for a specific course. You'll need to join the
-- "Students" table with the "Enrollments" and "Courses" tables.

select concat(first_name,' ',last_name) as name,c.course_name,e.enrollment_date from students s join enrollments e on s.student_id=e.student_id join courses c on e.course_id=c.course_id;

-- Find the names of students who have not made any payments. Use a LEFT JOIN between the
-- "Students" table and the "Payments" table and filter for students with NULL payment records.

SELECT 
    CONCAT(s.first_name, ' ', s.last_name) AS Name
FROM 
    Students s
LEFT JOIN 
    Payments p ON s.student_id = p.student_id
WHERE 
    p.payment_id IS NULL;
    
-- Write a query to identify courses that have no enrollments. You'll need to use a LEFT JOIN
-- between the "Courses" table and the "Enrollments" table and filter for courses with NULL  enrollment records.
    
    SELECT
    course_name from courses c left join enrollments e on c.course_id=e.course_id
    where enrollment_id is null;
    
    -- Identify students who are enrolled in more than one course. Use a self-join on the "Enrollments"
--     table to find students with multiple enrollment records.

SELECT 
    DISTINCT e1.student_id
FROM 
    Enrollments e1
JOIN 
    Enrollments e2 
    ON e1.student_id = e2.student_id AND e1.course_id <> e2.course_id;

-- Find teachers who are not assigned to any courses. Use a LEFT JOIN between the "Teacher"
-- table and the "Courses" table and filter for teachers with NULL course assignments.
SELECT 
    CONCAT(t.first_name, ' ', t.last_name) AS teacher_name
FROM 
    Teacher t
LEFT JOIN 
    Courses c ON t.teacher_id = c.teacher_id
WHERE 
    c.course_id IS NULL;

