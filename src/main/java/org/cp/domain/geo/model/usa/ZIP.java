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
import java.util.function.Predicate;

import org.cp.domain.geo.enums.Country;
import org.cp.domain.geo.enums.State;
import org.cp.domain.geo.model.PostalCode;
import org.cp.domain.geo.model.usa.support.StateZipCodesRepository;
import org.cp.elements.lang.Assert;
import org.cp.elements.lang.StringUtils;
import org.cp.elements.lang.annotation.NotNull;
import org.cp.elements.lang.annotation.NullSafe;
import org.cp.elements.lang.annotation.Nullable;

/**
 * Abstract Data Type (ADT) and {@link PostalCode} implementation modeling the {@literal Zone Improvement Plan (ZIP)}
 * system of {@literal postal codes} used by the {@link Country#UNITED_STATES_OF_AMERICA United States}
 * Postal Service since 1963 to assist in the sorting and routing of mail.
 *
 * @author John Blum
 * @see org.cp.domain.geo.enums.Country
 * @see org.cp.domain.geo.enums.State
 * @see org.cp.domain.geo.model.PostalCode
 * @see org.cp.domain.geo.model.usa.support.StateZipCodesRepository
 * @see <a href="https://www.50states.com/zipcodes/">All About ZIP Codes</a>
 * @since 0.1.0
 */
@SuppressWarnings("unused")
public class ZIP extends PostalCode {

  protected static final int STANDARD_POSTAL_CODE_LENGTH = 5;
  protected static final int EXTENSION_POSTAL_CODE_LENGTH = 9;

  protected static final String ZIP_TO_STRING = "%1$s%2$s";
  protected static final String ZIP_CODE_EXTENSION = "-%s";

  /**
   * Factory method used to construct a new instance of {@link ZIP} copied from the exiting,
   * required {@link PostalCode}.
   *
   * @param postalCode {@link PostalCode} to copy; must not be {@literal null}.
   * @return a new {@link ZIP} copied from the exiting {@link PostalCode}.
   * @throws IllegalArgumentException if the given {@link PostalCode} is {@literal null}.
   * @see org.cp.domain.geo.model.PostalCode
   * @see #of(String)
   */
  public static @NotNull ZIP from(@NotNull PostalCode postalCode) {

    Assert.notNull(postalCode, "PostalCode to copy is required");

    return postalCode instanceof ZIP ? (ZIP) postalCode
      : of(postalCode.getNumber());
  }

  /**
   * Factory method used to construct a new instance of {@link ZIP} initialized with the given,
   * required {@link String postal code}.
   *
   * @param code {@link String} containing the {@literal postal code} for the {@link ZIP};
   * must not be {@literal null} or {@literal empty}; must be either {@literal 5 digits} of {@literal 9 digits}.
   * @return a new {@link ZIP} initialized with the given {@link String postal code}.
   * @throws IllegalArgumentException if the given {@link String postal code} is not valid.
   */
  public static @NotNull ZIP of(@NotNull String code) {
    return new ZIP(code);
  }

  private final String code;

  private String fourDigitExtension;

  /**
   * Constructs a new {@link ZIP} initialized with the given, required {@link String postal code}.
   *
   * @param code {@link String} containing the {@literal postal code} defining this {@link ZIP};
   * must not be {@literal null} or {@literal empty}.
   * @throws IllegalArgumentException if the given {@link String postal code} is {@literal null} or {@literal empty},
   * or the {@link String postal code} is not the standard format of {@literal 5} or {@literal 9} digits.
   */
  public ZIP(@NotNull String code) {

    super(code);

    String postalCode = Optional.ofNullable(code)
      .filter(postalCodePredicate())
      .map(StringUtils::getDigits)
      .orElseThrow(() -> newIllegalArgumentException("5 or 9 digit postal code is required; but was [%s]", code));

    this.code = postalCode.substring(0, STANDARD_POSTAL_CODE_LENGTH);
    this.fourDigitExtension = postalCode.substring(STANDARD_POSTAL_CODE_LENGTH);
  }

