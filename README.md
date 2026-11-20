# Pronósticos de playoffs de hockey — API REST

![CI](https://github.com/gaelg22/ejercicio06---Hockey/actions/workflows/ci.yml/badge.svg)

API para una quiniela de los playoffs de hockey: los usuarios pronostican el marcador de cada juego y los líderes de estadísticas de cada ronda, y se guardan sus puntos por ronda. Ejercicio de la materia en la UPIIZ-IPN.

## Stack

Java 17 · Spring Boot 3.3 · Spring Data JPA · H2 · Bean Validation · Swagger (springdoc) · JUnit 5 + Mockito · Docker · GitHub Actions

## Qué tiene

- **10 entidades** relacionadas: equipos, jugadores, rondas de playoffs, juegos, marcadores reales, usuarios, sus datos y sus pronósticos (de marcador y de estadísticas).
- CRUD REST para cada una, organizado en `controllers → services → repositories`.
- **Validaciones** con Bean Validation (`@NotNull`, `@Min`, `@Email`, `@Size`) y mensajes en español; una petición inválida regresa `400` con el detalle.
- Documentación con **Swagger UI** en `/swagger-ui.html`.
- Base H2 en memoria, así que corre sin instalar nada más.
- Dockerfile multi-stage: compila con Maven dentro de Docker, listo para Render.
- **Tests** que corren solos en cada push con GitHub Actions.

## Endpoints

Todos tienen `GET` (lista y por id), `POST`, `PUT /{id}` y `DELETE /{id}`.

| Ruta | Recurso |
|---|---|
| `/api/teams`, `/api/teamplayers` | Equipos y jugadores |
| `/api/playoffrounds`, `/api/games`, `/api/gamescores` | Rondas, juegos y marcadores reales |
| `/api/roundstats` | Líderes de estadísticas reales de cada ronda |
| `/api/users`, `/api/userinfo` | Usuarios y sus puntos por ronda |
| `/api/scorepicks`, `/api/statspicks` | Pronósticos de marcador y de estadísticas |

## Correrlo

```bash
./mvnw spring-boot:run          # levanta en el puerto 10000 (o el de PORT)
# o con Docker
docker build -t hockey-api . && docker run -p 10000:10000 hockey-api
```

## Tests

```bash
./mvnw test
```

- `ValidacionesTest`: reglas de validación de pronósticos, rondas y usuarios sin levantar Spring.
- `PlayoffRoundControllerTest`: respuestas del controlador con `@WebMvcTest` (lista, alta válida y alta inválida que regresa 400).
- `PlayoffRoundServiceTest`: el servicio con el repositorio mockeado.
- `HockeyApplicationTests`: que la app levante completa.
