--liquibase formatted sql

--changeset Libu Mathew:5
create table recipe_ingredient
(
    recipe_id     int,
    ingredient_id int,
    constraint recipe_ingredient_pk primary key (recipe_id, ingredient_id),
    constraint fk_recipe foreign key (recipe_id) references recipe (id),
    constraint fk_ingredient foreign key (ingredient_id) references ingredient (id)
);
