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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import org.cp.domain.geo.enums.Country;

/**
 * Unit Tests for {@link PhoneNumber}.
 *
 * @author John Blum
 * @see org.junit.jupiter.api.Test
 * @see org.cp.domain.contact.phone.model.PhoneNumber
 * @since 0.1.0
 */
public class PhoneNumberUnitTests {

  @Test
  public void copyFromPhoneNumber() {

    AreaCode mockAreaCode = mock(AreaCode.class);
    ExchangeCode mockExchangeCode = mock(ExchangeCode.class);
    FourDigitNumber mockFourDigitNumber = mock(FourDigitNumber.class);

    PhoneNumber mockPhoneNumber = mock(PhoneNumber.class);

    doReturn(mockAreaCode).when(mockPhoneNumber).getAreaCode();
    doReturn(mockExchangeCode).when(mockPhoneNumber).getExchangeCode();
    doReturn(mockFourDigitNumber).when(mockPhoneNumber).getFourDigitNumber();

    PhoneNumber copy = PhoneNumber.from(mockPhoneNumber);

    assertThat(copy).isNotNull();
    assertThat(copy.getAreaCode()).isEqualTo(mockAreaCode);
    assertThat(copy.getExchangeCode()).isEqualTo(mockExchangeCode);
    assertThat(copy.getFourDigitNumber()).isEqualTo(mockFourDigitNumber);
    assertThat(copy.getCountry()).isNotPresent();
    assertThat(copy.getExtension()).isNotPresent();
    assertThat(copy.getType()).isNotPresent();

    verifyNoInteractions(mockAreaCode, mockExchangeCode, mockFourDigitNumber);
  }

  @Test
  public void copyFromPhoneNumberWithNonNullCountryExtensionAndType() {

    AreaCode mockAreaCode = mock(AreaCode.class);
    ExchangeCode mockExchangeCode = mock(ExchangeCode.class);
    FourDigitNumber mockFourDigitNumber = mock(FourDigitNumber.class);
    Extension mockExtension = mock(Extension.class);

    PhoneNumber mockPhoneNumber = mock(PhoneNumber.class);

    doNothing().when(mockPhoneNumber).setCountry(any());
    doReturn(mockAreaCode).when(mockPhoneNumber).getAreaCode();
    doReturn(mockExchangeCode).when(mockPhoneNumber).getExchangeCode();
    doReturn(mockFourDigitNumber).when(mockPhoneNumber).getFourDigitNumber();
    doReturn(Optional.ofNullable(mockExtension)).when(mockPhoneNumber).getExtension();
    doReturn(Optional.of(Country.UNITED_STATES_OF_AMERICA)).when(mockPhoneNumber).getCountry();

    PhoneNumber copy = PhoneNumber.from(mockPhoneNumber);

    assertThat(copy).isNotNull();
    assertThat(copy.getAreaCode()).isEqualTo(mockAreaCode);
    assertThat(copy.getExchangeCode()).isEqualTo(mockExchangeCode);
    assertThat(copy.getFourDigitNumber()).isEqualTo(mockFourDigitNumber);
    assertThat(copy.getExtension().orElse(null)).isEqualTo(mockExtension);
    assertThat(copy.getCountry().orElse(null)).isEqualTo(Country.UNITED_STATES_OF_AMERICA);
    assertThat(copy.getType()).isNotPresent();

    verifyNoInteractions(mockAreaCode, mockExchangeCode, mockFourDigitNumber);
  }

  @Test
  public void copyFromNullThrowsIllegalArgumentException() {

    assertThatIllegalArgumentException()
      .isThrownBy(() -> PhoneNumber.from(null))
      .withMessage("PhoneNumber to copy is required")
      .withNoCause();
  }

