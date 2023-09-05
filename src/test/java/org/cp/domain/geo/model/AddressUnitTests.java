/*
 * Copyright 2017-Present Author or Authors.
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
import static org.cp.elements.lang.ThrowableAssertions.assertThatUnsupportedOperationException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.util.Arrays;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import org.cp.domain.geo.enums.Country;
import org.cp.elements.lang.ThrowableOperation;
import org.cp.elements.lang.Visitor;
import org.mockito.InOrder;
import org.mockito.Mockito;

/**
 * Unit Tests for {@link Address}.
 *
 * @author John Blum
 * @see org.junit.jupiter.api.Test
 * @see org.mockito.Mockito
 * @see org.cp.domain.geo.model.Address
 * @see org.cp.domain.geo.model.BaseAddressUnitTests
 * @since 0.1.0
 */
@SuppressWarnings("unused")
public class AddressUnitTests extends BaseAddressUnitTests {

  @BeforeAll
  static void beforeTests() {
    Locale.setDefault(Locale.CANADA);
  }

  @Test
  void buildAddress() {

    Street mockStreet = mock(Street.class);
    City mockCity = mock(City.class);
    PostalCode mockPostalCode = mock(PostalCode.class);

    Address.Builder<?> addressBuilder = Address.builder();

    assertThat(addressBuilder).isNotNull();

    Address address = addressBuilder
      .on(mockStreet)
      .in(mockCity)
      .in(mockPostalCode)
      .build();

    assertAddress(address, mockStreet, mockCity, mockPostalCode);
    assertAddressWithNoCoordinatesTypeOrUnit(address);

    verifyNoInteractions(mockStreet, mockCity, mockPostalCode);
  }

  @Test
  void buildAddressInCountry() {

    Street mockStreet = mock(Street.class);
    City mockCity = mock(City.class);
    PostalCode mockPostalCode = mock(PostalCode.class);

    Address.Builder<?> addressBuilder = Address.builder(Country.CANADA);

    assertThat(addressBuilder).isNotNull();

    Address address = addressBuilder
      .on(mockStreet)
      .in(mockCity)
      .in(mockPostalCode)
      .build();

    assertAddress(address, mockStreet, mockCity, mockPostalCode, Country.CANADA);
    assertAddressWithNoCoordinatesTypeOrUnit(address);

    verifyNoInteractions(mockStreet, mockCity, mockPostalCode);
  }

  @Test
  void fromAddress() {

    Street mockStreet = mock(Street.class);
    City mockCity = mock(City.class);
    PostalCode mockPostalCode = mock(PostalCode.class);
    Coordinates mockCoordinates = mock(Coordinates.class);
    Unit mockUnit = mock(Unit.class);

    Address mockAddress = mockAddress(mockStreet, mockCity, mockPostalCode, Country.GERMANY);

    doReturn(Optional.of(mockCoordinates)).when(mockAddress).getCoordinates();
    doReturn(Optional.of(Address.Type.PO_BOX)).when(mockAddress).getType();
    doReturn(Optional.of(mockUnit)).when(mockAddress).getUnit();

    Address addressCopy = Address.from(mockAddress);

    assertAddress(addressCopy, mockStreet, mockCity, mockPostalCode, Country.GERMANY);
    assertAddressWithCoordinatesTypeAndUnit(addressCopy, mockCoordinates, Address.Type.PO_BOX, mockUnit);
    assertThat(addressCopy).isNotSameAs(mockAddress);

    verify(mockAddress, times(1)).getStreet();
    verify(mockAddress, times(1)).getCity();
    verify(mockAddress, times(1)).getPostalCode();
    verify(mockAddress, times(1)).getCountry();
    verify(mockAddress, times(1)).getCoordinates();
    verify(mockAddress, times(1)).getType();
    verify(mockAddress, times(1)).getUnit();
    verifyNoInteractions(mockStreet, mockCity, mockPostalCode, mockCoordinates, mockUnit);
    verifyNoMoreInteractions(mockAddress);
  }

  @Test
  void fromNullAddress() {

    assertThatIllegalArgumentException()
      .isThrownBy(() -> Address.from(null))
      .withMessage("Address to copy is required")
      .withNoCause();
  }

