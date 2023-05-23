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
package org.cp.domain.contact.phone.model;

import static org.cp.elements.lang.RuntimeExceptionsFactory.newIllegalArgumentException;
import static org.cp.elements.lang.RuntimeExceptionsFactory.newUnsupportedOperationException;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Optional;

import org.cp.domain.geo.enums.Country;
import org.cp.elements.lang.Assert;
import org.cp.elements.lang.Identifiable;
import org.cp.elements.lang.ObjectUtils;
import org.cp.elements.lang.Renderable;
import org.cp.elements.lang.StringUtils;
import org.cp.elements.lang.Visitable;
import org.cp.elements.lang.Visitor;
import org.cp.elements.lang.annotation.Dsl;
import org.cp.elements.lang.annotation.FluentApi;
import org.cp.elements.lang.annotation.NotNull;
import org.cp.elements.lang.annotation.Nullable;
import org.cp.elements.util.ComparatorResultBuilder;

/**
 * Abstract Data Type (ADT) modeling a {@literal phone number}.
 *
 * @author John Blum
 * @see java.lang.Cloneable
 * @see java.lang.Comparable
 * @see java.io.Serializable
 * @see org.cp.domain.contact.phone.model.AreaCode
 * @see org.cp.domain.contact.phone.model.ExchangeCode
 * @see org.cp.domain.contact.phone.model.Extension
 * @see org.cp.domain.contact.phone.model.FourDigitNumber
 * @see org.cp.domain.geo.enums.Country
 * @see org.cp.elements.lang.Identifiable
 * @see org.cp.elements.lang.Renderable
 * @see org.cp.elements.lang.Visitable
 * @see org.cp.elements.lang.Visitor
 * @see org.cp.elements.lang.annotation.FluentApi
 * @see org.cp.elements.lang.annotation.Dsl
 * @since 0.1.0
 */
