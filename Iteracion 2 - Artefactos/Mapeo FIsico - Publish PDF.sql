DROP DATABASE IF EXISTS PUBLISH_PDF;
CREATE DATABASE PUBLISH_PDF;
USE PUBLISH_PDF;

CREATE USER IF NOT EXISTS 'admin_publish-pdf'@'localhost' IDENTIFIED BY 'nimda';
GRANT ALL PRIVILEGES ON PUBLISH_PDF.* TO 'admin_publish-pdf'@'localhost' WITH GRANT OPTION;
FLUSH PRIVILEGES;

CREATE TABLE USUARIO (
    nombre_usuario VARCHAR(40),
    contraseña VARCHAR(40) NOT NULL,
    nombre VARCHAR(40) NOT NULL,
    descripcion VARCHAR(150),
    gustos VARCHAR(150),
    hobbies VARCHAR(150),
    foto VARCHAR(40),

    CONSTRAINT PK_USUARIO PRIMARY KEY (nombre_usuario)
);

CREATE TABLE ADMINISTRADOR (
    usuario VARCHAR(40),

    CONSTRAINT PK_ADMINISTRADOR PRIMARY KEY (usuario),
    CONSTRAINT FK_ADMINISTRADOR_USUARIO FOREIGN KEY (usuario) REFERENCES USUARIO (nombre_usuario)
);

CREATE TABLE EDITOR (
    usuario VARCHAR(40),

    CONSTRAINT PK_EDITOR PRIMARY KEY (usuario),
    CONSTRAINT FK_EDITOR_USUARIO FOREIGN KEY (usuario) REFERENCES USUARIO (nombre_usuario)
);

CREATE TABLE SUSCRIPTOR (
    usuario VARCHAR(40),

    CONSTRAINT PK_SUSCRIPTOR PRIMARY KEY (usuario),
    CONSTRAINT FK_SUSCRIPTOR_USUARIO FOREIGN KEY (usuario) REFERENCES USUARIO (nombre_usuario)
);

CREATE TABLE CUOTA_SUSCRIPCION (
    fecha_cuota DATE,
    administrador VARCHAR(40),
    porcentaje FLOAT NOT NULL,

    CONSTRAINT PK_CUOTA_SUSCRIPCION PRIMARY KEY (fecha_cuota),
    CONSTRAINT FK_CUOTA_SUSCRIPCION_ADMINISTRADOR FOREIGN KEY (administrador) REFERENCES ADMINISTRADOR (usuario)
);

CREATE TABLE ANUNCIO (
    anuncio_id INT AUTO_INCREMENT,
    administrador VARCHAR(40),
    fecha_publicado DATE NOT NULL,
    costo_dia DECIMAL(6, 2) NOT NULL,
    activo BOOLEAN NOT NULL,
    texto VARCHAR(150) NOT NULL,
    imagen VARCHAR(40),
    video VARCHAR(175),

    CONSTRAINT PK_ANUNCIO PRIMARY KEY (anuncio_id),
    CONSTRAINT FK_ANUNCIO_ADMINISTRADOR FOREIGN KEY (administrador) REFERENCES ADMINISTRADOR (usuario)
);

CREATE TABLE CATEGORIA (
    nombre VARCHAR(20),

    CONSTRAINT PK_CATEGORIA PRIMARY KEY (nombre)
);

CREATE TABLE REVISTA (
    revista_id INT AUTO_INCREMENT,
    editor VARCHAR(40),
    categoria VARCHAR(20),
    coste_mes DECIMAL(6, 2) NOT NULL,
    nombre VARCHAR(20) NOT NULL,
    descripcion VARCHAR(150),
    fecha_publicada DATE NOT NULL,
    comentarios_activos BOOLEAN,
    me_gusta_activos BOOLEAN,
    suscripciones_activas BOOLEAN,

    CONSTRAINT PK_REVISTA PRIMARY KEY (revista_id),
    CONSTRAINT FK_REVISTA_EDITOR FOREIGN KEY (editor) REFERENCES EDITOR (usuario),
    CONSTRAINT FK_REVISTA_CATEGORIA FOREIGN KEY (categoria) REFERENCES CATEGORIA (nombre)
);

