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