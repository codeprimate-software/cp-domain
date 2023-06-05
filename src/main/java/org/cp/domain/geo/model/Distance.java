/*
 * Copyright 2011-Present Author or Authors.
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

import java.util.function.Function;

import org.cp.elements.enums.LengthUnit;
import org.cp.elements.lang.Assert;
import org.cp.elements.lang.ObjectUtils;
import org.cp.elements.lang.annotation.Immutable;
import org.cp.elements.lang.annotation.NotNull;
import org.cp.elements.lang.annotation.NullSafe;
import org.cp.elements.lang.annotation.Nullable;

/**
 * Abstract Data Type (ADT) used to model distance from one point to another.
 *
 * @author John Blum
 * @see java.lang.Comparable
 * @see org.cp.elements.enums.LengthUnit
 * @since 0.1.0
 */
@Immutable
public class Distance implements Comparable<Distance> {

  protected static final String DISTANCE_TO_STRING = "%s %s";
  protected static final String METER_NAME = "meter";

  /**
   * Factory method used to construct a new instance of {@link Distance} with a given {@link Double measurement}
   * in {@link LengthUnit#FOOT feet}.
   *
   * @param measurement {@link Double value} for the {@literal measurement of distance};
   * must be greater than equal to {@literal 0}.
   * @return a new {@link Distance} {@link Double measurement} in {@link LengthUnit#FOOT feet}.
   * @throws IllegalArgumentException if the given {@link Double measurement} is less than {@literal 0}.
   * @see org.cp.elements.enums.LengthUnit#FOOT
   * @see org.cp.domain.geo.model.Distance
   * @see #of(double, LengthUnit)
   */
  public static @NotNull Distance inFeet(double measurement) {
    return of(measurement, LengthUnit.FOOT);
  }

  /**
   * Factory method used to construct a new instance of {@link Distance} with a given {@link Double measurement}
   * in {@link LengthUnit#KILOMETER kilometers}.
   *
   * @param measurement {@link Double value} for the {@literal measurement of distance};
   * must be greater than equal to {@literal 0}.
   * @return a new {@link Distance} {@link Double measurement} in {@link LengthUnit#KILOMETER kilometers}.
   * @throws IllegalArgumentException if the given {@link Double measurement} is less than {@literal 0}.
   * @see org.cp.elements.enums.LengthUnit#KILOMETER
   * @see org.cp.domain.geo.model.Distance
   * @see #of(double, LengthUnit)
   */
  public static @NotNull Distance inKilometers(double measurement) {
    return of(measurement, LengthUnit.KILOMETER);
  }

  /**
   * Factory method used to construct a new instance of {@link Distance} with a given {@link Double measurement}
   * in {@link LengthUnit#METER meters}.
   *
   * @param measurement {@link Double value} for the {@literal measurement of distance};
   * must be greater than equal to {@literal 0}.
   * @return a new {@link Distance} {@link Double measurement} in {@link LengthUnit#METER meters}.
   * @throws IllegalArgumentException if the given {@link Double measurement} is less than {@literal 0}.
   * @see org.cp.elements.enums.LengthUnit#METER
   * @see org.cp.domain.geo.model.Distance
   * @see #of(double, LengthUnit)
   */
  public static @NotNull Distance inMeters(double measurement) {
    return of(measurement, LengthUnit.METER);
  }

  /**
   * Factory method used to construct a new instance of {@link Distance} with a given {@link Double measurement}
   * in {@link LengthUnit#MILE miles}.
   *
   * @param measurement {@link Double value} for the {@literal measurement of distance};
   * must be greater than equal to {@literal 0}.
   * @return a new {@link Distance} {@link Double measurement} in {@link LengthUnit#MILE miles}.
   * @throws IllegalArgumentException if the given {@link Double measurement} is less than {@literal 0}.
   * @see org.cp.elements.enums.LengthUnit#MILE
   * @see org.cp.domain.geo.model.Distance
   * @see #of(double, LengthUnit)
   */
  public static @NotNull Distance inMiles(double measurement) {
    return of(measurement, LengthUnit.MILE);
  }

