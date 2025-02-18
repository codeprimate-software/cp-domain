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
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import org.cp.domain.contact.email.model.EmailAddress.Domain.Extension;
import org.cp.domain.contact.email.model.EmailAddress.Domain.Extensions;

/**
 * Unit Tests for {@link EmailAddress.Domain.Extension}.
 *
 * @author John Blum
 * @see org.cp.domain.contact.email.model.EmailAddress.Domain.Extension
 * @see org.junit.jupiter.api.Test
 * @see org.mockito.Mockito
 * @since 0.3.0
 */
public class EmailAddressDomainExtensionUnitTests {

  @Test
  void fromName() {

    Extension extension = Extension.named("ABC");

    assertThat(extension).isNotNull();
    assertThat(extension.getName()).isEqualTo("ABC");
    assertThat(extension.getExt()).isNotNull();
    assertThat(extension.getExt()).isNotPresent();
  }

  @Test
  void fromIllegalName() {

    Arrays.asList("  ", "", null).forEach(name ->
      assertThatIllegalArgumentException()
        .isThrownBy(() -> Extension.named(name))
        .withMessage("Name [%s] is required", name)
        .withNoCause());
  }

  @Test
  void extensionOfExtensions() {

    Extension extension = Extension.named("net");

    assertThat(extension).isNotNull();
    assertThat(extension).isNotSameAs(Extensions.NET);
    assertThat(extension.getName()).isEqualTo("net");
    assertThat(extension.getExt()).isPresent();
    assertThat(extension.getExt().orElse(null)).isEqualTo(Extensions.NET);
  }

  @Test
  void allExtensionsAreAnExtension() {

    Arrays.stream(Extensions.values()).forEach(extension -> {
      assertThat(extension.getName()).isEqualTo(extension.name().toLowerCase());
      assertThat(extension.getExt().orElse(null)).isSameAs(extension);
    });
  }
}
