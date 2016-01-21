DROP TABLE Precedencia;
DROP TABLE InformeSemanal;
DROP TABLE Asignacion;
DROP TABLE Actividad;
DROP TABLE Etapa;
DROP TABLE Dedicacion;
DROP TABLE Vacaciones;
DROP TABLE Trabajador;
DROP TABLE Proyecto;
DROP TABLE DatosConfigurables;
DROP TABLE CategoriaRoles;
DROP TABLE Administrador;


CREATE TABLE Administrador (
 nick VARCHAR(20) NOT NULL,
 password VARCHAR(20) NOT NULL,
 PRIMARY KEY (nick)
);


CREATE TABLE Trabajador (
 nick VARCHAR(20) NOT NULL,
 password VARCHAR(20),
 categoria INT,
 PRIMARY KEY (nick)
);


CREATE TABLE CategoriaRoles (
 categoria INT NOT NULL,
 rol VARCHAR(20) NOT NULL,
 PRIMARY KEY (categoria,rol),
 CHECK( (rol='JEFE') OR (rol='ANALISTA') OR (rol='DISENADOR') OR (rol='ANALISTA-PROGRAMADOR')
	 OR (rol='RESPONSABLE-PRUEBAS') OR (rol='PROGRAMADOR') OR (rol='PROBADOR'))
);


CREATE TABLE DatosConfigurables (
 nombreVariable VARCHAR(40),
 valor INT NOT NULL,
 PRIMARY KEY (nombreVariable)
);


CREATE TABLE Proyecto (
 id INT NOT NULL,
 nombre VARCHAR(100),
 fechaInicio DATE,
 fechaFin DATE,
 nickJefe VARCHAR(20) NOT NULL,
 PRIMARY KEY (id)
);


CREATE TABLE Vacaciones (
 fechaSemana DATE NOT NULL,
 nickTrabajador VARCHAR(20) NOT NULL,
 PRIMARY KEY (fechaSemana,nickTrabajador),
 FOREIGN KEY (nickTrabajador) REFERENCES Trabajador (nick)
);



CREATE TABLE Dedicacion (
 nickTrabajador VARCHAR(20) NOT NULL,
 idProyecto INT NOT NULL,
 porcentaje INT NOT NULL,
 PRIMARY KEY (nickTrabajador,idProyecto),
 FOREIGN KEY (nickTrabajador) REFERENCES Trabajador (nick),
 FOREIGN KEY (idProyecto) REFERENCES Proyecto (id),
 CHECK((porcentaje>0) AND (porcentaje<101))
);


CREATE TABLE Etapa (
 idProyecto INT NOT NULL,
 id INT NOT NULL,
 nombre VARCHAR(100),
 PRIMARY KEY (id,idProyecto),
 FOREIGN KEY (idProyecto) REFERENCES Proyecto (id)
);


CREATE TABLE Actividad (
 idProyecto INT NOT NULL,
 idEtapa INT NOT NULL,
 id INT NOT NULL,
 nombre VARCHAR(100),
 fechaInicio DATE,
 fechaFin DATE,
 esfuerzoEstimado INT,
 rol VARCHAR(20),
 PRIMARY KEY (idProyecto,idEtapa, id),
 FOREIGN KEY (idEtapa,idProyecto) REFERENCES Etapa (id,idProyecto),
 CHECK( (rol='JEFE') OR (rol='ANALISTA') OR (rol='DISENADOR') OR (rol='ANALISTA-PROGRAMADOR')
	 OR (rol='RESPONSABLE-PRUEBAS') OR (rol='PROGRAMADOR') OR (rol='PROBADOR'))
);

CREATE TABLE Asignacion (
 nickTrabajador VARCHAR(20) NOT NULL,
 idProyecto INT NOT NULL,
 idEtapa INT NOT NULL,
 idActividad INT NOT NULL,
 PRIMARY KEY (nickTrabajador,idProyecto,idEtapa,idActividad),
 FOREIGN KEY (nickTrabajador) REFERENCES Trabajador (nick),
 FOREIGN KEY (idProyecto,idEtapa,idActividad) REFERENCES Actividad (idProyecto,idEtapa,id)
);


