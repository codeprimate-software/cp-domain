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

import static java.util.Arrays.stream;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.cp.domain.geo.enums.Country;
import org.cp.elements.lang.Visitor;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * Unit tests for {@link Address}.
 *
 * @author John Blum
 * @see org.junit.Test
 * @see org.junit.runner.RunWith
 * @see org.mockito.Mock
 * @see org.mockito.Mockito
 * @see org.mockito.junit.MockitoJUnitRunner
 * @see org.cp.domain.geo.enums.Country
 * @see org.cp.domain.geo.model.Address
 * @since 1.0.0
 */
@RunWith(MockitoJUnitRunner.class)
public class AddressTests {

  @Mock
  private Address mockAddress;

  @Before
  public void setup() {
    when(mockAddress.getType()).thenCallRealMethod();
    doCallRealMethod().when(mockAddress).setType(any(Address.Type.class));
    when(mockAddress.getUnit()).thenCallRealMethod();
    doCallRealMethod().when(mockAddress).setUnit(any(Unit.class));
    doCallRealMethod().when(mockAddress).accept(any(Visitor.class));
    when(mockAddress.compareTo(any(Address.class))).thenCallRealMethod();
    when(mockAddress.validate()).thenCallRealMethod();
    when(mockAddress.as(any(Address.Type.class))).thenCallRealMethod();
    when(mockAddress.on(any(Street.class))).thenCallRealMethod();
    when(mockAddress.in(any(Unit.class))).thenCallRealMethod();
    when(mockAddress.in(any(City.class))).thenCallRealMethod();
    when(mockAddress.in(any(PostalCode.class))).thenCallRealMethod();
    when(mockAddress.in(any(Country.class))).thenCallRealMethod();
    when(mockAddress.with(any(Coordinates.class))).thenCallRealMethod();
  }

  private Address mockAddress(Street street, Unit unit, City city, PostalCode postalCode) {
    return mockAddress(mock(Address.class), street, unit, city, postalCode);
  }

  private Address mockAddress(Address mockAddress, Street street, Unit unit, City city, PostalCode postalCode) {

    when(mockAddress.getStreet()).thenReturn(street);
    when(mockAddress.getUnit()).thenReturn(Optional.ofNullable(unit));
    when(mockAddress.getCity()).thenReturn(city);
    when(mockAddress.getPostalCode()).thenReturn(postalCode);
    when(mockAddress.getCountry()).thenReturn(Country.UNITED_STATES_OF_AMERICA);

    return mockAddress;
  }

  @Test
  public void setAndGetTypeReturnsUnknown() {
    assertThat(this.mockAddress.getType().orElse(null)).isEqualTo(Address.Type.UNKNOWN);

    this.mockAddress.setType(Address.Type.HOME);

    assertThat(this.mockAddress.getType().orElse(null)).isEqualTo(Address.Type.UNKNOWN);

    this.mockAddress.setType(Address.Type.OFFICE);

    assertThat(this.mockAddress.getType().orElse(null)).isEqualTo(Address.Type.UNKNOWN);

    this.mockAddress.setType(Address.Type.UNKNOWN);

    assertThat(this.mockAddress.getType().orElse(null)).isEqualTo(Address.Type.UNKNOWN);
  }

  @Test
  public void setAndGetUnitReturnsEmptyOptional() {
    assertThat(this.mockAddress.getUnit()).isEqualTo(Optional.empty());

    this.mockAddress.setUnit(Unit.of("123").as(Unit.Type.OFFICE));

    assertThat(this.mockAddress.getUnit()).isEqualTo(Optional.empty());
  }

  @Test
  public void acceptVisitsTheAddress() {
    Visitor mockVisitor = mock(Visitor.class);

    this.mockAddress.accept(mockVisitor);

    verify(mockVisitor, times(1)).visit(eq(this.mockAddress));
  }

  @Test
  public void compareToEqualAddressReturnsZero() {
    Address mockAddress = mockAddress(Street.of(100, "Main").as(Street.Type.STREET),
      Unit.of("100").as(Unit.Type.OFFICE), City.of("Portland"), PostalCode.of("97205"));

    mockAddress(this.mockAddress, Street.of(100, "Main").as(Street.Type.STREET), Unit.of("100").as(Unit.Type.OFFICE),
      City.of("Portland"), PostalCode.of("97205"));

    assertThat(this.mockAddress.compareTo(mockAddress)).isEqualTo(0);

    verify(this.mockAddress, times(1)).getCountry();
    verify(this.mockAddress, times(1)).getCity();
    verify(this.mockAddress, times(1)).getPostalCode();
    verify(this.mockAddress, times(1)).getStreet();
    verify(this.mockAddress, times(1)).getUnit();
  }

