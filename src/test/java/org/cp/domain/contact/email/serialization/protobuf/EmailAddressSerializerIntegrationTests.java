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
package org.cp.domain.contact.email.serialization.protobuf;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.ByteBuffer;

import org.junit.jupiter.api.Test;

import org.cp.domain.contact.email.model.EmailAddress;
import org.cp.domain.contact.email.model.EmailAddress.Domain;
import org.cp.elements.io.IOUtils;
import org.cp.elements.security.model.User;

/**
 * Integration Test for {@link EmailAddressSerializer}.
 *
 * @author John Blum
 * @see org.cp.domain.contact.email.serialization.protobuf.EmailAddressSerializer
 * @see org.junit.jupiter.api.Test
 * @since 0.2.0
 */
public class EmailAddressSerializerIntegrationTests {

  private final EmailAddressSerializer emailAddressSerializer = new EmailAddressSerializer();

  @Test
  void serializeDeserializeEmailAddress() {

    EmailAddress emailAddress = EmailAddress.of(User.named("jonDoe"), Domain.of("home", Domain.Extensions.COM));

    byte[] data = this.emailAddressSerializer.serialize(emailAddress).array();

    assertThat(data).isNotNull().isNotEmpty();

    EmailAddress deserializedEmailAddress = this.emailAddressSerializer.deserialize(ByteBuffer.wrap(data));

    assertThat(deserializedEmailAddress).isNotNull()
      .isNotSameAs(emailAddress)
      .isEqualTo(emailAddress);
  }

  @Test
  void protobufSerializedEmailAddressIsLessThanJavaSerializedEmailAddress() throws IOException {

    EmailAddress emailAddress = EmailAddress.parse("hard@work.com");

    byte[] protobufBytes = this.emailAddressSerializer.serialize(emailAddress).array();
    byte[] javaSerializedBytes = IOUtils.serialize(emailAddress);

    //System.out.printf("Protobuf message bytes [%d]%n", protobufBytes.length);
    //System.out.printf("Java Serialized bytes [%d]%n", javaSerializedBytes.length);

    assertThat(javaSerializedBytes).isNotNull();
    assertThat(protobufBytes).isNotNull()
      .hasSizeLessThan(javaSerializedBytes.length);

  }
}