CREATE TABLE InformeSemanal (
 nickTrabajador VARCHAR(20) NOT NULL,
 idProyecto INT NOT NULL,
 idEtapa INT NOT NULL,
 idActividad INT NOT NULL,
 fechaSemana DATE NOT NULL,
 horasTarea1 INT,
 horasTarea2 INT,
 horasTarea3 INT,
 horasTarea4 INT,
 horasTarea5 INT,
 horasTarea6 INT,
 estado VARCHAR(20) NOT NULL,
 PRIMARY KEY (nickTrabajador,idProyecto,idEtapa,idActividad,fechaSemana),
 FOREIGN KEY (nickTrabajador) REFERENCES Trabajador (nick),
 FOREIGN KEY (idProyecto,idEtapa,idActividad) REFERENCES Actividad (idProyecto,idEtapa,id),
 CHECK((estado='ACEPTADO') OR (estado='RECHAZADO') OR (estado='PENDIENTE-APROBAR') OR (estado='PENDIENTE-ENVIAR'))
);

CREATE TABLE Precedencia (
 idProyectoPrecedente INT NOT NULL,
 idEtapaPrecedente INT NOT NULL,
 idActividadPrecedente INT NOT NULL,
 idProyectoPrecedida INT NOT NULL,
 idEtapaPrecedida INT NOT NULL,
 idActividadPrecedida INT NOT NULL,
 PRIMARY KEY (idProyectoPrecedente,idEtapaPrecedente,idActividadPrecedente,idProyectoPrecedida,idEtapaPrecedida,idActividadPrecedida),
 FOREIGN KEY (idProyectoPrecedente,idEtapaPrecedente,idActividadPrecedente) REFERENCES Actividad (idProyecto,idEtapa,id),
 FOREIGN KEY (idProyectoPrecedida,idEtapaPrecedida,idActividadPrecedida) REFERENCES Actividad (idProyecto,idEtapa,id)
);

INSERT INTO Administrador VALUES ('Administrador', 'password');
INSERT INTO Trabajador VALUES ('Trabajador1', 'password', 1);
INSERT INTO Trabajador VALUES ('Trabajador7', 'password', 1);
INSERT INTO Trabajador VALUES ('Trabajador2', 'password', 2);
INSERT INTO Trabajador VALUES ('Trabajador3', 'password', 3);
INSERT INTO Trabajador VALUES ('Trabajador4', 'password', 4);
INSERT INTO Trabajador VALUES ('Trabajador5', 'password', 4);
INSERT INTO Trabajador VALUES ('Trabajador8', 'password', 4);
INSERT INTO Trabajador VALUES ('Trabajador9', 'password', 2);
INSERT INTO Trabajador VALUES ('Trabajador10', 'password', 3);

INSERT INTO CategoriaRoles VALUES (1, 'JEFE');
INSERT INTO CategoriaRoles VALUES (2, 'ANALISTA');
INSERT INTO CategoriaRoles VALUES (3, 'DISENADOR');
INSERT INTO CategoriaRoles VALUES (3, 'ANALISTA-PROGRAMADOR');
INSERT INTO CategoriaRoles VALUES (3, 'RESPONSABLE-PRUEBAS');
INSERT INTO CategoriaRoles VALUES (4, 'PROGRAMADOR');
INSERT INTO CategoriaRoles VALUES (4, 'PROBADOR');

INSERT INTO DatosConfigurables VALUES ('ProyectosAlMismoTiempo', 2);


/*Proyecto 1*/

