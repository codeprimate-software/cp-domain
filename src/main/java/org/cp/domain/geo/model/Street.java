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
package org.cp.domain.geo.model;

import static org.cp.elements.lang.RuntimeExceptionsFactory.newIllegalArgumentException;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Optional;

import org.cp.domain.geo.enums.Country;
import org.cp.domain.geo.enums.Direction;
import org.cp.elements.lang.Assert;
import org.cp.elements.lang.Nameable;
import org.cp.elements.lang.ObjectUtils;
import org.cp.elements.lang.StringUtils;
import org.cp.elements.lang.annotation.Dsl;
import org.cp.elements.lang.annotation.FluentApi;
import org.cp.elements.lang.annotation.NotNull;
import org.cp.elements.lang.annotation.Nullable;
import org.cp.elements.util.ComparatorResultBuilder;

/**
 * Abstract Data Type (ADT) modeling a {@literal street} in a postal {@link Address}.
 *
 * @author John Blum
 * @see java.io.Serializable
 * @see java.lang.Cloneable
 * @see java.lang.Comparable
 * @see org.cp.domain.geo.model.Address
 * @see org.cp.elements.lang.Nameable
 * @see org.cp.elements.lang.annotation.FluentApi
 * @since 0.1.0
 */
@FluentApi
public class Street implements Cloneable, Comparable<Street>, Nameable<String>, Serializable {

  protected static final String STREET_TO_STRING = "%1$d %2$s%3$s%4$s";

  /**
   * Factory method used to construct a new {@link Street} copied from an existing, required {@link Street}.
   *
   * @param street {@link Street} to copy; must not be {@literal null}.
   * @return a new {@link Street} copied from the given {@link Street}.
   * @throws IllegalArgumentException if the {@link Street} to copy is {@literal null}.
   * @see org.cp.elements.lang.annotation.Dsl
   * @see org.cp.domain.geo.model.Street
   * @see #of(Integer, String)
   */
  @Dsl
  public static @NotNull Street from(@NotNull Street street) {

    Assert.notNull(street, "Street to copy is required");

    return of(street.getNumber(), street.getName()).as(street.getType().orElse(null));
  }

  /**
   * Factory method used to construct a new {@link Street} initialized with the given,
   * required {@link Integer number} and {@link String name}.
   *
   * @param number {@link Integer} containing the {@literal building number} on the {@link Street};
   * must not be {@literal null}.
   * @param name {@link String} containing the {@literal name} of the {@link Street}; must not be {@literal null}.
   * @return a new {@link Street} initialized with the given, required {@link Integer number} and {@link String name}.
   * @throws IllegalArgumentException if either {@link Integer number} or {@link String name} are {@literal null}
   * or the {@link String name} is {@link String#isEmpty() empty}.
   * @see org.cp.elements.lang.annotation.Dsl
   * @see org.cp.domain.geo.model.Street
   * @see #Street(Integer, String)
   */
  @Dsl
  public static @NotNull Street of(@NotNull Integer number, @NotNull String name) {
    return new Street(number, name);
  }

  /**
   * Factory method used to parse the given {@link String} and construct a new {@link Street}.
   *
   * @param street {@link String array} containing the components of a {@link Street}, such a street number, name,
   * optional directional and an optional street type.
   * @return a new {@link Street} constructed from the parse street components of the given {@link String}.
   * @throws IllegalArgumentException if the given {@link String} does not minimally contain a valid street number
   * and name.
   */
  public static @NotNull Street parse(@NotNull String street) {

    String[] streetComponents = assertStreet(street);

    int number = toStreetNumber(streetComponents);

    Optional<Direction> direction = toStreetDirection(streetComponents);

    String name = direction.isPresent() ? streetComponents[2] : streetComponents[1];

    Optional<Street.Type> streetType =
      direction.isPresent() && streetComponents.length > 3 ? toStreetType(streetComponents[3])
        : streetComponents.length > 2 ? toStreetType(streetComponents[2])
        : Optional.empty();

    Street resolvedStreet = of(number, name);

    direction.ifPresent(resolvedStreet::withDirection);
    streetType.ifPresent(resolvedStreet::as);

    return resolvedStreet;
  }

