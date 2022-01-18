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


INSERT INTO Invoices (id,title,creation_date,description,total,user_id) VALUES (2,'invoice2','2020-01-02','test 1',0,0);
INSERT INTO Invoices (id,title,creation_date,description,total,user_id) VALUES (1,'invoice1','2016-01-05','test 1',0,1);

INSERT INTO Invoices (id,title,creation_date,description,total,user_id) VALUES (3,'invoice3','2021-11-02','test 1',0,0);
INSERT INTO Invoices (id,title,creation_date,description,total,user_id) VALUES (4,'invoice4','2020-11-05','test 1',0,1);

INSERT INTO Invoices (id,title,creation_date,description,total,user_id) VALUES (5,'invoice5','2012-10-02','test 1',0,0);
INSERT INTO Invoices (id,title,creation_date,description,total,user_id) VALUES (6,'invoice6','2010-09-05','test 1',0,1);

INSERT INTO Invoices (id,title,creation_date,description,total,user_id) VALUES (7,'invoice7','2016-08-02','test 1',0,0);
INSERT INTO Invoices (id,title,creation_date,description,total,user_id) VALUES (8,'invoice8','2014-07-05','test 1',0,1);

INSERT INTO Invoices (id,title,creation_date,description,total,user_id) VALUES (9,'invoice9','2013-06-02','test 1',0,0);
INSERT INTO Invoices (id,title,creation_date,description,total,user_id) VALUES (10,'invoice10','2009-04-05','test 1',0,1);

INSERT INTO Invoices (id,title,creation_date,description,total,user_id) VALUES (11,'invoice11','2005-03-02','test 1',0,0);
INSERT INTO Invoices (id,title,creation_date,description,total,user_id) VALUES (12,'invoice12','2004-02-05','test 1',0,1);

INSERT INTO Invoices (id,title,creation_date,description,total,user_id) VALUES (13,'invoice13','2022-01-02','test 1',0,0);
INSERT INTO Invoices (id,title,creation_date,description,total,user_id) VALUES (14,'invoice14','2022-07-05','test 1',0,1);

INSERT INTO Invoices (id,title,creation_date,description,total,user_id) VALUES (15,'invoice15','2022-10-02','test 1',0,0);
INSERT INTO Invoices (id,title,creation_date,description,total,user_id) VALUES (16,'invoice16','2017-12-05','test 1',0,1);

INSERT INTO Invoices (id,title,creation_date,description,total,user_id) VALUES (17,'invoice17','2016-11-02','test 1',0,0);
INSERT INTO Invoices (id,title,creation_date,description,total,user_id) VALUES (18,'invoice18','2019-02-05','test 1',0,1);

INSERT INTO Invoices (id,title,creation_date,description,total,user_id) VALUES (19,'invoice19','2020-02-02','test 1',0,0);
INSERT INTO Invoices (id,title,creation_date,description,total,user_id) VALUES (20,'invoice20','2021-03-05','test 1',0,1);

INSERT INTO Invoices (id,title,creation_date,description,total,user_id) VALUES (21,'invoice21','2015-04-02','test 1',0,0);
INSERT INTO Invoices (id,title,creation_date,description,total,user_id) VALUES (22,'invoice22','2016-05-05','test 1',0,1);

INSERT INTO Invoices (id,title,creation_date,description,total,user_id) VALUES (23,'invoice23','2018-03-02','test 1',0,0);
INSERT INTO Invoices (id,title,creation_date,description,total,user_id) VALUES (24,'invoice24','2019-02-05','test 1',0,1);


INSERT INTO Invoices (id,title,creation_date,description,total,user_id) VALUES (25,'A','2020-01-02','B',0,0);
INSERT INTO Invoices (id,title,creation_date,description,total,user_id) VALUES (26,'A','2016-01-05','B',0,1);

INSERT INTO Invoices (id,title,creation_date,description,total,user_id) VALUES (27,'B','2022-01-12','A',0,0);
INSERT INTO Invoices (id,title,creation_date,description,total,user_id) VALUES (28,'B','2022-01-12','A',0,1);

INSERT INTO Invoice_items(id,priceafterdiscount,quantity,discount,item_id,invoice_id) VALUES (1,10,1,0,1,1);
INSERT INTO Invoice_items(id,priceafterdiscount,quantity,discount,item_id,invoice_id) VALUES (2,20,1,0,2,1);
INSERT INTO Invoice_items(id,priceafterdiscount,quantity,discount,item_id,invoice_id) VALUES (3,30,1,0,3,1);
INSERT INTO Invoice_items(id,priceafterdiscount,quantity,discount,item_id,invoice_id) VALUES (4,40,1,0,4,2);
INSERT INTO Invoice_items(id,priceafterdiscount,quantity,discount,item_id,invoice_id) VALUES (5,50,1,0,5,1);
INSERT INTO Invoice_items(id,priceafterdiscount,quantity,discount,item_id,invoice_id) VALUES (6,20,1,0,2,2);
INSERT INTO Invoice_items(id,priceafterdiscount,quantity,discount,item_id,invoice_id) VALUES (7,10,1,0,1,1);
INSERT INTO Invoice_items(id,priceafterdiscount,quantity,discount,item_id,invoice_id) VALUES (8,20,1,0,2,1);
INSERT INTO Invoice_items(id,priceafterdiscount,quantity,discount,item_id,invoice_id) VALUES (9,30,1,0,3,1);
INSERT INTO Invoice_items(id,priceafterdiscount,quantity,discount,item_id,invoice_id) VALUES (10,40,1,0,4,2);
INSERT INTO Invoice_items(id,priceafterdiscount,quantity,discount,item_id,invoice_id) VALUES (11,50,1,0,5,1);
INSERT INTO Invoice_items(id,priceafterdiscount,quantity,discount,item_id,invoice_id) VALUES (12,20,1,0,2,2);
INSERT INTO Invoice_items(id,priceafterdiscount,quantity,discount,item_id,invoice_id) VALUES (13,10,1,0,1,1);
INSERT INTO Invoice_items(id,priceafterdiscount,quantity,discount,item_id,invoice_id) VALUES (14,20,1,0,2,1);
INSERT INTO Invoice_items(id,priceafterdiscount,quantity,discount,item_id,invoice_id) VALUES (15,30,1,0,3,1);
INSERT INTO Invoice_items(id,priceafterdiscount,quantity,discount,item_id,invoice_id) VALUES (16,40,1,0,4,2);
INSERT INTO Invoice_items(id,priceafterdiscount,quantity,discount,item_id,invoice_id) VALUES (17,50,1,0,5,1);
INSERT INTO Invoice_items(id,priceafterdiscount,quantity,discount,item_id,invoice_id) VALUES (18,20,1,0,2,2);
