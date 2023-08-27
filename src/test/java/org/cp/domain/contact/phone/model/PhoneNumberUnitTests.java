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
import static org.cp.elements.lang.ThrowableAssertions.assertThatUnsupportedOperationException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
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
import org.cp.elements.lang.ThrowableOperation;
import org.cp.elements.lang.Visitor;

/**
 * Unit Tests for {@link PhoneNumber}.
 *
 * @author John Blum
 * @see org.junit.jupiter.api.Test
 * @see org.cp.domain.contact.phone.model.PhoneNumber
 * @since 0.1.0
 */
public class PhoneNumberUnitTests {

  private void assertPhoneNumber(PhoneNumber phoneNumber,
      AreaCode areaCode, ExchangeCode exchangeCode, LineNumber lineNumber) {

    assertThat(phoneNumber).isNotNull();
    assertThat(phoneNumber.getAreaCode()).isEqualTo(areaCode);
    assertThat(phoneNumber.getExchangeCode()).isEqualTo(exchangeCode);
    assertThat(phoneNumber.getLineNumber()).isEqualTo(lineNumber);
  }

  private void assertPhoneNumberWithCountryExtensionAndType(PhoneNumber phoneNumber,
      Country country, Extension extension, PhoneNumber.Type phoneNumberType) {

    assertThat(phoneNumber).isNotNull();
    assertThat(phoneNumber.getCountry().orElse(null)).isEqualTo(country);
    assertThat(phoneNumber.getExtension().orElse(null)).isEqualTo(extension);
    assertThat(phoneNumber.getType().orElse(null)).isEqualTo(phoneNumberType);
  }

  private void assertPhoneNumberWithNoCountryExtensionOrType(PhoneNumber phoneNumber) {

    assertThat(phoneNumber).isNotNull();
    assertThat(phoneNumber.getCountry()).isNotPresent();
    assertThat(phoneNumber.getExtension()).isNotPresent();
    assertThat(phoneNumber.getType()).isNotPresent();
  }

  private PhoneNumber mockPhoneNumber(AreaCode areaCode, ExchangeCode exchangeCode, LineNumber lineNumber) {

    PhoneNumber mockPhoneNumber = mock(PhoneNumber.class);

    doReturn(areaCode).when(mockPhoneNumber).getAreaCode();
    doReturn(exchangeCode).when(mockPhoneNumber).getExchangeCode();
    doReturn(lineNumber).when(mockPhoneNumber).getLineNumber();

    return mockPhoneNumber;
  }

  @Test
  void builderIsCorrect() {

    PhoneNumber.Builder phoneNumberBuilder = PhoneNumber.builder();

    assertThat(phoneNumberBuilder).isNotNull();
  }

  @Test
  void copyFromPhoneNumber() {

    AreaCode mockAreaCode = mock(AreaCode.class);
    ExchangeCode mockExchangeCode = mock(ExchangeCode.class);
    LineNumber mockLineNumber = mock(LineNumber.class);

    PhoneNumber mockPhoneNumber = mockPhoneNumber(mockAreaCode, mockExchangeCode, mockLineNumber);
    PhoneNumber copy = PhoneNumber.from(mockPhoneNumber);

    assertPhoneNumber(copy, mockAreaCode, mockExchangeCode, mockLineNumber);
    assertPhoneNumberWithNoCountryExtensionOrType(copy);
    assertThat(copy.isRoaming()).isTrue();
    assertThat(copy.isTextEnabled()).isFalse();

    verifyNoInteractions(mockAreaCode, mockExchangeCode, mockLineNumber);
  }

