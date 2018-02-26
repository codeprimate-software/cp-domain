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
 * The StreetTypeTests class...
 *
 * @author John Blum
 * @since 1.0.0
 */
public class StreetTypeTests {

  @Test
  public void streetTypeAbbreviations() {

    assertThat(Street.Type.ALLEY.getAbbreviation()).isEqualTo("ALY");
    assertThat(Street.Type.AVENUE.getAbbreviation()).isEqualTo("AVE");
    assertThat(Street.Type.BOULEVARD.getAbbreviation()).isEqualTo("BLVD");
    assertThat(Street.Type.CIRCLE.getAbbreviation()).isEqualTo("CRCL");
    assertThat(Street.Type.COURT.getAbbreviation()).isEqualTo("CT");
    assertThat(Street.Type.DRIVE.getAbbreviation()).isEqualTo("DR");
    assertThat(Street.Type.HIGHWAY.getAbbreviation()).isEqualTo("HWY");
    assertThat(Street.Type.JUNCTION.getAbbreviation()).isEqualTo("JCT");
    assertThat(Street.Type.LANE.getAbbreviation()).isEqualTo("LN");
    assertThat(Street.Type.LOOP.getAbbreviation()).isEqualTo("LP");
    assertThat(Street.Type.PLAZA.getAbbreviation()).isEqualTo("PL");
    assertThat(Street.Type.ROAD.getAbbreviation()).isEqualTo("RD");
    assertThat(Street.Type.ROUTE.getAbbreviation()).isEqualTo("RT");
    assertThat(Street.Type.STREET.getAbbreviation()).isEqualTo("ST");
    assertThat(Street.Type.WAY.getAbbreviation()).isEqualTo("WY");
  }

  @Test
  public void streetTypeNames() {

    assertThat(Street.Type.ALLEY.getName()).isEqualTo("Alley");
    assertThat(Street.Type.AVENUE.getName()).isEqualTo("Avenue");
    assertThat(Street.Type.BOULEVARD.getName()).isEqualTo("Boulevard");
    assertThat(Street.Type.CIRCLE.getName()).isEqualTo("Circle");
    assertThat(Street.Type.COURT.getName()).isEqualTo("Court");
    assertThat(Street.Type.DRIVE.getName()).isEqualTo("Drive");
    assertThat(Street.Type.HIGHWAY.getName()).isEqualTo("Highway");
    assertThat(Street.Type.JUNCTION.getName()).isEqualTo("Junction");
    assertThat(Street.Type.LANE.getName()).isEqualTo("Lane");
    assertThat(Street.Type.LOOP.getName()).isEqualTo("Loop");
    assertThat(Street.Type.PLAZA.getName()).isEqualTo("Plaza");
    assertThat(Street.Type.ROAD.getName()).isEqualTo("Road");
    assertThat(Street.Type.ROUTE.getName()).isEqualTo("Route");
    assertThat(Street.Type.STREET.getName()).isEqualTo("Street");
    assertThat(Street.Type.WAY.getName()).isEqualTo("Way");
  }

  @Test
  public void valueOfAbbreviationIsCorrect() {

    Arrays.stream(Street.Type.values()).forEach(streetType ->
      assertThat(Street.Type.valueOfAbbreviation(streetType.getAbbreviation())).isEqualTo(streetType));
  }

  @Test
  public void valueOfNameIsCorrect() {

    Arrays.stream(Street.Type.values()).forEach(streetType ->
      assertThat(Street.Type.valueOfName(streetType.getName())).isEqualTo(streetType));
  }

  @Test
  public void streetTypeToStringIsSameAsName() {

    Arrays.stream(Street.Type.values()).forEach(streetType ->
      assertThat(streetType.toString()).isEqualTo(streetType.getName()));
  }
}
