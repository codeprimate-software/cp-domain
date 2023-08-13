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
import java.util.Optional;

import org.cp.elements.enums.LengthUnit;
import org.cp.elements.lang.Assert;
import org.cp.elements.lang.ObjectUtils;
import org.cp.elements.lang.annotation.Alias;
import org.cp.elements.lang.annotation.Dsl;
import org.cp.elements.lang.annotation.FluentApi;
import org.cp.elements.lang.annotation.NotNull;
import org.cp.elements.lang.annotation.NullSafe;
import org.cp.elements.lang.annotation.Nullable;

/**
 * Abstract Data Type (ADT) that models geographic coordinates including {@literal latitude}, {@literal longitude}
 * and {@literal altitude}, or {@literal elevation}, as represented on a [topological] map of the world.
 *
 * @author John Blum
 * @see java.awt.Point
 * @see java.lang.Double
 * @see java.io.Serializable
 * @see org.cp.domain.geo.model.Elevation
 * @see org.cp.elements.lang.annotation.FluentApi
 * @since 0.1.0
 */
@SuppressWarnings("unused")
@FluentApi
public class Coordinates implements Serializable {

  /**
   * Null Island is the point on Earth's surface at zero degrees latitude and zero degrees longitude ( 0°N 0°E),
   * that is, where the prime meridian and the Equator intersect. Null Island is located in international waters
   * in the Atlantic Ocean, roughly 600 km off the coast of West Africa, in the Gulf of Guinea.
   */
  public static final Coordinates NULL_ISLAND = Coordinates.at(0.0d, 0.0d).at(Elevation.atSeaLevel());

  protected static final String COORDINATES_TO_STRING = "[latitude: %1$s, longitude: %2$s, altitude: %3$s]";


  /**
   * Factory method used to construct a new {@link Coordinates} initialized with
   * the given {@link Double latitude} and {@link Double longitude}.
   *
   * @param latitude {@link Double latitude} at the geographic coordinates.
   * @param longitude {@link Double longitude} at the geographic coordinates.
   * @return a new set of {@link Coordinates} initialized with the given {@link Double latitude}
   * and {@link Double longitude}.
   * @see #Coordinates(double, double)
   */
  public static @NotNull Coordinates at(double latitude, double longitude) {
    return new Coordinates(latitude, longitude);
  }

  /**
   * Factory method used to construct a new {@link Coordinates} initialized from the given AWT {@link Point}.
   *
   * @param point {@link Point} used to initialize the new {@link Coordinates}; must not be {@literal null}.
   * @return a new set of {@link Coordinates} initialized with the given AWT {@link Point}.
   * @throws IllegalArgumentException if {@link Point} is {@literal null}.
   * @see #at(double, double)
   * @see java.awt.Point
   */
  public static @NotNull Coordinates from(@NotNull Point point) {

    Assert.notNull(point, "Point is required");

    return at(point.getY(), point.getX());
  }

  /**
   * North-South position on the Earth's surface.
   * <p>
   * Lines of latitude, also called parallels, are imaginary lines that divide the Earth. They run east to west,
   * but measure your distance north or south. The equator is the most well known parallel. At 0 degrees latitude,
   * it equally divides the Earth into the Northern and Southern hemispheres.
   */
  private final double latitude;

  /**
   * East-West position on the Earth's surface.
   * <p>
   * Lines of longitude, also called meridians, are imaginary lines that divide the Earth. They run north to south
   * from pole to pole, but they measure the distance east or west. The prime meridian, which runs through Greenwich,
   * England, has a longitude of 0 degrees.
   */
  private final double longitude;

  /**
   * {@literal Altitude} at these {@link Coordinates geographic coordinates}.
   */
  private Elevation elevation;

  /**
   * Constructs a new {@link Coordinates} initialized with the given {@link Double latitude}
   * and {@link Double longitude}.
   *
   * @param latitude {@link Double latitude} at these {@link Coordinates geographic coordinates}.
   * @param longitude {@link Double longitude} at these {@link Coordinates geographic coordinates}.
   */
  public Coordinates(double latitude, double longitude) {

    this.latitude = latitude;
    this.longitude = longitude;
  }

  /**
   * Returns an {@link Optional} {@link Elevation altitude} at these {@link Coordinates}.
   * <p>
   * This method is an {@link Alias} for {@link #getElevation()}.
   *
   * @return an {@link Optional} {@link Elevation altitude} at these {@link Coordinates}.
   * @see org.cp.domain.geo.model.Elevation
   * @see java.util.Optional
   * @see #getElevation()
   */
  @NullSafe
  @Alias(forMember = "Coordinates.getElevation()")
  public @NotNull Optional<Elevation> getAltitude() {
    return getElevation();
  }

