<h1>Voting system for deciding where to have lunch 

(Приложение развернуто в application context: <b>vote</b>)

<h2>Постановка задачи:

Design and implement a REST API using Hibernate/Spring/SpringMVC (or Spring-Boot) without frontend.

Build a voting system for deciding where to have lunch.

* 2 types of users: admin and regular users
* Admin can input a restaurant and it's lunch menu of the day (2-5 items usually, just a dish name and price)
* Menu changes each day (admins do the updates)
* Users can vote on which restaurant they want to have lunch at
* Only one vote counted per user
* If user votes again the same day:
    - If it is before 11:00 we asume that he changed his mind.
    - If it is after 11:00 then it is too late, vote can't be changed
    
Each restaurant provides new menu each day.

<h2>REST API. Доступная функциональность.

Подробные примеры наполнения POST/PUT запросов см. в разделе с CURL примерами

**Основная функциональность (кратко):**
* **GET /vote/rest/v1/profile/restaurants** - список всех ресторанов с меню на сегодня
* **GET /vote/rest/v1/profile/results** - получение результатов голосования на сегодня (разрешено получать промежуточные итоги голосования до 11-00)
* **POST /vote/rest/v1/profile/vote** - сохранение голоса текущего пользователя (функциональность не работает после 11-00 по времени сервера)

<h3>Функциональность пользователя с правами Administrator (admin): 

* **GET /vote/rest/v1/admin/restaurants** - список всех ресторанов (без меню)
* **POST /vote/rest/v1/admin/restaurants** - внесение нового ресторана в базу 
* **PUT /vote/rest/v1/admin/restaurants/{restaurantId}** - изменение данных ресторана с id: {restaurantId}
* **DELETE /vote/rest/v1/admin/restaurants/{restaurantId}** - удаление ресторана с id: {restaurantId}

* **POST /vote/rest/v1/admin/restaurants/{restaurantId}/dishes** - добавление нового блюда в меню ресторана с id: {restaurantId}
* **PUT /vote/rest/v1/admin/restaurants/{restaurantId}/dishes/{dishId}** - изменение данных блюда с id: {dishId}
* **DELETE /vote/rest/v1/admin/restaurants/{restaurantId}/dishes/{dishId}** - удаление блюда с id: {dishId}

* **GET /rest/v1/admin/users** - получение списка всех учетных записей пользователей
* **GET /rest/v1/admin/users/{userId}** - получение учетных данных пользователя с id: {userId}
* **POST /rest/v1/admin/users** создание нового пользователя
* **PUT /rest/v1/admin/users/{userId}** - изменение учетных данных пользователя с id: {userId}
* **DELETE /rest/v1/admin/users/{userId}** - удаление учетной записи пользователя с id: {userId}

<h3>Функциональность пользователя с правами User (regular user): </h3>

* **GET /vote/rest/v1/profile/restaurants** - список всех ресторанов с меню на сегодня
* **GET /vote/rest/v1/profile/restaurants?date=YYYY-MM-DD** список всех ресторанов с меню на дату: YYYY-MM-DD

* **GET /vote/rest/v1/profile/vote** - получение голоса текущего пользователя на сегодня
* **GET /vote/rest/v1/profile/vote?date=YYYY-MM-DD** получение голоса текущего пользователя на дату: YYYY-MM-DD
* **POST /vote/rest/v1/profile/vote** - сохранение голоса текущего пользователя (функциональность не работает после 11-00 по времени сервера)
* **PUT /vote/rest/v1/profile/vote/{voteId}** - изменение голоса текущего пользователя (функциональность не работает после 11-00 по времени сервера)
* **DELETE /vote/rest/v1/profile/vote** - удаление из базы голоса текущего пользователя

* **GET /vote/rest/v1/profile/results** - получение результатов голосования на сегодня (разрешено получать промежуточные итоги голосования до 11-00)
* **GET /vote/rest/v1/profile/results?date=YYYY-MM-DD** получение результатов голосования на дату: YYYY-MM-DD

* **GET /vote/rest/v1/profile** - получение учетных данных текущего пользователя
* **PUT /vote/rest/v1/profile** - изменение учетных данных текущего пользователя
* **DELETE /vote/rest/v1/profile** - удаление учетных данных текущего пользователя 

<h3> Примеры CURL команд </h3>
<b>!!</b> В БД при инициализации всем временным полям проставляются даты в зависимости от текущей даны ( now() ) следует учесть это в curl командах, передающих даты
<h5> Administrator (управление ресторанами):

* GET /vote/rest/v1/admin/restaurants:
> `curl http://localhost:8080/vote/rest/v1/admin/restaurants --user Admin1:admin`
* POST /vote/rest/v1/admin/restaurants:
> `curl -g -X POST -d "{\"name\":\"Restaurant3\",\"description\":\"Restaurant3 description\"}" -H "Content-Type:application/json;charset=UTF-8" http://localhost:8080/vote/rest/v1/admin/restaurants --user  Admin1:admin`
* PUT /vote/rest/v1/admin/restaurants/100004
> `curl -X PUT -d "{\"name\":\"Restaurant1 updated name\",\"description\":\"Restaurant1 updated description\"}" -H "Content-Type:application/json;charset=UTF-8" http://localhost:8080/vote/rest/v1/admin/restaurants/100004 --user  Admin1:admin`
* DELETE /vote/rest/v1/admin/restaurants/100004
> `curl -X DELETE http://localhost:8080/vote/rest/v1/admin/restaurants/100004 --user Admin1:admin`

