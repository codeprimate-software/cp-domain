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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Optional;

import org.cp.domain.geo.enums.Country;
import org.cp.elements.lang.Visitor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * Unit tests for {@link Address}.
 *
 * @author John Blum
 * @see org.junit.Test
 * @see org.mockito.Mock
 * @see org.mockito.Mockito
 * @see org.cp.domain.geo.enums.Country
 * @see org.cp.domain.geo.model.Address
 * @since 1.0.0
 */
@RunWith(MockitoJUnitRunner.class)
public class AddressTests {

  @Mock
  private Address mockAddress;

  @SuppressWarnings("unused")
  private Address mockAddress(Street street, City city, PostalCode postalCode) {
    return mockAddress(street, null, city, postalCode);
  }

  private Address mockAddress(Street street, Unit unit, City city, PostalCode postalCode) {
    return mockAddress(street, unit, city, postalCode, Country.localCountry());
  }

  private Address mockAddress(Street street, Unit unit, City city, PostalCode postalCode, Country country) {

    Address mockAddress = mock(Address.class);

    when(mockAddress.getStreet()).thenReturn(street);
    when(mockAddress.getUnit()).thenReturn(Optional.ofNullable(unit));
    when(mockAddress.getCity()).thenReturn(city);
    when(mockAddress.getPostalCode()).thenReturn(postalCode);
    when(mockAddress.getCountry()).thenReturn(country);

    return mockAddress;
  }

  @Test
  public void setAndGetUnitReturnsEmptyOptional() {

    when(this.mockAddress.getUnit()).thenCallRealMethod();

    assertThat(this.mockAddress.getUnit().isPresent()).isFalse();

    this.mockAddress.setUnit(Unit.of("123"));

    assertThat(this.mockAddress.getUnit().isPresent()).isFalse();

    this.mockAddress.setUnit(null);

    assertThat(this.mockAddress.getUnit().isPresent()).isFalse();
  }

  @Test
  public void setAndGetTypeReturnsAddressTypeUnknown() {

    when(this.mockAddress.getType()).thenCallRealMethod();

    assertThat(this.mockAddress.getType().orElse(null)).isEqualTo(Address.Type.UNKNOWN);

    this.mockAddress.setType(Address.Type.HOME);

    assertThat(this.mockAddress.getType().orElse(null)).isEqualTo(Address.Type.UNKNOWN);

    this.mockAddress.setType(null);

    assertThat(this.mockAddress.getType().orElse(null)).isEqualTo(Address.Type.UNKNOWN);

    this.mockAddress.setType(Address.Type.PO_BOX);

    assertThat(this.mockAddress.getType().orElse(null)).isEqualTo(Address.Type.UNKNOWN);
  }

  @Test
  public void isBillingAddressReturnsTrue() {

    when(this.mockAddress.getType()).thenReturn(Optional.of(Address.Type.BILLING));
    when(this.mockAddress.isBilling()).thenCallRealMethod();

    assertThat(this.mockAddress.isBilling()).isTrue();

    verify(this.mockAddress, times(1)).getType();
  }

  @Test
  public void isBillingAddressReturnsFalse() {

    Arrays.stream(Address.Type.values())
      .filter(addressType -> !Address.Type.BILLING.equals(addressType))
      .forEach(addressType -> {

        when(this.mockAddress.getType()).thenReturn(Optional.of(addressType));
        when(this.mockAddress.isBilling()).thenCallRealMethod();

        assertThat(this.mockAddress.isBilling()).isFalse();

        verify(this.mockAddress, atLeastOnce()).getType();
      });
  }

  @Test
  public void isHomeAddressReturnsTrue() {

    when(this.mockAddress.getType()).thenReturn(Optional.of(Address.Type.HOME));
    when(this.mockAddress.isHome()).thenCallRealMethod();

    assertThat(this.mockAddress.isHome()).isTrue();

    verify(this.mockAddress, times(1)).getType();
  }

  @Test
  public void isHomeAddressReturnsFalse() {

    Arrays.stream(Address.Type.values())
      .filter(addressType -> !Address.Type.HOME.equals(addressType))
      .forEach(addressType -> {

        when(this.mockAddress.getType()).thenReturn(Optional.of(addressType));
        when(this.mockAddress.isHome()).thenCallRealMethod();

        assertThat(this.mockAddress.isHome()).isFalse();

        verify(this.mockAddress, atLeastOnce()).getType();
      });
  }

