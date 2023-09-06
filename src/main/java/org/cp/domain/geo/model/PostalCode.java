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

import java.io.Serializable;
import java.util.Optional;

import org.cp.domain.geo.enums.Country;
import org.cp.elements.lang.Assert;
import org.cp.elements.lang.ObjectUtils;
import org.cp.elements.lang.StringUtils;
import org.cp.elements.lang.annotation.Immutable;
import org.cp.elements.lang.annotation.NotNull;
import org.cp.elements.lang.annotation.Nullable;
import org.cp.elements.lang.annotation.ThreadSafe;
import org.cp.elements.util.ComparatorResultBuilder;

/**
 * Abstract Data Type (ADT) used to model postal codes used throughout the world in different countries.
 *
 * @author John Blum
 * @see java.io.Serializable
 * @see java.lang.Cloneable
 * @see java.lang.Comparable
 * @see org.cp.domain.geo.enums.Country
 * @see org.cp.elements.lang.annotation.Immutable
 * @see org.cp.elements.lang.annotation.ThreadSafe
 * @since 0.1.0
 */
@Immutable
@ThreadSafe
public class PostalCode implements Cloneable, Comparable<PostalCode>, Serializable {

  /**
   * Factory method used to construct a new {@link PostalCode} copied from the given, required {@link PostalCode}.
   *
   * @param postalCode {@link PostalCode} to copy; must not be {@literal null}.
   * @return a new {@link PostalCode} copied from the given, required {@link PostalCode}.
   * @throws IllegalArgumentException if the {@link PostalCode} to copy is {@literal null}.
   * @see org.cp.domain.geo.model.PostalCode#getNumber()
   * @see org.cp.domain.geo.model.PostalCode
   * @see #of(String)
   */
  public static @NotNull PostalCode from(@NotNull PostalCode postalCode) {

    Assert.notNull(postalCode, "PostalCode to copy is required");

    return of(postalCode.getNumber());
  }

  /**
   * Factory method used to construct a new {@link PostalCode} initialized with the given,
   * required {@link String number}.
   *
   * @param number {@link String} containing the {@literal number} of the {@link PostalCode};
   * must not be {@literal null} or {@literal empty}.
   * @return a new {@link PostalCode} initialized with the given, required {@link String number}.
   * @throws IllegalArgumentException if {@link String number} is {@literal null} or {@literal empty}.
   * @see #PostalCode(String)
   */
  public static @NotNull PostalCode of(@NotNull String number) {
    return new PostalCode(number);
  }

  private final String number;

  /**
   * Constructs a new {@link PostalCode} initialized with the given, required {@link String number}.
   *
   * @param number {@link String} containing the {@literal number} for the {@link PostalCode};
   * must not be {@literal null} or {@literal empty}.
   * @throws IllegalArgumentException if the {@link String number} for the {@link PostalCode}
   * is {@literal null} or {@literal empty}.
   */
  public PostalCode(@NotNull String number) {
    this.number = StringUtils.requireText(number, "Number [%s] is required");
  }

  /**
   * Returns an {@link Optional} {@link Country} in which this {@link PostalCode} has been assigned.
   * <p>
   * Returns {@link Optional#empty()} by default.
   *
   * @return an {@link Optional} {@link Country} in which this {@link PostalCode} has been assigned.
   * @see org.cp.domain.geo.enums.Country
   * @see java.util.Optional
   */
  public Optional<Country> getCountry() {
    return Optional.empty();
  }

  /**
   * Returns the {@link String number} of this {@link PostalCode}.
   *
   * @return the {@link String number} of this {@link PostalCode}.
   */
  public @NotNull String getNumber() {
    return this.number;
  }

  /**
   * Clone this {@link PostalCode}.
   *
   * @return a clone (copy) of this {@link PostalCode}.
   * @see java.lang.Object#clone()
   * @see #from(PostalCode)
   */
  @Override
  @SuppressWarnings("all")
  public @NotNull Object clone() throws CloneNotSupportedException {
    return from(this);
  }

  /**
   * Compares this {@link PostalCode} to the given, required {@link PostalCode} to determine
   * the relative order in a sort.
   *
   * @param postalCode {@link PostalCode} being compared to this {@link PostalCode}; must not be {@literal null}.
   * @return a {@link Integer value} indicating the order of this {@link PostalCode} relative to the given,
   * required {@link PostalCode}.
   * Returns a {@link Integer number} if this {@link PostalCode} comes before the given {@link PostalCode}.
   * Returns a {@link Integer positive number} if this {@link PostalCode} comes after the given {@link PostalCode}.
   * Returns {@link Integer 0} if this {@link PostalCode} is equal to the given {@link PostalCode}.
   * @see java.lang.Comparable#compareTo(Object)
   */
  @Override
  @SuppressWarnings({ "rawtypes", "unchecked" })
  public int compareTo(@NotNull PostalCode postalCode) {

    return ComparatorResultBuilder.<Comparable>create()
      .doCompare(getCountry(this), getCountry(postalCode))
      .doCompare(this.getNumber(), postalCode.getNumber())
      .build();
  }

  /**
   * Determines whether this {@link PostalCode} is equal to the given {@link Object}.
   *
   * @param obj {@link Object} evaluated for equality with this {@link PostalCode}.
   * @return a boolean value indicating whether this {@link PostalCode} equals the given {@link Object}.
   * @see java.lang.Object#equals(Object)
   */
  @Override
  public boolean equals(@Nullable Object obj) {

    if (this == obj) {
      return true;
    }

    if (!(obj instanceof PostalCode that)) {
      return false;
    }

    return ObjectUtils.equals(this.getNumber(), that.getNumber())
      && ObjectUtils.equals(getCountry(this), getCountry(that));
  }

  /**
   * Computes the {@link Integer hash code} of this {@link PostalCode}.
   *
   * @return the computed {@link Integer hash code} of this {@link PostalCode}.
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    return ObjectUtils.hashCodeOf(getNumber(), getCountry(this));
  }

  private @NotNull Country getCountry(@NotNull PostalCode postalCode) {
    return postalCode.getCountry().orElse(Country.UNKNOWN);
  }

  /**
   * Returns a {@link String} representation of this {@link PostalCode}.
   *
   * @return a {@link String} describing this {@link PostalCode}.
   * @see java.lang.Object#toString()
   * @see #getNumber()
   */
  @Override
  public @NotNull String toString() {
    return getNumber();
  }
}