INSERT INTO Proyecto VALUES(1, 'Crear pagina web', '2015-10-05' , '2015-12-14','Trabajador7');
INSERT INTO Etapa VALUES(1, 1, 'Inicio');
INSERT INTO Etapa VALUES(1, 2, 'Elaboracion');
INSERT INTO Etapa VALUES(1, 3, 'Contruccion');
INSERT INTO Etapa VALUES(1, 4, 'Transicion');
INSERT INTO Actividad VALUES(1, 1, 1, 'Comprobar la viabalidad','2015-10-05', '2015-10-15', 4, 'ANALISTA');
INSERT INTO Actividad VALUES(1, 1, 2, 'Identificar requisitos', '2015-10-15', '2015-10-23', 10, 'ANALISTA');
INSERT INTO Actividad VALUES(1, 1, 3, 'Obtener informe conceptual de objetivos', NULL, '2015-10-23', 0, NULL); /* hito */
INSERT INTO Actividad VALUES(1, 2, 1, 'Diseno de la BD', '2015-10-19', '2015-10-28', 15, 'ANALISTA');
INSERT INTO Actividad VALUES(1, 2, 2, 'Diseno del modelo de dominio', '2015-10-31', '2015-11-2', 15, 'ANALISTA');
INSERT INTO Actividad VALUES(1, 2, 3, 'Disenos iteraticos', '2015-10-26', '2015-11-4', 24, 'DISENADOR');
INSERT INTO Actividad VALUES(1, 2, 4, 'Disenos de casos de uso','2015-11-4', '2015-11-12', 14, 'DISENADOR');
INSERT INTO Actividad VALUES(1, 2, 5, 'Comprobar la viabilidad', '2015-11-12', '2015-11-13', 14, 'DISENADOR');
INSERT INTO Actividad VALUES(1, 2, 6, 'Informes Diseno aplicacion', NULL, '2015-11-13', 0, NULL); /* hito */
INSERT INTO Actividad VALUES(1, 3, 1, 'Programar la aplicacion', '2015-11-13','2015-11-30', 230, 'PROGRAMADOR');
INSERT INTO Actividad VALUES(1, 3, 2, 'Crear clases de prueba', '2015-11-13', '2015-11-30', 60, 'PROBADOR');
INSERT INTO Actividad VALUES(1, 3, 3, 'Comprobar la concordancia codigo-arquitectura', '2015-11-30', '2015-12-05', 14, 'ANALISTA-PROGRAMADOR');
INSERT INTO Actividad VALUES(1, 3, 4, 'Aplicacion operativa', NULL, '2015-12-05', 0, NULL); /* hito */
INSERT INTO Actividad VALUES(1, 4, 1, 'Integrar la aplicacion', '2015-11-05', '2015-12-09', 13, 'ANALISTA-PROGRAMADOR');
INSERT INTO Actividad VALUES(1, 4, 2, 'Corregir fallos', '2015-12-09', '2015-12-17', 30, 'PROGRAMADOR');
INSERT INTO Actividad VALUES(1, 4, 3, 'Crear Guia de Usuario', '2015-12-17', '2015-12-19', 14, 'ANALISTA-PROGRAMADOR');
INSERT INTO Actividad VALUES(1, 4, 4, 'Aplicacion lista para entorno real', NULL, '2015-12-20', 0, NULL); /* hito */

INSERT INTO InformeSemanal VALUES('Trabajador1', 1, 1, 1, '2015-10-05',0,2,0,0,0,0,'ACEPTADO');
INSERT INTO InformeSemanal VALUES('Trabajador1', 1, 1, 1, '2015-10-12',0,0,0,0,6,0,'ACEPTADO');
INSERT INTO InformeSemanal VALUES('Trabajador1', 1, 1, 2, '2015-10-12',3,0,3,5,0,0,'ACEPTADO');
INSERT INTO InformeSemanal VALUES('Trabajador2', 1, 1, 2, '2015-10-12',2,0,2,2,0,0,'ACEPTADO');
INSERT INTO InformeSemanal VALUES('Trabajador2', 1, 2, 1, '2015-10-19',6,0,0,34,0,0,'ACEPTADO');
INSERT INTO InformeSemanal VALUES('Trabajador2', 1, 2, 2, '2015-10-26',0,0,3,0,2,0,'ACEPTADO');
INSERT INTO InformeSemanal VALUES('Trabajador2', 1, 2, 3, '2015-10-26',0,0,5,0,20,0,'ACEPTADO');
INSERT INTO InformeSemanal VALUES('Trabajador3', 1, 2, 3, '2015-10-26',0,0,3,0,30,0,'ACEPTADO');
INSERT INTO InformeSemanal VALUES('Trabajador3', 1, 2, 4, '2015-11-2',0,3,0,3,0,0,'ACEPTADO');
INSERT INTO InformeSemanal VALUES('Trabajador1', 1, 2, 5, '2015-11-9',0,0,0,0,0,5,'ACEPTADO');
INSERT INTO InformeSemanal VALUES('Trabajador1', 1, 3, 1, '2015-11-9',0,0,0,0,30,0,'ACEPTADO');
INSERT INTO InformeSemanal VALUES('Trabajador2', 1, 3, 1, '2015-11-9',0,0,0,0,40,0,'ACEPTADO');
INSERT INTO InformeSemanal VALUES('Trabajador3', 1, 3, 1, '2015-11-9',0,0,0,0,40,0,'ACEPTADO');
INSERT INTO InformeSemanal VALUES('Trabajador4', 1, 3, 1, '2015-11-9',0,0,0,0,20,0,'ACEPTADO');
INSERT INTO InformeSemanal VALUES('Trabajador1', 1, 3, 1, '2015-11-16',0,0,0,0,20,0,'ACEPTADO');
INSERT INTO InformeSemanal VALUES('Trabajador2', 1, 3, 1, '2015-11-16',0,0,0,0,10,0,'ACEPTADO');
INSERT INTO InformeSemanal VALUES('Trabajador3', 1, 3, 1, '2015-11-16',0,0,0,0,30,0,'ACEPTADO');
INSERT INTO InformeSemanal VALUES('Trabajador4', 1, 3, 1, '2015-11-16',0,0,0,0,20,0,'ACEPTADO');
INSERT INTO InformeSemanal VALUES('Trabajador4', 1, 3, 2, '2015-11-16',0,0,0,0,20,3,'ACEPTADO');
INSERT INTO InformeSemanal VALUES('Trabajador4', 1, 3, 2, '2015-11-23',0,0,0,0,20,3,'ACEPTADO');
INSERT INTO InformeSemanal VALUES('Trabajador3', 1, 3, 3, '2015-11-30',3,2,0,2,0,2,'ACEPTADO');
INSERT INTO InformeSemanal VALUES('Trabajador4', 1, 4, 1, '2015-11-30',2,0,2,0,3,0,'ACEPTADO');
INSERT INTO InformeSemanal VALUES('Trabajador4', 1, 4, 2, '2015-12-07',4,0,2,0,6,0,'ACEPTADO');
INSERT INTO InformeSemanal VALUES('Trabajador4', 1, 4, 3, '2015-12-14',1,1,1,5,6,1,'ACEPTADO');

