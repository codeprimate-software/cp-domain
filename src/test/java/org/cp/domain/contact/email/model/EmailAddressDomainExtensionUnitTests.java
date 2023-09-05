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
package org.cp.domain.contact.email.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import org.cp.domain.contact.email.model.EmailAddress.Domain.Extension;

/**
 * Unit Tests for {@link EmailAddress.Domain.Extension}.
 *
 * @author John Blum
 * @see org.junit.jupiter.api.Test
 * @see org.cp.domain.contact.email.model.EmailAddress.Domain.Extension
 * @since 0.1.0
 */
public class EmailAddressDomainExtensionUnitTests {

  @Test
  void fromValidEmailAddressDomainAndExtension() {

    EmailAddress.Domain.Extension extension = EmailAddress.Domain.Extension
      .from("comcast.net")
      .orElse(null);

    assertThat(extension).isNotNull();
    assertThat(extension).isEqualTo(EmailAddress.Domain.Extension.NET);
  }

  @Test
  void fromValidEmailAddressDomainExtension() {

    Arrays.stream(EmailAddress.Domain.Extension.values()).forEach(domainExtension ->
      assertThat(EmailAddress.Domain.Extension.from(domainExtension.name()).orElse(null))
        .isEqualTo(domainExtension));
  }

  @Test
  void fromInvalidEmailAddressDomainExtension() {

    Optional<Extension> emailAddressDomainExtension = EmailAddress.Domain.Extension.from("XXX");

    assertThat(emailAddressDomainExtension).isNotNull();
    assertThat(emailAddressDomainExtension).isNotPresent();
  }
}
