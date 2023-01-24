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
package org.cp.domain.geo.model.support.units;

import static org.cp.elements.lang.RuntimeExceptionsFactory.newUnsupportedOperationException;

import java.util.Optional;

import org.cp.domain.geo.model.Unit;
import org.cp.elements.lang.Assert;
import org.cp.elements.lang.annotation.NotNull;

/**
 * {@link Unit} implementation for {@link Unit.Type#OFFICE}.
 *
 * @author John Blum
 * @see org.cp.domain.geo.model.Unit
 * @see org.cp.domain.geo.model.Unit.Type#OFFICE
 * @since 0.1.0
 */
public class Office extends Unit {

  /**
   * Factory method used to construct a new instance of {@link Office} copied from the given, required {@link Unit}.
   *
   * @param unit {@link Unit} to copy; must not be {@literal null}.
   * @return a new {@link Office} copied from the existing {@link Unit}.
   * @throws IllegalArgumentException if the given {@link Unit} is {@literal null}.
   * @see org.cp.domain.geo.model.Unit
   * @see #number(String)
   */
  public static @NotNull Office from(@NotNull Unit unit) {

    Assert.notNull(unit, "Unit to copy is required");

    return unit instanceof Office ? (Office) unit
      : number(unit.getNumber());
  }

  /**
   * Factory method used to construct a new instance of {@link Office} initialized with the given,
   * required {@link String number} used to identify the {@link Unit}.
   *
   * @param number {@link String} containing the {@literal number} identifying the {@link Unit};
   * must not be {@literal null} or {@literal empty}.
   * @return a new {@link Office} with the given, required {@link String number}.
   * @throws IllegalArgumentException if the given {@link String number} is {@literal null} or {@literal empty}.
   */
  public static @NotNull Office number(@NotNull String number) {
    return new Office(number);
  }

  /**
   * Constructs a new instance of {@link Office} initialized with the given, required {@link String number}
   * used to identify this {@link Unit}.
   *
   * @param number {@link String} containing the {@literal number} identifying this {@link Unit};
   * must not be {@literal null} or {@literal empty}.
   * @throws IllegalArgumentException if the given {@link String number} is {@literal null} or {@literal empty}.
   */
  protected Office(@NotNull String number) {
    super(number);
  }

  /**
   * Returns {@link Unit.Type#OFFICE}.
   *
   * @return {@link Unit.Type#OFFICE}.
   * @see org.cp.domain.geo.model.Unit.Type#OFFICE
   * @see java.util.Optional
   */
  @Override
  public final Optional<Unit.Type> getType() {
    return Optional.of(Type.OFFICE);
  }

  @Override
  public final Unit as(Unit.Type type) {
    throw newUnsupportedOperationException("Type cannot be changed");
  }
}
