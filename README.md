# AlertaPhishing - Servicio de Verificación

Servicio REST (Java 17, Spring Boot 3) que verifica enlaces, números de celular y cuentas CCI.

## Ejecutar
```
mvn spring-boot:run
```
- Swagger UI: http://localhost:8080/swagger-ui.html
- OpenAPI JSON: http://localhost:8080/v3/api-docs

## Probar
```
curl -X POST http://localhost:8080/api/v1/verificaciones \
  -H "Content-Type: application/json" -H "Accept-Language: es" \
  -d '{"tipo":"ENLACE","valor":"https://www.banco-verificacion-pe.com/login"}'
```
Cambia `Accept-Language` a `en` para recibir el mensaje en inglés.

Datos de ejemplo (data.sql): `banco-verificacion-pe.com` (5 reportes), `promo-yape-gratis.net` (1),
número `987654321` (4), cuenta `00219300123456789012` (3).

## Pruebas unitarias
```
mvn test
```

## Patrones
- Strategy: `strategy/Verificador` y sus implementaciones
- Factory: `factory/VerificadorFactory`
- Adapter: `adapter/AdaptadorFuenteExterna`
- Repository: `repository/AmenazaRepository`
