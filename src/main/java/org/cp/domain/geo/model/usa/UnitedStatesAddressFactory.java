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
package org.cp.domain.geo.model.usa;

import org.cp.domain.geo.annotation.CountryQualifier;
import org.cp.domain.geo.enums.Country;
import org.cp.domain.geo.enums.State;
import org.cp.domain.geo.model.AddressFactory;
import org.cp.domain.geo.model.City;
import org.cp.domain.geo.model.PostalCode;
import org.cp.domain.geo.model.Street;
import org.cp.domain.geo.model.usa.support.StateZipCodesRepository;
import org.cp.elements.lang.Assert;
import org.cp.elements.lang.annotation.NotNull;

/**
 * {@link AddressFactory} implementation used to construct and build new instances of
 * {@link UnitedStatesAddress}.
 *
 * @author John Blum
 * @see org.cp.domain.geo.annotation.CountryQualifier
 * @see org.cp.domain.geo.enums.Country#UNITED_STATES_OF_AMERICA
 * @see AddressFactory
 * @see org.cp.domain.geo.model.Address
 * @see org.cp.domain.geo.model.Address.Builder
 * @see org.cp.domain.geo.model.usa.UnitedStatesAddress
 * @see org.cp.domain.geo.model.usa.UnitedStatesAddress.Builder
 * @since 0.1.0
 */
@CountryQualifier(Country.UNITED_STATES_OF_AMERICA)
public class UnitedStatesAddressFactory extends AddressFactory<UnitedStatesAddress> {

  @Override
  public @NotNull UnitedStatesAddress newAddress(@NotNull Street street, @NotNull City city,
      @NotNull PostalCode postalCode) {

    return newAddress(street, city, postalCode, Country.UNITED_STATES_OF_AMERICA);
  }

  @Override
  public @NotNull UnitedStatesAddress newAddress(@NotNull Street street,
      @NotNull City city, @NotNull PostalCode postalCode, Country country) {

    assertCountryIsNullOrUnitedStates(country);

    return new UnitedStatesAddress(street, city, resolveState(postalCode), newZip(postalCode));
  }

  private void assertCountryIsNullOrUnitedStates(Country country) {
    Assert.argument(country, it -> it == null || Country.UNITED_STATES_OF_AMERICA.equals(it),
      () -> String.format("Country [%s] must be the United States of America", country));
  }

  private ZIP newZip(PostalCode postalCode) {
    return ZIP.from(postalCode);
  }

  private State resolveState(PostalCode postalCode) {
    return StateZipCodesRepository.getInstance().findBy(postalCode);
  }

  @Override
  @SuppressWarnings("unchecked")
  public UnitedStatesAddress.Builder newAddressBuilder() {
    return newAddressBuilder(Country.UNITED_STATES_OF_AMERICA);
  }

  @Override
  @SuppressWarnings("unchecked")
  public UnitedStatesAddress.Builder newAddressBuilder(Country country) {
    assertCountryIsNullOrUnitedStates(country);
    return UnitedStatesAddress.newUnitedStatesAddressBuilder();
  }
}
