/*
 * Copyright 2017-Present Author or Authors.
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
package org.cp.domain.contact.phone.model.usa.support;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Test;

import org.cp.domain.contact.phone.model.AreaCode;
import org.cp.domain.geo.enums.State;

/**
 * Unit Tests for {@link StateAreaCodesRepository}.
 *
 * @author John Blum
 * @see org.junit.jupiter.api.Test
 * @see org.cp.domain.contact.phone.model.usa.support.StateAreaCodesRepository
 * @since 0.1.0
 */
public class StateAreaCodesRepositoryUnitTests {

  @Test
  public void findAreaCodesByState() {

    StateAreaCodesRepository repository = StateAreaCodesRepository.getInstance();

    for (State state : State.values()) {
      Set<AreaCode> stateAreaCodes = repository.findAreaCodesBy(state);
      assertThat(stateAreaCodes).isNotNull();
      assertThat(stateAreaCodes).containsAll(repository.getStateAreaCodeMapping().get(state));
    }
  }

  @Test
  public void findAreaCodesByNullState() {

    assertThatIllegalArgumentException()
      .isThrownBy(() -> StateAreaCodesRepository.getInstance().findAreaCodesBy(null))
      .withMessage("State is required")
      .withNoCause();
  }

  @Test
  public void findStateByAreaCodeIsCorrect() {

    StateAreaCodesRepository repository = StateAreaCodesRepository.getInstance();

    for (Map.Entry<State, Set<AreaCode>> mapEntry : repository) {
      for (AreaCode areaCode : mapEntry.getValue()) {
        assertThat(repository.findStateBy(areaCode)).isEqualTo(mapEntry.getKey());
      }
    }
  }

  @Test
  public void findStateByForNonExistingAreaCode() {

    assertThatIllegalArgumentException()
      .isThrownBy(() -> StateAreaCodesRepository.getInstance().findStateBy(AreaCode.of("010")))
      .withMessage("No State for AreaCode [010] could be found")
      .withNoCause();
  }

  @Test
  public void noDuplicateAreaCodesExist() {

    AtomicInteger count = new AtomicInteger(0);

    Set<AreaCode> allAreaCodes = new HashSet<>();

    for (Map.Entry<State, Set<AreaCode>> mapEntry: StateAreaCodesRepository.getInstance()) {
      Set<AreaCode> areaCodes = mapEntry.getValue();
      count.addAndGet(areaCodes.size());
      assertThat(allAreaCodes.addAll(areaCodes)).isTrue();
    }

    assertThat(allAreaCodes).hasSize(count.get());
  }
}