  @Test
  void ofStreetCityAndPostalCode() {

    Street mockStreet = mock(Street.class);
    City mockCity = mock(City.class);
    PostalCode mockPostalCode = mock(PostalCode.class);

    Address address = Address.of(mockStreet, mockCity, mockPostalCode);

    assertAddress(address, mockStreet, mockCity, mockPostalCode);
    assertAddressWithNoCoordinatesTypeOrUnit(address);

    verifyNoInteractions(mockStreet, mockCity, mockPostalCode);
  }

  @Test
  void ofStreetCityPostalCodeAndCountry() {

    Street mockStreet = mock(Street.class);
    City mockCity = mock(City.class);
    PostalCode mockPostalCode = mock(PostalCode.class);

    Address address = Address.of(mockStreet, mockCity, mockPostalCode, Country.MEXICO);

    assertAddress(address, mockStreet, mockCity, mockPostalCode, Country.MEXICO);
    assertAddressWithNoCoordinatesTypeOrUnit(address);

    verifyNoInteractions(mockStreet, mockCity, mockPostalCode);
  }

  @Test
  void ofNullStreet() {

    City mockCity = mock(City.class);
    PostalCode mockPostalCode = mock(PostalCode.class);

    assertThatIllegalArgumentException()
      .isThrownBy(() -> Address.of(null, mockCity, mockPostalCode))
      .withMessage("Street is required")
      .withNoCause();

    verifyNoInteractions(mockCity, mockPostalCode);
  }

  @Test
  void ofNullCity() {

    Street mockStreet = mock(Street.class);
    PostalCode mockPostalCode = mock(PostalCode.class);

    assertThatIllegalArgumentException()
      .isThrownBy(() -> Address.of(mockStreet, null, mockPostalCode))
      .withMessage("City is required")
      .withNoCause();

    verifyNoInteractions(mockStreet, mockPostalCode);
  }

  @Test
  void ofNullPostalCode() {

    Street mockStreet = mock(Street.class);
    City mockCity = mock(City.class);

    assertThatIllegalArgumentException()
      .isThrownBy(() -> Address.of(mockStreet, mockCity, null))
      .withMessage("PostalCode is required")
      .withNoCause();

    verifyNoInteractions(mockStreet, mockCity);
  }

  @Test
  void ofNullCountry() {

    Street mockStreet = mock(Street.class);
    City mockCity = mock(City.class);
    PostalCode mockPostalCode = mock(PostalCode.class);

    Address address = Address.of(mockStreet, mockCity, mockPostalCode, null);

    assertAddress(address, mockStreet, mockCity, mockPostalCode);
    assertAddressWithNoCoordinatesTypeOrUnit(address);

    verifyNoInteractions(mockStreet, mockCity, mockPostalCode);
  }

  @Test
  void addressCoordinatesIsEmpty() {

    Address mockAddress = mock(Address.class);

    doCallRealMethod().when(mockAddress).getCoordinates();

    Optional<Coordinates> coordinates = mockAddress.getCoordinates();

    assertThat(coordinates).isNotNull();
    assertThat(coordinates).isNotPresent();

    verify(mockAddress, times(1)).getCoordinates();
    verifyNoMoreInteractions(mockAddress);
  }

  @Test
  void setCoordinatesThrowsUnsupportedOperationException() {

    Address mockAddress = mock(Address.class);
    Coordinates mockCoordinates = mock(Coordinates.class);

    doCallRealMethod().when(mockAddress).setCoordinates(any());

    assertThatUnsupportedOperationException()
      .isThrownBy(ThrowableOperation.fromRunnable(() -> mockAddress.setCoordinates(mockCoordinates)))
      .havingMessage("Setting Coordinates on a Locatable object of type [%s] is not supported",
        mockAddress.getClass().getName())
      .withNoCause();

    verify(mockAddress, times(1)).setCoordinates(eq(mockCoordinates));
    verifyNoMoreInteractions(mockAddress);
    verifyNoInteractions(mockCoordinates);
  }

  @Test
  void addressTypeIsUnknown() {

    Address mockAddress = mock(Address.class);

    doCallRealMethod().when(mockAddress).getType();

    Optional<Address.Type> addressType = mockAddress.getType();

    assertThat(addressType).isNotNull();
    assertThat(addressType).isPresent();
    assertThat(addressType.orElse(null)).isEqualTo(Address.Type.UNKNOWN);

    verify(mockAddress, times(1)).getType();
    verifyNoMoreInteractions(mockAddress);
  }

