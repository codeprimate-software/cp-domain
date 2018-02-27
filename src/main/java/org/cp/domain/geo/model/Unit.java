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
 * The {@link Unit} class is an Abstract Data Type (ADT) that models an apartment, office, room or suite
 * at a particular postal {@link Address}.
 *
 * @author John Blum
 * @see java.io.Serializable
 * @see java.lang.Cloneable
 * @see java.lang.Comparable
 * @see org.cp.domain.geo.model.Address
 * @see org.cp.elements.lang.annotation.Immutable
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public class Unit implements Cloneable, Comparable<Unit>, Serializable {

  public static final Unit EMPTY = Unit.of("0");

  /**
   * Factory method used to construct a new instance of {@link Unit} initialized with the given {@link String number}
   * as type {@link Unit.Type#APARTMENT}.
   *
   * @param number {@link String} containing the number of the {@link Unit}.
   * @return a new {@link Unit.Type#APARTMENT} {@link Unit} initialized with the given {@link String number}.
   * @see org.cp.domain.geo.model.Unit.Type#APARTMENT
   * @see #Unit(String)
   * @see #as(Type)
   */
  public static Unit apartment(String number) {
    return new Unit(number).as(Type.APARTMENT);
  }

  /**
   * Factory method used to construct a new instance of {@link Unit} initialized with the given {@link String number}
   * as type {@link Unit.Type#OFFICE}.
   *
   * @param number {@link String} containing the number of the {@link Unit}.
   * @return a new {@link Unit.Type#OFFICE} {@link Unit} initialized with the given {@link String number}.
   * @see org.cp.domain.geo.model.Unit.Type#OFFICE
   * @see #Unit(String)
   * @see #as(Type)
   */
  public static Unit office(String number) {
    return new Unit(number).as(Type.OFFICE);
  }

  /**
   * Factory method used to construct a new instance of {@link Unit} initialized with the given {@link String number}
   * as type {@link Unit.Type#ROOM}.
   *
   * @param number {@link String} containing the number of the {@link Unit}.
   * @return a new {@link Unit.Type#ROOM} {@link Unit} initialized with the given {@link String number}.
   * @see org.cp.domain.geo.model.Unit.Type#ROOM
   * @see #Unit(String)
   * @see #as(Type)
   */
  public static Unit room(String number) {
    return new Unit(number).as(Type.ROOM);
  }

  /**
   * Factory method used to construct a new instance of {@link Unit} initialized with the given {@link String number}
   * as type {@link Unit.Type#SUITE}.
   *
   * @param number {@link String} containing the number of the {@link Unit}.
   * @return a new {@link Unit.Type#SUITE} {@link Unit} initialized with the given {@link String number}.
   * @see org.cp.domain.geo.model.Unit.Type#SUITE
   * @see #Unit(String)
   * @see #as(Type)
   */
  public static Unit suite(String number) {
    return new Unit(number).as(Type.SUITE);
  }

  /**
   * Factory method used to construct an instance of {@link Unit} initialized with the given,
   * required {@link String number}.
   *
   * @param number {@link String} containing the number of the new {@link Unit}.
   * @return a new {@link Unit} initialized with the given, required {@link String number};
   * must not be {@literal null} or empty.
   * @throws IllegalArgumentException if {@link String number} is {@literal null} or empty.
   * @see org.cp.domain.geo.model.Unit.Type#UNKNOWN
   * @see #Unit(String)
   * @see #as(Type)
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

    Assert.notNull(unit, "Unit is required");

    return of(unit.getNumber()).as(unit.getType().orElse(null));
  }

  private final String number;

  private Type type;

  /**
   * Constructs a new instance of {@link Unit} initialized with the given {@link String number}.
   *
   * @param number {@link String} containing the number of this new {@link Unit}.
   * @throws IllegalArgumentException if {@link String number} is {@literal null} or empty.
   */
  public Unit(String number) {

    Assert.hasText(number, "Unit number [%s] is required", number);

    this.number = number;
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
   * Returns an {@link Optional} {@link Type} for this {@link Unit}.
   *
   * @return an {@link Optional} {@link Type} for this {@link Unit}.
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
   * Sets the {@link Unit.Type} of this {@link Unit} to {@link Unit.Type#APARTMENT}.
   *
   * @return this {@link Unit}.
   * @see org.cp.domain.geo.model.Unit.Type#APARTMENT
   * @see #as(Type)
   */
  public Unit asApartment() {
    return as(Type.APARTMENT);
  }

  /**
   * Sets the {@link Unit.Type} of this {@link Unit} to {@link Unit.Type#OFFICE}.
   *
   * @return this {@link Unit}.
   * @see org.cp.domain.geo.model.Unit.Type#OFFICE
   * @see #as(Type)
   */
  public Unit asOffice() {
    return as(Type.OFFICE);
  }

  /**
   * Sets the {@link Unit.Type} of this {@link Unit} to {@link Unit.Type#ROOM}.
   *
   * @return this {@link Unit}.
   * @see org.cp.domain.geo.model.Unit.Type#ROOM
   * @see #as(Type)
   */
  public Unit asRoom() {
    return as(Type.ROOM);
  }

  /**
   * Sets the {@link Unit.Type} of this {@link Unit} to {@link Unit.Type#SUITE}.
   *
   * @return this {@link Unit}.
   * @see org.cp.domain.geo.model.Unit.Type#SUITE
   * @see #as(Type)
   */
  public Unit asSuite() {
    return as(Type.SUITE);
  }

  /**
   * Clones this {@link Unit}.
   *
   * @return a clone of this {@link Unit}.
   * @see #from(Unit)
   */
  @Override
  @SuppressWarnings("all")
  public Object clone() throws CloneNotSupportedException {
    return from(this);
  }

  /**
   * Compares this {@link Unit} with the given {@link Unit} to determine relative ordering in a sort.
   *
   * A {@link Unit} is ordered by {@link #getNumber() number} first and {@link Type} second, if present.
   *
   * @param unit {@link Unit} to compare with this {@link Unit}.
   * @return a {@link Integer} value determining the order of this {@link Unit} relative to the given {@link Unit}
   * in a sort.
   * Returns a negative number to indicate this {@link Unit} comes before the given {@link Unit} in the sort order.
   * Returns a positive number to indicate this {@link Unit} comes after the given {@link Unit} in the sort order.
   * Returns {@literal 0} if this {@link Unit} is equal to the given {@link Unit}.
   * @see java.lang.Comparable#compareTo(Object)
   */
  @Override
  @SuppressWarnings("unchecked")
  public int compareTo(Unit unit) {

    return ComparatorResultBuilder.<Comparable>create()
      .doCompare(this.getNumber(), unit.getNumber())
      .doCompare(this.getType().orElse(Type.UNKNOWN), unit.getType().orElse(Type.UNKNOWN))
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
   * Computes the {@link Integer hash code} of this {@link Unit}.
   *
   * @return the computed {@link Integer hash code} of this {@link Unit}.
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
   * @see java.lang.String
   */
  @Override
  public String toString() {

    return String.format("%1$s%2$s",
      getType().map(type -> type.getName().concat(StringUtils.SINGLE_SPACE))
        .orElse(Type.UNIT.getName().concat(StringUtils.SINGLE_SPACE)),
      getNumber());
  }

  /**
   * The {@link Type} enum represents the type {@link Unit}, like apartment complex, office building or suite.
   */
  public enum Type {

    APARTMENT("APT", "Apartment"),
    OFFICE("OFC", "Office"),
    ROOM("RM", "Room"),
    SUITE("STE", "Suite"),
    UNIT("UNT", "Unit"),
    UNKNOWN("UKN", "Unknown");

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
        .orElseThrow(() -> newIllegalArgumentException("Unit Type for abbreviation [%s] was not found", abbreviation));
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
        .orElseThrow(() -> newIllegalArgumentException("Unit Type for name [%s] was not found", name));
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