INSERT INTO Precedencia VALUES(1, 1, 1, 1, 1, 2);
INSERT INTO Precedencia VALUES(1, 1, 2, 1, 1, 3);
INSERT INTO Precedencia VALUES(1, 1, 3, 1, 2, 1);
INSERT INTO Precedencia VALUES(1, 2, 1, 1, 2, 2);
INSERT INTO Precedencia VALUES(1, 2, 1, 1, 2, 3);
INSERT INTO Precedencia VALUES(1, 2, 1, 1, 2, 4);
INSERT INTO Precedencia VALUES(1, 2, 2, 1, 2, 5);
INSERT INTO Precedencia VALUES(1, 2, 3, 1, 2, 5);
INSERT INTO Precedencia VALUES(1, 2, 4, 1, 2, 5);
INSERT INTO Precedencia VALUES(1, 2, 5, 1, 2, 6);
INSERT INTO Precedencia VALUES(1, 2, 6, 1, 3, 1);
INSERT INTO Precedencia VALUES(1, 2, 6, 1, 3, 2);
INSERT INTO Precedencia VALUES(1, 3, 1, 1, 3, 3);
INSERT INTO Precedencia VALUES(1, 3, 2, 1, 3, 3);
INSERT INTO Precedencia VALUES(1, 3, 3, 1, 3, 4);
INSERT INTO Precedencia VALUES(1, 3, 4, 1, 4, 1);
INSERT INTO Precedencia VALUES(1, 4, 1, 1, 4, 2);
INSERT INTO Precedencia VALUES(1, 4, 2, 1, 4, 3);
INSERT INTO Precedencia VALUES(1, 4, 3, 1, 4, 4);

INSERT INTO Dedicacion VALUES('Trabajador1', 1, 50);
INSERT INTO Dedicacion VALUES('Trabajador2', 1, 50);
INSERT INTO Dedicacion VALUES('Trabajador3', 1, 50);
INSERT INTO Dedicacion VALUES('Trabajador4', 1, 50);

