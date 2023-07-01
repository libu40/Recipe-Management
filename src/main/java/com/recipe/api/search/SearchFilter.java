package com.recipe.api.search;

import com.recipe.api.enums.RecipeSearch;
import com.recipe.api.model.entity.Recipe;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public interface SearchFilter {
  boolean couldBeApplied(RecipeSearch opt);

  Predicate apply(
      CriteriaBuilder cb,
      String filterKey,
      String filterValue,
      Root<Recipe> root,
      Join<Object, Object> subRoot);
}