  @Test
  void copyFromPhoneNumberWithNonNullCountryExtensionAndType() {

    AreaCode mockAreaCode = mock(AreaCode.class);
    ExchangeCode mockExchangeCode = mock(ExchangeCode.class);
    LineNumber mockLineNumber = mock(LineNumber.class);
    Extension mockExtension = mock(Extension.class);

    PhoneNumber mockPhoneNumber = mockPhoneNumber(mockAreaCode, mockExchangeCode, mockLineNumber);

    doNothing().when(mockPhoneNumber).setCountry(any());
    doReturn(Optional.of(Country.UNITED_STATES_OF_AMERICA)).when(mockPhoneNumber).getCountry();
    doReturn(Optional.of(mockExtension)).when(mockPhoneNumber).getExtension();
    doReturn(Optional.of(PhoneNumber.Type.VOIP)).when(mockPhoneNumber).getType();
    doReturn(true).when(mockPhoneNumber).isTextEnabled();

    PhoneNumber copy = PhoneNumber.from(mockPhoneNumber);

    assertPhoneNumber(copy, mockAreaCode, mockExchangeCode, mockLineNumber);
    assertPhoneNumberWithCountryExtensionAndType(copy,
      Country.UNITED_STATES_OF_AMERICA, mockExtension, PhoneNumber.Type.VOIP);
    assertThat(copy.isTextEnabled()).isTrue();

    verifyNoInteractions(mockAreaCode, mockExchangeCode, mockLineNumber, mockExtension);
  }

  @Test
  void copyFromNullThrowsIllegalArgumentException() {

    assertThatIllegalArgumentException()
      .isThrownBy(() -> PhoneNumber.from(null))
      .withMessage("PhoneNumber to copy is required")
      .withNoCause();
  }

  @Test
  void ofAreaCodeExchangeCodeAndLineNumber() {

    AreaCode mockAreaCode = mock(AreaCode.class);
    ExchangeCode mockExchangeCode = mock(ExchangeCode.class);
    LineNumber mockLineNumber = mock(LineNumber.class);

    PhoneNumber phoneNumber = PhoneNumber.of(mockAreaCode, mockExchangeCode, mockLineNumber);

    assertPhoneNumber(phoneNumber, mockAreaCode, mockExchangeCode, mockLineNumber);
    assertPhoneNumberWithNoCountryExtensionOrType(phoneNumber);

    verifyNoInteractions(mockAreaCode, mockExchangeCode, mockLineNumber);
  }

  @Test
  void ofNullAreaCode() {

    ExchangeCode mockExchangeCode = mock(ExchangeCode.class);
    LineNumber mockLineNumber = mock(LineNumber.class);

    assertThatIllegalArgumentException()
      .isThrownBy(() -> PhoneNumber.of(null, mockExchangeCode, mockLineNumber))
      .withMessage("AreaCode is required")
      .withNoCause();

    verifyNoInteractions(mockExchangeCode, mockLineNumber);
  }

  @Test
  void ofNullExchangeCode() {

    AreaCode mockAreaCode = mock(AreaCode.class);
    LineNumber mockLineNumber = mock(LineNumber.class);

    assertThatIllegalArgumentException()
      .isThrownBy(() -> PhoneNumber.of(mockAreaCode, null, mockLineNumber))
      .withMessage("ExchangeCode is required")
      .withNoCause();

    verifyNoInteractions(mockAreaCode, mockLineNumber);
  }

  @Test
  void ofNullLineNumber() {

    AreaCode mockAreaCode = mock(AreaCode.class);
    ExchangeCode mockExchangeCode = mock(ExchangeCode.class);

    assertThatIllegalArgumentException()
      .isThrownBy(() -> PhoneNumber.of(mockAreaCode, mockExchangeCode, null))
      .withMessage("LineNumber is required")
      .withNoCause();

    verifyNoInteractions(mockAreaCode, mockExchangeCode);
  }

  @Test
  void parseValidPhoneNumberWithExtension() {

    PhoneNumber phoneNumber = PhoneNumber.parse("5035551234 x4321");

    assertPhoneNumber(phoneNumber, AreaCode.of(503), ExchangeCode.of(555), LineNumber.of(1234));
  }

  @Test
  void parseValidPhoneNumberWithHyphens() {

    PhoneNumber phoneNumber = PhoneNumber.parse("503-555-1234");

    assertPhoneNumber(phoneNumber, AreaCode.of(503), ExchangeCode.of(555), LineNumber.of(1234));
  }

