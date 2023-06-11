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
package org.cp.domain.contact.phone.model.usa.support;

import static org.cp.elements.lang.RuntimeExceptionsFactory.newIllegalArgumentException;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import org.cp.domain.contact.phone.model.AreaCode;
import org.cp.domain.geo.enums.Country;
import org.cp.domain.geo.enums.State;
import org.cp.elements.lang.ObjectUtils;
import org.cp.elements.lang.annotation.NotNull;
import org.cp.elements.util.ArrayUtils;
import org.cp.elements.util.MapBuilder;

/**
 * {@literal Data Access Object (DAO)} and Repository used to map {@link State States} to {@link AreaCode AreaCodes}
 * in the {@link Country#UNITED_STATES_OF_AMERICA} and provide queries to search for and lookup
 * a {@link State} by {@link AreaCode} and to return all {@link AreaCode AreaCodes} for a given {@link State}.
 *
 * @author John Blum
 * @see org.cp.domain.contact.phone.model.AreaCode
 * @see org.cp.domain.geo.enums.State
 * @see <a href="https://www.allareacodes.com/area_code_listings_by_state.htm">AllAreaCodes.com</a>
 * @since 0.1.0
 */
public class StateAreaCodesRepository implements Iterable<Map.Entry<State, Set<AreaCode>>> {

  private static final StateAreaCodesRepository STATE_AREA_CODES_REPOSITORY = new StateAreaCodesRepository();

  private static final Map<State, Set<AreaCode>> STATE_AREA_CODE_MAPPING = MapBuilder.<State, Set<AreaCode>>newHashMap()
    .put(State.ALABAMA, setOf(205, 251, 256, 334, 659, 938))
    .put(State.ALASKA, setOf(907))
    .put(State.ARIZONA, setOf(480, 520, 602, 623, 928))
    .put(State.ARKANSAS, setOf(479, 501, 870))
    .put(State.CALIFORNIA, setOf(209, 213, 279, 310, 323, 341, 350, 408, 415, 424, 442, 510, 530, 559, 562, 619, 626,
      628, 650, 657, 661, 669, 707, 714, 747, 760, 805, 818, 820, 831, 840, 858, 909, 916, 925, 949, 951))
    .put(State.COLORADO, setOf(303, 719, 720, 970, 983))
    .put(State.CONNECTICUT, setOf(203, 475, 860, 959))
    .put(State.DELAWARE, setOf(302))
    .put(State.DISTRICT_OF_COLUMBIA, setOf(202, 771))
    .put(State.FLORIDA, setOf(239, 305, 321, 352, 386, 407, 448, 561, 656, 689, 727, 754, 772, 786, 813, 850, 863, 904,
      941, 954))
    .put(State.GEORGIA, setOf(229, 404, 470, 478, 678, 706, 762, 770, 912, 943))
    .put(State.HAWAII, setOf(808))
    .put(State.IDAHO, setOf(208, 986))
    .put(State.ILLINOIS, setOf(217, 224, 309, 312, 331, 447, 464, 618, 630, 708, 773, 779, 815, 847, 872))
    .put(State.INDIANA, setOf(219, 260, 317, 463, 574, 765, 812, 930))
    .put(State.IOWA, setOf(319, 515, 563, 641, 712))
    .put(State.KANSAS, setOf(316, 620, 785, 913))
    .put(State.KENTUCKY, setOf(270, 364, 502, 606, 859))
    .put(State.LOUISIANA, setOf(225, 318, 337, 504, 985))
    .put(State.MAINE, setOf(207))
    .put(State.MARYLAND, setOf(240, 301, 410, 443, 667))
    .put(State.MASSACHUSETTS, setOf(339, 351, 413, 508, 617, 774, 781, 857, 978))
    .put(State.MICHIGAN, setOf(231, 248, 269, 313, 517, 586, 616, 734, 810, 906, 947, 989))
    .put(State.MINNESOTA, setOf(218, 320, 507, 612, 651, 763, 952))
    .put(State.MISSISSIPPI, setOf(228, 601, 662, 769))
    .put(State.MISSOURI, setOf(314, 417, 557, 573, 636, 660, 816))
    .put(State.MONTANA, setOf(406))
    .put(State.NEBRASKA, setOf(308, 402, 531))
    .put(State.NEVADA, setOf(702, 725, 775))
    .put(State.NEW_HAMPSHIRE, setOf(603))
    .put(State.NEW_JERSEY, setOf(201, 551, 609, 640, 732, 848, 856, 862, 908, 973))
    .put(State.NEW_MEXICO, setOf(505, 575))
    .put(State.NEW_YORK, setOf(212, 315, 332, 347, 363, 516, 518, 585, 607, 631, 646, 680, 716, 718, 838, 845, 914, 917,
      929, 934))
    .put(State.NORTH_CAROLINA, setOf(252, 336, 472, 704, 743, 828, 910, 919, 980, 984))
    .put(State.NORTH_DAKOTA, setOf(701))
    .put(State.OHIO, setOf(216, 220, 234, 326, 330, 380, 419, 440, 513, 567, 614, 740, 937))
    .put(State.OKLAHOMA, setOf(405, 539, 572, 580, 918))
    .put(State.OREGON, setOf(458, 503, 541, 971))
    .put(State.PENNSYLVANIA, setOf(215, 223, 267, 272, 412, 445, 484, 570, 582, 610, 717, 724, 814, 835, 878))
    .put(State.RHODE_ISLAND, setOf(401))
    .put(State.SOUTH_CAROLINA, setOf(803, 839, 843, 854, 864))
    .put(State.SOUTH_DAKOTA, setOf(605))
    .put(State.TENNESSEE, setOf(423, 615, 629, 731, 865, 901, 931))
    .put(State.TEXAS, setOf(210, 214, 254, 281, 325, 346, 361, 409, 430, 432, 469, 512, 682, 713, 726, 737, 806, 817,
      830, 832, 903, 915, 936, 940, 945, 956, 972, 979))
    .put(State.UTAH, setOf(385, 435, 801))
    .put(State.VERMONT, setOf(802))
    .put(State.VIRGINIA, setOf(276, 434, 540, 571, 703, 757, 804, 826, 948))
    .put(State.WASHINGTON, setOf(206, 253, 360, 425, 509, 564))
    .put(State.WEST_VIRGINIA, setOf(304, 681))
    .put(State.WISCONSIN, setOf(262, 414, 534, 608, 715, 920))
    .put(State.WYOMING, setOf(307))
    .build();

