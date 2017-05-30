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

import org.cp.domain.geo.enums.Country;
import org.cp.elements.lang.ObjectUtils;
import org.cp.elements.lang.StringUtils;
import org.cp.elements.util.ComparatorResultBuilder;

/**
 * The {@link Street} class is an Abstract Data Type (ADT) modeling the street in a postal {@link Address}.
 *
 * @author John Blum
 * @see java.io.Serializable
 * @see java.lang.Comparable
 * @see org.cp.domain.geo.model.Address
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public class Street implements Comparable<Street>, Serializable {

  private Integer number;

  private String name;

  private Type type;

  /**
   * Factory method used to construct a new instance of {@link Street} initialized with
   * the given {@link Integer number} and {@link String name}.
   *
   * @param number {@link Integer} containing the {@link Street} number.
   * @param name {@link String} containing the name of the new {@link Street}.
   * @return a new {@link Street} initialized with the given {@link Integer number} and {@link String name}.
   * @throws IllegalArgumentException if either {@link Integer number} or {@link String name} is {@literal null}
   * or {@link String name} is empty.
   * @see #Street(Integer, String)
   */
  public static Street of(Integer number, String name) {
    return new Street(number, name);
  }

  /**
   * Factory method used to construct a new instance of {@link Street} from the given {@link Street}.
   *
   * @param street {@link Street} to copy; must not be {@literal null}.
   * @return a new {@link Street} from the given {@link Street}.
   * @throws IllegalArgumentException if the {@link Street} to copy is {@literal null}.
   * @see org.cp.domain.geo.model.Street
   * @see #of(Integer, String)
   */
  public static Street from(Street street) {
    return of(street.getNumber(), street.getName()).as(street.getType().orElse(null));
  }

  /**
   * Constructs a new instance of {@link Street} initialized with the given {@link Integer number}
   * and {@link String name}.
   *
   * @param number {@link Integer} containing the {@link Street} number.
   * @param name {@link String} containing the name of the new {@link Street}.
   * @throws IllegalArgumentException if either {@link Integer number} or {@link String name} is {@literal null}
   * or {@link String name} is empty.
   */
  public Street(Integer number, String name) {

    this.number = Optional.ofNullable(number)
      .orElseThrow(() -> newIllegalArgumentException("Number is required"));

    this.name = Optional.ofNullable(name).filter(StringUtils::hasText)
      .orElseThrow(() -> newIllegalArgumentException("Name is required"));
  }

  /**
   * Returns the {@link String name} of this {@link Street}.
   *
   * @return the {@link String name} of this {@link Street}.
   */
  public String getName() {
    return name;
  }

  /**
   * Returns the {@link Integer number} on this {@link Street}.
   *
   * @return the {@link Integer number} on this {@link Street}.
   */
  public Integer getNumber() {
    return this.number;
  }

  /**
   * Returns the {@link Type} of this {@link Street}.
   *
   * @return the {@link Type} of this {@link Street}.
   * @see org.cp.domain.geo.model.Street.Type
   */
  public Optional<Type> getType() {
    return Optional.ofNullable(this.type);
  }

  /**
   * Builder method used to set the {@link Type} of this {@link Street}.
   *
   * @param type {@link Type} of this {@link Street}.
   * @return this {@link Street}.
   * @see org.cp.domain.geo.model.Street.Type
   */
  public Street as(Type type) {
    this.type = type;
    return this;
  }

  /**
   * Compares this {@link Street} with the given {@link Street} to determine the sort order.
   *
   * @param street {@link Street} being compared to this {@link Street}
   * to determine the relative sort order.
   * @return a {@link Integer#TYPE int} value indicating the sort order of this {@link Street}
   * relative to the given {@link Street}.
   * Returns a negative number to indicate this {@link Street} comes before the given {@link Street} in the sort order.
   * Returns a positive number to indicate this {@link Street} comes after the given {@link Street} in the sort order.
   * Returns {@literal 0} if this {@link Street} is equal to the given {@link Street}.
   * @see java.lang.Comparable#compareTo(Object)
   */
  @Override
  @SuppressWarnings("unchecked")
  public int compareTo(Street street) {

    return ComparatorResultBuilder.<Comparable>create()
      .doCompare(this.getName(), street.getName())
      .doCompare(this.getType().orElse(Type.UNKNOWN), street.getType().orElse(Type.UNKNOWN))
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
  public boolean equals(Object obj) {

    if (this == obj) {
      return true;
    }

    if (!(obj instanceof Street)) {
      return false;
    }

    Street that = (Street) obj;

    return ObjectUtils.equals(this.getName(), that.getName())
      && ObjectUtils.equals(this.getNumber(), that.getNumber())
      && ObjectUtils.equalsIgnoreNull(this.getType(), that.getType());
  }

  /**
   * Computes the hash code for this {@link Street}.
   *
   * @return the computed hash code for this {@link Street}.
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {

    int hashValue = 17;

    hashValue = 37 * hashValue + ObjectUtils.hashCode(this.getName());
    hashValue = 37 * hashValue + ObjectUtils.hashCode(this.getNumber());
    hashValue = 37 * hashValue + ObjectUtils.hashCode(this.getType());

    return hashValue;
  }

  /**
   * Returns a {@link String} representation of this {@link Street}.
   *
   * @return a {@link String} describing this {@link Street}.
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return String.format("%1$d %2$s%3$s", getNumber(), getName(),
      getType().map(type -> StringUtils.SINGLE_SPACE.concat(type.getAbbreviation())).orElse(StringUtils.EMPTY_STRING));
  }

  /**
   * The {@link Type} enum is a enumeration of {@link Street} suffixes recognized around the World.
   *
   * NOTE: not every {@link Country countries} may recognize all {@link Street} suffixes
   * and this is not a complete list and only represents some of the more common types.
   *
   * @see <a href="https://en.wikipedia.org/wiki/Street_suffix">Street Suffixes</a>
   */
  enum Type {

    ALLEY("ALY", "Alley"),
    AVENUE("AVE", "Avenue"),
    BOULEVARD("BLVD", "Boulevard"),
    CIRCLE("CR", "Circle"),
    COURT("CT", "Court"),
    DRIVE("DR", "Drive"),
    HIGHWAY("HWY", "Highway"),
    JUNCTION("JCT", "Junction"),
    LANE("LN", "Lane"),
    LOOP("LP", "Loop"),
    PLAZA("PL", "Plaza"),
    ROAD("RD", "Road"),
    ROUTE("RT", "Route"),
    STREET("ST", "Street"),
    WAY("WY", "Way"),
    UNKNOWN("UKN", "Unknown");

    /**
     * Factory method used to search for an appropriate {@link Street} {@link Type}
     * based on its {@link String abbreviation}.
     *
     * @param abbreviation {@link String} containing the abbreviation of the desired {@link Street} {@link Type}.
     * @return the {@link Street} {@link Type} for the given {@link String abbreviation}.
     * @throws IllegalArgumentException if no {@link Street} {@link Type} for the given {@link String abbreviation}
     * could be found.
     */
    public static Type valueOfAbbreviation(String abbreviation) {

      return Arrays.stream(values())
        .filter(type -> type.getAbbreviation().equalsIgnoreCase(StringUtils.trim(abbreviation)))
        .findFirst()
        .orElseThrow(() -> newIllegalArgumentException("No Street Type was found for abbreviation [%s]", abbreviation));
    }

    /**
     * Factory method used to search for an appropriate {@link Street} {@link Type}
     * based on its {@link String name}.
     *
     * @param name  {@link String} containing the name of the desired {@link Street} {@link Type}.
     * @return the {@link Street} {@link Type} for the given {@link String name}.
     * @throws IllegalArgumentException if no {@link Street} {@link Type} for the given {@link String name}
     * could be found.
     */
    public static Type valueOfName(String name) {

      return Arrays.stream(values())
        .filter(it -> it.getName().equalsIgnoreCase(StringUtils.trim(name)))
        .findFirst()
        .orElseThrow(() -> newIllegalArgumentException("No Street Type was found for name [%s]", name));
    }

    private final String abbreviation;
    private final String name;

    /* (non-Javadoc) */
    Type(String abbreviation, String name) {
      this.abbreviation = abbreviation;
      this.name = name;
    }

    /**
     * Returns the {@link String abbreviation} for this {@link Street} {@link Type}.
     *
     * @return the {@link String abbreviation} for this {@link Street} {@link Type}.
     */
    public String getAbbreviation() {
      return abbreviation;
    }

    /**
     * Returns the {@link String name} for this {@link Street} {@link Type}.
     *
     * @return the {@link String name} for this {@link Street} {@link Type}.
     */
    public String getName() {
      return name;
    }

    /**
     * Returns a {@link String} representation of this {@link Street} {@link Type}.
     *
     * @return a {@link String} describing this {@link Street} {@link Type}.
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
      return getName();
    }
  }
}
