/*
 * Copyright 2017-Present Author or Authors.
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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import org.cp.domain.contact.phone.model.AbstractPhoneNumber.GenericPhoneNumber;
import org.cp.domain.contact.phone.serialization.json.PhoneNumberJsonDeserializer;
import org.cp.domain.core.serialization.json.JsonSerializable;
import org.cp.domain.geo.enums.Country;
import org.cp.domain.geo.support.CountryAware;
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
 * Abstract Data Type (ADT) modeling a {@literal phone number}, such as a {@literal cell phone number},
 * {@literal land line} or a {@literal Voice-Over-IP (VOIP) number}.
 * <p>
 * Currently, the Codeprimate Domain {@link PhoneNumber} representation is limited to
 * the {@literal North American Numbering Plan (NANP)}. Eventually, support may be added for numbers outside of
 * North America and nations that do not participate in the {@literal NANP} numbering scheme.
 *
 * @author John Blum
 * @see java.lang.Cloneable
 * @see java.lang.Comparable
 * @see java.io.Serializable
 * @see org.cp.domain.contact.phone.model.AreaCode
 * @see org.cp.domain.contact.phone.model.ExchangeCode
 * @see org.cp.domain.contact.phone.model.Extension
 * @see org.cp.domain.contact.phone.model.LineNumber
 * @see org.cp.domain.core.serialization.json.JsonSerializable
 * @see org.cp.domain.geo.enums.Country
 * @see org.cp.domain.geo.support.CountryAware
 * @see org.cp.elements.lang.Identifiable
 * @see org.cp.elements.lang.Renderable
 * @see org.cp.elements.lang.Visitable
 * @see org.cp.elements.lang.annotation.FluentApi
 * @see <a href="https://en.wikipedia.org/wiki/National_conventions_for_writing_telephone_numbers">National conventions for writing telephone numbers</a>
 * @see <a href="https://en.wikipedia.org/wiki/North_American_Numbering_Plan">North American Numbering Plan (NANP)</a>
 * @since 0.1.0
 */
