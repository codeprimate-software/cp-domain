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

import java.util.Arrays;

import org.junit.Test;

/**
 * Unit tests for {@link Unit.Type}.
 *
 * @author John Blum
 * @see org.junit.Test
 * @see org.cp.domain.geo.model.Unit.Type
 * @since 1.0.0
 */
public class UnitTypeTests {

  @Test
  public void unitTypeAbbreviations() {

    assertThat(Unit.Type.APARTMENT.getAbbreviation()).isEqualTo("APT");
    assertThat(Unit.Type.OFFICE.getAbbreviation()).isEqualTo("OFC");
    assertThat(Unit.Type.ROOM.getAbbreviation()).isEqualTo("RM");
    assertThat(Unit.Type.SUITE.getAbbreviation()).isEqualTo("STE");
    assertThat(Unit.Type.UNIT.getAbbreviation()).isEqualTo("UNT");
    assertThat(Unit.Type.UNKNOWN.getAbbreviation()).isEqualTo("UKN");
  }

  @Test
  public void unitTypeNames() {

    assertThat(Unit.Type.APARTMENT.getName()).isEqualTo("Apartment");
    assertThat(Unit.Type.OFFICE.getName()).isEqualTo("Office");
    assertThat(Unit.Type.ROOM.getName()).isEqualTo("Room");
    assertThat(Unit.Type.SUITE.getName()).isEqualTo("Suite");
    assertThat(Unit.Type.UNIT.getName()).isEqualTo("Unit");
    assertThat(Unit.Type.UNKNOWN.getName()).isEqualTo("Unknown");
  }

  @Test
  public void unitTypeToStringIsSameAsUnitTypeName() {

    Arrays.stream(Unit.Type.values()).forEach(unitType ->
      assertThat(unitType.toString()).isEqualTo(unitType.getName()));
  }

  @Test
  public void valueOfAbbreviationIsCorrect() {

    Arrays.stream(Unit.Type.values()).forEach(unitType ->
      assertThat(Unit.Type.valueOfAbbreviation(unitType.getAbbreviation())).isEqualTo(unitType));
  }

  @Test
  public void valueOfNameIsCorrect() {

    Arrays.stream(Unit.Type.values()).forEach(unitType ->
      assertThat(Unit.Type.valueOfName(unitType.getName())).isEqualTo(unitType));
  }
}