  @Test
  public void compareToGreaterAddressReturnsNegativeInt() {
    Address mockAddress = mockAddress(Street.of(100, "Main").as(Street.Type.STREET),
      Unit.of("100").as(Unit.Type.OFFICE), City.of("Portland"), PostalCode.of("97205"));

    mockAddress(this.mockAddress, Street.of(200, "One").as(Street.Type.WAY), Unit.of("200").as(Unit.Type.SUITE),
      City.of("Ashland"), PostalCode.of("98765"));

    assertThat(this.mockAddress.compareTo(mockAddress)).isLessThan(0);

    verify(this.mockAddress, times(1)).getCountry();
    verify(this.mockAddress, times(1)).getCity();
    verify(this.mockAddress, times(1)).getPostalCode();
    verify(this.mockAddress, times(1)).getStreet();
    verify(this.mockAddress, times(1)).getUnit();
  }

  @Test
  public void compareToLesserAddressReturnsPositiveInt() {
    Address mockAddress = mockAddress(Street.of(100, "Main").as(Street.Type.STREET),
      Unit.of("100").as(Unit.Type.SUITE), City.of("Portland"), PostalCode.of("97205"));

    mockAddress(this.mockAddress, Street.of(200, "Main").as(Street.Type.STREET), Unit.of("10").as(Unit.Type.APARTMENT),
      City.of("Portland"), PostalCode.of("97205"));

    assertThat(this.mockAddress.compareTo(mockAddress)).isGreaterThan(0);

    verify(this.mockAddress, times(1)).getCountry();
    verify(this.mockAddress, times(1)).getCity();
    verify(this.mockAddress, times(1)).getPostalCode();
    verify(this.mockAddress, times(1)).getStreet();
    verify(this.mockAddress, times(1)).getUnit();
  }

  @Test(expected = IllegalStateException.class)
  public void validateFailsOnStreet() {
    try {
      mockAddress(this.mockAddress, null, Unit.EMPTY, City.of("Portland"), PostalCode.of("97205"));

      this.mockAddress.validate();
    }
    catch (IllegalStateException expected) {
      assertThat(expected).hasMessage("Street is required");
      assertThat(expected).hasNoCause();

      throw expected;
    }
    finally {
      verify(this.mockAddress, times(1)).getStreet();
      verify(this.mockAddress, never()).getCity();
      verify(this.mockAddress, never()).getPostalCode();
      verify(this.mockAddress, never()).getCountry();
    }
  }

  @Test(expected = IllegalStateException.class)
  public void validateFailsOnCity() {
    try {
      mockAddress(this.mockAddress, Street.of(100, "Main").as(Street.Type.STREET), Unit.EMPTY,
        null, PostalCode.of("97205"));

      this.mockAddress.validate();
    }
    catch (IllegalStateException expected) {
      assertThat(expected).hasMessage("City is required");
      assertThat(expected).hasNoCause();

      throw expected;
    }
    finally {
      verify(this.mockAddress, times(1)).getStreet();
      verify(this.mockAddress, times(1)).getCity();
      verify(this.mockAddress, never()).getPostalCode();
      verify(this.mockAddress, never()).getCountry();
    }
  }

  @Test(expected = IllegalStateException.class)
  public void validateFailsOnPostalCode() {
    try {
      mockAddress(this.mockAddress, Street.of(100, "Main").as(Street.Type.STREET), Unit.EMPTY,
        City.of("Portland"), null);

      this.mockAddress.validate();
    }
    catch (IllegalStateException expected) {
      assertThat(expected).hasMessage("Postal Code is required");
      assertThat(expected).hasNoCause();

      throw expected;
    }
    finally {
      verify(this.mockAddress, times(1)).getStreet();
      verify(this.mockAddress, times(1)).getCity();
      verify(this.mockAddress, times(1)).getPostalCode();
      verify(this.mockAddress, never()).getCountry();
    }
  }

  @Test(expected = IllegalStateException.class)
  public void validateFailsOnCountry() {
    try {
      mockAddress(this.mockAddress, Street.of(100, "Main").as(Street.Type.STREET), Unit.EMPTY,
        City.of("Portland"), PostalCode.of("97205"));

      when(this.mockAddress.getCountry()).thenReturn(null);

      this.mockAddress.validate();
    }
    catch (IllegalStateException expected) {
      assertThat(expected).hasMessage("Country is required");
      assertThat(expected).hasNoCause();

      throw expected;
    }
    finally {
      verify(this.mockAddress, times(1)).getStreet();
      verify(this.mockAddress, times(1)).getCity();
      verify(this.mockAddress, times(1)).getPostalCode();
      verify(this.mockAddress, times(1)).getCountry();
    }
  }

  @Test
  public void asTypeCallsSetTypeReturnsThis() {
    assertThat(this.mockAddress.<Address>as(Address.Type.HOME)).isSameAs(this.mockAddress);
    verify(this.mockAddress, times(1)).setType(eq(Address.Type.HOME));
  }

