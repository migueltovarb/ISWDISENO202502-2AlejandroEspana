# ParkiYa – Guía de uso de la API

Este documento explica cómo levantar el proyecto y cómo probar cada uno de los módulos expuestos por la API REST (usuarios, tarifas, reservas, códigos QR, pagos, notificaciones y reportes). Todos los ejemplos usan `curl`, pero puedes replicarlos en Postman u otra herramienta similar.

## Requisitos

- Java 21+
- Maven Wrapper incluido en el repo (`mvnw` / `mvnw.cmd`)
- MongoDB accesible (por defecto se usa la cadena definida en `application.properties`)

## Puesta en marcha

```bash
# Ejecutar pruebas unitarias y de integración
./mvnw test

# Levantar la aplicación
./mvnw spring-boot:run
```

La API queda disponible en `http://localhost:8080`.

## Uso desde Postman

1. Crea un **Environment** con la variable `baseUrl = http://localhost:8080`.
2. En cada request usa `{{baseUrl}}` para evitar cambiar URLs manualmente.
3. Importa una colección vacía y agrega los endpoints descritos abajo (método + ruta + payload).
4. Configura `Content-Type: application/json` en cada petición con cuerpo.

### Listado rápido de endpoints

| Módulo | Método | URL | Descripción |
| --- | --- | --- | --- |
| Usuarios | POST | `{{baseUrl}}/api/usuarios` | Registrar usuario |
| Usuarios | POST | `{{baseUrl}}/api/usuarios/auth/login` | Login |
| Usuarios | GET | `{{baseUrl}}/api/usuarios` | Listar |
| Usuarios | GET | `{{baseUrl}}/api/usuarios/{id}` | Obtener |
| Usuarios | PUT | `{{baseUrl}}/api/usuarios/{id}` | Actualizar |
| Tarifas | POST | `{{baseUrl}}/api/tarifas` | Crear |
| Tarifas | PUT | `{{baseUrl}}/api/tarifas/{id}` | Actualizar |
| Tarifas | GET | `{{baseUrl}}/api/tarifas` | Listar vigentes |
| Parqueaderos | POST | `{{baseUrl}}/api/parqueaderos` | Registrar parqueadero |
| Parqueaderos | PUT | `{{baseUrl}}/api/parqueaderos/{id}` | Actualizar |
| Parqueaderos | GET | `{{baseUrl}}/api/parqueaderos` | Listar |
| Parqueaderos | GET | `{{baseUrl}}/api/parqueaderos/{id}` | Obtener |
| Parqueaderos | GET | `{{baseUrl}}/api/parqueaderos/{id}/disponibilidad?fecha=YYYY-MM-DD` | Consultar cupos |
| Parqueaderos | GET | `{{baseUrl}}/api/parqueaderos/{id}/reporte?fecha=YYYY-MM-DD` | Reporte ocupación/facturación |
| Tarifas | GET | `{{baseUrl}}/api/tarifas/{id}` | Obtener |
| Tarifas | GET | `{{baseUrl}}/api/tarifas/{id}/calcular?minutos=90` | Calcular costo |
| Reservas | POST | `{{baseUrl}}/api/reservas` | Crear |
| Reservas | GET | `{{baseUrl}}/api/reservas` | Listar (opcional `?fecha=YYYY-MM-DD`) |
| Reservas | GET | `{{baseUrl}}/api/reservas/{id}` | Obtener |
| Reservas | GET | `{{baseUrl}}/api/reservas/cliente/{clienteId}` | Por usuario |
| Reservas | PATCH | `{{baseUrl}}/api/reservas/{id}/estado?estado=CONFIRMADA` | Cambiar estado |
| Códigos QR | POST | `{{baseUrl}}/api/codigos-qr?reservaId=...` | Generar |
| Códigos QR | POST | `{{baseUrl}}/api/codigos-qr/{codigoId}/validar` | Validar (entrada/salida) |
| Códigos QR | GET | `{{baseUrl}}/api/codigos-qr/reserva/{reservaId}` | Consultar |
| Pagos | POST | `{{baseUrl}}/api/pagos` | Registrar |
| Pagos | POST | `{{baseUrl}}/api/pagos/{id}/procesar` | Procesar |
| Pagos | POST | `{{baseUrl}}/api/pagos/{id}/confirmar` | Confirmar |
| Pagos | GET | `{{baseUrl}}/api/pagos/reserva/{reservaId}` | Pagos por reserva |
| Notificaciones | POST | `{{baseUrl}}/api/notificaciones` | Enviar |
| Notificaciones | GET | `{{baseUrl}}/api/notificaciones/usuario/{usuarioId}` | Por usuario |
| Notificaciones | GET | `{{baseUrl}}/api/notificaciones/reserva/{reservaId}` | Por reserva |
| Reportes | POST | `{{baseUrl}}/api/reportes` | Generar |
| Reportes | GET | `{{baseUrl}}/api/reportes` | Listar (opcional `?tipo=`) |
| Reportes | GET | `{{baseUrl}}/api/reportes/{id}` | Obtener |