  private static String[] assertStreet(String street) {

    Assert.hasText(street, "Street [%s] to parse is required", street);

    String[] streetComponents = street.trim().split("\\s+");

    Assert.isTrue(streetComponents.length > 1,
      "Street [%s] must minimally consist of a number and name", street);

    return streetComponents;
  }

  private static Optional<Direction> toStreetDirection(String[] streetComponents) {

    String direction = streetComponents[1];

    try {
      return Optional.of(Direction.fromAbbreviation(direction));
    }
    catch (Throwable ignore) {
      return ObjectUtils.doOperationSafely(args ->
        Optional.of(Direction.fromName(direction)), Optional.empty());
    }
  }

  private static int toStreetNumber(String[] streetComponents) {

    try {
      return Integer.parseInt(streetComponents[0]);
    }
    catch (NumberFormatException cause) {
      throw newIllegalArgumentException(cause, "Street %s must begin with a street number",
        Arrays.toString(streetComponents).replaceAll(StringUtils.COMMA_DELIMITER, StringUtils.EMPTY_STRING));
    }
  }

  private static Optional<Street.Type> toStreetType(String streetType) {

    try {
      return Optional.of(Street.Type.fromAbbreviation(streetType));
    }
    catch (Throwable ignore) {
      return ObjectUtils.doOperationSafely(args ->
        Optional.of(Street.Type.fromName(streetType)), Optional.empty());
    }
  }

  private Direction direction;

  private final Integer number;

  private final String name;

  private Type type;

  /**
   * Constructs a new {@link Street} initialized with the given, required {@link Integer number}
   * and {@link String name}.
   *
   * @param number {@link Integer} containing the {@literal building number} on the {@link Street};
   * must not be {@literal null}.
   * @param name {@link String} containing the {@literal name} of the {@link Street}; must not be {@literal null}.
   * @throws IllegalArgumentException if either {@link Integer number} or {@link String name} are {@literal null}
   * or the {@link String name} is {@link String#isEmpty() empty}.
   */
  public Street(@NotNull Integer number, @NotNull String name) {

    this.number = ObjectUtils.requireObject(number, "Street number is required");
    this.name = StringUtils.requireText(name, "Street name [%s] is required");
  }

  /**
   * Gets the {@link Direction} on this {@link Street}.
   *
   * @return the {@link Direction} on this {@link Street}.
   * @see org.cp.domain.geo.enums.Direction
   * @see java.util.Optional
   */
  public Optional<Direction> getDirection() {
    return Optional.ofNullable(this.direction);
  }

  /**
   * Returns the {@link String name} of this {@link Street}.
   *
   * @return the {@link String name} of this {@link Street}.
   */
  public @NotNull String getName() {
    return this.name;
  }

  /**
   * Returns the {@link Integer number} of the building on this {@link Street}.
   *
   * @return the {@link Integer number} of the building on this {@link Street}.
   */
  public @NotNull Integer getNumber() {
    return this.number;
  }

  /**
   * Returns an {@link Optional} {@link Type} for this {@link Street}.
   * <p>
   * For example, the {@link Type} of {@link Street} may be a {@link Street.Type#ROAD} or {@link Street.Type#HIGHWAY}.
   *
   * @return an {@link Optional} {@link Type} for this {@link Street}.
   * @see org.cp.domain.geo.model.Street.Type
   * @see java.util.Optional
   * @see #as(Type)
   */
  public Optional<Street.Type> getType() {
    return Optional.ofNullable(this.type);
  }

  /**
   * Builder method used to set the {@link Type} for this {@link Street}.
   *
   * @param type {@link Type} of {@link Street}.
   * @return this {@link Street}.
   * @see org.cp.domain.geo.model.Street.Type
   * @see org.cp.elements.lang.annotation.Dsl
   * @see #getType()
   */
  @Dsl
  public @NotNull Street as(@Nullable Street.Type type) {
    this.type = type;
    return this;
  }

  /**
   * Sets the {@link Type} of this {@link Street} to {@link Street.Type#AVENUE}.
   *
   * @return this {@link Street}.
   * @see org.cp.domain.geo.model.Street.Type#AVENUE
   * @see org.cp.elements.lang.annotation.Dsl
   * @see #as(Type)
   */
  @Dsl
  public @NotNull Street asAvenue() {
    return as(Street.Type.AVENUE);
  }

