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
package org.cp.domain.core.serialization.protobuf.converters;

import java.time.LocalDateTime;
import java.util.Objects;

import org.cp.domain.core.enums.Gender;
import org.cp.domain.core.enums.GenderProto;
import org.cp.domain.core.model.Person;
import org.cp.domain.core.model.proto.NameProto;
import org.cp.domain.core.model.proto.PersonProto;
import org.cp.domain.core.time.proto.TimestampProto;
import org.cp.elements.data.conversion.AbstractConverter;
import org.cp.elements.data.conversion.Converter;
import org.cp.elements.lang.Assert;
import org.cp.elements.lang.CodeBlocks;
import org.cp.elements.lang.StringUtils;
import org.cp.elements.time.DateTimeUtils;

/**
 * {@link Converter} used to convert a {@link Person} to a Protobuf message.
 *
 * @author John Blum
 * @see org.cp.domain.core.model.Person
 * @see org.cp.domain.core.model.proto.PersonProto
 * @see org.cp.elements.data.conversion.AbstractConverter
 * @since 0.2.0
 */
public class PersonConverter extends AbstractConverter<Person, PersonProto.Person> {

  @Override
  public PersonProto.Person convert(Person person) {

    Assert.notNull(person, "Person to convert into a Protobuf message is required");

    NameProto.Name name = buildName(person);

    PersonProto.Person.Builder personBuilder = PersonProto.Person.newBuilder().setName(name);

    person.getBirthDate().ifPresent(birthDate -> personBuilder.setBirthDate(buildTimestamp(birthDate)));
    person.getDateOfDeath().ifPresent(deathDate -> personBuilder.setDeathDate(buildTimestamp(deathDate)));
    person.getGender().ifPresent(gender -> personBuilder.setGender(toGender(gender)));

    CodeBlocks.ifThen(person.getId(), Objects::nonNull, personBuilder::setId);

    return personBuilder.build();
  }

  private TimestampProto.Timestamp buildTimestamp(LocalDateTime dateTime) {

    return TimestampProto.Timestamp.newBuilder()
      .setMilliseconds(DateTimeUtils.toMilliseconds(dateTime))
      .build();
  }

  private NameProto.Name buildName(Person person) {

    NameProto.Name.Builder nameBuilder = NameProto.Name.newBuilder()
      .setFirstName(person.getFirstName())
      .setLastName(person.getLastName());

    return person.getMiddleName()
      .filter(StringUtils::hasText)
      .map(nameBuilder::setMiddleName)
      .map(NameProto.Name.Builder::build)
      .orElseGet(nameBuilder::build);
  }

  private GenderProto.Gender toGender(Gender gender) {

    return switch (gender) {
      case FEMALE -> GenderProto.Gender.FEMALE;
      case MALE -> GenderProto.Gender.MALE;
      default -> GenderProto.Gender.NON_BINARY;
    };
  }
}
