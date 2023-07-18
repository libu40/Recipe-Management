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

/** The DTO responsible for holding the recipe related attributes information. */
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
@Getter
@Setter
public class RecipeDto {

  @ApiModelProperty(notes = "The name of the returned ingredient", example = "pasta")
  @NotBlank
  private String name;

  @ApiModelProperty(notes = "The instruction of the returned ingredient", example = "make a paste")
  private String instruction;

  @ApiModelProperty(notes = "The type of the returned ingredient", example = "vegetarian")
  @NotBlank
  private String variant;

  @ApiModelProperty(notes = "The serving count of the returned ingredient", example = "4")
  private Integer servingCount;

  @ApiModelProperty(notes = "The ingredients used for this recipe")
  @NotBlank
  private Set<IngredientDto> ingredients;
}
