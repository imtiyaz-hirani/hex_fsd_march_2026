use hex_march_fsd_2026;
show tables; 
drop table customer;
create table customer(id INT primary key auto_increment,
		name varchar(255),
        city varchar(255),
        email varchar(255));

desc customer; 

insert into customer(name,city,email) values ("harry potter", "london", "harry@gmail.com"),
("ronald weasley", "surrey", "ron@gmail.com");

select * from customer;

/*
Procedures are categorized in 2 types
1. Anonymous block 
2. Stored Procedure

MySQL  :- no AS in procedure , no DBMS_OUTPUT (select)  
Oracle :- U use AS in procedure , DBMS_OUTPUT works 
*/

/* Simple stored Procedure */
DELIMITER $$
create procedure get_all_customers()
BEGIN 
	select * from customer;
END
$$
DROP procedure get_all_customers;

/* A proc is created with the name as get_all_customers, now lets call it*/
CALL get_all_customers();  

/* Procedures can take inputs and give outputs */

/* create a proc to take input(city) from the caller amd return the rows*/
DELIMITER $$
create procedure get_customers_by_city(IN p_city varchar(255))
BEGIN
	-- validation 
    if TRIM(p_city) = "" OR p_city IS NULL then 
		SIGNAL sqlstate "45000"
        SET message_text = "city value cannot be blank or null";
    end if; 
    
	select * 
    from customer
    where city = p_city
    order by city;
END
$$
drop procedure get_customers_by_city;
CALL get_customers_by_city("MUMBAI"); 
CALL get_customers_by_city("");  // validation of input given 
CALL get_customers_by_city("   ");
CALL get_customers_by_city(NULL);
/*
Error Code: 1644. city value cannot be blank or null	
*/
CALL get_customers_by_city("london");

/*
	Write a Proc to fetch a record from customer based on given Id. 
    validation: Ensure that id is not 0 or negative 
*/

/* IN Param , OUT parameter */

/* create a proc for giving total number of customers as output based on given city.  */
DELIMITER $$
create procedure total_customers(IN p_city varchar(255), OUT total_customers INT)
BEGIN
		-- validation 
    if TRIM(p_city) = "" OR p_city IS NULL then 
		SIGNAL sqlstate "45000"
        SET message_text = "city value cannot be blank or null";
    end if; 
    
    select COUNT(id) into total_customers
    from customer
    where city = p_city;
    
END
$$

CALL total_customers("london", @total_cust); 
-- @total_cut is a session variable which is active in this entire file(session)

select @total_cust;

/* View: Create a view to hide email attribute from customer table */
create VIEW customer_view AS
select id,name,city
from customer ;


/* INOUT: variable is IN and also acts as an OUT */

-- update an email and return the updated value using INOUT PARAM 

DELIMITER $$ 
create procedure email_update(IN p_id INT, inout p_email VARCHAR(255)) 
BEGIN
	Update customer 
    SET email = p_email
    where id= p_id;
END
$$

SET @email_val = "harry@hogwarts.com";
CALL email_update(1,@email_val);
select @email_val; 

/*
Product Platform - sales 
sales
-----
id
product_name
category
quantity
price 
date_of_sale

Insert 3-5 records 

1. WAP get_sales_by_category 
input: category 

2. WAP Count Total sales by given category 
input: category 
output: INT total_sales

3. Create a View to product sales summary 
product_name, cateogry, quantity, price 

Opyional: Call them from Java Application 
*/

/*
	Trigger case study for ECOM scenario : 
    Product being purchased and entry being made in transaction table. 
*/
-- lets create tables 
create table products(
id INT primary key auto_increment, 
name varchar(255),
price DECIMAL, 
stock INT );

create table transactions(
id INT primary key auto_increment,
product_id INT,
quantity INT,
sale_date Date); 

-- insert some sample data 
insert into products (name,price,stock) values ('Apple Macbook M1', 67000, 4), ("Lenovo Thinkpad", 89000, 3);
select * from products;

/* lets make triggers 
   Triggers are programs like procedures that run automatically based on given condition 
*/

-- lets make a trigger for checking if the rdered quantity is greater than requested stock ??? 

DELIMITER $$ 
create trigger trg_validate_stock_quantity 
BEFORE INSERT ON transactions
FOR each row
BEGIN
	-- declaring a variable 
    DECLARE v_stock INT; 
	-- fetch stock of the given product 
    SELECT stock into v_stock -- i have the stock value of this particular product in v_stock variable 
    from products 
    where id = NEW.product_id;
    
    -- lets validate 
    IF NEW.quantity > v_stock THEN 
    SIGNAL sqlstate '45000'
    SET message_text = "Not enough stock is available";
    END IF; 
END
$$

-- insert into transactions values (1, 2, now()); 
/*
	NEW.product_id = 1
    NEW.quantity = 2 
    
    OLD:- points to records which are already present in DB 
    NEW:- points to records which are waiting to be inserted in DB 
*/

insert into transactions(product_id, quantity, sale_date) values (1, 5, now()); 

/* Trigger for updating the stock of the product after the insert has happend on transaction */

DELIMITER $$ 
create trigger trg_update_stock_after_purchase
AFTER INSERT ON transactions
FOR EACH ROW 
BEGIN
	update products 
    SET stock = stock - NEW.quantity 
    where id = NEW.product_id;
END
$$

insert into transactions(product_id, quantity, sale_date) values (1, 1, now()); 

DELIMITER $$
create procedure get_all_products()
BEGIN 
	select * from products;
END
$$

CALL get_all_products;

/*
Cursor: 
1. Declare the cursor 
2. OPEN it 
3. FETCH the rows 
4. CLOSE it 
*/

-- CAP to display names of all products using cursor 
DELIMITER $$
create procedure product_names_cur()
BEGIN
	-- declare variables 
    DECLARE v_name varchar(255);
    DECLARE done boolean DEFAULT FALSE; 
    
	-- save this query in the cursor -- Declare the cursor
    DECLARE customer_name_cursor cursor for
		select name from products;
        
    -- declare termination variable (done)
    DECLARE continue handler FOR NOT FOUND SET done = TRUE; -- keep looping, and when there is nothing to loop, make done=TRUE
    
    OPEN customer_name_cursor; 
    
    cursor_loop: LOOP 
    FETCH customer_name_cursor into v_name;
    
    IF done THEN  -- termination condition- if there is nothing to display , just leave the loop 
		leave cursor_loop;
    END IF;
    
    select v_name; 
    
    END LOOP; 
    
    close customer_name_cursor; 
END
$$

CALL product_names_cur();





