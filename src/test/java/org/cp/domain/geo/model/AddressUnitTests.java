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
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;
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
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

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
 * @since 0.1.0
 */
public class AddressUnitTests {

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

  private Address withUnit(Address address, Unit unit) {

    doReturn(Optional.ofNullable(unit)).when(address).getUnit();

    return address;
  }

  @Test
  public void fromAddress() {

    Country usa = Country.UNITED_STATES_OF_AMERICA;

    Street mockStreet = mock(Street.class);
    City mockCity = mock(City.class);
    PostalCode mockPostalCode = mock(PostalCode.class);

    Address mockAddress = mockAddress(mockStreet, mockCity, mockPostalCode, usa);
    Address addressCopy = Address.from(mockAddress);

    assertThat(addressCopy).isNotNull();
    assertThat(addressCopy).isNotSameAs(mockAddress);
    assertThat(addressCopy.getStreet()).isEqualTo(mockStreet);
    assertThat(addressCopy.getCity()).isEqualTo(mockCity);
    assertThat(addressCopy.getPostalCode()).isEqualTo(mockPostalCode);
    assertThat(addressCopy.getCountry()).isEqualTo(usa);

    verify(mockAddress, times(1)).getStreet();
    verify(mockAddress, times(1)).getCity();
    verify(mockAddress, times(1)).getPostalCode();
    verify(mockAddress, times(1)).getCountry();
    verifyNoInteractions(mockStreet, mockCity, mockPostalCode);
    verifyNoMoreInteractions(mockAddress);
  }

  @Test
  public void fromNullAddress() {

    assertThatIllegalArgumentException()
      .isThrownBy(() -> Address.from(null))
      .withMessage("Address to copy is required")
      .withNoCause();
  }

  @Test
  public void ofStreetCityAndPostalCode() {

    Street mockStreet = mock(Street.class);
    City mockCity = mock(City.class);
    PostalCode mockPostalCode = mock(PostalCode.class);

    Address address = Address.of(mockStreet, mockCity, mockPostalCode);

    assertThat(address).isNotNull();
    assertThat(address.getId()).isNull();
    assertThat(address.getStreet()).isEqualTo(mockStreet);
    assertThat(address.getCity()).isEqualTo(mockCity);
    assertThat(address.getPostalCode()).isEqualTo(mockPostalCode);
    assertThat(address.getCountry()).isEqualTo(Country.localCountry());

    verifyNoInteractions(mockStreet, mockCity, mockPostalCode);
  }

  @Test
  public void ofStreetCityPostalCodeAndCountry() {

    Street mockStreet = mock(Street.class);
    City mockCity = mock(City.class);
    PostalCode mockPostalCode = mock(PostalCode.class);
    Country canada = Country.CANADA;

    Address address = Address.of(mockStreet, mockCity, mockPostalCode, canada);

    assertThat(address).isNotNull();
    assertThat(address.getId()).isNull();
    assertThat(address.getStreet()).isEqualTo(mockStreet);
    assertThat(address.getCity()).isEqualTo(mockCity);
    assertThat(address.getPostalCode()).isEqualTo(mockPostalCode);
    assertThat(address.getCountry()).isEqualTo(canada);

    verifyNoInteractions(mockStreet, mockCity, mockPostalCode);
  }

  @Test
  public void ofNullStreet() {

    City mockCity = mock(City.class);
    PostalCode mockPostalCode = mock(PostalCode.class);

    assertThatIllegalArgumentException()
      .isThrownBy(() -> Address.of(null, mockCity, mockPostalCode))
      .withMessage("Street is required")
      .withNoCause();

    verifyNoInteractions(mockCity, mockPostalCode);
  }

  @Test
  public void ofNullCity() {

    Street mockStreet = mock(Street.class);
    PostalCode mockPostalCode = mock(PostalCode.class);

    assertThatIllegalArgumentException()
      .isThrownBy(() -> Address.of(mockStreet, null, mockPostalCode))
      .withMessage("City is required")
      .withNoCause();

    verifyNoInteractions(mockStreet, mockPostalCode);
  }

  @Test
  public void ofNullPostalCode() {

    Street mockStreet = mock(Street.class);
    City mockCity = mock(City.class);

    assertThatIllegalArgumentException()
      .isThrownBy(() -> Address.of(mockStreet, mockCity, null))
      .withMessage("Postal Code is required")
      .withNoCause();

    verifyNoInteractions(mockStreet, mockCity);
  }

