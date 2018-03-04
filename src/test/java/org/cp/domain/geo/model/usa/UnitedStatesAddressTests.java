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

package org.cp.domain.geo.model.usa;

import static org.assertj.core.api.Assertions.assertThat;

import org.cp.domain.geo.enums.Country;
import org.cp.domain.geo.model.City;
import org.cp.domain.geo.model.Coordinates;
import org.cp.domain.geo.model.PostalCode;
import org.cp.domain.geo.model.Street;
import org.cp.domain.geo.model.Unit;
import org.cp.elements.enums.State;
import org.junit.Test;

/**
 * Unit tests for {@link UnitedStatesAddress}.
 *
 * @author John Blum
 * @see org.junit.Test
 * @see org.cp.domain.geo.model.usa.UnitedStatesAddress
 * @since 1.0.0
 */
public class UnitedStatesAddressTests {

  @Test
  public void newUnitedStatesAddressIsCorrect() {

    UnitedStatesAddress address = UnitedStatesAddress.newUnitedStatesAddress();

    assertThat(address).isNotNull();
    assertThat(address.getStreet()).isNull();
    assertThat(address.getUnit().isPresent()).isFalse();
    assertThat(address.getCity()).isNull();
    assertThat(address.getPostalCode()).isNull();
    assertThat(address.getState()).isNull();
    assertThat(address.getZip()).isNull();
    assertThat(address.getCountry()).isEqualTo(Country.UNITED_STATES_OF_AMERICA);
    assertThat(address.getCoordinates().isPresent()).isFalse();
    assertThat(address.getType().isPresent()).isFalse();
  }

  @Test
  public void ofStreetCityStateAndZipIsCorrect() {

    Street street = Street.of(100, "Main").asStreet();
    City city = City.of("Portland");
    ZIP zip = ZIP.of("97205");

    UnitedStatesAddress address = UnitedStatesAddress.of(street, city, State.OREGON, zip);

    assertThat(address).isNotNull();
    assertThat(address.getStreet()).isEqualTo(street);
    assertThat(address.getUnit().isPresent()).isFalse();
    assertThat(address.getCity()).isEqualTo(city);
    assertThat(address.getPostalCode()).isEqualTo(zip);
    assertThat(address.getState()).isEqualTo(State.OREGON);
    assertThat(address.getZip()).isEqualTo(zip);
    assertThat(address.getCountry()).isEqualTo(Country.UNITED_STATES_OF_AMERICA);
    assertThat(address.getCoordinates().isPresent()).isFalse();
    assertThat(address.getType().isPresent()).isFalse();
  }

  @Test(expected = IllegalArgumentException.class)
  public void ofNullStreetThrowsIllegalArgumentException() {

    testOfInvalidStreetCityStateOrZipThrowsIllegalArgumentException(
      null, City.of("Portland"), State.OREGON, ZIP.of("97205"), "Street is required");
  }

  @Test(expected = IllegalArgumentException.class)
  public void ofNullCityThrowsIllegalArgumentException() {

    testOfInvalidStreetCityStateOrZipThrowsIllegalArgumentException(
      Street.of(100, "One").asWay(), null, State.OREGON, ZIP.of("97205"), "City is required");
  }

  @Test(expected = IllegalArgumentException.class)
  public void ofNullStateIgnoresArgumentReturnsNullState() {

    testOfInvalidStreetCityStateOrZipThrowsIllegalArgumentException(
      Street.of(100, "One").asWay(), City.of("Portland"), null, ZIP.of("97205"), "State is required");
  }

  @Test(expected = IllegalArgumentException.class)
  public void ofNullZipThrowsIllegalArgumentException() {

    testOfInvalidStreetCityStateOrZipThrowsIllegalArgumentException(
      Street.of(100, "One").asWay(), City.of("Portland"), State.OREGON, null, "Zip is required");
  }

  public void testOfInvalidStreetCityStateOrZipThrowsIllegalArgumentException(
      Street street, City city, State state, ZIP zip, String message) {

    try {
      UnitedStatesAddress.of(street, city, state, zip);
    }
    catch (IllegalArgumentException expected) {

      assertThat(expected).hasMessage(message);
      assertThat(expected).hasNoCause();

      throw expected;
    }
  }

  @Test
  public void fromAddressCopiesAddress() {

    UnitedStatesAddress address = UnitedStatesAddress.
      of(Street.of(100, "Main").asStreet(), City.of("Portland"), State.OREGON, ZIP.of("97205"))
      .with(Coordinates.of(1.0d, 1.0d))
      .in(Unit.suite("16"))
      .asHome();

    UnitedStatesAddress addressCopy = UnitedStatesAddress.from(address);

    assertThat(addressCopy).isNotNull();
    assertThat(addressCopy).isEqualTo(address);
    assertThat(addressCopy.getCoordinates()).isEqualTo(address.getCoordinates());
    assertThat(addressCopy.getType()).isEqualTo(address.getType());
  }

