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
import static org.assertj.core.api.Assertions.withPrecision;

import org.junit.Test;

import org.cp.domain.geo.model.Distance.Conversions;

import org.assertj.core.data.Offset;

/**
 * Unit Tests for {@link Distance.Conversions}.
 *
 * @author John Blum
 * @see org.junit.Test
 * @see org.cp.domain.geo.model.Distance.Conversions
 * @since 0.1.0
 */
public class DistanceConversionsUnitTests {

  private Offset<Double> testPrecision() {
    return withPrecision(0.0001d);
  }

  @Test
  public void feetToMetersIsCorrect() {

    assertThat(Distance.Conversions.feetToMeters(1.0d)).isEqualTo(Distance.Conversions.METERS_IN_FEET);
    assertThat(Distance.Conversions.feetToMeters(3.0d)).isEqualTo(0.9144d, testPrecision());
    assertThat(Distance.Conversions.feetToMeters(12.0d)).isEqualTo(3.6576d, testPrecision());
  }

  @Test
  public void feetToMilesIsCorrect() {

    assertThat(Distance.Conversions.feetToMiles(528.0d)).isEqualTo(0.1d);
    assertThat(Distance.Conversions.feetToMiles(Conversions.FEET_IN_MILES)).isOne();
    assertThat(Distance.Conversions.feetToMiles(10_000d)).isEqualTo(1.8939d, testPrecision());
  }

  @Test
  public void feetToYardsIsCorrect() {

    assertThat(Distance.Conversions.feetToYards(1.0d)).isEqualTo(0.3333, testPrecision());
    assertThat(Distance.Conversions.feetToYards(Distance.Conversions.FEET_IN_YARDS)).isOne();
    assertThat(Distance.Conversions.feetToYards(12.0d)).isEqualTo(4.0d);
  }

  @Test
  public void inchesToFeetIsCorrect() {

    assertThat(Distance.Conversions.inchesToFeet(6.0d)).isEqualTo(0.5d);
    assertThat(Distance.Conversions.inchesToFeet(Distance.Conversions.INCHES_IN_FEET)).isOne();
    assertThat(Distance.Conversions.inchesToFeet(18.0d)).isEqualTo(1.5d);
  }

  @Test
  public void kilometersToMetersIsCorrect() {

    assertThat(Distance.Conversions.kilometersToMeters(2.5d)).isEqualTo(2_500.0d);
    assertThat(Distance.Conversions.kilometersToMeters(1.0d)).isEqualTo(Distance.Conversions.METERS_IN_KILOMETERS);
    assertThat(Distance.Conversions.kilometersToMeters(0.25d)).isEqualTo(250.0d);
  }

  @Test
  public void metersToFeetIsCorrect() {

    assertThat(Distance.Conversions.metersToFeet(0.25d)).isEqualTo(0.82021, testPrecision());
    assertThat(Distance.Conversions.metersToFeet(1.0d)).isEqualTo(3.2808d, testPrecision());
    assertThat(Distance.Conversions.metersToFeet(3.0d)).isEqualTo(9.8425d, testPrecision());
  }

  @Test
  public void metersToKilometersIsCorrect() {

    assertThat(Distance.Conversions.metersToKilometers(125.0d)).isEqualTo(0.125d);
    assertThat(Distance.Conversions.metersToKilometers(Distance.Conversions.METERS_IN_KILOMETERS)).isOne();
    assertThat(Distance.Conversions.metersToKilometers(2_500.0d)).isEqualTo(2.5d);
  }

  @Test
  public void milesToFeetIsCorrect() {

    assertThat(Distance.Conversions.milesToFeet(0.5d)).isEqualTo(2640.0d);
    assertThat(Distance.Conversions.milesToFeet(1.0d)).isEqualTo(Distance.Conversions.FEET_IN_MILES);
    assertThat(Distance.Conversions.milesToFeet(12.0d)).isEqualTo(63360.0d);
  }

  @Test
  public void yardsToFeet() {

    assertThat(Distance.Conversions.yardsToFeet(0.5d)).isEqualTo(1.5d);
    assertThat(Distance.Conversions.yardsToFeet(1.0d)).isEqualTo(Distance.Conversions.FEET_IN_YARDS);
    assertThat(Distance.Conversions.yardsToFeet(3.0d)).isEqualTo(9.0d);
  }

