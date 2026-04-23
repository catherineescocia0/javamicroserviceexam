INSERT INTO customer (customer_number, customer_name, customer_mobile, customer_email, address1, address2)
VALUES
    (10000001, 'John Doe',    '09171234567', 'john.doe@gmail.com',   '123 Rizal St, Manila',         'Brgy. 001'),
    (10000002, 'Maria Clara',      '09281234567', 'maria.clara@gmail.com',    '456 Mabini Ave, Quezon City',  'Brgy. 002'),
    (10000003, 'Keyla Ochoa',        '09391234567', 'keyla.ochia@gmail.com',      '789 Bonifacio Rd, Makati',     NULL),
    (10000004, 'Truett Marks',        '09501234567', 'truettmarks@gmail.com',      '321 Luna St, Pasig',           'Brgy. 004'),
    (10000005, 'Maryam Lu',    '09611234567', 'maryam.lu@gmail.com',  '654 Aguinaldo Blvd, Taguig',   NULL);


INSERT INTO accounts (account_number, account_type, available_balance, customer_number)
VALUES
    (10001, 'Savings',  15000.00, 10000001),
    (10002, 'Checking',  8000.00, 10000001),
    (10003, 'Checking',  8000.00, 10000002),
    (10004, 'Savings',  25000.00, 10000003),
    (10005, 'Savings',   5000.00, 10000004),
    (10006, 'Checking', 12000.00, 10000005);

