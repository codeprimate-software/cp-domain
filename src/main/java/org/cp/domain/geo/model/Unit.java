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

package org.cp.domain.geo.model;

import static org.cp.elements.lang.RuntimeExceptionsFactory.newIllegalArgumentException;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Optional;

import org.cp.elements.lang.Assert;
import org.cp.elements.lang.ObjectUtils;
import org.cp.elements.lang.StringUtils;
import org.cp.elements.util.ComparatorResultBuilder;

/**
 * The {@link Unit} class is an Abstract Data Type (ADT) that models an apartment, office or suite
 * at a particular postal {@link Address}.
 *
 * @author John Blum
 * @see java.io.Serializable
 * @see java.lang.Comparable
 * @see org.cp.domain.geo.model.Address
 * @see org.cp.elements.lang.annotation.Immutable
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public class Unit implements Comparable<Unit>, Serializable {

  private final String number;

  private Type type;

  /**
   * Factory method used to construct an instance of {@link Unit} initialized with given,
   * required {@link String number}.
   *
   * @param number {@link String} containing the number of the new {@link Unit}.
   * @return a new {@link Unit} initialized with the given, required {@link String number};
   * must not be {@literal null} or empty.
   * @throws IllegalArgumentException if {@link String number} is {@literal null} or empty.
   * @see #Unit(String)
   */
  public static Unit of(String number) {
    return new Unit(number);
  }

  /**
   * Factory method used to construct a new instance of {@link Unit} copied from the given {@link Unit}.
   *
   * @param unit {@link Unit} to copy; must not be {@literal null}.
   * @return a new {@link Unit} copied from the given {@link Unit}.
   * @throws IllegalArgumentException if {@link Unit} is {@literal null}.
   * @see org.cp.domain.geo.model.Unit
   * @see #of(String)
   * @see #as(Type)
   */
  public static Unit from(Unit unit) {

    Assert.notNull(unit, "Unit must not be null");

    return of(unit.getNumber());
  }

  /**
   * Constructs a new instance of {@link Unit} initialized with the given {@link String number}.
   *
   * @param number {@link String} containing the number of this new {@link Unit}.
   * @throws IllegalArgumentException if number is {@literal null} or empty.
   */
  public Unit(String number) {
    this.number = Optional.ofNullable(number).filter(StringUtils::hasText)
      .orElseThrow(() -> newIllegalArgumentException("Number is required"));
  }

  /**
   * Returns the {@link String number} of this {@link Unit}.
   *
   * @return the {@link String number} of this {@link Unit}.
   * @see java.lang.String
   */
  public String getNumber() {
    return this.number;
  }

  /**
   * Returns the {@link Optional} {@link Type} of this {@link Unit}.
   *
   * @return the {@link Optional} {@link Type} of this {@link Unit}.
   * @see org.cp.domain.geo.model.Unit.Type
   */
  public Optional<Type> getType() {
    return Optional.ofNullable(this.type);
  }

  /**
   * Builder method to set the {@link Type} of this {@link Unit}.
   *
   * @param type {@link Type} of this {@link Unit}.
   * @return this {@link Unit}.
   * @see org.cp.domain.geo.model.Unit.Type
   */
  public Unit as(Type type) {
    this.type = type;
    return this;
  }

  /**
   * Compares this {@link Unit} with the given {@link Unit} to determine the relative sort order.
   *
   * A {@link Unit} is ordered by {@link #getNumber() number} first and {@link Type}, if present, second.
   *
   * @param unit {@link Unit} to compare with this {@link Unit} to determine the relative sort order.
   * @return a {@link Integer#TYPE int} value determining the sort order of this {@link Unit}
   * relative to the given {@link Unit}.
   * Returns a negative number to indicate this {@link Unit} comes before the given {@link Unit}.
   * Returns a positive number to indicate this {@link Unit} comes after the given {@link Unit}.
   * Returns {@literal 0} if this {@link Unit} is equal to the given {@link Unit Units}.
   * @see java.lang.Comparable#compareTo(Object)
   */
  @Override
  @SuppressWarnings("unchecked")
  public int compareTo(Unit unit) {
    return ComparatorResultBuilder.<Comparable>create()
      .doCompare(this.getType().orElse(null), unit.getType().orElse(null))
      .doCompare(this.getNumber(), unit.getNumber())
      .build();
  }

  /**
   * Determines whether this {@link Unit} is equal to the given {@link Object}.
   *
   * @param obj {@link Object} evaluated for equality with this {@link Unit}.
   * @return a boolean value indicating whether this {@link Unit} is equal to the given {@link Object}.
   * @see java.lang.Object#equals(Object)
   */
  @Override
  public boolean equals(Object obj) {

    if (this == obj) {
      return true;
    }

    if (!(obj instanceof Unit)) {
      return false;
    }

    Unit that = (Unit) obj;

    return ObjectUtils.equals(this.getNumber(), that.getNumber())
      && ObjectUtils.equals(this.getType(), that.getType());
  }

  /**
   * Computes the hash code of this {@link Unit}.
   *
   * @return the computed hash code of this {@link Unit}.
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {

    int hashValue = 17;

    hashValue = 37 * hashValue + ObjectUtils.hashCode(this.getNumber());
    hashValue = 37 * hashValue + ObjectUtils.hashCode(this.getType());

    return hashValue;
  }

  /**
   * Returns a {@link String} representation of this {@link Unit}.
   *
   * @return a {@link String} describing this {@link Unit}.
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return String.format("%1$s%2$s",
      getType().map(type -> type.getName().concat(StringUtils.SINGLE_SPACE)).orElse(StringUtils.EMPTY_STRING),
      getNumber());
  }

  /**
   * The {@link Type} enum represents the type {@link Unit}, like apartment complex, office building or suite.
   */
  public enum Type {

    APARTMENT("APT", "Apartment"),
    OFFICE("OFC", "Office"),
    ROOM("RM", "Room"),
    SUITE("STE", "Suite");

    private final String abbreviation;
    private final String name;

    /**
     * Factory method used to search for an appropriate {@link Unit} {@link Type}
     * based on its {@link String abbreviation}.
     *
     * @param abbreviation {@link String} containing the abbreviation of the desired {@link Unit} {@link Type}.
     * @return the {@link Unit} {@link Type} for the given {@link String abbreviation}.
     * @throws IllegalArgumentException if no {@link Unit} {@link Type} for the given {@link String abbreviation}
     * could be found.
     */
    public static Type valueOfAbbreviation(String abbreviation) {

      return Arrays.stream(values())
        .filter(type -> type.getAbbreviation().equalsIgnoreCase(StringUtils.trim(abbreviation)))
        .findFirst()
        .orElseThrow(() -> newIllegalArgumentException("No Unit Type was found for abbreviation [%s]", abbreviation));
    }

    /**
     * Factory method used to search for an appropriate {@link Unit} {@link Type}
     * based on its {@link String name}.
     *
     * @param name  {@link String} containing the name of the desired {@link Unit} {@link Type}.
     * @return the {@link Unit} {@link Type} for the given {@link String name}.
     * @throws IllegalArgumentException if no {@link Unit} {@link Type} for the given {@link String name}
     * could be found.
     */
    public static Type valueOfName(String name) {

      return Arrays.stream(values())
        .filter(type -> type.getName().equalsIgnoreCase(StringUtils.trim(name)))
        .findFirst()
        .orElseThrow(() -> newIllegalArgumentException("No Unit Type was found for name [%s]", name));
    }

    /* (non-Javadoc) */
    Type(String abbreviation, String name) {
      this.abbreviation = abbreviation;
      this.name = name;
    }

    /**
     * Returns the {@link String abbreviation} for this {@link Unit} {@link Type}.
     *
     * @return the {@link String abbreviation} for this {@link Unit} {@link Type}.
     */
    public String getAbbreviation() {
      return this.abbreviation;
    }

    /**
     * Returns the {@link String name} for this {@link Unit} {@link Type}.
     *
     * @return the {@link String name} for this {@link Unit} {@link Type}.
     */
    public String getName() {
      return this.name;
    }

    /**
     * Returns a {@link String} representation of this {@link Unit} {@link Type}.
     *
     * @return a {@link String} describing this {@link Unit} {@link Type}.
     * @see java.lang.Object#toString()
     * @see java.lang.String
     */
    @Override
    public String toString() {
      return getName();
    }
  }
}
