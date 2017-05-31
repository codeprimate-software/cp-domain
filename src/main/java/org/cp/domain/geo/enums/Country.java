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

import static java.util.Arrays.stream;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.cp.elements.lang.NullSafe;

/**
 * The {@link Country} enum is an enumerated type of all the Countries in the World.
 *
 * @author John Blum
 * @see org.cp.domain.geo.enums.Continent
 * @see <a href="https://www.countries-ofthe-world.com/all-countries.html">COUNTRIES-ofthe-WORLD.COM</a>
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public enum Country {

  AFGHANISTAN(Continent.ASIA),
  ALBANIA(Continent.EUROPE),
  ALGERIA(Continent.AFRICA),
  ANDORRA(Continent.EUROPE),
  ANGOLA(Continent.AFRICA),
  ANTIGUA_AND_BARBUDA(Continent.NORTH_AMERICA),
  ARGENTINA(Continent.SOUTH_AMERICA),
  ARMENIA(Continent.ASIA, Continent.EUROPE),
  AUSTRALIA(Continent.AUSTRALIA_AND_OCEANIA),
  AUSTRIA(Continent.EUROPE),
  AZERBAIJAN(Continent.ASIA, Continent.EUROPE),
  BAHAMAS(Continent.NORTH_AMERICA),
  BAHRAIN(Continent.ASIA),
  BANGLADESH(Continent.ASIA),
  BARBADOS(Continent.NORTH_AMERICA),
  BELARUS(Continent.EUROPE),
  BELGIUM(Continent.EUROPE),
  BELIZE(Continent.NORTH_AMERICA),
  BENIN(Continent.AFRICA),
  BHUTAN(Continent.ASIA),
  BOLIVIA(Continent.SOUTH_AMERICA),
  BOSNIA_AND_HERZEGOVINA(Continent.EUROPE),
  BOTSWANA(Continent.AFRICA),
  BRAZIL(Continent.SOUTH_AMERICA),
  BRUNEI(Continent.ASIA),
  BULGARIA(Continent.EUROPE),
  BURKINA_FASO(Continent.AFRICA),
  BURUNDI(Continent.AFRICA),
  CABO_VERDE(Continent.AFRICA),
  CAMBODIA(Continent.ASIA),
  CAMEROON(Continent.AFRICA),
  CANADA(Continent.NORTH_AMERICA),
  CENTRAL_AFRICAN_REPUBLIC(Continent.AFRICA),
  CHAD(Continent.AFRICA),
  CHILE(Continent.SOUTH_AMERICA),
  CHINA(Continent.ASIA),
  COLOMBIA(Continent.SOUTH_AMERICA),
  COMOROS(Continent.AFRICA),
  DEMOCRATIC_REPUBLIC_OF_THE_CONGO(Continent.AFRICA),
  REPUBLIC_OF_THE_CONGO(Continent.AFRICA),
  COSTA_RICA(Continent.NORTH_AMERICA),
  COTE_D_IVOIRE(Continent.AFRICA),
  CROATIA(Continent.EUROPE),
  CUBA(Continent.NORTH_AMERICA),
  CYPRUS(Continent.ASIA, Continent.EUROPE),
  CZECH_REPUBLIC(Continent.EUROPE),
  DENMARK(Continent.EUROPE),
  DJIBOUTI(Continent.AFRICA),
  DOMINICA(Continent.NORTH_AMERICA),
  DOMINICAN_REPUBLIC(Continent.NORTH_AMERICA),
  ECUADOR(Continent.SOUTH_AMERICA),
  EGYPT(Continent.AFRICA),
  EL_SALVADOR(Continent.NORTH_AMERICA),
  EQUATORIAL_GUINEA(Continent.AFRICA),
  ERITREA(Continent.AFRICA),
  ESTONIA(Continent.EUROPE),
  ETHIOPIA(Continent.AFRICA),
  FIJI(Continent.AUSTRALIA_AND_OCEANIA),
  FINLAND(Continent.EUROPE),
  FRANCE(Continent.EUROPE),
  GABON(Continent.AFRICA),
  GAMBIA(Continent.AFRICA),
  GEORGIA(Continent.ASIA, Continent.EUROPE),
  GERMANY(Continent.EUROPE),
  GHANA(Continent.AFRICA),
  GREECE(Continent.EUROPE),
  GRENADA(Continent.NORTH_AMERICA),
  GUATEMALA(Continent.NORTH_AMERICA),
  GUINEA(Continent.AFRICA),
  GUINEA_BISSAU(Continent.AFRICA),
  GUYANA(Continent.SOUTH_AMERICA),
  HAITI(Continent.NORTH_AMERICA),
  HONDURAS(Continent.NORTH_AMERICA),
  HUNGARY(Continent.EUROPE),
  ICELAND(Continent.EUROPE),
  INDIA(Continent.ASIA),
  INDONESIA(Continent.ASIA),
  IRAN(Continent.ASIA),
  IRAQ(Continent.ASIA),
  IRELAND(Continent.EUROPE),
  ISRAEL(Continent.ASIA),
  ITALY(Continent.EUROPE),
  JAMAICA(Continent.NORTH_AMERICA),
  JAPAN(Continent.ASIA),
  JORDAN(Continent.ASIA),
  KAZAKHSTAN(Continent.ASIA, Continent.EUROPE),
  KENYA(Continent.AFRICA),
  KIRIBATI(Continent.AUSTRALIA_AND_OCEANIA),
  KOSOVO(Continent.EUROPE),
  KUWAIT(Continent.ASIA),
  KYRGYZSTAN(Continent.ASIA),
  LAOS(Continent.ASIA),
  LATVIA(Continent.EUROPE),
  LEBANON(Continent.ASIA),
  LESOTHO(Continent.AFRICA),
  LIBERIA(Continent.AFRICA),
  LIBYA(Continent.AFRICA),
  LIECHTENSTEIN(Continent.EUROPE),
  LITHUANIA(Continent.EUROPE),
  LUXEMBOURG(Continent.EUROPE),
  MACEDONIA(Continent.EUROPE),
  MADAGASCAR(Continent.AFRICA),
  MALAWI(Continent.AFRICA),
  MALAYSIA(Continent.ASIA),
  MALDIVES(Continent.ASIA),
  MALI(Continent.AFRICA),
  MALTA(Continent.EUROPE),
  MARSHALL_ISLANDS(Continent.AUSTRALIA_AND_OCEANIA),
  MAURITANIA(Continent.AFRICA),
  MAURITIUS(Continent.AFRICA),
  MEXICO(Continent.NORTH_AMERICA),
  MICRONESIA(Continent.AUSTRALIA_AND_OCEANIA),
  MOLDOVA(Continent.EUROPE),
  MONACO(Continent.EUROPE),
  MONGOLIA(Continent.ASIA),
  MONTENEGRO(Continent.EUROPE),
  MOROCCO(Continent.AFRICA),
  MOZAMBIQUE(Continent.AFRICA),
  MYANMAR(Continent.ASIA),
  NAMIBIA(Continent.AFRICA),
  NAURU(Continent.AUSTRALIA_AND_OCEANIA),
  NEPAL(Continent.ASIA),
  NETHERLANDS(Continent.EUROPE),
  NEW_ZEALAND(Continent.AUSTRALIA_AND_OCEANIA),
  NICARAGUA(Continent.NORTH_AMERICA),
  NIGER(Continent.AFRICA),
  NIGERIA(Continent.AFRICA),
  NORTH_KOREA(Continent.ASIA),
  NORWAY(Continent.EUROPE),
  OMAN(Continent.ASIA),
  PAKISTAN(Continent.ASIA),
  PALAU(Continent.AUSTRALIA_AND_OCEANIA),
  PALESTINE(Continent.ASIA),
  PANAMA(Continent.NORTH_AMERICA),
  PAPUA_NEW_GUINEA(Continent.AUSTRALIA_AND_OCEANIA),
  PARAGUAY(Continent.SOUTH_AMERICA),
  PERU(Continent.SOUTH_AMERICA),
  PHILIPPINES(Continent.ASIA),
  POLAND(Continent.EUROPE),
  PORTUGAL(Continent.EUROPE),
  QATAR(Continent.ASIA),
  ROMANIA(Continent.EUROPE),
  RUSSIA(Continent.ASIA, Continent.EUROPE),
  RWANDA(Continent.AFRICA),
  SAINT_KITTS_AND_NEVIS(Continent.NORTH_AMERICA),
  SAINT_LUCIA(Continent.NORTH_AMERICA),
  SAINT_VINCENT_AND_THE_GRENADINES(Continent.NORTH_AMERICA),
  SAMOA(Continent.AUSTRALIA_AND_OCEANIA),
  SAN_MARINO(Continent.EUROPE),
  SAO_TOME_AND_PRINCIPE(Continent.AFRICA),
  SAUDI_ARABIA(Continent.ASIA),
  SENEGAL(Continent.AFRICA),
  SERBIA(Continent.EUROPE),
  SEYCHELLES(Continent.AFRICA),
  SIERRA_LEONE(Continent.AFRICA),
  SINGAPORE(Continent.ASIA),
  SLOVAKIA(Continent.EUROPE),
  SLOVENIA(Continent.EUROPE),
  SOLOMON_ISLANDS(Continent.AUSTRALIA_AND_OCEANIA),
  SOMALIA(Continent.AFRICA),
  SOUTH_AFRICA(Continent.AFRICA),
  SOUTH_KOREA(Continent.ASIA),
  SOUTH_SUDAN(Continent.AFRICA),
  SPAIN(Continent.EUROPE),
  SRI_LANKA(Continent.ASIA),
  SUDAN(Continent.AFRICA),
  SURINAME(Continent.SOUTH_AMERICA),
  SWAZILAND(Continent.AFRICA),
  SWEDEN(Continent.EUROPE),
  SWITZERLAND(Continent.EUROPE),
  SYRIA(Continent.ASIA),
  TAIWAN(Continent.ASIA),
  TAJIKISTAN(Continent.ASIA),
  TANZANIA(Continent.AFRICA),
  THAILAND(Continent.ASIA),
  TIMOR_LESTE(Continent.ASIA),
  TOGO(Continent.AFRICA),
  TONGA(Continent.AUSTRALIA_AND_OCEANIA),
  TRINIDAD_AND_TOBAGO(Continent.NORTH_AMERICA),
  TUNISIA(Continent.AFRICA),
  TURKEY(Continent.ASIA, Continent.EUROPE),
  TURKMENISTAN(Continent.ASIA),
  TUVALU(Continent.AUSTRALIA_AND_OCEANIA),
  UGANDA(Continent.AFRICA),
  UKRAINE(Continent.EUROPE),
  UNITED_ARAB_EMIRATES(Continent.ASIA),
  UNITED_KINGDOM(Continent.EUROPE),
  UNITED_STATES_OF_AMERICA(Continent.NORTH_AMERICA),
  URUGUAY(Continent.SOUTH_AMERICA),
  UZBEKISTAN(Continent.ASIA),
  VANUATU(Continent.AUSTRALIA_AND_OCEANIA),
  VATICAN_CITY(Continent.EUROPE),
  VENEZUELA(Continent.SOUTH_AMERICA),
  VIETNAM(Continent.ASIA),
  YEMEN(Continent.ASIA),
  ZAMBIA(Continent.AFRICA),
  ZIMBABWE(Continent.AFRICA),
  UNKNOWN;

  /**
   * Returns a {@link Set} of all the {@link Country Countries} in the World that are on the given {@link Continent}.
   *
   * @param continent {@link Continent} to determine the {@link Set} of {@link Country Countries} to return.
   * @return all {@link Country Countries} in the World that are on the given {@link Continent}.
   * @see org.cp.domain.geo.enums.Continent
   * @see org.cp.domain.geo.enums.Country
   * @see #values()
   */
  public static Set<Country> byContinent(Continent continent) {
    return stream(values()).filter(country -> country.isOnContinent(continent)).collect(Collectors.toSet());
  }

  private final Continent[] continents;

  /* (non-Javadoc) */
  Country(Continent... continents) {
    this.continents = continents;
  }

  /**
   * Returns a {@link Set} of {@link Continent Continents} in the World in which this {@link Country} belongs.
   *
   * @return a {@link Set} of {@link Continent Continents} in the World in which this {@link Country} belongs.
   * @see org.cp.domain.geo.enums.Continent
   */
  public Set<Continent> getContinents() {
    return Collections.unmodifiableSet(new TreeSet<>(Arrays.asList(this.continents)));
  }

  /**
   * Null-safe operation to determine whether this {@link Country} is on the given {@link Continent}.
   *
   * @param continent {@link Continent} to evaluate this {@link Country} against.
   * @return a boolean value indicating whether this {@link Country} is on the given {@link Continent}.
   * @see org.cp.domain.geo.enums.Continent
   */
  @NullSafe
  public boolean isOnContinent(Continent continent) {
    return getContinents().contains(continent);
  }
}
