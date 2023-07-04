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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoInteractions;

import org.junit.jupiter.api.Test;

import org.assertj.core.api.InstanceOfAssertFactories;
import org.cp.domain.geo.enums.Country;
import org.cp.elements.lang.annotation.NotNull;

/**
 * Unit Tests for {@link AbstractPhoneNumber}.
 *
 * @author John Blum
 * @see org.junit.jupiter.api.Test
 * @see org.mockito.Mockito
 * @see org.cp.domain.contact.phone.model.AbstractPhoneNumber
 * @since 0.1.0
 */
public class AbstractPhoneNumberUnitTests {

  @Test
  public void constructAbstractPhoneNumber() {

    AreaCode mockAreaCode = mock(AreaCode.class);
    ExchangeCode mockExchangeCode = mock(ExchangeCode.class);
    LineNumber mockLineNumber = mock(LineNumber.class);

    AbstractPhoneNumber phoneNumber = new TestPhoneNumber(mockAreaCode, mockExchangeCode, mockLineNumber);

    assertThat(phoneNumber).isNotNull();
    assertThat(phoneNumber.getId()).isNull();
    assertThat(phoneNumber.getAreaCode()).isEqualTo(mockAreaCode);
    assertThat(phoneNumber.getExchangeCode()).isEqualTo(mockExchangeCode);
    assertThat(phoneNumber.getLineNumber()).isEqualTo(mockLineNumber);
    assertThat(phoneNumber.getCountry()).isNotPresent();
    assertThat(phoneNumber.getExtension()).isNotPresent();
    assertThat(phoneNumber.isRoaming()).isTrue();
    assertThat(phoneNumber.isTextEnabled()).isFalse();
    assertThat(phoneNumber.getType()).isNotPresent();

    verifyNoInteractions(mockAreaCode, mockExchangeCode, mockLineNumber);
  }

  @Test
  public void constructAbstractPhoneNumberWithCountryExtensionTypeAndTexting() {

    AreaCode mockAreaCode = mock(AreaCode.class);
    ExchangeCode mockExchangeCode = mock(ExchangeCode.class);
    Extension mockExtension = mock(Extension.class);
    LineNumber mockLineNumber = mock(LineNumber.class);

    AbstractPhoneNumber phoneNumber = new TestPhoneNumber(mockAreaCode, mockExchangeCode, mockLineNumber)
      .<AbstractPhoneNumber>inLocalCountry()
      .asVoip()
      .identifiedBy(1L);

    assertThat(phoneNumber).isNotNull();

    phoneNumber.setExtension(mockExtension);
    phoneNumber.setTextEnabled(true);

    assertThat(phoneNumber.getId()).isOne();
    assertThat(phoneNumber.getAreaCode()).isEqualTo(mockAreaCode);
    assertThat(phoneNumber.getExchangeCode()).isEqualTo(mockExchangeCode);
    assertThat(phoneNumber.getLineNumber()).isEqualTo(mockLineNumber);
    assertThat(phoneNumber.getCountry().orElse(null)).isEqualTo(Country.localCountry());
    assertThat(phoneNumber.getExtension().orElse(null)).isEqualTo(mockExtension);
    assertThat(phoneNumber.isRoaming()).isFalse();
    assertThat(phoneNumber.isTextEnabled()).isTrue();
    assertThat(phoneNumber.isVoip()).isTrue();

    verifyNoInteractions(mockAreaCode, mockExchangeCode, mockExtension, mockLineNumber);
  }

  @Test
  public void constructAbstractPhoneNumberWithNullAreaCode() {

    ExchangeCode mockExchangeCode = mock(ExchangeCode.class);
    LineNumber mockLineNumber = mock(LineNumber.class);

    assertThatIllegalArgumentException()
      .isThrownBy(() -> new TestPhoneNumber(null, mockExchangeCode, mockLineNumber))
      .withMessage("AreaCode is required")
      .withNoCause();

    verifyNoInteractions(mockExchangeCode, mockLineNumber);
  }

