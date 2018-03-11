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

import java.awt.Point;
import java.io.IOException;

import org.cp.elements.enums.Distance;
import org.cp.elements.io.IOUtils;
import org.junit.Test;

/**
 * Unit tests for {@link Coordinates}.
 *
 * @author John Blum
 * @see java.awt.Point
 * @see org.junit.Test
 * @see org.cp.domain.geo.model.Coordinates
 * @since 1.0.0
 */
public class CoordinatesTests {

  @Test
  public void constructCoordinatesWithLatitudeAndLongitude() {

    Coordinates coordinates = new Coordinates(1.0d, 2.0d);

    assertThat(coordinates.getElevation().getAltitude()).isEqualTo(0.0d);
    assertThat(coordinates.getElevation().getDistance()).isEqualTo(Distance.getDefault());
    assertThat(coordinates.getLatitude()).isEqualTo(1.0d);
    assertThat(coordinates.getLongitude()).isEqualTo(2.0d);
  }

  @Test
  public void ofLatitudeAndLongitude() {

    Coordinates coordinates = Coordinates.of(1.0d, 2.0d);

    assertThat(coordinates).isNotNull();
    assertThat(coordinates.getElevation().getAltitude()).isEqualTo(0.0d);
    assertThat(coordinates.getElevation().getDistance()).isEqualTo(Distance.getDefault());
    assertThat(coordinates.getLatitude()).isEqualTo(1.0d);
    assertThat(coordinates.getLongitude()).isEqualTo(2.0d);
  }

  @Test
  public void ofLatitudeAndLongitudeAtElevation() {

    Coordinates coordinates = Coordinates.of(1.0d, 2.0d).at(4.0d);

    assertThat(coordinates).isNotNull();
    assertThat(coordinates.getElevation().getAltitude()).isEqualTo(4.0d);
    assertThat(coordinates.getElevation().getDistance()).isEqualTo(Distance.getDefault());
    assertThat(coordinates.getLatitude()).isEqualTo(1.0d);
    assertThat(coordinates.getLongitude()).isEqualTo(2.0d);
  }

  @Test
  public void fromPoint() {

    Point point = new Point(1, 2);

    Coordinates coordinates = Coordinates.from(point);

    assertThat(coordinates).isNotNull();
    assertThat(coordinates.getElevation().getAltitude()).isEqualTo(0.0d);
    assertThat(coordinates.getElevation().getDistance()).isEqualTo(Distance.getDefault());
    assertThat(coordinates.getLatitude()).isEqualTo(point.getX());
    assertThat(coordinates.getLongitude()).isEqualTo(point.getY());
  }

  @Test(expected = IllegalArgumentException.class)
  public void fromNullPointThrowsIllegalArgumentException() {

    try {
      Coordinates.from(null);
    }
    catch (IllegalArgumentException expected) {

      assertThat(expected).hasMessage("Point is required");
      assertThat(expected).hasNoCause();

      throw expected;
    }
  }

  @Test
  public void coordinatesAsPoint() {

    Point point = Coordinates.of(1.0d, 2.0d).asPoint();

    assertThat(point).isNotNull();
    assertThat(point.getX()).isEqualTo(1.0d);
    assertThat(point.getY()).isEqualTo(2.0d);
  }

  @Test
  public void atElevationSetsElevationAndReturnsThis() {

    Coordinates coordinates = Coordinates.of(1.0d, 2.0d);

    assertThat(coordinates).isNotNull();
    assertThat(coordinates.getElevation().getAltitude()).isEqualTo(0.0d);
    assertThat(coordinates.getLatitude()).isEqualTo(1.0d);
    assertThat(coordinates.getLongitude()).isEqualTo(2.0d);
    assertThat(coordinates.at(3.0d)).isSameAs(coordinates);
    assertThat(coordinates.getElevation().getAltitude()).isEqualTo(3.0d);
    assertThat(coordinates.getLatitude()).isEqualTo(1.0d);
    assertThat(coordinates.getLongitude()).isEqualTo(2.0d);
  }

  @Test
  public void atElevationWithDistanceSetsElevationReturnsThis() {

    Coordinates coordinates = Coordinates.of(1.0d, 2.0d);

    assertThat(coordinates).isNotNull();
    assertThat(coordinates.getElevation().getAltitude()).isEqualTo(0.0d);
    assertThat(coordinates.getElevation().getDistance()).isEqualTo(Distance.getDefault());
    assertThat(coordinates.getLatitude()).isEqualTo(1.0d);
    assertThat(coordinates.getLongitude()).isEqualTo(2.0d);
    assertThat(coordinates.at(4.0d, Distance.NANOMETER)).isSameAs(coordinates);
    assertThat(coordinates.getElevation().getAltitude()).isEqualTo(4.0d);
    assertThat(coordinates.getElevation().getDistance()).isEqualTo(Distance.NANOMETER);
    assertThat(coordinates.getLatitude()).isEqualTo(1.0d);
    assertThat(coordinates.getLongitude()).isEqualTo(2.0d);
  }

