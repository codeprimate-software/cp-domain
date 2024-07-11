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

import com.google.protobuf.Message;

import org.cp.domain.core.model.People;
import org.cp.domain.core.model.proto.PeopleProto;
import org.cp.elements.data.conversion.AbstractConverter;
import org.cp.elements.data.conversion.Converter;

/**
 * {@link Converter} used to convert {@link People} into a Protobuf {@link Message}.
 *
 * @author John Blum
 * @see org.cp.domain.core.model.People
 * @see org.cp.domain.core.model.proto.PeopleProto
 * @see org.cp.elements.data.conversion.AbstractConverter
 * @since 0.2.0
 */
public class PeopleConverter extends AbstractConverter<People, PeopleProto.People> {

  private final PersonConverter personConverter = new PersonConverter();

  protected PersonConverter getPersonConverter() {
    return this.personConverter;
  }

  @Override
  public PeopleProto.People convert(People people) {

    PeopleProto.People.Builder peopleBuilder = PeopleProto.People.newBuilder();

    people.forEach(person -> peopleBuilder.addPerson(getPersonConverter().convert(person)));

    return peopleBuilder.build();
  }
}
