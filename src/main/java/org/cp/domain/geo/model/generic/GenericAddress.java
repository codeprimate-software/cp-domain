/*
 * Copyright 2016 Author or Authors.
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
package org.cp.domain.geo.model.generic;

import org.cp.domain.geo.enums.Country;
import org.cp.domain.geo.model.AbstractAddress;
import org.cp.domain.geo.model.Address;
import org.cp.domain.geo.model.City;
import org.cp.domain.geo.model.PostalCode;
import org.cp.domain.geo.model.Street;
import org.cp.elements.lang.annotation.Alias;
import org.cp.elements.lang.annotation.NotNull;

/**
 * {@link Address} implementation modeling physical, postal addresses all around the world,
 * regardless of {@link Country}.
 *
 * @author John Blum
 * @see org.cp.domain.geo.model.AbstractAddress
 * @see org.cp.domain.geo.model.Address
 * @see org.cp.domain.geo.model.Street
 * @see org.cp.domain.geo.model.Unit
 * @see org.cp.domain.geo.model.City
 * @see org.cp.domain.geo.model.PostalCode
 * @see org.cp.domain.geo.enums.Country
 * @since 0.1.0
 */
public class GenericAddress extends AbstractAddress {

  /**
   * Factory method used to construct a new instance of {@link GenericAddress.Builder} to build a {@link GenericAddress}
   * based in the {@link Country#localCountry() local Country} determined by the current,
   * default {@link java.util.Locale}.
   *
   * @return a new {@link GenericAddress.Builder} used to build and initialize a new {@link GenericAddress}.
   * @see #newGenericAddress(Country)
   * @see GenericAddress.Builder
   */
  public static @NotNull GenericAddress.Builder newGenericAddress() {
    return newGenericAddress(Country.localCountry());
  }

  /**
   * Factory method used to construct a new instance of {@link GenericAddress.Builder} to build a {@link GenericAddress}
   * based in the given, required {@link Country}.
   *
   * @param country {@link Country} in which the {@link GenericAddress} will be located; must not be {@literal null}.
   * @return a new {@link GenericAddress.Builder} used to build and initialize a new {@link GenericAddress}.
   * @throws IllegalArgumentException if the given {@link Country} is {@literal null}.
   * @see org.cp.domain.geo.enums.Country
   * @see GenericAddress.Builder
   */
  public static @NotNull GenericAddress.Builder newGenericAddress(@NotNull Country country) {
    return new GenericAddress.Builder(country);
  }

  /**
   * Constructs a new instance of {@link GenericAddress} initialized with the given, required {@link Street},
   * {@link City} and {@link PostalCode}.
   *
   * @param street {@link Street} of the {@link Address}; must not be {@literal null}.
   * @param city {@link City} of the {@link Address}; must not be {@literal null}.
   * @param postalCode {@link PostalCode} of the {@link Address}; must not be {@literal null}.
   * @throws IllegalArgumentException if {@link Street}, {@link City} or {@link PostalCode} are {@literal null}.
   * @see org.cp.domain.geo.enums.Country#localCountry()
   * @see org.cp.domain.geo.model.Street
   * @see org.cp.domain.geo.model.City
   * @see org.cp.domain.geo.model.PostalCode
   */
  @SuppressWarnings("unused")
  protected GenericAddress(@NotNull Street street, @NotNull City city, @NotNull PostalCode postalCode) {
    super(street, city, postalCode);
  }

  /**
   * Constructs a new {@link GenericAddress} initialized with the given, required {@link Street}, {@link City},
   * {@link PostalCode} and {@link Country}.
   *
   * @param street {@link Street} of the {@link Address}; must not be {@literal null}.
   * @param city {@link City} of the {@link Address}; must not be {@literal null}.
   * @param postalCode {@link PostalCode} of the {@link Address}; must not be {@literal null}.
   * @param country {@link Country} of the {@link Address}; must not be {@literal null}.
   * @throws IllegalArgumentException if {@link Street}, {@link City}, {@link PostalCode} or {@link Country}
   * are {@literal null}.
   * @see org.cp.domain.geo.enums.Country
   * @see org.cp.domain.geo.model.Street
   * @see org.cp.domain.geo.model.City
   * @see org.cp.domain.geo.model.PostalCode
   */
  protected GenericAddress(@NotNull Street street, @NotNull City city, @NotNull PostalCode postalCode,
      @NotNull Country country) {

    super(street, city, postalCode, country);
  }

  /**
   * {@link AbstractAddress.Builder} used to build a new {@link GenericAddress}.
   *
   * @see org.cp.domain.geo.model.AbstractAddress.Builder
   * @see org.cp.domain.geo.model.generic.GenericAddress
   */
  public static class Builder extends AbstractAddress.Builder<GenericAddress> {

    /**
     * Constructs a new instance of {@link GenericAddress.Builder} to build a new {@link GenericAddress}
     * initialized with the given, required {@link Country} of origin.
     *
     * @param country {@link Country} of origin for the new {@link GenericAddress}; must not be {@literal null}
     * @throws IllegalArgumentException if the given {@link Country} is {@literal null}.
     */
    public Builder(@NotNull Country country) {
      super(country);
    }

    @Override
    @Alias(forMember = "AbstractAddress.Builder.getCountry()")
    protected Country getCountry() {
      return super.getCountry();
    }

    @Override
    public @NotNull GenericAddress doBuild() {
      return new GenericAddress(getStreet(), getCity(), getPostalCode(), getCountry());
    }
  }
}
