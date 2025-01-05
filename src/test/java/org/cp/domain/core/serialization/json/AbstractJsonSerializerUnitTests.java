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
package org.cp.domain.core.serialization.json;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.cp.elements.lang.ThrowableAssertions.assertThatThrowableOfType;

import java.nio.ByteBuffer;
import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.jayway.jsonpath.JsonPath;

import org.junit.jupiter.api.Test;

import org.cp.elements.data.serialization.DeserializationException;
import org.cp.elements.data.serialization.SerializationException;
import org.cp.elements.lang.IllegalTypeException;
import org.cp.elements.lang.Nameable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * Unit Tests for {@link AbstractJsonSerializer}.
 *
 * @author John Blum
 * @see org.cp.domain.core.serialization.json.AbstractJsonSerializer
 * @see org.junit.jupiter.api.Test
 * @since 0.3.0
 */
public class AbstractJsonSerializerUnitTests {

  private final AbstractJsonSerializer<User> jsonSerializer = new TestJsonSerializer();

  @Test
  void serializeDeserializeUserAsJson() {

    User user = User.named("pieDoe");

    String json = this.jsonSerializer.serialize(user);

    assertThat(json).isNotBlank();
    assertThat(JsonPath.<String>read(json, "$.name")).isEqualTo("pieDoe");

    User deserializedUser = this.jsonSerializer.deserialize(json);

    assertThat(deserializedUser).isNotNull();
    assertThat(deserializedUser).isNotSameAs(user);
    assertThat(deserializedUser).isEqualTo(user);
  }

  @Test
  void serializeDeserializeUserAsJsonBytes() {

    User user = User.named("jonDoe");

    ByteBuffer jsonBytes = this.jsonSerializer.serialize((Object) user);

    assertThat(jsonBytes).isNotNull();
    assertThat(jsonBytes.hasRemaining()).isTrue();

    User deserializedUser = this.jsonSerializer.deserialize(jsonBytes);

    assertThat(deserializedUser).isNotNull();
    assertThat(deserializedUser).isNotSameAs(user);
    assertThat(deserializedUser).isEqualTo(user);
  }

  @Test
  void serializeInvalidObjects() {

    Arrays.asList(true, 'x', 1, Math.PI, "test").forEach(object ->
      assertThatExceptionOfType(IllegalTypeException.class)
        .isThrownBy(() -> this.jsonSerializer.serialize(object))
        .withMessage("Target [%s] must be a [%s]", object, User.class.getName())
        .withNoCause());
  }

  @Test
  void serializeInvalidUser() {

    User user = BadUser.named("TEST");

    assertThatThrowableOfType(SerializationException.class)
      .isThrownBy(args -> this.jsonSerializer.serialize(user))
      .havingMessage("Failed to serialize object [%s] as JSON", user)
      .causedBy(JsonProcessingException.class)
      .havingMessageContaining("age")
      .causedBy(IllegalStateException.class)
      .havingMessageContaining("TEST")
      .withNoCause();
  }

  @Test
  void serializeNullObject() {

      assertThatIllegalArgumentException()
        .isThrownBy(() -> this.jsonSerializer.serialize(null))
        .withMessage("Target to serialize as JSON is required")
        .withNoCause();
  }

  @Test
  void deserializeInvalidJson() {

    String JSON = "{\"name\":\"dillDoe\",\"age\":}";

    assertThatThrowableOfType(DeserializationException.class)
      .isThrownBy(args -> this.jsonSerializer.deserialize(JSON))
      .havingMessage("Failed to deserialize JSON [{0}] as [{1}]", JSON, User.class.getName())
      .causedBy(JsonProcessingException.class)
      .havingMessageContaining("Unexpected character")
      .withNoCause();
  }

  static class TestJsonSerializer extends AbstractJsonSerializer<User> {

    @Override
    protected Class<User> getType() {
      return User.class;
    }
  }

  @Getter
  @EqualsAndHashCode
  @ToString(of = "name")
  @RequiredArgsConstructor
  static class User implements JsonSerializable, Nameable<String> {

    @JsonCreator
    static User named(@JsonProperty("name") String name) {
      return new User(name);
    }

    private final String name;
  }

  static class BadUser extends User {

    static BadUser named(String name) {
      return new BadUser(name);
    }

    BadUser(String name) {
      super(name);
    }

    @SuppressWarnings("unused")
    public String getAge() {
      throw new IllegalStateException("TEST");
    }
  }
}