  @Test(expected = IllegalArgumentException.class)
  public void fromNullAddressThrowsIllegalArgumentException() {

    try {
      UnitedStatesAddress.from(null);
    }
    catch (IllegalArgumentException expected) {

      assertThat(expected).hasMessage("Address to copy is required");
      assertThat(expected).hasNoCause();

      throw expected;
    }
  }

  @Test
  public void setAndGetState() {

    UnitedStatesAddress address = UnitedStatesAddress.newUnitedStatesAddress();

    assertThat(address).isNotNull();
    assertThat(address.getState()).isNull();

    address.setState(State.OREGON);

    assertThat(address.getState()).isEqualTo(State.OREGON);
    assertThat(address.in(State.MONTANA)).isSameAs(address);
    assertThat(address.getState()).isEqualTo(State.MONTANA);

    address.setState(State.IOWA);

    assertThat(address.getState()).isEqualTo(State.IOWA);
  }

  @Test(expected = IllegalArgumentException.class)
  public void setStateToNullThrowsIllegalArgumentException() {

    try {
      UnitedStatesAddress.newUnitedStatesAddress().setState(null);
    }
    catch (IllegalArgumentException expected) {

      assertThat(expected).hasMessage("State is required");
      assertThat(expected).hasNoCause();

      throw expected;
    }
  }

  @Test
  public void setAndGetZip() {

    UnitedStatesAddress address = UnitedStatesAddress.newUnitedStatesAddress();

    assertThat(address).isNotNull();
    assertThat(address.getZip()).isNull();

    address.setZip(ZIP.of("12345"));

    assertThat(address.getZip()).isEqualTo(ZIP.of("12345"));
    assertThat(address.in(ZIP.of("97205"))).isSameAs(address);
    assertThat(address.getZip()).isEqualTo(ZIP.of("97205"));

    address.setZip(ZIP.of("97213"));

    assertThat(address.getZip()).isEqualTo(ZIP.of("97213"));
  }

  @Test
  public void settingZipSetsPostalCode() {

    UnitedStatesAddress address = UnitedStatesAddress.newUnitedStatesAddress();

    assertThat(address).isNotNull();
    assertThat(address.getPostalCode()).isNull();
    assertThat(address.getZip()).isNull();

    ZIP zip = ZIP.of("12345");

    address.setZip(zip);

    assertThat(address.getZip()).isEqualTo(zip);
    assertThat(address.getPostalCode()).isEqualTo(zip);
  }

  @Test
  public void settingPostalCodeDoesNotSetZipButReturnsZipOfPostalCode() {

    UnitedStatesAddress address = UnitedStatesAddress.newUnitedStatesAddress();

    assertThat(address).isNotNull();
    assertThat(address.getPostalCode()).isNull();
    assertThat(address.getZip()).isNull();

    PostalCode postalCode = PostalCode.of("12345");

    address.setPostalCode(postalCode);

    assertThat(address.getPostalCode()).isEqualTo(postalCode);
    assertThat(address.getPostalCode()).isSameAs(postalCode);

    ZIP zip = address.getZip();

    assertThat(zip).isNotSameAs(postalCode);
    assertThat(zip.getCode()).isEqualTo(postalCode.getNumber());
  }

  @Test(expected = IllegalArgumentException.class)
  public void setZipToNullThrowsIllegalArgumentException() {

    try {
      UnitedStatesAddress.newUnitedStatesAddress().setZip(null);
    }
    catch (IllegalArgumentException expected) {

      assertThat(expected).hasMessage("Zip is required");
      assertThat(expected).hasNoCause();

      throw expected;
    }
  }

  @Test
  public void inStateReturnsThis() {

    UnitedStatesAddress address = UnitedStatesAddress.newUnitedStatesAddress();

    assertThat(address.in(State.OREGON)).isSameAs(address);
    assertThat(address.getState()).isEqualTo(State.OREGON);
  }

  @Test(expected = IllegalArgumentException.class)
  public void inNullStateThrowsIllegalArgumentException() {

    try {
      UnitedStatesAddress.newUnitedStatesAddress().in((State) null);
    }
    catch (IllegalArgumentException expected) {

      assertThat(expected).hasMessage("State is required");
      assertThat(expected).hasNoCause();

      throw expected;
    }
  }

  @Test
  public void inZipReturnsThis() {

    UnitedStatesAddress address = UnitedStatesAddress.newUnitedStatesAddress();

    ZIP zip = ZIP.of("12345");

    assertThat(address.in(zip)).isSameAs(address);
    assertThat(address.getZip()).isEqualTo(zip);
    assertThat(address.getPostalCode()).isEqualTo(zip);
  }

  @Test(expected = IllegalArgumentException.class)
  public void inNullZipThrowsIllegalArgumentException() {

    try {
      UnitedStatesAddress.newUnitedStatesAddress().in((ZIP) null);
    }
    catch (IllegalArgumentException expected) {

      assertThat(expected).hasMessage("Zip is required");
      assertThat(expected).hasNoCause();

      throw expected;
    }
  }
}
