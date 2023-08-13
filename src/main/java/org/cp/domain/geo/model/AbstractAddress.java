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
package org.cp.domain.geo.model;

import java.util.Locale;
import java.util.Optional;

import org.cp.domain.geo.enums.Country;
import org.cp.elements.lang.ObjectUtils;
import org.cp.elements.lang.annotation.FluentApi;
import org.cp.elements.lang.annotation.Id;
import org.cp.elements.lang.annotation.NotNull;
import org.cp.elements.lang.annotation.Nullable;

/**
 * Abstract Data Type (ADT) and implementation of the {@link Address} interface modeling a {@literal physical,
 * postal address}.
 * <p>
 * An {@link Address} is defined by a {@link Street}, an {@link Optional} {@link Unit}, {@link City}, {@link PostalCode}
 * and {@link Country} of origin, along with {@link Optional} {@link Coordinates geographic coordinates}.
 * <p>
 * An {@link Address} can also have a {@link Type} to indicate what kind of {@literal postal address}
 * this {@link Address} represents. The {@link Address.Type} can be used to define the {@link Address Address's}
 * use or purpose in an application.
 * <p>
 * {@link Coordinates Geographic coordinates} may be declared for geographic position on a map.
 *
 * @author John Blum
 * @see java.util.Locale
 * @see org.cp.domain.geo.enums.Country
 * @see org.cp.domain.geo.model.Address
 * @see org.cp.domain.geo.model.Address.Type
 * @see org.cp.domain.geo.model.City
 * @see org.cp.domain.geo.model.Coordinates
 * @see org.cp.domain.geo.model.PostalCode
 * @see org.cp.domain.geo.model.Street
 * @see org.cp.domain.geo.model.Unit
 * @see org.cp.domain.geo.model.generic.GenericAddress
 * @see org.cp.domain.geo.model.usa.UnitedStatesAddress
 * @see org.cp.elements.lang.annotation.FluentApi
 * @since 0.1.0
 */
@FluentApi
@SuppressWarnings("unused")
public abstract class AbstractAddress implements Address {

  protected static final String ADDRESS_TO_STRING =
    "{ @type = %1$s, street = %2$s, unit = %3$s, city = %4$s, postal code = %5$s, country = %6$s, type = %7$s }";


  private final City city;

  private Coordinates coordinates;

  private final Country country;

  @Id
  private Long id;

  private final PostalCode postalCode;

  private final Street street;

  private Type type;

  private Unit unit;

  /**
   * Constructs a new {@link AbstractAddress} initialized with the given, required {@link Street}, {@link City}
   * and {@link PostalCode}, along with a {@link Country} determined by {@link Locale}.
   *
   * @param street {@link Street} of the {@link Address}; must not be {@literal null}.
   * @param city {@link City} of the {@link Address}; must not be {@literal null}.
   * @param postalCode {@link PostalCode} of the {@link Address}; must not be {@literal null}.
   * @throws IllegalArgumentException if {@link Street}, {@link City} or {@link PostalCode} are {@literal null}.
   * @see org.cp.domain.geo.enums.Country#localCountry()
   * @see org.cp.domain.geo.model.Street
   * @see org.cp.domain.geo.model.City
   * @see org.cp.domain.geo.model.PostalCode
   */
  protected AbstractAddress(@NotNull Street street, @NotNull City city, @NotNull PostalCode postalCode) {
    this(street, city, postalCode, Country.localCountry());
  }

  /**
   * Constructs a new {@link AbstractAddress} initialized with the given, required {@link Street}, {@link City},
   * {@link PostalCode} and {@link Country}.
   *
   * @param street {@link Street} of the {@link Address}; must not be {@literal null}.
   * @param city {@link City} of the {@link Address}; must not be {@literal null}.
   * @param postalCode {@link PostalCode} of the {@link Address}; must not be {@literal null}.
   * @param country {@link Country} in which this {@link Address} is located; must not be {@literal null}.
   * @throws IllegalArgumentException if {@link Street}, {@link City}, {@link PostalCode} or {@link Country}
   * are {@literal null}.
   * @see org.cp.domain.geo.model.Street
   * @see org.cp.domain.geo.model.City
   * @see org.cp.domain.geo.model.PostalCode
   * @see org.cp.domain.geo.enums.Country
   */
  protected AbstractAddress(@NotNull Street street, @NotNull City city, @NotNull PostalCode postalCode,
      @NotNull Country country) {

    this.street = ObjectUtils.requireObject(street, "Street is required");
    this.city = ObjectUtils.requireObject(city, "City is required");
    this.postalCode = ObjectUtils.requireObject(postalCode, "PostalCode is required");
    this.country = ObjectUtils.requireObject(country, "Country is required");
  }

