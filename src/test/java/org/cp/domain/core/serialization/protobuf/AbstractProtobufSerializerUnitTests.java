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

import static org.assertj.core.api.Assertions.assertThat;
import static org.cp.elements.lang.RuntimeExceptionsFactory.newIllegalArgumentException;
import static org.cp.elements.lang.ThrowableAssertions.assertThatThrowableOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.nio.ByteBuffer;

import com.google.protobuf.Message;

import org.junit.jupiter.api.Test;

import org.cp.elements.data.conversion.ConversionException;
import org.cp.elements.data.serialization.SerializationException;

/**
 * Unit Tests for {@link AbstractProtobufSerializer}.
 *
 * @author John Blum
 * @see org.junit.jupiter.api.Test
 * @see org.cp.domain.core.serialization.protobuf.AbstractProtobufSerializer
 * @since 0.2.0
 */
public class AbstractProtobufSerializerUnitTests {

  @Test
  void serializesMessage() {

    byte[] messageBytes = "MESSAGE".getBytes();

    Message mockMessage = mock(Message.class);

    doReturn(messageBytes).when(mockMessage).toByteArray();

    AbstractProtobufSerializer serializer = mock(AbstractProtobufSerializer.class);

    doCallRealMethod().when(serializer).serialize(any());
    doCallRealMethod().when(serializer).toMessage(any());
    doReturn(mockMessage).when(serializer).convert(any());

    assertThat(serializer.serialize(TextMessage.from("TEST"))).isEqualTo(ByteBuffer.wrap(messageBytes));

    verify(serializer, times(1)).serialize(isA(TextMessage.class));
    verify(serializer, times(1)).toMessage(isA(TextMessage.class));
    verify(serializer, times(1)).convert(isA(TextMessage.class));
    verify(mockMessage, times(1)).toByteArray();
    verifyNoMoreInteractions(serializer, mockMessage);
  }

  @Test
  void serializeThrowsSerializationException() {

    AbstractProtobufSerializer serializer = mock(AbstractProtobufSerializer.class);

    doCallRealMethod().when(serializer).serialize(any());
    doCallRealMethod().when(serializer).toMessage(any());
    doThrow(newIllegalArgumentException("TEST")).when(serializer).convert(any());

    assertThatThrowableOfType(SerializationException.class)
      .isThrownBy(args -> serializer.serialize("MOCK"))
      .havingMessage("Failed to serialize Object of type [java.lang.String] using Protobuf")
      .causedBy(ConversionException.class)
      .havingMessage("Failed to convert [MOCK] into a Protobuf message")
      .causedBy(IllegalArgumentException.class)
      .havingMessage("TEST")
      .withNoCause();

    verify(serializer, times(1)).serialize(eq("MOCK"));
    verify(serializer, times(1)).toMessage(eq("MOCK"));
    verify(serializer, times(1)).convert(eq("MOCK"));
    verifyNoMoreInteractions(serializer);
  }

  @Test
  void toMessageWithMessageReturnsMessage() {

    Message mockMessage = mock(Message.class);

    AbstractProtobufSerializer serializer = mock(AbstractProtobufSerializer.class);

    doCallRealMethod().when(serializer).toMessage(any());

    assertThat(serializer.toMessage(mockMessage)).isSameAs(mockMessage);

    verify(serializer, times(1)).toMessage(eq(mockMessage));
    verifyNoMoreInteractions(serializer);
    verifyNoInteractions(mockMessage);
  }

  @Test
  void toMessageWithObjectCallsConvert() {

    Message mockMessage = mock(Message.class);
    TextMessage mockTextMessage = mock(TextMessage.class);

    AbstractProtobufSerializer serializer = mock(AbstractProtobufSerializer.class);

    doCallRealMethod().when(serializer).toMessage(any());
    doReturn(mockMessage).when(serializer).convert(any());

    assertThat(serializer.toMessage(mockTextMessage)).isEqualTo(mockMessage);

    verify(serializer, times(1)).toMessage(eq(mockTextMessage));
    verify(serializer, times(1)).convert(eq(mockTextMessage));
    verifyNoInteractions(mockTextMessage, mockMessage);
    verifyNoMoreInteractions(serializer);
  }

  @Test
  void toMessageThrowsConversionException() {

    AbstractProtobufSerializer serializer = mock(AbstractProtobufSerializer.class);

    doCallRealMethod().when(serializer).toMessage(any());
    doThrow(newIllegalArgumentException("TEST")).when(serializer).convert(any());

    assertThatThrowableOfType(ConversionException.class)
      .isThrownBy(args -> serializer.toMessage("MOCK"))
      .havingMessage("Failed to convert [MOCK] into a Protobuf message")
      .causedBy(IllegalArgumentException.class)
      .havingMessage("TEST")
      .withNoCause();

    verify(serializer, times(1)).toMessage(eq("MOCK"));
    verify(serializer, times(1)).convert(eq("MOCK"));
    verifyNoMoreInteractions(serializer);
  }

  @FunctionalInterface
  @SuppressWarnings("unused")
  interface TextMessage {

    static TextMessage from(String body) {
      return () -> body;
    }

    String getBody();

  }
}
