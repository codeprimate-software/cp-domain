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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.withPrecision;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import org.cp.domain.geo.model.Distance.Conversions;
import org.cp.elements.enums.LengthUnit;
import org.cp.elements.util.CollectionUtils;

/**
 * Unit Tests for {@link Distance}.
 *
 * @author John Blum
 * @see org.junit.jupiter.api.Test
 * @see org.cp.domain.geo.model.Distance
 * @see org.cp.elements.enums.LengthUnit
 * @since 0.1.0
 */
public class DistanceUnitTests {

  private void assertDistanceInFeet(Distance distance, double measurement) {
    assertDistanceInUnitLength(distance, measurement, LengthUnit.FOOT);
  }

  private void assertDistanceInKilometers(Distance distance, double measurement) {
    assertDistanceInUnitLength(distance, measurement, LengthUnit.KILOMETER);
  }

  private void assertDistanceInMeters(Distance distance, double measurement) {
    assertDistanceInUnitLength(distance, measurement, LengthUnit.METER);
  }

  private void assertDistanceInMiles(Distance distance, double measurement) {
    assertDistanceInUnitLength(distance, measurement, LengthUnit.MILE);
  }

  private void assertDistanceInYards(Distance distance, double measurement) {
    assertDistanceInUnitLength(distance, measurement, LengthUnit.YARD);
  }

  private void assertDistanceInUnitLength(Distance distance, double measurement, LengthUnit lengthUnit) {

    assertThat(distance).isNotNull();
    assertThat(distance.getLengthUnit()).isEqualTo(lengthUnit);
    assertThat(distance.getMeasurement()).isEqualTo(measurement, withPrecision(0.0001d));
  }

  private void assertDistanceOfOneInUnitLength(Distance distance, LengthUnit lengthUnit) {
    assertDistanceInUnitLength(distance, 1.0d, lengthUnit);
  }

  @Test
  public void constructNewDistance() {

    Distance distance = new Distance(1.0d, LengthUnit.METER);

    assertThat(distance).isNotNull();
    assertThat(distance.getMeasurement()).isOne();
    assertThat(distance.getLengthUnit()).isEqualTo(LengthUnit.METER);
  }

  @Test
  public void constructNewDistanceWithNegativeMeasurement() {

    assertThatIllegalArgumentException()
      .isThrownBy(() -> new Distance(-1.0d, LengthUnit.NANOMETER))
      .withMessage("The measurement of distance [-1.0] must be greater than equal to 0")
      .withNoCause();
  }

  @Test
  public void constructNewDistanceWithNullLengthUnit() {

    assertThatIllegalArgumentException()
      .isThrownBy(() -> new Distance(1.0d, null))
      .withMessage("LengthUnit is required")
      .withNoCause();
  }

  @Test
  public void inFeetIsCorrect() {
    assertDistanceOfOneInUnitLength(Distance.inFeet(1.0d), LengthUnit.FOOT);
  }

  @Test
  public void inKilometersIsCorrect() {
    assertDistanceOfOneInUnitLength(Distance.inKilometers(1.0d), LengthUnit.KILOMETER);
  }

  @Test
  public void inMetersIsCorrect() {
    assertDistanceOfOneInUnitLength(Distance.inMeters(1.0d), LengthUnit.METER);
  }

  @Test
  public void inMilesIsCorrect() {
    assertDistanceOfOneInUnitLength(Distance.inMiles(1.0d), LengthUnit.MILE);
  }

  @Test
  public void inYardsIsCorrect() {
    assertDistanceOfOneInUnitLength(Distance.inYards(1.0d), LengthUnit.YARD);
  }

  @Test
  public void ofMeasurementIsCorrect() {
    assertDistanceOfOneInUnitLength(Distance.of(1.0d), LengthUnit.getDefault());
  }

  @Test
  public void ofMeasurementInLengthUnitIsCorrect() {
    assertDistanceOfOneInUnitLength(Distance.of(1.0d, LengthUnit.MICROMETER), LengthUnit.MICROMETER);
  }

  @Test
  public void lengthUnitIsMetric() {

    Set<LengthUnit> nonMetricUnits = CollectionUtils
      .asSet(LengthUnit.INCH, LengthUnit.FOOT, LengthUnit.YARD, LengthUnit.MILE, LengthUnit.LIGHT_YEAR);

    Arrays.stream(LengthUnit.values()).forEach(lengthUnit ->
      assertThat(Distance.isMetric(lengthUnit)).isEqualTo(!nonMetricUnits.contains(lengthUnit)));
  }

