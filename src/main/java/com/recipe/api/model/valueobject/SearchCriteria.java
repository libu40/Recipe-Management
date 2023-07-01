package com.recipe.api.model.valueobject;

import com.recipe.api.enums.RequestKeyType;
import com.recipe.api.enums.RequestOperationType;
import com.recipe.api.util.EnumValidator;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.Setter;

/** This class hold s the user search criteria. */
@Getter
@Setter
@Valid
public class SearchCriteria {
  @ApiModelProperty(
      notes =
          "Column name to search "
              + "name, "
              + "servingCount, "
              + "type, "
              + "instruction, "
              + "ingredient)",
      example = "name")
  @EnumValidator(enumClass = RequestKeyType.class)
  private String key;

  @ApiModelProperty(
      notes =
          "Operation to search (cn - contains, "
              + "nc - doesn't contain, "
              + "eq - equals, "
              + "ne - not equals",
      example = "cn")
  @EnumValidator(enumClass = RequestOperationType.class)
  private String operation;

  @ApiModelProperty(notes = "The actual phrase you want to do search on", example = "Flour")
  private Object value;

  @ApiModelProperty(hidden = true)
  private String dataOption;

  public SearchCriteria(String key, String operation, Object value) {
    this.key = key;
    this.operation = operation;
    this.value = value;
  }
}
