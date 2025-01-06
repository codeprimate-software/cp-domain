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

import java.io.IOException;
import java.util.UUID;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.jayway.jsonpath.JsonPath;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import org.cp.domain.core.model.People;
import org.cp.domain.core.model.Person;
import org.cp.elements.lang.IdentifierSequence;
import org.cp.elements.lang.support.SimpleIdentifierSequence;
import org.cp.elements.util.ArrayUtils;

/**
 * Unit Tests for {@link People} {@literal JSON} serialization.
 *
 * @author John Blum
 * @see org.cp.domain.core.model.People
 * @see org.junit.jupiter.api.Test
 * @since 0.3.0
 */
public class PeopleJsonSerializationUnitTests {

  private static final JsonMapper jsonMapper = JsonMapper.builder()
    .configure(SerializationFeature.INDENT_OUTPUT, true)
    .build();

  private final IdentifierSequence<Long> identifierSequence = new SimpleIdentifierSequence();

  @BeforeAll
  static void initializeJsonMapper() {
    jsonMapper.findAndRegisterModules();
  }

  Person identifyAndVersion(Person person) {

    return person
      .atVersion(UUID.randomUUID())
      .identifiedBy(identifierSequence.nextId());
  }

  @Test
  void serializeDeserializePeople() throws IOException {

    People people = People.of(
      identifyAndVersion(Person.newPerson("Jon", "Doe").asMale().age(48)),
      identifyAndVersion(Person.newPerson("Jane", "Doe").asFemale().age(42)),
      identifyAndVersion(Person.newPerson("Bob", "Doe").asMale().age(51)),
      identifyAndVersion(Person.newPerson("Cookie", "Doe").asFemale().age(8)),
      identifyAndVersion(Person.newPerson("Dill", "Doe").asMale().age(24)),
      identifyAndVersion(Person.newPerson("Fro", "Doe").asMale().age(21)),
      identifyAndVersion(Person.newPerson("Hoe", "Doe").asFemale().age(35)),
      identifyAndVersion(Person.newPerson("Joe", "Doe").asMale().age(12)),
      identifyAndVersion(Person.newPerson("Lan", "Doe").asMale().age(17)),
      identifyAndVersion(Person.newPerson("Moe", "Doe").asMale().age(28)),
      identifyAndVersion(Person.newPerson("Pie", "Doe").asFemale().age(16)),
      identifyAndVersion(Person.newPerson("Sour", "Doe").asMale().age(13))
    );

    String json = jsonMapper.writeValueAsString(people);

    //System.out.printf("JSON [%s]%n", json);

    assertThat(json).isNotBlank();
    assertThat(JsonPath.<Integer>read(json, "$.length()")).isEqualTo(people.size());

    People deserializedPeople = jsonMapper.readValue(json, People.class);

    assertThat(deserializedPeople).isNotNull();
    assertThat(deserializedPeople).isNotSameAs(people);
    assertThat(deserializedPeople).hasSize(people.size());
    assertThat(deserializedPeople).containsExactlyInAnyOrder(ArrayUtils.asArray(people, Person.class));
  }
}
