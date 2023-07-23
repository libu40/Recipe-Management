package com.recipe.api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.recipe.api.exception.EntityNotFoundException;
import com.recipe.api.model.dto.IngredientDto;
import com.recipe.api.model.dto.RecipeDto;
import com.recipe.api.model.entity.Ingredient;
import com.recipe.api.model.entity.Recipe;
import com.recipe.api.repository.IngredientRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * Test class for {@link IngredientService}.
 */
@ContextConfiguration(classes = {IngredientService.class})
@ExtendWith(SpringExtension.class)
class IngredientServiceTest {
  @MockBean private IngredientRepository ingredientRepository;

  @Autowired private IngredientService ingredientService;
  /** Method under test: {@link IngredientService#getIngredientById(int)} */
  @Test
  void testGetIngredientByIdWithoutRecipe() {
    Ingredient ingredient = new Ingredient();
    ingredient.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
    ingredient.setId(1);
    ingredient.setName("Name");
    ingredient.setRecipes(new HashSet<>());
    ingredient.setUpdatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
    Optional<Ingredient> ofResult = Optional.of(ingredient);
    when(ingredientRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);
    IngredientDto actualIngredientById = ingredientService.getIngredientById(1);
    assertEquals("00:00", actualIngredientById.getCreatedAt().toLocalTime().toString());
    assertEquals("00:00", actualIngredientById.getUpdatedAt().toLocalTime().toString());
    assertTrue(actualIngredientById.getRecipes().isEmpty());
    assertEquals("Name", actualIngredientById.getName());
    verify(ingredientRepository).findById(Mockito.<Integer>any());
  }

  /** Method under test: {@link IngredientService#getIngredientById(int)} */
  @Test
  void testGetIngredientByIdWithEmptyRecipe() {
    HashSet<Recipe> recipes = new HashSet<>();
    recipes.add(new Recipe());

    Ingredient ingredient = new Ingredient();
    ingredient.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
    ingredient.setId(1);
    ingredient.setName("Name");
    ingredient.setRecipes(recipes);
    ingredient.setUpdatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
    Optional<Ingredient> ofResult = Optional.of(ingredient);
    when(ingredientRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);
    IngredientDto actualIngredientById = ingredientService.getIngredientById(1);
    assertEquals("00:00", actualIngredientById.getCreatedAt().toLocalTime().toString());
    assertEquals("00:00", actualIngredientById.getUpdatedAt().toLocalTime().toString());
    assertEquals(1, actualIngredientById.getRecipes().size());
    assertEquals("Name", actualIngredientById.getName());
    verify(ingredientRepository).findById(Mockito.<Integer>any());
  }

  /** Method under test: {@link IngredientService#getIngredientById(int)} */
  @Test()
  void testGetIngredientByIdWithException() {
    when(ingredientRepository.findById(Mockito.<Integer>any())).thenReturn(Optional.empty());

    Assertions.assertThrows(
        EntityNotFoundException.class, () -> ingredientService.getIngredientById(1));
  }

  /** Method under test: {@link IngredientService#getIngredientById(int)} */
  @Test
  void testGetIngredientByIdWithEmptyIngredient() {
    when(ingredientRepository.findById(Mockito.<Integer>any())).thenReturn(Optional.empty());
    assertThrows(EntityNotFoundException.class, () -> ingredientService.getIngredientById(1));
    verify(ingredientRepository).findById(Mockito.<Integer>any());
  }

  /** Method under test: {@link IngredientService#getIngredientById(int)} */
  @Test
  void testGetIngredientByIdEntityNotFoundException() {
    when(ingredientRepository.findById(Mockito.<Integer>any()))
        .thenThrow(new EntityNotFoundException(Object.class));
    assertThrows(EntityNotFoundException.class, () -> ingredientService.getIngredientById(1));
    verify(ingredientRepository).findById(Mockito.<Integer>any());
  }

