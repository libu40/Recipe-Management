package com.recipe.api.model;

import static org.assertj.core.api.Assertions.assertThat;

import com.recipe.api.model.dto.IngredientDto;
import com.recipe.api.model.dto.RecipeDto;
import groovy.util.logging.Slf4j;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * This is the dto test class responsible for testing Json serialization and deserialization of
 * recipe dto.
 */
@JsonTest
@RunWith(SpringRunner.class)
@Slf4j
public class RecipeDtoTest {

  private static final Logger LOGGER = LoggerFactory.getLogger(RecipeDtoTest.class);

  @Autowired private JacksonTester<RecipeDto> jacksonTester;

  private JsonContent<RecipeDto> asJson;

  private static final DateTimeFormatter dateTimeFormatter =
      DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

  private static final LocalDateTime LOCAL_DATE_TIME = LocalDateTime.of(2019, 3, 28, 14, 33, 48);

  private static final String NAME = "Pasta";
  private static final String INSTRUCTION = "Cook";
  private static final String VARIANT = "vegetarian";
  private static final String SERVING_COUNT = "7";
  private static final String CREATED_AT = LOCAL_DATE_TIME.format(dateTimeFormatter);
  private static final String UPDATED_AT = LOCAL_DATE_TIME.format(dateTimeFormatter);
  private static final Set<IngredientDto> INGREDIENTS = null;

  private static final String JSON_TO_DESERIALIZE =
      "{\"name\":\"Pasta\",\"instruction\":\"Cook\",\"variant\":\"vegetarian\",\"servingCount\":\"7\",\"createdAt\":\"2019-03-28 14:33:48\",\"updatedAt\":\"2019-03-28 14:33:48\"}";

  @Before
  public void setup() throws IOException {
    RecipeDto recipeDto =
        new RecipeDto(
            NAME,
            INSTRUCTION,
            VARIANT,
            Integer.parseInt(SERVING_COUNT),
            LOCAL_DATE_TIME,
            LOCAL_DATE_TIME,
            INGREDIENTS);

    asJson = jacksonTester.write(recipeDto);
  }

  @Test
  public void nameSerializeTest() {
    LOGGER.debug("serializing check for the recipe name: {}", NAME);
    assertThat(asJson).extractingJsonPathStringValue("@.name").isEqualTo(NAME);
  }

  @Test
  public void createdAtSerializeTest() {
    LOGGER.debug("serializing check for the recipe creation date: {}", CREATED_AT);
    assertThat(asJson).extractingJsonPathStringValue("@.createdAt").isEqualTo(CREATED_AT);
  }

  @Test
  public void ingredientSerializeTest() {
    LOGGER.debug("serializing check for the recipe ingredient date: {}", INGREDIENTS);
    assertThat(asJson).extractingJsonPathStringValue("@.ingredients").isEqualTo(INGREDIENTS);
  }

  @Test
  public void updatedAtSerializeTest() {
    LOGGER.debug("serializing check for the recipe updated date: {}", UPDATED_AT);
    assertThat(asJson).extractingJsonPathStringValue("@.updatedAt").isEqualTo(UPDATED_AT);
  }

  @Test
  public void variantSerializeTest() {
    LOGGER.debug("serializing check for the recipe variant: {}", VARIANT);
    assertThat(asJson).extractingJsonPathStringValue("@.variant").isEqualTo(VARIANT);
  }

  @Test
  public void instructionSerializeTest() {
    LOGGER.debug("serializing check for the recipe instruction: {}", INSTRUCTION);
    assertThat(asJson).extractingJsonPathStringValue("@.instruction").isEqualTo(INSTRUCTION);
  }

  @Test
  public void nameDeserializeTest() throws IOException {
    LOGGER.debug("deserializing check for the recipe variant: {}", NAME);
    assertThat(this.jacksonTester.parseObject(JSON_TO_DESERIALIZE).getName()).isEqualTo(NAME);
  }

  @Test
  public void instructionDeserializeTest() throws IOException {
    LOGGER.debug("deserializing check for the recipe instruction: {}", INSTRUCTION);
    assertThat(this.jacksonTester.parseObject(JSON_TO_DESERIALIZE).getInstruction())
        .isEqualTo(INSTRUCTION);
  }

  @Test
  public void servingCountDeserializeTest() throws IOException {
    LOGGER.debug("deserializing check for the recipe serving count: {}", SERVING_COUNT);
    assertThat(this.jacksonTester.parseObject(JSON_TO_DESERIALIZE).getServingCount())
        .isEqualTo(Integer.parseInt(SERVING_COUNT));
  }

  @Test
  public void variantDeserializeTest() throws IOException {
    LOGGER.debug("deserializing check for the recipe variant: {}", VARIANT);
    assertThat(this.jacksonTester.parseObject(JSON_TO_DESERIALIZE).getVariant()).isEqualTo(VARIANT);
  }

  @Test
  public void createdAtDeserializeTest() throws IOException {
    LOGGER.debug("deserializing check for the recipe creation date: {}", CREATED_AT);
    assertThat(
            this.jacksonTester
                .parseObject(JSON_TO_DESERIALIZE)
                .getCreatedAt()
                .format(dateTimeFormatter))
        .isEqualTo(CREATED_AT);
  }

  @Test
  public void updatedAtDeserializeTest() throws IOException {
    LOGGER.debug("deserializing check for the recipe updating date: {}", UPDATED_AT);
    assertThat(
            this.jacksonTester
                .parseObject(JSON_TO_DESERIALIZE)
                .getUpdatedAt()
                .format(dateTimeFormatter))
        .isEqualTo(UPDATED_AT);
  }
}