@FluentApi
@SuppressWarnings("unused")
@JsonDeserialize(using = PhoneNumberJsonDeserializer.class)
@JsonIgnoreProperties({ "new", "notNew", "cell", "landline", "roaming", "satellite", "unknown", "voip" })
public interface PhoneNumber extends Cloneable, Comparable<PhoneNumber>, CountryAware, Identifiable<Long>,
    JsonSerializable, Renderable, Serializable, Visitable {

  int REQUIRED_PHONE_NUMBER_LENGTH = AreaCode.REQUIRED_AREA_CODE_LENGTH
    + ExchangeCode.REQUIRED_EXCHANGE_CODE_LENGTH
    + LineNumber.REQUIRED_LINE_NUMBER_LENGTH;

  /**
   * Returns a new {@link PhoneNumber.Builder} used to construct and build a {@link PhoneNumber}.
   *
   * @return a new {@link PhoneNumber.Builder} used to construct and build a {@link PhoneNumber}.
   * @see org.cp.domain.contact.phone.model.PhoneNumber.Builder
   * @see org.cp.elements.lang.annotation.Dsl
   */
  @Dsl
  static @NotNull PhoneNumber.Builder builder() {
    return new PhoneNumber.Builder();
  }

  /**
   * Factory method used to construct a new {@link PhoneNumber} copied from an existing {@link PhoneNumber}.
   *
   * @param phoneNumber {@link PhoneNumber} to copy; must not be {@literal null}.
   * @return a new {@link PhoneNumber} copied from the existing {@link PhoneNumber}.
   * @throws IllegalArgumentException if the given {@link PhoneNumber} to copy is {@literal null}.
   * @see org.cp.domain.contact.phone.model.PhoneNumber.Builder#from(PhoneNumber)
   * @see org.cp.elements.lang.annotation.Dsl
   * @see #builder()
   */
  @Dsl
  static @NotNull PhoneNumber from(@NotNull PhoneNumber phoneNumber) {

    PhoneNumber.Builder phoneNumberBuilder = builder().from(phoneNumber);

    phoneNumber.getCountry().ifPresent(phoneNumberBuilder::inCountry);
    phoneNumber.getExtension().ifPresent(phoneNumberBuilder::withExtension);

    if (phoneNumber.isTextEnabled()) {
      phoneNumberBuilder.withTextEnabled();
    }

    PhoneNumber copy = phoneNumberBuilder.build();

    phoneNumber.getType().ifPresent(copy::setType);

    return copy;
  }

  /**
   * Factory method used to construct a new {@link PhoneNumber} initialized from the given {@link AreaCode},
   * {@link ExchangeCode} and {@link LineNumber}.
   *
   * @param areaCode {@link AreaCode} of the new {@link PhoneNumber}; must not be {@literal null}.
   * @param exchangeCode {@link ExchangeCode} of the new {@link PhoneNumber}; must not be {@literal null}.
   * @param lineNumber {@link LineNumber} of the new {@link PhoneNumber}; must not be {@literal null}.
   * @return a new {@link PhoneNumber} initialized with the given, required {@link AreaCode},
   * {@link ExchangeCode} and {@link LineNumber}.
   * @throws IllegalArgumentException if the given {@link AreaCode}, {@link ExchangeCode} or {@link LineNumber}
   * are {@literal null}.
   * @see org.cp.domain.contact.phone.model.AreaCode
   * @see org.cp.domain.contact.phone.model.ExchangeCode
   * @see org.cp.domain.contact.phone.model.LineNumber
   * @see org.cp.elements.lang.annotation.Dsl
   * @see #builder()
   */
  @Dsl
  static @NotNull PhoneNumber of(@NotNull AreaCode areaCode, @NotNull ExchangeCode exchangeCode,
      @NotNull LineNumber lineNumber) {

    return builder()
      .inAreaCode(areaCode)
      .usingExchange(exchangeCode)
      .withLineNumber(lineNumber)
      .build();
  }

  /**
   * Parses the given {@link String phone number} and constructs a new {@link PhoneNumber}.
   *
   * @param phoneNumber {@link String} containing the digits making up the {@link PhoneNumber}.
   * @return a new {@link PhoneNumber} with {@link AreaCode}, {@link ExchangeCode} and {@link LineNumber}
   * contained in the given {@link String phone number}.
   * @throws IllegalArgumentException if the given {@link String phone number} is not valid.
   * @see #of(AreaCode, ExchangeCode, LineNumber)
   */
  static @NotNull PhoneNumber parse(@NotNull String phoneNumber) {

    String phoneNumberDigits = toRawPhoneNumber(phoneNumber);

    assertValidPhoneNumber(phoneNumber, phoneNumberDigits);

    String areaCodeExchangeCodeLineNumber = toAreaCodeExchangeCodeLineNumber(phoneNumberDigits);

    return of(AreaCode.parse(areaCodeExchangeCodeLineNumber),
      ExchangeCode.parse(areaCodeExchangeCodeLineNumber),
      LineNumber.parse(areaCodeExchangeCodeLineNumber));
  }

  private static void assertValidPhoneNumber(String phoneNumber, String phoneNumberDigits) {
    Assert.isTrue(phoneNumberDigits.length() >= REQUIRED_PHONE_NUMBER_LENGTH,
      "Phone Number [%s] must be [%d] digits", phoneNumber, REQUIRED_PHONE_NUMBER_LENGTH);
  }

  private static String toAreaCodeExchangeCodeLineNumber(String phoneNumberDigits) {
    return phoneNumberDigits.substring(0, REQUIRED_PHONE_NUMBER_LENGTH);
  }

  private static String toRawPhoneNumber(String phoneNumber) {
    return StringUtils.getDigits(phoneNumber);
  }

  /**
   * Gets the {@link String three-digit} {@link AreaCode} of this {@link PhoneNumber}.
   *
   * @return the {@link String three-digit} {@link AreaCode} of this {@link PhoneNumber}.
   * @see org.cp.domain.contact.phone.model.AreaCode
   */
  AreaCode getAreaCode();

  /**
   * Gets the {@literal 3 digit} {@link ExchangeCode} of this {@link PhoneNumber}.
   * <p>
   * The {@literal Exchange Code}, also known as the {@literal Telephone Prefix} or {@literal Central Office Code}
   * is the second 3 digits in the 10-digit phone number.
   *
   * @return the {@link String three-digit} {@link ExchangeCode} of this {@link PhoneNumber}.
   * @see org.cp.domain.contact.phone.model.ExchangeCode
   */
  ExchangeCode getExchangeCode();

  /**
   * Gets the {@link LineNumber} of this {@link PhoneNumber}.
   *
   * @return the {@link LineNumber} of this {@link PhoneNumber}.
   * @see org.cp.domain.contact.phone.model.LineNumber
   */
  LineNumber getLineNumber();

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
      .isEmpty();
  }

  /**
   * Determines whether this {@link PhoneNumber} supports {@literal texting}.
   * <p>
   * Returns {@literal false} by default.
   *
   * @return a boolean value indicating whether this {@link PhoneNumber} supports {@literal texting}.
   */
  default boolean isTextEnabled() {
    return false;
  }

  /**
   * Gets the {@link Optional} {@link String extension} at this {@link PhoneNumber}.
   * <p>
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
   * Sets the {@link Extension} for this {@link PhoneNumber}.
   * <p>
   * Throws {@link UnsupportedOperationException} by default.
   *
   * @param extension {@link Extension} at this {@link PhoneNumber}.
   * @see org.cp.domain.contact.phone.model.Extension
   */
  default void setExtension(@Nullable Extension extension) {
    throw newUnsupportedOperationException("Setting Extension for a PhoneNumber of type [%s] is not supported",
      getClass().getName());
  }

  /**
   * Get the {@link Optional} {@link PhoneNumber.Type} of this {@link PhoneNumber}.
   * <p>
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
   * <p>
   * Throws {@link UnsupportedOperationException} by default.
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
   * @param <T> {@link Class concrete type} of {@link PhoneNumber}.
   * @param phoneNumberType {@link PhoneNumber.Type} of this {@link PhoneNumber}.
   * @return this {@link PhoneNumber}.
   * @see org.cp.domain.contact.phone.model.PhoneNumber.Type
   * @see org.cp.elements.lang.annotation.Dsl
   * @see #setType(PhoneNumber.Type)
   */
  @Dsl
  @SuppressWarnings("unchecked")
  default @NotNull <T extends PhoneNumber> T asType(@Nullable PhoneNumber.Type phoneNumberType) {
    setType(phoneNumberType);
    return (T) this;
  }

  /**
   * Builder method used to set the {@link PhoneNumber.Type} of this {@link PhoneNumber}
   * to {@link PhoneNumber.Type#CELL}.
   *
   * @param <T> {@link Class concrete type} of {@link PhoneNumber}.
   * @return this {@link PhoneNumber}.
   * @see org.cp.domain.contact.phone.model.PhoneNumber.Type#CELL
   * @see org.cp.elements.lang.annotation.Dsl
   * @see #asType(PhoneNumber.Type)
   */
  @Dsl
  default @NotNull <T extends PhoneNumber> T asCell() {
    return asType(PhoneNumber.Type.CELL);
  }

  /**
   * Builder method used to set the {@link PhoneNumber.Type} of this {@link PhoneNumber}
   * to {@link PhoneNumber.Type#LANDLINE}.
   *
   * @param <T> {@link Class concrete type} of {@link PhoneNumber}.
   * @return this {@link PhoneNumber}.
   * @see org.cp.domain.contact.phone.model.PhoneNumber.Type#LANDLINE
   * @see org.cp.elements.lang.annotation.Dsl
   * @see #asType(PhoneNumber.Type)
   */
  @Dsl
  default @NotNull <T extends PhoneNumber> T asLandline() {
    return asType(PhoneNumber.Type.LANDLINE);
  }

  /**
   * Builder method used to set the {@link PhoneNumber.Type} of this {@link PhoneNumber}
   * to {@link PhoneNumber.Type#SATELLITE}.
   *
   * @param <T> {@link Class concrete type} of {@link PhoneNumber}.
   * @return this {@link PhoneNumber}.
   * @see org.cp.domain.contact.phone.model.PhoneNumber.Type#SATELLITE
   * @see org.cp.elements.lang.annotation.Dsl
   * @see #asType(PhoneNumber.Type)
   */
  @Dsl
  default @NotNull <T extends PhoneNumber> T asSatellite() {
    return asType(PhoneNumber.Type.SATELLITE);
  }

  /**
   * Builder method used to set the {@link PhoneNumber.Type} of this {@link PhoneNumber}
   * to {@link PhoneNumber.Type#VOIP}.
   *
   * @param <T> {@link Class concrete type} of {@link PhoneNumber}.
   * @return this {@link PhoneNumber}.
   * @see org.cp.domain.contact.phone.model.PhoneNumber.Type#VOIP
   * @see org.cp.elements.lang.annotation.Dsl
   * @see #asType(PhoneNumber.Type)
   */
  @Dsl
  default @NotNull <T extends PhoneNumber> T asVoip() {
    return asType(PhoneNumber.Type.VOIP);
  }

  /**
   * Determines whether this is a {@link PhoneNumber.Type#CELL} {@link PhoneNumber}.
   *
   * @return a boolean value determining whether this is a {@link PhoneNumber.Type#CELL} {@link PhoneNumber}.
   * @see org.cp.domain.contact.phone.model.PhoneNumber.Type#CELL
   * @see #getType()
   */
  default boolean isCell() {

    return getType()
      .filter(PhoneNumber.Type.CELL::equals)
      .isPresent();
  }

  /**
   * Determines whether this is a {@link PhoneNumber.Type#LANDLINE} {@link PhoneNumber}.
   *
   * @return a boolean value determining whether this is a {@link PhoneNumber.Type#LANDLINE} {@link PhoneNumber}.
   * @see org.cp.domain.contact.phone.model.PhoneNumber.Type#LANDLINE
   * @see #getType()
   */
  default boolean isLandline() {

    return getType()
      .filter(PhoneNumber.Type.LANDLINE::equals)
      .isPresent();
  }

  /**
   * Determines whether this is a {@link PhoneNumber.Type#SATELLITE} {@link PhoneNumber}.
   *
   * @return a boolean value determining whether this is a {@link PhoneNumber.Type#SATELLITE} {@link PhoneNumber}.
   * @see org.cp.domain.contact.phone.model.PhoneNumber.Type#SATELLITE
   * @see #getType()
   */
  default boolean isSatellite() {

    return getType()
      .filter(PhoneNumber.Type.SATELLITE::equals)
      .isPresent();
  }

  /**
   * Determines whether this is an {@link PhoneNumber.Type#UNKNOWN} {@link PhoneNumber}.
   *
   * @return a boolean value determining whether this is an {@link PhoneNumber.Type#UNKNOWN} {@link PhoneNumber}.
   * @see org.cp.domain.contact.phone.model.PhoneNumber.Type#UNKNOWN
   * @see #getType()
   */
  default boolean isUnknown() {

    Optional<PhoneNumber.Type> phoneNumberType = getType();

    return phoneNumberType.isEmpty() || phoneNumberType
      .filter(PhoneNumber.Type.UNKNOWN::equals)
      .isPresent();
  }

  /**
   * Determines whether this is a {@link PhoneNumber.Type#VOIP} {@link PhoneNumber}.
   *
   * @return a boolean value determining whether this is an {@link PhoneNumber.Type#VOIP} {@link PhoneNumber}.
   * @see org.cp.domain.contact.phone.model.PhoneNumber.Type#VOIP
   * @see #getType()
   */
  default boolean isVoip() {

    return getType()
      .filter(PhoneNumber.Type.VOIP::equals)
      .isPresent();
  }

  /**
   * Visits this {@link PhoneNumber}.
   *
   * @param visitor {@link Visitor} used to {@link Visitor#visit(Visitable)} this {@link PhoneNumber}
   * to perform an operation on this {@link PhoneNumber}; must not be {@literal null}.
   * @see org.cp.elements.lang.Visitor
   */
  @Override
  default void accept(@NotNull Visitor visitor) {
    visitor.visit(this);
  }

  /**
   * Compares this {@link PhoneNumber} to the given, required {@link PhoneNumber} for relative ordering (sorting).
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
      .doCompare(this.getLineNumber(), that.getLineNumber())
      .doCompare(this.getExtension().orElse(null), that.getExtension().orElse(null))
      .build();
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
    private Boolean textEnabled;
    private Country country;
    private ExchangeCode exchangeCode;
    private Extension extension;
    private LineNumber lineNumber;

    /**
     * Builds a new {@link PhoneNumber} from an existing, required {@link PhoneNumber}.
     *
     * @param phoneNumber {@link PhoneNumber} to copy; must not be {@literal null}.
     * @return this {@link Builder}.
     * @throws IllegalArgumentException if the given {@link PhoneNumber} to copy is {@literal null}.
     * @see org.cp.elements.lang.annotation.Dsl
     */
    @Dsl
    public @NotNull Builder from(@NotNull PhoneNumber phoneNumber) {

      Assert.notNull(phoneNumber, "PhoneNumber to copy is required");

      Builder builder = inAreaCode(phoneNumber.getAreaCode())
        .usingExchange(phoneNumber.getExchangeCode())
        .withLineNumber(phoneNumber.getLineNumber());

      phoneNumber.getCountry().ifPresent(builder::inCountry);
      phoneNumber.getExtension().ifPresent(builder::withExtension);

      return builder;
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
     * Builder method used to set an optional {@link Country} of origin for the new {@link PhoneNumber}.
     *
     * @param country {@link Country} of origin for the new {@link PhoneNumber}.
     * @return this {@link Builder}.
     * @see org.cp.domain.geo.enums.Country
     */
    @Dsl
    public @NotNull Builder inCountry(@Nullable Country country) {
      this.country = country;
      return this;
    }

    /**
     * Builder method used to set the {@link Country} of origin for the new {@link PhoneNumber}
     * in the {@link Country#localCountry()}.
     *
     * @return this {@link Builder}.
     * @see org.cp.domain.geo.enums.Country#localCountry()
     * @see #inCountry(Country)
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
    public @NotNull Builder usingExchange(@NotNull ExchangeCode exchangeCode) {
      this.exchangeCode = ObjectUtils.requireObject(exchangeCode, "ExchangeCode is required");
      return this;
    }

    /**
     * Builder method used to set an optional {@link Extension} of the new {@link PhoneNumber}.
     *
     * @param extension optional {@link Extension} of the new {@link PhoneNumber}.
     * @return this {@link Builder}.
     * @see org.cp.domain.contact.phone.model.Extension
     */
    @Dsl
    public @NotNull Builder withExtension(@Nullable Extension extension) {
      this.extension = extension;
      return this;
    }

    /**
     * Builder method used to set the given, required {@link LineNumber} of the new {@link PhoneNumber}.
     *
     * @param number {@link LineNumber} of the new {@link PhoneNumber}; must not be {@literal null}.
     * @return this {@link Builder}.
     * @throws IllegalArgumentException if the given {@link LineNumber} is {@literal null}.
     * @see org.cp.domain.contact.phone.model.LineNumber
     */
    @Dsl
    public @NotNull Builder withLineNumber(@NotNull LineNumber number) {
      this.lineNumber = ObjectUtils.requireObject(number, "LineNumber is required");
      return this;
    }

    /**
     * Builder method used to set whether {@literal texting} is enabled for the new {@link PhoneNumber}.
     *
     * @return this {@link Builder}.
     * @see org.cp.elements.lang.annotation.Dsl
     */
    @Dsl
    public @NotNull Builder withTextEnabled() {
      this.textEnabled = Boolean.TRUE;
      return this;
    }

    /**
     * Builds a new {@link PhoneNumber}.
     *
     * @return a new {@link PhoneNumber}.
     * @see org.cp.domain.contact.phone.model.AbstractPhoneNumber
     * @see org.cp.domain.contact.phone.model.PhoneNumber
     */
    @Override
    public @NotNull PhoneNumber build() {

      AbstractPhoneNumber phoneNumber = new GenericPhoneNumber(this.areaCode, this.exchangeCode, this.lineNumber);

      phoneNumber.setCountry(this.country);
      phoneNumber.setExtension(this.extension);
      phoneNumber.setTextEnabled(this.textEnabled);

      return phoneNumber;
    }
  }

  /**
   * {@link Enum Enumeration} of {@link PhoneNumber} types.
   *
   * @see java.lang.Enum
   */
  enum Type {

    CELL("CELL", "Cellular"),
    LANDLINE("LAND", "Landline"),
    SATELLITE("SAT", "Satellite"),
    VOIP("VOIP", "Voice-Over-IP"),
    UNKNOWN("??", "Unknown");

    /**
     * Factory method used to search for and lookup a {@link PhoneNumber.Type} from the given
     * {@link String abbreviation}, ignoring case.
     *
     * @param abbreviation {@link String} containing the {@literal abbreviation} of the {@link PhoneNumber.Type}
     * to search for and lookup.
     * @return a {@link PhoneNumber.Type} for the given {@link String abbreviation}.
     * @throws IllegalArgumentException if the {@link String abbreviation} does not map to a {@link PhoneNumber.Type}.
     * @see PhoneNumber.Type#getAbbreviation()
     */
    public static @NotNull PhoneNumber.Type from(@NotNull String abbreviation) {

      return Arrays.stream(values())
        .filter(type -> type.getAbbreviation().equalsIgnoreCase(abbreviation))
        .findFirst()
        .orElseThrow(() ->
          newIllegalArgumentException("PhoneNumber.Type for abbreviation [%s] was not found", abbreviation));
    }

    private final String abbreviation;
    private final String description;

    /**
     * Constructs a new {@link PhoneNumber.Type} initialized with the given, required {@link String abbreviation}
     * and {@link String description}.
     *
     * @param abbreviation {@link String abbreviation} for this {@link PhoneNumber.Type}.
     * @param description {@link String description} of this {@link PhoneNumber.Type}.
     */
    Type(@NotNull String abbreviation, @NotNull String description) {
      this.abbreviation = StringUtils.requireText(abbreviation, "Abbreviation [%s] is required");
      this.description = StringUtils.requireText(description, "Description [%s] is required");
    }

    /**
     * Gets the {@link String abbreviation} for this {@link PhoneNumber.Type}.
     *
     * @return the {@link String abbreviation} for this {@link PhoneNumber.Type}.
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