  /**
   * Factory method used to construct a new instance of {@link Distance} with a given {@link Double measurement}
   * in {@link LengthUnit#YARD yards}.
   *
   * @param measurement {@link Double value} for the {@literal measurement of distance};
   * must be greater than equal to {@literal 0}.
   * @return a new {@link Distance} {@link Double measurement} in {@link LengthUnit#YARD yards}.
   * @throws IllegalArgumentException if the given {@link Double measurement} is less than {@literal 0}.
   * @see org.cp.elements.enums.LengthUnit#YARD
   * @see org.cp.domain.geo.model.Distance
   * @see #of(double, LengthUnit)
   */
  public static @NotNull Distance inYards(double measurement) {
    return of(measurement, LengthUnit.YARD);
  }

  /**
   * Factory method used to construct a new instance of {@link Distance} with a given {@link Double measurement}
   * in the {@link LengthUnit#getDefault() default LengthUnit} as determined by the current {@link java.util.Locale}.
   *
   * @param measurement {@link Double value} for the {@literal measurement of distance};
   * must be greater than equal to {@literal 0}.
   * @return a new {@link Distance} {@link Double measurement} in the {@link LengthUnit#getDefault() default LengthUnit}
   * as determined by the current {@link java.util.Locale}.
   * @throws IllegalArgumentException if the given {@link Double measurement} is less than {@literal 0}.
   * @see org.cp.elements.enums.LengthUnit#getDefault()
   * @see org.cp.domain.geo.model.Distance
   * @see #of(double, LengthUnit)
   */
  public static @NotNull Distance of(double measurement) {
    return of(measurement, LengthUnit.getDefault());
  }

  /**
   * Factory method used to construct a new instance of {@link Distance} with a given {@link Double measurement}
   * in the given, required {@link LengthUnit}.
   *
   * @param measurement {@link Double value} for the {@literal measurement of distance};
   * must be greater than equal to {@literal 0}.
   * @param lengthUnit {@link LengthUnit} used to quantify the {@link Double measurement};
   * must not be {@literal null}.
   * @return a new {@link Distance} {@link Double measurement} in the given {@link LengthUnit}.
   * @throws IllegalArgumentException if the given {@link Double measurement} is less than {@literal 0}
   * or the {@link LengthUnit} is {@literal null}.
   * @see org.cp.elements.enums.LengthUnit
   * @see org.cp.domain.geo.model.Distance
   */
  public static @NotNull Distance of(double measurement, @NotNull LengthUnit lengthUnit) {
    return new Distance(measurement, lengthUnit);
  }

  /**
   * Null-safe operation used to determine if the given {@link LengthUnit}
   * is a {@literal Metric System unit of length (distance)}.
   *
   * @param lengthUnit {@link LengthUnit} to evaluate.
   * @return a boolean value indicating whether the given {@link LengthUnit}
   * is a {@literal Metric System unit of length (distance)}.
   * @see <a href="https://en.wikipedia.org/wiki/Metric_system">Metric System</a>
   * @see org.cp.elements.enums.LengthUnit
   */
  @NullSafe
  static boolean isMetric(@NotNull LengthUnit lengthUnit) {
    return lengthUnit != null && lengthUnit.name().toLowerCase().endsWith(METER_NAME);
  }

  private final double measurement;

  private final LengthUnit lengthUnit;

  /**
   * Constructs a new {@link Distance} with the given {@link Double measurement} in the given,
   * required {@link LengthUnit unit}.
   *
   * @param measurement {@link Double value} for the {@literal measurement of distance};
   * must be greater than equal to {@literal 0}.
   * @param lengthUnit {@link LengthUnit} used to quantify the {@link Double measurement};
   * must not be {@literal null}.
   * @throws IllegalArgumentException if the given {@link Double measurement} is less than {@literal 0}
   * or the given {@link LengthUnit} is {@literal null}.
   * @see org.cp.elements.enums.LengthUnit
   */
  public Distance(double measurement, LengthUnit lengthUnit) {

    Assert.isTrue(measurement >= 0.0d,
      "The measurement of distance [%s] must be greater than equal to 0", measurement);

    this.measurement = measurement;
    this.lengthUnit = ObjectUtils.requireObject(lengthUnit, "LengthUnit is required");
  }

