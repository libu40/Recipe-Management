package com.recipe.api.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.recipe.api.enums.RecipeType;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** The DTO responsible for holding the recipe related attributes information. */
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class RecipeDto {

  @NotBlank private String name;

  private String instruction;

  @NotBlank private RecipeType type;

  private Integer servingCount;
}
