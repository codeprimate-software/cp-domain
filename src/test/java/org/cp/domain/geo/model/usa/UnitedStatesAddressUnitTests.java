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
package org.cp.domain.geo.model.usa;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.cp.elements.lang.ThrowableAssertions.assertThatIllegalStateException;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoInteractions;

import org.junit.Test;

import org.cp.domain.geo.enums.Country;
import org.cp.domain.geo.enums.State;
import org.cp.domain.geo.model.AbstractAddress;
import org.cp.domain.geo.model.Address;
import org.cp.domain.geo.model.City;
import org.cp.domain.geo.model.PostalCode;
import org.cp.domain.geo.model.Street;
import org.cp.elements.lang.IllegalTypeException;
import org.cp.elements.lang.ObjectUtils;

import org.assertj.core.api.InstanceOfAssertFactories;

/**
 * Unit Tests for {@link UnitedStatesAddress}.
 *
 * @author John Blum
 * @see org.junit.Test
 * @see org.mockito.Mockito
 * @see org.cp.domain.geo.model.usa.UnitedStatesAddress
 * @since 0.1.0
 */
public class UnitedStatesAddressUnitTests {

  private Country getCountry(AbstractAddress.Builder<?> addressBuilder) {
    return ObjectUtils.invoke(addressBuilder, "getCountry", Country.class);
  }

  @Test
  public void constructUnitedStatesAddress() {

    UnitedStatesCity city = UnitedStatesCity.from("MockCity", State.OREGON);

    Street mockStreet = mock(Street.class);
    ZIP mockZip = mock(ZIP.class);

    UnitedStatesAddress address = new UnitedStatesAddress(mockStreet, city, State.OREGON, mockZip);

    assertThat(address).isNotNull();
    assertThat(address.getStreet()).isEqualTo(mockStreet);
    assertThat(address.getUnit()).isNotPresent();
    assertThat(address.getCity()).isEqualTo(city);
    assertThat(address.getPostalCode()).isEqualTo(mockZip);
    assertThat(address.getState()).isEqualTo(State.OREGON);
    assertThat(address.getZip()).isEqualTo(mockZip);
    assertThat(address.getCountry()).isEqualTo(Country.UNITED_STATES_OF_AMERICA);

    verifyNoInteractions(mockStreet, mockZip);
  }

  @Test
  public void constructUnitedStatesAddressWithNullState() {

    Street mockStreet = mock(Street.class);
    City mockCity = mock(City.class);
    ZIP mockZip = mock(ZIP.class);

    assertThatIllegalArgumentException()
      .isThrownBy(() -> new UnitedStatesAddress(mockStreet, mockCity, null, mockZip))
      .withMessage("State is required")
      .withNoCause();

    verifyNoInteractions(mockStreet, mockCity, mockZip);
  }

  @Test
  public void getCityReturnsUnitedStatesCity() {

    UnitedStatesAddress address = UnitedStatesAddress.newUnitedStatesAddress()
      .in(State.WISCONSIN)
      .on(Street.of(314, "North Washington").asStreet())
      .in(City.of("Cuba City"))
      .in(ZIP.of("12345"))
      .build();

    assertThat(address).isNotNull();
    assertThat(address.getState()).isEqualTo(State.WISCONSIN);

    City city = address.getCity();

    assertThat(city).isInstanceOf(UnitedStatesCity.class);
    assertThat(city.getName()).isEqualTo("Cuba City");
    assertThat(city.getCountry().orElse(null)).isEqualTo(Country.UNITED_STATES_OF_AMERICA);

    assertThat(city)
      .asInstanceOf(InstanceOfAssertFactories.type(UnitedStatesCity.class))
      .extracting(UnitedStatesCity::getState)
      .isEqualTo(State.WISCONSIN);
  }
  @Test
  public void newUnitedStatesAddressReturnsBuilder() {

    UnitedStatesAddress.Builder addressBuilder = UnitedStatesAddress.newUnitedStatesAddress();

    assertThat(addressBuilder).isNotNull();
    assertThat(getCountry(addressBuilder)).isEqualTo(Country.UNITED_STATES_OF_AMERICA);
  }

