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
package org.cp.domain.geo.model.usa.cities;

import org.cp.domain.geo.enums.Country;
import org.cp.domain.geo.enums.State;
import org.cp.domain.geo.model.usa.ImmutableUnitedStatesCity;
import org.cp.elements.lang.annotation.Immutable;

/**
 * {@link ImmutableUnitedStatesCity} modeling the city of {@literal Jackson} {@link State#MISSISSIPPI},
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
public final class JacksonMississippi extends ImmutableUnitedStatesCity {

  public static final JacksonMississippi INSTANCE = new JacksonMississippi();

  private JacksonMississippi() {
    super("Jackson");
  }

  @Override
  public boolean isCapital() {
    return true;
  }

  @Override
  public State getState() {
    return State.MISSISSIPPI;
  }
}