  @Test
  public void ofAreaCodeExchangeCodeAndFourDigitNumber() {

    AreaCode mockAreaCode = mock(AreaCode.class);
    ExchangeCode mockExchangeCode = mock(ExchangeCode.class);
    FourDigitNumber mockFourDigitNumber = mock(FourDigitNumber.class);

    PhoneNumber phoneNumber = PhoneNumber.of(mockAreaCode, mockExchangeCode, mockFourDigitNumber);

    assertThat(phoneNumber).isNotNull();
    assertThat(phoneNumber.getAreaCode()).isEqualTo(mockAreaCode);
    assertThat(phoneNumber.getExchangeCode()).isEqualTo(mockExchangeCode);
    assertThat(phoneNumber.getFourDigitNumber()).isEqualTo(mockFourDigitNumber);
    assertThat(phoneNumber.getCountry()).isNotPresent();
    assertThat(phoneNumber.getExtension()).isNotPresent();
    assertThat(phoneNumber.getType()).isNotPresent();

    verifyNoInteractions(mockAreaCode, mockExchangeCode, mockFourDigitNumber);
  }

  @Test
  public void ofNullAreaCode() {

    ExchangeCode mockExchangeCode = mock(ExchangeCode.class);
    FourDigitNumber mockFourDigitNumber = mock(FourDigitNumber.class);

    assertThatIllegalArgumentException()
      .isThrownBy(() -> PhoneNumber.of(null, mockExchangeCode, mockFourDigitNumber))
      .withMessage("AreaCode is required")
      .withNoCause();

    verifyNoInteractions(mockExchangeCode, mockFourDigitNumber);
  }

  @Test
  public void ofNullExchangeCode() {

    AreaCode mockAreaCode = mock(AreaCode.class);
    FourDigitNumber mockFourDigitNumber = mock(FourDigitNumber.class);

    assertThatIllegalArgumentException()
      .isThrownBy(() -> PhoneNumber.of(mockAreaCode, null, mockFourDigitNumber))
      .withMessage("ExchangeCode is required")
      .withNoCause();

    verifyNoInteractions(mockAreaCode, mockFourDigitNumber);
  }

  @Test
  public void ofNullFourDigitNumber() {

    AreaCode mockAreaCode = mock(AreaCode.class);
    ExchangeCode mockExchangeCode = mock(ExchangeCode.class);

    assertThatIllegalArgumentException()
      .isThrownBy(() -> PhoneNumber.of(mockAreaCode, mockExchangeCode, null))
      .withMessage("FourDigitNumber is required")
      .withNoCause();

    verifyNoInteractions(mockAreaCode, mockExchangeCode);
  }

  @Test
  public void isRoamingInsideLocalCountryIsFalse() {

    PhoneNumber mockPhoneNumber = mock(PhoneNumber.class);

    doCallRealMethod().when(mockPhoneNumber).isRoaming();
    doReturn(Optional.of(Country.localCountry())).when(mockPhoneNumber).getCountry();

    assertThat(mockPhoneNumber.isRoaming()).isFalse();

    verify(mockPhoneNumber, times(1)).isRoaming();
    verify(mockPhoneNumber, times(1)).getCountry();
    verifyNoMoreInteractions(mockPhoneNumber);
  }

  @Test
  public void isRoamingOutsideLocalCountryIsTrue() {

    Optional<Country> country = Arrays.stream(Country.values())
      .filter(it -> !Country.localCountry().equals(it))
      .findFirst();

    PhoneNumber mockPhoneNumber = mock(PhoneNumber.class);

    doCallRealMethod().when(mockPhoneNumber).isRoaming();
    doReturn(country).when(mockPhoneNumber).getCountry();

    assertThat(mockPhoneNumber.isRoaming()).isTrue();

    verify(mockPhoneNumber, times(1)).isRoaming();
    verify(mockPhoneNumber, times(1)).getCountry();
    verifyNoMoreInteractions(mockPhoneNumber);
  }

