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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoInteractions;

import org.junit.jupiter.api.Test;

import org.cp.domain.geo.enums.Country;
import org.cp.domain.geo.model.generic.GenericAddress;
import org.cp.domain.geo.model.usa.UnitedStatesAddress;

/**
 * Unit Tests for {@link AddressFactory}.
 *
 * @author John Blum
 * @see org.junit.jupiter.api.Test
 * @see org.mockito.Mockito
 * @see org.cp.domain.geo.model.AddressFactory
 * @since 0.1.0
 */
public class AddressFactoryUnitTests {

  private void assertAddress(Address address, Street street, City city, PostalCode postalCode) {
    assertAddress(address, street, city, postalCode, Country.localCountry());
  }

  private void assertAddress(Address address, Street street, City city, PostalCode postalCode, Country country) {

    assertThat(address).isInstanceOf(AbstractAddress.class);
    assertThat(address.getStreet()).isEqualTo(street);
    assertThat(address.getCity()).isEqualTo(city);
    assertThat(address.getPostalCode()).isEqualTo(postalCode);
    assertThat(address.getCountry()).isEqualTo(country);
    assertThat(address.getCoordinates()).isNotPresent();
    assertThat(address.getType()).isNotPresent();
    assertThat(address.getUnit()).isNotPresent();
  }

  @Test
  public void newAddressInLocalCountry() {

    Street mockStreet = mock(Street.class);
    City mockCity = mock(City.class);
    PostalCode mockPostalCode = mock(PostalCode.class);

    Address address = AddressFactory.newAddress(mockStreet, mockCity, mockPostalCode);

    assertAddress(address, mockStreet, mockCity, mockPostalCode);

    verifyNoInteractions(mockStreet, mockCity, mockPostalCode);
  }

  @Test
  public void newAddressInGivenCountry() {

    Street mockStreet = mock(Street.class);
    City mockCity = mock(City.class);
    PostalCode mockPostalCode = mock(PostalCode.class);

    Address address = AddressFactory.newAddress(mockStreet, mockCity, mockPostalCode, Country.GERMANY);

    assertAddress(address, mockStreet, mockCity, mockPostalCode, Country.GERMANY);

    verifyNoInteractions(mockStreet, mockCity, mockPostalCode);
  }

  @Test
  public void newAddressBuilderInAustria() {

    Address.Builder<?> addressBuilder = AddressFactory.newAddressBuilder(Country.AUSTRIA);

    assertThat(addressBuilder).isInstanceOf(GenericAddress.Builder.class);
    assertThat(addressBuilder.getCountry()).isEqualTo(Country.AUSTRIA);
  }

  @Test
  public void newAddressBuilderInUnitedStates() {

    Address.Builder<?> addressBuilder = AddressFactory.newAddressBuilder(Country.UNITED_STATES_OF_AMERICA);

    assertThat(addressBuilder).isInstanceOf(UnitedStatesAddress.Builder.class);
    assertThat(addressBuilder.getCountry()).isEqualTo(Country.UNITED_STATES_OF_AMERICA);
  }

  @Test
  public void newGenericAddressBuilderIsCorrect() {

    Address.Builder<?> addressBuilder = AddressFactory.newGenericAddressBuilder(Country.EGYPT);

    assertThat(addressBuilder).isInstanceOf(GenericAddress.Builder.class);
    assertThat(addressBuilder.getCountry()).isEqualTo(Country.EGYPT);
  }
}
