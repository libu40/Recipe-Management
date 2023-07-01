--liquibase formatted sql

--changeset Libu Mathew:4

insert into recipe (name, instruction, variant, servingCount, createdAt, updatedAt)
VALUES ('Peanut Butter Pretzel Cookies', 'Test', 'Vegetarian', 4, current_timestamp(), current_timestamp());

insert into recipe (name, instruction, variant, servingCount, createdAt, updatedAt)
VALUES ('Green Beans with Toasted Butter Pecans', 'Test', 'Vegetarian', 3, current_timestamp(), current_timestamp());

insert into recipe (name, instruction, variant, servingCount, createdAt, updatedAt)
VALUES ('Honey Walnut with Cheese Cranberry Salad', 'Test', 'Vegetarian', 5, current_timestamp(), current_timestamp());

insert into recipe (name, instruction, variant, servingCount, createdAt, updatedAt)
VALUES ('Leftover Pork Roast Hash', 'Test', 'NonVegetarian', 6, current_timestamp(), current_timestamp());

insert into recipe (name, instruction, variant, servingCount, createdAt, updatedAt)
VALUES ('Kumquat Cake', 'Test', 'NonVegetarian', 8, current_timestamp(), current_timestamp());

insert into recipe (name, instruction, variant, servingCount, createdAt, updatedAt)
VALUES ('Quick Spring Pizza', 'Test', 'Vegetarian', 6, current_timestamp(), current_timestamp());

insert into recipe (name, instruction, variant, servingCount, createdAt, updatedAt)
VALUES ('Beef Steak', 'Test', 'NonVegetarian', 5, current_timestamp(), current_timestamp());
