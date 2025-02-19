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
package org.cp.domain.contact.email.serialization.json;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import org.cp.domain.contact.email.model.EmailAddress;
import org.cp.domain.contact.email.model.EmailAddress.Domain;
import org.cp.elements.security.model.User;

/**
 * Unit Tests for {@link EmailAddressJsonSerializer}
 *
 * @author John Blum
 * @see org.cp.domain.contact.email.model.EmailAddress
 * @see org.cp.domain.contact.email.serialization.json.EmailAddressJsonSerializer
 * @see org.junit.jupiter.api.Test
 * @since 0.3.0
 */
public class EmailAddressJsonSerializationUnitTests {

  private final EmailAddressJsonSerializer jsonSerializer = new EmailAddressJsonSerializer();

  @Test
  void serializeDeserializeEmailAddressAsJson() {

    EmailAddress emailAddress = EmailAddress.of(User.named("jonDoe"), Domain.of("home", Domain.Extensions.NET));

    assertThat(emailAddress).isNotNull();
    assertThat(emailAddress.toString()).isEqualTo("jonDoe@home.net");

    String json = this.jsonSerializer.serialize(emailAddress);

    assertThat(json).isNotBlank();

    EmailAddress deserializedEmailAddress = this.jsonSerializer.deserialize(json);

    assertThat(deserializedEmailAddress).isNotNull();
    assertThat(deserializedEmailAddress).isNotSameAs(emailAddress);
    assertThat(deserializedEmailAddress).isEqualTo(emailAddress);
  }
}
