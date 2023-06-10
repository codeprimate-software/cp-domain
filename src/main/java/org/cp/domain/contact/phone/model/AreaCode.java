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
 * Abstract Data Type (ADT) modeling the {@literal area code} of a {@link PhoneNumber}.
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
public class AreaCode implements Cloneable, Comparable<AreaCode>, Serializable {

  protected static final int REQUIRED_AREA_CODE_LENGTH = 3;

  /**
   * Factory method used to construct a new {@link AreaCode} copied from an existing, required {@link AreaCode}.
   *
   * @param areaCode {@link AreaCode} to copy; must not be {@literal null}.
   * @return a new {@link AreaCode} copied from an existing, required {@link AreaCode}.
   * @throws IllegalArgumentException if the {@link AreaCode} to copy is {@literal null}.
   */
  public static @NotNull AreaCode from(@NotNull AreaCode areaCode) {

    Assert.notNull(areaCode, "AreaCode to copy is required");

    return new AreaCode(areaCode.getNumber());
  }

  /**
   * Factory method used to construct a new {@link AreaCode} from the given, required {@link String number}.
   *
   * @param number {@link String} containing the {@literal 3-digit number} for the {@link AreaCode};
   * must not be {@literal null} or {@literal empty}; must be {@literal 3-digits}.
   * @return a new {@link AreaCode} initialized with the given, required {@link String number}.
   * @throws IllegalArgumentException if the {@link String number} is {@literal null}, {@literal empty}
   * or is not {@literal 3-digits}.
   */
  public static @NotNull AreaCode of(@NotNull String number) {
    return new AreaCode(number);
  }

  /**
   * Parses and extracts an {@link AreaCode} from the given, required {@link String phone number}.
   *
   * @param phoneNumber {@link String} containing the {@literal phone number} to parse and extract the {@link AreaCode};
   * must be {@literal 10-digits}.
   * @return a new {@link AreaCode} based on the given {@link String phone number}.
   * @throws IllegalArgumentException if the {@link String phone number} is not {@literal 10-digits}.
   */
  public static @NotNull AreaCode parse(@NotNull String phoneNumber) {

    String digits = StringUtils.getDigits(phoneNumber);

    Assert.isTrue(digits.length() == 10,
      "Phone Number [%s] to parse the Area Code from must be 10-digits", phoneNumber);

    return new AreaCode(digits.substring(0, REQUIRED_AREA_CODE_LENGTH));
  }

  private final String number;

  /**
   * Constructs a new {@link AreaCode} initialized with the given, required {@link String number}.
   *
   * @param number {@link String} containing a {@literal 3-digit number} for this {@link AreaCode};
   * must not be {@literal null} or {@literal empty}; must be {@literal 3-digits}.
   * @throws IllegalArgumentException if the {@link String number} is {@literal null}, {@literal empty}
   * or is not {@literal 3-digits}.
   */
  public AreaCode(@NotNull String number) {

    int requiredLength = getRequiredLength();

    Assert.isTrue(StringUtils.getDigits(number).length() == requiredLength,
      "AreaCode [%s] must be a %d-digit number", number, requiredLength);

    this.number = number;
  }

  /**
   * Gets the {@link String number} of this {@link AreaCode}.
   *
   * @return the {@link String number} of this {@link AreaCode}.
   */
  public @NotNull String getNumber() {
    return this.number;
  }


  /**
   * Returns the {@link Integer required length} of this {@link AreaCode}.
   *
   * @return the {@link Integer required length} of this {@link AreaCode}.
   */
  protected int getRequiredLength() {
    return REQUIRED_AREA_CODE_LENGTH;
  }

  @Override
  @SuppressWarnings("all")
  protected Object clone() throws CloneNotSupportedException {
    return from(this);
  }

  @Override
  public int compareTo(@NotNull AreaCode that) {
    return this.getNumber().compareTo(that.getNumber());
  }

  @Override
  public boolean equals(@Nullable Object obj) {

    if (this == obj) {
      return true;
    }

    if (!(obj instanceof AreaCode that)) {
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
