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
import org.cp.elements.lang.Assert;
import org.cp.elements.lang.Identifiable;
import org.cp.elements.lang.ObjectUtils;
import org.cp.elements.lang.StringUtils;
import org.cp.elements.lang.Verifiable;
import org.cp.elements.lang.Visitable;
import org.cp.elements.lang.Visitor;
import org.cp.elements.lang.annotation.Dsl;
import org.cp.elements.lang.annotation.FluentApi;
import org.cp.elements.lang.annotation.NotNull;
import org.cp.elements.lang.annotation.Nullable;
import org.cp.elements.util.ComparatorResultBuilder;

/**
 * Abstract Data Type (ADT) modeling a physical, postal address.
 *
 * This interface defines a universally portable address format that can be used around the world.
 *
 * This {@link Address} is also {@link Locatable} by geographic coordinates as defined on a map
 * as encoded or decoded by geocoding services.
 *
 * @author John Blum
 * @see java.io.Serializable
 * @see java.lang.Cloneable
 * @see java.lang.Comparable
 * @see java.util.Locale
 * @see org.cp.domain.geo.enums.Country
 * @see org.cp.domain.geo.model.AbstractAddress
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
 * @see org.cp.elements.lang.Verifiable
 * @see org.cp.elements.lang.Visitable
 * @see org.cp.elements.lang.annotation.FluentApi
 * @since 1.0.0
 */
