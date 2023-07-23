package com.recipe.api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.recipe.api.exception.EntityNotFoundException;
import com.recipe.api.model.dto.IngredientDto;
import com.recipe.api.model.dto.RecipeDto;
import com.recipe.api.model.entity.Ingredient;
import com.recipe.api.model.entity.Recipe;
import com.recipe.api.model.valueobject.SearchCriteria;
import com.recipe.api.model.valueobject.SearchRequest;
import com.recipe.api.repository.RecipeRepository;
import jakarta.ws.rs.NotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/** Test class for {@link RecipeService}. */
@ContextConfiguration(classes = {RecipeService.class})
@ExtendWith(SpringExtension.class)
class RecipeServiceTest {
  @MockBean private RecipeRepository recipeRepository;

  @Autowired private RecipeService recipeService;
  /** Method under test: {@link RecipeService#getAllRecipes()} */
  @Test
  void testGetAllMockRecipes() {
    when(recipeRepository.findAll()).thenReturn(new ArrayList<>());
    assertTrue(recipeService.getAllRecipes().isEmpty());
    verify(recipeRepository).findAll();
  }

  /** Method under test: {@link RecipeService#getAllRecipes()} */
  @Test
  void testGetAllMockRecipesInteractions() {
    ArrayList<Recipe> recipeList = new ArrayList<>();
    recipeList.add(new Recipe());
    when(recipeRepository.findAll()).thenReturn(recipeList);
    assertEquals(1, recipeService.getAllRecipes().size());
    verify(recipeRepository).findAll();
  }

  /** Method under test: {@link RecipeService#getAllRecipes()} */
  @Test
  void testGetAllRecipesWithIngredients() {
    ArrayList<Recipe> recipeList = new ArrayList<>();
    recipeList.add(new Recipe("Pasta", "Instruction", "VEGETARIAN", 7, new HashSet<>()));
    recipeList.add(new Recipe("PuffCake", "Instruction", "VEGETARIAN", 7, new HashSet<>()));
    when(recipeRepository.findAll()).thenReturn(recipeList);
    assertEquals(2, recipeService.getAllRecipes().size());
    verify(recipeRepository).findAll();
  }

  /** Method under test: {@link RecipeService#getAllRecipes()} */
  @Test
  void testGetAllRecipesWithInvalidRecipe() {
    when(recipeRepository.findAll()).thenThrow(new EntityNotFoundException(Object.class));
    assertThrows(EntityNotFoundException.class, () -> recipeService.getAllRecipes());
    verify(recipeRepository).findAll();
  }

  /** Method under test: {@link RecipeService#getAllRecipes()} */
  @Test
  void testGetAllRecipesWithMockRecipe() {
    Recipe recipe = mock(Recipe.class);
    when(recipe.getServingCount()).thenReturn(3);
    when(recipe.getInstruction()).thenReturn("Instruction");
    when(recipe.getName()).thenReturn("Name");
    when(recipe.getVariant()).thenReturn("Variant");
    when(recipe.getCreatedAt()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());
    when(recipe.getUpdatedAt()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());
    when(recipe.getIngredients()).thenReturn(new HashSet<>());

    ArrayList<Recipe> recipeList = new ArrayList<>();
    recipeList.add(recipe);
    when(recipeRepository.findAll()).thenReturn(recipeList);
    assertEquals(1, recipeService.getAllRecipes().size());
    verify(recipeRepository).findAll();
    verify(recipe).getServingCount();
    verify(recipe).getInstruction();
    verify(recipe).getName();
    verify(recipe).getVariant();
    verify(recipe).getCreatedAt();
    verify(recipe).getUpdatedAt();
    verify(recipe, atLeast(1)).getIngredients();
  }

  /** Method under test: {@link RecipeService#getAllRecipes()} */
  @Test
  void testGetAllRecipesWithValidIngredient() {
    Ingredient ingredient = new Ingredient();
    ingredient.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
    ingredient.setId(1);
    ingredient.setName("fetching all the recipes");
    ingredient.setRecipes(new HashSet<>());
    ingredient.setUpdatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());

    HashSet<Ingredient> ingredientSet = new HashSet<>();
    ingredientSet.add(ingredient);
    Recipe recipe = mock(Recipe.class);
    when(recipe.getServingCount()).thenReturn(3);
    when(recipe.getInstruction()).thenReturn("Instruction");
    when(recipe.getName()).thenReturn("Name");
    when(recipe.getVariant()).thenReturn("Variant");
    when(recipe.getCreatedAt()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());
    when(recipe.getUpdatedAt()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());
    when(recipe.getIngredients()).thenReturn(ingredientSet);