  @Test
  public void atElevationWithElevationSetsElevationReturnsThis() {

    Elevation elevation = Elevation.of(8.0d).in(Distance.MILE);

    Coordinates coordinates = Coordinates.of(1.0d, 2.0d);

    assertThat(coordinates).isNotNull();
    assertThat(coordinates.getElevation().getAltitude()).isEqualTo(0.0d);
    assertThat(coordinates.getElevation().getDistance()).isEqualTo(Distance.getDefault());
    assertThat(coordinates.getLatitude()).isEqualTo(1.0d);
    assertThat(coordinates.getLongitude()).isEqualTo(2.0d);
    assertThat(coordinates.at(elevation)).isSameAs(coordinates);
    assertThat(coordinates.getElevation()).isEqualTo(elevation);
    assertThat(coordinates.getLatitude()).isEqualTo(1.0d);
    assertThat(coordinates.getLongitude()).isEqualTo(2.0d);
  }

  @Test
  public void equalCoordinatesAreEqual() {

    Coordinates coordinatesOne = Coordinates.of(1.0d, 2.0d).at(3.0d);
    Coordinates coordinatesTwo = Coordinates.of(1.0d, 2.0d).at(4.0d);

    assertThat(coordinatesOne).isNotSameAs(coordinatesTwo);
    assertThat(coordinatesOne).isEqualTo(coordinatesTwo);
  }

  @Test
  public void equalsNullReturnsFalse() {
    assertThat(Coordinates.of(1.0d, 2.0d).at(3.0d)).isNotEqualTo(null);
  }

  @Test
  public void identicalCoordinatesAreEqual() {

    Coordinates coordinates = Coordinates.of(1.0d, 2.0d).at(3.0d);

    assertThat(coordinates).isEqualTo(coordinates);
  }

  @Test
  public void unequalCoordinatesAreNotEqual() {

    Coordinates coordinatesOne = Coordinates.of(1.0d, 2.0d).at(4.0d);
    Coordinates coordinatesTwo = Coordinates.of(2.0d, 1.0d).at(4.0d);

    assertThat(coordinatesOne).isNotSameAs(coordinatesTwo);
    assertThat(coordinatesOne).isNotEqualTo(coordinatesTwo);
  }

  @Test
  public void hashCodeIsCorrect() {

    Coordinates coordinates = Coordinates.of(1.0d, 2.0d);

    int hashCodeOne = coordinates.hashCode();

    assertThat(hashCodeOne).isNotZero();

    int hashCodeTwo = coordinates.at(3.0d).hashCode();

    assertThat(hashCodeTwo).isNotZero();
    assertThat(hashCodeTwo).isEqualTo(hashCodeOne);
    assertThat(coordinates.hashCode()).isEqualTo(hashCodeTwo);
    assertThat(Coordinates.of(2.0d, 1.0d).at(3.0d).hashCode()).isNotEqualTo(hashCodeTwo);
  }

  @Test
  public void toStringIsCorrect() {

    assertThat(Coordinates.of(1.0d, 2.0d).at(3.0d).toString())
      .isEqualTo("[latitude: 1.0, longitude: 2.0; @ elevation: 3.0 feet]");
  }

  @Test
  public void serializationIsCorrect() throws IOException, ClassNotFoundException {

    Elevation elevation = Elevation.of(4.0d).in(Distance.KILOMETER);

    Coordinates coordinates = Coordinates.of(1.0d, 2.0d).at(elevation);

    assertThat(coordinates).isNotNull();
    assertThat(coordinates.getElevation()).isEqualTo(elevation);
    assertThat(coordinates.getLatitude()).isEqualTo(1.0d);
    assertThat(coordinates.getLongitude()).isEqualTo(2.0d);

    byte[] coordinatesBytes = IOUtils.serialize(coordinates);

    assertThat(coordinatesBytes).isNotNull();
    assertThat(coordinatesBytes.length).isGreaterThan(0);

    Coordinates coordinatesDeserialized = IOUtils.deserialize(coordinatesBytes);

    assertThat(coordinatesDeserialized).isNotNull();
    assertThat(coordinatesDeserialized).isNotSameAs(coordinates);
    assertThat(coordinatesDeserialized).isEqualTo(coordinates);
    assertThat(coordinatesDeserialized.getElevation()).isEqualTo(coordinates.getElevation());
  }
}