  @Test
  public void isMailingAddressReturnsTrue() {

    when(this.mockAddress.getType()).thenReturn(Optional.of(Address.Type.MAILING));
    when(this.mockAddress.isMailing()).thenCallRealMethod();

    assertThat(this.mockAddress.isMailing()).isTrue();

    verify(this.mockAddress, times(1)).getType();
  }

  @Test
  public void isMailingAddressReturnsFalse() {

    Arrays.stream(Address.Type.values())
      .filter(addressType -> !Address.Type.MAILING.equals(addressType))
      .forEach(addressType -> {

        when(this.mockAddress.getType()).thenReturn(Optional.of(addressType));
        when(this.mockAddress.isMailing()).thenCallRealMethod();

        assertThat(this.mockAddress.isMailing()).isFalse();

        verify(this.mockAddress, atLeastOnce()).getType();
      });
  }

  @Test
  public void isOfficeAddressReturnsTrue() {

    when(this.mockAddress.getType()).thenReturn(Optional.of(Address.Type.OFFICE));
    when(this.mockAddress.isOffice()).thenCallRealMethod();

    assertThat(this.mockAddress.isOffice()).isTrue();

    verify(this.mockAddress, times(1)).getType();
  }

  @Test
  public void isOfficeAddressReturnsFalse() {

    Arrays.stream(Address.Type.values())
      .filter(addressType -> !Address.Type.OFFICE.equals(addressType))
      .forEach(addressType -> {

        when(this.mockAddress.getType()).thenReturn(Optional.of(addressType));
        when(this.mockAddress.isOffice()).thenCallRealMethod();

        assertThat(this.mockAddress.isOffice()).isFalse();

        verify(this.mockAddress, atLeastOnce()).getType();
      });
  }

  @Test
  public void isPoBoxAddressReturnsTrue() {

    when(this.mockAddress.getType()).thenReturn(Optional.of(Address.Type.PO_BOX));
    when(this.mockAddress.isPoBox()).thenCallRealMethod();

    assertThat(this.mockAddress.isPoBox()).isTrue();

    verify(this.mockAddress, times(1)).getType();
  }

  @Test
  public void isPoBoxAddressReturnsFalse() {

    Arrays.stream(Address.Type.values())
      .filter(addressType -> !Address.Type.PO_BOX.equals(addressType))
      .forEach(addressType -> {

        when(this.mockAddress.getType()).thenReturn(Optional.of(addressType));
        when(this.mockAddress.isPoBox()).thenCallRealMethod();

        assertThat(this.mockAddress.isPoBox()).isFalse();

        verify(this.mockAddress, atLeastOnce()).getType();
      });
  }

  @Test
  public void isWorkAddressReturnsTrue() {

    when(this.mockAddress.getType()).thenReturn(Optional.of(Address.Type.WORK));
    when(this.mockAddress.isWork()).thenCallRealMethod();

    assertThat(this.mockAddress.isWork()).isTrue();

    verify(this.mockAddress, times(1)).getType();
  }

  @Test
  public void isWorkAddressReturnsFalse() {

    Arrays.stream(Address.Type.values())
      .filter(addressType -> !Address.Type.WORK.equals(addressType))
      .forEach(addressType -> {

        when(this.mockAddress.getType()).thenReturn(Optional.of(addressType));
        when(this.mockAddress.isWork()).thenCallRealMethod();

        assertThat(this.mockAddress.isWork()).isFalse();

        verify(this.mockAddress, atLeastOnce()).getType();
      });
  }

  @Test
  public void acceptVisitsAddress() {

    Visitor mockVisitor = mock(Visitor.class);

    doCallRealMethod().when(this.mockAddress).accept(any(Visitor.class));

    this.mockAddress.accept(mockVisitor);

    verify(mockVisitor, times(1)).visit(eq(this.mockAddress));
  }

