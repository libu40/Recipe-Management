package com.recipe.api.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import com.recipe.api.model.dto.IngredientDto;
import com.recipe.api.model.dto.RecipeDto;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

/**
 * This integration tests the {@link IngredientController} to test whether all the endpoints are
 * accessible with the expected response
 */
@SpringBootTest
@AutoConfigureMockMvc
class IngredientControllerTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  private static Faker faker;

  @BeforeAll
  public static void setUp() {
    faker = new Faker();
  }

  @Test
  void shouldFetchAllIngredients() throws Exception {
    String fetchedIngredients =
        mockMvc
            .perform(get("/api/ingredients").accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andReturn()
            .getResponse()
            .getContentAsString();
    List<IngredientDto> ingredients =
        List.of(objectMapper.readValue(fetchedIngredients, IngredientDto[].class));
    Assertions.assertNotNull(ingredients);
    Assertions.assertEquals("Flour", ingredients.get(0).getName(), "names are not equal");
    Assertions.assertNotNull(ingredients.get(0).getCreatedAt());
    Assertions.assertNotNull(ingredients.get(0).getUpdatedAt());
    Assertions.assertNotNull(ingredients.get(0).getRecipes());
  }

  @Test
  void shouldFetchIngredientById() throws Exception {
    String fetchedIngredient =
        mockMvc
            .perform(get("/api/ingredients/2").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print())
            .andReturn()
            .getResponse()
            .getContentAsString();
    IngredientDto ingredient = objectMapper.readValue(fetchedIngredient, IngredientDto.class);
    Assertions.assertNotNull(ingredient);
    Assertions.assertEquals("Baking powder", ingredient.getName(), "names are not equal");
    Assertions.assertNotNull(ingredient.getCreatedAt());
    Assertions.assertNotNull(ingredient.getUpdatedAt());
    Assertions.assertNotNull(ingredient.getRecipes());
  }

  @Test
  void shouldNotFetchIngredientByInvalidId() throws Exception {
    String fetchedIngredients =
        mockMvc
            .perform(get("/api/ingredients/100").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print())
            .andReturn()
            .getResponse()
            .getContentAsString();
    IngredientDto ingredient = objectMapper.readValue(fetchedIngredients, IngredientDto.class);
    Assertions.assertNull(ingredient.getName());
    Assertions.assertNull(ingredient.getRecipes());
    Assertions.assertNull(ingredient.getCreatedAt());
    Assertions.assertNull(ingredient.getUpdatedAt());
  }

  @Test
  void shouldCreateIngredient() throws Exception {
    RecipeDto recipeDto = new RecipeDto();
    recipeDto.setName(faker.food().dish());
    recipeDto.setInstruction("IT instruction");
    recipeDto.setVariant("IT variant");
    recipeDto.setCreatedAt(LocalDateTime.now());
    recipeDto.setUpdatedAt(LocalDateTime.now());
    Set<RecipeDto> recipeSet = new HashSet<>(Collections.singleton(recipeDto));
    mockMvc
        .perform(
            post("/api/ingredients")
                .content(
                    asJsonString(
                        new IngredientDto(
                            faker.food().ingredient(),
                            recipeSet,
                            LocalDateTime.now(),
                            LocalDateTime.now())))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated())
        .andDo(print())
        .andReturn()
        .getResponse()
        .getContentAsString();
  }

  @Test
  void shouldFetchIngredientsBySortedAndPaginatedWithDefaultValues() throws Exception {
    String fetchedIngredients =
        mockMvc
            .perform(get("/api/ingredients/page").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print())
            .andReturn()
            .getResponse()
            .getContentAsString();
    List<IngredientDto> ingredients =
        List.of(objectMapper.readValue(fetchedIngredients, IngredientDto[].class));
    Assertions.assertNotNull(ingredients);
    Assertions.assertEquals("Flour", ingredients.get(0).getName(), "names are not equal");
    Assertions.assertNotNull(ingredients.get(0).getCreatedAt());
    Assertions.assertNotNull(ingredients.get(0).getUpdatedAt());
    Assertions.assertNotNull(ingredients.get(0).getRecipes());
  }

  @Test
  void shouldFetchIngredientsBySortedAndPaginatedWithSpecifiedValues() throws Exception {
    String fetchedIngredients =
        mockMvc
            .perform(
                get("/api/ingredients/page")
                    .accept(MediaType.APPLICATION_JSON)
                    .param("pageNo", "0")
                    .param("pageSize", "5")
                    .param("sortBy", "DESC")
                    .param("attribute", "name"))
            .andExpect(status().isOk())
            .andDo(print())
            .andReturn()
            .getResponse()
            .getContentAsString();
    List<IngredientDto> ingredients =
        List.of(objectMapper.readValue(fetchedIngredients, IngredientDto[].class));
    Assertions.assertNotNull(ingredients);
    Assertions.assertEquals("Sugar", ingredients.get(0).getName(), "names are not equal");
    Assertions.assertNotNull(ingredients.get(0).getCreatedAt());
    Assertions.assertNotNull(ingredients.get(0).getUpdatedAt());
    Assertions.assertNotNull(ingredients.get(0).getRecipes());
  }

  private String asJsonString(final Object obj) {
    try {
      return new ObjectMapper().writeValueAsString(obj);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
