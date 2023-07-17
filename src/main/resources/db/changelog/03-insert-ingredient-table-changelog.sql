--liquibase formatted sql

--changeset Libu Mathew:3
insert into ingredient (name, created_at, updated_at)
VALUES ('Flour', current_timestamp(), current_timestamp());

insert into ingredient (name, created_at, updated_at)
VALUES ('Baking powder', current_timestamp(), current_timestamp());

insert into ingredient (name, created_at, updated_at)
VALUES ('Sugar', current_timestamp(), current_timestamp());

insert into ingredient (name, created_at, updated_at)
VALUES ('Salt', current_timestamp(), current_timestamp());

insert into ingredient (name, created_at, updated_at)
VALUES ('Milk', current_timestamp(), current_timestamp());

insert into ingredient (name, created_at, updated_at)
VALUES ('Egg', current_timestamp(), current_timestamp());

insert into ingredient (name, created_at, updated_at)
VALUES ('Paprika', current_timestamp(), current_timestamp());

insert into ingredient (name, created_at, updated_at)
VALUES ('Potatoes', current_timestamp(), current_timestamp());