<h5> Administrator (управление меню):

* POST /vote/rest/v1/admin/restaurants/100005/dishes:
> `curl -g -X POST -d "{\"name\":\"New dish in restaurant 2\",\"date\":\"2018-01-20\",\"price\":\"4.50\"}" -H "Content-Type:application/json;charset=UTF-8" http://localhost:8080/vote/rest/v1/admin/restaurants/100005/dishes --user  Admin1:admin`
* PUT /vote/rest/v1/admin/restaurants/100005/dishes/100016:
> `curl -g -X PUT -d "{\"name\":\"Sicilian Cheese Pizza Updated\",\"date\":\"2018-01-20\",\"price\":\"4.50\"}" -H "Content-Type:application/json;charset=UTF-8" http://localhost:8080/vote/rest/v1/admin/restaurants/100005/dishes/100016 --user  Admin1:admin`
* DELETE /vote/rest/v1/admin/restaurants/100005/dishes/100016
> `curl -X DELETE http://localhost:8080/vote/rest/v1/admin/restaurants/100005/dishes/100016 --user Admin1:admin`

<h5> Administrator (управление учетными записями пользователей):

* GET /rest/v1/admin/users
> `curl http://localhost:8080/vote/rest/v1/admin/users --user Admin1:admin`
* GET /rest/v1/admin/users/100001
> `curl http://localhost:8080/vote/rest/v1/admin/users/100001 --user Admin1:admin`
* POST /rest/v1/admin/users
> `curl -g -X POST -d "{\"name\":\"Voter3\",\"nickname\":\"Voter3\",\"password\":\"12345\",\"enabled\":\"true\",\"roles\":[\"ROLE_USER\"]}" -H "Content-Type:application/json;charset=UTF-8" http://localhost:8080/vote/rest/v1/admin/users --user  Admin1:admin`
* PUT /rest/v1/admin/users/100001
> `curl -g -X PUT -d "{\"name\":\"Voter2 Updated\",\"nickname\":\"Voter2 updated\",\"password\":\"12345\",\"enabled\":\"true\",\"roles\":[\"ROLE_USER\"]}" -H "Content-Type:application/json;charset=UTF-8" http://localhost:8080/vote/rest/v1/admin/users/100001 --user  Admin1:admin`
* DELETE /rest/v1/admin/users/100001
> `curl -X DELETE http://localhost:8080/vote/rest/v1/admin/users/100001 --user Admin1:admin`

<h5> Regular User (функциональность голосования):

* GET /vote/rest/v1/profile/restaurants:
> `curl http://localhost:8080/vote/rest/v1/profile/restaurants --user Voter2:12345`
* GET /vote/rest/v1/profile/restaurants?date=2018-01-20
> `curl http://localhost:8080/vote/rest/v1/profile/restaurants?date=2018-01-20 --user Voter2:12345`
* GET /vote/rest/v1/profile/vote
> `curl http://localhost:8080/vote/rest/v1/profile/vote --user Voter2:12345`
* GET /vote/rest/v1/profile/vote?date=2018-01-20
> `curl http://localhost:8080/vote/rest/v1/profile/vote?date=2018-01-20 --user Voter2:12345`
* POST /vote/rest/v1/profile/vote
> `curl -g -X POST -d "{\"restaurantId\":\"100004\",\"restaurantName\":\"Restaurant1\"}" -H "Content-Type:application/json;charset=UTF-8" http://localhost:8080/vote/rest/v1/profile/vote --user  Voter1:12345`
* PUT /vote/rest/v1/profile/vote/100022
> `curl -g -X PUT -d "{\"restaurantId\":\"100004\",\"restaurantName\":\"Restaurant1\"}" -H "Content-Type:application/json;charset=UTF-8" http://localhost:8080/vote/rest/v1/profile/vote/100022 --user  Voter2:12345`
* DELETE /rest/v1/profile/vote
> `curl -X DELETE http://localhost:8080/vote/rest/v1/profile/vote --user Voter2:12345`
* GET /vote/rest/v1/profile/results:
> `curl http://localhost:8080/vote/rest/v1/profile/results --user Voter2:12345`
* GET /vote/rest/v1/profile/results?date=2018-01-20
> `curl http://localhost:8080/vote/rest/v1/profile/results?date=2018-01-20 --user Voter2:12345`

<h5> Regular User (Управление своей учетной записью):

* GET /rest/v1/profile
> `curl http://localhost:8080/vote/rest/v1/profile --user Voter1:12345`
* PUT /rest/v1/profile
> `curl -g -X PUT -d "{\"name\":\"Voter1 Updated\",\"nickname\":\"Voter1updated\",\"password\":\"12345\"}" -H "Content-Type:application/json;charset=UTF-8" http://localhost:8080/vote/rest/v1/profile --user  Voter1:12345`
* DELETE /rest/v1/profile
> `curl -X DELETE http://localhost:8080/vote/rest/v1/profile --user Voter1:12345`