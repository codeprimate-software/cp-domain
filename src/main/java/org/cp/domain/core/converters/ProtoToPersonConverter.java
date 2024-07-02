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
package org.cp.domain.core.converters;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.cp.domain.core.enums.Gender;
import org.cp.domain.core.model.Name;
import org.cp.domain.core.model.Person;
import org.cp.domain.core.model.proto.NameProto;
import org.cp.domain.core.model.proto.PersonProto;
import org.cp.domain.core.time.proto.TimestampProto;
import org.cp.elements.data.conversion.AbstractConverter;
import org.cp.elements.data.conversion.Converter;
import org.cp.elements.lang.Assert;

/**
 * {@link Converter} used to convert a {@link PersonProto} to a {@link Person}.
 *
 * @author John Blum
 * @see org.cp.domain.core.model.Person
 * @see org.cp.domain.core.model.proto.PersonProto
 * @see org.cp.elements.data.conversion.AbstractConverter
 * @since 0.2.0
 */
public class ProtoToPersonConverter extends AbstractConverter<PersonProto.Person, Person> {

  @Override
  public Person convert(PersonProto.Person person) {

    Assert.notNull(person, "Person message to convert is required");

    Long id = person.hasId() ? person.getId() : null;

    LocalDateTime birthDate = person.hasBirthDate() ? toLocalDateTime(person.getBirthDate()) : null;
    LocalDateTime deathDate = person.hasDeathDate() ? toLocalDateTime(person.getDeathDate()) : null;

    Gender gender = person.hasGender() ? toGender(person) : null;

    return Person.newPerson(toName(person), birthDate)
      .died(deathDate)
      .as(gender)
      .identifiedBy(id);
  }

  private Name toName(PersonProto.Person person) {

    NameProto.Name name = person.getName();

    return Name.of(name.getFirstName(), name.getMiddleName(), name.getLastName());
  }

  private LocalDateTime toLocalDateTime(TimestampProto.Timestamp timestamp) {

    return timestamp != null
      ? LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp.getMilliseconds()), ZoneOffset.systemDefault())
      : null;
  }

  private Gender toGender(PersonProto.Person person) {

    return switch(person.getGender()) {
      case FEMALE -> Gender.FEMALE;
      case MALE -> Gender.MALE;
      case NON_BINARY ->  Gender.NON_BINARY;
      default -> null;
    };
  }
}
