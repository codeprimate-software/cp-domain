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

import java.util.HashSet;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;

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
 * @see org.cp.domain.geo.enums.Country
 * @see org.cp.domain.geo.model.AbstractAddress
 * @see org.cp.domain.geo.model.generic.GenericAddress
 * @see org.cp.domain.geo.model.usa.UnitedStatesAddress
 * @since 1.0.0
 */
public class AbstractAddressTests {

  @Test
  public void newAddressWithNullCountry() {

    Address address = AbstractAddress.newAddress(null);

    assertThat(address).isNotNull();
    assertThat(address.getCountry()).isEqualTo(Country.byIsoThree(Locale.getDefault().getISO3Country()));
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
  public void ofStreetCityPostalCodeAndCountry() {

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
    assertThat(address.getType().isPresent()).isFalse();
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

  @Test
  public void setAndGetCity() {

    Address address = AbstractAddress.newAddress();

    City newYork = City.of("New York");
    City portland = City.of("Portland");

    assertThat(address).isNotNull();
    assertThat(address.getCity()).isNull();

    address.setCity(newYork);

    assertThat(address.getCity()).isEqualTo(newYork);
    assertThat(address.<Address>in(portland)).isSameAs(address);
    assertThat(address.getCity()).isEqualTo(portland);
  }

  @Test(expected = IllegalArgumentException.class)
  public void setCityToNullThrowsIllegalArgumentException() {

    try {
      AbstractAddress.newAddress().setCity(null);
    }
    catch (IllegalArgumentException expected) {

      assertThat(expected).hasMessage("City is required");
      assertThat(expected).hasNoCause();

      throw expected;
    }
  }

  @Test
  public void setAndGetPostalCode() {

    Address address = AbstractAddress.newAddress();

    PostalCode postalCodeOne = PostalCode.of("12345");
    PostalCode postalCodeTwo = PostalCode.of("54321");

    assertThat(address).isNotNull();
    assertThat(address.getPostalCode()).isNull();

    address.setPostalCode(postalCodeOne);

    assertThat(address.getPostalCode()).isEqualTo(postalCodeOne);
    assertThat(address.<Address>in(postalCodeTwo)).isSameAs(address);
    assertThat(address.getPostalCode()).isEqualTo(postalCodeTwo);
  }

  @Test(expected = IllegalArgumentException.class)
  public void setPostalCodeToNullThrowsIllegalArgumentException() {

    try {
      AbstractAddress.newAddress().setPostalCode(null);
    }
    catch (IllegalArgumentException expected) {

      assertThat(expected).hasMessage("Postal Code is required");
      assertThat(expected).hasNoCause();

      throw expected;
    }
  }

  @Test
  public void setAndGetCountry() {

    Address address = AbstractAddress.newAddress(null);

    Country canada = Country.CANADA;
    Country unitedStates = Country.UNITED_STATES_OF_AMERICA;

    assertThat(address).isNotNull();
    assertThat(address.getCountry()).isEqualTo(Country.byIsoThree(Locale.getDefault().getISO3Country()));

    address.setCountry(unitedStates);

    assertThat(address.getCountry()).isEqualTo(unitedStates);
    assertThat(address.<Address>in(canada)).isSameAs(address);
    assertThat(address.getCountry()).isEqualTo(canada);
  }

  @Test(expected = IllegalArgumentException.class)
  public void setCountryToNullThrowsIllegalArgumentException() {

    try {
      AbstractAddress.newAddress().setCountry(null);
    }
    catch (IllegalArgumentException expected) {

      assertThat(expected).hasMessage("Country is required");
      assertThat(expected).hasNoCause();

      throw expected;
    }
  }

  @Test
  public void setAndGetCoordinates() {

    Address address = AbstractAddress.newAddress();

    Coordinates coordinatesOne = Coordinates.of(1.0d, 1.0d);
    Coordinates coordinatesTwo = Coordinates.of(2.0d, 2.0d);

    assertThat(address).isNotNull();
    assertThat(address.getCoordinates().isPresent()).isFalse();

    address.setCoordinates(coordinatesOne);

    assertThat(address.getCoordinates().orElse(null)).isEqualTo(coordinatesOne);
    assertThat(address.<Address>with(coordinatesTwo)).isSameAs(address);
    assertThat(address.getCoordinates().orElse(null)).isEqualTo(coordinatesTwo);

    address.setCoordinates(null);

    assertThat(address.getCoordinates().isPresent()).isFalse();
  }

  @Test
  public void setAndGetType() {

    Address address = AbstractAddress.newAddress();

    assertThat(address).isNotNull();
    assertThat(address.getType().isPresent()).isFalse();

    address.setType(Address.Type.HOME);

    assertThat(address.getType().orElse(null)).isEqualTo(Address.Type.HOME);
    assertThat(address.<Address>as(Address.Type.WORK)).isSameAs(address);
    assertThat(address.getType().orElse(null)).isEqualTo(Address.Type.WORK);

    address.setType(null);

    assertThat(address.getType().isPresent()).isFalse();
  }

  @Test
  public void cloneIsCorrect() throws CloneNotSupportedException {

    Street street = Street.of(100, "One").asWay();
    Unit unit = Unit.apartment("100");
    City city = City.of("Toronto");
    PostalCode postalCode = PostalCode.of("12345");
    Coordinates coordinates = Coordinates.of(1.0d, 2.0d);

    Address address = AbstractAddress.newAddress(Country.CANADA)
      .on(street)
      .in(unit)
      .in(city)
      .in(postalCode)
      .with(coordinates)
      .asPoBox();

    Address addressClone = (Address) address.clone();

    assertThat(addressClone).isNotNull();
    assertThat(addressClone).isNotSameAs(address);
    assertThat(addressClone).isEqualTo(address);
    assertThat(addressClone.getCoordinates()).isEqualTo(address.getCoordinates());
    assertThat(addressClone.getType()).isEqualTo(address.getType());
  }

  @Test
  public void equalsNullReturnsFalse() {

    Address address = AbstractAddress.of(Street.of(100, "One").as(Street.Type.WAY),
      City.of("Portland"), PostalCode.of("123"), Country.UNITED_STATES_OF_AMERICA);

    assertThat(address).isNotEqualTo(null);
  }

  @Test
  public void equalAddressesAreEqual() {

    Street street = Street.of(100, "Main").as(Street.Type.AVENUE);
    Unit unit = Unit.of("S2100").as(Unit.Type.SUITE);
    City city = City.of("Portland");
    PostalCode postalCode = PostalCode.of("97205");
    Country country = Country.CANADA;

    Address addressOne = AbstractAddress.of(street, city, postalCode, country).in(unit).asBilling();
    Address addressTwo = AbstractAddress.of(street, city, postalCode, country).in(unit).asMailing();

    assertThat(addressOne).isNotNull();
    assertThat(addressTwo).isNotNull();
    assertThat(addressOne).isNotSameAs(addressTwo);
    assertThat(addressOne).isEqualTo(addressTwo);
  }

  @Test
  public void identicalAddressAreEqual() {

    Street street = Street.of(100, "Main").as(Street.Type.AVENUE);
    Unit unit = Unit.of("S2100").as(Unit.Type.SUITE);
    City city = City.of("Portland");
    PostalCode postalCode = PostalCode.of("97205");
    Country country = Country.UNITED_STATES_OF_AMERICA;

    Address address = AbstractAddress.of(street, city, postalCode, country).in(unit).asOffice();

    assertThat(address).isNotNull();
    assertThat(address).isSameAs(address);
    assertThat(address).isEqualTo(address);
  }

  @Test
  public void unequalAddressesAreNotEqual() {

    Street street = Street.of(100, "Main").as(Street.Type.AVENUE);
    City city = City.of("Portland");
    PostalCode postalCode = PostalCode.of("97205");

    Address addressOne = AbstractAddress.of(street, city, postalCode, Country.UNITED_STATES_OF_AMERICA)
      .in(Unit.of("A100").as(Unit.Type.APARTMENT))
      .asOffice();

    Address addressTwo = AbstractAddress.of(street, city, postalCode, Country.UNITED_KINGDOM)
      .in(Unit.of("S2100").as(Unit.Type.SUITE))
      .asWork();

    assertThat(addressOne).isNotNull();
    assertThat(addressTwo).isNotNull();
    assertThat(addressOne).isNotSameAs(addressTwo);
    assertThat(addressOne).isNotEqualTo(addressTwo);
  }

  @Test
  public void hashCodeOfAddressIsCorrect() {

    Set<Integer> hashCodes = new HashSet<>();

    Address address = AbstractAddress.newAddress(Country.UNITED_STATES_OF_AMERICA)
      .on(Street.of(100, "Main").as(Street.Type.AVENUE))
      .in(City.of("Portland"))
      .in(PostalCode.of("12345"));

    hashCodes.add(address.hashCode());
    hashCodes.add(address.in(Unit.of("A100").as(Unit.Type.APARTMENT)).hashCode());
    hashCodes.add(address.asBilling().hashCode());
    hashCodes.add(address.in(PostalCode.of("97205")).hashCode());
    hashCodes.add(address.asMailing().hashCode());
    hashCodes.add(address.in(Country.UNITED_KINGDOM).hashCode());

    assertThat(hashCodes).hasSize(4);

    hashCodes.forEach(hashCode -> assertThat(hashCode).isNotZero());
  }

  @Test
  public void toStringReturnsAddressStringRepresentation() {

    Address address = AbstractAddress.newAddress(Country.CANADA)
      .on(Street.of(100, "One").as(Street.Type.WAY))
      .in(Unit.of("S2100").as(Unit.Type.SUITE))
      .in(City.of("Portland"))
      .in(PostalCode.of("97205"))
      .asOffice();

    assertThat(address.toString()).isEqualTo(String.format(
      "{ @type = %s, street = 100 One WY, unit = Suite S2100, city = Portland, postal code = 97205, country = CANADA, type = Office }",
        address.getClass().getName()));
  }
}
