/*
 * Copyright 2017-Present Author or Authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.cp.domain.geo.util;

import java.util.function.Supplier;

import org.cp.domain.geo.enums.Country;
import org.cp.elements.lang.annotation.NotNull;
import org.cp.elements.lang.annotation.Nullable;

/**
 * Abstract utility class used to process geographic data and types.
 *
 * @author John Blum
 * @since 0.1.0
 */
public abstract class GeoUtils {

  /**
   * Resolves {@link Country} to {@link Country#localCountry() local Country} if {@literal null}.
   *
   * @param country {@link Country} to evaluate.
   * @return the given {@link Country} if not {@literal null} or {@link Country#localCountry()}.
   * @see org.cp.domain.geo.enums.Country
   */
  public static @NotNull Country resolveCountry(@Nullable Country country) {
    return resolveCountry(country, Country::localCountry);
  }

  /**
   * Resolves {@link Country} to {@link Country#UNKNOWN} if {@literal null}.
   *
   * @param country {@link Country} to evaluate.
   * @return the given {@link Country} if not {@literal null} or {@link Country#UNKNOWN}.
   * @see org.cp.domain.geo.enums.Country
   */
  public static @NotNull Country resolveToUnknownCountry(@Nullable Country country) {
    return resolveCountry(country, () -> Country.UNKNOWN);
  }

  private static @NotNull Country resolveCountry(@Nullable Country country, Supplier<Country> countrySupplier) {
    return country != null ? country : countrySupplier.get();
  }
}
