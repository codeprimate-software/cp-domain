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
 * Abstract Data Type (ADT) modeling an {@link String extension} of a {@link PhoneNumber}.
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
public class Extension implements Cloneable, Comparable<Extension>, Serializable {

  /**
   * Factory method used to construct a new instance of {@link Extension} copied from the existing,
   * required {@link Extension}.
   *
   * @param extension {@link Extension} to copy; must not be {@literal null}.
   * @return a new {@link Extension} copied from the existing, required {@link Extension}.
   * @throws IllegalArgumentException if the {@link Extension} to copy is {@literal null}.
   */
  public static @NotNull Extension from(@NotNull Extension extension) {

    Assert.notNull(extension, "Extension to copy is required");

    return new Extension(extension.getNumber());
  }

  /**
   * Factory method used to construct a new instance of {@link Extension} from the given,
   * required {@link String number}.
   *
   * @param number {@link String} containing the {@literal number} of this {@link Extension};
   * must not be {@literal null} or {@literal empty}.
   * @throws IllegalArgumentException if the {@link String number} is {@literal null}, {@literal empty}
   * or not {@link Character#isDigit(char) digits}.
   * @return a new {@link Extension} initialized with the given, required {@link String number}.
   */
  public static @NotNull Extension of(@NotNull String number) {
    return new Extension(number);
  }

  private final String number;

  /**
   * Constructs a new instance of {@link Extension} initialized with the given, required {@link String number}.
   *
   * @param number {@link String} containing the {@literal number} of this {@link Extension};
   * must not be {@literal null} or {@literal empty}.
   * @throws IllegalArgumentException if the {@link String number} is {@literal null}, {@literal empty}
   * or not {@link Character#isDigit(char) digits}.
   */
  public Extension(@NotNull String number) {

    Assert.isTrue(StringUtils.isDigits(number), "Extension [%s] must contain digits only", number);

    this.number = number;
  }

  /**
   * Gets the {@link String number} of this {@link Extension}.
   *
   * @return the {@link String number} of this {@link Extension}.
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
  public int compareTo(@NotNull Extension that) {
    return this.getNumber().compareTo(that.getNumber());
  }

  @Override
  public boolean equals(Object obj) {

    if (this == obj) {
      return true;
    }

    if (!(obj instanceof Extension)) {
      return false;
    }

    Extension that = (Extension) obj;

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
