
USE HORIZON_ONE;

INSERT INTO rol (id_rol, nombre_rol,tipo_permiso) VALUES
(1, 'Administrador','ADMIN'),
(2, 'Supervisor','PERSONAL'),
(3, 'Empleado','ADMIN'),
(4, 'Invitado','ADMIN');

-- ============================================
-- 2. ÁREAS
-- ============================================
INSERT INTO area (id_area, nombre_area, descripcion_area, activo_area, ubicacion) VALUES
(1, 'Recursos Humanos', 'Departamento de gestión de personal', 'Activo', 'Edificio A - Piso 3'),
(2, 'Tecnología', 'Departamento de TI y sistemas', 'Activo', 'Edificio B - Piso 2'),
(3, 'Finanzas', 'Departamento de contabilidad y finanzas', 'Activo', 'Edificio A - Piso 1'),
(4, 'Operaciones', 'Departamento de operaciones', 'Activo', 'Edificio C - Planta Baja'),
(5, 'Archivo', 'Área de archivos históricos', 'Inactivo', 'Sótano');

-- ============================================
-- 3. USUARIOS
-- ============================================
INSERT INTO usuarios (matricula, id_rol, rfc, curp, fecha_alta, nombre_usuario, apellido_paterno_usuario, apellido_materno_usuario, telefono, tipo_contrato, correo, activo, cp_usuario, calle_usuario, contrasenia) VALUES
(1, 1, 'JUPG850101ABC', 'JUPG850101HDFRNN01', '2025-01-15', 'Juan', 'Pérez', 'García', '5512345678', 'Tiempo Completo', 'juan.perez@horza.com', 'Activo', '01234', 'Av. Principal 123', 'admin123'),
(2, 2, 'MARS900215XYZ', 'MARS900215MDFRMN02', '2025-02-01', 'María', 'Sánchez', 'Ramírez', '5523456789', 'Tiempo Completo', 'maria.sanchez@horza.com', 'Activo', '01235', 'Calle Secundaria 456', 'super123'),
(3, 3, 'CARL880320DEF', 'CARL880320HDFMNR03', '2025-03-10', 'Carlos', 'López', 'Martínez', '5534567890', 'Medio Tiempo', 'carlos.lopez@horza.com', 'Activo', '01236', 'Av. Reforma 789', 'emp123'),
(4, 3, 'ANRO950412GHI', 'ANRO950412MDFLPR04', '2025-04-05', 'Ana', 'Rodríguez', 'Flores', '5545678901', 'Tiempo Completo', 'ana.rodriguez@horza.com', 'Activo', '01237', 'Calle Norte 321', 'ana2025'),

(5, 4, 'PEHE921125JKL', 'PEHE921125HDFRMN05', '2025-05-20', 'Pedro', 'Hernández', 'Cruz', '5556789012', 'Temporal', 'pedro.hernandez@horza.com', 'Inactivo', '01238', 'Av. Sur 654', 'temp123');

INSERT INTO usuarios (matricula, id_rol, rfc, curp, fecha_alta, nombre_usuario, apellido_paterno_usuario, apellido_materno_usuario, telefono, tipo_contrato, correo, activo, cp_usuario, calle_usuario, contrasenia) VALUES
(6, 2, 'PLHE929125JKL', 'PLHE929125HDFRMN05', '2024-05-20', 'Isra', 'Hernández', 'Cruz', '5556782512', 'Confinza', 'ista.sO@horza.com', 'Activo', '05638', 'Av. Norte 654', 'israsO78');
-- ============================================
-- 4. BITÁCORAS (una por usuario)
-- ============================================
INSERT INTO bitacora (id_bitacora, matricula, num_entradas, num_inasistencias, num_retardos, num_entradas_anticipadas) VALUES
(1, 1, 20, 0, 1, 5),
(2, 2, 18, 1, 2, 3),
(3, 3, 15, 2, 1, 2),
(4, 4, 22, 0, 0, 8),
(5, 5, 5, 5, 3, 0);

-- ============================================
-- 5. CALENDARIOS
-- ============================================
INSERT INTO calendario (id_calendario, nombre_calendario, fecha_inicio, fecha_fin, descripcion, activo_calendario) VALUES
(1, 'Calendario 2025', '2025-01-01', '2025-12-31', 'Calendario laboral del año 2025', 'Activo'),
(2, 'Calendario Verano 2025', '2025-06-01', '2025-08-31', 'Horario especial de verano', 'Activo'),
(3, 'Calendario 2024', '2024-01-01', '2024-12-31', 'Calendario del año anterior', 'Inactivo');

-- ============================================
-- 6. HORARIOS
-- ============================================
INSERT INTO horario (id_horario, id_calendario, nombre_horario, descripcion, activo_horario) VALUES
(1, 1, 'Horario Matutino', 'Turno de 8:00 AM a 3:00 PM', 'Activo'),
(2, 1, 'Horario Vespertino', 'Turno de 2:00 PM a 9:00 PM', 'Activo'),
(3, 2, 'Horario Verano', 'Turno reducido de verano', 'Activo'),
(4, 3, 'Horario Antiguo', 'Horario del año pasado', 'Inactivo');

