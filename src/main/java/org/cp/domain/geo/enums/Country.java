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
package org.cp.domain.geo.enums;

import static org.cp.elements.lang.RuntimeExceptionsFactory.newIllegalArgumentException;

import java.util.Arrays;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.cp.elements.lang.annotation.NotNull;
import org.cp.elements.lang.annotation.NullSafe;
import org.cp.elements.lang.annotation.Nullable;

/**
 * {@link Enum Enumeration} of all the {@literal Countries} in the {@literal World}.
 *
 * @author John Blum
 * @see org.cp.domain.geo.enums.Continent
 * @see <a href="https://en.wikipedia.org/wiki/Country_code">Country Code - Wikipedia</a>
 * @see <a href="https://en.wikipedia.org/wiki/ISO_3166-1">ISO 3166-1 - Wikipedia</a>
 * @see <a href="https://www.countries-ofthe-world.com/all-countries.html">COUNTRIES-of-the-WORLD.COM</a>
 * @see <a href="https://countrycode.org/">countrycode.org</a>
 * @since 0.1.0
 */
@SuppressWarnings("unused")
public enum Country {

  AFGHANISTAN("AF", "AFG", "004", Continent.ASIA),
  ALBANIA("AL", "ALB", "248", Continent.EUROPE),
  ALGERIA("DZ", "DZA", "012", Continent.AFRICA),
  ANDORRA("AD", "AND", "020", Continent.EUROPE),
  ANGOLA("AO", "AGO", "024", Continent.AFRICA),
  ANTIGUA_AND_BARBUDA("AG", "ATG", "028", Continent.NORTH_AMERICA),
  ARGENTINA("AR", "ARG", "032", Continent.SOUTH_AMERICA),
  ARMENIA("AM", "ARM", "051", Continent.ASIA, Continent.EUROPE),
  AUSTRALIA("AU", "AUS", "036", Continent.AUSTRALIA_AND_OCEANIA),
  AUSTRIA("AT", "AUT", "040", Continent.EUROPE),
  AZERBAIJAN("AZ", "AZE", "031", Continent.ASIA, Continent.EUROPE),
  BAHAMAS("BS", "BHS", "044", Continent.NORTH_AMERICA),
  BAHRAIN("BH", "BHR", "048", Continent.ASIA),
  BANGLADESH("BD", "BGD", "050", Continent.ASIA),
  BARBADOS("BB", "BRB", "052", Continent.NORTH_AMERICA),
  BELARUS("BY", "BLR", "112", Continent.EUROPE),
  BELGIUM("BE", "BEL", "056", Continent.EUROPE),
  BELIZE("BZ", "BLZ", "084", Continent.NORTH_AMERICA),
  BENIN("BJ", "BEN", "204", Continent.AFRICA),
  BHUTAN("BT", "BTN", "064", Continent.ASIA),
  BOLIVIA("BO", "BOL", "068", Continent.SOUTH_AMERICA),
  BOSNIA_AND_HERZEGOVINA("BA", "BIH", "070", Continent.EUROPE),
  BOTSWANA("BW", "BWA", "072", Continent.AFRICA),
  BRAZIL("BR", "BRA", "076", Continent.SOUTH_AMERICA),
  BRUNEI_DARUSSALAM("BN", "BRN", "096", Continent.ASIA),
  BULGARIA("BG", "BGR", "100", Continent.EUROPE),
  BURKINA_FASO("BF", "BFA", "854", Continent.AFRICA),
  BURUNDI("BI", "BDI", "108", Continent.AFRICA),
  CABO_VERDE("CV", "CPV", "132", Continent.AFRICA),
  CAMBODIA("KH", "KHM", "116", Continent.ASIA),
  CAMEROON("CM", "CMR", "120", Continent.AFRICA),
  CANADA("CA", "CAN", "124", Continent.NORTH_AMERICA),
  CENTRAL_AFRICAN_REPUBLIC("CF", "CAF", "140", Continent.AFRICA),
  CHAD("TD", "TCD", "148", Continent.AFRICA),
  CHILE("CL", "CHL", "152", Continent.SOUTH_AMERICA),
  CHINA("CN", "CHN", "156", Continent.ASIA),
  COLOMBIA("CO", "COL", "170", Continent.SOUTH_AMERICA),
  COMOROS("KM", "COM", "174", Continent.AFRICA),
  CONGO("CG", "COG", "178", Continent.AFRICA),
  DEMOCRATIC_REPUBLIC_OF_THE_CONGO("CD", "COD", "180", Continent.AFRICA),
  COSTA_RICA("CR", "CRI", "188", Continent.NORTH_AMERICA),
  COTE_D_IVOIRE("CI", "CIV", "384", Continent.AFRICA),
  CROATIA("HR", "HRV", "191", Continent.EUROPE),
  CUBA("CU", "CUB", "192", Continent.NORTH_AMERICA),
  CYPRUS("CY", "CYP", "196", Continent.ASIA, Continent.EUROPE),
  CZECHIA("CZ", "CZE", "203", Continent.EUROPE),
  DENMARK("DK", "DNK", "208", Continent.EUROPE),
  DJIBOUTI("DJ", "DJI", "262", Continent.AFRICA),
  DOMINICA("DM", "DMA", "212", Continent.NORTH_AMERICA),
  DOMINICAN_REPUBLIC("DO", "DOM", "214", Continent.NORTH_AMERICA),
  ECUADOR("EC", "ECU", "218", Continent.SOUTH_AMERICA),
  EGYPT("EG", "EGY", "818", Continent.AFRICA),
  EL_SALVADOR("SV", "SLV", "222", Continent.NORTH_AMERICA),
  EQUATORIAL_GUINEA("GQ", "GNQ", "226", Continent.AFRICA),
  ERITREA("ER", "ERI", "232", Continent.AFRICA),
  ESTONIA("EE", "EST", "233", Continent.EUROPE),
  ETHIOPIA("ET", "ETH", "231", Continent.AFRICA),
  FIJI("FJ", "FJI", "242", Continent.AUSTRALIA_AND_OCEANIA),
  FINLAND("FI", "FIN", "246", Continent.EUROPE),
  FRANCE("FR", "FRA", "250", Continent.EUROPE),
  GABON("GA", "GAB", "266", Continent.AFRICA),
  GAMBIA("GM", "GMB", "270", Continent.AFRICA),
  GEORGIA("GE", "GEO", "268", Continent.ASIA, Continent.EUROPE),
  GERMANY("DE", "DEU", "276", Continent.EUROPE),
  GHANA("GH", "GHA", "288", Continent.AFRICA),
  GREECE("GR", "GRC", "300", Continent.EUROPE),
  GRENADA("GD", "GRD", "308", Continent.NORTH_AMERICA),
  GUATEMALA("GT", "GRM", "320", Continent.NORTH_AMERICA),
  GUINEA("GN", "GIN", "324", Continent.AFRICA),
  GUINEA_BISSAU("GW", "GNB", "624", Continent.AFRICA),
  GUYANA("GY", "GUY", "328", Continent.SOUTH_AMERICA),
  HAITI("HT", "HTI", "332", Continent.NORTH_AMERICA),
  HONDURAS("HN", "HND", "340", Continent.NORTH_AMERICA),
  HUNGARY("HU", "HUN", "348", Continent.EUROPE),
  ICELAND("IS", "ISL", "352", Continent.EUROPE),
  INDIA("IN", "IND", "356", Continent.ASIA),
  INDONESIA("ID", "IDN", "360", Continent.ASIA),
  IRAN("IR", "IRN", "364", Continent.ASIA),
  IRAQ("IQ", "IRQ", "368", Continent.ASIA),
  IRELAND("IE", "IRL", "372", Continent.EUROPE),
  ISRAEL("IL", "ISR", "376", Continent.ASIA),
  ITALY("IT", "ITA", "380", Continent.EUROPE),
  JAMAICA("JM", "JAM", "388", Continent.NORTH_AMERICA),
  JAPAN("JP", "JPN", "392", Continent.ASIA),
  JORDAN("JO", "JOR", "400", Continent.ASIA),
  KAZAKHSTAN("KZ", "KAZ", "398", Continent.ASIA, Continent.EUROPE),
  KENYA("KE", "KEN", "404", Continent.AFRICA),
  KIRIBATI("KI", "KIR", "296", Continent.AUSTRALIA_AND_OCEANIA),
  DEMOCRATIC_PEOPLES_REPUBLIC_OF_KOREA("KP", "PRK", "408", Continent.ASIA),
  REPUBLIC_OF_KOREA("KR", "KOR", "410", Continent.ASIA),
  KOSOVO("XK", "UNK", "383", Continent.EUROPE),
  KUWAIT("KW", "KWT", "414", Continent.ASIA),
  KYRGYZSTAN("KG", "KGZ", "417", Continent.ASIA),
  LAOS("LA", "LAO", "418", Continent.ASIA),
  LATVIA("LV", "LVA", "428", Continent.EUROPE),
  LEBANON("LB", "LBN", "422", Continent.ASIA),
  LESOTHO("LS", "LSO", "426", Continent.AFRICA),
  LIBERIA("LR", "LBR", "430", Continent.AFRICA),
  LIBYA("LY", "LBY", "434", Continent.AFRICA),
  LIECHTENSTEIN("LI", "LIE", "438", Continent.EUROPE),
  LITHUANIA("LT", "LTU", "440", Continent.EUROPE),
  LUXEMBOURG("LU", "LUX", "442", Continent.EUROPE),
  MACEDONIA("MK", "MKD", "807", Continent.EUROPE),
  MADAGASCAR("MG", "MDG", "450", Continent.AFRICA),
  MALAWI("MW", "MWI", "454", Continent.AFRICA),
  MALAYSIA("MY", "MYS", "458", Continent.ASIA),
  MALDIVES("MV", "MDV", "462", Continent.ASIA),
  MALI("ML", "MLI", "466", Continent.AFRICA),
  MALTA("MT", "MLT", "470", Continent.EUROPE),
  MARSHALL_ISLANDS("MH", "MHL", "584", Continent.AUSTRALIA_AND_OCEANIA),
  MAURITANIA("MR", "MRT", "478", Continent.AFRICA),
  MAURITIUS("MU", "MUS", "480", Continent.AFRICA),
  MEXICO("MX", "MEX", "484", Continent.NORTH_AMERICA),
  MICRONESIA("FM", "FSM", "583", Continent.AUSTRALIA_AND_OCEANIA),
  MOLDOVA("MD", "MDA", "498", Continent.EUROPE),
  MONACO("MC", "MCO", "492", Continent.EUROPE),
  MONGOLIA("MN", "MNG", "496", Continent.ASIA),
  MONTENEGRO("ME", "MNE", "499", Continent.EUROPE),
  MOROCCO("MA", "MAR", "504", Continent.AFRICA),
  MOZAMBIQUE("MZ", "MOZ", "508", Continent.AFRICA),
  MYANMAR("MM", "MMR", "104", Continent.ASIA),
  NAMIBIA("NA", "NAM", "516", Continent.AFRICA),
  NAURU("NR", "NRU", "520", Continent.AUSTRALIA_AND_OCEANIA),
  NEPAL("NP", "NPL", "524", Continent.ASIA),
  NETHERLANDS("NL", "NLD", "528", Continent.EUROPE),
  NEW_ZEALAND("NZ", "NZL", "554", Continent.AUSTRALIA_AND_OCEANIA),
  NICARAGUA("NI", "NIC", "558", Continent.NORTH_AMERICA),
  NIGER("NE", "NER", "562", Continent.AFRICA),
  NIGERIA("NG", "NGA", "566", Continent.AFRICA),
  NORWAY("NO", "NOR", "578", Continent.EUROPE),
  OMAN("OM", "OMN", "512", Continent.ASIA),
  PAKISTAN("PK", "PAK", "586", Continent.ASIA),
  PALAU("PW", "PLW", "585", Continent.AUSTRALIA_AND_OCEANIA),
  PALESTINE("PS", "PSE", "275", Continent.ASIA),
  PANAMA("PA", "PAN", "591", Continent.NORTH_AMERICA),
  PAPUA_NEW_GUINEA("PG", "PNG", "598", Continent.AUSTRALIA_AND_OCEANIA),
  PARAGUAY("PY", "PRY", "600", Continent.SOUTH_AMERICA),
  PERU("PE", "PER", "604", Continent.SOUTH_AMERICA),
  PHILIPPINES("PH", "PHL", "608", Continent.ASIA),
  POLAND("PL", "POL", "616", Continent.EUROPE),
  PORTUGAL("PT", "PRT", "620", Continent.EUROPE),
  QATAR("QA", "QAT", "634", Continent.ASIA),
  ROMANIA("RO", "ROU", "642", Continent.EUROPE),
  RUSSIA("RU", "RUS", "643", Continent.ASIA, Continent.EUROPE),
  RWANDA("RW", "RWA", "646", Continent.AFRICA),
  SAINT_KITTS_AND_NEVIS("KN", "KNA", "659", Continent.NORTH_AMERICA),
  SAINT_LUCIA("LC", "LCA", "662", Continent.NORTH_AMERICA),
  SAINT_VINCENT_AND_THE_GRENADINES("VC", "VCT", "670", Continent.NORTH_AMERICA),
  SAMOA("WS", "WSM", "882", Continent.AUSTRALIA_AND_OCEANIA),
  SAN_MARINO("SM", "SMR", "674", Continent.EUROPE),
  SAO_TOME_AND_PRINCIPE("ST", "STP", "678", Continent.AFRICA),
  SAUDI_ARABIA("SA", "SAU", "682", Continent.ASIA),
  SENEGAL("SN", "SEN", "686", Continent.AFRICA),
  SERBIA("RS", "SRB", "688", Continent.EUROPE),
  SEYCHELLES("SC", "SYC", "690", Continent.AFRICA),
  SIERRA_LEONE("SL", "SLE", "694", Continent.AFRICA),
  SINGAPORE("SG", "SGP", "702", Continent.ASIA),
  SLOVAKIA("SK", "SVK", "703", Continent.EUROPE),
  SLOVENIA("SI", "SVN", "705", Continent.EUROPE),
  SOLOMON_ISLANDS("SB", "SLB", "090", Continent.AUSTRALIA_AND_OCEANIA),
  SOMALIA("SO", "SOM", "706", Continent.AFRICA),
  SOUTH_AFRICA("ZA", "ZAF", "710", Continent.AFRICA),
  SOUTH_SUDAN("SS", "SSD", "728", Continent.AFRICA),
  SPAIN("ES", "ESP", "724", Continent.EUROPE),
  SRI_LANKA("LK", "LKA", "144", Continent.ASIA),
  SUDAN("SD", "SDN", "729", Continent.AFRICA),
  SURINAME("SR", "SUR", "740", Continent.SOUTH_AMERICA),
  SWAZILAND("SZ", "SWZ", "748", Continent.AFRICA),
  SWEDEN("SE", "SWE", "752", Continent.EUROPE),
  SWITZERLAND("CH", "CHE", "756", Continent.EUROPE),
  SYRIA("SY", "SYR", "760", Continent.ASIA),
  TAIWAN("TW", "TWN", "158", Continent.ASIA),
  TAJIKISTAN("TJ", "TJK", "762", Continent.ASIA),
  TANZANIA("TZ", "TZA", "834", Continent.AFRICA),
  THAILAND("TH", "THA", "764", Continent.ASIA),
  TIMOR_LESTE("TL", "TLS", "626", Continent.ASIA),
  TOGO("TG", "TGO", "768", Continent.AFRICA),
  TONGA("TO", "TON", "776", Continent.AUSTRALIA_AND_OCEANIA),
  TRINIDAD_AND_TOBAGO("TT", "TTO", "780", Continent.NORTH_AMERICA),
  TUNISIA("TN", "TUN", "788", Continent.AFRICA),
  TURKEY("TR", "TUR", "792", Continent.ASIA, Continent.EUROPE),
  TURKMENISTAN("TM", "TKM", "795", Continent.ASIA),
  TUVALU("TV", "TUV", "798", Continent.AUSTRALIA_AND_OCEANIA),
  UGANDA("UG", "UGA", "800", Continent.AFRICA),
  UKRAINE("UA", "UKR", "804", Continent.EUROPE),
  UNITED_ARAB_EMIRATES("AE", "ARE", "784", Continent.ASIA),
  UNITED_KINGDOM("GB", "GBR", "826", Continent.EUROPE),
  UNITED_STATES_OF_AMERICA("US", "USA", "840", Continent.NORTH_AMERICA),
  URUGUAY("UY", "URY", "858", Continent.SOUTH_AMERICA),
  UZBEKISTAN("UZ", "UZB", "860", Continent.ASIA),
  VANUATU("VU", "VUT", "548", Continent.AUSTRALIA_AND_OCEANIA),
  VATICAN_CITY("VA", "VAT", "336", Continent.EUROPE),
  VENEZUELA("VE", "VEN", "862", Continent.SOUTH_AMERICA),
  VIETNAM("VN", "VNM", "704", Continent.ASIA),
  YEMEN("YE", "YEM", "887", Continent.ASIA),
  ZAMBIA("ZM", "ZMB", "894", Continent.AFRICA),
  ZIMBABWE("ZW", "ZWE", "716", Continent.AFRICA),
  UNKNOWN("UK", "UKN", "999");

