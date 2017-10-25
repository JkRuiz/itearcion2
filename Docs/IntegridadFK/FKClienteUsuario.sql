
/*
Probar las foreign key en la tabla CLIENTE, donde EMAIL es la primary key de la tabla USUARIOS y PREFERENCIACATEGORIA
es la primary key de CATEGORIA
*/

INSERT INTO USUARIOS VALUES ('jc.ruiz@sa.com', 'Juan Camilo', 100000);

INSERT INTO CATEGORIA VALUES ('FUERTE');

/*
Caso en que ambas foreign key existan
*/
INSERT INTO CLIENTEFR VALUES ('jc.ruiz@sa.com', 'laclave', 'jc.ruiz', 2000, 'FUERTE', 'Juan Camilo', 100000);

/*
Caso en que la foreign key de USUARIOS no exista
*/
INSERT INTO CLIENTEFR VALUES ('otro@sa.com', 'laclave', 'jc.ruiz', 2000, 'FUERTE', 'Juan Camilo', 100000);

/*
Caso en que la foreign key de CATEGORIA no exista
*/
INSERT INTO CLIENTEFR VALUES ('otro@sa.com', 'laclave', 'jc.ruiz', 2000, 'ENTRADA', 'Juan Camilo', 100000);

/*
Para borrar, primero se boora LIENTE luego CATEGORIA y finalmente USUARIOS
*/
DELETE FROM CLIENTEFR WHERE EMAIL = 'jc.ruiz@sa.com';
DELETE FROM CATEGORIA WHERE NOMCATEGORIA = 'FUERTE';
DELETE FROM USUARIOS WHERE EMAIL = 'jc.ruiz@sa.com';



