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

import org.cp.elements.enums.Distance;
import org.cp.elements.io.IOUtils;
import org.junit.Test;

/**
 * Unit tests for {@link Elevation}.
 *
 * @author John Blum
 * @see org.junit.Test
 * @see org.cp.domain.geo.model.Elevation
 * @since 1.0.0
 */
public class ElevationTests {

  @Test
  public void constructElevationAboveSeaLevel() {

    Elevation elevation = new Elevation(1.0d);

    assertThat(elevation).isNotNull();
    assertThat(elevation.getAltitude()).isEqualTo(1.0d);
    assertThat(elevation.getDistance()).isEqualTo(Distance.getDefault());
  }

  @Test
  public void constructElevationAtSeaLevel() {

    Elevation elevation = new Elevation(0.0d);

    assertThat(elevation).isNotNull();
    assertThat(elevation.getAltitude()).isEqualTo(0.0d);
    assertThat(elevation.getDistance()).isEqualTo(Distance.getDefault());
  }

  @Test
  public void constructElevationBelowSeaLevel() {

    Elevation elevation = new Elevation(-1.0d);

    assertThat(elevation).isNotNull();
    assertThat(elevation.getAltitude()).isEqualTo(-1.0d);
    assertThat(elevation.getDistance()).isEqualTo(Distance.getDefault());
  }

  @Test
  public void elevationAtSeaLevelIsCorrect() {

    Elevation elevation = Elevation.atSeaLevel();

    assertThat(elevation).isNotNull();
    assertThat(elevation.getAltitude()).isEqualTo(0.0d);
    assertThat(elevation.getDistance()).isEqualTo(Distance.getDefault());
  }

  @Test
  public void elevationOfAltitudeIsCorrect() {

    Elevation elevation = Elevation.of(1000.0d);

    assertThat(elevation).isNotNull();
    assertThat(elevation.getAltitude()).isEqualTo(1000.0d);
    assertThat(elevation.getDistance()).isEqualTo(Distance.getDefault());
  }

  @Test
  public void setAndGetDistance() {

    Elevation elevation = Elevation.atSeaLevel();

    assertThat(elevation).isNotNull();
    assertThat(elevation.getDistance()).isEqualTo(Distance.getDefault());

    elevation.setDistance(Distance.NANOMETER);

    assertThat(elevation.getDistance()).isEqualTo(Distance.NANOMETER);
  }

  @Test
  public void setDistanceToNullReturnsDefaultDistance() {

    Elevation elevation = Elevation.atSeaLevel();

    assertThat(elevation).isNotNull();
    assertThat(elevation.getDistance()).isEqualTo(Distance.getDefault());

    elevation.setDistance(null);

    assertThat(elevation.getDistance()).isEqualTo(Distance.getDefault());
  }

  @Test
  public void inDistanceSetsDistanceReturnsThis() {

    Elevation elevation = Elevation.of(1.0d);

    assertThat(elevation).isNotNull();
    assertThat(elevation.getAltitude()).isEqualTo(1.0d);
    assertThat(elevation.getDistance()).isEqualTo(Distance.getDefault());
    assertThat(elevation.in(Distance.INCH)).isSameAs(elevation);
    assertThat(elevation.getAltitude()).isEqualTo(1.0d);
    assertThat(elevation.getDistance()).isEqualTo(Distance.INCH);
    assertThat(elevation.in(Distance.KILOMETER)).isSameAs(elevation);
    assertThat(elevation.getAltitude()).isEqualTo(1.0d);
    assertThat(elevation.getDistance()).isEqualTo(Distance.KILOMETER);
    assertThat(elevation.in(null)).isSameAs(elevation);
    assertThat(elevation.getAltitude()).isEqualTo(1.0d);
    assertThat(elevation.getDistance()).isEqualTo(Distance.getDefault());
  }

  @Test
  public void inFeetSetsDistanceToFeetReturnsThis() {

    Elevation elevation = Elevation.of(1.0d).inFeet();

    assertThat(elevation).isNotNull();
    assertThat(elevation.getAltitude()).isEqualTo(1.0d);
    assertThat(elevation.getDistance()).isEqualTo(Distance.FOOT);
  }

  @Test
  public void inMetersSetsDistanceToMetersReturnsThis() {

    Elevation elevation = Elevation.of(1.0d).inMeters();

    assertThat(elevation).isNotNull();
    assertThat(elevation.getAltitude()).isEqualTo(1.0d);
    assertThat(elevation.getDistance()).isEqualTo(Distance.METER);
  }

  @Test
  public void isAboveSeaLevelReturnsTrue() {
    assertThat(Elevation.of(1.0d).isAboveSeaLevel()).isTrue();
  }

  @Test
  public void isAboveSeaLevelReturnsFalse() {
    assertThat(Elevation.atSeaLevel().isAboveSeaLevel()).isFalse();
    assertThat(Elevation.of(-1.0d).isAboveSeaLevel()).isFalse();
  }