  /**
   * Gets the {@link LengthUnit} used to quantify the {@link Double measurement} of this {@link Distance}.
   *
   * @return the {@link LengthUnit} used to quantify the {@link Double measurement} of this {@link Distance}.
   * @see org.cp.elements.enums.LengthUnit
   * @see #getMeasurement()
   */
  public @NotNull LengthUnit getLengthUnit() {
    return this.lengthUnit;
  }

  /**
   * Gets the {@link Double measurement} of this {@link Distance}.
   *
   * @return the {@link Double measurement} of this {@link Distance}.
   * @see java.lang.Double
   * @see #getLengthUnit()
   */
  public double getMeasurement() {
    return this.measurement;
  }

  /**
   * Determines whether this {@link Distance} is {@link #getMeasurement() measured} in {@link LengthUnit#FOOT feet}.
   *
   * @return a boolean value indicating whether this {@link Distance}
   * is {@link #getMeasurement() measured} in {@link LengthUnit#FOOT feet}.
   * @see org.cp.elements.enums.LengthUnit#FOOT
   * @see #getLengthUnit()
   */
  public boolean isInFeet() {
    return LengthUnit.FOOT.equals(getLengthUnit());
  }

  /**
   * Determines whether this {@link Distance} is {@link #getMeasurement() measured} in
   * {@link LengthUnit#KILOMETER kilometers}.
   *
   * @return a boolean value indicating whether this {@link Distance}
   * is {@link #getMeasurement() measured} in {@link LengthUnit#KILOMETER kilometers}.
   * @see org.cp.elements.enums.LengthUnit#KILOMETER
   * @see #getLengthUnit()
   */
  public boolean isInKilometers() {
    return LengthUnit.KILOMETER.equals(getLengthUnit());
  }

  /**
   * Determines whether this {@link Distance} is {@link #getMeasurement() measured} in
   * {@link LengthUnit#METER meters}.
   *
   * @return a boolean value indicating whether this {@link Distance}
   * is {@link #getMeasurement() measured} in {@link LengthUnit#METER meters}.
   * @see org.cp.elements.enums.LengthUnit#METER
   * @see #getLengthUnit()
   */
  public boolean isInMeters() {
    return LengthUnit.METER.equals(getLengthUnit());
  }

  /**
   * Determines whether this {@link Distance} is {@link #getMeasurement() measured} in
   * {@link LengthUnit#MILE miles}.
   *
   * @return a boolean value indicating whether this {@link Distance}
   * is {@link #getMeasurement() measured} in {@link LengthUnit#MILE miles}.
   * @see org.cp.elements.enums.LengthUnit#MILE
   * @see #getLengthUnit()
   */
  public boolean isInMiles() {
    return LengthUnit.MILE.equals((getLengthUnit()));
  }

  /**
   * Determines whether this {@link Distance} is {@link #getMeasurement() measured} in
   * {@link LengthUnit#YARD yards}.
   *
   * @return a boolean value indicating whether this {@link Distance}
   * is {@link #getMeasurement() measured} in {@link LengthUnit#YARD yards}.
   * @see org.cp.elements.enums.LengthUnit#YARD
   * @see #getLengthUnit()
   */
  public boolean isInYards() {
    return LengthUnit.YARD.equals(getLengthUnit());
  }

  /**
   * Determines whether the {@link #getLengthUnit()} used to quantify the {@link #getMeasurement()}
   * of this {@link Distance} is in {@link LengthUnit#METER meters}.
   *
   * @return a boolean value indicating whether the {@link #getLengthUnit()} used to quantify
   * the {@link #getMeasurement()} of this {@link Distance} is in {@link LengthUnit#METER meters}.
   * @see #isMetric(LengthUnit)
   * @see #getLengthUnit()
   * @see #isNonMetric()
   */
  public boolean isMetric() {
    return isMetric(getLengthUnit());
  }

