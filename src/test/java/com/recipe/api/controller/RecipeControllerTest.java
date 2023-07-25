package com.recipe.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import com.recipe.api.enums.RequestDataOptionType;
import com.recipe.api.enums.RequestKeyType;
import com.recipe.api.enums.RequestOperationType;
import com.recipe.api.model.dto.IngredientDto;
import com.recipe.api.model.dto.RecipeDto;
import com.recipe.api.model.valueobject.SearchCriteria;
import com.recipe.api.model.valueobject.SearchRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * This integration tests the {@link RecipeController} to test whether all the endpoints are
 * accessible with the expected response
 */
@SpringBootTest
@AutoConfigureMockMvc
class RecipeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static Faker faker;

    @BeforeAll
    public static void setUp() {
        faker = new Faker();
    }

    @Test
    void shouldFetchAllRecipes() throws Exception {
        String fetchedRecipes = mockMvc.perform(get("/api/recipes").accept(MediaType.APPLICATION_JSON)).andDo(print()).andReturn().getResponse().getContentAsString();
        List<RecipeDto> recipes = List.of(objectMapper.readValue(fetchedRecipes, RecipeDto[].class));
        Assertions.assertNotNull(recipes);
        Assertions.assertEquals("Peanut Butter Pretzel Cookies", recipes.get(0).getName(), "names are not equal");
        Assertions.assertEquals("Test", recipes.get(0).getInstruction(), "instructions are not equal");
        Assertions.assertEquals("Vegetarian", recipes.get(0).getVariant(), "variants are not equal");
        Assertions.assertEquals(4, recipes.get(0).getServingCount(), "serving count are not equal");
        Assertions.assertNotNull(recipes.get(0).getCreatedAt());
        Assertions.assertNotNull(recipes.get(0).getUpdatedAt());
        Assertions.assertNotNull(recipes.get(0).getIngredients());
    }

    @Test
    void shouldFetchRecipeById() throws Exception {
        String fetchedRecipe = mockMvc.perform(get("/api/recipes/2").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andDo(print()).andReturn().getResponse().getContentAsString();
        RecipeDto recipe = objectMapper.readValue(fetchedRecipe, RecipeDto.class);
        Assertions.assertNotNull(recipe);
        Assertions.assertEquals("Green Beans with Toasted Butter Pecans", recipe.getName(), "names are not equal");
        Assertions.assertNotNull(recipe.getCreatedAt());
        Assertions.assertNotNull(recipe.getVariant());
        Assertions.assertNotNull(recipe.getInstruction());
        Assertions.assertNotNull(recipe.getServingCount());
        Assertions.assertNotNull(recipe.getUpdatedAt());
        Assertions.assertNotNull(recipe.getIngredients());
    }

    @Test
    void shouldNotFetchRecipeByInvalidId() throws Exception {
        String fetchedRecipe = mockMvc.perform(get("/api/recipes/100").accept(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound()).andDo(print()).andReturn().getResponse().getContentAsString();
        RecipeDto recipe = objectMapper.readValue(fetchedRecipe, RecipeDto.class);
        Assertions.assertNull(recipe.getName());
        Assertions.assertNull(recipe.getIngredients());
        Assertions.assertNull(recipe.getCreatedAt());
        Assertions.assertNull(recipe.getUpdatedAt());
    }

    @Test
    void shouldFetchRecipesBySortedAndPaginatedWithDefaultValues() throws Exception {
        String fetchedRecipes = mockMvc.perform(get("/api/recipes/page").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andDo(print()).andReturn().getResponse().getContentAsString();
        List<RecipeDto> recipes = List.of(objectMapper.readValue(fetchedRecipes, RecipeDto[].class));
        Assertions.assertNotNull(recipes);
        Assertions.assertEquals("Peanut Butter Pretzel Cookies", recipes.get(0).getName(), "names are not equal");
        Assertions.assertNotNull(recipes.get(0).getCreatedAt());
        Assertions.assertNotNull(recipes.get(0).getUpdatedAt());
        Assertions.assertNotNull(recipes.get(0).getIngredients());
        Assertions.assertNotNull(recipes.get(0).getInstruction());
        Assertions.assertNotNull(recipes.get(0).getVariant());
        Assertions.assertNotNull(recipes.get(0).getServingCount());
    }

    @Test
    void shouldFetchRecipesBySortedAndPaginatedWithSpecifiedValues() throws Exception {
        String fetchedRecipes = mockMvc.perform(get("/api/recipes/page").accept(MediaType.APPLICATION_JSON).param("pageNo", "0").param("pageSize", "5").param("sortBy", "ASC").param("attribute", "id")).andExpect(status().isOk()).andDo(print()).andReturn().getResponse().getContentAsString();
        List<RecipeDto> recipes = List.of(objectMapper.readValue(fetchedRecipes, RecipeDto[].class));
        Assertions.assertNotNull(recipes);
        Assertions.assertEquals("Peanut Butter Pretzel Cookies", recipes.get(0).getName(), "names are not equal");
        Assertions.assertNotNull(recipes.get(0).getCreatedAt());
        Assertions.assertNotNull(recipes.get(0).getUpdatedAt());
        Assertions.assertNotNull(recipes.get(0).getIngredients());
        Assertions.assertNotNull(recipes.get(0).getVariant());
        Assertions.assertNotNull(recipes.get(0).getInstruction());
        Assertions.assertNotNull(recipes.get(0).getServingCount());
    }

    @Test
    void shouldCreateRecipe() throws Exception {
        IngredientDto ingredientDto = new IngredientDto();
        ingredientDto.setName(faker.food().ingredient());
        ingredientDto.setCreatedAt(LocalDateTime.now());
        ingredientDto.setUpdatedAt(LocalDateTime.now());
        Set<IngredientDto> ingredients = new HashSet<>(Collections.singleton(ingredientDto));
        mockMvc.perform(post("/api/recipes").content(asJsonString(new RecipeDto(faker.food().dish(), "IT instruction", "IT variant", 5, LocalDateTime.now(), LocalDateTime.now(), ingredients))).accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated()).andDo(print()).andReturn().getResponse().getContentAsString();
    }

    @Test
    void shouldUpdateRecipe() throws Exception {
        String fetchedRecipe = mockMvc.perform(put("/api/recipes/6").content(asJsonString(new RecipeDto(faker.food().dish(), "IT update instruction", "IT update variant", 7, LocalDateTime.now(), LocalDateTime.now(), new HashSet<>()))).accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andDo(print()).andReturn().getResponse().getContentAsString();
        RecipeDto recipe = objectMapper.readValue(fetchedRecipe, RecipeDto.class);
        Assertions.assertNotNull(recipe);
        Assertions.assertNotNull(recipe.getCreatedAt());
        Assertions.assertNotNull(recipe.getVariant());
        Assertions.assertNotNull(recipe.getInstruction());
        Assertions.assertNotNull(recipe.getServingCount());
        Assertions.assertNotNull(recipe.getUpdatedAt());
    }

    @Test
    void shouldPartiallyUpdateRecipe() throws Exception {
        String fetchedRecipe = mockMvc.perform(patch("/api/recipes/5").content(asJsonString(new RecipeDto("Kumquat Cake", "Test", "NonVegetarian", 1, LocalDateTime.now(), LocalDateTime.now(), new HashSet<>()))).accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andDo(print()).andReturn().getResponse().getContentAsString();
        RecipeDto recipe = objectMapper.readValue(fetchedRecipe, RecipeDto.class);
        Assertions.assertNotNull(recipe);
        Assertions.assertNotNull(recipe.getCreatedAt());
        Assertions.assertNotNull(recipe.getVariant());
        Assertions.assertNotNull(recipe.getInstruction());
        Assertions.assertNotNull(recipe.getServingCount());
        Assertions.assertNotNull(recipe.getUpdatedAt());
    }

    @Test
    void shouldRemoveRecipeById() throws Exception {
        mockMvc.perform(delete("/api/recipes/7")).andExpect(status().isNoContent()).andDo(print()).andReturn().getResponse().getContentAsString();
    }

    @Test
    void shouldSearchRecipesInSortedAndPaginatedWithSearchCriteria() throws Exception {
        List<SearchCriteria> searchCriteriaList = new ArrayList<>();
        SearchCriteria searchCriteria = new SearchCriteria(RequestKeyType.name.toString(), RequestOperationType.CN.toString(), "Peanut Butter", RequestDataOptionType.ALL.toString());
        searchCriteriaList.add(searchCriteria);
        String fetchedRecipes = mockMvc.perform(post("/api/recipes/search").content(asJsonString(new SearchRequest(searchCriteriaList, RequestDataOptionType.ALL.toString()))).accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).param("pageNo", "0").param("pageSize", "10").param("sortBy", "ASC")).andExpect(status().isOk()).andDo(print()).andReturn().getResponse().getContentAsString();
        List<RecipeDto> recipes = List.of(objectMapper.readValue(fetchedRecipes, RecipeDto[].class));
        Assertions.assertNotNull(recipes);
        Assertions.assertEquals("Peanut Butter Pretzel Cookies", recipes.get(0).getName(), "names are not equal");
        Assertions.assertNotNull(recipes.get(0).getCreatedAt());
        Assertions.assertNotNull(recipes.get(0).getUpdatedAt());
        Assertions.assertNotNull(recipes.get(0).getIngredients());
        Assertions.assertNotNull(recipes.get(0).getVariant());
        Assertions.assertNotNull(recipes.get(0).getInstruction());
        Assertions.assertNotNull(recipes.get(0).getServingCount());
    }

    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
