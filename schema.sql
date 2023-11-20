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

create table activity_categories
(
    id serial primary key,
    title varchar(50) unique not null,
    description text
);

create table events_activities
(
    id serial primary key,
    user_id int,
    date_time timestamp,
    title varchar(255) not null,
    notes text,
    category int not null,
    mood_rating int not null,
    foreign key (user_id) references users(id),
    foreign key (category) references activity_categories(id),
    foreign key (mood_rating) references mood_categories(id)
);

create table recommendations
(
    id serial primary key,
    text text,
    source varchar(100)
);

create table mood_history
(
    id serial primary key,
    mood_id int not null,
    date_time timestamp,
    foreign key (mood_id) references mood_categories(id)
);