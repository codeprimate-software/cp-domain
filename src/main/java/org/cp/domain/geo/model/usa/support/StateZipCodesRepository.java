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
package org.cp.domain.geo.model.usa.support;

import static org.cp.elements.lang.RuntimeExceptionsFactory.newIllegalArgumentException;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

import org.cp.domain.geo.enums.Country;
import org.cp.domain.geo.enums.State;
import org.cp.domain.geo.model.PostalCode;
import org.cp.domain.geo.model.usa.ZIP;
import org.cp.elements.lang.Assert;
import org.cp.elements.lang.StringUtils;
import org.cp.elements.lang.annotation.NotNull;
import org.cp.elements.lang.annotation.NullSafe;
import org.cp.elements.lang.annotation.Nullable;
import org.cp.elements.util.MapBuilder;

/**
 * Repository of {@link State} to {@link ZIP} codes declared in the {@link Country#UNITED_STATES_OF_AMERICA}.
 *
 * @author John Blum
 * @see java.util.concurrent.ConcurrentMap
 * @see org.cp.domain.geo.enums.Country
 * @see org.cp.domain.geo.enums.State
 * @see org.cp.domain.geo.model.PostalCode
 * @see org.cp.domain.geo.model.usa.ZIP
 * @since 0.1.0
 */
public class StateZipCodesRepository {

  private static final StateZipCodesRepository STATE_ZIP_CODES_REPOSITORY = new StateZipCodesRepository();

  private static final Map<State, ZipCodeRegion> repository;

  static {
    repository = MapBuilder.<State, ZipCodeRegion>newConcurrentMap()
      .put(State.ALABAMA, ZipCodeRegion.of("35", "36"))
      .put(State.ALASKA, ZipCodeRegion.of("995", "999"))
      .put(State.ARIZONA, ZipCodeRegion.of("85", "86"))
      .put(State.ARKANSAS, ZipCodeRegion.of("716", "729"))
      .put(State.CALIFORNIA, ZipCodeRegion.of("900", "961"))
      .put(State.COLORADO, ZipCodeRegion.of("80", "81"))
      .put(State.CONNECTICUT, ZipCodeRegion.of("06"))
      .put(State.DISTRICT_OF_COLUMBIA, ZipCodeRegion.of("200", "205"))
      .put(State.FLORIDA, ZipCodeRegion.of("32", "34"))
      .put(State.GEORGIA, ZipCodeRegion.of("30", "31"))
      .put(State.HAWAII, ZipCodeRegion.of("967", "968"))
      .put(State.IDAHO, ZipCodeRegion.of("832", "839"))
      .put(State.ILLINOIS, ZipCodeRegion.of("60", "62"))
      .put(State.INDIANA, ZipCodeRegion.of("46", "47"))
      .put(State.IOWA, ZipCodeRegion.of("50", "52"))
      .put(State.KANSAS, ZipCodeRegion.of("66", "67"))
      .put(State.KENTUCKY, ZipCodeRegion.of("40", "42"))
      .put(State.LOUISIANA, ZipCodeRegion.of("700", "715"))
      .put(State.MAINE, ZipCodeRegion.of("039", "049"))
      .put(State.MASSACHUSETTS, ZipCodeRegion.of("010", "027"))
      .put(State.MICHIGAN, ZipCodeRegion.of("48", "49"))
      .put(State.MINNESOTA, ZipCodeRegion.of("550", "567"))
      .put(State.MISSISSIPPI, ZipCodeRegion.of("386", "399"))
      .put(State.MISSOURI, ZipCodeRegion.of("63", "65"))
      .put(State.MONTANA, ZipCodeRegion.of("59"))
      .put(State.NEBRASKA, ZipCodeRegion.of("68", "69"))
      .put(State.NEVADA, ZipCodeRegion.of("889", "899"))
      .put(State.NEW_HAMPSHIRE, ZipCodeRegion.of("030", "038"))
      .put(State.NEW_JERSEY, ZipCodeRegion.of("07", "08"))
      .put(State.NEW_MEXICO, ZipCodeRegion.of("870", "884"))
      .put(State.NEW_YORK, ZipCodeRegion.of("10", "14"))
      .put(State.NORTH_CAROLINA, ZipCodeRegion.of("27", "28"))
      .put(State.NORTH_DAKOTA, ZipCodeRegion.of("58"))
      .put(State.OHIO, ZipCodeRegion.of("43", "45"))
      .put(State.OKLAHOMA, ZipCodeRegion.of("73", "74"))
      .put(State.OREGON, ZipCodeRegion.of("97"))
      .put(State.PENNSYLVANIA, ZipCodeRegion.of("150", "196"))
      .put(State.RHODE_ISLAND, ZipCodeRegion.of("028", "029"))
      .put(State.SOUTH_CAROLINA, ZipCodeRegion.of("29"))
      .put(State.SOUTH_DAKOTA, ZipCodeRegion.of("57"))
      .put(State.TENNESSEE, ZipCodeRegion.of("370", "385"))
      .put(State.TEXAS, ZipCodeRegion.of("75", "79"))
      .put(State.UTAH, ZipCodeRegion.of("84"))
      .put(State.VERMONT, ZipCodeRegion.of("05"))
      .put(State.VIRGINIA, ZipCodeRegion.of("220", "246"))
      .put(State.WASHINGTON, ZipCodeRegion.of("980", "984"))
      .put(State.WEST_VIRGINIA, ZipCodeRegion.of("247", "269"))
      .put(State.WISCONSIN, ZipCodeRegion.of("53", "54"))
      .put(State.WYOMING, ZipCodeRegion.of("820", "831"))
      .build();
  }

