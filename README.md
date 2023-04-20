# PersonalBlog
RESTful приложение предоставляющее API для управления личным блогом. Доступны регистрация новых пользователей, аутентификация при помощи JWT, сброс пароля с использованием JavaMailSender, создание новых постов, оставление коментариев и лайков.
## Особенности
- Верификация почты с помощью JavaMailSender и токена.
- Сброс пароля с помощью JavaMailSender и токена.
- Автоудаление истёкших токенов верификации планировщиком событий.
- JWT аутенитфикация.
- Возможность запускать приложение в Docker контейнере.
- Кэширование результатов обращения к базе данных.
## Стек технологий
- Spring Boot
- Spring Security
- Spring Data JPA
- Spring Cache
- Spring Email
- Hibernate
- PostgreSQL
- Docker
- REST
- JSON
- JWT
- Maven
- Junit
## Как запустить
1. Скачайте и установите DOCKER с [официального сайта](https://www.docker.com).
2. [Настройте почту](https://yandex.ru/support/mail/mail-clients/others.html), введите логин и пароль в `application.properties`.
3. Выполните `mvnw clean install`, а затем `docker-composer up --build` в папке проекта.
4. Приложение запустится по адресу [localhost:8080](http://localhost:8080).