CREATE TABLE NUMERO_REVISTA (
    numero_id INT,
    revista INT,
    archivo VARCHAR(40) NOT NULL,

    CONSTRAINT PK_NUMERO PRIMARY KEY (numero_id),
    CONSTRAINT FK_NUMERO_TO_REVISTA FOREIGN KEY (revista) REFERENCES REVISTA (revista_id)
);

CREATE TABLE COSTO_DIA_REVISTA (
    fecha_costo_dia DATE,
    revista INT,
    costo_dia DECIMAL(6, 2) NOT NULL,

    CONSTRAINT PK_COSTO_DIA PRIMARY KEY (fecha_costo_dia, revista),
    CONSTRAINT FK_COSTO_DIA_TO_REVISTA FOREIGN KEY (revista) REFERENCES REVISTA (revista_id)
);

CREATE TABLE ETIQUETA (
    nombre VARCHAR(20),

    CONSTRAINT PK_ETIQUETA PRIMARY KEY (nombre)
);

CREATE TABLE ETIQUETAS_ANUNCIO (
    anuncio INT,
    etiqueta VARCHAR(20),

    CONSTRAINT PK_ETIQUETAS_ANUNCIO PRIMARY KEY (anuncio, etiqueta),
    CONSTRAINT FK_ETIQUETAS_ANUNCIO_TO_ANUNCIO FOREIGN KEY (anuncio) REFERENCES ANUNCIO (anuncio_id),
    CONSTRAINT FK_ETIQUETAS_ANUNCIO_TO_ETIQUETA FOREIGN KEY (etiqueta) REFERENCES ETIQUETA (nombre)
);

CREATE TABLE ETIQUETAS_SUSCRIPTOR (
    suscriptor VARCHAR(40),
    etiqueta VARCHAR(20),

    CONSTRAINT PK_ETIQUETAS_SUSCRIPTOR PRIMARY KEY (suscriptor, etiqueta),
    CONSTRAINT FK_ETIQUETAS_SUSCRIPTOR_TO_SUSCRIPTOR FOREIGN KEY (suscriptor) REFERENCES SUSCRIPTOR (usuario),
    CONSTRAINT FK_ETIQUETAS_SUSCRIPTOR_TO_ETIQUETA FOREIGN KEY (etiqueta) REFERENCES ETIQUETA (nombre)
);

CREATE TABLE ETIQUETAS_REVISTA (
    revista INT,
    etiqueta VARCHAR(20),

    CONSTRAINT PK_ETIQUETAS_REVISTA PRIMARY KEY (revista, etiqueta),
    CONSTRAINT FK_ETIQUETAS_REVISTA_TO_REVISTA FOREIGN KEY (revista) REFERENCES REVISTA (revista_id),
    CONSTRAINT FK_ETIQUETAS_REVISTA_TO_ETIQUETA FOREIGN KEY (etiqueta) REFERENCES ETIQUETA (nombre)
);

CREATE TABLE COMENTARIOS (
    revista INT,
    suscriptor VARCHAR(40),
    comentario VARCHAR(150) NOT NULL,
    fecha DATE NOT NULL,

    CONSTRAINT PK_COMENTARIOS PRIMARY KEY (revista, suscriptor),
    CONSTRAINT FK_COMENTARIOS_REVISTA FOREIGN KEY (revista) REFERENCES REVISTA (revista_id),
    CONSTRAINT FK_COMENTARIOS_SUSCRIPTOR FOREIGN KEY (suscriptor) REFERENCES SUSCRIPTOR (usuario)
);

CREATE TABLE SUSCRIPCIONES (
    revista INT,
    suscriptor VARCHAR(40),
    fecha_suscripcion DATE NOT NULL,
    mensual BOOLEAN,

    CONSTRAINT PK_SUSCRIPCIONES PRIMARY KEY (revista, suscriptor),
    CONSTRAINT FK_SUSCRIPCIONES_REVISTA FOREIGN KEY (revista) REFERENCES REVISTA (revista_id),
    CONSTRAINT FK_SUSCRIPCIONES_SUSCRIPTOR FOREIGN KEY (suscriptor) REFERENCES SUSCRIPTOR (usuario)
);

