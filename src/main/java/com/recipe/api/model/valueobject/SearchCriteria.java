package com.recipe.api.model.valueobject;

import com.recipe.api.enums.RequestKeyType;
import com.recipe.api.enums.RequestOperationType;
import com.recipe.api.enums.EnumValidator;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** This class hold s the user search criteria. */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
  private String value;

  @ApiModelProperty(hidden = true)
  private String dataOption;
}