  /**
   * Sets the {@link Type} of this {@link Street} to {@link Street.Type#BOULEVARD}.
   *
   * @return this {@link Street}.
   * @see org.cp.domain.geo.model.Street.Type#BOULEVARD
   * @see org.cp.elements.lang.annotation.Dsl
   * @see #as(Type)
   */
  @Dsl
  public @NotNull Street asBoulevard() {
    return as(Street.Type.BOULEVARD);
  }

  /**
   * Sets the {@link Type} of this {@link Street} to {@link Street.Type#DRIVE}.
   *
   * @return this {@link Street}.
   * @see org.cp.domain.geo.model.Street.Type#DRIVE
   * @see org.cp.elements.lang.annotation.Dsl
   * @see #as(Type)
   */
  @Dsl
  public @NotNull Street asDrive() {
    return as(Street.Type.DRIVE);
  }

  /**
   * Sets the {@link Type} of this {@link Street} to {@link Street.Type#FREEWAY}.
   *
   * @return this {@link Street}.
   * @see org.cp.domain.geo.model.Street.Type#FREEWAY
   * @see org.cp.elements.lang.annotation.Dsl
   * @see #as(Type)
   */
  @Dsl
  public @NotNull Street asFreeway() {
    return as(Street.Type.FREEWAY);
  }

  /**
   * Sets the {@link Type} of this {@link Street} to {@link Street.Type#HIGHWAY}.
   *
   * @return this {@link Street}.
   * @see org.cp.domain.geo.model.Street.Type#HIGHWAY
   * @see org.cp.elements.lang.annotation.Dsl
   * @see #as(Type)
   */
  @Dsl
  public @NotNull Street asHighway() {
    return as(Street.Type.HIGHWAY);
  }

  /**
   * Sets the {@link Type} of this {@link Street} to {@link Street.Type#LANE}.
   *
   * @return this {@link Street}.
   * @see org.cp.domain.geo.model.Street.Type#LANE
   * @see org.cp.elements.lang.annotation.Dsl
   * @see #as(Type)
   */
  @Dsl
  public @NotNull Street asLane() {
    return as(Street.Type.LANE);
  }

  /**
   * Sets the {@link Type} of this {@link Street} to {@link Street.Type#ROAD}.
   *
   * @return this {@link Street}.
   * @see org.cp.domain.geo.model.Street.Type#ROAD
   * @see org.cp.elements.lang.annotation.Dsl
   * @see #as(Type)
   */
  @Dsl
  public @NotNull Street asRoad() {
    return as(Street.Type.ROAD);
  }

  /**
   * Sets the {@link Type} of this {@link Street} to {@link Street.Type#ROUTE}.
   *
   * @return this {@link Street}.
   * @see org.cp.domain.geo.model.Street.Type#ROUTE
   * @see org.cp.elements.lang.annotation.Dsl
   * @see #as(Type)
   */
  @Dsl
  public @NotNull Street asRoute() {
    return as(Street.Type.ROUTE);
  }

  /**
   * Sets the {@link Type} of this {@link Street} to {@link Street.Type#STREET}.
   *
   * @return this {@link Street}.
   * @see org.cp.domain.geo.model.Street.Type#STREET
   * @see org.cp.elements.lang.annotation.Dsl
   * @see #as(Type)
   */
  @Dsl
  public @NotNull Street asStreet() {
    return as(Street.Type.STREET);
  }

  /**
   * Sets the {@link Type} of this {@link Street} to {@link Street.Type#WAY}.
   *
   * @return this {@link Street}.
   * @see org.cp.domain.geo.model.Street.Type#WAY
   * @see org.cp.elements.lang.annotation.Dsl
   * @see #as(Type)
   */
  @Dsl
  public @NotNull Street asWay() {
    return as(Street.Type.WAY);
  }

  /**
   * Builder method used to set the {@link Direction} on this {@link Street}.
   *
   * @param direction {@link Direction} on this {@link Street}.
   * @return this {@link Street}.
   * @see org.cp.domain.geo.enums.Direction
   */
  public @NotNull Street withDirection(@Nullable Direction direction) {
    this.direction = direction;
    return this;
  }