  @Test
  public void isCellPhoneNumberWhenCell() {

    PhoneNumber mockPhoneNumber = mock(PhoneNumber.class);

    doReturn(Optional.of(PhoneNumber.Type.CELL)).when(mockPhoneNumber).getType();
    doCallRealMethod().when(mockPhoneNumber).isCell();

    assertThat(mockPhoneNumber.isCell()).isTrue();

    verify(mockPhoneNumber, times(1)).getType();
    verify(mockPhoneNumber, times(1)).isCell();
    verifyNoMoreInteractions(mockPhoneNumber);
  }

  @Test
  public void isCellPhoneNumberWhenNotCell() {

    PhoneNumber mockPhoneNumber = mock(PhoneNumber.class);

    doCallRealMethod().when(mockPhoneNumber).isCell();

    Arrays.stream(PhoneNumber.Type.values())
      .filter(phoneNumberType -> !PhoneNumber.Type.CELL.equals(phoneNumberType))
      .forEach(phoneNumberType -> {
        doReturn(Optional.of(phoneNumberType)).when(mockPhoneNumber).getType();
        assertThat(mockPhoneNumber.isCell()).isFalse();
      });

    int callCount = PhoneNumber.Type.values().length - 1;

    verify(mockPhoneNumber, times(callCount)).getType();
    verify(mockPhoneNumber, times(callCount)).isCell();
    verifyNoMoreInteractions(mockPhoneNumber);
  }

  @Test
  public void isHomePhoneNumberWhenHome() {

    PhoneNumber mockPhoneNumber = mock(PhoneNumber.class);

    doReturn(Optional.of(PhoneNumber.Type.HOME)).when(mockPhoneNumber).getType();
    doCallRealMethod().when(mockPhoneNumber).isHome();

    assertThat(mockPhoneNumber.isHome()).isTrue();

    verify(mockPhoneNumber, times(1)).getType();
    verify(mockPhoneNumber, times(1)).isHome();
    verifyNoMoreInteractions(mockPhoneNumber);
  }

  @Test
  public void isHomePhoneNumberWhenNotHome() {

    PhoneNumber mockPhoneNumber = mock(PhoneNumber.class);

    doCallRealMethod().when(mockPhoneNumber).isHome();

    Arrays.stream(PhoneNumber.Type.values())
      .filter(phoneNumberType -> !PhoneNumber.Type.HOME.equals(phoneNumberType))
      .forEach(phoneNumberType -> {
        doReturn(Optional.of(phoneNumberType)).when(mockPhoneNumber).getType();
        assertThat(mockPhoneNumber.isHome()).isFalse();
      });

    int callCount = PhoneNumber.Type.values().length - 1;

    verify(mockPhoneNumber, times(callCount)).getType();
    verify(mockPhoneNumber, times(callCount)).isHome();
    verifyNoMoreInteractions(mockPhoneNumber);
  }

  @Test
  public void isUnknownPhoneNumberWhenKnown() {

    PhoneNumber mockPhoneNumber = mock(PhoneNumber.class);

    doCallRealMethod().when(mockPhoneNumber).isUnknown();

    Arrays.stream(PhoneNumber.Type.values())
      .filter(phoneNumberType -> !PhoneNumber.Type.UNKNOWN.equals(phoneNumberType))
      .forEach(phoneNumberType -> {
        doReturn(Optional.of(phoneNumberType)).when(mockPhoneNumber).getType();
        assertThat(mockPhoneNumber.isUnknown()).isFalse();
      });

    int callCount = PhoneNumber.Type.values().length - 1;

    verify(mockPhoneNumber, times(callCount)).getType();
    verify(mockPhoneNumber, times(callCount)).isUnknown();
    verifyNoMoreInteractions(mockPhoneNumber);
  }

