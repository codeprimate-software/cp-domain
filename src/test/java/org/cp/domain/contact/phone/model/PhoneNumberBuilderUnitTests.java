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

import org.cp.domain.geo.enums.Country;

/**
 * Unit Test for {@link PhoneNumber.Builder}.
 *
 * @author John Blum
 * @see org.junit.jupiter.api.Test
 * @see org.mockito.Mockito
 * @see org.cp.domain.contact.phone.model.PhoneNumber.Builder
 * @since 0.1.0
 */
public class PhoneNumberBuilderUnitTests {

  @Test
  public void buildBasicPhoneNumber() {

    AreaCode mockAreaCode = mock(AreaCode.class);
    ExchangeCode mockExchangeCode = mock(ExchangeCode.class);
    LineNumber mockLineNumber = mock(LineNumber.class);

    PhoneNumber phoneNumber = PhoneNumber.builder()
      .inAreaCode(mockAreaCode)
      .with(mockExchangeCode)
      .with(mockLineNumber)
      .build();

    assertThat(phoneNumber).isNotNull();
    assertThat(phoneNumber.getAreaCode()).isEqualTo(mockAreaCode);
    assertThat(phoneNumber.getExchangeCode()).isEqualTo(mockExchangeCode);
    assertThat(phoneNumber.getCountry()).isNotPresent();
    assertThat(phoneNumber.getExtension()).isNotPresent();
    assertThat(phoneNumber.getType()).isNotPresent();

    verifyNoInteractions(mockAreaCode, mockExchangeCode, mockLineNumber);
  }

  @Test
  public void buildCompletePhoneNumber() {

    AreaCode mockAreaCode = mock(AreaCode.class);
    ExchangeCode mockExchangeCode = mock(ExchangeCode.class);
    Extension mockExtension = mock(Extension.class);
    LineNumber mockLineNumber = mock(LineNumber.class);

    PhoneNumber phoneNumber = PhoneNumber.builder()
      .inCountry(Country.CANADA)
      .inAreaCode(mockAreaCode)
      .with(mockExchangeCode)
      .with(mockExtension)
      .with(mockLineNumber)
      .build()
      .asCell();

    assertThat(phoneNumber).isNotNull();
    assertThat(phoneNumber.getAreaCode()).isEqualTo(mockAreaCode);
    assertThat(phoneNumber.getExchangeCode()).isEqualTo(mockExchangeCode);
    assertThat(phoneNumber.getCountry().orElse(null)).isEqualTo(Country.CANADA);
    assertThat(phoneNumber.getExtension().orElse(null)).isEqualTo(mockExtension);
    assertThat(phoneNumber.getType().orElse(null)).isEqualTo(PhoneNumber.Type.CELL);

    verifyNoInteractions(mockAreaCode, mockExchangeCode, mockExtension, mockLineNumber);
  }

  @Test
  public void buildCompletePhoneNumberInLocalCountry() {

    AreaCode mockAreaCode = mock(AreaCode.class);
    ExchangeCode mockExchangeCode = mock(ExchangeCode.class);
    Extension mockExtension = mock(Extension.class);
    LineNumber mockLineNumber = mock(LineNumber.class);

    PhoneNumber phoneNumber = PhoneNumber.builder()
      .inLocalCountry()
      .inAreaCode(mockAreaCode)
      .with(mockExchangeCode)
      .with(mockExtension)
      .with(mockLineNumber)
      .build()
      .asVoip();

    assertThat(phoneNumber).isNotNull();
    assertThat(phoneNumber.getAreaCode()).isEqualTo(mockAreaCode);
    assertThat(phoneNumber.getExchangeCode()).isEqualTo(mockExchangeCode);
    assertThat(phoneNumber.getCountry().orElse(null)).isEqualTo(Country.localCountry());
    assertThat(phoneNumber.getExtension().orElse(null)).isEqualTo(mockExtension);
    assertThat(phoneNumber.getType().orElse(null)).isEqualTo(PhoneNumber.Type.VOIP);

    verifyNoInteractions(mockAreaCode, mockExchangeCode, mockExtension, mockLineNumber);
  }

  @Test
  public void buildPhoneNumberWithNullAreaCode() {

    assertThatIllegalArgumentException()
      .isThrownBy(() -> PhoneNumber.builder().inAreaCode(null).build())
      .withMessage("AreaCode is required")
      .withNoCause();
  }

  @Test
  public void buildPhoneNumberWithNullExchangeCode() {

    assertThatIllegalArgumentException()
      .isThrownBy(() -> PhoneNumber.builder().with((ExchangeCode) null).build())
      .withMessage("ExchangeCode is required")
      .withNoCause();
  }

  @Test
  public void buildPhoneNumberWithNullLineNumber() {

    assertThatIllegalArgumentException()
      .isThrownBy(() -> PhoneNumber.builder().with((LineNumber) null).build())
      .withMessage("LineNumber is required")
      .withNoCause();
  }
}
