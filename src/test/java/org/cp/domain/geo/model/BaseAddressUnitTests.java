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
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.util.Optional;

import org.cp.domain.geo.enums.Country;
import org.cp.domain.geo.model.usa.UnitedStatesCity;
import org.cp.domain.geo.model.usa.ZIP;
import org.cp.domain.geo.model.usa.support.StateZipCodesRepository;

/**
 * Abstract base class for all {@link Address} Unit Tests.
 *
 * @author John Blum
 * @see java.util.Locale
 * @see org.junit.jupiter.api.Test
 * @see org.cp.domain.geo.model.Address
 * @see org.cp.domain.geo.enums.Country
 * @since 0.1.0
 */
@SuppressWarnings("unused")
public abstract class BaseAddressUnitTests {

  protected void assertAddress(Address actual, Address expected) {

    assertThat(actual).isNotNull();
    assertThat(actual).isNotSameAs(expected);
    assertThat(actual).isEqualTo(expected);
    assertThat(actual.getCoordinates().orElse(null)).isEqualTo(expected.getCoordinates().orElse(null));
    assertThat(actual.getType().orElse(Address.Type.UNKNOWN)).isEqualTo(expected.getType().orElse(Address.Type.UNKNOWN));
    assertThat(actual.getUnit().orElse(Unit.EMPTY)).isEqualTo(expected.getUnit().orElse(Unit.EMPTY));
  }

  protected void assertAddress(Address address, Street street, City city, PostalCode postalCode) {
    assertAddress(address, street, city, postalCode, Country.localCountry());
  }

  protected void assertAddress(Address address, Street street, City city, PostalCode postalCode, Country country) {

    assertThat(address).isNotNull();
    assertThat(address.getStreet()).isEqualTo(street);
    assertThat(address.getCity()).isEqualTo(city);
    assertThat(address.getPostalCode()).isEqualTo(postalCode);
    assertThat(address.getCountry()).isEqualTo(country);
  }

  protected void assertAddressWithCoordinatesTypeAndUnit(Address address,
      Coordinates coordinates, Address.Type addressType, Unit unit) {

    assertThat(address).isNotNull();
    assertThat(address.getCoordinates().orElse(null)).isEqualTo(coordinates);
    assertThat(address.getType().orElse(null)).isEqualTo(addressType);
    assertThat(address.getUnit().orElse(null)).isEqualTo(unit);
  }

  protected void assertAddressWithNoCoordinatesTypeOrUnit(Address address) {

    assertThat(address).isNotNull();
    assertThat(address.getCoordinates()).isNotPresent();
    assertThat(address.getType()).isNotPresent();
    assertThat(address.getUnit()).isNotPresent();
  }

  protected Address mockAddress(Street street, City city, PostalCode postalCode) {
    return mockAddress(street, city, postalCode, Country.localCountry());
  }

  protected Address mockAddress(Street street, City city, PostalCode postalCode, Country country) {

    Address mockAddress = mock(Address.class);

    doReturn(street).when(mockAddress).getStreet();
    doReturn(city).when(mockAddress).getCity();
    doReturn(postalCode).when(mockAddress).getPostalCode();
    doReturn(country).when(mockAddress).getCountry();

    return mockAddress;
  }

  protected City mockCity(String name) {

    City mockCity = mock(City.class);

    doReturn(name).when(mockCity).getName();
    doReturn(Optional.of(Country.localCountry())).when(mockCity).getCountry();

    return mockCity;
  }

  protected PostalCode mockPostalCode(String number) {

    PostalCode mockPostalCode = mock(PostalCode.class);

    doReturn(number).when(mockPostalCode).getNumber();

    return mockPostalCode;
  }

  protected City toLocalCity(City city, PostalCode postalCode) {
    return Country.UNITED_STATES_OF_AMERICA.equals(Country.localCountry())
      ? UnitedStatesCity.from(city).in(StateZipCodesRepository.getInstance().findBy(postalCode))
      : city;
  }

  protected PostalCode toLocalPostalCode(PostalCode postalCode) {
    return Country.UNITED_STATES_OF_AMERICA.equals(Country.localCountry())
      ? ZIP.from(postalCode)
      : postalCode;
  }

  protected Address withCoordinates(Address address, Coordinates coordinates) {
    doReturn(Optional.ofNullable(coordinates)).when(address).getCoordinates();
    return address;
  }

  protected Address withType(Address address, Address.Type addressType) {
    doReturn(Optional.ofNullable(addressType)).when(address).getType();
    return address;
  }

  protected Address withUnit(Address address, Unit unit) {
    doReturn(Optional.ofNullable(unit)).when(address).getUnit();
    return address;
  }
}