  /**
   * Determines whether the {@link #getLengthUnit()} used to quantify the {@link #getMeasurement()}
   * of this {@link Distance} is not in {@link LengthUnit#METER meters}.
   *
   * @return a boolean value indicating whether the {@link #getLengthUnit()} used to quantify
   * the {@link #getMeasurement()} of this {@link Distance} is not in {@link LengthUnit#METER meters}.
   * @see #isMetric(LengthUnit)
   * @see #getLengthUnit()
   * @see #isMetric()
   */
  public boolean isNonMetric() {
    return !isMetric();
  }

  /**
   * Convert this {@link Distance} {@link #getMeasurement()} in {@link #getLengthUnit()}
   * to {@link LengthUnit#FOOT feet}.
   *
   * If this {@link Distance} is already {@link #getMeasurement() measured} in {@link LengthUnit#FOOT feet},
   * then this {@link Distance} is returned.
   *
   * @return this {@link Distance} in {@link LengthUnit#FOOT feet}.
   * @see Distance.ConversionFunctions#TO_FEET
   * @see #isInFeet()
   */
  public @NotNull Distance toFeet() {
    return isInFeet() ? this : ConversionFunctions.TO_FEET.apply(this);
  }

  /**
   * Convert this {@link Distance} {@link #getMeasurement()} in {@link #getLengthUnit()}
   * to {@link LengthUnit#KILOMETER kilometers}.
   *
   * If this {@link Distance} is already {@link #getMeasurement() measured} in {@link LengthUnit#KILOMETER kilometers},
   * then this {@link Distance} is returned.
   *
   * @return this {@link Distance} in {@link LengthUnit#KILOMETER kilometers}.
   * @see Distance.ConversionFunctions#TO_KILOMETERS
   * @see #isInKilometers()
   */
  public @NotNull Distance toKilometers() {
    return isInKilometers() ? this : ConversionFunctions.TO_KILOMETERS.apply(this);
  }

  /**
   * Convert this {@link Distance} {@link #getMeasurement()} in {@link #getLengthUnit()}
   * to {@link LengthUnit#METER meters}.
   *
   * If this {@link Distance} is already {@link #getMeasurement() measured} in {@link LengthUnit#METER meters},
   * then this {@link Distance} is returned.
   *
   * @return this {@link Distance} in {@link LengthUnit#METER meters}.
   * @see org.cp.elements.enums.LengthUnit#getMeterConversionFactor()
   * @see Distance.ConversionFunctions#TO_METERS
   * @see #getMeasurement()
   * @see #getLengthUnit()
   * @see #inMeters(double)
   * @see #isInMeters()
   */
  public @NotNull Distance toMeters() {
    return isInMeters() ? this : Distance.inMeters(getMeasurement() * getLengthUnit().getMeterConversionFactor());
  }

  /**
   * Convert this {@link Distance} {@link #getMeasurement()} in {@link #getLengthUnit()}
   * to {@link LengthUnit#MILE miles}.
   *
   * If this {@link Distance} is already {@link #getMeasurement() measured} in {@link LengthUnit#MILE miles},
   * then this {@link Distance} is returned.
   *
   * @return this {@link Distance} in {@link LengthUnit#MILE miles}.
   * @see Distance.ConversionFunctions#TO_MILES
   * @see #isInMiles()
   */
  public @NotNull Distance toMiles() {
    return isInMiles() ? this : ConversionFunctions.TO_MILES.apply(this);
  }

  /**
   * Convert this {@link Distance} {@link #getMeasurement()} in {@link #getLengthUnit()}
   * to {@link LengthUnit#YARD yards}.
   *
   * If this {@link Distance} is already {@link #getMeasurement() measured} in {@link LengthUnit#YARD yards},
   * then this {@link Distance} is returned.
   *
   * @return this {@link Distance} in {@link LengthUnit#YARD yards}.
   * @see Distance.ConversionFunctions#TO_YARDS
   * @see #isInYards()
   */
  public @NotNull Distance toYards() {
    return isInYards() ? this : ConversionFunctions.TO_YARDS.apply(this);
  }