@FluentApi
@SuppressWarnings("unused")
public interface Address extends Cloneable, Comparable<Address>, Identifiable<Long>, Locatable, Serializable,
    Verifiable<Address>, Visitable {

  /**
   * Factory method used to construct a new {@link Address} from an existing {@link Address}.
   *
   * @param address {@link Address} to copy; must not be {@literal null}.
   * @return a new {@link Address} copied from the existing {@link Address}.
   * @throws IllegalArgumentException if the given {@link Address} is {@literal null}.
   * @see #from(Street, City, PostalCode, Country)
   * @see org.cp.elements.lang.annotation.Dsl
   * @see org.cp.domain.geo.model.Address
   */
  @Dsl
  static @NotNull Address from(@NotNull Address address) {

    Assert.notNull(address, "Address to copy is required");

    return from(address.getStreet(), address.getCity(), address.getPostalCode(), address.getCountry());
  }

  /**
   * Factory method used to construct a new {@link Address} from the given, required {@link Street}, {@link City}
   * and {@link PostalCode}, defaulting the {@link Country} based on the {@link Locale}.
   *
   * @param street {@link Street} of the {@link Address}; must not be {@literal null}.
   * @param city {@link City} of the {@link Address}; must not be {@literal null}.
   * @param postalCode {@link PostalCode} of the {@link Address}; must not be {@literal null}.
   * @return a new {@link Address} constructed from the given, required {@link Street}, {@link City}
   * and {@link PostalCode}.
   * @throws IllegalArgumentException if {@link Street}, {@link City} or {@link PostalCode} are {@literal null}.
   * @see #from(Street, City, PostalCode, Country)
   * @see org.cp.domain.geo.model.Street
   * @see org.cp.domain.geo.model.City
   * @see org.cp.domain.geo.model.PostalCode
   * @see org.cp.domain.geo.enums.Country#localCountry()
   * @see org.cp.elements.lang.annotation.Dsl
   */
  @Dsl
  static @NotNull Address from(@NotNull Street street, @NotNull City city, @NotNull PostalCode postalCode) {
    return from(street, city, postalCode, Country.localCountry());
  }

  /**
   * Factory method used to construct a new {@link Address} from the given, minimally required components of
   * an {@link Address} that can locate the {@link Address} anywhere in the world.
   *
   * @param street {@link Street} of the {@link Address}; must not be {@literal null}.
   * @param city {@link City} of the {@link Address}; must not be {@literal null}.
   * @param postalCode {@link PostalCode} of the {@link Address}; must not be {@literal null}.
   * @param country {@link Country} of the {@link Address}; must not be {@literal null}.
   * @return a new {@link Address} constructed from the given, required {@link Street},
   * {@link City}, {@link PostalCode} and {@link Country}.
   * @throws IllegalArgumentException if {@link Street}, {@link City}, {@link PostalCode} or {@link Country}
   * are {@literal null}.
   * @see org.cp.domain.geo.model.Street
   * @see org.cp.domain.geo.model.City
   * @see org.cp.domain.geo.model.PostalCode
   * @see org.cp.domain.geo.enums.Country
   * @see org.cp.elements.lang.annotation.Dsl
   */
  @Dsl
  static @NotNull Address from(@NotNull Street street, @NotNull City city, @NotNull PostalCode postalCode,
      @NotNull Country country) {

    Assert.notNull(street, "Street is required");
    Assert.notNull(city, "City is required");
    Assert.notNull(postalCode, "Postal Code is required");
    Assert.notNull(country, "Country is required");

    return new Address() {

      @Override
      public @Nullable Long getId() {
        return null;
      }

      @Override
      public @NotNull Street getStreet() {
        return street;
      }

      @Override
      public @NotNull City getCity() {
        return city;
      }

      @Override
      public @NotNull PostalCode getPostalCode() {
        return postalCode;
      }

      @Override
      public @NotNull Country getCountry() {
        return country;
      }
    };
  }

  /**
   * Factory method used to construct a new {@link Builder} used to build and construct a new {@link Address}.
   *
   * @return a new {@link Builder} used to build and construct a new {@link Address}.
   * @see org.cp.domain.geo.model.Address.Builder
   * @see org.cp.elements.lang.annotation.Dsl
   */
  @Dsl
  static @NotNull Builder newBuilder() {
    return new Builder();
  }

  /**
   * Returns the {@link Street} of this {@link Address}.
   *
   * For example: {@literal 100 Main St.}
   *
   * @return the {@link Street} of this {@link Address}.
   * @see org.cp.domain.geo.model.Street
   */
  Street getStreet();

  /**
   * Returns an {@link Optional} {@link Unit} on the {@link Street} at this {@link Address}.
   *
   * The {@link Unit} may represent an {@literal apartment number}, an {@literal office number}
   * a {@literal suite number}, or any similar {@literal unit number}.
   *
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
   * Returns an {@link Optional} {@link Type type} to describe the use of this {@link Address},
   * such as {@link Type#BILLING}, {@link Type#HOME}, {@link Type#MAILING}, {@link Type#OFFICE},
   * and so on.
   *
   * Defaults to {@link Type#UNKNOWN}.
   *
   * @return an {@link Optional} {@link Type type} for this {@link Address}; defaults to {@link Type#UNKNOWN}.
   * @see org.cp.domain.geo.model.Address.Type
   * @see java.util.Optional
   */
  default Optional<Type> getType() {
    return Optional.of(Type.UNKNOWN);
  }

  /**
   * Sets the {@link Address} {@link Type type} describing the use of this {@link Address},
   * such as {@link Type#BILLING}, {@link Type#HOME}, {@link Type#MAILING}, {@link Type#OFFICE}, and so on.
   *
   * @param type {@link Type type} for this {@link Address}.
   * @throws UnsupportedOperationException by default.
   * @see org.cp.domain.geo.model.Address.Type
   */
  default void setType(Type type) {
    throw newUnsupportedOperationException("Setting the Address.Type for an Address of type [%s] is not supported",
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
    return Type.BILLING.equals(getType().orElse(null));
  }

  /**
   * Determines whether this {@link Address} is a {@link Type#HOME} {@link Address}.
   *
   * @return a boolean value indicating whether this {@link Address} is a {@link Type#HOME} {@link Address}.
   * @see org.cp.domain.geo.model.Address.Type#HOME
   * @see #getType()
   */
  default boolean isHome() {
    return Type.HOME.equals(getType().orElse(null));
  }

  /**
   * Determines whether this {@link Address} is a {@link Type#MAILING} {@link Address}.
   *
   * @return a boolean value indicating whether this {@link Address} is a {@link Type#MAILING} {@link Address}.
   * @see org.cp.domain.geo.model.Address.Type#MAILING
   * @see #getType()
   */
  default boolean isMailing() {
    return Type.MAILING.equals(getType().orElse(null));
  }

  /**
   * Determines whether this {@link Address} is an {@link Type#OFFICE} {@link Address}.
   *
   * @return a boolean value indicating whether this {@link Address} is an {@link Type#OFFICE} {@link Address}.
   * @see org.cp.domain.geo.model.Address.Type#OFFICE
   * @see #getType()
   */
  default boolean isOffice() {
    return Type.OFFICE.equals(getType().orElse(null));
  }

  /**
   * Determines whether this {@link Address} is a {@link Type#PO_BOX}.
   *
   * @return a boolean value indicating whether this {@link Address} is a {@link Type#PO_BOX}.
   * @see org.cp.domain.geo.model.Address.Type#PO_BOX
   * @see #getType()
   */
  default boolean isPoBox() {
    return Type.PO_BOX.equals(getType().orElse(null));
  }

  /**
   * Determines whether this {@link Address} is a {@link Type#WORK} {@link Address}.
   *
   * @return a boolean value indicating whether this {@link Address} is a {@link Type#WORK} {@link Address}.
   * @see org.cp.domain.geo.model.Address.Type#WORK
   * @see #getType()
   */
  default boolean isWork() {
    return Type.WORK.equals(getType().orElse(null));
  }

  /**
   * Accepts a {@link Visitor} visiting this {@link Address} in order to perform any type of data access operation
   * required by the application.
   *
   * @param visitor {@link Visitor} visiting this {@link Address};
   * must not be {@literal null}.
   * @see org.cp.elements.lang.Visitable#accept(Visitor)
   * @see org.cp.elements.lang.Visitor#visit(Visitable)
   */
  @Override
  default void accept(@NotNull Visitor visitor) {
    visitor.visit(this);
  }

  /**
   * Compares this {@link Address} to the given {@link Address} to determine relative ordering (sort) when used in
   * an ordered data structure.
   *
   * @param that {@link Address} to compare with this {@link Address}; must not be {@literal null}.
   * @return a {@link Integer} value indicating the order of this {@link Address} relative to
   * the given {@link Address}.
   * Returns a {@link Integer negative number} to indicate this {@link Address} comes before the given {@link Address}.
   * Returns a {@link Integer positive number} to indicate this {@link Address} comes after the given {@link Address}.
   * And, returns {@literal 0} if this {@link Address} is equal to the given {@link Address}.
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
   * Validates this {@link Address}.
   *
   * The {@link Address} is considered valid only if the {@link #getStreet() street}, {@link #getCity() city}
   * {@link #getPostalCode() postal code} and {@link #getCountry() country} are set, that is are not {@literal null}.
   *
   * @return this {@link Address}.
   * @throws IllegalStateException if {@link #getStreet() street}, {@link #getCity() city},
   * {@link #getPostalCode() postal code} or {@link #getCountry() country} are not set.
   * @see org.cp.elements.lang.Verifiable#validate()
   */
  @Override
  default @NotNull Address validate() {

    ObjectUtils.requireState(getStreet(), "Street is required");
    ObjectUtils.requireState(getCity(), "City is required");
    ObjectUtils.requireState(getPostalCode(), "Postal Code is required");
    ObjectUtils.requireState(getCountry(), "Country is required");

    return this;
  }

  /**
   * Builder method used to set the {@link Type} for this {@link Address}.
   *
   * The {@link Type} serves to identify the purpose or use for this {@link Address},
   * such as for {@link Type#MAILING} purposes.
   *
   * @param <T> concrete {@link Class subtype} of this {@link Address}.
   * @param type {@link Type} of this {@link Address}.
   * @return this {@link Address}.
   * @see org.cp.domain.geo.model.Address.Type
   * @see org.cp.elements.lang.annotation.Dsl
   * @see #setType(Type)
   */
  @Dsl
  @SuppressWarnings("unchecked")
  default @NotNull <T extends Address> T as(@Nullable Type type) {
    setType(type);
    return (T) this;
  }

  /**
   * Sets the {@link Address.Type type} of this {@link Address} to {@link Type#BILLING}.
   *
   * @param <T> concrete {@link Class subtype} of this {@link Address}.
   * @return this {@link Address}.
   * @see org.cp.domain.geo.model.Address.Type#BILLING
   * @see org.cp.elements.lang.annotation.Dsl
   * @see #as(Type)
   */
  @Dsl
  default @NotNull <T extends Address> T asBilling() {
    return as(Type.BILLING);
  }

  /**
   * Sets the {@link Address.Type type} of this {@link Address} to {@link Type#HOME}.
   *
   * @param <T> concrete {@link Class subtype} of this {@link Address}.
   * @return this {@link Address}.
   * @see org.cp.domain.geo.model.Address.Type#HOME
   * @see org.cp.elements.lang.annotation.Dsl
   * @see #as(Type)
   */
  @Dsl
  default @NotNull <T extends Address> T asHome() {
    return as(Type.HOME);
  }

  /**
   * Sets the {@link Address.Type type} of this {@link Address} to {@link Type#MAILING}.
   *
   * @param <T> concrete {@link Class subtype} of this {@link Address}.
   * @return this {@link Address}.
   * @see org.cp.domain.geo.model.Address.Type#MAILING
   * @see org.cp.elements.lang.annotation.Dsl
   * @see #as(Type)
   */
  @Dsl
  default @NotNull <T extends Address> T asMailing() {
    return as(Type.MAILING);
  }

  /**
   * Sets the {@link Address.Type type} of this {@link Address} to {@link Type#OFFICE}.
   *
   * @param <T> concrete {@link Class subtype} of this {@link Address}.
   * @return this {@link Address}.
   * @see org.cp.domain.geo.model.Address.Type#OFFICE
   * @see org.cp.elements.lang.annotation.Dsl
   * @see #as(Type)
   */
  @Dsl
  default @NotNull <T extends Address> T asOffice() {
    return as(Type.OFFICE);
  }

  /**
   * Sets the {@link Address.Type type} of this {@link Address} to {@link Type#PO_BOX}.
   *
   * @param <T> concrete {@link Class subtype} of this {@link Address}.
   * @return this {@link Address}.
   * @see org.cp.domain.geo.model.Address.Type#PO_BOX
   * @see org.cp.elements.lang.annotation.Dsl
   * @see #as(Type)
   */
  @Dsl
  default @NotNull <T extends Address> T asPoBox() {
    return as(Type.PO_BOX);
  }

  /**
   * Sets the {@link Address.Type type} of this {@link Address} to {@link Type#WORK}.
   *
   * @param <T> concrete {@link Class subtype} of this {@link Address}.
   * @return this {@link Address}.
   * @see org.cp.domain.geo.model.Address.Type#WORK
   * @see org.cp.elements.lang.annotation.Dsl
   * @see #as(Type)
   */
  @Dsl
  default @NotNull <T extends Address> T asWork() {
    return as(Type.WORK);
  }

  /**
   * An Elements {@link Builder} used to construct a new {@link Address} using a {@link FluentApi} and {@link Dsl}.
   *
   * @see org.cp.elements.lang.Builder
   */
  @FluentApi
  class Builder implements org.cp.elements.lang.Builder<Address> {

    private Street street;
    private City city;
    private PostalCode postalCode;
    private Country country;
    private Coordinates coordinates;

    /**
     * Builder method used to build and construct a new {@link Address} from an existing, required {@link Address}.
     *
     * @param address {@link Address} to copy; must not be {@literal null}.
     * @return this {@link Builder}.
     * @throws IllegalArgumentException if the given {@link Address} is {@literal null}.
     * @see org.cp.domain.geo.model.Address
     * @see #on(Street)
     * @see #in(City)
     * @see #in(PostalCode)
     * @see #in(Country)
     * @see #at(Coordinates)
     */
    public @NotNull Builder from(@NotNull Address address) {

      Assert.notNull(address, "Address to copy is required");

      return on(address.getStreet())
        .in(address.getCity())
        .in(address.getPostalCode())
        .in(address.getCountry())
        .at(address.getCoordinates().orElse(null));
    }

    /**
     * Builder method used to set the {@link Street} of the {@link Address}.
     *
     * @param street {@link Street} of the {@link Address}; must not be {@literal null}.
     * @return this {@link Builder}.
     * @see org.cp.elements.lang.annotation.Dsl
     * @see org.cp.domain.geo.model.Street
     * @see Address#getStreet()
     */
    @Dsl
    public @NotNull Builder on(@NotNull Street street) {
      this.street = street;
      return this;
    }

    /**
     * Builder method used to set the {@link City} of the {@link Address}.
     *
     * @param city {@link City} of the {@link Address}; must not be {@literal null}.
     * @return this {@link Builder}.
     * @see org.cp.elements.lang.annotation.Dsl
     * @see org.cp.domain.geo.model.City
     * @see Address#getCity()
     */
    @Dsl
    public @NotNull Builder in(@NotNull City city) {
      this.city = city;
      return this;
    }

    /**
     * Builder method used to set the {@link PostalCode} of the {@link Address}.
     *
     * @param postalCode {@link PostalCode} of the {@link Address}; must not be {@literal null}.
     * @return this {@link Builder}.
     * @see org.cp.elements.lang.annotation.Dsl
     * @see org.cp.domain.geo.model.PostalCode
     * @see Address#getPostalCode()
     */
    @Dsl
    public @NotNull Builder in(@NotNull PostalCode postalCode) {
      this.postalCode = postalCode;
      return this;
    }

    /**
     * Builder method used to set the {@link Country} of the {@link Address}.
     *
     * @param country {@link Country} of the {@link Address}; must not be {@literal null}.
     * @return this {@link Builder}.
     * @see org.cp.elements.lang.annotation.Dsl
     * @see org.cp.domain.geo.enums.Country
     * @see Address#getCountry()
     */
    @Dsl
    public @NotNull Builder in(@NotNull Country country) {
      this.country = country;
      return this;
    }

    /**
     * Builder method used to set the {@link Country} of the {@link Address} based on {@link Locale}.
     *
     * @return this {@link Builder}.
     * @see org.cp.elements.lang.annotation.Dsl
     * @see org.cp.domain.geo.enums.Country
     * @see Address#getCountry()
     * @see #in(Country)
     */
    @Dsl
    public @NotNull Builder inLocalCountry() {
      return in(Country.localCountry());
    }

    /**
     * Builder method used to set the geographic {@link Coordinates} of the {@link Address}.
     *
     * @param coordinates {@link Coordinates} of the {@link Address}.
     * @return this {@link Builder}.
     * @see org.cp.elements.lang.annotation.Dsl
     * @see org.cp.domain.geo.model.Coordinates
     * @see Address#getCoordinates()
     */
    @Dsl
    public @NotNull Builder at(@Nullable Coordinates coordinates) {
      this.coordinates = coordinates;
      return this;
    }

    private Optional<Coordinates> getCoordinates() {
      return Optional.ofNullable(this.coordinates);
    }

    private Optional<Country> getCountry() {
      return Optional.ofNullable(this.country);
    }

    /**
     * Builds a new {@link Address} from the components of an {@link Address}, minimally including, but not limited to,
     * the {@link Street}, {@link City}, {@link PostalCode} and {@link Country}.
     *
     * May also include the {@link Coordinates} at the {@link Address}.
     *
     * @return a new {@link Address}.
     * @throws IllegalArgumentException if the {@link Street}, {@link City}, {@link PostalCode} or {@link Country}
     * are {@literal null}.
     * @see #from(Street, City, PostalCode, Country)
     * @see org.cp.domain.geo.model.Address#from(Street, City, PostalCode, Country)
     * @see org.cp.domain.geo.model.Address#from(Street, City, PostalCode)
     */
    @Dsl
    @Override
    public @NotNull Address build() {

      Address address = getCountry()
        .map(country -> Address.from(this.street, this.city, this.postalCode, country))
        .orElseGet(() -> Address.from(this.street, this.city, this.postalCode));

      getCoordinates().ifPresent(address::setCoordinates);

      return address;
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
    WORK("WA", "Work"),
    UNKNOWN("??", "Unknown");

    /**
     * Factory method used to search for an appropriate {@link Address.Type} based on its {@link String abbreviation}.
     *
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
     * Returns a {@link String} representation for this {@link Address.Type}.
     *
     * @return a {@link String} describing this {@link Address.Type}.
     * @see #getDescription()
     */
    @Override
    public @NotNull String toString() {
      return getDescription();
    }
  }
}