  /** Method under test: {@link IngredientService#getIngredientById(int)} */
  @Test
  void testGetIngredientByIdInvocations() {
    Recipe recipe = mock(Recipe.class);
    when(recipe.getServingCount()).thenReturn(3);
    when(recipe.getInstruction()).thenReturn("Instruction");
    when(recipe.getName()).thenReturn("Name");
    when(recipe.getVariant()).thenReturn("Variant");
    when(recipe.getCreatedAt()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());
    when(recipe.getUpdatedAt()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());

    HashSet<Recipe> recipes = new HashSet<>();
    recipes.add(recipe);

    Ingredient ingredient = new Ingredient();
    ingredient.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
    ingredient.setId(1);
    ingredient.setName("Name");
    ingredient.setRecipes(recipes);
    ingredient.setUpdatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
    Optional<Ingredient> ofResult = Optional.of(ingredient);
    when(ingredientRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);
    IngredientDto actualIngredientById = ingredientService.getIngredientById(1);
    assertEquals("00:00", actualIngredientById.getCreatedAt().toLocalTime().toString());
    assertEquals("00:00", actualIngredientById.getUpdatedAt().toLocalTime().toString());
    assertEquals(1, actualIngredientById.getRecipes().size());
    assertEquals("Name", actualIngredientById.getName());
    verify(ingredientRepository).findById(Mockito.<Integer>any());
    verify(recipe).getServingCount();
    verify(recipe).getInstruction();
    verify(recipe).getName();
    verify(recipe).getVariant();
    verify(recipe).getCreatedAt();
    verify(recipe).getUpdatedAt();
  }

  /** Method under test: {@link IngredientService#getAllIngredients()} */
  @Test
  void testGetAllIngredients() {
    when(ingredientRepository.findAll()).thenReturn(new ArrayList<>());
    assertThrows(EntityNotFoundException.class, () -> ingredientService.getAllIngredients());
    verify(ingredientRepository).findAll();
  }

  /** Method under test: {@link IngredientService#getAllIngredients()} */
  @Test
  void testGetAllIngredientInvocations() {
    Ingredient ingredient = new Ingredient();
    ingredient.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
    ingredient.setId(1);
    ingredient.setName("fetch all the ingredients");
    ingredient.setRecipes(new HashSet<>());
    ingredient.setUpdatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());

