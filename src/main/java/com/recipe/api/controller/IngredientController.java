package com.recipe.api.controller;

import com.recipe.api.model.dto.IngredientDto;
import com.recipe.api.service.IngredientService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import jakarta.validation.Valid;
import jakarta.ws.rs.core.MediaType;
import java.net.URI;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/** This is the ingredient controller which accepts teh web request. */
@Api(value = "Ingredient Controller", tags = "Ingredient Controller")
@RestController
@RequestMapping("/api")
public class IngredientController {

  private static final Logger LOGGER = LogManager.getLogger(IngredientController.class.getName());

  private final IngredientService ingredientService;

  @Autowired
  public IngredientController(IngredientService ingredientService) {
    this.ingredientService = ingredientService;
  }

  /**
   * Method to list ingredient by id
   *
   * @param id id
   * @return ingredient
   */
  @ApiOperation(value = "List ingredient by id", response = IngredientDto.class)
  @ApiResponses(
      value = {
        @ApiResponse(code = 200, message = "Success|OK"),
        @ApiResponse(code = 500, message = "Application Server Error!"),
        @ApiResponse(code = 404, message = "Ingredient not found for the given Id!")
      })
  @GetMapping(value = "/ingredient/{id}", produces = MediaType.APPLICATION_JSON)
  public ResponseEntity<IngredientDto> getIngredientById(
      @ApiParam(value = "Ingredient Id", required = true) @PathVariable int id) {
    LOGGER.info("fetch ingredient for the id: {}", id);
    IngredientDto ingredient = ingredientService.getIngredientById(id);
    return ResponseEntity.ok().body(ingredient);
  }

  /**
   * Method to list all ingredients
   *
   * @return ingredients
   */
  @ApiOperation(value = "List all ingredients", response = IngredientDto.class)
  @ApiResponses(
      value = {
        @ApiResponse(code = 200, message = "Success|OK"),
        @ApiResponse(code = 500, message = "Application Server Error!"),
        @ApiResponse(code = 404, message = "No Ingredients found!")
      })
  @GetMapping(value = "/ingredients", produces = MediaType.APPLICATION_JSON)
  public ResponseEntity<List<IngredientDto>> getAllIngredients() {
    LOGGER.info("Fetch all the ingredients");
    List<IngredientDto> ingredients = ingredientService.getAllIngredients();
    return ResponseEntity.ok().body(ingredients);
  }

  /**
   * Method to list ingredient in a paginated sorted way.
   *
   * @param pageNo page no
   * @param pageSize page size
   * @param sortBy sort by
   * @return ingredients
   */
  @ApiOperation(
      value = "List all ingredients in a paginated sorted order",
      response = IngredientDto.class)
  @ApiResponses(
      value = {
        @ApiResponse(code = 200, message = "Success|OK"),
        @ApiResponse(code = 500, message = "Application Server Error!"),
        @ApiResponse(code = 404, message = "No Ingredients found!"),
        @ApiResponse(code = 400, message = "Bad request"),
      })
  @GetMapping(value = "/ingredients", produces = MediaType.APPLICATION_JSON)
  public ResponseEntity<List<IngredientDto>> getIngredientList(
      @RequestParam(defaultValue = "0") Integer pageNo,
      @RequestParam(defaultValue = "10") Integer pageSize,
      @RequestParam(defaultValue = "id") String sortBy) {
    LOGGER.info("Fetch all the ingredients with pagination enabled in a sorted way");
    List<IngredientDto> ingredients =
        ingredientService.getSortedAndPaginatedIngredients(pageNo, pageSize, sortBy);
    return ResponseEntity.ok().body(ingredients);
  }

  /**
   * Method to create an ingredient
   *
   * @param ingredient ingredient
   * @return ingredient
   */
  @ApiOperation(value = "Create an ingredient")
  @ApiResponses(
      value = {
        @ApiResponse(code = 201, message = "Created"),
        @ApiResponse(code = 500, message = "Application Server Error!"),
        @ApiResponse(code = 400, message = "Bad request")
      })
  @PostMapping(
      value = "/ingredients",
      consumes = MediaType.APPLICATION_JSON,
      produces = MediaType.APPLICATION_JSON)
  public ResponseEntity<String> createIngredient(@Valid @RequestBody IngredientDto ingredient) {
    LOGGER.info("create ingredient with name: {}", ingredient.getName());
    ingredientService.createIngredient(ingredient);
    URI uri =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{label}")
            .buildAndExpand("/ingredients")
            .toUri();
    return ResponseEntity.created(uri).build();
  }
}
