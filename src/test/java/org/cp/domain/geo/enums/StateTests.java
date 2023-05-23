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

package org.cp.domain.geo.enums;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNull;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link State}.
 *
 * @author John J. Blum
 * @see org.junit.jupiter.api.Test
 * @see org.cp.domain.geo.enums.State
 * @since 1.0.0
 */
public class StateTests {

  @Test
  public void valueOfReturnsState() {

    Arrays.stream(State.values()).forEach(state ->
      assertThat(State.valueOf(state.name())).isEqualTo(state));

  }

  @Test
  public void valueOfAbbreviationsReturnsState() {

    Arrays.stream(State.values()).forEach(state ->
      assertThat(State.valueOfAbbreviation(state.getAbbreviation())).isEqualTo(state));
  }

  @Test
  public void valueOfInvalidAbbreviationsReturnsNull() {

    assertThat(State.valueOfAbbreviation("Cali")).isNull();
    assertThat(State.valueOfAbbreviation("Oregon")).isNull();
    assertThat(State.valueOfAbbreviation("Wash")).isNull();
  }

  @Test
  public void valueOfMixedCaseAbbreviationsReturnsState() {

    assertThat(State.valueOfAbbreviation("WI")).isEqualTo(State.WISCONSIN);
    assertThat(State.valueOfAbbreviation("Ia")).isEqualTo(State.IOWA);
    assertThat(State.valueOfAbbreviation("mT")).isEqualTo(State.MONTANA);
    assertThat(State.valueOfAbbreviation("or")).isEqualTo(State.OREGON);
  }

  @Test
  public void valueOfNullAbbreviationReturnsNull() {
    assertThat(State.valueOfAbbreviation(null)).isNull();
  }

  @Test
  public void valueOfNameReturnsState() {

    Arrays.stream(State.values()).forEach(state ->
      assertThat(State.valueOfName(state.getName())).isEqualTo(state));
  }

  @Test
  public void valueOfNullNameReturnsNull() {
    assertThat(State.valueOfName(null)).isNull();
  }

  @Test
  public void valueOfMixedCaseNamesReturnsState() {

    assertThat(State.valueOfName("wisconsin")).isEqualTo(State.WISCONSIN);
    assertThat(State.valueOfName("Iowa")).isEqualTo(State.IOWA);
    assertThat(State.valueOfName("MONTANA")).isEqualTo(State.MONTANA);
    assertThat(State.valueOfName("ORegon")).isEqualTo(State.OREGON);
  }

  @Test
  public void valueOfInvalidNamesReturnsNull() {

    assertNull(State.valueOfName("OR"));
    assertNull(State.valueOfName("Oregano"));
    assertNull(State.valueOfName("Wash"));
  }

  @Test
  public void toStringReturnsName() {

    Arrays.stream(State.values()).forEach(state ->
      assertThat(state.toString()).isEqualTo(state.getName()));
  }
}
