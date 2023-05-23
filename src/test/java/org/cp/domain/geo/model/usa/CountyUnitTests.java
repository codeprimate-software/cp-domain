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
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import org.cp.domain.geo.enums.State;

/**
 * Unit Tests for {@link County}.
 *
 * @author John Blum
 * @see org.junit.jupiter.api.Test
 * @see org.mockito.Mockito
 * @see org.cp.domain.geo.model.usa.County
 * @since 0.1.0
 */
public class CountyUnitTests {

  @Test
  public void constructNewCounty() {

    County county = new County("TestCounty");

    assertThat(county).isNotNull();
    assertThat(county.getName()).isEqualTo("TestCounty");
    assertThat(county.getState()).isNotPresent();
  }

  @Test
  public void constructNewCountyWithIllegalName() {

    Arrays.asList("  ", "", null).forEach(name ->
      assertThatIllegalArgumentException()
        .isThrownBy(() -> new County(name))
        .withMessage("Name [%s] is required", name)
        .withNoCause());
  }

  @Test
  public void fromCounty() {

    County mockCounty = mock(County.class);

    doReturn("MockCounty").when(mockCounty).getName();

    County county = County.from(mockCounty);

    assertThat(county).isNotNull();
    assertThat(county.getName()).isEqualTo("MockCounty");
    assertThat(county.getState()).isNotPresent();

    verify(mockCounty, times(1)).getName();
    verifyNoMoreInteractions(mockCounty);
  }

  @Test
  public void fromNull() {

    assertThatIllegalArgumentException()
      .isThrownBy(() -> County.from(null))
      .withMessage("County to copy is required")
      .withNoCause();
  }

  @Test
  public void ofName() {

    County county = County.of("TestCounty");

    assertThat(county).isNotNull();
    assertThat(county.getName()).isEqualTo("TestCounty");
    assertThat(county.getState()).isNotPresent();
  }

  @Test
  public void getAndInState() {

    County county = County.of("TestCounty");

    assertThat(county).isNotNull();
    assertThat(county.getName()).isEqualTo("TestCounty");
    assertThat(county.getState()).isNotPresent();
    assertThat(county.in(State.OREGON)).isSameAs(county);
    assertThat(county.getName()).isEqualTo("TestCounty");
    assertThat(county.getState().orElse(null)).isEqualTo(State.OREGON);
    assertThat(county.in(State.IOWA)).isSameAs(county);
    assertThat(county.getName()).isEqualTo("TestCounty");
    assertThat(county.getState().orElse(null)).isEqualTo(State.IOWA);
    assertThat(county.in(null)).isSameAs(county);
    assertThat(county.getName()).isEqualTo("TestCounty");
    assertThat(county.getState()).isNotPresent();
  }

  @Test
  public void cloneMakesCopy() throws CloneNotSupportedException {

    County multnomah = County.of("Multnomah").in(State.OREGON);
    County clone = (County) multnomah.clone();

    assertThat(clone).isNotSameAs(multnomah);
    assertThat(clone).isEqualTo(multnomah);
  }

  @Test
  public void compareToIsCorrect() {

    County grantWisconsin = County.of("Grant").in(State.WISCONSIN);
    County multnomahOregon = County.of("Multnomah").in(State.OREGON);
    County washingtonOregon = County.of("Washington").in(State.OREGON);

    assertThat(multnomahOregon).isLessThan(grantWisconsin);
    assertThat(multnomahOregon).isLessThan(washingtonOregon);
    assertThat(multnomahOregon).isEqualByComparingTo(multnomahOregon);
    assertThat(grantWisconsin).isGreaterThan(washingtonOregon);
  }

  @Test
  public void equalsEqualCounty() {

    County multnomahOne = County.of("Multnomah").in(State.OREGON);
    County multnomahTwo = County.of("Multnomah").in(State.OREGON);

    assertThat(multnomahOne).isNotNull();
    assertThat(multnomahOne).isNotSameAs(multnomahTwo);
    assertThat(multnomahOne).isEqualTo(multnomahTwo);
    assertThat(multnomahOne.equals(multnomahTwo)).isTrue();
  }

  @Test
  @SuppressWarnings("all")
  public void equalsSameCounty() {

    County multnomah = County.of("Multnomah").in(State.OREGON);

    assertThat(multnomah).isNotNull();
    assertThat(multnomah).isEqualTo(multnomah);
    assertThat(multnomah.equals(multnomah)).isTrue();
  }

  @Test
  public void equalsUnequalCounty() {

    County multnomah = County.of("Multnomah");
    County multnomahOregon = County.of("Multnomah").in(State.OREGON);
    County multnomahMaine = County.of("Multnomah").in(State.MAINE);
    County washingtonOregon = County.of("Washington").in(State.OREGON);
    County vancouverWashington = County.of("Vancouver").in(State.WASHINGTON);

    assertThat(multnomahOregon).isNotNull();
    assertThat(multnomahOregon).isNotEqualTo(multnomah);
    assertThat(multnomahOregon).isNotEqualTo(multnomahMaine);
    assertThat(multnomahOregon).isNotEqualTo(washingtonOregon);
    assertThat(multnomahOregon).isNotEqualTo(vancouverWashington);
  }

  @Test
  public void equalsNullIsNullSafe() {
    assertThat(County.of("Multnomah").in(State.OREGON)).isNotEqualTo(null);
  }

  @Test
  public void equalsObjectReturnsFalse() {
    assertThat(County.of("Multnomah")).isNotEqualTo("Multnomah");
  }

  @Test
  public void hashCodeIsCorrect() {

    County multnomah = County.of("Multnomah");
    County multnomahOregonOne = County.of("Multnomah").in(State.OREGON);
    County multnomahOregonTwo = County.of("Multnomah").in(State.OREGON);
    County multnomahMaine = County.of("Multnomah").in(State.MAINE);
    County washingtonOregon = County.of("Washington").in(State.OREGON);
    County vancouverWashington = County.of("Vancouver").in(State.WASHINGTON);

    assertThat(multnomahOregonOne).isNotNull();
    assertThat(multnomahOregonOne).hasSameHashCodeAs(multnomahOregonTwo);
    assertThat(multnomahOregonOne.hashCode()).isEqualTo(multnomahOregonOne.hashCode());
    assertThat(multnomahOregonOne).doesNotHaveSameHashCodeAs(multnomah);
    assertThat(multnomahOregonOne).doesNotHaveSameHashCodeAs(multnomahMaine);
    assertThat(multnomahOregonOne).doesNotHaveSameHashCodeAs(washingtonOregon);
    assertThat(multnomahOregonOne).doesNotHaveSameHashCodeAs(vancouverWashington);
  }

  @Test
  public void toStringCallsGetName() {

    County county = spy(County.of("TEST").in(State.OREGON));

    assertThat(county.toString()).isEqualTo("TEST");

    verify(county, times(1)).getName();
    verifyNoMoreInteractions(county);
  }
}