  /**
   * Returns a {@link Set} of all the {@link Country Countries} in the {@literal World}
   * located on the given {@link Continent}.
   *
   * @param continent {@link Continent} containing the {@link Country Countries} to return.
   * @return all {@link Country Countries} in the {@literal World} located on the given {@link Continent}.
   * @see org.cp.domain.geo.enums.Continent
   * @see org.cp.domain.geo.enums.Country
   * @see #isLocatedOnContinent(Continent)
   * @see #values()
   */
  @NullSafe
  public static Set<Country> byContinent(@Nullable Continent continent) {

    return Arrays.stream(values())
      .filter(country -> country.isLocatedOnContinent(continent))
      .collect(Collectors.toSet());
  }

  private static Optional<Country> byQueryPredicate(@NotNull Predicate<Country> countryPredicate) {

    return Arrays.stream(values())
      .filter(countryPredicate)
      .findFirst();
  }

  /**
   * Factory method used to find a {@link Country} based on the {@literal ISO 3 alphanumeric country code}.
   *
   * @param isoThreeCountryCode {@literal ISO 3 alphanumeric country code} defined by {@literal ISO 3166-3}.
   * @return a {@link Country} for the given {@literal ISO 3 alphanumeric country code}.
   * @throws IllegalArgumentException if no {@link Country} is found.
   * @see org.cp.domain.geo.enums.Country#getIsoThree()
   * @see #byQueryPredicate(Predicate)
   */
  public static @NotNull Country byIsoThree(@NotNull String isoThreeCountryCode) {

    return byQueryPredicate(country -> country.getIsoThree().equalsIgnoreCase(isoThreeCountryCode))
      .orElseThrow(() -> newIllegalArgumentException("Country for ISO 3166-3 [%s] was not found",
        isoThreeCountryCode));
  }

