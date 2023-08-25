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
package org.cp.domain.contact.phone.model.usa;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.cp.elements.lang.ThrowableAssertions.assertThatUnsupportedOperationException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import org.cp.domain.contact.phone.model.AreaCode;
import org.cp.domain.contact.phone.model.ExchangeCode;
import org.cp.domain.contact.phone.model.Extension;
import org.cp.domain.contact.phone.model.LineNumber;
import org.cp.domain.contact.phone.model.PhoneNumber;
import org.cp.domain.contact.phone.model.usa.support.StateAreaCodesRepository;
import org.cp.domain.geo.enums.Country;
import org.cp.domain.geo.enums.State;
import org.cp.elements.lang.ThrowableOperation;

/**
 * Unit Tests for {@link UnitedStatesPhoneNumber}.
 *
 * @author John Blum
 * @see org.junit.jupiter.api.Test
 * @see org.mockito.Mockito
 * @see org.cp.domain.contact.phone.model.usa.UnitedStatesPhoneNumber
 * @since 0.1.0
 */
class UnitedStatesPhoneNumberUnitTests {

  @Test
  void fromPhoneNumber() {

    AreaCode mockAreaCode = mock(AreaCode.class);
    ExchangeCode mockExchangeCode = mock(ExchangeCode.class);
    LineNumber mockLineNumber = mock(LineNumber.class);

    Country country = Country.UNITED_STATES_OF_AMERICA.equals(Country.localCountry()) ? Country.GERMANY
      : Country.localCountry();

    PhoneNumber mockPhoneNumber = mock(PhoneNumber.class);

    doReturn(mockAreaCode).when(mockPhoneNumber).getAreaCode();
    doReturn(mockExchangeCode).when(mockPhoneNumber).getExchangeCode();
    doReturn(mockLineNumber).when(mockPhoneNumber).getLineNumber();
    doReturn(Optional.of(country)).when(mockPhoneNumber).getCountry();

    UnitedStatesPhoneNumber phoneNumber = UnitedStatesPhoneNumber.from(mockPhoneNumber);

    assertThat(phoneNumber).isNotNull();
    assertThat(phoneNumber.getAreaCode()).isEqualTo(mockAreaCode);
    assertThat(phoneNumber.getExchangeCode()).isEqualTo(mockExchangeCode);
    assertThat(phoneNumber.getLineNumber()).isEqualTo(mockLineNumber);
    assertThat(phoneNumber.getCountry().orElse(null)).isEqualTo(Country.UNITED_STATES_OF_AMERICA);
    assertThat(phoneNumber.getExtension()).isNotPresent();
    assertThat(phoneNumber.getType()).isNotPresent();

    verify(mockPhoneNumber, times(1)).getAreaCode();
    verify(mockPhoneNumber, times(1)).getExchangeCode();
    verify(mockPhoneNumber, times(1)).getLineNumber();
    verify(mockPhoneNumber, times(1)).getExtension();
    verify(mockPhoneNumber, times(1)).getType();
    verify(mockPhoneNumber, never()).getCountry();

    verifyNoInteractions(mockAreaCode, mockExchangeCode, mockLineNumber);
    verifyNoMoreInteractions(mockPhoneNumber);
  }

  @Test
  void fromPhoneNumberWithExtensionAndType() {

    AreaCode mockAreaCode = mock(AreaCode.class);
    ExchangeCode mockExchangeCode = mock(ExchangeCode.class);
    Extension mockExtension = mock(Extension.class);
    LineNumber mockLineNumber = mock(LineNumber.class);

    Country country = Country.UNITED_STATES_OF_AMERICA.equals(Country.localCountry()) ? Country.GERMANY
      : Country.localCountry();

    PhoneNumber mockPhoneNumber = mock(PhoneNumber.class);

    doReturn(mockAreaCode).when(mockPhoneNumber).getAreaCode();
    doReturn(mockExchangeCode).when(mockPhoneNumber).getExchangeCode();
    doReturn(mockLineNumber).when(mockPhoneNumber).getLineNumber();
    doReturn(Optional.of(country)).when(mockPhoneNumber).getCountry();
    doReturn(Optional.of(mockExtension)).when(mockPhoneNumber).getExtension();
    doReturn(Optional.of(PhoneNumber.Type.CELL)).when(mockPhoneNumber).getType();

    UnitedStatesPhoneNumber phoneNumber = UnitedStatesPhoneNumber.from(mockPhoneNumber);

    assertThat(phoneNumber).isNotNull();
    assertThat(phoneNumber.getAreaCode()).isEqualTo(mockAreaCode);
    assertThat(phoneNumber.getExchangeCode()).isEqualTo(mockExchangeCode);
    assertThat(phoneNumber.getLineNumber()).isEqualTo(mockLineNumber);
    assertThat(phoneNumber.getCountry().orElse(null)).isEqualTo(Country.UNITED_STATES_OF_AMERICA);
    assertThat(phoneNumber.getExtension().orElse(null)).isEqualTo(mockExtension);
    assertThat(phoneNumber.isCell()).isTrue();

    verify(mockPhoneNumber, times(1)).getAreaCode();
    verify(mockPhoneNumber, times(1)).getExchangeCode();
    verify(mockPhoneNumber, times(1)).getLineNumber();
    verify(mockPhoneNumber, times(1)).getExtension();
    verify(mockPhoneNumber, times(1)).getType();
    verify(mockPhoneNumber, never()).getCountry();

    verifyNoInteractions(mockAreaCode, mockExchangeCode, mockExtension, mockLineNumber);
    verifyNoMoreInteractions(mockPhoneNumber);
  }

