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
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import org.cp.domain.geo.enums.Direction;
import org.cp.elements.io.IOUtils;
import org.cp.elements.lang.ThrowableAssertions;

/**
 * Unit Tests for {@link Street}.
 *
 * @author John Blum
 * @see org.junit.jupiter.api.Test
 * @see org.cp.domain.geo.model.Street
 * @since 0.1.0
 */
public class StreetUnitTests {

  private void assertStreet(Street actual, Street expected) {
    assertStreet(actual, expected.getNumber(), expected.getName(), expected.getType().orElse(null));
  }

  private void assertStreet(Street street, Integer number, String name) {

    assertStreet(street, number, name, null);
    assertThat(street.getDirection()).isNotPresent();
    assertThat(street.getType()).isNotPresent();
  }

  private void assertStreet(Street street, Integer number, String name, Street.Type streetType) {

    assertThat(street).isNotNull();
    assertThat(street.getName()).isEqualTo(name);
    assertThat(street.getNumber()).isEqualTo(number);
    assertThat(street.getType().orElse(null)).isEqualTo(streetType);
  }

  private void assertStreet(Street street, Integer number, String name, Street.Type streetType, Direction direction) {

    assertThat(street).isNotNull();
    assertThat(street.getName()).isEqualTo(name);
    assertThat(street.getNumber()).isEqualTo(number);
    assertThat(street.getType().orElse(null)).isEqualTo(streetType);
    assertThat(street.getDirection().orElse(null)).isEqualTo(direction);
  }

  @Test
  void constructNewStreet() {

    Street street = new Street(100, "Main");

    assertStreet(street, 100, "Main");
  }

  @Test
  void constructNewStreetWithInvalidName() {

    Arrays.asList("  ", "", null).forEach(invalidName ->
      assertThatIllegalArgumentException()
        .isThrownBy(() -> new Street(100, invalidName))
        .withMessage("Street name [%s] is required", invalidName)
        .withNoCause());
  }

  @Test
  void constructNewStreetWitNullNumber() {

    assertThatIllegalArgumentException()
      .isThrownBy(() -> new Street(null, "Main"))
      .withMessage("Street number is required")
      .withNoCause();
  }

  @Test
  void fromStreet() {

    Street street = Street.of(100, "Main").as(Street.Type.STREET);
    Street streetCopy = Street.from(street);

    assertStreet(streetCopy, street);
  }

  @Test
  void fromNullStreet() {

    assertThatIllegalArgumentException()
      .isThrownBy(() -> Street.from(null))
      .withMessage("Street to copy is required")
      .withNoCause();
  }

  @Test
  void ofStreetNumberAndName() {

    Street street = Street.of(100, "Main");

    assertStreet(street, 100, "Main");
  }

  @Test
  void ofStreetNumberNameAndType() {

    Street street = Street.of(2, "One").as(Street.Type.WAY);

    assertStreet(street, 2, "One", Street.Type.WAY);
  }

  @Test
  void parseStreetWithNumberAndNameIsCorrect() {

    Street street = Street.parse("100 Main");

    assertStreet(street, 100, "Main");
  }

  @Test
  void parseStreetWithNumberDirectionNameAndTypeIsCorrect() {

    Street street = Street.parse("767 SW Airline Rd");

    assertStreet(street, 767, "Airline", Street.Type.ROAD, Direction.SOUTHWEST);
  }

  @Test
  void parseStreetWithNumberDirectionAndNameIsCorrect() {

    Street street = Street.parse("8421 NE Main");

    assertStreet(street, 8421, "Main", null, Direction.NORTHEAST);
  }

  @Test
  void parseStreetWithNumberNameAndTypeIsCorrect() {

    Street street = Street.parse("1248 One Way");

    assertStreet(street, 1248, "One", Street.Type.WAY, null);
  }

  @Test
  void parseStreetWithNumberNumericNameAndTypeIsCorrect() {

    Street street = Street.parse("99 10th Ave");

    assertStreet(street, 99, "10th", Street.Type.AVENUE, null);
  }

  @Test
  void parseStreetWithSpacing() {

    Street street = Street.parse("  101  SouTH 5th   AVE ");

    assertStreet(street, 101, "5th", Street.Type.AVENUE, Direction.SOUTH);
  }

