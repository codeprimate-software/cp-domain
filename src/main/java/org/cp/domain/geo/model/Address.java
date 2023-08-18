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

import static org.cp.elements.lang.RuntimeExceptionsFactory.newIllegalArgumentException;
import static org.cp.elements.lang.RuntimeExceptionsFactory.newUnsupportedOperationException;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Locale;
import java.util.Optional;

import org.cp.domain.geo.enums.Country;
import org.cp.domain.geo.util.GeoUtils;
import org.cp.elements.lang.Assert;
import org.cp.elements.lang.Identifiable;
import org.cp.elements.lang.Renderable;
import org.cp.elements.lang.StringUtils;
import org.cp.elements.lang.Visitable;
import org.cp.elements.lang.Visitor;
import org.cp.elements.lang.annotation.Dsl;
import org.cp.elements.lang.annotation.FluentApi;
import org.cp.elements.lang.annotation.NotNull;
import org.cp.elements.lang.annotation.NullSafe;
import org.cp.elements.lang.annotation.Nullable;
import org.cp.elements.util.ComparatorResultBuilder;

/**
 * Abstract Data Type (ADT) modeling a {@literal physical, postal address}.
 * <p>
 * This interface defines a universally portable address format that can be used anywhere around the world.
 * <p>
 * This {@link Address} is also {@link Locatable} by {@link Coordinates geographic coordinates} as defined on
 * a {@literal map} as encoded or decoded by geocoding services, and can have a {@link Elevation} at this location.
 *
 * @author John Blum
 * @see java.io.Serializable
 * @see java.lang.Cloneable
 * @see java.lang.Comparable
 * @see java.util.Locale
 * @see org.cp.domain.geo.enums.Country
 * @see org.cp.domain.geo.model.AbstractAddress
 * @see org.cp.domain.geo.model.Address.Builder
 * @see org.cp.domain.geo.model.Address.Type
 * @see org.cp.domain.geo.model.Addressable
 * @see org.cp.domain.geo.model.City
 * @see org.cp.domain.geo.model.Coordinates
 * @see org.cp.domain.geo.model.Elevation
 * @see org.cp.domain.geo.model.Locatable
 * @see org.cp.domain.geo.model.PostalCode
 * @see org.cp.domain.geo.model.Street
 * @see org.cp.domain.geo.model.Unit
 * @see org.cp.elements.lang.Identifiable
 * @see org.cp.elements.lang.Renderable
 * @see org.cp.elements.lang.Visitable
 * @see org.cp.elements.lang.annotation.FluentApi
 * @since 0.1.0
 */
