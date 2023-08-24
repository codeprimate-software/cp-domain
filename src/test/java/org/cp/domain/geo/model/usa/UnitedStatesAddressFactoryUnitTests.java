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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import org.junit.jupiter.api.Test;

import org.cp.domain.geo.enums.Country;
import org.cp.domain.geo.model.BaseAddressUnitTests;
import org.cp.domain.geo.model.City;
import org.cp.domain.geo.model.PostalCode;
import org.cp.domain.geo.model.Street;
import org.testcontainers.shaded.org.apache.commons.lang3.function.TriFunction;

/**
 * Unit Tests for {@link UnitedStatesAddressFactory}.
 *
 * @author John Blum
 * @see org.junit.jupiter.api.Test
 * @see org.cp.domain.geo.model.BaseAddressUnitTests
 * @see org.cp.domain.geo.model.usa.UnitedStatesAddressFactory
 * @since 0.1.0
 */
public class UnitedStatesAddressFactoryUnitTests extends BaseAddressUnitTests {

  private final UnitedStatesAddressFactory addressFactory = new UnitedStatesAddressFactory();

  private void testNewAddress(TriFunction<Street, City, PostalCode, UnitedStatesAddress> newAddressFunction) {

    Street mockStreet = mock(Street.class);
    City mockCity = mockCity("Portland");
    PostalCode mockPostalCode = mockPostalCode("97205");

    UnitedStatesAddress address = newAddressFunction.apply(mockStreet, mockCity, mockPostalCode);

    assertAddress(address, mockStreet, toLocalCity(mockCity, mockPostalCode), toLocalPostalCode(mockPostalCode),
      Country.UNITED_STATES_OF_AMERICA);

    assertAddressWithNoCoordinatesTypeOrUnit(address);

    assertThat(address.getCity()).isInstanceOf(UnitedStatesCity.class);
    assertThat(address.getPostalCode()).isInstanceOf(ZIP.class);

    verify(mockCity, atLeastOnce()).getName();
    verify(mockPostalCode, atLeastOnce()).getNumber();
    verifyNoMoreInteractions(mockCity, mockPostalCode);
    verifyNoInteractions(mockStreet);
  }

  @Test
  void newAddress() {
    testNewAddress(this.addressFactory::newAddress);
  }

  @Test
  void newAddressInGermany() {

    assertThatIllegalArgumentException()
      .isThrownBy(() -> testNewAddress((mockStreet, mockCity, mockPostalCode) ->
        this.addressFactory.newAddress(mockStreet, mockCity, mockPostalCode, Country.GERMANY)))
      .withMessage("Country [GERMANY] must be the United States of America")
      .withNoCause();
  }

  @Test
  void newAddressInUnitedStates() {

    testNewAddress((mockStreet, mockCity, mockPostalCode) ->
      this.addressFactory.newAddress(mockStreet, mockCity, mockPostalCode, Country.UNITED_STATES_OF_AMERICA));
  }

  @Test
  void newAddressBuilder() {

    UnitedStatesAddress.Builder addressBuilder = this.addressFactory.newAddressBuilder();

    assertThat(addressBuilder).isNotNull();
    assertThat(addressBuilder.getCountry()).isEqualTo(Country.UNITED_STATES_OF_AMERICA);
  }

  @Test
  void newAddressBuilderInEgypt() {

    assertThatIllegalArgumentException()
      .isThrownBy(() -> this.addressFactory.newAddressBuilder(Country.EGYPT))
      .withMessage("Country [EGYPT] must be the United States of America")
      .withNoCause();
  }

  @Test
  void newAddressBuilderInUnitedStates() {

    UnitedStatesAddress.Builder addressBuilder = this.addressFactory.newAddressBuilder(Country.UNITED_STATES_OF_AMERICA);

    assertThat(addressBuilder).isNotNull();
    assertThat(addressBuilder.getCountry()).isEqualTo(Country.UNITED_STATES_OF_AMERICA);
  }
}