  @Test
  void setTypeThrowsUnsupportedOperationException() {

    Address mockAddress = mock(Address.class);

    doCallRealMethod().when(mockAddress).setType(any());

    assertThatUnsupportedOperationException()
      .isThrownBy(ThrowableOperation.fromRunnable(() -> mockAddress.setType(Address.Type.HOME)))
      .havingMessage("Setting Address.Type for Address of type [%s] is not supported",
        mockAddress.getClass().getName())
      .withNoCause();

    verify(mockAddress, times(1)).setType(eq(Address.Type.HOME));
    verifyNoMoreInteractions(mockAddress);
  }

  @Test
  void addressUnitIsEmpty() {

    Address mockAddress = mock(Address.class);

    doCallRealMethod().when(mockAddress).getUnit();

    Optional<Unit> unit = mockAddress.getUnit();

    assertThat(unit).isNotNull();
    assertThat(unit).isNotPresent();

    verify(mockAddress, times(1)).getUnit();
    verifyNoMoreInteractions(mockAddress);
  }

  @Test
  void setUnitThrowsUnsupportedOperationException() {

    Address mockAddress = mock(Address.class);

    Unit unit = Unit.of("123");

    doCallRealMethod().when(mockAddress).setUnit(any());

    assertThatUnsupportedOperationException()
      .isThrownBy(ThrowableOperation.fromRunnable(() -> mockAddress.setUnit(unit)))
      .havingMessage("Setting Unit for Address of type [%s] is not supported", mockAddress.getClass().getName())
      .withNoCause();

    verify(mockAddress, times(1)).setUnit(unit);
    verifyNoMoreInteractions(mockAddress);
  }

  private void testIsAddressType(Address.Type expectedAddressType, Function<Address, Boolean> isAddressTypeFunction,
      Consumer<Address> arrange, Consumer<Address> verify) {

    Address mockAddress = mock(Address.class);

    Arrays.stream(Address.Type.values()).forEach(addressType -> {

      Mockito.reset(mockAddress);

      doReturn(Optional.of(addressType)).when(mockAddress).getType();

      arrange.accept(mockAddress);

      assertThat(isAddressTypeFunction.apply(mockAddress)).isEqualTo(expectedAddressType.equals(addressType));

      verify.accept(mockAddress);
      verify(mockAddress, times(1)).getType();
      verifyNoMoreInteractions(mockAddress);
    });
  }

  @Test
  void isBillingAddress() {

    testIsAddressType(Address.Type.BILLING, Address::isBilling,
      mockAddress -> doCallRealMethod().when(mockAddress).isBilling(),
      mockAddress -> verify(mockAddress, times(1)).isBilling());
  }

  @Test
  void isHomeAddress() {

    testIsAddressType(Address.Type.HOME, Address::isHome,
      mockAddress -> doCallRealMethod().when(mockAddress).isHome(),
      mockAddress -> verify(mockAddress, times(1)).isHome());
  }

  @Test
  void isMailingAddress() {

    testIsAddressType(Address.Type.MAILING, Address::isMailing,
      mockAddress -> doCallRealMethod().when(mockAddress).isMailing(),
      mockAddress -> verify(mockAddress, times(1)).isMailing());
  }

  @Test
  void isPoBoxAddress() {

    testIsAddressType(Address.Type.PO_BOX, Address::isPoBox,
      mockAddress -> doCallRealMethod().when(mockAddress).isPoBox(),
      mockAddress -> verify(mockAddress, times(1)).isPoBox());
  }

  @Test
  void asAddressTypeCallsSetType() {

    Address mockAddress = mock(Address.class);

    doCallRealMethod().when(mockAddress).as(any());
    doNothing().when(mockAddress).setType(any());

    assertThat(mockAddress.<Address>as(Address.Type.RESIDENTIAL)).isSameAs(mockAddress);

    verify(mockAddress, times(1)).as(eq(Address.Type.RESIDENTIAL));
    verify(mockAddress, times(1)).setType(eq(Address.Type.RESIDENTIAL));
    verifyNoMoreInteractions(mockAddress);
  }

  private void testAsAddressType(Address.Type expectedAddressType, Function<Address, Address> arrangeAct,
    Consumer<Address> verify) {

    Address mockAddress = mock(Address.class);

    doCallRealMethod().when(mockAddress).as(any(Address.Type.class));
    doNothing().when(mockAddress).setType(any());

    assertThat(arrangeAct.apply(mockAddress)).isSameAs(mockAddress);

    verify.accept(mockAddress);
    verify(mockAddress, times(1)).as(eq(expectedAddressType));
    verify(mockAddress, times(1)).setType(eq(expectedAddressType));
    verifyNoMoreInteractions(mockAddress);
  }

