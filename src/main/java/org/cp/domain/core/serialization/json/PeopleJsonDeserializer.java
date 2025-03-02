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
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import org.cp.domain.core.model.People;
import org.cp.domain.core.model.Person;
import org.cp.elements.lang.Assert;
import org.cp.elements.lang.ObjectUtils;

/**
 * Jackson {@link JsonDeserializer} for {@link People}.
 *
 * @author John Blum
 * @see com.fasterxml.jackson.core.JsonParser
 * @see com.fasterxml.jackson.databind.JsonDeserializer
 * @see org.cp.domain.core.model.People
 * @since 0.3.0
 */
public class PeopleJsonDeserializer extends JsonDeserializer<People> {

  @Override
  public People deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {

    JsonNode peopleNode = jsonParser.getCodec().readTree(jsonParser);

    Assert.isTrue(peopleNode.isArray(), "Expected an array from JSON [%s]", peopleNode.getNodeType());

    List<Person> personList = new ArrayList<>();

    peopleNode.forEach(personNode -> {
      Person person = ObjectUtils.doSafely(args ->
        deserializationContext.readTreeAsValue(personNode, Person.class));
      personList.add(person);
    });

    return People.of(personList);
  }
}
