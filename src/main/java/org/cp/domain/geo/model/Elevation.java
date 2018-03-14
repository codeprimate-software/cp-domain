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

import java.io.Serializable;

import org.cp.elements.enums.Distance;

/**
 * The {@link Elevation} class is an Abstract Data Type (ADT) representing an elevation or altitude.
 *
 * @author John Blum
 * @see java.io.Serializable
 * @see org.cp.domain.geo.enums.Country
 * @see org.cp.elements.enums.Distance
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public class Elevation implements Serializable {

  private static final Distance DEFAULT_DISTANCE = Distance.getDefault();

  /**
   * Factory method used to construct a new, default instance of {@link Elevation} at sea level.
   *
   * @return a new {@link Elevation} at sea level.
   * @see #of(double)
   */
  public static Elevation atSeaLevel() {
    return of(0.0d);
  }

  /**
   * Factory method used to construct a new instance of {@link Elevation} initialized with
   * the given {@link Double altitude}.
   *
   * @param altitude {@link Double} value containing the altitude at this {@link Elevation};
   * may be negative.
   * @return a new instance of {@link Elevation} initialized with the given {@link Double altitude}.
   * @see #Elevation(double)
   */
  public static Elevation of(double altitude) {
    return new Elevation(altitude);
  }

  private final double altitude;

  private Distance distance;

  /**
   * Constructs a new instance of {@link Elevation} initialized with the given {@link Double altitude}.
   *
   * @param altitude {@link Double} value containing the altitude at this {@link Elevation};
   * may be negative.
   * @see org.cp.elements.enums.Distance#getDefault()
   */
  public Elevation(double altitude) {
    this.altitude = altitude;
    this.distance = Distance.getDefault();
  }

  /**
   * Returns the {@link Double altitude} at this {@link Elevation}.
   *
   * The {@link Double altitude} may be negative indicating that this {@link Elevation}
   * is below sea level.
   *
   * @return the {@link Double altitude} at this {@link Elevation}.
   * @see java.lang.Double#TYPE
   */
  public double getAltitude() {
    return this.altitude;
  }

  /**
   * Return the {@link Distance unit of measurement} (e.g. {@literal feet} or {@literal meters})
   * when measuring this {@link Elevation}.
   *
   * Defaults to {@link Distance#FOOT feet}.
   *
   * @return the {@link Distance unit of measurement} when measuring this {@link Elevation}.
   * @see org.cp.elements.enums.Distance
   */
  public Distance getDistance() {
    return this.distance != null ? this.distance : DEFAULT_DISTANCE;
  }

  /**
   * Sets the {@link Distance unit of measurement} (e.g. {@literal feet} or {@literal meters})
   * used to measure this {@link Elevation}.
   *
   * @param distance {@link Distance} specifying the unit of measurement used to measure
   * this {@link Elevation}.
   * @see org.cp.elements.enums.Distance
   */
  public void setDistance(Distance distance) {
    this.distance = distance;
  }

  /**
   * Builder method used to set the {@link Distance unit of measurement} in the given {@link Distance}.
   *
   * @return this {@link Elevation}.
   * @see org.cp.elements.enums.Distance
   * @see #setDistance(Distance)
   */
  public Elevation in(Distance distance) {
    setDistance(distance);
    return this;
  }

  /**
   * Builder method used to set the {@link Distance unit of measurement} in {@link Distance#FOOT feet}.
   *
   * @return this {@link Elevation}.
   * @see org.cp.elements.enums.Distance#FOOT
   * @see #in(Distance)
   */
  public Elevation inFeet() {
    return in(Distance.FOOT);
  }

  /**
   * Builder method used to set the {@link Distance unit of measurement} in {@link Distance#METER meters}.
   *
   * @return this {@link Elevation}.
   * @see org.cp.elements.enums.Distance#METER
   * @see #in(Distance)
   */
  public Elevation inMeters() {
    return in(Distance.METER);
  }

  /**
   * Returns a boolean value indicating whether this {@link Elevation} is above sea level.
   *
   * An {@link Elevation} is considered to be above sea level with an {@link #getAltitude() altitude}
   * greater than {@literal 0}.
   *
   * @return a boolean value indicating whether this {@link Elevation} is above sea level.
   * @see #getAltitude()
   */
  public boolean isAboveSeaLevel() {
    return getAltitude() > 0.0d;
  }

  /**
   * Returns a boolean value indicating whether this {@link Elevation} is at sea level.
   *
   * An {@link Elevation} is considered to be at sea level with an {@link #getAltitude() altitude}
   * equal to {@literal 0}.
   *
   * @return a boolean value indicating whether this {@link Elevation} is at sea level.
   * @see #getAltitude()
   */
  public boolean isAtSeaLevel() {
    return getAltitude() == 0.0d;
  }

  /**
   * Returns a boolean value indicating whether this {@link Elevation} is below sea level.
   *
   * An {@link Elevation} is considered below sea level with an {@link #getAltitude() altitude}
   * less than {@literal 0}.
   *
   * @return a boolean value indicating whether this {@link Elevation} is below sea level.
   * @see #getAltitude()
   */
  public boolean isBelowSeaLevel() {
    return getAltitude() < 0.0d;
  }

  /**
   * Determines whether this {@link Elevation} is equal to the given {@link Object}.
   *
   * @param obj {@link Object} to evaluate with this {@link Elevation} in the equality comparison.
   * @return a boolean value indicating whether this {@link Elevation} is equal to the given {@link Object}.
   * @see java.lang.Object#equals(Object)
   */
  @Override
  public boolean equals(Object obj) {

    if (this == obj) {
      return true;
    }

    if (!(obj instanceof Elevation)) {
      return false;
    }

    Elevation that = (Elevation) obj;

    return this.getAltitude() == that.getAltitude()
      && this.getDistance().equals(that.getDistance());
  }

  /**
   * Computes the {@link Integer hash code} of this {@link Elevation}.
   *
   * @return the computed {@link Integer hash code} of this {@link Elevation}.
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {

    int hashValue = 17;

    hashValue = 37 * hashValue + Double.valueOf(getAltitude()).hashCode();
    hashValue = 37 * hashValue + getDistance().hashCode();

    return hashValue;
  }

  /**
   * Returns a {@link String} representation of this {@link Elevation}.
   *
   * @return a {@link String} describing this {@link Elevation}.
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {

    return String.format("%1$s %2$s", getAltitude(),
      Math.abs(getAltitude()) != 1.0d ? getDistance().getPluralName().toLowerCase()
        : getDistance().name().toLowerCase());
  }
}