@FluentApi
@SuppressWarnings("unused")
public interface PhoneNumber extends Cloneable, Comparable<PhoneNumber>,
    Identifiable<Long>, Renderable, Serializable, Visitable {

  /**
   * Factory method used to construct a new instance of {@link PhoneNumber} copied from an existing,
   * required {@link PhoneNumber}.
   *
   * @param phoneNumber {@link PhoneNumber} to copy; must not be {@literal null}.
   * @return a new {@link PhoneNumber} copied from the existing, required {@link PhoneNumber}.
   * @throws IllegalArgumentException if the given {@link PhoneNumber} to copy is {@literal null}.
   * @see #of(AreaCode, ExchangeCode, FourDigitNumber)
   * @see PhoneNumber
   */
  static @NotNull PhoneNumber from(@NotNull PhoneNumber phoneNumber) {

    Assert.notNull(phoneNumber, "PhoneNumber to copy is required");

    PhoneNumber copy = of(phoneNumber.getAreaCode(), phoneNumber.getExchangeCode(), phoneNumber.getFourDigitNumber());

    phoneNumber.getExtension().ifPresent(copy::setExtension);
    phoneNumber.getType().ifPresent(copy::setType);

    return copy;
  }

  /**
   * Factory method used to construct a new instance of {@link PhoneNumber} initialized with the given,
   * required {@link AreaCode}, {@link  ExchangeCode} and {@link FourDigitNumber}.
   *
   * @param areaCode {@link AreaCode} of the new {@link PhoneNumber}; must not be {@literal null}.
   * @param exchangeCode {@link ExchangeCode} of the new {@link PhoneNumber}; must not be {@literal null}.
   * @param number {@link FourDigitNumber} of the new {@link PhoneNumber}; must not be {@literal null}.
   * @return a new {@link PhoneNumber} initialized from the given, required {@link AreaCode},
   * {@link  ExchangeCode} and {@link FourDigitNumber}.
   * @throws IllegalArgumentException if the given {@link AreaCode}, {@link ExchangeCode} or {@link FourDigitNumber}
   * are {@literal null}.
   * @see org.cp.domain.contact.phone.model.AreaCode
   * @see org.cp.domain.contact.phone.model.ExchangeCode
   * @see org.cp.domain.contact.phone.model.FourDigitNumber
   */
  static @NotNull PhoneNumber of(@NotNull AreaCode areaCode, @NotNull ExchangeCode exchangeCode,
      @NotNull FourDigitNumber number) {

    Assert.notNull(areaCode, "AreaCode [%s] is required", areaCode);
    Assert.notNull(exchangeCode, "ExchangeCode [%s] is required", exchangeCode);
    Assert.notNull(number, "FourDigitNumber [%s] is required", number);

    return new PhoneNumber() {

      private Long id;

      @Override
      public @NotNull AreaCode getAreaCode() {
        return areaCode;
      }

      @Override
      public @NotNull ExchangeCode getExchangeCode() {
        return exchangeCode;
      }

      @Override
      public @NotNull FourDigitNumber getFourDigitNumber() {
        return number;
      }

      @Override
      public @Nullable Long getId() {
        return this.id;
      }

      @Override
      public void setId(@Nullable Long id) {
        this.id = id;
      }
    };
  }

  /**
   * Determines whether the use of this {@link PhoneNumber} would occur outside the {@link Country} of origin.
   *
   * @return a boolean value indicating whether the use of this {@link PhoneNumber} would occur outside
   * the {@link Country} of origin.
   * @see org.cp.domain.geo.enums.Country
   * @see #getCountry()
   */
  default boolean isRoaming() {

    return getCountry()
      .filter(Country.localCountry()::equals)
      .isPresent();
  }

  /**
   * Gets the {@link String three-digit} {@link AreaCode} of this {@link PhoneNumber}.
   *
   * @return the {@link String three-digit} {@link AreaCode} of this {@link PhoneNumber}.
   * @see org.cp.domain.contact.phone.model.AreaCode
   */
  AreaCode getAreaCode();

  /**
   * Gets the {@link String three-digit} {@link ExchangeCode} of this {@link PhoneNumber}.
   *
   * @return the {@link String three-digit} {@link ExchangeCode} of this {@link PhoneNumber}.
   * @see org.cp.domain.contact.phone.model.ExchangeCode
   */
  ExchangeCode getExchangeCode();

  /**
   * Gets the {@link FourDigitNumber} of this {@link PhoneNumber}.
   *
   * @return the {@link FourDigitNumber} of this {@link PhoneNumber}.
   * @see org.cp.domain.contact.phone.model.FourDigitNumber
   */
  FourDigitNumber getFourDigitNumber();

  /**
   * Gets an {@link Optional} {@link Country} in which this {@link PhoneNumber} resides.
   *
   * Returns {@link Optional#empty()} by default.
   *
   * @return an {@link Optional} {@link Country} in which this {@link PhoneNumber} resides.
   * @see org.cp.domain.geo.enums.Country
   * @see java.util.Optional
   */
  default Optional<Country> getCountry() {
    return Optional.empty();
  }

  /**
   * Gets an {@link Optional} {@link String extension} at this {@link PhoneNumber}.
   *
   * Returns {@link Optional#empty()} by default.
   *
   * @return an {@link Optional} {@link String extension} at this {@link PhoneNumber}.
   * @see org.cp.domain.contact.phone.model.Extension
   * @see java.util.Optional
   */
  default Optional<Extension> getExtension() {
    return Optional.empty();
  }

  /**
   * Sets the {@link String extension} of this {@link PhoneNumber}.
   *
   * @param extension {@link String extension} of this {@link PhoneNumber}.
   * @throws UnsupportedOperationException by default.
   * @see org.cp.domain.contact.phone.model.Extension
   */
  default void setExtension(@Nullable Extension extension) {
    throw newUnsupportedOperationException("Setting Extension for a PhoneNumber of type [%s] is not supported",
      getClass().getName());
  }

  /**
   * Get an {@link Optional} {@link PhoneNumber.Type} of this {@link PhoneNumber}.
   *
   * Returns {@link Optional#empty()} by default.
   *
   * @return the {@link PhoneNumber.Type} of this {@link PhoneNumber}.
   * @see org.cp.domain.contact.phone.model.PhoneNumber.Type
   * @see java.util.Optional
   */
  default Optional<PhoneNumber.Type> getType() {
    return Optional.empty();
  }

  /**
   * Sets the {@link PhoneNumber.Type} of this {@link PhoneNumber}.
   *
   * @param phoneNumberType {@link PhoneNumber.Type} for this {@link PhoneNumber}.
   * @throws UnsupportedOperationException by default.
   * @see org.cp.domain.contact.phone.model.PhoneNumber.Type
   */
  default void setType(@Nullable PhoneNumber.Type phoneNumberType) {
    throw newUnsupportedOperationException("Setting PhoneNumber.Type for a PhoneNumber of type [%s] is not supported",
      getClass().getName());
  }

  /**
   * Builder method used to set this {@link PhoneNumber} to the given {@link PhoneNumber.Type}.
   *
   * @param phoneNumberType {@link PhoneNumber.Type} of this {@link PhoneNumber}.
   * @return this {@link PhoneNumber}.
   * @see org.cp.domain.contact.phone.model.PhoneNumber.Type
   * @see #setType(PhoneNumber.Type)
   */
  default @NotNull PhoneNumber asType(@Nullable PhoneNumber.Type phoneNumberType) {
    setType(phoneNumberType);
    return this;
  }

  /**
   * Builder method used to set the {@link PhoneNumber.Type} of this {@link PhoneNumber}
   * to {@link PhoneNumber.Type#HOME}.
   *
   * @return this {@link PhoneNumber}.
   * @see org.cp.domain.contact.phone.model.PhoneNumber.Type#HOME
   * @see #asType(PhoneNumber.Type)
   */
  default @NotNull PhoneNumber asHome() {
    return asType(PhoneNumber.Type.HOME);
  }

  /**
   * Builder method used to set the {@link PhoneNumber.Type} of this {@link PhoneNumber}
   * to {@link PhoneNumber.Type#CELL}.
   *
   * @return this {@link PhoneNumber}.
   * @see org.cp.domain.contact.phone.model.PhoneNumber.Type#CELL
   * @see #asType(PhoneNumber.Type)
   */
  @Dsl
  default @NotNull PhoneNumber asCell() {
    return asType(PhoneNumber.Type.CELL);
  }

  /**
   * Builder method used to set the {@link PhoneNumber.Type} of this {@link PhoneNumber}
   * to {@link PhoneNumber.Type#VOIP}.
   *
   * @return this {@link PhoneNumber}.
   * @see org.cp.domain.contact.phone.model.PhoneNumber.Type#VOIP
   * @see #asType(PhoneNumber.Type)
   */
  @Dsl
  default @NotNull PhoneNumber asVOIP() {
    return asType(PhoneNumber.Type.VOIP);
  }

  /**
   * Builder method used to set the {@link PhoneNumber.Type} of this {@link PhoneNumber}
   * to {@link PhoneNumber.Type#WORK}.
   *
   * @return this {@link PhoneNumber}.
   * @see org.cp.domain.contact.phone.model.PhoneNumber.Type#WORK
   * @see #asType(PhoneNumber.Type)
   */
  @Dsl
  default @NotNull PhoneNumber asWork() {
    return asType(PhoneNumber.Type.WORK);
  }

  /**
   * Determines whether this is a {@link PhoneNumber.Type#HOME} {@link PhoneNumber}.
   *
   * @return a boolean value determining whether this is a {@link PhoneNumber.Type#HOME} {@link PhoneNumber}.
   * @see org.cp.domain.contact.phone.model.PhoneNumber.Type#HOME
   * @see #getType()
   */
  default boolean isHome() {
    return PhoneNumber.Type.HOME.equals(getType().orElse(null));
  }

  /**
   * Determines whether this is a {@link PhoneNumber.Type#CELL} {@link PhoneNumber}.
   *
   * @return a boolean value determining whether this is a {@link PhoneNumber.Type#CELL} {@link PhoneNumber}.
   * @see org.cp.domain.contact.phone.model.PhoneNumber.Type#CELL
   * @see #getType()
   */
  default boolean isCell() {
    return PhoneNumber.Type.CELL.equals(getType().orElse(null));
  }

  /**
   * Determines whether this is an {@link PhoneNumber.Type#VOIP} {@link PhoneNumber}.
   *
   * @return a boolean value determining whether this is an {@link PhoneNumber.Type#VOIP} {@link PhoneNumber}.
   * @see org.cp.domain.contact.phone.model.PhoneNumber.Type#VOIP
   * @see #getType()
   */
  default boolean isVOIP() {
    return PhoneNumber.Type.VOIP.equals(getType().orElse(null));
  }

  /**
   * Determines whether this is an {@link PhoneNumber.Type#UNKNOWN} {@link PhoneNumber}.
   *
   * @return a boolean value determining whether this is an {@link PhoneNumber.Type#UNKNOWN} {@link PhoneNumber}.
   * @see org.cp.domain.contact.phone.model.PhoneNumber.Type#UNKNOWN
   * @see #getType()
   */
  default boolean isUnknown() {
    return PhoneNumber.Type.UNKNOWN.equals(getType().orElse(null));
  }

  /**
   * Determines whether this is a {@link PhoneNumber.Type#WORK} {@link PhoneNumber}.
   *
   * @return a boolean value determining whether this is a {@link PhoneNumber.Type#WORK} {@link PhoneNumber}.
   * @see org.cp.domain.contact.phone.model.PhoneNumber.Type#WORK
   * @see #getType()
   */
  default boolean isWork() {
    return PhoneNumber.Type.WORK.equals(getType().orElse(null));
  }

  /**
   * Visits this {@link PhoneNumber}.
   *
   * @param visitor {@link Visitor} used to {@link Visitor#visit(Visitable)} this {@link PhoneNumber};
   * must not be {@literal null}.
   * @see org.cp.elements.lang.Visitor
   */
  @Override
  default void accept(@NotNull Visitor visitor) {
    visitor.visit(this);
  }

  /**
   * Compares this {@link PhoneNumber} to the give, required {@link PhoneNumber} for relative order (sorting).
   *
   * @param that {@link PhoneNumber} being compared for order with this {@link PhoneNumber};
   * must not be {@literal null}.
   * @return an {@link Integer value} indicating the relative order (sort) of this {@link PhoneNumber}
   * with the given, required {@link PhoneNumber}.
   */
  @Override
  @SuppressWarnings({ "rawtypes", "unchecked" })
  default int compareTo(@NotNull PhoneNumber that) {

    return ComparatorResultBuilder.<Comparable>create()
      .doCompare(this.getAreaCode(), that.getAreaCode())
      .doCompare(this.getExchangeCode(), that.getExchangeCode())
      .doCompare(this.getFourDigitNumber(), that.getFourDigitNumber())
      .doCompare(this.getExtension().orElse(null), that.getExtension().orElse(null))
      .build();
  }

  /**
   * Builder method used to set the {@link Extension} of this {@link PhoneNumber}.
   *
   * @param extension {@link Extension} of this {@link PhoneNumber}.
   * @return this {@link PhoneNumber}.
   * @see org.cp.domain.contact.phone.model.Extension
   */
  default PhoneNumber withExtension(@Nullable Extension extension) {
    setExtension(extension);
    return this;
  }

  /**
   * Elements {@link Builder} used to construct and build a new {@link PhoneNumber}.
   *
   * @see org.cp.domain.contact.phone.model.PhoneNumber
   * @see org.cp.elements.lang.Builder
   */
  @FluentApi
  class Builder implements org.cp.elements.lang.Builder<PhoneNumber> {

    private AreaCode areaCode;
    private Country country;
    private ExchangeCode exchangeCode;
    private Extension extension;
    private FourDigitNumber number;

    /**
     * Builds a new {@link PhoneNumber} from an existing, required {@link PhoneNumber}.
     *
     * @param phoneNumber {@link PhoneNumber} to copy; must not be {@literal null}.
     * @return this {@link Builder}.
     * @throws IllegalArgumentException if the given {@link PhoneNumber} to copy is {@literal null}.
     */
    public @NotNull Builder from(@NotNull PhoneNumber phoneNumber) {

      Assert.notNull(phoneNumber, "PhoneNumber to copy is required");

      return inAreaCode(phoneNumber.getAreaCode())
        .inCountry(phoneNumber.getCountry().orElseGet(Country::localCountry))
        .with(phoneNumber.getExchangeCode())
        .with(phoneNumber.getExtension().orElse(null))
        .with(phoneNumber.getFourDigitNumber());
    }

    /**
     * Builder method used to set the given, required {@link AreaCode} of the new {@link PhoneNumber}.
     *
     * @param areaCode {@link AreaCode} of the new {@link PhoneNumber}; must not be {@literal null}.
     * @return this {@link Builder}.
     * @throws IllegalArgumentException if the given {@link AreaCode} is {@literal null}.
     * @see org.cp.domain.contact.phone.model.AreaCode
     */
    @Dsl
    public @NotNull Builder inAreaCode(@NotNull AreaCode areaCode) {
      this.areaCode = ObjectUtils.requireObject(areaCode, "AreaCode is required");
      return this;
    }

    /**
     * Builder method used to set an optional {@link Country} of the new {@link PhoneNumber}.
     *
     * @param country {@link Country} of the new {@link PhoneNumber}.
     * @return this {@link Builder}.
     * @see org.cp.domain.geo.enums.Country
     */
    @Dsl
    public @NotNull Builder inCountry(@Nullable Country country) {
      this.country = country;
      return this;
    }

    /**
     * Builder method used to set the new {@link PhoneNumber} in the {@link Country#localCountry()}.
     *
     * @return this {@link Builder}.
     * @see org.cp.domain.geo.enums.Country#localCountry()
     */
    @Dsl
    public @NotNull Builder inLocalCountry() {
      return inCountry(Country.localCountry());
    }

    /**
     * Builder method used to set the given, required {@link ExchangeCode} of the new {@link PhoneNumber}.
     *
     * @param exchangeCode {@link ExchangeCode} of the new {@link PhoneNumber}; must not be {@literal null}.
     * @return this {@link Builder}.
     * @throws IllegalArgumentException if the given {@link ExchangeCode} is {@literal null}.
     * @see org.cp.domain.contact.phone.model.ExchangeCode
     */
    @Dsl
    public @NotNull Builder with(@NotNull ExchangeCode exchangeCode) {
      this.exchangeCode = ObjectUtils.requireObject(exchangeCode, "ExchangeCode is required");
      return this;
    }

    /**
     * Builder method used to set an optional {@link Extension} of the new {@link PhoneNumber}.
     *
     * @param extension {@link Extension} of the new {@link PhoneNumber}.
     * @return this {@link Builder}.
     * @see org.cp.domain.contact.phone.model.Extension
     */
    @Dsl
    public @NotNull Builder with(@Nullable Extension extension) {
      this.extension = extension;
      return this;
    }

    /**
     * Builder method used to set the given, required {@link FourDigitNumber} of the new {@link PhoneNumber}.
     *
     * @param number {@link FourDigitNumber} of the new {@link PhoneNumber}; must not be {@literal null}.
     * @return this {@link Builder}.
     * @throws IllegalArgumentException if the given {@link FourDigitNumber} is {@literal null}.
     * @see org.cp.domain.contact.phone.model.FourDigitNumber
     */
    @Dsl
    public @NotNull Builder with(@NotNull FourDigitNumber number) {
      this.number = ObjectUtils.requireObject(number, "FourDigitNumber is required");
      return this;
    }

    /**
     * Builds a new {@link PhoneNumber}.
     *
     * @return a new {@link PhoneNumber}.
     * @see org.cp.domain.contact.phone.model.PhoneNumber
     */
    @Override
    public @NotNull PhoneNumber build() {

      return new PhoneNumber() {

        @Override
        public @NotNull AreaCode getAreaCode() {
          return Builder.this.areaCode;
        }

        @Override
        public Optional<Country> getCountry() {
          return Optional.ofNullable(Builder.this.country);
        }

        @Override
        public @NotNull ExchangeCode getExchangeCode() {
          return Builder.this.exchangeCode;
        }

        @Override
        public Optional<Extension> getExtension() {
          return Optional.ofNullable(Builder.this.extension);
        }

        @Override
        public @NotNull FourDigitNumber getFourDigitNumber() {
          return Builder.this.number;
        }

        @Override
        public @Nullable Long getId() {
          return null;
        }
      };
    }
  }

  /**
   * {@link Enum Enumeration} of {@link PhoneNumber} types.
   *
   * @see java.lang.Enum
   */
  enum Type {

    HOME("HM", "Home"),
    CELL("CLL", "Cellular"),
    VOIP("VOIP", "Voice-Over-IP"),
    WORK("WRK", "Work"),
    UNKNOWN("??", "Unknown");

    /**
     * Factory method used to search and lookup a {@link PhoneNumber.Type} from the given {@link String abbreviation},
     * ignore case.
     *
     * @param abbreviation {@link String} containing the {@literal abbreviation} of the {@link PhoneNumber.Type}
     * to search and lookup.
     * @return a {@link PhoneNumber.Type} for the given {@link String abbreviation}.
     * @throws IllegalArgumentException if the {@link String abbreviation} does not map to a {@link PhoneNumber.Type}.
     * @see PhoneNumber.Type#getAbbreviation()
     */
    public static @NotNull PhoneNumber.Type from(@NotNull String abbreviation) {

      return Arrays.stream(values())
        .filter(type -> type.getAbbreviation().equalsIgnoreCase(abbreviation))
        .findFirst()
        .orElseThrow(() -> newIllegalArgumentException("PhoneNumber.Type for abbreviation [%s] was not found",
          abbreviation));
    }

    private final String abbreviation;
    private final String description;

    /**
     * Constructs a new instance of {@link PhoneNumber.Type} initialized with the given,
     * required {@link String abbreviation} and {@link String description}.
     *
     * @param abbreviation {@link String abbreviation} of this {@link PhoneNumber.Type}.
     * @param description {@link String description} of this {@link PhoneNumber.Type}.
     */
    Type(@NotNull String abbreviation, @NotNull String description) {
      this.abbreviation = StringUtils.requireText(abbreviation, "Abbreviation [%s] is required");
      this.description = StringUtils.requireText(description, "Description [%s] is required");
    }

    /**
     * Gets the {@link String abbreviation} of this {@link PhoneNumber.Type}.
     *
     * @return the {@link String abbreviation} of this {@link PhoneNumber.Type}.
     */
    public @NotNull String getAbbreviation() {
      return this.abbreviation;
    }

    /**
     * Gets the {@link String description} of this {@link PhoneNumber.Type}.
     *
     * @return the {@link String description} of this {@link PhoneNumber.Type}.
     */
    public @NotNull String getDescription() {
      return this.description;
    }

    /**
     * Returns a {@link String} representation of this {@link PhoneNumber.Type}.
     *
     * @return a {@link String} describing this {@link PhoneNumber.Type}.
     * @see java.lang.String
     */
    @Override
    public String toString() {
      return getDescription();
    }
  }
}
