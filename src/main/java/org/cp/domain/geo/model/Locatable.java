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

import java.util.Optional;

/**
 * The {@link Locatable} interface defines a contract for implementing application domain objects
 * that can be geographically positioned on a map.
 *
 * @author John Blum
 * @see org.cp.domain.geo.model.Coordinates
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public interface Locatable {

  /**
   * Returns the geographic {@link Coordinates} associated with this object.
   *
   * @return the geographic {@link Coordinates} associated with this object.
   * @see org.cp.domain.geo.model.Coordinates
   */
  Optional<Coordinates> getCoordinates();

  /**
   * Sets the geographic {@link Coordinates} to associate with this object.
   *
   * @param coordinates geographic {@link Coordinates} to associate with this object.
   * @see org.cp.domain.geo.model.Coordinates
   */
  void setCoordinates(Coordinates coordinates);

}