  /**
   * Clones this {@link Street}.
   *
   * @return a clone (copy) of this {@link Street}.
   * @see java.lang.Object#clone()
   * @see #from(Street)
   */
  @Override
  @SuppressWarnings("all")
  public @NotNull Object clone() throws CloneNotSupportedException {
    return from(this);
  }

  /**
   * Compares this {@link Street} with the given {@link Street} to determine relative ordering in a sort.
   *
   * @param street {@link Street} to compare with this {@link Street}.
   * @return a {@link Integer} value indicating the order of this {@link Street} relative to the given {@link Street}
   * in a sort.
   * Returns a negative number to indicate this {@link Street} comes before the given {@link Street} in the sort order.
   * Returns a positive number to indicate this {@link Street} comes after the given {@link Street} in the sort order.
   * Returns {@literal 0} if this {@link Street} is equal to the given {@link Street}.
   * @see java.lang.Comparable#compareTo(Object)
   */
  @Override
  @SuppressWarnings({ "rawtypes", "unchecked" })
  public int compareTo(@NotNull Street street) {

    return ComparatorResultBuilder.<Comparable>create()
      .doCompare(this.getName(), street.getName())
      .doCompare(this.getType().orElse(Street.Type.UNKNOWN), street.getType().orElse(Street.Type.UNKNOWN))
      .doCompare(this.getDirection().orElse(Direction.NORTH), street.getDirection().orElse(Direction.NORTH))
      .doCompare(this.getNumber(), street.getNumber())
      .build();
  }

  /**
   * Determines whether this {@link Street} is equal to the given {@link Object}.
   *
   * @param obj {@link Object} to evaluate for equality with this {@link Street}.
   * @return a boolean value indicating whether this {@link Street} is equal to the given {@link Object}.
   * @see java.lang.Object#equals(Object)
   */
  @Override
  public boolean equals(@Nullable Object obj) {

    if (this == obj) {
      return true;
    }

    if (!(obj instanceof Street that)) {
      return false;
    }

    return ObjectUtils.equals(this.getName(), that.getName())
      && ObjectUtils.equals(this.getNumber(), that.getNumber())
      && ObjectUtils.equalsIgnoreNull(this.getType(), that.getType());
  }

  /**
   * Computes the {@link Integer hash code} for this {@link Street}.
   *
   * @return the computed {@link Integer hash code} for this {@link Street}.
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    return ObjectUtils.hashCodeOf(getName(), getNumber(), getType().orElse(Street.Type.UNKNOWN),
      getDirection().orElse(null));
  }

  /**
   * Returns a {@link String} representation of this {@link Street}.
   *
   * @return a {@link String} describing this {@link Street}.
   * @see java.lang.Object#toString()
   */
  @Override
  public @NotNull String toString() {

    return String.format(STREET_TO_STRING, getNumber(),
      getDirection()
        .map(direction -> direction.getAbbreviation().concat(StringUtils.SINGLE_SPACE))
        .orElse(StringUtils.EMPTY_STRING),
      getName(),
      getType()
        .map(type -> StringUtils.SINGLE_SPACE.concat(type.getAbbreviation()))
        .orElse(StringUtils.EMPTY_STRING));
  }

  /**
   * {@link Enum Enumeration} of {@link Street} suffixes recognized around the world.
   * <p>
   * NOTE: Not every {@link Country} may recognize all {@link Street} suffixes defined by this {@link Enum enumeration}
   * nor is this {@link Enum enumeration} complete. This {@link Enum enumeration} only represents some of the more
   * common {@link Street} types.
   *
   * @see java.lang.Enum
   * @see org.cp.elements.lang.Nameable
   * @see <a href="https://en.wikipedia.org/wiki/Street_suffix">Street Suffixes</a>
   */
  public enum Type implements Nameable<String> {

