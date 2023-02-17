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
package org.cp.domain.geo.model.usa;

import java.io.Serializable;
import java.util.Optional;

import org.cp.domain.geo.enums.Country;
import org.cp.domain.geo.enums.State;
import org.cp.elements.lang.Assert;
import org.cp.elements.lang.Nameable;
import org.cp.elements.lang.ObjectUtils;
import org.cp.elements.lang.StringUtils;
import org.cp.elements.lang.annotation.NotNull;
import org.cp.elements.lang.annotation.Nullable;
import org.cp.elements.util.ComparatorResultBuilder;

/**
 * Abstract Data Type (ADT) modeling a {@literal County} in a {@link State}
 * of the {@link Country#UNITED_STATES_OF_AMERICA United States}.
 *
 * @author John Blum
 * @see java.io.Serializable
 * @see java.lang.Cloneable
 * @see java.lang.Comparable
 * @see org.cp.elements.lang.Nameable
 * @see org.cp.domain.geo.enums.Country
 * @see org.cp.domain.geo.enums.State
 * @since 0.1.0
 */
public class County implements Cloneable, Comparable<County>, Nameable<String>, Serializable {

  /**
   * Factory method used to construct a new instance of {@link County} copied from the existing,
   * required {@link County}.
   *
   * @param county {@link County} to copy; must not be {@literal null}.
   * @return a new {@link County} copied from the given, existing {@link County}.
   * @throws IllegalArgumentException if the given {@link County} is {@literal null}.
   * @see #of(String)
   */
  public static @NotNull County from(@NotNull County county) {

    Assert.notNull(county, "County to copy is required");

    return of(county.getName());

  }

  /**
   * Factory method used to construct a new instance of {@link County} with the given, required {@link String name}.
   *
   * @param name {@link String} containing the {@literal name} of this {@link County};
   * must not be {@literal null} or {@literal empty}.
   * @return a new {@link County} with the given {@link String name}.
   * @throws IllegalArgumentException if the given {@link String name} is {@literal null} or {@literal empty}.
   */
  public static @NotNull County of(@NotNull String name) {
    return new County(name);
  }

  private State state;

  private final String name;

  /**
   * Constructs a new instance of {@link County} initialized with the given, required {@link String name}.
   *
   * @param name {@link String} containing the {@literal name} of this {@link County};
   * must not be {@literal null} or {@literal empty}.
   * @throws IllegalArgumentException if the given {@link String name} is {@literal null} or {@literal empty}.
   */
  public County(@NotNull String name) {
    this.name = StringUtils.requireText(name, "Name [%s] is required");
  }

  /**
   * Return the {@link String name} of this {@link County}.
   *
   * @return the {@link String name} of this {@link County}.
   */
  @Override
  public @NotNull String getName() {
    return this.name;
  }

  /**
   * Returns an {@link Optional} {@link State} in which this {@link County} is located.
   *
   * @return an {@link Optional} {@link State} in which this {@link County} is located.
   * @see org.cp.domain.geo.enums.State
   * @see java.util.Optional
   * @see #in(State)
   */
  public Optional<State> getState() {
    return Optional.ofNullable(this.state);
  }

  /**
   * Builder method used to set the {@link State} in which this {@link County} is located.
   *
   * @param state {@link State} in which this {@link County} is located.
   * @return this {@link County}.
   * @see org.cp.domain.geo.enums.State
   */
  public @NotNull County in(@Nullable State state) {
    this.state = state;
    return this;
  }

  @Override
  @SuppressWarnings("all")
  protected @NotNull Object clone() throws CloneNotSupportedException {
    return from(this).in(resolveState(this));
  }

  @Override
  @SuppressWarnings({ "rawtypes", "unchecked" })
  public int compareTo(@NotNull County county) {

    return ComparatorResultBuilder.<Comparable>create()
      .doCompare(resolveState(this), resolveState(county))
      .doCompare(this.getName(), county.getName())
      .getResult();
  }

  @Override
  public boolean equals(@Nullable Object obj) {

    if (this == obj) {
      return true;
    }

    if (!(obj instanceof County)) {
      return false;
    }

    County that = (County) obj;

    return ObjectUtils.equals(this.getName(), that.getName())
      && ObjectUtils.equalsIgnoreNull(resolveState(this), resolveState(that));

  }

  @Override
  public int hashCode() {
    return ObjectUtils.hashCodeOf(getName(), resolveState(this));
  }

  private @Nullable State resolveState(@Nullable County county) {
    return county != null ? county.getState().orElse(null) : null;
  }

  @Override
  public @NotNull String toString() {
    return getName();
  }
}