  /**
   * Factory method used to find a {@link Country} based on the {@literal ISO 3-digit numeric code}.
   *
   * @param isoThreeDigitNumericCountryCode {@literal ISO 3-digit numeric code} defined by {@literal ISO 3166}.
   * @return a {@link Country} for the given {@literal ISO 3-digit numeric code}.
   * @throws IllegalArgumentException if no {@link Country} is found.
   * @see org.cp.domain.geo.enums.Country#getIsoThreeDigitNumericCountryCode()
   * @see #byQueryPredicate(Predicate)
   */
  public static @NotNull Country byIsoThreeDigitNumericCountryCode(@NotNull String isoThreeDigitNumericCountryCode) {

    return byQueryPredicate(country -> country.getIsoThreeDigitNumericCountryCode()
      .equalsIgnoreCase(isoThreeDigitNumericCountryCode))
      .orElseThrow(() -> newIllegalArgumentException("Country for ISO 3-digit numeric country code [%s] was not found",
        isoThreeDigitNumericCountryCode));
  }

  /**
   * Factory method used to find a {@link Country} based on the {@literal ISO 2 alphanumeric country code}.
   *
   * @param isoTwoCountryCode {@literal ISO 2 alphanumeric country code} defined by {@literal ISO 3166-2}.
   * @return a {@link Country} for the given {@literal ISO 2 alphanumeric country code}.
   * @throws IllegalArgumentException if no {@link Country} is found.
   * @see org.cp.domain.geo.enums.Country#getIsoTwo()
   * @see #byQueryPredicate(Predicate)
   */
  public static @NotNull Country byIsoTwo(String isoTwoCountryCode) {

    return byQueryPredicate(country -> country.getIsoTwo().equalsIgnoreCase(isoTwoCountryCode))
      .orElseThrow(() -> newIllegalArgumentException("Country for ISO 3166-2 [%s] was not found",
        isoTwoCountryCode));
  }

