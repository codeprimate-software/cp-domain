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

import java.util.Optional;

import org.cp.domain.geo.enums.Country;
import org.cp.domain.geo.model.AbstractAddress;
import org.cp.domain.geo.model.Address;
import org.cp.domain.geo.model.City;
import org.cp.domain.geo.model.PostalCode;
import org.cp.domain.geo.model.Street;
import org.cp.elements.enums.State;
import org.cp.elements.lang.Assert;
import org.cp.elements.lang.ObjectUtils;

/**
 * The {@link UnitedStatesAddress} class is an implementation of {@link Address} that models and represents an
 * {@link Address} of the {@link Country#UNITED_STATES_OF_AMERICA Unite States of America}.
 *
 * @author John Blum
 * @see org.cp.domain.geo.enums.Country
 * @see org.cp.domain.geo.model.AbstractAddress
 * @see org.cp.domain.geo.model.Address
 * @see org.cp.domain.geo.model.City
 * @see org.cp.domain.geo.model.PostalCode
 * @see org.cp.domain.geo.model.Street
 * @see org.cp.domain.geo.model.Unit
 * @see org.cp.domain.geo.model.usa.ZIP
 * @see org.cp.elements.enums.State
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public class UnitedStatesAddress extends AbstractAddress {

  protected static final String UNITED_STATES_ADDRESS_TO_STRING =
    "{ @type = %1$s, street = %2$s, unit = %3$s, city = %4$s, state = %5$s, zip = %6$s, country = %7$s, type = %8$s }";

  /**
   * Factory method used to construct a new, uninitialized instance of {@link UnitedStatesAddress}.
   *
   * @return a new, uninitialized instance of {@link UnitedStatesAddress}.
   * @see org.cp.domain.geo.enums.Country#UNITED_STATES_OF_AMERICA
   * @see #in(Country)
   */
  public static UnitedStatesAddress newUnitedStatesAddress() {
    return new UnitedStatesAddress().in(Country.UNITED_STATES_OF_AMERICA);
  }

  /**
   * Constructs a new instance of {@link UnitedStatesAddress} initialized with the given {@link Street},
   * {@link City}, {@link State} and {@link ZIP}.
   *
   * @param street {@link Street} of the new {@link UnitedStatesAddress}.
   * @param city {@link City} of the new {@link UnitedStatesAddress}.
   * @param state {@link State} of the new {@link UnitedStatesAddress}.
   * @param zip (@link ZIP} of the new {@link UnitedStatesAddress}.
   * @return a new {@link UnitedStatesAddress} initialized with the given {@link Street}, {@link City},
   * {@link State} and {@link ZIP}.
   * @throws IllegalArgumentException if {@link Street}, {@link City}, {@link State} or {@link ZIP}
   * are {@literal null}.
   * @see org.cp.domain.geo.model.Street
   * @see org.cp.domain.geo.model.City
   * @see org.cp.domain.geo.model.usa.ZIP
   * @see org.cp.elements.enums.State
   * @see #newUnitedStatesAddress()
   * @see #on(Street)
   * @see #in(City)
   * @see #in(State)
   * @see #in(ZIP)
   */
  public static UnitedStatesAddress of(Street street, City city, State state, ZIP zip) {
    return newUnitedStatesAddress().in(zip).in(state).in(city).on(street);
  }

  /**
   * Factory method used to contruct a new instance of {@link UnitedStatesAddress} copied from
   * the given {@link Address}.
   *
   * @param address {@link Address} to copy.
   * @return a new {@link UnitedStatesAddress} copied from the given {@link Address}.
   * @throws IllegalArgumentException if {@link Address} is {@literal null}.
   * @see org.cp.domain.geo.model.Address
   * @see #newUnitedStatesAddress()
   */
  public static UnitedStatesAddress from(Address address) {

    Assert.notNull(address, "Address to copy is required");

    UnitedStatesAddress unitedStatesAddress = newUnitedStatesAddress()
      .as(address.getType().orElse(null))
      .on(address.getStreet())
      .in(address.getUnit().orElse(null))
      .in(address.getCity())
      .in(address.getPostalCode())
      .with(address.getCoordinates().orElse(null));

    Optional.of(address)
      .filter(it -> it instanceof UnitedStatesAddress)
      .map(it -> ((UnitedStatesAddress) it).getState())
      .ifPresent(unitedStatesAddress::in);

    Optional.of(address)
      .filter(it -> it instanceof UnitedStatesAddress)
      .map(it -> ((UnitedStatesAddress) it).getZip())
      .ifPresent(unitedStatesAddress::in);

    return unitedStatesAddress;
  }

  private State state;

  private ZIP zip;

  /**
   * Sets the {@link State} of this {@link UnitedStatesAddress}.
   *
   * @param state {@link State} to set for this {@link UnitedStatesAddress}.
   * @throws IllegalArgumentException if {@link State} is {@literal null}.
   * @see org.cp.elements.enums.State
   */
  public void setState(State state) {

    Assert.notNull(state,"State is required");

    this.state = state;
  }

  /**
   * Returns the {@link State} of this {@link UnitedStatesAddress}.
   *
   * @return the {@link State} of this {@link UnitedStatesAddress}.
   * @see org.cp.elements.enums.State
   */
  public State getState() {
    return this.state;
  }

  /**
   * Sets the {@link ZIP Zip Code} for this {@link UnitedStatesAddress}.
   *
   * @param zip {@link ZIP Zip Code} to set for this {@link UnitedStatesAddress}.
   * @throws IllegalArgumentException if {@link ZIP} is {@literal null}.
   * @see org.cp.domain.geo.model.usa.ZIP
   * @see #setPostalCode(PostalCode)
   */
  public void setZip(ZIP zip) {

    Assert.notNull(zip,"Zip is required");

    this.zip = zip;
    setPostalCode(zip);
  }

  /**
   * Returns the {@link ZIP Zip Code} for this {@link UnitedStatesAddress}.
   *
   * @return the {@link ZIP Zip Code} for this {@link UnitedStatesAddress}.
   * @see org.cp.domain.geo.model.PostalCode
   * @see org.cp.domain.geo.model.usa.ZIP
   * @see #getPostalCode()
   */
  public ZIP getZip() {

    return Optional.ofNullable(this.zip)
      .orElseGet(() ->
        Optional.ofNullable(getPostalCode())
          .map(it -> ZIP.of(it.getNumber()))
          .orElse(null));
  }

  /**
   * Builder method to set the {@link State} of this {@link UnitedStatesAddress}.
   *
   * @param state {@link State} to set for this {@link UnitedStatesAddress}.
   * @return this {@link UnitedStatesAddress}.
   * @throws IllegalArgumentException if {@link State} is {@literal null}.
   * @see org.cp.elements.enums.State
   * @see #setState(State)
   */
  public UnitedStatesAddress in(State state) {
    setState(state);
    return this;
  }

  /**
   * Builder method to set the {@link ZIP Zip Code} for this {@link UnitedStatesAddress}.
   *
   * @param zipCode {@link ZIP Zip Code} to set for this {@link UnitedStatesAddress}.
   * @return this {@link UnitedStatesAddress}.
   * @throws IllegalArgumentException if {@link ZIP} is {@literal null}.
   * @see org.cp.domain.geo.model.usa.ZIP
   * @see #setZip(ZIP)
   */
  public UnitedStatesAddress in(ZIP zipCode) {
    setZip(zipCode);
    return this;
  }

  /**
   * Determines whether this {@link UnitedStatesAddress} is equal to the given {@link Object}.
   *
   * @param obj {@link Object} to evaluate for equality with this {@link Address}.
   * @return a boolean value indicating whether this {@link UnitedStatesAddress} is equal to
   * the given {@link Object}.
   * @see org.cp.domain.geo.model.AbstractAddress#equals(Object)
   * @see java.lang.Object#equals(Object)
   */
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

  /**
   * Computes the {@link Integer hash code} of this {@link UnitedStatesAddress}.
   *
   * @return the computed {@link Integer hash code} of this {@link UnitedStatesAddress}.
   * @see org.cp.domain.geo.model.AbstractAddress#hashCode()
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {

    int hashValue = super.hashCode();

    hashValue = 37 * hashValue + ObjectUtils.hashCode(this.getState());
    hashValue = 37 * hashValue + ObjectUtils.hashCode(this.getZip());

    return hashValue;
  }

  /**
   * Returns a {@link String} representation of this {@link UnitedStatesAddress}.
   *
   * @return a {@link String} representation of this {@link UnitedStatesAddress}.
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {

    return String.format(UNITED_STATES_ADDRESS_TO_STRING,
      getClass().getName(), getStreet(), getUnit(), getCity(), getState(), getZip(), getCountry(), getType());
  }
}
