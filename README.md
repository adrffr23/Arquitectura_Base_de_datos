
# CachePerformanceApp

## Descripción

Aplicación desarrollada en Java y Spring Boot para
comparar los tiempos de acceso a una base de datos
con y sin la utilización de memoria caché.

El sistema utiliza H2 Database para almacenar
10.000 registros de productos tecnológicos y
Caffeine para optimizar las consultas repetidas.

## Tecnologías

- Java 17
- Spring Boot 4.1.1
- Spring Data JPA
- H2 Database
- Spring Cache
- Caffeine
- HTML5
- CSS3
- JavaScript
- Maven

## Funcionalidades

- Consulta de productos sin caché.
- Consulta de productos con caché.
- Medición de tiempos en milisegundos.
- Promedios acumulativos independientes.
- Contador de ejecuciones.
- Comparación gráfica de rendimiento.
- Cálculo del porcentaje de mejora.
- Limpieza de caché.
- Reinicio de estadísticas.

## Arquitectura

Frontend (HTML, CSS, JavaScript)
        |
        v
ProductoController
        |
        v
ProductoService
        |
        +---- H2 Database
        |
        +---- Spring Cache / Caffeine

EstadisticasService registra los tiempos
y calcula los promedios acumulativos.

## Ejecución

### Requisitos

- JDK 17 o superior compatible con el proyecto.
- Acceso a internet en la primera compilación.

### Iniciar la aplicación

En Windows, abrir una terminal en la carpeta
principal del proyecto y ejecutar:

```powershell
.\mvnw.cmd spring-boot:run
```

Abrir en el navegador:

http://localhost:8080/

La primera ejecución crea automáticamente
10.000 registros si la base de datos está vacía.

## Endpoints REST

| Método | Ruta | Descripción |
|---|---|---|
| GET | /api/productos/sin-cache | Consulta sin caché |
| GET | /api/productos/con-cache | Consulta con caché |
| GET | /api/productos/estadisticas | Estadísticas generales |
| POST | /api/productos/limpiar-cache | Limpiar caché |
| POST | /api/productos/reiniciar-estadisticas | Reiniciar promedios |

## Medición de rendimiento

Se utiliza System.nanoTime() para medir el
tiempo de obtención de los productos.

Cada consulta actualiza su contador y
promedio acumulado de forma independiente.

El promedio con caché incluye la primera
consulta, que requiere cargar los datos.

Los resultados pueden variar según el equipo
y las condiciones de ejecución.

## CORS

CORS (Cross-Origin Resource Sharing) es un
mecanismo de seguridad de los navegadores
que controla las solicitudes entre orígenes
diferentes.

En este proyecto, el frontend y la API
se ejecutan en el mismo origen:

http://localhost:8080

Por eso no es necesario habilitar CORS.

En un entorno de desarrollo con frontend
separado, Spring Boot puede configurar
los orígenes permitidos mediante @CrossOrigin
o una configuración global de WebMvcConfigurer.


Al ejecutar el proyecto en otro equipo,
se genera una nueva base de datos con los
10.000 registros de prueba.


