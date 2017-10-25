
/*
Probar las foreign key en la tabla ORGANIZADOR, donde EMAIL es la primary key de la tabla USUARIOS 
*/

INSERT INTO USUARIOS VALUES ('jc.ruiz@sa.com', 'Juan Camilo', 100000);

INSERT INTO CATEGORIA VALUES ('FUERTE');

/*
Caso en que ambas foreign key existan
*/
INSERT INTO ORGANIZADOR VALUES ('jc.ruiz@sa.com', 'jc.ruiz', 'laclave','Juan Camilo', 100000);

/*
Caso en que la foreign key de USUARIOS no exista
*/
INSERT INTO ORGANIZADOR VALUES ('otro@sa.com', 'jc.ruiz', 'laclave','Juan Camilo', 100000);

/*
Para borrar, primero se boora MENU luego RESTAURANTE y finalmente ZONA
*/
DELETE FROM ORGANIZADOR WHERE EMAIL = 'jc.ruiz@sa.com';
DELETE FROM USUARIOS WHERE EMAIL = 'jc.ruiz@sa.com';



