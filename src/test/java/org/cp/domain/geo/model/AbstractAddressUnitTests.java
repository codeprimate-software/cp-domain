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
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoInteractions;

import org.junit.jupiter.api.Test;

import org.cp.domain.geo.enums.Country;

/**
 * Unit Tests for {@link AbstractAddress}.
 *
 * @author John Blum
 * @see org.junit.jupiter.api.Test
 * @see org.mockito.Mockito
 * @see org.cp.domain.geo.model.AbstractAddress
 * @since 0.1.0
 */
public class AbstractAddressUnitTests {

  private void assertAddress(Address actual, Address expected) {

    assertThat(actual).isNotNull();
    assertThat(actual).isNotSameAs(expected);
    assertThat(actual).isEqualTo(expected);
    assertThat(actual.getCoordinates().orElse(null)).isEqualTo(expected.getCoordinates().orElse(null));
    assertThat(actual.getType().orElse(Address.Type.UNKNOWN)).isEqualTo(expected.getType().orElse(Address.Type.UNKNOWN));
    assertThat(actual.getUnit().orElse(Unit.EMPTY)).isEqualTo(expected.getUnit().orElse(Unit.EMPTY));
  }

  private void assertAddress(Address address, Street street, City city, PostalCode postalCode) {
    assertAddress(address, street, city, postalCode, Country.localCountry());
  }

  private void assertAddress(Address address, Street street, City city, PostalCode postalCode, Country country) {

    assertThat(address).isNotNull();
    assertThat(address.getStreet()).isEqualTo(street);
    assertThat(address.getCity()).isEqualTo(city);
    assertThat(address.getPostalCode()).isEqualTo(postalCode);
    assertThat(address.getCountry()).isEqualTo(country);
    assertThat(address.getCoordinates()).isNotPresent();
    assertThat(address.getType()).isNotPresent();
    assertThat(address.getUnit()).isNotPresent();
  }

  private void assertAddress(Address address, Street street, City city, PostalCode postalCode, Country country,
      Coordinates coordinates, Address.Type addressType, Unit unit) {

    assertThat(address).isNotNull();
    assertThat(address.getStreet()).isEqualTo(street);
    assertThat(address.getCity()).isEqualTo(city);
    assertThat(address.getPostalCode()).isEqualTo(postalCode);
    assertThat(address.getCountry()).isEqualTo(country);
    assertThat(address.getCoordinates().orElse(Coordinates.NULL_ISLAND)).isEqualTo(coordinates);
    assertThat(address.getType().orElse(Address.Type.UNKNOWN)).isEqualTo(addressType);
    assertThat(address.getUnit().orElse(Unit.EMPTY)).isEqualTo(unit);
  }

  @SuppressWarnings("unused")
  private Address mockAddress(Street street, City city, PostalCode postalCode) {
    return mockAddress(street, city, postalCode, Country.localCountry());
  }

  private Address mockAddress(Street street, City city, PostalCode postalCode, Country country) {

    Address mockAddress = mock(Address.class);

    doReturn(street).when(mockAddress).getStreet();
    doReturn(city).when(mockAddress).getCity();
    doReturn(postalCode).when(mockAddress).getPostalCode();
    doReturn(country).when(mockAddress).getCountry();

    return mockAddress;
  }

  @SuppressWarnings("unchecked")
  private <T extends Address> T newAddress(Street street, Unit unit, City city, PostalCode postalCode) {

    TestAddress address = new TestAddress(street, city, postalCode);

    address.setUnit(unit);

    return (T) address;
  }

  @SuppressWarnings({ "unchecked", "unused" })
  private <T extends Address> T newAddress(Street street, Unit unit, City city, PostalCode postalCode, Country country) {

    TestAddress address = new TestAddress(street, city, postalCode, country);

    address.setUnit(unit);

    return (T) address;
  }

  @Test
  void constructNewAbstractAddressWithStreetCityAndPostalCode() {

    Street mockStreet = mock(Street.class);
    City mockCity = mock(City.class);
    PostalCode mockPostalCode = mock(PostalCode.class);

    AbstractAddress address = new TestAddress(mockStreet, mockCity, mockPostalCode);

    assertAddress(address, mockStreet, mockCity, mockPostalCode);

    verifyNoInteractions(mockStreet, mockCity, mockPostalCode);
  }

