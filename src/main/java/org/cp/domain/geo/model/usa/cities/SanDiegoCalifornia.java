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
package org.cp.domain.geo.model.usa.cities;

import org.cp.domain.geo.enums.Country;
import org.cp.domain.geo.enums.State;
import org.cp.domain.geo.model.usa.ImmutableUnitedStatesCity;
import org.cp.elements.lang.annotation.Immutable;

/**
 * {@link ImmutableUnitedStatesCity} modeling the city of {@literal San Diego} {@link State#CALIFORNIA},
 * {@link Country#UNITED_STATES_OF_AMERICA USA}.
 *
 * @author John Blum
 * @see org.cp.domain.geo.enums.Country
 * @see org.cp.domain.geo.enums.State
 * @see org.cp.domain.geo.model.usa.ImmutableUnitedStatesCity
 * @see org.cp.elements.lang.annotation.Immutable
 * @since 0.1.0
 */
@Immutable
public final class SanDiegoCalifornia extends ImmutableUnitedStatesCity {

  public static final SanDiegoCalifornia INSTANCE = new SanDiegoCalifornia();

  private SanDiegoCalifornia() {
    super("San Diego");
  }

  @Override
  public State getState() {
    return State.CALIFORNIA;
  }
}
