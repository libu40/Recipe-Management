package com.recipe.api.controller;

import com.recipe.api.model.dto.RecipeDto;
import com.recipe.api.model.valueobject.SearchRequest;
import com.recipe.api.service.RecipeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import jakarta.validation.Valid;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.net.URI;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/** This is the recipe controller which accepts teh web request. */
@Api(value = "Recipe Controller", tags = "Recipe Controller")
@RestController
@RequestMapping("/api")
public class RecipeController {

  private static final Logger LOGGER = LogManager.getLogger(RecipeController.class.getName());

  private final RecipeService recipeService;

  @Autowired
  public RecipeController(RecipeService recipeService) {
    this.recipeService = recipeService;
  }

  /**
   * Method to list recipe by id
   *
   * @param id id
   * @return recipe
   */
  @ApiOperation(value = "List recipe by id", response = RecipeDto.class)
  @ApiResponses(
      value = {
        @ApiResponse(code = 200, message = "Success|OK"),
        @ApiResponse(code = 500, message = "Application Server Error!"),
        @ApiResponse(code = 404, message = "Recipe not found for the given Id!")
      })
  @GetMapping(value = "/recipes/{id}", produces = MediaType.APPLICATION_JSON)
  public Response getRecipeById(
      @ApiParam(value = "recipe Id", required = true) @PathVariable int id) {
    LOGGER.info("fetch recipe for the id: {}", id);
    RecipeDto recipe = recipeService.getRecipeById(id);
    return Response.status(Response.Status.OK).entity(recipe).build();
  }

  /**
   * Method to list all recipes
   *
   * @return recipes
   */
  @ApiOperation(value = "List all recipes", response = RecipeDto.class)
  @ApiResponses(
      value = {
        @ApiResponse(code = 200, message = "Success|OK"),
        @ApiResponse(code = 500, message = "Application Server Error!"),
        @ApiResponse(code = 404, message = "No recipe found!")
      })
  @GetMapping(value = "/recipes", produces = MediaType.APPLICATION_JSON)
  public ResponseEntity<List<RecipeDto>> getAllRecipes() {
    LOGGER.info("Fetch all the recipes");
    List<RecipeDto> recipes = recipeService.getAllRecipes();
    return ResponseEntity.ok().body(recipes);
  }

  /**
   * Method to list recipes in a paginated sorted way.
   *
   * @param pageNo page no
   * @param pageSize page size
   * @param sortBy sort by
   * @return ingredients
   */
  @ApiOperation(value = "List all recipes in a paginated sorted order", response = RecipeDto.class)
  @ApiResponses(
      value = {
        @ApiResponse(code = 200, message = "Success|OK"),
        @ApiResponse(code = 500, message = "Application Server Error!"),
        @ApiResponse(code = 404, message = "No recipes found!"),
        @ApiResponse(code = 400, message = "Bad request"),
      })
  @GetMapping(value = "/recipes/page", produces = MediaType.APPLICATION_JSON)
  public ResponseEntity<List<RecipeDto>> getRecipeList(
      @RequestParam(defaultValue = "0") Integer pageNo,
      @RequestParam(defaultValue = "10") Integer pageSize,
      @RequestParam(defaultValue = "id") String sortBy) {
    LOGGER.info("Fetch all the recipes with pagination enabled in a sorted way");
    List<RecipeDto> recipes = recipeService.getSortedAndPaginatedRecipes(pageNo, pageSize, sortBy);
    return ResponseEntity.ok().body(recipes);
  }

