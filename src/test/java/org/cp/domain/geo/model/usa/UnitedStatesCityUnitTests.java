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
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.withSettings;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import org.cp.domain.geo.enums.Country;
import org.cp.domain.geo.enums.State;
import org.cp.domain.geo.model.City;
import org.mockito.quality.Strictness;

/**
 * Unit Tests for {@link UnitedStatesCity}.
 *
 * @author John Blum
 * @see org.junit.jupiter.api.Test
 * @see org.mockito.Mockito
 * @see org.cp.domain.geo.model.City
 * @see org.cp.domain.geo.model.usa.UnitedStatesCity
 * @since 0.1.0
 */
public class UnitedStatesCityUnitTests {

  private City newCity(String name) {
    return newCity(name, Country.localCountry());
  }

  private City newCity(String name, Country country) {

    return new City(name) {

      @Override
      public Optional<Country> getCountry() {
        return Optional.ofNullable(country);
      }
    };
  }

  @Test
  public void constructNewUnitedStatesCity() {

    UnitedStatesCity city = new UnitedStatesCity("TestCity");

    assertThat(city).isNotNull();
    assertThat(city.getName()).isEqualTo("TestCity");
    assertThat(city.getState()).isNull();
    assertThat(city.getCountry().orElse(null)).isEqualTo(Country.UNITED_STATES_OF_AMERICA);
  }

  @Test
  public void constructNewUnitedStatesCityWithIllegalName() {

    Arrays.asList("  ", "", null).forEach(name ->
      assertThatIllegalArgumentException()
        .isThrownBy(() -> new UnitedStatesCity(name))
        .withMessage("Name [%s] is required", name)
        .withNoCause());
  }

  @Test
  public void newUnitedStatesCityIsCorrect() {

    UnitedStatesCity city = UnitedStatesCity.newUnitedStatesCity("TestCity");

    assertThat(city).isNotNull();
    assertThat(city.getName()).isEqualTo("TestCity");
    assertThat(city.getCountry().orElse(null)).isEqualTo(Country.UNITED_STATES_OF_AMERICA);
    assertThat(city.getState()).isNull();
  }

  @Test
  public void fromCity() {

    City mockCity = mock(City.class, withSettings().strictness(Strictness.LENIENT));

    doReturn("MockCity").when(mockCity).getName();
    doReturn(Optional.of(Country.UNITED_KINGDOM)).when(mockCity).getCountry();

    UnitedStatesCity city = UnitedStatesCity.from(mockCity);

    assertThat(city).isNotNull();
    assertThat(city).isNotSameAs(mockCity);
    assertThat(city.getName()).isEqualTo("MockCity");
    assertThat(city.getState()).isNull();
    assertThat(city.getCountry().orElse(null)).isEqualTo(Country.UNITED_STATES_OF_AMERICA);

    verify(mockCity, times(1)).getName();
    verifyNoMoreInteractions(mockCity);
  }

  @Test
  public void fromCityInCountryAndState() {

    UnitedStatesCity city = UnitedStatesCity.newUnitedStatesCity("NonMockCity").in(State.CALIFORNIA);
    UnitedStatesCity copy = UnitedStatesCity.from(city);

    assertThat(copy).isNotNull();
    assertThat(copy).isNotSameAs(city);
    assertThat(copy).isEqualTo(city);
    assertThat(copy.getName()).isEqualTo("NonMockCity");
    assertThat(copy.getState()).isEqualTo(State.CALIFORNIA);
    assertThat(copy.getCountry().orElse(null)).isEqualTo(Country.UNITED_STATES_OF_AMERICA);
  }

  @Test
  public void fromNullCity() {

    assertThatIllegalArgumentException()
      .isThrownBy(() -> UnitedStatesCity.from(null))
      .withMessage("City is required")
      .withNoCause();
  }

  @Test
  public void setAndGetState() {

    UnitedStatesCity city = UnitedStatesCity.newUnitedStatesCity("Portland");

    assertThat(city).isNotNull();
    assertThat(city.getName()).isEqualTo("Portland");
    assertThat(city.getState()).isNull();

    city.setState(State.MAINE);

    assertThat(city.getName()).isEqualTo("Portland");
    assertThat(city.getState()).isEqualTo(State.MAINE);

    city.setState(State.OREGON);

    assertThat(city.getName()).isEqualTo("Portland");
    assertThat(city.getState()).isEqualTo(State.OREGON);

    city.setState(State.WISCONSIN);

    assertThat(city.getName()).isEqualTo("Portland");
    assertThat(city.getState()).isEqualTo(State.WISCONSIN);
  }

  @Test
  public void setStateToNull() {

    assertThatIllegalArgumentException()
      .isThrownBy(() -> UnitedStatesCity.newUnitedStatesCity("TestCity").setState(null))
      .withMessage("State is required")
      .withNoCause();
  }

  @Test
  public void inState() {

    UnitedStatesCity city = UnitedStatesCity.newUnitedStatesCity("MockCity");

    assertThat(city).isNotNull();
    assertThat(city.getName()).isEqualTo("MockCity");
    assertThat(city.getState()).isNull();
    assertThat(city.in(State.WISCONSIN)).isSameAs(city);
    assertThat(city.getState()).isEqualTo(State.WISCONSIN);
    assertThat(city.in(State.IOWA)).isSameAs(city);
    assertThat(city.getState()).isEqualTo(State.IOWA);
    assertThat(city.in(State.MONTANA)).isSameAs(city);
    assertThat(city.getState()).isEqualTo(State.MONTANA);
    assertThat(city.in(State.OREGON)).isSameAs(city);
    assertThat(city.getState()).isEqualTo(State.OREGON);
  }

