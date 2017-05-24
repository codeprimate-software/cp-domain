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

/**
 * The Suite class...
 *
 * @author John Blum
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public class Suite extends Unit {

  public static Suite of(String number) {
    return new Suite(number);
  }

  public static Suite from(Unit unit) {

    Assert.notNull(unit, "Unit must not be null");

    return of(unit.getNumber());
  }

  public Suite(String number) {
    super(number);
  }

  @Override
  public final Optional<Type> getType() {
    return Optional.of(Type.SUITE);
  }

  @Override
  public Unit as(Unit.Type type) {
    throw newUnsupportedOperationException("Type cannot be changed");
  }
}