    ALLEY("ALLY", "Alley"),
    AVENUE("AVE", "Avenue"),
    BEND("BND", "Bend"),
    BOULEVARD("BLVD", "Boulevard"),
    BYPASS("BYP", "Bypass"),
    CAUSEWAY("CSWY", "Causeway"),
    CENTER("CTR", "Center"),
    CIRCLE("CRCL", "Circle"),
    CORNER("CNR", "Corner"),
    COURT("CT", "Court"),
    CROSSING("XING", "Crossing"),
    CROSSROAD("XRD", "Crossroad"),
    CURVE("CURV", "Curve"),
    DRIVE("DR", "Drive"),
    EXPRESSWAY("EXP", "Expressway"),
    FERRY("FRY", "Ferry"),
    FORK("FRK", "Fork"),
    FREEWAY("FWY", "Freeway"),
    GATEWAY("GTWY", "Gateway"),
    HIGHWAY("HWY", "Highway"),
    JUNCTION("JCT", "Junction"),
    LANE("LN", "Lane"),
    LOOP("LP", "Loop"),
    MOTORWAY("MTWY", "Motorway"),
    OVERPASS("OPAS", "Overpass"),
    PARKWAY("PKWY", "Parkway"),
    PLACE("PL", "Place"),
    PLAZA("PLZ", "Plaza"),
    ROAD("RD", "Road"),
    ROUTE("RTE", "Route"),
    SKYWAY("SKWY", "Skyway"),
    SQUARE("SQR", "Square"),
    STREET("ST", "Street"),
    TURNPIKE("TPKE", "Turnpike"),
    UNDERPASS("UPAS", "Underpass"),
    UNKNOWN("UKN", "Unknown"),
    VIADUCT("VIA", "Viaduct"),
    WAY("WY", "Way");

    /**
     * Factory method used to search for a {@link Street.Type} given an {@link String abbreviation}.
     *
     * @param abbreviation {@link String} containing an {@literal abbreviation} for the desired {@link Street.Type},
     * such as {@literal HWY} for {@literal highway}.
     * @return a {@link Street.Type} for the given {@link String abbreviation}.
     * @throws IllegalArgumentException if a {@link Street.Type} for the given {@link String abbreviation}
     * could not be found.
     * @see #fromName(String)
     * @see #getAbbreviation()
     * @see #values()
     */
    public static @NotNull Street.Type fromAbbreviation(@Nullable String abbreviation) {

      return Arrays.stream(values())
        .filter(streetType -> streetType.getAbbreviation().equalsIgnoreCase(StringUtils.trim(abbreviation)))
        .findFirst()
        .orElseThrow(() -> newIllegalArgumentException("Street.Type for abbreviation [%s] was not found",
          abbreviation));
    }

    /**
     * Factory method used to search for a {@link Street.Type} given a {@link String name}.
     *
     * @param name {@link String} containing a {@literal name} of the desired {@link Street.Type},
     * such as {@literal highway}.
     * @return a {@link Street.Type} for the given {@link String name}.
     * @throws IllegalArgumentException if a {@link Street.Type} for the given {@link String name}
     * could not be found.
     * @see #fromAbbreviation(String)
     * @see #getName()
     * @see #values()
     */
    public static @NotNull Street.Type fromName(@Nullable String name) {

      return Arrays.stream(values())
        .filter(streetType -> streetType.getName().equalsIgnoreCase(StringUtils.trim(name)))
        .findFirst()
        .orElseThrow(() -> newIllegalArgumentException("Street.Type for name [%s] was not found", name));
    }

    private final String abbreviation;
    private final String name;

    Type(String abbreviation, String name) {

      this.abbreviation = StringUtils.requireText(abbreviation, "Abbreviation [%s] is required");
      this.name = StringUtils.requireText(name, "Name [%s] is required");
    }

    /**
     * Returns the {@link String abbreviation} for this {@link Street.Type}.
     *
     * @return the {@link String abbreviation} for this {@link Street.Type}.
     * @see #getName()
     */
    public @NotNull String getAbbreviation() {
      return this.abbreviation;
    }

    /**
     * Returns a {@link String description} of this {@link Street.Type}.
     *
     * @return a {@link String description} of this {@link Street.Type}.
     * @see #getAbbreviation()
     */
    public @NotNull String getName() {
      return this.name;
    }

    /**
     * Returns a {@link String} representation of this {@link Street.Type}.
     *
     * @return a {@link String} describing this {@link Street.Type}.
     * @see java.lang.Object#toString()
     * @see #getName()
     */
    @Override
    public @NotNull String toString() {
      return getName();
    }
  }
}
