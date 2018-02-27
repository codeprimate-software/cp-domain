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

import org.junit.Test;

/**
 * Unit tests for {@link City}.
 *
 * @author John Blum
 * @see org.junit.Test
 * @see org.cp.domain.geo.model.City
 * @since 1.0.0
 */
public class CityTests {

  @Test
  public void constructCityWithName() {

    City city = new City("Portland");

    assertThat(city).isNotNull();
    assertThat(city.getName()).isEqualTo("Portland");
    assertThat(city.getCountry().isPresent()).isFalse();
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructCityWithBlankNameThrowsIllegalArgumentException() {
    testConstructCityWithInvalidNameThrowsIllegalArgumentException("  ");
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructCityWithEmptyNameThrowsIllegalArgumentException() {
    testConstructCityWithInvalidNameThrowsIllegalArgumentException("");
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructCityWithNullNameThrowsIllegalArgumentException() {
    testConstructCityWithInvalidNameThrowsIllegalArgumentException(null);
  }

  private void testConstructCityWithInvalidNameThrowsIllegalArgumentException(String name) {

    try {
      new City(name);
    }
    catch (IllegalArgumentException expected) {

      assertThat(expected).hasMessage("City name [%s] is required", name);
      assertThat(expected).hasNoCause();

      throw expected;
    }
  }

  @Test
  public void ofName() {

    City city = City.of("Portland");

    assertThat(city).isNotNull();
    assertThat(city.getName()).isEqualTo("Portland");
    assertThat(city.getCountry().isPresent()).isFalse();
  }

  @Test
  public void fromCopiesCity() {

    City city = City.of("Portland");
    City cityCopy = City.from(city);

    assertThat(cityCopy).isNotNull();
    assertThat(cityCopy).isNotSameAs(city);
    assertThat(cityCopy).isEqualTo(city);
  }

  @Test(expected = IllegalArgumentException.class)
  public void fromNullCityThrowsIllegalArgumentException() {

    try {
      City.from(null);
    }
    catch (IllegalArgumentException expected) {

      assertThat(expected).hasMessage("City is required");
      assertThat(expected).hasNoCause();

      throw expected;
    }
  }

  @Test
  public void cloneIsSuccessful() throws CloneNotSupportedException {

    City city = City.of("Portland");
    City cityClone = (City) city.clone();

    assertThat(cityClone).isNotNull();
    assertThat(cityClone).isNotSameAs(city);
    assertThat(cityClone).isEqualTo(city);
  }

  @Test
  public void comparedToEqualCityReturnsZero() {

    City cityOne = City.of("Portland");
    City cityTwo = City.of("Portland");

    assertThat(cityOne).isNotSameAs(cityTwo);
    assertThat(cityOne).isEqualTo(cityTwo);
    assertThat(cityOne.compareTo(cityTwo)).isZero();
  }

  @Test
  public void comparedToGreaterCityReturnsLessThanZero() {

    City cityOne = City.of("Portland");
    City cityTwo = City.of("Seattle");

    assertThat(cityOne).isNotSameAs(cityTwo);
    assertThat(cityOne).isNotEqualTo(cityTwo);
    assertThat(cityOne.compareTo(cityTwo)).isLessThan(0);
  }

  @Test
  public void comparedToLesserCityReturnsGreaterThanZero() {

    City cityOne = City.of("Portland");
    City cityTwo = City.of("Cuba City");

    assertThat(cityOne).isNotSameAs(cityTwo);
    assertThat(cityOne).isNotEqualTo(cityTwo);
    assertThat(cityOne.compareTo(cityTwo)).isGreaterThan(0);
  }

  @Test
  public void equalsNullReturnsFalse() {
    assertThat(City.of("Seattle")).isNotEqualTo(null);
  }

  @Test
  public void equalCitiesAreEqual() {

    City cityOne = City.of("Portland");
    City cityTwo = City.of("Portland");

    assertThat(cityOne).isNotSameAs(cityTwo);
    assertThat(cityOne).isEqualTo(cityTwo);
  }

  @Test
  public void identicalCitiesAreEqual() {

    City city = City.of("Seattle");

    assertThat(city).isEqualTo(city);
  }

  @Test
  public void unequalCitiesAreNotEqual() {

    City cityOne = City.of("Portland");
    City cityTwo = City.of("Seattle");

    assertThat(cityOne).isNotSameAs(cityTwo);
    assertThat(cityOne).isNotEqualTo(cityTwo);
  }

  @Test
  public void hashCodeOfCityIsCorrect() {

    City portland = City.of("Portland");
    City seattle = City.of("Seattle");

    assertThat(portland.hashCode()).isNotZero();
    assertThat(portland.hashCode()).isEqualTo(portland.hashCode());
    assertThat(portland.hashCode()).isNotEqualTo(seattle.hashCode());
  }

  @Test
  public void toStringIsSameAsCityName() {
    assertThat(City.of("Portland").toString()).isEqualTo("Portland");
  }
}
