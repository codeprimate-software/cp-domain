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
 * Unit tests for {@link Unit}.
 *
 * @author John Blum
 * @see org.junit.Test
 * @see org.cp.domain.geo.model.Unit
 * @since 1.0.0
 */
public class UnitTests {

  @Test
  public void constructUnitWithNumber() {

    Unit unit = new Unit("101");

    assertThat(unit).isNotNull();
    assertThat(unit.getNumber()).isEqualTo("101");
    assertThat(unit.getType().isPresent()).isFalse();
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructUnitWithBlankNumberThrowsIllegalArgumentException() {
    testConstructUnitWithInvalidNumberThrowsIllegalArgumentException("  ");
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructUnitWithEmptyNumberThrowsIllegalArgumentException() {
    testConstructUnitWithInvalidNumberThrowsIllegalArgumentException("");
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructUnitWithNullNumberThrowsIllegalArgumentException() {
    testConstructUnitWithInvalidNumberThrowsIllegalArgumentException(null);
  }

  private void testConstructUnitWithInvalidNumberThrowsIllegalArgumentException(String number) {

    try {
      new Unit(number);
    }
    catch (IllegalArgumentException expected) {

      assertThat(expected).hasMessage("Unit number [%s] is required", number);
      assertThat(expected).hasNoCause();

      throw expected;
    }
  }

  @Test
  public void apartmentWithNumberIsCorrect() {

    Unit apartment = Unit.apartment("A100");

    assertThat(apartment).isNotNull();
    assertThat(apartment.getNumber()).isEqualTo("A100");
    assertThat(apartment.getType().orElse(null)).isEqualTo(Unit.Type.APARTMENT);
  }

  @Test
  public void officeWithNumberIsCorrect() {

    Unit office = Unit.office("101");

    assertThat(office).isNotNull();
    assertThat(office.getNumber()).isEqualTo("101");
    assertThat(office.getType().orElse(null)).isEqualTo(Unit.Type.OFFICE);
  }

  @Test
  public void roomWithNumberIsCorrect() {

    Unit room = Unit.room("RM121");

    assertThat(room).isNotNull();
    assertThat(room.getNumber()).isEqualTo("RM121");
    assertThat(room.getType().orElse(null)).isEqualTo(Unit.Type.ROOM);
  }

  @Test
  public void suiteWithNumberIsCorrect() {

    Unit suite = Unit.suite("16");

    assertThat(suite).isNotNull();
    assertThat(suite.getNumber()).isEqualTo("16");
    assertThat(suite.getType().orElse(null)).isEqualTo(Unit.Type.SUITE);
  }

  @Test
  public void ofConstructsUnitInitializedWithNumber() {

    Unit unit = Unit.of("101");

    assertThat(unit).isNotNull();
    assertThat(unit.getNumber()).isEqualTo("101");
    assertThat(unit.getType().isPresent()).isFalse();
  }

  @Test
  public void fromCopiesUnit() {

    Unit unit = Unit.of("16").as(Unit.Type.SUITE);
    Unit unitCopy = Unit.from(unit);

    assertThat(unitCopy).isNotNull();
    assertThat(unitCopy).isNotSameAs(unit);
    assertThat(unitCopy.getNumber()).isEqualTo(unit.getNumber());
    assertThat(unitCopy.getType()).isEqualTo(unit.getType());
  }

  @Test(expected = IllegalArgumentException.class)
  public void fromNullUnitThrowsIllegalArgumentException() {

    try {
      Unit.from(null);
    }
    catch (IllegalArgumentException expected) {

      assertThat(expected).hasMessage("Unit is required");
      assertThat(expected).hasNoCause();

      throw expected;
    }
  }

  @Test
  public void asTypeSetsUnitTypeAndReturnsThis() {

    Unit unit = Unit.of("100");

    assertThat(unit).isNotNull();
    assertThat(unit.as(Unit.Type.ROOM)).isSameAs(unit);
    assertThat(unit.getType().orElse(null)).isEqualTo(Unit.Type.ROOM);
  }

  @Test
  public void asApartmentSetsUnitTypeToApartmentReturnsThis() {

    Unit unit = Unit.of("100");

    assertThat(unit).isNotNull();
    assertThat(unit.asApartment()).isSameAs(unit);
    assertThat(unit.getType().orElse(null)).isEqualTo(Unit.Type.APARTMENT);
  }

  @Test
  public void asOfficeSetsUnitTypeToOfficeReturnsThis() {

    Unit unit = Unit.of("100");

    assertThat(unit).isNotNull();
    assertThat(unit.asOffice()).isSameAs(unit);
    assertThat(unit.getType().orElse(null)).isEqualTo(Unit.Type.OFFICE);
  }

  @Test
  public void asRoomSetsUnitTypeToRoomReturnsThis() {

    Unit unit = Unit.of("100");

    assertThat(unit).isNotNull();
    assertThat(unit.asRoom()).isSameAs(unit);
    assertThat(unit.getType().orElse(null)).isEqualTo(Unit.Type.ROOM);
  }

  @Test
  public void asSuiteSetsUnitTypeToSuiteReturnsThis() {

    Unit unit = Unit.of("100");

    assertThat(unit).isNotNull();
    assertThat(unit.asSuite()).isSameAs(unit);
    assertThat(unit.getType().orElse(null)).isEqualTo(Unit.Type.SUITE);
  }

  @Test
  public void cloneIsSuccessful() throws CloneNotSupportedException {

    Unit unit = Unit.suite("16");
    Unit unitClone = (Unit) unit.clone();

    assertThat(unitClone).isNotNull();
    assertThat(unitClone).isNotSameAs(unit);
    assertThat(unitClone).isEqualTo(unit);
  }

  @Test
  public void comparedToEqualUnitReturnsZero() {

    Unit unitOne = Unit.apartment("A100");
    Unit unitTwo = Unit.apartment("A100");

    assertThat(unitOne).isNotSameAs(unitTwo);
    assertThat(unitOne).isEqualTo(unitTwo);
    assertThat(unitOne.compareTo(unitTwo)).isZero();
  }

  @Test
  public void comparedToGreaterUnitReturnsLessThanZero() {

    Unit unitOne = Unit.apartment("A100");
    Unit unitTwo = Unit.suite("S2100");

    assertThat(unitOne).isNotSameAs(unitTwo);
    assertThat(unitOne).isNotEqualTo(unitTwo);
    assertThat(unitOne.compareTo(unitTwo)).isLessThan(0);
  }

  @Test
  public void comparedToLesserUnitReturnsGreaterThanZero() {

    Unit unitOne = Unit.room("RM001");
    Unit unitTwo = Unit.office("1");

    assertThat(unitOne).isNotSameAs(unitTwo);
    assertThat(unitOne).isNotEqualTo(unitTwo);
    assertThat(unitOne.compareTo(unitTwo)).isGreaterThan(0);
  }

  @Test
  public void equalsNullReturnsFalse() {
    assertThat(Unit.office("101")).isNotEqualTo(null);
  }

  @Test
  public void equalUnitsAreEqual() {

    Unit unitOne = Unit.suite("2100");
    Unit unitTwo = Unit.suite("2100");

    assertThat(unitOne).isNotSameAs(unitTwo);
    assertThat(unitOne).isEqualTo(unitTwo);
  }

  @Test
  public void identicalUnitsAreEqual() {

    Unit unit = Unit.apartment("A100");

    assertThat(unit).isEqualTo(unit);
  }

  @Test
  public void unequalUnitsAreNotEqual() {

    Unit unitOne = Unit.apartment("A100");
    Unit unitTwo = Unit.suite("2100");

    assertThat(unitOne).isNotSameAs(unitTwo);
    assertThat(unitOne).isNotEqualTo(unitTwo);
  }

  @Test
  public void hashCodeIsCorrect() {

    Unit unit = Unit.apartment("100");

    int hashCodeOne = unit.hashCode();

    assertThat(hashCodeOne).isNotZero();

    int hashCodeTwo = unit.asRoom().hashCode();

    assertThat(hashCodeTwo).isNotEqualTo(hashCodeOne);
    assertThat(unit.hashCode()).isEqualTo(hashCodeTwo);
  }

  @Test
  public void toStringWithTypeIsCorrect() {
    assertThat(Unit.suite("16").toString()).isEqualTo("Suite 16");
  }

  @Test
  public void toStringWithoutTypeIsCorrect() {
    assertThat(Unit.of("16").toString()).isEqualTo("Unit 16");
  }
}