  @Test
  public void ofNullCountry() {

    Street mockStreet = mock(Street.class);
    City mockCity = mock(City.class);
    PostalCode mockPostalCode = mock(PostalCode.class);

    assertThatIllegalArgumentException()
      .isThrownBy(() -> Address.of(mockStreet, mockCity, mockPostalCode, null))
      .withMessage("Country is required")
      .withNoCause();

    verifyNoInteractions(mockStreet, mockCity, mockPostalCode);
  }

  @Test
  public void constructAddressUsingBuilder() {

    Street mockStreet = mock(Street.class);
    City mockCity = mock(City.class);
    PostalCode mockPostalCode = mock(PostalCode.class);
    Country egypt = Country.EGYPT;

    Address.Builder addressBuilder = Address.newBuilder();

    assertThat(addressBuilder).isNotNull();

    Address address = addressBuilder
      .on(mockStreet)
      .in(mockCity)
      .in(mockPostalCode)
      .in(egypt)
      .build();

    assertThat(address).isNotNull();
    assertThat(address.getStreet()).isEqualTo(mockStreet);
    assertThat(address.getCity()).isEqualTo(mockCity);
    assertThat(address.getPostalCode()).isEqualTo(mockPostalCode);
    assertThat(address.getCountry()).isEqualTo(egypt);

    verifyNoInteractions(mockStreet, mockCity, mockPostalCode);
  }

  @Test
  public void constructAddressUsingBuilderFromAddress() {

    Street mockStreet = mock(Street.class);
    City mockCity = mock(City.class);
    PostalCode mockPostalCode = mock(PostalCode.class);

    Address mockAddress = mockAddress(mockStreet, mockCity, mockPostalCode);
    Address addressCopy = Address.newBuilder().from(mockAddress).build();

    assertThat(addressCopy).isNotNull();
    assertThat(addressCopy).isNotSameAs(mockAddress);
    assertThat(addressCopy.getStreet()).isEqualTo(mockStreet);
    assertThat(addressCopy.getCity()).isEqualTo(mockCity);
    assertThat(addressCopy.getPostalCode()).isEqualTo(mockPostalCode);
    assertThat(addressCopy.getCountry()).isEqualTo(Country.localCountry());
    assertThat(addressCopy.getCoordinates()).isNotPresent();

    verify(mockAddress, times(1)).getStreet();
    verify(mockAddress, times(1)).getCity();
    verify(mockAddress, times(1)).getPostalCode();
    verify(mockAddress, times(1)).getCountry();
    verify(mockAddress, times(1)).getCoordinates();
    verifyNoInteractions(mockStreet, mockCity, mockPostalCode);
    verifyNoMoreInteractions(mockAddress);
  }

  @Test
  public void constructAddressUsingBuilderFromNullAddress() {

    assertThatIllegalArgumentException()
      .isThrownBy(() -> Address.newBuilder().from(null))
      .withMessage("Address to copy is required")
      .withNoCause();
  }

  @Test
  public void addressUnitIsEmpty() {

    Address mockAddress = mock(Address.class);

    doCallRealMethod().when(mockAddress).getUnit();

    Optional<Unit> unit = mockAddress.getUnit();

    assertThat(unit).isNotNull();
    assertThat(unit).isNotPresent();

    verify(mockAddress, times(1)).getUnit();
    verifyNoMoreInteractions(mockAddress);
  }

  @Test
  public void addressTypeIsUnknown() {

    Address mockAddress = mock(Address.class);

    doCallRealMethod().when(mockAddress).getType();

    Optional<Address.Type> addressType = mockAddress.getType();

    assertThat(addressType).isNotNull();
    assertThat(addressType).isPresent();
    assertThat(addressType.orElse(null)).isEqualTo(Address.Type.UNKNOWN);

    verify(mockAddress, times(1)).getType();
    verifyNoMoreInteractions(mockAddress);
  }

  private void testIsAddressType(Address.Type expectedAddressType, Function<Address, Boolean> isAddressTypeFunction,
      Consumer<Address> arrange, Consumer<Address> verify) {

    Address mockAddress = mock(Address.class);

    Arrays.stream(Address.Type.values()).forEach(addressType -> {

      Mockito.reset(mockAddress);

      doReturn(Optional.ofNullable(addressType)).when(mockAddress).getType();

      arrange.accept(mockAddress);

      assertThat(isAddressTypeFunction.apply(mockAddress)).isEqualTo(expectedAddressType.equals(addressType));

      verify.accept(mockAddress);
      verify(mockAddress, times(1)).getType();
      verifyNoMoreInteractions(mockAddress);
    });
  }

