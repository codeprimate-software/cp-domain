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

import org.cp.domain.core.model.People;
import org.cp.domain.core.model.Person;
import org.cp.elements.io.IOUtils;

/**
 * Integration Tests to test the de/serialization of {@link People}.
 *
 * @author John Blum
 * @see org.junit.jupiter.api.Test
 * @see org.cp.domain.core.model.People
 * @see org.cp.domain.core.serialization.protobuf.PeopleSerializer
 * @since 0.2.0
 */
public class PeopleSerializationIntegrationTests {

  private final PeopleSerializer peopleSerializer = new PeopleSerializer();

  private LocalDateTime birthDateOf(int year, Month month, int day) {
    return LocalDateTime.of(year, month, day, 0, 0, 0);
  }

  @Test
  void serializeDeserializePeople() throws IOException {

    People people = People.of(
      Person.newPerson("Jon", "Doe").born(birthDateOf(1981, Month.SEPTEMBER, 18)).asMale(),
      Person.newPerson("Jane", "Doe").born(birthDateOf(1989, Month.APRIL, 4)).asFemale(),
      Person.newPerson("Pie", "Doe").born(birthDateOf(2008, Month.AUGUST, 5)).asFemale()
    );

    byte[] data = this.peopleSerializer.serialize(people).array();
    byte[] javaSerializedBytes = IOUtils.serialize(people.stream().toList());

    assertThat(data).isNotNull().isNotEmpty();
    assertThat(javaSerializedBytes).isNotNull().isNotEmpty();
    assertThat(data).hasSizeLessThan(javaSerializedBytes.length);

    //System.out.printf("Protobuf message size [%d]; Java serialized bytes size [%d]%n",
    //  data.length, javaSerializedBytes.length);

    People deserializedPeople = this.peopleSerializer.deserialize(ByteBuffer.wrap(data));

    assertThat(deserializedPeople).isNotNull().isNotSameAs(people);
    assertThat(deserializedPeople).hasSameSizeAs(people);
    assertThat(deserializedPeople).containsAll(people);
  }
}