  @Test
  void parseValidPhoneNumberWithParenthesis() {

    PhoneNumber phoneNumber = PhoneNumber.parse("(971) 555-1234");

    assertPhoneNumber(phoneNumber, AreaCode.of(971), ExchangeCode.of(555), LineNumber.of(1234));
  }

  @Test
  void parseInvalidPhoneNumberThrowsIllegalArgumentException() {

    Arrays.asList("555-1234", "  ", "", null).forEach(invalidPhoneNumber ->
      assertThatIllegalArgumentException()
        .isThrownBy(() -> PhoneNumber.parse(invalidPhoneNumber))
        .withMessage("Phone Number [%s] must be [%d] digits",
          invalidPhoneNumber, PhoneNumber.REQUIRED_PHONE_NUMBER_LENGTH)
        .withNoCause());
  }

  @Test
  void asTypeCallsSetType() {

    PhoneNumber mockPhoneNumber = mock(PhoneNumber.class);

    doCallRealMethod().when(mockPhoneNumber).asType(any());

    assertThat(mockPhoneNumber.<PhoneNumber>asType(PhoneNumber.Type.UNKNOWN)).isSameAs(mockPhoneNumber);

    verify(mockPhoneNumber, times(1)).asType(eq(PhoneNumber.Type.UNKNOWN));
    verify(mockPhoneNumber, times(1)).setType(eq(PhoneNumber.Type.UNKNOWN));
    verifyNoMoreInteractions(mockPhoneNumber);
  }

  @Test
  void asCellPhoneNumberCallsSetTypeWithCellPhoneNumberType() {

    PhoneNumber mockPhoneNumber = mock(PhoneNumber.class);

    doCallRealMethod().when(mockPhoneNumber).asType(any());
    doCallRealMethod().when(mockPhoneNumber).asCell();

    assertThat(mockPhoneNumber.<PhoneNumber>asCell()).isSameAs(mockPhoneNumber);

    verify(mockPhoneNumber, times(1)).asCell();
    verify(mockPhoneNumber, times(1)).asType(eq(PhoneNumber.Type.CELL));
    verify(mockPhoneNumber, times(1)).setType(eq(PhoneNumber.Type.CELL));
    verifyNoMoreInteractions(mockPhoneNumber);
  }

  @Test
  void asLandlinePhoneNumberCallsSetTypeWithLandlinePhoneNumberType() {

    PhoneNumber mockPhoneNumber = mock(PhoneNumber.class);

    doCallRealMethod().when(mockPhoneNumber).asType(any());
    doCallRealMethod().when(mockPhoneNumber).asLandline();

    assertThat(mockPhoneNumber.<PhoneNumber>asLandline()).isSameAs(mockPhoneNumber);

    verify(mockPhoneNumber, times(1)).asLandline();
    verify(mockPhoneNumber, times(1)).asType(eq(PhoneNumber.Type.LANDLINE));
    verify(mockPhoneNumber, times(1)).setType(eq(PhoneNumber.Type.LANDLINE));
    verifyNoMoreInteractions(mockPhoneNumber);
  }

  @Test
  void asSatellitePhoneNumberCallsSetTypeWithSatellitePhoneNumberType() {

    PhoneNumber mockPhoneNumber = mock(PhoneNumber.class);

    doCallRealMethod().when(mockPhoneNumber).asType(any());
    doCallRealMethod().when(mockPhoneNumber).asSatellite();

    assertThat(mockPhoneNumber.<PhoneNumber>asSatellite()).isSameAs(mockPhoneNumber);

    verify(mockPhoneNumber, times(1)).asSatellite();
    verify(mockPhoneNumber, times(1)).asType(eq(PhoneNumber.Type.SATELLITE));
    verify(mockPhoneNumber, times(1)).setType(eq(PhoneNumber.Type.SATELLITE));
    verifyNoMoreInteractions(mockPhoneNumber);
  }

