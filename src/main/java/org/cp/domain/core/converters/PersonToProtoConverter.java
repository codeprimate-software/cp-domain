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

import java.time.LocalDateTime;

import org.cp.domain.core.enums.GenderProto;
import org.cp.domain.core.model.Person;
import org.cp.domain.core.model.proto.NameProto;
import org.cp.domain.core.model.proto.PersonProto;
import org.cp.domain.core.time.proto.TimestampProto;
import org.cp.elements.data.conversion.AbstractConverter;
import org.cp.elements.data.conversion.Converter;
import org.cp.elements.time.DateTimeUtils;

/**
 * {@link Converter} used to convert a {@link Person} to a {@link PersonProto}.
 *
 * @author John Blum
 * @see org.cp.domain.core.model.Person
 * @see org.cp.elements.data.conversion.AbstractConverter
 * @since 0.2.0
 */
public class PersonToProtoConverter extends AbstractConverter<Person, PersonProto.Person> {

  @Override
  public PersonProto.Person convert(Person person) {

    NameProto.Name name = NameProto.Name.newBuilder()
      .setFirstName(person.getFirstName())
      .setLastName(person.getLastName())
      .setMiddleName(person.getMiddleName().orElse(null))
      .build();

    return PersonProto.Person.newBuilder()
      .setId(person.getId())
      .setName(name)
      .setBirthDate(toTimestamp(person.getBirthDate().orElse(null)))
      .setDeathDate(toTimestamp(person.getDateOfDeath().orElse(null)))
      .setGender(toGender(person))
      .build();
  }

  private TimestampProto.Timestamp toTimestamp(LocalDateTime dateTime) {

    return dateTime != null
      ? TimestampProto.Timestamp.newBuilder().setMilliseconds(DateTimeUtils.toMilliseconds(dateTime)).build()
      : null;
  }

  private GenderProto.Gender toGender(Person person) {

    return person.getGender()
      .map(gender -> {
        switch (gender) {
          case FEMALE -> {
            return GenderProto.Gender.FEMALE;
          }
          case MALE -> {
            return GenderProto.Gender.MALE;
          }
          default -> {
            return null;
          }
        }
      })
      .orElse(null);
  }
}
