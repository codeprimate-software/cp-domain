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

import static org.cp.elements.lang.RuntimeExceptionsFactory.newUnsupportedOperationException;

import org.cp.domain.geo.enums.State;
import org.cp.elements.lang.annotation.Immutable;
import org.cp.elements.lang.annotation.NotNull;

/**
 * {@link Immutable} extension and implementation of {@link UnitedStatesCity}.
 *
 * @author John Blum
 * @see org.cp.domain.geo.model.usa.UnitedStatesCity
 * @see org.cp.elements.lang.annotation.Immutable
 * @since 0.1.0
 */
@Immutable
public abstract class ImmutableUnitedStatesCity extends UnitedStatesCity {

  protected ImmutableUnitedStatesCity(@NotNull String name) {
    super(name);
  }

  @Override
  public final void setState(State state) {
    throw newUnsupportedOperationException("State cannot be changed");
  }
}
