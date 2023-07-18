package com.recipe.api.enums;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

/**
 * Constraints on the enum validation for the user request in searching the required records based
 * on the fields.
 */
public class EnumValidatorConstraint implements ConstraintValidator<EnumValidator, String> {
  private List<String> acceptedValues;

  @Override
  public void initialize(EnumValidator constraintAnnotation) {
    acceptedValues = new ArrayList<>();
    Class<? extends Enum<?>> enumClass = constraintAnnotation.enumClass();

    Enum[] enumValArr = enumClass.getEnumConstants();

    for (Enum enumVal : enumValArr) {
      acceptedValues.add(enumVal.toString().toUpperCase());
    }
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if (value == null) {
      return true;
    }
    return acceptedValues.contains(value.toUpperCase());
  }
}
