package com.recipe.api.search;

import static com.recipe.api.constants.Constants.JOINED_TABLE_NAME;

import com.recipe.api.enums.RecipeSearch;
import com.recipe.api.model.entity.Recipe;
import com.recipe.api.model.valueobject.SearchCriteria;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.NonNull;
import org.springframework.data.jpa.domain.Specification;

public class SearchSpecification implements Specification<Recipe> {
  private final transient SearchCriteria criteria;

  // I made it static because this filters isn't object-specific and no need to populate it with
  // every new specification
  private static final List<SearchFilter> searchFilters = new ArrayList<>();

  public SearchSpecification(SearchCriteria criteria) {
    super();
    filterList();
    this.criteria = criteria;
  }

  @Override
  public Predicate toPredicate(
      Root<Recipe> root, CriteriaQuery<?> query, @NonNull CriteriaBuilder cb) {
    Optional<RecipeSearch> operation = RecipeSearch.getOperation(criteria.getOperation());
    String filterValue = criteria.getValue().toLowerCase();
    String filterKey = criteria.getKey();

    Join<Object, Object> subRoot = root.join(JOINED_TABLE_NAME, JoinType.INNER);
    query.distinct(true);

    return operation
        .flatMap(
            searchOperation ->
                searchFilters.stream()
                    .filter(searchFilter -> searchFilter.couldBeApplied(searchOperation))
                    .findFirst()
                    .map(
                        searchFilter ->
                            searchFilter.apply(cb, filterKey, filterValue, root, subRoot)))
        .orElse(null);
  }

  private void filterList() {
    searchFilters.add(new SearchFilterEqual());
    searchFilters.add(new SearchFilterNotEqual());
    searchFilters.add(new SearchFilterContains());
    searchFilters.add(new SearchFilterDoesNotContain());
  }
}
