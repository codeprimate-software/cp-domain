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

import java.util.concurrent.atomic.AtomicReference;

import org.cp.domain.geo.model.Address;
import org.cp.domain.geo.model.Coordinates;
import org.cp.domain.geo.model.Distance;
import org.cp.elements.lang.annotation.NotNull;
import org.cp.elements.service.annotation.Service;
import org.cp.elements.service.loader.ServiceLoaderSupport;

/**
 * {@link Service} interface defining a contract for Service Provider Implementations (SPI)
 * to geocode an {@link Address} as a set of {@link Coordinates} as well as to reverse geocode
 * a set of {@link Coordinates} as an {@link Address}.
 *
 * @author John Blum
 * @see org.cp.domain.geo.model.Address
 * @see org.cp.domain.geo.model.Distance
 * @see org.cp.domain.geo.model.Coordinates
 * @see org.cp.elements.service.annotation.Service
 * @see org.cp.elements.service.loader.ServiceLoaderSupport
 * @since 0.1.0
 */
@Service
@SuppressWarnings("unused")
public interface GeocodingService {

  AtomicReference<GeocodingService.Loader> GEOCODING_SERVICE_LOADER_REFERENCE = new AtomicReference<>();

  /**
   * Gets the {@link java.util.ServiceLoader} implementation used to load and initialize
   * the preferred {@link GeocodingService} {@literal Service Provider Implementation (SPI)}
   * used to perform geocoding operations.
   *
   * @return the {@link GeocodingService.Loader} {@link java.util.ServiceLoader} implementation
   * used to load and initialize a {@link GeocodingService} {@literal Service Provider Implementation (SPI)}.
   * @see org.cp.domain.geo.service.spi.GeocodingService.Loader
   */
  static @NotNull GeocodingService.Loader getLoader() {
    return GEOCODING_SERVICE_LOADER_REFERENCE.updateAndGet(it -> it != null ? it : new GeocodingService.Loader() { });
  }

  /**
   * Computes the {@link Distance} between two geographic {@link Coordinates}.
   * <p>
   * This operation may result in a service provider call to compute the {@link Distance} between the two geographic
   * {@link Coordinates}, or implementations may choose to partly implement this call using triangulation.
   *
   * @param origin {@link Coordinates} representing the {@literal origin} in the distance calculation.
   * @param destination {@link Coordinates} representing the {@literal destination} in the distance calculation.
   * @return the computed {@link Distance} between two geographic {@link Coordinates}.
   * @see org.cp.domain.geo.model.Coordinates
   * @see org.cp.domain.geo.model.Distance
   */
  Distance distanceBetween(Coordinates origin, Coordinates destination);

  /**
   * Computes the geographic {@link Coordinates} of the given {@link Address}.
   *
   * @param address {@link Address} from which the geographic {@link Coordinates} are computed.
   * @return the geographic {@link Coordinates} computed from the given {@link Address}.
   * @see org.cp.domain.geo.model.Coordinates
   * @see org.cp.domain.geo.model.Address
   */
  Coordinates geocode(Address address);

  /**
   * Computes an {@link Address} from the given geographic {@link Coordinates}.
   *
   * @param coordinates {@link Coordinates} from which to compute an {@link Address}.
   * @return the geographic {@link Coordinates} computed from the given {@link Address}.
   * @see org.cp.domain.geo.model.Coordinates
   * @see org.cp.domain.geo.model.Address
   */
  Address reverseGeocode(Coordinates coordinates);

  interface Loader extends ServiceLoaderSupport<GeocodingService> {

    @Override
    default Class<GeocodingService> getType() {
      return GeocodingService.class;
    }
  }
}