  @Test
  public void unitedSatesAddressBuiltWithBuilder() {

    UnitedStatesCity city = UnitedStatesCity.from("Portland", State.OREGON);

    UnitedStatesAddress address = UnitedStatesAddress.newUnitedStatesAddress()
      .in(State.OREGON)
      .in(city)
      .in(PostalCode.of("12345"))
      .on(Street.of(100, "Main").asStreet())
      .build();

    assertThat(address).isNotNull();
    assertThat(address.getStreet()).isEqualTo(Street.of(100, "Main").asStreet());
    assertThat(address.getUnit()).isNotPresent();
    assertThat(address.getCity()).isEqualTo(city);
    assertThat(address.getPostalCode()).isEqualTo(PostalCode.of("12345"));
    assertThat(address.getState()).isEqualTo(State.OREGON);
    assertThat(address.getZip()).isEqualTo(ZIP.of("12345"));
    assertThat(address.getCountry()).isEqualTo(Country.UNITED_STATES_OF_AMERICA);
  }

  @Test
  public void unitedStatesAddressBuilderWithStateDeterminedByZip() {

    UnitedStatesCity city = UnitedStatesCity.from("Portland", State.OREGON);

    UnitedStatesAddress address = UnitedStatesAddress.newUnitedStatesAddress()
      .on(Street.of(2, "One").asWay())
      .in(city)
      .in(ZIP.of("97205"))
      .build();

    assertThat(address).isNotNull();
    assertThat(address.getStreet()).isEqualTo(Street.of(2, "One").asWay());
    assertThat(address.getUnit()).isNotPresent();
    assertThat(address.getCity()).isEqualTo(city);
    assertThat(address.getPostalCode()).isEqualTo(PostalCode.of("97205"));
    assertThat(address.getState()).isEqualTo(State.OREGON);
    assertThat(address.getZip()).isEqualTo(ZIP.of("97205"));
    assertThat(address.getCountry()).isEqualTo(Country.UNITED_STATES_OF_AMERICA);
  }

  @Test
  public void unitedStatesAddressBuilderSetToNullState() {

    assertThatIllegalArgumentException()
      .isThrownBy(() -> UnitedStatesAddress.newUnitedStatesAddress().in((State) null))
      .withMessage("State is required")
      .withNoCause();
  }

  @Test
  public void unitedStatesAddressBuilderWithUndeterminedState() {

    assertThatIllegalStateException()
      .isThrownBy(args -> UnitedStatesAddress.newUnitedStatesAddress()
        .<UnitedStatesAddress.Builder>in(PostalCode.of("00001"))
        .getState())
      .havingMessage("State was not initialized")
      .causedBy(IllegalArgumentException.class)
      .havingMessage("State for ZIP code [00001] not found")
      .withNoCause();
  }

  @Test
  public void compareToUnitedStatesAddressOrdersCorrectly() {

    Street street = Street.of(100, "Main").asStreet();
    City portland = City.of("Portland");
    ZIP zip = ZIP.of("12345");

    UnitedStatesAddress portlandMaine = UnitedStatesAddress.newUnitedStatesAddress()
      .in(State.MAINE)
      .in(portland)
      .in(zip)
      .on(street)
      .build();

    UnitedStatesAddress portlandOregon = UnitedStatesAddress.newUnitedStatesAddress()
      .in(State.OREGON)
      .in(portland)
      .in(zip)
      .on(street)
      .build();

    assertThat(portlandMaine).isNotNull();
    assertThat(portlandOregon).isNotNull();
    assertThat(portlandOregon).isNotSameAs(portlandMaine);
    assertThat(portlandOregon).isNotEqualTo(portlandMaine);
    assertThat(portlandMaine).isLessThan(portlandOregon);
    assertThat(portlandOregon).isGreaterThan(portlandMaine);
  }

