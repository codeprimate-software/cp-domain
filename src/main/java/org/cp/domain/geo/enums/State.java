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

package org.cp.domain.geo.enums;

import java.util.Arrays;

/**
 * The {@link State} enum is an {@link Enum enumeration} of all 50 of the United States of America.
 *
 * @author John J. Blum
 * @see java.lang.Enum
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public enum State {

  ALABAMA("AL", "Alabama"),
  ALASKA("AK", "Alaska"),
  ARIZONA("AZ", "Arizona"),
  ARKANSAS("AR", "Arkansas"),
  CALIFORNIA("CA", "California"),
  COLORADO("CO", "Colarado"),
  CONNECTICUT("CT", "Connecticut"),
  DELAWARE("DE", "Delaware"),
  DISTRICT_OF_COLUMBIA("DC", "District of Columbia"),
  FLORIDA("FL", "Florida"),
  GEORGIA("GA", "Georgia"),
  HAWAII("HI", "Hawaii"),
  IDAHO("ID", "Idaho"),
  ILLINOIS("IL", "Illinois"),
  INDIANA("IN", "Indiana"),
  IOWA("IA", "Iowa"),
  KANSAS("KA", "Kansas"),
  KENTUCKY("KY", "Kentucky"),
  LOUISIANA("LA", "Louisiana"),
  MAINE("ME", "Maine"),
  MARYLAND("MD", "Maryland"),
  MASSACHUSETTS("MA", "Massachusetts"),
  MICHIGAN("MI", "Michigan"),
  MINNESOTA("MN", "Minnesota"),
  MISSISSIPPI("MS", "Mississippi"),
  MISSOURI("MO", "Missouri"),
  MONTANA("MT", "Montana"),
  NEBRASKA("NE", "Nebraska"),
  NEVADA("NV", "Nevada"),
  NEW_HAMPSHIRE("NH", "New Hampshire"),
  NEW_JERSEY("NJ", "New Jersey"),
  NEW_MEXICO("NM", "New Mexico"),
  NEW_YORK("NY", "New York"),
  NORTH_CAROLINA("NC", "North Carolina"),
  NORTH_DAKOTA("ND", "North Dakota"),
  OHIO("OH", "Ohio"),
  OKLAHOMA("OK", "Oklahoma"),
  OREGON("OR", "Oregon"),
  PENNSYLVANIA("PA", "Pennsylvania"),
  RHODE_ISLAND("RI", "Rhode Island"),
  SOUTH_CAROLINA("SC", "South Carolina"),
  SOUTH_DAKOTA("SD", "South Dakota"),
  TENNESSEE("TN", "Tennessee"),
  TEXAS("TX", "Texas"),
  UTAH("UT", "Utah"),
  VERMONT("VT", "Vermont"),
  VIRGINIA("VA", "Virginia"),
  WASHINGTON("WA", "Washington"),
  WEST_VIRGINIA("WV", "West Virginia"),
  WISCONSIN("WI", "Wisconsin"),
  WYOMING("WY", "Wyoming");

  /**
   * Factory method used to find or lookup a {@link State} by {@link String abbreviation}.
   *
   * @param abbreviation {@link String} containing the abbreviation of the {@link State} to find.
   * @return the {@link State} with the given {@link String abbreviation} or {@literal null}
   * if no {@link State} with the given {@link String abbreviation} could be found.
   * @see #getAbbreviation()
   * @see #values()
   */
  public static State valueOfAbbreviation(String abbreviation) {

    return Arrays.stream(values())
      .filter(state -> state.getAbbreviation().equalsIgnoreCase(abbreviation))
      .findFirst()
      .orElse(null);
  }

  /**
   * Factory method used to find or lookup a {@link State} by {@link String name}.
   *
   * @param name {@link String} containing the name of the {@link State} to find.
   * @return the {@link State} with the given {@link String name} or {@literal null}
   * if no {@link State} with the given {@link String name} could be found.
   * @see #getName()
   * @see #values()
   */
  public static State valueOfName(String name) {

    return Arrays.stream(values())
      .filter(state -> state.getName().equalsIgnoreCase(name))
      .findFirst()
      .orElse(null);
  }

  private final String abbreviation;
  private final String name;

  /**
   * Constructs a new instance of {@link State} initialized with
   * the given {@link String abbreviation} and {@link String name}.
   *
   * @param abbreviation {@link String} containing the {@link State} abbreviation.
   * @param name {@link String} containing the {@link State} name.
   */
  State(String abbreviation, String name) {
    this.abbreviation = abbreviation;
    this.name = name;
  }

  /**
   * Returns the {@link String abbreviation} of this {@link State}.
   *
   * @return the {@link String abbreviation} of this {@link State}.
   */
  public String getAbbreviation() {
    return this.abbreviation;
  }

  /**
   * Returns the {@link String name} of this {@link State}.
   *
   * @return the {@link String name} of this {@link State}.
   */
  public String getName() {
    return this.name;
  }

  /**
   * Returns a {@link String} representation of this {@link State}.
   *
   * @return a {@link String} describing this {@link State}.
   * @see java.lang.Object#toString()
   * @see java.lang.String
   */
  @Override
  public String toString() {
    return getName();
  }
}
