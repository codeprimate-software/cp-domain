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

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.cp.elements.util.CollectionUtils;

/**
 * Unit Tests for {@link Country}.
 *
 * @author John Blum
 * @see org.junit.jupiter.api.Test
 * @see org.cp.domain.geo.enums.Continent
 * @see org.cp.domain.geo.enums.Country
 * @since 0.1.0
 */
public class CountryTests {

  private Set<Country> testedCountries;

  @BeforeEach
  public void setup() {
    this.testedCountries = new HashSet<>();
  }

  private Country add(Country country) {
    assertThat(this.testedCountries.add(country)).isTrue();
    return country;
  }

  @Test
  public void allCountryContinentsAreCorrect() {

    assertThat(add(Country.AFGHANISTAN).getContinents()).containsExactly(Continent.ASIA);
    assertThat(add(Country.ALBANIA).getContinents()).containsExactly(Continent.EUROPE);
    assertThat(add(Country.ALGERIA).getContinents()).containsExactly(Continent.AFRICA);
    assertThat(add(Country.ANDORRA).getContinents()).containsExactly(Continent.EUROPE);
    assertThat(add(Country.ANGOLA).getContinents()).containsExactly(Continent.AFRICA);
    assertThat(add(Country.ANTIGUA_AND_BARBUDA).getContinents()).containsExactly(Continent.NORTH_AMERICA);
    assertThat(add(Country.ARGENTINA).getContinents()).containsExactly(Continent.SOUTH_AMERICA);
    assertThat(add(Country.ARMENIA).getContinents()).containsExactly(Continent.ASIA, Continent.EUROPE);
    assertThat(add(Country.AUSTRALIA).getContinents()).containsExactly(Continent.AUSTRALIA_AND_OCEANIA);
    assertThat(add(Country.AUSTRIA).getContinents()).containsExactly(Continent.EUROPE);
    assertThat(add(Country.AZERBAIJAN).getContinents()).containsExactly(Continent.ASIA, Continent.EUROPE);
    assertThat(add(Country.BAHAMAS).getContinents()).containsExactly(Continent.NORTH_AMERICA);
    assertThat(add(Country.BAHRAIN).getContinents()).containsExactly(Continent.ASIA);
    assertThat(add(Country.BANGLADESH).getContinents()).containsExactly(Continent.ASIA);
    assertThat(add(Country.BARBADOS).getContinents()).containsExactly(Continent.NORTH_AMERICA);
    assertThat(add(Country.BELARUS).getContinents()).containsExactly(Continent.EUROPE);
    assertThat(add(Country.BELGIUM).getContinents()).containsExactly(Continent.EUROPE);
    assertThat(add(Country.BELIZE).getContinents()).containsExactly(Continent.NORTH_AMERICA);
    assertThat(add(Country.BENIN).getContinents()).containsExactly(Continent.AFRICA);
    assertThat(add(Country.BHUTAN).getContinents()).containsExactly(Continent.ASIA);
    assertThat(add(Country.BOLIVIA).getContinents()).containsExactly(Continent.SOUTH_AMERICA);
    assertThat(add(Country.BOSNIA_AND_HERZEGOVINA).getContinents()).containsExactly(Continent.EUROPE);
    assertThat(add(Country.BOTSWANA).getContinents()).containsExactly(Continent.AFRICA);
    assertThat(add(Country.BRAZIL).getContinents()).containsExactly(Continent.SOUTH_AMERICA);
    assertThat(add(Country.BRUNEI_DARUSSALAM).getContinents()).containsExactly(Continent.ASIA);
    assertThat(add(Country.BULGARIA).getContinents()).containsExactly(Continent.EUROPE);
    assertThat(add(Country.BURKINA_FASO).getContinents()).containsExactly(Continent.AFRICA);
    assertThat(add(Country.BURUNDI).getContinents()).containsExactly(Continent.AFRICA);
    assertThat(add(Country.CABO_VERDE).getContinents()).containsExactly(Continent.AFRICA);
    assertThat(add(Country.CAMBODIA).getContinents()).containsExactly(Continent.ASIA);
    assertThat(add(Country.CAMEROON).getContinents()).containsExactly(Continent.AFRICA);
    assertThat(add(Country.CANADA).getContinents()).containsExactly(Continent.NORTH_AMERICA);
    assertThat(add(Country.CENTRAL_AFRICAN_REPUBLIC).getContinents()).containsExactly(Continent.AFRICA);
    assertThat(add(Country.CHAD).getContinents()).containsExactly(Continent.AFRICA);
    assertThat(add(Country.CHILE).getContinents()).containsExactly(Continent.SOUTH_AMERICA);
    assertThat(add(Country.CHINA).getContinents()).containsExactly(Continent.ASIA);
    assertThat(add(Country.COLOMBIA).getContinents()).containsExactly(Continent.SOUTH_AMERICA);
    assertThat(add(Country.COMOROS).getContinents()).containsExactly(Continent.AFRICA);
    assertThat(add(Country.DEMOCRATIC_REPUBLIC_OF_THE_CONGO).getContinents()).containsExactly(Continent.AFRICA);
    assertThat(add(Country.CONGO).getContinents()).containsExactly(Continent.AFRICA);
    assertThat(add(Country.COSTA_RICA).getContinents()).containsExactly(Continent.NORTH_AMERICA);
    assertThat(add(Country.COTE_D_IVOIRE).getContinents()).containsExactly(Continent.AFRICA);
    assertThat(add(Country.CROATIA).getContinents()).containsExactly(Continent.EUROPE);
    assertThat(add(Country.CUBA).getContinents()).containsExactly(Continent.NORTH_AMERICA);
    assertThat(add(Country.CYPRUS).getContinents()).containsExactly(Continent.ASIA, Continent.EUROPE);
    assertThat(add(Country.CZECHIA).getContinents()).containsExactly(Continent.EUROPE);
    assertThat(add(Country.DENMARK).getContinents()).containsExactly(Continent.EUROPE);
    assertThat(add(Country.DJIBOUTI).getContinents()).containsExactly(Continent.AFRICA);
    assertThat(add(Country.DOMINICA).getContinents()).containsExactly(Continent.NORTH_AMERICA);
    assertThat(add(Country.DOMINICAN_REPUBLIC).getContinents()).containsExactly(Continent.NORTH_AMERICA);
    assertThat(add(Country.ECUADOR).getContinents()).containsExactly(Continent.SOUTH_AMERICA);
    assertThat(add(Country.EGYPT).getContinents()).containsExactly(Continent.AFRICA);
    assertThat(add(Country.EL_SALVADOR).getContinents()).containsExactly(Continent.NORTH_AMERICA);
    assertThat(add(Country.EQUATORIAL_GUINEA).getContinents()).containsExactly(Continent.AFRICA);
    assertThat(add(Country.ERITREA).getContinents()).containsExactly(Continent.AFRICA);
    assertThat(add(Country.ESTONIA).getContinents()).containsExactly(Continent.EUROPE);
    assertThat(add(Country.ETHIOPIA).getContinents()).containsExactly(Continent.AFRICA);
    assertThat(add(Country.FIJI).getContinents()).containsExactly(Continent.AUSTRALIA_AND_OCEANIA);
    assertThat(add(Country.FINLAND).getContinents()).containsExactly(Continent.EUROPE);
    assertThat(add(Country.FRANCE).getContinents()).containsExactly(Continent.EUROPE);
    assertThat(add(Country.GABON).getContinents()).containsExactly(Continent.AFRICA);
    assertThat(add(Country.GAMBIA).getContinents()).containsExactly(Continent.AFRICA);
    assertThat(add(Country.GEORGIA).getContinents()).containsExactly(Continent.ASIA, Continent.EUROPE);
    assertThat(add(Country.GERMANY).getContinents()).containsExactly(Continent.EUROPE);
    assertThat(add(Country.GHANA).getContinents()).containsExactly(Continent.AFRICA);
    assertThat(add(Country.GREECE).getContinents()).containsExactly(Continent.EUROPE);
    assertThat(add(Country.GRENADA).getContinents()).containsExactly(Continent.NORTH_AMERICA);
    assertThat(add(Country.GUATEMALA).getContinents()).containsExactly(Continent.NORTH_AMERICA);
    assertThat(add(Country.GUINEA).getContinents()).containsExactly(Continent.AFRICA);
    assertThat(add(Country.GUINEA_BISSAU).getContinents()).containsExactly(Continent.AFRICA);
    assertThat(add(Country.GUYANA).getContinents()).containsExactly(Continent.SOUTH_AMERICA);
    assertThat(add(Country.HAITI).getContinents()).containsExactly(Continent.NORTH_AMERICA);
    assertThat(add(Country.HONDURAS).getContinents()).containsExactly(Continent.NORTH_AMERICA);
    assertThat(add(Country.HUNGARY).getContinents()).containsExactly(Continent.EUROPE);
    assertThat(add(Country.ICELAND).getContinents()).containsExactly(Continent.EUROPE);
    assertThat(add(Country.INDIA).getContinents()).containsExactly(Continent.ASIA);
    assertThat(add(Country.INDONESIA).getContinents()).containsExactly(Continent.ASIA);
    assertThat(add(Country.IRAN).getContinents()).containsExactly(Continent.ASIA);
    assertThat(add(Country.IRAQ).getContinents()).containsExactly(Continent.ASIA);
    assertThat(add(Country.IRELAND).getContinents()).containsExactly(Continent.EUROPE);
    assertThat(add(Country.ISRAEL).getContinents()).containsExactly(Continent.ASIA);
    assertThat(add(Country.ITALY).getContinents()).containsExactly(Continent.EUROPE);
    assertThat(add(Country.JAMAICA).getContinents()).containsExactly(Continent.NORTH_AMERICA);
    assertThat(add(Country.JAPAN).getContinents()).containsExactly(Continent.ASIA);
    assertThat(add(Country.JORDAN).getContinents()).containsExactly(Continent.ASIA);
    assertThat(add(Country.KAZAKHSTAN).getContinents()).containsExactly(Continent.ASIA, Continent.EUROPE);
    assertThat(add(Country.KENYA).getContinents()).containsExactly(Continent.AFRICA);
    assertThat(add(Country.KIRIBATI).getContinents()).containsExactly(Continent.AUSTRALIA_AND_OCEANIA);
    assertThat(add(Country.DEMOCRATIC_PEOPLES_REPUBLIC_OF_KOREA).getContinents()).containsExactly(Continent.ASIA);
    assertThat(add(Country.REPUBLIC_OF_KOREA).getContinents()).containsExactly(Continent.ASIA);
    assertThat(add(Country.KOSOVO).getContinents()).containsExactly(Continent.EUROPE);
    assertThat(add(Country.KUWAIT).getContinents()).containsExactly(Continent.ASIA);
    assertThat(add(Country.KYRGYZSTAN).getContinents()).containsExactly(Continent.ASIA);
    assertThat(add(Country.LAOS).getContinents()).containsExactly(Continent.ASIA);
    assertThat(add(Country.LATVIA).getContinents()).containsExactly(Continent.EUROPE);
    assertThat(add(Country.LEBANON).getContinents()).containsExactly(Continent.ASIA);
    assertThat(add(Country.LESOTHO).getContinents()).containsExactly(Continent.AFRICA);
    assertThat(add(Country.LIBERIA).getContinents()).containsExactly(Continent.AFRICA);
    assertThat(add(Country.LIBYA).getContinents()).containsExactly(Continent.AFRICA);
    assertThat(add(Country.LIECHTENSTEIN).getContinents()).containsExactly(Continent.EUROPE);
    assertThat(add(Country.LITHUANIA).getContinents()).containsExactly(Continent.EUROPE);
    assertThat(add(Country.LUXEMBOURG).getContinents()).containsExactly(Continent.EUROPE);
    assertThat(add(Country.MACEDONIA).getContinents()).containsExactly(Continent.EUROPE);
    assertThat(add(Country.MADAGASCAR).getContinents()).containsExactly(Continent.AFRICA);
    assertThat(add(Country.MALAWI).getContinents()).containsExactly(Continent.AFRICA);
    assertThat(add(Country.MALAYSIA).getContinents()).containsExactly(Continent.ASIA);
    assertThat(add(Country.MALDIVES).getContinents()).containsExactly(Continent.ASIA);
    assertThat(add(Country.MALI).getContinents()).containsExactly(Continent.AFRICA);
    assertThat(add(Country.MALTA).getContinents()).containsExactly(Continent.EUROPE);
    assertThat(add(Country.MARSHALL_ISLANDS).getContinents()).containsExactly(Continent.AUSTRALIA_AND_OCEANIA);
    assertThat(add(Country.MAURITANIA).getContinents()).containsExactly(Continent.AFRICA);
    assertThat(add(Country.MAURITIUS).getContinents()).containsExactly(Continent.AFRICA);
    assertThat(add(Country.MEXICO).getContinents()).containsExactly(Continent.NORTH_AMERICA);
    assertThat(add(Country.MICRONESIA).getContinents()).containsExactly(Continent.AUSTRALIA_AND_OCEANIA);
    assertThat(add(Country.MOLDOVA).getContinents()).containsExactly(Continent.EUROPE);
    assertThat(add(Country.MONACO).getContinents()).containsExactly(Continent.EUROPE);
    assertThat(add(Country.MONGOLIA).getContinents()).containsExactly(Continent.ASIA);
    assertThat(add(Country.MONTENEGRO).getContinents()).containsExactly(Continent.EUROPE);
    assertThat(add(Country.MOROCCO).getContinents()).containsExactly(Continent.AFRICA);
    assertThat(add(Country.MOZAMBIQUE).getContinents()).containsExactly(Continent.AFRICA);
    assertThat(add(Country.MYANMAR).getContinents()).containsExactly(Continent.ASIA);
    assertThat(add(Country.NAMIBIA).getContinents()).containsExactly(Continent.AFRICA);
    assertThat(add(Country.NAURU).getContinents()).containsExactly(Continent.AUSTRALIA_AND_OCEANIA);
    assertThat(add(Country.NEPAL).getContinents()).containsExactly(Continent.ASIA);
    assertThat(add(Country.NETHERLANDS).getContinents()).containsExactly(Continent.EUROPE);
    assertThat(add(Country.NEW_ZEALAND).getContinents()).containsExactly(Continent.AUSTRALIA_AND_OCEANIA);
    assertThat(add(Country.NICARAGUA).getContinents()).containsExactly(Continent.NORTH_AMERICA);
    assertThat(add(Country.NIGER).getContinents()).containsExactly(Continent.AFRICA);
    assertThat(add(Country.NIGERIA).getContinents()).containsExactly(Continent.AFRICA);
    assertThat(add(Country.NORWAY).getContinents()).containsExactly(Continent.EUROPE);
    assertThat(add(Country.OMAN).getContinents()).containsExactly(Continent.ASIA);
    assertThat(add(Country.PAKISTAN).getContinents()).containsExactly(Continent.ASIA);
    assertThat(add(Country.PALAU).getContinents()).containsExactly(Continent.AUSTRALIA_AND_OCEANIA);
    assertThat(add(Country.PALESTINE).getContinents()).containsExactly(Continent.ASIA);
    assertThat(add(Country.PANAMA).getContinents()).containsExactly(Continent.NORTH_AMERICA);
    assertThat(add(Country.PAPUA_NEW_GUINEA).getContinents()).containsExactly(Continent.AUSTRALIA_AND_OCEANIA);
    assertThat(add(Country.PARAGUAY).getContinents()).containsExactly(Continent.SOUTH_AMERICA);
    assertThat(add(Country.PERU).getContinents()).containsExactly(Continent.SOUTH_AMERICA);
    assertThat(add(Country.PHILIPPINES).getContinents()).containsExactly(Continent.ASIA);
    assertThat(add(Country.POLAND).getContinents()).containsExactly(Continent.EUROPE);
    assertThat(add(Country.PORTUGAL).getContinents()).containsExactly(Continent.EUROPE);
    assertThat(add(Country.QATAR).getContinents()).containsExactly(Continent.ASIA);
    assertThat(add(Country.ROMANIA).getContinents()).containsExactly(Continent.EUROPE);
    assertThat(add(Country.RUSSIA).getContinents()).containsExactly(Continent.ASIA, Continent.EUROPE);
    assertThat(add(Country.RWANDA).getContinents()).containsExactly(Continent.AFRICA);
    assertThat(add(Country.SAINT_KITTS_AND_NEVIS).getContinents()).containsExactly(Continent.NORTH_AMERICA);
    assertThat(add(Country.SAINT_LUCIA).getContinents()).containsExactly(Continent.NORTH_AMERICA);
    assertThat(add(Country.SAINT_VINCENT_AND_THE_GRENADINES).getContinents()).containsExactly(Continent.NORTH_AMERICA);
    assertThat(add(Country.SAMOA).getContinents()).containsExactly(Continent.AUSTRALIA_AND_OCEANIA);
    assertThat(add(Country.SAN_MARINO).getContinents()).containsExactly(Continent.EUROPE);
    assertThat(add(Country.SAO_TOME_AND_PRINCIPE).getContinents()).containsExactly(Continent.AFRICA);
    assertThat(add(Country.SAUDI_ARABIA).getContinents()).containsExactly(Continent.ASIA);
    assertThat(add(Country.SENEGAL).getContinents()).containsExactly(Continent.AFRICA);
    assertThat(add(Country.SERBIA).getContinents()).containsExactly(Continent.EUROPE);
    assertThat(add(Country.SEYCHELLES).getContinents()).containsExactly(Continent.AFRICA);
    assertThat(add(Country.SIERRA_LEONE).getContinents()).containsExactly(Continent.AFRICA);
    assertThat(add(Country.SINGAPORE).getContinents()).containsExactly(Continent.ASIA);
    assertThat(add(Country.SLOVAKIA).getContinents()).containsExactly(Continent.EUROPE);
    assertThat(add(Country.SLOVENIA).getContinents()).containsExactly(Continent.EUROPE);
    assertThat(add(Country.SOLOMON_ISLANDS).getContinents()).containsExactly(Continent.AUSTRALIA_AND_OCEANIA);
    assertThat(add(Country.SOMALIA).getContinents()).containsExactly(Continent.AFRICA);
    assertThat(add(Country.SOUTH_AFRICA).getContinents()).containsExactly(Continent.AFRICA);
    assertThat(add(Country.SOUTH_SUDAN).getContinents()).containsExactly(Continent.AFRICA);
    assertThat(add(Country.SPAIN).getContinents()).containsExactly(Continent.EUROPE);
    assertThat(add(Country.SRI_LANKA).getContinents()).containsExactly(Continent.ASIA);
    assertThat(add(Country.SUDAN).getContinents()).containsExactly(Continent.AFRICA);
    assertThat(add(Country.SURINAME).getContinents()).containsExactly(Continent.SOUTH_AMERICA);
    assertThat(add(Country.SWAZILAND).getContinents()).containsExactly(Continent.AFRICA);
    assertThat(add(Country.SWEDEN).getContinents()).containsExactly(Continent.EUROPE);
    assertThat(add(Country.SWITZERLAND).getContinents()).containsExactly(Continent.EUROPE);
    assertThat(add(Country.SYRIA).getContinents()).containsExactly(Continent.ASIA);
    assertThat(add(Country.TAIWAN).getContinents()).containsExactly(Continent.ASIA);
    assertThat(add(Country.TAJIKISTAN).getContinents()).containsExactly(Continent.ASIA);
    assertThat(add(Country.TANZANIA).getContinents()).containsExactly(Continent.AFRICA);
    assertThat(add(Country.THAILAND).getContinents()).containsExactly(Continent.ASIA);
    assertThat(add(Country.TIMOR_LESTE).getContinents()).containsExactly(Continent.ASIA);
    assertThat(add(Country.TOGO).getContinents()).containsExactly(Continent.AFRICA);
    assertThat(add(Country.TONGA).getContinents()).containsExactly(Continent.AUSTRALIA_AND_OCEANIA);
    assertThat(add(Country.TRINIDAD_AND_TOBAGO).getContinents()).containsExactly(Continent.NORTH_AMERICA);
    assertThat(add(Country.TUNISIA).getContinents()).containsExactly(Continent.AFRICA);
    assertThat(add(Country.TURKEY).getContinents()).containsExactly(Continent.ASIA, Continent.EUROPE);
    assertThat(add(Country.TURKMENISTAN).getContinents()).containsExactly(Continent.ASIA);
    assertThat(add(Country.TUVALU).getContinents()).containsExactly(Continent.AUSTRALIA_AND_OCEANIA);
    assertThat(add(Country.UGANDA).getContinents()).containsExactly(Continent.AFRICA);
    assertThat(add(Country.UKRAINE).getContinents()).containsExactly(Continent.EUROPE);
    assertThat(add(Country.UNITED_ARAB_EMIRATES).getContinents()).containsExactly(Continent.ASIA);
    assertThat(add(Country.UNITED_KINGDOM).getContinents()).containsExactly(Continent.EUROPE);
    assertThat(add(Country.UNITED_STATES_OF_AMERICA).getContinents()).containsExactly(Continent.NORTH_AMERICA);
    assertThat(add(Country.URUGUAY).getContinents()).containsExactly(Continent.SOUTH_AMERICA);
    assertThat(add(Country.UZBEKISTAN).getContinents()).containsExactly(Continent.ASIA);
    assertThat(add(Country.VANUATU).getContinents()).containsExactly(Continent.AUSTRALIA_AND_OCEANIA);
    assertThat(add(Country.VATICAN_CITY).getContinents()).containsExactly(Continent.EUROPE);
    assertThat(add(Country.VENEZUELA).getContinents()).containsExactly(Continent.SOUTH_AMERICA);
    assertThat(add(Country.VIETNAM).getContinents()).containsExactly(Continent.ASIA);
    assertThat(add(Country.YEMEN).getContinents()).containsExactly(Continent.ASIA);
    assertThat(add(Country.ZAMBIA).getContinents()).containsExactly(Continent.AFRICA);
    assertThat(add(Country.ZIMBABWE).getContinents()).containsExactly(Continent.AFRICA);
    assertThat(add(Country.UNKNOWN).getContinents()).isEmpty();
    assertThat(this.testedCountries).contains(Country.values());
  }

