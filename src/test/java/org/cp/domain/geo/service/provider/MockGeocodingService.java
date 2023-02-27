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
package org.cp.domain.geo.service.provider;

import static org.mockito.Mockito.mock;

import org.cp.domain.geo.model.Address;
import org.cp.domain.geo.model.Coordinates;
import org.cp.domain.geo.model.Distance;
import org.cp.domain.geo.service.spi.GeocodingService;
import org.cp.elements.lang.annotation.NotNull;
import org.cp.elements.lang.annotation.Nullable;
import org.cp.elements.lang.annotation.Qualifier;

/**
 * Mock implementation of the {@link GeocodingService}.
 *
 * @author John Blum
 * @see org.mockito.Mockito
 * @see org.cp.domain.geo.model.Address
 * @see org.cp.domain.geo.model.Coordinates
 * @see org.cp.domain.geo.model.Distance
 * @see org.cp.domain.geo.service.spi.GeocodingService
 * @see org.cp.elements.lang.annotation.Qualifier
 * @since 0.1.0
 */
@Qualifier(name = "mockGeocodingService")
public class MockGeocodingService implements GeocodingService {

  @Override
  public @NotNull Distance distanceBetween(@Nullable Coordinates origin, @Nullable Coordinates destination) {
    return mock(Distance.class);
  }

  @Override
  public @NotNull Coordinates geocode(@Nullable Address address) {
    return mock(Coordinates.class);
  }

  @Override
  public @NotNull Address reverseGeocode(@Nullable Coordinates coordinates) {
    return mock(Address.class);
  }
}