  @Test
  public void compareToEqualAddressReturnsZero() {

    Address mockAddressOne = mockAddress(Street.of(100, "Main").as(Street.Type.STREET),
      Unit.of("100").as(Unit.Type.OFFICE), City.of("Portland"), PostalCode.of("97205"));

    Address mockAddressTwo = mockAddress(Street.of(100, "Main").as(Street.Type.STREET),
      Unit.of("100").as(Unit.Type.OFFICE), City.of("Portland"), PostalCode.of("97205"));

    when(mockAddressOne.compareTo(any(Address.class))).thenCallRealMethod();

    assertThat(mockAddressOne).isNotSameAs(mockAddressTwo);
    assertThat(mockAddressOne.compareTo(mockAddressTwo)).isEqualTo(0);

    Arrays.asList(mockAddressOne, mockAddressTwo).forEach(mockAddress -> {
      verify(mockAddress, times(1)).getCountry();
      verify(mockAddress, times(1)).getCity();
      verify(mockAddress, times(1)).getPostalCode();
      verify(mockAddress, times(1)).getStreet();
      verify(mockAddress, times(1)).getUnit();
    });
  }

  @Test
  public void compareToGreaterAddressReturnsNegativeInt() {

    Address mockAddressOne = mockAddress(Street.of(100, "Main").as(Street.Type.STREET),
      Unit.of("100").as(Unit.Type.OFFICE), City.of("Ashland"), PostalCode.of("97205"));

    Address mockAddressTwo = mockAddress(Street.of(200, "One").as(Street.Type.WAY),
      Unit.of("200").as(Unit.Type.SUITE), City.of("Portland"), PostalCode.of("98765"));

    when(mockAddressOne.compareTo(any(Address.class))).thenCallRealMethod();

    assertThat(mockAddressOne).isNotSameAs(mockAddressTwo);
    assertThat(mockAddressOne.compareTo(mockAddressTwo)).isLessThan(0);

    Arrays.asList(mockAddressOne, mockAddressTwo).forEach(mockAddress -> {
      verify(mockAddress, times(1)).getCountry();
      verify(mockAddress, times(1)).getCity();
      verify(mockAddress, times(1)).getPostalCode();
      verify(mockAddress, times(1)).getStreet();
      verify(mockAddress, times(1)).getUnit();
    });
  }

  @Test
  public void compareToLesserAddressReturnsPositiveInt() {

    Address mockAddressOne = mockAddress(Street.of(200, "Main").as(Street.Type.STREET),
      Unit.of("10").as(Unit.Type.SUITE), City.of("Portland"), PostalCode.of("97205"));

    Address mockAddressTwo = mockAddress(Street.of(100, "Main").as(Street.Type.STREET),
      Unit.of("100").as(Unit.Type.APARTMENT), City.of("Portland"), PostalCode.of("97205"));

    when(mockAddressOne.compareTo(any(Address.class))).thenCallRealMethod();

    assertThat(mockAddressOne).isNotSameAs(mockAddressTwo);
    assertThat(mockAddressOne.compareTo(mockAddressTwo)).isGreaterThan(0);

    Arrays.asList(mockAddressOne, mockAddressTwo).forEach(mockAddress -> {
      verify(mockAddress, times(1)).getCountry();
      verify(mockAddress, times(1)).getCity();
      verify(mockAddress, times(1)).getPostalCode();
      verify(mockAddress, times(1)).getStreet();
      verify(mockAddress, times(1)).getUnit();
    });
  }

  @Test
  public void validateValidAddress() {

    Address mockAddress = mockAddress(Street.of(100, "Main").as(Street.Type.STREET),
      null, City.of("Portland"), PostalCode.of("97205"), Country.UNITED_STATES_OF_AMERICA);

    when(mockAddress.validate()).thenCallRealMethod();

    assertThat(mockAddress.validate()).isSameAs(mockAddress);

    verify(mockAddress, times(1)).getStreet();
    verify(mockAddress, times(1)).getCity();
    verify(mockAddress, times(1)).getPostalCode();
    verify(mockAddress, times(1)).getCountry();
    verify(mockAddress, never()).getType();
    verify(mockAddress, never()).getUnit();
  }

  @Test(expected = IllegalStateException.class)
  public void validateFailsOnStreet() {

    Address mockAddress = mockAddress(null, Unit.EMPTY, City.of("Portland"), PostalCode.of("97205"));

    when(mockAddress.validate()).thenCallRealMethod();

    try {
      mockAddress.validate();
    }
    catch (IllegalStateException expected) {

      assertThat(expected).hasMessage("Street is required");
      assertThat(expected).hasNoCause();

      throw expected;
    }
    finally {
      verify(mockAddress, times(1)).getStreet();
      verify(mockAddress, never()).getCity();
      verify(mockAddress, never()).getPostalCode();
      verify(mockAddress, never()).getCountry();
      verify(mockAddress, never()).getType();
      verify(mockAddress, never()).getUnit();
    }
  }