CREATE TABLE ME_GUSTAS (
    revista INT,
    suscriptor VARCHAR(40),
    fecha DATE NOT NULL,

    CONSTRAINT PK_ME_GUSTAS PRIMARY KEY (revista, suscriptor),
    CONSTRAINT FK_ME_GUSTAS_REVISTA FOREIGN KEY (revista) REFERENCES REVISTA (revista_id),
    CONSTRAINT FK_ME_GUSTAS_SUSCRIPTOR FOREIGN KEY (suscriptor) REFERENCES SUSCRIPTOR (usuario)
);

CREATE TABLE PAGO_SUSCRIPCION (
    revista INT,
    suscriptor VARCHAR(40),
    fecha_pago DATE,
    costo DECIMAL(6, 2),

    CONSTRAINT PK_PAGO_SUSCRIPCION PRIMARY KEY (revista, suscriptor, fecha_pago),
    CONSTRAINT FK_PAGO_SUSCRIPCION_REVISTA FOREIGN KEY (revista) REFERENCES REVISTA (revista_id),
    CONSTRAINT FK_PAGO_SUSCRIPCION_SUSCRIPTOR FOREIGN KEY (suscriptor) REFERENCES SUSCRIPTOR (usuario)
);

INSERT INTO USUARIO VALUES ('tmp_sus', 'tmp', 'suscriptor', 'descripcion', 'gustos', 'hobbies', NULL),
    ('tmp_edi', 'tmp', 'editor', 'descripcion', 'gustos', 'hobbies', NULL),
    ('tmp_adm', 'tmp', 'administrador', 'descripcion', 'gustos', 'hobbies', NULL);

INSERT INTO SUSCRIPTOR VALUES ('tmp_sus');
INSERT INTO EDITOR VALUES ('tmp_edi');
INSERT INTO ADMINISTRADOR VALUES ('tmp_adm');

INSERT INTO CATEGORIA VALUES ('Cientifica'), ('Relatos'), ('Comic');
INSERT INTO REVISTA (editor, nombre, fecha_publicada, categoria, descripcion)
	VALUES ('tmp_edi', 'Investigacion...', '2021-10-22', 'Cientifica', 9.99, 'Investigacion sobre muchas cosas cientificas.'),
	('tmp_edi', 'Cuentos del hoy', '2021-09-28', 'Relatos', 12.35, 'Revista en la cual se publican distintos cuentos actuales.'),
    ('tmp_edi', 'Ultimate Spiderman', '2021-07-10', 'Comic', 0, 'Comic del superheroe spiderman.'),
    ('tmp_edi', 'Batman V Superman', '2021-08-16', 'Comic', 5.95, 'Comic de DC.');
    
INSERT INTO SUSCRIPCIONES VALUES (3, 'tmp_sus', '2021-10-10', 1), (4, 'tmp_sus', '2021-10-10', 1);

INSERT INTO ETIQUETA VALUES ('Spiderman'), ('Cuentos'), ('Ciencia'), ('Matematica'), ('Fisica'), ('Relato'), ('Accion'),
	('Drama'), ('Ficcion'), ('Ciencia Ficcion'), ('Marvel'), ('DC');
    
INSERT INTO ETIQUETAS_REVISTA VALUES (1, 'Ciencia'), (1, 'Matematica'), (1, 'Fisica'), (2, 'Relato'), (2, 'Drama'),
	(2, 'Cuentos'), (2, 'Ficcion'), (2, 'Ciencia Ficcion'), (3, 'Accion'), (3, 'Ciencia Ficcion'), (3, 'Marvel'),
    (3, 'Spiderman'), (3, 'Ficcion'), (4, 'DC'), (4, 'Accion'), (4, 'Ciencia Ficcion'), (4, 'Ficcion');