  /**
   * Return the local {@link Country} determined from the current {@link Locale}.
   *
   * @return the {@link Country} in this {@link Locale}.
   * @see org.cp.domain.geo.enums.Country#getIsoThree()
   * @see java.util.Locale#getDefault()
   * @see java.util.Locale#getISO3Country()
   */
  public static @NotNull Country localCountry() {

    Locale defaultLocale = Locale.getDefault();

    String isoThreeLetterCode = defaultLocale.getISO3Country();

    return byIsoThree(isoThreeLetterCode);
  }

  private final Set<Continent> continents;

  private final String isoTwo;
  private final String isoThree;
  private final String isoThreeDigitNumericCountryCode;

  /**
   * Constructs a new {@link Country} located on the given {@link Continent Continents}.
   *
   * @param continents array of {@link Continent Continents} in which this {@link Country} is located.
   * @see #Country(String, String, String, Continent...)
   * @see org.cp.domain.geo.enums.Continent
   */
  Country(Continent... continents) {
    this(null, null, null, continents);
  }

  /**
   * Constructs a new {@link Country} on the given {@link Continent Continents}
   * with the {@literal ISO} country codes.
   *
   * @param isoTwo {@link String} containing the {@literal ISO 2 alphanumeric country code}.
   * @param isoThree {@link String} containing the {@literal ISO 3 alphanumeric country code}.
   * @param isoThreeDigitNumericCountryCode {@link String} containing the {@literal ISO 3-digit numeric country code}.
   * @param continents array of {@link Continent Continents} in which this {@link Country} is located.
   * @see org.cp.domain.geo.enums.Continent
   */
  Country(String isoTwo, String isoThree, String isoThreeDigitNumericCountryCode, Continent... continents) {

    this.isoTwo = isoTwo;
    this.isoThree = isoThree;
    this.isoThreeDigitNumericCountryCode = isoThreeDigitNumericCountryCode;
    this.continents = Set.of(continents);
  }

