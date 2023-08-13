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

import static org.cp.elements.lang.RuntimeExceptionsFactory.newUnsupportedOperationException;

import java.util.Objects;

import org.cp.elements.lang.Constants;

/**
 * Interface defining a contract for application domain objects that have a postal {@link Address},
 * such as a {@literal household} or a {@literal store}.
 *
 * @author John Blum
 * @see java.lang.FunctionalInterface
 * @see org.cp.domain.geo.model.Address
 * @since 0.1.0
 */
@FunctionalInterface
@SuppressWarnings("unused")
public interface Addressable<T> {

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
   * @throws UnsupportedOperationException by default.
   * @see org.cp.domain.geo.model.Address
   */
  default void setAddress(Address address) {
    throw newUnsupportedOperationException(Constants.NOT_IMPLEMENTED);
  }

  /**
   * Builder method used to set the postal {@link Address} locating this {@link Addressable object}.
   *
   * @param address {@link Address} locating this {@link Addressable object}.
   * @return this {@link Addressable object}.
   * @see org.cp.domain.geo.model.Address
   * @see #setAddress(Address)
   */
  @SuppressWarnings("unchecked")
  default T withAddress(Address address) {
    setAddress(address);
    return (T) this;
  }
}
