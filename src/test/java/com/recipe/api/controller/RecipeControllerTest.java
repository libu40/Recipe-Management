package com.recipe.api.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import com.recipe.api.model.dto.RecipeDto;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

/**
 * This integration tests the {@link RecipeController} to test whether all the endpoints are
 * accessible with the expected response
 */
@SpringBootTest
@AutoConfigureMockMvc
class RecipeControllerTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  private static Faker faker;

  @BeforeAll
  public static void setUp() {
    faker = new Faker();
  }

  @Test
  void shouldFetchAllRecipes() throws Exception {
    String fetchedRecipes =
        mockMvc
            .perform(get("/api/recipes").accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andReturn()
            .getResponse()
            .getContentAsString();
    List<RecipeDto> recipes = List.of(objectMapper.readValue(fetchedRecipes, RecipeDto[].class));
    Assertions.assertNotNull(recipes);
    Assertions.assertEquals(
        "Peanut Butter Pretzel Cookies", recipes.get(0).getName(), "names are not equal");
    Assertions.assertEquals("Test", recipes.get(0).getInstruction(), "instructions are not equal");
    Assertions.assertEquals("Vegetarian", recipes.get(0).getVariant(), "variants are not equal");
    Assertions.assertEquals(4, recipes.get(0).getServingCount(), "serving count are not equal");
    Assertions.assertNotNull(recipes.get(0).getCreatedAt());
    Assertions.assertNotNull(recipes.get(0).getUpdatedAt());
    Assertions.assertNotNull(recipes.get(0).getIngredients());
  }

  @Test
  void shouldFetchRecipeById() throws Exception {
    String fetchedRecipe =
        mockMvc
            .perform(get("/api/recipes/2").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print())
            .andReturn()
            .getResponse()
            .getContentAsString();
    RecipeDto recipe = objectMapper.readValue(fetchedRecipe, RecipeDto.class);
    Assertions.assertNotNull(recipe);
    Assertions.assertEquals(
        "Green Beans with Toasted Butter Pecans", recipe.getName(), "names are not equal");
    Assertions.assertNotNull(recipe.getCreatedAt());
    Assertions.assertNotNull(recipe.getVariant());
    Assertions.assertNotNull(recipe.getInstruction());
    Assertions.assertNotNull(recipe.getServingCount());
    Assertions.assertNotNull(recipe.getUpdatedAt());
    Assertions.assertNotNull(recipe.getIngredients());
  }

  @Test
  void shouldNotFetchRecipeByInvalidId() throws Exception {
    String fetchedRecipe =
        mockMvc
            .perform(get("/api/recipes/100").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andDo(print())
            .andReturn()
            .getResponse()
            .getContentAsString();
    RecipeDto recipe = objectMapper.readValue(fetchedRecipe, RecipeDto.class);
    Assertions.assertNull(recipe.getName());
    Assertions.assertNull(recipe.getIngredients());
    Assertions.assertNull(recipe.getCreatedAt());
    Assertions.assertNull(recipe.getUpdatedAt());
  }

  private String asJsonString(final Object obj) {
    try {
      return new ObjectMapper().writeValueAsString(obj);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
