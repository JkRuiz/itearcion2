
/*
Probar las foreign key en la tabla PEDIDO, donde EMAIL_USER es la primary key de la tabla USUARIOS y 
*/

INSERT INTO USUARIOS VALUES ('jc.ruiz@sa.com', 'Juan Camilo', 100000);

/*
Caso en que la foreign key exista
*/
INSERT INTO PEDIDO VALUES (1, 100, '23/05/2016', 'jc.ruiz@sa.com', 1, 0, '12:03:10 AM');

/*
Caso en que la foreign key no exista
*/
INSERT INTO PEDIDO VALUES (2, 100, '23/05/2016', 'otro@sa.com', 1, 0, '12:03:10 AM');

/*
Para borrar, primero se boora PEDIDO luego USUARIO
*/
DELETE FROM PEDIDO WHERE NUM_PEDIDO = 1;
DELETE FROM USUARIOS WHERE EMAIL = 'jc.ruiz@sa.com';



