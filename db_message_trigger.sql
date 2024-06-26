CREATE TABLE Message (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         customer VARCHAR(255),
                         message TEXT,
                         dateTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

DELIMITER $$


CREATE TRIGGER after_savings_update_trigger
    AFTER INSERT ON savings_mgmt_banking
    FOR EACH ROW
BEGIN
    DECLARE customer_email VARCHAR(255);

    -- Retrieve the customer's full name
    SELECT email
    INTO customer_email
    FROM customers
    WHERE id = customers.id;

    -- Insert the message into the Message table
    INSERT INTO Message (customer, message, dateTime)
    VALUES (customer_email, CONCAT('Account Deposit Successfull -- Added amount: ', NEW.amount), NOW());
END $$

CREATE TRIGGER after_withdrawal_update_trigger
    AFTER INSERT ON ne.withdraw_mgmt_banking
    FOR EACH ROW
BEGIN
    DECLARE customer_email VARCHAR(255);

    -- Retrieve the customer's full name
    SELECT email
    INTO customer_email
    FROM customers
    WHERE id = customers.id;

    -- Insert the message into the Message table
    INSERT INTO Message (customer, message, dateTime)
    VALUES (customer_email, CONCAT('Account Withdrawal Successfull -- Withdrawed amount: ', NEW.amount), NOW());
END $$

DELIMITER ;