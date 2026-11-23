# API RESTFULL — Sistema de Gestión de Soporte Técnico

Aplicación backend desarrollada en **Java** con **Spring Boot** que implementa una API RESTful para el registro, consulta, actualización y eliminación de **clientes**, **técnicos** y **solicitudes de soporte técnico**. El proyecto utiliza una arquitectura organizada por capas, validación de datos de entrada, manejo centralizado de excepciones y persistencia temporal en memoria.

---

## Integrantes del Equipo

| Integrante | Rol / Responsabilidad |
| :--- | :--- |
| **Juan Cajan** | Arquitectura backend, desarrollo de controladores, servicios, validaciones y pruebas en Postman |
| **Leslis Bellasmin** | Modelado de entidades, DTOs, manejo de excepciones, pruebas funcionales y documentación |

* **Institución:** ZEGEL  
* **Docente:** Mechan Timana Luis Alexander  

---

## Arquitectura y Tecnologías Utilizadas

* **Lenguaje:** Java 17 (o superior)
* **Framework:** Spring Boot 3.x (Spring Web, Spring Boot DevTools)
* **Validación:** Jakarta Bean Validation (`@Valid`, `@NotBlank`, `@NotNull`, `@Email`)
* **Gestor de dependencias:** Apache Maven
* **Persistencia:** Colecciones en memoria (`List` / `Map` / `ConcurrentHashMap`)
* **Pruebas funcionales:** Postman

### Estructura del Proyecto
```text
src/main/java/com/zegel/soporte/
├── controller/   # Controladores REST (@RestController) con rutas /api/v1
├── dto/          # Objetos de transferencia de datos y reglas de validación
├── exception/    # Manejador global de errores (@ControllerAdvice) y excepciones personalizadas
├── model/        # Entidades de dominio (Cliente, Tecnico, Solicitud) y enumeraciones
├── service/      # Interfaces e implementaciones de la lógica de negocio
└── SoporteTecnicoApplication.java