  /**
   * Returns a {@link Set} of {@link Continent Continents} in the {@literal World}
   * in which this {@link Country} is located.
   *
   * @return a {@link Set} of {@link Continent Continents} in the {@literal World}
   * in which this {@link Country} is located.
   * @see org.cp.domain.geo.enums.Continent
   * @see java.util.Set
   */
  public Set<Continent> getContinents() {
    return this.continents;
  }

  /**
   * Returns the {@literal ISO 2 alphanumeric country code} for this {@link Country}.
   *
   * @return the {@literal ISO 2 alphanumeric country code} for this {@link Country}.
   * @see #getIsoThree()
   */
  public @NotNull String getIsoTwo() {
    return this.isoTwo;
  }

  /**
   * Returns the {@literal ISO 3 alphanumeric country code} for this {@link Country}.
   *
   * @return the {@literal ISO 3 alphanumeric country code} for this {@link Country}.
   * @see java.util.Locale#getISO3Country()
   * @see #getIsoTwo()
   */
  public @NotNull String getIsoThree() {
    return this.isoThree;
  }

  /**
   * Returns the {@literal ISO 3-digit numeric country code} for this {@link Country}.
   *
   * @return the {@literal ISO 3-digit numeric country code} for this {@link Country}.
   */
  public @NotNull String getIsoThreeDigitNumericCountryCode() {
    return this.isoThreeDigitNumericCountryCode;
  }

