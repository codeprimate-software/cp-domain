/*
 * Copyright 2017 Author or Authors.
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

import java.awt.Point;
import java.io.Serializable;

import org.cp.elements.enums.Distance;
import org.cp.elements.lang.Assert;
import org.cp.elements.lang.ObjectUtils;

/**
 * The {@link Coordinates} class is an Abstract Data Type (ADT) that models geographic coordinates
 * including latitude, longitude and elevation as represented on a map of the world.
 *
 * @author John Blum
 * @see java.awt.Point
 * @see java.io.Serializable
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public class Coordinates implements Serializable {

  protected static final String COORDINATES_TO_STRING = "[latitude: %1$s, longitude: %2$s; @ elevation: %3$s]";

  /**
   * Factory method used to construct a new instance of {@link Coordinates} initialized with
   * the given {@link Double latitude} and {@link Double longitude}.
   *
   * @param latitude {@link Double latitude} of the geographic coordinates.
   * @param longitude {@link Double longitude} of the geographic coordinates.
   * @return a new set of {@link Coordinates} initialized with the given {@link Double latitude}
   * and {@link Double longitude}.
   * @see #Coordinates(double, double)
   */
  public static Coordinates of(double latitude, double longitude) {
    return new Coordinates(latitude, longitude);
  }

  /**
   * Factory method used to construct a new instance of {@link Coordinates} initialized from the given {@link Point}.
   *
   * @param point {@link Point} used to initialize the new {@link Coordinates}; must not be {@literal null}.
   * @return a new set of {@link Coordinates} initialized with the given {@link Point}.
   * @throws IllegalArgumentException if {@link Point} is {@literal null}.
   * @see #of(double, double)
   * @see java.awt.Point
   */
  public static Coordinates from(Point point) {

    Assert.notNull(point, "Point is required");

    return of(point.getX(), point.getY());
  }

  // North-South position on the Earth's surface.
  private final double latitude;

  // East-West position on the Earth's surface.
  private final double longitude;

  // Altitude at these geographic coordinates.
  private Elevation elevation;

  /**
   * Constructs a new instance of {@link Coordinates} initialized with
   * the given {@link Double latitude} and {@link Double longitude}.
   *
   * @param latitude {@link Double latitude} of the geographic coordinates.
   * @param longitude {@link Double longitude} of the geographic coordinates.
   */
  public Coordinates(double latitude, double longitude) {

    this.elevation = Elevation.of(0.0d);
    this.latitude = latitude;
    this.longitude = longitude;
  }

  /**
   * Returns the {@link Elevation} at these {@link Coordinates}.
   *
   * @return the {@link Elevation} at these {@link Coordinates}.
   * @see org.cp.domain.geo.model.Elevation
   */
  public Elevation getElevation() {
    return this.elevation;
  }

  /**
   * Returns the {@link Double latitude} at these {@link Coordinates}.
   *
   * @return the {@link Double latitude} at these {@link Coordinates}.
   */
  public double getLatitude() {
    return this.latitude;
  }

  /**
   * Returns the {@link Double longitude} at these {@link Coordinates}.
   *
   * @return the {@link Double longitude} at these {@link Coordinates}.
   */
  public double getLongitude() {
    return this.longitude;
  }

  /**
   * Returns these {@link Coordinates} as a {@link Point}.
   *
   * @return these {@link Coordinates} as a {@link Point}.
   * @see java.awt.Point
   */
  public Point asPoint() {
    return new Point(Double.valueOf(getLatitude()).intValue(), Double.valueOf(getLongitude()).intValue());
  }

  /**
   * Sets the {@link Double elevation} using the {@link Distance#getDefault() default unit of measeurement}
   * at these {@link Coordinates}.
   *
   * @param elevation {@link Double elevation} at these {@link Coordinates}.
   * @return these {@link Coordinates}.
   * @see org.cp.elements.enums.Distance#getDefault()
   * @see #at(double, Distance)
   */
  public Coordinates at(double elevation) {
    return at(elevation, Distance.getDefault());
  }

  /**
   * Sets the {@link Double elevation} using the given {@link Distance unit of measeurement}
   * at these {@link Coordinates}.
   *
   * @param elevation {@link Double elevation} at these {@link Coordinates}.
   * @param distance {@link Distance} used as the unit of measurement.
   * @return these {@link Coordinates}.
   * @see org.cp.elements.enums.Distance
   * @see #at(Elevation)
   */
  public Coordinates at(double elevation, Distance distance) {
    return at(Elevation.of(elevation).in(distance));
  }

  /**
   * Sets the {@link Double elevation} using the given {@link Distance unit of measeurement}
   * at these {@link Coordinates}.
   *
   * @param elevation {@link Elevation} at these {@link Coordinates}.
   * @return these {@link Coordinates}.
   * @see org.cp.domain.geo.model.Elevation
   */
  public Coordinates at(Elevation elevation) {
    this.elevation = elevation;
    return this;
  }

  /**
   * Determines whether these {@link Coordinates} are equal to the given {@link Object}.
   *
   * @param obj {@link Object} being evaluated for equality with these {@link Coordinates}.
   * @return a boolean value indicating whether these {@link Coordinates} are equal to the given {@link Object}.
   * @see java.lang.Object#equals(Object)
   */
  @Override
  public boolean equals(Object obj) {

    if (this == obj) {
      return true;
    }

    if (!(obj instanceof Coordinates)) {
      return false;
    }

    Coordinates that = (Coordinates) obj;

    return this.getLatitude() == that.getLatitude()
      && this.getLongitude() == that.getLongitude();
  }

  /**
   * Computes the hash code for these {@link Coordinates}.
   *
   * @return the computed hash code for these {@link Coordinates}.
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {

    int hashValue = 17;

    hashValue = 37 * hashValue + ObjectUtils.hashCode(getLatitude());
    hashValue = 37 * hashValue + ObjectUtils.hashCode(getLongitude());

    return hashValue;
  }

  /**
   * Returns a {@link String} representation of these {@link Coordinates}.
   *
   * @return a {@link String} describing these {@link Coordinates}.
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return String.format(COORDINATES_TO_STRING, getLatitude(), getLongitude(), getElevation());
  }
}
