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
import org.cp.elements.lang.annotation.Dsl;
import org.cp.elements.lang.annotation.FluentApi;
import org.cp.elements.lang.annotation.NotNull;
import org.cp.elements.lang.annotation.Nullable;
import org.cp.elements.util.ComparatorResultBuilder;

/**
 * Abstract Data Type (ADT) modeling a type of unit, such as an {@literal apartment}, {@literal office}, {@literal room}
 * or {@literal suite} at an associated postal {@link Address}.
 *
 * @author John Blum
 * @see java.io.Serializable
 * @see java.lang.Cloneable
 * @see java.lang.Comparable
 * @see org.cp.domain.geo.model.Address
 * @see org.cp.elements.lang.annotation.FluentApi
 * @since 0.1.0
 */
@FluentApi
public class Unit implements Cloneable, Comparable<Unit>, Serializable {

  public static final String UNIT_TO_STRING = "%1$s%2$s";

  public static final Unit EMPTY = Unit.of("0").as(Unit.Type.UNKNOWN);

  /**
   * Factory method used to construct a new instance of {@link Unit} initialized with the given,
   * required {@link String number}, and type of {@link Unit.Type#APARTMENT}.
   *
   * @param number {@link String} containing the {@literal number} of the {@link Unit};
   * must not be {@literal null} or {@literal empty}.
   * @return a new {@link Unit.Type#APARTMENT} {@link Unit} initialized with the given,
   * required {@link String number}.
   * @see org.cp.domain.geo.model.Unit.Type#APARTMENT
   * @see org.cp.elements.lang.annotation.Dsl
   * @see #asApartment()
   * @see #Unit(String)
   */
  @Dsl
  public static @NotNull Unit apartment(@NotNull String number) {
    return new Unit(number).asApartment();
  }

  /**
   * Factory method used to construct a new instance of {@link Unit} initialized with the given,
   * required {@link String number}, and type of {@link Unit.Type#OFFICE}.
   *
   * @param number {@link String} containing the {@literal number} of the {@link Unit};
   * must not be {@literal null} or {@literal empty}.
   * @return a new {@link Unit.Type#OFFICE} {@link Unit} initialized with the given,
   * required {@link String number}.
   * @see org.cp.domain.geo.model.Unit.Type#OFFICE
   * @see org.cp.elements.lang.annotation.Dsl
   * @see #Unit(String)
   * @see #asOffice()
   */
  @Dsl
  public static @NotNull Unit office(@NotNull String number) {
    return new Unit(number).asOffice();
  }

  /**
   * Factory method used to construct a new instance of {@link Unit} initialized with the given,
   * required {@link String number}, and type of {@link Unit.Type#ROOM}.
   *
   * @param number {@link String} containing the {@literal number} of the {@link Unit};
   * must not be {@literal null} or {@literal empty}.
   * @return a new {@link Unit.Type#ROOM} {@link Unit} initialized with the given,
   * required {@link String number}.
   * @see org.cp.domain.geo.model.Unit.Type#ROOM
   * @see org.cp.elements.lang.annotation.Dsl
   * @see #Unit(String)
   * @see #asRoom()
   */
  @Dsl
  public static @NotNull Unit room(@NotNull String number) {
    return new Unit(number).asRoom();
  }

  /**
   * Factory method used to construct a new instance of {@link Unit} initialized with the given,
   * required {@link String number}, and type of {@link Unit.Type#SUITE}.
   *
   * @param number {@link String} containing the {@literal number} of the {@link Unit};
   * must not be {@literal null} or {@literal empty}.
   * @return a new {@link Unit.Type#SUITE} {@link Unit} initialized with the given,
   * required {@link String number}.
   * @see org.cp.domain.geo.model.Unit.Type#SUITE
   * @see org.cp.elements.lang.annotation.Dsl
   * @see #Unit(String)
   * @see #asSuite()
   */
  @Dsl
  public static @NotNull Unit suite(@NotNull String number) {
    return new Unit(number).asSuite();
  }

  /**
   * Factory method used to construct a new instance of {@link Unit} copied from the given, required {@link Unit}.
   *
   * @param unit {@link Unit} to copy; must not be {@literal null}.
   * @return a new {@link Unit} copied from the given {@link Unit}.
   * @throws IllegalArgumentException if {@link Unit} is {@literal null}.
   * @see org.cp.elements.lang.annotation.Dsl
   * @see org.cp.domain.geo.model.Unit
   * @see #of(String)
   * @see #as(Type)
   */
  @Dsl
  public static @NotNull Unit from(@NotNull Unit unit) {

    Assert.notNull(unit, "Unit to copy is required");

    return of(unit.getNumber()).as(unit.getType().orElse(null));
  }

