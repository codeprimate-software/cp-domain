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
    assertThat(actual.getType().orElse(Address.Type.UNKNOWN))
      .isEqualTo(expected.getType().orElse(Address.Type.UNKNOWN));
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

  private PostalCode mockPostalCode(String number) {

    PostalCode mockPostalCode = mock(PostalCode.class);

    doReturn(number).when(mockPostalCode).getNumber();

    return mockPostalCode;
  }

  @Test
  public void constructNewAbstractAddressWithStreetCityAndPostalCode() {

    Street mockStreet = mock(Street.class);
    City mockCity = mock(City.class);
    PostalCode mockPostalCode = mockPostalCode("12345");

    AbstractAddress address = new TestAddress(mockStreet, mockCity, mockPostalCode);

    assertAddress(address, mockStreet, mockCity, mockPostalCode);

    verifyNoInteractions(mockStreet, mockCity, mockPostalCode);
  }

  @Test
  public void constructNewAbstractAddressWithStreetCityPostalCodeAndCountry() {

    Street mockStreet = mock(Street.class);
    City mockCity = mock(City.class);
    PostalCode mockPostalCode = mock(PostalCode.class);

    AbstractAddress address = new TestAddress(mockStreet, mockCity, mockPostalCode, Country.GERMANY);

    assertAddress(address, mockStreet, mockCity, mockPostalCode, Country.GERMANY);

    verifyNoInteractions(mockStreet, mockCity, mockPostalCode);
  }

  @Test
  public void constructWithNullStreet() {

    City mockCity = mock(City.class);
    PostalCode mockPostalCode = mock(PostalCode.class);

    assertThatIllegalArgumentException()
      .isThrownBy(() -> new TestAddress(null, mockCity, mockPostalCode))
      .withMessage("Street is required")
      .withNoCause();

    verifyNoInteractions(mockCity, mockPostalCode);
  }

  @Test
  public void constructWithNullCity() {

    Street mockStreet = mock(Street.class);
    PostalCode mockPostalCode = mock(PostalCode.class);

    assertThatIllegalArgumentException()
      .isThrownBy(() -> new TestAddress(mockStreet, null, mockPostalCode))
      .withMessage("City is required")
      .withNoCause();

    verifyNoInteractions(mockStreet, mockPostalCode);
  }

  @Test
  public void constructWithNullPostalCode() {

    Street mockStreet = mock(Street.class);
    City mockCity = mock(City.class);

    assertThatIllegalArgumentException()
      .isThrownBy(() -> new TestAddress(mockStreet, mockCity, null))
      .withMessage("PostalCode is required")
      .withNoCause();

    verifyNoInteractions(mockStreet, mockCity);
  }

  @Test
  public void constructWithNullCountry() {

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
    City mockCity = mock(City.class);
    PostalCode mockPostalCode = mockPostalCode("12345");

    AbstractAddress address = AbstractAddress.of(mockStreet, mockCity, mockPostalCode);

    assertAddress(address, mockStreet, mockCity, mockPostalCode);

    verifyNoInteractions(mockStreet, mockCity);
  }

  @Test
  public void ofStreetCityPostalCodeAndCountry() {

    Street mockStreet = mock(Street.class);
    City mockCity = mock(City.class);
    PostalCode mockPostalCode = mock(PostalCode.class);

    AbstractAddress address = AbstractAddress.of(mockStreet, mockCity, mockPostalCode, Country.MEXICO);

    assertAddress(address, mockStreet, mockCity, mockPostalCode, Country.MEXICO);

    verifyNoInteractions(mockStreet, mockCity, mockPostalCode);
  }

  protected static class TestAddress extends AbstractAddress {

    protected TestAddress(Street street, City city, PostalCode postalCode) {
      super(street, city, postalCode);
    }

    protected TestAddress(Street street, City city, PostalCode postalCode, Country country) {
      super(street, city, postalCode, country);
    }
  }
}
