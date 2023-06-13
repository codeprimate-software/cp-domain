/*
 * Copyright 2011-Present Author or Authors.
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
package org.cp.domain.core.enums;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import org.cp.elements.enums.Race;

/**
 * Unit Tests for {@link Race}.
 *
 * @author John J. Blum
 * @see org.junit.jupiter.api.Test
 * @see org.cp.domain.core.enums.Race
 * @since 0.1.0
 */
public class RaceUnitTests {

  @Test
  public void valueOfReturnsRace() {

    Arrays.stream(org.cp.elements.enums.Race.values()).forEach(race ->
      assertThat(org.cp.elements.enums.Race.valueOf(race.name())).isEqualTo(race));
  }

  @Test
  public void valueOfAbbreviationIsCorrect() {

    Arrays.stream(org.cp.elements.enums.Race.values()).forEach(race ->
      assertThat(org.cp.elements.enums.Race.valueOfAbbreviation(race.getAbbreviation())).isEqualTo(race));
  }

  @Test
  public void valueOfAbbreviationIsLenient() {

    assertThat(org.cp.elements.enums.Race.valueOfAbbreviation("BLACK")).isEqualTo(org.cp.elements.enums.Race.AFRICAN_AMERICAN);
    assertThat(org.cp.elements.enums.Race.valueOfAbbreviation("Indian")).isEqualTo(org.cp.elements.enums.Race.AMERICAN_INDIAN);
    assertThat(org.cp.elements.enums.Race.valueOfAbbreviation("white")).isEqualTo(org.cp.elements.enums.Race.WHITE);
  }

  @Test
  public void valueOfAbbreviationIsNullSafeReturnsNull() {
    assertThat(org.cp.elements.enums.Race.valueOfAbbreviation(null)).isNull();
  }

  @Test
  public void valueOfAbbreviationUsingNameReturnsNull() {
    assertThat(org.cp.elements.enums.Race.valueOfAbbreviation(org.cp.elements.enums.Race.AFRICAN_AMERICAN.getName())).isNull();
  }

  @Test
  public void valueOfInvalidAbbreviationReturnsNull() {

    assertThat(org.cp.elements.enums.Race.valueOfAbbreviation("")).isNull();
    assertThat(org.cp.elements.enums.Race.valueOfAbbreviation("  ")).isNull();
    assertThat(org.cp.elements.enums.Race.valueOfAbbreviation("alien")).isNull();
    assertThat(org.cp.elements.enums.Race.valueOfAbbreviation("EURO")).isNull();
    assertThat(org.cp.elements.enums.Race.valueOfAbbreviation("heBrew")).isNull();
    assertThat(org.cp.elements.enums.Race.valueOfAbbreviation("muslim")).isNull();
    assertThat(org.cp.elements.enums.Race.valueOfAbbreviation("Spanish")).isNull();
  }

  @Test
  public void valueOfNameIsCorrect() {

    Arrays.stream(org.cp.elements.enums.Race.values()).forEach(race ->
      assertThat(org.cp.elements.enums.Race.valueOfName(race.getName())).isEqualTo(race));
  }

  @Test
  public void valueOfNameIsLenient() {

    assertThat(org.cp.elements.enums.Race.valueOfName("African American")).isEqualTo(org.cp.elements.enums.Race.AFRICAN_AMERICAN);
    assertThat(org.cp.elements.enums.Race.valueOfName("ASIAN")).isEqualTo(org.cp.elements.enums.Race.ASIAN);
    assertThat(org.cp.elements.enums.Race.valueOfName("white")).isEqualTo(org.cp.elements.enums.Race.WHITE);
  }

  @Test
  public void valueOfNameIsNullSafeReturnsNull() {
    assertThat(org.cp.elements.enums.Race.valueOfName(null)).isNull();
  }

  @Test
  public void valueOfNameUsingAbbreviationReturnsNull() {

    assertThat(org.cp.elements.enums.Race.valueOfName("Alaskan")).isNull();
    assertThat(org.cp.elements.enums.Race.valueOfName("Hawaiian")).isNull();
    assertThat(org.cp.elements.enums.Race.valueOfName("Indian")).isNull();
  }

  @Test
  public void valueOfInvalidNameReturnsNull() {

    assertThat(org.cp.elements.enums.Race.valueOfName("")).isNull();
    assertThat(org.cp.elements.enums.Race.valueOfName("  ")).isNull();
    assertThat(org.cp.elements.enums.Race.valueOfName("Eskimo")).isNull();
    assertThat(org.cp.elements.enums.Race.valueOfName("Negro")).isNull();
    assertThat(Race.valueOfName("Redman")).isNull();
  }
}
