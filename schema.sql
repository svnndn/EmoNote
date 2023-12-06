create table users
(
    id serial primary key,
    nickname varchar(255) unique not null,
    name varchar(255),
    surname varchar(255),
    email varchar(255) unique not null,
    password varchar(255) not null,
    date_of_birth date,
    gender varchar(20),
    description text
);

create table mood_categories
(
    id serial primary key,
    title varchar(50)
);

create table mood_journal
(
    id serial primary key,
    user_id int,
    date_time timestamp,
    title varchar(255) not null,
    notes text,
    mood_rating int not null,
    foreign key (user_id) references users(id),
    foreign key (mood_rating) references mood_categories(id)
);

create table comment
(
    id serial primary key,
    user_id int,
    mood_journal_id int,
    date_time timestamp,
    notes text not null,
    foreign key (user_id) references users(id),
    foreign key (mood_journal_id) references mood_journal(id)
);

insert into mood_categories (id, title) values (1, '1.png');
insert into mood_categories (id, title) values (2, '2.png');
insert into mood_categories (id, title) values (3, '3.png');
insert into mood_categories (id, title) values (4, '4.png');
insert into mood_categories (id, title) values (5, '5.png');
insert into mood_categories (id, title) values (6, '6.png');
insert into mood_categories (id, title) values (7, '7.png');
insert into mood_categories (id, title) values (8, '8.png');
insert into mood_categories (id, title) values (9, '9.png');
insert into mood_categories (id, title) values (10, '10.png');
insert into mood_categories (id, title) values (11, '11.png');
insert into mood_categories (id, title) values (12, '12.png');
insert into mood_categories (id, title) values (13, '13.png');
insert into mood_categories (id, title) values (14, '14.png');
insert into mood_categories (id, title) values (15, '15.png');
insert into mood_categories (id, title) values (16, '16.png');
insert into mood_categories (id, title) values (17, '17.png');
insert into mood_categories (id, title) values (18, '18.png');
insert into mood_categories (id, title) values (19, '19.png');
insert into mood_categories (id, title) values (20, '20.png');
insert into mood_categories (id, title) values (21, '21.png');
insert into mood_categories (id, title) values (22, '22.png');
insert into mood_categories (id, title) values (23, '23.png');
insert into mood_categories (id, title) values (24, '24.png');
insert into mood_categories (id, title) values (25, '25.png');