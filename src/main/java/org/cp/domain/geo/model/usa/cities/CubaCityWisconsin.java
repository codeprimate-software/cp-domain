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

import static org.cp.elements.lang.RuntimeExceptionsFactory.newUnsupportedOperationException;

import org.cp.domain.geo.model.usa.UnitedStatesCity;
import org.cp.elements.enums.State;

/**
 * The CubaCityWisconsin class...
 *
 * @author John Blum
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public final class CubaCityWisconsin extends UnitedStatesCity {

  public static final CubaCityWisconsin INSTANCE = new CubaCityWisconsin();

  private CubaCityWisconsin() {
    super("Cuba City");
  }

  @Override
  public final State getState() {
    return State.WISCONSIN;
  }

  @Override
  public final void setState(State state) {
    throw newUnsupportedOperationException("State cannot be changed");
  }
}
