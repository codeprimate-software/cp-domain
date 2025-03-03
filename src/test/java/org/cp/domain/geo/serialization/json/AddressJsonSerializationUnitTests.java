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
package org.cp.domain.geo.serialization.json;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.Test;

import org.cp.domain.geo.model.Address;
import org.cp.domain.geo.model.City;
import org.cp.domain.geo.model.PostalCode;
import org.cp.domain.geo.model.Street;
import org.cp.domain.geo.model.Unit;

/**
 * Unit Tests for {@link Address} JSON de/serialization.
 *
 * @author John Blum
 * @see org.cp.domain.geo.model.Address
 * @see org.junit.jupiter.api.Test
 * @since 0.3.0
 */
public class AddressJsonSerializationUnitTests {

  private final AddressJsonSerializer serializer = new AddressJsonSerializer();

  @Test
  void serializeAndDeserializeAddressAsJson() {

    Address address = Address.builder()
      .on(Street.of(180, "Elm").as(Street.Type.COURT))
      .in(Unit.apartment("1706E"))
      .in(City.of("Sunnyvale"))
      .in(PostalCode.of("94086"))
      .build()
      .asHome();

    assertThat(address).isNotNull();

    String json = this.serializer.serialize(address);

    assertThat(json).isNotNull();
    assertThat(json).isNotBlank();

    Address deserializedAddress = this.serializer.deserialize(json);

    assertThat(deserializedAddress).isNotNull();
    assertThat(deserializedAddress).isNotSameAs(address);
    assertThat(deserializedAddress).isEqualTo(address);
  }
}