  @Test
  void asVoipPhoneNumberCallsSetTypeWithVoipPhoneNumberType() {

    PhoneNumber mockPhoneNumber = mock(PhoneNumber.class);

    doCallRealMethod().when(mockPhoneNumber).asType(any());
    doCallRealMethod().when(mockPhoneNumber).asVoip();

    assertThat(mockPhoneNumber.<PhoneNumber>asVoip()).isSameAs(mockPhoneNumber);

    verify(mockPhoneNumber, times(1)).asVoip();
    verify(mockPhoneNumber, times(1)).asType(eq(PhoneNumber.Type.VOIP));
    verify(mockPhoneNumber, times(1)).setType(eq(PhoneNumber.Type.VOIP));
    verifyNoMoreInteractions(mockPhoneNumber);
  }

  @Test
  void isCellPhoneNumberWhenCell() {

    PhoneNumber mockPhoneNumber = mock(PhoneNumber.class);

    doReturn(Optional.of(PhoneNumber.Type.CELL)).when(mockPhoneNumber).getType();
    doCallRealMethod().when(mockPhoneNumber).isCell();

    assertThat(mockPhoneNumber.isCell()).isTrue();

    verify(mockPhoneNumber, times(1)).isCell();
    verify(mockPhoneNumber, times(1)).getType();
    verifyNoMoreInteractions(mockPhoneNumber);
  }

  @Test
  void isCellPhoneNumberWhenNotCell() {

    PhoneNumber mockPhoneNumber = mock(PhoneNumber.class);

    doCallRealMethod().when(mockPhoneNumber).isCell();

    Arrays.stream(PhoneNumber.Type.values())
      .filter(phoneNumberType -> !PhoneNumber.Type.CELL.equals(phoneNumberType))
      .forEach(phoneNumberType -> {
        doReturn(Optional.of(phoneNumberType)).when(mockPhoneNumber).getType();
        assertThat(mockPhoneNumber.isCell()).isFalse();
      });

    int callCount = PhoneNumber.Type.values().length - 1;

    verify(mockPhoneNumber, times(callCount)).isCell();
    verify(mockPhoneNumber, times(callCount)).getType();
    verifyNoMoreInteractions(mockPhoneNumber);
  }

  @Test
  void isLandlinePhoneNumberWhenLandline() {

    PhoneNumber mockPhoneNumber = mock(PhoneNumber.class);

    doReturn(Optional.of(PhoneNumber.Type.LANDLINE)).when(mockPhoneNumber).getType();
    doCallRealMethod().when(mockPhoneNumber).isLandline();

    assertThat(mockPhoneNumber.isLandline()).isTrue();

    verify(mockPhoneNumber, times(1)).isLandline();
    verify(mockPhoneNumber, times(1)).getType();
    verifyNoMoreInteractions(mockPhoneNumber);
  }

  @Test
  void isLandlinePhoneNumberWhenNotLandline() {

    PhoneNumber mockPhoneNumber = mock(PhoneNumber.class);

    doCallRealMethod().when(mockPhoneNumber).isLandline();

    Arrays.stream(PhoneNumber.Type.values())
      .filter(phoneNumberType -> !PhoneNumber.Type.LANDLINE.equals(phoneNumberType))
      .forEach(phoneNumberType -> {
        doReturn(Optional.of(phoneNumberType)).when(mockPhoneNumber).getType();
        assertThat(mockPhoneNumber.isLandline()).isFalse();
      });

    int callCount = PhoneNumber.Type.values().length - 1;

    verify(mockPhoneNumber, times(callCount)).isLandline();
    verify(mockPhoneNumber, times(callCount)).getType();
    verifyNoMoreInteractions(mockPhoneNumber);
  }

  @Test
  void isSatellitePhoneNumberWhenSatellite() {

    PhoneNumber mockPhoneNumber = mock(PhoneNumber.class);

    doReturn(Optional.of(PhoneNumber.Type.SATELLITE)).when(mockPhoneNumber).getType();
    doCallRealMethod().when(mockPhoneNumber).isSatellite();

    assertThat(mockPhoneNumber.isSatellite()).isTrue();

    verify(mockPhoneNumber, times(1)).isSatellite();
    verify(mockPhoneNumber, times(1)).getType();
    verifyNoMoreInteractions(mockPhoneNumber);
  }

