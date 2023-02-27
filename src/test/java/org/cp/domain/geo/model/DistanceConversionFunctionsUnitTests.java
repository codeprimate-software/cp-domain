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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.withPrecision;

import org.junit.Test;

import org.cp.elements.enums.LengthUnit;

import org.assertj.core.data.Offset;

/**
 * Unit Tests for {@link Distance.ConversionFunctions}.
 *
 * @author John Blum
 * @see org.junit.Test
 * @see org.cp.domain.geo.model.Distance.ConversionFunctions
 * @since 0.1.0
 */
public class DistanceConversionFunctionsUnitTests {

  private Offset<Double> testPrecision() {
    return withPrecision(0.0001d);
  }

  private void assertDistance(Distance distance, double measurement, LengthUnit lengthUnit) {

    assertThat(distance).isNotNull();
    assertThat(distance.getLengthUnit()).isEqualTo(lengthUnit);
    assertThat(distance.getMeasurement()).isEqualTo(measurement, testPrecision());
  }

  private void assertInFeet(Distance distance, double measurement) {

    assertDistance(distance, measurement, LengthUnit.FOOT);
    assertThat(distance.isInFeet()).isTrue();
  }

  private void assertInKilometers(Distance distance, double measurement) {

    assertDistance(distance, measurement, LengthUnit.KILOMETER);
    assertThat(distance.isInKilometers()).isTrue();
  }

  private void assertInMiles(Distance distance, double measurement) {

    assertDistance(distance, measurement, LengthUnit.MILE);
    assertThat(distance.isInMiles()).isTrue();
  }

  private void assertInYards(Distance distance, double measurement) {

    assertDistance(distance, measurement, LengthUnit.YARD);
    assertThat(distance.isInYards()).isTrue();
  }

  private Distance inCentimeters(double measurement) {
    return Distance.of(measurement, LengthUnit.CENTIMETER);
  }

  private Distance inInches(double measurement) {
    return Distance.of(measurement, LengthUnit.INCH);
  }

  @Test
  public void toFeetFromInches() {

    Distance distance = Distance.ConversionFunctions.TO_FEET.apply(Distance.of(48.0d, LengthUnit.INCH));

    assertInFeet(distance, 4.0d);
  }

  @Test
  public void toFeetFromFeet() {

    Distance distance = Distance.inFeet(5.0);

    assertThat(Distance.ConversionFunctions.TO_FEET.apply(distance)).isSameAs(distance);
  }

  @Test
  public void toFeetFromYards() {

    Distance distance = Distance.ConversionFunctions.TO_FEET.apply(Distance.inYards(5.0d));

    assertInFeet(distance, 15.0d);
  }

  @Test
  public void toFeetFromMiles() {

    Distance distance = Distance.ConversionFunctions.TO_FEET.apply(Distance.inMiles(2.0d));

    assertInFeet(distance, 10560.0d);
  }

  @Test
  public void toFeetFromMeters() {

    Distance distance = Distance.ConversionFunctions.TO_FEET.apply(Distance.inMeters(3.0d));

    assertInFeet(distance, 9.8425d);
  }

  @Test
  public void toFeetFromKilometers() {

    Distance distance = Distance.ConversionFunctions.TO_FEET.apply(Distance.inKilometers(1.5d));

    assertInFeet(distance, 4921.2598d);
  }

  @Test
  public void toFeetFromCentimeters() {

    Distance distance = Distance.ConversionFunctions.TO_FEET.apply(Distance.of(36.0d, LengthUnit.CENTIMETER));

    assertInFeet(distance, 1.1811d);
  }

  @Test
  public void toFeetWithNullDistance() {

    assertThatIllegalArgumentException()
      .isThrownBy(() -> Distance.ConversionFunctions.TO_FEET.apply(null))
      .withMessage("Distance is required")
      .withNoCause();
  }

  @Test
  public void toKilometersFromInches() {

    Distance distance = Distance.ConversionFunctions.TO_KILOMETERS.apply(inInches(124.0d));

    assertInKilometers(distance, 0.0031d);
  }

  @Test
  public void toKilometersFromFeet() {

    Distance distance = Distance.ConversionFunctions.TO_KILOMETERS.apply(Distance.inFeet(256.0d));

    assertInKilometers(distance, 0.0780d);
  }

  @Test
  public void toKilometersFromYards() {

    Distance distance = Distance.ConversionFunctions.TO_KILOMETERS.apply(Distance.inYards(468.0d));

    assertInKilometers(distance, 0.4279d);
  }

  @Test
  public void toKilometersFromMiles() {

    Distance distance = Distance.ConversionFunctions.TO_KILOMETERS.apply(Distance.inMiles(201.0d));

    assertInKilometers(distance, 323.4781d);
  }

  @Test
  public void toKilometersFromMeters() {

    Distance distance = Distance.ConversionFunctions.TO_KILOMETERS.apply(Distance.inMeters(3690.0d));

    assertInKilometers(distance, 3.690d);
  }

