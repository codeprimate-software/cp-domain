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
import org.cp.domain.geo.model.generic.GenericAddress;
import org.cp.domain.geo.model.usa.UnitedStatesAddress;
import org.cp.elements.lang.Assert;
import org.cp.elements.lang.ObjectUtils;
import org.cp.elements.lang.annotation.Dsl;
import org.cp.elements.lang.annotation.FluentApi;
import org.cp.elements.lang.annotation.Id;
import org.cp.elements.lang.annotation.NotNull;
import org.cp.elements.lang.annotation.NullSafe;
import org.cp.elements.lang.annotation.Nullable;

/**
 * Abstract Data Type (ADT) and implementation of the {@link Address} interface modeling a {@literal physical,
 * postal address} defined by a {@link Street}, an {@link Optional} {@link Unit}, {@link City}, {@link PostalCode}
 * and {@link Country} of origin along with {@link Optional} geographic {@link Coordinates}.
 *
 * An {@link Address} can also have a {@link Type} to indicate what kind of postal address this {@link Address}
 * represents. The {@link Address.Type} can be used to define the {@link Address Address's} use or purpose.
 *
 * The geographic {@link Coordinates} may be declared for reverse geocoding purposes.
 *
 * @author John Blum
 * @see java.util.Locale
 * @see org.cp.domain.geo.enums.Country
 * @see org.cp.domain.geo.model.Address
 * @see org.cp.domain.geo.model.Address.Type
 * @see org.cp.domain.geo.model.City
 * @see org.cp.domain.geo.model.Coordinates
 * @see org.cp.domain.geo.model.Locatable
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

  /**
   * Factory method used to construct a new {@link AbstractAddress} initialized with and copied from
   * the given, required {@link Address}.
   *
   * @param <T> concrete {@link Class subtype} of the new {@link AbstractAddress}.
   * The concrete {@link Address} {@link Class type} is based on {@link Country} of origin
   * from the given, required {@link Address}.
   * @param address {@link Address} to copy; must not be {@literal null}.
   * @return a new {@link AbstractAddress} copied from the given, required {@link Address}.
   * @throws IllegalArgumentException if the given {@link Address} is {@literal null}.
   * @see org.cp.elements.lang.annotation.Dsl
   * @see org.cp.domain.geo.model.Address
   * @see #newAddress(Country)
   */
  @Dsl
  public static @NotNull <T extends AbstractAddress> T from(@NotNull Address address) {

    Assert.notNull(address, "Address to copy is required");

    T addressCopy = of(address.getStreet(), address.getCity(), address.getPostalCode(), address.getCountry());

    address.getCoordinates().ifPresent(addressCopy::setCoordinates);
    address.getType().ifPresent(addressCopy::setType);
    address.getUnit().ifPresent(addressCopy::setUnit);

    return addressCopy;
  }

  /**
   * Factory method used to construct a new {@link AbstractAddress} based in the {@link Country} of origin
   * determined by the current, default {@link Locale}.
   *
   * @return a new {@link AbstractAddress} based in the {@link Country} of origin determined by
   * the current, default {@link Locale}.
   * @see org.cp.domain.geo.enums.Country#localCountry()
   * @see org.cp.domain.geo.model.AbstractAddress
   * @see org.cp.elements.lang.annotation.Dsl
   * @see #newAddress(Country)
   */
  @Dsl
  @NullSafe
  public static @NotNull AbstractAddress.Builder<? extends AbstractAddress> newAddress() {
    return newAddress(Country.localCountry());
  }

  /**
   * Factory method used to construct a new {@link AbstractAddress} based in the given {@link Country}.
   *
   * The concrete {@link Class subtype} of the new {@link AbstractAddress} is based on
   * the given {@link Country} of origin.
   *
   * @param country {@link Country} of origin for the new {@link AbstractAddress}.
   * @return a new {@link AbstractAddress} based in the given {@link Country}.
   * @see org.cp.domain.geo.model.usa.UnitedStatesAddress
   * @see org.cp.domain.geo.model.generic.GenericAddress
   * @see org.cp.domain.geo.model.AbstractAddress
   * @see org.cp.elements.lang.annotation.Dsl
   * @see org.cp.domain.geo.enums.Country
   */
  @Dsl
  @NullSafe
  public static @NotNull AbstractAddress.Builder<? extends AbstractAddress> newAddress(@Nullable Country country) {

    Optional<Country> optionalCountry = Optional.ofNullable(country);

    return optionalCountry
      .filter(Country.UNITED_STATES_OF_AMERICA::equals)
      .<AbstractAddress.Builder<? extends AbstractAddress>>map(it -> UnitedStatesAddress.newUnitedStatesAddress())
      .orElseGet(() -> optionalCountry
        .map(GenericAddress::newGenericAddress)
        .orElseGet(GenericAddress::newGenericAddress));
  }

  /**
   * Factory method used to construct a new {@link AbstractAddress} initialized with the given, required {@link Street},
   * {@link City} and {@link PostalCode}, defaulting to the {@link Country} of origin determined by the current,
   * default {@link Locale}.
   *
   * @param <T> concrete {@link Class subtype} of the new {@link AbstractAddress}.
   * The concrete {@link Address} {@link Class type} is based on the {@link Country} of origin determined by
   * the current, default {@link Locale}, such as the {@link UnitedStatesAddress Unites States of America}.
   * @param street {@link Street} of the {@link Address}; must not be {@literal null}.
   * @param city {@link City} of the {@link Address}; must not be {@literal null}.
   * @param postalCode {@link PostalCode} of the {@link Address}; must not be {@literal null}.
   * @return a new {@link AbstractAddress} initialized from the given, required {@link Street}, {@link City},
   * {@link PostalCode} and {@link Country} of origin determined by the current, default {@link Locale}.
   * @throws IllegalArgumentException if {@link Street}, {@link City} or {@link PostalCode} are {@literal null}.
   * @see org.cp.elements.lang.annotation.Dsl
   * @see org.cp.domain.geo.model.Street
   * @see org.cp.domain.geo.model.City
   * @see org.cp.domain.geo.model.PostalCode
   * @see #newAddress()
   */
  @Dsl
  @SuppressWarnings("unchecked")
  public static @NotNull <T extends AbstractAddress> T of(@NotNull Street street,
      @NotNull City city, @NotNull PostalCode postalCode) {

    return (T) newAddress()
      .on(street)
      .in(city)
      .in(postalCode)
      .build();
  }

  /**
   * Factory method used to construct a new {@link AbstractAddress} initialized with the given, required {@link Street},
   * {@link City}, {@link PostalCode} and {@link Country}.
   *
   * @param <T> concrete {@link Class subtype} of the new {@link AbstractAddress}.
   * The concrete {@link Address} {@link Class type} is based on the given {@link Country} of origin,
   * such as the {@link UnitedStatesAddress United States of America}.
   * @param street {@link Street} of the new {@link Address}; must not be {@literal null}.
   * @param city {@link City} of the new {@link Address}; must not be {@literal null}.
   * @param postalCode {@link PostalCode} of the new {@link Address}; must not be {@literal null}.
   * @param country {@link Country} of the new {@link Address}; must not be {@literal null}.
   * @return a new {@link AbstractAddress} initialized from the given, required {@link Street},
   * {@link City}, {@link PostalCode} and {@link Country}.
   * @throws IllegalArgumentException if {@link Street}, {@link City} or {@link PostalCode} are {@literal null}.
   * @see org.cp.elements.lang.annotation.Dsl
   * @see org.cp.domain.geo.model.Street
   * @see org.cp.domain.geo.model.City
   * @see org.cp.domain.geo.model.PostalCode
   * @see org.cp.domain.geo.enums.Country
   * @see #newAddress(Country)
   */
  @Dsl
  @SuppressWarnings("unchecked")
  public static @NotNull <T extends AbstractAddress> T of(@NotNull Street street,
      @NotNull City city, @NotNull PostalCode postalCode, @NotNull Country country) {

    return (T) newAddress(country)
      .on(street)
      .in(city)
      .in(postalCode)
      .build();
  }

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
   * and {@link PostalCode} along with a {@link Country} determined by the current, default {@link Locale}.
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
   * @param country {@link Country} of the {@link Address}; must not be {@literal null}.
   * @throws IllegalArgumentException if {@link Street}, {@link City}, {@link PostalCode} or {@link Country}
   * are {@literal null}.
   * @see org.cp.domain.geo.enums.Country
   * @see org.cp.domain.geo.model.Street
   * @see org.cp.domain.geo.model.City
   * @see org.cp.domain.geo.model.PostalCode
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
   * Sets an optional {@link Unit} on the {@link Street} of this {@link Address}.
   *
   * The {@link Unit} may represent an {@literal apartment number}, an {@literal office number}
   * a {@literal suite number}, or any similar {@literal unit number}.
   *
   * @param unit {@link Unit} on the {@link Street} of this {@link Address}.
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
   * @return a {@link Address clone} (copy) of this {@link Address}.
   * @throws CloneNotSupportedException if a {@link Object#clone()} of this {@link Address} is not supported.
   */
  @Override
  @SuppressWarnings("all")
  public @NotNull Object clone() throws CloneNotSupportedException {
    return from(this);
  }

  /**
   * Determines whether this {@link Address} is equal to the given {@link Object}.
   *
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

  /**
   * An Elements {@link org.cp.elements.lang.Builder} implementation used to build and construct
   * a concrete{@link AbstractAddress} from the components of an {@link Address}.
   *
   * @param <T> concrete {@link AbstractAddress} {@link Class type} based on the {@link Country} of origin
   * determined by the current, default {@link Locale}.
   * @see org.cp.elements.lang.Builder
   */
  public static abstract class Builder<T extends AbstractAddress> implements org.cp.elements.lang.Builder<T> {

    private Street street;
    private Unit unit;
    private City city;
    private PostalCode postalCode;
    private final Country country;
    private Coordinates coordinates;

    /**
     * Constructs a new {@link AbstractAddress.Builder} initialized with the given, required {@link Country} of origin
     * for the {@link Address}.
     *
     * @param country {@link Country} of origin for the {@link Address}; must not be {@literal null}.
     * @throws IllegalArgumentException if the given {@link Country} is {@literal null}.
     * @see org.cp.domain.geo.enums.Country
     */
    protected Builder(@NotNull Country country) {
      this.country = ObjectUtils.requireObject(country, "Country is required");
    }

    /**
     * Get the configured {@link Street} for the {@link Address}.
     *
     * @return the configured {@link Street} for the {@link Address}.
     * @see org.cp.domain.geo.model.Street
     */
    protected Street getStreet() {
      return this.street;
    }

    /**
     * Get the configured {@link City} for the {@link Address}.
     *
     * @return the configured {@link City} for the {@link Address}.
     * @see org.cp.domain.geo.model.City
     */
    protected City getCity() {
      return this.city;
    }

    /**
     * Get the configured {@link PostalCode} for the {@link Address}.
     *
     * @return the configured {@link PostalCode} for the {@link Address}.
     * @see org.cp.domain.geo.model.PostalCode
     */
    protected PostalCode getPostalCode() {
      return this.postalCode;
    }

    /**
     * Get the configured{@link Country} for the {@link Address}.
     *
     * @return the configured {@link Country} for the {@link Address}.
     * @see org.cp.domain.geo.enums.Country
     */
    protected @NotNull Country getCountry() {
      return this.country;
    }

    /**
     * Get the configured, {@link Optional} {@link Coordinates} at the {@link Address}.
     *
     * @return the configured, {@link Optional} {@link Coordinates} at the {@link Address}.
     * @see org.cp.domain.geo.model.Coordinates
     * @see java.util.Optional
     */
    protected Optional<Coordinates> getCoordinates() {
      return Optional.ofNullable(this.coordinates);
    }

    /**
     * Get the configured, {@link Optional} {@link Unit} on the {@link Street} of the {@link Address}.
     *
     * @return the configured, {@link Optional} {@link Unit} on the {@link Street} of the {@link Address}.
     * @see org.cp.domain.geo.model.Unit
     * @see java.util.Optional
     */
    protected Optional<Unit> getUnit() {
      return Optional.ofNullable(this.unit);
    }

    /**
     * Builder method used to set the {@link Street} of the {@link Address}.
     *
     * @param <S> concrete {@link Class subtype} of the {@link AbstractAddress.Builder}.
     * @param street {@link Street} of the {@link Address}; must not be {@literal null}.
     * @return this {@link AbstractAddress.Builder}.
     * @see org.cp.elements.lang.annotation.Dsl
     * @see org.cp.domain.geo.model.Street
     * @see Address#getStreet()
     */
    @Dsl
    @SuppressWarnings("unchecked")
    public @NotNull <S extends Builder<T>> S on(@NotNull Street street) {
      this.street = street;
      return (S) this;
    }

    /**
     * Builder method used to set the {@link Unit} on the {@link Street} of the {@link Address}.
     *
     * @param <S> concrete {@link Class subtype} of the {@link AbstractAddress.Builder}.
     * @param unit {@link Unit} on the {@link Street} of the {@link Address}.
     * @return this {@link AbstractAddress.Builder}.
     * @see org.cp.elements.lang.annotation.Dsl
     * @see org.cp.domain.geo.model.Unit
     * @see Address#getUnit()
     */
    @Dsl
    @SuppressWarnings("unchecked")
    public @NotNull <S extends Builder<T>> S in(@NotNull Unit unit) {
      this.unit = unit;
      return (S) this;
    }

    /**
     * Builder method used to set the {@link City} of the {@link Address}.
     *
     * @param <S> concrete {@link Class subtype} of the {@link AbstractAddress.Builder}.
     * @param city {@link City} of the {@link Address}; must not be {@literal null}.
     * @return this {@link AbstractAddress.Builder}.
     * @see org.cp.elements.lang.annotation.Dsl
     * @see org.cp.domain.geo.model.City
     * @see Address#getCity()
     */
    @Dsl
    @SuppressWarnings("unchecked")
    public @NotNull <S extends Builder<T>> S in(@NotNull City city) {
      this.city = city;
      return (S) this;
    }

    /**
     * Builder method used to set the {@link PostalCode} of the {@link Address}.
     *
     * @param <S> concrete {@link Class subtype} of the {@link AbstractAddress.Builder}.
     * @param postalCode {@link PostalCode} of the {@link Address}; must not be {@literal null}.
     * @return this {@link AbstractAddress.Builder}.
     * @see org.cp.elements.lang.annotation.Dsl
     * @see org.cp.domain.geo.model.PostalCode
     * @see Address#getPostalCode()
     */
    @Dsl
    @SuppressWarnings("unchecked")
    public @NotNull <S extends Builder<T>> S in(@NotNull PostalCode postalCode) {
      this.postalCode = postalCode;
      return (S) this;
    }

    /**
     * Builder method used to set the geographic {@link Coordinates} of the {@link Address}.
     *
     * @param <S> concrete {@link Class subtype} of the {@link AbstractAddress.Builder}.
     * @param coordinates {@link Coordinates} of the {@link Address}.
     * @return this {@link AbstractAddress.Builder}.
     * @see org.cp.elements.lang.annotation.Dsl
     * @see org.cp.domain.geo.model.Coordinates
     * @see Address#getCoordinates()
     */
    @Dsl
    @SuppressWarnings("unchecked")
    public @NotNull <S extends Builder<T>> S at(@Nullable Coordinates coordinates) {
      this.coordinates = coordinates;
      return (S) this;
    }

    @Override
    public final T build() {

      T address = doBuild();

      getUnit().ifPresent(address::setUnit);
      getCoordinates().ifPresent(address::setCoordinates);

      return address;
    }

    /**
     * Builder method to construct a concrete {@link AbstractAddress}.
     *
     * @return a new, concrete instance of {@link AbstractAddress}.
     */
    protected abstract T doBuild();

  }
}
