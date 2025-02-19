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

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import org.cp.elements.security.model.User;

/**
 * Jackson {@link JsonDeserializer} implementation for Elements {@link User}.
 *
 * @author John Blum
 * @see com.fasterxml.jackson.databind.JsonDeserializer
 * @see org.cp.elements.security.model.User
 * @since 0.3.0
 */
@SuppressWarnings("rawtypes")
public class UserJsonDeserializer extends JsonDeserializer<User> {

  @Override
  @SuppressWarnings("all")
  public User deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
      throws IOException, JacksonException {

    JsonNode userNode = jsonParser.getCodec().readTree(jsonParser);
    String username = userNode.get("username").asText();
    User user = User.named(username);

    return user;
  }
}
