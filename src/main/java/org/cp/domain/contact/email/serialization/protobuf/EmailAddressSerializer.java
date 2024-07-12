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
package org.cp.domain.contact.email.serialization.protobuf;

import static org.cp.elements.lang.ElementsExceptionsFactory.newDeserializationException;

import java.nio.ByteBuffer;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;

import org.cp.domain.contact.email.model.EmailAddress;
import org.cp.domain.contact.email.model.proto.EmailProto;
import org.cp.domain.contact.email.serialization.protobuf.converters.EmailAddressConverter;
import org.cp.domain.contact.email.serialization.protobuf.converters.EmailAddressProtoConverter;
import org.cp.domain.core.serialization.protobuf.AbstractProtobufSerializer;
import org.cp.elements.data.conversion.ConversionException;
import org.cp.elements.data.serialization.Serializer;

/**
 * {@link Serializer} used to de/serialize an {@link EmailAddress} using Protobuf.
 *
 * @author John Blum
 * @see org.cp.domain.contact.email.model.EmailAddress
 * @see org.cp.domain.contact.email.model.proto.EmailProto
 * @see org.cp.domain.core.serialization.protobuf.AbstractProtobufSerializer
 * @since 0.2.0
 */
public class EmailAddressSerializer extends AbstractProtobufSerializer {

  private final EmailAddressConverter emailAddressConverter = new EmailAddressConverter();
  private final EmailAddressProtoConverter emailAddressProtoConverter = new EmailAddressProtoConverter();

  protected EmailAddressConverter getEmailAddressConverter() {
    return this.emailAddressConverter;
  }

  protected EmailAddressProtoConverter getEmailAddressProtoConverter() {
    return this.emailAddressProtoConverter;
  }

  @Override
  protected Message convert(Object target) {

    if (target instanceof EmailAddress emailAddress) {
      return getEmailAddressConverter().convert(emailAddress);
    }

    throw new ConversionException("Cannot convert [%s] into a Protobuf message".formatted(target));
  }

  @Override
  @SuppressWarnings("unchecked")
  public <T> T deserialize(ByteBuffer bytes) {

    try {
      byte[] data = bytes.array();
      return (T) getEmailAddressProtoConverter().convert(EmailProto.Email.parseFrom(data));
    }
    catch (InvalidProtocolBufferException cause) {
      throw newDeserializationException(cause, "Failed to deserialize byte array into an EmailAddress");
    }
  }
}
