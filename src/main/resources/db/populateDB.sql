DELETE FROM user_roles;
DELETE FROM user_votes;
DELETE FROM dishes;
DELETE FROM restaurants;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (nickname, fullname , password) VALUES
  ('Voter1', 'Иванов Иван Иванович', '12345'),
  ('Voter2', 'Ivanov Ivan Ivanovitch', '12345'),
  ('Admin1', 'Петров Петр Петрович', 'admin'),
  ('Admin2', 'Petrov Petr Petrovitch', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_USER', 100001),
  ('ROLE_ADMIN', 100002),
  ('ROLE_USER', 100002),
  ('ROLE_ADMIN', 100003),
  ('ROLE_USER', 100003);

INSERT INTO restaurants (name, description) VALUES
  ('Restaurant1', 'Restaurant 1 description'),
  ('Restaurant2', 'Restaurant 2 description');

INSERT INTO dishes (restaurant_id, date_time, name, price) VALUES
  (100004 ,'2017-01-03 00:00:00', 'Бигмак', 10.25),
  (100004, '2017-01-03 00:00:00', 'Hamburger', 17),
  (100004, '2017-01-03 00:00:00', 'Кола лайт 200мл.', 30.99),
  (100004, '2017-01-03 00:00:00', 'Вафельный рожок', 22),
  (100005, '2017-01-03 00:00:00', 'Маргарита', 24),
  (100005, '2017-01-03 00:00:00', 'Пепперони', 15.1),
  (100005, '2017-01-03 00:00:00', 'Овощи гриль', 19),
  (100004, '2017-01-04 00:00:00', 'Биг тейсти', 14.02),
  (100004, '2017-01-04 00:00:00', 'Triple Cheeseburger', 64.00),
  (100004, '2017-01-04 00:00:00', 'Pico Guacamole with 100% Pure Beef 1/4 lb. Patty', 156.32),
  (100005, '2017-01-04 00:00:00', 'Sicilian Cheese Pizza', 12.99),
  (100005, '2017-01-04 00:00:00', 'Куриная', 4.05);

INSERT INTO user_votes (user_id, restaurant_id, date_time) VALUES
  (100000, 100004, '2017-01-03 08:00:00'),
  (100001, 100005, '2017-01-03 08:58:00'),
  (100002, 100004, '2017-01-03 10:00:00'),
  (100003, 100004, '2017-01-03 10:58:00'),
  (100001, 100005, '2017-01-04 09:15:40'),
  (100002, 100005, '2017-01-04 10:12:55'),
  (100003, 100004, '2017-01-04 10:45:00');
