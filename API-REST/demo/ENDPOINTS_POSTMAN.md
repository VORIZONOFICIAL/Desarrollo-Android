# API REST - Sistema HORZA ONE
## Documentación de Endpoints para Postman

Base URL: `http://localhost:8080/api`

---

## 1. AUTENTICACIÓN

### Login
**POST** `/auth/login`
```json
{
  "correo": "juan.perez@horza.com",
  "contrasena": "admin123"
}
```
**Respuesta exitosa (200):**
```json
{
  "success": true,
  "mensaje": "Login exitoso",
  "matricula": 1,
  "nombreCompleto": "Juan Pérez García",
  "nombreRol": "Administrador",
  "idRol": 1
}
```

---

## 2. USUARIOS

### Listar todos los usuarios
**GET** `/usuarios`

### Obtener usuario por matrícula
**GET** `/usuarios/{matricula}`

### Crear usuario
**POST** `/usuarios`
```json
{
  "matricula": 6,
  "idRol": 3,
  "rfc": "TEST010101ABC",
  "curp": "TEST010101HDFRNN01",
  "fechaAlta": "2025-11-14",
  "nombreUsuario": "Prueba",
  "apellidoPaternoUsuario": "Test",
  "apellidoMaternoUsuario": "Demo",
  "telefono": "5512345678",
  "tipoContrato": "Tiempo Completo",
  "correo": "prueba@horza.com",
  "activo": "Activo",
  "cpUsuario": "01234",
  "calleUsuario": "Calle Test 123",
  "contrasena": "test123"
}
```

### Actualizar usuario
**PUT** `/usuarios/{matricula}`
```json
{
  "idRol": 3,
  "rfc": "TEST010101ABC",
  "curp": "TEST010101HDFRNN01",
  "fechaAlta": "2025-11-14",
  "nombreUsuario": "Prueba Actualizado",
  "apellidoPaternoUsuario": "Test",
  "apellidoMaternoUsuario": "Demo",
  "telefono": "5512345678",
  "tipoContrato": "Tiempo Completo",
  "correo": "prueba@horza.com",
  "activo": "Activo",
  "cpUsuario": "01234",
  "calleUsuario": "Calle Test 456"
}
```

### Eliminar usuario
**DELETE** `/usuarios/{matricula}`

### Cambiar contraseña
**POST** `/usuarios/cambiar-contrasena`
```json
{
  "matricula": 1,
  "contrasenaActual": "admin123",
  "contrasenaNueva": "nuevaPassword123"
}
```

---

## 3. ROLES

### Listar todos los roles
**GET** `/roles`

### Obtener rol por ID
**GET** `/roles/{id}`

### Crear rol
**POST** `/roles`
```json
{
  "idRol": 5,
  "nombreRol": "Gerente"
}
```

### Actualizar rol
**PUT** `/roles/{id}`
```json
{
  "nombreRol": "Gerente General"
}
```

### Eliminar rol
**DELETE** `/roles/{id}`

---

## 4. ÁREAS

### Listar todas las áreas
**GET** `/areas`

### Obtener área por ID
**GET** `/areas/{id}`

### Crear área
**POST** `/areas`
```json
{
  "idArea": 6,
  "nombreArea": "Marketing",
  "descripcionArea": "Departamento de marketing y publicidad",
  "activoArea": "Activo",
  "ubicacion": "Edificio A - Piso 2"
}
```

### Actualizar área
**PUT** `/areas/{id}`
```json
{
  "nombreArea": "Marketing Digital",
  "descripcionArea": "Departamento de marketing digital",
  "activoArea": "Activo",
  "ubicacion": "Edificio A - Piso 2"
}
```

### Eliminar área
**DELETE** `/areas/{id}`

---

## 5. BITÁCORAS

### Listar todas las bitácoras
**GET** `/bitacoras`

### Obtener bitácora por ID
**GET** `/bitacoras/{id}`

### Crear bitácora
**POST** `/bitacoras`
```json
{
  "idBitacora": 6,
  "matricula": 1,
  "numEntradas": 0,
  "numInasistencias": 0,
  "numRetardos": 0,
  "numEntradasAnticipadas": 0
}
```

### Actualizar bitácora
**PUT** `/bitacoras/{id}`
```json
{
  "matricula": 1,
  "numEntradas": 25,
  "numInasistencias": 1,
  "numRetardos": 2,
  "numEntradasAnticipadas": 5
}
```

### Eliminar bitácora
**DELETE** `/bitacoras/{id}`

---

## 6. CALENDARIOS

### Listar todos los calendarios
**GET** `/calendarios`

### Obtener calendario por ID
**GET** `/calendarios/{id}`

### Crear calendario
**POST** `/calendarios`
```json
{
  "idCalendario": 4,
  "nombreCalendario": "Calendario 2026",
  "fechaInicio": "2026-01-01",
  "fechaFin": "2026-12-31",
  "descripcion": "Calendario laboral 2026",
  "activoCalendario": "Activo"
}
```

### Actualizar calendario
**PUT** `/calendarios/{id}`
```json
{
  "nombreCalendario": "Calendario 2026 Actualizado",
  "fechaInicio": "2026-01-01",
  "fechaFin": "2026-12-31",
  "descripcion": "Calendario laboral actualizado 2026",
  "activoCalendario": "Activo"
}
```