  @Test
  public void compareToEqualUnitedStatesAddressReturnsZero() {

    Street street = Street.of(2, "One").asWay();
    City portland = City.of("Portland");
    ZIP zip = ZIP.of("97205");

    UnitedStatesAddress portlandOregonOne = UnitedStatesAddress.newUnitedStatesAddress()
      .in(portland)
      .in(zip)
      .on(street)
      .build();

    UnitedStatesAddress portlandOregonTwo = UnitedStatesAddress.newUnitedStatesAddress()
      .in(portland)
      .in(zip)
      .on(street)
      .build();

    assertThat(portlandOregonOne).isNotNull();
    assertThat(portlandOregonTwo).isNotNull();
    assertThat(portlandOregonOne).isNotSameAs(portlandOregonTwo);
    assertThat(portlandOregonOne).isEqualTo(portlandOregonTwo);
    assertThat(portlandOregonOne).isEqualByComparingTo(portlandOregonTwo);
  }

  @Test
  @SuppressWarnings("all")
  public void compareToNonUnitedStatesAddress() {

    Address mockAddress = mock(Address.class);
    Street mockStreet = mock(Street.class);
    City mockCity = mock(City.class);
    ZIP mockZip = mock(ZIP.class);

    assertThatExceptionOfType(IllegalTypeException.class)
      .isThrownBy(() -> UnitedStatesAddress.newUnitedStatesAddress()
        .in(State.OREGON)
        .on(mockStreet)
        .in(mockCity)
        .in(mockZip)
        .build()
        .compareTo(mockAddress))
      .withMessage("[%s] is not an instance of [%s]", mockAddress, UnitedStatesAddress.class)
      .withNoCause();

    verifyNoInteractions(mockAddress, mockStreet, mockCity, mockZip);
  }

  @Test
  public void compareToSameUnitedStatesAddressReturnsZero() {

    Street street = Street.of(2, "One").asWay();
    City portland = City.of("Portland");
    ZIP zip = ZIP.of("97205");

    UnitedStatesAddress portlandOregon = UnitedStatesAddress.newUnitedStatesAddress()
      .in(portland)
      .in(zip)
      .on(street)
      .build();

    assertThat(portlandOregon).isNotNull();
    assertThat(portlandOregon).isEqualByComparingTo(portlandOregon);
  }

  @Test
  public void equalsEqualAddress() {

    Street street = Street.of(2, "One").asWay();
    City portland = City.of("Portland");
    ZIP zip = ZIP.of("97205");

    UnitedStatesAddress portlandOregonOne = UnitedStatesAddress.newUnitedStatesAddress()
      .in(portland)
      .in(zip)
      .on(street)
      .build();

    UnitedStatesAddress portlandOregonTwo = UnitedStatesAddress.newUnitedStatesAddress()
      .in(portland)
      .in(zip)
      .on(street)
      .build();

    assertThat(portlandOregonOne).isNotNull();
    assertThat(portlandOregonTwo).isNotNull();
    assertThat(portlandOregonOne).isNotSameAs(portlandOregonTwo);
    assertThat(portlandOregonOne).isEqualTo(portlandOregonTwo);
    assertThat(portlandOregonOne.equals(portlandOregonTwo)).isTrue();
  }

  @Test
  @SuppressWarnings("all")
  public void equalsNullIsNullSafe() {

    Street mockStreet = mock(Street.class);
    City mockCity = mock(City.class);
    ZIP mockZip = mock(ZIP.class);

    UnitedStatesAddress address = UnitedStatesAddress.newUnitedStatesAddress()
      .in(State.OREGON)
      .in(mockCity)
      .in(mockZip)
      .on(mockStreet)
      .build();

    assertThat(address).isNotNull();
    assertThat(address.equals(null)).isFalse();
  }

