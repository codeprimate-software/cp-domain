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

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import org.cp.elements.enums.LengthUnit;
import org.cp.elements.io.IOUtils;

/**
 * Unit Tests for {@link Elevation}.
 *
 * @author John Blum
 * @see org.junit.jupiter.api.Test
 * @see org.cp.domain.geo.model.Elevation
 * @since 0.1.0
 */
public class ElevationUnitTests {

  @Test
  public void constructElevationAboveSeaLevel() {

    Elevation elevation = new Elevation(1.0d);

    assertThat(elevation).isNotNull();
    assertThat(elevation.getAltitude()).isEqualTo(1.0d);
    assertThat(elevation.getLengthUnit()).isEqualTo(LengthUnit.getDefault());
  }

  @Test
  public void constructElevationAtSeaLevel() {

    Elevation elevation = new Elevation(Elevation.SEA_LEVEL_VALUE);

    assertThat(elevation).isNotNull();
    assertThat(elevation.getAltitude()).isEqualTo(0.0d);
    assertThat(elevation.getLengthUnit()).isEqualTo(LengthUnit.getDefault());
  }

  @Test
  public void constructElevationBelowSeaLevel() {

    Elevation elevation = new Elevation(-1.0d);

    assertThat(elevation).isNotNull();
    assertThat(elevation.getAltitude()).isEqualTo(-1.0d);
    assertThat(elevation.getLengthUnit()).isEqualTo(LengthUnit.getDefault());
  }

  @Test
  public void elevationAtAltitudeIsCorrect() {

    Elevation elevation = Elevation.at(100.0d);

    assertThat(elevation).isNotNull();
    assertThat(elevation.getAltitude()).isEqualTo(100.0d);
    assertThat(elevation.getLengthUnit()).isEqualTo(LengthUnit.getDefault());
  }

  @Test
  public void elevationAtSeaLevelIsCorrect() {

    Elevation elevation = Elevation.atSeaLevel();

    assertThat(elevation).isNotNull();
    assertThat(elevation.getAltitude()).isEqualTo(Elevation.SEA_LEVEL_VALUE);
    assertThat(elevation.getLengthUnit()).isEqualTo(LengthUnit.getDefault());
  }

  @Test
  public void setAndGetLengthUnit() {

    Elevation elevation = Elevation.atSeaLevel();

    assertThat(elevation).isNotNull();
    assertThat(elevation.getLengthUnit()).isEqualTo(LengthUnit.getDefault());

    elevation.setLengthUnit(LengthUnit.KILOMETER);

    assertThat(elevation.getLengthUnit()).isEqualTo(LengthUnit.KILOMETER);

    elevation.setLengthUnit(null);

    assertThat(elevation.getLengthUnit()).isEqualTo(LengthUnit.getDefault());
  }

  @Test
  public void inSetsLengthUnitReturnsThis() {

    Elevation elevation = Elevation.at(1.0d);

    assertThat(elevation).isNotNull();
    assertThat(elevation.getAltitude()).isEqualTo(1.0d);
    assertThat(elevation.getLengthUnit()).isEqualTo(LengthUnit.getDefault());
    assertThat(elevation.in(LengthUnit.INCH)).isSameAs(elevation);
    assertThat(elevation.getAltitude()).isEqualTo(1.0d);
    assertThat(elevation.getLengthUnit()).isEqualTo(LengthUnit.INCH);
    assertThat(elevation.in(LengthUnit.KILOMETER)).isSameAs(elevation);
    assertThat(elevation.getAltitude()).isEqualTo(1.0d);
    assertThat(elevation.getLengthUnit()).isEqualTo(LengthUnit.KILOMETER);
    assertThat(elevation.in(null)).isSameAs(elevation);
    assertThat(elevation.getAltitude()).isEqualTo(1.0d);
    assertThat(elevation.getLengthUnit()).isEqualTo(LengthUnit.getDefault());
  }

  @Test
  public void inFeetSetsLengthUnitToFootReturnsThis() {

    Elevation elevation = Elevation.at(1.0d).inFeet();

    assertThat(elevation).isNotNull();
    assertThat(elevation.getAltitude()).isEqualTo(1.0d);
    assertThat(elevation.getLengthUnit()).isEqualTo(LengthUnit.FOOT);
  }

