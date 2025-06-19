# Challenge PinApp

## ✅ Funcionalidad
- Registro e inicio de sesión con JWT
- Endpoints protegidos con autenticación
- RabbitMQ para tareas asíncronas: al crear un cliente, se envía un mensaje a la cola. Se eligió usarlo en este punto suponiendo 
que en un caso real debería enviarse un correo notificando al cliente de la creación exitosa. Se desacopla del flujo
original ya que el proveedor de envio de correo puede realizarlo de forma asíncrona)
- Se utilizó prometheus para exponer métricas

## Ejecutar RabbitMQ localmente
- Es necesario tener docker instalado
```bash
  docker run -d --hostname rabbit --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3-management
```

## Desplegar con Docker
- Si queremos probar todo el bloque completo podemos realizarlo con docker compose. La ejecución del siguiente comando va
- alevantar una imagen de la aplicación, la cola, prometheus y grafana
- Debemos abrir una consola en la ruta del proyecto donde se encuentra el file docker-compose.yml
- Ejecutar el siguiente comando
```bash
  docker compose up -d --build
```

# Probar los Endpoints de la Aplicación:

- Swagger UI:
```sh
- http://localhost:8080/swagger-ui.html
```
- Se adjunta colección de postman en el proyecto

- Registrar un usuario
```sh
-  http://localhost:8080/auth/register
```
```
{
  "username": "fabrialbrieu",
  "password": "password123"
}

```

- Autenticar un usuario
```sh
-  http://localhost:8080/auth/login
```
```
{
  "username": "fabrialbrieu",
  "password": "password123"
}

```

------------------------------

- Crear un cliente (POST)

Debo previamente crear un usuario, autenticarlo y utilizar el token generado para la autenticación.
```sh
-  http://localhost:8080/api/customers
```
```
{
    "name": "Fabri",
    "surname": "Albrieu",
    "dateOfBirth": "2000-10-27"
}

```

- Obtener un listado de los clientes y promedio de edad (GET)
```sh
-  http://localhost:8080/api/customers
```




