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
package org.cp.domain.core.io;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.ByteBuffer;
import java.time.LocalDateTime;
import java.time.Month;

import org.junit.jupiter.api.Test;

import org.cp.domain.core.converters.ProtoToPersonConverter;
import org.cp.domain.core.converters.PersonToProtoConverter;
import org.cp.domain.core.model.Name;
import org.cp.domain.core.model.Person;
import org.cp.domain.core.serialization.PersonProtoSerializer;

/**
 * Integration Tests to test the de/serialization of {@link Person}.
 *
 * @author John Blum
 * @since 0.2.0
 */
public class PersonSerializationIntegrationTests {

  private final PersonToProtoConverter personProtoConverter = new PersonToProtoConverter();
  private final ProtoToPersonConverter personConverter = new ProtoToPersonConverter();
  private final PersonProtoSerializer personSerializer = new PersonProtoSerializer();

  @Test
  void serializeDeserializePersonWithProtobuf() {

    Person person = Person.newPerson(Name.of("Jon", "Doe"))
      .born(LocalDateTime.of(2000, Month.JULY, 1, 13, 30, 45))
      .asMale();

    byte[] data = this.personSerializer.serialize(this.personProtoConverter.convert(person)).array();

    assertThat(data).isNotNull().isNotEmpty();

    Person deserializedPerson = this.personConverter.convert(this.personSerializer.deserialize(ByteBuffer.wrap(data)));

    assertThat(deserializedPerson).isNotNull();
    assertThat(deserializedPerson).isNotSameAs(person);
    assertThat(deserializedPerson.getName()).isEqualTo(person.getName());
  }

}
