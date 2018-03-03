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

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;

import org.cp.elements.util.CollectionUtils;
import org.junit.Test;

/**
 * Unit tests for {@link Continent}.
 *
 * @author John Blum
 * @see org.junit.Test
 * @see org.cp.domain.geo.enums.Continent
 * @see org.cp.domain.geo.enums.Country
 * @since 1.0.0
 */
public class ContinentTests {

  @Test
  public void countriesInAfrica() {

    Set<Country> expectedCountriesInAfrica = CollectionUtils.asSet(
      Country.ALGERIA,
      Country.ANGOLA,
      Country.BENIN,
      Country.BOTSWANA,
      Country.BURKINA_FASO,
      Country.BURUNDI,
      Country.CABO_VERDE,
      Country.CAMEROON,
      Country.CENTRAL_AFRICAN_REPUBLIC,
      Country.CHAD,
      Country.COMOROS,
      Country.DEMOCRATIC_REPUBLIC_OF_THE_CONGO,
      Country.CONGO,
      Country.COTE_D_IVOIRE,
      Country.DJIBOUTI,
      Country.EGYPT,
      Country.EQUATORIAL_GUINEA,
      Country.ERITREA,
      Country.ETHIOPIA,
      Country.GABON,
      Country.GAMBIA,
      Country.GHANA,
      Country.GUINEA,
      Country.GUINEA_BISSAU,
      Country.KENYA,
      Country.LESOTHO,
      Country.LIBERIA,
      Country.LIBYA,
      Country.MADAGASCAR,
      Country.MALAWI,
      Country.MALI,
      Country.MAURITANIA,
      Country.MAURITIUS,
      Country.MOROCCO,
      Country.MOZAMBIQUE,
      Country.NAMIBIA,
      Country.NIGER,
      Country.NIGERIA,
      Country.RWANDA,
      Country.SAO_TOME_AND_PRINCIPE,
      Country.SENEGAL,
      Country.SEYCHELLES,
      Country.SIERRA_LEONE,
      Country.SOMALIA,
      Country.SOUTH_AFRICA,
      Country.SOUTH_SUDAN,
      Country.SUDAN,
      Country.SWAZILAND,
      Country.TANZANIA,
      Country.TOGO,
      Country.TUNISIA,
      Country.UGANDA,
      Country.ZAMBIA,
      Country.ZIMBABWE
    );

    Set<Country> actualCountriesInAfrica = Continent.AFRICA.countries();

    assertThat(actualCountriesInAfrica).isNotNull();
    assertThat(actualCountriesInAfrica).hasSize(expectedCountriesInAfrica.size());
    assertThat(actualCountriesInAfrica).containsAll(expectedCountriesInAfrica);
  }

  @Test
  public void countriesInAntarctica() {

    Set<Country> countriesInAntarctica = Continent.ANTARCTICA.countries();

    assertThat(countriesInAntarctica).isNotNull();
    assertThat(countriesInAntarctica).isEmpty();
  }

