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
import org.cp.elements.lang.ObjectUtils;
import org.cp.elements.lang.annotation.Immutable;
import org.cp.elements.util.ComparatorResultBuilder;

/**
 * The {@link PostalCode} class is an Abstract Data Type (ADT) used to model and represent postal codes
 * used throughout the World in different Countries.
 *
 * @author John Blum
 * @see java.io.Serializable
 * @see java.lang.Cloneable
 * @see java.lang.Comparable
 * @see org.cp.domain.geo.enums.Country
 * @see org.cp.elements.lang.annotation.Immutable
 * @since 1.0.0
 */
@Immutable
@SuppressWarnings("unused")
public class PostalCode implements Cloneable, Comparable<PostalCode>, Serializable {

  private final String number;

  /**
   * Factory method used to construct a new instance of {@link PostalCode} initialized with
   * the given {@link String number}.
   *
   * @param number {@link String} containing the number for the {@link PostalCode}.
   * @return a new {@link PostalCode} initialized with the given {@link String number}.
   * @throws IllegalArgumentException if the {@link String number} is {@literal null} or empty.
   * @see #PostalCode(String)
   */
  public static PostalCode of(String number) {
    return new PostalCode(number);
  }

  /**
   * Factory method used to construct a new instance of {@link PostalCode} copied from the given,
   * existing {@link PostalCode}.
   *
   * @param postalCode {@link PostalCode} to copy; must not be {@literal null}.
   * @return a new {@link PostalCode} copied from the given {@link PostalCode}.
   * @throws IllegalArgumentException if the {@link PostalCode} to copy is {@literal null}.
   * @see org.cp.domain.geo.model.PostalCode#getNumber()
   * @see org.cp.domain.geo.model.PostalCode
   * @see #of(String)
   */
  public static PostalCode from(PostalCode postalCode) {

    Assert.notNull(postalCode, "Postal Code is required");

    return of(postalCode.getNumber());
  }

  /**
   * Constructs a new instance of {@link PostalCode} initialized with the given {@link String number}.
   *
   * @param number {@link String} containing the {@link PostalCode} number.
   * @throws IllegalArgumentException if the {@link String number} is {@literal null} or empty.
   */
  public PostalCode(String number) {

    Assert.hasText(number, "Postal Code number [%s] is required", number);

    this.number = number;
  }

  /**
   * Returns an {@link Optional} {@link Country} to which this {@link PostalCode} belongs.
   *
   * @return an {@link Optional} {@link Country} to which this {@link PostalCode} belongs.
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
   * @see java.lang.String
   */
  public String getNumber() {
    return this.number;
  }

  /**
   * Clone this {@link PostalCode}.
   *
   * @return a clone of this {@link PostalCode}.
   * @see java.lang.Object#clone()
   * @see #from(PostalCode)
   */
  @Override
  @SuppressWarnings("all")
  public Object clone() throws CloneNotSupportedException {
    return from(this);
  }

  /**
   * Compares this {@link PostalCode} with the given {@link PostalCode} to determine the sort order.
   *
   * @param postalCode {@link PostalCode} being compared to this {@link PostalCode}
   * to determine the relative sort order.
   * @return a {@link Integer#TYPE int} value indicating the sort order of this {@link PostalCode}
   * relative to the given {@link PostalCode}.
   * Returns a negative number to indicate this {@link PostalCode} comes before the given {@link PostalCode}
   * in the sort order.
   * Returns a positive number to indicate this {@link PostalCode} comes after the given {@link PostalCode}
   * in the sort order.
   * Returns {@literal 0} if this {@link PostalCode} is equal to the given {@link PostalCode}.
   * @see java.lang.Comparable#compareTo(Object)
   */
  @Override
  public int compareTo(PostalCode postalCode) {

    return ComparatorResultBuilder.<String>create()
      .doCompare(this.toString(), postalCode.toString())
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
  public boolean equals(Object obj) {

    if (this == obj) {
      return true;
    }

    if (!(obj instanceof PostalCode)) {
      return false;
    }

    PostalCode that = (PostalCode) obj;

    return ObjectUtils.equals(this.getNumber(), that.getNumber());
  }

  /**
   * Computes the hash code of this {@link PostalCode}.
   *
   * @return the computed hash code of this {@link PostalCode}.
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {

    int hashValue = 17;

    hashValue = 37 * hashValue + ObjectUtils.hashCode(this.getNumber());

    return hashValue;
  }

  /**
   * Returns a {@link String} representation of this {@link PostalCode}.
   *
   * @return a {@link String} describing this {@link PostalCode}.
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return getNumber();
  }
}