  @Test
  void constructNewAbstractAddressWithStreetCityPostalCodeAndCountry() {

    Street mockStreet = mock(Street.class);
    City mockCity = mock(City.class);
    PostalCode mockPostalCode = mock(PostalCode.class);

    AbstractAddress address = new TestAddress(mockStreet, mockCity, mockPostalCode, Country.GERMANY);

    assertAddress(address, mockStreet, mockCity, mockPostalCode, Country.GERMANY);

    verifyNoInteractions(mockStreet, mockCity, mockPostalCode);
  }

  @Test
  void constructNewAbstractAddressWithNullStreet() {

    City mockCity = mock(City.class);
    PostalCode mockPostalCode = mock(PostalCode.class);

    assertThatIllegalArgumentException()
      .isThrownBy(() -> new TestAddress(null, mockCity, mockPostalCode))
      .withMessage("Street is required")
      .withNoCause();

    verifyNoInteractions(mockCity, mockPostalCode);
  }

  @Test
  void constructNewAbstractAddressWithNullCity() {

    Street mockStreet = mock(Street.class);
    PostalCode mockPostalCode = mock(PostalCode.class);

    assertThatIllegalArgumentException()
      .isThrownBy(() -> new TestAddress(mockStreet, null, mockPostalCode))
      .withMessage("City is required")
      .withNoCause();

    verifyNoInteractions(mockStreet, mockPostalCode);
  }

  @Test
  void constructNewAbstractAddressWithNullPostalCode() {

    Street mockStreet = mock(Street.class);
    City mockCity = mock(City.class);

    assertThatIllegalArgumentException()
      .isThrownBy(() -> new TestAddress(mockStreet, mockCity, null))
      .withMessage("PostalCode is required")
      .withNoCause();

    verifyNoInteractions(mockStreet, mockCity);
  }

  @Test
  void constructNewAbstractAddressWithNullCountry() {

    Street mockStreet = mock(Street.class);
    City mockCity = mock(City.class);
    PostalCode mockPostalCode = mock(PostalCode.class);

    assertThatIllegalArgumentException()
      .isThrownBy(() -> new TestAddress(mockStreet, mockCity, mockPostalCode, null))
      .withMessage("Country is required")
      .withNoCause();

    verifyNoInteractions(mockStreet, mockCity, mockPostalCode);
  }

  @Test
  void setAndGetCoordinates() {

    Street mockStreet = mock(Street.class);
    City mockCity = mock(City.class);
    PostalCode mockPostalCode = mock(PostalCode.class);
    Coordinates mockCoordinates = mock(Coordinates.class);

    AbstractAddress address = new TestAddress(mockStreet, mockCity, mockPostalCode, Country.AUSTRIA);

    assertThat(address.getCoordinates()).isNotPresent();

    address.setCoordinates(mockCoordinates);

    assertThat(address.getCoordinates()).isPresent();
    assertThat(address.getCoordinates().orElse(null)).isEqualTo(mockCoordinates);

    address.setCoordinates(null);

    assertThat(address.getCoordinates()).isNotPresent();
    assertThat(address.getCoordinates().orElse(Coordinates.NULL_ISLAND)).isEqualTo(Coordinates.NULL_ISLAND);

    verifyNoInteractions(mockStreet, mockCity, mockPostalCode);
  }

  @Test
  void setAndGetId() {

    Street mockStreet = mock(Street.class);
    City mockCity = mock(City.class);
    PostalCode mockPostalCode = mock(PostalCode.class);

    AbstractAddress address = new TestAddress(mockStreet, mockCity, mockPostalCode, Country.UNITED_KINGDOM);

    assertThat(address.getId()).isNull();

    address.setId(1L);

    assertThat(address.getId()).isEqualTo(1L);
    assertThat(address.<Address>identifiedBy(2L)).isSameAs(address);
    assertThat(address.getId()).isEqualTo(2L);

    address.setId(null);

    assertThat(address.getId()).isNull();

    verifyNoInteractions(mockStreet, mockCity, mockPostalCode);
  }