  @Test
  public void lengthUnitIsNotMetric() {

    Set<LengthUnit> nonMetricUnits = CollectionUtils
      .asSet(LengthUnit.INCH, LengthUnit.FOOT, LengthUnit.YARD, LengthUnit.MILE, LengthUnit.LIGHT_YEAR);

    Arrays.stream(LengthUnit.values()).forEach(lengthUnit ->
      assertThat(!Distance.isMetric(lengthUnit)).isEqualTo(nonMetricUnits.contains(lengthUnit)));
  }

  @Test
  public void nullLengthUnitIsNotMetricAndIsNullSafe() {
    assertThat(Distance.isMetric(null)).isFalse();
  }

  @Test
  public void isInFeetReturnsTrue() {
    assertThat(Distance.of(10.0d, LengthUnit.FOOT).isInFeet()).isTrue();
  }

  @Test
  public void isInFeetReturnsFalse() {

    assertThat(Distance.of(12.0d, LengthUnit.INCH).isInFeet()).isFalse();
    assertThat(Distance.of(1.0d, LengthUnit.METER).isInFeet()).isFalse();
  }

  @Test
  public void isInKilometersReturnsTrue() {
    assertThat(Distance.of(2.0d, LengthUnit.KILOMETER).isInKilometers()).isTrue();
  }

  @Test
  public void isInKilometersReturnsFalse() {

    assertThat(Distance.of(1000.0d, LengthUnit.METER).isInKilometers()).isFalse();
    assertThat(Distance.of(1.0d, LengthUnit.MILE).isInKilometers()).isFalse();
  }

  @Test
  public void isInMetersReturnsTrue() {
    assertThat(Distance.of(10.0d, LengthUnit.METER).isInMeters()).isTrue();
  }

  @Test
  public void isInMetersReturnsFalse() {

    assertThat(Distance.of(1000.0d, LengthUnit.MILLIMETER).isInMeters()).isFalse();
    assertThat(Distance.of(100.0d, LengthUnit.CENTIMETER).isInMeters()).isFalse();
    assertThat(Distance.of(3.28084d, LengthUnit.FOOT).isInMeters()).isFalse();
    assertThat(Distance.of(1.0d, LengthUnit.YARD).isInMeters()).isFalse();
  }

  @Test
  public void isInMilesReturnsTrue() {
    assertThat(Distance.of(100.0d, LengthUnit.MILE).isInMiles()).isTrue();
  }

  @Test
  public void isInMilesReturnsFalse() {

    assertThat(Distance.of(Conversions.FEET_IN_MILES, LengthUnit.FOOT).isInMiles()).isFalse();
    assertThat(Distance.of(1.60934, LengthUnit.KILOMETER).isInMiles()).isFalse();
  }

  @Test
  public void isInYardsReturnsTrue() {
    assertThat(Distance.of(100, LengthUnit.YARD).isInYards()).isTrue();
  }

  @Test
  public void isInYardsReturnsFalse() {

    assertThat(Distance.of(36.0d, LengthUnit.INCH).isInYards()).isFalse();
    assertThat(Distance.of(3.0d, LengthUnit.FOOT).isInYards()).isFalse();
    assertThat(Distance.of(1.0d, LengthUnit.METER).isInYards()).isFalse();
  }

  @Test
  public void isMetricReturnsTrue() {

    assertThat(Distance.of(1000.0d, LengthUnit.NANOMETER).isMetric()).isTrue();
    assertThat(Distance.of(1000.0d, LengthUnit.MILLIMETER).isMetric()).isTrue();
    assertThat(Distance.of(100.0d, LengthUnit.METER).isMetric()).isTrue();
    assertThat(Distance.of(10.0d, LengthUnit.KILOMETER).isMetric()).isTrue();
    assertThat(Distance.of(10.0d, LengthUnit.MEGAMETER).isMetric()).isTrue();
  }

  @Test
  public void isMetricReturnsFalse() {

    assertThat(Distance.of(Conversions.METERS_IN_FEET, LengthUnit.FOOT).isMetric()).isFalse();
    assertThat(Distance.of(1.0d, LengthUnit.YARD).isMetric()).isFalse();
  }

  @Test
  public void isNonMetricReturnsTrue() {

    assertThat(Distance.of(1.0d, LengthUnit.INCH).isNonMetric()).isTrue();
    assertThat(Distance.of(10.0d, LengthUnit.FOOT).isNonMetric()).isTrue();
    assertThat(Distance.of(100.0d, LengthUnit.YARD).isNonMetric()).isTrue();
    assertThat(Distance.of(5.0d, LengthUnit.MILE).isNonMetric()).isTrue();
  }

