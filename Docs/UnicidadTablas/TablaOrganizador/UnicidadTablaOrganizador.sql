
/* Se crea un nuevo usuario para poder crear un organizador
*/
INSERT INTO USUARIOS VALUES ('ElOrganizador@gmail.com', 'Don carlos', 1020202020);
INSERT INTO ORGANIZADOR VALUES ('ElOrganizador@gmail.com', 'organiz96', 'claveSecreta', 'Don carlos', 1020202020);
INSERT INTO ORGANIZADOR VALUES ('ElOrganizador@gmail.com', 'otroOrganiz', 'otraClave', 'Otro carlos', 1020202020);

SELECT * FROM ORGANIZADOR;