  @Test
  void fromNullPhoneNumberThrowsIllegalArgumentException() {

    assertThatIllegalArgumentException()
      .isThrownBy(() -> UnitedStatesPhoneNumber.from(null))
      .withMessage("PhoneNumber to copy is required")
      .withNoCause();
  }

  @Test
  void ofAreaCodeExchangeCodeLineNumber() {

    AreaCode mockAreaCode = mock(AreaCode.class);
    ExchangeCode mockExchangeCode = mock(ExchangeCode.class);
    LineNumber mockLineNumber = mock(LineNumber.class);

    UnitedStatesPhoneNumber phoneNumber = UnitedStatesPhoneNumber.of(mockAreaCode, mockExchangeCode, mockLineNumber);

    assertThat(phoneNumber).isNotNull();
    assertThat(phoneNumber.getAreaCode()).isEqualTo(mockAreaCode);
    assertThat(phoneNumber.getExchangeCode()).isEqualTo(mockExchangeCode);
    assertThat(phoneNumber.getLineNumber()).isEqualTo(mockLineNumber);
    assertThat(phoneNumber.getCountry().orElse(null)).isEqualTo(Country.UNITED_STATES_OF_AMERICA);
    assertThat(phoneNumber.getExtension()).isNotPresent();
    assertThat(phoneNumber.getType()).isNotPresent();

    verifyNoInteractions(mockAreaCode, mockExchangeCode, mockLineNumber);
  }

  @Test
  void constructNewUnitedStatesPhoneNumber() {

    AreaCode mockAreaCode = mock(AreaCode.class);
    ExchangeCode mockExchangeCode = mock(ExchangeCode.class);
    LineNumber mockLineNumber = mock(LineNumber.class);

    UnitedStatesPhoneNumber phoneNumber = new UnitedStatesPhoneNumber(mockAreaCode, mockExchangeCode, mockLineNumber)
      .asVoip();

    assertThat(phoneNumber).isNotNull();
    assertThat(phoneNumber.getAreaCode()).isEqualTo(mockAreaCode);
    assertThat(phoneNumber.getExchangeCode()).isEqualTo(mockExchangeCode);
    assertThat(phoneNumber.getLineNumber()).isEqualTo(mockLineNumber);
    assertThat(phoneNumber.getCountry().orElse(null)).isEqualTo(Country.UNITED_STATES_OF_AMERICA);
    assertThat(phoneNumber.getExtension()).isNotPresent();
    assertThat(phoneNumber.isVoip()).isTrue();

    verifyNoInteractions(mockAreaCode, mockExchangeCode, mockLineNumber);
  }

  @Test
  void setCountryForUnitedStatesPhoneNumber() {

    UnitedStatesPhoneNumber phoneNumber = mock(UnitedStatesPhoneNumber.class);

    doCallRealMethod().when(phoneNumber).setCountry(any());

    assertThatUnsupportedOperationException()
      .isThrownBy(ThrowableOperation.fromRunnable(() -> phoneNumber.setCountry(Country.CANADA)))
      .havingMessage("Cannot set the Country for a UnitedStatesPhoneNumber")
      .withNoCause();
  }

  @Test
  void getPhoneNumbersStateIsCorrect() {

    ExchangeCode mockExchangeCode = mock(ExchangeCode.class);
    LineNumber mockLineNumber= mock(LineNumber.class);

    Arrays.stream(State.values()).forEach(state ->
      assertThat(UnitedStatesPhoneNumber.of(areaCodeForState(state), mockExchangeCode, mockLineNumber).getState())
        .isEqualTo(state));

    verifyNoInteractions(mockExchangeCode, mockLineNumber);
  }

  private AreaCode areaCodeForState(State state) {
    return StateAreaCodesRepository.getInstance().findAreaCodesBy(state).iterator().next();
  }
}
