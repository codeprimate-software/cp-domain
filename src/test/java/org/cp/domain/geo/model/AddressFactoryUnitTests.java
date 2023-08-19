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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import org.junit.jupiter.api.Test;

import org.cp.domain.geo.enums.Country;
import org.cp.domain.geo.model.usa.UnitedStatesAddress;

/**
 * Unit Tests for {@link AddressFactory}.
 *
 * @author John Blum
 * @see org.junit.jupiter.api.Test
 * @see org.mockito.Mockito
 * @see org.cp.domain.geo.model.AddressFactory
 * @see org.cp.domain.geo.model.BaseAddressUnitTests
 * @since 0.1.0
 */
public class AddressFactoryUnitTests extends BaseAddressUnitTests {

  @Test
  void newAddressInLocalCountry() {

    Street mockStreet = mock(Street.class);
    City mockCity = mockCity("Portland");
    PostalCode mockPostalCode = mockPostalCode("97205");

    Address address = AddressFactory.getInstance().newAddress(mockStreet, mockCity, mockPostalCode);

    assertAddress(address, mockStreet, toLocalCity(mockCity, mockPostalCode), toLocalPostalCode(mockPostalCode));

    verify(mockCity, atLeastOnce()).getName();
    verify(mockPostalCode, atLeast(2)).getNumber();
    verifyNoMoreInteractions(mockPostalCode);
    verifyNoInteractions(mockStreet);
  }

  @Test
  void newAddressInGermany() {

    Street mockStreet = mock(Street.class);
    City mockCity = mock(City.class);
    PostalCode mockPostalCode = mock(PostalCode.class);

    Address address = AddressFactory.getInstance(Country.GERMANY)
      .newAddress(mockStreet, mockCity, mockPostalCode, Country.GERMANY);

    assertAddress(address, mockStreet, mockCity, mockPostalCode, Country.GERMANY);

    verifyNoInteractions(mockStreet, mockCity, mockPostalCode);
  }

  @Test
  void newAddressBuilderInGermany() {

    Address.Builder<?> addressBuilder = AddressFactory.getInstance(Country.GERMANY)
      .newAddressBuilder(Country.GERMANY);

    assertThat(addressBuilder).isNotNull();
    assertThat(addressBuilder.getCountry()).isEqualTo(Country.GERMANY);
  }

  @Test
  void newAddressBuilderInLocalCountry() {

    Address.Builder<?> addressBuilder = AddressFactory.getInstance().newAddressBuilder();

    assertThat(addressBuilder).isNotNull();
    assertThat(addressBuilder.getCountry()).isEqualTo(Country.localCountry());
  }

  @Test
  void newAddressBuilderInUnitedStates() {

    Address.Builder<?> addressBuilder = AddressFactory.getInstance(Country.UNITED_STATES_OF_AMERICA)
      .newAddressBuilder(Country.UNITED_STATES_OF_AMERICA);

    assertThat(addressBuilder).isInstanceOf(UnitedStatesAddress.Builder.class);
    assertThat(addressBuilder.getCountry()).isEqualTo(Country.UNITED_STATES_OF_AMERICA);
  }
}
