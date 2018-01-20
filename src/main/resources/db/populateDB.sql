DELETE FROM user_roles;
DELETE FROM user_votes;
DELETE FROM dishes;
DELETE FROM restaurants;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (nickname, name , password) VALUES
  ('Voter1', 'Иванов Иван Иванович', '{noop}12345'),
  ('Voter2', 'Ivanov Ivan Ivanovitch', '{noop}12345'),
  ('Admin1', 'Петров Петр Петрович', '{noop}admin'),
  ('Admin2', 'Petrov Petr Petrovitch', '{noop}admin');

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

INSERT INTO dishes (restaurant_id, date, name, price) VALUES
  (100004 ,DATEADD('DAY', -1 , now() ), 'Бигмак', 10.25),
  (100004, DATEADD('DAY', -1 , now() ), 'Hamburger', 17),
  (100004, DATEADD('DAY', -1 , now() ), 'Кола лайт 200мл.', 30.99),
  (100004, DATEADD('DAY', -1 , now() ), 'Вафельный рожок', 22),
  (100005, DATEADD('DAY', -1 , now() ), 'Маргарита', 24),
  (100005, DATEADD('DAY', -1 , now() ), 'Пепперони', 15.1),
  (100005, DATEADD('DAY', -1 , now() ), 'Овощи гриль', 19),
  (100004, now(), 'Биг тейсти', 14.02),
  (100004, now(), 'Triple Cheeseburger', 64.00),
  (100004, now(), 'Pure beef', 156.32),
  (100005, now(), 'Sicilian Cheese Pizza', 12.99),
  (100005, now(), 'Куриная', 4.05);

INSERT INTO user_votes (user_id, restaurant_id, date, time) VALUES
  (100000, 100004, DATEADD('DAY', -1 , now() ), '08:00:00'),
  (100001, 100005, DATEADD('DAY', -1 , now() ), '08:58:00'),
  (100002, 100004, DATEADD('DAY', -1 , now() ), '10:00:00'),
  (100003, 100004, DATEADD('DAY', -1 , now() ), '10:58:00'),
  (100001, 100005, now(), '09:15:40'),
  (100002, 100005, now(), '10:12:55'),
  (100003, 100004, now(), '10:45:00');