  @Test
  public void isCapitalReturnsFalseByDefault() {

    assertThat(UnitedStatesCity.newUnitedStatesCity("Portland").in(State.OREGON).isCapital()).isFalse();
    assertThat(UnitedStatesCity.newUnitedStatesCity("Madison").in(State.WISCONSIN).isCapital()).isFalse();
    assertThat(UnitedStatesCity.newUnitedStatesCity("Washington").in(State.DISTRICT_OF_COLUMBIA).isCapital()).isFalse();
  }

  @Test
  public void compareToIsCorrect() {

    City portland = newCity("Portland");
    City berlinGermany = newCity("Berlin", Country.GERMANY);
    City portlandGermany = newCity("Portland", Country.GERMANY);

    UnitedStatesCity cubaCityWisconsin = UnitedStatesCity.newUnitedStatesCity("Cuba City").in(State.WISCONSIN);
    UnitedStatesCity portlandOregon = UnitedStatesCity.newUnitedStatesCity("Portland").in(State.OREGON);
    UnitedStatesCity portlandMaine = UnitedStatesCity.newUnitedStatesCity("Portland").in(State.MAINE);

    assertThat(cubaCityWisconsin).isGreaterThan(portlandOregon);
    assertThat(cubaCityWisconsin.compareTo(portland)).isLessThan(0);
    assertThat(cubaCityWisconsin.compareTo(berlinGermany)).isGreaterThan(0);
    assertThat(cubaCityWisconsin.compareTo(portlandGermany)).isGreaterThan(0);
    assertThat(cubaCityWisconsin).isEqualByComparingTo(cubaCityWisconsin);
    assertThat(portlandMaine).isLessThan(portlandOregon);
    assertThat(portlandOregon).isGreaterThan(portlandMaine);
    assertThat(portlandOregon).isLessThan(cubaCityWisconsin);
    assertThat(portlandOregon.compareTo(portlandGermany)).isGreaterThan(0);
    assertThat(berlinGermany).isLessThan(portlandGermany);
    assertThat(portland).isGreaterThan(portlandGermany);
  }

  @Test
  public void equalsEqualCity() {

    UnitedStatesCity portlandOregonOne = UnitedStatesCity.newUnitedStatesCity("Portland").in(State.OREGON);
    UnitedStatesCity portlandOregonTwo = UnitedStatesCity.newUnitedStatesCity("Portland").in(State.OREGON);

    assertThat(portlandOregonOne).isEqualTo(portlandOregonTwo);
    assertThat(portlandOregonOne.equals(portlandOregonTwo)).isTrue();
  }

  @Test
  @SuppressWarnings("all")
  public void equalsSameCity() {

    UnitedStatesCity city = UnitedStatesCity.newUnitedStatesCity("Missoula").in(State.MONTANA);

    assertThat(city).isSameAs(city);
    assertThat(city.equals(city)).isTrue();
  }

  @Test
  @SuppressWarnings("all")
  public void equalsObjectReturnsFalse() {
    assertThat(UnitedStatesCity.newUnitedStatesCity("MockCity").in(State.IOWA).equals("MockCity, IA")).isFalse();
  }

  @Test
  @SuppressWarnings("all")
  public void equalsNullIsNullSafe() {
    assertThat(UnitedStatesCity.newUnitedStatesCity("TestCity").in(State.WISCONSIN).equals(null)).isFalse();
  }

  @Test
  public void equalsEquivalentCityInStateReturnsFalse() {

    assertThat(UnitedStatesCity.newUnitedStatesCity("Portland").in(State.OREGON))
      .isNotEqualTo(newCity("Portland", Country.UNITED_STATES_OF_AMERICA));
  }

  @Test
  public void equalsEquivalentCityInNoStateReturnsTrue() {

    assertThat(UnitedStatesCity.newUnitedStatesCity("Portland"))
      .isEqualTo(newCity("Portland", Country.UNITED_STATES_OF_AMERICA));
  }

  @Test
  public void equalsDifferentCityReturnsFalse() {

    assertThat(UnitedStatesCity.newUnitedStatesCity("Portland").in(State.OREGON))
      .isNotEqualTo(UnitedStatesCity.newUnitedStatesCity("Cuba City").in(State.WISCONSIN));
  }

  @Test
  public void equalsCityOutOfStateReturnsFalse() {

    assertThat(UnitedStatesCity.newUnitedStatesCity("Portland").in(State.OREGON))
      .isNotEqualTo(UnitedStatesCity.newUnitedStatesCity("Portland").in(State.MAINE));
  }

  @Test
  public void hashCodeIsCorrect() {

    City portland = newCity("Portland");

    UnitedStatesCity dubuqueIowa = UnitedStatesCity.newUnitedStatesCity("Dubuque").in(State.IOWA);
    UnitedStatesCity portlandMaine = UnitedStatesCity.newUnitedStatesCity("Portland").in(State.MAINE);
    UnitedStatesCity portlandOregon = UnitedStatesCity.newUnitedStatesCity("Portland").in(State.OREGON);

    assertThat(portlandOregon).hasSameHashCodeAs(portlandOregon);
    assertThat(portlandOregon).doesNotHaveSameHashCodeAs(portland);
    assertThat(portlandOregon).doesNotHaveSameHashCodeAs(dubuqueIowa);
    assertThat(portlandOregon).doesNotHaveSameHashCodeAs(portlandMaine);
    assertThat(portlandOregon.hashCode()).isEqualTo(portlandOregon.hashCode());
  }

  @Test
  public void toStringIsCorrect() {

    assertThat(UnitedStatesCity.newUnitedStatesCity("Portland").toString()).isEqualTo("Portland");
    assertThat(UnitedStatesCity.newUnitedStatesCity("Portland").in(State.OREGON).toString()).isEqualTo("Portland");
  }
}