  /**
   * Compares this {@link Distance} with the given, required {@link Distance} to determine relative ordering
   * in a sort data structure.
   *
   * Like {@link #equals(Object)}, this {@link Comparable#compareTo(Object)} method implementation evaluates
   * the measurement of the {@link Distance}. That is, {@literal 1 meter} is less than {@literal 1 kilometer},
   * but greater than {@literal 1 yard}, naturally. However, {@literal 3 feet} is equally comparable to
   * {@literal 1 yard}, and thus returns {@literal 0}.
   *
   * @param distance {@link Distance} to compare with this {@link Distance}; must not be {@literal null}.
   * @return an {@link Integer value} indicating the relative ordering of this {@link Distance}
   * with the given {@link Distance}. Return less than {@literal 0} if this {@link Distance} comes before
   * the given {@link Distance}; greater than {@literal 0} if this {@link Distance} comes after, and {@literal 0}
   * if this {@link Distance} is equal to the given {@link Distance}.
   * @see java.lang.Comparable#compareTo(Object)
   * @see #getMeasurement()
   * @see #toMeters()
   */
  @Override
  public int compareTo(@NotNull Distance distance) {
    return Double.compare(this.toMeters().getMeasurement(), distance.toMeters().getMeasurement());
  }

  /**
   * Determines whether this {@link Distance} is equal to the given {@link Object}.
   *
   * Two {@link Distance Distances} are {@literal equal} if their {@link #getMeasurement() measurements}
   * are the same (or equivalent) regardless of the base {@link #getLengthUnit() length unit}.  For example,
   * {@literal 1000 meters} is equal to {@literal 1 kilometer}.
   *
   * @param obj {@link Object} to test for equality with this {@link Distance}.
   * @return a boolean value indicating whether this {@link Distance} is equal to the given {@link Object}.
   * @see java.lang.Object#equals(Object)
   * @see #getMeasurement()
   * @see #toMeters()
   */
  @Override
  public boolean equals(@Nullable Object obj) {

    if (this == obj) {
      return true;
    }

    if (!(obj instanceof Distance that)) {
      return false;
    }

    return this.toMeters().getMeasurement() == that.toMeters().getMeasurement();
  }

  /**
   * Computes the {@literal hash code} of this {@link Distance}.
   *
   * @return the computed {@literal hash code} of this {@link Distance}.
   * @see java.lang.Object#hashCode()
   * @see #getMeasurement()
   * @see #getLengthUnit()
   */
  @Override
  public int hashCode() {
    return ObjectUtils.hashCodeOf(getMeasurement(), getLengthUnit());
  }

  /**
   * Returns a {@link String} representation of this {@link Distance}.
   *
   * @return a {@link String} describing this {@link Distance}.
   * @see java.lang.Object#toString()
   * @see #getMeasurement()
   * @see #getLengthUnit()
   */
  @Override
  public String toString() {

    double measurement = getMeasurement();

    String lengthUnitName = measurement != 1.0d
      ? getLengthUnit().getPluralName()
      : getLengthUnit().name();

    return String.format(DISTANCE_TO_STRING, measurement, lengthUnitName);
  }

  protected static abstract class Conversions {

    protected static final double FEET_IN_MILES = 5280.0d;
    protected static final double FEET_IN_YARDS = 3.0d;
    protected static final double INCHES_IN_FEET = 12.0d;
    protected static final double METERS_IN_FEET = 0.3048d;
    protected static final double METERS_IN_KILOMETERS = 1000.0d;

    /**
     * Given a {@link Double measurement} in {@link LengthUnit#FOOT feet},
     * converts the {@link Double measurement} to {@link LengthUnit#METER meters}.
     *
     * @param measurement {@link Double value} representing the {@literal measurement} to convert.
     * @return the given {@link Double measurement} in {@link LengthUnit#FOOT feet}
     * to {@link LengthUnit#METER meters}.
     */
    protected static double feetToMeters(double measurement) {
      return measurement * METERS_IN_FEET;
    }

    /**
     * Given a {@link Double measurement} in {@link LengthUnit#FOOT feet},
     * converts the {@link Double measurement} to {@link LengthUnit#MILE miles}.
     *
     * @param measurement {@link Double value} representing the {@literal measurement} to convert.
     * @return the given {@link Double measurement} in {@link LengthUnit#FOOT feet}
     * to {@link LengthUnit#MILE miles}.
     */
    protected static double feetToMiles(double measurement) {
      return measurement / FEET_IN_MILES;
    }

