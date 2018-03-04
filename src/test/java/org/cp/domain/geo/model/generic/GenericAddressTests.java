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

package org.cp.domain.geo.model.generic;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Locale;

import org.cp.domain.geo.enums.Country;
import org.cp.domain.geo.model.City;
import org.cp.domain.geo.model.Coordinates;
import org.cp.domain.geo.model.PostalCode;
import org.cp.domain.geo.model.Street;
import org.cp.domain.geo.model.Unit;
import org.junit.Test;

/**
 * Unit tests for {@link GenericAddress}
 *
 * @author John Blum
 * @see org.junit.Test
 * @see org.cp.domain.geo.enums.Country
 * @see org.cp.domain.geo.model.generic.GenericAddress
 * @since 1.0.0
 */
public class GenericAddressTests {

  @Test
  public void newGenericAddressInLocalCountry() {

    GenericAddress address = GenericAddress.newGenericAddress();

    assertThat(address).isNotNull();
    assertThat(address.getStreet()).isNull();
    assertThat(address.getUnit().isPresent()).isFalse();
    assertThat(address.getCity()).isNull();
    assertThat(address.getPostalCode()).isNull();
    assertThat(address.getCountry()).isEqualTo(Country.byIsoThree(Locale.getDefault().getISO3Country()));
    assertThat(address.getType().isPresent()).isFalse();
    assertThat(address.getCoordinates().isPresent()).isFalse();
  }

  @Test
  public void newGenericAddressWithCountry() {

    GenericAddress address = GenericAddress.newGenericAddress(Country.UNITED_STATES_OF_AMERICA);

    assertThat(address).isNotNull();
    assertThat(address.getStreet()).isNull();
    assertThat(address.getUnit().isPresent()).isFalse();
    assertThat(address.getCity()).isNull();
    assertThat(address.getPostalCode()).isNull();
    assertThat(address.getCountry()).isEqualTo(Country.UNITED_STATES_OF_AMERICA);
    assertThat(address.getType().isPresent()).isFalse();
    assertThat(address.getCoordinates().isPresent()).isFalse();
  }

  @Test(expected = IllegalArgumentException.class)
  public void newGenericAddressWithNullCountryThrowsIllegalArgumentException() {

    try {
      GenericAddress.newGenericAddress(null);
    }
    catch (IllegalArgumentException expected) {

      assertThat(expected).hasMessage("Country is required");
      assertThat(expected).hasNoCause();

      throw expected;
    }
  }

  @Test
  public void ofStreetCityPostalCodeAndCountry() {

    Street street = Street.of(100, "Main").asStreet();
    City city = City.of("Portland");
    PostalCode postalCode = PostalCode.of("12345");
    Country country = Country.CANADA;

    GenericAddress address = GenericAddress.of(street, city, postalCode, country);

    assertThat(address).isNotNull();
    assertThat(address.getStreet()).isEqualTo(street);
    assertThat(address.getUnit().isPresent()).isFalse();
    assertThat(address.getCity()).isEqualTo(city);
    assertThat(address.getPostalCode()).isEqualTo(postalCode);
    assertThat(address.getCountry()).isEqualTo(country);
    assertThat(address.getType().isPresent()).isFalse();
    assertThat(address.getCoordinates().isPresent()).isFalse();
  }

  @Test(expected = IllegalArgumentException.class)
  public void ofNullStreetThrowsIllegalArgumentException() {

    testOfInvalidStreetCityPostalCodeOrCountryThrowsIllegalArgumentException(
      null, City.of("Portland"), PostalCode.of("12345"), Country.UNITED_STATES_OF_AMERICA,
        "Street is required");
  }

  @Test(expected = IllegalArgumentException.class)
  public void ofNullCityThrowsIllegalArgumentException() {

    testOfInvalidStreetCityPostalCodeOrCountryThrowsIllegalArgumentException(
      Street.of(100, "One").asWay(), null, PostalCode.of("12345"), Country.UNITED_STATES_OF_AMERICA,
        "City is required");
  }

  @Test(expected = IllegalArgumentException.class)
  public void ofNullPostalCodeThrowsIllegalArgumentException() {

    testOfInvalidStreetCityPostalCodeOrCountryThrowsIllegalArgumentException(
      Street.of(100, "One").asWay(), City.of("Portland"), null, Country.UNITED_STATES_OF_AMERICA,
        "Postal Code is required");
  }

  @Test(expected = IllegalArgumentException.class)
  public void ofNullCountryThrowsIllegalArgumentException() {

    testOfInvalidStreetCityPostalCodeOrCountryThrowsIllegalArgumentException(
      Street.of(100, "One").asWay(), City.of("Portland"), PostalCode.of("12345"), null,
        "Country is required");
  }

  public void testOfInvalidStreetCityPostalCodeOrCountryThrowsIllegalArgumentException(
      Street street, City city, PostalCode postalCode, Country country, String message) {

    try {
      GenericAddress.of(street, city, postalCode, country);
    }
    catch (IllegalArgumentException expected) {

      assertThat(expected).hasMessage(message);
      assertThat(expected).hasNoCause();

      throw expected;
    }
  }

  @Test
  public void fromAddressCopiesAddress() {

    GenericAddress address = GenericAddress
      .of(Street.of(100, "Main").asStreet(), City.of("Portland"), PostalCode.of("12345"), Country.CANADA)
      .with(Coordinates.of(1.0d, 2.0d))
      .in(Unit.suite("16"))
      .asHome();

    GenericAddress addressCopy = GenericAddress.from(address);

    assertThat(addressCopy).isNotNull();
    assertThat(addressCopy).isEqualTo(address);
    assertThat(addressCopy.getCoordinates()).isEqualTo(address.getCoordinates());
    assertThat(addressCopy.getType()).isEqualTo(address.getType());
  }

  @Test(expected = IllegalArgumentException.class)
  public void fromNullAddressThrowsIllegalArgumentException() {

    try {
      GenericAddress.from(null);
    }
    catch (IllegalArgumentException expected) {

      assertThat(expected).hasMessage("Address to copy is required");
      assertThat(expected).hasNoCause();

      throw expected;
    }
  }
}
