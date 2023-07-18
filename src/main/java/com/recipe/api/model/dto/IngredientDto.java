package com.recipe.api.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotBlank;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** The DTO responsible for holding the ingredient related attributes information. */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class IngredientDto {

  @ApiModelProperty(notes = "The name of the returned ingredient", example = "wheat")
  @NotBlank
  private String name;

  @ApiModelProperty(notes = "The recipes used in the ingredient")
  @NotBlank
  private Set<RecipeDto> recipes;
}
