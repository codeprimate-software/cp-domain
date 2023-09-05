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

import java.io.Serializable;

import org.cp.elements.enums.LengthUnit;
import org.cp.elements.lang.Integers;
import org.cp.elements.lang.MathUtils;
import org.cp.elements.lang.ObjectUtils;
import org.cp.elements.lang.annotation.Dsl;
import org.cp.elements.lang.annotation.FluentApi;
import org.cp.elements.lang.annotation.NotNull;
import org.cp.elements.lang.annotation.Nullable;

/**
 * Abstract Data Type (ADT) modeling {@literal elevation} or {@literal altitude}.
 *
 * @author John Blum
 * @see java.io.Serializable
 * @see java.lang.Comparable
 * @see org.cp.elements.enums.LengthUnit
 * @see org.cp.elements.lang.annotation.FluentApi
 * @since 0.1.0
 */
@FluentApi
public class Elevation implements Comparable<Elevation>, Serializable {

  protected static final double SEA_LEVEL_VALUE = 0.0d;
  protected static final double ONE = 1.0d;

  protected static final LengthUnit DEFAULT_LENGTH_UNIT = LengthUnit.getDefault();

  protected static final String ELEVATION_TO_STRING = "%1$s %2$s";

  /**
   * Factory method used to construct a new {@link Elevation} initialized with the given {@link Double altitude}
   * using a {@link LengthUnit#getDefault() default unit of measurement} determined by the current,
   * default {@link java.util.Locale}.
   * <p>
   * A {@link Double negative altitude} is below {@literal sea level}. A {@link Double positive altitude}
   * is above {@literal sea level}. And, an {@literal altitude} of {@link Double zero} is at {@literal sea level}.
   *
   * @param altitude {@link Double value} containing the {@literal altitude} of this {@link Elevation}.
   * @return a new {@link Elevation} initialized with the given {@link Double altitude}
   * using a {@link LengthUnit default unit of measurement}.
   * @see #Elevation(double)
   */
  public static @NotNull Elevation at(double altitude) {
    return new Elevation(altitude);
  }

  /**
   * Factory method used to construct a new {@link Elevation} at {@literal sea level},
   * or {@literal 0} {@link LengthUnit#METER meters}.
   *
   * @return a new {@link Elevation} at {@literal sea level}.
   * @see #at(double)
   */
  public static @NotNull Elevation atSeaLevel() {
    return at(SEA_LEVEL_VALUE);
  }

  private final double altitude;

  private LengthUnit lengthUnit;

  /**
   * Constructs a new {@link Elevation} initialized with the given {@link Double altitude}
   * using a {@link LengthUnit#getDefault() default unit of measurement} determined by the current,
   * default {@link java.util.Locale}.
   * <p>
   * A {@link Double negative altitude} is below {@literal sea level}. A {@link Double positive altitude}
   * is above {@literal sea level}. And, an {@literal altitude} of {@link Double zero} is at {@literal sea level}.
   *
   * @param altitude {@link Double value} containing the {@literal altitude} of this {@link Elevation}.
   * @see org.cp.elements.enums.LengthUnit#getDefault()
   */
  public Elevation(double altitude) {

    this.altitude = altitude;
    this.lengthUnit = DEFAULT_LENGTH_UNIT;
  }

  /**
   * Returns the {@link Double altitude} of this {@link Elevation}.
   * <p>
   * A {@link Double negative altitude} is below {@literal sea level}. A {@link Double positive altitude}
   * is above {@literal sea level}. And, an {@literal altitude} of {@link Double zero} is at {@literal sea level}.
   *
   * @return the {@link Double altitude} of this {@link Elevation}.
   * @see java.lang.Double#TYPE
   */
  public double getAltitude() {
    return this.altitude;
  }

  /**
   * Return the {@link LengthUnit unit of measurement} used when measuring the {@literal altitude}
   * of this {@link Elevation}, such as {@link LengthUnit#FOOT feet} or {@link LengthUnit#METER meters}.
   * <p>
   * Defaults to a {@link LengthUnit#getDefault() unit of measurement} determined by the current,
   * default {@link java.util.Locale}.
   *
   * @return the {@link LengthUnit unit of measurement} used when measuring the {@literal altitude}
   * of this {@link Elevation}.
   * @see org.cp.elements.enums.LengthUnit
   */
  public @NotNull LengthUnit getLengthUnit() {
    LengthUnit lengthUnit = this.lengthUnit;
    return lengthUnit != null ? lengthUnit : DEFAULT_LENGTH_UNIT;
  }

  /**
   * Sets the {@link LengthUnit unit of measurement} used when measuring the {@literal altitude}
   * of this {@link Elevation}, such as {@link LengthUnit#FOOT feet} or {@link LengthUnit#METER meters}.
   *
   * @param lengthUnit {@link LengthUnit unit of measurement} used when measuring the {@literal altitude}
   * of this {@link Elevation}.
   * @see org.cp.elements.enums.LengthUnit
   */
  public void setLengthUnit(@Nullable LengthUnit lengthUnit) {
    this.lengthUnit = lengthUnit;
  }

  /**
   * Builder method used to set the {@link LengthUnit unit of measurement} measuring the {@literal altitude}
   * of this {@link Elevation}.
   *
   * @param lengthUnit {@link LengthUnit} used as the {@literal }unit of measurement}.
   * @return this {@link Elevation}.
   * @see org.cp.elements.lang.annotation.Dsl
   * @see org.cp.elements.enums.LengthUnit
   * @see #setLengthUnit(LengthUnit)
   */
  @Dsl
  public @NotNull Elevation in(@Nullable LengthUnit lengthUnit) {
    setLengthUnit(lengthUnit);
    return this;
  }