  /**
   * Gets a reference to the {@literal Singleton} instance of the {@link StateZipCodesRepository}.
   *
   * @return a reference to the {@literal Singleton} instance of the {@link StateZipCodesRepository}.
   * @see org.cp.domain.geo.model.usa.support.StateZipCodesRepository
   */
  public static @NotNull StateZipCodesRepository getInstance() {
    return STATE_ZIP_CODES_REPOSITORY;
  }

  /**
   * Gets a reference to the {@literal non-null} {@link Map Repository data structure} used to
   * map {@link State States} to {@link ZIP} codes.
   *
   * @return a reference to the {@link Map Repository data structure} used to
   * map {@link State States} to {@link ZIP} codes.
   * @see org.cp.domain.geo.model.usa.support.StateZipCodesRepository.ZipCodeRegion
   * @see java.util.Map
   */
  protected Map<State, ZipCodeRegion> getRepository() {
    return Collections.unmodifiableMap(repository);
  }

  /**
   * Query method used to find a {@link State} given a required {@link PostalCode}.
   *
   * @param postalCode {@link PostalCode} used to find the corresponding {@link State};
   * must not be {@literal null}.
   * @return a {@link State} for the given {@link PostalCode}.
   * @throws IllegalArgumentException if the given {@link PostalCode} is {@literal null},
   * or a {@link State} could not be found for the given {@link PostalCode}.
   * @see org.cp.domain.geo.model.PostalCode
   * @see org.cp.domain.geo.enums.State
   * @see #getRepository()
   */
  public @NotNull State findBy(@NotNull PostalCode postalCode) {

    Assert.notNull(postalCode, "PostalCode used to find a State is required");

    return getRepository().entrySet().stream()
      .filter(entry -> entry.getValue().isInRegion(postalCode))
      .map(Map.Entry::getKey)
      .findFirst()
      .orElseThrow(() -> newIllegalArgumentException("State for ZIP code [%s] not found", postalCode));
  }

  /**
   * Abstract Data Type (ADT) modeling a range of {@link ZIP} codes for a region.
   */
  protected static class ZipCodeRegion {

    /**
     * Factory method used to construct a new {@link ZipCodeRegion} initialized with the given,
     * required {@link String ZIP code prefix}.
     *
     * @param zipCodePrefix {@link String} containing the {@link ZIP} code prefix that a {@link PostalCode}
     * or {@link ZIP} code must start with; must not be {@literal null} or {@literal empty}.
     * @return a new {@link ZipCodeRegion}.
     * @throws IllegalArgumentException if the {@link String ZIP code prefix} is {@literal null}
     * or {@literal empty}.
     * @see #of(String, String)
     */
    protected static @NotNull ZipCodeRegion of(@NotNull String zipCodePrefix) {
      return of(zipCodePrefix, null);
    }