  @Test(expected = IllegalStateException.class)
  public void validateFailsOnCity() {

    Address mockAddress = mockAddress(Street.of(100, "Main").as(Street.Type.STREET),
      Unit.EMPTY, null, PostalCode.of("97205"));

    when(mockAddress.validate()).thenCallRealMethod();

    try {
      mockAddress.validate();
    }
    catch (IllegalStateException expected) {

      assertThat(expected).hasMessage("City is required");
      assertThat(expected).hasNoCause();

      throw expected;
    }
    finally {
      verify(mockAddress, times(1)).getStreet();
      verify(mockAddress, times(1)).getCity();
      verify(mockAddress, never()).getPostalCode();
      verify(mockAddress, never()).getCountry();
      verify(mockAddress, never()).getType();
      verify(mockAddress, never()).getUnit();
    }
  }

  @Test(expected = IllegalStateException.class)
  public void validateFailsOnPostalCode() {

    Address mockAddress = mockAddress(Street.of(100, "Main"), null,
      City.of("Portland"), null);

    when(mockAddress.validate()).thenCallRealMethod();

    try {
      mockAddress.validate();
    }
    catch (IllegalStateException expected) {

      assertThat(expected).hasMessage("Postal Code is required");
      assertThat(expected).hasNoCause();

      throw expected;
    }
    finally {
      verify(mockAddress, times(1)).getStreet();
      verify(mockAddress, times(1)).getCity();
      verify(mockAddress, times(1)).getPostalCode();
      verify(mockAddress, never()).getCountry();
      verify(mockAddress, never()).getType();
      verify(mockAddress, never()).getUnit();
    }
  }

  @Test(expected = IllegalStateException.class)
  public void validateFailsOnCountry() {

    Address mockAddress = mockAddress(Street.of(100, "Main"),null,
      City.of("Portland"), PostalCode.of("97205"), null);

    when(mockAddress.validate()).thenCallRealMethod();

    try {
      mockAddress.validate();
    }
    catch (IllegalStateException expected) {

      assertThat(expected).hasMessage("Country is required");
      assertThat(expected).hasNoCause();

      throw expected;
    }
    finally {
      verify(mockAddress, times(1)).getStreet();
      verify(mockAddress, times(1)).getCity();
      verify(mockAddress, times(1)).getPostalCode();
      verify(mockAddress, times(1)).getCountry();
      verify(mockAddress, never()).getType();
      verify(mockAddress, never()).getUnit();
    }
  }

  @Test
  public void asTypeCallsSetTypeAndReturnsThis() {

    when(this.mockAddress.as(any(Address.Type.class))).thenCallRealMethod();

    assertThat(this.mockAddress.<Address>as(Address.Type.HOME)).isSameAs(this.mockAddress);

    verify(this.mockAddress, times(1)).setType(eq(Address.Type.HOME));
  }

  @Test
  public void asBillingCallsSetTypeWithAddressTypeBillingAndReturnsThis() {

    when(this.mockAddress.as(any(Address.Type.class))).thenCallRealMethod();
    when(this.mockAddress.asBilling()).thenCallRealMethod();

    assertThat(this.mockAddress.<Address>asBilling()).isSameAs(this.mockAddress);

    verify(this.mockAddress, times(1)).setType(eq(Address.Type.BILLING));
  }

  @Test
  public void asHomeCallsSetTypeWithAddressTypeHomeAndReturnsThis() {

    when(this.mockAddress.as(any(Address.Type.class))).thenCallRealMethod();
    when(this.mockAddress.asHome()).thenCallRealMethod();

    assertThat(this.mockAddress.<Address>asHome()).isSameAs(this.mockAddress);

    verify(this.mockAddress, times(1)).setType(eq(Address.Type.HOME));
  }

  @Test
  public void asMailingCallsSetTypeWithAddressTypeMailingAndReturnsThis() {

    when(this.mockAddress.as(any(Address.Type.class))).thenCallRealMethod();
    when(this.mockAddress.asMailing()).thenCallRealMethod();

    assertThat(this.mockAddress.<Address>asMailing()).isSameAs(this.mockAddress);

    verify(this.mockAddress, times(1)).setType(eq(Address.Type.MAILING));
  }

