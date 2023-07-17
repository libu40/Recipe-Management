--liquibase formatted sql

--changeset Libu Mathew:2
create table recipe
(
    id            int primary key auto_increment,
    name          varchar(75) unique not null,
    instruction   varchar(75),
    variant       varchar(75)        not null DEFAULT 'VEGETARIAN',
    serving_count int,
    created_at    timestamp                   default current_timestamp,
    updated_at    datetime                    default current_timestamp on update current_timestamp
);