  @Test
  public void countriesInAsia() {

    Set<Country> expectedCountriesInAsia = CollectionUtils.asSet(
      Country.AFGHANISTAN,
      Country.ARMENIA,
      Country.AZERBAIJAN,
      Country.BAHRAIN,
      Country.BANGLADESH,
      Country.BHUTAN,
      Country.BRUNEI_DARUSSALAM,
      Country.CAMBODIA,
      Country.CHINA,
      Country.CYPRUS,
      Country.GEORGIA,
      Country.INDIA,
      Country.INDONESIA,
      Country.IRAN,
      Country.IRAQ,
      Country.ISRAEL,
      Country.JAPAN,
      Country.JORDAN,
      Country.KAZAKHSTAN,
      Country.DEMOCRATIC_PEOPLES_REPUBLIC_OF_KOREA,
      Country.REPUBLIC_OF_KOREA,
      Country.KUWAIT,
      Country.KYRGYZSTAN,
      Country.LAOS,
      Country.LEBANON,
      Country.MALAYSIA,
      Country.MALDIVES,
      Country.MONGOLIA,
      Country.MYANMAR,
      Country.NEPAL,
      Country.OMAN,
      Country.PAKISTAN,
      Country.PALESTINE,
      Country.PHILIPPINES,
      Country.QATAR,
      Country.RUSSIA,
      Country.SAUDI_ARABIA,
      Country.SINGAPORE,
      Country.SRI_LANKA,
      Country.SYRIA,
      Country.TAIWAN,
      Country.TAJIKISTAN,
      Country.THAILAND,
      Country.TIMOR_LESTE,
      Country.TURKEY,
      Country.TURKMENISTAN,
      Country.UNITED_ARAB_EMIRATES,
      Country.UZBEKISTAN,
      Country.VIETNAM,
      Country.YEMEN
    );

    Set<Country> actualCountriesInAsia = Continent.ASIA.countries();

    assertThat(actualCountriesInAsia).isNotNull();
    assertThat(actualCountriesInAsia).hasSize(expectedCountriesInAsia.size());
    assertThat(actualCountriesInAsia).containsAll(expectedCountriesInAsia);
  }

  @Test
  public void countriesInAustraliaAndOceania() {

    Set<Country> expectedCountriesInAustraliaAndOceania = CollectionUtils.asSet(
      Country.AUSTRALIA,
      Country.FIJI,
      Country.KIRIBATI,
      Country.MARSHALL_ISLANDS,
      Country.MICRONESIA,
      Country.NAURU,
      Country.NEW_ZEALAND,
      Country.PALAU,
      Country.PAPUA_NEW_GUINEA,
      Country.SAMOA,
      Country.SOLOMON_ISLANDS,
      Country.TONGA,
      Country.TUVALU,
      Country.VANUATU
    );

    Set<Country> actualCountriesInAustraliaAndOceania = Continent.AUSTRALIA_AND_OCEANIA.countries();

    assertThat(actualCountriesInAustraliaAndOceania).isNotNull();
    assertThat(actualCountriesInAustraliaAndOceania).hasSize(expectedCountriesInAustraliaAndOceania.size());
    assertThat(actualCountriesInAustraliaAndOceania).containsAll(expectedCountriesInAustraliaAndOceania);
  }

  @Test
  public void countriesInEurope() {

    Set<Country> expectedCountriesInEurope = CollectionUtils.asSet(
      Country.ALBANIA,
      Country.ANDORRA,
      Country.ARMENIA,
      Country.AUSTRIA,
      Country.AZERBAIJAN,
      Country.BELARUS,
      Country.BELGIUM,
      Country.BOSNIA_AND_HERZEGOVINA,
      Country.BULGARIA,
      Country.CROATIA,
      Country.CYPRUS,
      Country.CZECHIA,
      Country.DENMARK,
      Country.ESTONIA,
      Country.FINLAND,
      Country.FRANCE,
      Country.GEORGIA,
      Country.GERMANY,
      Country.GREECE,
      Country.HUNGARY,
      Country.ICELAND,
      Country.IRELAND,
      Country.ITALY,
      Country.KAZAKHSTAN,
      Country.KOSOVO,
      Country.LATVIA,
      Country.LIECHTENSTEIN,
      Country.LITHUANIA,
      Country.LUXEMBOURG,
      Country.MACEDONIA,
      Country.MALTA,
      Country.MOLDOVA,
      Country.MONACO,
      Country.MONTENEGRO,
      Country.NETHERLANDS,
      Country.NORWAY,
      Country.POLAND,
      Country.PORTUGAL,
      Country.ROMANIA,
      Country.RUSSIA,
      Country.SAN_MARINO,
      Country.SERBIA,
      Country.SLOVAKIA,
      Country.SLOVENIA,
      Country.SPAIN,
      Country.SWEDEN,
      Country.SWITZERLAND,
      Country.TURKEY,
      Country.UKRAINE,
      Country.UNITED_KINGDOM,
      Country.VATICAN_CITY
    );

    Set<Country> actualCountriesInEurope = Continent.EUROPE.countries();

    assertThat(actualCountriesInEurope).isNotNull();
    assertThat(actualCountriesInEurope).hasSize(expectedCountriesInEurope.size());
    assertThat(actualCountriesInEurope).containsAll(expectedCountriesInEurope);
  }

