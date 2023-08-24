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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoInteractions;

import org.junit.jupiter.api.Test;

import org.cp.domain.geo.enums.Country;
import org.cp.domain.geo.model.BaseAddressUnitTests;
import org.cp.domain.geo.model.City;
import org.cp.domain.geo.model.PostalCode;
import org.cp.domain.geo.model.Street;
import org.testcontainers.shaded.org.apache.commons.lang3.function.TriFunction;

/**
 * Unit Tests for {@link GenericAddressFactory}.
 *
 * @author John Blum
 * @see org.junit.jupiter.api.Test
 * @see org.cp.domain.geo.model.BaseAddressUnitTests
 * @see org.cp.domain.geo.model.generic.GenericAddressFactory
 * @since 0.1.0
 */
public class GenericAddressFactoryUnitTests extends BaseAddressUnitTests {

  private final GenericAddressFactory addressFactory = new GenericAddressFactory();

  private void testNewAddress(TriFunction<Street, City, PostalCode, GenericAddress> newAddressFunction, Country country) {

    Street mockStreet = mock(Street.class);
    City mockCity = mock(City.class);
    PostalCode mockPostalCode = mock(PostalCode.class);

    GenericAddress address = newAddressFunction.apply(mockStreet, mockCity, mockPostalCode);

    if (country != null) {
      assertAddress(address, mockStreet, mockCity, mockPostalCode, country);
    }
    else {
      assertAddress(address, mockStreet, mockCity, mockPostalCode);
    }

    assertAddressWithNoCoordinatesTypeOrUnit(address);

    verifyNoInteractions(mockStreet, mockCity, mockPostalCode);
  }

  @Test
  void newAddress() {
    testNewAddress(this.addressFactory::newAddress, null);
  }

  @Test
  void newAddressInGermany() {

    Country germany = Country.GERMANY;

    testNewAddress((mockStreet, mockCity, mockPostalCode) ->
      this.addressFactory.newAddress(mockStreet, mockCity, mockPostalCode, germany), germany);
  }

  @Test
  void newAddressInUnitedStates() {

    Country unitedStates = Country.UNITED_STATES_OF_AMERICA;

    testNewAddress((mockStreet, mockCity, mockPostalCode) ->
      this.addressFactory.newAddress(mockStreet, mockCity, mockPostalCode, unitedStates), unitedStates);
  }

  @Test
  void newAddressBuilder() {

    GenericAddress.Builder addressBuilder = this.addressFactory.newAddressBuilder();

    assertThat(addressBuilder).isNotNull();
    assertThat(addressBuilder.getCountry()).isEqualTo(Country.localCountry());
  }

  @Test
  void newAddressBuilderInEgypt() {

    GenericAddress.Builder addressBuilder = this.addressFactory.newAddressBuilder(Country.EGYPT);

    assertThat(addressBuilder).isNotNull();
    assertThat(addressBuilder.getCountry()).isEqualTo(Country.EGYPT);
  }

  @Test
  void newAddressBuilderInUnitedStates() {

    GenericAddress.Builder addressBuilder = this.addressFactory.newAddressBuilder(Country.UNITED_STATES_OF_AMERICA);

    assertThat(addressBuilder).isNotNull();
    assertThat(addressBuilder.getCountry()).isEqualTo(Country.UNITED_STATES_OF_AMERICA);
  }
}
