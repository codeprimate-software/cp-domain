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
package org.cp.domain.contact.phone.serialization.json;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import org.cp.domain.contact.phone.model.AreaCode;
import org.cp.domain.contact.phone.model.ExchangeCode;
import org.cp.domain.contact.phone.model.LineNumber;
import org.cp.domain.contact.phone.model.PhoneNumber;
import org.cp.domain.geo.enums.Country;
import org.cp.elements.lang.Renderer;

/**
 * Unit Tests for {@link PhoneNumberJsonSerializer}.
 *
 * @author John Blum
 * @see org.cp.domain.contact.phone.model.PhoneNumber
 * @see org.cp.domain.contact.phone.serialization.json.PhoneNumberJsonSerializer
 * @see org.junit.jupiter.api.Test
 * @since 0.3.0
 */
public class PhoneNumberJsonSerializationUnitTests {

  private final PhoneNumberJsonSerializer jsonSerializer = new PhoneNumberJsonSerializer();

  @Test
  void serializeDeserializePhoneNumber() {

    Renderer<PhoneNumber> phoneNumberRenderer = phoneNumber -> "%s-%s-%s"
      .formatted(phoneNumber.getAreaCode(), phoneNumber.getExchangeCode(), phoneNumber.getLineNumber());

    PhoneNumber phoneNumber = PhoneNumber.builder()
      .inAreaCode(AreaCode.of(971))
      .usingExchange(ExchangeCode.of(555))
      .withLineNumber(LineNumber.of(1234))
      .withTextEnabled()
      .inLocalCountry()
      .build()
      .asCell()
      .identifiedBy(1L);

    assertThat(phoneNumber).isNotNull();
    assertThat(phoneNumberRenderer.render(phoneNumber)).isEqualTo("971-555-1234");

    String json = this.jsonSerializer.serialize(phoneNumber);

    assertThat(json).isNotBlank();

    PhoneNumber deserializedPhoneNumber = this.jsonSerializer.deserialize(json);

    assertThat(deserializedPhoneNumber).isNotNull();
    assertThat(deserializedPhoneNumber).isNotSameAs(phoneNumber);
    assertThat(phoneNumberRenderer.render(deserializedPhoneNumber)).isEqualTo(phoneNumberRenderer.render(phoneNumber));
    assertThat(deserializedPhoneNumber.getCountry().orElse(null)).isEqualTo(Country.localCountry());
    assertThat(deserializedPhoneNumber.isCell()).isTrue();
    assertThat(deserializedPhoneNumber.isTextEnabled()).isTrue();
    assertThat(deserializedPhoneNumber.getId()).isOne();
  }
}