INSERT INTO Asignacion VALUES('Trabajador1', 1, 1, 1);
INSERT INTO Asignacion VALUES('Trabajador1', 1, 1, 2);
INSERT INTO Asignacion VALUES('Trabajador2', 1, 1, 2);
INSERT INTO Asignacion VALUES('Trabajador2', 1, 2, 1);
INSERT INTO Asignacion VALUES('Trabajador2', 1, 2, 2);
INSERT INTO Asignacion VALUES('Trabajador2', 1, 2, 3);
INSERT INTO Asignacion VALUES('Trabajador3', 1, 2, 3);
INSERT INTO Asignacion VALUES('Trabajador3', 1, 2, 4);
INSERT INTO Asignacion VALUES('Trabajador1', 1, 2, 5);
INSERT INTO Asignacion VALUES('Trabajador1', 1, 3, 1);
INSERT INTO Asignacion VALUES('Trabajador2', 1, 3, 1);
INSERT INTO Asignacion VALUES('Trabajador3', 1, 3, 1);
INSERT INTO Asignacion VALUES('Trabajador4', 1, 3, 1);
INSERT INTO Asignacion VALUES('Trabajador4', 1, 3, 2);
INSERT INTO Asignacion VALUES('Trabajador3', 1, 3, 3);
INSERT INTO Asignacion VALUES('Trabajador4', 1, 4, 1);
INSERT INTO Asignacion VALUES('Trabajador4', 1, 4, 2);
INSERT INTO Asignacion VALUES('Trabajador4', 1, 4, 3);

/* Proyecto 2*/
INSERT INTO Proyecto VALUES(2, 'Programa para intel S.A.', '2015-10-05' , '2015-12-14', 'Trabajador1');
INSERT INTO Etapa VALUES(2, 1, 'Inicio');
INSERT INTO Etapa VALUES(2, 2, 'Elaboracion');
INSERT INTO Etapa VALUES(2, 3, 'Contruccion');
INSERT INTO Etapa VALUES(2, 4, 'Transicion');
INSERT INTO Actividad VALUES(2, 1, 1, 'Comprobar la viabalidad','2015-10-05', '2015-10-15', 4, 'ANALISTA');
INSERT INTO Actividad VALUES(2, 1, 2, 'Identificar requisitos', '2015-10-15', '2015-10-23', 10, 'ANALISTA');
INSERT INTO Actividad VALUES(2, 1, 3, 'Obtener informe conceptual de objetivos', NULL, '2015-10-23', 0, NULL); /* hito */
INSERT INTO Actividad VALUES(2, 2, 1, 'Diseno de la BD', '2015-10-19', '2015-10-28', 15, 'ANALISTA');
INSERT INTO Actividad VALUES(2, 2, 2, 'Diseno del modelo de dominio', '2015-10-31', '2015-11-2', 15, 'ANALISTA');
INSERT INTO Actividad VALUES(2, 2, 3, 'Disenos iteraticos', '2015-10-26', '2015-11-4', 24, 'DISENADOR');
INSERT INTO Actividad VALUES(2, 2, 4, 'Disenos de casos de uso','2015-11-4', '2015-11-12', 14, 'DISENADOR');
INSERT INTO Actividad VALUES(2, 2, 5, 'Comprobar la viabilidad', '2015-11-12', '2015-11-13', 14, 'DISENADOR');
INSERT INTO Actividad VALUES(2, 2, 6, 'Informes Diseno aplicacion', NULL, '2015-11-13', 0, NULL); /* hito */
INSERT INTO Actividad VALUES(2, 3, 1, 'Programar la aplicacion', '2015-11-13','2015-11-30', 230, 'PROGRAMADOR');
INSERT INTO Actividad VALUES(2, 3, 2, 'Crear clases de prueba', '2015-11-13', '2015-11-30', 60, 'PROBADOR');
INSERT INTO Actividad VALUES(2, 3, 3, 'Comprobar la concordancia codigo-arquitectura', '2015-11-30', '2015-12-05', 14, 'ANALISTA-PROGRAMADOR');
INSERT INTO Actividad VALUES(2, 3, 4, 'Aplicacion operativa', NULL, '2015-12-05', 0, NULL); /* hito */
INSERT INTO Actividad VALUES(2, 4, 1, 'Integrar la aplicacion', '2015-11-05', '2015-12-09', 13, 'ANALISTA-PROGRAMADOR');
INSERT INTO Actividad VALUES(2, 4, 2, 'Corregir fallos', '2015-12-09', '2015-12-17', 30, 'PROGRAMADOR');
INSERT INTO Actividad VALUES(2, 4, 3, 'Crear Guia de Usuario', '2015-12-17', '2015-12-19', 14, 'ANALISTA-PROGRAMADOR');
INSERT INTO Actividad VALUES(2, 4, 4, 'Aplicacion lista para entorno real', NULL, '2015-12-20', 0, NULL); /* hito */

