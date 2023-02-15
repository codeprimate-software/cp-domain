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

import static org.cp.elements.lang.RuntimeExceptionsFactory.newIllegalStateException;

import org.cp.domain.geo.enums.Country;
import org.cp.domain.geo.enums.State;
import org.cp.domain.geo.model.AbstractAddress;
import org.cp.domain.geo.model.Address;
import org.cp.domain.geo.model.City;
import org.cp.domain.geo.model.PostalCode;
import org.cp.domain.geo.model.Street;
import org.cp.domain.geo.model.usa.support.StateZipCodesRepository;
import org.cp.elements.lang.Assert;
import org.cp.elements.lang.ObjectUtils;
import org.cp.elements.lang.annotation.Alias;
import org.cp.elements.lang.annotation.NotNull;
import org.cp.elements.lang.annotation.Nullable;
import org.cp.elements.lang.annotation.Qualifier;
import org.cp.elements.util.ComparatorResultBuilder;

/**
 * {@link Address} implementation modeling a {@literal physical, postal address}
 * in the {@link Country#UNITED_STATES_OF_AMERICA United States of America}.
 *
 * @author John Blum
 * @see org.cp.elements.lang.annotation.Qualifier
 * @see org.cp.domain.geo.model.AbstractAddress
 * @see org.cp.domain.geo.model.Address
 * @see org.cp.domain.geo.model.Street
 * @see org.cp.domain.geo.model.Unit
 * @see org.cp.domain.geo.model.City
 * @see org.cp.domain.geo.model.PostalCode
 * @see org.cp.domain.geo.model.usa.ZIP
 * @see org.cp.domain.geo.enums.Country
 * @see org.cp.domain.geo.enums.State
 * @since 0.1.0
 */
@Qualifier(name = "usa")
public class UnitedStatesAddress extends AbstractAddress {

  protected static final String UNITED_STATES_ADDRESS_TO_STRING =
    "{ @type = %1$s, street = %2$s, unit = %3$s, city = %4$s, state = %5$s, zip = %6$s, country = %7$s, type = %8$s }";

  /**
   * Factory method used to construct a new instance of {@link UnitedStatesAddress.Builder} to build
   * an {@link UnitedStatesAddress}.
   *
   * @return a new {@link UnitedStatesAddress.Builder} used to build, construct and initialize
   * an {@link UnitedStatesAddress}.
   * @see org.cp.domain.geo.model.usa.UnitedStatesAddress.Builder
   */
  public static @NotNull UnitedStatesAddress.Builder newUnitedStatesAddress() {
    return new UnitedStatesAddress.Builder();
  }

  private final State state;

  /**
   * Constructs a new {@link UnitedStatesAddress} initialized with the given, required {@link Street}, {@link City},
   * {@link State} and {@link ZIP} code uniquely identifying a {@literal physical, postal address}
   * in the {@link Country#UNITED_STATES_OF_AMERICA United States of America}.
   *
   * @param street {@link Street} of this {@link Address}; must not be {@literal null}.
   * @param city {@link City} of this {@link Address}; must not be {@literal null}.
   * @param state {@link State} of this {@link Address}; must not be {@literal null}.
   * @param zip {@link ZIP}, or {@link PostalCode}, of this {@link Address}; must not be {@literal null}.
   * @throws IllegalArgumentException if the {@link Street}, {@link City}, {@link State} or {@link ZIP}
   * are {@literal null}.
   * @see org.cp.domain.geo.enums.Country#UNITED_STATES_OF_AMERICA
   * @see org.cp.domain.geo.model.Street
   * @see org.cp.domain.geo.model.City
   * @see org.cp.domain.geo.enums.State
   * @see org.cp.domain.geo.model.usa.ZIP
   */
  protected UnitedStatesAddress(@NotNull Street street, @NotNull City city, @NotNull State state, @NotNull ZIP zip) {

    super(street, city, zip, Country.UNITED_STATES_OF_AMERICA);

    this.state = ObjectUtils.requireObject(state, "State is required");
  }

  /**
   * Returns the {@link City} of this {@link UnitedStatesAddress} as a {@link UnitedStatesCity}.
   *
   * @return the {@link City} of this {@link UnitedStatesAddress} as a {@link UnitedStatesCity}.
   * @see org.cp.domain.geo.model.usa.UnitedStatesCity
   * @see org.cp.domain.geo.model.City
   * @see #getCity()
   */
  @Override
  public @NotNull City getCity() {
    return UnitedStatesCity.from(super.getCity(), getState());
  }

  /**
   * Returns the {@link State} of this {@link UnitedStatesAddress}.
   *
   * @return the {@link State} of this {@link UnitedStatesAddress}.
   * @see org.cp.domain.geo.enums.State
   */
  public @NotNull State getState() {
    return this.state;
  }

