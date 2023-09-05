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
package org.cp.domain.geo.model.usa;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.cp.elements.lang.ThrowableAssertions.assertThatIllegalStateException;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoInteractions;

import org.junit.jupiter.api.Test;

import org.assertj.core.api.InstanceOfAssertFactories;
import org.cp.domain.geo.enums.Country;
import org.cp.domain.geo.enums.State;
import org.cp.domain.geo.model.Address;
import org.cp.domain.geo.model.City;
import org.cp.domain.geo.model.PostalCode;
import org.cp.domain.geo.model.Street;
import org.cp.elements.lang.IllegalTypeException;

/**
 * Unit Tests for {@link UnitedStatesAddress}.
 *
 * @author John Blum
 * @see org.junit.jupiter.api.Test
 * @see org.mockito.Mockito
 * @see org.cp.domain.geo.model.usa.UnitedStatesAddress
 * @since 0.1.0
 */
public class UnitedStatesAddressUnitTests {

  private void assertAddress(UnitedStatesAddress address, Street street, City city, State state, ZIP zip) {

    assertThat(address).isNotNull();
    assertThat(address.getStreet()).isEqualTo(street);
    assertThat(address.getUnit()).isNotPresent();
    assertThat(address.getCity()).isEqualTo(city);
    assertThat(address.getState()).isEqualTo(state);
    assertThat(address.getPostalCode()).isEqualTo(zip);
    assertThat(address.getZip()).isEqualTo(zip);
    assertThat(address.getCountry()).isEqualTo(Country.UNITED_STATES_OF_AMERICA);
  }

