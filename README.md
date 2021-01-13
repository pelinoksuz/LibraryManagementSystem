# LibraryManagementSystem

DDL for now

DROP DATABASE IF EXISTS LMS;
CREATE DATABASE LMS;
USE LMS;

CREATE TABLE Publisher
(
  pub_id int 					NOT NULL AUTO_INCREMENT,
  name VARCHAR(144)  			NULL,
  pub_username VARCHAR(144)   	UNIQUE,
  pub_password varchar(144)  	NULL,
  PRIMARY KEY (pub_id)
);
insert into Publisher (name,pub_username,pub_password)values('Penguin Random House','PublisherUserName1','password');
insert into Publisher (name,pub_username,pub_password)values('Hachette Livre','PublisherUserName2','password');
insert into Publisher (name,pub_username,pub_password)values('HarperCollins','PublisherUserName3','password');
CREATE TABLE Librarian
(
  lib_id int(144) 			NOT NULL AUTO_INCREMENT,
  name VARCHAR(144) 		NOT NULL,
  surname VARCHAR(144) 		NOT NULL ,
  lib_username VARCHAR(144) NOT NULL UNIQUE,
  lib_password VARCHAR(144) NOT NULL,
  PRIMARY KEY (lib_id)
);
insert into Librarian(lib_id,name, surname, lib_username,lib_password)values(1000,'ZOYA','JAVED','username','password');

CREATE TABLE Book
(
  book_id int(10) 			AUTO_INCREMENT,
  genre VARCHAR(144) 		 NULL,
  author_name VARCHAR(144) 	 NULL,
  title VARCHAR(144) 		 NULL ,
  status VARCHAR(144) 		 NULL,
  times_borrowed INT 	 	 NULL,
  publication_date VARCHAR(144)  NULL,
  requested VARCHAR(144)  	NULL,
  pub_id int(144)  			NULL,
  lib_id int(144)  			NULL,
  PRIMARY KEY (book_id),
  FOREIGN KEY (pub_id) REFERENCES Publisher(pub_id),
  FOREIGN KEY (lib_id) REFERENCES Librarian(lib_id)
);
insert into book(genre,author_name,title,status,publication_date,times_borrowed,requested,pub_id, lib_id)values('Drama','JK Rowling','Harry Potter Cursed Child','Overdue',	'2018-10-12',12, 'not requested',0002,1000);
insert into book(genre,author_name,title,status,publication_date,times_borrowed,requested,pub_id, lib_id)values('Scifi','Dan Brown','Angels And Demons','Borrowed',	'2018-10-12',15, 'not requested',0002,1000);
insert into book(book_id,genre,author_name,title,status,publication_date,times_borrowed,requested, pub_id, lib_id)values(262363,'Drama','Jk Rowling','Never Be The Same','Overdue','2018-10-12',13,'not requested',0002,1000);
insert into book(genre,author_name,title,status,publication_date,times_borrowed,requested,pub_id, lib_id)values('Scifi','Dan Brown','Angels','Overdue',	'2018-10-12',15, 'not requested',0002,1000);

CREATE TABLE Student
(
  student_id int(21)		 NOT NULL AUTO_INCREMENT,
  name varchar(64)		 	 NULL,
  surname varchar(64) 		 NULL,
  st_username  varchar(64) 	 UNIQUE,
  st_password  varchar(64) 	 NULL,
  date_expiry date 			 NULL,
  date_start date 			 NULL,
  book_id int(144) 			null,
  PRIMARY KEY (student_id)
);
insert into Student(name, surname, st_username, st_password, date_start, date_expiry, book_id)values('HARIS','OZAIR','stUserName1','password','2018-10-12','2019-12-19',00002);
insert into Student(name, surname, st_username, st_password, date_start, date_expiry, book_id)values('OZAIR','HARIS','stUserName2','password','2018-02-12','2019-02-19',00004);
insert into Student(name, surname, st_username, st_password, date_start, date_expiry, book_id)values('AWAIS','LODHI','stUserName3','password','2018-11-12','2019-11-19',00006);
insert into Student(name, surname, st_username, st_password, date_start, date_expiry, book_id)values('AWA','LOD','stUserName4','password','2018-11-12','2019-11-19',262364);

CREATE TABLE borrowed_by
(
  borrow_id int (21)NOT NULL AUTO_INCREMENT,
  date_issue DATE NOT NULL,
  date_returned DATE,
  fine_amnt INT,
  student_id int(21) NOT NULL,
  book_id int(10) NOT NULL,
  lib_id int(144),
  PRIMARY KEY (borrow_id),
  FOREIGN KEY (student_id) REFERENCES Student(student_id),
  FOREIGN KEY (book_id) REFERENCES Book(book_id),
  FOREIGN KEY (lib_id) REFERENCES Librarian(lib_id)
);

