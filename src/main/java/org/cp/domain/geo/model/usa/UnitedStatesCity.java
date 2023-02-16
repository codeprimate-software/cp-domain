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
import org.cp.domain.geo.enums.State;
import org.cp.domain.geo.model.City;
import org.cp.elements.lang.Assert;
import org.cp.elements.lang.ObjectUtils;
import org.cp.elements.lang.annotation.NotNull;
import org.cp.elements.lang.annotation.NullSafe;
import org.cp.elements.lang.annotation.Nullable;
import org.cp.elements.util.ComparatorResultBuilder;

/**
 * {@link City} in the {@link Country#UNITED_STATES_OF_AMERICA United States of America}.
 *
 * @author John Blum
 * @see org.cp.domain.geo.enums.Country
 * @see org.cp.domain.geo.enums.State
 * @see org.cp.domain.geo.model.City
 * @since 0.1.0
 */
@SuppressWarnings("unused")
public class UnitedStatesCity extends City {

  /**
   * Factory method used to construct a new instance of {@link UnitedStatesCity} initialized with the given,
   * required {@link String name}.
   *
   * @param name {@link String} containing the {@literal name} of the {@link City};
   * must not be {@literal null} or {@literal empty}.
   * @return a new {@link UnitedStatesCity} with the given {@link String name}.
   * @throws IllegalArgumentException if the given {@link String name} is {@literal null} or {@literal empty}.
   */
  public static @NotNull UnitedStatesCity newUnitedStatesCity(@NotNull String name) {
    return new UnitedStatesCity(name);
  }

  /**
   * Factory method used to construct a new instance of {@link UnitedStatesCity} copied from the existing,
   * required {@link City}.
   *
   * @param city {@link City} to copy; must not be {@literal null}.
   * @return a new {@link UnitedStatesCity} copied from the existing {@link City}
   * @throws IllegalArgumentException if the given {@link City} is {@literal null}.
   * @see org.cp.domain.geo.model.City
   * @see #newUnitedStatesCity(String)
   */
  public static @NotNull UnitedStatesCity from(@NotNull City city) {

    Assert.notNull(city, "City is required");

    UnitedStatesCity usCity = newUnitedStatesCity(city.getName());

    Optional.of(city)
      .filter(UnitedStatesCity.class::isInstance)
      .map(UnitedStatesCity.class::cast)
      .map(UnitedStatesCity::getState)
      .ifPresent(usCity::in);

    return usCity;
  }

  private State state;

  /**
   * Constructs a new instance of {@link UnitedStatesCity} initialized with the given, required {@link String name}.
   *
   * @param name {@link String} containing the {@literal name} of the {@link City};
   * must not be {@literal null} or {@literal empty}.
   * @throws IllegalArgumentException if the given {@link String name} is {@literal null} or {@literal empty}.
   */
  public UnitedStatesCity(@NotNull String name) {
    super(name);
  }

  /**
   * Determines whether this {@link City} is the {@literal capital} of
   * the {@link Country#UNITED_STATES_OF_AMERICA United States}
   * or the {@literal capital} of the containing {@link State}.
   *
   * Returns {@literal false} by default.
   *
   * @return a boolean value indicating whether this {@link City} is the {@literal capital} of
   * the {@link Country#UNITED_STATES_OF_AMERICA United States} or the {@literal capital} of
   * the containing {@link State}.
   */
  public boolean isCapital() {
    return false;
  }

  /**
   * Returns the {@link Country#UNITED_STATES_OF_AMERICA}.
   *
   * @return the {@link Country#UNITED_STATES_OF_AMERICA}.
   * @see org.cp.domain.geo.enums.Country#UNITED_STATES_OF_AMERICA
   * @see java.util.Optional
   */
  @Override
  public final Optional<Country> getCountry() {
    return Optional.of(Country.UNITED_STATES_OF_AMERICA);
  }

  /**
   * Configures the {@link State} in which this {@link City} is located.
   *
   * @param state {@link State} in which this {@link City} is located; must not be {@literal null}.
   * @throws IllegalArgumentException if the given {@link State} is {@literal null}.
   * @see org.cp.domain.geo.enums.State
   * @see #getState()
   * @see #in(State)
   */
  public void setState(@NotNull State state) {
    this.state = ObjectUtils.requireObject(state, "State is required");
  }

  /**
   * Returns the configured {@link State} in which this {@link City} is located.
   *
   * @return the configured {@link State} in which this {@link City} is located.
   * @see org.cp.domain.geo.enums.State
   * @see #setState(State)
   */
  public @Nullable State getState() {
    return this.state;
  }

  /**
   * Builder method used to configure the {@link State} in which this {@link City} is located.
   *
   * @param state {@link State} in which this {@link City} is located; must not be {@literal null}.
   * @return this {@link UnitedStatesCity}.
   * @throws IllegalArgumentException if the given {@link State} is {@literal null}.
   * @see #setState(State)
   */
  public @NotNull UnitedStatesCity in(@NotNull State state) {
    setState(state);
    return this;
  }

  @Override
  @SuppressWarnings({ "rawtypes", "unchecked" })
  public int compareTo(@NotNull City city) {

    ComparatorResultBuilder builder = ComparatorResultBuilder.<Comparable>create();

    builder.doCompare(resolveCountry(this), resolveCountry(city));

    Optional.of(city)
      .filter(UnitedStatesCity.class::isInstance)
      .map(UnitedStatesCity.class::cast)
      .ifPresent(it -> builder.doCompare(this.getState(), it.getState()));

    builder.doCompare(this.getName(), city.getName());

    return builder.build();
  }

  @Override
  public boolean equals(@Nullable Object obj) {

    if (this == obj) {
      return true;
    }

    if (!(obj instanceof City)) {
      return false;
    }

    City that = (City) obj;

    return isUnitedStatesCity(that)
      && super.equals(obj)
      && ObjectUtils.equalsIgnoreNull(this.getState(), resolveState(that));
  }

  @NullSafe
  private boolean isUnitedStatesCity(@Nullable City city) {
    return city instanceof UnitedStatesCity
      || Country.UNITED_STATES_OF_AMERICA.equals(resolveCountry(city));
  }

  @NullSafe
  private @Nullable Country resolveCountry(@Nullable City city) {
    return city != null ? city.getCountry().orElse(null) : null;
  }

  @NullSafe
  private @Nullable State resolveState(@Nullable City city) {
    return city instanceof UnitedStatesCity ? ((UnitedStatesCity) city).getState() : null;
  }

  @Override
  public int hashCode() {
    return ObjectUtils.hashCodeOf(super.hashCode(), getState());
  }
}
