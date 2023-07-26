package com.recipe.api.enums;

import java.util.Optional;

/**
 * The search which contains the data filtering operations.
 */
public enum RecipeSearch {
  CONTAINS,
  DOES_NOT_CONTAIN,
  EQUAL,
  NOT_EQUAL;

  public static Optional<RecipeSearch> getOperation(final String input) {
    String lowerInput = input.toLowerCase();
      return switch (lowerInput) {
          case "cn" -> Optional.of(CONTAINS);
          case "nc" -> Optional.of(DOES_NOT_CONTAIN);
          case "eq" -> Optional.of(EQUAL);
          case "ne" -> Optional.of(NOT_EQUAL);
          default -> Optional.empty();
      };
  }
}
