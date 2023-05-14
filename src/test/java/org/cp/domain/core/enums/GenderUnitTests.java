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

import org.junit.Test;

import org.cp.elements.enums.Gender;

/**
 * Unit Tests for {@link Gender}.
 *
 * @author John J. Blum
 * @see org.junit.Test
 * @see org.cp.domain.core.enums.Gender
 * @since 0.1.0
 */
public class GenderUnitTests {

  @Test
  public void valueOfReturnsGender() {

    Arrays.stream(org.cp.elements.enums.Gender.values()).forEach(gender ->
      assertThat(org.cp.elements.enums.Gender.valueOf(gender.name())).isEqualTo(gender));
  }

  @Test
  public void valueOfAbbreviationReturnsGender() {

    Arrays.stream(org.cp.elements.enums.Gender.values()).forEach(gender ->
      assertThat(org.cp.elements.enums.Gender.valueOfAbbreviation(gender.getAbbreviation())).isEqualTo(gender));
  }

  @Test
  public void valueOfAbbreviationUsingNameReturnsNull() {

    assertThat(org.cp.elements.enums.Gender.valueOfAbbreviation("Female")).isNull();
    assertThat(org.cp.elements.enums.Gender.valueOfAbbreviation("Girl")).isNull();
    assertThat(org.cp.elements.enums.Gender.valueOfAbbreviation("Woman")).isNull();
    assertThat(org.cp.elements.enums.Gender.valueOfAbbreviation("Women")).isNull();
  }

  @Test
  public void valueOfInvalidAbbreviationReturnsNull() {

    assertThat(org.cp.elements.enums.Gender.valueOfAbbreviation("B")).isNull(); // Boy
    assertThat(org.cp.elements.enums.Gender.valueOfAbbreviation("G")).isNull(); // Guy / Girl
    assertThat(org.cp.elements.enums.Gender.valueOfAbbreviation("W")).isNull(); // Woman
  }

  @Test
  public void valueOfNullAbbreviationIsNullSafeAndReturnsNull() {
    assertThat(org.cp.elements.enums.Gender.valueOfAbbreviation(null)).isNull();
  }

  @Test
  public void valueOfNameReturnsGender() {

    Arrays.stream(org.cp.elements.enums.Gender.values()).forEach(gender ->
      assertThat(org.cp.elements.enums.Gender.valueOfName(gender.getName())).isEqualTo(gender));
  }

  @Test
  public void valueOfNameUsingAbbreviationReturnsNull() {

    assertThat(org.cp.elements.enums.Gender.valueOfName("B")).isNull();
    assertThat(org.cp.elements.enums.Gender.valueOfName("F")).isNull();
    assertThat(org.cp.elements.enums.Gender.valueOfName("G")).isNull();
    assertThat(org.cp.elements.enums.Gender.valueOfName("M")).isNull();
    assertThat(org.cp.elements.enums.Gender.valueOfName("W")).isNull();
  }

  @Test
  public void valueOfInvalidNameReturnsNull() {

    assertThat(org.cp.elements.enums.Gender.valueOfName("Boy")).isNull();
    assertThat(org.cp.elements.enums.Gender.valueOfName("Girl")).isNull();
    assertThat(org.cp.elements.enums.Gender.valueOfName("Man")).isNull();
    assertThat(org.cp.elements.enums.Gender.valueOfName("Men")).isNull();
    assertThat(org.cp.elements.enums.Gender.valueOfName("Woman")).isNull();
    assertThat(org.cp.elements.enums.Gender.valueOfName("Women")).isNull();
  }

  @Test
  public void valueOfNullNameIsNullSafeReturnsNull() {
    assertThat(Gender.valueOfName(null)).isNull();
  }
}