  /**
   * Sets the {@link Long identifier} uniquely identifying this {@link Address}.
   *
   * @param id {@link Long identifier} uniquely identifying this {@link Address}.
   * @see org.cp.elements.lang.Identifiable
   */
  @Override
  public void setId(@Nullable Long id) {
    this.id = id;
  }

  /**
   * Returns the {@link Long identifier} uniquely identifying this {@link Address}.
   *
   * @return the {@link Long identifier} uniquely identifying this {@link Address}.
   * @see org.cp.elements.lang.Identifiable
   */
  @Override
  public @Nullable Long getId() {
    return this.id;
  }

  @Override
  public @NotNull Street getStreet() {
    return this.street;
  }

  /**
   * Sets an optional {@link Unit} on the {@link Street} at this {@link Address}.
   * <p>
   * The {@link Unit} may represent an {@literal apartment number}, an {@literal office number}
   * a {@literal suite number}, or any similar {@literal unit number}.
   *
   * @param unit {@link Unit} on the {@link Street} at this {@link Address}.
   * @see org.cp.domain.geo.model.Unit
   * @see #getUnit()
   */
  public void setUnit(@Nullable Unit unit) {
    this.unit = unit;
  }

  @Override
  public Optional<Unit> getUnit() {
    return Optional.ofNullable(this.unit);
  }

  @Override
  public @NotNull City getCity() {
    return this.city;
  }

  @Override
  public @NotNull PostalCode getPostalCode() {
    return this.postalCode;
  }

  @Override
  public @NotNull Country getCountry() {
    return this.country;
  }

  @Override
  public void setCoordinates(@Nullable Coordinates coordinates) {
    this.coordinates = coordinates;
  }

  @Override
  public Optional<Coordinates> getCoordinates() {
    return Optional.ofNullable(this.coordinates);
  }

  @Override
  public void setType(@Nullable Type type) {
    this.type = type;
  }

  @Override
  public Optional<Type> getType() {
    return Optional.ofNullable(this.type);
  }

  /**
   * Clones this {@link Address}.
   *
   * @return a {@link Address clone} ({@literal copy}) of this {@link Address}.
   * @throws CloneNotSupportedException if a {@link Object#clone()} of this {@link Address} is not supported.
   */
  @Override
  @SuppressWarnings("all")
  public @NotNull Object clone() throws CloneNotSupportedException {
    return Address.from(this);
  }

  /**
   * Determines whether this {@link Address} is equal to the given {@link Object}.
   * <p>
   * Equality is determined by {@link #getStreet() street}, {@link #getUnit() unit}, {@link #getCity() city},
   * {@link #getPostalCode() postal code} and {@link #getCountry() country}.
   *
   * @param obj {@link Object} to evaluate for equality with this {@link Address}.
   * @return a boolean value indicating whether this {@link Address} is equal to the given {@link Object}.
   * @see java.lang.Object#equals(Object)
   */
  @Override
  public boolean equals(@Nullable Object obj) {

    if (this == obj) {
      return true;
    }

    if (!(obj instanceof Address that)) {
      return false;
    }

    return ObjectUtils.equals(this.getStreet(), that.getStreet())
      && ObjectUtils.equals(this.getUnit().orElse(Unit.EMPTY), that.getUnit().orElse(Unit.EMPTY))
      && ObjectUtils.equals(this.getCity(), that.getCity())
      && ObjectUtils.equals(this.getPostalCode(), that.getPostalCode())
      && ObjectUtils.equals(this.getCountry(), that.getCountry());
  }

  /**
   * Computes the {@link Integer hash code} for this {@link Address}.
   *
   * @return the computed {@link Integer hash code} for this {@link Address}.
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    return ObjectUtils.hashCodeOf(getStreet(), getUnit().orElse(Unit.EMPTY), getCity(), getPostalCode(), getCountry());
  }

  /**
   * Returns a {@link String} representation of this {@link Address}.
   *
   * @return a {@link String} describing this {@link Address}.
   * @see java.lang.Object#toString()
   */
  @Override
  public @NotNull String toString() {

    return String.format(ADDRESS_TO_STRING, getClass().getName(),
      getStreet(), getUnit().orElse(null), getCity(), getPostalCode(), getCountry(),
        getType().orElse(Type.UNKNOWN));
  }
}
