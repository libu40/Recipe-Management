--liquibase formatted sql

--changeset Libu Mathew:6
insert into recipe_ingredient (recipe_id, ingredient_id)
VALUES (1, 1);

insert into recipe_ingredient (recipe_id, ingredient_id)
VALUES (1, 2);

insert into recipe_ingredient (recipe_id, ingredient_id)
VALUES (2, 2);

insert into recipe_ingredient (recipe_id, ingredient_id)
VALUES (2, 3);

insert into recipe_ingredient (recipe_id, ingredient_id)
VALUES (3, 2);

insert into recipe_ingredient (recipe_id, ingredient_id)
VALUES (4, 2);

insert into recipe_ingredient (recipe_id, ingredient_id)
VALUES (3, 1);
