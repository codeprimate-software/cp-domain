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
import org.cp.domain.geo.enums.State;
import org.cp.domain.geo.model.City;
import org.cp.elements.lang.Assert;
import org.cp.elements.lang.ObjectUtils;
import org.cp.elements.util.ComparatorResultBuilder;

/**
 * The UnitedStatesCity class...
 *
 * @author John Blum
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public class UnitedStatesCity extends City {

  private State state;

  public static UnitedStatesCity newUnitedStatesCity(String name) {
    return new UnitedStatesCity(name);
  }

  public static UnitedStatesCity of(String name, State state) {
    return newUnitedStatesCity(name).in(state);
  }

  public static UnitedStatesCity from(City city) {

    Assert.notNull(city, "City must not be null");

    return newUnitedStatesCity(city.getName());
  }

  public UnitedStatesCity(String name) {
    super(name);
  }

  public boolean isCapital() {
    return false;
  }

  @Override
  public final Optional<Country> getCountry() {
    return Optional.of(Country.UNITED_STATES_OF_AMERICA);
  }

  public void setState(State state) {
    this.state = Optional.ofNullable(state)
      .orElseThrow(() -> newIllegalArgumentException("State is required"));
  }

  public State getState() {
    return this.state;
  }

  public UnitedStatesCity in(State state) {
    setState(state);
    return this;
  }

  @Override
  @SuppressWarnings("unchecked")
  public int compareTo(City city) {
    ComparatorResultBuilder builder = ComparatorResultBuilder.<Comparable>create()
      .doCompare(this.getCountry().orElse(null), city.getCountry().orElse(null));

    Optional.of(city)
      .filter(localCity-> localCity instanceof UnitedStatesCity)
      .ifPresent(localCity -> builder.doCompare(this.getState(), ((UnitedStatesCity) localCity).getState()));

    return builder.doCompare(this.getName(), city.getName()).build();
  }

  @Override
  public boolean equals(Object obj) {

    if (this == obj) {
      return true;
    }

    if (!(obj instanceof UnitedStatesCity)) {
      return false;
    }

    UnitedStatesCity that = (UnitedStatesCity) obj;

    return ObjectUtils.equals(this.getState(), that.getState());
  }

  @Override
  public int hashCode() {

    int hashValue = super.hashCode();

    hashValue = 37 * hashValue + ObjectUtils.hashCode(this.getState());
    hashValue = 37 * hashValue + ObjectUtils.hashCode(this.getCountry());

    return hashValue;
  }
}
