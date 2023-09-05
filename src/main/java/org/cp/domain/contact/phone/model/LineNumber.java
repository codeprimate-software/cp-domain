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
package org.cp.domain.contact.phone.model;

import java.io.Serializable;

import org.cp.elements.lang.Assert;
import org.cp.elements.lang.ObjectUtils;
import org.cp.elements.lang.Renderable;
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
 * @see org.cp.elements.lang.Renderable
 * @since 0.1.0
 */
@Immutable
public class LineNumber implements Cloneable, Comparable<LineNumber>, Renderable, Serializable {

  protected static final int REQUIRED_LINE_NUMBER_LENGTH = 4;

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
   * Factory method used to construct a new {@link LineNumber} initialized with the given {@link Integer number}.
   *
   * @param number {@link Integer 3-digit number} for the {@link ExchangeCode}; must be {@literal 3-digits}.
   * @return a new {@link LineNumber} with the given {@link Integer number}.
   * @throws IllegalArgumentException if the given {@link Integer number} is not {@literal 3-digits}.
   */
  public static @NotNull LineNumber of(int number) {
    return new LineNumber(String.valueOf(Math.abs(number)));
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
   * Factory method used to parse and extract a {@link LineNumber} from the given, required {@link String phone number}.
   *
   * @param phoneNumber {@link String} containing a {@literal phone number} from which to parse
   * and extract a {@link LineNumber}; must contain at least {@literal 4-digits}.
   * @return a new {@link LineNumber} from the given {@link String phone number}.
   * @throws IllegalArgumentException if the {@link String phone number} does not consist of
   * at least {@literal 4-digits}.
   */
  public static @NotNull LineNumber parse(@NotNull String phoneNumber) {

    String digits = StringUtils.getDigits(phoneNumber);

    Assert.isTrue(digits.length() >= REQUIRED_LINE_NUMBER_LENGTH,
      "Phone Number [%s] must be at least %d-digits", phoneNumber, REQUIRED_LINE_NUMBER_LENGTH);

    return new LineNumber(digits.substring(digits.length() - REQUIRED_LINE_NUMBER_LENGTH));
  }

  /**
   * Constructs a new {@link LineNumber} initialized with the given, required {@link String number}.
   *
   * @param number {@link String} containing the {@literal 4-digit number} for this {@link LineNumber};
   * must not be {@literal null} or {@literal empty}; must be {@literal 4-digits}.
   * @throws IllegalArgumentException if the {@link String line number} is {@literal null}, {@literal empty}
   * or is not of the {@link #getRequiredLength() required length}.
   */
  public LineNumber(@NotNull String number) {

    int requiredNumberLength = getRequiredLength();

    String resolvedNumber = StringUtils.getDigits(number);

    Assert.isTrue(resolvedNumber.length() == requiredNumberLength,
      "LineNumber [%s] must be a %d-digit number", number, requiredNumberLength);

    this.number = resolvedNumber;
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
   * Get the required {@link Integer length} for a {@link LineNumber}.
   *
   * @return the required {@link Integer length} for a {@link LineNumber}.
   */
  protected int getRequiredLength() {
    return REQUIRED_LINE_NUMBER_LENGTH;
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
