-- TRANSACTIONS 
--timestamp DATETIME(9),
--timestamp VARCHAR(13),
-- pardon my french, lemois - since month is a keyword 
-- ref: https://www.h2database.com/html/advanced.html#keywords
CREATE TABLE IF NOT EXISTS transactions(
id VARCHAR(36) PRIMARY KEY,
version NUMBER(10),
unixtime NUMBER(14),
lemois VARCHAR(12),
monid VARCHAR(2),
customer_id VARCHAR(10),
name VARCHAR(36),
amount VARCHAR(10)
);