  /**
   * Returns the {@link ZIP} code of this {@link UnitedStatesAddress}.
   *
   * {@literal ZIP} code is an alias for {@link #getPostalCode() postal code}.
   *
   * @return the {@link ZIP} code of this {@link UnitedStatesAddress}.
   * @see org.cp.domain.geo.model.PostalCode
   * @see org.cp.domain.geo.model.usa.ZIP
   * @see #getPostalCode()
   */
  @Alias(forMember = "AbstractAddress.getPostalCode()")
  public @NotNull ZIP getZip() {
    return ZIP.from(getPostalCode());
  }

  /**
   * Compares this {@link UnitedStatesAddress} with the given, required {@link Address} to determine relative ordering
   * (sort order) in an ordered data structure, such as a {@link java.util.List} or an array.
   *
   * @param that {@link Address} to compare with this {@link Address}; must not be {@literal null}.
   * @return an {@link Integer value} determining the order (sort) of this {@link UnitedStatesAddress}
   * relative to the given, required {@link Address}.
   * @throws org.cp.elements.lang.IllegalTypeException if the given {@link Address} is not an instance of
   * {@link UnitedStatesAddress}.
   * @see java.lang.Comparable#compareTo(Object)
   * @see org.cp.domain.geo.model.Address
   */
  @Override
  @SuppressWarnings({ "rawtypes", "unchecked" })
  public int compareTo(@NotNull Address that) {

    Assert.isInstanceOf(that, UnitedStatesAddress.class);

    return ComparatorResultBuilder.<Comparable>create()
      .doCompare(this.getState(), ((UnitedStatesAddress) that).getState())
      .getResult(() -> super.compareTo(that));
  }

  /**
   * Determines whether this {@link UnitedStatesAddress} is equal to the given {@link Object}.
   *
   * @param obj {@link Object} to evaluate for equality with this {@link Address}.
   * @return a boolean value indicating whether this {@link UnitedStatesAddress} is equal to the given {@link Object}.
   * @see org.cp.domain.geo.model.AbstractAddress#equals(Object)
   * @see java.lang.Object#equals(Object)
   */
  @Override
  public boolean equals(@Nullable Object obj) {

    if (this == obj) {
      return true;
    }

    if (!(obj instanceof UnitedStatesAddress)) {
      return false;
    }

    UnitedStatesAddress that = (UnitedStatesAddress) obj;

    return super.equals(obj) && ObjectUtils.equals(this.getState(), that.getState());
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
    return ObjectUtils.hashCodeOf(super.hashCode(), getState());
  }

  /**
   * Returns a {@link String} representation of this {@link UnitedStatesAddress}.
   *
   * @return a {@link String} describing this {@link UnitedStatesAddress}.
   * @see java.lang.Object#toString()
   */
  @Override
  public @NotNull String toString() {

    return String.format(UNITED_STATES_ADDRESS_TO_STRING, getClass().getName(),
      getStreet(), getUnit().orElse(null), getCity(), getState(), getZip(), getCountry(),
      getType().orElse(Address.Type.UNKNOWN));
  }

  /**
   * {@link org.cp.elements.lang.Builder} implementation used to build an {@link UnitedStatesAddress}.
   *
   * @see org.cp.domain.geo.model.AbstractAddress.Builder
   * @see org.cp.domain.geo.model.usa.UnitedStatesAddress
   */
  public static class Builder extends AbstractAddress.Builder<UnitedStatesAddress> {

    private State state;

    /**
     * Constructs a new instance of {@link UnitedStatesAddress.Builder} used to build an {@link UnitedStatesAddress}
     * residing in the {@link Country#UNITED_STATES_OF_AMERICA}.
     *
     * @see org.cp.domain.geo.enums.Country#UNITED_STATES_OF_AMERICA
     */
    public Builder() {
      super(Country.UNITED_STATES_OF_AMERICA);
    }

    protected @NotNull State getState() {

      try {

        State state = resolveState(this.state);

        return ObjectUtils.requireState(state, "State was not initialized");
      }
      catch (IllegalArgumentException cause) {
        throw newIllegalStateException(cause, "State was not initialized");
      }
    }

    private @NotNull State resolveState(@Nullable State state) {
      return state != null ? state : StateZipCodesRepository.getInstance().findBy(getZip());
    }

    @Alias(forMember = "AbstractAddress.Builder.getPostalCode()")
    protected @NotNull ZIP getZip() {
      return ZIP.from(getPostalCode());
    }

    /**
     * Builder method used to set the {@link State} of the {@link UnitedStatesAddress}.
     *
     * @param state {@link State} of the {@link UnitedStatesAddress}; must not be {@literal null}.
     * @return this {@link UnitedStatesAddress.Builder}.
     * @throws IllegalArgumentException if {@link State} is {@literal null}.
     * @see org.cp.domain.geo.enums.State
     * @see #getState()
     */
    public @NotNull Builder in(@NotNull State state) {
      this.state = ObjectUtils.requireObject(state, "State is required");
      return this;
    }

    @Override
    protected @NotNull UnitedStatesAddress doBuild() {
      return new UnitedStatesAddress(getStreet(), getCity(), getState(), getZip());
    }
  }
}
