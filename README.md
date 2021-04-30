# CRUD-сервис
## Запуск приложения
Приложение контейнеризировано и запускается командой docker-compose up. В компоуз файле описаны 3 сервиса:
1. Непосредственно, само приложение
2. Flyway для миграции
3. Postgres DB

Также приложение может быть запущено через mvn package -> java -jar target/crud-test.jar, но для этого
придется отдельно запускать БД и выполнять миграции. 

Имеется docker-healthcheck для мониторинга состояния приложения.

Приложение реализовано в стиле многоуровневой архитектуры с вынесением доменной части, части 
представления, а также части конфигурирования приложения.

## Получение токена
Для получения токена необходимо отправить POST запрос по адресу {service_host}:{service_port}/login 
(в нашем случае адрес будет localhost:8010/login) с телом запроса:
```json
{
    "username": "name",
    "password": "pass"
}
```
В ответ сервер возвращает токен вида: eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJuYW1lIiwiYXV0aG9yaXRpZXMiOlsiVVNFUiJdLCJpYXQiOjE2MTk3MDc1OTcsImV4cCI6MTYyMDU3MTU5N30.7PmWNc2KJem1tGE5wh9yzLXNwygy6uFtbYT5VilCYibx337OUTbXMBwK6Ld5E8emmqX2c9M-p4sNZZzYL9-ljw.
Последующие эндпоинты будут доступны в сервисе при наличии хэдера Authorization, содержимое 
которого должно состоять из префикса Bearer и далее полученный при логине токен.

## Swagger
Доступен по ссылке {service_host}:{service_port}/swagger-ui.html (в нашем случае localhost:8010/swagger-ui.html)