### Eliminar calendario
**DELETE** `/calendarios/{id}`

---

## 7. HORARIOS

### Listar todos los horarios
**GET** `/horarios`

### Obtener horario por ID
**GET** `/horarios/{id}`

### Crear horario
**POST** `/horarios`
```json
{
  "idHorario": 5,
  "idCalendario": 1,
  "nombreHorario": "Horario Nocturno",
  "descripcion": "Turno de 10:00 PM a 6:00 AM",
  "activoHorario": "Activo"
}
```

### Actualizar horario
**PUT** `/horarios/{id}`
```json
{
  "idCalendario": 1,
  "nombreHorario": "Horario Nocturno Modificado",
  "descripcion": "Turno de 10:00 PM a 6:00 AM",
  "activoHorario": "Activo"
}
```

### Eliminar horario
**DELETE** `/horarios/{id}`

---

## 8. BLOQUES DE HORARIO

### Listar todos los bloques
**GET** `/bloques-horario`

### Obtener bloque por ID
**GET** `/bloques-horario/{id}`

### Crear bloque
**POST** `/bloques-horario`
```json
{
  "idBloque": 7,
  "idHorario": 1,
  "idArea": 1,
  "nombreBloque": "Bloque Extra",
  "horaInicio": "16:00:00",
  "horaFin": "20:00:00"
}
```

### Actualizar bloque
**PUT** `/bloques-horario/{id}`
```json
{
  "idHorario": 1,
  "idArea": 1,
  "nombreBloque": "Bloque Extra Modificado",
  "horaInicio": "16:00:00",
  "horaFin": "21:00:00"
}
```

### Eliminar bloque
**DELETE** `/bloques-horario/{id}`

---

## 9. DISPOSITIVOS

### Listar todos los dispositivos
**GET** `/dispositivos`

### Obtener dispositivo por ID
**GET** `/dispositivos/{id}`

### Crear dispositivo
**POST** `/dispositivos`
```json
{
  "idDispositivo": 6,
  "idArea": 2,
  "nombreDispositivo": "Terminal Nueva TI",
  "descripcionDispositivo": "Lector biométrico nuevo",
  "activoDispositivo": "Activo"
}
```

### Actualizar dispositivo
**PUT** `/dispositivos/{id}`
```json
{
  "idArea": 2,
  "nombreDispositivo": "Terminal Actualizada TI",
  "descripcionDispositivo": "Lector biométrico actualizado",
  "activoDispositivo": "Activo"
}
```

### Eliminar dispositivo
**DELETE** `/dispositivos/{id}`

---

## 10. REGISTROS

### Listar todos los registros
**GET** `/registros`

### Obtener registro por ID
**GET** `/registros/{id}`

### Crear registro
**POST** `/registros`
```json
{
  "idRegistro": 17,
  "matricula": 1,
  "idBitacora": 1,
  "idDispositivo": 1,
  "idArea": 1,
  "tipoRegistro": "Entrada",
  "fecha": "2025-11-14",
  "hora": "08:00:00",
  "observacion": "Entrada normal",
  "estadoRegistro": "Puntual"
}
```

### Actualizar registro
**PUT** `/registros/{id}`
```json
{
  "matricula": 1,
  "idBitacora": 1,
  "idDispositivo": 1,
  "idArea": 1,
  "tipoRegistro": "Salida",
  "fecha": "2025-11-14",
  "hora": "15:00:00",
  "observacion": "Salida normal",
  "estadoRegistro": "Puntual"
}
```

### Eliminar registro
**DELETE** `/registros/{id}`

---

## NOTAS IMPORTANTES:

1. **Todos los endpoints tienen CORS habilitado** (`@CrossOrigin(origins = "*")`)

2. **Content-Type**: Usar `application/json` en todas las peticiones POST y PUT

3. **Códigos de respuesta HTTP**:
   - 200: OK (GET, PUT exitosos)
   - 201: Created (POST exitoso)
   - 204: No Content (DELETE exitoso)
   - 400: Bad Request (Error en validación)
   - 401: Unauthorized (Login fallido)
   - 404: Not Found (Recurso no encontrado)

4. **Base de datos**: `horza_one` en MySQL (localhost:3306)

5. **Para probar en Postman**:
   - Importa estos ejemplos como colección
   - La aplicación corre en `http://localhost:8080`
   - Primero ejecuta los scripts SQL para tener datos de prueba

---

## EJEMPLOS DE PRUEBAS EN POSTMAN:

### Test 1: Login
1. POST `http://localhost:8080/api/auth/login`
2. Body: `{"correo": "juan.perez@horza.com", "contrasena": "admin123"}`

### Test 2: Listar Usuarios
1. GET `http://localhost:8080/api/usuarios`

### Test 3: Crear Área Nueva
1. POST `http://localhost:8080/api/areas`
2. Body: Ver ejemplo en sección ÁREAS

### Test 4: Cambiar Contraseña
1. POST `http://localhost:8080/api/usuarios/cambiar-contrasena`
2. Body: Ver ejemplo en sección USUARIOS
