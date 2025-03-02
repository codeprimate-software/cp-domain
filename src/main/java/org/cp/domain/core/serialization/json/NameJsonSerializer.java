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

import org.cp.domain.core.model.Name;
import org.cp.elements.lang.ObjectUtils;
import org.cp.elements.lang.StringUtils;
import org.cp.elements.lang.ThrowableOperation;

/**
 * Jackson {@link JsonSerializer} implementation for {@link Name}.
 *
 * @author John Blum
 * @see org.cp.domain.core.model.Name
 * @see com.fasterxml.jackson.core.JsonGenerator
 * @see com.fasterxml.jackson.databind.JsonSerializer
 * @since 0.3.0
 */
public class NameJsonSerializer extends JsonSerializer<Name> {

  @Override
  public void serialize(Name name, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {

    jsonGenerator.writeStartObject();
    jsonGenerator.writeStringField("firstName", name.getFirstName());
    jsonGenerator.writeStringField("lastName", name.getLastName());

    name.getMiddleName()
      .filter(StringUtils::hasText)
      .ifPresent(middleName -> ObjectUtils.doSafely(ThrowableOperation.fromVoidReturning(args ->
        jsonGenerator.writeStringField("middleName", middleName))));

    jsonGenerator.writeEndObject();
  }
}
