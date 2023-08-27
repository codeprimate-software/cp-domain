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
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import org.cp.domain.geo.enums.Country;

/**
 * Abstract base class for all {@link PhoneNumber} Unit Tests.
 *
 * @author John Blum
 * @see org.cp.domain.contact.phone.model.PhoneNumber
 * @since 0.1.0
 */
public abstract class BasePhoneNumberUnitTests {

  protected void assertPhoneNumber(PhoneNumber phoneNumber,
    AreaCode areaCode, ExchangeCode exchangeCode, LineNumber lineNumber) {

    assertThat(phoneNumber).isNotNull();
    assertThat(phoneNumber.getAreaCode()).isEqualTo(areaCode);
    assertThat(phoneNumber.getExchangeCode()).isEqualTo(exchangeCode);
    assertThat(phoneNumber.getLineNumber()).isEqualTo(lineNumber);
  }

  protected void assertPhoneNumberWithCountryExtensionAndType(PhoneNumber phoneNumber,
    Country country, Extension extension, PhoneNumber.Type phoneNumberType) {

    assertThat(phoneNumber).isNotNull();
    assertThat(phoneNumber.getCountry().orElse(null)).isEqualTo(country);
    assertThat(phoneNumber.getExtension().orElse(null)).isEqualTo(extension);
    assertThat(phoneNumber.getType().orElse(null)).isEqualTo(phoneNumberType);
  }

  protected void assertPhoneNumberWithNoCountryExtensionOrType(PhoneNumber phoneNumber) {

    assertThat(phoneNumber).isNotNull();
    assertThat(phoneNumber.getCountry()).isNotPresent();
    assertThat(phoneNumber.getExtension()).isNotPresent();
    assertThat(phoneNumber.getType()).isNotPresent();
  }

  protected PhoneNumber mockPhoneNumber(AreaCode areaCode, ExchangeCode exchangeCode, LineNumber lineNumber) {

    PhoneNumber mockPhoneNumber = mock(PhoneNumber.class);

    doReturn(areaCode).when(mockPhoneNumber).getAreaCode();
    doReturn(exchangeCode).when(mockPhoneNumber).getExchangeCode();
    doReturn(lineNumber).when(mockPhoneNumber).getLineNumber();

    return mockPhoneNumber;
  }
}
