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
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.awt.Point;
import java.io.IOException;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import org.cp.elements.enums.LengthUnit;
import org.cp.elements.io.IOUtils;

/**
 * Unit Tests for {@link Coordinates}.
 *
 * @author John Blum
 * @see java.awt.Point
 * @see org.junit.jupiter.api.Test
 * @see org.mockito.Mockito
 * @see org.cp.domain.geo.model.Coordinates
 * @since 0.1.0
 */
public class CoordinatesUnitTests {

  @Test
  public void constructCoordinatesWithLatitudeAndLongitude() {

    Coordinates coordinates = new Coordinates(1.0d, 2.0d);

    assertThat(coordinates).isNotNull();
    assertThat(coordinates.getElevation()).isNotPresent();
    assertThat(coordinates.getLatitude()).isEqualTo(1.0d);
    assertThat(coordinates.getLongitude()).isEqualTo(2.0d);
  }

  @Test
  public void atLatitudeAndLongitude() {

    Coordinates coordinates = Coordinates.at(1.0d, 2.0d);

    assertThat(coordinates).isNotNull();
    assertThat(coordinates.getElevation()).isNotPresent();
    assertThat(coordinates.getLatitude()).isEqualTo(1.0d);
    assertThat(coordinates.getLongitude()).isEqualTo(2.0d);
  }

  @Test
  public void atLatitudeLongitudeAndElevation() {

    Coordinates coordinates = Coordinates.at(1.0d, 2.0d).at(4.0d);

    assertThat(coordinates).isNotNull();
    assertThat(coordinates.getElevation()).isNotNull();
    assertThat(coordinates.getElevation().map(Elevation::getAltitude).orElse(null)).isEqualTo(4.0d);
    assertThat(coordinates.getElevation().map(Elevation::getLengthUnit).orElse(null))
      .isEqualTo(LengthUnit.getDefault());
    assertThat(coordinates.getLatitude()).isEqualTo(1.0d);
    assertThat(coordinates.getLongitude()).isEqualTo(2.0d);
  }

  @Test
  public void fromPoint() {

    Point point = new Point(1, 2);

    Coordinates coordinates = Coordinates.from(point);

    assertThat(coordinates).isNotNull();
    assertThat(coordinates.getElevation()).isNotPresent();
    assertThat(coordinates.getLatitude()).isEqualTo(point.getY());
    assertThat(coordinates.getLongitude()).isEqualTo(point.getX());
  }

  @Test
  public void fromNullPoint() {

    assertThatIllegalArgumentException()
      .isThrownBy(() -> Coordinates.from(null))
      .withMessage("Point is required")
      .withNoCause();
  }

  @Test
  public void altitudeIsAnAliasForElevation() {

    Elevation mockElevation = mock(Elevation.class);

    Coordinates coordinates = spy(Coordinates.at(1.0d, 2.0d));

    doReturn(Optional.of(mockElevation)).when(coordinates).getElevation();

    Optional<Elevation> altitude = coordinates.getAltitude();

    assertThat(altitude).isNotNull();
    assertThat(altitude.orElse(null)).isEqualTo(mockElevation);

    verify(coordinates, times(1)).getAltitude();
    verify(coordinates, times(1)).getElevation();
    verifyNoMoreInteractions(coordinates);
  }

  @Test
  public void coordinatesAsPoint() {

    Point point = Coordinates.at(1.0d, 2.0d).asPoint();

    assertThat(point).isNotNull();
    assertThat(point.getX()).isEqualTo(2.0d);
    assertThat(point.getY()).isEqualTo(1.0d);
  }

  @Test
  public void atElevationWithAltitudeSetsElevationAndReturnsThis() {

    Coordinates coordinates = Coordinates.at(1.0d, 2.0d);

    assertThat(coordinates).isNotNull();
    assertThat(coordinates.getElevation()).isNotPresent();
    assertThat(coordinates.getLatitude()).isEqualTo(1.0d);
    assertThat(coordinates.getLongitude()).isEqualTo(2.0d);
    assertThat(coordinates.at(3.0d)).isSameAs(coordinates);
    assertThat(coordinates.getElevation()).isPresent();
    assertThat(coordinates.getElevation().map(Elevation::getAltitude).orElse(null)).isEqualTo(3.0d);
    assertThat(coordinates.getLatitude()).isEqualTo(1.0d);
    assertThat(coordinates.getLongitude()).isEqualTo(2.0d);
  }

  @Test
  public void atElevationWithAltitudeAndLengthUnitSetsElevationReturnsThis() {

    Coordinates coordinates =  Coordinates.at(1.0d, 2.0d);

    assertThat(coordinates).isNotNull();
    assertThat(coordinates.getElevation()).isNotPresent();
    assertThat(coordinates.getLatitude()).isEqualTo(1.0d);
    assertThat(coordinates.getLongitude()).isEqualTo(2.0d);
    assertThat(coordinates.at(4.0d, LengthUnit.KILOMETER)).isSameAs(coordinates);
    assertThat(coordinates.getElevation()).isPresent();
    assertThat(coordinates.getElevation().map(Elevation::getAltitude).orElse(null)).isEqualTo(4.0d);
    assertThat(coordinates.getElevation().map(Elevation::getLengthUnit).orElse(null))
      .isEqualTo(LengthUnit.KILOMETER);
    assertThat(coordinates.getLatitude()).isEqualTo(1.0d);
    assertThat(coordinates.getLongitude()).isEqualTo(2.0d);
  }