  @Test
  public void countriesInAfrica() {

    Set<Country> expectedAfricanCountries = CollectionUtils.asSet(
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

    Set<Country> actualAfricanCountries = Country.byContinent(Continent.AFRICA);

    assertThat(actualAfricanCountries).isNotEmpty();
    assertThat(actualAfricanCountries).containsAll(expectedAfricanCountries);
    assertThat(expectedAfricanCountries).containsAll(actualAfricanCountries);
  }

  @Test
  public void countriesInAntarctica() {

    Set<Country> countries = Country.byContinent(Continent.ANTARCTICA);

    assertThat(countries).isNotNull();
    assertThat(countries).isEmpty();
  }

  @Test
  public void countriesInAsia() {

    Set<Country> expectedAsianCounties = CollectionUtils.asSet(
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

    Set<Country> actualAsianCountries = Country.byContinent(Continent.ASIA);

    assertThat(actualAsianCountries).isNotEmpty();
    assertThat(actualAsianCountries).containsAll(expectedAsianCounties);
    assertThat(expectedAsianCounties).containsAll(actualAsianCountries);
  }

  @Test
  public void countriesInAustraliaAndOceania() {

    Set<Country> expectedAustralianAndOceaniaCountries = CollectionUtils.asSet(
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

    Set<Country> actualAustralianAndOceaniaCountries = Country.byContinent(Continent.AUSTRALIA_AND_OCEANIA);

    assertThat(actualAustralianAndOceaniaCountries).isNotEmpty();
    assertThat(actualAustralianAndOceaniaCountries).containsAll(expectedAustralianAndOceaniaCountries);
    assertThat(expectedAustralianAndOceaniaCountries).containsAll(actualAustralianAndOceaniaCountries);
  }

  @Test
  public void countriesInEurope() {

    Set<Country> expectedEuropeanCountries = CollectionUtils.asSet(
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

    Set<Country> actualEuropeanCounties = Country.byContinent(Continent.EUROPE);

    assertThat(actualEuropeanCounties).isNotEmpty();
    assertThat(actualEuropeanCounties).containsAll(expectedEuropeanCountries);
    assertThat(expectedEuropeanCountries).containsAll(actualEuropeanCounties);
  }

  @Test
  public void countriesInNorthAmerica() {

    Set<Country> expectedNorthAmericanCountries = CollectionUtils.asSet(
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

    Set<Country> actualNorthAmericanCountries = Country.byContinent(Continent.NORTH_AMERICA);

    assertThat(actualNorthAmericanCountries).isNotEmpty();
    assertThat(actualNorthAmericanCountries).containsAll(expectedNorthAmericanCountries);
    assertThat(expectedNorthAmericanCountries).containsAll(actualNorthAmericanCountries);
  }

  @Test
  public void countriesInSouthAmerica() {

    Set<Country> expectedSouthAmericanCountries = CollectionUtils.asSet(
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

    Set<Country> actualSouthAmericanCountries = Country.byContinent(Continent.SOUTH_AMERICA);

    assertThat(actualSouthAmericanCountries).isNotEmpty();
    assertThat(actualSouthAmericanCountries).containsAll(expectedSouthAmericanCountries);
    assertThat(expectedSouthAmericanCountries).containsAll(actualSouthAmericanCountries);
  }

  @Test
  public void countriesInUnknown() {

    Set<Country> unknownCountries = Country.byContinent(Continent.UNKNOWN);

    assertThat(unknownCountries).isNotNull();
    assertThat(unknownCountries).isEmpty();
  }

  @Test
  public void countryIsoNumericThreeDigitCodesAreUnique() {

    Set<String> isoNumericThreeDigitCodes = Arrays.stream(Country.values())
      .map(Country::getIsoNumericThreeDigitCode)
      .collect(Collectors.toSet());

    assertThat(isoNumericThreeDigitCodes).hasSize(Country.values().length);
  }

  @Test
  public void countryIsoAlphaThreeLetterCodesAreUnique() {

    Set<String> isoAlphaThreeLetterCodes = Arrays.stream(Country.values())
      .map(Country::getIsoAlphaThreeLetterCode)
      .collect(Collectors.toSet());

    assertThat(isoAlphaThreeLetterCodes).hasSize(Country.values().length);
  }

  @Test
  public void countryIsoAlphaTwoLetterCodesAreUnique() {

    Set<String> isoAlphaTwoLetterCodes = Arrays.stream(Country.values())
      .map(Country::getIsoAlphaTwoLetterCode)
      .collect(Collectors.toSet());

    assertThat(isoAlphaTwoLetterCodes).hasSize(Country.values().length);
  }

  @Test
  public void byIsoNumericThreeDigitCodeIsCorrect() {

    Arrays.stream(Country.values()).forEach(country ->
      assertThat(Country.byIsoNumeric(country.getIsoNumericThreeDigitCode())).isEqualTo(country));
  }

  @Test
  public void byIsoAlphaThreeLetterCodeIsCorrect() {

    Arrays.stream(Country.values()).forEach(country ->
      assertThat(Country.byIsoThree(country.getIsoAlphaThreeLetterCode())).isEqualTo(country));
  }

  @Test
  public void byIsoAlphaTwoLetterCodeIsCorrect() {

    Arrays.stream(Country.values()).forEach(country ->
      assertThat(Country.byIsoTwo(country.getIsoAlphaTwoLetterCode())).isEqualTo(country));
  }

  @Test
  public void isOnContinentReturnsTrue() {

    assertThat(Country.EGYPT.isOnContinent(Continent.AFRICA)).isTrue();
    assertThat(Country.RUSSIA.isOnContinent(Continent.ASIA)).isTrue();
    assertThat(Country.AUSTRALIA.isOnContinent(Continent.AUSTRALIA_AND_OCEANIA)).isTrue();
    assertThat(Country.RUSSIA.isOnContinent(Continent.EUROPE)).isTrue();
    assertThat(Country.UNITED_KINGDOM.isOnContinent(Continent.EUROPE)).isTrue();
    assertThat(Country.UNITED_STATES_OF_AMERICA.isOnContinent(Continent.NORTH_AMERICA)).isTrue();
    assertThat(Country.COLOMBIA.isOnContinent(Continent.SOUTH_AMERICA)).isTrue();
  }

  @Test
  public void isOnContinentReturnsFalse() {

    assertThat(Country.COLOMBIA.isOnContinent(Continent.AFRICA)).isFalse();
    assertThat(Country.UNITED_STATES_OF_AMERICA.isOnContinent(Continent.ANTARCTICA)).isFalse();
    assertThat(Country.AUSTRALIA.isOnContinent(Continent.ASIA)).isFalse();
    assertThat(Country.JAPAN.isOnContinent(Continent.AUSTRALIA_AND_OCEANIA)).isFalse();
    assertThat(Country.AUSTRALIA.isOnContinent(Continent.EUROPE)).isFalse();
    assertThat(Country.BRAZIL.isOnContinent(Continent.NORTH_AMERICA)).isFalse();
    assertThat(Country.CANADA.isOnContinent(Continent.SOUTH_AMERICA)).isFalse();
    assertThat(Country.UNITED_STATES_OF_AMERICA.isOnContinent(Continent.UNKNOWN)).isFalse();
  }
}
