INSERT INTO users VALUES(55, '******', 'robyy@qq.com');
INSERT INTO users VALUES(56, '******', 'bob@gmail.com');
INSERT INTO users VALUES(57, '******', 'galen@hotmail.com');
INSERT INTO users VALUES(58, '******', 'max@bing.com');
INSERT INTO users VALUES(59, '******', 'lucy@gmail.com');
INSERT INTO users VALUES(1, '******', 'ryan@gmail.com');
INSERT INTO users VALUES(2, '******', 'alex@gmail.com');


INSERT INTO events VALUES(1, 'music');
INSERT INTO events VALUES(3, 'movie');
INSERT INTO events VALUES(4, 'music3');
INSERT INTO events VALUES(7, 'concert');
INSERT INTO events VALUES(9, 'tech meetup');
INSERT INTO events VALUES(10, 'react meetup');
INSERT INTO events VALUES(19, 'spring meetup');
INSERT INTO events VALUES(29, 'java meetup');
INSERT INTO events VALUES(49, 'c meetup');

-- make sure no same event_id user_id combinattion added,
-- event_id+user_id should be unique,
-- in code controller level, made an assumption that
-- when the same user purchase the same event more than 1 time, 
-- only update the qty(increment) for the existing order.

--id, approve_status, qty, event_id, user_id 
INSERT INTO orders VALUES(1, true, 3, 3, 55);
INSERT INTO orders VALUES(2, true, 2, 1, 56);
INSERT INTO orders VALUES(3, true, 1, 10, 57);
INSERT INTO orders VALUES(4, true, 1, 19, 58);
INSERT INTO orders VALUES(5, true, 4, 29, 59);
INSERT INTO orders VALUES(6, true, 9, 49, 55);
INSERT INTO orders VALUES(7, true, 3, 7, 56);
INSERT INTO orders VALUES(8, true, 18, 4, 1);
INSERT INTO orders VALUES(9, true, 3, 7, 59);
INSERT INTO orders VALUES(10, true, 100, 29, 55); -- robyy@qq.com