  /**
   * Gets a single, shared instance of this {@link StateAreaCodesRepository}.
   *
   * @return a single, shared instance of this {@link StateAreaCodesRepository}.
   */
  public static StateAreaCodesRepository getInstance() {
    return STATE_AREA_CODES_REPOSITORY;
  }

  private static Set<AreaCode> setOf(Integer... areaCodes) {

    return Arrays.stream(ArrayUtils.nullSafeArray(areaCodes, Integer.class))
      .map(AreaCode::of)
      .collect(Collectors.toSet());
  }

  /**
   * Constructs a new {@link StateAreaCodesRepository}.
   */
  protected StateAreaCodesRepository() { }

  /**
   * Returns an unmodifiable mapping of {@link State States} to {@link AreaCode AreaCodes}.
   *
   * @return an unmodifiable mapping of {@link State States} to {@link AreaCode AreaCodes}.
   * @see java.util.Map
   */
  protected Map<State, Set<AreaCode>> getStateAreaCodeMapping() {
    return Collections.unmodifiableMap(STATE_AREA_CODE_MAPPING);
  }

  /**
   * Queries all {@link AreaCode AreaCodes} for a given, required {@link State}.
   *
   * @param state {@link State} used to query all {@link AreaCode AreaCodes} assigned to the {@link State};
   * must not be {@literal null}.
   * @return a {@link Set} of all {@link AreaCode AreaCodes} for the given {@link State}.
   * @see org.cp.domain.contact.phone.model.AreaCode
   * @see org.cp.domain.geo.enums.State
   * @see #getStateAreaCodeMapping()
   * @see java.util.Set
   */
  public Set<AreaCode> findAreaCodesBy(@NotNull State state) {
    return getStateAreaCodeMapping().get(ObjectUtils.requireObject(state, "State is required"));
  }

  /**
   * Query for {@link State} with the given {@link AreaCode}.
   *
   * @param areaCode {@link AreaCode} used to query the matching {@link State}.
   * @return the {@link State} assigned to the given {@link AreaCode}.
   * @see org.cp.domain.contact.phone.model.AreaCode
   * @see org.cp.domain.geo.enums.State
   * @see #getStateAreaCodeMapping()
   */
  public State findStateBy(AreaCode areaCode) {

    return getStateAreaCodeMapping().entrySet().stream()
      .filter(entry -> entry.getValue().contains(areaCode))
      .map(Entry::getKey)
      .findFirst()
      .orElseThrow(() -> newIllegalArgumentException("No State for AreaCode [%s] could be found", areaCode));
  }

  @Override
  public Iterator<Entry<State, Set<AreaCode>>> iterator() {
    return getStateAreaCodeMapping().entrySet().iterator();
  }
}