    /**
     * Given a {@link Double measurement} in {@link LengthUnit#FOOT feet},
     * converts the {@link Double measurement} to {@link LengthUnit#YARD yards}.
     *
     * @param measurement {@link Double value} representing the {@literal measurement} to convert.
     * @return the given {@link Double measurement} in {@link LengthUnit#FOOT feet}
     * to {@link LengthUnit#YARD yards}.
     */
    protected static double feetToYards(double measurement) {
      return measurement / FEET_IN_YARDS;
    }

    /**
     * Given a {@link Double measurement} in {@link LengthUnit#INCH inches},
     * converts the {@link Double measurement} to {@link LengthUnit#FOOT feet}.
     *
     * @param measurement {@link Double value} representing the {@literal measurement} to convert.
     * @return the given {@link Double measurement} in {@link LengthUnit#INCH inches}
     * to {@link LengthUnit#FOOT feet}.
     */
    protected static double inchesToFeet(double measurement) {
      return measurement / INCHES_IN_FEET;
    }

    /**
     * Given a {@link Double measurement} in {@link LengthUnit#KILOMETER kilometers},
     * converts the {@link Double measurement} to {@link LengthUnit#METER meters}.
     *
     * @param measurement {@link Double value} representing the {@literal measurement} to convert.
     * @return the given {@link Double measurement} in {@link LengthUnit#KILOMETER kilometers}
     * to {@link LengthUnit#METER meters}.
     */
    @SuppressWarnings("unused")
    protected static double kilometersToMeters(double measurement) {
      return measurement * METERS_IN_KILOMETERS;
    }

    /**
     * Given a {@link Double measurement} in {@link LengthUnit#METER meters},
     * converts the {@link Double measurement} to {@link LengthUnit#FOOT feet}.
     *
     * @param measurement {@link Double value} representing the {@literal measurement} to convert.
     * @return the given {@link Double measurement} in {@link LengthUnit#METER meters}
     * to {@link LengthUnit#FOOT feet}.
     */
    protected static double metersToFeet(double measurement) {
      return measurement / METERS_IN_FEET;
    }

    /**
     * Given a {@link Double measurement} in {@link LengthUnit#METER meters},
     * converts the {@link Double measurement} to {@link LengthUnit#KILOMETER kilometers}.
     *
     * @param measurement {@link Double value} representing the {@literal measurement} to convert.
     * @return the given {@link Double measurement} in {@link LengthUnit#METER meters}
     * to {@link LengthUnit#KILOMETER kilometers}.
     */
    protected static double metersToKilometers(double measurement) {
      return measurement / METERS_IN_KILOMETERS;
    }

    /**
     * Given a {@link Double measurement} in {@link LengthUnit#MILE miles},
     * converts the {@link Double measurement} to {@link LengthUnit#FOOT feet}.
     *
     * @param measurement {@link Double value} representing the {@literal measurement} to convert.
     * @return the given {@link Double measurement} in {@link LengthUnit#MILE miles}
     * to {@link LengthUnit#FOOT feet}.
     */
    protected static double milesToFeet(double measurement) {
      return measurement * FEET_IN_MILES;
    }

    /**
     * Given a {@link Double measurement} in {@link LengthUnit#YARD yards},
     * converts the {@link Double measurement} to {@link LengthUnit#FOOT feet}.
     *
     * @param measurement {@link Double value} representing the {@literal measurement} to convert.
     * @return the given {@link Double measurement} in {@link LengthUnit#YARD yards}
     * to {@link LengthUnit#FOOT feet}.
     */
    protected static double yardsToFeet(double measurement) {
      return measurement * FEET_IN_YARDS;
    }

    protected static double feetToKilometers(double measurement) {
      return metersToKilometers(feetToMeters(measurement));
    }

    protected static double inchesToKilometers(double measurement) {
      return feetToKilometers(inchesToFeet(measurement));
    }

