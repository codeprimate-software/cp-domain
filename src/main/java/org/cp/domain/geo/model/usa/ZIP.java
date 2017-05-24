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

package org.cp.domain.geo.model.usa;

import static org.cp.elements.lang.RuntimeExceptionsFactory.newIllegalArgumentException;

import java.util.Optional;

import org.cp.domain.geo.model.PostalCode;
import org.cp.elements.lang.StringUtils;

/**
 * The {@link ZIP} class models the Zone Improvement Plan (ZIP) system of postal codes used by the United States
 * Postal Service since 1963 to assist in the sorting of mail.
 *
 * @author John Blum
 * @see java.io.Serializable
 * @see java.lang.Comparable
 * @see org.cp.domain.geo.model.PostalCode
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public class ZIP extends PostalCode {

  private final String code;

  private String plusFourExtension;

  public static ZIP of(String code) {
    return new ZIP(code);
  }

  public ZIP(String code) {
    super(code);

    this.code = Optional.ofNullable(code)
      .filter(StringUtils::hasText)
      .map(StringUtils::getDigits)
      .map(StringUtils::length)
      .filter(length -> length == 5 || length == 9)
      .map(length -> code)
      .orElseThrow(() -> newIllegalArgumentException("5 or 9 digit postal code number [%s] is required", code));
  }

  /**
   * Returns a 5 or 9 digit number of the postal code.
   *
   * @return a {@link String} containing the 5 or 9 digit number of the postal code.
   */
  public String getCode() {
    return this.code;
  }

  /**
   * Returns the {@link String number} of the {@link PostalCode}.
   *
   * @return the {@link String number} of the {@link PostalCode}.
   * @see java.lang.String
   */
  @Override
  public String getNumber() {
    return StringUtils.getDigits(getCode() + getPlusFourExtension().orElse(StringUtils.EMPTY_STRING));
  }

  /**
   * Returns the {@link Optional} 4 digit number extension of the postal code.
   *
   * @return an {@link Optional} {@link String} containing the 4 digit number extension of the postal code.
   * @see java.lang.String
   * @see java.util.Optional
   */
  public Optional<String> getPlusFourExtension() {
    return Optional.ofNullable(plusFourExtension).filter(StringUtils::hasText);
  }

  /**
   * Builder method to set the {@link ZIP} code four digit number extension.
   *
   * @param plusFourExtension {@link String} containing the {@link ZIP} code four digit number extension.
   * @return this {@link ZIP}.
   */
  public ZIP plusFour(String plusFourExtension) {
    this.plusFourExtension = plusFourExtension;
    return this;
  }

  /**
   * Returns a {@link String} representation of this {@link ZIP}.
   *
   * @return a {@link String} describing this {@link ZIP} code.
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return String.format("%1$s%2$s", getCode(),
      getPlusFourExtension().map(code -> String.format("-%s", code)).orElse(StringUtils.EMPTY_STRING));
  }
}