  @Test
  public void countriesInNorthAmerica() {

    Set<Country> expectedCountriesInNorthAmerica = CollectionUtils.asSet(
      Country.ANTIGUA_AND_BARBUDA,
      Country.BAHAMAS,
      Country.BARBADOS,
      Country.BELIZE,
      Country.CANADA,
      Country.COSTA_RICA,
      Country.CUBA,
      Country.DOMINICA,
      Country.DOMINICAN_REPUBLIC,
      Country.EL_SALVADOR,
      Country.GRENADA,
      Country.GUATEMALA,
      Country.HAITI,
      Country.HONDURAS,
      Country.JAMAICA,
      Country.MEXICO,
      Country.NICARAGUA,
      Country.PANAMA,
      Country.SAINT_KITTS_AND_NEVIS,
      Country.SAINT_LUCIA,
      Country.SAINT_VINCENT_AND_THE_GRENADINES,
      Country.TRINIDAD_AND_TOBAGO,
      Country.UNITED_STATES_OF_AMERICA
    );

    Set<Country> actualCountriesInNorthAmerica = Continent.NORTH_AMERICA.countries();

    assertThat(actualCountriesInNorthAmerica).isNotNull();
    assertThat(actualCountriesInNorthAmerica).hasSize(expectedCountriesInNorthAmerica.size());
    assertThat(actualCountriesInNorthAmerica).containsAll(expectedCountriesInNorthAmerica);
  }

  @Test
  public void countriesInSouthAmerica() {

    Set<Country> expectedCountriesInSouthAmerica = CollectionUtils.asSet(
      Country.ARGENTINA,
      Country.BOLIVIA,
      Country.BRAZIL,
      Country.CHILE,
      Country.COLOMBIA,
      Country.ECUADOR,
      Country.GUYANA,
      Country.PARAGUAY,
      Country.PERU,
      Country.SURINAME,
      Country.URUGUAY,
      Country.VENEZUELA
    );

    Set<Country> actualCountriesInSouthAmerica = Continent.SOUTH_AMERICA.countries();

    assertThat(actualCountriesInSouthAmerica).isNotNull();
    assertThat(actualCountriesInSouthAmerica).hasSize(expectedCountriesInSouthAmerica.size());
    assertThat(actualCountriesInSouthAmerica).containsAll(expectedCountriesInSouthAmerica);
  }

  @Test
  public void countiesInUnknown() {

    Set<Country> countriesInUnknown = Continent.UNKNOWN.countries();

    assertThat(countriesInUnknown).isNotNull();
    assertThat(countriesInUnknown).isEmpty();
  }

  @Test
  public void toStringReturnsFriendlyName() {

    assertThat(Continent.AFRICA.toString()).isEqualTo("Africa");
    assertThat(Continent.ANTARCTICA.toString()).isEqualTo("Antarctica");
    assertThat(Continent.ASIA.toString()).isEqualTo("Asia");
    assertThat(Continent.AUSTRALIA_AND_OCEANIA.toString()).isEqualTo("Australia & Oceania");
    assertThat(Continent.EUROPE.toString()).isEqualTo("Europe");
    assertThat(Continent.NORTH_AMERICA.toString()).isEqualTo("North America");
    assertThat(Continent.SOUTH_AMERICA.toString()).isEqualTo("South America");
    assertThat(Continent.UNKNOWN.toString()).isEqualTo("Unknown");
  }
}
