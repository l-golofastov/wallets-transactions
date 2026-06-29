# wallets-transactions

Небольшой сервис для кошельков и переводов между ними.

Проект написан на Java 25, Spring Boot 3, Spring Data JPA и PostgreSQL.

## Как запустить

Нужен Docker.

```bash
docker compose up --build
```

После запуска приложение будет доступно по адресу:

```text
http://localhost:8080
```

База данных PostgreSQL поднимется рядом с приложением.

При первом запуске создаются два кошелька:

```text
ACC-001: 10000.00 RUB
ACC-002: 5000.00 RUB
```

Начальные данные создаются через файл `src/main/resources/data.sql`.

## Настройки базы

Основные настройки лежат в `src/main/resources/application.properties`.

Для Docker они передаются через переменные окружения:

```text
SPRING_DATASOURCE_URL
SPRING_DATASOURCE_USERNAME
SPRING_DATASOURCE_PASSWORD
```

## Тесты

В проекте есть тест:

```text
TransactionServiceTest
```

Он проверяет, что перевод не проходит, если у отправителя недостаточно денег.

Если Maven установлен локально, тесты можно запустить так:

```bash
mvn test
```