  /**
   * Factory method used to construct a new instance of {@link Unit} initialized with the given,
   * required {@link String number}.
   *
   * @param number {@link String} containing the {@literal number} of the new {@link Unit};
   * must not be {@literal null} or {@literal empty}.
   * @return a new {@link Unit} initialized with the given, required {@link String number}.
   * @throws IllegalArgumentException if {@link String number} is {@literal null} or {@literal empty}.
   * @see org.cp.domain.geo.model.Unit.Type#UNKNOWN
   * @see org.cp.elements.lang.annotation.Dsl
   * @see #Unit(String)
   * @see #as(Type)
   */
  @Dsl
  public static @NotNull Unit of(@NotNull String number) {
    return new Unit(number).as(Unit.Type.UNKNOWN);
  }

  private final String number;

  private Type type;

  /**
   * Constructs a new instance of {@link Unit} initialized with the given, required {@link String number}.
   *
   * @param number {@link String} containing the {@literal number} of this new {@link Unit};
   * must not be {@literal null} or {@literal empty}.
   * @throws IllegalArgumentException if {@link String number} is {@literal null} or {@literal empty}.
   */
  public Unit(@NotNull String number) {
    this.number = StringUtils.requireText(number, "Number [%s] is required");
  }

  /**
   * Returns the {@link String number} of this {@link Unit}.
   *
   * @return the {@link String number} of this {@link Unit}.
   * @see java.lang.String
   */
  public @NotNull String getNumber() {
    return this.number;
  }

  /**
   * Returns an {@link Optional} {@link Type} for this {@link Unit}.
   *
   * @return an {@link Optional} {@link Type} for this {@link Unit}.
   * @see org.cp.domain.geo.model.Unit.Type
   */
  public Optional<Unit.Type> getType() {
    return Optional.ofNullable(this.type);
  }

  /**
   * Builder method used to set the {@link Type} for this {@link Unit}.
   *
   * @param type {@link Type} to assign to this {@link Unit}.
   * @return this {@link Unit}.
   * @see org.cp.elements.lang.annotation.Dsl
   * @see org.cp.domain.geo.model.Unit.Type
   */
  @Dsl
  public @NotNull Unit as(@Nullable Unit.Type type) {
    this.type = type;
    return this;
  }

  /**
   * Sets the {@link Unit.Type} of this {@link Unit} to {@link Unit.Type#APARTMENT}.
   *
   * @return this {@link Unit}.
   * @see org.cp.domain.geo.model.Unit.Type#APARTMENT
   * @see org.cp.elements.lang.annotation.Dsl
   * @see #as(Type)
   */
  @Dsl
  public @NotNull Unit asApartment() {
    return as(Unit.Type.APARTMENT);
  }

  /**
   * Sets the {@link Unit.Type} of this {@link Unit} to {@link Unit.Type#OFFICE}.
   *
   * @return this {@link Unit}.
   * @see org.cp.domain.geo.model.Unit.Type#OFFICE
   * @see org.cp.elements.lang.annotation.Dsl
   * @see #as(Type)
   */
  @Dsl
  public @NotNull Unit asOffice() {
    return as(Unit.Type.OFFICE);
  }

  /**
   * Sets the {@link Unit.Type} of this {@link Unit} to {@link Unit.Type#ROOM}.
   *
   * @return this {@link Unit}.
   * @see org.cp.domain.geo.model.Unit.Type#ROOM
   * @see org.cp.elements.lang.annotation.Dsl
   * @see #as(Type)
   */
  @Dsl
  public @NotNull Unit asRoom() {
    return as(Unit.Type.ROOM);
  }

  /**
   * Sets the {@link Unit.Type} of this {@link Unit} to {@link Unit.Type#SUITE}.
   *
   * @return this {@link Unit}.
   * @see org.cp.domain.geo.model.Unit.Type#SUITE
   * @see org.cp.elements.lang.annotation.Dsl
   * @see #as(Type)
   */
  @Dsl
  public @NotNull Unit asSuite() {
    return as(Unit.Type.SUITE);
  }

  /**
   * Clones this {@link Unit}.
   *
   * @return a clone (copy) of this {@link Unit}.
   * @see java.lang.Object#clone()
   * @see #from(Unit)
   */
  @Override
  @SuppressWarnings("all")
  public @NotNull Object clone() throws CloneNotSupportedException {
    return from(this);
  }