    protected static double inchesToMiles(double measurement) {
      return feetToMiles(inchesToFeet(measurement));
    }

    protected static double inchesToYards(double measurement) {
      return feetToYards(inchesToFeet(measurement));
    }

    protected static double metersToMiles(double measurement) {
      return feetToMiles(metersToFeet(measurement));
    }

    protected static double metersToYards(double measurement) {
      return feetToYards(metersToFeet(measurement));
    }

    protected static double milesToKilometers(double measurement) {
      return feetToKilometers(milesToFeet(measurement));
    }

    protected static double milesToYards(double measurement) {
      return feetToYards(milesToFeet(measurement));
    }

    protected static double yardsToKilometers(double measurement) {
      return feetToKilometers(yardsToFeet(measurement));
    }

    protected static double yardsToMiles(double measurement) {
      return feetToMiles(yardsToFeet(measurement));
    }
  }

  public enum ConversionFunctions implements Function<Distance, Distance> {

    TO_FEET {

      @Override
      public @NotNull Distance apply(@NotNull Distance distance) {

        Assert.notNull(distance, "Distance is required");

        return distance.isInFeet() ? distance
          : isInInches(distance) ? Distance.inFeet(Conversions.inchesToFeet(distance.getMeasurement()))
          : distance.isInYards() ? Distance.inFeet(Conversions.yardsToFeet(distance.getMeasurement()))
          : distance.isInMiles() ? Distance.inFeet(Conversions.milesToFeet(distance.getMeasurement()))
          : Distance.inFeet(Conversions.metersToFeet(TO_METERS.apply(distance).getMeasurement()));
      }
    },

    TO_KILOMETERS {

      @Override
      public @NotNull Distance apply(@NotNull Distance distance) {

        Assert.notNull(distance, "Distance is required");

        return distance.isInKilometers() ? distance
          : isInInches(distance) ? Distance.inKilometers(Conversions.inchesToKilometers(distance.getMeasurement()))
          : distance.isInFeet() ? Distance.inKilometers(Conversions.feetToKilometers(distance.getMeasurement()))
          : distance.isInYards() ? Distance.inKilometers(Conversions.yardsToKilometers(distance.getMeasurement()))
          : distance.isInMiles() ? Distance.inKilometers(Conversions.milesToKilometers(distance.getMeasurement()))
          : Distance.inKilometers(Conversions.metersToKilometers(TO_METERS.apply(distance).getMeasurement()));
      }
    },

    TO_METERS {

      @Override
      public @NotNull Distance apply(@NotNull Distance distance) {
        return ObjectUtils.requireObject(distance, "Distance is required").toMeters();
      }
    },

    TO_MILES {

      @Override
      public @NotNull Distance apply(@NotNull Distance distance) {

        Assert.notNull(distance, "Distance is required");

        return distance.isInMiles() ? distance
          : isInInches(distance) ? Distance.inMiles(Conversions.inchesToMiles(distance.getMeasurement()))
          : distance.isInFeet() ? Distance.inMiles(Conversions.feetToMiles(distance.getMeasurement()))
          : distance.isInYards() ? Distance.inMiles(Conversions.yardsToMiles(distance.getMeasurement()))
          : Distance.inMiles(Conversions.metersToMiles(TO_METERS.apply(distance).getMeasurement()));
      }
    },

    TO_YARDS {

      @Override
      public @NotNull Distance apply(@NotNull Distance distance) {

        Assert.notNull(distance, "Distance is required");

        return distance.isInYards() ? distance
          : isInInches(distance) ? Distance.inYards(Conversions.inchesToYards(distance.getMeasurement()))
          : distance.isInFeet() ? Distance.inYards(Conversions.feetToYards(distance.getMeasurement()))
          : distance.isInMiles() ? Distance.inYards(Conversions.milesToYards(distance.getMeasurement()))
          : Distance.inYards(Conversions.metersToYards(TO_METERS.apply(distance).getMeasurement()));
      }
    };

    @NullSafe
    private static boolean isInInches(@Nullable Distance distance) {
      return distance != null && LengthUnit.INCH.equals(distance.getLengthUnit());
    }
  }
}
