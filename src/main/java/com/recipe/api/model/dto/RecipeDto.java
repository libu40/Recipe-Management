package com.recipe.api.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
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
  @NotBlank
  private String instruction;

  @ApiModelProperty(notes = "The type of the returned ingredient", example = "vegetarian")
  @NotBlank
  private String variant;

  @ApiModelProperty(notes = "The serving count of the returned ingredient", example = "4")
  @NotNull
  private Integer servingCount;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonSerialize(using = LocalDateTimeSerializer.class)
  private LocalDateTime createdAt;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonSerialize(using = LocalDateTimeSerializer.class)
  private LocalDateTime updatedAt;

  @ApiModelProperty(notes = "The ingredients used for this recipe")
  private Set<IngredientDto> ingredients;
}
