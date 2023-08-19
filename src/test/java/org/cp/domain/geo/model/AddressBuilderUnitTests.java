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

import java.util.Locale;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import org.cp.domain.geo.enums.Country;

/**
 * Unit Tests for {@link Address.Builder}
 *
 * @author John Blum
 * @see org.junit.jupiter.api.Test
 * @see org.mockito.Mockito
 * @see org.cp.domain.geo.model.Address.Builder
 * @see org.cp.domain.geo.model.BaseAddressUnitTests
 * @since 0.1.0
 */
@SuppressWarnings("unused")
public class AddressBuilderUnitTests extends BaseAddressUnitTests {

  @BeforeAll
  static void beforeTests() {
    Locale.setDefault(Locale.CANADA);
  }

  @Test
  @SuppressWarnings({ "rawtypes", "unchecked" })
  void addressBuilderResolvesToLocalCountryWhenNull() {

    Address.Builder<?> addressBuilder = new Address.Builder<>();

    assertThat(addressBuilder).isNotNull();
    assertThat(addressBuilder.getCountry()).isEqualTo(Country.localCountry());
    assertThat(addressBuilder.<Address.Builder>in(Country.CANADA)).isSameAs(addressBuilder);
    assertThat(addressBuilder.getCountry()).isEqualTo(Country.CANADA);
    assertThat(addressBuilder.<Address.Builder>in((Country) null)).isSameAs(addressBuilder);
    assertThat(addressBuilder.getCountry()).isEqualTo(Country.localCountry());
  }

  @Test
  void fromAddress() {

    Street mockStreet = mock(Street.class);
    Unit mockUnit = mock(Unit.class);
    City mockCity = mock(City.class);
    PostalCode mockPostalCode = mock(PostalCode.class);
    Coordinates mockCoordinates = mock(Coordinates.class);

    Address mockAddress = mockAddress(mockStreet, mockCity, mockPostalCode, Country.CANADA);

    doReturn(Optional.of(mockCoordinates)).when(mockAddress).getCoordinates();
    doReturn(Optional.of(mockUnit)).when(mockAddress).getUnit();

    Address.Builder<Address> addressBuilder = Address.Builder.from(mockAddress);

    assertThat(addressBuilder).isNotNull();
    assertThat(addressBuilder.getStreet()).isEqualTo(mockStreet);
    assertThat(addressBuilder.getUnit().orElse(null)).isEqualTo(mockUnit);
    assertThat(addressBuilder.getCity()).isEqualTo(mockCity);
    assertThat(addressBuilder.getPostalCode()).isEqualTo(mockPostalCode);
    assertThat(addressBuilder.getCountry()).isEqualTo(Country.CANADA);
    assertThat(addressBuilder.getCoordinates().orElse(null)).isEqualTo(mockCoordinates);

    verify(mockAddress, times(1)).getStreet();
    verify(mockAddress, times(1)).getUnit();
    verify(mockAddress, times(1)).getCity();
    verify(mockAddress, times(1)).getPostalCode();
    verify(mockAddress, times(1)).getCountry();
    verify(mockAddress, times(1)).getCoordinates();
    verifyNoMoreInteractions(mockAddress);
  }

  @Test
  void fromNullAddressThrowsIllegalArgumentException() {

    assertThatIllegalArgumentException()
      .isThrownBy(() -> Address.Builder.from(null))
      .withMessage("Address to copy is required")
      .withNoCause();
  }

  @Test
  void buildBasicAddressInLocalCountry() {

    Street mockStreet = mock(Street.class);
    City mockCity = mock(City.class);
    PostalCode mockPostalCode = mock(PostalCode.class);

    Address address = new Address.Builder<>()
      .on(mockStreet)
      .in(mockCity)
      .in(mockPostalCode)
      .inLocalCountry()
      .build();

    assertAddress(address, mockStreet, mockCity, mockPostalCode);

    assertThat(address.getCoordinates()).isNotPresent();
    assertThat(address.getType()).isNotPresent();
    assertThat(address.getUnit()).isNotPresent();

    verifyNoMoreInteractions(mockStreet, mockCity, mockPostalCode);
  }

  @Test
  void buildCompleteAddressInGermany() {

    Street mockStreet = mock(Street.class);
    Unit mockUnit = mock(Unit.class);
    City mockCity = mock(City.class);
    PostalCode mockPostalCode = mock(PostalCode.class);
    Coordinates mockCoordinates = mock(Coordinates.class);

    Address address = new Address.Builder<>()
      .on(mockStreet)
      .in(mockUnit)
      .in(mockCity)
      .in(mockPostalCode)
      .in(Country.GERMANY)
      .at(mockCoordinates)
      .build()
      .asPoBox();

    assertAddress(address, mockStreet, mockCity, mockPostalCode, Country.GERMANY);

    assertThat(address.getCoordinates().orElse(null)).isEqualTo(mockCoordinates);
    assertThat(address.getType().orElse(null)).isEqualTo(Address.Type.PO_BOX);
    assertThat(address.getUnit().orElse(null)).isEqualTo(mockUnit);

    verifyNoMoreInteractions(mockStreet, mockUnit, mockCity, mockPostalCode, mockCoordinates);
  }

  @Test
  void buildAddressWithNullStreet() {

    City mockCity = mock(City.class);
    PostalCode mockPostalCode = mock(PostalCode.class);

    assertThatIllegalArgumentException()
      .isThrownBy(() -> new Address.Builder<>().in(mockCity).in(mockPostalCode).build())
      .withMessage("Street is required")
      .withNoCause();

    verifyNoInteractions(mockCity, mockPostalCode);
  }

  @Test
  void buildAddressWithNullCity() {

    Street mockStreet = mock(Street.class);
    PostalCode mockPostalCode = mock(PostalCode.class);

    assertThatIllegalArgumentException()
      .isThrownBy(() -> new Address.Builder<>().on(mockStreet).in(mockPostalCode).build())
      .withMessage("City is required")
      .withNoCause();

    verifyNoInteractions(mockStreet, mockPostalCode);
  }

  @Test
  void buildAddressWithNullPostalCode() {

    Street mockStreet = mock(Street.class);
    City mockCity = mock(City.class);

    assertThatIllegalArgumentException()
      .isThrownBy(() -> new Address.Builder<>().on(mockStreet).in(mockCity).build())
      .withMessage("PostalCode is required")
      .withNoCause();

    verifyNoInteractions(mockStreet, mockCity);
  }
}
