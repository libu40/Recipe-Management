package com.recipe.api.service;

import com.recipe.api.exception.EntityNotFoundException;
import com.recipe.api.mapper.IngredientMapper;
import com.recipe.api.model.dto.IngredientDto;
import com.recipe.api.model.entity.Ingredient;
import com.recipe.api.repository.IngredientRepository;
import java.util.List;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** This service class is used for doing the CRUD business logic for ingredients. */
@Service
@Transactional
public class IngredientService {

  private static final Logger LOGGER = LogManager.getLogger(IngredientService.class.getName());

  private final IngredientRepository ingredientRepository;

  private final IngredientMapper ingredientMapper;

  @Autowired
  public IngredientService(
      IngredientRepository ingredientRepository, IngredientMapper ingredientMapper) {
    this.ingredientRepository = ingredientRepository;
    this.ingredientMapper = ingredientMapper;
  }

  public IngredientDto getIngredientById(int id) {
    LOGGER.info("fetch ingredients by id: {}", id);
    Optional<Ingredient> ingredient = ingredientRepository.findById(id);
    if (ingredient.isPresent()) {
      Ingredient ingredientEntity = ingredient.get();
      return ingredientMapper.ingredientEntityToDto(ingredientEntity);
    }
    throw new EntityNotFoundException(Ingredient.class, "id", String.valueOf(id));
  }

  public List<IngredientDto> getAllIngredients() {
    LOGGER.info("fetch all the ingredients");
    List<Ingredient> ingredients = ingredientRepository.findAll();
    if (!ingredients.isEmpty()) {
      return ingredients.stream().map(ingredientMapper::ingredientEntityToDto).toList();
    }
    throw new EntityNotFoundException(Ingredient.class);
  }

  public List<IngredientDto> getSortedAndPaginatedIngredients(
      Integer pageNo, Integer pageSize, Direction sortBy, String attribute) {
    LOGGER.info("fetch all the ingredients in a sorted paginated way");
    Page<Ingredient> paginatedIngredients =
        ingredientRepository.findAll(
            PageRequest.of(pageNo, pageSize, sortBy, attribute));
    List<Ingredient> ingredients = paginatedIngredients.getContent();
    return ingredients.stream().map(ingredientMapper::ingredientEntityToDto).toList();
  }

  public void createIngredient(IngredientDto ingredient) {
    LOGGER.info("create the ingredients");
    Ingredient saveIngredient = ingredientMapper.ingredientDtoToEntity(ingredient);
    ingredientRepository.save(saveIngredient);
  }
}