INSERT INTO InformeSemanal VALUES('Trabajador7', 2, 1, 1, '2015-10-05',0,2,0,0,0,0,'ACEPTADO');
INSERT INTO InformeSemanal VALUES('Trabajador7', 2, 1, 1, '2015-10-12',0,0,0,0,6,0,'ACEPTADO');
INSERT INTO InformeSemanal VALUES('Trabajador7', 2, 1, 2, '2015-10-12',3,0,3,5,0,0,'ACEPTADO');
INSERT INTO InformeSemanal VALUES('Trabajador9', 2, 1, 2, '2015-10-12',2,0,2,2,0,0,'ACEPTADO');
INSERT INTO InformeSemanal VALUES('Trabajador9', 2, 2, 1, '2015-10-19',6,0,0,34,0,0,'ACEPTADO');
INSERT INTO InformeSemanal VALUES('Trabajador9', 2, 2, 2, '2015-10-26',0,0,3,0,2,0,'ACEPTADO');
INSERT INTO InformeSemanal VALUES('Trabajador9', 2, 2, 3, '2015-10-26',0,0,5,0,20,0,'ACEPTADO');
INSERT INTO InformeSemanal VALUES('Trabajador10', 2, 2, 3, '2015-10-26',0,0,3,0,30,0,'ACEPTADO');
INSERT INTO InformeSemanal VALUES('Trabajador10', 2, 2, 4, '2015-11-2',0,3,0,3,0,0,'ACEPTADO');
INSERT INTO InformeSemanal VALUES('Trabajador7', 2, 2, 5, '2015-11-9',0,0,0,0,0,5,'ACEPTADO');
INSERT INTO InformeSemanal VALUES('Trabajador7', 2, 3, 1, '2015-11-9',0,0,0,0,30,0,'ACEPTADO');
INSERT INTO InformeSemanal VALUES('Trabajador9', 2, 3, 1, '2015-11-9',0,0,0,0,40,0,'ACEPTADO');
INSERT INTO InformeSemanal VALUES('Trabajador10', 2, 3, 1, '2015-11-9',0,0,0,0,40,0,'ACEPTADO');
INSERT INTO InformeSemanal VALUES('Trabajador8', 2, 3, 1, '2015-11-9',0,0,0,0,20,0,'ACEPTADO');
INSERT INTO InformeSemanal VALUES('Trabajador7', 2, 3, 1, '2015-11-16',0,0,0,0,20,0,'ACEPTADO');
INSERT INTO InformeSemanal VALUES('Trabajador9', 2, 3, 1, '2015-11-16',0,0,0,0,10,0,'ACEPTADO');
INSERT INTO InformeSemanal VALUES('Trabajador10', 2, 3, 1, '2015-11-16',0,0,0,0,30,0,'ACEPTADO');
INSERT INTO InformeSemanal VALUES('Trabajador8', 2, 3, 1, '2015-11-16',0,0,0,0,20,0,'ACEPTADO');
INSERT INTO InformeSemanal VALUES('Trabajador8', 2, 3, 2, '2015-11-16',0,0,0,0,20,3,'ACEPTADO');
INSERT INTO InformeSemanal VALUES('Trabajador8', 2, 3, 2, '2015-11-23',0,0,0,0,20,3,'ACEPTADO');
INSERT INTO InformeSemanal VALUES('Trabajador10', 2, 3, 3, '2015-11-30',3,2,0,2,0,2,'ACEPTADO');
INSERT INTO InformeSemanal VALUES('Trabajador8', 2, 4, 1, '2015-11-30',2,0,2,0,3,0,'ACEPTADO');
INSERT INTO InformeSemanal VALUES('Trabajador8', 2, 4, 2, '2015-12-07',4,0,2,0,6,0,'ACEPTADO');
INSERT INTO InformeSemanal VALUES('Trabajador8', 2, 4, 3, '2015-12-14',1,1,1,5,6,1,'ACEPTADO');

