/*
 * Copyright 2011-Present Author or Authors.
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
package org.cp.domain.geo.support;

import java.util.Optional;

import org.cp.domain.geo.enums.Country;
import org.cp.elements.lang.annotation.Dsl;
import org.cp.elements.lang.annotation.NotNull;

/**
 * Interface for implementing {@link Class domain types} that can declare their {@link Country} of origin.
 *
 * @author John Blum
 * @see java.lang.FunctionalInterface
 * @see org.cp.domain.geo.enums.Country
 * @since 0.1.0
 */
@FunctionalInterface
public interface CountryAware {

  /**
   * Gets the {@link Country} of origin for this implementing {@literal domain type}.
   *
   * Returns {@link Optional#empty()} by default.
   *
   * @return the {@link Country} of origin for this {@literal domain type}.
   * @see org.cp.domain.geo.enums.Country
   * @see java.util.Optional
   */
  default Optional<Country> getCountry() {
    return Optional.empty();
  }

  /**
   * Sets the {@link Country} of origin for this implementing {@literal domain type}.
   *
   * @param country {@link Country} of origin for this implementing {@literal domain type}.
   * @see org.cp.domain.geo.enums.Country
   * @see #inLocalCountry()
   * @see #in(Country)
   * @see #getCountry()
   */
  void setCountry(Country country);

  /**
   * Builder method used to set the {@link Country} of origin for this implementing {@literal domain type}.
   *
   * @param <T> {@link Class type} of {@link Object domain object} implementing this interface.
   * @param country {@link Country} of origin for this implementing {@literal domain type}.
   * @return the instance of this {@literal domain type}.
   * @see org.cp.domain.geo.enums.Country
   * @see #setCountry(Country)
   */
  @Dsl
  @SuppressWarnings("unchecked")
  default @NotNull <T extends CountryAware> T in(Country country) {
    setCountry(country);
    return (T) this;
  }

  /**
   * Builder method used to set the {@link Country} of origin for this implementing {@literal domain type}
   * to the {@link Country#localCountry()}
   *
   * @param <T> {@link Class type} of {@link Object domain object} implementing this interface.
   * @return the instance of this {@literal domain type}.
   * @see #in(Country)
   */
  @Dsl
  default @NotNull <T extends CountryAware> T inLocalCountry() {
    return in(Country.localCountry());
  }
}