  /**
   * Determines whether this {@link Country} is the given {@link Country}.
   *
   * @param country {@link Country} to evaluate.
   * @return a boolean value indicating whether this {@link Country} is the given {@link Country}.
   */
  private boolean isCountry(@Nullable Country country) {
    return this.equals(country);
  }

  /**
   * Determines whether this {@link Country} is the {@link Country#CANADA}.
   *
   * @return a boolean value indicating whether this {@link Country} is the {@link Country#CANADA}.
   */
  public boolean isCanada() {
    return isCountry(Country.CANADA);
  }

  /**
   * Determines whether this {@link Country} is the {@link Country#CHINA}.
   *
   * @return a boolean value indicating whether this {@link Country} is the {@link Country#CHINA}.
   */
  public boolean isChina() {
    return isCountry(Country.CHINA);
  }

  /**
   * Determines whether this {@link Country} is the {@link Country#FRANCE}.
   *
   * @return a boolean value indicating whether this {@link Country} is the {@link Country#FRANCE}.
   */
  public boolean isFrance() {
    return isCountry(Country.FRANCE);
  }

  /**
   * Determines whether this {@link Country} is the {@link Country#GERMANY}.
   *
   * @return a boolean value indicating whether this {@link Country} is the {@link Country#GERMANY}.
   */
  public boolean isGermany() {
    return isCountry(Country.GERMANY);
  }