    /**
     * Factory method used to construct a new {@link ZipCodeRegion} initialized with the given range,
     * starting with the given, required {@link String ZIP code start}
     * and ending with the required {@link String ZIP code end}.
     *
     * @param zipCodeStart {@link String} containing the start of the {@link ZIP} code range;
     * must not be {@literal null} or {@literal empty}.
     * @param zipCodeEnd {@link String} containing the end of the {@link ZIP} code range.
     * @return a new {@link ZipCodeRegion}.
     * @throws IllegalArgumentException if the {@link String ZIP code start} is {@literal null}
     * or {@literal empty}.
     */
    protected static @NotNull ZipCodeRegion of(@NotNull String zipCodeStart, @Nullable String zipCodeEnd) {
      return new ZipCodeRegion(zipCodeStart, zipCodeEnd);
    }

    private final String zipCodeStart;
    private final String zipCodeEnd;

    /**
     * Constructs a new {@link ZipCodeRegion} initialized with the given range, starting with the given,
     * required {@link String ZIP code start} and ending with the required {@link String ZIP code end}.
     *
     * @param zipCodeStart {@link String} containing the start of the {@link ZIP} code range;
     * must not be {@literal null} or {@literal empty}.
     * @param zipCodeEnd {@link String} containing the end of the {@link ZIP} code range.
     * @throws IllegalArgumentException if the {@link String ZIP code start} is {@literal null} or {@literal empty}.
     */
    protected ZipCodeRegion(@NotNull String zipCodeStart, @Nullable String zipCodeEnd) {

      this.zipCodeStart = StringUtils.requireText(zipCodeStart,
        "The beginning [%s] of the ZIP code range is required");

      this.zipCodeEnd = zipCodeEnd;
    }

    /**
     * Determines whether this {@link ZipCodeRegion} declares a {@link ZIP} code range.
     *
     * @return a boolean value indicating whether this {@link ZipCodeRegion} declares a {@link ZIP} code range.
     * @see #getZipCodeEnd()
     */
    protected boolean isRange() {
      return StringUtils.hasText(getZipCodeEnd());
    }

    /**
     * Determines whether the given {@link PostalCode} is in this {@link ZipCodeRegion}.
     *
     * @param postalCode {@link PostalCode} to evaluate.
     * @return a boolean value indicating whether the given {@link PostalCode} is in this {@link ZipCodeRegion}.
     * @see org.cp.domain.geo.model.PostalCode
     * @see #startsWithPrefixPostalCodePredicate()
     * @see #inRegionPostalCodePredicate()
     * @see #inRangePostalCodePredicate()
     * @see #validPostalCodePredicate()
     */
    @NullSafe
    protected boolean isInRegion(@Nullable PostalCode postalCode) {
      return inRegionPostalCodePredicate().test(postalCode);
    }

    private Predicate<PostalCode> inRegionPostalCodePredicate() {
      return validPostalCodePredicate().and(inRangePostalCodePredicate().or(startsWithPrefixPostalCodePredicate()));
    }

    private Predicate<PostalCode> inRangePostalCodePredicate() {

      return postalCode -> isRange()
        && postalCode.getNumber().compareTo(getZipCodeStart()) >= 0
        && postalCode.getNumber().compareTo(getAdjustedZipCodeEnd()) <= 0;
    }

    private Predicate<PostalCode> startsWithPrefixPostalCodePredicate() {
      return postalCode -> postalCode.getNumber().startsWith(getZipCodeStart());
    }

    private Predicate<PostalCode> validPostalCodePredicate() {
      return postalCode -> Objects.nonNull(postalCode) && StringUtils.hasText(postalCode.getNumber());
    }

    protected @NotNull String getAdjustedZipCodeEnd() {
      return StringUtils.padRight(getZipCodeEnd(), '9', 9);
    }

    protected @NotNull String getZipCodeStart() {
      return this.zipCodeStart;
    }

    protected @Nullable String getZipCodeEnd() {
      return this.zipCodeEnd;
    }
  }
}