INSERT INTO Precedencia VALUES(2, 1, 1, 2, 1, 2);
INSERT INTO Precedencia VALUES(2, 1, 2, 2, 1, 3);
INSERT INTO Precedencia VALUES(2, 1, 3, 2, 2, 1);
INSERT INTO Precedencia VALUES(2, 2, 1, 2, 2, 2);
INSERT INTO Precedencia VALUES(2, 2, 1, 2, 2, 3);
INSERT INTO Precedencia VALUES(2, 2, 1, 2, 2, 4);
INSERT INTO Precedencia VALUES(2, 2, 2, 2, 2, 5);
INSERT INTO Precedencia VALUES(2, 2, 3, 2, 2, 5);
INSERT INTO Precedencia VALUES(2, 2, 4, 2, 2, 5);
INSERT INTO Precedencia VALUES(2, 2, 5, 2, 2, 6);
INSERT INTO Precedencia VALUES(2, 2, 6, 2, 3, 1);
INSERT INTO Precedencia VALUES(2, 2, 6, 2, 3, 2);
INSERT INTO Precedencia VALUES(2, 3, 1, 2, 3, 3);
INSERT INTO Precedencia VALUES(2, 3, 2, 2, 3, 3);
INSERT INTO Precedencia VALUES(2, 3, 3, 2, 3, 4);
INSERT INTO Precedencia VALUES(2, 3, 4, 2, 4, 1);
INSERT INTO Precedencia VALUES(2, 4, 1, 2, 4, 2);
INSERT INTO Precedencia VALUES(2, 4, 2, 2, 4, 3);
INSERT INTO Precedencia VALUES(2, 4, 3, 2, 4, 4);

INSERT INTO Dedicacion VALUES('Trabajador7', 2, 50);
INSERT INTO Dedicacion VALUES('Trabajador9', 2, 50);
INSERT INTO Dedicacion VALUES('Trabajador10', 2, 50);
INSERT INTO Dedicacion VALUES('Trabajador8', 2, 50);

INSERT INTO Asignacion VALUES('Trabajador7', 2, 1, 1);
INSERT INTO Asignacion VALUES('Trabajador7', 2, 1, 2);
INSERT INTO Asignacion VALUES('Trabajador9', 2, 1, 2);
INSERT INTO Asignacion VALUES('Trabajador9', 2, 2, 1);
INSERT INTO Asignacion VALUES('Trabajador9', 2, 2, 2);
INSERT INTO Asignacion VALUES('Trabajador9', 2, 2, 3);
INSERT INTO Asignacion VALUES('Trabajador10', 2, 2, 3);
INSERT INTO Asignacion VALUES('Trabajador10', 2, 2, 4);
INSERT INTO Asignacion VALUES('Trabajador7', 2, 2, 5);
INSERT INTO Asignacion VALUES('Trabajador7', 2, 3, 1);
INSERT INTO Asignacion VALUES('Trabajador9', 2, 3, 1);
INSERT INTO Asignacion VALUES('Trabajador10', 2, 3, 1);
INSERT INTO Asignacion VALUES('Trabajador8', 2, 3, 1);
INSERT INTO Asignacion VALUES('Trabajador8', 2, 3, 2);
INSERT INTO Asignacion VALUES('Trabajador10', 2, 3, 3);
INSERT INTO Asignacion VALUES('Trabajador8', 2, 4, 1);
INSERT INTO Asignacion VALUES('Trabajador8', 2, 4, 2);
INSERT INTO Asignacion VALUES('Trabajador8', 2, 4, 3);

/* Proyecto 3*/
INSERT INTO Proyecto VALUES(3, 'Proyecto sin terminar', '2016-01-18' , NULL, 'Trabajador1');
INSERT INTO Etapa VALUES(3, 1, 'Etapa unica');
INSERT INTO Actividad VALUES(3, 1, 1, 'Actividad1','2016-01-18', '2016-01-19', 24, 'ANALISTA');
INSERT INTO Actividad VALUES(3, 1, 2, 'Actividad2', '2016-01-20', '2015-10-22', 36, 'ANALISTA');
INSERT INTO Actividad VALUES(3, 1, 3, 'Actividad3', '2016-01-20', NULL, 60, 'PROGRAMADOR');
INSERT INTO Actividad VALUES(3, 1, 4, 'Actividad4', NULL, NULL, 48, 'ANALISTA');
INSERT INTO Actividad VALUES(3, 1, 5, 'Actividad5', NULL, NULL, 57, 'ANALISTA');
INSERT INTO Actividad VALUES(3, 1, 6, 'Actividad6HITO', NULL, NULL, 0, NULL);

