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
    switch (lowerDataOption) {
      case "all":
        return Optional.of(ALL);
      case "any":
        return Optional.of(ANY);
    }
    return Optional.empty();
  }
}