-- ============================================
-- 7. BLOQUES DE HORARIO
-- ============================================
INSERT INTO bloques_horario (id_bloque, id_horario, id_area, nombre_bloque, hora_inicio, hora_fin) VALUES
(1, 1, 1, 'Bloque Mañana RH', '08:00:00', '12:00:00'),
(2, 1, 1, 'Bloque Tarde RH', '12:00:00', '15:00:00'),
(3, 1, 2, 'Bloque Mañana TI', '08:00:00', '12:00:00'),
(4, 1, 2, 'Bloque Tarde TI', '12:00:00', '15:00:00'),
(5, 2, 3, 'Bloque Tarde Finanzas', '14:00:00', '18:00:00'),
(6, 2, 4, 'Bloque Noche Operaciones', '18:00:00', '21:00:00');

-- ============================================
-- 8. DISPOSITIVOS
-- ============================================
INSERT INTO dispositivo (id_dispositivo, id_area, nombre_dispositivo, descripcion_dispositivo, activo_dispositivo) VALUES
(1, 1, 'Terminal RH Entrada', 'Lector biométrico entrada RH', 'Activo'),
(2, 2, 'Terminal TI Principal', 'Lector biométrico área TI', 'Activo'),
(3, 3, 'Terminal Finanzas', 'Lector de tarjeta magnética', 'Activo'),
(4, 4, 'Terminal Operaciones', 'Lector biométrico y facial', 'Activo'),
(5, 5, 'Terminal Archivo', 'Dispositivo antiguo', 'Inactivo');

-- ============================================
-- 9. REGISTROS DE ASISTENCIA (últimos 3 días)
-- ============================================
-- Día 1 (12 de noviembre)
INSERT INTO registro (id_registro, matricula, id_bitacora, id_dispositivo, id_area, tipo_registro, fecha, hora, observacion, estado_registro) VALUES
(1, 1, 1, 1, 1, 'Entrada', '2025-11-12', '08:00:00', 'Entrada puntual', 'Puntual'),
(2, 1, 1, 1, 1, 'Salida', '2025-11-12', '15:05:00', 'Salida normal', 'Puntual'),
(3, 2, 2, 1, 1, 'Entrada', '2025-11-12', '08:15:00', 'Llegó tarde', 'Retardo'),
(4, 2, 2, 1, 1, 'Salida', '2025-11-12', '15:00:00', 'Salida normal', 'Puntual'),
(5, 3, 3, 2, 2, 'Entrada', '2025-11-12', '07:50:00', 'Entrada anticipada', 'Anticipado'),
(6, 3, 3, 2, 2, 'Salida', '2025-11-12', '14:55:00', 'Salida normal', 'Puntual');

-- Día 2 (13 de noviembre)
INSERT INTO registro (id_registro, matricula, id_bitacora, id_dispositivo, id_area, tipo_registro, fecha, hora, observacion, estado_registro) VALUES
(7, 1, 1, 1, 1, 'Entrada', '2025-11-13', '08:02:00', 'Entrada puntual', 'Puntual'),
(8, 1, 1, 1, 1, 'Salida', '2025-11-13', '15:00:00', 'Salida normal', 'Puntual'),
(9, 2, 2, 1, 1, 'Entrada', '2025-11-13', '08:05:00', 'Entrada puntual', 'Puntual'),
(10, 2, 2, 1, 1, 'Salida', '2025-11-13', '15:10:00', 'Salida normal', 'Puntual'),
(11, 4, 4, 3, 3, 'Entrada', '2025-11-13', '07:55:00', 'Entrada anticipada', 'Anticipado'),
(12, 4, 4, 3, 3, 'Salida', '2025-11-13', '15:00:00', 'Salida normal', 'Puntual');

-- Día 3 (14 de noviembre - hoy)
INSERT INTO registro (id_registro, matricula, id_bitacora, id_dispositivo, id_area, tipo_registro, fecha, hora, observacion, estado_registro) VALUES
(13, 1, 1, 1, 1, 'Entrada', '2025-11-14', '08:00:00', 'Entrada puntual', 'Puntual'),
(14, 2, 2, 1, 1, 'Entrada', '2025-11-14', '08:03:00', 'Entrada puntual', 'Puntual'),
(15, 3, 3, 2, 2, 'Entrada', '2025-11-14', '08:20:00', 'Llegó tarde', 'Retardo'),
(16, 4, 4, 3, 3, 'Entrada', '2025-11-14', '07:58:00', 'Entrada anticipada', 'Anticipado');

-- ============================================
-- 10. USUARIOS-CALENDARIO (Asignaciones)
-- ============================================
INSERT INTO usuarios_calendario (matricula, id_calendario) VALUES
(1, 1),
(2, 1),
(3, 1),
(4, 1),
(1, 2),
(2, 2);

-- ============================================
-- VERIFICACIÓN DE DATOS
-- ============================================
SELECT 'Roles insertados:' AS Info, COUNT(*) AS Total FROM rol
UNION ALL
SELECT 'Áreas insertadas:', COUNT(*) FROM area
UNION ALL
SELECT 'Usuarios insertados:', COUNT(*) FROM usuarios
UNION ALL
SELECT 'Bitácoras insertadas:', COUNT(*) FROM bitacora
UNION ALL
SELECT 'Calendarios insertados:', COUNT(*) FROM calendario
UNION ALL
SELECT 'Horarios insertados:', COUNT(*) FROM horario
UNION ALL
SELECT 'Bloques insertados:', COUNT(*) FROM bloques_horario
UNION ALL
SELECT 'Dispositivos insertados:', COUNT(*) FROM dispositivo
UNION ALL
SELECT 'Registros insertados:', COUNT(*) FROM registro
UNION ALL
SELECT 'Asignaciones calendario:', COUNT(*) FROM usuarios_calendario;