  @Test
  public void constructAbstractPhoneNumberWithNullExchangeCode() {

    AreaCode mockAreaCode = mock(AreaCode.class);
    LineNumber mockLineNumber = mock(LineNumber.class);

    assertThatIllegalArgumentException()
      .isThrownBy(() -> new TestPhoneNumber(mockAreaCode, null, mockLineNumber))
      .withMessage("ExchangeCode is required")
      .withNoCause();

    verifyNoInteractions(mockAreaCode, mockLineNumber);
  }

  @Test
  public void constructAbstractPhoneNumberWithNullLineNumber() {

    AreaCode mockAreaCode = mock(AreaCode.class);
    ExchangeCode mockExchangeCode = mock(ExchangeCode.class);

    assertThatIllegalArgumentException()
      .isThrownBy(() -> new TestPhoneNumber(mockAreaCode, mockExchangeCode, null))
      .withMessage("LineNumber is required")
      .withNoCause();

    verifyNoInteractions(mockAreaCode, mockExchangeCode);
  }

  @Test
  public void clonesPhoneNumberCorrectly() {

    AreaCode mockAreaCode = mock(AreaCode.class);
    ExchangeCode mockExchangeCode = mock(ExchangeCode.class);
    Extension mockExtension = mock(Extension.class);
    LineNumber mockLineNumber = mock(LineNumber.class);

    AbstractPhoneNumber phoneNumber = new TestPhoneNumber(mockAreaCode, mockExchangeCode, mockLineNumber);

    phoneNumber.asVoip()
      .<AbstractPhoneNumber>in(Country.GERMANY)
      .identifiedBy(1L);

    phoneNumber.setExtension(mockExtension);
    phoneNumber.setTextEnabled(true);

    Object clone = phoneNumber.clone();

    assertThat(clone).isInstanceOf(AbstractPhoneNumber.class)
      .asInstanceOf(InstanceOfAssertFactories.type(AbstractPhoneNumber.class))
      .extracting(PhoneNumber::getId)
      .isNull();

    PhoneNumber clonedPhoneNumber = (PhoneNumber) clone;

    assertThat(clonedPhoneNumber.getAreaCode()).isEqualTo(mockAreaCode);
    assertThat(clonedPhoneNumber.getExchangeCode()).isEqualTo(mockExchangeCode);
    assertThat(clonedPhoneNumber.getLineNumber()).isEqualTo(mockLineNumber);
    assertThat(clonedPhoneNumber.getCountry().orElse(null)).isEqualTo(Country.GERMANY);
    assertThat(clonedPhoneNumber.getExtension().orElse(null)).isEqualTo(mockExtension);
    assertThat(clonedPhoneNumber.isRoaming()).isTrue();
    assertThat(clonedPhoneNumber.isTextEnabled()).isTrue();

    verifyNoInteractions(mockAreaCode, mockExchangeCode, mockExtension, mockLineNumber);
  }

  @Test
  public void equalPhoneNumbersAreEqual() {

    AbstractPhoneNumber phoneNumberOne =
      new TestPhoneNumber(AreaCode.of(503), ExchangeCode.of(555), LineNumber.of(1234))
        .asCell();

    AbstractPhoneNumber phoneNumberTwo =
      new TestPhoneNumber(AreaCode.of(503), ExchangeCode.of(555), LineNumber.of(1234))
        .asLandline();

    assertThat(phoneNumberOne).isEqualTo(phoneNumberTwo);

    phoneNumberOne.<AbstractPhoneNumber>inLocalCountry().setExtension(Extension.of("11358"));
    phoneNumberTwo.<AbstractPhoneNumber>inLocalCountry().setExtension(Extension.of("11358"));


    assertThat(phoneNumberOne).isEqualTo(phoneNumberTwo);
  }

  @Test
  @SuppressWarnings("all")
  public void samePhoneNumberIsEqual() {

    AbstractPhoneNumber phoneNumber =
      new TestPhoneNumber(AreaCode.of(503), ExchangeCode.of(555), LineNumber.of(1234));

    assertThat(phoneNumber).isEqualTo(phoneNumber);
  }