## Happy paths recomendados (paso a paso)

Cada flujo se probó con los cuerpos mostrados. Copia exactamente estas estructuras en Postman:

### 1. Usuarios
1. **Registrar cliente**
   ```json
   {
     "nombre":"Ana",
     "correo":"ana@test.com",
     "telefono":"3152021451",
     "contrasena":"Prueba",
     "tipoUsuario":"CLIENTE"
   }
   ```
   Endpoint: `POST {{baseUrl}}/api/usuarios`
2. **Iniciar sesión**
   ```json
   {
     "correo":"ana@test.com",
     "contrasena":"Prueba"
   }
   ```
   Endpoint: `POST {{baseUrl}}/api/usuarios/auth/login`
3. **Listar usuarios**: `GET {{baseUrl}}/api/usuarios`

### 2. Tarifas
1. **Crear tarifa base**
   ```json
   {
     "tipo":"Diurna",
     "valorHora":5.5,
     "horarioInicio":"08:00:00",
     "horarioFin":"18:00:00",
     "vigente":true
   }
   ```
   Endpoint: `POST {{baseUrl}}/api/tarifas`
2. **Listar vigentes**: `GET {{baseUrl}}/api/tarifas`
3. **Calcular costo** (ej. 120 min): `GET {{baseUrl}}/api/tarifas/{tarifaId}/calcular?minutos=120`

### 3. Parqueaderos
1. **Crear parqueadero base**
   ```json
   {
     "nombre":"Sede Centro",
     "ubicacion":"Cra 10 # 20-30",
     "capacidadTotal":50
   }
   ```
   Endpoint: `POST {{baseUrl}}/api/parqueaderos`
2. **Consultar disponibilidad del día**
   ```
   GET {{baseUrl}}/api/parqueaderos/{parqueaderoId}/disponibilidad?fecha=2025-12-01
   ```
   - La respuesta indica `capacidadTotal`, `reservasProgramadas` y `cuposDisponibles`.
3. **Generar reporte de ocupación y facturación**
   ```
   GET {{baseUrl}}/api/parqueaderos/{parqueaderoId}/reporte?fecha=2025-12-01
   ```
   - Incluye cupos utilizados, porcentaje de ocupación y facturación total del día.

### 4. Reservas + Códigos QR
1. **Crear reserva** (usa IDs creados en los pasos previos).
   ```json
   {
     "horaEntrada":"2025-12-01T08:00:00",
     "horaSalida":"2025-12-01T10:00:00",
     "clienteId":"{{clienteId}}",
     "tarifaId":"{{tarifaId}}",
     "parqueaderoId":"{{parqueaderoId}}"
   }
   ```
   Endpoint: `POST {{baseUrl}}/api/reservas`
   - La respuesta incluye `codigoQrId`; guárdalo.
2. **Validar QR para ENTRADA**: `POST {{baseUrl}}/api/codigos-qr/{codigoQrId}/validar` (ejecútalo dentro de la ventana permitida).
3. **Validar QR para SALIDA** (segunda llamada al mismo endpoint después de la hora programada).
4. **Consultar reserva** para ver `minutosConsumidos` y `cargoAdicional` actualizados: `GET {{baseUrl}}/api/reservas/{id}`

### 5. Pagos
1. **Registrar pago pendiente**
   ```json
   {
     "reservaId":"{{reservaId}}",
     "metodo":"EFECTIVO",
     "monto":150.0,
     "referenciaTransaccion":"ORD-001"
   }
   ```
   Endpoint: `POST {{baseUrl}}/api/pagos`
2. **Procesar**: `POST {{baseUrl}}/api/pagos/{pagoId}/procesar`
3. **Confirmar**: `POST {{baseUrl}}/api/pagos/{pagoId}/confirmar` (la reserva pasa a `CONFIRMADA`).

