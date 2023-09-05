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
package org.cp.domain.contact.phone.model.usa;

import static org.cp.elements.lang.RuntimeExceptionsFactory.newUnsupportedOperationException;

import java.util.Optional;

import org.cp.domain.contact.phone.model.AbstractPhoneNumber;
import org.cp.domain.contact.phone.model.AreaCode;
import org.cp.domain.contact.phone.model.ExchangeCode;
import org.cp.domain.contact.phone.model.LineNumber;
import org.cp.domain.contact.phone.model.PhoneNumber;
import org.cp.domain.contact.phone.model.usa.support.StateAreaCodesRepository;
import org.cp.domain.geo.enums.Country;
import org.cp.domain.geo.enums.State;
import org.cp.elements.lang.Assert;
import org.cp.elements.lang.annotation.NotNull;

/**
 * {@link Country#UNITED_STATES_OF_AMERICA} implementation of the {@link PhoneNumber} interface.
 *
 * @author John Blum
 * @see org.cp.domain.contact.phone.model.AbstractPhoneNumber
 * @see org.cp.domain.contact.phone.model.PhoneNumber
 * @since 0.1.0
 */
@SuppressWarnings("unused")
public class UnitedStatesPhoneNumber extends AbstractPhoneNumber {

  /**
   * Factory method used to construct a new {@link UnitedStatesPhoneNumber} copied from an existing,
   * required {@link PhoneNumber}.
   *
   * @param phoneNumber {@link PhoneNumber} to copy; must not be {@literal null}.
   * @return a new {@link UnitedStatesPhoneNumber} copied from the given, required {@link PhoneNumber}.
   * @throws IllegalArgumentException if the given {@link PhoneNumber} to copy is {@literal null}.
   * @see org.cp.domain.contact.phone.model.PhoneNumber
   * @see #of(AreaCode, ExchangeCode, LineNumber)
   */
  public static @NotNull UnitedStatesPhoneNumber from(@NotNull PhoneNumber phoneNumber) {

    Assert.notNull(phoneNumber, "PhoneNumber to copy is required");

    UnitedStatesPhoneNumber usPhoneNumber =
      of(phoneNumber.getAreaCode(), phoneNumber.getExchangeCode(), phoneNumber.getLineNumber());

    phoneNumber.getExtension().ifPresent(usPhoneNumber::setExtension);
    phoneNumber.getType().ifPresent(usPhoneNumber::setType);

    return usPhoneNumber;
  }

  /**
   * Factory method used to construct a new {@link UnitedStatesPhoneNumber} initialized with the given,
   * required {@link AreaCode}, {@link ExchangeCode} and {@link LineNumber}.
   *
   * @param areaCode {@link AreaCode} of this {@link PhoneNumber}; must not be {@literal null}.
   * @param exchangeCode {@link ExchangeCode} of this {@link PhoneNumber}; must not be {@literal null}.
   * @param lineNumber {@link LineNumber} of this {@link PhoneNumber}; must not be {@literal null}.
   * @throws IllegalArgumentException if the given {@link AreaCode}, {@link ExchangeCode} or {@link LineNumber}
   * are {@literal null}.
   * @return a new {@link UnitedStatesPhoneNumber} initialized with the given, required {@link AreaCode},
   * @see #UnitedStatesPhoneNumber(AreaCode, ExchangeCode, LineNumber)
   * {@link ExchangeCode} and {@link LineNumber}.
   * @see org.cp.domain.contact.phone.model.LineNumber
   * @see org.cp.domain.contact.phone.model.ExchangeCode
   * @see org.cp.domain.contact.phone.model.AreaCode
   */
  public static @NotNull UnitedStatesPhoneNumber of(@NotNull AreaCode areaCode, @NotNull ExchangeCode exchangeCode,
      @NotNull LineNumber lineNumber) {

    return new UnitedStatesPhoneNumber(areaCode, exchangeCode, lineNumber);
  }

  /**
   * Constructs a new {@link UnitedStatesPhoneNumber} initialized with the given, required {@link AreaCode},
   * {@link ExchangeCode} and {@link LineNumber}.
   *
   * @param areaCode {@link AreaCode} of this {@link PhoneNumber}; must not be {@literal null}.
   * @param exchangeCode {@link ExchangeCode} of this {@link PhoneNumber}; must not be {@literal null}.
   * @param lineNumber {@link LineNumber} of this {@link PhoneNumber}; must not be {@literal null}.
   * @throws IllegalArgumentException if the given {@link AreaCode}, {@link ExchangeCode} or {@link LineNumber}
   * are {@literal null}.
   * @see org.cp.domain.contact.phone.model.LineNumber
   * @see org.cp.domain.contact.phone.model.ExchangeCode
   * @see org.cp.domain.contact.phone.model.AreaCode
   */
  public UnitedStatesPhoneNumber(@NotNull AreaCode areaCode, @NotNull ExchangeCode exchangeCode,
      @NotNull LineNumber lineNumber) {

    super(areaCode, exchangeCode, lineNumber);
  }

  @Override
  public final Optional<Country> getCountry() {
    return Optional.of(Country.UNITED_STATES_OF_AMERICA);
  }

  @Override
  public final void setCountry(Country country) {
    throw newUnsupportedOperationException("Cannot set Country for a %s", getClass().getSimpleName());
  }

  /**
   * Get the {@link State} of this {@link PhoneNumber}.
   *
   * @return the {@link State} of this {@link PhoneNumber}.
   * @see org.cp.domain.geo.enums.State
   */
  public State getState() {
    return StateAreaCodesRepository.getInstance().findStateBy(getAreaCode());
  }
}
