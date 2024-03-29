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
package org.cp.domain.geo.enums;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import org.cp.elements.lang.StringUtils;

/**
 * {@link Enum Enumeration} of all 7 continents in the world.
 *
 * @author John Blum
 * @see org.cp.domain.geo.enums.Country
 * @see <a href="https://www.worldometers.info/geography/7-continents/">7 contintents</a>
 * @see <a href="https://www.countries-ofthe-world.com/continents-of-the-world.html">COUNTRIES-ofthe-WORLD.COM</a>
 * @since 0.1.0
 */
@SuppressWarnings("unused")
public enum Continent {

  AFRICA,
  ANTARCTICA,
  ASIA,
  AUSTRALIA_AND_OCEANIA,
  EUROPE,
  NORTH_AMERICA,
  SOUTH_AMERICA,
  UNKNOWN;

  /**
   * Returns all {@link Country Countries} located on this {@link Continent}.
   *
   * @return a {@link Set} of {@link Country Countries} located on this {@link Continent}.
   * @see org.cp.domain.geo.enums.Country
   * @see java.util.Set
   */
  public Set<Country> countries() {

    return Arrays.stream(Country.values())
      .filter(country -> country.isLocatedOnContinent(this))
      .collect(Collectors.toSet());
  }

  /**
   * Returns a {@link String} representation of this {@link Continent}.
   *
   * @return a {@link String} describing this {@link Continent}
   * @see java.lang.Object#toString()
   * @see #name()
   */
  @Override
  public String toString() {

    return Arrays.stream(name().split("_"))
      .map(String::toLowerCase)
      .map(StringUtils::capitalize)
      .map(part -> "and".equalsIgnoreCase(part) ? "&" : part)
      .reduce((partOne, partTwo) -> partOne.concat(StringUtils.SINGLE_SPACE).concat(partTwo))
      .orElse(name());
  }
}