  @Test
  public void inMetersSetsLengthUnitToMeterReturnsThis() {

    Elevation elevation = Elevation.at(1.0d).inMeters();

    assertThat(elevation).isNotNull();
    assertThat(elevation.getAltitude()).isEqualTo(1.0d);
    assertThat(elevation.getLengthUnit()).isEqualTo(LengthUnit.METER);
  }

  @Test
  public void isAboveSeaLevelReturnsTrue() {
    assertThat(Elevation.at(1.0d).isAboveSeaLevel()).isTrue();
  }

  @Test
  public void isAboveSeaLevelReturnsFalse() {

    assertThat(Elevation.atSeaLevel().isAboveSeaLevel()).isFalse();
    assertThat(Elevation.at(-1.0d).isAboveSeaLevel()).isFalse();
  }

  @Test
  public void isAtSeaLevelReturnsTrue() {
    assertThat(Elevation.atSeaLevel().isAtSeaLevel()).isTrue();
  }

  @Test
  public void isAtSeaLevelReturnsFalse() {

    assertThat(Elevation.at(1.0d).isAtSeaLevel()).isFalse();
    assertThat(Elevation.at(-1.0d).isAtSeaLevel()).isFalse();
  }

  @Test
  public void isBelowSeaLevelReturnsTrue() {
    assertThat(Elevation.at(-1.0d).isBelowSeaLevel()).isTrue();
  }

  @Test
  public void isBelowSeaLevelReturnsFalse() {

    assertThat(Elevation.atSeaLevel().isBelowSeaLevel()).isFalse();
    assertThat(Elevation.at(1.0d).isBelowSeaLevel()).isFalse();
  }

  @Test
  public void compareToIsCorrect() {

    Elevation fortyEightThousandInches = Elevation.at(48_000.0d).in(LengthUnit.INCH);
    Elevation thirtyTwoThousandFeet = Elevation.at(32_000.0d).inFeet();
    Elevation negativeFiveMiles = Elevation.at(-5.0d).in(LengthUnit.MILE);
    Elevation fourThousandMeters = Elevation.at(4_000d).inMeters();
    Elevation twoKilometers = Elevation.at(2.0d).in(LengthUnit.KILOMETER);

    List<Elevation> elevations = Arrays.asList(
      twoKilometers,
      fourThousandMeters,
      negativeFiveMiles,
      thirtyTwoThousandFeet,
      fortyEightThousandInches
    );

    Collections.sort(elevations);

    assertThat(elevations).containsExactly(negativeFiveMiles, fortyEightThousandInches, twoKilometers,
      fourThousandMeters, thirtyTwoThousandFeet);
  }

  @Test
  public void equalElevationsAreEqual() {

    Elevation elevationOne = Elevation.at(1.0d).inMeters();
    Elevation elevationTwo = Elevation.at(1.0d).inMeters();

    assertThat(elevationOne).isNotNull();
    assertThat(elevationOne).isNotSameAs(elevationTwo);
    assertThat(elevationOne).isEqualTo(elevationTwo);
  }

  @Test
  public void equalElevationsWithCommonMeasurementsAreEqual() {

    assertThat(Elevation.atSeaLevel().inFeet()).isEqualTo(Elevation.atSeaLevel().inMeters());

    Elevation twelveInches = Elevation.at(12.0d).in(LengthUnit.INCH);
    Elevation oneFoot = Elevation.at(1.0d).inFeet();

    assertThat(twelveInches)
      .describedAs("12 in. as meters [%s]; 1 foot as meters [%s]",
        twelveInches.toMeasurementInMeters(), oneFoot.toMeasurementInMeters())
      .isEqualTo(oneFoot);

    assertThat(Elevation.at(3.0d).inFeet()).isEqualTo(Elevation.at(1.0d).in(LengthUnit.YARD));
    assertThat(Elevation.at(5280.0d).inFeet()).isEqualTo(Elevation.at(1.0d).in(LengthUnit.MILE));
    assertThat(Elevation.at(1_000.0d).inMeters()).isEqualTo(Elevation.at(1.0d).in(LengthUnit.KILOMETER));
  }