    ArrayList<Recipe> recipeList = new ArrayList<>();
    recipeList.add(recipe);
    when(recipeRepository.findAll()).thenReturn(recipeList);
    assertEquals(1, recipeService.getAllRecipes().size());
    verify(recipeRepository).findAll();
    verify(recipe).getServingCount();
    verify(recipe).getInstruction();
    verify(recipe).getName();
    verify(recipe).getVariant();
    verify(recipe).getCreatedAt();
    verify(recipe).getUpdatedAt();
    verify(recipe, atLeast(1)).getIngredients();
  }

  /** Method under test: {@link RecipeService#getRecipeById(int)} */
  @Test
  void testGetRecipeById() {
    when(recipeRepository.findById(Mockito.<Integer>any())).thenReturn(Optional.of(new Recipe()));
    RecipeDto actualRecipeById = recipeService.getRecipeById(1);
    assertNull(actualRecipeById.getCreatedAt());
    assertEquals("VEGETARIAN", actualRecipeById.getVariant());
    assertNull(actualRecipeById.getUpdatedAt());
    assertNull(actualRecipeById.getServingCount());
    assertNull(actualRecipeById.getName());
    assertNull(actualRecipeById.getInstruction());
    assertTrue(actualRecipeById.getIngredients().isEmpty());
    verify(recipeRepository).findById(Mockito.<Integer>any());
  }

  /** Method under test: {@link RecipeService#getRecipeById(int)} */
  @Test
  void testGetRecipeByIdInteractions() {
    Recipe recipe = mock(Recipe.class);
    when(recipe.getServingCount()).thenReturn(3);
    when(recipe.getInstruction()).thenReturn("Instruction");
    when(recipe.getName()).thenReturn("Name");
    when(recipe.getVariant()).thenReturn("Variant");
    when(recipe.getCreatedAt()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());
    when(recipe.getUpdatedAt()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());
    when(recipe.getIngredients()).thenReturn(new HashSet<>());
    Optional<Recipe> ofResult = Optional.of(recipe);
    when(recipeRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);
    RecipeDto actualRecipeById = recipeService.getRecipeById(1);
    assertEquals("Variant", actualRecipeById.getVariant());
    assertEquals("00:00", actualRecipeById.getCreatedAt().toLocalTime().toString());
    assertEquals(3, actualRecipeById.getServingCount().intValue());
    assertEquals("00:00", actualRecipeById.getUpdatedAt().toLocalTime().toString());
    assertEquals("Name", actualRecipeById.getName());
    assertEquals("Instruction", actualRecipeById.getInstruction());
    assertTrue(actualRecipeById.getIngredients().isEmpty());
    verify(recipeRepository).findById(Mockito.<Integer>any());
    verify(recipe).getServingCount();
    verify(recipe).getInstruction();
    verify(recipe).getName();
    verify(recipe).getVariant();
    verify(recipe).getCreatedAt();
    verify(recipe).getUpdatedAt();
    verify(recipe, atLeast(1)).getIngredients();
  }

  /** Method under test: {@link RecipeService#getRecipeById(int)} */
  @Test
  void testGetRecipeByIdWithValidIngredient() {
    Ingredient ingredient = new Ingredient();
    ingredient.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
    ingredient.setId(1);
    ingredient.setName("fetching all the recipes");
    ingredient.setRecipes(new HashSet<>());
    ingredient.setUpdatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());

    HashSet<Ingredient> ingredientSet = new HashSet<>();
    ingredientSet.add(ingredient);
    Recipe recipe = mock(Recipe.class);
    when(recipe.getServingCount()).thenReturn(3);
    when(recipe.getInstruction()).thenReturn("Instruction");
    when(recipe.getName()).thenReturn("Name");
    when(recipe.getVariant()).thenReturn("Variant");
    when(recipe.getCreatedAt()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());
    when(recipe.getUpdatedAt()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());
    when(recipe.getIngredients()).thenReturn(ingredientSet);
    Optional<Recipe> ofResult = Optional.of(recipe);
    when(recipeRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);
    RecipeDto actualRecipeById = recipeService.getRecipeById(1);
    assertEquals("Variant", actualRecipeById.getVariant());
    assertEquals("00:00", actualRecipeById.getCreatedAt().toLocalTime().toString());
    assertEquals(3, actualRecipeById.getServingCount().intValue());
    assertEquals("00:00", actualRecipeById.getUpdatedAt().toLocalTime().toString());
    assertEquals("Name", actualRecipeById.getName());
    assertEquals("Instruction", actualRecipeById.getInstruction());
    assertEquals(1, actualRecipeById.getIngredients().size());
    verify(recipeRepository).findById(Mockito.<Integer>any());
    verify(recipe).getServingCount();
    verify(recipe).getInstruction();
    verify(recipe).getName();
    verify(recipe).getVariant();
    verify(recipe).getCreatedAt();
    verify(recipe).getUpdatedAt();
    verify(recipe, atLeast(1)).getIngredients();
  }

  /** Method under test: {@link RecipeService#getRecipeById(int)} */
  @Test
  void testGetRecipeByIdWithInvalidId() {
    when(recipeRepository.findById(Mockito.<Integer>any())).thenReturn(Optional.empty());
    assertThrows(EntityNotFoundException.class, () -> recipeService.getRecipeById(1));
    verify(recipeRepository).findById(Mockito.<Integer>any());
  }

  /** Method under test: {@link RecipeService#getRecipeById(int)} */
  @Test
  void testGetRecipeWithMockRecipe() {
    Recipe recipe = mock(Recipe.class);
    when(recipe.getName()).thenThrow(new NotFoundException());
    Optional<Recipe> ofResult = Optional.of(recipe);
    when(recipeRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);
    assertThrows(NotFoundException.class, () -> recipeService.getRecipeById(1));
    verify(recipeRepository).findById(Mockito.<Integer>any());
    verify(recipe).getName();
  }

  /**
   * Method under test: {@link RecipeService#getSortedAndPaginatedRecipes(Integer, Integer,
   * Sort.Direction, String)}
   */
  @Test
  void testGetSortedAndPaginatedRecipes() {
    when(recipeRepository.findAll(Mockito.<Pageable>any()))
        .thenReturn(new PageImpl<>(new ArrayList<>()));
    assertTrue(
        recipeService.getSortedAndPaginatedRecipes(1, 3, Sort.Direction.ASC, "Field").isEmpty());
    verify(recipeRepository).findAll(Mockito.<Pageable>any());
  }

  /**
   * Method under test: {@link RecipeService#getSortedAndPaginatedRecipes(Integer, Integer,
   * Sort.Direction, String)}
   */
  @Test
  void testGetSortedAndPaginatedMockRecipes() {
    ArrayList<Recipe> content = new ArrayList<>();
    content.add(new Recipe());
    PageImpl<Recipe> pageImpl = new PageImpl<>(content);
    when(recipeRepository.findAll(Mockito.<Pageable>any())).thenReturn(pageImpl);
    assertEquals(
        1, recipeService.getSortedAndPaginatedRecipes(1, 3, Sort.Direction.ASC, "Field").size());
    verify(recipeRepository).findAll(Mockito.<Pageable>any());
  }

  /**
   * Method under test: {@link RecipeService#getSortedAndPaginatedRecipes(Integer, Integer,
   * Sort.Direction, String)}
   */
  @Test
  void testGetSortedAndPaginatedValidRecipes() {
    ArrayList<Recipe> content = new ArrayList<>();
    content.add(new Recipe("Pasta", "Instruction", "VEGETARIAN", 7, new HashSet<>()));
    content.add(new Recipe("PuffCake", "Instruction", "VEGETARIAN", 7, new HashSet<>()));
    PageImpl<Recipe> pageImpl = new PageImpl<>(content);
    when(recipeRepository.findAll(Mockito.<Pageable>any())).thenReturn(pageImpl);
    assertEquals(
        2, recipeService.getSortedAndPaginatedRecipes(1, 3, Sort.Direction.ASC, "Field").size());
    verify(recipeRepository).findAll(Mockito.<Pageable>any());
  }

  /**
   * Method under test: {@link RecipeService#getSortedAndPaginatedRecipes(Integer, Integer,
   * Sort.Direction, String)}
   */
  @Test
  void testGetSortedAndPaginatedRecipesIsNotNull() {
    when(recipeRepository.findAll(Mockito.<Pageable>any()))
        .thenReturn(new PageImpl<>(new ArrayList<>()));
    assertNotNull(recipeService.getSortedAndPaginatedRecipes(1, 3, Sort.Direction.ASC, "Field"));
  }

  /**
   * Method under test: {@link RecipeService#getSortedAndPaginatedRecipes(Integer, Integer,
   * Sort.Direction, String)}
   */
  @Test
  void testGetSortedAndPaginatedRecipesInteractions() {
    when(recipeRepository.findAll(Mockito.<Pageable>any()))
        .thenReturn(new PageImpl<>(new ArrayList<>()));
    assertTrue(
        recipeService.getSortedAndPaginatedRecipes(1, 3, Sort.Direction.DESC, "Field").isEmpty());
    verify(recipeRepository).findAll(Mockito.<Pageable>any());
  }

  /**
   * Method under test: {@link RecipeService#getSortedAndPaginatedRecipes(Integer, Integer,
   * Sort.Direction, String)}
   */
  @Test
  void testGetSortedAndPaginatedNullRecipes() {
    when(recipeRepository.findAll(Mockito.<Pageable>any()))
        .thenThrow(new EntityNotFoundException(Object.class));
    assertThrows(
        EntityNotFoundException.class,
        () -> recipeService.getSortedAndPaginatedRecipes(1, 3, Sort.Direction.ASC, "Field"));
    verify(recipeRepository).findAll(Mockito.<Pageable>any());
  }

  /**
   * Method under test: {@link RecipeService#getSortedAndPaginatedRecipes(Integer, Integer,
   * Sort.Direction, String)}
   */
  @Test
  void testGetSortedAndPaginatedRecipesInteraction() {
    Recipe recipe = mock(Recipe.class);
    when(recipe.getServingCount()).thenReturn(3);
    when(recipe.getInstruction()).thenReturn("Instruction");
    when(recipe.getName()).thenReturn("Name");
    when(recipe.getVariant()).thenReturn("Variant");
    when(recipe.getCreatedAt()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());
    when(recipe.getUpdatedAt()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());
    when(recipe.getIngredients()).thenReturn(new HashSet<>());

    ArrayList<Recipe> content = new ArrayList<>();
    content.add(recipe);
    PageImpl<Recipe> pageImpl = new PageImpl<>(content);
    when(recipeRepository.findAll(Mockito.<Pageable>any())).thenReturn(pageImpl);
    assertEquals(
        1, recipeService.getSortedAndPaginatedRecipes(1, 3, Sort.Direction.ASC, "Field").size());
    verify(recipeRepository).findAll(Mockito.<Pageable>any());
    verify(recipe).getServingCount();
    verify(recipe).getInstruction();
    verify(recipe).getName();
    verify(recipe).getVariant();
    verify(recipe).getCreatedAt();
    verify(recipe).getUpdatedAt();
    verify(recipe, atLeast(1)).getIngredients();
  }

  /**
   * Method under test: {@link RecipeService#getSortedAndPaginatedRecipes(Integer, Integer,
   * Sort.Direction, String)}
   */
  @Test
  void testGetSortedAndPaginatedRecipesWithValidIngredient() {
    Ingredient ingredient = new Ingredient();
    ingredient.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
    ingredient.setId(1);
    ingredient.setName(
        "fetching the recipes in a sorted paginated way for pageNo: {} pageSize: {} sortBy: {}");
    ingredient.setRecipes(new HashSet<>());
    ingredient.setUpdatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());

    HashSet<Ingredient> ingredientSet = new HashSet<>();
    ingredientSet.add(ingredient);
    Recipe recipe = mock(Recipe.class);
    when(recipe.getServingCount()).thenReturn(3);
    when(recipe.getInstruction()).thenReturn("Instruction");
    when(recipe.getName()).thenReturn("Name");
    when(recipe.getVariant()).thenReturn("Variant");
    when(recipe.getCreatedAt()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());
    when(recipe.getUpdatedAt()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());
    when(recipe.getIngredients()).thenReturn(ingredientSet);

    ArrayList<Recipe> content = new ArrayList<>();
    content.add(recipe);
    PageImpl<Recipe> pageImpl = new PageImpl<>(content);
    when(recipeRepository.findAll(Mockito.<Pageable>any())).thenReturn(pageImpl);
    assertEquals(
        1, recipeService.getSortedAndPaginatedRecipes(1, 3, Sort.Direction.ASC, "Field").size());
    verify(recipeRepository).findAll(Mockito.<Pageable>any());
    verify(recipe).getServingCount();
    verify(recipe).getInstruction();
    verify(recipe).getName();
    verify(recipe).getVariant();
    verify(recipe).getCreatedAt();
    verify(recipe).getUpdatedAt();
    verify(recipe, atLeast(1)).getIngredients();
  }

  /**
   * Method under test: {@link RecipeService#getSortedAndPaginatedRecipes(Integer, Integer,
   * Sort.Direction, String)}
   */
  @Test
  void testGetSortedAndPaginatedRecipesPagination() {
    Recipe recipe = mock(Recipe.class);
    when(recipe.getServingCount()).thenReturn(3);
    when(recipe.getInstruction()).thenReturn("Instruction");
    when(recipe.getName()).thenReturn("Name");
    when(recipe.getVariant()).thenReturn("Variant");
    when(recipe.getCreatedAt()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());
    when(recipe.getUpdatedAt()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());
    when(recipe.getIngredients()).thenReturn(new HashSet<>());

    ArrayList<Recipe> content = new ArrayList<>();
    content.add(recipe);
    PageImpl<Recipe> pageImpl = new PageImpl<>(content);
    when(recipeRepository.findAll(Mockito.<Pageable>any())).thenReturn(pageImpl);
    assertNotNull(recipeService.getSortedAndPaginatedRecipes(1, 3, Sort.Direction.ASC, "Field"));
  }

  /** Method under test: {@link RecipeService#createRecipe(RecipeDto)} */
  @Test
  void testCreateMockRecipe() {
    when(recipeRepository.save(Mockito.any())).thenReturn(new Recipe());
    recipeService.createRecipe(new RecipeDto());
    verify(recipeRepository).save(Mockito.any());
  }

  /** Method under test: {@link RecipeService#createRecipe(RecipeDto)} */
  @Test
  void testCreateValidRecipe() {
    when(recipeRepository.save(Mockito.any())).thenReturn(new Recipe());
    recipeService.createRecipe(
        new RecipeDto(
            "Pasta",
            "Instruction",
            "VEGETARIAN",
            7,
            LocalDateTime.now(),
            LocalDateTime.now(),
            new HashSet<>()));
    verify(recipeRepository).save(Mockito.any());
  }

  /** Method under test: {@link RecipeService#createRecipe(RecipeDto)} */
  @Test
  void testCreateInvalidRecipe() {
    when(recipeRepository.save(Mockito.any())).thenThrow(new EntityNotFoundException(Object.class));
    assertThrows(Exception.class, () -> recipeService.createRecipe(new RecipeDto()));
    verify(recipeRepository).save(Mockito.any());
  }

  /** Method under test: {@link RecipeService#createRecipe(RecipeDto)} */
  @Test
  void testCreateRecipeWithEmptyIngredient() {
    when(recipeRepository.save(Mockito.any())).thenReturn(new Recipe());

    HashSet<IngredientDto> ingredients = new HashSet<>();
    ingredients.add(new IngredientDto());
    LocalDateTime createdAt = LocalDate.of(1970, 1, 1).atStartOfDay();
    recipeService.createRecipe(
        new RecipeDto(
            "Create recipe",
            "Create recipe",
            "Create recipe",
            3,
            createdAt,
            LocalDate.of(1970, 1, 1).atStartOfDay(),
            ingredients));
    verify(recipeRepository).save(Mockito.any());
  }

  /** Method under test: {@link RecipeService#updateRecipe(int, RecipeDto)} */
  @Test
  void testUpdateRecipe() {
    when(recipeRepository.save(Mockito.any())).thenReturn(new Recipe());
    when(recipeRepository.findById(Mockito.<Integer>any())).thenReturn(Optional.of(new Recipe()));
    RecipeDto actualUpdateRecipeResult = recipeService.updateRecipe(1, new RecipeDto());
    assertNull(actualUpdateRecipeResult.getCreatedAt());
    assertEquals("VEGETARIAN", actualUpdateRecipeResult.getVariant());
    assertNull(actualUpdateRecipeResult.getUpdatedAt());
    assertNull(actualUpdateRecipeResult.getServingCount());
    assertNull(actualUpdateRecipeResult.getName());
    assertNull(actualUpdateRecipeResult.getInstruction());
    assertTrue(actualUpdateRecipeResult.getIngredients().isEmpty());
    verify(recipeRepository).save(Mockito.any());
    verify(recipeRepository).findById(Mockito.<Integer>any());
  }

  /** Method under test: {@link RecipeService#updateRecipe(int, RecipeDto)} */
  @Test
  void testUpdateInvalidRecipe() {
    when(recipeRepository.save(Mockito.any()))
        .thenThrow(
            new IllegalStateException(
                "Update the recipe for the id: {} with name: {} instruction: {} type: {} serving count: {} "));
    when(recipeRepository.findById(Mockito.<Integer>any())).thenReturn(Optional.of(new Recipe()));
    assertThrows(Exception.class, () -> recipeService.updateRecipe(1, new RecipeDto()));
    verify(recipeRepository).save(Mockito.any());
    verify(recipeRepository).findById(Mockito.<Integer>any());
  }

  /** Method under test: {@link RecipeService#updateRecipe(int, RecipeDto)} */
  @Test
  void testUpdateValidRecipe() {
    Recipe recipe = mock(Recipe.class);
    when(recipe.getServingCount()).thenReturn(3);
    when(recipe.getInstruction()).thenReturn("Instruction");
    when(recipe.getName()).thenReturn("Name");
    when(recipe.getVariant()).thenReturn("Variant");
    when(recipe.getCreatedAt()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());
    when(recipe.getUpdatedAt()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());
    when(recipe.getIngredients()).thenReturn(new HashSet<>());
    when(recipeRepository.save(Mockito.any())).thenReturn(recipe);
    when(recipeRepository.findById(Mockito.<Integer>any())).thenReturn(Optional.of(new Recipe()));
    RecipeDto actualUpdateRecipeResult = recipeService.updateRecipe(1, new RecipeDto());
    assertEquals("Variant", actualUpdateRecipeResult.getVariant());
    assertEquals("00:00", actualUpdateRecipeResult.getCreatedAt().toLocalTime().toString());
    assertEquals(3, actualUpdateRecipeResult.getServingCount().intValue());
    assertEquals("00:00", actualUpdateRecipeResult.getUpdatedAt().toLocalTime().toString());
    assertEquals("Name", actualUpdateRecipeResult.getName());
    assertEquals("Instruction", actualUpdateRecipeResult.getInstruction());
    assertTrue(actualUpdateRecipeResult.getIngredients().isEmpty());
    verify(recipeRepository).save(Mockito.any());
    verify(recipeRepository).findById(Mockito.<Integer>any());
    verify(recipe).getServingCount();
    verify(recipe).getInstruction();
    verify(recipe).getName();
    verify(recipe).getVariant();
    verify(recipe).getCreatedAt();
    verify(recipe).getUpdatedAt();
    verify(recipe, atLeast(1)).getIngredients();
  }

  /** Method under test: {@link RecipeService#updateRecipe(int, RecipeDto)} */
  @Test
  void testUpdateRecipeWithValidIngredient() {
    Ingredient ingredient = new Ingredient();
    ingredient.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
    ingredient.setId(1);
    ingredient.setName(
        "Update the recipe for the id: {} with name: {} instruction: {} type: {} serving count: {} ");
    ingredient.setRecipes(new HashSet<>());
    ingredient.setUpdatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());

    HashSet<Ingredient> ingredientSet = new HashSet<>();
    ingredientSet.add(ingredient);
    Recipe recipe = mock(Recipe.class);
    when(recipe.getServingCount()).thenReturn(3);
    when(recipe.getInstruction()).thenReturn("Instruction");
    when(recipe.getName()).thenReturn("Name");
    when(recipe.getVariant()).thenReturn("Variant");
    when(recipe.getCreatedAt()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());
    when(recipe.getUpdatedAt()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());
    when(recipe.getIngredients()).thenReturn(ingredientSet);
    when(recipeRepository.save(Mockito.any())).thenReturn(recipe);
    when(recipeRepository.findById(Mockito.<Integer>any())).thenReturn(Optional.of(new Recipe()));
    RecipeDto actualUpdateRecipeResult = recipeService.updateRecipe(1, new RecipeDto());
    assertEquals("Variant", actualUpdateRecipeResult.getVariant());
    assertEquals("00:00", actualUpdateRecipeResult.getCreatedAt().toLocalTime().toString());
    assertEquals(3, actualUpdateRecipeResult.getServingCount().intValue());
    assertEquals("00:00", actualUpdateRecipeResult.getUpdatedAt().toLocalTime().toString());
    assertEquals("Name", actualUpdateRecipeResult.getName());
    assertEquals("Instruction", actualUpdateRecipeResult.getInstruction());
    assertEquals(1, actualUpdateRecipeResult.getIngredients().size());
    verify(recipeRepository).save(Mockito.any());
    verify(recipeRepository).findById(Mockito.<Integer>any());
    verify(recipe).getServingCount();
    verify(recipe).getInstruction();
    verify(recipe).getName();
    verify(recipe).getVariant();
    verify(recipe).getCreatedAt();
    verify(recipe).getUpdatedAt();
    verify(recipe, atLeast(1)).getIngredients();
  }

  /** Method under test: {@link RecipeService#updateRecipe(int, RecipeDto)} */
  @Test
  void testUpdateRecipeInvalid() {
    when(recipeRepository.findById(Mockito.<Integer>any())).thenReturn(Optional.empty());
    assertThrows(Exception.class, () -> recipeService.updateRecipe(1, new RecipeDto()));
    verify(recipeRepository).findById(Mockito.<Integer>any());
  }

  /** Method under test: {@link RecipeService#updateRecipe(int, RecipeDto)} */
  @Test
  void testUpdateRecipeNotFound() {
    Recipe recipe = mock(Recipe.class);
    doThrow(new NotFoundException()).when(recipe).setInstruction(Mockito.any());
    Optional<Recipe> ofResult = Optional.of(recipe);
    when(recipeRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);
    assertThrows(Exception.class, () -> recipeService.updateRecipe(1, new RecipeDto()));
    verify(recipeRepository).findById(Mockito.<Integer>any());
    verify(recipe).setInstruction(Mockito.any());
  }

  /** Method under test: {@link RecipeService#deleteRecipe(int)} */
  @Test
  void testDeleteRecipe() {
    when(recipeRepository.findById(Mockito.<Integer>any())).thenReturn(Optional.of(new Recipe()));
    doNothing().when(recipeRepository).deleteById(Mockito.<Integer>any());
    assertThrows(IllegalStateException.class, () -> recipeService.deleteRecipe(1));
    verify(recipeRepository).findById(Mockito.<Integer>any());
    verify(recipeRepository).deleteById(Mockito.<Integer>any());
  }

  /** Method under test: {@link RecipeService#deleteRecipe(int)} */
  @Test
  void testDeleteRecipe2() {
    when(recipeRepository.findById(Mockito.<Integer>any())).thenReturn(Optional.empty());
    doNothing().when(recipeRepository).deleteById(Mockito.<Integer>any());
    assertTrue(recipeService.deleteRecipe(1));
    verify(recipeRepository).findById(Mockito.<Integer>any());
    verify(recipeRepository).deleteById(Mockito.<Integer>any());
  }

  /** Method under test: {@link RecipeService#deleteRecipe(int)} */
  @Test
  void testDeleteRecipe3() {
    doThrow(new NotFoundException()).when(recipeRepository).deleteById(Mockito.<Integer>any());
    assertThrows(NotFoundException.class, () -> recipeService.deleteRecipe(1));
    verify(recipeRepository).deleteById(Mockito.<Integer>any());
  }

  /**
   * Method under test: {@link RecipeService#searchRecipe(SearchRequest, Integer, Integer,
   * Sort.Direction)}
   */
  @Test
  void testSearchRecipe() {
    assertThrows(
        Exception.class,
        () -> recipeService.searchRecipe(new SearchRequest(), 1, 3, Sort.Direction.ASC));
    assertThrows(
        Exception.class,
        () ->
            recipeService.searchRecipe(
                new SearchRequest(new ArrayList<>(), "id"), 1, 3, Sort.Direction.ASC));
  }

  @Test
  void testSearchRecipe3() {
    when(recipeRepository.findAll(Mockito.<Specification<Recipe>>any(), Mockito.<Pageable>any()))
        .thenReturn(new PageImpl<>(new ArrayList<>()));

    ArrayList<SearchCriteria> searchCriteriaList = new ArrayList<>();
    searchCriteriaList.add(new SearchCriteria("id", "id", "42", "id"));
    SearchRequest searchRequest = mock(SearchRequest.class);
    when(searchRequest.getDataOption()).thenReturn("Data Option");
    when(searchRequest.getSearchCriteria()).thenReturn(searchCriteriaList);
    assertTrue(recipeService.searchRecipe(searchRequest, 1, 3, Sort.Direction.ASC).isEmpty());
    verify(recipeRepository).findAll(Mockito.<Specification<Recipe>>any(), Mockito.<Pageable>any());
    verify(searchRequest).getDataOption();
    verify(searchRequest).getSearchCriteria();
  }

  /**
   * Method under test: {@link RecipeService#searchRecipe(SearchRequest, Integer, Integer,
   * Sort.Direction)}
   */
  @Test
  void testSearchRecipe4() {
    ArrayList<SearchCriteria> searchCriteriaList = new ArrayList<>();
    searchCriteriaList.add(new SearchCriteria("id", "id", "42", "id"));
    SearchRequest searchRequest = mock(SearchRequest.class);
    when(searchRequest.getDataOption()).thenThrow(new IllegalStateException("id"));
    when(searchRequest.getSearchCriteria()).thenReturn(searchCriteriaList);
    assertThrows(
        IllegalStateException.class,
        () -> recipeService.searchRecipe(searchRequest, 1, 3, Sort.Direction.ASC));
    verify(searchRequest).getDataOption();
    verify(searchRequest).getSearchCriteria();
  }

  /**
   * Method under test: {@link RecipeService#searchRecipe(SearchRequest, Integer, Integer,
   * Sort.Direction)}
   */
  @Test
  void testSearchRecipe5() {
    ArrayList<Recipe> content = new ArrayList<>();
    content.add(new Recipe());
    PageImpl<Recipe> pageImpl = new PageImpl<>(content);
    when(recipeRepository.findAll(Mockito.<Specification<Recipe>>any(), Mockito.<Pageable>any()))
        .thenReturn(pageImpl);

    ArrayList<SearchCriteria> searchCriteriaList = new ArrayList<>();
    searchCriteriaList.add(new SearchCriteria("id", "id", "42", "id"));
    SearchRequest searchRequest = mock(SearchRequest.class);
    when(searchRequest.getDataOption()).thenReturn("Data Option");
    when(searchRequest.getSearchCriteria()).thenReturn(searchCriteriaList);
    assertEquals(1, recipeService.searchRecipe(searchRequest, 1, 3, Sort.Direction.ASC).size());
    verify(recipeRepository).findAll(Mockito.<Specification<Recipe>>any(), Mockito.<Pageable>any());
    verify(searchRequest).getDataOption();
    verify(searchRequest).getSearchCriteria();
  }

  /**
   * Method under test: {@link RecipeService#searchRecipe(SearchRequest, Integer, Integer,
   * Sort.Direction)}
   */
  @Test
  void testSearchRecipe6() {
    ArrayList<Recipe> content = new ArrayList<>();
    content.add(new Recipe());
    content.add(new Recipe());
    PageImpl<Recipe> pageImpl = new PageImpl<>(content);
    when(recipeRepository.findAll(Mockito.<Specification<Recipe>>any(), Mockito.<Pageable>any()))
        .thenReturn(pageImpl);

    ArrayList<SearchCriteria> searchCriteriaList = new ArrayList<>();
    searchCriteriaList.add(new SearchCriteria("id", "id", "42", "id"));
    SearchRequest searchRequest = mock(SearchRequest.class);
    when(searchRequest.getDataOption()).thenReturn("Data Option");
    when(searchRequest.getSearchCriteria()).thenReturn(searchCriteriaList);
    assertEquals(2, recipeService.searchRecipe(searchRequest, 1, 3, Sort.Direction.ASC).size());
    verify(recipeRepository).findAll(Mockito.<Specification<Recipe>>any(), Mockito.<Pageable>any());
    verify(searchRequest).getDataOption();
    verify(searchRequest).getSearchCriteria();
  }

  /**
   * Method under test: {@link RecipeService#searchRecipe(SearchRequest, Integer, Integer,
   * Sort.Direction)}
   */
  @Test
  void testSearchRecipe8() {
    when(recipeRepository.findAll(Mockito.<Specification<Recipe>>any(), Mockito.<Pageable>any()))
        .thenReturn(new PageImpl<>(new ArrayList<>()));

    ArrayList<SearchCriteria> searchCriteriaList = new ArrayList<>();
    searchCriteriaList.add(new SearchCriteria("id", "id", "42", "id"));
    searchCriteriaList.add(new SearchCriteria("id", "id", "42", "id"));
    SearchRequest searchRequest = mock(SearchRequest.class);
    when(searchRequest.getDataOption()).thenReturn("Data Option");
    when(searchRequest.getSearchCriteria()).thenReturn(searchCriteriaList);
    assertTrue(recipeService.searchRecipe(searchRequest, 1, 3, Sort.Direction.ASC).isEmpty());
    verify(recipeRepository).findAll(Mockito.<Specification<Recipe>>any(), Mockito.<Pageable>any());
    verify(searchRequest, atLeast(1)).getDataOption();
    verify(searchRequest).getSearchCriteria();
  }

  /**
   * Method under test: {@link RecipeService#searchRecipe(SearchRequest, Integer, Integer,
   * Sort.Direction)}
   */
  @Test
  void testSearchMockRecipe() {
    when(recipeRepository.findAll(Mockito.<Specification<Recipe>>any(), Mockito.<Pageable>any()))
        .thenReturn(new PageImpl<>(new ArrayList<>()));

    ArrayList<SearchCriteria> searchCriteriaList = new ArrayList<>();
    searchCriteriaList.add(new SearchCriteria("id", "id", "42", "id"));
    SearchRequest searchRequest = mock(SearchRequest.class);
    when(searchRequest.getDataOption()).thenReturn("Data Option");
    when(searchRequest.getSearchCriteria()).thenReturn(searchCriteriaList);
    assertTrue(recipeService.searchRecipe(searchRequest, 1, 3, Sort.Direction.DESC).isEmpty());
    verify(recipeRepository).findAll(Mockito.<Specification<Recipe>>any(), Mockito.<Pageable>any());
    verify(searchRequest).getDataOption();
    verify(searchRequest).getSearchCriteria();
  }

  /**
   * Method under test: {@link RecipeService#searchRecipe(SearchRequest, Integer, Integer,
   * Sort.Direction)}
   */
  @Test
  void testSearchRecipeInteractions() {
    when(recipeRepository.findAll(Mockito.<Specification<Recipe>>any(), Mockito.<Pageable>any()))
        .thenReturn(new PageImpl<>(new ArrayList<>()));

    ArrayList<SearchCriteria> searchCriteriaList = new ArrayList<>();
    searchCriteriaList.add(new SearchCriteria("id", "id", "42", "id"));
    searchCriteriaList.add(new SearchCriteria("id", "id", "42", "id"));
    searchCriteriaList.add(new SearchCriteria("id", "id", "42", "id"));
    SearchRequest searchRequest = mock(SearchRequest.class);
    when(searchRequest.getDataOption()).thenReturn("Data Option");
    when(searchRequest.getSearchCriteria()).thenReturn(searchCriteriaList);
    assertTrue(recipeService.searchRecipe(searchRequest, 1, 3, Sort.Direction.ASC).isEmpty());
    verify(recipeRepository).findAll(Mockito.<Specification<Recipe>>any(), Mockito.<Pageable>any());
    verify(searchRequest, atLeast(1)).getDataOption();
    verify(searchRequest).getSearchCriteria();
  }

  /**
   * Method under test: {@link RecipeService#searchRecipe(SearchRequest, Integer, Integer,
   * Sort.Direction)}
   */
  @Test
  void testSearchValidRecipe() {
    Recipe recipe = mock(Recipe.class);
    when(recipe.getServingCount()).thenReturn(3);
    when(recipe.getInstruction()).thenReturn("Instruction");
    when(recipe.getName()).thenReturn("Name");
    when(recipe.getVariant()).thenReturn("Variant");
    when(recipe.getCreatedAt()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());
    when(recipe.getUpdatedAt()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());
    when(recipe.getIngredients()).thenReturn(new HashSet<>());

    ArrayList<Recipe> content = new ArrayList<>();
    content.add(recipe);
    PageImpl<Recipe> pageImpl = new PageImpl<>(content);
    when(recipeRepository.findAll(Mockito.<Specification<Recipe>>any(), Mockito.<Pageable>any()))
        .thenReturn(pageImpl);

    ArrayList<SearchCriteria> searchCriteriaList = new ArrayList<>();
    searchCriteriaList.add(new SearchCriteria("id", "id", "42", "id"));
    SearchRequest searchRequest = mock(SearchRequest.class);
    when(searchRequest.getDataOption()).thenReturn("Data Option");
    when(searchRequest.getSearchCriteria()).thenReturn(searchCriteriaList);
    assertEquals(1, recipeService.searchRecipe(searchRequest, 1, 3, Sort.Direction.ASC).size());
    verify(recipeRepository).findAll(Mockito.<Specification<Recipe>>any(), Mockito.<Pageable>any());
    verify(recipe).getServingCount();
    verify(recipe).getInstruction();
    verify(recipe).getName();
    verify(recipe).getVariant();
    verify(recipe).getCreatedAt();
    verify(recipe).getUpdatedAt();
    verify(recipe, atLeast(1)).getIngredients();
    verify(searchRequest).getDataOption();
    verify(searchRequest).getSearchCriteria();
  }
}
