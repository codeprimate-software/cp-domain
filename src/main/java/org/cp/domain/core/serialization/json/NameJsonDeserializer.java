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

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import org.cp.domain.core.model.Name;

/**
 * Jackson {@link JsonDeserializer} implementation for {@link Name}.
 *
 * @author John Blum
 * @see org.cp.domain.core.model.Name
 * @see com.fasterxml.jackson.core.JsonParser
 * @see com.fasterxml.jackson.databind.JsonDeserializer
 * @since 0.3.0
 */
public class NameJsonDeserializer extends JsonDeserializer<Name> {

  @Override
  public Name deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {

    ObjectCodec objectCodec = jsonParser.getCodec();
    JsonNode jsonNode = objectCodec.readTree(jsonParser);

    String firstName = jsonNode.get("firstName").asText();
    String lastName = jsonNode.get("lastName").asText();
    String middleName = jsonNode.has("middleName") ? jsonNode.get("middleName").asText()
      : Name.NO_MIDDLE_NAME;

    return Name.of(firstName, middleName, lastName);
  }
}
