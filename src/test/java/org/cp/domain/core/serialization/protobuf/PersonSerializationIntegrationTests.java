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
package org.cp.domain.core.serialization.protobuf;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.time.LocalDateTime;
import java.time.Month;

import org.junit.jupiter.api.Test;

import org.cp.domain.core.model.Name;
import org.cp.domain.core.model.Person;
import org.cp.elements.io.IOUtils;

/**
 * Integration Tests to test the de/serialization of {@link Person}.
 *
 * @author John Blum
 * @see org.junit.jupiter.api.Test
 * @see org.cp.domain.core.model.Person
 * @see org.cp.domain.core.serialization.protobuf.PersonSerializer
 * @since 0.2.0
 */
public class PersonSerializationIntegrationTests {

  private final PersonSerializer personSerializer = new PersonSerializer();

  @Test
  void serializeDeserializePersonWithProtobuf() {

    Person person = Person.newPerson(Name.of("Jon", "J", "Doe"))
      .born(LocalDateTime.of(2000, Month.JULY, 1, 13, 30, 45))
      .asMale();

    byte[] data = this.personSerializer.serialize(person).array();

    assertThat(data).isNotNull().isNotEmpty();

    Person deserializedPerson = this.personSerializer.deserialize(ByteBuffer.wrap(data));

    assertThat(deserializedPerson).isNotNull();
    assertThat(deserializedPerson).isNotSameAs(person);
    assertThat(deserializedPerson.getName()).isEqualTo(person.getName());
  }

  @Test
  void protobufSerializedBytesIsLessThanJavaSerializedBytes() throws IOException {

    Person person = Person.newPerson(Name.of("Albert", "Einstein"))
      .born(LocalDateTime.of(1879, Month.MARCH, 14, 0, 0))
      .died(LocalDateTime.of(1955, Month.APRIL, 18, 0, 0))
      .asMale()
      .identifiedBy(1L);

    byte[] javaSerializedData = IOUtils.serialize(person);
    byte[] protobufSerializedData = this.personSerializer.serialize(person).array();

    //System.out.printf("PROTOBUF [%d] vs. JAVA SERIALIZATION [%d]%n",
    //  protobufSerializedData.length, javaSerializedData.length);

    assertThat(javaSerializedData).isNotNull().isNotEmpty();
    assertThat(protobufSerializedData).isNotNull().isNotEmpty().hasSizeLessThan(javaSerializedData.length);
  }
}
