package com.recipe.api.repository;

import com.recipe.api.model.entity.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * This repository is responsible for handling the ingredient JPA interactions with the database.
 */
@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Integer> {}