  @Test
  public void isBillingAddress() {

    testIsAddressType(Address.Type.BILLING, Address::isBilling,
      mockAddress -> doCallRealMethod().when(mockAddress).isBilling(),
      mockAddress -> verify(mockAddress, times(1)).isBilling());
  }

  @Test
  public void isHomeAddress() {

    testIsAddressType(Address.Type.HOME, Address::isHome,
      mockAddress -> doCallRealMethod().when(mockAddress).isHome(),
      mockAddress -> verify(mockAddress, times(1)).isHome());
  }

  @Test
  public void isMailingAddress() {

    testIsAddressType(Address.Type.MAILING, Address::isMailing,
      mockAddress -> doCallRealMethod().when(mockAddress).isMailing(),
      mockAddress -> verify(mockAddress, times(1)).isMailing());
  }

  @Test
  public void isOfficeAddress() {

    testIsAddressType(Address.Type.OFFICE, Address::isOffice,
      mockAddress -> doCallRealMethod().when(mockAddress).isOffice(),
      mockAddress -> verify(mockAddress, times(1)).isOffice());
  }

  @Test
  public void isPoBoxAddress() {

    testIsAddressType(Address.Type.PO_BOX, Address::isPoBox,
      mockAddress -> doCallRealMethod().when(mockAddress).isPoBox(),
      mockAddress -> verify(mockAddress, times(1)).isPoBox());
  }

  @Test
  public void isWorkAddress() {

    testIsAddressType(Address.Type.WORK, Address::isWork,
      mockAddress -> doCallRealMethod().when(mockAddress).isWork(),
      mockAddress -> verify(mockAddress, times(1)).isWork());
  }

  @Test
  public void setAddressTypeThrowsUnsupportedOperationException() {

    Address mockAddress = mock(Address.class);

    doCallRealMethod().when(mockAddress).setType(any());

    assertThatUnsupportedOperationException()
      .isThrownBy(ThrowableOperation.fromRunnable(() -> mockAddress.setType(Address.Type.HOME)))
      .havingMessage("Setting Address.Type for an Address of type [%s] is not supported",
        mockAddress.getClass().getName())
      .withNoCause();

    verify(mockAddress, times(1)).setType(eq(Address.Type.HOME));
    verifyNoMoreInteractions(mockAddress);
  }

  @Test
  public void acceptsVisitor() {

    Address mockAddress = mock(Address.class);

    Visitor mockVisitor = mock(Visitor.class);

    doCallRealMethod().when(mockAddress).accept(any());

    mockAddress.accept(mockVisitor);

    verify(mockAddress, times(1)).accept(eq(mockVisitor));
    verify(mockVisitor, times(1)).visit(eq(mockAddress));
    verifyNoMoreInteractions(mockAddress, mockVisitor);
  }