INSERT INTO InformeSemanal VALUES('Trabajador1', 3, 1, 1, '2016-01-18',0,16,0,0,0,0,'ACEPTADO');
INSERT INTO InformeSemanal VALUES('Trabajador2', 3, 1, 1, '2016-01-18',0,0,4,0,4,0,'ACEPTADO');
INSERT INTO InformeSemanal VALUES('Trabajador1', 3, 1, 2, '2016-01-18',6,6,6,6,0,0,'ACEPTADO');
INSERT INTO InformeSemanal VALUES('Trabajador2', 3, 1, 2, '2016-01-18',2,2,2,2,2,2,'RECHAZADO');
INSERT INTO InformeSemanal VALUES('Trabajador3', 3, 1, 3, '2016-01-18',18,0,0,18,0,0,'PENDIENTE-APROBAR');
INSERT INTO InformeSemanal VALUES('Trabajador4', 3, 1, 3, '2016-01-18',0,0,9,0,9,0,'PENDIENTE-APROBAR');

INSERT INTO Precedencia VALUES(3, 1, 1, 3, 1, 2);
INSERT INTO Precedencia VALUES(3, 1, 1, 3, 1, 3);
INSERT INTO Precedencia VALUES(3, 1, 2, 3, 1, 4);
INSERT INTO Precedencia VALUES(3, 1, 3, 3, 1, 4);
INSERT INTO Precedencia VALUES(3, 1, 4, 3, 1, 5);
INSERT INTO Precedencia VALUES(3, 1, 5, 3, 1, 6);

INSERT INTO Dedicacion VALUES('Trabajador1', 3, 100);
INSERT INTO Dedicacion VALUES('Trabajador2', 3, 50);
INSERT INTO Dedicacion VALUES('Trabajador3', 3, 100);
INSERT INTO Dedicacion VALUES('Trabajador4', 3, 50);

INSERT INTO Asignacion VALUES('Trabajador1', 3, 1, 1);
INSERT INTO Asignacion VALUES('Trabajador2', 3, 1, 1);
INSERT INTO Asignacion VALUES('Trabajador1', 3, 1, 2);
INSERT INTO Asignacion VALUES('Trabajador2', 3, 1, 2);
INSERT INTO Asignacion VALUES('Trabajador3', 3, 1, 3);
INSERT INTO Asignacion VALUES('Trabajador4', 3, 1, 3);


/*Vacaciones trabajadores*/

INSERT INTO Vacaciones VALUES('2015-08-17', 'Trabajador1');
INSERT INTO Vacaciones VALUES('2015-08-24', 'Trabajador1');
INSERT INTO Vacaciones VALUES('2015-09-07', 'Trabajador1');
INSERT INTO Vacaciones VALUES('2015-09-14', 'Trabajador1');
INSERT INTO Vacaciones VALUES('2015-08-17', 'Trabajador7');
INSERT INTO Vacaciones VALUES('2015-08-24', 'Trabajador7');
INSERT INTO Vacaciones VALUES('2015-09-07', 'Trabajador7');
INSERT INTO Vacaciones VALUES('2015-09-14', 'Trabajador7');
INSERT INTO Vacaciones VALUES('2015-08-17', 'Trabajador2');
INSERT INTO Vacaciones VALUES('2015-09-21', 'Trabajador2');
INSERT INTO Vacaciones VALUES('2015-09-28', 'Trabajador2');
INSERT INTO Vacaciones VALUES('2015-08-24', 'Trabajador2');
INSERT INTO Vacaciones VALUES('2015-08-17', 'Trabajador3');
INSERT INTO Vacaciones VALUES('2015-08-24', 'Trabajador3');
INSERT INTO Vacaciones VALUES('2015-08-17', 'Trabajador9');
INSERT INTO Vacaciones VALUES('2015-09-21', 'Trabajador9');
INSERT INTO Vacaciones VALUES('2015-09-28', 'Trabajador9');
INSERT INTO Vacaciones VALUES('2015-08-24', 'Trabajador9');
INSERT INTO Vacaciones VALUES('2015-08-17', 'Trabajador10');
INSERT INTO Vacaciones VALUES('2015-08-24', 'Trabajador10');
INSERT INTO Vacaciones VALUES('2015-08-17', 'Trabajador4');
INSERT INTO Vacaciones VALUES('2015-08-24', 'Trabajador4');
INSERT INTO Vacaciones VALUES('2015-08-17', 'Trabajador5');
INSERT INTO Vacaciones VALUES('2015-08-24', 'Trabajador5');