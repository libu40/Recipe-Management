package com.recipe.api.model.valueobject;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.recipe.api.enums.RequestDataOptionType;
import com.recipe.api.util.EnumValidator;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.Valid;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Search request for fetching the filtered records. */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchRequest {

  @JsonProperty("criteria")
  @ApiModelProperty(notes = "Search criteria you want to search recipe with")
  @Valid
  private List<SearchCriteria> searchCriteria;

  @ApiModelProperty(
      notes = "If you want all or just one criteria is enough for filter to work",
      example = "all")
  @EnumValidator(enumClass = RequestDataOptionType.class)
  private String dataOption;
}
