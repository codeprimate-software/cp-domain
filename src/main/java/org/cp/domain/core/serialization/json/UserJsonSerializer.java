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

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import org.cp.elements.lang.Assert;
import org.cp.elements.security.model.User;

/**
 * Jackson {@link JsonSerializer} for an Elements {@link User}.
 *
 * @author John Blum
 * @see com.fasterxml.jackson.databind.JsonSerializer
 * @see org.cp.elements.security.model.User
 * @since 0.3.0
 */
@SuppressWarnings("rawtypes")
public class UserJsonSerializer extends JsonSerializer<User> {

  @Override
  public void serialize(User user, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
      throws IOException {

    Assert.notNull(user, "User to serialize as JSON is required");

    jsonGenerator.writeStartObject();
    jsonGenerator.writeStringField("username", user.getName().toString());
    jsonGenerator.writeEndObject();
  }
}