  @Test
  public void unequalPhoneNumbersAreNotEqual() {

    AbstractPhoneNumber phoneNumberOne =
      new TestPhoneNumber(AreaCode.of(503), ExchangeCode.of(555), LineNumber.of(1234));

    phoneNumberOne.setExtension(Extension.of("1234"));

    AbstractPhoneNumber phoneNumberTwo =
      new TestPhoneNumber(AreaCode.of(503), ExchangeCode.of(555), LineNumber.of(1234));

    assertThat(phoneNumberOne).isNotEqualTo(phoneNumberTwo);

    phoneNumberOne.setExtension(null);
    phoneNumberTwo.inLocalCountry();

    assertThat(phoneNumberOne).isNotEqualTo(phoneNumberTwo);

    AbstractPhoneNumber phoneNumberThree =
      new TestPhoneNumber(AreaCode.of(971), ExchangeCode.of(555), LineNumber.of(4321))
        .in(Country.GERMANY);

    phoneNumberOne.<AbstractPhoneNumber>in(Country.GERMANY).setExtension(Extension.of("2480"));
    phoneNumberThree.setExtension(Extension.of("2480"));

    assertThat(phoneNumberOne).isNotEqualTo(phoneNumberThree);
  }

  @Test
  @SuppressWarnings("all")
  public void phoneNumberIsNotEqualToObject() {

    AbstractPhoneNumber phoneNumber =
      new TestPhoneNumber(AreaCode.of(503), ExchangeCode.of(555), LineNumber.of(1234));

    assertThat(phoneNumber.equals("503-555-1234")).isFalse();
  }

  @Test
  @SuppressWarnings("all")
  public void phoneNumberIsNotEqualToNull() {

    AbstractPhoneNumber phoneNumber =
      new TestPhoneNumber(AreaCode.of(503), ExchangeCode.of(555), LineNumber.of(1234));

    assertThat(phoneNumber.equals(null)).isFalse();
  }

  @Test
  public void hashCodeIsCorrect() {

    AbstractPhoneNumber phoneNumber =
      new TestPhoneNumber(AreaCode.of(503), ExchangeCode.of(555), LineNumber.of(1234));

    assertThat(phoneNumber).hasSameHashCodeAs(phoneNumber);
    assertThat(phoneNumber).doesNotHaveSameHashCodeAs("503-504-8657");
    assertThat(phoneNumber).doesNotHaveSameHashCodeAs(
      new TestPhoneNumber(AreaCode.of(503), ExchangeCode.of(555), LineNumber.of(1234))
        .inLocalCountry());
  }

  @Test
  public void toStringIsCorrect() {

    AbstractPhoneNumber phoneNumber =
      new TestPhoneNumber(AreaCode.of(503), ExchangeCode.of(555), LineNumber.of(1234))
        .<AbstractPhoneNumber>inLocalCountry()
        .asCell();

    phoneNumber.setExtension(Extension.of("2480"));

    String expectedString = String.format(AbstractPhoneNumber.PHONE_NUMBER_TO_STRING, phoneNumber.getClass().getName(),
      phoneNumber.getAreaCode(), phoneNumber.getExchangeCode(), phoneNumber.getLineNumber(),
      "x".concat(phoneNumber.getExtension().map(Extension::getNumber).orElse("")),
      phoneNumber.getType().map(PhoneNumber.Type::getAbbreviation).orElse(null),
      phoneNumber.getCountry().orElse(null));

    assertThat(phoneNumber).hasToString(expectedString);
  }

  private static final class TestPhoneNumber extends AbstractPhoneNumber {

    private TestPhoneNumber(@NotNull AreaCode areaCode, @NotNull ExchangeCode exchangeCode,
        @NotNull LineNumber lineNumber) {

      super(areaCode, exchangeCode, lineNumber);
    }
  }
}
