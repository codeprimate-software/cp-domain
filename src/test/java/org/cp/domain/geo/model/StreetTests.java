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
 * Unit tests for {@link Street}.
 *
 * @author John Blum
 * @see org.junit.Test
 * @see org.cp.domain.geo.model.Street
 * @since 1.0.0
 */
public class StreetTests {

  @Test
  public void constructStreetWithNameAndNumber() {

    Street street = new Street(100, "Main");

    assertThat(street).isNotNull();
    assertThat(street.getName()).isEqualTo("Main");
    assertThat(street.getNumber()).isEqualTo(100);
    assertThat(street.getType().isPresent()).isFalse();
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructStreetWithBlankNameThrowsIllegalArgumentException() {
    testConstructStreetWithInvalidNameThrowsIllegalArgumentException("  ");
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructStreetWithEmptyNameThrowsIllegalArgumentException() {
    testConstructStreetWithInvalidNameThrowsIllegalArgumentException("");
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructStreetWithNullNameThrowsIllegalArgumentException() {
    testConstructStreetWithInvalidNameThrowsIllegalArgumentException(null);
  }

  private void testConstructStreetWithInvalidNameThrowsIllegalArgumentException(String name) {

    try {
      new Street(100, name);
    }
    catch (IllegalArgumentException expected) {

      assertThat(expected).hasMessage("Street name [%s] is required", name);
      assertThat(expected).hasNoCause();

      throw expected;
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructStreetWitNullNumberThrowsIllegalArgumentException() {

    try {
      new Street(null, "Main");
    }
    catch (IllegalArgumentException expected) {

      assertThat(expected).hasMessage("Street number is required");
      assertThat(expected).hasNoCause();

      throw expected;
    }
  }

  @Test
  public void ofStreetNumberAndName() {

    Street street = Street.of(100, "Main");

    assertThat(street).isNotNull();
    assertThat(street.getName()).isEqualTo("Main");
    assertThat(street.getNumber()).isEqualTo(100);
    assertThat(street.getType().isPresent()).isFalse();
  }

  @Test
  public void ofStreetNumberNameAndType() {

    Street street = Street.of(100, "One").as(Street.Type.WAY);

    assertThat(street).isNotNull();
    assertThat(street.getName()).isEqualTo("One");
    assertThat(street.getNumber()).isEqualTo(100);
    assertThat(street.getType().orElse(null)).isEqualTo(Street.Type.WAY);
  }

  @Test
  public void fromStreet() {

    Street street = Street.of(100, "Main").as(Street.Type.STREET);
    Street streetCopy = Street.from(street);

    assertThat(streetCopy).isNotNull();
    assertThat(streetCopy.getName()).isEqualTo(street.getName());
    assertThat(streetCopy.getNumber()).isEqualTo(street.getNumber());
    assertThat(streetCopy.getType()).isEqualTo(street.getType());
  }

  @Test(expected = IllegalArgumentException.class)
  public void fromNullStreetThrowsIllegalArgumentException() {

    try {
      Street.from(null);
    }
    catch (IllegalArgumentException expected) {

      assertThat(expected).hasMessage("The Street to copy is required");
      assertThat(expected).hasNoCause();

      throw expected;
    }
  }

  @Test
  public void asAvenueSetsStreetTypeToAvenueReturnsThis() {

    Street street = Street.of(100, "Park").asAvenue();

    assertThat(street).isNotNull();
    assertThat(street.getName()).isEqualTo("Park");
    assertThat(street.getNumber()).isEqualTo(100);
    assertThat(street.getType().orElse(null)).isEqualTo(Street.Type.AVENUE);
  }

  @Test
  public void asBoulevardSetsStreetTypeToToBoulevardReturnsThis() {

    Street street = Street.of(100, "Hollywood").asBoulevard();

    assertThat(street).isNotNull();
    assertThat(street.getName()).isEqualTo("Hollywood");
    assertThat(street.getNumber()).isEqualTo(100);
    assertThat(street.getType().orElse(null)).isEqualTo(Street.Type.BOULEVARD);
  }

  @Test
  public void asDriveSetsStreetTypeToDriveReturnsThis() {

    Street street = Street.of(100, "Rodeo").asDrive();

    assertThat(street).isNotNull();
    assertThat(street.getName()).isEqualTo("Rodeo");
    assertThat(street.getNumber()).isEqualTo(100);
    assertThat(street.getType().orElse(null)).isEqualTo(Street.Type.DRIVE);
  }

  @Test
  public void asHighwaySetsStreetTypeToHighwayReturnsThis() {

    Street street = Street.of(100, "101").asHighway();

    assertThat(street).isNotNull();
    assertThat(street.getName()).isEqualTo("101");
    assertThat(street.getNumber()).isEqualTo(100);
    assertThat(street.getType().orElse(null)).isEqualTo(Street.Type.HIGHWAY);
  }

  @Test
  public void asLaneSetsStreetTypeToLaneReturnsThis() {

    Street street = Street.of(100, "Lois").asLane();

    assertThat(street).isNotNull();
    assertThat(street.getName()).isEqualTo("Lois");
    assertThat(street.getNumber()).isEqualTo(100);
    assertThat(street.getType().orElse(null)).isEqualTo(Street.Type.LANE);
  }

  @Test
  public void asRoadSetsStreetTypeToRoadReturnsThis() {

    Street street = Street.of(100, "John Deere").asRoad();

    assertThat(street).isNotNull();
    assertThat(street.getName()).isEqualTo("John Deere");
    assertThat(street.getNumber()).isEqualTo(100);
    assertThat(street.getType().orElse(null)).isEqualTo(Street.Type.ROAD);
  }

  @Test
  public void asRouteSetsStreetTypeToRouteReturnsThis() {

    Street street = Street.of(100, "66").asRoute();

    assertThat(street).isNotNull();
    assertThat(street.getName()).isEqualTo("66");
    assertThat(street.getNumber()).isEqualTo(100);
    assertThat(street.getType().orElse(null)).isEqualTo(Street.Type.ROUTE);
  }

  @Test
  public void asStreetSetsStreetTypeToStreetReturnsThis() {

    Street street = Street.of(100, "Oregon").asStreet();

    assertThat(street).isNotNull();
    assertThat(street.getName()).isEqualTo("Oregon");
    assertThat(street.getNumber()).isEqualTo(100);
    assertThat(street.getType().orElse(null)).isEqualTo(Street.Type.STREET);
  }

  @Test
  public void asWaySetsStreetTypeToWayReturnThis() {

    Street street = Street.of(100, "One").asWay();

    assertThat(street).isNotNull();
    assertThat(street.getName()).isEqualTo("One");
    assertThat(street.getNumber()).isEqualTo(100);
    assertThat(street.getType().orElse(null)).isEqualTo(Street.Type.WAY);
  }

  @Test
  public void compareToEqualStreetsReturnsZero() {

    Street streetOne = Street.of(100, "Main").asStreet();
    Street streetTwo = Street.of(100, "Main").asStreet();

    assertThat(streetOne).isNotSameAs(streetTwo);
    assertThat(streetOne).isEqualTo(streetTwo);
    assertThat(streetOne.compareTo(streetTwo)).isZero();
  }

  @Test
  public void compareToGreeterStreetReturnsLessThanZero() {

    Street streetOne = Street.of(100, "Main").asStreet();
    Street streetTwo = Street.of(100, "One").asWay();

    assertThat(streetOne).isNotSameAs(streetTwo);
    assertThat(streetOne).isNotEqualTo(streetTwo);
    assertThat(streetOne.compareTo(streetTwo)).isLessThan(0);
  }

  @Test
  public void compareToLesserStreetReturnsGreeterThanZero() {

    Street streetOne = Street.of(100, "Main").asStreet();
    Street streetTwo = Street.of(100, "66").asRoute();

    assertThat(streetOne).isNotSameAs(streetTwo);
    assertThat(streetOne).isNotEqualTo(streetTwo);
    assertThat(streetOne.compareTo(streetTwo)).isGreaterThan(0);
  }

  @Test
  public void differentStreetsAreNotEqual() {

    Street streetOne = Street.of(100, "Main").asStreet();
    Street streetTwo = Street.of(100, "One").asWay();

    assertThat(streetOne).isNotSameAs(streetTwo);
    assertThat(streetOne).isNotEqualTo(streetTwo);
  }

  @Test
  public void equalStreetsAreEqual() {

    Street streetOne = Street.of(100, "Main").asStreet();
    Street streetTwo = Street.of(100, "Main").asStreet();

    assertThat(streetOne).isNotSameAs(streetTwo);
    assertThat(streetOne).isEqualTo(streetTwo);
  }

  @Test
  public void equalToNullReturnsFalse() {
    assertThat(Street.of(100, "Main").asStreet()).isNotEqualTo(null);
  }

  @Test
  public void identicalStreetsAreEqual() {

    Street street = Street.of(100, "Main").asStreet();

    assertThat(street).isEqualTo(street);
  }

  @Test
  public void hashCodeIsCorrect() {

    Street street = Street.of(100, "Main");

    int hashCodeOne = street.hashCode();

    assertThat(hashCodeOne).isNotZero();

    street.asStreet();

    int hashCodeTwo = street.hashCode();

    assertThat(hashCodeTwo).isNotZero();
    assertThat(hashCodeTwo).isNotEqualTo(hashCodeOne);
    assertThat(street.hashCode()).isEqualTo(hashCodeTwo);
  }

  @Test
  public void toStringReturnsStreetAsString() {
    assertThat(Street.of(100, "Oregon").asStreet().toString()).isEqualTo("100 Oregon ST");
    assertThat(Street.of(100, "One").asWay().toString()).isEqualTo("100 One WY");
  }
}
