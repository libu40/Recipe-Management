package com.recipe.api.mapper;

import com.recipe.api.model.dto.IngredientDto;
import com.recipe.api.model.entity.Ingredient;
import com.recipe.api.util.CycleAvoidingMappingContext;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

/**
 * This mapper class is responsible for mapping the ingredient dto and entities using mapping
 * framework automatically.
 */
@Component
@Mapper(componentModel = "spring")
public interface IngredientMapper {

  IngredientDto ingredientEntityToDto(
      Ingredient ingredient, @Context CycleAvoidingMappingContext context);

  Ingredient ingredientDtoToEntity(
      IngredientDto ingredientDto, @Context CycleAvoidingMappingContext context);
}