  @Test
  void isSatellitePhoneNumberWhenNotSatellite() {

    PhoneNumber mockPhoneNumber = mock(PhoneNumber.class);

    doCallRealMethod().when(mockPhoneNumber).isSatellite();

    Arrays.stream(PhoneNumber.Type.values())
      .filter(phoneNumberType -> !PhoneNumber.Type.SATELLITE.equals(phoneNumberType))
      .forEach(phoneNumberType -> {
        doReturn(Optional.of(phoneNumberType)).when(mockPhoneNumber).getType();
        assertThat(mockPhoneNumber.isSatellite()).isFalse();
      });

    int callCount = PhoneNumber.Type.values().length - 1;

    verify(mockPhoneNumber, times(callCount)).isSatellite();
    verify(mockPhoneNumber, times(callCount)).getType();
    verifyNoMoreInteractions(mockPhoneNumber);
  }

  @Test
  void isUnknownPhoneNumberWhenKnown() {

    PhoneNumber mockPhoneNumber = mock(PhoneNumber.class);

    doCallRealMethod().when(mockPhoneNumber).isUnknown();

    Arrays.stream(PhoneNumber.Type.values())
      .filter(phoneNumberType -> !PhoneNumber.Type.UNKNOWN.equals(phoneNumberType))
      .forEach(phoneNumberType -> {
        doReturn(Optional.of(phoneNumberType)).when(mockPhoneNumber).getType();
        assertThat(mockPhoneNumber.isUnknown()).isFalse();
      });

    int callCount = PhoneNumber.Type.values().length - 1;

    verify(mockPhoneNumber, times(callCount)).isUnknown();
    verify(mockPhoneNumber, times(callCount)).getType();
    verifyNoMoreInteractions(mockPhoneNumber);
  }

  @Test
  void isUnknownPhoneNumberWhenUnknown() {

    PhoneNumber mockPhoneNumber = mock(PhoneNumber.class);

    doReturn(Optional.of(PhoneNumber.Type.UNKNOWN)).when(mockPhoneNumber).getType();
    doCallRealMethod().when(mockPhoneNumber).isUnknown();

    assertThat(mockPhoneNumber.isUnknown()).isTrue();

    verify(mockPhoneNumber, times(1)).isUnknown();
    verify(mockPhoneNumber, times(1)).getType();
    verifyNoMoreInteractions(mockPhoneNumber);
  }

  @Test
  void isUnknownPhoneNumberWhenUnset() {

    PhoneNumber mockPhoneNumber = mock(PhoneNumber.class);

    doReturn(Optional.empty()).when(mockPhoneNumber).getType();
    doCallRealMethod().when(mockPhoneNumber).isUnknown();

    assertThat(mockPhoneNumber.isUnknown()).isTrue();

    verify(mockPhoneNumber, times(1)).isUnknown();
    verify(mockPhoneNumber, times(1)).getType();
    verifyNoMoreInteractions(mockPhoneNumber);
  }

  @Test
  void isVoipPhoneNumberWhenVoip() {

    PhoneNumber mockPhoneNumber = mock(PhoneNumber.class);

    doReturn(Optional.of(PhoneNumber.Type.VOIP)).when(mockPhoneNumber).getType();
    doCallRealMethod().when(mockPhoneNumber).isVoip();

    assertThat(mockPhoneNumber.isVoip()).isTrue();

    verify(mockPhoneNumber, times(1)).isVoip();
    verify(mockPhoneNumber, times(1)).getType();
    verifyNoMoreInteractions(mockPhoneNumber);
  }

  @Test
  void isVoipPhoneNumberWhenNotVoip() {

    PhoneNumber mockPhoneNumber = mock(PhoneNumber.class);

    doCallRealMethod().when(mockPhoneNumber).isVoip();

    Arrays.stream(PhoneNumber.Type.values())
      .filter(phoneNumberType -> !PhoneNumber.Type.VOIP.equals(phoneNumberType))
      .forEach(phoneNumberType -> {
        doReturn(Optional.of(phoneNumberType)).when(mockPhoneNumber).getType();
        assertThat(mockPhoneNumber.isVoip()).isFalse();
      });

    int callCount = PhoneNumber.Type.values().length - 1;

    verify(mockPhoneNumber, times(callCount)).isVoip();
    verify(mockPhoneNumber, times(callCount)).getType();
    verifyNoMoreInteractions(mockPhoneNumber);
  }

