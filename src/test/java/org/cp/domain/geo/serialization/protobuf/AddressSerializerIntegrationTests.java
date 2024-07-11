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
package org.cp.domain.geo.serialization.protobuf;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.ByteBuffer;

import org.junit.jupiter.api.Test;

import org.cp.domain.geo.enums.Direction;
import org.cp.domain.geo.model.Address;
import org.cp.domain.geo.model.City;
import org.cp.domain.geo.model.PostalCode;
import org.cp.domain.geo.model.Street;
import org.cp.domain.geo.model.Street.Type;
import org.cp.domain.geo.model.Unit;

/**
 * Integration Tests for {@link AddressSerializer}.
 *
 * @author John Blum
 * @see org.junit.jupiter.api.Test
 * @see org.cp.domain.geo.serialization.protobuf.AddressSerializer
 * @since 0.2.0
 */
public class AddressSerializerIntegrationTests {

  private final AddressSerializer addressSerializer = new AddressSerializer();

  @Test
  void serializeThenDeserializeAddressCorrectly() {

    Address address = Address.builder()
      .on(Street.of(180, "Elm").as(Type.COURT).withDirection(Direction.EAST))
      .in(Unit.apartment("1706"))
      .in(City.of("Sunnyvale"))
      .in(PostalCode.of("94086"))
      .build();

    byte[] data = this.addressSerializer.serialize(address).array();

    assertThat(data).isNotNull().isNotEmpty();

    Address deserializedAddress = this.addressSerializer.deserialize(ByteBuffer.wrap(data));

    assertThat(deserializedAddress).isNotNull().isNotSameAs(address).isEqualTo(address);
  }

}
