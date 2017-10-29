CREATE TABLE EQUIVALENTES (
    INGREDIENTE1 VARCHAR(20) NOT NULL,
    INGREDIENTE2 VARCHAR(20) NOT NULL,
    RESTAURANTE_NOMBRE VARCHAR(100) NOT NULL,
    PRIMARY KEY (INGREDIENTE1, INGREDIENTE2),
    FOREIGN KEY (INGREDIENTE1) REFERENCES INGREDIENTES(NOMBRE),
    FOREIGN KEY (INGREDIENTE2) REFERENCES INGREDIENTES(NOMBRE),
    FOREIGN KEY (RESTAURANTE_NOMBRE) REFERENCES RESTAURANTES(NOMBRE)
);

CREATE TABLE PEDIDO (
    NUM_PEDIDO NUMBER(38,0) NOT NULL,
    PRECIO NUMBER(38,0) NOT NULL,
    EMAIL_USER VARCHAR(50) NOT NULL,
    FECHA DATE NOT NULL,
    PAGADO NUMBER(38,0) NOT NULL,
    ENTREGADO NUMBER(38,0) NOT NULL,
    HORA VARCHAR(30) NOT NULL,
    CAMBIO VARCHAR(30) NOT NULL,
    PRIMARY KEY (NUM_PEDIDO),
    FOREIGN KEY (EMAIL_USER) REFERENCES USUARIOS(EMAIL)
);

CREATE TABLE PEDIDO_MENUS (
    NUM_PEDIDO NUMBER(38,0) NOT NULL,
    ID_MENU NUMBER(38,0) NOT NULL,
    PRIMARY KEY (ID_MENU, NUM_PEDIDO),
    FOREIGN KEY (ID_MENU) REFERENCES MENU(ID_MENU),
    FOREIGN KEY (NUM_PEDIDO) REFERENCES PEDIDO(NUM_PEDIDO)
);

CREATE TABLE PEDIDO_PLATO (
    NUM_PEDIDO NUMBER(38,0) NOT NULL,
    ID_PLATO NUMBER(38,0) NOT NULL,
    PRIMARY KEY (ID_PLATO, NUM_PEDIDO),
    FOREIGN KEY (ID_PLATO) REFERENCES PLATO(ID_PLATO),
    FOREIGN KEY (NUM_PEDIDO) REFERENCES PEDIDO(NUM_PEDIDO)
);