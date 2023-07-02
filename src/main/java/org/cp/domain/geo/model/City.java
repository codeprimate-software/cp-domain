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

import java.io.Serializable;
import java.util.Optional;

import org.cp.domain.geo.enums.Country;
import org.cp.elements.lang.Assert;
import org.cp.elements.lang.Nameable;
import org.cp.elements.lang.ObjectUtils;
import org.cp.elements.lang.StringUtils;
import org.cp.elements.lang.annotation.Immutable;
import org.cp.elements.lang.annotation.NotNull;
import org.cp.elements.lang.annotation.Nullable;
import org.cp.elements.lang.concurrent.ThreadSafe;
import org.cp.elements.util.ComparatorResultBuilder;

/**
 * Abstract Data Type (ADT) modeling a {@literal city} of a {@link Country} for a postal {@link Address}.
 *
 * @author John Blum
 * @see java.io.Serializable
 * @see java.lang.Cloneable
 * @see java.lang.Comparable
 * @see org.cp.elements.lang.Nameable
 * @see org.cp.elements.lang.annotation.Immutable
 * @see org.cp.elements.lang.concurrent.ThreadSafe
 * @since 0.1.0
 */
@Immutable
@ThreadSafe
public class City implements Cloneable, Comparable<City>, Nameable<String>, Serializable {

  /**
   * Factory method used to construct a new {@link City} copied from the given, required {@link City}.
   *
   * @param city {@link City} to copy; must not be {@literal null}.
   * @return a new {@link City} copied from the given, required {@link City}.
   * @throws IllegalArgumentException if {@link City} is {@literal null}.
   * @see org.cp.domain.geo.model.City#getName()
   * @see org.cp.domain.geo.model.City
   * @see #of(String)
   */
  public static @NotNull City from(@NotNull City city) {

    Assert.notNull(city, "City to copy is required");

    return of(city.getName());
  }

  /**
   * Factory method used to construct a new {@link City} initialized with the given, required {@link String name}.
   *
   * @param name {@link String} containing the {@literal name} of the {@link City};
   * must not be {@literal null} or {@literal empty}.
   * @return a new {@link City} initialized with the given, required {@link String name}.
   * @throws IllegalArgumentException if {@link String name} is {@literal null} or {@literal empty}.
   * @see #City(String)
   */
  public static @NotNull City of(@NotNull String name) {
    return new City(name);
  }

  private final String name;

  /**
   * Constructs a new {@link City} initialized with the given, required {@link String name}.
   *
   * @param name {@link String} containing the {@literal name} of this {@link City};
   * must not be {@literal null} or {@literal empty}.
   * @throws IllegalArgumentException if {@link String name} is {@literal null} or {@literal empty}.
   */
  public City(@NotNull String name) {
    this.name = StringUtils.requireText(name, "Name [%s] is required");
  }

  /**
   * Returns an {@link Optional} {@link Country} in which this {@link City} originates.
   * <p>
   * Returns {@link Optional#empty()} by default.
   *
   * @return an {@link Optional} {@link Country} in which this {@link City} originates.
   * @see org.cp.domain.geo.enums.Country
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
  public @NotNull String getName() {
    return this.name;
  }

  /**
   * Clones this {@link City}.
   *
   * @return a clone (copy) of this {@link City}.
   * @see java.lang.Object#clone()
   * @see #from(City)
   */
  @Override
  @SuppressWarnings("all")
  public @NotNull Object clone() throws CloneNotSupportedException {
    return from(this);
  }

  /**
   * Compares this {@link City} to the given, required {@link City} to determine relative ordering in a sort.
   *
   * @param city {@link City} to compare with this {@link City}; must not be {@literal null}.
   * @return an {@link Integer} value indicating the order of this {@link City} relative to the given {@link City}.
   * Returns a {@link Integer negative value} if this {@link City} comes before the given {@link City}.
   * Returns a {@link Integer positive value} if this {@link City} comes after the given {@link City}.
   * Returns {@link Integer 0} if this {@link City} is equal to the given {@link City}.
   * @see java.lang.Comparable#compareTo(Object)
   */
  @Override
  @SuppressWarnings({ "rawtypes", "unchecked" })
  public int compareTo(@NotNull City city) {

    return ComparatorResultBuilder.<Comparable>create()
      .doCompare(getCountry(this), getCountry(city))
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
  public boolean equals(@Nullable Object obj) {

    if (this == obj) {
      return true;
    }

    if (!(obj instanceof City that)) {
      return false;
    }

    return ObjectUtils.equals(this.getName(), that.getName())
      && ObjectUtils.equals(getCountry(this), getCountry(that));
  }

  /**
   * Computes the {@link Integer hash code} of this {@link City}.
   *
   * @return the computed {@link Integer hash code} of this {@link City}.
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    return ObjectUtils.hashCodeOf(getName(), getCountry(this));
  }

  private @NotNull Country getCountry(@NotNull City city) {
    return city.getCountry().orElse(Country.UNKNOWN);
  }

  /**
   * Returns a {@link String} representation of this {@link City}.
   *
   * @return a {@link String} describing this {@link City}.
   * @see java.lang.Object#toString()
   * @see #getName()
   */
  @Override
  public @NotNull String toString() {
    return getName();
  }
}
