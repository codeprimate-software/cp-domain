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
 * Abstract Data Type (ADT) modeling the {@literal 4-digit number} of a {@link PhoneNumber}.
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
public class FourDigitNumber implements Cloneable, Comparable<FourDigitNumber>, Serializable {

  private static final int REQUIRED_NUMBER_LENGTH = 4;

  /**
   * Factory method used to construct a new instance of {@link FourDigitNumber} copied from the existing,
   * required {@link FourDigitNumber}.
   *
   * @param number {@link FourDigitNumber} to copy; must not be {@literal null}.
   * @return a new {@link FourDigitNumber} copied from the existing, required {@link FourDigitNumber}.
   * @throws IllegalArgumentException if the {@link FourDigitNumber} to copy is {@literal null}.
   */
  public static @NotNull FourDigitNumber from(@NotNull FourDigitNumber number) {

    Assert.notNull(number, "FourDigitNumber to copy is required");

    return new FourDigitNumber(number.getNumber());
  }

  /**
   * Factory method used to construct a new instance of {@link FourDigitNumber} from the given,
   * required {@link String 4-digit number}.
   *
   * @param number {@link String} containing the {@literal 4-digit number} of this {@link FourDigitNumber};
   * must not be {@literal null} or {@literal empty}; must be {@literal 4-digits}.
   * @throws IllegalArgumentException if the {@link String 4-digit number} is {@literal null}, {@literal empty}
   * or is not (only) {@literal 4-digits}.
   * @return a new {@link FourDigitNumber} initialized with the given, required {@link String 4-digit number}.
   */
  public static @NotNull FourDigitNumber of(@NotNull String number) {
    return new FourDigitNumber(number);
  }

  private final String number;

  /**
   * Constructs a new instance of {@link FourDigitNumber} initialized with the given,
   * required {@link String 4-digit number}.
   *
   * @param number {@link String} containing the {@literal 4-digit number} of this {@link FourDigitNumber};
   * must not be {@literal null} or {@literal empty}; must be {@literal 4-digits}.
   * @throws IllegalArgumentException if the {@link String 4-digit number} is {@literal null}, {@literal empty}
   * or is not (only) {@literal 4-digits}.
   */
  public FourDigitNumber(@NotNull String number) {

    Assert.isTrue(StringUtils.getDigits(number).length() == REQUIRED_NUMBER_LENGTH,
      "Number [%s] must be a %d-digit number", number, REQUIRED_NUMBER_LENGTH);

    this.number = number;
  }

  /**
   * Gets the {@link String 4-digit number} of this {@link FourDigitNumber}.
   *
   * @return the {@link String 4-digit number} of this {@link FourDigitNumber}.
   */
  public @NotNull String getNumber() {
    return this.number;
  }

  @Override
  @SuppressWarnings("all")
  protected Object clone() throws CloneNotSupportedException {
    return from(this);
  }

  @Override
  public int compareTo(@NotNull FourDigitNumber that) {
    return this.getNumber().compareTo(that.getNumber());
  }

  @Override
  public boolean equals(@Nullable Object obj) {

    if (this == obj) {
      return true;
    }

    if (!(obj instanceof FourDigitNumber that)) {
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
