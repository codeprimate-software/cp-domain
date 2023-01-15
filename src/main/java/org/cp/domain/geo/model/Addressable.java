/*
 * Copyright 2016 Author or Authors.
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
package org.cp.domain.geo.model;

import java.util.Objects;

/**
 * Interface defining a contract for application domain objects that have a postal {@link Address},
 * such as a {@literal household} or a {@literal store}.
 *
 * @author John Blum
 * @see org.cp.domain.geo.model.Address
 * @since 0.1.0
 */
@SuppressWarnings("unused")
public interface Addressable {

  /**
   * Determines whether this {@link Object} has an {@link Address}.
   *
   * @return a {@link Boolean} value indicating whether this {@link Object} has an {@link Address}.
   * @see #getAddress()
   */
  default boolean isAddressPresent() {
    return Objects.nonNull(getAddress());
  }

  /**
   * Returns the postal {@link Address} locating this {@link Object}.
   *
   * @return the postal {@link Address} locating this {@link Object}.
   * @see org.cp.domain.geo.model.Address
   */
  Address getAddress();

  /**
   * Set the postal {@link Address} locating this {@link Object}.
   *
   * @param address postal {@link Address} locating this {@link Object}.
   * @see org.cp.domain.geo.model.Address
   */
  void setAddress(Address address);

}