  /**
   * Returns an {@link Optional} {@link Elevation} at these {@link Coordinates}.
   *
   * @return an {@link Optional} {@link Elevation} at these {@link Coordinates}.
   * @see org.cp.domain.geo.model.Elevation
   * @see java.util.Optional
   * @see #getAltitude()
   */
  @NullSafe
  public @NotNull Optional<Elevation> getElevation() {
    return Optional.ofNullable(this.elevation);
  }

  /**
   * Returns the {@link Double latitude} at these {@link Coordinates}.
   * <p>
   * Lines of latitude, also called parallels, are imaginary lines that divide the Earth. They run east to west,
   * but measure your distance north or south. The equator is the most well known parallel. At 0 degrees latitude,
   * it equally divides the Earth into the Northern and Southern hemispheres.
   *
   * @return the {@link Double latitude} at these {@link Coordinates}.
   */
  public double getLatitude() {
    return this.latitude;
  }

  /**
   * Returns the {@link Double longitude} at these {@link Coordinates}.
   * <p>
   * Lines of longitude, also called meridians, are imaginary lines that divide the Earth. They run north to south
   * from pole to pole, but they measure the distance east or west. The prime meridian, which runs through Greenwich,
   * England, has a longitude of 0 degrees.
   *
   * @return the {@link Double longitude} at these {@link Coordinates}.
   */
  public double getLongitude() {
    return this.longitude;
  }

  /**
   * Returns these {@link Coordinates} as an AWT {@link Point}.
   *
   * @return these {@link Coordinates} as an AWT {@link Point}.
   * @see #getLatitude()
   * @see #getLongitude()
   * @see java.awt.Point
   */
  public @NotNull Point asPoint() {
    return new Point(Double.valueOf(getLongitude()).intValue(), Double.valueOf(getLatitude()).intValue());
  }

  /**
   * Sets the {@link Double elevation} or {@literal altitude} at these {@link Coordinates}
   * using the {@link LengthUnit#getDefault() default unit of measeurement}.
   * <p>
   * A {@link Double positive value} is above {@literal sea level}. A {@link Double negative value}
   * is below {@literal sea level}. And, a {@link Double zero value} is at {@literal sea level},
   *
   * @param altitude {@link Double elevation} at these {@link Coordinates}.
   * @return these {@link Coordinates}.
   * @see org.cp.elements.enums.LengthUnit#getDefault()
   * @see org.cp.elements.lang.annotation.Dsl
   * @see #at(double, LengthUnit)
   */
  @Dsl
  public @NotNull Coordinates at(double altitude) {
    return at(altitude, LengthUnit.getDefault());
  }

  /**
   * Sets the {@link Double elevation} or {@literal altitude} at these {@link Coordinates}
   * using the given {@link LengthUnit unit of measurement}.
   * <p>
   * A {@link Double positive value} is above {@literal sea level}. A {@link Double negative value}
   * is below {@literal sea level}. And, a {@link Double zero value} is at {@literal sea level},
   *
   * @param altitude {@link Double elevation} at these {@link Coordinates}.
   * @param lengthUnit {@link LengthUnit} used as the unit of measurement.
   * @return these {@link Coordinates}.
   * @see org.cp.elements.lang.annotation.Dsl
   * @see org.cp.elements.enums.LengthUnit
   * @see #at(Elevation)
   */
  @Dsl
  public @NotNull Coordinates at(double altitude, @Nullable LengthUnit lengthUnit) {
    return at(Elevation.at(altitude).in(lengthUnit));
  }

  /**
   * Sets the {@link Elevation} or {@literal altitude} at these {@link Coordinates}.
   *
   * @param elevation {@link Elevation}, or {@literal altitude} at these {@link Coordinates}.
   * @return these {@link Coordinates}.
   * @see org.cp.elements.lang.annotation.Dsl
   * @see org.cp.domain.geo.model.Elevation
   */
  @Dsl
  public @NotNull Coordinates at(@Nullable Elevation elevation) {
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
  public boolean equals(@Nullable Object obj) {

    if (this == obj) {
      return true;
    }

    if (!(obj instanceof Coordinates that)) {
      return false;
    }

    return this.getLatitude() == that.getLatitude()
      && this.getLongitude() == that.getLongitude();
  }

  /**
   * Computes the {@link Integer hash code} for these {@link Coordinates}.
   *
   * @return the computed {@link Integer hash code} for these {@link Coordinates}.
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    return ObjectUtils.hashCodeOf(getLatitude(), getLongitude());
  }

  /**
   * Returns a {@link String} representation of these {@link Coordinates}.
   *
   * @return a {@link String} describing these {@link Coordinates}.
   * @see java.lang.Object#toString()
   */
  @Override
  public @NotNull String toString() {
    return String.format(COORDINATES_TO_STRING, getLatitude(), getLongitude(), getElevation().orElse(null));
  }
}