  @Test
  public void isUnknownPhoneNumberWhenUnknown() {

    PhoneNumber mockPhoneNumber = mock(PhoneNumber.class);

    doReturn(Optional.of(PhoneNumber.Type.UNKNOWN)).when(mockPhoneNumber).getType();
    doCallRealMethod().when(mockPhoneNumber).isUnknown();

    assertThat(mockPhoneNumber.isUnknown()).isTrue();

    verify(mockPhoneNumber, times(1)).getType();
    verify(mockPhoneNumber, times(1)).isUnknown();
    verifyNoMoreInteractions(mockPhoneNumber);
  }

  @Test
  public void isUnknownPhoneNumberWhenUnset() {

    PhoneNumber mockPhoneNumber = mock(PhoneNumber.class);

    doReturn(Optional.empty()).when(mockPhoneNumber).getType();
    doCallRealMethod().when(mockPhoneNumber).isUnknown();

    assertThat(mockPhoneNumber.isUnknown()).isTrue();

    verify(mockPhoneNumber, times(1)).getType();
    verify(mockPhoneNumber, times(1)).isUnknown();
    verifyNoMoreInteractions(mockPhoneNumber);
  }

  @Test
  public void isVoipPhoneNumberWhenVoip() {

    PhoneNumber mockPhoneNumber = mock(PhoneNumber.class);

    doReturn(Optional.of(PhoneNumber.Type.VOIP)).when(mockPhoneNumber).getType();
    doCallRealMethod().when(mockPhoneNumber).isVOIP();

    assertThat(mockPhoneNumber.isVOIP()).isTrue();

    verify(mockPhoneNumber, times(1)).getType();
    verify(mockPhoneNumber, times(1)).isVOIP();
    verifyNoMoreInteractions(mockPhoneNumber);
  }

  @Test
  public void isVoipPhoneNumberWhenNotVoip() {

    PhoneNumber mockPhoneNumber = mock(PhoneNumber.class);

    doCallRealMethod().when(mockPhoneNumber).isVOIP();

    Arrays.stream(PhoneNumber.Type.values())
      .filter(phoneNumberType -> !PhoneNumber.Type.VOIP.equals(phoneNumberType))
      .forEach(phoneNumberType -> {
        doReturn(Optional.of(phoneNumberType)).when(mockPhoneNumber).getType();
        assertThat(mockPhoneNumber.isVOIP()).isFalse();
      });

    int callCount = PhoneNumber.Type.values().length - 1;

    verify(mockPhoneNumber, times(callCount)).getType();
    verify(mockPhoneNumber, times(callCount)).isVOIP();
    verifyNoMoreInteractions(mockPhoneNumber);
  }

  @Test
  public void isWorkPhoneNumberWhenWork() {

    PhoneNumber mockPhoneNumber = mock(PhoneNumber.class);

    doReturn(Optional.of(PhoneNumber.Type.WORK)).when(mockPhoneNumber).getType();
    doCallRealMethod().when(mockPhoneNumber).isWork();

    assertThat(mockPhoneNumber.isWork()).isTrue();

    verify(mockPhoneNumber, times(1)).getType();
    verify(mockPhoneNumber, times(1)).isWork();
    verifyNoMoreInteractions(mockPhoneNumber);
  }

  @Test
  public void isWorkPhoneNumberWhenNotWork() {

    PhoneNumber mockPhoneNumber = mock(PhoneNumber.class);

    doCallRealMethod().when(mockPhoneNumber).isWork();

    Arrays.stream(PhoneNumber.Type.values())
      .filter(phoneNumberType -> !PhoneNumber.Type.WORK.equals(phoneNumberType))
      .forEach(phoneNumberType -> {
        doReturn(Optional.of(phoneNumberType)).when(mockPhoneNumber).getType();
        assertThat(mockPhoneNumber.isWork()).isFalse();
      });

    int callCount = PhoneNumber.Type.values().length - 1;

    verify(mockPhoneNumber, times(callCount)).getType();
    verify(mockPhoneNumber, times(callCount)).isWork();
    verifyNoMoreInteractions(mockPhoneNumber);
  }
}
