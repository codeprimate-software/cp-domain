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
package org.cp.domain.contact.phone.model;

import java.io.Serializable;

import org.cp.elements.lang.Assert;
import org.cp.elements.lang.ObjectUtils;
import org.cp.elements.lang.StringUtils;
import org.cp.elements.lang.annotation.Immutable;
import org.cp.elements.lang.annotation.NotNull;
import org.cp.elements.lang.annotation.Nullable;

/**
 * Abstract Data Type (ADT) modeling the {@literal line number} of a {@link PhoneNumber}.
 *
 * @author John Blum
 * @see java.lang.Cloneable
 * @see java.lang.Comparable
 * @see java.io.Serializable
 * @see org.cp.domain.contact.phone.model.PhoneNumber
 * @see org.cp.elements.lang.annotation.Immutable
 * @since 0.1.0
 */
@Immutable
public class LineNumber implements Cloneable, Comparable<LineNumber>, Serializable {

  private static final int REQUIRED_NUMBER_LENGTH = 4;

  /**
   * Factory method used to construct a new {@link LineNumber} copied from an existing, required {@link LineNumber}.
   *
   * @param number {@link LineNumber} to copy; must not be {@literal null}.
   * @return a new {@link LineNumber} copied from an existing, required {@link LineNumber}.
   * @throws IllegalArgumentException if the {@link LineNumber} to copy is {@literal null}.
   */
  public static @NotNull LineNumber from(@NotNull LineNumber number) {

    Assert.notNull(number, "LineNumber to copy is required");

    return new LineNumber(number.getNumber());
  }

  /**
   * Factory method used to construct a new {@link LineNumber} from the given, required {@link String number}.
   *
   * @param number {@link String} containing the {@literal 4-digit number} of this {@link LineNumber};
   * must not be {@literal null} or {@literal empty}; must be {@literal 4-digits}.
   * @return a new {@link LineNumber} initialized with the given, required {@link String number}.
   * @throws IllegalArgumentException if the {@link String number} is {@literal null}, {@literal empty}
   * or is not {@literal 4-digits}.
   */
  public static @NotNull LineNumber of(@NotNull String number) {
    return new LineNumber(number);
  }

  private final String number;

  /**
   * Constructs a new {@link LineNumber} initialized with the given, required {@link String number}.
   *
   * @param number {@link String} containing the {@literal 4-digit number} for this {@link LineNumber};
   * must not be {@literal null} or {@literal empty}; must be {@literal 4-digits}.
   * @throws IllegalArgumentException if the {@link String line number} is {@literal null}, {@literal empty}
   * or is not of the {@link #getRequiredNumberLength() required length}.
   */
  public LineNumber(@NotNull String number) {

    int requiredNumberLength = getRequiredNumberLength();

    Assert.isTrue(StringUtils.getDigits(number).length() == requiredNumberLength,
      "Number [%s] must be a %d-digit number", number, requiredNumberLength);

    this.number = number;
  }

  /**
   * Gets the {@link String number} of this {@link LineNumber}.
   *
   * @return the {@link String number} of this {@link LineNumber}.
   */
  public @NotNull String getNumber() {
    return this.number;
  }

  /**
   * Gets the required {@link Integer length} of the {@link #getNumber()} for this {@link LineNumber}.
   *
   * @return the required {@link Integer length} of the {@link #getNumber()} for this {@link LineNumber}.
   */
  protected int getRequiredNumberLength() {
    return REQUIRED_NUMBER_LENGTH;
  }

  @Override
  @SuppressWarnings("all")
  protected Object clone() throws CloneNotSupportedException {
    return from(this);
  }

  @Override
  public int compareTo(@NotNull LineNumber that) {
    return this.getNumber().compareTo(that.getNumber());
  }

  @Override
  public boolean equals(@Nullable Object obj) {

    if (this == obj) {
      return true;
    }

    if (!(obj instanceof LineNumber that)) {
      return false;
    }

    return ObjectUtils.equals(this.getNumber(), that.getNumber());
  }

  @Override
  public int hashCode() {
    return ObjectUtils.hashCodeOf(this.getNumber());
  }

  @Override
  public String toString() {
    return getNumber();
  }
}
