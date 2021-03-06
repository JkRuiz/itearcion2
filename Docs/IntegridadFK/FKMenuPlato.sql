
/*
Probar las foreign key en la tabla MENU_PLATO, donde ID_PLATO es la primary key de la tabla PLATO y 
ID_MENU la primary key de MENU
*/

INSERT INTO CATEGORIA VALUES ('FUERTE');

INSERT INTO ZONA (NOMBRE, CAPACIDAD, ABIERTO, APTODISCAPACIDAD, DESCRIPCIONTECNICA)
VALUES ('ZONA COLOMBIA', 250, 1, 1, 'Zona tematica de Colombia, comida y ambiente tipicos');

INSERT INTO RESTAURANTES (NOMBRE,REPRESENTANTE,TIPOCOMIDA,ZONA, VALOR_COSTOS,VALOR_VENTAS)
VALUES ('LA BANDEJA PAISA', 'JORGE', 'COMIDA COLOMBIANA', 'ZONA COLOMBIA', 3000,8000);

INSERT INTO MENU VALUES ('Combo 1', 1000,3000, 20, 30,'LA BANDEJA PAISA', 1);

INSERT INTO PLATO (NOMBRE, DESCRIPCION, TRADUCCION, TIEMPOPREPARACION, COSTOPRODUCCION, PRECIOVENTA, RESTAURANTE, ID_PLATO,
VENDIDOS, DISPONIBLES, TIPO, CATEGORIA)
VALUES ('FILET MIGNON', 'DELICIOSO FILET SERVIDO CON ARROZ Y ENSALADA', 'DELIIOUS MEAT SERVED WITH RICE AND SALAD',
2, 400,2000, 'LA BANDEJA PAISA', 1, 10, 30, 'CARNE', 'FUERTE');

/*
Caso en que la foreign key exista
*/
INSERT INTO MENU_PLATO VALUES (1,1);

/*
Caso en que la foreign key ID_PLATO no exista
*/
INSERT INTO MENU_PLATO VALUES (2,1);

/*
Caso en que la foreign key ID_MENU no exista
*/
INSERT INTO MENU_PLATO VALUES (1,2);

/*
Para borrar, primero se boora MENU_PLATO luego MENU, luego PLATO, luego RESTAURANTES, luego ZONA, luego CATEGORIA
*/
DELETE FROM MENU_PLATO WHERE ID_MENU = 1;
DELETE FROM MENU WHERE ID_MENU = 1;
DELETE FROM PLATO WHERE ID_PLATO = 1;
DELETE FROM RESTAURANTES WHERE NOMBRE = 'LA BANDEJA PAISA';
DELETE FROM ZONA WHERE NOMBRE = 'ZONA COLOMBIA';
DELETE FROM CATEGORIA;


