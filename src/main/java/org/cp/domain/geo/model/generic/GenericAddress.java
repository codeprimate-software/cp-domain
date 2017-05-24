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
 * The GenericAddress class...
 *
 * @author John Blum
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public class GenericAddress extends AbstractAddress {

  public static GenericAddress newGenericAddress() {
    return new GenericAddress();
  }

  public static GenericAddress of(Street street, City city, PostalCode postalCode, Country country) {
    return newGenericAddress().on(street).in(city).in(postalCode).in(country);
  }

  public static GenericAddress from(Address address) {

    Assert.notNull(address, "Address to copy must not be null");

    return newGenericAddress().as(address.getType().orElse(Type.UNKNOWN))
      .on(address.getStreet()).in(address.getUnit().orElse(null))
      .in(address.getCity()).in(address.getPostalCode()).in(address.getCountry())
      .with(address.getCoordinates().orElse(null));
  }
}