  @Test
  public void asOfficeCallsSetTypeWithAddressTypeOfficeAndReturnsThis() {

    when(this.mockAddress.as(any(Address.Type.class))).thenCallRealMethod();
    when(this.mockAddress.asOffice()).thenCallRealMethod();

    assertThat(this.mockAddress.<Address>asOffice()).isSameAs(this.mockAddress);

    verify(this.mockAddress, times(1)).setType(eq(Address.Type.OFFICE));
  }

  @Test
  public void asPoBoxCallsSetTypeWithAddressTypePoBoxAndReturnsThis() {

    when(this.mockAddress.as(any(Address.Type.class))).thenCallRealMethod();
    when(this.mockAddress.asPoBox()).thenCallRealMethod();

    assertThat(this.mockAddress.<Address>asPoBox()).isSameAs(this.mockAddress);

    verify(this.mockAddress, times(1)).setType(eq(Address.Type.PO_BOX));
  }

  @Test
  public void asWorkCallsSetTypeWithAddressTypeWorkAndReturnsThis() {

    when(this.mockAddress.as(any(Address.Type.class))).thenCallRealMethod();
    when(this.mockAddress.asWork()).thenCallRealMethod();

    assertThat(this.mockAddress.<Address>asWork()).isSameAs(this.mockAddress);

    verify(this.mockAddress, times(1)).setType(eq(Address.Type.WORK));
  }

  @Test
  public void onStreetCallsSetStreetReturnsThis() {

    Street expectedStreet = Street.of(100, "Main").as(Street.Type.STREET);

    when(this.mockAddress.on(any(Street.class))).thenCallRealMethod();

    assertThat(this.mockAddress.<Address>on(expectedStreet)).isSameAs(this.mockAddress);

    verify(this.mockAddress, times(1)).setStreet(eq(expectedStreet));
  }

  @Test
  public void inUnitCallsSetUnitReturnsThis() {

    Unit expectedUnit = Unit.of("123").as(Unit.Type.OFFICE);

    when(this.mockAddress.in(any(Unit.class))).thenCallRealMethod();

    assertThat(this.mockAddress.<Address>in(expectedUnit)).isSameAs(this.mockAddress);

    verify(this.mockAddress, times(1)).setUnit(eq(expectedUnit));
  }

  @Test
  public void inCityCallsSetCityReturnsThis() {

    City expectedCity = City.of("Portland");

    when(this.mockAddress.in(any(City.class))).thenCallRealMethod();

    assertThat(this.mockAddress.<Address>in(expectedCity)).isSameAs(this.mockAddress);

    verify(this.mockAddress, times(1)).setCity(eq(expectedCity));
  }

  @Test
  public void inPostalCodeCallsSetPostalCodeReturnsThis() {

    PostalCode expectedPostalCode = PostalCode.of("12345");

    when(this.mockAddress.in(any(PostalCode.class))).thenCallRealMethod();

    assertThat(this.mockAddress.<Address>in(expectedPostalCode)).isSameAs(this.mockAddress);

    verify(this.mockAddress, times(1)).setPostalCode(eq(expectedPostalCode));
  }

  @Test
  public void inCountryCallsSetCountryReturnsThis() {

    Country expectedCountry = Country.UNITED_KINGDOM;

    when(this.mockAddress.in(any(Country.class))).thenCallRealMethod();

    assertThat(this.mockAddress.<Address>in(expectedCountry)).isSameAs(this.mockAddress);

    verify(this.mockAddress, times(1)).setCountry(eq(expectedCountry));
  }

  @Test
  public void inLocalCountry() {

    when(this.mockAddress.in(any(Country.class))).thenCallRealMethod();
    when(this.mockAddress.inLocalCountry()).thenCallRealMethod();

    assertThat(this.mockAddress.<Address>inLocalCountry()).isSameAs(this.mockAddress);

    verify(this.mockAddress, times(1)).in(eq(Country.localCountry()));
    verify(this.mockAddress, times(1)).setCountry(eq(Country.localCountry()));
  }

  @Test
  public void withCoordinatesCallsSetCoordinatesReturnsThis() {

    Coordinates expectedCoordinates = Coordinates.of(1.012489753, 10.2501248);

    when(this.mockAddress.with(any(Coordinates.class))).thenCallRealMethod();

    assertThat(this.mockAddress.<Address>with(expectedCoordinates)).isSameAs(this.mockAddress);

    verify(this.mockAddress, times(1)).setCoordinates(eq(expectedCoordinates));
  }
}
