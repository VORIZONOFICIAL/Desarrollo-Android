-- ===============================================
--  CREACIÓN DE BASE DE DATOS HORIZON_ONE
-- ===============================================
CREATE DATABASE IF NOT EXISTS HORIZON_ONE
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

USE HORIZON_ONE;

-- ===============================================
--  1. TABLA ROL
-- ===============================================
CREATE TABLE rol (
  id_rol INT PRIMARY KEY AUTO_INCREMENT,
  nombre_rol VARCHAR(50) NOT NULL,
  tipo_permiso ENUM('ADMIN','PERSONAL') NOT NULL
) ENGINE=InnoDB;

-- ===============================================
--  2. TABLA USUARIOS
-- ===============================================
CREATE TABLE USUARIOS (
  matricula INT  primary key  NOT NULL,
  id_rol INT NOT NULL,
  rfc VARCHAR(20) NOT NULL,
  curp VARCHAR(20) NOT NULL,
  fecha_alta DATE,
  nombre_usuario VARCHAR(40) NOT NULL,
  apellido_paterno_usuario VARCHAR(20) NOT NULL,
  apellido_materno_usuario VARCHAR(20) NOT NULL,
  telefono VARCHAR(12),
  tipo_contrato VARCHAR(40),
  correo VARCHAR(50),
  activo ENUM('Activo', 'Inactivo') DEFAULT 'Activo',
  estado_presencia ENUM('Dentro', 'Fuera') DEFAULT 'Fuera' COMMENT 'Indica si está físicamente en las instalaciones',
  cp_usuario VARCHAR(7),
  calle_usuario VARCHAR (40),
  contrasenia varchar(12),
  FOREIGN KEY (id_rol) REFERENCES ROL(id_rol)
) ENGINE=InnoDB;

-- ===============================================
--  3. TABLA CALENDARIO
-- ===============================================
CREATE TABLE CALENDARIO (
  id_calendario INT PRIMARY KEY NOT NULL,
  nombre_calendario VARCHAR(60),
  fecha_inicio DATE,
  fecha_fin DATE,
  descripcion TEXT,
  activo_calendario ENUM('Activo', 'Inactivo') DEFAULT 'Activo'
) ENGINE=InnoDB;

-- ===============================================
--  4. TABLA HORARIO
-- ===============================================
CREATE TABLE HORARIO (
  id_horario INT PRIMARY KEY NOT NULL,
  id_calendario INT NOT NULL,
  nombre_horario VARCHAR(60),
  descripcion TEXT,
  activo_horario ENUM('Activo', 'Inactivo') DEFAULT 'Activo',
  FOREIGN KEY (id_calendario) REFERENCES CALENDARIO(id_calendario) ON DELETE CASCADE
) ENGINE=InnoDB;

-- ===============================================
--  5. TABLA AREA
-- ===============================================
CREATE TABLE AREA (
  id_area INT  PRIMARY KEY NOT NULL AUTO_INCREMENT,
  nombre_area VARCHAR(60) NOT NULL,
  descripcion_area TEXT,
  activo_area ENUM('Activo', 'Inactivo') DEFAULT 'Activo',
  ubicacion VARCHAR (100)
) ENGINE=InnoDB;

-- ===============================================
--  6. TABLA DISPOSITIVO
-- ===============================================
CREATE TABLE DISPOSITIVO (
  id_dispositivo INT PRIMARY KEY,
  id_area INT NOT NULL,
  nombre_dispositivo VARCHAR(100) NOT NULL,
  descripcion_dispositivo TEXT,
  activo_dispositivo ENUM('Activo', 'Inactivo') DEFAULT 'Inactivo',
  FOREIGN KEY (id_area) REFERENCES AREA(id_area) ON DELETE CASCADE
) ENGINE=InnoDB;

-- ===============================================
--  7. TABLA BITACORA
-- ===============================================
CREATE TABLE BITACORA (
  id_bitacora INT  PRIMARY KEY NOT NULL,
  matricula INT NOT NULL,
  num_entradas INT DEFAULT 0,
  num_inasistencias INT DEFAULT 0,
  num_retardos INT DEFAULT 0,
  num_entradas_anticipadas INT DEFAULT 0,
  FOREIGN KEY (matricula) REFERENCES USUARIOS(matricula) ON DELETE CASCADE
) ENGINE=InnoDB;

