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
package org.cp.domain.geo.model.generic;

import org.cp.domain.geo.annotation.CountryQualifier;
import org.cp.domain.geo.enums.Country;
import org.cp.domain.geo.model.AddressFactory;
import org.cp.domain.geo.model.City;
import org.cp.domain.geo.model.PostalCode;
import org.cp.domain.geo.model.Street;
import org.cp.elements.lang.annotation.NotNull;

/**
 * {@link AddressFactory} implementation used to construct and build new instances of {@link GenericAddress}.
 *
 * @author John Blum
 * @see org.cp.domain.geo.annotation.CountryQualifier
 * @see org.cp.domain.geo.enums.Country#UNKNOWN
 * @see AddressFactory
 * @see org.cp.domain.geo.model.Address
 * @see org.cp.domain.geo.model.Address.Builder
 * @see org.cp.domain.geo.model.generic.GenericAddress
 * @see org.cp.domain.geo.model.generic.GenericAddress.Builder
 * @since 0.1.0
 */
@CountryQualifier(Country.UNKNOWN)
public class GenericAddressFactory extends AddressFactory<GenericAddress> {

  @Override
  public @NotNull GenericAddress newAddress(@NotNull Street street,
      @NotNull City city, @NotNull PostalCode postalCode) {

    return new GenericAddress(street, city, postalCode);
  }

  @Override
  public @NotNull GenericAddress newAddress(@NotNull Street street,
      @NotNull City city, @NotNull PostalCode postalCode, @NotNull Country country) {

    return new GenericAddress(street, city, postalCode, country);
  }

  @Override
  @SuppressWarnings("unchecked")
  public GenericAddress.Builder newAddressBuilder() {
    return GenericAddress.newGenericAddressBuilder();
  }

  @Override
  @SuppressWarnings("unchecked")
  public @NotNull GenericAddress.Builder newAddressBuilder(@NotNull Country country) {
    return GenericAddress.newGenericAddressBuilder(country);
  }
}
