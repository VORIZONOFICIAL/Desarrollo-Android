-- Script para verificar y actualizar el campo estado_presencia
-- Ejecutar este script si los usuarios ya existen en la base de datos

USE HORIZON_ONE;

-- 1. Verificar la estructura de la tabla (debe incluir estado_presencia)
DESCRIBE usuarios;

-- 2. Si el campo no existe, agregarlo:
-- ALTER TABLE usuarios ADD COLUMN estado_presencia ENUM('Dentro', 'Fuera') DEFAULT 'Fuera' COMMENT 'Indica si está físicamente en las instalaciones' AFTER activo;

-- 3. Establecer todos los usuarios existentes como "Fuera" por defecto
UPDATE usuarios SET estado_presencia = 'Fuera' WHERE estado_presencia IS NULL;

-- 4. Verificar los cambios
SELECT matricula, nombre_usuario, apellido_paterno_usuario, activo, estado_presencia 
FROM usuarios;

-- 5. Consulta para ver el último registro de cada usuario y su estado de presencia
SELECT 
    u.matricula,
    CONCAT(u.nombre_usuario, ' ', u.apellido_paterno_usuario) AS nombre_completo,
    u.estado_presencia,
    r.tipo_registro AS ultimo_tipo_registro,
    r.fecha AS ultima_fecha,
    r.hora AS ultima_hora
FROM usuarios u
LEFT JOIN (
    SELECT r1.*
    FROM registro r1
    INNER JOIN (
        SELECT matricula, MAX(CONCAT(fecha, ' ', hora)) AS max_datetime
        FROM registro
        GROUP BY matricula
    ) r2 ON r1.matricula = r2.matricula 
    AND CONCAT(r1.fecha, ' ', r1.hora) = r2.max_datetime
) r ON u.matricula = r.matricula
ORDER BY u.matricula;
