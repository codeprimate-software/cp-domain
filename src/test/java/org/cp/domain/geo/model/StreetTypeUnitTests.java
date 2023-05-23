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

import org.junit.jupiter.api.Test;

import org.cp.elements.lang.StringUtils;

/**
 * Unit Tests for {@link Street.Type}.
 *
 * @author John Blum
 * @see org.junit.jupiter.api.Test
 * @see org.cp.domain.geo.model.Street.Type
 * @since 0.1.0
 */
public class StreetTypeUnitTests {

  @Test
  public void streetTypeAbbreviations() {

    assertThat(Street.Type.ALLEY.getAbbreviation()).isEqualTo("ALLY");
    assertThat(Street.Type.AVENUE.getAbbreviation()).isEqualTo("AVE");
    assertThat(Street.Type.BEND.getAbbreviation()).isEqualTo("BND");
    assertThat(Street.Type.BOULEVARD.getAbbreviation()).isEqualTo("BLVD");
    assertThat(Street.Type.BYPASS.getAbbreviation()).isEqualTo("BYP");
    assertThat(Street.Type.CAUSEWAY.getAbbreviation()).isEqualTo("CSWY");
    assertThat(Street.Type.CENTER.getAbbreviation()).isEqualTo("CTR");
    assertThat(Street.Type.CIRCLE.getAbbreviation()).isEqualTo("CRCL");
    assertThat(Street.Type.CORNER.getAbbreviation()).isEqualTo("CNR");
    assertThat(Street.Type.COURT.getAbbreviation()).isEqualTo("CT");
    assertThat(Street.Type.CROSSING.getAbbreviation()).isEqualTo("XING");
    assertThat(Street.Type.CROSSROAD.getAbbreviation()).isEqualTo("XRD");
    assertThat(Street.Type.CURVE.getAbbreviation()).isEqualTo("CURV");
    assertThat(Street.Type.DRIVE.getAbbreviation()).isEqualTo("DR");
    assertThat(Street.Type.EXPRESSWAY.getAbbreviation()).isEqualTo("EXP");
    assertThat(Street.Type.FERRY.getAbbreviation()).isEqualTo("FRY");
    assertThat(Street.Type.FORK.getAbbreviation()).isEqualTo("FRK");
    assertThat(Street.Type.FREEWAY.getAbbreviation()).isEqualTo("FWY");
    assertThat(Street.Type.GATEWAY.getAbbreviation()).isEqualTo("GTWY");
    assertThat(Street.Type.HIGHWAY.getAbbreviation()).isEqualTo("HWY");
    assertThat(Street.Type.JUNCTION.getAbbreviation()).isEqualTo("JCT");
    assertThat(Street.Type.LANE.getAbbreviation()).isEqualTo("LN");
    assertThat(Street.Type.LOOP.getAbbreviation()).isEqualTo("LP");
    assertThat(Street.Type.MOTORWAY.getAbbreviation()).isEqualTo("MTWY");
    assertThat(Street.Type.OVERPASS.getAbbreviation()).isEqualTo("OPAS");
    assertThat(Street.Type.PARKWAY.getAbbreviation()).isEqualTo("PKWY");
    assertThat(Street.Type.PLACE.getAbbreviation()).isEqualTo("PL");
    assertThat(Street.Type.PLAZA.getAbbreviation()).isEqualTo("PLZ");
    assertThat(Street.Type.ROAD.getAbbreviation()).isEqualTo("RD");
    assertThat(Street.Type.ROUTE.getAbbreviation()).isEqualTo("RTE");
    assertThat(Street.Type.SKYWAY.getAbbreviation()).isEqualTo("SKWY");
    assertThat(Street.Type.SQUARE.getAbbreviation()).isEqualTo("SQR");
    assertThat(Street.Type.STREET.getAbbreviation()).isEqualTo("ST");
    assertThat(Street.Type.TURNPIKE.getAbbreviation()).isEqualTo("TPKE");
    assertThat(Street.Type.UNDERPASS.getAbbreviation()).isEqualTo("UPAS");
    assertThat(Street.Type.UNKNOWN.getAbbreviation()).isEqualTo("UKN");
    assertThat(Street.Type.VIADUCT.getAbbreviation()).isEqualTo("VIA");
    assertThat(Street.Type.WAY.getAbbreviation()).isEqualTo("WY");
  }

  @Test
  public void streetTypeDescription() {

    Arrays.stream(Street.Type.values()).forEach(streetType ->
      assertThat(streetType.getDescription()).isEqualTo(StringUtils.capitalize(streetType.name().toLowerCase())));
  }

  @Test
  public void fromAbbreviation() {

    Arrays.stream(Street.Type.values()).forEach(streetType ->
      assertThat(Street.Type.fromAbbreviation(streetType.getAbbreviation())).isEqualTo(streetType));
  }

  @Test
  public void fromDescription() {

    Arrays.stream(Street.Type.values()).forEach(streetType -> {
      assertThat(Street.Type.fromDescription(streetType.getDescription())).isEqualTo(streetType);
      assertThat(Street.Type.fromDescription(streetType.name())).isEqualTo(streetType);
    });
  }

  @Test
  public void streetTypeToStringIsSameAsDescription() {

    Arrays.stream(Street.Type.values()).forEach(streetType ->
      assertThat(streetType.toString()).isEqualTo(streetType.getDescription()));
  }
}
