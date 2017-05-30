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
import java.util.Optional;

import org.cp.domain.geo.enums.Country;
import org.cp.elements.lang.Assert;
import org.cp.elements.lang.ObjectUtils;
import org.cp.elements.lang.StringUtils;
import org.cp.elements.lang.annotation.Immutable;
import org.cp.elements.util.ComparatorResultBuilder;

/**
 * The {@link City} class is an Abstract Data Type (ADT) modeling a city in a given location of a {@link Country}.
 *
 * @author John Blum
 * @see java.io.Serializable
 * @see java.lang.Comparable
 * @see org.cp.elements.lang.annotation.Immutable
 * @since 1.0.0
 */
@Immutable
@SuppressWarnings("unused")
public class City implements Comparable<City>, Serializable {

  private final String name;

  /**
   * Factory method used to construct a new instance of {@link City} initialized with the given {@link String name}.
   *
   * @param name {@link String} containing the name of this {@link City}.
   * @return a new {@link City} initialized with the given {@link String name}.
   * @throws IllegalArgumentException if {@link String name} is {@literal null} or empty.
   * @see #City(String)
   */
  public static City of(String name) {
    return new City(name);
  }

  /**
   * Factory method used to construct a new instance of {@link City} copied from the given {@link City}.
   *
   * @param city {@link City} to copy; must not be {@literal null}.
   * @return a new {@link City} copied from the given {@link City}.
   * @throws IllegalArgumentException if the {@link City} to copy is {@literal null}.
   * @see org.cp.domain.geo.model.City
   * @see #of(String)
   */
  public static City from(City city) {

    Assert.notNull(city, "City must not be null");

    return of(city.getName());
  }

  /**
   * Constructs a new instance of {@link City} initialized with the given {@link String name}.
   *
   * @param name {@link String} containing the name of this {@link City}.
   * @throws IllegalArgumentException if {@link String name} is {@literal null} or empty.
   */
  public City(String name) {
    this.name = Optional.ofNullable(name).filter(StringUtils::hasText)
      .orElseThrow(() -> newIllegalArgumentException("Name is required"));
  }

  /**
   * Returns an {@link Optional} {@link Country} of origin for this {@link City};
   *
   * @return an {@link Optional} {@link Country} of origin for this {@link City};
   * @see java.util.Optional
   */
  public Optional<Country> getCountry() {
    return Optional.empty();
  }

  /**
   * Returns the {@link String name} of this {@link City}.
   *
   * @return the {@link String name} of this {@link City}.
   */
  public String getName() {
    return this.name;
  }

  /**
   * Compares this {@link City} to the given {@link City} to determine the relative, natural sort order.
   *
   * @param city {@link City} being compared with this {@link City} to determine the relative, natural sort order.
   * @return a {@link Integer#TYPE int} value indicating the sort order of this {@link City}
   * relative to the given {@link City}.
   * Returns a negative value to indicate this {@link City} comes before the given {@link City} in the sort order.
   * Returns a positive value to indicate this {@link City} comes after the given {@link City} in the sort order.
   * Returns {@literal 0} if this {@link City} is equal to the given {@link City}.
   * @see java.lang.Comparable#compareTo(Object)
   */
  @Override
  @SuppressWarnings("unchecked")
  public int compareTo(City city) {

    return ComparatorResultBuilder.<Comparable>create()
      .doCompare(this.getCountry().orElse(Country.UNKNOWN), city.getCountry().orElse(Country.UNKNOWN))
      .doCompare(this.getName(), city.getName())
      .build();
  }

  /**
   * Determines whether this {@link City} is equal to the given {@link Object}.
   *
   * @param obj {@link Object} to evaluate for equality with this {@link City}.
   * @return a boolean value indicating whether this {@link City} is equal to the given {@link Object}.
   * @see java.lang.Object#equals(Object)
   */
  @Override
  public boolean equals(Object obj) {

    if (this == obj) {
      return true;
    }

    if (!(obj instanceof City)) {
      return false;
    }

    City that = (City) obj;

    return ObjectUtils.equals(this.getName(), that.getName());
  }

  /**
   * Computes the hash code of this {@link City}.
   *
   * @return an {@link Integer#TYPE int} value containing the computed hash code of this {@link City}.
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {

    int hashValue = 17;

    hashValue = 37 * hashValue + ObjectUtils.hashCode(this.getName());

    return hashValue;
  }

  /**
   * Returns a {@link String} representation of this {@link City}.
   *
   * @return a {@link String} describing this {@link City}.
   * @see java.lang.Object#toString()
   * @see java.lang.String
   */
  @Override
  public String toString() {
    return getName();
  }
}
