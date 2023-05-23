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

/**
 * Abstract Data Type (ADT) modeling the {@literal 3-digit exchange code} of a {@link PhoneNumber}.
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
public class ExchangeCode implements Cloneable, Comparable<ExchangeCode>, Serializable {

  private static final int REQUIRED_EXCHANGE_CODE_LENGTH = 3;

  /**
   * Factory method used to construct a new instance of {@link ExchangeCode} copied from the existing,
   * required {@link ExchangeCode}.
   *
   * @param exchangeCode {@link ExchangeCode} to copy; must not be {@literal null}.
   * @return a new {@link ExchangeCode} copied from the existing, required {@link ExchangeCode}.
   * @throws IllegalArgumentException if the {@link ExchangeCode} to copy is {@literal null}.
   */
  public static @NotNull ExchangeCode from(@NotNull ExchangeCode exchangeCode) {

    Assert.notNull(exchangeCode, "ExchangeCode to copy is required");

    return new ExchangeCode(exchangeCode.getThreeDigitNumber());
  }

  /**
   * Factory method used to construct a new instance of {@link ExchangeCode} from the given,
   * required {@link String 3-digit number}.
   *
   * @param threeDigitNumber {@link String} containing the {@literal 3-digit number} of this {@link ExchangeCode};
   * must not be {@literal null} or {@literal empty}; must be {@literal 3-digits}.
   * @throws IllegalArgumentException if the {@link String 3-digit number} is {@literal null}, {@literal empty}
   * or is not (only) {@literal 3-digits}.
   * @return a new {@link ExchangeCode} initialized with the given, required {@link String 3-digit number}.
   */
  public static @NotNull ExchangeCode of(@NotNull String threeDigitNumber) {
    return new ExchangeCode(threeDigitNumber);
  }

  private final String threeDigitNumber;

  /**
   * Constructs a new instance of {@link ExchangeCode} initialized with the given,
   * required {@link String 3-digit number}.
   *
   * @param threeDigitNumber {@link String} containing the {@literal 3-digit number} of this {@link ExchangeCode};
   * must not be {@literal null} or {@literal empty}; must be {@literal 3-digits}.
   * @throws IllegalArgumentException if the {@link String 3-digit number} is {@literal null}, {@literal empty}
   * or is not (only) {@literal 3-digits}.
   */
  public ExchangeCode(@NotNull String threeDigitNumber) {

    Assert.isTrue(StringUtils.getDigits(threeDigitNumber).length() == REQUIRED_EXCHANGE_CODE_LENGTH,
      "ExchangeCode [%s] must be a %d-digit number", threeDigitNumber, REQUIRED_EXCHANGE_CODE_LENGTH);

    this.threeDigitNumber = threeDigitNumber;
  }

  /**
   * Gets the {@link String 3-digit number} of this {@link ExchangeCode}.
   *
   * @return the {@link String 3-digit number} of this {@link ExchangeCode}.
   */
  public @NotNull String getThreeDigitNumber() {
    return this.threeDigitNumber;
  }

  @Override
  @SuppressWarnings("all")
  protected Object clone() throws CloneNotSupportedException {
    return from(this);
  }

  @Override
  public int compareTo(@NotNull ExchangeCode that) {
    return this.getThreeDigitNumber().compareTo(that.getThreeDigitNumber());
  }

  @Override
  public boolean equals(Object obj) {

    if (this == obj) {
      return true;
    }

    if (!(obj instanceof ExchangeCode)) {
      return false;
    }

    ExchangeCode that = (ExchangeCode) obj;

    return ObjectUtils.equals(this.getThreeDigitNumber(), that.getThreeDigitNumber());
  }

  @Override
  public int hashCode() {
    return ObjectUtils.hashCodeOf(this.getThreeDigitNumber());
  }

  @Override
  public String toString() {
    return getThreeDigitNumber();
  }
}
