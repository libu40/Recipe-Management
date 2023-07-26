package com.recipe.api.enums;

import java.util.Optional;

/**
 * The enum which filters based on the data filtering.
 */
public enum DataOption {
  ANY,
  ALL;

  public static Optional<DataOption> getDataOption(final String dataOption) {
    String lowerDataOption = dataOption.toLowerCase();
      return switch (lowerDataOption) {
          case "all" -> Optional.of(ALL);
          case "any" -> Optional.of(ANY);
          default -> Optional.empty();
      };
  }
}
