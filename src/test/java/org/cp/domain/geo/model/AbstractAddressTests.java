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

package org.cp.domain.geo.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.cp.domain.geo.enums.Country;
import org.cp.domain.geo.model.generic.GenericAddress;
import org.cp.domain.geo.model.usa.UnitedStatesAddress;
import org.junit.Test;

/**
 * Unit tests for {@link AbstractAddress}.
 *
 * @author John Blum
 * @see org.junit.Test
 * @see org.mockito.Mockito
 * @see org.cp.domain.geo.model.AbstractAddress
 * @since 1.0.0
 */
public class AbstractAddressTests {

  @Test
  public void newAddressWithNullCountry() {

    Address address = AbstractAddress.newAddress(null);

    assertThat(address).isNotNull();
    assertThat(address.getCountry()).isNull();
  }

  @Test
  public void newGenericAddress() {

    Address address = AbstractAddress.newAddress(Country.UNITED_KINGDOM);

    assertThat(address).isInstanceOf(GenericAddress.class);
    assertThat(address.getCountry()).isEqualTo(Country.UNITED_KINGDOM);
  }

  @Test
  public void newLocalCountryAddress() {

    Address address = AbstractAddress.newAddress();

    assertThat(address).isNotNull();
    assertThat(address.getCountry()).isEqualTo(Country.localCountry());

    Optional.of(address)
      .map(Address::getCountry)
      .filter(Country.UNITED_STATES_OF_AMERICA::equals)
      .ifPresent(it -> assertThat(address).isInstanceOf(UnitedStatesAddress.class));
  }

  @Test
  public void newUnitedStatesAddress() {

    Address address = AbstractAddress.newAddress(Country.UNITED_STATES_OF_AMERICA);

    assertThat(address).isInstanceOf(UnitedStatesAddress.class);
    assertThat(address.getCountry()).isEqualTo(Country.UNITED_STATES_OF_AMERICA);
  }

  @Test
  public void ofSreetCityPostalCodeAndCountry() {

    City city = City.of("Portland");
    PostalCode postalCode = PostalCode.of("97205");
    Street street = Street.of(100, "Main").as(Street.Type.STREET);

    Address address = AbstractAddress.of(street, city, postalCode, Country.UNITED_STATES_OF_AMERICA);

    assertThat(address).isNotNull();
    assertThat(address.getStreet()).isEqualTo(street);
    assertThat(address.getUnit().isPresent()).isFalse();
    assertThat(address.getCity()).isEqualTo(city);
    assertThat(address.getPostalCode()).isEqualTo(postalCode);
    assertThat(address.getCountry()).isEqualTo(Country.UNITED_STATES_OF_AMERICA);
    assertThat(address.getType().orElse(null)).isEqualTo(Address.Type.UNKNOWN);
  }

  @Test
  public void fromAddress() {

    City city = City.of("Portland");
    Coordinates coordinates = Coordinates.of(101.1d, 202.2d);
    PostalCode postalCode = PostalCode.of("12345");
    Street street = Street.of(100, "One").as(Street.Type.WAY);
    Unit unit = Unit.of("A100").as(Unit.Type.APARTMENT);

    Address mockAddress = mock(Address.class);

    when(mockAddress.getStreet()).thenReturn(street);
    when(mockAddress.getUnit()).thenReturn(Optional.of(unit));
    when(mockAddress.getCity()).thenReturn(city);
    when(mockAddress.getPostalCode()).thenReturn(postalCode);
    when(mockAddress.getCountry()).thenReturn(Country.UNITED_STATES_OF_AMERICA);
    when(mockAddress.getCoordinates()).thenReturn(Optional.of(coordinates));
    when(mockAddress.getType()).thenReturn(Optional.of(Address.Type.HOME));

    Address address = AbstractAddress.from(mockAddress);

    assertThat(address).isNotNull();
    assertThat(address.getStreet()).isEqualTo(street);
    assertThat(address.getUnit().orElse(null)).isEqualTo(unit);
    assertThat(address.getCity()).isEqualTo(city);
    assertThat(address.getPostalCode()).isEqualTo(postalCode);
    assertThat(address.getCountry()).isEqualTo(Country.UNITED_STATES_OF_AMERICA);
    assertThat(address.getCoordinates().orElse(null)).isEqualTo(coordinates);
    assertThat(address.getType().orElse(null)).isEqualTo(Address.Type.HOME);

    verify(mockAddress, times(1)).getStreet();
    verify(mockAddress, times(1)).getUnit();
    verify(mockAddress, times(1)).getCity();
    verify(mockAddress, times(1)).getPostalCode();
    verify(mockAddress, times(1)).getCountry();
    verify(mockAddress, times(1)).getCoordinates();
    verify(mockAddress, times(1)).getType();
    verifyNoMoreInteractions(mockAddress);
  }

  @Test(expected = IllegalArgumentException.class)
  public void fromNullAddressThrowsIllegalArgumentException() {

    try {
      AbstractAddress.from(null);
    }
    catch (IllegalArgumentException expected) {

      assertThat(expected).hasMessage("Address is required");
      assertThat(expected).hasNoCause();

      throw expected;
    }
  }

  @Test
  public void setAndGetId() {

    Address address = AbstractAddress.newAddress();

    assertThat(address).isNotNull();
    assertThat(address.getId()).isNull();
    assertThat(address.isNew()).isTrue();

    address.setId(1L);

    assertThat(address.getId()).isEqualTo(1L);
    assertThat(address.isNew()).isFalse();
    assertThat(address.<Address>identifiedBy(2L)).isSameAs(address);
    assertThat(address.getId()).isEqualTo(2L);
    assertThat(address.isNew()).isFalse();

    address.setId(null);

    assertThat(address.getId()).isNull();
    assertThat(address.isNew()).isTrue();
  }

  @Test
  public void setAndGetStreet() {

    Address address = AbstractAddress.newAddress();

    Street streetOne = Street.of(100, "Main").as(Street.Type.STREET);
    Street streetTwo = Street.of(200, "One").as(Street.Type.WAY);

    address.setStreet(streetOne);

    assertThat(address.getStreet()).isEqualTo(streetOne);
    assertThat(address.<Address>on(streetTwo)).isSameAs(address);
    assertThat(address.getStreet()).isEqualTo(streetTwo);
  }

  @Test(expected = IllegalArgumentException.class)
  public void setStreetToNullThrowsIllegalArgumentException() {

    try {
      AbstractAddress.newAddress().setStreet(null);
    }
    catch (IllegalArgumentException expected) {

      assertThat(expected).hasMessage("Street is required");
      assertThat(expected).hasNoCause();

      throw expected;
    }
  }

  @Test
  public void setAndGetUnit() {

    Address address = AbstractAddress.newAddress();

    Unit apartment = Unit.of("A100").as(Unit.Type.APARTMENT);
    Unit suite = Unit.of("2100").as(Unit.Type.SUITE);

    assertThat(address).isNotNull();
    assertThat(address.getUnit().orElse(null)).isNull();

    address.setUnit(apartment);

    assertThat(address.getUnit().orElse(null)).isEqualTo(apartment);
    assertThat(address.<Address>in(suite)).isSameAs(address);
    assertThat(address.getUnit().orElse(null)).isEqualTo(suite);

    address.setUnit(null);

    assertThat(address.getUnit().isPresent()).isFalse();
  }
}
