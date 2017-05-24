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

package org.cp.domain.geo.enums;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * The {@link Continent} enum is a enumerated type of all 7 Continents in the World.
 *
 * @author John Blum
 * @see <a href="https://www.countries-ofthe-world.com/continents-of-the-world.html">COUNTRIES-ofthe-WORLD.COM</a>
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public enum Continent {

  AFRICA,
  ANTARCTICA,
  AUSTRALIA_AND_OCEANIA,
  ASIA,
  EUROPE,
  NORTH_AMERICA,
  SOUTH_AMERICA;

  public Set<Country> countries() {
    return Arrays.stream(Country.values()).filter(country -> country.isOnContinent(this))
      .collect(Collectors.toSet());
  }
}
