
/*
Probar las foreign key en la tabla RESERVA, donde EMAIL_CLIENTE es la primary key de la tabla CLIENTEFR y 
ID_MENU la primary key de MENU
*/

INSERT INTO USUARIOS VALUES ('jc.ruiz@sa.com', 'Juan Camilo', 100000);

INSERT INTO CATEGORIA VALUES ('FUERTE');

INSERT INTO CLIENTEFR VALUES ('jc.ruiz@sa.com', 'laclave', 'jc.ruiz', 2000, 'FUERTE', 'Juan Camilo', 100000);

INSERT INTO ZONA (NOMBRE, CAPACIDAD, ABIERTO, APTODISCAPACIDAD, DESCRIPCIONTECNICA)
VALUES ('ZONA COLOMBIA', 250, 1, 1, 'Zona tematica de Colombia, comida y ambiente tipicos');

INSERT INTO RESTAURANTES (NOMBRE,REPRESENTANTE,TIPOCOMIDA,ZONA, VALOR_COSTOS,VALOR_VENTAS)
VALUES ('LA BANDEJA PAISA', 'JORGE', 'COMIDA COLOMBIANA', 'ZONA COLOMBIA', 3000,8000);

INSERT INTO MENU VALUES ('Combo 1', 1000,3000, 20, 30,'LA BANDEJA PAISA', 1);
/*
Caso en que la foreign key exista
*/
INSERT INTO RESERVA VALUES ('23/05/2016', 100, 'ZONA COLOMBIA', 1, 'jc.ruiz@sa.com');

/*
Caso en que la foreign key EMAIL_CLIENTE no exista
*/
INSERT INTO RESERVA VALUES ('22/02/2016', 100, 'ZONA COLOMBIA', 1, 'otro@sa.com');

/*
Caso en que la foreign key ID_MENU no exista
*/
INSERT INTO RESERVA VALUES ('21/01/2016', 100, 'ZONA COLOMBIA', 2,  'jc.ruiz@sa.com');

/*
Para borrar, primero se boora RESERVA luego MENU, luego RESTAURANTE, luego ZONA, luego CLIENTE, luego USUARIO
*/
DELETE FROM RESERVA WHERE ZONA = 'ZONA COLOMBIA';
DELETE FROM MENU WHERE ID_MENU = 1;
DELETE FROM RESTAURANTES WHERE NOMBRE = 'LA BANDEJA PAISA';
DELETE FROM ZONA WHERE NOMBRE = 'ZONA COLOMBIA';
DELETE FROM CLIENTEFR WHERE EMAIL = 'jc.ruiz@sa.com';
DELETE FROM USUARIOS WHERE EMAIL = 'jc.ruiz@sa.com';



