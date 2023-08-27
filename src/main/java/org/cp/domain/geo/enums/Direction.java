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
package org.cp.domain.geo.enums;

import static org.cp.elements.lang.RuntimeExceptionsFactory.newIllegalArgumentException;

import java.util.Arrays;

import org.cp.domain.geo.model.Distance;
import org.cp.elements.lang.Nameable;
import org.cp.elements.lang.StringUtils;
import org.cp.elements.lang.annotation.NotNull;

/**
 * {@link Enum Enumeration} of {@literal directions}.
 *
 * @author John Blum
 * @see java.lang.Enum
 * @see org.cp.elements.lang.Nameable
 * @since 0.1.0
 */
@SuppressWarnings("unused")
public enum Direction implements Nameable<String> {

  NORTH("N"),
  NORTHEAST("NE"),
  NORTHWEST("NW"),
  SOUTH("S"),
  SOUTHEAST("SE"),
  SOUTHWEST("SW"),
  EAST("E"),
  WEST("W");

  /**
   * Factory method used to query a {@link Direction} by {@link String abbreviation}.
   *
   * @param abbreviation {@link String} containing the {@literal abbreviation} of the {@link Direction}.
   * @return a {@link Direction} matching the given {@link String abbreviation}.
   * @throws IllegalArgumentException if the {@link String abbreviation} does not match a {@link Direction}.
   * @see #getAbbreviation()
   */
  public static @NotNull Direction fromAbbreviation(@NotNull String abbreviation) {

    return Arrays.stream(values())
      .filter(direction -> direction.getAbbreviation().equalsIgnoreCase(abbreviation))
      .findFirst()
      .orElseThrow(() -> newIllegalArgumentException("Direction abbreviation [%s] is not valid", abbreviation));
  }

  /**
   * Factory method used to query a {@link Direction} by {@link String name}.
   *
   * @param name {@link String} containing a {@literal name} used to look up the {@link Direction}.
   * @return a {@link Direction} for the given {@link String name}.
   * @throws IllegalArgumentException if the given {@link String name} does not match a {@link Direction}.
   * @see #name()
   */
  public static @NotNull Direction fromName(@NotNull String name) {

    return Arrays.stream(values())
      .filter(direction -> direction.name().equalsIgnoreCase(name))
      .findFirst()
      .orElseThrow(() -> newIllegalArgumentException("Direction name [%s] is not valid", name));
  }

  private final String abbreviation;

  Direction(@NotNull String abbreviation) {
    this.abbreviation = StringUtils.requireText(abbreviation, "Abbreviation [%s] is required");
  }

  /**
   * Get the {@link String abbreviation} (for example: {@literal NE}) symbolizing this {@link Direction}.
   *
   * @return the symbolic {@link String abbreviation} symbolizing this {@link Direction}.
   * @see #getName()
   */
  public @NotNull String getAbbreviation() {
    return this.abbreviation;
  }

  /**
   * Gets the {@link String name} (for example: {@literal Southwest}) of this {@link Direction}.
   *
   * @return the {@link String name} of this {@link Direction}.
   * @see #getAbbreviation()
   * @see #name()
   */
  @Override
  public @NotNull String getName() {
    return StringUtils.capitalize(name().toLowerCase());
  }

  /**
   * Determines whether this {@link Direction} is due {@link #NORTH}.
   *
   * @return a boolean value indicating whether this {@link Direction} is due {@link #NORTH}.
   */
  public boolean isNorth() {
    return NORTH.equals(this);
  }

  /**
   * Determines whether this {@link Direction} is {@literal Northbound}.
   *
   * @return a boolean value indicating whether this {@link Direction} is {@literal Northbound}.
   * @see #isNortheast()
   * @see #isNorthwest()
   * @see #isNorth()
   */
  public boolean isNorthbound() {
    return isNorth() || isNortheast() || isNorthwest();
  }

  /**
   * Determines whether this {@link Direction} is towards the {@link #NORTHEAST}.
   *
   * @return a boolean value indicating whether this {@link Direction} is towards the {@link #NORTHEAST}.
   */
  public boolean isNortheast() {
    return NORTHEAST.equals(this);
  }

  /**
   * Determines whether this {@link Direction} is towards the {@link #NORTHWEST}.
   *
   * @return a boolean value indicating whether this {@link Direction} is towards the {@link #NORTHWEST}.
   */
  public boolean isNorthwest() {
    return NORTHWEST.equals(this);
  }

  /**
   * Determines whether this {@link Direction} is due {@link #SOUTH}.
   *
   * @return a boolean value indicating whether this {@link Direction} is due {@link #SOUTH}.
   */
  public boolean isSouth() {
    return SOUTH.equals(this);
  }

  /**
   * Determines whether this {@link Direction} is {@literal Southbound}.
   *
   * @return a boolean value indicating whether this {@link Direction} is {@literal Southbound}.
   * @see #isSoutheast()
   * @see #isSouthwest()
   * @see #isSouth()
   */
  public boolean isSouthbound() {
    return isSouth() || isSoutheast() || isSouthwest();
  }

  /**
   * Determines whether this {@link Direction} is towards the {@link #SOUTHEAST}.
   *
   * @return a boolean value indicating whether this {@link Direction} is towards the {@link #SOUTHEAST}.
   */
  public boolean isSoutheast() {
    return SOUTHEAST.equals(this);
  }

  /**
   * Determines whether this {@link Direction} is towards the {@link #SOUTHWEST}.
   *
   * @return a boolean value indicating whether this {@link Direction} is towards the {@link #SOUTHWEST}.
   */
  public boolean isSouthwest() {
    return SOUTHWEST.equals(this);
  }

  /**
   * Determines whether this {@link Direction} is due {@link #EAST}.
   *
   * @return a boolean value indicating whether this {@link Direction} is due {@link #EAST}.
   */
  public boolean isEast() {
    return EAST.equals(this);
  }

  /**
   * Determines whether this {@link Direction} is {@literal Eastbound}.
   *
   * @return a boolean value indicating whether this {@link Direction} is {@literal Eastbound}.
   * @see #isNortheast()
   * @see #isSoutheast()
   * @see #isEast()
   */
  public boolean isEastbound() {
    return isEast() || isNortheast() || isSoutheast();
  }

  /**
   * Determines whether this {@link Direction} is due {@link #WEST}.
   *
   * @return a boolean value indicating whether this {@link Direction} is due {@link #WEST}.
   */
  public boolean isWest() {
    return WEST.equals(this);
  }

  /**
   * Determines whether this {@link Direction} is {@literal Westbound}.
   *
   * @return a boolean value indicating whether this {@link Direction} is {@literal Westbound}.
   * @see #isNorthwest()
   * @see #isSouthwest()
   * @see #isWest()
   */
  public boolean isWestbound() {
    return isWest() || isNorthwest() || isSouthwest();
  }

  /**
   * Returns a {@link String} representation of this {@link Distance}.
   *
   * @return a {@link String} describing this {@link Direction}.
   * @see #getName()
   */
  @Override
  public String toString() {
    return getName();
  }
}
