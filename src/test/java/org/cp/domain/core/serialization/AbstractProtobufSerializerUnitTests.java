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
package org.cp.domain.core.serialization;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.nio.ByteBuffer;
import java.util.Arrays;

import com.google.protobuf.Message;

import org.junit.jupiter.api.Test;

import org.cp.elements.lang.Constants;
import org.cp.elements.lang.IllegalTypeException;

/**
 * Unit Tests for {@link AbstractProtobufSerializer}.
 *
 * @author John Blum
 * @see org.junit.jupiter.api.Test
 * @see org.cp.domain.core.serialization.AbstractProtobufSerializer
 * @since 0.2.0
 */
public class AbstractProtobufSerializerUnitTests {

  private final AbstractProtobufSerializer serializer = new TestProtobufSerializer();

  @Test
  void serializesMessage() {

    byte[] messageBytes = "MESSAGE".getBytes();

    Message mockMessage = mock(Message.class);

    doReturn(messageBytes).when(mockMessage).toByteArray();

    assertThat(this.serializer.serialize(mockMessage)).isEqualTo(ByteBuffer.wrap(messageBytes));

    verify(mockMessage, times(1)).toByteArray();
    verifyNoMoreInteractions(mockMessage);
  }

  @Test
  void serializeInvalidMessage() {

    Arrays.asList(new Object(), "Message", null).forEach(object ->
      assertThatExceptionOfType(IllegalTypeException.class)
        .isThrownBy(() -> this.serializer.serialize(object))
        .withMessage("Object must be an instance of Message")
        .withNoCause());
    ;
  }

  static class TestProtobufSerializer extends AbstractProtobufSerializer {

    @Override
    public <T> T deserialize(ByteBuffer bytes) {
      throw new UnsupportedOperationException(Constants.NOT_IMPLEMENTED);
    }
  }
}