  @Test
  void setAndGetType() {

    Street mockStreet = mock(Street.class);
    City mockCity = mock(City.class);
    PostalCode mockPostalCode = mock(PostalCode.class);

    AbstractAddress address = new TestAddress(mockStreet, mockCity, mockPostalCode, Country.UNITED_STATES_OF_AMERICA);

    assertThat(address.getType()).isNotPresent();

    address.setType(Address.Type.PO_BOX);

    assertThat(address.getType()).isPresent();
    assertThat(address.getType().orElse(null)).isEqualTo(Address.Type.PO_BOX);
    assertThat(address.<Address>as(Address.Type.MAILING)).isSameAs(address);
    assertThat(address.getType().orElse(null)).isEqualTo(Address.Type.MAILING);
    assertThat(address.<Address>asBilling()).isSameAs(address);
    assertThat(address.getType().orElse(null)).isEqualTo(Address.Type.BILLING);

    address.setType(null);

    assertThat(address.getType()).isNotPresent();
    assertThat(address.getType().orElse(Address.Type.UNKNOWN)).isEqualTo(Address.Type.UNKNOWN);

    verifyNoInteractions(mockStreet, mockCity, mockPostalCode);
  }

  @Test
  void setAndGetUnit() {

    Street mockStreet = mock(Street.class);
    City mockCity = mock(City.class);
    PostalCode mockPostalCode = mock(PostalCode.class);
    Unit mockUnit = mock(Unit.class);

    AbstractAddress address = new TestAddress(mockStreet, mockCity, mockPostalCode, Country.EGYPT);

    assertThat(address.getUnit()).isNotPresent();

    address.setUnit(mockUnit);

    assertThat(address.getUnit()).isPresent();
    assertThat(address.getUnit().orElse(null)).isEqualTo(mockUnit);

    address.setUnit(null);

    assertThat(address.getUnit()).isNotPresent();
    assertThat(address.getUnit().orElse(Unit.EMPTY)).isEqualTo(Unit.EMPTY);

    verifyNoInteractions(mockStreet, mockCity, mockPostalCode, mockUnit);
  }

  @Test
  void cloneAddressIsCorrect() throws CloneNotSupportedException {

    Street mockStreet = mock(Street.class);
    Unit mockUnit = mock(Unit.class);
    City mockCity = mock(City.class);
    PostalCode mockPostalCode = mock(PostalCode.class);
    Coordinates mockCoordinates = mock(Coordinates.class);

    AbstractAddress address = new TestAddress(mockStreet, mockCity, mockPostalCode, Country.GREECE);

    address.setUnit(mockUnit);
    address.setType((Address.Type.PO_BOX));
    address.setCoordinates(mockCoordinates);

    Object addressClone = address.clone();

    assertThat(addressClone).isInstanceOf(AbstractAddress.class);
    assertAddress((AbstractAddress) addressClone, address);

    verifyNoInteractions(mockStreet, mockUnit, mockCity, mockPostalCode, mockCoordinates);
  }

  @Test
  void equalAddressesAreEqual() {

    Unit suiteSixteen = Unit.of("16").asSuite();

    AbstractAddress addressOne = newAddress(Street.of(100, "Main").asStreet(), suiteSixteen,
      City.of("Portland"), PostalCode.of("97205"))
      .at(Coordinates.at(1.0d, 2.0d))
      .asBilling();

    AbstractAddress addressTwo = newAddress(Street.of(100, "Main").asStreet(), suiteSixteen,
      City.of("Portland"), PostalCode.of("97205"))
      .at(Coordinates.at(2.0d, 1.0d))
      .asMailing();

    assertThat(addressOne).isNotSameAs(addressTwo);
    assertThat(addressOne).isEqualTo(addressTwo);
    assertThat(addressOne.equals(addressTwo)).isTrue();
  }

  @Test
  @SuppressWarnings("all")
  void identicalAddressesAreEqual() {

    Street mockStreet = mock(Street.class);
    Unit mockUnit = mock(Unit.class);
    City mockCity = mock(City.class);
    PostalCode mockPostalCode = mock(PostalCode.class);
    Coordinates mockCoordinates = mock(Coordinates.class);

    AbstractAddress addressOne = newAddress(mockStreet, mockUnit, mockCity, mockPostalCode)
      .at(mockCoordinates)
      .asPoBox();

    AbstractAddress addressTwo = addressOne;

    assertThat(addressOne).isSameAs(addressTwo);
    assertThat(addressOne).isEqualTo(addressTwo);
    assertThat(addressOne.equals(addressTwo)).isTrue();

    verifyNoInteractions(mockStreet, mockUnit, mockCity, mockPostalCode, mockCoordinates);
  }

