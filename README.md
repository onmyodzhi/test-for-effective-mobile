# REST сервис для управления задачами

Этот проект представляет собой **REST сервис** для управления задачами, с использованием технологий **Spring Boot**, **OAuth2.0** для авторизации и **PostgreSQL** для хранения данных.

## Описание

Сервис предоставляет API для работы с сущностью "Task", включая возможность:

- Получить список всех задачь с фильтрацией, сортировкой и пагинацией
- Получить информацию о задаче по ID
- Создать новую задачу
- Обновить информацию о задаче
- Удалить задачу

Также сервис поддерживает авторизацию через протокол **OAuth2.0** для получения `access_token`.

## Технологии

Проект использует следующие технологии:

- **Java 17**
- **Spring Boot 3.3.5**
- **Spring Security** для реализации OAuth2.0
- **PostgreSQL** для хранения данных
- **Lombok** для упрощения работы с кодом
- **Liquibase** для управления миграциями
- **Keycloak** для управления пользователями и аутентификацией
- **Docker** и **Docker Compose** для удобного развертывания
- и другие технологии, которые необходимы для реализации проекта (можно посмотреть в файле `pom.xml`)

## Установка и запуск

### Шаг 1: Клонировать репозиторий

```bash
git clone https://github.com/onmyodzhi/test-for-effective-mobile
cd your-repository-directory
```
### Шаг 2: Настройка переменных окружения

Если бы это не было тестовое задание то я бы попросил вас создать `.env` в корне проекта и заполнить его следующими значениями. Но так как это тестовое задание, делать я этого не буду и просто прикреплю его в корень проекта

### Шаг 3: Сборка и запуск через Docker

Для сборки и запуска проекта с использованием Docker, выполните команду:

```bash
docker-compose up --build
```

Ваши контейнеры будут собраны и запущены. При успешном завершении процесса сервис будет доступен по адресу **http://localhost:4141**, а интерфейс **Keycloak** (для авторизации) будет доступен по адресу **http://localhost:8080**.

### Шаг 4: Доступ к приложению

После успешного развертывания контейнеров, вы сможете получить доступ к вашему приложению:

- **Основной сервис** (API для работы с сущностью "Task"): [http://localhost:4141](http://localhost:4141)
- **Keycloak** (для авторизации через OAuth2.0): [http://localhost:8080](http://localhost:8080)

### Шаг 5: Авторизация и получение токена

Для авторизации через OAuth2.0 вам необходимо получить `access_token` от сервиса **Keycloak**. Для этого отправьте POST-запрос на эндпоинт `/auth`:

```bash
curl -X POST http://localhost:4141/auth \
    -H "Content-Type: application/json" \
    -d '{
            "email": "your_email",
            "password": "your_password"
        }'
```
Замените `your_email` и `your_password` на соответствующие данные, зарегистрированные в **Keycloak**. В ответе вы получите `access_token`, который будет использоваться для выполнения защищенных запросов к API.

### Примеры API запросов

После того как вы получите `access_token`, вы можете использовать его для выполнения запросов к API. Вот несколько примеров запросов:

#### 1. Получить информацию о задаче по ID

Для получения данных о задаче по его ID отправьте GET-запрос:

```bash
curl -X GET http://localhost:4141/v1/task/{id} \
    -H "Authorization: Bearer {your_access_token}"
```

Замените `{id}` на ID задачи, которую вы хотите получить, а access_token на полученный в предыдущем шаге `access_token`.

#### 2. Получить информацио о всех задачах

Для получения данных о задачах отправьте GET-запрос:

```bash
curl -X GET http://localhost:4141/v1/tasks \
    -H "Authorization: Bearer {your_access_token}"
```

Замените access_token на полученный в предыдущем шаге `access_token`.

#### и другие запросы (описаны в документации к API)

## Контакты

Если у вас есть вопросы или предложения, не стесняйтесь обращаться к [onmyodzhi](https://github.com/onmyodzhi).
- [телеграм](https://t.me/onmyodzhi)

## Ссылки

- [GitHub репозиторий](https://github.com/onmyodzhi/test-for-smart-delta-systems)