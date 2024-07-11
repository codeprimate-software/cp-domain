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
package org.cp.domain.geo.serialization.protobuf;

import static org.cp.elements.lang.ElementsExceptionsFactory.newConversionException;
import static org.cp.elements.lang.ElementsExceptionsFactory.newDeserializationException;

import java.nio.ByteBuffer;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;

import org.cp.domain.core.serialization.protobuf.AbstractProtobufSerializer;
import org.cp.domain.geo.model.Address;
import org.cp.domain.geo.model.proto.GeoProto;
import org.cp.domain.geo.serialization.protobuf.converters.AddressConverter;
import org.cp.domain.geo.serialization.protobuf.converters.AddressProtoConverter;
import org.cp.elements.data.serialization.Serializer;

/**
 * {@link Serializer} used to de/serialize an {@link Address}.
 *
 * @author John Blum
 * @see org.cp.domain.geo.model.Address
 * @see org.cp.elements.data.serialization.Serializer
 * @since 0.2.0
 */
public class AddressSerializer extends AbstractProtobufSerializer {

  private final AddressConverter addressConverter = new AddressConverter();
  private final AddressProtoConverter addressProtoConverter = new AddressProtoConverter();

  protected AddressConverter getAddressConverter() {
    return this.addressConverter;
  }

  protected AddressProtoConverter getAddressProtoConverter() {
    return this.addressProtoConverter;
  }

  @Override
  protected Message convert(Object target) {

    if (target instanceof Address address) {
      return getAddressConverter().convert(address);
    }

    throw newConversionException("Cannot convert [%s] into a Protobuf message".formatted(target));
  }

  @Override
  @SuppressWarnings("unchecked")
  public <T> T deserialize(ByteBuffer bytes) {

    try {
      byte[] data = bytes.array();
      return (T) getAddressProtoConverter().convert(GeoProto.Address.parseFrom(data));
    }
    catch (InvalidProtocolBufferException cause) {
      throw newDeserializationException(cause, "Failed to deserialize byte array into an Address");
    }
  }
}
