USE api_test;

INSERT INTO person VALUES ('123','Joaquin','Alonso Saiz',45);
INSERT INTO person VALUES ('456','Manuel','Sanchez Gomis',45);
INSERT INTO person VALUES ('222','Pedro','Lopez Lopez',45);

GRANT ALL PRIVILEGES ON api_test.* TO manolo@'%' IDENTIFIED BY '1111';