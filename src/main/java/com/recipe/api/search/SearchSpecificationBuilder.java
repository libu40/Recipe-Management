package com.recipe.api.search;

import com.recipe.api.enums.DataOption;
import com.recipe.api.model.entity.Recipe;
import com.recipe.api.model.valueobject.SearchCriteria;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.domain.Specification;

public class SearchSpecificationBuilder {
  private final List<SearchCriteria> params;

  public SearchSpecificationBuilder(List<SearchCriteria> searchCriterionRequests) {
    this.params = searchCriterionRequests;
  }

  public final SearchSpecificationBuilder with(SearchCriteria searchCriteriaRequest) {
    params.add(searchCriteriaRequest);
    return this;
  }

  public Optional<Specification<Recipe>> build() {
    if (params.isEmpty()) return Optional.empty();

    Specification<Recipe> result = new SearchSpecification(params.get(0));

    for (int i = 1; i < params.size(); i++) {
      SearchCriteria criteria = params.get(i);
      Optional<DataOption> dataOption = DataOption.getDataOption(criteria.getDataOption());
      if (dataOption.isPresent()) {
        result = (dataOption.get() == DataOption.ALL)
            ? Specification.where(result).and(new SearchSpecification(criteria))
            : Specification.where(result).or(new SearchSpecification(criteria));
      }
    }
    return Optional.of(result);
  }
}
