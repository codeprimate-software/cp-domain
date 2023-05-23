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

import org.junit.jupiter.api.Test;

import org.cp.elements.io.IOUtils;

/**
 * Unit Tests for {@link Unit}.
 *
 * @author John Blum
 * @see org.junit.jupiter.api.Test
 * @see org.cp.domain.geo.model.Unit
 * @since 0.1.0
 */
public class UnitUnitTests {

  private void assertUnit(Unit actual, Unit expected) {

    assertThat(actual).isNotNull();
    assertThat(actual).isNotSameAs(expected);
    assertThat(actual).isEqualTo(expected);
    assertUnit(actual, expected.getNumber(), expected.getType().orElse(null));
  }

  private void assertUnit(Unit unit, String number, Unit.Type type) {

    assertThat(unit).isNotNull();
    assertThat(unit.getNumber()).isEqualTo(number);
    assertThat(unit.getType().orElse(null)).isEqualTo(type);
  }

  @Test
  public void constructNewUnitWithNumber() {

    Unit unit = new Unit("101");

    assertThat(unit).isNotNull();
    assertThat(unit.getNumber()).isEqualTo("101");
    assertThat(unit.getType()).isNotPresent();
  }

  @Test
  public void constructNewUnitWithInvalidNumber() {

    Arrays.asList(null, "", "  ").forEach(number ->
      assertThatIllegalArgumentException()
        .isThrownBy(() -> new Unit(number))
        .withMessage("Number [%s] is required", number)
        .withNoCause());
  }

  @Test
  public void apartmentNumberIsCorrect() {

    Unit apartment = Unit.apartment("A100");

    assertUnit(apartment, "A100", Unit.Type.APARTMENT);
  }

  @Test
  public void officeNumberIsCorrect() {

    Unit office = Unit.office("101");

    assertUnit(office, "101", Unit.Type.OFFICE);
  }

  @Test
  public void roomNumberIsCorrect() {

    Unit room = Unit.room("RM121");

    assertUnit(room, "RM121", Unit.Type.ROOM);
  }

  @Test
  public void suiteNumberIsCorrect() {

    Unit suite = Unit.suite("16");

    assertUnit(suite, "16", Unit.Type.SUITE);
  }

  @Test
  public void fromCopiesUnit() {

    Unit suiteSixteen = Unit.suite("16");
    Unit unitCopy = Unit.from(suiteSixteen);

    assertUnit(unitCopy, suiteSixteen);
  }

  @Test
  public void fromNullUnit() {

    assertThatIllegalArgumentException()
      .isThrownBy(() -> Unit.from(null))
      .withMessage("Unit to copy is required")
      .withNoCause();
  }

  @Test
  public void ofNumberConstructsNewUnitInitializedWithNumber() {

    Unit unit = Unit.of("101");

    assertUnit(unit, "101", Unit.Type.UNKNOWN);
  }

  @Test
  public void asTypeSetsTypeReturnsUnit() {

    Unit unit = Unit.of("100");

    assertUnit(unit, "100", Unit.Type.UNKNOWN);
    assertThat(unit.as(Unit.Type.ROOM)).isSameAs(unit);
    assertUnit(unit, "100", Unit.Type.ROOM);
  }

  @Test
  public void asApartmentSetsTypeToApartmentReturnsUnit() {

    Unit unit = Unit.of("100");

    assertUnit(unit, "100", Unit.Type.UNKNOWN);
    assertThat(unit.asApartment()).isSameAs(unit);
    assertUnit(unit, "100", Unit.Type.APARTMENT);
  }

  @Test
  public void asOfficeSetsTypeToOfficeReturnsUnit() {

    Unit unit = Unit.of("100");

    assertUnit(unit, "100", Unit.Type.UNKNOWN);
    assertThat(unit.asOffice()).isSameAs(unit);
    assertUnit(unit, "100", Unit.Type.OFFICE);
  }

  @Test
  public void asRoomSetsTypeToRoomReturnsUnit() {

    Unit unit = Unit.of("100");

    assertUnit(unit, "100", Unit.Type.UNKNOWN);
    assertThat(unit.asRoom()).isSameAs(unit);
    assertUnit(unit, "100", Unit.Type.ROOM);
  }

