CREATE TABLE PERSON
(
    personid int PRIMARY KEY NOT NULL AUTO_INCREMENT,
    username varchar(64) NOT NULL,
    firstname varchar(64),
    lastname varchar(64),
    birthdate DATE
);
CREATE UNIQUE INDEX PERSON_username_uindex ON PERSON (username);