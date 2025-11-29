# Sistema HORZA ONE - API REST

## 📋 Descripción
API REST completa desarrollada con Spring Boot 3.5.7, JPA, Lombok y MySQL para el sistema de gestión de asistencias HORZA ONE.

## 🛠️ Tecnologías
- **Spring Boot 3.5.7**
- **Spring Data JPA**
- **Spring Web**
- **Lombok**
- **MySQL 8**
- **Maven**
- **Java 17**

## 📦 Estructura del Proyecto

```
src/main/java/com/example/demo/
├── controller/          # Controladores REST (Endpoints)
│   ├── LoginController.java
│   ├── UsuarioController.java
│   ├── RolController.java
│   ├── AreaController.java
│   ├── BitacoraController.java
│   ├── CalendarioController.java
│   ├── HorarioController.java
│   ├── BloqueHorarioController.java
│   ├── DispositivoController.java
│   └── RegistroController.java
│
├── dto/                 # Data Transfer Objects
│   ├── LoginRequest.java
│   ├── LoginResponse.java
│   ├── CambioContrasenaRequest.java
│   ├── UsuarioDTO.java
│   ├── RolDTO.java
│   ├── AreaDTO.java
│   ├── BitacoraDTO.java
│   ├── CalendarioDTO.java
│   ├── HorarioDTO.java
│   ├── BloqueHorarioDTO.java
│   ├── DispositivoDTO.java
│   └── RegistroDTO.java
│
├── model/               # Entidades JPA
│   ├── Usuario.java
│   ├── Rol.java
│   ├── Area.java
│   ├── Bitacora.java
│   ├── Calendario.java
│   ├── Horario.java
│   ├── BloqueHorario.java
│   ├── Dispositivo.java
│   ├── Registro.java
│   ├── UsuarioCalendario.java
│   └── RolUsuario.java
│
├── repository/          # Repositorios JPA
│   ├── UsuarioRepository.java
│   ├── RolRepository.java
│   ├── AreaRepository.java
│   ├── BitacoraRepository.java
│   ├── CalendarioRepository.java
│   ├── HorarioRepository.java
│   ├── BloqueHorarioRepository.java
│   ├── DispositivoRepository.java
│   └── RegistroRepository.java
│
├── service/             # Interfaces de servicios
│   ├── LoginService.java
│   ├── UsuarioService.java
│   ├── RolService.java
│   ├── AreaService.java
│   ├── BitacoraService.java
│   ├── CalendarioService.java
│   ├── HorarioService.java
│   ├── BloqueHorarioService.java
│   ├── DispositivoService.java
│   └── RegistroService.java
│
└── service/impl/        # Implementaciones de servicios
    ├── LoginServiceImpl.java
    ├── UsuarioServiceImpl.java
    ├── RolServiceImpl.java
    ├── AreaServiceImpl.java
    ├── BitacoraServiceImpl.java
    ├── CalendarioServiceImpl.java
    ├── HorarioServiceImpl.java
    ├── BloqueHorarioServiceImpl.java
    ├── DispositivoServiceImpl.java
    └── RegistroServiceImpl.java
```

## 🚀 Instalación y Configuración

### 1. Prerrequisitos
- Java 17 o superior
- Maven 3.6+
- MySQL 8.0+
- IDE (IntelliJ IDEA, Eclipse, VS Code)

### 2. Configurar Base de Datos

```bash
# Ejecutar los scripts SQL en orden:
1. sql/horza_one_base.sql      # Crea la estructura de la BD
2. sql/datos_prueba.sql         # Inserta datos de prueba
```

### 3. Configurar application.properties

