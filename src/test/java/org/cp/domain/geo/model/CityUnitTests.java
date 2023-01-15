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
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import java.io.IOException;
import java.util.Arrays;

import org.junit.Test;

import org.cp.domain.geo.enums.Country;
import org.cp.elements.io.IOUtils;

/**
 * Unit Tests for {@link City}.
 *
 * @author John Blum
 * @see org.junit.Test
 * @see org.cp.domain.geo.model.City
 * @since 1.0.0
 */
public class CityUnitTests {

  private void assertCity(City actual, City expected) {

    assertThat(actual).isNotNull();
    assertThat(actual).isNotSameAs(expected);
    assertThat(actual).isEqualTo(expected);
  }

  private void assertCity(City city, String name) {

    assertCity(city, name, null);
    assertThat(city.getCountry()).isNotPresent();
  }

  private void assertCity(City city, String name, Country country) {

    assertThat(city).isNotNull();
    assertThat(city.getName()).isEqualTo(name);
    assertThat(city.getCountry().orElse(null)).isEqualTo(country);
  }

  @Test
  public void constructNewCityWithName() {

    City city = new City("Portland");

    assertCity(city, "Portland");
  }

  @Test
  public void constructNewCityWithInvalidName() {

    Arrays.asList(null, "", "  ").forEach(name ->
      assertThatIllegalArgumentException()
        .isThrownBy(() -> new City(name))
        .withMessage("Name [%s] is required", name)
        .withNoCause());
  }

  @Test
  public void fromCity() {

    City city = City.of("Cuba City");
    City cityCopy = City.from(city);

    assertCity(city, cityCopy);
  }

  @Test
  public void fromNullCity() {

    assertThatIllegalArgumentException()
      .isThrownBy(() -> City.from(null))
      .withMessage("City to copy is required")
      .withNoCause();
  }

  @Test
  public void ofName() {

    City city = City.of("Dubuque");

    assertCity(city, "Dubuque");
  }

  @Test
  public void cloneIsSuccessful() throws CloneNotSupportedException {

    City city = City.of("Missoula");

    Object cityClone = city.clone();

    assertThat(cityClone).isInstanceOf(City.class);
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
    assertThat(cityTwo.compareTo(cityOne)).isZero();
  }

  @Test
  @SuppressWarnings("all")
  public void compareToIdenticalCityReturnsZero() {

    City city = City.of("Portland");

    assertThat(city.compareTo(city)).isZero();
  }

  @Test
  public void compareToUnequalCityReturnsNonZero() {

    City portland = City.of("Portland");
    City seattle = City.of("Seattle");

    assertThat(portland).isNotSameAs(seattle);
    assertThat(portland).isNotEqualTo(seattle);
    assertThat(portland.compareTo(seattle)).isLessThan(0);
    assertThat(seattle.compareTo(portland)).isGreaterThan(0);
  }

  @Test
  @SuppressWarnings("all")
  public void equalsNullIsNullSafeReturnsFalse() {
    assertThat(City.of("Seattle").equals(null)).isFalse();
  }

  @Test
  @SuppressWarnings("all")
  public void equalsObjectReturnsFalse() {
    assertThat(City.of("Seattle").equals("Seattle")).isFalse();
  }

  @Test
  public void equalCitiesAreEqual() {

    City cityOne = City.of("Portland");
    City cityTwo = City.of("Portland");

    assertThat(cityOne).isNotSameAs(cityTwo);
    assertThat(cityOne).isEqualTo(cityTwo);
    assertThat(cityOne.equals(cityTwo)).isTrue();
  }

  @Test
  public void identicalCitiesAreEqual() {

    City city = City.of("Seattle");

    assertThat(city).isEqualTo(city);
  }

  @Test
  public void unequalCitiesAreNotEqual() {

    City portland = City.of("Portland");
    City seattle = City.of("Seattle");

    assertThat(portland).isNotSameAs(seattle);
    assertThat(portland).isNotEqualTo(seattle);
    assertThat(portland.equals(seattle)).isFalse();
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
    assertThat(City.of("Seattle").toString()).isEqualTo("Seattle");
  }

  @Test
  public void citySerializationIsCorrect() throws IOException, ClassNotFoundException {

    City portland = City.of("Portland");

    assertCity(portland, "Portland");

    byte[] portlandBytes = IOUtils.serialize(portland);

    assertThat(portlandBytes).isNotNull();
    assertThat(portlandBytes).hasSizeGreaterThan(0);

    City deserializedPortland = IOUtils.deserialize(portlandBytes);

    assertCity(deserializedPortland, portland);
  }
}