### 6. Notificaciones
1. **Enviar aviso**
   ```json
   {
     "mensaje":"Tu reserva esta lista",
     "tipo":"APP",
     "usuarioId":"{{clienteId}}",
     "reservaId":"{{reservaId}}",
     "idUsuarioDestino":"{{clienteId}}"
   }
   ```
   Endpoint: `POST {{baseUrl}}/api/notificaciones`
2. **Consultar por usuario**: `GET {{baseUrl}}/api/notificaciones/usuario/{{clienteId}}`
3. **Consultar por reserva**: `GET {{baseUrl}}/api/notificaciones/reserva/{{reservaId}}`

### 7. Reportes
1. **Generar reporte financiero**
   ```json
   {
     "tipo":"FINANCIERO",
     "contenido":"Ingresos diarios del parqueadero"
   }
   ```
   Endpoint: `POST {{baseUrl}}/api/reportes`
2. **Listar todos / filtrar por tipo**: `GET {{baseUrl}}/api/reportes?tipo=FINANCIERO`
3. **Obtener por ID**: `GET {{baseUrl}}/api/reportes/{reporteId}`

## Convenciones de respuesta

Todas las rutas devuelven `ApiResponse<T>`:

```json
{
  "success": true,
  "message": "Texto opcional",
  "data": { ... }
}
```

## 1. Usuarios (`/api/usuarios`)

Registrar cliente:

```bash
curl -X POST http://localhost:8080/api/usuarios ^
  -H "Content-Type: application/json" ^
  -d "{\"nombre\":\"Ana\",\"correo\":\"ana@test.com\",\"telefono\":\"123\",\"contrasena\":\"clave\",\"tipoUsuario\":\"CLIENTE\"}"
```

Login:

```bash
curl -X POST http://localhost:8080/api/usuarios/auth/login ^
  -H "Content-Type: application/json" ^
  -d "{\"correo\":\"ana@test.com\",\"contrasena\":\"clave\"}"
```

Listar / obtener / actualizar:

```bash
curl http://localhost:8080/api/usuarios
curl http://localhost:8080/api/usuarios/{id}
curl -X PUT http://localhost:8080/api/usuarios/{id} -H "Content-Type: application/json" -d "{...}"
```

## 2. Tarifas (`/api/tarifas`)

Crear tarifa:

```bash
curl -X POST http://localhost:8080/api/tarifas ^
  -H "Content-Type: application/json" ^
  -d "{\"tipo\":\"General\",\"valorHora\":5.5}"
```

Actualizar, listar vigentes, obtener, calcular costo:

```bash
curl -X PUT http://localhost:8080/api/tarifas/{id} -H "Content-Type: application/json" -d "{...}"
curl http://localhost:8080/api/tarifas
curl http://localhost:8080/api/tarifas/{id}
curl http://localhost:8080/api/tarifas/{id}/calcular?minutos=90
```

## 3. Parqueaderos (`/api/parqueaderos`)

Registrar parqueadero y consultar disponibilidad:

```bash
curl -X POST http://localhost:8080/api/parqueaderos ^
  -H "Content-Type: application/json" ^
  -d "{\"nombre\":\"Sede Centro\",\"ubicacion\":\"Cra 10 # 20-30\",\"capacidadTotal\":50}"

curl http://localhost:8080/api/parqueaderos
curl http://localhost:8080/api/parqueaderos/{id}
curl "http://localhost:8080/api/parqueaderos/{id}/disponibilidad?fecha=2025-12-01"
```

## 4. Reservas (`/api/reservas`)

Crear reserva (genera automáticamente un código QR activo):

```bash
curl -X POST http://localhost:8080/api/reservas ^
  -H "Content-Type: application/json" ^
  -d "{\"horaEntrada\":\"2025-12-01T08:00:00\",\"horaSalida\":\"2025-12-01T10:00:00\",\"clienteId\":\"{clienteId}\",\"tarifaId\":\"{tarifaId}\",\"parqueaderoId\":\"{parqueaderoId}\"}"
```

Consultar, filtrar por fecha o cliente, actualizar estado:

```bash
curl http://localhost:8080/api/reservas
curl http://localhost:8080/api/reservas?fecha=2025-12-01
curl http://localhost:8080/api/reservas/{id}
curl http://localhost:8080/api/reservas/cliente/{clienteId}
curl -X PATCH "http://localhost:8080/api/reservas/{id}/estado?estado=CONFIRMADA"
```