  @Test
  void asBillingAddress() {

    testAsAddressType(Address.Type.BILLING, mockAddress -> {
      doCallRealMethod().when(mockAddress).asBilling();
      return mockAddress.asBilling();
    }, mockAddress -> verify(mockAddress, times(1)).asBilling());
  }

  @Test
  void asHomeAddress() {

    testAsAddressType(Address.Type.HOME, mockAddress -> {
      doCallRealMethod().when(mockAddress).asHome();
      return mockAddress.asHome();
    }, mockAddress -> verify(mockAddress, times(1)).asHome());
  }

  @Test
  void asMailingAddress() {

    testAsAddressType(Address.Type.MAILING, mockAddress -> {
      doCallRealMethod().when(mockAddress).asMailing();
      return mockAddress.asMailing();
    }, mockAddress -> verify(mockAddress, times(1)).asMailing());
  }

  @Test
  void asPoBoxAddress() {

    testAsAddressType(Address.Type.PO_BOX, mockAddress -> {
      doCallRealMethod().when(mockAddress).asPoBox();
      return mockAddress.asPoBox();
    }, mockAddress -> verify(mockAddress, times(1)).asPoBox());
  }

  @Test
  void acceptsVisitor() {

    Address mockAddress = mock(Address.class);
    Visitor mockVisitor = mock(Visitor.class);

    doCallRealMethod().when(mockAddress).accept(any());

    mockAddress.accept(mockVisitor);

    verify(mockAddress, times(1)).accept(eq(mockVisitor));
    verify(mockVisitor, times(1)).visit(eq(mockAddress));
    verifyNoMoreInteractions(mockAddress, mockVisitor);
  }

  @Test
  void compareToEqualAddressReturnsZero() {

    Street street = Street.of(100, "Main");
    Unit unit = Unit.of("A");
    City city = City.of("South Park");
    PostalCode postalCode = PostalCode.of("12345");
    Country georgia = Country.GEORGIA;

    Address mockAddressOne = withUnit(mockAddress(street, city, postalCode, georgia), unit);
    Address mockAddressTwo = withUnit(mockAddress(street, city, postalCode, georgia), unit);

    doCallRealMethod().when(mockAddressOne).compareTo(eq(mockAddressTwo));
    doCallRealMethod().when(mockAddressTwo).compareTo(eq(mockAddressOne));

    assertThat(mockAddressOne).isNotSameAs(mockAddressTwo);
    assertThat(mockAddressOne.compareTo(mockAddressTwo)).isZero();
    assertThat(mockAddressTwo.compareTo(mockAddressOne)).isZero();
  }

  @Test
  @SuppressWarnings("all")
  void compareToSelfReturnsZero() {

    Address mockAddress = withUnit(mockAddress(Street.of(100, "Main"),
        City.of("Portland"), PostalCode.of("97205")), Unit.of("101"));

    doCallRealMethod().when(mockAddress).compareTo(any());

    assertThat(mockAddress.compareTo(mockAddress)).isZero();

    InOrder order = inOrder(mockAddress);

    order.verify(mockAddress, times(1)).compareTo(eq(mockAddress));
    order.verify(mockAddress, times(2)).getCountry();
    order.verify(mockAddress, times(2)).getCity();
    order.verify(mockAddress, times(2)).getPostalCode();
    order.verify(mockAddress, times(2)).getStreet();
    order.verify(mockAddress, times(2)).getUnit();

    verifyNoMoreInteractions(mockAddress);
  }

  @Test
  void compareToUnequalAddressReturnsNonZero() {

    Address mockPortlandAddress =
      mockAddress(Street.of(100, "Main"), City.of("Portland"), PostalCode.of("54321"));

    Address mockSeattleAddress =
      mockAddress(Street.of(100, "Main"), City.of("Seattle"), PostalCode.of("12345"));

    doCallRealMethod().when(mockPortlandAddress).compareTo(eq(mockSeattleAddress));
    doCallRealMethod().when(mockSeattleAddress).compareTo(eq(mockPortlandAddress));

    assertThat(mockPortlandAddress).isNotSameAs(mockSeattleAddress);
    assertThat(mockPortlandAddress.compareTo(mockSeattleAddress)).isLessThan(0);
    assertThat(mockSeattleAddress.compareTo(mockPortlandAddress)).isGreaterThan(0);
  }
}