  @Test
  public void isNonMetricReturnsFalse() {

    assertThat(Distance.of(10_000.0d, LengthUnit.MICROMETER).isNonMetric()).isFalse();
    assertThat(Distance.of(100.0d, LengthUnit.METER).isNonMetric()).isFalse();
    assertThat(Distance.of(1.0d, LengthUnit.KILOMETER).isNonMetric()).isFalse();
  }

  @Test
  public void toFeetFromFeetIsSame() {

    Distance distance = Distance.inFeet(12.0d);

    assertDistanceInFeet(distance, 12.0d);
    assertThat(distance.toFeet()).isSameAs(distance);
  }

  @Test
  public void toFeetIsCorrect() {

    assertDistanceInFeet(Distance.of(51.0d, LengthUnit.MILLIMETER).toFeet(), 0.1673d);
    assertDistanceInFeet(Distance.of(725.0d, LengthUnit.CENTIMETER).toFeet(), 23.7861d);
    assertDistanceInFeet(Distance.of(400.0d, LengthUnit.YARD).toFeet(), 1200.0d);
    assertDistanceInFeet(Distance.of(10.0d, LengthUnit.METER).toFeet(), 32.8084d);
    assertDistanceInFeet(Distance.of(2.0d, LengthUnit.KILOMETER).toFeet(), 6561.6797d);
    assertDistanceInFeet(Distance.of(5.0d, LengthUnit.MILE).toFeet(), 26400.0d);
  }

  @Test
  public void toKilometersFromKilometersIsSame() {

    Distance distance = Distance.inKilometers(2.0d);

    assertDistanceInKilometers(distance, 2.0d);
    assertThat(distance.toKilometers()).isSameAs(distance);
  }

  @Test
  public void toKilometersIsCorrect() {

    assertDistanceInKilometers(Distance.of(120.0d, LengthUnit.INCH).toKilometers(), 0.0030d);
    assertDistanceInKilometers(Distance.of(32_000.0d, LengthUnit.FOOT).toKilometers(), 9.7536d);
    assertDistanceInKilometers(Distance.of(25.0d, LengthUnit.YARD).toKilometers(), 0.0229d);
    assertDistanceInKilometers(Distance.of(1000.0d, LengthUnit.METER).toKilometers(), 1.0d);
    assertDistanceInKilometers(Distance.of(60.0d, LengthUnit.MILE).toKilometers(), 96.5606d);
  }

  @Test
  public void toMetersFromMetersIsSame() {

    Distance distance = Distance.inMeters(100.0d);

    assertDistanceInMeters(distance, 100.0d);
    assertThat(distance.toMeters()).isSameAs(distance);
  }

  @Test
  public void toMetersIsCorrect() {

    assertDistanceInMeters(Distance.of(5000.0d, LengthUnit.INCH).toMeters(), 127.0d);
    assertDistanceInMeters(Distance.of(6.0d, LengthUnit.FOOT).toMeters(), 1.8288d);
    assertDistanceInMeters(Distance.of(100.0d, LengthUnit.YARD).toMeters(), 91.44d);
    assertDistanceInMeters(Distance.of(3.0d, LengthUnit.KILOMETER).toMeters(), 3000.0d);
    assertDistanceInMeters(Distance.of(10.0d, LengthUnit.MILE).toMeters(), 16093.4d);
  }

  @Test
  public void toMilesFromMilesIsSame() {

    Distance distance = Distance.inMiles(5.0d);

    assertDistanceInMiles(distance, 5.0f);
    assertThat(distance.toMiles()).isSameAs(distance);
  }

  @Test
  public void toMilesIsCorrect() {

    assertDistanceInMiles(Distance.of(500.0d, LengthUnit.FOOT).toMiles(), 0.0947d);
    assertDistanceInMiles(Distance.of(4000.0d, LengthUnit.METER).toMiles(), 2.4854d);
    assertDistanceInMiles(Distance.of(7200.0d, LengthUnit.YARD).toMiles(), 4.0909d);
  }

  @Test
  public void toYardsFromYardsIsSame() {

    Distance distance = Distance.inYards(100.0d);

    assertDistanceInYards(distance, 100.0f);
    assertThat(distance.toYards()).isSameAs(distance);
  }

