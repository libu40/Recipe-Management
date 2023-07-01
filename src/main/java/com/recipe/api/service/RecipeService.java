package com.recipe.api.service;

import com.recipe.api.exception.EntityNotFoundException;
import com.recipe.api.mapper.RecipeMapper;
import com.recipe.api.model.dto.RecipeDto;
import com.recipe.api.model.entity.Recipe;
import com.recipe.api.model.valueobject.SearchRequest;
import com.recipe.api.repository.RecipeRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** This service class is used for doing the CRUD business logic for recipes. */
@Service
@Transactional
public class RecipeService {

  private static final Logger LOGGER = LogManager.getLogger(RecipeService.class.getName());

  private final RecipeRepository recipeRepository;

  private final RecipeMapper recipeMapper;

  @Autowired
  public RecipeService(RecipeRepository recipeRepository, RecipeMapper recipeMapper) {
    this.recipeRepository = recipeRepository;
    this.recipeMapper = recipeMapper;
  }

  public List<RecipeDto> getAllRecipes() {
    LOGGER.info("fetching all the recipes");
    List<Recipe> recipeList = recipeRepository.findAll();
    if (!recipeList.isEmpty()) {
      return recipeList.stream().map(recipeMapper::recipeEntityToDto).toList();
    }
    return new ArrayList<>();
  }

  public RecipeDto getRecipeById(int id) {
    LOGGER.info("fetching all the recipes");
    Optional<Recipe> recipe = recipeRepository.findById(id);
    if (recipe.isPresent()) {
      return recipeMapper.recipeEntityToDto(recipe.get());
    } else {
      throw new EntityNotFoundException(RecipeService.class, "id", String.valueOf(id));
    }
  }

  public List<RecipeDto> getSortedAndPaginatedRecipes(
      Integer pageNo, Integer pageSize, String sortBy) {
    LOGGER.info(
        "fetching the recipes in a sorted paginated way for pageNo: {} pageSize: {} sortBy: {}",
        pageNo,
        pageSize,
        sortBy);
    Page<Recipe> recipeList =
        recipeRepository.findAll(PageRequest.of(pageNo, pageSize, Sort.Direction.valueOf(sortBy)));
    if (!recipeList.isEmpty()) {
      return recipeList.getContent().stream().map(recipeMapper::recipeEntityToDto).toList();
    }
    return new ArrayList<>();
  }

  public void createRecipe(RecipeDto recipe) {
    LOGGER.info("Create recipe");
    recipeRepository.save(recipeMapper.recipeDtoToEntity(recipe));
  }

  public RecipeDto updateRecipe(int id, RecipeDto recipeDto) {
    LOGGER.info(
        "Update the recipe for the id: {} with name: {} instruction: {} type: {} serving count: {} ",
        id,
        recipeDto.getName(),
        recipeDto.getInstruction(),
        recipeDto.getType(),
        recipeDto.getServingCount());
    Optional<Recipe> recipe = recipeRepository.findById(id);
    if (recipe.isPresent()) {
      recipe.get().setInstruction(recipeDto.getInstruction());
      recipe.get().setName(recipeDto.getName());
      recipe.get().setServingCount(recipeDto.getServingCount());
      recipe.get().setType(recipeDto.getType());

      Recipe updatedRecipe = recipeRepository.save(recipe.get());
      return recipeMapper.recipeEntityToDto(updatedRecipe);
    } else {
      throw new EntityNotFoundException(RecipeService.class, "id", String.valueOf(id));
    }
  }

  public boolean deleteRecipe(int id) {
    LOGGER.info("Delete the recipe with the id: {},", id);
    recipeRepository.deleteById(id);
    if (recipeRepository.findById(id).isPresent()) {
      throw new IllegalStateException("Unable to delete teh recipe");
    } else {
      return true;
    }
  }

  public List<RecipeDto> searchRecipe(
      SearchRequest searchRequest, Integer pageNo, Integer pageSize, String sortBy) {
    return null;
  }
}
