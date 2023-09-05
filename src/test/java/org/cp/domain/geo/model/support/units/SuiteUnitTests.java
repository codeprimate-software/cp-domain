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
package org.cp.domain.geo.model.support.units;

import java.util.function.Function;

import org.cp.domain.geo.model.Unit;
import org.cp.domain.geo.model.Unit.Type;

/**
 * Unit Tests for {@link Suite}.
 *
 * @author John Blum
 * @see org.junit.jupiter.api.Test
 * @see org.cp.domain.geo.model.Unit
 * @see org.cp.domain.geo.model.Unit.Type#SUITE
 * @see org.cp.domain.geo.model.support.units.Suite
 * @see org.cp.domain.geo.model.support.units.AbstractConcreteUnitUnitTests
 * @since 0.1.0
 */
public class SuiteUnitTests extends AbstractConcreteUnitUnitTests<Suite> {

  @Override
  protected Function<Unit, Suite> fromUnitFactoryMethod() {
    return Suite::from;
  }

  @Override
  protected Function<String, Suite> numberFactoryMethod() {
    return Suite::number;
  }

  @Override
  protected Function<String, Suite> unitConstructor() {
    return Suite::new;
  }

  @Override
  protected Type getExpectedUnitType() {
    return Unit.Type.SUITE;
  }
}