  @Test
  public void toYardsIsCorrect() {

    assertDistanceInYards(Distance.of(36.0d, LengthUnit.INCH).toYards(), 1.0d);
    assertDistanceInYards(Distance.of(300.0d, LengthUnit.FOOT).toYards(), 100.0d);
    assertDistanceInYards(Distance.of(1000.0d, LengthUnit.METER).toYards(), 1093.6133d);
    assertDistanceInYards(Distance.of(1.0d, LengthUnit.KILOMETER).toYards(), 1093.6133d);
  }

  @Test
  public void compareToIsCorrect() {

    Distance fiveMiles = Distance.inMiles(5.0d);
    Distance fourYards = Distance.inYards(4.0d);
    Distance eightThousandMeters = Distance.inMeters(8_000.0d);
    Distance sixThousandCentimeters = Distance.of(6000.0d, LengthUnit.CENTIMETER);
    Distance thirtySizeThousandFeet = Distance.inFeet(36_256.0d);
    Distance twoKilometers = Distance.inKilometers(2.0d);

    List<Distance> distances = new ArrayList<>(Arrays.asList(
      fiveMiles,
      thirtySizeThousandFeet,
      eightThousandMeters,
      twoKilometers,
      sixThousandCentimeters,
      fourYards
    ));

    Collections.sort(distances);

    assertThat(distances).containsExactly(fourYards, sixThousandCentimeters, twoKilometers, eightThousandMeters,
      fiveMiles, thirtySizeThousandFeet);
  }

  @Test
  public void equalsEqualDistanceReturnsTrue() {

    Distance oneHundredMetersOne = Distance.inMeters(100.0d);
    Distance oneHundredMetersTwo = Distance.inMeters(100.0d);

    assertThat(oneHundredMetersOne).isNotNull();
    assertThat(oneHundredMetersOne).isEqualTo(oneHundredMetersTwo);
    assertThat(oneHundredMetersOne).isNotSameAs(oneHundredMetersTwo);
    assertThat(oneHundredMetersOne.equals(oneHundredMetersTwo)).isTrue();
  }

  @Test
  public void equalsEquivalentDistanceReturnsTrue() {

    Distance oneThousandMeters = Distance.inMeters(1000.0d);
    Distance oneKilometer = oneThousandMeters.toKilometers();
    Distance threeThousandTwoHundredEightyFeet = oneThousandMeters.toFeet();

    assertDistanceInKilometers(oneKilometer, 1.0d);
    assertDistanceInFeet(threeThousandTwoHundredEightyFeet, 3280.8399d);
    assertThat(oneThousandMeters).isEqualTo(oneKilometer);
    assertThat(oneKilometer).isEqualTo(threeThousandTwoHundredEightyFeet);
    assertThat(threeThousandTwoHundredEightyFeet).isEqualTo(oneThousandMeters);
  }

  @Test
  @SuppressWarnings("all")
  public void equalsSelfReturnsTrue() {

    Distance distance = Distance.inMeters(100.0d);

    assertThat(distance).isNotNull();
    assertThat(distance).isEqualTo(distance);
    assertThat(distance.equals(distance)).isTrue();
  }

  @Test
  public void equalsNullIsNullSafeReturnsFalse() {
    assertThat(Distance.inMeters(100.0d)).isNotEqualTo(null);
  }

  @Test
  public void equalsObjectReturnsFalse() {
    assertThat(Distance.inMeters(100.0d)).isNotEqualTo("100 meters").isNotEqualTo("100m");
  }

  @Test
  public void hashCodeIsCorrect() {

    Distance distance = Distance.inMeters(1234.0d);

    int distanceHashCode = distance.hashCode();

    assertThat(distanceHashCode).isNotZero();
    assertThat(distanceHashCode).isEqualTo(distance.hashCode());
    assertThat(distance).hasSameHashCodeAs(Distance.inMeters(1234.0d));
    assertThat(distance).doesNotHaveSameHashCodeAs(Distance.inKilometers(1.2340d));
  }

  @Test
  public void toStringIsCorrect() {

    assertThat(Distance.inMeters(1.0d).toString()).isEqualTo("1.0 METER");
    assertThat(Distance.inMeters(100.0d).toString()).isEqualTo("100.0 METERS");
    assertThat(Distance.of(1.0d, LengthUnit.INCH).toString()).isEqualTo("1.0 INCH");
    assertThat(Distance.of(2.0d, LengthUnit.INCH).toString()).isEqualTo("2.0 INCHES");
    assertThat(Distance.inFeet(1.0d).toString()).isEqualTo("1.0 FOOT");
    assertThat(Distance.inFeet(2.0d).toString()).isEqualTo("2.0 FEET");
  }
}
