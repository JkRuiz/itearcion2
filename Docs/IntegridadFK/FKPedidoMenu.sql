
/*
Probar las foreign key en la tabla PEDIDO_MENU, donde ID_MENU es la primary key de la tabla MENU y 
NUM_PEDIDO la primary key de PEDIDO
*/


INSERT INTO USUARIOS VALUES ('jc.ruiz@sa.com', 'Juan Camilo', 100000);

INSERT INTO PEDIDO VALUES (1, 100, '23/05/2016', 'jc.ruiz@sa.com', 1, 0, '12:03:10 AM');

INSERT INTO CATEGORIA VALUES ('FUERTE');

INSERT INTO ZONA (NOMBRE, CAPACIDAD, ABIERTO, APTODISCAPACIDAD, DESCRIPCIONTECNICA)
VALUES ('ZONA COLOMBIA', 250, 1, 1, 'Zona tematica de Colombia, comida y ambiente tipicos');

INSERT INTO RESTAURANTES (NOMBRE,REPRESENTANTE,TIPOCOMIDA,ZONA, VALOR_COSTOS,VALOR_VENTAS)
VALUES ('LA BANDEJA PAISA', 'JORGE', 'COMIDA COLOMBIANA', 'ZONA COLOMBIA', 3000,8000);

INSERT INTO MENU VALUES ('Combo 1', 1000,3000, 20, 30,'LA BANDEJA PAISA', 1);

/*
Caso en que las foreign key existan
*/
INSERT INTO PEDIDO_MENUS VALUES (1,1);

/*
Caso en que la foreign key ID_MENU no exista
*/
INSERT INTO PEDIDO_MENUS VALUES (1,2);

/*
Caso en que la foreign key NUM_PEDIDO no exista
*/
INSERT INTO PEDIDO_MENUS VALUES (2,1);

/*
Para borrar, primero se boora PEDIDO_MENUS luego MENU, luego RESTAURANTES, luego ZONA, luego CATEGORIA, luego PEDIDO, luego USUARIO
*/
DELETE FROM PEDIDO_MENUS;
DELETE FROM MENU WHERE ID_MENU = 1;
DELETE FROM RESTAURANTES WHERE NOMBRE = 'LA BANDEJA PAISA';
DELETE FROM ZONA WHERE NOMBRE = 'ZONA COLOMBIA';
DELETE FROM CATEGORIA;
DELETE FROM PEDIDO;
DELETE FROM USUARIOS;