  @Test
  public void onStreetCallsSetStreetReturnsThis() {
    Street expectedStreet = Street.of(100, "Main").as(Street.Type.AVENUE);

    assertThat(this.mockAddress.<Address>on(expectedStreet)).isSameAs(this.mockAddress);
    verify(this.mockAddress, times(1)).setStreet(eq(expectedStreet));
  }

  @Test
  public void inUnitCallsSetUnitReturnsThis() {
    Unit expectedUnit = Unit.of("123").as(Unit.Type.OFFICE);

    assertThat(this.mockAddress.<Address>in(expectedUnit)).isSameAs(this.mockAddress);
    verify(this.mockAddress, times(1)).setUnit(eq(expectedUnit));
  }

  @Test
  public void inCityCallsSetCityReturnsThis() {
    City expectedCity = City.of("Portland");

    assertThat(this.mockAddress.<Address>in(expectedCity)).isSameAs(this.mockAddress);
    verify(this.mockAddress, times(1)).setCity(eq(expectedCity));
  }

  @Test
  public void inPostalCodeCallsSetPostalCodeReturnsThis() {
    PostalCode expectedPostalCode = PostalCode.of("12345");

    assertThat(this.mockAddress.<Address>in(expectedPostalCode)).isSameAs(this.mockAddress);
    verify(this.mockAddress, times(1)).setPostalCode(eq(expectedPostalCode));
  }

  @Test
  public void inCountryCallsSetCountryReturnsThis() {
    Country expectedCountry = Country.UNITED_KINGDOM;

    assertThat(this.mockAddress.<Address>in(expectedCountry)).isSameAs(this.mockAddress);
    verify(this.mockAddress, times(1)).setCountry(eq(expectedCountry));
  }

  @Test
  public void withCoordinatesCallsSetCoordinatesReturnsThis() {
    Coordinates expectedCoordinates = Coordinates.of(1.012489753, 10.2501248);

    assertThat(this.mockAddress.<Address>with(expectedCoordinates)).isSameAs(this.mockAddress);
    verify(this.mockAddress, times(1)).setCoordinates(eq(expectedCoordinates));
  }

  @Test
  public void addressTypeAbbreviations() {
    assertThat(Address.Type.BILLING.getAbbreviation()).isEqualTo("BA");
    assertThat(Address.Type.HOME.getAbbreviation()).isEqualTo("HA");
    assertThat(Address.Type.MAILING.getAbbreviation()).isEqualTo("MA");
    assertThat(Address.Type.OFFICE.getAbbreviation()).isEqualTo("OA");
    assertThat(Address.Type.PO_BOX.getAbbreviation()).isEqualTo("PO");
    assertThat(Address.Type.WORK.getAbbreviation()).isEqualTo("WA");
    assertThat(Address.Type.UNKNOWN.getAbbreviation()).isEqualTo("??");
  }

  @Test
  public void addressTypeDescriptions() {
    assertThat(Address.Type.BILLING.getDescription()).isEqualTo("Billing Address");
    assertThat(Address.Type.HOME.getDescription()).isEqualTo("Home Address");
    assertThat(Address.Type.MAILING.getDescription()).isEqualTo("Mailing Address");
    assertThat(Address.Type.OFFICE.getDescription()).isEqualTo("Office Address");
    assertThat(Address.Type.PO_BOX.getDescription()).isEqualTo("Post Office Box");
    assertThat(Address.Type.WORK.getDescription()).isEqualTo("Work Address");
    assertThat(Address.Type.UNKNOWN.getDescription()).isEqualTo("Unknown");
  }

  @Test
  public void addressTypeToStringIsSameAsDecription() {
    assertThat(Address.Type.BILLING.toString()).isEqualTo(Address.Type.BILLING.getDescription());
    assertThat(Address.Type.HOME.toString()).isEqualTo(Address.Type.HOME.getDescription());
    assertThat(Address.Type.MAILING.toString()).isEqualTo(Address.Type.MAILING.getDescription());
    assertThat(Address.Type.OFFICE.toString()).isEqualTo(Address.Type.OFFICE.getDescription());
    assertThat(Address.Type.PO_BOX.toString()).isEqualTo(Address.Type.PO_BOX.getDescription());
    assertThat(Address.Type.WORK.toString()).isEqualTo(Address.Type.WORK.getDescription());
    assertThat(Address.Type.UNKNOWN.toString()).isEqualTo(Address.Type.UNKNOWN.getDescription());
  }

  @Test
  public void addressTypeValueOfAbbreviationIsCorrect() {
    stream(Address.Type.values()).forEach(addressType ->
      assertThat(Address.Type.valueOfAbbreviation(addressType.getAbbreviation())).isSameAs(addressType));
  }
}
