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
package org.cp.domain.geo.service.spi;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;

import org.cp.domain.geo.enums.State;
import org.cp.domain.geo.model.Address;
import org.cp.domain.geo.model.City;
import org.cp.domain.geo.model.Coordinates;
import org.cp.domain.geo.model.Distance;
import org.cp.domain.geo.model.PostalCode;
import org.cp.domain.geo.model.Street;
import org.cp.domain.geo.model.usa.UnitedStatesAddress;
import org.cp.domain.geo.service.provider.MockGeocodingService;
import org.cp.domain.geo.service.provider.TestGeocodingService;

/**
 * Unit Tests for {@link GeocodingService}.
 *
 * @author John Blum
 * @see org.junit.jupiter.api.Test
 * @see org.mockito.Mockito
 * @see org.cp.domain.geo.model.Address
 * @see org.cp.domain.geo.model.Coordinates
 * @see org.cp.domain.geo.model.Distance
 * @see org.cp.domain.geo.service.spi.GeocodingService
 * @see org.cp.domain.geo.service.provider.MockGeocodingService
 * @see org.cp.domain.geo.service.provider.TestGeocodingService
 * @since 0.1.0
 */
public class GeocodingServiceUnitTests {

  @Test
  public void loadFirstAvailableGeocodingService() {

    GeocodingService geocodingService = GeocodingService.getLoader().getServiceInstance();

    assertThat(geocodingService).isInstanceOf(TestGeocodingService.class);

    assertThat(geocodingService
      .distanceBetween(Coordinates.at(210.0d, 012.0d),
        Coordinates.at(404.5d, 202.0d))) .isEqualTo(Distance.inMeters(100.0d));

    assertThat(geocodingService.geocode(Address.of(Street.of(100, "One").asWay(),
      City.of("Portland"), PostalCode.of("97205")))).isEqualTo(Coordinates.NULL_ISLAND);

    Address actual = geocodingService.reverseGeocode(Coordinates.at(123.0d, 321.0d));

    Address expected = UnitedStatesAddress.newUnitedStatesAddressBuilder()
      .in(State.OREGON)
      .in(City.of("Portland"))
      .in(PostalCode.of("97205"))
      .on(Street.of(100, "Main").asStreet())
      .build();

    assertThat(actual)
      .describedAs("Expected [%s]; but was [%s]", expected, actual)
      .isEqualTo(expected);
  }

  @Test
  public void loadTargetedGeocodingService() {

    GeocodingService geocodingService = GeocodingService.getLoader()
      .getServiceInstance("mockGeocodingService");

    assertThat(geocodingService).isInstanceOf(MockGeocodingService.class);
    assertThat(geocodingService.distanceBetween(mock(Coordinates.class), mock(Coordinates.class)))
      .isInstanceOf(Distance.class);
    assertThat(geocodingService.geocode(mock(Address.class))).isInstanceOf(Coordinates.class);
    assertThat(geocodingService.reverseGeocode(mock(Coordinates.class))).isInstanceOf(Address.class);
  }
}
