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
package org.cp.domain.geo.service.provider;

import org.cp.domain.geo.enums.Country;
import org.cp.domain.geo.model.Address;
import org.cp.domain.geo.model.City;
import org.cp.domain.geo.model.Coordinates;
import org.cp.domain.geo.model.Distance;
import org.cp.domain.geo.model.PostalCode;
import org.cp.domain.geo.model.Street;
import org.cp.domain.geo.service.spi.GeocodingService;
import org.cp.elements.lang.annotation.NotNull;
import org.cp.elements.lang.annotation.Nullable;
import org.cp.elements.lang.annotation.Qualifier;

/**
 * Test implementation of the {@link GeocodingService}.
 *
 * @author John Blum
 * @see org.cp.domain.geo.model.AbstractAddress
 * @see org.cp.domain.geo.model.Address
 * @see org.cp.domain.geo.model.Coordinates
 * @see org.cp.domain.geo.model.Distance
 * @see org.cp.domain.geo.service.spi.GeocodingService
 * @see org.cp.elements.lang.annotation.Qualifier
 * @since 0.1.0
 */
@SuppressWarnings("all")
@Qualifier(name = "test-geocoding-service")
public class TestGeocodingService implements GeocodingService {

  @Override
  public @NotNull Distance distanceBetween(@Nullable Coordinates origin, @Nullable Coordinates destination) {
    return Distance.inMeters(100.0d);
  }

  @Override
  public @NotNull Coordinates geocode(@Nullable Address address) {
    return Coordinates.NULL_ISLAND;
  }

  @Override
  public @NotNull Address reverseGeocode(@Nullable Coordinates coordinates) {
    return Address.of(Street.of(100, "Main").asStreet(),
      City.of("Portland"), PostalCode.of("97205"), Country.UNITED_STATES_OF_AMERICA);
  }
}
