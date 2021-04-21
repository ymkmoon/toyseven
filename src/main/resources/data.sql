INSERT INTO admin_role (name, display_name) values ('ADMIN', '관리자');

INSERT INTO admin (username, name, password, role) values ('rb', 'rb', '1234', 'ADMIN');
INSERT INTO admin (username, name, password, role) values ('tori', 'tori', '1234', 'ADMIN');
INSERT INTO admin (username, name, password, role) values ('guke', 'guke', '$2a$10$oZPWMiSQF4lduNY/X8q9ZuuE14kJhcfIeER7SR/Ou0iE6W1VUY9WW', 'ADMIN');


INSERT INTO voc_category (name, display_name) values ('breakdown', '고장');
INSERT INTO voc_category (name, display_name) values ('theft', '도난');

INSERT INTO station_information (station_id, name, latitude, longitude, total_rack_count, total_parking_bike_count) VALUES ('station1', 'name 1', 1, 1, 1, 1);

INSERT INTO voc_question (category, title, content, username, email, station_id, need_reply) VALUES ('theft', 'title 1', 'content 1', 'username 1', 'email 1', 'station1', 1);

INSERT INTO voc_answer (question_id, content, admin_id) VALUES (1, 'content 1', 1);
INSERT INTO voc_answer (question_id, content, admin_id) VALUES (1, 'content 2', 1);
INSERT INTO voc_answer (question_id, content, admin_id) VALUES (1, 'content 3', 1);