# FinanceApp
_____
В данном репозитории находится реализация финансового приложения в рамках хакатона **Sovcombank Team Challenge 2022**

##Описание выбранного стека
Чтобы реализовать весь заданный функционал, мы решили воспользоватся стандарнтной клиент-серверной архитектурой.

### Frontend
___
Для реализации клиентского приложения был выбран framework **React**. Он прекрасно подходит для этой задачи т.к. финансовое приложение имеет множество повторяющихся модулей - таких как  блок отображения курса валюты, график её стоимости и т.д.
Для Стилизации приложения был использован **Sass** он также является более модульным препроцессором для css.
Для связи с сервером используется **Axious** - эта библиотека поддерживает способ авторизации jwt который мы хотели использовать.
Для сборки фронтовой части был испльзован современный инструмент Vite. Он работает в разу быстрее обычного WebPack и имеет более удобную настройку.

Макет приложения в [figma](https://www.figma.com/file/VP59Idg3xNbbWvfQndj9to/Skolkovo-X-%D0%A1%D0%BE%D0%B2%D0%BA%D0%BE%D0%BC%D0%B1%D0%B0%D0%BD%D0%BA?node-id=0%3A1&t=IhHPwxUCDgAPaLLM-1)

### Backend
____
Backend реализован на **Spring Boot**
Хотели сделать авторизацию пользователя с использование spring boot security. Данные из фронта должны были пересылаться на бэк , если пользовательно только регестрируется, то с помощью работы с jpa repositories данные сохранялись бы в базу данных.

Каждый запрос должен был проходить через модуль spring security, где запросы фильтровались бы, а так же там кеширивался бы пароль пользователя.

Сама архитектура у нас построена таким образом, что есть Controllers , которые работаю с пользователями через DTO сущности. Данные запросов пользователя отправляются через Rest api, далее обрабатываются на сервере, после чего отправляется ответ, содержащий сформированную информацию.

Приложение разбито на 3 слоя.

Первый слой - это controllers, которые соединяются с клиентской частью. Сущности, используемые в работе с контроллерами - DTO. Данные сущности используются только в этом слое. С помощью ModelMappers реализован перевод одной сущности в другую.

Далее идёт бизнес слой - в него помещена вся логика приложения. В этом слое мы подключаемся с помощью WebClient к сайту с информацие про валюту http://www.cbr.ru и достаем оттуда данные в формате xml и парсим нужные нам поля. Всё это делается через различные сервисы и соответсвующие модели.

Последний слой - это слой работы с базой данных. Здесь используются repositories, позволяющие взаимодействовать с бд.

Всё это находится в модуле finance. Но кроме того, реализован ещё один модуль, на который сделан основной акцент.
___
Для хранения данных используется бд Postgre

Вид диаграммы сущностей бд

![bd diagram](https://github.com/techi9/SovcomBank_Hackathon/blob/master/bd_diagram.png)

Сущность credentials содержит дополнительную информацию о поьзователе - email и номер телефона. Данные поля пользователь можен часто изменять.

Сущность passport содержит поля такие, как имя, фамилия, отчество, номер пасспорта и дата рождения. Эти данные были вынесены в отдельную таблицу, так как их изменение практически невозможно. Это некая статическая информация о пользователе.

Сущность user содержит поле роли - пользователь может быть либо администратором, либо пользователем (от этого зависит, какой модель ему будет отображаться). Так же поле статус указывает на то, какие возможности есть у пользователя. Если пользователь находится в статусе ожидание подтверждения, то он не сможет пользоваться сайтом, пока не дождется подтвержение аккаунта со стороны администрации. Так же пользователь может быть заблокирован, что лишает его возможности пользоваться своими счетами.

Сущность account содержит в себе тип валюты, в которой хранятся средства на счету, количество средств, номер счёта, статус (счёт может быть заблокирован). А так же между пользователем и счётом есть связь один ко много - у пользователя может быть много счетов.

Сущность transaction хранит информацию о произведенной операции. Она хранит дату транзакции, количество средств, используемых в транзакции, а также счета, участвующие в транзакции.

Таблицы transaction и user связаны между собой отношение многие ко многим.

Также есть таблица confirmation_token, которая используется для хранения токена, подтверждающего пользователя. Этот токен должен использоваться для аутентификации пользователя на клиентской части.

Таблица transaction_queue используется для модуля woker. В данной таблице хранятся транзакции, которые еще не обработаны сервером. task_status указывает, в каком состоянии находится обработка транзакции (ожидание, в процессе, завершена).
___
Основной упор мы сделали на модуль woker. Он представляет собой некую очередь транзакций. Каждая транзакция, которая приходит на сервер добавляется в очередь. Далее транзакции разпределяются на потоки, которые будут обрабатывать каждую из них. Проблема, которую решает данный модуль - это исключение возможности того, чтобы при возникновении любых проблем невозможности сервера обработать запрос в данный момент, транзакция не будет теряться, а будет сохранена в базу данных и ожидать очереди обработки. Так же исключена возможность параллельного изменения данных.



### Deploy
___
Для того чтобы весь проект было удобно развертывать где угодно, все модули проекты имеют свой **dockerfile**. А для запуска всех контейнеров используется **DockerCompose**.

Внутри контейнера с Frontend используется http-сервер Nginx.

Для запуска проекта можно использовать команду - 

    docker-compose up -f docker-compose.yml