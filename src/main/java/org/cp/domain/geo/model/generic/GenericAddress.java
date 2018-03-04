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

package org.cp.domain.geo.model.generic;

import org.cp.domain.geo.enums.Country;
import org.cp.domain.geo.model.AbstractAddress;
import org.cp.domain.geo.model.Address;
import org.cp.domain.geo.model.City;
import org.cp.domain.geo.model.PostalCode;
import org.cp.domain.geo.model.Street;
import org.cp.elements.lang.Assert;

/**
 * The {@link GenericAddress} class is an {@link Address} representing addresses all around the world,
 * regardless of {@link Country}.
 *
 * @author John Blum
 * @see org.cp.domain.geo.enums.Country
 * @see org.cp.domain.geo.model.AbstractAddress
 * @see org.cp.domain.geo.model.Address
 * @see org.cp.domain.geo.model.City
 * @see org.cp.domain.geo.model.Coordinates
 * @see org.cp.domain.geo.model.PostalCode
 * @see org.cp.domain.geo.model.Street
 * @see org.cp.domain.geo.model.Unit
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public class GenericAddress extends AbstractAddress {

  /**
   * Factory method used to construct a new instance of {@link GenericAddress} based in the local {@link Country}
   * as determined by the current {@link java.util.Locale}.
   *
   * @return a new {@link GenericAddress} based in the local {@link Country}.
   * @see org.cp.domain.geo.enums.Country#localCountry()
   * @see #in(Country)
   */
  public static GenericAddress newGenericAddress() {
    return new GenericAddress().in(Country.localCountry());
  }

  /**
   * Constructs a new instance of {@link GenericAddress} initialized with the given {@link Country}.
   *
   * @param country {@link Country} in which this {@link GenericAddress} exists.
   * @return a new {@link GenericAddress} in the given {@link Country}.
   * @throws IllegalArgumentException if {@link Country} is {@literal null}.
   * @see org.cp.domain.geo.enums.Country
   * @see #in(Country)
   */
  public static GenericAddress newGenericAddress(Country country) {
    return new GenericAddress().in(country);
  }

  /**
   * Factory method used to construct a new instance of {@link GenericAddress} initialized with
   * the given {@link Street}, {@link City}, {@link PostalCode} and {@link Country}.
   *
   * @param street {@link Street} of the new {@link GenericAddress}.
   * @param city {@link City} of the new {@link GenericAddress}.
   * @param postalCode {@link PostalCode} of the new {@link GenericAddress}.
   * @param country {@link Country} of the new {@link GenericAddress}.
   * @return a new {@link GenericAddress} initialized with the given {@link Street}, {@link City},
   * {@link PostalCode} and {@link Country}.
   * @throws IllegalArgumentException if {@link Street}, {@link City}, {@link PostalCode}
   * or {@link Country} are {@literal null}.
   * @see org.cp.domain.geo.enums.Country
   * @see org.cp.domain.geo.model.Street
   * @see org.cp.domain.geo.model.City
   * @see org.cp.domain.geo.model.PostalCode
   * @see #newGenericAddress()
   * @see #on(Street)
   * @see #in(City)
   * @see #in(PostalCode)
   * @see #in(Country)
   */
  public static GenericAddress of(Street street, City city, PostalCode postalCode, Country country) {
    return newGenericAddress().on(street).in(city).in(postalCode).in(country);
  }

  /**
   * Factory method used to construct a new instance of {@link GenericAddress} initialized from
   * the given {@link Address}.
   *
   * @param address {@link Address} to copy.
   * @return a new {@link GenericAddress} copied from the given {@link Address}.
   * @throws IllegalArgumentException if {@link Address} is {@literal null}.
   * @see org.cp.domain.geo.model.Address
   * @see #newGenericAddress()
   */
  public static GenericAddress from(Address address) {

    Assert.notNull(address, "Address to copy is required");

    return newGenericAddress()
      .as(address.getType().orElse(null))
      .on(address.getStreet())
      .in(address.getUnit().orElse(null))
      .in(address.getCity())
      .in(address.getPostalCode())
      .in(address.getCountry())
      .with(address.getCoordinates().orElse(null));
  }
}
