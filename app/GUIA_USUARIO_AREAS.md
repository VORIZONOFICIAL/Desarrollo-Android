# 📱 Guía de Usuario - Gestión de Áreas

## ✨ Funcionalidades Implementadas

### 1️⃣ **Ver Todas las Áreas**
- Al abrir la pantalla, automáticamente se cargan todas las áreas desde la base de datos
- Cada área muestra:
  - 🏢 **Nombre del área**
  - 📍 **Ubicación**
  - 📝 **Descripción**
  - ✅ **Estado** (Activo/Inactivo)

---

### 2️⃣ **Crear Nueva Área**

**Pasos:**
1. Completa el formulario:
   - **Nombre**: Ingresa el nombre del área (ej: "Ventas")
   - **Descripción**: Describe las funciones del área
   - **Ubicación**: Especifica dónde se encuentra (ej: "Edificio D - Piso 1")
   - **Estado**: Activa/Desactiva con el switch (✅ Activo por defecto)

2. Click en **"Registrar Área"**

3. ✅ Verás un mensaje: "Área registrada exitosamente"

4. El formulario se limpia automáticamente

5. La lista se actualiza mostrando la nueva área

---

### 3️⃣ **Editar Área Existente**

**Pasos:**
1. En la lista de áreas, localiza el área que deseas editar

2. Click en el botón **"Editar"** (azul) del área

3. El formulario se llena automáticamente con los datos del área:
   - El título del botón cambia a **"Actualizar Área"**
   - Aparece un botón **"Cancelar"** a la derecha
   - Mensaje: "Editando: [Nombre del Área]"

4. Modifica los campos que necesites

5. Click en **"Actualizar Área"**

6. ✅ Mensaje: "Área actualizada exitosamente"

7. La lista se actualiza con los nuevos datos

**Cancelar edición:**
- Click en **"Cancelar"** para descartar cambios
- El formulario se limpia y vuelve al modo "Registrar"

---

### 4️⃣ **Eliminar Área**

**Pasos:**
1. En la lista de áreas, localiza el área que deseas eliminar

2. Click en el botón **"Eliminar"** (rojo) del área

3. Aparece un diálogo de confirmación:
   ```
   Eliminar Área
   
   ¿Estás seguro de eliminar el área '[Nombre]'?
   
   Esta acción no se puede deshacer.
   
   [Eliminar]  [Cancelar]
   ```

4. Click en **"Eliminar"** para confirmar

5. ✅ Mensaje: "Área '[Nombre]' eliminada"

6. La lista se actualiza automáticamente

**⚠️ Importante:**
- Si estabas editando el área que eliminaste, la edición se cancela automáticamente
- La eliminación es permanente y no se puede deshacer

---

## 🎯 Flujo de Trabajo Completo

### Ejemplo: Actualizar información de un área

1. **Ver áreas** → La app carga automáticamente "Recursos Humanos", "Tecnología", etc.

2. **Editar** → Click en "Editar" de "Recursos Humanos"
   - Formulario se llena: "Recursos Humanos", "Departamento de gestión...", etc.
   - Botón cambia a "Actualizar Área"

3. **Modificar** → Cambias ubicación a "Edificio A - Piso 4"

4. **Guardar** → Click "Actualizar Área"
   - Mensaje: "Área actualizada exitosamente"
   - Lista muestra nueva ubicación

5. **Verificar** → El área aparece con la ubicación actualizada

---

## 🔍 Características Especiales

### Identificación Visual
- **NO necesitas recordar IDs numéricos**
- Todas las operaciones se hacen por nombre del área
- El sistema maneja los IDs internamente

### Estados Visuales
- **Estado Activo**: Badge verde
- **Estado Inactivo**: Badge rojo
- El switch en el formulario refleja el estado actual

### Validaciones
El formulario valida que:
- ✅ Nombre no esté vacío
- ✅ Descripción no esté vacía
- ✅ Ubicación no esté vacía

Si falta algún campo:
- ❌ Aparece un mensaje de error en el campo
- El campo inválido queda resaltado
- El cursor se posiciona en el campo con error

---

## 📊 Indicadores Visuales

### Formulario Normal (Crear)
```
┌─────────────────────────────────┐
│  [Campos del formulario]        │
└─────────────────────────────────┘

┌──────────────────┐
│ Registrar Área   │  ← Botón único
└──────────────────┘
```

### Formulario en Edición
```
┌─────────────────────────────────┐
│  [Campos llenos con datos]      │
└─────────────────────────────────┘

┌──────────────┐  ┌───────────┐
│ Actualizar   │  │ Cancelar  │  ← Dos botones
└──────────────┘  └───────────┘
```

### Item de Área en Lista
```
┌────────────────────────────────────────┐
│ 🏢 Recursos Humanos       [Activo ✅]  │
│ 📍 Edificio A - Piso 3                 │
│                                        │
│ Departamento de gestión de personal   │
│                                        │
│              [Editar]  [Eliminar]      │
└────────────────────────────────────────┘
```

---

## ⚡ Tips de Uso

1. **Crear rápido**: Después de registrar, el formulario se limpia automáticamente para crear otra área

2. **Editar múltiple**: Puedes editar varias áreas seguidas sin problema

3. **Cancelar seguro**: Si no estás seguro de los cambios, usa "Cancelar" para volver atrás

4. **Confirmación**: Solo las eliminaciones requieren confirmación adicional

5. **Actualización automática**: Después de cualquier operación, la lista se refresca sola

---

## 🐛 Solución de Problemas

### No se cargan las áreas
- **Verifica**: Backend corriendo en `localhost:8080`
- **Revisa**: Conexión del emulador (`10.0.2.2:8080`)
- **Prueba**: GET `http://localhost:8080/api/areas` en Postman

### Error al crear/actualizar
- **Verifica**: Todos los campos estén completos
- **Revisa**: Backend esté respondiendo
- **Comprueba**: Base de datos tenga `AUTO_INCREMENT` en `id_area`

### No aparece el botón Cancelar
- **Normal**: Solo aparece en modo edición
- **Verifica**: Hiciste click en "Editar" de algún área

### El área no se elimina
- **Verifica**: No tenga relaciones en otras tablas (Dispositivos, Bloques de Horario)
- **Solución**: Primero elimina las entidades relacionadas

---

## 📝 Notas Importantes

1. **IDs internos**: El sistema maneja IDs automáticamente, tú solo usas nombres

2. **Sincronización**: Los cambios se reflejan inmediatamente en la app y base de datos

3. **Persistencia**: Todos los cambios son permanentes (excepto si cancelas edición)

4. **Seguridad**: Las eliminaciones requieren confirmación para evitar borrados accidentales

5. **Estado por defecto**: Nuevas áreas se crean con estado "Activo"

---

## 🎨 Colores de Interfaz

- **Botón Editar**: Azul (`boton_principal_v2`)
- **Botón Eliminar**: Rojo (`boton_eliminar`)
- **Botón Registrar/Actualizar**: Naranja (`boton_principal`)
- **Botón Cancelar**: Gris (`boton_secundario`)
- **Badge Activo**: Verde (`item_registro_verde`)
- **Badge Inactivo**: Rojo (`item_registro_rojo`)

---

¡Listo! Ahora puedes gestionar áreas de forma completa usando solo los nombres. 🚀