  @Test
  @SuppressWarnings("all")
  public void equalsObjectReturnsFalse() {

    Street mockStreet = mock(Street.class);
    City mockCity = mock(City.class);
    ZIP mockZip = mock(ZIP.class);

    UnitedStatesAddress address = UnitedStatesAddress.newUnitedStatesAddress()
      .in(State.OREGON)
      .in(mockCity)
      .in(mockZip)
      .on(mockStreet)
      .build();

    assertThat(address).isNotNull();
    assertThat(address.equals("100 Main St. Portland, OR 972005")).isFalse();
  }

  @Test
  @SuppressWarnings("all")
  public void equalsSameAddress() {

    Street street = Street.of(2, "One").asWay();
    City portland = City.of("Portland");
    ZIP zip = ZIP.of("97205");

    UnitedStatesAddress portlandOregon = UnitedStatesAddress.newUnitedStatesAddress()
      .in(portland)
      .in(zip)
      .on(street)
      .build();

    assertThat(portlandOregon).isNotNull();
    assertThat(portlandOregon).isEqualTo(portlandOregon);
    assertThat(portlandOregon.equals(portlandOregon)).isTrue();
  }

  @Test
  public void hashCodeIsCorrect() {

    Street street = Street.of(100, "Main").asStreet();
    City portland = City.of("Portland");
    ZIP zip = ZIP.of("12345");

    UnitedStatesAddress portlandMaine = UnitedStatesAddress.newUnitedStatesAddress()
      .in(State.MAINE)
      .in(portland)
      .in(zip)
      .on(street)
      .build();

    UnitedStatesAddress portlandOregonOne = UnitedStatesAddress.newUnitedStatesAddress()
      .in(State.OREGON)
      .in(portland)
      .in(zip)
      .on(street)
      .build();

    UnitedStatesAddress portlandOregonTwo = UnitedStatesAddress.newUnitedStatesAddress()
      .in(State.OREGON)
      .in(portland)
      .in(zip)
      .on(street)
      .build();

    assertThat(portlandMaine).isNotNull();
    assertThat(portlandOregonOne).isNotNull();
    assertThat(portlandOregonTwo).isNotNull();
    assertThat(portlandMaine).isNotEqualTo(portlandOregonOne);
    assertThat(portlandOregonOne).isNotSameAs(portlandOregonTwo);
    assertThat(portlandOregonOne).isEqualTo(portlandOregonTwo);

    int hashCode = portlandOregonOne.hashCode();

    assertThat(hashCode).isNotZero();
    assertThat(hashCode).isEqualTo(portlandOregonOne.hashCode());
    assertThat(portlandOregonOne).hasSameHashCodeAs(portlandOregonTwo);
    assertThat(portlandOregonOne).doesNotHaveSameHashCodeAs(portlandMaine);
    assertThat(portlandOregonOne).doesNotHaveSameHashCodeAs("100 Main St. Portland, OR 97205");
  }

  @Test
  public void toStringIsCorrect() {

    UnitedStatesAddress portlandOregon = UnitedStatesAddress.newUnitedStatesAddress()
      .in(State.OREGON)
      .in(City.of("Portland"))
      .in(ZIP.of("12345"))
      .on(Street.of(100, "Main").asStreet())
      .build();

    assertThat(portlandOregon).isNotNull();
    assertThat(portlandOregon.toString())
      .isEqualTo(String.format(UnitedStatesAddress.UNITED_STATES_ADDRESS_TO_STRING, UnitedStatesAddress.class.getName(),
        portlandOregon.getStreet(), portlandOregon.getUnit().orElse(null), portlandOregon.getCity(),
        portlandOregon.getState(), portlandOregon.getZip(), portlandOregon.getCountry(),
        portlandOregon.getType().orElse(Address.Type.UNKNOWN)));
  }
}