-- ===============================================
--  8. TABLA REGISTRO
-- ===============================================
CREATE TABLE REGISTRO (
  id_registro INT  PRIMARY KEY,
  matricula INT  NOT NULL,
  id_bitacora INT NOT NULL,
  id_dispositivo INT NOT NULL,
  id_area INT NOT NULL,
  tipo_registro ENUM('Entrada','Salida') NOT NULL,
  fecha DATE NOT NULL,
  hora TIME NOT NULL,
  observacion TEXT,
  estado_registro ENUM('Puntual','Retardo','Anticipado'),
  FOREIGN KEY (id_bitacora) REFERENCES BITACORA(id_bitacora) ON DELETE CASCADE,
  FOREIGN KEY (matricula) REFERENCES USUARIOS(matricula) ON DELETE CASCADE,
  FOREIGN KEY (id_dispositivo) REFERENCES DISPOSITIVO(id_dispositivo) ON DELETE CASCADE,
  FOREIGN KEY (id_area) REFERENCES AREA(id_area) ON DELETE CASCADE
) ENGINE=InnoDB;

-- ===============================================
--  9. TABLA BLOQUES_HORARIO
-- ===============================================
CREATE TABLE BLOQUES_HORARIO (
  id_bloque INT PRIMARY KEY NOT NULL,
  id_horario INT NOT NULL,
  id_area INT NOT NULL,
  nombre_bloque VARCHAR(60),
  hora_inicio TIME,
  hora_fin TIME,
  FOREIGN KEY (id_horario) REFERENCES HORARIO(id_horario) ON DELETE CASCADE,
  FOREIGN KEY (id_area) REFERENCES AREA(id_area) ON DELETE CASCADE
) ENGINE=InnoDB;

-- ===============================================
--  10. TABLA ROL_USUARIO
-- ===============================================
CREATE TABLE ROL_USUARIO (
  matricula INT NOT NULL,
  id_rol INT NOT NULL,
  PRIMARY KEY (matricula, id_rol),
  FOREIGN KEY (matricula) REFERENCES USUARIOS(matricula) ON DELETE CASCADE,
  FOREIGN KEY (id_rol) REFERENCES ROL(id_rol) ON DELETE CASCADE
) ENGINE=InnoDB;

-- ===============================================
--  11. TABLA USUARIOS_CALENDARIO
-- ===============================================
CREATE TABLE USUARIOS_CALENDARIO (
  matricula INT NOT NULL,
  id_calendario INT NOT NULL,
  PRIMARY KEY (matricula, id_calendario),
  FOREIGN KEY (matricula) REFERENCES USUARIOS(matricula) ON DELETE CASCADE,
  FOREIGN KEY (id_calendario) REFERENCES CALENDARIO(id_calendario) ON DELETE CASCADE
) ENGINE=InnoDB;

-- ===============================================
--  2.1. CREACION DE LOS ROLES PREDETERMINADOS POR EL SISTEMA
-- ===============================================

CREATE ROLE 'ROL_ADMIN';
CREATE ROLE 'ROL_PERSONAL';

-- ===============================================
--  2.2. ASIGNACION DE PRIVILEGIOS A LOS ROLES 
-- ===============================================

GRANT ALL PRIVILEGES ON horizon_one.* TO 'ROL_ADMIN';
GRANT SELECT ON horizon_one.* TO 'ROL_PERSONAL';

-- ===============================================
--  2.3. TRIGGERS 
-- ===============================================

CREATE TABLE auditoria (
    id INT AUTO_INCREMENT PRIMARY KEY,
    usuario_mysql VARCHAR(50),
    tabla_afectada VARCHAR(50),
    momento TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    accion VARCHAR(20)
);

DELIMITER $$

CREATE TRIGGER aud_usuarios
AFTER INSERT ON usuarios
FOR EACH ROW
BEGIN
    INSERT INTO auditoria (usuario_mysql, tabla_afectada, accion)
    VALUES (CURRENT_USER(), 'usuarios', 'INSERTO');
END$$

DELIMITER ;

DELIMITER $$

CREATE TRIGGER aud_usuarios_del
AFTER DELETE ON usuarios
FOR EACH ROW
BEGIN
    INSERT INTO auditoria (usuario_mysql, tabla_afectada, accion)
    VALUES (CURRENT_USER(), 'usuarios', 'ELIMINO');
END$$

DELIMITER ;

