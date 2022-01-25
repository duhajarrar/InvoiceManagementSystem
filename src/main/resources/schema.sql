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
                       id long AUTO_INCREMENT PRIMARY KEY NOT NULL,
                       username VARCHAR(250) NOT NULL ,
                       first_name VARCHAR(250) NOT NULL,
                       last_name VARCHAR(250) NOT NULL,
                       enabled Boolean ,
--                        role VARCHAR(250),
                       password VARCHAR(250));
-------------------------------------------------------------------
DROP TABLE IF EXISTS authorities;
CREATE TABLE authorities (
                    id long AUTO_INCREMENT PRIMARY KEY NOT NULL,
                    user_id long NOT NULL,
                    authority VARCHAR(250) NOT NULL,
--                     PRIMARY KEY(customer_id,authority),
                    FOREIGN KEY (user_id) REFERENCES users(id));
------------------------------------------------------------------
DROP TABLE IF EXISTS Invoices ;
CREATE TABLE Invoices (
                           id long AUTO_INCREMENT PRIMARY KEY NOT NULL,
                           compnumber long NOT NULL ,
                           title VARCHAR(250) NOT NULL,
                           creation_date DATE NOT NULL,
                           description VARCHAR(250) NOT NULL,
                           total long NOT NULL,
                           user_id long NOT NULL,
                           FOREIGN KEY (user_id) REFERENCES users(id));
--------------------------------------------------------------------------------
DROP TABLE IF EXISTS Item ;
CREATE TABLE Item (
                    id long AUTO_INCREMENT PRIMARY KEY NOT NULL,
                    name VARCHAR(250) NOT NULL,
                    price int NOT NULL);
--------------------------------------------------------------------------------
DROP TABLE IF EXISTS Invoice_items ;
CREATE TABLE Invoice_items (
                          id long AUTO_INCREMENT PRIMARY KEY NOT NULL,
                          priceafterdiscount int NOT NULL,
                          quantity int NOT NULL,
                          discount int NOT NULL,
                          item_id long NOT NULL,
                          invoice_id long NOT NULL,
--                           PRIMARY KEY (item_id),
                          FOREIGN KEY (invoice_id) REFERENCES Invoices(id),
                          FOREIGN KEY (item_id) REFERENCES Item(id));
--------------------------------------------------------------------------------
DROP TABLE IF EXISTS file_response ;
CREATE TABLE file_response (
                               id long AUTO_INCREMENT PRIMARY KEY NOT NULL,
                               invoice_id long NOT NULL,
                               name VARCHAR(250) NOT NULL,
                               uri VARCHAR(250) NOT NULL,
                               type VARCHAR(250) NOT NULL,
                               size long NOT NULL,
                               FOREIGN KEY (invoice_id) REFERENCES Invoices(id));
