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

import static org.cp.elements.lang.ElementsExceptionsFactory.newDeserializationException;
import static org.cp.elements.lang.ElementsExceptionsFactory.newSerializationException;

import java.nio.ByteBuffer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import org.cp.elements.lang.Assert;
import org.cp.elements.lang.annotation.NotNull;
import org.cp.elements.security.model.User;

/**
 * Abstract base class and {@link JsonSerializer} implementation used to de/serialize an {@link Object}
 * as {@literal JSON}.
 *
 * @author John Blum
 * @see com.fasterxml.jackson.databind.json.JsonMapper
 * @see org.cp.domain.core.serialization.json.JsonSerializable
 * @see org.cp.domain.core.serialization.json.JsonSerializer
 * @since 0.3.0
 */
public abstract class AbstractJsonSerializer<T extends JsonSerializable> implements JsonSerializer<T> {

  private final JsonMapper jsonMapper;

  public AbstractJsonSerializer() {

    JsonMapper jsonMapper = JsonMapper.builder()
      .configure(SerializationFeature.INDENT_OUTPUT, true)
      .configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false)
      .configure(DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES, true)
      .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
      .build();

    SimpleModule module = new SimpleModule();

    module.addSerializer(User.class, new UserJsonSerializer());
    module.addDeserializer(User.class, new UserJsonDeserializer());

    jsonMapper.registerModule(module);
    jsonMapper.findAndRegisterModules();

    this.jsonMapper = customize(jsonMapper);
  }

  protected JsonMapper getJsonMapper() {
    return this.jsonMapper;
  }

  protected abstract Class<T> getType();

  protected String getTypeName() {
    return getType().getName();
  }

  protected JsonMapper customize(JsonMapper jsonMapper) {
    return jsonMapper;
  }

  @Override
  public ByteBuffer serialize(Object target) {

    Assert.isInstanceOf(target, getType(), () -> "Target [%s] must be a [%s]".formatted(target, getType().getName()));

    T typedTarget = getType().cast(target);
    String json = serialize(typedTarget);
    byte[] bytes = json.getBytes();

    return ByteBuffer.wrap(bytes);
  }

  @Override
  public String serialize(@NotNull T target) {

    Assert.notNull(target, "Target to serialize as JSON is required");

    try {
      return getJsonMapper().writeValueAsString(target);
    }
    catch (JsonProcessingException cause) {
      throw newSerializationException(cause, "Failed to serialize object [{0}] as JSON", target);
    }
  }

  @Override
  @SuppressWarnings("unchecked")
  public <T> T deserialize(@NotNull ByteBuffer bytes) {
    Assert.notNull(bytes, "ByteBuffer is required");
    String json = new String(bytes.array());
    return (T) deserialize(json);
  }

  @Override
  public T deserialize(String json) {

    Assert.hasText(json, "JSON [%s] is required", json);

    try {
      return getJsonMapper().readValue(json, getType());
    }
    catch (JsonProcessingException cause) {
      throw newDeserializationException(cause, "Failed to deserialize JSON [{0}] as [{1}]", json, getTypeName());
    }
  }
}
