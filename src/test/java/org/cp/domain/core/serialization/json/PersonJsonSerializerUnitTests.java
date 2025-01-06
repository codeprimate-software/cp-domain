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
package org.cp.domain.core.serialization.json;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.UUID;

import com.jayway.jsonpath.JsonPath;

import org.junit.jupiter.api.Test;

import org.cp.domain.core.model.Person;

/**
 * Unit Tests for {@link PersonJsonSerializer}.
 *
 * @author John Blum
 * @see org.cp.domain.core.model.Person
 * @see org.cp.domain.core.serialization.json.PersonJsonSerializer
 * @see org.junit.jupiter.api.Test
 * @since 0.3.0
 */
public class PersonJsonSerializerUnitTests {

  private final PersonJsonSerializer jsonSerializer = new PersonJsonSerializer();

  @Test
  void serializeDeserializePersonAsJson() {

    LocalDateTime birthDate = LocalDateTime.of(2000, Month.APRIL, 1, 12, 30, 45, 0);

    Person person = Person.newPerson("Jon", "Doe", birthDate)
      .asMale()
      .atVersion(UUID.randomUUID())
      .identifiedBy(1L);

    String json = this.jsonSerializer.serialize(person);

    assertThat(json).isNotBlank();
    assertThat(JsonPath.<String>read(json, "$.firstName")).isEqualTo("Jon");
    assertThat(JsonPath.<String>read(json, "$.lastName")).isEqualTo("Doe");

    Person deserializedPerson = this.jsonSerializer.deserialize(json);

    assertThat(deserializedPerson).isNotNull();
    assertThat(deserializedPerson).isNotSameAs(person);
    assertThat(deserializedPerson).isEqualTo(person);
  }
}
