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
import static org.cp.elements.lang.ElementsExceptionsFactory.newSerializationException;

import java.nio.ByteBuffer;

import com.google.protobuf.Message;

import org.cp.elements.data.serialization.Serializer;
import org.cp.elements.lang.ClassUtils;

/**
 * Abstract base class and implementation of {@link Serializer} used to de/serialize Google Protobuf
 * {@link Message Messages}.
 *
 * @author John Blum
 * @see java.nio.ByteBuffer
 * @see com.google.protobuf.Message
 * @see org.cp.elements.data.serialization.Serializer
 * @since 0.2.0
 */
public abstract class AbstractProtobufSerializer implements Serializer {

  @Override
  public ByteBuffer serialize(Object target) {

    try {
      return ByteBuffer.wrap(toMessage(target).toByteArray());
    }
    catch (Throwable t) {
      throw newSerializationException(t, "Failed to serialize Object of type [%s] using Protobuf"
        .formatted(ClassUtils.getClassName(target)));
    }
  }

  protected Message toMessage(Object target) {

    try {
      return target instanceof Message message ? message : convert(target);
    }
    catch (Throwable t) {
      throw newConversionException(t, "Failed to convert [%s] into a Protobuf message".formatted(target));
    }
  }

  protected abstract Message convert(Object target);

}
