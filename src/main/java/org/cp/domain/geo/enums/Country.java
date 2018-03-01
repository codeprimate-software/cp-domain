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

import static org.cp.elements.lang.RuntimeExceptionsFactory.newIllegalArgumentException;
import static org.cp.elements.lang.RuntimeExceptionsFactory.newIllegalStateException;

import java.util.Arrays;
import java.util.Collections;
import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.cp.elements.lang.ObjectUtils;
import org.cp.elements.lang.StringUtils;
import org.cp.elements.lang.annotation.NullSafe;
import org.cp.elements.util.ArrayUtils;

/**
 * The {@link Country} enum is an enumerated type of all the Countries in the World.
 *
 * @author John Blum
 * @see org.cp.domain.geo.enums.Continent
 * @see <a href="https://en.wikipedia.org/wiki/Country_code">Country Code - Wikipedia</a>
 * @see <a href="https://en.wikipedia.org/wiki/ISO_3166-1">ISO 3166-1 - Wikipedia</a>
 * @see <a href="https://www.countries-ofthe-world.com/all-countries.html">COUNTRIES-of-the-WORLD.COM</a>
 * @see <a href="https://countrycode.org/">countrycode.org</a>
 * @since 1.0.0
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
   * Returns a {@link Set} of all the {@link Country Countries} in the World that are on the given {@link Continent}.
   *
   * @param continent {@link Continent} used to determine the {@link Set} of {@link Country Countries} to return.
   * @return all {@link Country Countries} in the World that are on the given {@link Continent}.
   * @see org.cp.domain.geo.enums.Continent
   * @see org.cp.domain.geo.enums.Country
   * @see #isOnContinent(Continent)
   * @see #values()
   */
  public static Set<Country> byContinent(Continent continent) {

    return Arrays.stream(values())
      .filter(country -> country.isOnContinent(continent))
      .collect(Collectors.toSet());
  }

  /**
   * Factory method to lookup and find a {@link Country} based on the {@literal ISO numeric-2-digit code}.
   *
   * @param isoNumericThreeDigitCode {@literal ISO numeric-3-digit code} defined by ISO 3166.
   * @return a {@link Country} for the given {@literal ISO numeric-3-digit code}.
   * @throws IllegalArgumentException if no {@link Country} is found.
   * @see org.cp.domain.geo.enums.Country#getIsoNumericThreeDigitCode()
   * @see java.lang.String
   * @see #values()
   */
  public static Country byIsoNumeric(String isoNumericThreeDigitCode) {

    return Arrays.stream(values())
      .filter(country -> country.getIsoNumericThreeDigitCode().equals(isoNumericThreeDigitCode))
      .findFirst()
      .orElseThrow(() -> newIllegalArgumentException("No Country for ISO numeric code [%s] was found",
        isoNumericThreeDigitCode));
  }

  /**
   * Factory method to lookup and find a {@link Country} based on the {@literal ISO alpha-3-letter code}.
   *
   * @param isoAlphaThreeLetterCode {@literal ISO alpha-3-letter code} defined by ISO 3166-3.
   * @return a {@link Country} for the given {@literal ISO alpha-3-letter code}.
   * @throws IllegalArgumentException if no {@link Country} is found.
   * @see org.cp.domain.geo.enums.Country#getIsoAlphaThreeLetterCode()
   * @see java.lang.String
   * @see #values()
   */
  public static Country byIsoThree(String isoAlphaThreeLetterCode) {

    return Arrays.stream(values())
      .filter(country -> country.getIsoAlphaThreeLetterCode().equals(isoAlphaThreeLetterCode))
      .findFirst()
      .orElseThrow(() -> newIllegalArgumentException("No Country for ISO 3166-3 [%s] was found",
        isoAlphaThreeLetterCode));
  }

  /**
   * Factory method to lookup and find a {@link Country} based on the {@literal ISO alpha-2-letter code}.
   *
   * @param isoAlphaTwoLetterCode {@literal ISO alpha-2-letter code} defined by ISO 3166-2.
   * @return a {@link Country} for the given {@literal ISO alpha-2-letter code}.
   * @throws IllegalArgumentException if no {@link Country} is found.
   * @see org.cp.domain.geo.enums.Country#getIsoAlphaTwoLetterCode()
   * @see java.lang.String
   * @see #values()
   */
  public static Country byIsoTwo(String isoAlphaTwoLetterCode) {

    return Arrays.stream(values())
      .filter(country -> country.getIsoAlphaTwoLetterCode().equals(isoAlphaTwoLetterCode))
      .findFirst()
      .orElseThrow(() -> newIllegalArgumentException("No Country for ISO 3166-2 [%s] was found",
        isoAlphaTwoLetterCode));
  }

  /**
   * Return the local {@link Country} determined from the current {@link Locale}.
   *
   * @return the {@link Country} in this {@link Locale}.
   * @see org.cp.domain.geo.enums.Country#getIsoAlphaThreeLetterCode()
   * @see java.util.Locale#getDefault()
   * @see java.util.Locale#getISO3Country()
   */
  public static Country localCountry() {

    String isoAlphaThreeLetterCode = StringUtils.toLowerCase(Locale.getDefault().getISO3Country());

    return Arrays.stream(Country.values())
      .filter(country ->
        ObjectUtils.equals(StringUtils.toLowerCase(country.getIsoAlphaThreeLetterCode()), isoAlphaThreeLetterCode))
      .findFirst()
      .orElseThrow(() -> newIllegalStateException("Country for ISO alpha-3-letter code [%s] was not found",
        isoAlphaThreeLetterCode));
  }

  private final Set<Continent> continents;

  private final String isoAlphaTwoLetterCode;
  private final String isoAlphaThreeLetterCode;
  private final String isoNumericThreeDigitCode;

  /**
   * Constructs a new instance of {@link Country} on the given {@link Continent Continents}.
   *
   * @param continents array of {@link Continent Continents} in which this {@link Country} resides.
   * @see #Country(String, String, String, Continent...)
   * @see org.cp.domain.geo.enums.Continent
   */
  Country(Continent... continents) {
    this(null, null, null, continents);
  }

  /**
   * Constructs a new instance of {@link Country} on the given {@link Continent Continents}.
   *
   * @param isoAlphaTwoLetterCode {@link String} containing the ISO alpha-2-letter country code.
   * @param isoAlphaThreeLetterCode {@link String} containing the ISO alpha-3-letter country code.
   * @param isoNumericThreeDigitCode {@link String} containing the ISO numeric 3 digit country code.
   * @param continents array of {@link Continent Continents} in which this {@link Country} resides.
   * @see org.cp.domain.geo.enums.Continent
   */
  Country(String isoAlphaTwoLetterCode, String isoAlphaThreeLetterCode, String isoNumericThreeDigitCode,
      Continent... continents) {

    this.isoAlphaTwoLetterCode = isoAlphaTwoLetterCode;
    this.isoAlphaThreeLetterCode = isoAlphaThreeLetterCode;
    this.isoNumericThreeDigitCode = isoNumericThreeDigitCode;
    this.continents = Collections.unmodifiableSet(new TreeSet<>(
      Arrays.asList(ArrayUtils.nullSafeArray(continents, Continent.class))));
  }

  /**
   * Returns a {@link Set} of {@link Continent Continents} in the World in which this {@link Country} resides.
   *
   * @return a {@link Set} of {@link Continent Continents} in the World in which this {@link Country} resides.
   * @see org.cp.domain.geo.enums.Continent
   * @see java.util.Set
   */
  public Set<Continent> getContinents() {
    return this.continents;
  }

  /**
   * Returns the ISO alpha 2 letter code for this {@link Country}.
   *
   * @return the ISO alpha 2 letter code for this {@link Country}.
   * @see java.lang.String
   */
  public String getIsoAlphaTwoLetterCode() {
    return this.isoAlphaTwoLetterCode;
  }

  /**
   * Returns the ISO alpha 3 letter code for this {@link Country}.
   *
   * @return the ISO alpha 3 letter code for this {@link Country}.
   * @see java.util.Locale#getISO3Country()
   * @see java.lang.String
   */
  public String getIsoAlphaThreeLetterCode() {
    return this.isoAlphaThreeLetterCode;
  }

  /**
   * Returns the ISO numeric 3 digit code for this {@link Country}.
   *
   * @return the ISO numeric 3 digit code for this {@link Country}.
   * @see java.lang.String
   */
  public String getIsoNumericThreeDigitCode() {
    return isoNumericThreeDigitCode;
  }

  /**
   * Determine whether this {@link Country} is on the given {@link Continent}.
   *
   * @param continent {@link Continent} to evaluate.
   * @return a boolean value indicating whether this {@link Country} is on the given {@link Continent}.
   * @see org.cp.domain.geo.enums.Continent
   * @see #getContinents()
   */
  @NullSafe
  public boolean isOnContinent(Continent continent) {
    return getContinents().contains(continent);
  }
}
