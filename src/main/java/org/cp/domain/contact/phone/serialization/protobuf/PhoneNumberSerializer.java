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
package org.cp.domain.contact.phone.serialization.protobuf;

import static org.cp.elements.lang.ElementsExceptionsFactory.newDeserializationException;

import java.nio.ByteBuffer;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;

import org.cp.domain.contact.phone.model.PhoneNumber;
import org.cp.domain.contact.phone.model.proto.PhoneNumberProto;
import org.cp.domain.contact.phone.serialization.protobuf.converters.PhoneNumberConverter;
import org.cp.domain.contact.phone.serialization.protobuf.converters.PhoneNumberProtoConverter;
import org.cp.domain.core.serialization.protobuf.AbstractProtobufSerializer;
import org.cp.elements.data.conversion.ConversionException;
import org.cp.elements.data.serialization.Serializer;

/**
 * {@link Serializer} used to de/serialize a {@link PhoneNumber} using Protobuf.
 *
 * @author John Blum
 * @see org.cp.domain.contact.phone.model.PhoneNumber
 * @see org.cp.domain.contact.phone.model.proto.PhoneNumberProto
 * @see org.cp.domain.core.serialization.protobuf.AbstractProtobufSerializer
 * @since 0.2.0
 */
public class PhoneNumberSerializer extends AbstractProtobufSerializer {

  private final PhoneNumberConverter phoneNumberConverter = new PhoneNumberConverter();
  private final PhoneNumberProtoConverter phoneNumberProtoConverter = new PhoneNumberProtoConverter();

  protected PhoneNumberConverter getPhoneNumberConverter() {
    return this.phoneNumberConverter;
  }

  protected PhoneNumberProtoConverter getPhoneNumberProtoConverter() {
    return this.phoneNumberProtoConverter;
  }

  @Override
  protected Message convert(Object target) {

    if (target instanceof PhoneNumber phoneNumber) {
      return getPhoneNumberConverter().convert(phoneNumber);
    }

    throw new ConversionException("Cannot convert [%s] into a Protobuf message".formatted(target));
  }

  @Override
  @SuppressWarnings("unchecked")
  public <T> T deserialize(ByteBuffer bytes) {

    try {
      byte[] data = bytes.array();
      return (T) getPhoneNumberProtoConverter().convert(PhoneNumberProto.PhoneNumber.parseFrom(data));
    }
    catch (InvalidProtocolBufferException cause) {
      throw newDeserializationException(cause, "Failed to deserialize byte array as a PhoneNumber");
    }
  }
}
