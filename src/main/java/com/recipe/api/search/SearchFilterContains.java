package com.recipe.api.search;

import static com.recipe.api.util.Constants.INGREDIENT_KEY;

import com.recipe.api.enums.RecipeSearch;
import com.recipe.api.model.entity.Recipe;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class SearchFilterContains implements SearchFilter {

  @Override
  public boolean couldBeApplied(RecipeSearch opt) {
    return opt == RecipeSearch.CONTAINS;
  }

  @Override
  public Predicate apply(
      CriteriaBuilder cb,
      String filterKey,
      String filterValue,
      Root<Recipe> root,
      Join<Object, Object> subRoot) {
    if (filterKey.equals(INGREDIENT_KEY))
      return cb.like(cb.lower(subRoot.get(filterKey).as(String.class)), "%" + filterValue + "%");

    return cb.like(cb.lower(root.get(filterKey).as(String.class)), "%" + filterValue + "%");
  }
}