  @Test
  void parseStreetWithIllegalStreetsThrowsIllegalArgumentException() {

    Arrays.asList("  ", "", null).forEach(illegalStreet ->
      assertThatIllegalArgumentException()
        .isThrownBy(() -> Street.parse(illegalStreet))
        .withMessage("Street [%s] to parse is required", illegalStreet)
        .withNoCause());
  }

  @Test
  void parseStreetWithNoNameThrowsIllegalArgumentException() {

    assertThatIllegalArgumentException()
      .isThrownBy(() -> Street.parse("100"))
      .withMessage("Street [100] must minimally consist of a number and name")
      .withNoCause();
  }

  @Test
  void parseStreetWithNoNumberThrowsIllegalArgumentException() {

    Arrays.asList("10th Ave", "One Way").forEach(illegalStreet ->
      ThrowableAssertions.assertThatIllegalArgumentException()
        .isThrownBy(args -> Street.parse(illegalStreet))
        .havingMessage("Street [%s] must begin with a street number", illegalStreet)
        .causedBy(NumberFormatException.class)
        .withNoCause());
  }

  @Test
  void asAvenueSetsStreetTypeToAvenueReturnsStreet() {

    Street street = Street.of(100, "Park").asAvenue();

    assertStreet(street, 100, "Park", Street.Type.AVENUE);
  }

  @Test
  void asBoulevardSetStreetTypeToBoulevardReturnsStreet() {

    Street street = Street.of(100, "Hollywood").asBoulevard();

    assertStreet(street, 100, "Hollywood", Street.Type.BOULEVARD);
  }

  @Test
  void asDriveSetsStreetTypeToDriveReturnsStreet() {

    Street street = Street.of(100, "Rodeo").asDrive();

    assertStreet(street, 100, "Rodeo", Street.Type.DRIVE);
  }

  @Test
  void asFreewaySetsStreetTypeToFreewayReturnsStreet() {

    Street street = Street.of(100, "Ventura").asFreeway();

    assertStreet(street, 100, "Ventura", Street.Type.FREEWAY);
  }

  @Test
  void asHighwaySetsStreetTypeToHighwayReturnsStreet() {

    Street street = Street.of(100, "101").asHighway();

    assertStreet(street, 100, "101", Street.Type.HIGHWAY);
  }

  @Test
  void asLaneSetsStreetTypeToLaneReturnsStreet() {

    Street street = Street.of(100, "Lois").asLane();

    assertStreet(street, 100, "Lois", Street.Type.LANE);
  }

  @Test
  void asRoadSetsStreetTypeToRoadReturnsStreet() {

    Street street = Street.of(100, "Mound View").asRoad();

    assertStreet(street, 100, "Mound View", Street.Type.ROAD);
  }

  @Test
  void asRouteSetsStreetTypeToRouteReturnsStreet() {

    Street street = Street.of(100, "66").asRoute();

    assertStreet(street, 100, "66", Street.Type.ROUTE);
  }

  @Test
  void asStreetSetsStreetTypeToStreetReturnsStreet() {

    Street street = Street.of(100, "Oregon").asStreet();

    assertStreet(street, 100, "Oregon", Street.Type.STREET);
  }

  @Test
  void asWaySetsStreetTypeToWayReturnStreet() {

    Street street = Street.of(100, "One").asWay();

    assertStreet(street, 100, "One", Street.Type.WAY);
  }

  @Test
  void withDirectionReturnsStreet() {

    Street street = Street.of(6700, "Oregon")
      .withDirection(Direction.NORTHEAST)
      .asStreet();

    assertStreet(street, 6700, "Oregon", Street.Type.STREET, Direction.NORTHEAST);
  }

  @Test
  void cloneIsCorrect() throws CloneNotSupportedException {

    Street street = Street.of(100, "Main").asStreet();

    Object streetClone = street.clone();

    assertThat(streetClone).isInstanceOf(Street.class);
    assertThat(streetClone).isNotSameAs(street);
    assertThat(streetClone).isEqualTo(street);
  }

