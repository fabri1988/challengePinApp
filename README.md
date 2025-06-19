# Challenge PinApp

## ✅ Funcionalidad
- Registro e inicio de sesión con JWT
- Endpoint protegido con autenticación
- RabbitMQ para tareas asíncronas: al crear un cliente, se envía un mensaje a la cola. Se eligió usarlo en este punto suponiendo 
que en un caso real debería enviarse un correo notificando al cliente de la creación exitosa. Se desacopla del flujo
original ya que el proveedor de envio de correo puede realizarlo de forma asíncrona)

## Ejecutar RabbitMQ localmente
- Es necesario tener docker instalado
```bash
docker run -d --hostname rabbit --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3-management
```

## Desplegar con Docker
- Si queremos probar todo el bloque completo podemos levantar una imagen de la app, la cola, prometheus y grafana
- Debemos abrir una consola en la ruta del proyecto donde se encuentra el file docker-compose.yml
- Ejecutar el siguiente comando
```bash
docker compose up -d --build
```

# Probar los Endpoints de la Aplicación:

- Swagger UI: http://localhost:8080/swagger-ui.html 
- Se adjunta colección de postman en el proyecto



