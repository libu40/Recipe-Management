package com.recipe.api.service;

import com.recipe.api.exception.EntityNotFoundException;
import com.recipe.api.model.dto.IngredientDto;
import com.recipe.api.model.dto.RecipeDto;
import com.recipe.api.model.entity.Ingredient;
import com.recipe.api.model.entity.Recipe;
import com.recipe.api.repository.IngredientRepository;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** This service class is used for doing the CRUD business logic for ingredients. */
@Service
@Transactional
public class IngredientService {

  private static final Logger LOGGER = LogManager.getLogger(IngredientService.class.getName());

  private final IngredientRepository ingredientRepository;

  @Autowired
  public IngredientService(IngredientRepository ingredientRepository) {
    this.ingredientRepository = ingredientRepository;
  }

  public IngredientDto getIngredientById(int id) {
    LOGGER.info("fetch ingredients by id: {}", id);
    Optional<Ingredient> ingredient = ingredientRepository.findById(id);
    if (ingredient.isPresent()) {
      return convertToDto(ingredient.get());
    }
    throw new EntityNotFoundException(Ingredient.class, "id", String.valueOf(id));
  }

  public List<IngredientDto> getAllIngredients() {
    LOGGER.info("fetch all the ingredients");
    List<Ingredient> ingredients = ingredientRepository.findAll();
    if (!ingredients.isEmpty()) {
      return ingredients.stream().map(this::convertToDto).toList();
    }
    throw new EntityNotFoundException(Ingredient.class);
  }

  public List<IngredientDto> getSortedAndPaginatedIngredients(
      Integer pageNo, Integer pageSize, Direction sortBy, String attribute) {
    LOGGER.info("fetch all the ingredients in a sorted paginated way");
    Page<Ingredient> paginatedIngredients =
        ingredientRepository.findAll(PageRequest.of(pageNo, pageSize, sortBy, attribute));
    List<Ingredient> ingredients = paginatedIngredients.getContent();
    return ingredients.stream().map(this::convertToDto).toList();
  }

  public void createIngredient(IngredientDto ingredient) {
    LOGGER.info("create the ingredients");
    Ingredient saveIngredient = convertToEntity(ingredient);
    ingredientRepository.save(saveIngredient);
  }

  /**
   * The method responsible for converting the entity to dto.
   *
   * @param ingredient entity
   * @return ingredient dto
   */
  private IngredientDto convertToDto(Ingredient ingredient) {
    Set<RecipeDto> recipes = new HashSet<>();
    IngredientDto ingredientDto = new IngredientDto();
    RecipeDto recipeDto = new RecipeDto();
    ingredientDto.setName(ingredient.getName());
    for (Recipe recipeEntity : ingredient.getRecipes()) {
      recipeDto.setName(recipeEntity.getName());
      recipeDto.setVariant(recipeEntity.getVariant());
      recipeDto.setInstruction(recipeEntity.getInstruction());
      recipeDto.setServingCount(recipeEntity.getServingCount());
      recipes.add(recipeDto);
    }
    ingredientDto.setRecipes(recipes);
    return ingredientDto;
  }

  /**
   * The method responsible for converting dto to entity.
   *
   * @param ingredientDto ingredient dto
   * @return ingredient entity
   */
  private Ingredient convertToEntity(IngredientDto ingredientDto) {
    Set<Recipe> recipes = new HashSet<>();
    Ingredient ingredient = new Ingredient();
    Recipe recipeEntity = new Recipe();
    ingredient.setName(ingredientDto.getName());
    for (RecipeDto recipeDto : ingredientDto.getRecipes()) {
      recipeEntity.setName(recipeDto.getName());
      recipeEntity.setInstruction(recipeDto.getInstruction());
      recipeEntity.setServingCount(recipeDto.getServingCount());
      recipeEntity.setVariant(recipeDto.getVariant());
      recipes.add(recipeEntity);
    }
    ingredient.setRecipes(recipes);
    return ingredient;
  }
}
