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

import static java.util.Arrays.stream;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

/**
 * Unit tests for {@link Address.Type}.
 *
 * @author John Blum
 * @see org.junit.Test
 * @see org.cp.domain.geo.model.Address.Type
 * @since 1.0.0
 */
public class AddressTypeTests {

  @Test
  public void addressTypeAbbreviations() {

    assertThat(Address.Type.BILLING.getAbbreviation()).isEqualTo("BA");
    assertThat(Address.Type.HOME.getAbbreviation()).isEqualTo("HA");
    assertThat(Address.Type.MAILING.getAbbreviation()).isEqualTo("MA");
    assertThat(Address.Type.OFFICE.getAbbreviation()).isEqualTo("OA");
    assertThat(Address.Type.PO_BOX.getAbbreviation()).isEqualTo("PO");
    assertThat(Address.Type.WORK.getAbbreviation()).isEqualTo("WA");
    assertThat(Address.Type.UNKNOWN.getAbbreviation()).isEqualTo("??");
  }

  @Test
  public void addressTypeDescriptions() {

    assertThat(Address.Type.BILLING.getDescription()).isEqualTo("Billing");
    assertThat(Address.Type.HOME.getDescription()).isEqualTo("Home");
    assertThat(Address.Type.MAILING.getDescription()).isEqualTo("Mailing");
    assertThat(Address.Type.OFFICE.getDescription()).isEqualTo("Office");
    assertThat(Address.Type.PO_BOX.getDescription()).isEqualTo("Post Office Box");
    assertThat(Address.Type.WORK.getDescription()).isEqualTo("Work");
    assertThat(Address.Type.UNKNOWN.getDescription()).isEqualTo("Unknown");
  }

  @Test
  public void addressTypeToStringIsSameAsDescription() {

    assertThat(Address.Type.BILLING.toString()).isEqualTo(Address.Type.BILLING.getDescription());
    assertThat(Address.Type.HOME.toString()).isEqualTo(Address.Type.HOME.getDescription());
    assertThat(Address.Type.MAILING.toString()).isEqualTo(Address.Type.MAILING.getDescription());
    assertThat(Address.Type.OFFICE.toString()).isEqualTo(Address.Type.OFFICE.getDescription());
    assertThat(Address.Type.PO_BOX.toString()).isEqualTo(Address.Type.PO_BOX.getDescription());
    assertThat(Address.Type.WORK.toString()).isEqualTo(Address.Type.WORK.getDescription());
    assertThat(Address.Type.UNKNOWN.toString()).isEqualTo(Address.Type.UNKNOWN.getDescription());
  }

  @Test
  public void addressTypeValueOfAbbreviationIsCorrect() {

    stream(Address.Type.values()).forEach(addressType ->
      assertThat(Address.Type.valueOfAbbreviation(addressType.getAbbreviation())).isSameAs(addressType));
  }
}