  @NullSafe
  private @NotNull Predicate<String> postalCodePredicate() {

    return postalCode -> {
      int length = StringUtils.length(StringUtils.getDigits(postalCode));
      return length == STANDARD_POSTAL_CODE_LENGTH || length == EXTENSION_POSTAL_CODE_LENGTH;
    };
  }

  /**
   * Returns the {@link String 4 digit postal code}, or possibly the {@link String 9 digit postal code},
   * of this {@link ZIP}.
   *
   * @return the {@link String 4 digit postal code}, or possibly the {@link String 9 digit postal code},
   * of this {@link ZIP}.
   */
  public @NotNull String getCode() {
    return this.code;
  }

  /**
   * Returns the {@link Country#UNITED_STATES_OF_AMERICA}.
   *
   * @return the {@link Country#UNITED_STATES_OF_AMERICA}.
   * @see org.cp.domain.geo.enums.Country#UNITED_STATES_OF_AMERICA
   * @see java.util.Optional
   */
  @Override
  public final Optional<Country> getCountry() {
    return Optional.of(Country.UNITED_STATES_OF_AMERICA);
  }

  /**
   * Returns the {@link Optional} {@link String 4 digit postal extension} of this {@link ZIP}.
   *
   * @return the {@link Optional} {@link String 4 digit postal extension} of this {@link ZIP}.
   * @see java.util.Optional
   * @see java.lang.String
   */
  public Optional<String> getFourDigitExtension() {

    return Optional.ofNullable(this.fourDigitExtension)
      .filter(StringUtils::hasText);
  }

  /**
   * Returns the complete, {@link String 9 digit postal code} of this {@link ZIP}.
   *
   * @return the complete, {@link String 9 digit postal code} of this {@link ZIP}.
   * @see #codePlusFourDigitExtension()
   * @see java.lang.String
   * @see #getCode()
   */
  @Override
  public @NotNull String getNumber() {
    String number = codePlusFourDigitExtension();
    return StringUtils.truncate(StringUtils.getDigits(number), EXTENSION_POSTAL_CODE_LENGTH);
  }

  /**
   * Gets the {@link State} in which this {@link ZIP} is assigned.
   *
   * @return the {@link State} in which this {@link ZIP} is assigned.
   * @throws IllegalArgumentException if this {@link ZIP} is not assigned to a {@link State}.
   * @see org.cp.domain.geo.enums.State
   * @see #getCode()
   */
  public @NotNull State getState() {
    return StateZipCodesRepository.getInstance().findBy(this);
  }

  private @NotNull String codePlusFourDigitExtension() {
    return getCode() + resolveFourDigitExtension();
  }

  /**
   * Builder method used to set the {@link ZIP} code {@link String four-digit postal extension}.
   *
   * @param fourDigitExtension {@link String} containing the {@link ZIP} code
   * {@literal four-digit postal extension}.
   * @return this {@link ZIP}.
   */
  public @NotNull ZIP plusFour(@Nullable String fourDigitExtension) {
    this.fourDigitExtension = fourDigitExtension;
    return this;
  }

  private @NotNull String resolveFourDigitExtension() {
    return getFourDigitExtension().orElse(StringUtils.EMPTY_STRING);
  }

  /**
   * Returns a {@link String} representation of this {@link ZIP} code.
   *
   * @return a {@link String} describing this {@link ZIP} code.
   * @see java.lang.Object#toString()
   * @see #getFourDigitExtension()
   * @see #getCode()
   */
  @Override
  public @NotNull String toString() {

    String fourDigitExtension = getFourDigitExtension()
      .map(extension -> String.format(ZIP_CODE_EXTENSION, extension))
      .orElse(StringUtils.EMPTY_STRING);

    return String.format(ZIP_TO_STRING, getCode(), fourDigitExtension);
  }
}
