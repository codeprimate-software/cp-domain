/*
 * Copyright 2016 Author or Authors.
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
package org.cp.domain.geo.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import org.cp.elements.lang.StringUtils;

/**
 * Unit Tests for {@link Address.Type}.
 *
 * @author John Blum
 * @see org.junit.jupiter.api.Test
 * @see org.cp.domain.geo.model.Address.Type
 * @since 0.1.0
 */
public class AddressTypeUnitTests {

  @Test
  public void addressTypeAbbreviation() {

    assertThat(Address.Type.BILLING.getAbbreviation()).isEqualTo("BA");
    assertThat(Address.Type.HOME.getAbbreviation()).isEqualTo("HA");
    assertThat(Address.Type.MAILING.getAbbreviation()).isEqualTo("MA");
    assertThat(Address.Type.OFFICE.getAbbreviation()).isEqualTo("OA");
    assertThat(Address.Type.PO_BOX.getAbbreviation()).isEqualTo("PO");
    assertThat(Address.Type.RESIDENTIAL.getAbbreviation()).isEqualTo("RA");
    assertThat(Address.Type.WORK.getAbbreviation()).isEqualTo("WA");
    assertThat(Address.Type.UNKNOWN.getAbbreviation()).isEqualTo("??");
  }

  @Test
  public void addressTypeDescription() {

    assertThat(Address.Type.BILLING.getDescription()).isEqualTo("Billing");
    assertThat(Address.Type.HOME.getDescription()).isEqualTo("Home");
    assertThat(Address.Type.MAILING.getDescription()).isEqualTo("Mailing");
    assertThat(Address.Type.OFFICE.getDescription()).isEqualTo("Office");
    assertThat(Address.Type.PO_BOX.getDescription()).isEqualTo("Post Office Box");
    assertThat(Address.Type.RESIDENTIAL.getDescription()).isEqualTo("Residential");
    assertThat(Address.Type.WORK.getDescription()).isEqualTo("Work");
    assertThat(Address.Type.UNKNOWN.getDescription()).isEqualTo("Unknown");
  }

  @Test
  public void addressTypeToStringIsLikeOrSameAsDescription() {

    Arrays.stream(Address.Type.values())
      .filter(addressType -> !Address.Type.PO_BOX.equals(addressType))
      .forEach(addressType ->
        assertThat(addressType.toString()).isEqualTo(addressType.getDescription().concat(" Address")));
  }

  @Test
  void poBoxAddressTypeToStringIsSameAsDescription() {
    assertThat(Address.Type.PO_BOX.toString()).isEqualTo(Address.Type.PO_BOX.getDescription());
  }

  @Test
  public void fromAbbreviationToAddressType() {

    Arrays.stream(Address.Type.values()).forEach(addressType -> {
      assertThat(Address.Type.from(addressType.getAbbreviation())).isSameAs(addressType);
      assertThat(Address.Type.from(addressType.getAbbreviation().toLowerCase())).isEqualTo(addressType);
      assertThat(Address.Type.from(StringUtils.capitalize(addressType.getAbbreviation().toLowerCase())))
        .isEqualTo(addressType);
    });
  }

  @Test
  public void fromUnknownAbbreviation() {

    assertThatIllegalArgumentException()
      .isThrownBy(() -> Address.Type.from("unknown"))
      .withMessage("Address.Type for abbreviation [unknown] was not found")
      .withNoCause();
  }
}
