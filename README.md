# Spring WebFlux API Example

Este proyecto es una demostración del uso de **Spring WebFlux**, una tecnología orientada a la programación reactiva y no bloqueante. Se basa en dos funcionalidades:

1. **CRUD de Usuarios**: Un `UserController` conectado a una base de datos **MongoDB** para realizar operaciones básicas de creación, lectura, actualización y eliminación de usuarios.
2. **Controlador Asíncrono**: Un `UserAsyncController` que realiza tres peticiones en paralelo a servicios externos mockeados usando **WebClient**.

Además, el proyecto incluye una configuración para ejecutar las imágenes de Docker (API y MongoDB) mediante **Docker Compose**.

---

## Funcionalidades

### 1. CRUD de Usuarios

El controlador `UserController` expone endpoints para interactuar con una base de datos **MongoDB**. Las operaciones soportadas son:

- **Crear Usuario**: `POST /users`
- **Obtener Usuario por ID**: `GET /users/{id}`
- **Actualizar Usuario**: `PUT /users/{id}`
- **Eliminar Usuario**: `DELETE /users/{id}`

Se utiliza programación reactiva con `Mono` y `Flux` para interactuar con la base de datos Mongo.

### 2. Controlador Asíncrono

El controlador `UserAsyncController` simula llamadas a servicios externos usando **WebClient**. Estas peticiones se realizan en paralelo.

- Endpoint: `GET /async-users`
- Resultado: Una respuesta JSON combinando los resultados de tres servicios mockeados.

#### Mockeo de Servicios Externos

Los servicios mockeados están configurados con **Mocky**. URLs para generar mocks [https://mocky.io/](https://mocky.io/).

---

## Arquitectura de Carpetas

```plaintext
src
└── main
    ├── java/com/spring/webflux
    │   ├── controller
    │   │   ├── UserController.java
    │   │   ├── UserAsyncController.java
    │   ├── model
    │   │   └── User.java
    │   ├── repository
    │   │   └── UserRepository.java
    │   ├── service
    │   │   └── UserService.java
    │   │   └── UserAsyncService.java
    │   ├── exception
    │   │   ├── CustomException.java
    │   │   ├── DatabaseException.java
    │   │   ├── UserNotFoundException.java
    │   │   ├── GlobalExceptionHandler.java
    └── resources
        └── application.properties
```

---

## CURLS de ejemplos

```curl
curl --location 'http://localhost:8080/users' \
--header 'Content-Type: application/json' \
--data-raw '{"name":"Ariel Ortega", "email":"ariel.ortega@gmail.com"}'
```

```curl
curl --location 'http://localhost:8080/users/671e5e53d9bf6208ef0e8dd6'
```

```curl
curl --location --request DELETE 'http://localhost:8080/users/673a4bbe55dd8b52acae9339'
```

```curl
curl --location 'http://localhost:8080/async-users'
```

---

## DOCKER COMPOSE

```docker-compose
version: '3.8'
services:
  mongodb:
    image: mongo:latest
    container_name: mongo
    ports:
      - 27017:27017
    environment:
      MONGO_INITDB_ROOT_USERNAME: root
      MONGO_INITDB_ROOT_PASSWORD: example

  mongo-express:
    image: mongo-express:latest
    container_name: mongo-express
    depends_on:
      - mongodb
    ports:
      - 8081:8081
    environment:
      ME_CONFIG_MONGODB_ADMINUSERNAME: root
      ME_CONFIG_MONGODB_ADMINPASSWORD: example
      ME_CONFIG_MONGODB_SERVER: mongodb

```
