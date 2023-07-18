package com.recipe.api.mapper;

import com.recipe.api.model.dto.RecipeDto;
import com.recipe.api.model.entity.Recipe;
import com.recipe.api.util.CycleAvoidingMappingContext;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

/**
 * This mapper class is responsible for mapping the recipe dto and entities using a mapping
 * framework automatically.
 */
@Component
@Mapper(componentModel = "spring")
public interface RecipeMapper {

  RecipeDto recipeEntityToDto(Recipe recipe, @Context CycleAvoidingMappingContext context);

  Recipe recipeDtoToEntity(RecipeDto recipeDto, @Context CycleAvoidingMappingContext context);
}