  @Test
  public void equalElevationsWithEquivalentMeasurementsAreEqual() {

    Elevation fiveMiles = Elevation.at(5).in(LengthUnit.MILE);
    Elevation elevationInMeters = Elevation.at(8046.7d).inMeters();

    assertThat(fiveMiles).isNotNull();
    assertThat(fiveMiles).isNotSameAs(elevationInMeters);
    assertThat(fiveMiles).isEqualTo(elevationInMeters);
  }

  @Test
  public void identicalElevationsAreEqual() {

    Elevation elevation = Elevation.at(1.0d).inMeters();

    assertThat(elevation).isNotNull();
    assertThat(elevation).isSameAs(elevation);
    assertThat(elevation).isEqualTo(elevation);
  }

  @Test
  public void unequalElevationsAreNotEqual() {
    assertThat(Elevation.at(1.0d).inFeet()).isNotEqualTo(Elevation.at(1.0d).inMeters());
  }

  @Test
  public void equalsNullIsNullSafeReturnsFalse() {
    assertThat(Elevation.atSeaLevel()).isNotEqualTo(null);
  }

  @Test
  public void equalsObjectReturnsFalse() {
    assertThat(Elevation.atSeaLevel()).isNotEqualTo("test");
  }

  @Test
  public void hashCodeIsCorrect() {

    Elevation elevation = Elevation.at(1.0d).inFeet();

    assertThat(elevation).isNotNull();
    assertThat(elevation.getAltitude()).isEqualTo(1.0d);
    assertThat(elevation.getLengthUnit()).isEqualTo(LengthUnit.FOOT);

    int hashCodeInFeet = elevation.hashCode();

    assertThat(hashCodeInFeet).isNotZero();
    assertThat(hashCodeInFeet).isEqualTo(elevation.hashCode());
    assertThat(elevation.inMeters()).isSameAs(elevation);
    assertThat(elevation.getLengthUnit()).isEqualTo(LengthUnit.METER);

    int hashCodeInMeters = elevation.hashCode();

    assertThat(hashCodeInMeters).isNotZero();
    assertThat(hashCodeInMeters).isNotEqualTo(hashCodeInFeet);
    assertThat(hashCodeInMeters).isEqualTo(elevation.hashCode());

    int hashCodeForNewAltitude = Elevation.at(2.0d).inMeters().hashCode();

    assertThat(hashCodeForNewAltitude).isNotZero();
    assertThat(hashCodeForNewAltitude).isNotEqualTo(hashCodeInFeet);
    assertThat(hashCodeForNewAltitude).isNotEqualTo(hashCodeInMeters);
  }

  @Test
  public void toStringIsCorrect() {

    assertThat(Elevation.at(1.0d).in(LengthUnit.YARD).toString()).isEqualTo("1.0 yard");
    assertThat(Elevation.at(2.0d).in(LengthUnit.YARD).toString()).isEqualTo("2.0 yards");
    assertThat(Elevation.at(1.0d).inMeters().toString()).isEqualTo("1.0 meter");
    assertThat(Elevation.at(2.0d).inMeters().toString()).isEqualTo("2.0 meters");
  }

  @Test
  public void toStringWithSpecialPluralFormIsCorrect() {

    assertThat(Elevation.at(1.0d).inFeet().toString()).isEqualTo("1.0 foot");
    assertThat(Elevation.at(2.0d).inFeet().toString()).isEqualTo("2.0 feet");
    assertThat(Elevation.at(1.0d).in(LengthUnit.INCH).toString()).isEqualTo("1.0 inch");
    assertThat(Elevation.at(2.0d).in(LengthUnit.INCH).toString()).isEqualTo("2.0 inches");
  }

  @Test
  public void serializationDeserializationIsCorrect() throws IOException, ClassNotFoundException {

    Elevation elevation = Elevation.at(10.0d).inMeters();

    assertThat(elevation).isNotNull();
    assertThat(elevation.getAltitude()).isEqualTo(10.0d);
    assertThat(elevation.getLengthUnit()).isEqualTo(LengthUnit.METER);

    byte[] elevationBytes = IOUtils.serialize(elevation);

    assertThat(elevationBytes).isNotNull();
    assertThat(elevationBytes.length).isGreaterThan(0);

    Elevation deserializedElevation = IOUtils.deserialize(elevationBytes);

    assertThat(deserializedElevation).isNotNull();
    assertThat(deserializedElevation).isNotSameAs(elevation);
    assertThat(deserializedElevation).isEqualTo(elevation);
  }
}
