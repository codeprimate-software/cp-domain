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
import static org.cp.elements.lang.ElementsExceptionsFactory.newDeserializationException;

import java.nio.ByteBuffer;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;

import org.cp.domain.core.model.Person;
import org.cp.domain.core.model.proto.PersonProto;
import org.cp.domain.core.serialization.protobuf.converters.PersonConverter;
import org.cp.domain.core.serialization.protobuf.converters.PersonProtoConverter;
import org.cp.elements.data.serialization.Serializer;

/**
 * {@link Serializer} used to de/serialize a {@link Person}.
 *
 * @author John Blum
 * @see java.nio.ByteBuffer
 * @see org.cp.domain.core.model.proto.PersonProto
 * @since 0.2.0
 */
public class PersonSerializer extends AbstractProtobufSerializer {

  private final PersonConverter personConverter = new PersonConverter();

  private final PersonProtoConverter personProtoConverter = new PersonProtoConverter();

  protected PersonConverter getPersonConverter() {
    return this.personConverter;
  }

  protected PersonProtoConverter getPersonProtoConverter() {
    return this.personProtoConverter;
  }

  @Override
  protected Message convert(Object target) {

    if (target instanceof Person person) {
      return getPersonConverter().convert(person);
    }

    throw newConversionException("Cannot convert [%s] into a Protobuf message".formatted(target));
  }

  @Override
  @SuppressWarnings("unchecked")
  public <T> T deserialize(ByteBuffer byteBuffer) {

    try {
      byte[] data = byteBuffer.array();
      return (T) getPersonProtoConverter().convert(PersonProto.Person.parseFrom(data));
    }
    catch (InvalidProtocolBufferException cause) {
      throw newDeserializationException(cause, "Failed to deserialize byte array into a Person");
    }
  }
}
