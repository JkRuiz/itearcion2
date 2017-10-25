
/*
El atributo ABIERTO y el APTODISCAPACIDAD deben ser 1 o 0
*/


/*
Aqui cumplen
*/
INSERT INTO ZONA VALUES ('ZONA BRASIL', 100, 1, 0, 'Zona bonita');


/*
Aqui no cumple ABIERTO
*/
INSERT INTO ZONA VALUES ('ZONA COLOMBIA', 100, 4, 0, 'Zona bonita');
/*
Aqui no cumple APTODISCAPACIDAD
*/
INSERT INTO ZONA VALUES ('ZONA ECUADOR', 100, 1, 5, 'Zona bonita');

/*
Se borra
*/
DELETE FROM ZONA WHERE NOMBRE = 'ZONA BRASIL';