  @Test
  public void toKilometersFromKilometers() {

    Distance distance = Distance.inKilometers(2.0d);

    assertThat(Distance.ConversionFunctions.TO_KILOMETERS.apply(distance)).isSameAs(distance);
  }

  @Test
  public void toKilometersFromCentimeters() {

    Distance distance = Distance.ConversionFunctions.TO_KILOMETERS.apply(inCentimeters(42.0d));

    assertInKilometers(distance, 0.0004d);
  }

  @Test
  public void toKilometersWithNullDistance() {

    assertThatIllegalArgumentException()
      .isThrownBy(() -> Distance.ConversionFunctions.TO_KILOMETERS.apply(null))
      .withMessage("Distance is required")
      .withNoCause();
  }

  @Test
  public void toMetersFromMeters() {

    Distance distance = Distance.inMeters(100.0d);

    assertThat(Distance.ConversionFunctions.TO_METERS.apply(distance)).isSameAs(distance);
  }

  @Test
  public void toMetersWithNullDistance() {

    assertThatIllegalArgumentException()
      .isThrownBy(() -> Distance.ConversionFunctions.TO_METERS.apply(null))
      .withMessage("Distance is required")
      .withNoCause();
  }

  @Test
  public void toMilesFromInches() {

    Distance distance = Distance.ConversionFunctions.TO_MILES.apply(inInches(3210.0d));

    assertInMiles(distance, 0.0507d);
  }

  @Test
  public void toMilesFromFeet() {

    Distance distance = Distance.ConversionFunctions.TO_MILES.apply(Distance.inFeet(1642.0d));

    assertInMiles(distance, 0.3109d);
  }

  @Test
  public void toMilesFromYards() {

    Distance distance = Distance.ConversionFunctions.TO_MILES.apply(Distance.inYards(4256.0d));

    assertInMiles(distance, 2.4182d);
  }

  @Test
  public void toMilesFromMiles() {

    Distance distance = Distance.inMiles(500.0d);

    assertThat(Distance.ConversionFunctions.TO_MILES.apply(distance)).isSameAs(distance);
  }

  @Test
  public void toMilesFromMeters() {

    Distance distance = Distance.ConversionFunctions.TO_MILES.apply(Distance.inMeters(2101.0d));

    assertInMiles(distance, 1.3055d);
  }

  @Test
  public void toMilesFromKilometers() {

    Distance distance = Distance.ConversionFunctions.TO_MILES.apply(Distance.inKilometers(10.0d));

    assertInMiles(distance, 6.2137d);
  }

  @Test
  public void toMilesFromCentimeters() {

    Distance distance = Distance.ConversionFunctions.TO_MILES.apply(inCentimeters(99_909.0d));

    assertInMiles(distance, 0.6208d);
  }

  @Test
  public void toMilesWithNullDistance() {

    assertThatIllegalArgumentException()
      .isThrownBy(() -> Distance.ConversionFunctions.TO_MILES.apply(null))
      .withMessage("Distance is required")
      .withNoCause();
  }

  @Test
  public void toYardsFromInches() {

    Distance distance = Distance.ConversionFunctions.TO_YARDS.apply(inInches(51.0d));

    assertInYards(distance, 1.4167d);
  }

  @Test
  public void toYardsFromFeet() {

    Distance distance = Distance.ConversionFunctions.TO_YARDS.apply(Distance.inFeet(42.0d));

    assertInYards(distance, 14.0d);
  }

  @Test
  public void toYardsFromYards() {

    Distance distance = Distance.inYards(100.0d);

    assertThat(Distance.ConversionFunctions.TO_YARDS.apply(distance)).isSameAs(distance);
  }

  @Test
  public void toYardsFromMiles() {

    Distance distance = Distance.ConversionFunctions.TO_YARDS.apply(Distance.inMiles(2.1d));

    assertInYards(distance, 3696.0d);
  }

  @Test
  public void toYardsFromMeters() {

    Distance distance = Distance.ConversionFunctions.TO_YARDS.apply(Distance.inMeters(72.5d));

    assertInYards(distance, 79.2869d);
  }

  @Test
  public void toYardsFromKilometers() {

    Distance distance = Distance.ConversionFunctions.TO_YARDS.apply(Distance.inKilometers(1.2d));

    assertInYards(distance, 1312.3359d);
  }

  @Test
  public void toYardsFromCentimeters() {

    Distance distance = Distance.ConversionFunctions.TO_YARDS.apply(inCentimeters(4210.0d));

    assertInYards(distance, 46.0411d);
  }

  @Test
  public void toYardsWithNullDistance() {

    assertThatIllegalArgumentException()
      .isThrownBy(() -> Distance.ConversionFunctions.TO_YARDS.apply(null))
      .withMessage("Distance is required")
      .withNoCause();
  }
}
