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

import java.util.Optional;

import org.cp.domain.geo.enums.Country;
import org.cp.elements.lang.ObjectUtils;
import org.cp.elements.lang.annotation.NotNull;
import org.cp.elements.lang.annotation.Nullable;
import org.cp.elements.text.FormatUtils;

/**
 * Abstract base class and implementation of the {@link PhoneNumber} interface encapsulating functionality
 * common to all {@link PhoneNumber} implementations in the {@literal North American Numbering Plan (NANP)}.
 *
 * @author John Blum
 * @see org.cp.domain.contact.phone.model.AreaCode
 * @see org.cp.domain.contact.phone.model.ExchangeCode
 * @see org.cp.domain.contact.phone.model.Extension
 * @see org.cp.domain.contact.phone.model.LineNumber
 * @see org.cp.domain.contact.phone.model.PhoneNumber
 * @see org.cp.domain.geo.enums.Country
 * @since 0.1.0
 */
@SuppressWarnings("unused")
public abstract class AbstractPhoneNumber implements PhoneNumber {

  private static final String PHONE_NUMBER_TO_STRING =
    "{ @type = %1$s, areaCode = %2$s, exchangeCode = %3$s, number = %4$s, extension = %5$s, country = %6$s }";

  private Boolean textEnabled;

  private final AreaCode areaCode;

  private Country country;

  private final ExchangeCode exchangeCode;

  private Extension extension;

  private final LineNumber lineNumber;

  private Long id;

  private PhoneNumber.Type phoneNumberType;

  /**
   * Constructs a new instance of {@link AbstractPhoneNumber} initialized with the given,
   * required {@link AreaCode}, {@link ExchangeCode} and {@link LineNumber}.
   *
   * @param areaCode {@link AreaCode} of this {@link PhoneNumber}; must not be {@literal null}.
   * @param exchangeCode {@link ExchangeCode} of this {@link PhoneNumber}; must not be {@literal null}.
   * @param lineNumber {@link LineNumber} of this {@link PhoneNumber}; must not be {@literal null}.
   * @throws IllegalArgumentException if the given {@link AreaCode}, {@link ExchangeCode} or {@link LineNumber}
   * are {@literal null}.
   * @see org.cp.domain.contact.phone.model.AreaCode
   * @see org.cp.domain.contact.phone.model.ExchangeCode
   * @see org.cp.domain.contact.phone.model.LineNumber
   */
  public AbstractPhoneNumber(@NotNull AreaCode areaCode, @NotNull ExchangeCode exchangeCode,
      @NotNull LineNumber lineNumber) {

    this.areaCode = ObjectUtils.requireObject(areaCode, "AreaCode is required");
    this.exchangeCode = ObjectUtils.requireObject(exchangeCode, "ExchangeCode is required");
    this.lineNumber = ObjectUtils.requireObject(lineNumber, "LineNumber is required");
  }

  @Override
  public @NotNull AreaCode getAreaCode() {
    return this.areaCode;
  }

  @Override
  public Optional<Country> getCountry() {
    return Optional.ofNullable(this.country);
  }

  @Override
  public void setCountry(@Nullable Country country) {
    this.country = country;
  }

  @Override
  public @NotNull ExchangeCode getExchangeCode() {
    return this.exchangeCode;
  }

  @Override
  public Optional<Extension> getExtension() {
    return Optional.ofNullable(this.extension);
  }

  @Override
  public void setExtension(@Nullable Extension extension) {
    this.extension = extension;
  }

  @Override
  public @NotNull LineNumber getLineNumber() {
    return this.lineNumber;
  }

  @Override
  public @Nullable Long getId() {
    return this.id;
  }

  @Override
  public void setId(@Nullable Long id) {
    this.id = id;
  }

  @Override
  public boolean isTextEnabled() {
    return Boolean.TRUE.equals(this.textEnabled);
  }

  public void setTextEnabled(@Nullable Boolean textEnabled) {
    this.textEnabled = textEnabled;
  }

  @Override
  public Optional<PhoneNumber.Type> getType() {
    return Optional.ofNullable(this.phoneNumberType);
  }

  @Override
  public void setType(@Nullable PhoneNumber.Type phoneNumberType) {
    this.phoneNumberType = phoneNumberType;
  }

  @Override
  @SuppressWarnings("all")
  public Object clone() {
    return PhoneNumber.from(this);
  }

  @Override
  public boolean equals(@Nullable Object obj) {

    if (this == obj) {
      return true;
    }

    if (!(obj instanceof PhoneNumber that)) {
      return false;
    }

    return ObjectUtils.equals(this.getAreaCode(), that.getAreaCode())
      && ObjectUtils.equals(this.getExchangeCode(), that.getExchangeCode())
      && ObjectUtils.equals(this.getLineNumber(), that.getLineNumber())
      && ObjectUtils.equalsIgnoreNull(this.getExtension().orElse(null), that.getExtension().orElse(null))
      && ObjectUtils.equalsIgnoreNull(this.getCountry().orElse(null), that.getCountry().orElse(null));
  }

  @Override
  public int hashCode() {
    return ObjectUtils.hashCodeOf(getAreaCode(), getExchangeCode(), getLineNumber(),
      getExtension().orElse(null), getCountry().orElse(null));
  }

  @Override
  public @NotNull String toString() {

    return FormatUtils.format(PHONE_NUMBER_TO_STRING, getClass().getName(),
      getAreaCode(), getExchangeCode(), getLineNumber(),
      getExtension().map(ext -> "x".concat(ext.toString())).orElse(null),
      getCountry().orElse(null));
  }

  static final class GenericPhoneNumber extends AbstractPhoneNumber {

    GenericPhoneNumber(@NotNull AreaCode areaCode, @NotNull ExchangeCode exchangeCode, @NotNull LineNumber lineNumber) {
      super(areaCode, exchangeCode, lineNumber);
    }
  }
}
