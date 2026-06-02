USE employee_management;

INSERT INTO employees
    (first_name, last_name, email, phone, department, job_title, salary, hire_date, status)
VALUES
    ('Amit', 'Sharma', 'amit.sharma@example.com', '9876543210', 'Engineering', 'Junior Java Developer', 45000.00, '2024-06-10', 'ACTIVE'),
    ('Priya', 'Nair', 'priya.nair@example.com', '9876543211', 'HR', 'HR Executive', 38000.00, '2023-11-15', 'ACTIVE'),
    ('Rahul', 'Mehta', 'rahul.mehta@example.com', '9876543212', 'Finance', 'Accounts Analyst', 42000.00, '2022-08-01', 'ACTIVE'),
    ('Sneha', 'Patel', 'sneha.patel@example.com', '9876543213', 'Engineering', 'QA Engineer', 41000.00, '2025-01-20', 'ACTIVE');
