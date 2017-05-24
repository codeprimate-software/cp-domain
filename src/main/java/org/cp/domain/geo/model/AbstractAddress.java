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
import static org.cp.elements.lang.RuntimeExceptionsFactory.newIllegalArgumentException;

import java.util.Optional;

import org.cp.domain.geo.enums.Country;
import org.cp.domain.geo.model.usa.UnitedStatesAddress;
import org.cp.elements.lang.Assert;
import org.cp.elements.lang.NullSafe;
import org.cp.elements.lang.ObjectUtils;
import org.cp.elements.lang.annotation.Id;

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
   * Null-safe factory method used to construct a new instance of an {@link Address} sub-type
   * based on the {@link Country} of origin.
   *
   * @param <T> {@link Class} type of the new {@link Address}.  The specific {@link Address} sub-type
   * is determined by the {@link Country} of origin (e.g. {@link UnitedStatesAddress}).
   * @param country {@link Country} of origin for the new {@link Address}.
   * @return a new {@link Address} sub-type based on the {@link Country} of origin.
   */
  @NullSafe
  @SuppressWarnings("unchecked")
  public static <T extends AbstractAddress> T newAddress(Country country) {

    return Optional.ofNullable(country)
      .filter(Country.UNITED_STATES_OF_AMERICA::equals)
      .map(localCountry -> (T) newUnitedStatesAddress())
      .orElseGet(() -> {
        T address = (T) newGenericAddress();
        Optional.ofNullable(country).ifPresent(address::in);
        return address;
    });
  }

  /**
   * Factory method used to construct a new instance of {@link Address} initialized with
   * the given {@link String street}, {@link String city}, {@link PostalCode} and {@link Country}.
   *
   * @param <T> {@link Class} type of the new {@link Address}.  The specific {@link Address} sub-type
   * is determined by the {@link Country} of origin (e.g. {@link UnitedStatesAddress}.
   * @param street {@link Street} of the new {@link Address}.
   * @param city {@link City} of this new {@link Address}.
   * @param postalCode {@link PostalCode} of the new {@link Address}.
   * @param country {@link Country} of the new {@link Address}.
   * @return a new {@link Address} initialized with the given {@link String street}, {@link City},
   * {@link PostalCode} and {@link Country}.
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
   * @param <T> {@link Class} type of the new {@link Address}.  The specific {@link Address} sub-type
   * is determined by the {@link Country} of origin (e.g. {@link UnitedStatesAddress}).
   * @param address {@link Address} to copy; must not be {@literal null}.
   * @return a new {@link Address} copied from the given {@link Address}.
   * @throws IllegalArgumentException if the {@link Address} to copy, or the {@link Street}, {@link City}
   * or {@link PostalCode} are {@literal null}.
   * @see org.cp.domain.geo.model.Address
   * @see #newAddress(Country)
   * @see #on(Street)
   * @see #in(Unit)
   * @see #in(City)
   * @see #in(PostalCode)
   * @see #with(Coordinates)
   */
  public static <T extends AbstractAddress> T from(Address address) {

    Assert.notNull(address, "Address must not be null");

    return newAddress(address.getCountry()).as(address.getType().orElse(null))
      .on(address.getStreet())
      .in(address.getUnit().orElse(null))
      .in(address.getCity())
      .in(address.getPostalCode())
      .with(address.getCoordinates().orElse(null));
  }

  /**
   * Returns the identifier uniquely identifying this {@link Address}.
   *
   * @return the identifier uniquely identifying this {@link Address}.
   * @see org.cp.elements.lang.Identifiable
   */
  @Override
  public Long getId() {
    return this.id;
  }

  /**
   * Sets the identifier uniquely identifying this {@link Address}.
   *
   * @param id identifier uniquely identifying this {@link Address}.
   * @see org.cp.elements.lang.Identifiable
   */
  @Override
  public void setId(Long id) {
    this.id = id;
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
   * Sets the street (e.g. 100 Main St.) for this {@link Address}.
   *
   * @param street {@link String} containing street (e.g. 100 Main St.) for this {@link Address}.
   */
  @Override
  public void setStreet(Street street) {
    this.street = Optional.ofNullable(street)
      .orElseThrow(() -> newIllegalArgumentException("Street [%s] is required", street));
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
   * Sets the {@link Optional} {@link Unit} for this {@link Address}.  The {@link Unit} may represent
   * an apartment number, an office or a suite.  The default method implementation is a no-op.
   *
   * @param unit {@link Unit} for this {@link Address}; may be {@literal null}.
   * @see org.cp.domain.geo.model.Unit
   * @see java.util.Optional
   */
  @Override
  public void setUnit(Unit unit) {
    this.unit = Optional.ofNullable(unit).orElse(null);
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
   * Set the {@link City} for this {@link Address}.
   *
   * @param city {@link City} for this {@link Address}.
   * @see org.cp.domain.geo.model.City
   */
  @Override
  public void setCity(City city) {
    this.city = Optional.ofNullable(city)
      .orElseThrow(() -> newIllegalArgumentException("City [%s] is required", city));
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
   * Sets the {@link PostalCode} for this {@link Address}.
   *
   * @param postalCode {@link PostalCode} for this {@link Address}.
   * @see org.cp.domain.geo.model.PostalCode
   */
  @Override
  public void setPostalCode(PostalCode postalCode) {
    this.postalCode = Optional.ofNullable(postalCode)
      .orElseThrow(() -> newIllegalArgumentException("Postal Code is required"));
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
   * Sets the {@link Country} for this {@link Address}.
   *
   * @param country {@link Country} for this {@link Address}.
   * @see org.cp.domain.geo.enums.Country
   */
  @Override
  public void setCountry(Country country) {
    this.country = Optional.ofNullable(country)
      .orElseThrow(() -> newIllegalArgumentException("Country [%s] is required"));
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
    return Optional.of(Optional.ofNullable(this.type).orElse(Type.UNKNOWN));
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
   * Determines whether this {@link Address} is equal to the given {@link Object}.
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
      && ObjectUtils.equals(this.getCity(), that.getCity())
      && ObjectUtils.equals(this.getPostalCode(), that.getPostalCode())
      && ObjectUtils.equals(this.getCountry(), that.getCountry());
  }

  /**
   * Computes the hash code for this {@link Address}.
   *
   * @return the computed hash code for this {@link Address}.
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {

    int hashValue = 17;

    hashValue = 37 * hashValue + ObjectUtils.hashCode(this.getStreet());
    hashValue = 37 * hashValue + ObjectUtils.hashCode(this.getUnit());
    hashValue = 37 * hashValue + ObjectUtils.hashCode(this.getCity());
    hashValue = 37 * hashValue + ObjectUtils.hashCode(this.getPostalCode());
    hashValue = 37 * hashValue + ObjectUtils.hashCode(this.getCountry());

    return hashValue;
  }

  /**
   * Returns a {@link String} representation of this {@link Address}.
   *
   * @return a {@link String} describing the state of this {@link Address}.
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return String.format(
      "{ @type = %1$s, street = %2$s, unit = %3$s, city = %4$s, postalCode = %5$s, country = %6$s, type = %7$s }",
        getClass().getName(), getStreet(), getUnit(), getCity(), getPostalCode(), getCountry(), getType());
  }
}
