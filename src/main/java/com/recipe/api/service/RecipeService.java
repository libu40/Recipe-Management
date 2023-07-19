package com.recipe.api.service;

import com.recipe.api.exception.EntityNotFoundException;
import com.recipe.api.model.dto.IngredientDto;
import com.recipe.api.model.dto.RecipeDto;
import com.recipe.api.model.entity.Ingredient;
import com.recipe.api.model.entity.Recipe;
import com.recipe.api.model.valueobject.SearchCriteria;
import com.recipe.api.model.valueobject.SearchRequest;
import com.recipe.api.repository.RecipeRepository;
import com.recipe.api.search.SearchSpecificationBuilder;
import jakarta.ws.rs.NotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** This service class is used for doing the CRUD business logic for recipes. */
@Service
@Transactional
public class RecipeService {

  private static final Logger LOGGER = LogManager.getLogger(RecipeService.class.getName());

  private final RecipeRepository recipeRepository;

  @Autowired
  public RecipeService(RecipeRepository recipeRepository) {
    this.recipeRepository = recipeRepository;
  }

  public List<RecipeDto> getAllRecipes() {
    LOGGER.info("fetching all the recipes");
    List<Recipe> recipeList = recipeRepository.findAll();
    if (!recipeList.isEmpty()) {
      return recipeList.stream().map(this::convertToDto).toList();
    }
    return new ArrayList<>();
  }

  public RecipeDto getRecipeById(int id) {
    LOGGER.info("fetching all the recipes");
    Optional<Recipe> recipe = recipeRepository.findById(id);
    if (recipe.isPresent()) {
      return convertToDto(recipe.get());
    } else {
      throw new EntityNotFoundException(RecipeService.class, "id", String.valueOf(id));
    }
  }

  public List<RecipeDto> getSortedAndPaginatedRecipes(
      Integer pageNo, Integer pageSize, Direction sortBy, String field) {
    LOGGER.info(
        "fetching the recipes in a sorted paginated way for pageNo: {} pageSize: {} sortBy: {}",
        pageNo,
        pageSize,
        sortBy);
    Page<Recipe> recipeList =
        recipeRepository.findAll(PageRequest.of(pageNo, pageSize, sortBy, field));
    if (!recipeList.isEmpty()) {
      return recipeList.getContent().stream().map(this::convertToDto).toList();
    }
    return new ArrayList<>();
  }

  public void createRecipe(RecipeDto recipe) {
    LOGGER.info("Create recipe");
    recipeRepository.save(convertToEntity(recipe));
  }

  public RecipeDto updateRecipe(int id, RecipeDto recipeDto) {
    LOGGER.info(
        "Update the recipe for the id: {} with name: {} instruction: {} type: {} serving count: {} ",
        id,
        recipeDto.getName(),
        recipeDto.getInstruction(),
        recipeDto.getVariant(),
        recipeDto.getServingCount());
    Optional<Recipe> recipe = recipeRepository.findById(id);
    if (recipe.isPresent()) {
      recipe.get().setInstruction(recipeDto.getInstruction());
      recipe.get().setName(recipeDto.getName());
      recipe.get().setServingCount(recipeDto.getServingCount());
      recipe.get().setVariant(recipeDto.getVariant());

      Recipe updatedRecipe = recipeRepository.save(recipe.get());
      return convertToDto(updatedRecipe);
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
    List<SearchCriteria> searchCriterionRequests = new ArrayList<>();
    SearchSpecificationBuilder builder = new SearchSpecificationBuilder(searchCriterionRequests);
    Pageable pageRequest = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
    Specification<Recipe> recipeSpecification = createRecipeSpecification(searchRequest, builder);
    Page<Recipe> filteredRecipes = recipeRepository.findAll(recipeSpecification, pageRequest);

    return filteredRecipes.toList().stream().map(this::convertToDto).toList();
  }

  private Specification<Recipe> createRecipeSpecification(
      SearchRequest recipeSearchRequest, SearchSpecificationBuilder builder) {
    List<SearchCriteria> searchCriteriaRequests = recipeSearchRequest.getSearchCriteria();

    if (Optional.ofNullable(searchCriteriaRequests).isPresent()) {
      List<SearchCriteria> searchCriteriaList =
          searchCriteriaRequests.stream()
              .map(
                  searchCriteria ->
                      new SearchCriteria(
                          searchCriteria.getKey(),
                          searchCriteria.getOperation(),
                          searchCriteria.getValue(),
                          searchCriteria.getDataOption()))
              .toList();

      if (!searchCriteriaList.isEmpty())
        searchCriteriaList.forEach(
            criteria -> {
              criteria.setDataOption(recipeSearchRequest.getDataOption());
              builder.with(criteria);
            });
    }

    return builder.build().orElseThrow(() -> new NotFoundException("criteria not found"));
  }

  /**
   * The method responsible for converting the entity to dto.
   *
   * @param recipe entity
   * @return recipe dto
   */
  private RecipeDto convertToDto(Recipe recipe) {
    Set<IngredientDto> ingredientDtoSet = new HashSet<>();
    RecipeDto recipeDto = new RecipeDto();
    IngredientDto ingredientDto = new IngredientDto();
    recipeDto.setName(recipe.getName());
    recipeDto.setServingCount(recipe.getServingCount());
    recipeDto.setInstruction(recipe.getInstruction());
    recipeDto.setVariant(recipe.getVariant());
    for (Ingredient ingredient : recipe.getIngredients()) {
      ingredientDto.setName(ingredient.getName());
      ingredientDto.setCreatedAt(ingredient.getCreatedAt());
      ingredientDto.setUpdatedAt(ingredient.getUpdatedAt());
      ingredientDtoSet.add(ingredientDto);
    }
    recipeDto.setCreatedAt(recipe.getCreatedAt());
    recipeDto.setUpdatedAt(recipe.getUpdatedAt());
    recipeDto.setIngredients(ingredientDtoSet);
    return recipeDto;
  }

  /**
   * The method responsible for converting from dto to entity.
   *
   * @param recipeDto recipe dto
   * @return recipe entity
   */
  private Recipe convertToEntity(RecipeDto recipeDto) {
    Set<Ingredient> ingredients = new HashSet<>();
    Recipe recipe = new Recipe();
    Ingredient ingredient = new Ingredient();
    recipe.setName(recipeDto.getName());
    recipe.setVariant(recipe.getVariant());
    recipe.setServingCount(recipeDto.getServingCount());
    recipe.setInstruction(recipe.getInstruction());
    if (recipeDto.getIngredients() != null) {
      for (IngredientDto ingredientDto : recipeDto.getIngredients()) {
        ingredient.setName(ingredientDto.getName());
        ingredients.add(ingredient);
      }
    }
    recipe.setIngredients(ingredients);
    return recipe;
  }
}
