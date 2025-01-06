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

import java.io.IOException;

import com.fasterxml.jackson.databind.json.JsonMapper;
import com.jayway.jsonpath.JsonPath;

import org.junit.jupiter.api.Test;

import org.cp.domain.core.model.Name;

/**
 * Unit Tests for {@link Name} {@literal JSON} serialization.
 *
 * @author John Blum
 * @see org.cp.domain.core.model.Name
 * @see com.fasterxml.jackson.databind.json.JsonMapper
 * @see com.jayway.jsonpath.JsonPath
 * @see org.junit.jupiter.api.Test
 * @since 0.3.0
 */
public class NameJsonSerializationUnitTests {

  private final JsonMapper jsonMapper = JsonMapper.builder().build();

  @Test
  void serializeDeserializeName() throws IOException {

    Name name = Name.of("Jon", "R", "Doe");

    String json = this.jsonMapper.writeValueAsString(name);

    assertThat(json).isNotBlank();
    assertThat(JsonPath.<String>read(json, "$.firstName")).isEqualTo("Jon");
    assertThat(JsonPath.<String>read(json, "$.lastName")).isEqualTo("Doe");

    Name deserializedName = this.jsonMapper.readValue(json, Name.class);

    assertThat(deserializedName).isNotNull();
    assertThat(deserializedName).isNotSameAs(name);
    assertThat(deserializedName).isEqualTo(name);
  }
}
