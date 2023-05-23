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
package org.cp.domain.geo.model.support.units;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;

import org.junit.jupiter.api.Test;

import org.cp.domain.geo.model.Unit;

/**
 * Abstract base class used to test the concrete {@link Unit} types.
 *
 * @author John Blum
 * @see org.junit.jupiter.api.Test
 * @see org.mockito.Mockito
 * @see java.util.function.Function
 * @see org.cp.domain.geo.model.Unit
 * @since 0.1.0
 */
public abstract class AbstractConcreteUnitUnitTests<T extends Unit> {

  protected abstract Function<Unit, T> fromUnitFactoryMethod();

  protected abstract Function<String, T> numberFactoryMethod();

  protected abstract Function<String, T> unitConstructor();

  protected abstract Unit.Type getExpectedUnitType();

  protected Unit mockUnit(String number) {

    Unit mockUnit = mock(Unit.class);

    doReturn(number).when(mockUnit).getNumber();
    doReturn(Optional.of(Unit.Type.UNKNOWN)).when(mockUnit).getType();

    return mockUnit;
  }

  @Test
  public void constructUnit() {

    T unit = unitConstructor().apply("10A");

    assertThat(unit).isNotNull();
    assertThat(unit.getNumber()).isEqualTo("10A");
    assertThat(unit.getType().orElse(null)).isEqualTo(getExpectedUnitType());
  }

  @Test
  public void constructUnitWithInvalidNumber() {

    Arrays.asList(null, "", "  ").forEach(number ->
      assertThatIllegalArgumentException()
        .isThrownBy(() -> unitConstructor().apply(number))
        .withMessage("Number [%s] is required", number)
        .withNoCause());
  }

  @Test
  public void fromDifferentUnit() {

    Unit mockUnit = mockUnit("1");

    T unit = fromUnitFactoryMethod().apply(mockUnit);

    assertThat(unit).isNotNull();
    assertThat(unit).isNotSameAs(mockUnit);
    assertThat(unit.getNumber()).isEqualTo("1");
    assertThat(unit.getType().orElse(Unit.Type.UNKNOWN)).isEqualTo(getExpectedUnitType());

    verify(mockUnit, times(1)).getNumber();
    verifyNoMoreInteractions(mockUnit);
  }

  @Test
  public void fromNullUnit() {

    assertThatIllegalArgumentException()
      .isThrownBy(() -> fromUnitFactoryMethod().apply(null))
      .withMessage("Unit to copy is required")
      .withNoCause();
  }

  @Test
  public void fromSameUnit() {

    T unit = unitConstructor().apply("1");
    T copy = fromUnitFactoryMethod().apply(unit);

    assertThat(copy).isSameAs(unit);
  }

  @Test
  public void ofNumber() {

    T unit = numberFactoryMethod().apply("A");

    assertThat(unit).isNotNull();
    assertThat(unit.getNumber()).isEqualTo("A");
    assertThat(unit.getType().orElse(null)).isEqualTo(getExpectedUnitType());
  }
}