El archivo `src/main/resources/application.properties` ya está configurado:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/horza_one?useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=n0m3l0
spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect
spring.jpa.show-sql=true
```

**Ajusta el usuario y contraseña según tu configuración de MySQL.**

### 4. Compilar el Proyecto

```bash
mvn clean install
```

### 5. Ejecutar la Aplicación

```bash
mvn spring-boot:run
```

O ejecuta directamente desde tu IDE la clase `DemoApplication.java`

La aplicación estará disponible en: **http://localhost:8080**

## 📡 Endpoints Principales

### Autenticación
- **POST** `/api/auth/login` - Iniciar sesión

### Usuarios
- **GET** `/api/usuarios` - Listar todos
- **GET** `/api/usuarios/{id}` - Obtener por ID
- **POST** `/api/usuarios` - Crear usuario
- **PUT** `/api/usuarios/{id}` - Actualizar usuario
- **DELETE** `/api/usuarios/{id}` - Eliminar usuario
- **POST** `/api/usuarios/cambiar-contrasena` - Cambiar contraseña

### Roles
- **GET** `/api/roles` - Listar todos
- **GET** `/api/roles/{id}` - Obtener por ID
- **POST** `/api/roles` - Crear rol
- **PUT** `/api/roles/{id}` - Actualizar rol
- **DELETE** `/api/roles/{id}` - Eliminar rol

### Áreas
- **GET** `/api/areas` - Listar todas
- **GET** `/api/areas/{id}` - Obtener por ID
- **POST** `/api/areas` - Crear área
- **PUT** `/api/areas/{id}` - Actualizar área
- **DELETE** `/api/areas/{id}` - Eliminar área

### Bitácoras
- **GET** `/api/bitacoras` - Listar todas
- **GET** `/api/bitacoras/{id}` - Obtener por ID
- **POST** `/api/bitacoras` - Crear bitácora
- **PUT** `/api/bitacoras/{id}` - Actualizar bitácora
- **DELETE** `/api/bitacoras/{id}` - Eliminar bitácora

### Calendarios
- **GET** `/api/calendarios` - Listar todos
- **GET** `/api/calendarios/{id}` - Obtener por ID
- **POST** `/api/calendarios` - Crear calendario
- **PUT** `/api/calendarios/{id}` - Actualizar calendario
- **DELETE** `/api/calendarios/{id}` - Eliminar calendario

### Horarios
- **GET** `/api/horarios` - Listar todos
- **GET** `/api/horarios/{id}` - Obtener por ID
- **POST** `/api/horarios` - Crear horario
- **PUT** `/api/horarios/{id}` - Actualizar horario
- **DELETE** `/api/horarios/{id}` - Eliminar horario

### Bloques de Horario
- **GET** `/api/bloques-horario` - Listar todos
- **GET** `/api/bloques-horario/{id}` - Obtener por ID
- **POST** `/api/bloques-horario` - Crear bloque
- **PUT** `/api/bloques-horario/{id}` - Actualizar bloque
- **DELETE** `/api/bloques-horario/{id}` - Eliminar bloque

### Dispositivos
- **GET** `/api/dispositivos` - Listar todos
- **GET** `/api/dispositivos/{id}` - Obtener por ID
- **POST** `/api/dispositivos` - Crear dispositivo
- **PUT** `/api/dispositivos/{id}` - Actualizar dispositivo
- **DELETE** `/api/dispositivos/{id}` - Eliminar dispositivo

### Registros
- **GET** `/api/registros` - Listar todos
- **GET** `/api/registros/{id}` - Obtener por ID
- **POST** `/api/registros` - Crear registro
- **PUT** `/api/registros/{id}` - Actualizar registro
- **DELETE** `/api/registros/{id}` - Eliminar registro

## 🧪 Pruebas con Postman

Consulta el archivo `ENDPOINTS_POSTMAN.md` para ver ejemplos detallados de cada endpoint.

### Ejemplo rápido de Login:

```json
POST http://localhost:8080/api/auth/login
Content-Type: application/json

{
  "correo": "juan.perez@horza.com",
  "contrasena": "admin123"
}
```

### Ejemplo de Cambio de Contraseña:

```json
POST http://localhost:8080/api/usuarios/cambiar-contrasena
Content-Type: application/json

{
  "matricula": 1,
  "contrasenaActual": "admin123",
  "contrasenaNueva": "nuevaPassword123"
}
```

## 📊 Tablas de la Base de Datos

1. **rol** - Roles de usuarios (Administrador, Supervisor, Empleado, Invitado)
2. **area** - Áreas de la empresa (RH, TI, Finanzas, Operaciones)
3. **usuarios** - Información de empleados
4. **bitacora** - Resumen de asistencias por usuario
5. **calendario** - Calendarios laborales
6. **horario** - Horarios de trabajo
7. **bloques_horario** - Bloques de tiempo por horario y área
8. **dispositivo** - Dispositivos de registro (lectores biométricos)
9. **registro** - Registros de entrada/salida
10. **usuarios_calendario** - Relación usuarios-calendarios
11. **rol_usuario** - Relación adicional roles-usuarios

## 🔒 Características de Seguridad

- **CORS** habilitado para todas las rutas
- **Validación** de contraseñas en cambio de password
- **Autenticación** mediante login con correo y contraseña
- **Respuestas HTTP** estándar con códigos apropiados

## 📝 Notas Adicionales

- Todas las entidades usan **Lombok** para reducir boilerplate
- Los DTOs separan la capa de presentación de la persistencia
- Implementación completa de **CRUD** para todas las tablas
- Manejo de relaciones **JPA** entre entidades
- Servicios con **inyección de dependencias** mediante `@Autowired`

## 🐛 Troubleshooting

### Error de conexión a MySQL
- Verifica que MySQL esté corriendo
- Confirma usuario/contraseña en `application.properties`
- Asegúrate que la BD `horza_one` existe

### Error 404 en endpoints
- Verifica que la aplicación esté corriendo en puerto 8080
- Confirma la ruta completa: `http://localhost:8080/api/...`

### Errores de compilación
- Ejecuta `mvn clean install`
- Verifica que tengas Java 17
- Actualiza dependencias con `mvn dependency:resolve`

## 👨‍💻 Autor

Sistema desarrollado para la gestión de asistencias HORZA ONE

## 📄 Licencia

Este proyecto es privado y confidencial.
