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

package org.cp.domain.geo.model.usa;

import static org.cp.elements.lang.RuntimeExceptionsFactory.newIllegalArgumentException;

import java.util.Optional;

import org.cp.domain.geo.enums.Country;
import org.cp.domain.geo.model.AbstractAddress;
import org.cp.domain.geo.model.Address;
import org.cp.domain.geo.model.City;
import org.cp.domain.geo.model.Street;
import org.cp.elements.enums.State;
import org.cp.elements.lang.Assert;
import org.cp.elements.lang.ObjectUtils;

/**
 * The {@link UnitedStatesAddress} class is an implementation of {@link Address} that models and represents an
 * {@link Address} of the {@link Country#UNITED_STATES_OF_AMERICA Unite States of America}.
 *
 * @author John Blum
 * @see org.cp.domain.geo.enums.Continent
 * @see org.cp.domain.geo.enums.Country
 * @see org.cp.domain.geo.model.AbstractAddress
 * @see org.cp.domain.geo.model.Address
 * @see org.cp.domain.geo.model.City
 * @see org.cp.domain.geo.model.Coordinates
 * @see org.cp.domain.geo.model.PostalCode
 * @see org.cp.domain.geo.model.Street
 * @see org.cp.domain.geo.model.Unit
 * @see org.cp.domain.geo.model.usa.ZIP
 * @see org.cp.elements.enums.State
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public class UnitedStatesAddress extends AbstractAddress {

  private State state;

  private ZIP zip;

  public static UnitedStatesAddress newUnitedStatesAddress() {
    return new UnitedStatesAddress().in(Country.UNITED_STATES_OF_AMERICA);
  }

  public static UnitedStatesAddress of(Street street, City city, State state, ZIP zip) {
    return newUnitedStatesAddress().on(street).<UnitedStatesAddress>in(city).in(state).in(zip);
  }

  public static UnitedStatesAddress from(Address address) {

    Assert.notNull(address, "Address to copy must not be null");

    UnitedStatesAddress unitedStatesAddress = newUnitedStatesAddress().as(address.getType().orElse(Type.UNKNOWN))
      .on(address.getStreet()).in(address.getUnit().orElse(null))
      .in(address.getCity()).in(address.getPostalCode());

    Optional.of(address)
      .filter(localAddress -> localAddress instanceof UnitedStatesAddress)
      .map(localAddress -> ((UnitedStatesAddress) localAddress).getState())
      .ifPresent(unitedStatesAddress::in);

    return unitedStatesAddress;
  }

  public State getState() {
    return this.state;
  }

  public void setState(State state) {
    this.state = Optional.ofNullable(state)
      .orElseThrow(() -> newIllegalArgumentException("State [%s] is required", state));
  }

  public ZIP getZip() {
    return this.zip;
  }

  public void setZip(ZIP zip) {
    this.zip = Optional.ofNullable(zip)
      .orElseThrow(() -> newIllegalArgumentException("ZIP [%s] is required", zip));

    setPostalCode(zip);
  }

  public UnitedStatesAddress in(State state) {
    setState(state);
    return this;
  }

  public UnitedStatesAddress in(ZIP zipCode) {
    setZip(zipCode);
    return this;
  }

  @Override
  public boolean equals(Object obj) {

    if (this == obj) {
      return true;
    }

    if (!(obj instanceof UnitedStatesAddress)) {
      return false;
    }

    UnitedStatesAddress that = (UnitedStatesAddress) obj;

    return super.equals(obj)
      && ObjectUtils.equals(this.getState(), that.getState())
      && ObjectUtils.equals(this.getZip(), that.getZip());
  }

  @Override
  public int hashCode() {

    int hashValue = super.hashCode();

    hashValue = 37 * hashValue + ObjectUtils.hashCode(this.getState());
    hashValue = 37 * hashValue + ObjectUtils.hashCode(this.getZip());

    return hashValue;
  }

  @Override
  public String toString() {
    return String.format(
      "{ @type = %1$s, street = %2$s, unit = %3$s, city = %4$s, state = %5$s, zip = %6$s, country = %7$s, type = %8$s }",
        getClass().getName(), getStreet(), getUnit(), getCity(), getState(), getZip(), getCountry(), getType());
  }
}
