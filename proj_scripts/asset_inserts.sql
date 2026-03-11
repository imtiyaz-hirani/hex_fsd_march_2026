/* Asset Case Study */
use hex_asset_mgmt; 
INSERT INTO `hex_asset_mgmt`.`department` (`name`) VALUES 
('Information Technology'),
('Human Resources'),
('Finance'),
('Marketing'),
('Operations');

INSERT INTO `hex_asset_mgmt`.`employee` (`name`, `job_title`, `department_id`) VALUES 
('Alice Johnson', 'Senior Developer', 1),
('Bob Smith', 'IT Support', 1),
('Charlie Davis', 'HR Manager', 2),
('Diana Prince', 'Financial Analyst', 3),
('Edward Norton', 'Marketing Lead', 4),
('Fiona Gallagher', 'Operations Coordinator', 5);

INSERT INTO `hex_asset_mgmt`.`asset` (`title`, `category`, `stock_count`, `details`) VALUES 
('MacBook Pro 16', 'Laptop', 10, 'M3 Chip, 32GB RAM'),
('Dell UltraSharp 27', 'Monitor', 15, '4K UHD Resolution'),
('Logitech MX Master 3', 'Peripherals', 20, 'Wireless Mouse'),
('iPhone 15 Pro', 'Mobile', 5, '128GB Titanium'),
('Ergonomic Chair', 'Furniture', 12, 'Mesh back with lumbar support'),
('Yeti Blue Nano', 'Audio', 8, 'USB Microphone');

INSERT INTO `hex_asset_mgmt`.`asset_employee` (`asset_id`, `employee_id`, `date_of_allocation`, `serial_number`) VALUES 
(1, 1, '2024-01-15', 'LAP-MAC-001'), -- Alice gets a MacBook
(3, 1, '2024-01-15', 'MOU-LOG-055'), -- Alice also gets a mouse
(2, 2, '2024-02-01', 'MON-DEL-102'), -- Bob gets a monitor
(5, 3, '2023-11-10', 'FUR-CHR-012'), -- Charlie gets a chair
(4, 4, '2024-03-05', 'MOB-IPH-888'), -- Diana gets a phone
(1, 5, '2024-03-10', 'LAP-MAC-002'); -- Edward gets a MacBook

-- - Display all Assets that a certain employee has (employee_id)
/*
	1. Manual mapping
    2. Join
*/
-- Manual Mapping 
select a.title, a.category, ae.date_of_allocation, e.job_title 
from asset as a, asset_employee as ae,employee as e
where a.id = ae.asset_id AND
ae.employee_id = e.id AND
e.id = 1;


-- Joins (Hibernate prefers Joins)
select a.title, a.category, ae.date_of_allocation, e.job_title 
from asset as a 
JOIN asset_employee as ae ON a.id = ae.asset_id
JOIN employee e ON ae.employee_id = e.id
where e.id=1;

-- Query 2: Display all Employees that belong to given department and have borrowed asset with category='Peripherals'

-- Manual Mapping
select e.* 
from asset a, asset_employee ae, employee e, department d 
where a.id = ae.asset_id AND 
ae.employee_id = e.id AND 
e.department_id = d.id AND
d.id=1 AND a.category IN ('peripherals','monitor');

-- Joins 
select e.*
from asset a 
JOIN asset_employee ae ON a.id = ae.asset_id  
JOIN employee e ON ae.employee_id = e.id 
JOIN department d ON e.department_id = d.id
where d.id=1 AND a.category IN ('peripherals','monitor');










