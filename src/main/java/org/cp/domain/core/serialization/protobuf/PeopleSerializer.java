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

import static org.cp.elements.lang.ElementsExceptionsFactory.newConversionException;

import java.nio.ByteBuffer;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;

import org.cp.domain.core.model.People;
import org.cp.domain.core.model.proto.PeopleProto;
import org.cp.domain.core.serialization.protobuf.converters.PeopleConverter;
import org.cp.domain.core.serialization.protobuf.converters.PeopleProtoConverter;
import org.cp.elements.data.serialization.DeserializationException;
import org.cp.elements.data.serialization.Serializer;
import org.cp.elements.lang.Assert;

/**
 * {@link Serializer} used to de/serialize a group of {@link People}.
 *
 * @author John Blum
 * @see org.cp.domain.core.model.People
 * @see AbstractProtobufSerializer
 * @see org.cp.elements.data.serialization.Serializer
 * @since 0.2.0
 */
public class PeopleSerializer extends AbstractProtobufSerializer {

  private final PeopleConverter peopleConverter = new PeopleConverter();

  private final PeopleProtoConverter peopleProtoConverter = new PeopleProtoConverter();

  protected PeopleConverter getPeopleConverter() {
    return this.peopleConverter;
  }

  protected PeopleProtoConverter getPeopleProtoConverter() {
    return this.peopleProtoConverter;
  }

  @Override
  protected Message convert(Object target) {

    if (target instanceof People people) {
      return getPeopleConverter().convert(people);
    }

    throw newConversionException("Cannot convert [%s] into a Protobuf message", target);
  }

  @Override
  @SuppressWarnings("unchecked")
  public <T> T deserialize(ByteBuffer bytes) {

    Assert.notNull(bytes, "Data to deserialize is required");

    try {
      byte[] data = bytes.array();
      return (T) getPeopleProtoConverter().convert(PeopleProto.People.parseFrom(data));
    }
    catch (InvalidProtocolBufferException cause) {
      throw new DeserializationException("Failed to deserialize byte array into a group of People");
    }
  }
}