  /**
   * Determines whether this {@link Country} is the {@link Country#ITALY}.
   *
   * @return a boolean value indicating whether this {@link Country} is the {@link Country#ITALY}.
   */
  public boolean isItaly() {
    return isCountry(Country.ITALY);
  }

  /**
   * Determines whether this {@link Country} is the {@link Country#JAPAN}.
   *
   * @return a boolean value indicating whether this {@link Country} is the {@link Country#JAPAN}.
   */
  public boolean isJapan() {
    return isCountry(Country.JAPAN);
  }

  /**
   * Determine whether this {@link Country} is located on the given {@link Continent}.
   *
   * @param continent {@link Continent} to evaluate.
   * @return a boolean value indicating whether this {@link Country} is located on the given {@link Continent}.
   * @see org.cp.domain.geo.enums.Continent
   * @see #getContinents()
   */
  @NullSafe
  public boolean isLocatedOnContinent(@Nullable Continent continent) {
    return continent != null && getContinents().contains(continent);
  }

  /**
   * Determines whether this {@link Country} is the {@link Country#RUSSIA}.
   *
   * @return a boolean value indicating whether this {@link Country} is the {@link Country#RUSSIA}.
   */
  public boolean isRussia() {
    return isCountry(Country.RUSSIA);
  }

  /**
   * Determines whether this {@link Country} is the {@link Country#SPAIN}.
   *
   * @return a boolean value indicating whether this {@link Country} is the {@link Country#SPAIN}.
   */
  public boolean isSpain() {
    return isCountry(Country.SPAIN);
  }

  /**
   * Determines whether this {@link Country} is the {@link Country#UNITED_KINGDOM}.
   *
   * @return a boolean value indicating whether this {@link Country} is the {@link Country#UNITED_KINGDOM}.
   */
  public boolean isUnitedKingdom() {
    return isCountry(Country.UNITED_KINGDOM);
  }

  /**
   * Determines whether this {@link Country} is the {@link Country#UNITED_STATES_OF_AMERICA}.
   *
   * @return a boolean value indicating whether this {@link Country} is the {@link Country#UNITED_STATES_OF_AMERICA}.
   */
  public boolean isUnitedStatesOfAmerica() {
    return isCountry(Country.UNITED_STATES_OF_AMERICA);
  }
}
