-- DROP TABLE IF EXISTS Users;
-- CREATE TABLE Users (
--                      id long AUTO_INCREMENT PRIMARY KEY,
--                      email VARCHAR(250) NOT NULL,
--                      enabled BOOLEAN NOT NULL,
--                      first_name VARCHAR(250) NOT NULL,
--                      last_name VARCHAR(250) NOT NULL,
--                      password VARCHAR(250) NOT NULL);
-- INSERT INTO Users (id ,email,enabled,first_name, last_name,password) VALUES (1,'test@test.com',True,'Test1','Test2','12345');
-- INSERT INTO Users (id ,email,enabled,first_name, last_name,password) VALUES (2,'d@gmail.com',True,'duha','jarrar','123');

--------------------------------------------------------------------
DROP TABLE IF EXISTS users ;
CREATE TABLE users (
                       id long AUTO_INCREMENT PRIMARY KEY,
                       username VARCHAR(250),
                       first_name VARCHAR(250),
                       last_name VARCHAR(250),
                       enabled Boolean ,
--                        role VARCHAR(250),
                       password VARCHAR(250));
-------------------------------------------------------------------
DROP TABLE IF EXISTS authorities;
CREATE TABLE authorities (
                    id long AUTO_INCREMENT PRIMARY KEY,
                    user_id long,
                    authority VARCHAR(250) NOT NULL,
--                     PRIMARY KEY(customer_id,authority),
                    FOREIGN KEY (user_id) REFERENCES users(id));
------------------------------------------------------------------
DROP TABLE IF EXISTS Invoices ;
CREATE TABLE Invoices (
                           id long AUTO_INCREMENT PRIMARY KEY,
                           title VARCHAR(250) NOT NULL,
                           creation_date DATE NOT NULL,
                           description VARCHAR(250) NOT NULL,
                           total long NOT NULL,
                           user_id long,
                           FOREIGN KEY (user_id) REFERENCES users(id));
--------------------------------------------------------------------------------
DROP TABLE IF EXISTS Item ;
CREATE TABLE Item (
                    id long AUTO_INCREMENT PRIMARY KEY,
                    name VARCHAR(250) NOT NULL,
                    price int NOT NULL);
--------------------------------------------------------------------------------
DROP TABLE IF EXISTS Invoice_items ;
CREATE TABLE Invoice_items (
                          id long AUTO_INCREMENT PRIMARY KEY,
                          priceafterdiscount int,
                          quantity int NOT NULL,
                          discount int,
                          item_id long,
                          invoice_id long,
--                           PRIMARY KEY (item_id),
                          FOREIGN KEY (invoice_id) REFERENCES Invoices(id),
                          FOREIGN KEY (item_id) REFERENCES Item(id));
--------------------------------------------------------------------------------
DROP TABLE IF EXISTS file_response ;
CREATE TABLE file_response (
                               id long AUTO_INCREMENT PRIMARY KEY,
                               invoice_id long,
                               name VARCHAR(250),
                               uri VARCHAR(250),
                               type VARCHAR(250),
                               size long,
                               FOREIGN KEY (invoice_id) REFERENCES Invoices(id));