  @Test
  public void compareToEqualAddressReturnsZero() {

    Unit unit = Unit.of("A");
    Street street = Street.of(100, "Main");
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
  public void compareToSelfReturnsZero() {

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
  public void compareToUnequalAddressReturnsNonZero() {

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

  @Test
  public void validateAddress() {

    Street mockStreet = mock(Street.class);
    City mockCity = mock(City.class);
    PostalCode mockPostalCode = mock(PostalCode.class);

    Address mockAddress = mockAddress(mockStreet, mockCity, mockPostalCode);

    doCallRealMethod().when(mockAddress).validate();

    assertThat(mockAddress.validate()).isSameAs(mockAddress);

    verify(mockAddress, times(1)).validate();
    verify(mockAddress, times(1)).getStreet();
    verify(mockAddress, times(1)).getCity();
    verify(mockAddress, times(1)).getPostalCode();
    verify(mockAddress, times(1)).getCountry();
    verifyNoInteractions(mockStreet, mockCity, mockPostalCode);
    verifyNoMoreInteractions(mockAddress);
  }

  @Test
  public void validateAddressWithNoStreet() {

    City mockCity = mock(City.class);
    PostalCode mockPostalCode = mock(PostalCode.class);

    Address mockAddress = mockAddress(null, mockCity, mockPostalCode);

    doCallRealMethod().when(mockAddress).validate();

    assertThatIllegalStateException()
      .isThrownBy(mockAddress::validate)
      .withMessage("Street is required")
      .withNoCause();

    verify(mockAddress, times(1)).validate();
    verify(mockAddress, times(1)).getStreet();
    verifyNoInteractions(mockCity, mockPostalCode);
    verifyNoMoreInteractions(mockAddress);
  }

  @Test
  public void validateAddressWithNoCity() {

    Street mockStreet = mock(Street.class);
    PostalCode mockPostalCode = mock(PostalCode.class);

    Address mockAddress = mockAddress(mockStreet, null, mockPostalCode);

    doCallRealMethod().when(mockAddress).validate();

    assertThatIllegalStateException()
      .isThrownBy(mockAddress::validate)
      .withMessage("City is required")
      .withNoCause();

    verify(mockAddress, times(1)).validate();
    verify(mockAddress, times(1)).getStreet();
    verify(mockAddress, times(1)).getCity();
    verifyNoInteractions(mockStreet, mockPostalCode);
    verifyNoMoreInteractions(mockAddress);
  }

  @Test
  public void validateAddressWithNoPostalCode() {

    Street mockStreet = mock(Street.class);
    City mockCity = mock(City.class);

    Address mockAddress = mockAddress(mockStreet, mockCity, null);

    doCallRealMethod().when(mockAddress).validate();

    assertThatIllegalStateException()
      .isThrownBy(mockAddress::validate)
      .withMessage("Postal Code is required")
      .withNoCause();

    verify(mockAddress, times(1)).validate();
    verify(mockAddress, times(1)).getStreet();
    verify(mockAddress, times(1)).getCity();
    verify(mockAddress, times(1)).getPostalCode();
    verifyNoInteractions(mockStreet, mockCity);
    verifyNoMoreInteractions(mockAddress);
  }

  @Test
  public void validateAddressWithNoCountry() {

    Street mockStreet = mock(Street.class);
    City mockCity = mock(City.class);
    PostalCode mockPostalCode = mock(PostalCode.class);

    Address mockAddress = mockAddress(mockStreet, mockCity, mockPostalCode, null);

    doCallRealMethod().when(mockAddress).validate();

    assertThatIllegalStateException()
      .isThrownBy(mockAddress::validate)
      .withMessage("Country is required")
      .withNoCause();

    verify(mockAddress, times(1)).validate();
    verify(mockAddress, times(1)).getStreet();
    verify(mockAddress, times(1)).getCity();
    verify(mockAddress, times(1)).getPostalCode();
    verify(mockAddress, times(1)).getCountry();
    verifyNoInteractions(mockStreet, mockCity, mockPostalCode);
    verifyNoMoreInteractions(mockAddress);
  }

  @Test
  public void asAddressTypeCallsSetType() {

    Address mockAddress = mock(Address.class);

    doCallRealMethod().when(mockAddress).as(any());
    doNothing().when(mockAddress).setType(any());

    assertThat((Address) mockAddress.as(Address.Type.PO_BOX)).isSameAs(mockAddress);

    verify(mockAddress, times(1)).as(eq(Address.Type.PO_BOX));
    verify(mockAddress, times(1)).setType(eq(Address.Type.PO_BOX));
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
  public void asBillingAddress() {

    testAsAddressType(Address.Type.BILLING, mockAddress -> {
      doCallRealMethod().when(mockAddress).asBilling();
      return mockAddress.asBilling();
    }, mockAddress -> verify(mockAddress, times(1)).asBilling());
  }

  @Test
  public void asHomeAddress() {

    testAsAddressType(Address.Type.HOME, mockAddress -> {
      doCallRealMethod().when(mockAddress).asHome();
      return mockAddress.asHome();
    }, mockAddress -> verify(mockAddress, times(1)).asHome());
  }

  @Test
  public void asMailingAddress() {

    testAsAddressType(Address.Type.MAILING, mockAddress -> {
      doCallRealMethod().when(mockAddress).asMailing();
      return mockAddress.asMailing();
    }, mockAddress -> verify(mockAddress, times(1)).asMailing());
  }

  @Test
  public void asOfficeAddress() {

    testAsAddressType(Address.Type.OFFICE, mockAddress -> {
      doCallRealMethod().when(mockAddress).asOffice();
      return mockAddress.asOffice();
    }, mockAddress -> verify(mockAddress, times(1)).asOffice());
  }

  @Test
  public void asPoBoxAddress() {

    testAsAddressType(Address.Type.PO_BOX, mockAddress -> {
      doCallRealMethod().when(mockAddress).asPoBox();
      return mockAddress.asPoBox();
    }, mockAddress -> verify(mockAddress, times(1)).asPoBox());
  }

  @Test
  public void asWorkAddress() {

    testAsAddressType(Address.Type.WORK, mockAddress -> {
      doCallRealMethod().when(mockAddress).asWork();
      return mockAddress.asWork();
    }, mockAddress -> verify(mockAddress, times(1)).asWork());
  }
}
