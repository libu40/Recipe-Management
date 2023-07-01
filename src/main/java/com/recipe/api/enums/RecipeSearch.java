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
    switch (lowerInput) {
      case "cn":
        return Optional.of(CONTAINS);
      case "nc":
        return Optional.of(DOES_NOT_CONTAIN);
      case "eq":
        return Optional.of(EQUAL);
      case "ne":
        return Optional.of(NOT_EQUAL);
    }
    return Optional.empty();
  }
}
