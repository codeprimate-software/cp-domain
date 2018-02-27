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

import static org.cp.domain.geo.model.generic.GenericAddress.newGenericAddress;
import static org.cp.domain.geo.model.usa.UnitedStatesAddress.newUnitedStatesAddress;

import java.util.Locale;
import java.util.Optional;

import org.cp.domain.geo.enums.Country;
import org.cp.domain.geo.model.usa.UnitedStatesAddress;
import org.cp.elements.lang.Assert;
import org.cp.elements.lang.ObjectUtils;
import org.cp.elements.lang.annotation.Id;
import org.cp.elements.lang.annotation.NullSafe;

/**
 * The {@link AbstractAddress} class is an abstract implementation of the {@link Address} interface
 * with support for setting and getting the {@link Street}, {@link Unit}, {@link City}, {@link PostalCode}
 * and {@link Country} of origin along with, {@link Optional Optionally} geographic {@link Coordinates}
 * of the postal address.
 *
 * This class also provides a fluent API using the Builder Software Design Pattern to construct a new instance
 * of {@link Address} beginning with the {@link #newAddress(Country)} method.  Additionally, this class provides
 * methods like {@link #on(Street)}, {@link #in(Unit)}, {@link #in(City)}, {@link #in(PostalCode)}
 * and {@link #in(Country)} along with several others to specify {@link Type} and {@link Coordinates}.
 *
 * The {@link Type} may also be set to indicate what kind of postal {@link Address} this object represents.
 *
 * Finally, geographic {@link Coordinates} may be specified for reverse geocoding purposes.
 *
 * @author John Blum
 * @see org.cp.domain.geo.enums.Continent
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
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public abstract class AbstractAddress implements Address {

  protected static final String ADDRESS_TO_STRING =
    "{ @type = %1$s, street = %2$s, unit = %3$s, city = %4$s, postal code = %5$s, country = %6$s, type = %7$s }";

  /**
   * Factory method used to construct a new instance of {@link AbstractAddress} based in the {@link Country}
   * determined by the current, default {@link Locale}.
   *
   * @param <T> {@link Class type} of the new {@link Address}.  The specific {@link Address} {@link Class type}
   * is based on the {@link Country} determined by the current, default {@link Locale}.
   * @return a new {@link Address} based in the {@link Country} determined by the current, default {@link Locale}.
   * @see org.cp.domain.geo.enums.Country#localCountry()
   * @see org.cp.domain.geo.model.AbstractAddress
   * @see #newAddress()
   */
  public static <T extends AbstractAddress> T newAddress() {
    return newAddress(Country.localCountry());
  }

  /**
   * Factory method used to construct a new instance of {@link AbstractAddress} initialized with
   * the given {@link Country} as a base of origin.
   *
   * The {@link Address} specific {@link Class sub-type} is based on the {@link Country} of origin.
   *
   * @param <T> {@link Class type} of the new {@link Address}.  The specific {@link Address} {@link Class type}
   * is determined from the {@link Country} of origin (e.g. {@link UnitedStatesAddress}).
   * @param country {@link Country} of origin for the new {@link Address}.
   * @return a new {@link Address} based in the given {@link Country}.
   * @see org.cp.domain.geo.model.usa.UnitedStatesAddress
   * @see org.cp.domain.geo.model.generic.GenericAddress
   * @see org.cp.domain.geo.model.AbstractAddress
   * @see org.cp.domain.geo.enums.Country
   */
  @NullSafe
  @SuppressWarnings("unchecked")
  public static <T extends AbstractAddress> T newAddress(Country country) {

    Optional<Country> optionalCountry = Optional.ofNullable(country);

    return optionalCountry
      .filter(Country.UNITED_STATES_OF_AMERICA::equals)
      .map(localCountry -> (T) newUnitedStatesAddress())
      .orElseGet(() -> {

        T address = (T) newGenericAddress();

        optionalCountry.ifPresent(address::in);

        return address;
    });
  }

  /**
   * Factory method used to construct a new instance of {@link AbstractAddress} initialized with
   * the given {@link Street}, {@link City}, {@link PostalCode} and {@link Country}.
   *
   * @param <T> {@link Class type} of the new {@link Address}.  The specific {@link Address} {@link Class type}
   * is determined from the {@link Country} of origin (e.g. {@link UnitedStatesAddress}.
   * @param street {@link Street} of the new {@link Address}.
   * @param city {@link City} of the new {@link Address}.
   * @param postalCode {@link PostalCode} of the new {@link Address}.
   * @param country {@link Country} of the new {@link Address}.
   * @return a new {@link Address} initialized with the given {@link Street}, {@link City}, {@link PostalCode}
   * and {@link Country}.
   * @throws IllegalArgumentException if {@link Street}, {@link City} or {@link PostalCode} are {@literal null}.
   * @see org.cp.domain.geo.enums.Country
   * @see org.cp.domain.geo.model.City
   * @see org.cp.domain.geo.model.PostalCode
   * @see org.cp.domain.geo.model.Street
   * @see #newAddress(Country)
   * @see #on(Street)
   * @see #in(City)
   * @see #in(PostalCode)
   */
  public static <T extends AbstractAddress> T of(Street street, City city, PostalCode postalCode, Country country) {
    return newAddress(country).on(street).in(city).in(postalCode);
  }

  /**
   * Factory method used to construct a new instance of {@link Address} copied from the given {@link Address}.
   *
   * @param <T> {@link Class type} of the new {@link Address}.  The specific {@link Address} {@link Class type}
   * is determined from the given {@link Address Address's} {@link Country} of origin
   * (e.g. {@link UnitedStatesAddress}).
   * @param address {@link Address} to copy; must not be {@literal null}.
   * @return a new {@link Address} copied from the given {@link Address}.
   * @throws IllegalArgumentException if the {@link Address} to copy, or {@link Street}, {@link City}
   * or {@link PostalCode} of the given {@link Address} are {@literal null}.
   * @see org.cp.domain.geo.model.Address
   * @see #newAddress(Country)
   * @see #as(Type)
   * @see #on(Street)
   * @see #in(Unit)
   * @see #in(City)
   * @see #in(PostalCode)
   * @see #with(Coordinates)
   */
  public static <T extends AbstractAddress> T from(Address address) {

    Assert.notNull(address, "Address is required");

    return newAddress(address.getCountry())
      .as(address.getType().orElse(null))
      .on(address.getStreet())
      .in(address.getUnit().orElse(null))
      .in(address.getCity())
      .in(address.getPostalCode())
      .with(address.getCoordinates().orElse(null));
  }

  private City city;

  private Coordinates coordinates;

  private Country country;

  @Id
  private Long id;

  private PostalCode postalCode;

  private Street street;

  private Type type;

  private Unit unit;

  /**
   * Returns the {@link Long identifier} uniquely identifying this {@link Address}.
   *
   * @return the {@link Long identifier} uniquely identifying this {@link Address}.
   * @see org.cp.elements.lang.Identifiable
   * @see java.lang.Long
   */
  @Override
  public Long getId() {
    return this.id;
  }

  /**
   * Sets the {@link Long identifier} uniquely identifying this {@link Address}.
   *
   * @param id {@link Long identifier} uniquely identifying this {@link Address}.
   * @see org.cp.elements.lang.Identifiable
   * @see java.lang.Long
   */
  @Override
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * Sets the street (e.g. 100 Main St.) for this {@link Address}.
   *
   * @param street {@link String} containing street (e.g. 100 Main St.) for this {@link Address}.
   */
  @Override
  public void setStreet(Street street) {

    Assert.notNull(street, "Street is required");

    this.street = street;
  }

  /**
   * Returns the street (e.g. 100 Main St.) for this {@link Address}.
   *
   * @return the street (e.g. 100 Main St.) for this {@link Address}.
   */
  @Override
  public Street getStreet() {
    return this.street;
  }

  /**
   * Sets the {@link Optional} {@link Unit} for this {@link Address}.  The {@link Unit} may represent
   * an apartment number, an office or a suite.  The default method implementation is a no-op.
   *
   * @param unit {@link Unit} for this {@link Address}; may be {@literal null}.
   * @see org.cp.domain.geo.model.Unit
   * @see java.util.Optional
   */
  @Override
  public void setUnit(Unit unit) {
    this.unit = unit;
  }

  /**
   * Returns an {@link Optional} {@link Unit} for this {@link Address}.  The {@link Unit} may represent
   * an apartment number, an office or a suite.
   *
   * @return the {@link Optional} {@link Unit} for this {@link Address}.
   * @see org.cp.domain.geo.model.Unit
   * @see java.util.Optional
   */
  @Override
  public Optional<Unit> getUnit() {
    return Optional.ofNullable(this.unit);
  }

  /**
   * Set the {@link City} for this {@link Address}.
   *
   * @param city {@link City} for this {@link Address}.
   * @see org.cp.domain.geo.model.City
   */
  @Override
  public void setCity(City city) {

    Assert.notNull(city, "City is required");

    this.city = city;
  }

  /**
   * Returns the {@link City} for this {@link Address}.
   *
   * @return the {@link City} for this {@link Address}.
   * @see org.cp.domain.geo.model.City
   */
  @Override
  public City getCity() {
    return this.city;
  }

  /**
   * Sets the {@link PostalCode} for this {@link Address}.
   *
   * @param postalCode {@link PostalCode} for this {@link Address}.
   * @see org.cp.domain.geo.model.PostalCode
   */
  @Override
  public void setPostalCode(PostalCode postalCode) {

    Assert.notNull(postalCode, "Postal Code is required");

    this.postalCode = postalCode;
  }

  /**
   * Returns the {@link PostalCode} for this {@link Address}.
   *
   * @return the {@link PostalCode} for this {@link Address}.
   * @see org.cp.domain.geo.model.PostalCode
   */
  @Override
  public PostalCode getPostalCode() {
    return this.postalCode;
  }

  /**
   * Sets the {@link Country} for this {@link Address}.
   *
   * @param country {@link Country} for this {@link Address}.
   * @see org.cp.domain.geo.enums.Country
   */
  @Override
  public void setCountry(Country country) {

    Assert.notNull(country, "Country is required");

    this.country = country;
  }

  /**
   * Returns the {@link Country} for this {@link Address}.
   *
   * @return the {@link Country} for this {@link Address}.
   * @see org.cp.domain.geo.enums.Country
   */
  @Override
  public Country getCountry() {
    return this.country;
  }

  /**
   * Sets the geographic {@link Coordinates} to associate with this object.
   *
   * @param coordinates geographic {@link Coordinates} to associate with this object.
   * @see org.cp.domain.geo.model.Coordinates
   */
  @Override
  public void setCoordinates(Coordinates coordinates) {
    this.coordinates = coordinates;
  }

  /**
   * Returns the geographic {@link Coordinates} associated with this object.
   *
   * @return the geographic {@link Coordinates} associated with this object.
   * @see org.cp.domain.geo.model.Coordinates
   */
  @Override
  public Optional<Coordinates> getCoordinates() {
    return Optional.ofNullable(this.coordinates);
  }

  /**
   * Sets {@link Address} {@link Type type}, such as {@link Type#HOME}, {@link Type#MAILING},
   * {@link Type#WORK}, and so on.  The default method implementation is a no-op.
   *
   * @param type {@link Type type} for this {@link Address}.
   * @see Type
   */
  @Override
  public void setType(Type type) {
    this.type = type;
  }

  /**
   * Returns the {@link Optional} {@link Type type} of this {@link Address}, such as {@link Type#HOME},
   * {@link Type#MAILING}, {@link Type#WORK}, and so on.
   *
   * The default is {@link Type#UNKNOWN}.
   *
   * @return the {@link Optional} {@link Type type} of this {@link Address};
   * defaults to {@link Type#UNKNOWN}.
   * @see Type
   * @see java.util.Optional
   */
  @Override
  public Optional<Type> getType() {
    return Optional.ofNullable(this.type);
  }

  /**
   * Clones this {@link Address}.
   *
   * @return a clone of this {@link Address}.
   * @see java.lang.Object#clone()
   * @see #from(Address)
   */
  @Override
  @SuppressWarnings("all")
  public Object clone() throws CloneNotSupportedException {
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
  public boolean equals(Object obj) {

    if (this == obj) {
      return true;
    }

    if (!(obj instanceof Address)) {
      return false;
    }

    Address that = (Address) obj;

    return ObjectUtils.equals(this.getStreet(), that.getStreet())
      && ObjectUtils.equals(this.getUnit(), that.getUnit())
      && ObjectUtils.equals(this.getPostalCode(), that.getPostalCode())
      && ObjectUtils.equals(this.getCity(), that.getCity())
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

    int hashValue = 17;

    hashValue = 37 * hashValue + ObjectUtils.hashCode(this.getStreet());
    hashValue = 37 * hashValue + ObjectUtils.hashCode(this.getUnit());
    hashValue = 37 * hashValue + ObjectUtils.hashCode(this.getPostalCode());
    hashValue = 37 * hashValue + ObjectUtils.hashCode(this.getCity());
    hashValue = 37 * hashValue + ObjectUtils.hashCode(this.getCountry());

    return hashValue;
  }

  /**
   * Returns a {@link String} representation of this {@link Address}.
   *
   * @return a {@link String} describing this {@link Address}.
   * @see java.lang.Object#toString()
   * @see java.lang.String
   */
  @Override
  public String toString() {

    return String.format(ADDRESS_TO_STRING,
      getClass().getName(), getStreet(), getUnit().orElse(null), getCity(), getPostalCode(), getCountry(),
        getType().orElse(null));
  }
}
