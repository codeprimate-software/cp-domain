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
package org.cp.domain.contact.phone.serialization.protobuf;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.ByteBuffer;

import org.junit.jupiter.api.Test;

import org.cp.domain.contact.phone.model.AreaCode;
import org.cp.domain.contact.phone.model.ExchangeCode;
import org.cp.domain.contact.phone.model.Extension;
import org.cp.domain.contact.phone.model.LineNumber;
import org.cp.domain.contact.phone.model.PhoneNumber;
import org.cp.elements.io.IOUtils;

/**
 * Integration Tests for {@link PhoneNumberSerializer}.
 *
 * @author John Blum
 * @see org.cp.domain.contact.phone.serialization.protobuf.PhoneNumberSerializer
 * @see org.junit.jupiter.api.Test
 * @since 0.2.0
 */
public class PhoneNumberSerializerIntegrationTests {

  private final PhoneNumberSerializer phoneNumberSerializer = new PhoneNumberSerializer();

  @Test
  void serializeDeserializePhoneNumber() {

    PhoneNumber phoneNumber = PhoneNumber.of(AreaCode.of(408), ExchangeCode.of(555), LineNumber.of(1234));

    phoneNumber.setExtension(Extension.of(9876));

    byte[] data = this.phoneNumberSerializer.serialize(phoneNumber).array();

    assertThat(data).isNotNull().isNotEmpty();

    PhoneNumber deserializedPhoneNumber = this.phoneNumberSerializer.deserialize(ByteBuffer.wrap(data));

    assertThat(deserializedPhoneNumber).isNotNull().isNotSameAs(phoneNumber);
    assertThat(deserializedPhoneNumber.getAreaCode()).isEqualTo(phoneNumber.getAreaCode());
    assertThat(deserializedPhoneNumber.getExchangeCode()).isEqualTo(phoneNumber.getExchangeCode());
    assertThat(deserializedPhoneNumber.getLineNumber()).isEqualTo(phoneNumber.getLineNumber());
    assertThat(deserializedPhoneNumber.getExtension().orElse(null)).isEqualTo(phoneNumber.getExtension()
      .orElseGet(() -> Extension.of(123)));
  }

  @Test
  void protobufSerializedBytesIsLessThanJavaSerializedBytes() throws IOException {

    PhoneNumber phoneNumber = PhoneNumber.of(AreaCode.of(503), ExchangeCode.of(555), LineNumber.of(1234));

    phoneNumber.setExtension(Extension.of(9876));

    byte[] protobufBytes = this.phoneNumberSerializer.serialize(phoneNumber).array();
    byte[] javaSerializedBytes = IOUtils.serialize(phoneNumber);

    //System.out.printf("Protobuf Serialized bytes [%d] vs. Java Serialized bytes [%d]",
    //  protobufBytes.length, javaSerializedBytes.length);

    assertThat(javaSerializedBytes).isNotNull().isNotEmpty();
    assertThat(protobufBytes).isNotNull().isNotEmpty().hasSizeLessThan(javaSerializedBytes.length);
  }
}
