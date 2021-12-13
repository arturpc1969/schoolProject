DROP TABLE IF EXISTS User CASCADE;

CREATE TABLE User (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(20) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL
);

DROP TABLE IF EXISTS Course CASCADE;

CREATE TABLE Course (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(10) NOT NULL UNIQUE,
    name VARCHAR(20) NOT NULL UNIQUE,
    description VARCHAR(500)
);

DROP TABLE IF EXISTS course_users_id CASCADE;

create table course_users_id(
	course_id bigint not null,
	user_id bigint not null,
	primary key(course_id, user_id),
	foreign key(course_id) references Course(id),
	foreign key(user_id) references User(id)
);

DROP TABLE IF EXISTS course_users CASCADE;

CREATE TABLE course_users(
	id BIGINT AUTO_INCREMENT,
	course_id BIGINT NOT NULL,
	user_id BIGINT NOT NULL,
	enroll_date DATE NOT NULL,
	PRIMARY KEY(course_id, user_id)
);