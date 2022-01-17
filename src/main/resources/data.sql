INSERT INTO users(id,username,first_name,last_name,enabled,password) VALUES (0,'admin','admin','admin',TRUE,'$2a$10$R244P6hZ4MUa9EBeAUEcne5B8Lb6mTzw5.2vzKH6S7q9u3pqrGfoW');
INSERT INTO users(id,username,first_name,last_name,enabled,password) VALUES (1,'user','user','user',TRUE,'$2a$10$R244P6hZ4MUa9EBeAUEcne5B8Lb6mTzw5.2vzKH6S7q9u3pqrGfoW');
INSERT INTO users(id,username,first_name,last_name,enabled,password) VALUES (2,'duha','user','user',TRUE,'$2a$10$R244P6hZ4MUa9EBeAUEcne5B8Lb6mTzw5.2vzKH6S7q9u3pqrGfoW');

INSERT INTO authorities (id,user_id, authority) VALUES (1,1, 'ROLE_USER');
INSERT INTO authorities (id,user_id, authority) VALUES (2,0, 'ROLE_ADMIN');
INSERT INTO authorities (id,user_id, authority) VALUES (3,0, 'ROLE_USER');
INSERT INTO authorities (id,user_id, authority) VALUES (4,2, 'ROLE_USER');

INSERT INTO Item (id,name,price) VALUES (1,'test1',10);
INSERT INTO Item (id,name,price) VALUES (2,'test2',20);
INSERT INTO Item (id,name,price) VALUES (3,'test3',30);
INSERT INTO Item (id,name,price) VALUES (4,'test4',40);
INSERT INTO Item (id,name,price) VALUES (5,'test5',50);
INSERT INTO Item (id,name,price) VALUES (6,'test6',60);
INSERT INTO Item (id,name,price) VALUES (7,'test7',70);
INSERT INTO Item (id,name,price) VALUES (8,'test8',80);
INSERT INTO Item (id,name,price) VALUES (9,'test9',90);
INSERT INTO Item (id,name,price) VALUES (10,'test10',100);
INSERT INTO Invoices (id,title,creation_date,description,total,user_id) VALUES (2,'invoice2','2022-01-02','test 1',0,0);
INSERT INTO Invoices (id,title,creation_date,description,total,user_id) VALUES (1,'invoice1','2022-01-05','test 1',0,1);
INSERT INTO Invoice_items(id,priceafterdiscount,quantity,discount,item_id,invoice_id) VALUES (1,10,1,0,1,1);
INSERT INTO Invoice_items(id,priceafterdiscount,quantity,discount,item_id,invoice_id) VALUES (2,20,1,0,2,1);
INSERT INTO Invoice_items(id,priceafterdiscount,quantity,discount,item_id,invoice_id) VALUES (3,30,1,0,3,1);
INSERT INTO Invoice_items(id,priceafterdiscount,quantity,discount,item_id,invoice_id) VALUES (4,40,1,0,4,2);
INSERT INTO Invoice_items(id,priceafterdiscount,quantity,discount,item_id,invoice_id) VALUES (5,50,1,0,5,1);
INSERT INTO Invoice_items(id,priceafterdiscount,quantity,discount,item_id,invoice_id) VALUES (6,20,1,0,2,2);

