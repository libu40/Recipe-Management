--liquibase formatted sql

--changeset Libu Mathew:1
create table ingredient
(
    id        int primary key auto_increment,
    name      varchar(75) unique not null,
    created_at timestamp default current_timestamp,
    updated_at datetime  default current_timestamp on update current_timestamp
);