  @Test
  public void atElevationWithElevationSetsElevationReturnsThis() {

    Elevation elevation = Elevation.at(8.0d).in(LengthUnit.MILE);

    Coordinates coordinates = Coordinates.at(1.0d, 2.0d);

    assertThat(coordinates).isNotNull();
    assertThat(coordinates.getElevation()).isNotPresent();
    assertThat(coordinates.getLatitude()).isEqualTo(1.0d);
    assertThat(coordinates.getLongitude()).isEqualTo(2.0d);
    assertThat(coordinates.at(elevation)).isSameAs(coordinates);
    assertThat(coordinates.getElevation()).isPresent();
    assertThat(coordinates.getElevation().orElse(null)).isEqualTo(elevation);
    assertThat(coordinates.getLatitude()).isEqualTo(1.0d);
    assertThat(coordinates.getLongitude()).isEqualTo(2.0d);
  }

  @Test
  public void equalCoordinatesAreEqual() {

    Coordinates coordinatesOne = Coordinates.at(1.0d, 2.0d).at(Elevation.at(3.0d).inMeters());
    Coordinates coordinatesTwo = Coordinates.at(1.0d, 2.0d).at(Elevation.at(3.0d).inFeet());

    assertThat(coordinatesOne).isNotSameAs(coordinatesTwo);
    assertThat(coordinatesOne).isEqualTo(coordinatesTwo);
  }

  @Test
  public void identicalCoordinatesAreEqual() {

    Coordinates coordinates = Coordinates.at(1.0d, 2.0d).at(Elevation.at(3.0d).inMeters());

    assertThat(coordinates).isEqualTo(coordinates);
  }

  @Test
  public void unequalCoordinatesAreNotEqual() {

    Coordinates coordinatesOne = Coordinates.at(1.0d, 2.0d).at(Elevation.at(4.0d).inMeters());
    Coordinates coordinatesTwo = Coordinates.at(2.0d, 1.0d).at(Elevation.at(-4.0d).inMeters());

    assertThat(coordinatesOne).isNotSameAs(coordinatesTwo);
    assertThat(coordinatesOne).isNotEqualTo(coordinatesTwo);
  }

  @Test
  public void equalsNullIsNullSafeReturnsFalse() {
    assertThat(Coordinates.at(1.0d, 2.0d).at(3.0d)).isNotEqualTo(null);
  }

  @Test
  public void equalsObjectReturnsFalse() {
    assertThat(Coordinates.at(1.0d, 2.0d).at(Elevation.at(3.0d).inMeters())).isNotEqualTo("test");
  }

  @Test
  public void hashCodeIsCorrect() {

    Coordinates coordinates = Coordinates.at(1.0d, 2.0d);

    int coordinatesHashCodeOne = coordinates.hashCode();

    assertThat(coordinatesHashCodeOne).isNotZero();

    int coordinatesHashCodeTwo = coordinates.at(Elevation.at(3.0d).inMeters()).hashCode();

    assertThat(coordinatesHashCodeTwo).isNotZero();
    assertThat(coordinatesHashCodeTwo).isEqualTo(coordinatesHashCodeOne);
    assertThat(coordinates.hashCode()).isEqualTo(coordinatesHashCodeTwo).isEqualTo(coordinatesHashCodeOne);
  }

  @Test
  public void hashCodesForUnequalCoordinatesAreNotEqual() {

    assertThat(Coordinates.at(1.0d, 2.0d).at(Elevation.at(3.0d).inMeters()))
      .doesNotHaveSameHashCodeAs(Coordinates.at(2.0d, 1.0d).at(Elevation.at(3.0d).inMeters()));
  }

  @Test
  public void toStringIsCorrect() {

    Elevation threeMeters = Elevation.at(3.0d).inMeters();

    assertThat(Coordinates.at(1.0d, 2.0d).at(threeMeters).toString())
      .isEqualTo("[latitude: 1.0, longitude: 2.0, altitude: 3.0 meters]");
  }

  @Test
  public void serializationIsCorrect() throws IOException, ClassNotFoundException {

    Elevation elevation = Elevation.at(4.0d).in(LengthUnit.KILOMETER);

    Coordinates coordinates = Coordinates.at(1.0d, 2.0d).at(elevation);

    assertThat(coordinates).isNotNull();
    assertThat(coordinates.getElevation()).isPresent();
    assertThat(coordinates.getElevation().orElse(null)).isEqualTo(elevation);
    assertThat(coordinates.getLatitude()).isEqualTo(1.0d);
    assertThat(coordinates.getLongitude()).isEqualTo(2.0d);

    byte[] coordinatesBytes = IOUtils.serialize(coordinates);

    assertThat(coordinatesBytes).isNotNull();
    assertThat(coordinatesBytes.length).isGreaterThan(0);

    Coordinates coordinatesDeserialized = IOUtils.deserialize(coordinatesBytes);

    assertThat(coordinatesDeserialized).isNotNull();
    assertThat(coordinatesDeserialized).isNotSameAs(coordinates);
    assertThat(coordinatesDeserialized).isEqualTo(coordinates);
    assertThat(coordinatesDeserialized.getElevation().orElse(null))
      .isEqualTo(coordinates.getElevation().orElse(Elevation.atSeaLevel()));
  }
}
