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
 * ingredient dto.
 */
@JsonTest
@RunWith(SpringRunner.class)
@Slf4j
public class IngredientDtoTest {

  private static final Logger LOGGER = LoggerFactory.getLogger(IngredientDtoTest.class);

  @Autowired private JacksonTester<IngredientDto> jacksonTester;

  private JsonContent<IngredientDto> asJson;

  private static final DateTimeFormatter dateTimeFormatter =
      DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

  private static final LocalDateTime LOCAL_DATE_TIME = LocalDateTime.of(2019, 3, 28, 14, 33, 48);

  private static final String NAME = "Potato";
  private static final Set<RecipeDto> RECIPES = null;
  private static final String CREATED_AT = LOCAL_DATE_TIME.format(dateTimeFormatter);
  private static final String UPDATED_AT = LOCAL_DATE_TIME.format(dateTimeFormatter);

  private static final String JSON_TO_DESERIALIZE =
      "{\"name\":\"Potato\",\"createdAt\":\"2019-03-28 14:33:48\",\"updatedAt\":\"2019-03-28 14:33:48\"}";

  @Before
  public void setup() throws IOException {
    IngredientDto ingredientDto =
        new IngredientDto(NAME, RECIPES, LOCAL_DATE_TIME, LOCAL_DATE_TIME);
    asJson = jacksonTester.write(ingredientDto);
  }

  @Test
  public void nameSerializeTest() {
    LOGGER.debug("serializing check for the ingredient name: {}", NAME);
    assertThat(asJson).extractingJsonPathStringValue("@.name").isEqualTo(NAME);
  }

  @Test
  public void createdAtSerializeTest() {
    LOGGER.debug("serializing check for the ingredient creation date: {}", CREATED_AT);
    assertThat(asJson).extractingJsonPathStringValue("@.createdAt").isEqualTo(CREATED_AT);
  }

  @Test
  public void recipeSerializeTest() {
    LOGGER.debug("serializing check for the ingredient recipe date: {}", RECIPES);
    assertThat(asJson).extractingJsonPathStringValue("@.recipes").isEqualTo(RECIPES);
  }

  @Test
  public void updatedAtSerializeTest() {
    LOGGER.debug("serializing check for the ingredient updated date: {}", UPDATED_AT);
    assertThat(asJson).extractingJsonPathStringValue("@.updatedAt").isEqualTo(UPDATED_AT);
  }

  @Test
  public void nameDeserializeTest() throws IOException {
    LOGGER.debug("deserializing check for the ingredient name: {}", NAME);
    assertThat(this.jacksonTester.parseObject(JSON_TO_DESERIALIZE).getName()).isEqualTo(NAME);
  }

  @Test
  public void createdAtDeserializeTest() throws IOException {
    LOGGER.debug("deserializing check for the ingredient creation date: {}", CREATED_AT);
    assertThat(
            this.jacksonTester
                .parseObject(JSON_TO_DESERIALIZE)
                .getCreatedAt()
                .format(dateTimeFormatter))
        .isEqualTo(CREATED_AT);
  }

  @Test
  public void updatedAtDeserializeTest() throws IOException {
    LOGGER.debug("deserializing check for the ingredient updating date: {}", UPDATED_AT);
    assertThat(
            this.jacksonTester
                .parseObject(JSON_TO_DESERIALIZE)
                .getUpdatedAt()
                .format(dateTimeFormatter))
        .isEqualTo(UPDATED_AT);
  }
}