  /**
   * Method to create an recipe
   *
   * @param recipe recipe
   * @return recipe
   */
  @ApiOperation(value = "Create an recipe")
  @ApiResponses(
      value = {
        @ApiResponse(code = 201, message = "Created"),
        @ApiResponse(code = 500, message = "Application Server Error!"),
        @ApiResponse(code = 400, message = "Bad request")
      })
  @PostMapping(
      value = "/recipes",
      consumes = MediaType.APPLICATION_JSON,
      produces = MediaType.APPLICATION_JSON)
  public ResponseEntity<String> createRecipe(@Valid @RequestBody RecipeDto recipe) {
    LOGGER.info("create recipe with name: {}", recipe.getName());
    recipeService.createRecipe(recipe);
    URI uri =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{label}")
            .buildAndExpand("/recipes")
            .toUri();
    return ResponseEntity.created(uri).build();
  }
  /**
   * Method to update an recipe
   *
   * @param recipeDto recipe
   * @param id recipe id
   * @return recipe
   */
  @ApiOperation(value = "Update an recipe")
  @ApiResponses(
      value = {
        @ApiResponse(code = 200, message = "Success|OK"),
        @ApiResponse(code = 500, message = "Application Server Error!"),
        @ApiResponse(code = 404, message = "Recipe not found")
      })
  @PutMapping(
      value = "/recipes/{id}",
      consumes = MediaType.APPLICATION_JSON,
      produces = MediaType.APPLICATION_JSON)
  public ResponseEntity<RecipeDto> updateRecipe(
      @ApiParam(value = "recipe Id", required = true) @PathVariable("id") Integer id,
      @RequestBody RecipeDto recipeDto) {
    LOGGER.info("Update the recipe for the id: {}", id);
    RecipeDto recipe = recipeService.updateRecipe(id, recipeDto);
    if (recipe != null) {
      return new ResponseEntity<>(recipe, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  /**
   * Method to partial update an recipe
   *
   * @param recipeDto recipe
   * @param id recipe id
   * @return recipe
   */
  @ApiOperation(value = "Update partially an recipe")
  @ApiResponses(
      value = {
        @ApiResponse(code = 200, message = "Success|OK"),
        @ApiResponse(code = 500, message = "Application Server Error!"),
        @ApiResponse(code = 404, message = "Recipe not found")
      })
  @PatchMapping(
      value = "/recipes/{id}",
      consumes = MediaType.APPLICATION_JSON,
      produces = MediaType.APPLICATION_JSON)
  public ResponseEntity<RecipeDto> partialUpdateRecipe(
      @ApiParam(value = "recipe Id", required = true) @PathVariable("id") Integer id,
      @RequestBody RecipeDto recipeDto) {
    LOGGER.info("Partially update the recipe for the id: {}", id);
    RecipeDto recipe = recipeService.updateRecipe(id, recipeDto);
    if (recipe != null) {
      return new ResponseEntity<>(recipe, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  /**
   * Method to delete a recipe.
   *
   * @param id recipe id
   */
  @ApiOperation(value = "Delete a recipe")
  @ApiResponses(
      value = {
        @ApiResponse(code = 204, message = "No content"),
        @ApiResponse(code = 500, message = "Application Server Error!")
      })
  @DeleteMapping(value = "/recipes/{id}")
  public ResponseEntity<HttpStatus> deleteTutorial(@PathVariable("id") Integer id) {
    LOGGER.info("Delete the recipe for the id: {}", id);
    boolean isRecipeDeleted = recipeService.deleteRecipe(id);
    if (isRecipeDeleted) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } else {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  /**
   * Method to list recipes by search criteria in a paginated sorted way.
   *
   * @param pageNo page no
   * @param pageSize page size
   * @param sortBy sort by
   * @return ingredients
   */
  @ApiOperation(
      value = "List all recipes by search criteria in a paginated sorted order",
      response = RecipeDto.class)
  @ApiResponses(
      value = {
        @ApiResponse(code = 200, message = "Success|OK"),
        @ApiResponse(code = 500, message = "Application Server Error!"),
        @ApiResponse(code = 404, message = "No recipe found!"),
        @ApiResponse(code = 400, message = "Bad request"),
      })
  @PostMapping(value = "/recipes/search", produces = MediaType.APPLICATION_JSON)
  public ResponseEntity<List<RecipeDto>> getRecipesList(
      @RequestParam(defaultValue = "0") Integer pageNo,
      @RequestParam(defaultValue = "10") Integer pageSize,
      @RequestParam(defaultValue = "id") String sortBy,
      @Valid @RequestBody SearchRequest searchRequest) {
    LOGGER.info("Fetch all the recipes based on the search criteria");
    List<RecipeDto> recipes = recipeService.searchRecipe(searchRequest, pageNo, pageSize, sortBy);
    return ResponseEntity.ok().body(recipes);
  }
}
