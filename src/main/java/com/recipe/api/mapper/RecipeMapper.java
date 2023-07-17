package com.recipe.api.mapper;

import com.recipe.api.model.dto.RecipeDto;
import com.recipe.api.model.entity.Recipe;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

/**
 * This mapper class is responsible for mapping the recipe dto and entities using a mapping framework
 * automatically.
 */
@Component
@Mapper(componentModel = "spring")
public interface RecipeMapper {

    RecipeDto recipeEntityToDto(Recipe recipe);

    Recipe recipeDtoToEntity(RecipeDto recipeDto);
}
