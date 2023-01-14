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

import org.cp.elements.io.IOUtils;

/**
 * Unit Tests for {@link Street}.
 *
 * @author John Blum
 * @see org.junit.Test
 * @see org.cp.domain.geo.model.Street
 * @since 0.1.0
 */
public class StreetUnitTests {

  private void assertStreet(Street actual, Street expected) {
    assertStreet(actual, expected.getNumber(), expected.getName(), expected.getType().orElse(null));
  }

  private void assertStreet(Street street, Integer number, String name) {

    assertStreet(street, number, name, null);
    assertThat(street.getType()).isNotPresent();
  }

  private void assertStreet(Street street, Integer number, String name, Street.Type streetType) {

    assertThat(street).isNotNull();
    assertThat(street.getName()).isEqualTo(name);
    assertThat(street.getNumber()).isEqualTo(number);
    assertThat(street.getType().orElse(null)).isEqualTo(streetType);
  }

  @Test
  public void constructNewStreet() {

    Street street = new Street(100, "Main");

    assertStreet(street, 100, "Main");
  }

  @Test
  public void constructNewStreetWithInvalidName() {

    Arrays.asList(null, "", "  ").forEach(name ->
      assertThatIllegalArgumentException()
        .isThrownBy(() -> new Street(100, name))
        .withMessage("Street name [%s] is required", name)
        .withNoCause());
  }

  @Test
  public void constructNewStreetWitNullNumber() {

    assertThatIllegalArgumentException()
      .isThrownBy(() -> new Street(null, "Main"))
      .withMessage("Street number is required")
      .withNoCause();
  }

  @Test
  public void ofStreetNumberAndName() {

    Street street = Street.of(100, "Main");

    assertStreet(street, 100, "Main");
  }

  @Test
  public void ofStreetNumberNameAndType() {

    Street street = Street.of(2, "One").as(Street.Type.WAY);

    assertStreet(street, 2, "One", Street.Type.WAY);
  }

  @Test
  public void fromStreet() {

    Street street = Street.of(100, "Main").as(Street.Type.STREET);
    Street streetCopy = Street.from(street);

    assertStreet(streetCopy, street);
  }

  @Test
  public void fromNullStreet() {

    assertThatIllegalArgumentException()
      .isThrownBy(() -> Street.from(null))
      .withMessage("Street to copy is required")
      .withNoCause();
  }

  @Test
  public void asAvenueSetsStreetTypeToAvenueReturnsStreet() {

    Street street = Street.of(100, "Park").asAvenue();

    assertStreet(street, 100, "Park", Street.Type.AVENUE);
  }

  @Test
  public void asBoulevardSetStreetTypeToBoulevardReturnsStreet() {

    Street street = Street.of(100, "Hollywood").asBoulevard();

    assertStreet(street, 100, "Hollywood", Street.Type.BOULEVARD);
  }

  @Test
  public void asDriveSetsStreetTypeToDriveReturnsStreet() {

    Street street = Street.of(100, "Rodeo").asDrive();

    assertStreet(street, 100, "Rodeo", Street.Type.DRIVE);
  }

  @Test
  public void asFreewaySetsStreetTypeToFreewayReturnsStreet() {

    Street street = Street.of(100, "Ventura").asFreeway();

    assertStreet(street, 100, "Ventura", Street.Type.FREEWAY);
  }

  @Test
  public void asHighwaySetsStreetTypeToHighwayReturnsStreet() {

    Street street = Street.of(100, "101").asHighway();

    assertStreet(street, 100, "101", Street.Type.HIGHWAY);
  }

  @Test
  public void asLaneSetsStreetTypeToLaneReturnsStreet() {

    Street street = Street.of(100, "Lois").asLane();

    assertStreet(street, 100, "Lois", Street.Type.LANE);
  }

  @Test
  public void asRoadSetsStreetTypeToRoadReturnsStreet() {

    Street street = Street.of(100, "Mound View").asRoad();

    assertStreet(street, 100, "Mound View", Street.Type.ROAD);
  }

  @Test
  public void asRouteSetsStreetTypeToRouteReturnsStreet() {

    Street street = Street.of(100, "66").asRoute();

    assertStreet(street, 100, "66", Street.Type.ROUTE);
  }

  @Test
  public void asStreetSetsStreetTypeToStreetReturnsStreet() {

    Street street = Street.of(100, "Oregon").asStreet();

    assertStreet(street, 100, "Oregon", Street.Type.STREET);
  }

  @Test
  public void asWaySetsStreetTypeToWayReturnStreet() {

    Street street = Street.of(100, "One").asWay();

    assertStreet(street, 100, "One", Street.Type.WAY);
  }

