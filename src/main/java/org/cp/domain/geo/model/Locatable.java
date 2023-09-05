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
package org.cp.domain.geo.model;

import static org.cp.elements.lang.RuntimeExceptionsFactory.newUnsupportedOperationException;

import java.util.Optional;

/**
 * Interface defining a contract for application domain objects that can be geographically positioned on a map,
 * or are capable of being located.
 *
 * @author John Blum
 * @param <T> concrete {@link Class type} of this {@link Locatable object}.
 * @see org.cp.domain.geo.model.Coordinates
 * @since 0.1.0
 */
@SuppressWarnings("unused")
public interface Locatable<T> {

  /**
   * Determines whether this {@link Object} has been geographically located.
   *
   * @return a {@link Boolean value} indicating whether this {@link Object} has been geographically located.
   * @see #getCoordinates()
   */
  default boolean isLocated() {
    return getCoordinates().isPresent();
  }

  /**
   * Return geographic {@link Coordinates}, such as {@literal latitude}, {@literal longitude} and {@literal altitude},
   * associated with this {@link Locatable object}.
   * <p>
   * Returns {@link Optional#empty()} by default.
   *
   * @return geographic {@link Coordinates} associated with this {@link Locatable object}.
   * Returns {@link Optional#empty()} by default.
   * @see org.cp.domain.geo.model.Coordinates
   */
  default Optional<Coordinates> getCoordinates() {
    return Optional.empty();
  }

  /**
   * Sets geographic {@link Coordinates}, such as {@literal latitude}, {@literal longitude} and {@literal altitude},
   * for this {@link Locatable object}.
   * <p>
   * Most goecoding, or geomapping, services adjust geographic coordinates at a particular location overtime,
   * such as an address, hence the reason {@link Coordinates} can be mutated.
   *
   * @param coordinates geographic {@link Coordinates} to set for this {@link Locatable object}.
   * @see org.cp.domain.geo.model.Coordinates
   */
  default void setCoordinates(Coordinates coordinates) {
    throw newUnsupportedOperationException("Setting Coordinates on a Locatable object of type [%s] is not supported",
      getClass().getName());
  }

  /**
   * Builder method used to set the geographic {@link Coordinates} of this {@link Locatable object}.
   *
   * @param coordinates geographic {@link Coordinates} to set for this {@link Locatable object}.
   * @return this {@link Locatable object}.
   * @see org.cp.domain.geo.model.Coordinates
   * @see #setCoordinates(Coordinates)
   */
  @SuppressWarnings("unchecked")
  default T at(Coordinates coordinates) {
    setCoordinates(coordinates);
    return (T) this;
  }
}