  @Test
  void isRoamingInsideLocalCountryIsFalse() {

    PhoneNumber mockPhoneNumber = mock(PhoneNumber.class);

    doReturn(Optional.of(Country.localCountry())).when(mockPhoneNumber).getCountry();
    doCallRealMethod().when(mockPhoneNumber).isRoaming();

    assertThat(mockPhoneNumber.isRoaming()).isFalse();

    verify(mockPhoneNumber, times(1)).isRoaming();
    verify(mockPhoneNumber, times(1)).getCountry();
    verifyNoMoreInteractions(mockPhoneNumber);
  }

  @Test
  void isRoamingOutsideLocalCountryIsTrue() {

    Optional<Country> country = Arrays.stream(Country.values())
      .filter(it -> !Country.localCountry().equals(it))
      .findFirst();

    PhoneNumber mockPhoneNumber = mock(PhoneNumber.class);

    doReturn(country).when(mockPhoneNumber).getCountry();
    doCallRealMethod().when(mockPhoneNumber).isRoaming();

    assertThat(mockPhoneNumber.isRoaming()).isTrue();

    verify(mockPhoneNumber, times(1)).isRoaming();
    verify(mockPhoneNumber, times(1)).getCountry();
    verifyNoMoreInteractions(mockPhoneNumber);
  }

  @Test
  void isTextEnabled() {

    PhoneNumber mockPhoneNumber = mock(PhoneNumber.class);

    doCallRealMethod().when(mockPhoneNumber).isTextEnabled();

    assertThat(mockPhoneNumber.isTextEnabled()).isFalse();

    verify(mockPhoneNumber, times(1)).isTextEnabled();
    verifyNoMoreInteractions(mockPhoneNumber);
  }

  @Test
  void setExtensionThrowsUnsupportedOperationException() {

    Extension mockExtension = mock(Extension.class);
    PhoneNumber mockPhoneNumber = mock(PhoneNumber.class);

    doCallRealMethod().when(mockPhoneNumber).setExtension(any());

    assertThatUnsupportedOperationException()
      .isThrownBy(ThrowableOperation.fromRunnable(() -> mockPhoneNumber.setExtension(mockExtension)))
      .havingMessage("Setting Extension for a PhoneNumber of type [%s] is not supported",
        mockPhoneNumber.getClass().getName())
      .withNoCause();

    verify(mockPhoneNumber, times(1)).setExtension(eq(mockExtension));
    verifyNoMoreInteractions(mockPhoneNumber);
    verifyNoInteractions(mockExtension);
  }

  @Test
  void setTypeThrowsUnsupportedOperationException() {

    PhoneNumber mockPhoneNumber = mock(PhoneNumber.class);

    doCallRealMethod().when(mockPhoneNumber).setType(any());

    assertThatUnsupportedOperationException()
      .isThrownBy(ThrowableOperation.fromRunnable(() -> mockPhoneNumber.setType(PhoneNumber.Type.CELL)))
      .havingMessage("Setting PhoneNumber.Type for a PhoneNumber of type [%s] is not supported",
        mockPhoneNumber.getClass().getName())
      .withNoCause();

    verify(mockPhoneNumber, times(1)).setType(eq(PhoneNumber.Type.CELL));
    verifyNoMoreInteractions(mockPhoneNumber);
  }

  @Test
  void acceptVisitsPhoneNumber() {

    Visitor mockVisitor = mock(Visitor.class);

    PhoneNumber mockPhoneNumber = mock(PhoneNumber.class);

    doCallRealMethod().when(mockPhoneNumber).accept(any(Visitor.class));

    mockPhoneNumber.accept(mockVisitor);

    verify(mockPhoneNumber, times(1)).accept(eq(mockVisitor));
    verify(mockVisitor, times(1)).visit(eq(mockPhoneNumber));
    verifyNoMoreInteractions(mockPhoneNumber, mockVisitor);
  }
}
