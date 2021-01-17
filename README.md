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

CREATE TABLE Librarian
(
  lib_id int(144) 			NOT NULL AUTO_INCREMENT,
  name VARCHAR(144) 		NOT NULL,
  surname VARCHAR(144) 		NOT NULL ,
  lib_username VARCHAR(144) NOT NULL UNIQUE,
  lib_password VARCHAR(144) NOT NULL,
  PRIMARY KEY (lib_id)
);

CREATE TABLE Student
(
  student_id int(21)		 NOT NULL AUTO_INCREMENT,
  name varchar(64)		 	 NULL,
  surname varchar(64) 		 NULL,
  st_username  varchar(64) 	 UNIQUE,
  st_password  varchar(64) 	 NULL,
  PRIMARY KEY (student_id)
);

CREATE TABLE Book
(
  book_id int(10) 			AUTO_INCREMENT,
  pub_id int 			NOT NULL,
  genre VARCHAR(144) 		 NULL,
  author_name VARCHAR(144) 	 NULL,
  title VARCHAR(144) 		 NULL ,
  status VARCHAR(144) 		 NULL,
  times_borrowed INT 	 	 NULL,
  publication_date VARCHAR(144)  NULL,
  requested VARCHAR(144)  	NULL,
  FOREIGN KEY (pub_id) REFERENCES Publisher(pub_id),
  PRIMARY KEY (book_id)
);


CREATE TABLE borrowed_by
(
  borrow_id int (21)NOT 	NULL AUTO_INCREMENT,
  date_returned DATE 		NULL,
  date_start date 			NULL,
  student_id int(21) 		NOT NULL,
  lib_id int (21) 			NULL,
  book_id int(10) 			NOT NULL,
  PRIMARY KEY (borrow_id),
  FOREIGN KEY (student_id) REFERENCES Student(student_id),
  FOREIGN KEY (lib_id) REFERENCES Librarian(lib_id),
  FOREIGN KEY (book_id) REFERENCES Book(book_id)
);

CREATE TABLE fine
(
  fine_id int (21) 			NOT NULL AUTO_INCREMENT,
  fine_amount int (21) 		NULL,
  borrow_id int (21) 		NULL,
  lib_id int (21) 			NULL,
  PRIMARY KEY (fine_id),
  FOREIGN KEY (lib_id) REFERENCES Librarian(lib_id),
  FOREIGN KEY (borrow_id) REFERENCES borrowed_by(borrow_id)
);

CREATE TABLE User
(
  id BIGINT NOT NULL AUTO_INCREMENT,
  user_name VARCHAR(255) NOT NULL unique,
  password VARCHAR(255) NOT NULL,
  roles VARCHAR(255) NOT NULL,
  PRIMARY KEY (id)
);