  @Test
  public void isAtSeaLevelReturnsTrue() {
    assertThat(Elevation.atSeaLevel().isAtSeaLevel()).isTrue();
  }

  @Test
  public void isAtSeaLevelReturnsFalse() {
    assertThat(Elevation.of(1.0d).isAtSeaLevel()).isFalse();
    assertThat(Elevation.of(-1.0d).isAtSeaLevel()).isFalse();
  }

  @Test
  public void isBelowSeaLevelReturnsTrue() {
    assertThat(Elevation.of(-1.0d).isBelowSeaLevel()).isTrue();
  }

  @Test
  public void isBelowSeaLevelReturnsFalse() {
    assertThat(Elevation.atSeaLevel().isBelowSeaLevel()).isFalse();
    assertThat(Elevation.of(1.0d).isBelowSeaLevel()).isFalse();
  }

  @Test
  public void equalsNullReturnsFalse() {
    assertThat(Elevation.atSeaLevel()).isNotEqualTo(null);
  }

  @Test
  public void equalElevationsAreEqual() {

    Elevation elevationOne = Elevation.of(1.0d).inFeet();
    Elevation elevationTwo = Elevation.of(1.0d).inFeet();

    assertThat(elevationOne).isNotNull();
    assertThat(elevationOne).isNotSameAs(elevationTwo);
    assertThat(elevationOne).isEqualTo(elevationTwo);
  }

  @Test
  public void identicalElevationsAreEqual() {

    Elevation elevation = Elevation.of(1.0d).inFeet();

    assertThat(elevation).isNotNull();
    assertThat(elevation).isSameAs(elevation);
    assertThat(elevation).isEqualTo(elevation);
  }

  @Test
  public void unequalElevationsAreNotEqual() {

    assertThat(Elevation.atSeaLevel().inMeters()).isNotEqualTo(Elevation.atSeaLevel().inFeet());
    assertThat(Elevation.of(3.0d).inFeet()).isNotEqualTo(Elevation.of(1.0d).in(Distance.YARD));
  }

  @Test
  public void hashCodeIsCorrect() {

    Elevation elevation = Elevation.of(1.0d).inFeet();

    assertThat(elevation).isNotNull();
    assertThat(elevation.getAltitude()).isEqualTo(1.0d);
    assertThat(elevation.getDistance()).isEqualTo(Distance.FOOT);

    int hashCode = elevation.hashCode();

    assertThat(hashCode).isNotZero();
    assertThat(hashCode).isEqualTo(elevation.hashCode());
    assertThat(elevation.inMeters()).isSameAs(elevation);
    assertThat(elevation.getDistance()).isEqualTo(Distance.METER);

    int hashCodeInMeters = elevation.hashCode();

    assertThat(hashCodeInMeters).isNotZero();
    assertThat(hashCodeInMeters).isNotEqualTo(hashCode);
    assertThat(hashCodeInMeters).isEqualTo(elevation.hashCode());

    int hashCodeWithNewAltitude = Elevation.of(2.0d).inMeters().hashCode();

    assertThat(hashCodeWithNewAltitude).isNotZero();
    assertThat(hashCodeWithNewAltitude).isNotEqualTo(hashCode);
    assertThat(hashCodeWithNewAltitude).isNotEqualTo(hashCodeInMeters);
  }

  @Test
  public void toStringIsCorrect() {

    assertThat(Elevation.of(1.0d).inFeet().toString()).isEqualTo("1.0 foot");
    assertThat(Elevation.of(2.0d).inFeet().toString()).isEqualTo("2.0 feet");
    assertThat(Elevation.of(1.0d).in(Distance.INCH).toString()).isEqualTo("1.0 inch");
    assertThat(Elevation.of(2.0d).in(Distance.INCH).toString()).isEqualTo("2.0 inches");
    assertThat(Elevation.of(1.0d).inMeters().toString()).isEqualTo("1.0 meter");
    assertThat(Elevation.of(2.0d).inMeters().toString()).isEqualTo("2.0 meters");
  }

  @Test
  public void serializationDeserializationIsCorrect() throws IOException, ClassNotFoundException {

    Elevation elevation = Elevation.of(10.0d).inMeters();

    assertThat(elevation).isNotNull();
    assertThat(elevation.getAltitude()).isEqualTo(10.0d);
    assertThat(elevation.getDistance()).isEqualTo(Distance.METER);

    byte[] elevationBytes = IOUtils.serialize(elevation);

    assertThat(elevationBytes).isNotNull();
    assertThat(elevationBytes.length).isGreaterThan(0);

    Elevation deserializedElevation = IOUtils.deserialize(elevationBytes);

    assertThat(deserializedElevation).isNotNull();
    assertThat(deserializedElevation).isNotSameAs(elevation);
    assertThat(deserializedElevation).isEqualTo(elevation);
  }
}
