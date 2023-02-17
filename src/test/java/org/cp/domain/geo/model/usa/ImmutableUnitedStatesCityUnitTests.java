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
package org.cp.domain.geo.model.usa;

import static org.assertj.core.api.Assertions.assertThat;
import static org.cp.elements.lang.ThrowableAssertions.assertThatUnsupportedOperationException;

import org.junit.Test;

import org.cp.domain.geo.enums.Country;
import org.cp.domain.geo.enums.State;
import org.cp.elements.lang.ThrowableOperation;
import org.cp.elements.lang.annotation.NotNull;

/**
 * Unit Tests for {@link ImmutableUnitedStatesCity}.
 *
 * @author John Blum
 * @see org.junit.Test
 * @see org.cp.domain.geo.model.usa.ImmutableUnitedStatesCity
 * @since 0.1.0
 */
public class ImmutableUnitedStatesCityUnitTests {

  @Test
  public void constructImmutableUnitedStatesCity() {

    ImmutableUnitedStatesCity city = new TestImmutableUnitedStatesCity("TestCity");

    assertThat(city).isNotNull();
    assertThat(city.getCountry().orElse(null)).isEqualTo(Country.UNITED_STATES_OF_AMERICA);
    assertThat(city.getName()).isEqualTo("TestCity");
    assertThat(city.getState()).isNull();
  }

  @Test
  public void setStateThrowsException() {

    assertThatUnsupportedOperationException()
      .isThrownBy(ThrowableOperation.fromConsumer(args -> new TestImmutableUnitedStatesCity("TestCity").setState(State.OREGON)))
      .havingMessage("State cannot be changed")
      .withNoCause();
  }

  private static final class TestImmutableUnitedStatesCity extends ImmutableUnitedStatesCity {

    private TestImmutableUnitedStatesCity(@NotNull String name) {
      super(name);
    }
  }
}