  @Test
  void unequalAddressesAreNotEqual() {

    Unit suiteSixteen = Unit.of("16").asSuite();

    AbstractAddress addressOne = newAddress(Street.of(200, "Park").asWay(), suiteSixteen,
      City.of("ABC"), PostalCode.of("12345"))
      .at(Coordinates.at(1.0d, 2.0d))
      .asPoBox();

    AbstractAddress addressTwo = newAddress(Street.of(200, "Par").asAvenue(), suiteSixteen,
      City.of("XYZ"), PostalCode.of("54321"))
      .at(Coordinates.at(1.0d, 2.0d))
      .asPoBox();

    assertThat(addressOne).isNotSameAs(addressTwo);
    assertThat(addressOne).isNotEqualTo(addressTwo);
    assertThat(addressOne.equals(addressTwo)).isFalse();
  }

  @Test
  @SuppressWarnings("all")
  void equalsNullIsNullSafeReturnsFalse() {

    Street mockStreet = mock(Street.class);
    City mockCity = mock(City.class);
    PostalCode mockPostalCode = mock(PostalCode.class);

    assertThat(this.<Address>newAddress(mockStreet, null, mockCity, mockPostalCode)).isNotEqualTo(null);

    verifyNoInteractions(mockStreet, mockCity, mockPostalCode);
  }

  @Test
  @SuppressWarnings("all")
  void equalsObjectReturnsFalse() {

    assertThat(this.<Address>newAddress(Street.of(100, "Main").asStreet(), null,
      City.of("Portland"), PostalCode.of("97205"))).isNotEqualTo("100 Main St. Portland, OR 97005");
  }

  @Test
  void hashCodeIsCorrect() {

    Unit suiteSixteen = Unit.of("16").asSuite();

    AbstractAddress address = newAddress(Street.of(100, "Main").asStreet(), suiteSixteen,
      City.of("Portland"), PostalCode.of("12345"), Country.CANADA);

    int hashCode = address.hashCode();

    assertThat(hashCode).isNotEqualTo(0);
    assertThat(hashCode).isEqualTo(address.hashCode());

    assertThat(address).hasSameHashCodeAs(Address.builder(Country.CANADA)
      .on(Street.of(100, "Main").asStreet())
      .in(suiteSixteen)
      .in(City.of("Portland"))
      .in(PostalCode.of("12345"))
      .build());

    assertThat(address).doesNotHaveSameHashCodeAs(Address.builder(Country.MEXICO)
      .on(Street.of(100, "Main").asStreet())
      .in(suiteSixteen)
      .in(City.of("Portland"))
      .in(PostalCode.of("12345"))
      .build());

    assertThat(address).doesNotHaveSameHashCodeAs("100 Main St. Portland, OR 12345");
  }

  @Test
  void toStringIsCorrect() {

    AbstractAddress address = Address.builder(Country.UNKNOWN)
      .on(Street.of(100, "Main").asStreet())
      .in(Unit.of("16").asSuite())
      .in(City.of("Portland"))
      .in(PostalCode.of("97205"))
      .at(Coordinates.at(1.0d, 2.0d).at(Elevation.at(100.0d)))
      .build()
      .asPoBox();

    assertThat(address).isNotNull();
    assertThat(address.toString()).isEqualTo(AbstractAddress.ADDRESS_TO_STRING, address.getClass().getName(),
      address.getStreet(), address.getUnit().orElse(null), address.getCity(), address.getPostalCode(),
      address.getCountry(), address.getType().orElse(null));
  }

  @Test
  void buildAbstractAddress() {

    Street mockStreet = mock(Street.class);
    Unit mockUnit = mock(Unit.class);
    City mockCity = mock(City.class);
    PostalCode mockPostalCode = mock(PostalCode.class);
    Country unitedKingdom = Country.UNITED_KINGDOM;
    Coordinates mockCoordinates = mock(Coordinates.class);

    AbstractAddress address = new TestAddress.Builder<AbstractAddress>()
      .on(mockStreet)
      .in(mockUnit)
      .in(mockCity)
      .in(mockPostalCode)
      .in(unitedKingdom)
      .at(mockCoordinates)
      .build()
      .asPoBox();

    assertAddress(address, mockStreet, mockCity, mockPostalCode, unitedKingdom,
      mockCoordinates, Address.Type.PO_BOX, mockUnit);

    verifyNoInteractions(mockStreet, mockUnit, mockCity, mockPostalCode, mockCoordinates);
  }

  static class TestAddress extends AbstractAddress {

    TestAddress(Street street, City city, PostalCode postalCode) {
      super(street, city, postalCode);
    }

    TestAddress(Street street, City city, PostalCode postalCode, Country country) {
      super(street, city, postalCode, country);
    }
  }
}