  /**
   * Builder method used to set the {@link LengthUnit unit of measurement} measuring the {@literal altitude}
   * of this {@link Elevation} in {@link LengthUnit#FOOT feet}.
   *
   * @return this {@link Elevation}.
   * @see org.cp.elements.enums.LengthUnit#FOOT
   * @see org.cp.elements.lang.annotation.Dsl
   * @see #in(LengthUnit)
   */
  @Dsl
  public @NotNull Elevation inFeet() {
    return in(LengthUnit.FOOT);
  }

  /**
   * Builder method used to set the {@link LengthUnit unit of measurement} measuring the {@literal altitude}
   * of this {@link Elevation} in {@link LengthUnit#METER meters}.
   *
   * @return this {@link Elevation}.
   * @see org.cp.elements.enums.LengthUnit#METER
   * @see org.cp.elements.lang.annotation.Dsl
   * @see #in(LengthUnit)
   */
  public Elevation inMeters() {
    return in(LengthUnit.METER);
  }

  /**
   * Returns a boolean value indicating whether this {@link Elevation} is above {@literal sea level}.
   * <p>
   * An {@link Elevation} is considered to be above {@literal sea level} with an {@link #getAltitude() altitude}
   * greater than {@literal 0}.
   *
   * @return a boolean value indicating whether this {@link Elevation} is above {@literal sea level}.
   * @see #getAltitude()
   */
  public boolean isAboveSeaLevel() {
    return getAltitude() > SEA_LEVEL_VALUE;
  }

  /**
   * Returns a boolean value indicating whether this {@link Elevation} is at {@literal sea level}.
   * <p>
   * An {@link Elevation} is considered to be at {@literal sea level} with an {@link #getAltitude() altitude}
   * equal to {@literal 0}.
   *
   * @return a boolean value indicating whether this {@link Elevation} is at {@literal sea level}.
   * @see #getAltitude()
   */
  public boolean isAtSeaLevel() {
    return getAltitude() == SEA_LEVEL_VALUE;
  }

  /**
   * Returns a boolean value indicating whether this {@link Elevation} is below {@literal sea level}.
   * <p>
   * An {@link Elevation} is considered to be below {@literal sea level} with an {@link #getAltitude() altitude}
   * less than {@literal 0}.
   *
   * @return a boolean value indicating whether this {@link Elevation} is below {@literal sea level}.
   * @see #getAltitude()
   */
  public boolean isBelowSeaLevel() {
    return getAltitude() < SEA_LEVEL_VALUE;
  }

  /**
   * Converts this {@link Elevation} to a {@link Double measurement} in meters.
   *
   * @return this {@link Elevation} as a {@link Double measurement} in meters.
   * @see #getLengthUnit()
   * @see #getAltitude()
   */
  public double toMeasurementInMeters() {
    return getAltitude() * getLengthUnit().getMeterConversionFactor();
  }

  /**
   * Compares this {@link Elevation} to the given, required {@link Elevation} sorting this and that {@link Elevation}
   * for relative ordering in an ordered data structure.
   *
   * @param that {@link Elevation} to compare to this {@link Elevation}; must not be {@literal null}
   * @return an {@link Integer value} indicating the (sort) order of this {@link Elevation} relative to
   * the given {@link Elevation}.
   * Returns a {@link Integer negative value} if this {@link Elevation} is less than the given {@link Elevation}.
   * Returns a {@link Integer positive value} if this {@link Elevation} is greater than the given {@link Elevation}.
   * Returns {@link Integer zero} if this {@link Elevation} is equal to the given {@link Elevation}.
   * @see #getLengthUnit()
   * @see #getAltitude()
   */
  @Override
  public int compareTo(@NotNull Elevation that) {

    double thisValue = this.getAltitude() * this.getLengthUnit().getMeterConversionFactor();
    double thatValue = that.getAltitude() * that.getLengthUnit().getMeterConversionFactor();

    int precision = Math.min(MathUtils.countNumberOfDecimalPlaces(thisValue),
      MathUtils.countNumberOfDecimalPlaces(thatValue));

    return Double.compare(MathUtils.roundToPrecision(thisValue, precision),
      MathUtils.roundToPrecision(thatValue, precision));
  }

  /**
   * Determines whether this {@link Elevation} is equal to the given {@link Object}.
   *
   * @param obj {@link Object} to evaluate for equality with this {@link Elevation}.
   * @return a boolean value indicating whether this {@link Elevation} is equal to the given {@link Object}.
   * @see java.lang.Object#equals(Object)
   * @see #compareTo(Elevation)
   */
  @Override
  public boolean equals(@Nullable Object obj) {

    if (this == obj) {
      return true;
    }

    if (!(obj instanceof Elevation that)) {
      return false;
    }

    return Integers.isZero(compareTo(that));
  }

  /**
   * Computes the {@link Integer hash code} of this {@link Elevation}.
   *
   * @return the computed {@link Integer hash code} of this {@link Elevation}.
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    return ObjectUtils.hashCodeOf(toMeasurementInMeters());
  }

  /**
   * Returns a {@link String} representation of this {@link Elevation}.
   *
   * @return a {@link String} describing this {@link Elevation}.
   * @see java.lang.Object#toString()
   */
  @Override
  public @NotNull String toString() {

    double altitude = getAltitude();

    String unitName = Math.abs(altitude) != ONE
      ? getLengthUnit().getPluralName().toLowerCase()
      : getLengthUnit().name().toLowerCase();

    return String.format(ELEVATION_TO_STRING, altitude, unitName);
  }
}
