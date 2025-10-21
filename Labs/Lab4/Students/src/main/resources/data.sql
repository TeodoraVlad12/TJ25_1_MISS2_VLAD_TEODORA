INSERT INTO instructors (name, email) VALUES 
('Dr. John Smith', 'john.smith@university.edu'),
('Prof. Maria Garcia', 'maria.garcia@university.edu'),
('Dr. Robert Johnson', 'robert.johnson@university.edu');

INSERT INTO packs (year, semester, name) VALUES 
(2, 1, 'Mathematics Pack'),
(2, 1, 'Science Pack'),
(3, 1, 'Advanced Programming Pack'),
(3, 2, 'Database Systems Pack');

INSERT INTO courses (type, code, abbr, name, instructor_id, pack_id, group_count, description) VALUES 
('COMPULSORY', 'CS101', 'PROG', 'Programming Fundamentals', 1, NULL, 3, 'Introduction to programming concepts'),
('COMPULSORY', 'MATH201', 'CALC', 'Calculus I', 2, NULL, 2, 'Differential and integral calculus'),
('OPTIONAL', 'CS301', 'ALG', 'Advanced Algorithms', 1, 3, 2, 'Complex algorithmic problems and solutions'),
('OPTIONAL', 'CS302', 'ML', 'Machine Learning', 3, 3, 1, 'Introduction to machine learning concepts'),
('OPTIONAL', 'DB401', 'DBMS', 'Database Management Systems', 2, 4, 2, 'Relational database design and implementation');