  @Test
  public void asSuiteSetsTypeToSuiteReturnsUnit() {

    Unit unit = Unit.of("100");

    assertUnit(unit, "100", Unit.Type.UNKNOWN);
    assertThat(unit.asSuite()).isSameAs(unit);
    assertUnit(unit, "100", Unit.Type.SUITE);
  }

  @Test
  public void cloneIsSuccessful() throws CloneNotSupportedException {

    Unit unit = Unit.suite("16");
    Object unitClone = unit.clone();

    assertThat(unitClone).isInstanceOf(Unit.class);
    assertThat(unitClone).isNotSameAs(unit);
    assertThat(unitClone).isEqualTo(unitClone);
  }

  @Test
  public void comparedToEqualUnitReturnsZero() {

    Unit unitOne = Unit.suite("16");
    Unit unitTwo = Unit.suite("16");

    assertThat(unitOne).isEqualTo(unitTwo);
    assertThat(unitOne).isNotSameAs(unitTwo);
    assertThat(unitOne.compareTo(unitTwo)).isZero();
    assertThat(unitTwo.compareTo(unitOne)).isZero();
  }

  @Test
  @SuppressWarnings("all")
  public void compareToIdenticalUnitReturnsZero() {

    Unit unit = Unit.suite("16");

    assertThat(unit.compareTo(unit)).isZero();
  }

  @Test
  public void comparedToUnequalUnitReturnsNonZero() {

    Unit unitOne = Unit.apartment("A100");
    Unit unitTwo = Unit.suite("16");

    assertThat(unitOne).isNotEqualTo(unitTwo);
    assertThat(unitOne).isNotSameAs(unitTwo);
    assertThat(unitOne.compareTo(unitTwo)).isGreaterThan(0);
    assertThat(unitTwo.compareTo(unitOne)).isLessThan(0);
  }

  @Test
  @SuppressWarnings("all")
  public void equalsNullIsNullSafeReturnsFalse() {
    assertThat(Unit.office("101").equals(null)).isFalse();
  }

  @Test
  @SuppressWarnings("all")
  public void equalsObjectReturnsFalse() {
    assertThat(Unit.suite("16").equals("sweet 16")).isFalse();
  }

  @Test
  public void equalUnitsAreEqual() {

    Unit unitOne = Unit.suite("16");
    Unit unitTwo = Unit.suite("16");

    assertThat(unitOne).isNotSameAs(unitTwo);
    assertThat(unitOne).isEqualTo(unitTwo);
    assertThat(unitOne.equals(unitTwo)).isTrue();
  }

  @Test
  public void identicalUnitsAreEqual() {

    Unit unit = Unit.apartment("A100");

    assertThat(unit).isEqualTo(unit);
  }

  @Test
  public void unequalUnitsAreNotEqual() {

    Unit unitOne = Unit.apartment("A100");
    Unit unitTwo = Unit.suite("16");

    assertThat(unitOne).isNotSameAs(unitTwo);
    assertThat(unitOne).isNotEqualTo(unitTwo);
    assertThat(unitOne.equals(unitTwo)).isFalse();
  }

  @Test
  public void hashCodeIsCorrect() {

    Unit unit = Unit.apartment("100");

    int hashCodeOne = unit.hashCode();

    assertThat(hashCodeOne).isNotZero();
    assertThat(unit.hashCode()).isEqualTo(hashCodeOne);

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
    assertThat(Unit.of("16").toString()).isEqualTo("Unknown 16");
  }

  @Test
  public void unitSerializationIsCorrect() throws IOException, ClassNotFoundException {

    Unit unit = Unit.suite("16");

    assertUnit(unit, "16", Unit.Type.SUITE);

    byte[] unitBytes = IOUtils.serialize(unit);

    assertThat(unitBytes).isNotNull();
    assertThat(unitBytes).hasSizeGreaterThan(0);

    Unit deserializedUnit = IOUtils.deserialize(unitBytes);

    assertUnit(deserializedUnit, unit);
  }
}
