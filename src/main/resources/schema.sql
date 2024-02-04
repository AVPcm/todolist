create table users
(
    id       int auto_increment,
    login    varchar,
    password varchar,

    primary key (id)
);

create table tasks
(
    id          int auto_increment,
    title       varchar,
    description varchar,
    user_id     int,

    primary key (id),
    foreign key (user_id) references users (id)
);