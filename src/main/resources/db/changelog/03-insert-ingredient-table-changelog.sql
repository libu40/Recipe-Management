--liquibase formatted sql

--changeset Libu Mathew:3

insert into ingredient (name, createdAt, updatedAt)
VALUES ('Flour', current_timestamp(), current_timestamp());

insert into ingredient (name, createdAt, updatedAt)
VALUES ('Baking powder', current_timestamp(), current_timestamp());

insert into ingredient (name, createdAt, updatedAt)
VALUES ('Sugar', current_timestamp(), current_timestamp());

insert into ingredient (name, createdAt, updatedAt)
VALUES ('Salt', current_timestamp(), current_timestamp());

insert into ingredient (name, createdAt, updatedAt)
VALUES ('Milk', current_timestamp(), current_timestamp());

insert into ingredient (name, createdAt, updatedAt)
VALUES ('Egg', current_timestamp(), current_timestamp());

insert into ingredient (name, createdAt, updatedAt)
VALUES ('Paprika', current_timestamp(), current_timestamp());

insert into ingredient (name, createdAt, updatedAt)
VALUES ('Potatoes', current_timestamp(), current_timestamp());
