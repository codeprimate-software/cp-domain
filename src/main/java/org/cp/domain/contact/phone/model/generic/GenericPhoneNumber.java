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
package org.cp.domain.contact.phone.model.generic;

import java.util.Optional;

import org.cp.domain.contact.phone.model.AbstractPhoneNumber;
import org.cp.domain.contact.phone.model.AreaCode;
import org.cp.domain.contact.phone.model.ExchangeCode;
import org.cp.domain.contact.phone.model.FourDigitNumber;
import org.cp.domain.contact.phone.model.PhoneNumber;
import org.cp.domain.geo.enums.Country;
import org.cp.elements.lang.Assert;
import org.cp.elements.lang.annotation.NotNull;
import org.cp.elements.lang.annotation.Nullable;

/**
 * Default implementation of the {@link PhoneNumber} interface.
 *
 * @author John Blum
 * @see org.cp.domain.contact.phone.model.AbstractPhoneNumber
 * @see org.cp.domain.contact.phone.model.PhoneNumber
 * @since 0.1.0
 */
@SuppressWarnings("unused")
public class GenericPhoneNumber extends AbstractPhoneNumber {

  /**
   * Factory method used to construct a new instance of {@link GenericPhoneNumber} copied from the existing,
   * required {@link PhoneNumber}.
   *
   * @param phoneNumber {@link PhoneNumber} to copy; must not be {@literal null}.
   * @return a new {@link GenericPhoneNumber} copied from the given, required {@link PhoneNumber}.
   * @throws IllegalArgumentException if the given {@link PhoneNumber} to copy is {@literal null}.
   * @see org.cp.domain.contact.phone.model.PhoneNumber
   */
  public static @NotNull GenericPhoneNumber from(@NotNull PhoneNumber phoneNumber) {

    Assert.notNull(phoneNumber, "PhoneNumber to copy is required");

    GenericPhoneNumber genericPhoneNumber =
      of(phoneNumber.getAreaCode(), phoneNumber.getExchangeCode(), phoneNumber.getFourDigitNumber());

    genericPhoneNumber = phoneNumber.getCountry()
      .map(genericPhoneNumber::in)
      .orElse(genericPhoneNumber.inLocalCountry());

    phoneNumber.getExtension().ifPresent(genericPhoneNumber::setExtension);
    phoneNumber.getType().ifPresent(genericPhoneNumber::setType);

    return genericPhoneNumber;
  }

  /**
   * Factory method used to construct a new instance of {@link GenericPhoneNumber} initialized with the given,
   * required {@link AreaCode}, {@link ExchangeCode} and {@link FourDigitNumber}.
   *
   * @param areaCode {@link AreaCode} of this {@link PhoneNumber}; must not be {@literal null}.
   * @param exchangeCode {@link ExchangeCode} of this {@link PhoneNumber}; must not be {@literal null}.
   * @param number {@link FourDigitNumber} of this {@link PhoneNumber}; must not be {@literal null}.
   * @throws IllegalArgumentException if the given {@link AreaCode}, {@link ExchangeCode} or {@link FourDigitNumber}
   * are {@literal null}.
   * @return a new {@link GenericPhoneNumber} initialized with the given, required {@link AreaCode},
   * {@link ExchangeCode} and {@link FourDigitNumber}.
   * @see org.cp.domain.contact.phone.model.FourDigitNumber
   * @see org.cp.domain.contact.phone.model.ExchangeCode
   * @see org.cp.domain.contact.phone.model.AreaCode
   */
  public static @NotNull GenericPhoneNumber of(@NotNull AreaCode areaCode, @NotNull ExchangeCode exchangeCode,
      @NotNull FourDigitNumber number) {

    return new GenericPhoneNumber(areaCode, exchangeCode, number);
  }

  private Country country;

  /**
   * Constructs a new instance of {@link GenericPhoneNumber} initialized with the given,
   * required {@link AreaCode}, {@link ExchangeCode} and {@link FourDigitNumber}.
   *
   * @param areaCode {@link AreaCode} of this {@link PhoneNumber}; must not be {@literal null}.
   * @param exchangeCode {@link ExchangeCode} of this {@link PhoneNumber}; must not be {@literal null}.
   * @param number {@link FourDigitNumber} of this {@link PhoneNumber}; must not be {@literal null}.
   * @throws IllegalArgumentException if the given {@link AreaCode}, {@link ExchangeCode} or {@link FourDigitNumber}
   * are {@literal null}.
   * @see org.cp.domain.contact.phone.model.FourDigitNumber
   * @see org.cp.domain.contact.phone.model.ExchangeCode
   * @see org.cp.domain.contact.phone.model.AreaCode
   */
  public GenericPhoneNumber(@NotNull AreaCode areaCode, @NotNull ExchangeCode exchangeCode,
      @NotNull FourDigitNumber number) {

    super(areaCode, exchangeCode, number);
  }

  @Override
  public Optional<Country> getCountry() {
    return Optional.ofNullable(this.country);
  }

  /**
   * Builder method used to set the optional {@link Country} of this {@link PhoneNumber}.
   *
   * @param country optional {@link Country} of this {@link PhoneNumber}.
   * @return this {@link GenericPhoneNumber}.
   * @see org.cp.domain.geo.enums.Country
   */
  public @NotNull GenericPhoneNumber in(@Nullable Country country) {
    this.country = country;
    return this;
  }

  /**
   * Builder method used to set this {@link PhoneNumber} in the {@link Country#localCountry()}.
   *
   * @return this {@link GenericPhoneNumber}
   * @see org.cp.domain.geo.enums.Country#localCountry()
   * @see #in(Country)
   */
  public @NotNull GenericPhoneNumber inLocalCountry() {
    return in(Country.localCountry());
  }
}