  /**
   * Compares this {@link Unit} with the given, required {@link Unit} to determine the relative ordering in a sort.
   *
   * A {@link Unit} is ordered by {@link #getNumber() number} first and {@link Unit.Type}, if present, second.
   *
   * @param unit {@link Unit} to compare with this {@link Unit}; must not be {@literal null}.
   * @return a {@link Integer} value determining the order of this {@link Unit} relative to the given {@link Unit}.
   * Returns a {@link Integer negative number} to indicate this {@link Unit} comes before the given {@link Unit}.
   * Returns a {@link Integer positive number} to indicate this {@link Unit} comes after the given {@link Unit}.
   * Returns {@link Integer 0} if this {@link Unit} is equal to the given {@link Unit}.
   * @see java.lang.Comparable#compareTo(Object)
   */
  @Override
  @SuppressWarnings({ "rawtypes", "unchecked" })
  public int compareTo(@NotNull Unit unit) {

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
  public boolean equals(@Nullable Object obj) {

    if (this == obj) {
      return true;
    }

    if (!(obj instanceof Unit)) {
      return false;
    }

    Unit that = (Unit) obj;

    return ObjectUtils.equals(this.getNumber(), that.getNumber())
      && ObjectUtils.equals(this.getType().orElse(Unit.Type.UNKNOWN), that.getType().orElse(Unit.Type.UNKNOWN));
  }

  /**
   * Computes the {@link Integer hash code} of this {@link Unit}.
   *
   * @return the computed {@link Integer hash code} of this {@link Unit}.
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    return ObjectUtils.hashCodeOf(getNumber(), getType().orElse(Unit.Type.UNKNOWN));
  }

  /**
   * Returns a {@link String} representation of this {@link Unit}.
   *
   * @return a {@link String} describing this {@link Unit}.
   * @see java.lang.Object#toString()
   */
  @Override
  public @NotNull String toString() {

    return String.format(UNIT_TO_STRING, getType()
        .map(type -> type.getDescription().concat(StringUtils.SINGLE_SPACE))
        .orElse(Unit.Type.UNIT.getDescription().concat(StringUtils.SINGLE_SPACE)),
      getNumber());
  }

  /**
   * {@link Enum Enumration} of {@link Unit} types, such as an {@literal apartment} complex,
   * an {@literal office} building or a {@literal room} number.
   *
   * @see java.lang.Enum
   */
  public enum Type {

    APARTMENT("APT", "Apartment"),
    OFFICE("OFC", "Office"),
    ROOM("RM", "Room"),
    SUITE("STE", "Suite"),
    UNIT("UNT", "Unit"),
    UNKNOWN("UKN", "Unknown");

    /**
     * Factory method used to search for a {@link Unit.Type} given an {@link String abbreviation}.
     *
     * @param abbreviation {@link String} containing the {@literal abbreviation} of the desired {@link Unit.Type},
     * such as {@literal RM} for {@literal room}.
     * @return the {@link Unit.Type} for the given {@link String abbreviation}.
     * @throws IllegalArgumentException if a {@link Unit.Type} for the given {@link String abbreviation}
     * could not be found.
     * @see #fromDescription(String)
     * @see #getAbbreviation()
     * @see #values()
     */
    public static @NotNull Unit.Type fromAbbreviation(@Nullable String abbreviation) {

      return Arrays.stream(values())
        .filter(type -> type.getAbbreviation().equalsIgnoreCase(StringUtils.trim(abbreviation)))
        .findFirst()
        .orElseThrow(() -> newIllegalArgumentException("Unit.Type for abbreviation [%s] was not found", abbreviation));
    }

    /**
     * Factory method used to search for an appropriate {@link Unit} {@link Type}
     * based on its {@link String name}.
     *
     * @param description  {@link String} containing the name of the desired {@link Unit} {@link Type}.
     * @return the {@link Unit} {@link Type} for the given {@link String name}.
     * @throws IllegalArgumentException if no {@link Unit} {@link Type} for the given {@link String name}
     * could be found.
     */
    public static @NotNull Unit.Type fromDescription(@Nullable String description) {

      return Arrays.stream(values())
        .filter(type -> type.getDescription().equalsIgnoreCase(StringUtils.trim(description)))
        .findFirst()
        .orElseThrow(() -> newIllegalArgumentException("Unit.Type for description [%s] was not found", description));
    }

    private final String abbreviation;
    private final String description;

    Type(@NotNull String abbreviation, @NotNull String description) {

      this.abbreviation = StringUtils.requireText(abbreviation, "Abbreviation [%s] is required");
      this.description = StringUtils.requireText(description, "Description [%s] is required");
    }

    /**
     * Returns an {@link String abbreviation} for this {@link Unit.Type}.
     *
     * @return an {@link String abbreviation} for this {@link Unit.Type}.
     * @see #getDescription()
     */
    public @NotNull String getAbbreviation() {
      return this.abbreviation;
    }

    /**
     * Returns a {@link String description} of this {@link Unit.Type}.
     *
     * @return a {@link String description} of this {@link Unit.Type}.
     * @see #getAbbreviation()
     */
    public @NotNull String getDescription() {
      return this.description;
    }

    /**
     * Returns a {@link String} representation of this {@link Unit.Type}.
     *
     * @return a {@link String} describing this {@link Unit.Type}.
     * @see java.lang.Object#toString()
     * @see #getDescription()
     */
    @Override
    public @NotNull String toString() {
      return getDescription();
    }
  }
}