@FluentApi
@SuppressWarnings("unused")
public interface Address extends Cloneable, Comparable<Address>, Identifiable<Long>, Locatable<Address>, Renderable,
    Serializable, Visitable {

  /**
   * Factory method used to construct a new {@link Address.Builder} to build a new {@link Address}
   * based in the {@link Country#localCountry() local country} determined by {@link Locale}.
   *
   * @param <T> concrete {@link Class type} of {@link Address} built by the {@link Address.Builder}.
   * @param <BUILDER> concrete {@link Class type} of {@link Address.Builder}.
   * @return a new {@link Address.Builder} used to construct and build a new {@link Address}
   * based in the {@link Country#localCountry() local country} determined by {@link Locale}.
   * @see org.cp.domain.geo.enums.Country#localCountry()
   * @see org.cp.domain.geo.model.Address.Builder
   * @see org.cp.elements.lang.annotation.Dsl
   * @see #builder(Country)
   */
  @Dsl
  static @NotNull <T extends Address, BUILDER extends Address.Builder<T>> BUILDER builder() {
    return builder(Country.localCountry());
  }

  /**
   * Factory method used to construct a new {@link Address.Builder} to build a new {@link Address}
   * based in the given {@link Country}.
   * <p>
   * If {@link Country} is {@literal null}, then {@link Country} defaults to {@link Country#localCountry()}
   * based on the {@link Locale}.
   *
   * @param <T> concrete {@link Class type} of {@link Address} built by the {@link Address.Builder}.
   * @param <BUILDER> concrete {@link Class type} of {@link Address.Builder}.
   * @param country {@link Country} of origin for the new {@link Address}.
   * @return a new {@link Address.Builder} used to construct and build a new {@link Address}
   * based in the given {@link Country}.
   * @see org.cp.domain.geo.model.AddressFactory#newAddressBuilder(Country)
   * @see org.cp.domain.geo.model.Address.Builder
   * @see org.cp.domain.geo.enums.Country
   * @see org.cp.elements.lang.annotation.Dsl
   */
  @Dsl @NullSafe
  static @NotNull <T extends Address, BUILDER extends Address.Builder<T>> BUILDER builder(@Nullable Country country) {
    return AddressFactory.<T>getInstance(country).newAddressBuilder(country);
  }

  /**
   * Factory method used to construct a new {@link Address} copied from an existing {@link Address}.
   *
   * @param address {@link Address} to copy; must not be {@literal null}.
   * @return a new {@link Address} copied from an existing {@link Address}.
   * @throws IllegalArgumentException if the given {@link Address} is {@literal null}.
   * @see #of(Street, City, PostalCode, Country)
   * @see org.cp.elements.lang.annotation.Dsl
   * @see org.cp.domain.geo.model.Address
   */
  @Dsl
  static @NotNull Address from(@NotNull Address address) {

    Address copy = Address.Builder.from(address).build();

    address.getType().ifPresent(copy::setType);

    return copy;
  }

  /**
   * Factory method used to construct a new {@link Address} from the given, required {@link Street}, {@link City}
   * and {@link PostalCode}, with a default {@link Country} based on {@link Locale}.
   *
   * @param <T> concrete {@link Class type} of {@link Address}.
   * @param street {@link Street} of the {@link Address}; must not be {@literal null}.
   * @param city {@link City} of the {@link Address}; must not be {@literal null}.
   * @param postalCode {@link PostalCode} of the {@link Address}; must not be {@literal null}.
   * @return a new {@link Address} constructed from the given, required {@link Street}, {@link City}
   * and {@link PostalCode}.
   * @throws IllegalArgumentException if {@link Street}, {@link City} or {@link PostalCode} are {@literal null}.
   * @see #of(Street, City, PostalCode, Country)
   * @see org.cp.domain.geo.enums.Country#localCountry()
   * @see org.cp.domain.geo.model.Address
   * @see org.cp.domain.geo.model.Street
   * @see org.cp.domain.geo.model.City
   * @see org.cp.domain.geo.model.PostalCode
   * @see org.cp.elements.lang.annotation.Dsl
   */
  @Dsl
  static @NotNull <T extends Address> T of(@NotNull Street street, @NotNull City city, @NotNull PostalCode postalCode) {
    return of(street, city, postalCode, Country.localCountry());
  }

  /**
   * Factory method used to construct a new {@link Address} from the given, minimally required components of
   * an {@link Address} locating the {@link Address} anywhere in the world.
   *
   * @param <T> concrete {@link Class type} of {@link Address}.
   * @param street {@link Street} of the {@link Address}; must not be {@literal null}.
   * @param city {@link City} of the {@link Address}; must not be {@literal null}.
   * @param postalCode {@link PostalCode} of the {@link Address}; must not be {@literal null}.
   * @param country {@link Country} of the {@link Address}; must not be {@literal null}.
   * @return a new {@link Address} constructed from the given, required {@link Street},
   * {@link City}, {@link PostalCode} and {@link Country}.
   * @throws IllegalArgumentException if {@link Street}, {@link City}, {@link PostalCode} or {@link Country}
   * are {@literal null}.
   * @see org.cp.domain.geo.model.Address
   * @see org.cp.domain.geo.model.Street
   * @see org.cp.domain.geo.model.City
   * @see org.cp.domain.geo.model.PostalCode
   * @see org.cp.domain.geo.enums.Country
   * @see org.cp.elements.lang.annotation.Dsl
   */
  @Dsl
  static @NotNull <T extends Address> T of(@NotNull Street street, @NotNull City city, @NotNull PostalCode postalCode,
      @Nullable Country country) {

    return Address.<T, Address.Builder<T>>builder(country)
      .on(street)
      .in(city)
      .in(postalCode)
      .build();
  }

  /**
   * Returns the {@link Street} of this {@link Address}.
   * <p>
   * For example: {@literal 100 Main St.}
   *
   * @return the {@link Street} of this {@link Address}.
   * @see org.cp.domain.geo.model.Street
   */
  Street getStreet();

  /**
   * Returns an {@link Optional} {@link Unit} on the {@link Street} at this {@link Address}.
   * <p>
   * The {@link Unit} may represent an {@literal apartment number}, an {@literal office number}
   * a {@literal suite number}, or any similar {@literal unit number}.
   * <p>
   * Defaults to {@link Optional#empty()}.
   *
   * @return an {@link Optional} {@link Unit} on the {@link Street} at this {@link Address}.
   * @see org.cp.domain.geo.model.Unit
   * @see java.util.Optional
   */
  default Optional<Unit> getUnit() {
    return Optional.empty();
  }

  /**
   * Sets the {@link Unit} on the given {@link Street} at this {@link Address}.
   *
   * @param unit {@link Unit} on the given {@link Street} at this {@link Address}.
   * @throws UnsupportedOperationException by default.
   * @see org.cp.domain.geo.model.Unit
   */
  default void setUnit(Unit unit) {
    throw newUnsupportedOperationException("Setting Unit for Address of type [%s] is not supported",
      getClass().getName());
  }

  /**
   * Returns the {@link City} of this {@link Address}.
   *
   * @return the {@link City} of this {@link Address}.
   * @see org.cp.domain.geo.model.City
   */
  City getCity();

  /**
   * Returns the {@link PostalCode} of this {@link Address}.
   *
   * @return the {@link PostalCode} of this {@link Address}.
   * @see org.cp.domain.geo.model.PostalCode
   */
  PostalCode getPostalCode();

  /**
   * Returns the {@link Country} of this {@link Address}.
   *
   * @return the {@link Country} of this {@link Address}.
   * @see org.cp.domain.geo.enums.Country
   */
  Country getCountry();

  /**
   * Returns an {@link Optional} {@link Type} to describe the use of this {@link Address},
   * such as for {@link Type#BILLING} or {@link Type#MAILING} purposes.
   * <p>
   * The {@link Address.Type} may also describe the type of physical location, such as
   * {@literal Type#HOME residential}, or a {@link Type#PO_BOX}, and so on.
   * <p>
   * Defaults to {@link Type#UNKNOWN}.
   *
   * @return an {@link Optional} {@link Type} for this {@link Address}; defaults to {@link Type#UNKNOWN}.
   * @see org.cp.domain.geo.model.Address.Type
   * @see java.util.Optional
   */
  default Optional<Type> getType() {
    return Optional.of(Type.UNKNOWN);
  }

  /**
   * Sets the {@link Address.Type} to describe the use or purpose of this {@link Address}.
   *
   * @param type {@link Type Address.type} for this {@link Address}.
   * @throws UnsupportedOperationException by default.
   * @see org.cp.domain.geo.model.Address.Type
   */
  default void setType(Type type) {
    throw newUnsupportedOperationException("Setting Address.Type for Address of type [%s] is not supported",
      getClass().getName());
  }

  /**
   * Determines whether this {@link Address} is a {@link Type#BILLING} {@link Address}.
   *
   * @return a boolean value indicating whether this {@link Address} is a {@link Type#BILLING} {@link Address}.
   * @see org.cp.domain.geo.model.Address.Type#BILLING
   * @see #getType()
   */
  default boolean isBilling() {
    return Address.Type.BILLING.equals(getType().orElse(null));
  }

  /**
   * Determines whether this {@link Address} is a {@link Type#HOME} {@link Address}.
   *
   * @return a boolean value indicating whether this {@link Address} is a {@link Type#HOME} {@link Address}.
   * @see org.cp.domain.geo.model.Address.Type#HOME
   * @see #getType()
   */
  default boolean isHome() {
    return Address.Type.HOME.equals(getType().orElse(null));
  }

  /**
   * Determines whether this {@link Address} is a {@link Type#MAILING} {@link Address}.
   *
   * @return a boolean value indicating whether this {@link Address} is a {@link Type#MAILING} {@link Address}.
   * @see org.cp.domain.geo.model.Address.Type#MAILING
   * @see #getType()
   */
  default boolean isMailing() {
    return Address.Type.MAILING.equals(getType().orElse(null));
  }

  /**
   * Determines whether this {@link Address} is a {@link Type#PO_BOX}.
   *
   * @return a boolean value indicating whether this {@link Address} is a {@link Type#PO_BOX}.
   * @see org.cp.domain.geo.model.Address.Type#PO_BOX
   * @see #getType()
   */
  default boolean isPoBox() {
    return Address.Type.PO_BOX.equals(getType().orElse(null));
  }

  /**
   * Builder method used to set the {@link Address.Type} for this {@link Address}.
   * <p>
   * The {@link Address.Type} serves to identify the purpose or use for this {@link Address},
   * such as for {@link Address.Type#MAILING} or {@link Address.Type#BILLING} purposes.
   *
   * @param <T> concrete {@link Class type} of this {@link Address}.
   * @param type {@link Type} to described the purpose or use for this {@link Address}.
   * @return this {@link Address}.
   * @see org.cp.domain.geo.model.Address.Type
   * @see org.cp.elements.lang.annotation.Dsl
   * @see #setType(Type)
   */
  @Dsl
  @SuppressWarnings("unchecked")
  default @NotNull <T extends Address> T as(@Nullable Address.Type type) {
    setType(type);
    return (T) this;
  }

  /**
   * Builder method used to set the {@link Address.Type type} for this {@link Address} to {@link Address.Type#BILLING}.
   *
   * @param <T> concrete {@link Class type} of this {@link Address}.
   * @return this {@link Address}.
   * @see org.cp.domain.geo.model.Address.Type#BILLING
   * @see org.cp.elements.lang.annotation.Dsl
   * @see #as(Type)
   */
  @Dsl
  default @NotNull <T extends Address> T asBilling() {
    return as(Address.Type.BILLING);
  }

  /**
   * Builder method used to set the {@link Address.Type type} for this {@link Address} to {@link Address.Type#HOME}.
   *
   * @param <T> concrete {@link Class type} of this {@link Address}.
   * @return this {@link Address}.
   * @see org.cp.domain.geo.model.Address.Type#HOME
   * @see org.cp.elements.lang.annotation.Dsl
   * @see #as(Type)
   */
  @Dsl
  default @NotNull <T extends Address> T asHome() {
    return as(Address.Type.HOME);
  }

  /**
   * Builder method used to set the {@link Address.Type type} for this {@link Address} to {@link Type#MAILING}.
   *
   * @param <T> concrete {@link Class type} of this {@link Address}.
   * @return this {@link Address}.
   * @see org.cp.domain.geo.model.Address.Type#MAILING
   * @see org.cp.elements.lang.annotation.Dsl
   * @see #as(Type)
   */
  @Dsl
  default @NotNull <T extends Address> T asMailing() {
    return as(Address.Type.MAILING);
  }

  /**
   * Builder method used to set the {@link Address.Type type} for this {@link Address} to {@link Type#PO_BOX}.
   *
   * @param <T> concrete {@link Class type} of this {@link Address}.
   * @return this {@link Address}.
   * @see org.cp.domain.geo.model.Address.Type#PO_BOX
   * @see org.cp.elements.lang.annotation.Dsl
   * @see #as(Type)
   */
  @Dsl
  default @NotNull <T extends Address> T asPoBox() {
    return as(Address.Type.PO_BOX);
  }

  /**
   * Accepts a {@link Visitor} visiting this {@link Address} to perform some data access operation required by
   * the application.
   *
   * @param visitor {@link Visitor} visiting this {@link Address}; must not be {@literal null}.
   * @see org.cp.elements.lang.Visitable#accept(Visitor)
   * @see org.cp.elements.lang.Visitor#visit(Visitable)
   */
  @Override
  default void accept(@NotNull Visitor visitor) {
    visitor.visit(this);
  }

  /**
   * Compares this {@link Address} to the given {@link Address} to determine relative ordering (sort order)
   * when stored in an ordered data structure.
   *
   * @param that {@link Address} to compare with this {@link Address}; must not be {@literal null}.
   * @return a {@link Integer value} indicating the relative ordering of this {@link Address}
   * to the given {@link Address}.
   * Returns a {@link Integer negative number} if this {@link Address} comes before the given {@link Address}.
   * Returns a {@link Integer positive number} if this {@link Address} comes after the given {@link Address}.
   * Returns {@literal 0} if this {@link Address} is equal to the given {@link Address}.
   * @see java.lang.Comparable#compareTo(Object)
   */
  @Override
  @SuppressWarnings({ "rawtypes", "unchecked" })
  default int compareTo(@NotNull Address that) {

    return ComparatorResultBuilder.<Comparable>create()
      .doCompare(this.getCountry().name(), that.getCountry().name())
      .doCompare(this.getCity(), that.getCity())
      .doCompare(this.getPostalCode(), that.getPostalCode())
      .doCompare(this.getStreet(), that.getStreet())
      .doCompare(this.getUnit().orElse(Unit.EMPTY), that.getUnit().orElse(Unit.EMPTY))
      .build();
  }

  /**
   * Elements {@link Builder} used to construct a new {@link Address} using a {@link FluentApi} and {@link Dsl}.
   *
   * @see org.cp.elements.lang.Builder
   */
  @FluentApi
  class Builder<T extends Address> implements org.cp.elements.lang.Builder<T> {

    /**
     * Factory method used to build and construct a new {@link Address} copied from an existing {@link Address}.
     *
     * @param <T> concrete {@link Class type} of {@link Address} to build.
     * @param address {@link Address} to copy; must not be {@literal null}.
     * @return a new {@link Address.Builder}.
     * @throws IllegalArgumentException if the given {@link Address} is {@literal null}.
     * @see org.cp.elements.lang.annotation.Dsl
     * @see org.cp.domain.geo.model.Address
     * @see #on(Street)
     * @see #in(City)
     * @see #in(PostalCode)
     * @see #in(Country)
     * @see #at(Coordinates)
     */
    @Dsl
    public static <T extends Address> @NotNull Builder<T> from(@NotNull Address address) {

      Assert.notNull(address, "Address to copy is required");

      return new Builder<T>()
        .on(address.getStreet())
        .in(address.getUnit().orElse(null))
        .in(address.getCity())
        .in(address.getPostalCode())
        .in(address.getCountry())
        .at(address.getCoordinates().orElse(null));
    }

    private Street street;
    private Unit unit;
    private City city;
    private PostalCode postalCode;
    private Country country;
    private Coordinates coordinates;

    /**
     * Get the configured {@link Street} for the {@link Address}.
     *
     * @return the configured {@link Street} for the {@link Address}.
     * @see org.cp.domain.geo.model.Street
     */
    protected @NotNull Street getStreet() {
      return this.street;
    }

    /**
     * Get the configured {@link City} for the {@link Address}.
     *
     * @return the configured {@link City} for the {@link Address}.
     * @see org.cp.domain.geo.model.City
     */
    protected @NotNull City getCity() {
      return this.city;
    }

    /**
     * Get the configured {@link PostalCode} for the {@link Address}.
     *
     * @return the configured {@link PostalCode} for the {@link Address}.
     * @see org.cp.domain.geo.model.PostalCode
     */
    protected @NotNull PostalCode getPostalCode() {
      return this.postalCode;
    }

    /**
     * Get the configured {@link Country} for the {@link Address}.
     *
     * @return the configured {@link Country} for the {@link Address}.
     * @see org.cp.domain.geo.enums.Country
     */
    protected @NotNull Country getCountry() {
      return GeoUtils.resolveCountry(this.country);
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
     * Gets the configured, {@link Optional} {@link Unit} on the {@link Street} of the {@link Address}.
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
     * @param <S> {@link Class concrete type} of {@link Address.Builder}.
     * @param street {@link Street} of the {@link Address}; must not be {@literal null}.
     * @return this {@link Address.Builder}.
     * @see org.cp.elements.lang.annotation.Dsl
     * @see org.cp.domain.geo.model.Street
     * @see Address#getStreet()
     */
    @Dsl
    @SuppressWarnings("unchecked")
    public @NotNull <S extends Address.Builder<T>> S on(@NotNull Street street) {
      this.street = street;
      return (S) this;
    }


    /**
     * Builder method used to set the {@link City} of the {@link Address}.
     *
     * @param <S> {@link Class concrete type} of {@link Address.Builder}.
     * @param city {@link City} of the {@link Address}; must not be {@literal null}.
     * @return this {@link Address.Builder}.
     * @see org.cp.elements.lang.annotation.Dsl
     * @see org.cp.domain.geo.model.City
     * @see Address#getCity()
     */
    @Dsl
    @SuppressWarnings("unchecked")
    public @NotNull <S extends Address.Builder<T>> S in(@NotNull City city) {
      this.city = city;
      return (S) this;
    }

    /**
     * Builder method used to set the {@link Country} of the {@link Address}.
     *
     * @param <S> {@link Class concrete type} of {@link Address.Builder}.
     * @param country {@link Country} of the {@link Address}; must not be {@literal null}.
     * @return this {@link Address.Builder}.
     * @see org.cp.elements.lang.annotation.Dsl
     * @see org.cp.domain.geo.enums.Country
     * @see Address#getCountry()
     */
    @Dsl
    @SuppressWarnings("unchecked")
    public @NotNull <S extends Address.Builder<T>> S in(@NotNull Country country) {
      this.country = country;
      return (S) this;
    }

    /**
     * Builder method used to set the {@link Country} of the {@link Address} to the {@link Country#localCountry()}
     * based on {@link Locale}.
     *
     * @param <S> {@link Class concrete type} of {@link Address.Builder}.
     * @return this {@link Address.Builder}.
     * @see org.cp.elements.lang.annotation.Dsl
     * @see org.cp.domain.geo.enums.Country
     * @see Address#getCountry()
     * @see #in(Country)
     */
    @Dsl
    public @NotNull <S extends Address.Builder<T>> S inLocalCountry() {
      return in(Country.localCountry());
    }

    /**
     * Builder method used to set the {@link PostalCode} of the {@link Address}.
     *
     * @param <S> {@link Class concrete type} of {@link Address.Builder}.
     * @param postalCode {@link PostalCode} of the {@link Address}; must not be {@literal null}.
     * @return this {@link Address.Builder}.
     * @see org.cp.elements.lang.annotation.Dsl
     * @see org.cp.domain.geo.model.PostalCode
     * @see Address#getPostalCode()
     */
    @Dsl
    @SuppressWarnings("unchecked")
    public @NotNull <S extends Address.Builder<T>> S in(@NotNull PostalCode postalCode) {
      this.postalCode = postalCode;
      return (S) this;
    }

    /**
     * Builder method used to set the {@link Unit} on the {@link Street} of the {@link Address}.
     *
     * @param <S> {@link Class concrete type} of {@link Address.Builder}.
     * @param unit {@link Unit} on the {@link Street} of the {@link Address}.
     * @return this {@link Address.Builder}.
     * @see org.cp.elements.lang.annotation.Dsl
     * @see org.cp.domain.geo.model.Unit
     * @see Address#getUnit()
     */
    @Dsl
    @SuppressWarnings("unchecked")
    public @NotNull <S extends Address.Builder<T>> S in(@NotNull Unit unit) {
      this.unit = unit;
      return (S) this;
    }

    /**
     * Builder method used to set the geographic {@link Coordinates} of the {@link Address}.
     *
     * @param <S> {@link Class concrete type} of {@link Address.Builder}.
     * @param coordinates {@link Coordinates} of the {@link Address}.
     * @return this {@link Address.Builder}.
     * @see org.cp.elements.lang.annotation.Dsl
     * @see org.cp.domain.geo.model.Coordinates
     * @see Address#getCoordinates()
     */
    @Dsl
    @SuppressWarnings("unchecked")
    public @NotNull <S extends Address.Builder<T>> S at(@Nullable Coordinates coordinates) {
      this.coordinates = coordinates;
      return (S) this;
    }

    /**
     * Builds a new {@link Address} of {@link Class type T} from the components of an {@link Address},
     * minimally including, but not limited to, the {@link Street}, {@link City}, {@link PostalCode}
     * and {@link Country}.
     * <p>
     * May also include the {@link Coordinates} at the {@link Address} and {@link Unit} on the {@link Street}.
     *
     * @return a new {@link Address}.
     * @throws IllegalArgumentException if the {@link Street}, {@link City}, {@link PostalCode} or {@link Country}
     * are {@literal null}.
     * @see org.cp.domain.geo.model.Address#of(Street, City, PostalCode, Country)
     * @see org.cp.domain.geo.model.Address#of(Street, City, PostalCode)
     */
    @Dsl @Override
    public @NotNull T build() {

      Assert.notNull(getStreet(), "Street is required");
      Assert.notNull(getCity(), "City is required");
      Assert.notNull(getPostalCode(), "PostalCode is required");
      Assert.notNull(getCountry(), "Country is required");

      T address = doBuild();

      getCoordinates().ifPresent(address::setCoordinates);
      getUnit().ifPresent(address::setUnit);

      return address;
    }

    protected T doBuild() {
      return AddressFactory.<T>getInstance(getCountry())
        .newAddress(getStreet(), getCity(), getPostalCode(), getCountry());
    }
  }

  /**
   * {@link Enum Enumeration} of {@link Address} types.
   *
   * @see java.lang.Enum
   */
  enum Type {

    BILLING("BA", "Billing"),
    HOME("HA", "Home"),
    MAILING("MA", "Mailing"),
    OFFICE("OA", "Office"),
    PO_BOX("PO", "Post Office Box"),
    RESIDENTIAL("RA", "Residential"),
    WORK("WA", "Work"),
    UNKNOWN("??", "Unknown");

    /**
     * Factory method used to search for an appropriate {@link Address.Type} based on its {@link String abbreviation}.
     * <p>
     * This method performs a case-insensitive, ignoring whitespace search.
     *
     * @param abbreviation {@link String} containing the {@literal abbreviation} used to identify
     * the desired {@link Address.Type}.
     * @return the {@link Address.Type} for the given {@link String abbreviation}.
     * @throws IllegalArgumentException if no {@link Address.Type} could be found
     * for the given {@link String abbreviation}.
     * @see #values()
     */
    public static @NotNull Address.Type from(@Nullable String abbreviation) {

      return Arrays.stream(values())
        .filter(type -> type.getAbbreviation().equalsIgnoreCase(StringUtils.trim(abbreviation)))
        .findFirst()
        .orElseThrow(() -> newIllegalArgumentException("Address.Type for abbreviation [%s] was not found",
          abbreviation));
    }

    private final String abbreviation;
    private final String description;

    Type(@NotNull String abbreviation, @NotNull String description) {

      this.abbreviation = StringUtils.requireText(abbreviation,
        "An abbreviation for this Address.Type is required");

      this.description = StringUtils.requireText(description,
        "A description of this Address.Type is required");
    }

    /**
     * Returns an {@link String abbreviation} for this {@link Address.Type}.
     *
     * @return an {@link String abbreviation} for this {@link Address.Type}.
     * @see #getDescription()
     */
    public @NotNull String getAbbreviation() {
      return this.abbreviation;
    }

    /**
     * Returns a {@link String description} of this {@link Address.Type}.
     *
     * @return a {@link String} describing this {@link Address.Type}.
     * @see #getAbbreviation()
     */
    public @NotNull String getDescription() {
      return this.description;
    }

    /**
     * Determines whether this {@link Address.Type} is a {@link #PO_BOX}.
     * <p>
     * @return a boolean value determining whether this {@link Address.Type} is a {@link #PO_BOX}.
     * @see org.cp.domain.geo.model.Address.Type#PO_BOX
     */
    public boolean isPoBox() {
      return PO_BOX.equals(this);
    }

    /**
     * Returns a {@link String} representation for this {@link Address.Type}.
     *
     * @return a {@link String} describing this {@link Address.Type}.
     * @see #getDescription()
     */
    @Override
    public @NotNull String toString() {
      return getDescription().concat(isPoBox() ? StringUtils.EMPTY_STRING: " Address");
    }
  }
}
