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
package org.cp.domain.geo.model;

import java.util.Locale;

import org.cp.domain.geo.enums.Country;
import org.cp.domain.geo.model.generic.GenericAddress;
import org.cp.elements.lang.annotation.NotNull;
import org.cp.elements.service.ServiceUnavailableException;

/**
 * Factory for {@link Address Addresses}.
 *
 * @author John Blum
 * @see org.cp.domain.geo.model.Address
 * @see org.cp.domain.geo.model.AddressBuilderServiceLoader
 * @since 0.1.0
 */
@SuppressWarnings("unused")
abstract class AddressFactory {

  /**
   * Constructs a new {@link Address} on the given {@link Street}, located in the given {@link City}, {@link PostalCode}
   * and {@link Country} based on {@link Locale}.
   *
   * @param <T> concrete {@link Class type} of {@link Address}.
   * @param street {@link Street} of the {@link Address}; must not be {@literal null}.
   * @param city {@link City} of the {@link Address}; must not be {@literal null}.
   * @param postalCode {@link PostalCode} of the {@link Address}; must not be {@literal null}.
   * @return a new {@link Address} on the given {@link Street}, in the given {@link City} and {@link PostalCode},
   * located in the {@link Country local country}.
   * @throws IllegalArgumentException if the given {@link Street}, {@link City} or {@link PostalCode}
   * are {@literal null}.
   * @see org.cp.domain.geo.model.Address
   * @see org.cp.domain.geo.model.Street
   * @see org.cp.domain.geo.model.City
   * @see org.cp.domain.geo.model.PostalCode
   */
  @SuppressWarnings("unchecked")
  static <T extends Address> T newAddress(@NotNull Street street, @NotNull City city, @NotNull PostalCode postalCode) {
    return (T) new FactoryAddress(street, city, postalCode);
  }

  /**
   * Constructs a new {@link Address} on the given {@link Street}, located in the given {@link City}, {@link PostalCode}
   * and {@link Country}.
   *
   * @param <T> concrete {@link Class type} of {@link Address}.
   * @param street {@link Street} of the {@link Address}; must not be {@literal null}.
   * @param city {@link City} of the {@link Address}; must not be {@literal null}.
   * @param postalCode {@link PostalCode} of the {@link Address}; must not be {@literal null}.
   * @param country {@link Country} of the {@link Address}; must not be {@literal null}.
   * @return a new {@link Address} on the given {@link Street}, located in the given {@link City}, {@link PostalCode}
   * and {@link Country}.
   * @throws IllegalArgumentException if the given {@link Street}, {@link City}, {@link PostalCode} or {@link Country}
   * are {@literal null}.
   * @see org.cp.domain.geo.model.Address
   * @see org.cp.domain.geo.model.Street
   * @see org.cp.domain.geo.model.City
   * @see org.cp.domain.geo.model.PostalCode
   * @see org.cp.domain.geo.enums.Country
   */
  @SuppressWarnings("unchecked")
  static <T extends Address> T newAddress(@NotNull Street street, @NotNull City city, @NotNull PostalCode postalCode,
      @NotNull Country country) {

    return (T) new FactoryAddress(street, city, postalCode, country);
  }

  /**
   * Constructs a new {@link Address.Builder} used to build an {@link Address} located in the given {@link Country}.
   *
   * @param <T> {@link Class Type} of {@link Address}.
   * @param <BUILDER> {@link Class Type} of {@link Address.Builder}.
   * @param country {@link Country} in which the new {@link Address} is located.
   * @return a new {@link Address.Builder}.
   * @see org.cp.domain.geo.enums.Country
   * @see org.cp.domain.geo.model.Address
   * @see org.cp.domain.geo.model.Address.Builder
   * @see org.cp.domain.geo.model.AddressBuilderServiceLoader
   * @see #newGenericAddressBuilder(Country)
   */
  @SuppressWarnings("unchecked")
  static @NotNull <T extends Address, BUILDER extends Address.Builder<T>> BUILDER newAddressBuilder(
      @NotNull Country country) {

    try {
      return (BUILDER) AddressBuilderServiceLoader.INSTANCE.getServiceInstance(country);
    }
    catch (ServiceUnavailableException ignore) {
      return newGenericAddressBuilder(country);
    }
  }

  /**
   * Constructs a new, generic {@link Address.Builder} used to build an {@link Address}
   * located in the given {@link Country}.
   *
   * @param <T> {@link Class Type} of {@link Address}.
   * @param <BUILDER> {@link Class Type} of {@link Address.Builder}.
   * @param country {@link Country} in which the new {@link Address} is located.
   * @return a new, generic {@link Address.Builder}.
   * @see org.cp.domain.geo.enums.Country
   * @see org.cp.domain.geo.model.Address
   * @see org.cp.domain.geo.model.Address.Builder
   */
  @SuppressWarnings("unchecked")
  static @NotNull <T extends Address, BUILDER extends Address.Builder<T>> BUILDER newGenericAddressBuilder(
      @NotNull Country country) {

    return (BUILDER) GenericAddress.newGenericAddressBuilder(country);
  }

  static class FactoryAddress extends AbstractAddress {

    FactoryAddress(Street street, City city, PostalCode postalCode) {
      super(street, city, postalCode);
    }

    FactoryAddress(Street street, City city, PostalCode postalCode, Country country) {
      super(street, city, postalCode, country);
    }
  }
}
