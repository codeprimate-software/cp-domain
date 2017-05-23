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

/**
 * The {@link Addressable} interface defines a contract for implementing application domain objects that physically
 * have an {@link Address}, such as a household or store, etc.
 *
 * @author John Blum
 * @see org.cp.domain.geo.model.Address
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public interface Addressable {

  /**
   * Returns the {@link Address} associated with this object.
   *
   * @return the {@link Address} associated with this object.
   * @see org.cp.domain.geo.model.Address
   */
  Address getAddress();

  /**
   * Sets the {@link Address} to associate with this object.
   *
   * @param address {@link Address} to associate with this object.
   * @see org.cp.domain.geo.model.Address
   */
  void setAddress(Address address);

}
