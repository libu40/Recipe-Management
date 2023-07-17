--liquibase formatted sql

--changeset Libu Mathew:4
insert into recipe (name, instruction, variant, serving_count, created_at, updated_at)
VALUES ('Peanut Butter Pretzel Cookies', 'Test', 'Vegetarian', 4, current_timestamp(), current_timestamp());

insert into recipe (name, instruction, variant, serving_count, created_at, updated_at)
VALUES ('Green Beans with Toasted Butter Pecans', 'Test', 'Vegetarian', 3, current_timestamp(), current_timestamp());

insert into recipe (name, instruction, variant, serving_count, created_at, updated_at)
VALUES ('Honey Walnut with Cheese Cranberry Salad', 'Test', 'Vegetarian', 5, current_timestamp(), current_timestamp());

insert into recipe (name, instruction, variant, serving_count, created_at, updated_at)
VALUES ('Leftover Pork Roast Hash', 'Test', 'NonVegetarian', 6, current_timestamp(), current_timestamp());

insert into recipe (name, instruction, variant, serving_count, created_at, updated_at)
VALUES ('Kumquat Cake', 'Test', 'NonVegetarian', 8, current_timestamp(), current_timestamp());

insert into recipe (name, instruction, variant, serving_count, created_at, updated_at)
VALUES ('Quick Spring Pizza', 'Test', 'Vegetarian', 6, current_timestamp(), current_timestamp());

insert into recipe (name, instruction, variant, serving_count, created_at, updated_at)
VALUES ('Beef Steak', 'Test', 'NonVegetarian', 5, current_timestamp(), current_timestamp());