  @Test
  public void feetToKilometerIsCorrect() {

    assertThat(Distance.Conversions.feetToKilometers(12.0d)).isEqualTo(0.0037d, testPrecision());
    assertThat(Distance.Conversions.feetToKilometers(36_000d)).isEqualTo(10.9728d, testPrecision());
    assertThat(Distance.Conversions.feetToKilometers(121_000d)).isEqualTo(36.8808d, testPrecision());
  }

  @Test
  public void inchesToKilometersIsCorrect() {

    assertThat(Distance.Conversions.inchesToKilometers(5.0d)).isEqualTo(0.0001d, testPrecision());
    assertThat(Distance.Conversions.inchesToKilometers(144.0d)).isEqualTo(0.0037d, testPrecision());
    assertThat(Distance.Conversions.inchesToKilometers(10_000.0d)).isEqualTo(0.254d);
  }

  @Test
  public void inchesToMilesIsCorrect() {

    assertThat(Distance.Conversions.inchesToMiles(10_000d)).isEqualTo(0.1578d, testPrecision());
    assertThat(Distance.Conversions.inchesToMiles(500.0d)).isEqualTo(0.0079d, testPrecision());
    assertThat(Distance.Conversions.inchesToMiles(25.0d)).isEqualTo(0.0004d, testPrecision());
  }

  @Test
  public void inchesToYardsIsCorrect() {

    assertThat(Distance.Conversions.inchesToYards(6.0d)).isEqualTo(0.16667d, testPrecision());
    assertThat(Distance.Conversions.inchesToYards(36.0d)).isOne();
    assertThat(Distance.Conversions.inchesToYards(144.0d)).isEqualTo(4.0d);
  }

  @Test
  public void metersToMilesIsCorrect() {

    assertThat(Distance.Conversions.metersToMiles(500.0d)).isEqualTo(0.3107d, testPrecision());
    assertThat(Distance.Conversions.metersToMiles(2000.0d)).isEqualTo(1.2427d, testPrecision());
    assertThat(Distance.Conversions.metersToMiles(10_000.0d)).isEqualTo(6.2137d, testPrecision());
  }

  @Test
  public void metersToYardsIsCorrect() {

    assertThat(Distance.Conversions.metersToYards(0.5d)).isEqualTo(0.5468d, testPrecision());
    assertThat(Distance.Conversions.metersToYards(1.0d)).isEqualTo(1.0936d, testPrecision());
    assertThat(Distance.Conversions.metersToYards(5.0d)).isEqualTo(5.4680d, testPrecision());
  }

  @Test
  public void milesToKilometersIsCorrect() {

    assertThat(Distance.Conversions.milesToKilometers(0.25d)).isEqualTo(0.4023d, testPrecision());
    assertThat(Distance.Conversions.milesToKilometers(1.0d)).isEqualTo(1.6093d, testPrecision());
    assertThat(Distance.Conversions.milesToKilometers(5.0d)).isEqualTo(8.0467d, testPrecision());
    assertThat(Distance.Conversions.milesToKilometers(12.0d)).isEqualTo(19.3121d, testPrecision());
    assertThat(Distance.Conversions.milesToKilometers(60.0d)).isEqualTo(96.5606d, testPrecision());
  }

  @Test
  public void milesToYardsIsCorrect() {

    assertThat(Distance.Conversions.milesToYards(0.5d)).isEqualTo(880.0d);
    assertThat(Distance.Conversions.milesToYards(2.5d)).isEqualTo(4400.0d);
    assertThat(Distance.Conversions.milesToYards(8.0d)).isEqualTo(14080.0d);
  }

  @Test
  public void yardsToKilometersIsCorrect() {

    assertThat(Distance.Conversions.yardsToKilometers(100.0d)).isEqualTo(0.0914d, testPrecision());
    assertThat(Distance.Conversions.yardsToKilometers(2480.0d)).isEqualTo(2.2677d, testPrecision());
    assertThat(Distance.Conversions.yardsToKilometers(12600.0d)).isEqualTo(11.5214d, testPrecision());
  }

  @Test
  public void yardsToMilesIsCorrect() {

    assertThat(Distance.Conversions.yardsToMiles(100.0d)).isEqualTo(0.0568d, testPrecision());
    assertThat(Distance.Conversions.yardsToMiles(2480.0d)).isEqualTo(1.4091d, testPrecision());
    assertThat(Distance.Conversions.yardsToMiles(12600.0d)).isEqualTo(7.1591d, testPrecision());
  }
}
