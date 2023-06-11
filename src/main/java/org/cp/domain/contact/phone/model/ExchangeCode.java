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

import static org.cp.elements.lang.RuntimeExceptionsFactory.newIllegalArgumentException;

import java.io.Serializable;

import org.cp.elements.lang.Assert;
import org.cp.elements.lang.ObjectUtils;
import org.cp.elements.lang.Renderable;
import org.cp.elements.lang.StringUtils;
import org.cp.elements.lang.annotation.Immutable;
import org.cp.elements.lang.annotation.NotNull;
import org.cp.elements.lang.annotation.Nullable;

/**
 * Abstract Data Type (ADT) modeling the {@literal exchange code} of a {@link PhoneNumber}.
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
public class ExchangeCode implements Cloneable, Comparable<ExchangeCode>, Renderable, Serializable {

  protected static final int REQUIRED_EXCHANGE_CODE_LENGTH = 3;

  /**
   * Factory method used to construct a new {@link ExchangeCode} copied from an existing, required {@link ExchangeCode}.
   *
   * @param exchangeCode {@link ExchangeCode} to copy; must not be {@literal null}.
   * @return a new {@link ExchangeCode} copied from an existing, required {@link ExchangeCode}.
   * @throws IllegalArgumentException if the {@link ExchangeCode} to copy is {@literal null}.
   */
  public static @NotNull ExchangeCode from(@NotNull ExchangeCode exchangeCode) {

    Assert.notNull(exchangeCode, "ExchangeCode to copy is required");

    return new ExchangeCode(exchangeCode.getNumber());
  }

  /**
   * Factory method used to construct a new {@link ExchangeCode} initialized with the given {@link Integer number}.
   *
   * @param number {@link Integer 3-digit number} for the {@link ExchangeCode}; must be {@literal 3-digits}.
   * @return a new {@link ExchangeCode} with the given {@link Integer number}.
   * @throws IllegalArgumentException if the {@link Integer number} is not {@literal 3-digits}.
   */
  public static @NotNull ExchangeCode of(int number) {
    return new ExchangeCode(String.valueOf(Math.abs(number)));
  }

  /**
   * Factory method used to construct a new {@link ExchangeCode} from the given, required {@link String number}.
   *
   * @param number {@link String} containing the {@literal 3-digit number} for the {@link ExchangeCode};
   * must not be {@literal null} or {@literal empty}; must be {@literal 3-digits}.
   * @return a new {@link ExchangeCode} initialized with the given, required {@link String number}.
   * @throws IllegalArgumentException if the {@link String number} is {@literal null}, {@literal empty}
   * or is not {@literal 3-digits}.
   */
  public static @NotNull ExchangeCode of(@NotNull String number) {
    return new ExchangeCode(number);
  }

  /**
   * Factory method used to parse and extract an {@link ExchangeCode} from the given,
   * required {@link String phone number}.
   *
   * @param phoneNumber {@link String} containing the {@literal phone number} from which to parse
   * and extract an {@link ExchangeCode}; must be {@literal 10-digits} or {@literal 7-digits}.
   * @return an {@link ExchangeCode} from the given {@link String phone number}.
   * @throws IllegalArgumentException if the given {@link String phone number} is not valid.
   */
  public static @NotNull ExchangeCode parse(@NotNull String phoneNumber) {

    String digits = StringUtils.getDigits(phoneNumber);

    return switch (digits.length()) {
      case 10 -> new ExchangeCode(digits.substring(3, 3 + REQUIRED_EXCHANGE_CODE_LENGTH));
      case 7 -> new ExchangeCode(digits.substring(0, REQUIRED_EXCHANGE_CODE_LENGTH));
      default -> throw newIllegalArgumentException("Phone Number [%s] must be 10-digits or 7-digits", phoneNumber);
    };
  }

  private final String number;

  /**
   * Constructs a new {@link ExchangeCode} initialized with the given, required {@link String number}.
   *
   * @param number {@link String} containing the {@literal 3-digit number} for this {@link ExchangeCode};
   * must not be {@literal null} or {@literal empty}; must be {@literal 3-digits}.
   * @throws IllegalArgumentException if the {@link String number} is {@literal null}, {@literal empty}
   * or is not {@literal 3-digits}.
   */
  public ExchangeCode(@NotNull String number) {

    int requiredLength = getRequiredLength();

    String resolvedNumber = StringUtils.getDigits(number);

    Assert.isTrue(resolvedNumber.length() == requiredLength,
      "ExchangeCode [%s] must be a %d-digit number", number, requiredLength);

    this.number = resolvedNumber;
  }

  /**
   * Gets the {@link String number} of this {@link ExchangeCode}.
   *
   * @return the {@link String number} of this {@link ExchangeCode}.
   */
  public @NotNull String getNumber() {
    return this.number;
  }

  /**
   * Get the required {@link Integer length} for an {@link ExchangeCode}.
   *
   * @return the required {@link Integer length} for an {@link ExchangeCode}.
   */
  protected int getRequiredLength() {
    return REQUIRED_EXCHANGE_CODE_LENGTH;
  }

  @Override
  @SuppressWarnings("all")
  protected Object clone() throws CloneNotSupportedException {
    return from(this);
  }

  @Override
  public int compareTo(@NotNull ExchangeCode that) {
    return this.getNumber().compareTo(that.getNumber());
  }

  @Override
  public boolean equals(@Nullable Object obj) {

    if (this == obj) {
      return true;
    }

    if (!(obj instanceof ExchangeCode that)) {
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
