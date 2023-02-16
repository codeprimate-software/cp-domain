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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.util.Optional;

import org.junit.Test;

import org.cp.domain.geo.enums.Country;

/**
 * Unit Tests for {@link AbstractAddress}.
 *
 * @author John Blum
 * @see org.junit.Test
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
    assertThat(City.from(address.getCity())).isEqualTo(city);
    assertThat(address.getPostalCode()).isEqualTo(postalCode);
    assertThat(address.getCountry()).isEqualTo(country);
    assertThat(address.getCoordinates()).isNotPresent();
    assertThat(address.getType()).isNotPresent();
    assertThat(address.getUnit()).isNotPresent();
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

  private City mockCity(String name) {

    City mockCity = mock(City.class);

    doReturn(name).when(mockCity).getName();

    return mockCity;
  }

  private PostalCode mockPostalCode(String number) {

    PostalCode mockPostalCode = mock(PostalCode.class);

    doReturn(number).when(mockPostalCode).getNumber();

    return mockPostalCode;
  }

  private void verifyCityInteractions(City mockCity) {

    verify(mockCity, times(2)).getName();
    verify(mockCity, times(1)).getCountry();
    verifyNoMoreInteractions(mockCity);
  }

  @Test
  public void constructNewAbstractAddressWithStreetCityAndPostalCode() {

    Street mockStreet = mock(Street.class);
    City mockCity = mockCity("MockCity");
    PostalCode mockPostalCode = mockPostalCode("12345");

    AbstractAddress address = new TestAddress(mockStreet, mockCity, mockPostalCode);

    assertAddress(address, mockStreet, mockCity, mockPostalCode);

    verifyCityInteractions(mockCity);
    verifyNoInteractions(mockStreet, mockPostalCode);
  }

  @Test
  public void constructNewAbstractAddressWithStreetCityPostalCodeAndCountry() {

    Street mockStreet = mock(Street.class);
    City mockCity = mockCity("MockCity");
    PostalCode mockPostalCode = mock(PostalCode.class);

    AbstractAddress address = new TestAddress(mockStreet, mockCity, mockPostalCode, Country.GERMANY);

    assertAddress(address, mockStreet, mockCity, mockPostalCode, Country.GERMANY);

    verifyCityInteractions(mockCity);
    verifyNoInteractions(mockStreet, mockPostalCode);
  }

  @Test
  public void constructNewAbstractAddressWithNullStreet() {

    City mockCity = mock(City.class);
    PostalCode mockPostalCode = mock(PostalCode.class);

    assertThatIllegalArgumentException()
      .isThrownBy(() -> new TestAddress(null, mockCity, mockPostalCode))
      .withMessage("Street is required")
      .withNoCause();

    verifyNoInteractions(mockCity, mockPostalCode);
  }

  @Test
  public void constructNewAbstractAddressWithNullCity() {

    Street mockStreet = mock(Street.class);
    PostalCode mockPostalCode = mock(PostalCode.class);

    assertThatIllegalArgumentException()
      .isThrownBy(() -> new TestAddress(mockStreet, null, mockPostalCode))
      .withMessage("City is required")
      .withNoCause();

    verifyNoInteractions(mockStreet, mockPostalCode);
  }

  @Test
  public void constructNewAbstractAddressWithNullPostalCode() {

    Street mockStreet = mock(Street.class);
    City mockCity = mock(City.class);

    assertThatIllegalArgumentException()
      .isThrownBy(() -> new TestAddress(mockStreet, mockCity, null))
      .withMessage("PostalCode is required")
      .withNoCause();

    verifyNoInteractions(mockStreet, mockCity);
  }

  @Test
  public void constructNewAbstractAddressWithNullCountry() {

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
  public void fromAddress() {

    Street mockStreet = mock(Street.class);
    Unit mockUnit = mock(Unit.class);
    City mockCity = mock(City.class);
    PostalCode mockPostalCode = mockPostalCode("12345");
    Coordinates mockCoordinates = mock(Coordinates.class);

    Address mockAddress = mockAddress(mockStreet, mockCity, mockPostalCode, Country.GERMANY);

    doReturn(Optional.of(mockCoordinates)).when(mockAddress).getCoordinates();
    doReturn(Optional.of(Address.Type.PO_BOX)).when(mockAddress).getType();
    doReturn(Optional.of(mockUnit)).when(mockAddress).getUnit();

    AbstractAddress address = AbstractAddress.from(mockAddress);

    assertAddress(address, mockAddress);

    verify(mockAddress, times(2)).getStreet();
    verify(mockAddress, times(3)).getUnit();
    verify(mockAddress, times(2)).getCity();
    verify(mockAddress, times(2)).getPostalCode();
    verify(mockAddress, times(2)).getCountry();
    verify(mockAddress, times(2)).getCoordinates();
    verify(mockAddress, times(2)).getType();
    verifyNoInteractions(mockStreet, mockUnit, mockCity, mockCoordinates);
    verifyNoMoreInteractions(mockAddress);
  }

  @Test
  public void fromNullAddress() {

    assertThatIllegalArgumentException()
      .isThrownBy(() -> AbstractAddress.from(null))
      .withMessage("Address to copy is required")
      .withNoCause();
  }

  @Test
  public void newAddress() {

    AbstractAddress.Builder<?> addressBuilder = AbstractAddress.newAddress();

    assertThat(addressBuilder).isNotNull();
    assertThat(addressBuilder.getStreet()).isNull();
    assertThat(addressBuilder.getUnit()).isNotPresent();
    assertThat(addressBuilder.getCity()).isNull();
    assertThat(addressBuilder.getPostalCode()).isNull();
    assertThat(addressBuilder.getCountry()).isEqualTo(Country.localCountry());
    assertThat(addressBuilder.getCoordinates()).isNotPresent();
  }

  @Test
  public void newAddressWithCountry() {

    AbstractAddress.Builder<?> addressBuilder = AbstractAddress.newAddress(Country.CANADA);

    assertThat(addressBuilder).isNotNull();
    assertThat(addressBuilder.getStreet()).isNull();
    assertThat(addressBuilder.getUnit()).isNotPresent();
    assertThat(addressBuilder.getCity()).isNull();
    assertThat(addressBuilder.getPostalCode()).isNull();
    assertThat(addressBuilder.getCountry()).isEqualTo(Country.CANADA);
    assertThat(addressBuilder.getCoordinates()).isNotPresent();
  }

  @Test
  public void ofStreetCityAndPostalCode() {

    Street mockStreet = mock(Street.class);
    City mockCity = mockCity("MockCity");
    PostalCode mockPostalCode = mockPostalCode("12345");

    AbstractAddress address = AbstractAddress.of(mockStreet, mockCity, mockPostalCode);

    assertAddress(address, mockStreet, mockCity, mockPostalCode);

    verifyCityInteractions(mockCity);
    verifyNoInteractions(mockStreet);
  }

  @Test
  public void ofStreetCityPostalCodeAndCountry() {

    Street mockStreet = mock(Street.class);
    City mockCity = mockCity("MockCity");
    PostalCode mockPostalCode = mock(PostalCode.class);

    AbstractAddress address = AbstractAddress.of(mockStreet, mockCity, mockPostalCode, Country.MEXICO);

    assertAddress(address, mockStreet, mockCity, mockPostalCode, Country.MEXICO);

    verifyCityInteractions(mockCity);
    verifyNoInteractions(mockStreet, mockPostalCode);
  }

  @Test
  public void setAndGetId() {

    Street mockStreet = mock(Street.class);
    City mockCity = mock(City.class);
    PostalCode mockPostalCode = mock(PostalCode.class);

    AbstractAddress address = AbstractAddress.of(mockStreet, mockCity, mockPostalCode, Country.UNITED_KINGDOM);

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
  public void setAndGetUnit() {

    Street mockStreet = mock(Street.class);
    City mockCity = mock(City.class);
    PostalCode mockPostalCode = mock(PostalCode.class);
    Unit mockUnit = mock(Unit.class);

    AbstractAddress address = AbstractAddress.of(mockStreet, mockCity, mockPostalCode, Country.AUSTRALIA);

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
  public void setAndGetType() {

    Street mockStreet = mock(Street.class);
    City mockCity = mock(City.class);
    PostalCode mockPostalCode = mock(PostalCode.class);

    AbstractAddress address = AbstractAddress.of(mockStreet, mockCity, mockPostalCode, Country.COLOMBIA);

    assertThat(address.getType()).isNotPresent();

    address.setType(Address.Type.PO_BOX);

    assertThat(address.getType()).isPresent();
    assertThat(address.getType().orElse(null)).isEqualTo(Address.Type.PO_BOX);
    assertThat(address.<Address>as(Address.Type.MAILING)).isSameAs(address);
    assertThat(address.getType().orElse(null)).isEqualTo(Address.Type.MAILING);
    assertThat(address.<Address>asHome()).isSameAs(address);
    assertThat(address.getType().orElse(null)).isEqualTo(Address.Type.HOME);

    address.setType(null);

    assertThat(address.getType()).isNotPresent();
    assertThat(address.getType().orElse(Address.Type.UNKNOWN)).isEqualTo(Address.Type.UNKNOWN);

    verifyNoInteractions(mockStreet, mockCity, mockPostalCode);
  }

  @Test
  public void setAndGetCoordinates() {

    Street mockStreet = mock(Street.class);
    City mockCity = mock(City.class);
    PostalCode mockPostalCode = mock(PostalCode.class);
    Coordinates mockCoordinates = mock(Coordinates.class);

    AbstractAddress address = AbstractAddress.of(mockStreet, mockCity, mockPostalCode, Country.EGYPT);

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
  public void cloneAddressIsCorrect() throws CloneNotSupportedException {

    Street mockStreet = mock(Street.class);
    Unit mockUnit = mock(Unit.class);
    City mockCity = mock(City.class);
    PostalCode mockPostalCode = mock(PostalCode.class);
    Coordinates mockCoordinates = mock(Coordinates.class);

    AbstractAddress address = AbstractAddress.of(mockStreet, mockCity, mockPostalCode, Country.GREECE);

    address.setUnit(mockUnit);
    address.setType((Address.Type.PO_BOX));
    address.setCoordinates(mockCoordinates);

    Object addressClone = address.clone();

    assertThat(addressClone).isInstanceOf(AbstractAddress.class);
    assertAddress((AbstractAddress) addressClone, address);

    verifyNoInteractions(mockStreet, mockUnit, mockCity, mockPostalCode, mockCoordinates);
  }

  @Test
  public void equalAddressesAreEqual() {

    Unit suiteSixteen = Unit.of("16").asSuite();

    AbstractAddress addressOne = new TestAddress(Street.of(100, "Main").asStreet(),
      City.of("Portland"), PostalCode.of("97205"));

    addressOne.setUnit(suiteSixteen);
    addressOne.setType(Address.Type.BILLING);
    addressOne.setCoordinates(Coordinates.at(1.0d, 2.0d));

    AbstractAddress addressTwo = new TestAddress(Street.of(100, "Main").asStreet(),
      City.of("Portland"), PostalCode.of("97205"));

    addressTwo.setUnit(suiteSixteen);
    addressTwo.setType(Address.Type.MAILING);
    addressTwo.setCoordinates(Coordinates.at(2.0d, 1.0d));

    assertThat(addressOne).isNotSameAs(addressTwo);
    assertThat(addressOne).isEqualTo(addressTwo);
    assertThat(addressOne.equals(addressTwo)).isTrue();
  }

  @Test
  @SuppressWarnings("all")
  public void identicalAddressesAreEqual() {

    Street mockStreet = mock(Street.class);
    Unit mockUnit = mock(Unit.class);
    City mockCity = mock(City.class);
    PostalCode mockPostalCode = mock(PostalCode.class);
    Coordinates mockCoordinates = mock(Coordinates.class);

    AbstractAddress addressOne = new TestAddress(mockStreet, mockCity, mockPostalCode);

    addressOne.setUnit(mockUnit);
    addressOne.setType(Address.Type.PO_BOX);
    addressOne.setCoordinates(mockCoordinates);

    AbstractAddress addressTwo = addressOne;

    assertThat(addressOne).isSameAs(addressTwo);
    assertThat(addressOne).isEqualTo(addressTwo);
    assertThat(addressOne.equals(addressTwo)).isTrue();

    verifyNoInteractions(mockStreet, mockUnit, mockCity, mockPostalCode, mockCoordinates);
  }

  @Test
  public void unequalAddressesAreNotEqual() {

    Unit suiteSixteen = Unit.of("16").asSuite();

    AbstractAddress addressOne = new TestAddress(Street.of(100, "Main").asStreet(),
      City.of("ABC"), PostalCode.of("12345"));

    addressOne.setUnit(suiteSixteen);
    addressOne.setType(Address.Type.PO_BOX);
    addressOne.setCoordinates(Coordinates.at(1.0d, 2.0d));

    AbstractAddress addressTwo = new TestAddress(Street.of(100, "Main").asAvenue(),
      City.of("XYZ"), PostalCode.of("54321"));

    addressTwo.setUnit(suiteSixteen);
    addressTwo.setType(Address.Type.PO_BOX);
    addressTwo.setCoordinates(Coordinates.at(1.0d, 2.0d));

    assertThat(addressOne).isNotSameAs(addressTwo);
    assertThat(addressOne).isNotEqualTo(addressTwo);
    assertThat(addressOne.equals(addressTwo)).isFalse();
  }

  @Test
  @SuppressWarnings("all")
  public void equalsNullIsNullSafeReturnsFalse() {

    Street mockStreet = mock(Street.class);
    City mockCity = mock(City.class);
    PostalCode mockPostalCode = mock(PostalCode.class);

    assertThat(new TestAddress(mockStreet, mockCity, mockPostalCode))
      .isNotEqualTo(null);

    verifyNoInteractions(mockStreet, mockCity, mockPostalCode);
  }

  @Test
  @SuppressWarnings("all")
  public void equalsObjectReturnsFalse() {

    assertThat(AbstractAddress.<AbstractAddress>of(Street.of(100, "Main").asStreet(),
      City.of("Portland"), PostalCode.of("97205"))) .isNotEqualTo("100 Main St. Portland, OR 97005");
  }

  @Test
  public void hashCodeIsCorrect() {

    Unit suiteSixteen = Unit.of("16").asSuite();

    AbstractAddress address = new TestAddress(Street.of(100, "Main").asStreet(),
      City.of("Portland"), PostalCode.of("12345"), Country.CANADA);

    address.setUnit(suiteSixteen);

    int hashCode = address.hashCode();

    assertThat(hashCode).isNotEqualTo(0);
    assertThat(hashCode).isEqualTo(address.hashCode());
    assertThat(address).hasSameHashCodeAs(AbstractAddress.newAddress(Country.CANADA)
      .on(Street.of(100, "Main").asStreet())
      .in(suiteSixteen)
      .in(City.of("Portland"))
      .in(PostalCode.of("12345"))
      .build());
    assertThat(address).doesNotHaveSameHashCodeAs(AbstractAddress.newAddress(Country.MEXICO)
      .on(Street.of(100, "Main").asStreet())
      .in(suiteSixteen)
      .in(City.of("Portland"))
      .in(PostalCode.of("12345"))
      .build());
    assertThat(address).doesNotHaveSameHashCodeAs("100 Main St. Portland, OR 12345");
  }

  @Test
  public void toStringIsCorrect() {

    AbstractAddress address = AbstractAddress.newAddress(Country.UNKNOWN)
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
  public void buildAbstractAddress() {

    Street mockStreet = mock(Street.class);
    Unit mockUnit = mock(Unit.class);
    City mockCity = mock(City.class);
    PostalCode mockPostalCode = mock(PostalCode.class);
    Country usa = Country.UNITED_STATES_OF_AMERICA;
    Coordinates mockCoordinates = mock(Coordinates.class);

    AbstractAddress address = new TestAddress.TestBuilder(usa)
      .on(mockStreet)
      .in(mockCity)
      .in(mockPostalCode)
      .in(mockUnit)
      .at(mockCoordinates)
      .build()
      .asPoBox();

    assertThat(address.getStreet()).isEqualTo(mockStreet);
    assertThat(address.getCity()).isEqualTo(mockCity);
    assertThat(address.getPostalCode()).isEqualTo(mockPostalCode);
    assertThat(address.getCountry()).isEqualTo(usa);
    assertThat(address.getCoordinates()).isPresent();
    assertThat(address.getCoordinates().orElse(Coordinates.NULL_ISLAND)).isEqualTo(mockCoordinates);
    assertThat(address.getType()).isPresent();
    assertThat(address.getType().orElse(Address.Type.UNKNOWN)).isEqualTo(Address.Type.PO_BOX);
    assertThat(address.getUnit()).isPresent();
    assertThat(address.getUnit().orElse(Unit.EMPTY)).isEqualTo(mockUnit);

    verifyNoInteractions(mockStreet, mockUnit, mockCity, mockPostalCode, mockCoordinates);
  }

  @Test
  public void buildAbstractAddressWithNullCountry() {

    assertThatIllegalArgumentException()
      .isThrownBy(() -> new TestAddress.TestBuilder(null))
      .withMessage("Country is required")
      .withNoCause();
  }

  protected static class TestAddress extends AbstractAddress {

    protected TestAddress(Street street, City city, PostalCode postalCode) {
      super(street, city, postalCode);
    }

    protected TestAddress(Street street, City city, PostalCode postalCode, Country country) {
      super(street, city, postalCode, country);
    }

    protected static class TestBuilder extends AbstractAddress.Builder<TestAddress> {

      protected TestBuilder(Country country) {
        super(country);
      }

      @Override
      protected TestAddress doBuild() {
        return new TestAddress(getStreet(), getCity(), getPostalCode(), getCountry());
      }
    }
  }
}