Los campos `minutosConsumidos` y `cargoAdicional` se actualizan automáticamente al validar la salida con el QR.

## 5. Códigos QR (`/api/codigos-qr`)

Los QR tienen dos usos: entrada y salida. Se expiran si el usuario intenta entrar antes de 15 minutos de la hora programada o llega más de 15 minutos tarde. Tras la entrada se recalcula la expiración para la salida (ventana de 10 minutos de gracia), y al validar la salida se cobra automáticamente cualquier exceso de tiempo.

Endpoints:

```bash
# Generar (ya se hace al crear la reserva, pero se puede regenerar)
curl -X POST "http://localhost:8080/api/codigos-qr?reservaId={reservaId}"

# Validar uso (primera vez = entrada, segunda vez = salida)
curl -X POST http://localhost:8080/api/codigos-qr/{codigoId}/validar

# Obtener por reserva
curl http://localhost:8080/api/codigos-qr/reserva/{reservaId}
```

Errores comunes:
- `BusinessException` cuando se intenta ingresar fuera de la ventana o si el código ya fue usado.
- Estado cambia automáticamente a `EXPIRADO` o `DESACTIVADO` según corresponda.

## 6. Pagos (`/api/pagos`)

Registrar pago asociado a una reserva:

```bash
curl -X POST http://localhost:8080/api/pagos ^
  -H "Content-Type: application/json" ^
  -d "{\"reservaId\":\"{reservaId}\",\"metodo\":\"EFECTIVO\",\"monto\":20}"
```

Flujo completo:

```bash
curl -X POST http://localhost:8080/api/pagos/{pagoId}/procesar
curl -X POST http://localhost:8080/api/pagos/{pagoId}/confirmar
curl http://localhost:8080/api/pagos/reserva/{reservaId}
```

Al confirmar se actualiza la reserva a `CONFIRMADA`.

## 7. Notificaciones (`/api/notificaciones`)

Enviar y consultar notificaciones:

```bash
curl -X POST http://localhost:8080/api/notificaciones ^
  -H "Content-Type: application/json" ^
  -d "{\"mensaje\":\"Tu reserva está lista\",\"tipo\":\"APP\",\"usuarioId\":\"{clienteId}\"}"

curl http://localhost:8080/api/notificaciones/usuario/{usuarioId}
curl http://localhost:8080/api/notificaciones/reserva/{reservaId}
```

## 8. Reportes (`/api/reportes`)

```bash
curl -X POST http://localhost:8080/api/reportes ^
  -H "Content-Type: application/json" ^
  -d "{\"tipo\":\"FINANCIERO\",\"contenido\":\"Detalle...\"}"

curl http://localhost:8080/api/reportes
curl http://localhost:8080/api/reportes?tipo=OPERATIVO
curl http://localhost:8080/api/reportes/{id}
```

## Disponibilidad en tiempo real y reportes de ocupación

- `GET /api/parqueaderos/{id}/disponibilidad?fecha=` devuelve en tiempo real la capacidad total, reservas programadas y cupos disponibles para cada parqueadero en una fecha específica (si se omite la fecha usa el día actual). Las reservas canceladas no se cuentan para los cupos usados.
- `GET /api/parqueaderos/{id}/reporte?fecha=` genera un resumen diario de ocupación y facturación, indicando cuántas reservas confirmadas tuvo el parqueadero, el porcentaje de ocupación frente a su capacidad y la facturación total calculada con los montos de las reservas no canceladas.

Recuerda incluir `parqueaderoId` cuando crees una reserva para que estas métricas estén sincronizadas.

## Escenarios recomendados de prueba

1. **Flujo completo**: crear usuario → crear tarifa → crear reserva → obtener QR → validar entrada a la hora correcta → validar salida con retraso para verificar `cargoAdicional` → confirmar pago.
2. **Expiración de QR**: intentar validar después de la ventana de entrada para verificar estado `EXPIRADO`.
3. **Uso indebido**: validar un QR más de dos veces y esperar el mensaje de negocio.
4. **Reportería y notificaciones**: generar reportes y notificaciones vinculados a la reserva para comprobar filtros por usuario/reserva.

Con esto dispones de todos los pasos necesarios para probar el funcionamiento de la API y los nuevos controles del QR de entrada/salida.