  @Test
  public void cloneIsCorrect() throws CloneNotSupportedException {

    Street street = Street.of(100, "Main").asStreet();

    Object streetClone = street.clone();

    assertThat(streetClone).isInstanceOf(Street.class);
    assertThat(streetClone).isNotSameAs(street);
    assertThat(streetClone).isEqualTo(street);
  }

  @Test
  public void comparedToEqualStreetReturnsZero() {

    Street streetOne = Street.of(100, "Main").asStreet();
    Street streetTwo = Street.of(100, "Main").asStreet();

    assertThat(streetOne).isNotSameAs(streetTwo);
    assertThat(streetOne).isEqualTo(streetTwo);
    assertThat(streetOne.compareTo(streetTwo)).isZero();
  }

  @Test
  @SuppressWarnings("all")
  public void compareToSelfReturnsZero() {

    Street street = Street.of(100, "Main").asStreet();

    assertThat(street.compareTo(street)).isZero();
  }

  @Test
  public void comparedToUnequalStreetReturnsNonZero() {

    Street streetOne = Street.of(100, "Main").asStreet();
    Street streetTwo = Street.of(100, "One").asWay();

    assertThat(streetOne).isNotSameAs(streetTwo);
    assertThat(streetOne).isNotEqualTo(streetTwo);
    assertThat(streetOne.compareTo(streetTwo)).isLessThan(0);
    assertThat(streetTwo.compareTo(streetOne)).isGreaterThan(0);
  }

  @Test
  public void equalStreetsAreEqual() {

    Street streetOne = Street.of(100, "Main").asStreet();
    Street streetTwo = Street.of(100, "Main").asStreet();

    assertThat(streetOne).isNotSameAs(streetTwo);
    assertThat(streetOne).isEqualTo(streetTwo);
  }

  @Test
  public void identicalStreetsAreEqual() {

    Street street = Street.of(100, "Main").asStreet();

    assertThat(street).isEqualTo(street);
  }

  @Test
  public void unequalStreetsAreNotEqual() {

    Street streetOne = Street.of(100, "Main").asStreet();
    Street streetTwo = Street.of(100, "One").asWay();

    assertThat(streetOne).isNotSameAs(streetTwo);
    assertThat(streetOne).isNotEqualTo(streetTwo);
  }

  @Test
  @SuppressWarnings("all")
  public void equalsNullIsNullSafeReturnsFalse() {
    assertThat(Street.of(100, "Main").asStreet().equals(null)).isFalse();
  }

  @Test
  @SuppressWarnings("all")
  public void equalsObjectReturnsFalse() {
    assertThat(Street.of(100, "Main").asStreet().equals("100 Main St.")).isFalse();
  }

  @Test
  public void hashCodeOfStreetIsCorrect() {

    Street street = Street.of(100, "Main");

    int hashCodeOne = street.hashCode();

    assertThat(hashCodeOne).isNotZero();
    assertThat(street.asStreet()).isSameAs(street);

    int hashCodeTwo = street.hashCode();

    assertThat(hashCodeTwo).isNotZero();
    assertThat(hashCodeTwo).isNotEqualTo(hashCodeOne);
    assertThat(street.hashCode()).isEqualTo(hashCodeTwo);
    assertThat(street).hasSameHashCodeAs(Street.of(100, "Main").asStreet());
    assertThat(street).doesNotHaveSameHashCodeAs(Street.of(100, "One").asWay());
  }

  @Test
  public void toStringWithTypeIsCorrect() {

    assertThat(Street.of(100, "Oregon").asStreet().toString()).isEqualTo("100 Oregon ST");
    assertThat(Street.of(100, "One").asWay().toString()).isEqualTo("100 One WY");
  }

  @Test
  public void toStringWithoutTypeIsCorrect() {
    assertThat(Street.of(100, "Main").toString()).isEqualTo("100 Main");
  }

  @Test
  public void streetSerializationIsCorrect() throws IOException, ClassNotFoundException {

    Street street = Street.of(100, "Oregon").asStreet();

    assertThat(street).isNotNull();

    byte[] streetBytes = IOUtils.serialize(street);

    assertThat(streetBytes).isNotNull();
    assertThat(streetBytes).hasSizeGreaterThan(0);

    Street deserializedStreet = IOUtils.deserialize(streetBytes);

    assertThat(deserializedStreet).isNotNull();
    assertThat(deserializedStreet).isNotSameAs(street);
    assertThat(deserializedStreet).isEqualTo(street);
  }
}