  @Test
  void comparedToEqualStreetReturnsZero() {

    Street streetOne = Street.of(100, "Main").asStreet();
    Street streetTwo = Street.of(100, "Main").asStreet();

    assertThat(streetOne).isNotSameAs(streetTwo);
    assertThat(streetOne).isEqualTo(streetTwo);
    assertThat(streetOne.compareTo(streetTwo)).isZero();
    assertThat(streetTwo.compareTo(streetOne)).isZero();
  }

  @Test
  @SuppressWarnings("all")
  void compareToSelfReturnsZero() {

    Street street = Street.of(100, "Main").asStreet();

    assertThat(street.compareTo(street)).isZero();
  }

  @Test
  void compareToStreetWithDirection() {

    Street streetW = Street.of(100, "Test").withDirection(Direction.WEST).asDrive();
    Street streetNe = Street.of(100, "Test").withDirection(Direction.NORTHEAST).asStreet();
    Street streetNw = Street.of(100, "Test").withDirection(Direction.NORTHWEST).asStreet();
    Street streetSe = Street.of(100, "Test").withDirection(Direction.SOUTHEAST).asStreet();
    Street streetE = Street.of(100, "Test").withDirection(Direction.EAST).asWay();

    assertThat(Stream.of(streetSe, streetNw, streetNe, streetW, streetE).sorted())
      .containsExactly(streetW, streetNe, streetNw, streetSe, streetE);
  }

  @Test
  void comparedToUnequalStreetReturnsNonZero() {

    Street streetOne = Street.of(100, "Main").asStreet();
    Street streetTwo = Street.of(100, "One").asWay();

    assertThat(streetOne).isNotSameAs(streetTwo);
    assertThat(streetOne).isNotEqualTo(streetTwo);
    assertThat(streetOne.compareTo(streetTwo)).isLessThan(0);
    assertThat(streetTwo.compareTo(streetOne)).isGreaterThan(0);
  }

  @Test
  void equalStreetsAreEqual() {

    Street streetOne = Street.of(100, "Main").asStreet();
    Street streetTwo = Street.of(100, "Main").asStreet();

    assertThat(streetOne).isNotSameAs(streetTwo);
    assertThat(streetOne).isEqualTo(streetTwo);
    assertThat(streetOne.equals(streetTwo)).isTrue();
  }

  @Test
  @SuppressWarnings("all")
  void identicalStreetsAreEqual() {

    Street street = Street.of(100, "Main").asStreet();

    assertThat(street).isEqualTo(street);
    assertThat(street.equals(street)).isTrue();
  }

  @Test
  void unequalStreetsAreNotEqual() {

    Street streetOne = Street.of(100, "Main").asStreet();
    Street streetTwo = Street.of(100, "One").asWay();

    assertThat(streetOne).isNotSameAs(streetTwo);
    assertThat(streetOne).isNotEqualTo(streetTwo);
    assertThat(streetOne.equals(streetTwo)).isFalse();
  }

  @Test
  @SuppressWarnings("all")
  void equalsNullIsNullSafeReturnsFalse() {
    assertThat(Street.of(100, "Main").asStreet().equals(null)).isFalse();
  }

  @Test
  @SuppressWarnings("all")
  void equalsObjectReturnsFalse() {
    assertThat(Street.of(100, "Main").asStreet().equals("100 Main St.")).isFalse();
  }

  @Test
  void hashCodeOfStreetIsCorrect() {

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
  void toStringWithoutTypeIsCorrect() {
    assertThat(Street.of(100, "Main").toString()).isEqualTo("100 Main");
  }

  @Test
  void toStringWithTypeIsCorrect() {

    assertThat(Street.of(100, "Oregon").asStreet().toString()).isEqualTo("100 Oregon ST");
    assertThat(Street.of(100, "One").asWay().toString()).isEqualTo("100 One WY");
  }

  @Test
  void toStringWithDirectionAndType() {
    assertThat(Street.of(7652, "Oregon").withDirection(Direction.NORTHEAST).asStreet().toString())
      .isEqualTo("7652 NE Oregon ST");
  }

  @Test
  void streetSerializationIsCorrect() throws IOException, ClassNotFoundException {

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