    ArrayList<Ingredient> ingredientList = new ArrayList<>();
    ingredientList.add(ingredient);
    when(ingredientRepository.findAll()).thenReturn(ingredientList);
    List<IngredientDto> actualAllIngredients = ingredientService.getAllIngredients();
    assertEquals(1, actualAllIngredients.size());
    IngredientDto getResult = actualAllIngredients.get(0);
    assertEquals("00:00", getResult.getCreatedAt().toLocalTime().toString());
    assertEquals("00:00", getResult.getUpdatedAt().toLocalTime().toString());
    assertTrue(getResult.getRecipes().isEmpty());
    assertEquals("fetch all the ingredients", getResult.getName());
    verify(ingredientRepository).findAll();
  }

  /** Method under test: {@link IngredientService#getAllIngredients()} */
  @Test
  void testGetMultipleIngredients() {
    Ingredient ingredient = new Ingredient();
    ingredient.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
    ingredient.setId(1);
    ingredient.setName("fetch all the ingredients");
    ingredient.setRecipes(new HashSet<>());
    ingredient.setUpdatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());

    Ingredient ingredient2 = new Ingredient();
    ingredient2.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
    ingredient2.setId(2);
    ingredient2.setName("Name");
    ingredient2.setRecipes(new HashSet<>());
    ingredient2.setUpdatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());

    ArrayList<Ingredient> ingredientList = new ArrayList<>();
    ingredientList.add(ingredient2);
    ingredientList.add(ingredient);
    when(ingredientRepository.findAll()).thenReturn(ingredientList);
    List<IngredientDto> actualAllIngredients = ingredientService.getAllIngredients();
    assertEquals(2, actualAllIngredients.size());
    IngredientDto getResult = actualAllIngredients.get(0);
    assertEquals("00:00", getResult.getUpdatedAt().toLocalTime().toString());
    IngredientDto getResult2 = actualAllIngredients.get(1);
    assertEquals("00:00", getResult2.getUpdatedAt().toLocalTime().toString());
    assertTrue(getResult2.getRecipes().isEmpty());
    assertEquals("fetch all the ingredients", getResult2.getName());
    assertTrue(getResult.getRecipes().isEmpty());
    assertEquals("00:00", getResult2.getCreatedAt().toLocalTime().toString());
    assertEquals("Name", getResult.getName());
    assertEquals("1970-01-01", getResult.getCreatedAt().toLocalDate().toString());
    verify(ingredientRepository).findAll();
  }

  /** Method under test: {@link IngredientService#getAllIngredients()} */
  @Test
  void testGetAllIngredientsWithException() {
    when(ingredientRepository.findAll()).thenThrow(new EntityNotFoundException(Object.class));
    assertThrows(EntityNotFoundException.class, () -> ingredientService.getAllIngredients());
    verify(ingredientRepository).findAll();
  }

  /** Method under test: {@link IngredientService#getAllIngredients()} */
  @Test
  void testGetAllIngredientsWithEmptyRecipe() {
    HashSet<Recipe> recipes = new HashSet<>();
    recipes.add(new Recipe());

    Ingredient ingredient = new Ingredient();
    ingredient.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
    ingredient.setId(1);
    ingredient.setName("fetch all the ingredients");
    ingredient.setRecipes(recipes);
    ingredient.setUpdatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());

    ArrayList<Ingredient> ingredientList = new ArrayList<>();
    ingredientList.add(ingredient);
    when(ingredientRepository.findAll()).thenReturn(ingredientList);
    List<IngredientDto> actualAllIngredients = ingredientService.getAllIngredients();
    assertEquals(1, actualAllIngredients.size());
    IngredientDto getResult = actualAllIngredients.get(0);
    assertEquals("00:00", getResult.getCreatedAt().toLocalTime().toString());
    assertEquals("00:00", getResult.getUpdatedAt().toLocalTime().toString());
    assertEquals(1, getResult.getRecipes().size());
    assertEquals("fetch all the ingredients", getResult.getName());
    verify(ingredientRepository).findAll();
  }

  /** Method under test: {@link IngredientService#getAllIngredients()} */
  @Test
  void testGetAllIngredientsWithRecipe() {
    HashSet<Recipe> recipes = new HashSet<>();
    Recipe recipe = new Recipe();
    recipe.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
    recipe.setUpdatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
    recipe.setInstruction("Instruction");
    recipe.setName("Pasta");
    recipe.setVariant("Vegetarian");
    recipe.setServingCount(3);
    recipes.add(recipe);

    Ingredient ingredient = new Ingredient();
    ingredient.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
    ingredient.setId(1);
    ingredient.setName("fetch all the ingredients");
    ingredient.setRecipes(recipes);
    ingredient.setUpdatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());

    ArrayList<Ingredient> ingredientList = new ArrayList<>();
    ingredientList.add(ingredient);
    when(ingredientRepository.findAll()).thenReturn(ingredientList);
    assertNotNull(ingredientService.getAllIngredients());
  }

  /** Method under test: {@link IngredientService#getAllIngredients()} */
  @Test
  void testGetAllIngredientsWithMockRecipe() {
    Recipe recipe = mock(Recipe.class);
    when(recipe.getServingCount()).thenReturn(3);
    when(recipe.getInstruction()).thenReturn("Instruction");
    when(recipe.getName()).thenReturn("Name");
    when(recipe.getVariant()).thenReturn("Variant");
    when(recipe.getCreatedAt()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());
    when(recipe.getUpdatedAt()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());

    HashSet<Recipe> recipes = new HashSet<>();
    recipes.add(recipe);

    Ingredient ingredient = new Ingredient();
    ingredient.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
    ingredient.setId(1);
    ingredient.setName("fetch all the ingredients");
    ingredient.setRecipes(recipes);
    ingredient.setUpdatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());

    ArrayList<Ingredient> ingredientList = new ArrayList<>();
    ingredientList.add(ingredient);
    when(ingredientRepository.findAll()).thenReturn(ingredientList);
    List<IngredientDto> actualAllIngredients = ingredientService.getAllIngredients();
    assertEquals(1, actualAllIngredients.size());
    IngredientDto getResult = actualAllIngredients.get(0);
    assertEquals("00:00", getResult.getCreatedAt().toLocalTime().toString());
    assertEquals("00:00", getResult.getUpdatedAt().toLocalTime().toString());
    assertEquals(1, getResult.getRecipes().size());
    assertEquals("fetch all the ingredients", getResult.getName());
    verify(ingredientRepository).findAll();
    verify(recipe).getServingCount();
    verify(recipe).getInstruction();
    verify(recipe).getName();
    verify(recipe).getVariant();
    verify(recipe).getCreatedAt();
    verify(recipe).getUpdatedAt();
  }

  /**
   * Method under test: {@link IngredientService#getSortedAndPaginatedIngredients(Integer, Integer,
   * Sort.Direction, String)}
   */
  @Test
  void testGetSortedAndPaginatedMockIngredients() {
    when(ingredientRepository.findAll(Mockito.<Pageable>any()))
        .thenReturn(new PageImpl<>(new ArrayList<>()));
    assertTrue(
        ingredientService
            .getSortedAndPaginatedIngredients(1, 3, Sort.Direction.ASC, "Attribute")
            .isEmpty());
    verify(ingredientRepository).findAll(Mockito.<Pageable>any());
  }

  /**
   * Method under test: {@link IngredientService#getSortedAndPaginatedIngredients(Integer, Integer,
   * Sort.Direction, String)}
   */
  @Test
  void testGetSortedAndPaginatedIngredients() {
    Ingredient ingredient = new Ingredient();
    ingredient.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
    ingredient.setId(1);
    ingredient.setName("fetch all the ingredients in a sorted paginated way");
    ingredient.setRecipes(new HashSet<>());
    ingredient.setUpdatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());

    ArrayList<Ingredient> content = new ArrayList<>();
    content.add(ingredient);
    PageImpl<Ingredient> pageImpl = new PageImpl<>(content);
    when(ingredientRepository.findAll(Mockito.<Pageable>any())).thenReturn(pageImpl);
    List<IngredientDto> actualSortedAndPaginatedIngredients =
        ingredientService.getSortedAndPaginatedIngredients(1, 3, Sort.Direction.ASC, "Attribute");
    assertEquals(1, actualSortedAndPaginatedIngredients.size());
    IngredientDto getResult = actualSortedAndPaginatedIngredients.get(0);
    assertEquals("00:00", getResult.getCreatedAt().toLocalTime().toString());
    assertEquals("00:00", getResult.getUpdatedAt().toLocalTime().toString());
    assertTrue(getResult.getRecipes().isEmpty());
    assertEquals("fetch all the ingredients in a sorted paginated way", getResult.getName());
    verify(ingredientRepository).findAll(Mockito.<Pageable>any());
  }

  /**
   * Method under test: {@link IngredientService#getSortedAndPaginatedIngredients(Integer, Integer,
   * Sort.Direction, String)}
   */
  @Test
  void testGetSortedAndPaginatedWithMultipleIngredients() {
    Ingredient ingredient = new Ingredient();
    ingredient.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
    ingredient.setId(1);
    ingredient.setName("fetch all the ingredients in a sorted paginated way");
    ingredient.setRecipes(new HashSet<>());
    ingredient.setUpdatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());

    Ingredient ingredient2 = new Ingredient();
    ingredient2.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
    ingredient2.setId(2);
    ingredient2.setName("Name");
    ingredient2.setRecipes(new HashSet<>());
    ingredient2.setUpdatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());

    ArrayList<Ingredient> content = new ArrayList<>();
    content.add(ingredient2);
    content.add(ingredient);
    PageImpl<Ingredient> pageImpl = new PageImpl<>(content);
    when(ingredientRepository.findAll(Mockito.<Pageable>any())).thenReturn(pageImpl);
    List<IngredientDto> actualSortedAndPaginatedIngredients =
        ingredientService.getSortedAndPaginatedIngredients(1, 3, Sort.Direction.ASC, "Attribute");
    assertEquals(2, actualSortedAndPaginatedIngredients.size());
    IngredientDto getResult = actualSortedAndPaginatedIngredients.get(0);
    assertEquals("00:00", getResult.getUpdatedAt().toLocalTime().toString());
    IngredientDto getResult2 = actualSortedAndPaginatedIngredients.get(1);
    assertEquals("00:00", getResult2.getUpdatedAt().toLocalTime().toString());
    assertTrue(getResult2.getRecipes().isEmpty());
    assertEquals("fetch all the ingredients in a sorted paginated way", getResult2.getName());
    assertTrue(getResult.getRecipes().isEmpty());
    assertEquals("00:00", getResult2.getCreatedAt().toLocalTime().toString());
    assertEquals("Name", getResult.getName());
    assertEquals("1970-01-01", getResult.getCreatedAt().toLocalDate().toString());
    verify(ingredientRepository).findAll(Mockito.<Pageable>any());
  }

  /**
   * Method under test: {@link IngredientService#getSortedAndPaginatedIngredients(Integer, Integer,
   * Sort.Direction, String)}
   */
  @Test
  void testGetSortedAndPaginatedIngredientsWithEmptyRecipe() {
    when(ingredientRepository.findAll(Mockito.<Pageable>any()))
        .thenReturn(new PageImpl<>(new ArrayList<>()));
    assertNotNull(
        ingredientService.getSortedAndPaginatedIngredients(1, 3, Sort.Direction.ASC, "Attribute"));
  }

  /**
   * Method under test: {@link IngredientService#getSortedAndPaginatedIngredients(Integer, Integer,
   * Sort.Direction, String)}
   */
  @Test
  void testGetSortedAndPaginatedNotEmptyIngredients() {
    when(ingredientRepository.findAll(Mockito.<Pageable>any()))
        .thenReturn(new PageImpl<>(new ArrayList<>()));
    assertTrue(
        ingredientService
            .getSortedAndPaginatedIngredients(1, 3, Sort.Direction.DESC, "Attribute")
            .isEmpty());
    verify(ingredientRepository).findAll(Mockito.<Pageable>any());
  }

  /**
   * Method under test: {@link IngredientService#getSortedAndPaginatedIngredients(Integer, Integer,
   * Sort.Direction, String)}
   */
  @Test
  void testGetSortedAndPaginatedIngredientsWithException() {
    when(ingredientRepository.findAll(Mockito.<Pageable>any()))
        .thenThrow(new EntityNotFoundException(Object.class));
    assertThrows(
        EntityNotFoundException.class,
        () ->
            ingredientService.getSortedAndPaginatedIngredients(
                1, 3, Sort.Direction.ASC, "Attribute"));
    verify(ingredientRepository).findAll(Mockito.<Pageable>any());
  }

  /**
   * Method under test: {@link IngredientService#getSortedAndPaginatedIngredients(Integer, Integer,
   * Sort.Direction, String)}
   */
  @Test
  void testGetSortedAndPaginatedIngredientsInvocations() {
    HashSet<Recipe> recipes = new HashSet<>();
    recipes.add(new Recipe());

    Ingredient ingredient = new Ingredient();
    ingredient.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
    ingredient.setId(1);
    ingredient.setName("fetch all the ingredients in a sorted paginated way");
    ingredient.setRecipes(recipes);
    ingredient.setUpdatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());

    ArrayList<Ingredient> content = new ArrayList<>();
    content.add(ingredient);
    PageImpl<Ingredient> pageImpl = new PageImpl<>(content);
    when(ingredientRepository.findAll(Mockito.<Pageable>any())).thenReturn(pageImpl);
    List<IngredientDto> actualSortedAndPaginatedIngredients =
        ingredientService.getSortedAndPaginatedIngredients(1, 3, Sort.Direction.ASC, "Attribute");
    assertEquals(1, actualSortedAndPaginatedIngredients.size());
    IngredientDto getResult = actualSortedAndPaginatedIngredients.get(0);
    assertEquals("00:00", getResult.getCreatedAt().toLocalTime().toString());
    assertEquals("00:00", getResult.getUpdatedAt().toLocalTime().toString());
    assertEquals(1, getResult.getRecipes().size());
    assertEquals("fetch all the ingredients in a sorted paginated way", getResult.getName());
    verify(ingredientRepository).findAll(Mockito.<Pageable>any());
  }

  /**
   * Method under test: {@link IngredientService#getSortedAndPaginatedIngredients(Integer, Integer,
   * Sort.Direction, String)}
   */
  @Test
  void testGetSortedAndPaginatedIngredients9() {

    Ingredient ingredient = new Ingredient();
    ingredient.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
    ingredient.setId(1);
    ingredient.setName("fetch all the ingredients in a sorted paginated way");
    ingredient.setRecipes(new HashSet<>());
    ingredient.setUpdatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());

    ArrayList<Ingredient> content = new ArrayList<>();
    content.add(ingredient);
    PageImpl<Ingredient> pageImpl = new PageImpl<>(content);

    when(ingredientRepository.findAll(Mockito.<Pageable>any())).thenReturn(pageImpl);
    assertNotNull(
        ingredientService.getSortedAndPaginatedIngredients(1, 3, Sort.Direction.ASC, "Attribute"));
  }

  /** Method under test: {@link IngredientService#createIngredient(IngredientDto)} */
  @Test
  void testCreateIngredient() {
    Ingredient ingredient = new Ingredient();
    ingredient.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
    ingredient.setId(1);
    ingredient.setName("Name");
    ingredient.setRecipes(new HashSet<>());
    ingredient.setUpdatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
    when(ingredientRepository.save(Mockito.any())).thenReturn(ingredient);
    ingredientService.createIngredient(new IngredientDto());
    verify(ingredientRepository).save(Mockito.any());
  }

  /** Method under test: {@link IngredientService#createIngredient(IngredientDto)} */
  @Test
  void testCreateMockIngredient() {
    Ingredient ingredient = new Ingredient();
    ingredient.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
    ingredient.setId(1);
    ingredient.setName("Name");
    ingredient.setRecipes(new HashSet<>());
    ingredient.setUpdatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
    when(ingredientRepository.save(new Ingredient())).thenReturn(ingredient);
    ingredientService.createIngredient(new IngredientDto());
    verify(ingredientRepository).save(Mockito.any());
  }

  /** Method under test: {@link IngredientService#createIngredient(IngredientDto)} */
  @Test
  void testCreateIngredientVerification() {
    Ingredient ingredient = new Ingredient();
    ingredient.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
    ingredient.setId(1);
    ingredient.setName("Name");
    ingredient.setRecipes(new HashSet<>());
    ingredient.setUpdatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
    when(ingredientRepository.save(Mockito.any())).thenReturn(ingredient);
    HashSet<RecipeDto> recipes = new HashSet<>();
    LocalDateTime createdAt = LocalDate.of(1970, 1, 1).atStartOfDay();
    ingredientService.createIngredient(
        new IngredientDto(
            "create the ingredients", recipes, createdAt, LocalDate.of(1970, 1, 1).atStartOfDay()));
    verify(ingredientRepository).save(Mockito.any());
  }

  /** Method under test: {@link IngredientService#createIngredient(IngredientDto)} */
  @Test
  void testCreateIngredient4() {
    Ingredient ingredient = new Ingredient();
    ingredient.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
    ingredient.setId(1);
    ingredient.setName("Name");
    ingredient.setRecipes(new HashSet<>());
    ingredient.setUpdatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
    when(ingredientRepository.save(Mockito.any())).thenReturn(ingredient);

    HashSet<RecipeDto> recipes = new HashSet<>();
    recipes.add(new RecipeDto());
    LocalDateTime createdAt = LocalDate.of(1970, 1, 1).atStartOfDay();
    ingredientService.createIngredient(
        new IngredientDto(
            "create the ingredients", recipes, createdAt, LocalDate.of(1970, 1, 1).atStartOfDay()));
    verify(ingredientRepository).save(Mockito.any());
  }
}
