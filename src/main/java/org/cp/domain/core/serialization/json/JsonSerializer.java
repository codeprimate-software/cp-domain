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

import org.cp.elements.data.serialization.Serializer;

/**
 * Serializer for {@literal JSON}.
 *
 * @author John Blum
 * @param <T> {@link Class type} of {@link Object} to serialize to {@literal JSON}
 * @see org.cp.domain.core.serialization.json.JsonSerializable
 * @see org.cp.elements.data.serialization.Serializer
 * @since 0.3.0
 */
public interface JsonSerializer<T extends JsonSerializable> extends Serializer {

  /**
   * Serialize the {@link T target} to {@literal JSON}.
   *
   * @param target {@link T object} to serialize to {@literal JSON}.
   * @return the {@link T target} serialized as {@literal JSON}.
   */
  String serialize(T target);

  /**
   * Deserialize the {@literal JSON} into an {@link Object} of {@link Class type} {@link T}.
   *
   * @param json {@literal JSON} to deserialize as an {@link Object}.
   * @return the {@literal JSON} into an {@link Object} of {@link Class type} {@link T}.
   */
  T deserialize(String json);

}
