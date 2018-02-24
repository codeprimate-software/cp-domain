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
import static org.cp.elements.lang.RuntimeExceptionsFactory.newIllegalStateException;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Locale;
import java.util.Optional;

import org.cp.domain.geo.enums.Country;
import org.cp.elements.lang.Identifiable;
import org.cp.elements.lang.StringUtils;
import org.cp.elements.lang.Verifyable;
import org.cp.elements.lang.Visitable;
import org.cp.elements.lang.Visitor;
import org.cp.elements.util.ComparatorResultBuilder;

/**
 * The {@link Address} interface is an Abstract Data Type (ADT) defining a contract for a postal address.
 *
 * This interface models a universally accepted address format across the world.
 *
 * This {@link Address} is also {@link Locatable} by geographic coordinates on a map.
 *
 * @author John Blum
 * @see java.io.Serializable
 * @see java.lang.Cloneable
 * @see java.lang.Comparable
 * @see org.cp.domain.geo.enums.Country
 * @see org.cp.domain.geo.model.AbstractAddress
 * @see org.cp.domain.geo.model.Address.Type
 * @see org.cp.domain.geo.model.Addressable
 * @see org.cp.domain.geo.model.City
 * @see org.cp.domain.geo.model.Coordinates
 * @see org.cp.domain.geo.model.Locatable
 * @see org.cp.domain.geo.model.PostalCode
 * @see org.cp.domain.geo.model.Street
 * @see org.cp.domain.geo.model.Unit
 * @see org.cp.elements.lang.Identifiable
 * @see org.cp.elements.lang.Verifyable
 * @see org.cp.elements.lang.Visitable
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public interface Address extends Cloneable, Comparable<Address>, Serializable,
    Identifiable<Long>, Locatable, Verifyable<Address>, Visitable {

  /**
   * Returns the {@link Street} (e.g. 100 Main St.) for this {@link Address}.
   *
   * @return the {@link Street} (e.g. 100 Main St.) for this {@link Address}.
   * @see org.cp.domain.geo.model.Street
   */
  Street getStreet();

  /**
   * Sets the {@link Street} (e.g. 100 Main St.) for this {@link Address}.
   *
   * @param street {@link Street} (e.g. 100 Main St.) for this {@link Address}.
   * @see org.cp.domain.geo.model.Street
   */
  void setStreet(Street street);

  /**
   * Returns an {@link Optional} {@link Unit} for this {@link Address}.
   *
   * The {@link Unit} may represent an apartment number, an office or a suite number.
   *
   * Defaults to {@link Optional#empty()}.
   *
   * @return the {@link Optional} {@link Unit} for this {@link Address}.
   * @see org.cp.domain.geo.model.Unit
   * @see java.util.Optional
   */
  default Optional<Unit> getUnit() {
    return Optional.empty();
  }

  /**
   * Sets the {@link Optional} {@link Unit} for this {@link Address}.
   *
   * The {@link Unit} may represent an apartment number, an office or a suite number.
   *
   * The default method implementation is a no-op.
   *
   * @param unit {@link Unit} for this {@link Address}; may be {@literal null}.
   * @see org.cp.domain.geo.model.Unit
   */
  default void setUnit(Unit unit) {
  }

  /**
   * Returns the {@link City} for this {@link Address}.
   *
   * @return the {@link City} for this {@link Address}.
   * @see org.cp.domain.geo.model.City
   */
  City getCity();

  /**
   * Set the {@link City} for this {@link Address}.
   *
   * @param city {@link City} for this {@link Address}.
   * @see org.cp.domain.geo.model.City
   */
  void setCity(City city);

  /**
   * Returns the {@link PostalCode} for this {@link Address}.
   *
   * @return the {@link PostalCode} for this {@link Address}.
   * @see org.cp.domain.geo.model.PostalCode
   */
  PostalCode getPostalCode();

  /**
   * Sets the {@link PostalCode} for this {@link Address}.
   *
   * @param postalCode {@link PostalCode} for this {@link Address}.
   * @see org.cp.domain.geo.model.PostalCode
   */
  void setPostalCode(PostalCode postalCode);

  /**
   * Returns the {@link Country} for this {@link Address}.
   *
   * @return the {@link Country} for this {@link Address}.
   * @see org.cp.domain.geo.enums.Country
   */
  Country getCountry();

  /**
   * Sets the {@link Country} for this {@link Address}.
   *
   * @param country {@link Country} for this {@link Address}.
   * @see org.cp.domain.geo.enums.Country
   */
  void setCountry(Country country);

  /**
   * Returns an {@link Optional} {@link Type type} of this {@link Address}, such as {@link Type#BILLING},
   * {@link Type#HOME}, {@link Type#MAILING}, {@link Type#OFFICE}, and so on.
   *
   * Defaults to {@link Type#UNKNOWN}.
   *
   * @return the {@link Optional} {@link Type type} of this {@link Address}; defaults to {@link Type#UNKNOWN}.
   * @see org.cp.domain.geo.model.Address.Type
   * @see java.util.Optional
   */
  default Optional<Type> getType() {
    return Optional.of(Type.UNKNOWN);
  }

  /**
   * Sets the {@link Address} {@link Type type}, such as {@link Type#BILLING}, {@link Type#HOME}, {@link Type#MAILING},
   * {@link Type#OFFICE}, and so on.
   *
   * The default method implementation is a no-op.
   *
   * @param type {@link Type type} of this {@link Address}.
   * @see org.cp.domain.geo.model.Address.Type
   */
  default void setType(Type type) {
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
   * required by this application.
   *
   * @param visitor {@link Visitor} visiting this {@link Address}.
   * @see org.cp.elements.lang.Visitable#accept(Visitor)
   * @see org.cp.elements.lang.Visitor#visit(Visitable)
   */
  @Override
  default void accept(Visitor visitor) {
    visitor.visit(this);
  }

  /**
   * Compares this {@link Address} to the given {@link Address} in order to determine the relative ordering.
   *
   * @param address the {@link Address} to compare with this {@link Address}.
   * @return a {@link Integer value} indicating the sort order of this {@link Address}
   * relative to the given {@link Address}.
   * Returns a negative number to indicate this {@link Address} comes before the given {@link Address} in the order.
   * Returns a positive number to indicate this {@link Address} comes after the given {@link Address} in the order.
   * Returns {@literal 0} if this {@link Address} is equal to the given {@link Address}.
   * @see java.lang.Comparable#compareTo(Object)
   */
  @Override
  @SuppressWarnings("unchecked")
  default int compareTo(Address address) {

    return ComparatorResultBuilder.<Comparable>create()
      .doCompare(this.getCountry().name(), address.getCountry().name())
      .doCompare(this.getCity(), address.getCity())
      .doCompare(this.getPostalCode(), address.getPostalCode())
      .doCompare(this.getStreet(), address.getStreet())
      .doCompare(this.getUnit().orElse(Unit.EMPTY), address.getUnit().orElse(Unit.EMPTY))
      .build();
  }

  /**
   * Validates that this {@link Address} is valid.
   *
   * The {@link Address} is considered valid iff the {@link #getStreet() street}, {@link #getCity() city}
   * {@link #getPostalCode() postal code} and {@link #getCountry() country} are set, i.e. are not {@literal null}.
   *
   * @return this {@link Address}.
   * @throws IllegalStateException if {@link #getStreet() street}, {@link #getCity() city},
   * {@link #getPostalCode() postal code} or {@link #getCountry() country} are not set.
   * @see org.cp.elements.lang.Verifyable#validate()
   */
  @Override
  default Address validate() {

    Optional.ofNullable(getStreet()).orElseThrow(() -> newIllegalStateException("Street is required"));
    Optional.ofNullable(getCity()).orElseThrow(() -> newIllegalStateException("City is required"));
    Optional.ofNullable(getPostalCode()).orElseThrow(() -> newIllegalStateException("Postal Code is required"));
    Optional.ofNullable(getCountry()).orElseThrow(() -> newIllegalStateException("Country is required"));

    return this;
  }

  @SuppressWarnings("unchecked")
  default <T extends Address> T as(Type type) {
    setType(type);
    return (T) this;
  }

  /**
   * Sets the {@link Address.Type type} of this {@link Address} to {@link Type#BILLING}.
   *
   * @param <T> {@link Class sub-type} of this {@link Address}.
   * @return this {@link Address}.
   * @see org.cp.domain.geo.model.Address.Type#BILLING
   * @see #as(Type)
   */
  default <T extends Address> T asBilling() {
    return as(Type.BILLING);
  }

  /**
   * Sets the {@link Address.Type type} of this {@link Address} to {@link Type#HOME}.
   *
   * @param <T> {@link Class sub-type} of this {@link Address}.
   * @return this {@link Address}.
   * @see org.cp.domain.geo.model.Address.Type#HOME
   * @see #as(Type)
   */
  default <T extends Address> T asHome() {
    return as(Type.HOME);
  }

  /**
   * Sets the {@link Address.Type type} of this {@link Address} to {@link Type#MAILING}.
   *
   * @param <T> {@link Class sub-type} of this {@link Address}.
   * @return this {@link Address}.
   * @see org.cp.domain.geo.model.Address.Type#MAILING
   * @see #as(Type)
   */
  default <T extends Address> T asMailing() {
    return as(Type.MAILING);
  }

  /**
   * Sets the {@link Address.Type type} of this {@link Address} to {@link Type#OFFICE}.
   *
   * @param <T> {@link Class sub-type} of this {@link Address}.
   * @return this {@link Address}.
   * @see org.cp.domain.geo.model.Address.Type#OFFICE
   * @see #as(Type)
   */
  default <T extends Address> T asOffice() {
    return as(Type.OFFICE);
  }

  /**
   * Sets the {@link Address.Type type} of this {@link Address} to {@link Type#PO_BOX}.
   *
   * @param <T> {@link Class sub-type} of this {@link Address}.
   * @return this {@link Address}.
   * @see org.cp.domain.geo.model.Address.Type#PO_BOX
   * @see #as(Type)
   */
  default <T extends Address> T asPoBox() {
    return as(Type.PO_BOX);
  }

  /**
   * Sets the {@link Address.Type type} of this {@link Address} to {@link Type#WORK}.
   *
   * @param <T> {@link Class sub-type} of this {@link Address}.
   * @return this {@link Address}.
   * @see org.cp.domain.geo.model.Address.Type#WORK
   * @see #as(Type)
   */
  default <T extends Address> T asWork() {
    return as(Type.WORK);
  }

  /**
   * Builder method to set the {@link Street} of this {@link Address}.
   *
   * @param <T> {@link Class sub-type} of this {@link Address}.
   * @param street {@link Street} to set.
   * @return this {@link Address}
   * @see org.cp.domain.geo.model.Street
   * @see #setStreet(Street)
   */
  @SuppressWarnings("unchecked")
  default <T extends Address> T on(Street street) {
    setStreet(street);
    return (T) this;
  }

  /**
   * Builder method to set the {@link Unit} of this {@link Address}.
   *
   * @param <T> {@link Class sub-type} of this {@link Address}.
   * @param unit  {@link Unit} to set.
   * @return this {@link Address}
   * @see org.cp.domain.geo.model.Unit
   * @see #setUnit(Unit)
   */
  @SuppressWarnings("unchecked")
  default <T extends Address> T in(Unit unit) {
    setUnit(unit);
    return (T) this;
  }

  /**
   * Builder method to set the {@link City} of this {@link Address}.
   *
   * @param <T> {@link Class sub-type} of this {@link Address}.
   * @param city  {@link City} to set.
   * @return this {@link Address}
   * @see org.cp.domain.geo.model.City
   * @see #setCity(City)
   */
  @SuppressWarnings("unchecked")
  default <T extends Address> T in(City city) {
    setCity(city);
    return (T) this;
  }

  /**
   * Builder method to set the {@link PostalCode} of this {@link Address}.
   *
   * @param <T> {@link Class sub-type} of this {@link Address}.
   * @param postalCode {@link PostalCode} to set.
   * @return this {@link Address}
   * @see org.cp.domain.geo.model.PostalCode
   * @see #setPostalCode(PostalCode)
   */
  @SuppressWarnings("unchecked")
  default <T extends Address> T in(PostalCode postalCode) {
    setPostalCode(postalCode);
    return (T) this;
  }

  /**
   * Builder method to set the {@link Country} of this {@link Address}.
   *
   * @param <T> {@link Class sub-type} of this {@link Address}.
   * @param country {@link Country} to set.
   * @return this {@link Address}
   * @see org.cp.domain.geo.enums.Country
   * @see #setCountry(Country)
   */
  @SuppressWarnings("unchecked")
  default <T extends Address> T in(Country country) {
    setCountry(country);
    return (T) this;
  }

  /**
   * Builder method to set the {@link Country} of this {@link Address}
   * to this {@link Locale Locale's} {@link Country}.
   *
   * @param <T> {@link Class sub-type} of this {@link Address}.
   * @return this {@link Address}
   * @see org.cp.domain.geo.enums.Country#localCountry()
   * @see #in(Country)
   */
  default <T extends Address> T inLocalCountry() {
    return in(Country.localCountry());
  }

  /**
   * Builder method to set the {@link Coordinates} of this {@link Address}.
   *
   * @param <T> {@link Class sub-type} of this {@link Address}.
   * @param coordinates {@link Coordinates} to set.
   * @return this {@link Address}
   * @see org.cp.domain.geo.model.Coordinates
   * @see #setCoordinates(Coordinates)
   */
  @SuppressWarnings("unchecked")
  default <T extends Address> T with(Coordinates coordinates) {
    setCoordinates(coordinates);
    return (T) this;
  }

  /**
   * The {@link Type} enum is an enumeration of different {@link Address} types.
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
     * Factory method used to search for an appropriate {@link Address} {@link Type}
     * based on its {@link String abbreviation}.
     *
     * This method performs a case-insensitive, ignoring whitespace search.
     *
     * @param abbreviation {@link String} containing the abbreviation used to identify
     * the desired {@link Address} {@link Type}.
     * @return the {@link Address} {@link Type} for the given {@link String abbreviation}.
     * @throws IllegalArgumentException if no {@link Address} {@link Type} could be found
     * for the given {@link String abbreviation}
     */
    public static Type valueOfAbbreviation(String abbreviation) {

      return Arrays.stream(values())
        .filter(type -> type.getAbbreviation().equalsIgnoreCase(StringUtils.trim(abbreviation)))
        .findFirst().orElseThrow(() ->
          newIllegalArgumentException("Type for abbreviation [%s] was not found", abbreviation));
    }

    private final String abbreviation;
    private final String description;

    /* (non-Javadoc) */
    Type(String abbreviation, String description) {
      this.abbreviation = abbreviation;
      this.description = description;
    }

    /**
     * Returns an {@link String abbreviation} for this {@link Address} {@link Type}.
     *
     * @return an {@link String abbreviation} for this {@link Address} {@link Type}.
     */
    public String getAbbreviation() {
      return this.abbreviation;
    }

    /**
     * Returns a {@link String description} of this {@link Address} {@link Type}.
     *
     * @return a {@link String} describing this {@link Address} {@link Type}.
     */
    public String getDescription() {
      return this.description;
    }

    /**
     * Returns a {@link String} representation of this {@link Address} {@link Type}.
     *
     * @return a {@link String} describing this {@link Address} {@link Type}.
     * @see java.lang.Object#toString()
     * @see java.lang.String
     */
    @Override
    public String toString() {
      return getDescription();
    }
  }
}