  @Test
  public void constructUnitedStatesAddress() {

    Street mockStreet = mock(Street.class);

    UnitedStatesCity city = UnitedStatesCity.newUnitedStatesCity("MockCity").in(State.OREGON);

    ZIP mockZip = mock(ZIP.class);

    UnitedStatesAddress address = new UnitedStatesAddress(mockStreet, city, State.OREGON, mockZip);

    assertAddress(address, mockStreet, city, State.OREGON, mockZip);

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

    UnitedStatesAddress address = new UnitedStatesAddress(Street.of(314, "North Washington").asStreet(),
      City.of("Cuba City"), State.WISCONSIN, ZIP.of("12345"));

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
  void getPostalCodeReturnsZip() {

    PostalCode postalCode = PostalCode.of("12345");

    UnitedStatesAddress address = UnitedStatesAddress.newUnitedStatesAddressBuilder()
      .in(State.IOWA)
      .on(Street.of(100, "Old Davenport").asRoad())
      .in(City.of("Dubuque"))
      .in(postalCode)
      .build();

    assertThat(address).isNotNull();
    assertThat(address.getPostalCode()).isInstanceOf(ZIP.class);
    assertThat(address.getZip()).isEqualTo(ZIP.from(postalCode));
  }

  @Test
  public void newUnitedStatesAddressBuilderReturnsBuilder() {

    UnitedStatesAddress.Builder addressBuilder = UnitedStatesAddress.newUnitedStatesAddressBuilder();

    assertThat(addressBuilder).isNotNull();
    assertThat(addressBuilder.getCountry()).isEqualTo(Country.UNITED_STATES_OF_AMERICA);
  }

  @Test
  public void unitedSatesAddressBuiltWithBuilder() {

    PostalCode postalCode = PostalCode.of("12345");

    Street street = Street.of(100, "Main").asStreet();

    UnitedStatesCity city = UnitedStatesCity.newUnitedStatesCity("Portland").in(State.OREGON);

    UnitedStatesAddress address = UnitedStatesAddress.newUnitedStatesAddressBuilder()
      .in(State.OREGON)
      .in(city)
      .in(postalCode)
      .on(street)
      .build();

    assertAddress(address, street, city, State.OREGON, ZIP.from(postalCode));
  }

  @Test
  public void unitedStatesAddressBuilderWithStateDeterminedByZip() {

    ZIP zip = ZIP.of("97205");

    UnitedStatesCity city = UnitedStatesCity.newUnitedStatesCity("Portland").in(State.OREGON);

    Street street = Street.of(2, "One").asWay();

    UnitedStatesAddress address = UnitedStatesAddress.newUnitedStatesAddressBuilder()
      .in(city)
      .in(zip)
      .on(street)
      .build();

    assertAddress(address, street, city, State.OREGON, zip);
  }

  @Test
  public void unitedStatesAddressBuilderSetToNullState() {

    assertThatIllegalArgumentException()
      .isThrownBy(() -> UnitedStatesAddress.newUnitedStatesAddressBuilder().in((State) null))
      .withMessage("State is required")
      .withNoCause();
  }

  @Test
  public void unitedStatesAddressBuilderWithUndeterminedState() {

    assertThatIllegalStateException()
      .isThrownBy(args -> UnitedStatesAddress.newUnitedStatesAddressBuilder()
        .<UnitedStatesAddress.Builder>in(ZIP.of("00000"))
        .getState())
      .havingMessage("State was not initialized")
      .causedBy(IllegalArgumentException.class)
      .havingMessage("State for ZIP code [00000] not found")
      .withNoCause();
  }

  @Test
  public void compareToUnitedStatesAddressOrdersCorrectly() {

    Street street = Street.of(100, "Main").asStreet();
    City portland = City.of("Portland");
    ZIP zip = ZIP.of("12345");

    UnitedStatesAddress portlandMaine = UnitedStatesAddress.newUnitedStatesAddressBuilder()
      .in(State.MAINE)
      .in(portland)
      .in(zip)
      .on(street)
      .build();

    UnitedStatesAddress portlandOregon = UnitedStatesAddress.newUnitedStatesAddressBuilder()
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

    UnitedStatesAddress portlandOregonOne = UnitedStatesAddress.newUnitedStatesAddressBuilder()
      .in(portland)
      .in(zip)
      .on(street)
      .build();

    UnitedStatesAddress portlandOregonTwo = UnitedStatesAddress.newUnitedStatesAddressBuilder()
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
      .isThrownBy(() -> UnitedStatesAddress.newUnitedStatesAddressBuilder()
        .in(State.OREGON)
        .in(mockCity)
        .in(mockZip)
        .on(mockStreet)
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

    UnitedStatesAddress portlandOregon = UnitedStatesAddress.newUnitedStatesAddressBuilder()
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

    UnitedStatesAddress portlandOregonOne = UnitedStatesAddress.newUnitedStatesAddressBuilder()
      .in(portland)
      .in(zip)
      .on(street)
      .build();

    UnitedStatesAddress portlandOregonTwo = UnitedStatesAddress.newUnitedStatesAddressBuilder()
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

    UnitedStatesAddress address = UnitedStatesAddress.newUnitedStatesAddressBuilder()
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

    UnitedStatesAddress address = UnitedStatesAddress.newUnitedStatesAddressBuilder()
      .in(State.OREGON)
      .in(City.of("Portland"))
      .in(ZIP.of("97205"))
      .on(Street.of(100, "Main").asStreet())
      .build();

    assertThat(address).isNotNull();
    assertThat(address.equals("100 Main St. Portland, OR 97205")).isFalse();
  }

  @Test
  @SuppressWarnings("all")
  public void equalsSameAddress() {

    UnitedStatesAddress portlandOregon = UnitedStatesAddress.newUnitedStatesAddressBuilder()
      .on(Street.of(2, "One").asWay())
      .in(City.of("Portland"))
      .in(ZIP.of("97205"))
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

    UnitedStatesAddress portlandMaine = UnitedStatesAddress.newUnitedStatesAddressBuilder()
      .in(State.MAINE)
      .in(portland)
      .in(zip)
      .on(street)
      .build();

    UnitedStatesAddress portlandOregonOne = UnitedStatesAddress.newUnitedStatesAddressBuilder()
      .in(State.OREGON)
      .in(portland)
      .in(zip)
      .on(street)
      .build();

    UnitedStatesAddress portlandOregonTwo = UnitedStatesAddress.newUnitedStatesAddressBuilder()
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

    UnitedStatesAddress portlandOregon = UnitedStatesAddress.newUnitedStatesAddressBuilder()
      .on(Street.of(100, "Main").asStreet())
      .<UnitedStatesAddress.Builder>in(City.of("Portland"))
      .in(State.OREGON)
      .in(ZIP.of("12345"))
      .build();

    assertThat(portlandOregon).isNotNull();

    assertThat(portlandOregon.toString())
      .isEqualTo(String.format(UnitedStatesAddress.UNITED_STATES_ADDRESS_TO_STRING, UnitedStatesAddress.class.getName(),
        portlandOregon.getStreet(), portlandOregon.getUnit().orElse(null), portlandOregon.getCity(),
        portlandOregon.getState(), portlandOregon.getZip(), portlandOregon.getCountry(),
        portlandOregon.getType().orElse(Address.Type.UNKNOWN)));
  }
}
