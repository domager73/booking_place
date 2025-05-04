# Booking Place API - Документация

## О проекте

Booking Place API - это RESTful сервис для управления бронированием рабочих мест. Проект использует:
- Java 17
- Spring Boot 3.4.5
- PostgreSQL 14
- Docker
- Swagger для документации API

## 🚀 Запуск проекта

### 1. Запуск с Docker (рекомендуется)

```bash
docker-compose up --build
```

Сервисы будут доступны:
- Бэкенд: `http://localhost:8080`
- Фронтенд: `http://localhost:3000`
- База данных: `postgres:5432`

### 2. Ручная настройка (без Docker)

1. Установите PostgreSQL и выполните init.sql
2. Настройте application.properties:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/mydatabase
   spring.datasource.username=myuser
   spring.datasource.password=mypassword
   ```
3. Запустите приложение:
```bash
./gradlew bootRun
```

##  API Endpoints

Документация Swagger доступна после запуска:
`http://localhost:8080/swagger-ui.html`

### **Пользователи** (`/users`)
- `POST /users/sign-in` - Регистрация пользователя
  ```json
  {
    "email": "user@example.com",
    "password": "password123",
    "fullName": "Иван Иванов"
  }
  ```
- `POST /users/login` - Авторизация пользователя

### **Рабочие места** (`/workspace`)
- `POST /workspace` - Создать рабочее место
  ```json
  {
    "name": "Рабочее место №1"
  }
  ```
- `GET /workspace` - Получить все места
- `DELETE /workspace/{id}` - Удалить место
- `PUT /workspace` - Обновить место

### **Бронирования** (`/booking`)
- `POST /booking` - Создать бронь
  ```json
  {
    "userId": 1,
    "workspaceId": 1,
    "startTime": "2025-05-10T09:00:00Z",
    "endTime": "2025-05-10T17:00:00Z"
  }
  ```
- `GET /booking/by-user/{userId}` - Брони пользователя
- `GET /booking/by-workspace/{workspaceId}` - Брони места
- `DELETE /booking/{id}` - Отменить бронь

##  Структура базы данных

Таблицы создаются автоматически из `init.sql`:

```sql
CREATE TABLE users (
  user_id SERIAL PRIMARY KEY,
  email VARCHAR(255) UNIQUE NOT NULL,
  password VARCHAR(255) NOT NULL,
  full_name VARCHAR(100) NOT NULL,
  role VARCHAR(20) NOT NULL DEFAULT 'user'
);

CREATE TABLE workspaces (
  workspace_id SERIAL PRIMARY KEY,
  name VARCHAR(100) NOT NULL
);

CREATE TABLE bookings (
  booking_id SERIAL PRIMARY KEY,
  user_id INTEGER REFERENCES users(user_id),
  workspace_id INTEGER REFERENCES workspaces(workspace_id),
  start_time TIMESTAMP WITH TIME ZONE NOT NULL,
  end_time TIMESTAMP WITH TIME ZONE NOT NULL
);
```

##  Настройки окружения

Основные переменные в `docker-compose.yml`:
```yaml
environment:
  SPRING_DATASOURCE_URL: jdbc:postgresql://postgres:5432/mydatabase
  SPRING_DATASOURCE_USERNAME: myuser
  SPRING_DATASOURCE_PASSWORD: mypassword
  SPRING_JPA_HIBERNATE_DDL-AUTO: validate
```

## Зависимости

Основные зависимости:
- Spring Boot Starter Web
- Spring Data JPA
- PostgreSQL Driver
- Lombok
- Swagger UI
- Validation API