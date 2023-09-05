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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.cp.elements.lang.ThrowableAssertions.assertThatUnsupportedOperationException;

import java.util.Arrays;
import java.util.Map;

import org.junit.jupiter.api.Test;

import org.cp.domain.geo.enums.State;
import org.cp.domain.geo.model.PostalCode;
import org.cp.domain.geo.model.usa.ZIP;

/**
 * Unit Tests for {@link StateZipCodesRepository}.
 *
 * @author John Blum
 * @see org.junit.jupiter.api.Test
 * @see org.cp.domain.geo.enums.State
 * @see org.cp.domain.geo.model.PostalCode
 * @see org.cp.domain.geo.model.usa.ZIP
 * @see org.cp.domain.geo.model.usa.support.StateZipCodesRepository
 * @since 0.1.0
 */
public class StateZipCodesRepositoryUnitTests {

  @Test
  public void getInstanceReturnsSingleNonNullObject() {

    StateZipCodesRepository repository = StateZipCodesRepository.getInstance();

    assertThat(repository).isNotNull();
    assertThat(repository).isSameAs(StateZipCodesRepository.getInstance());
  }

  @Test
  public void getRepositoryIsImmutable() {

    Map<State, StateZipCodesRepository.ZipCodeRegion> repository =
      StateZipCodesRepository.getInstance().getRepository();

    assertThat(repository).isNotNull();
    assertThat(repository).isNotEmpty();

    assertThatUnsupportedOperationException()
      .isThrownBy(args -> repository.put(State.OREGON,
        StateZipCodesRepository.ZipCodeRegion.of("00", "99")))
      .withNoCause();
  }

  @Test
  public void zipCodesMapToState() {

    StateZipCodesRepository repository = StateZipCodesRepository.getInstance();

    assertThat(repository.findBy(PostalCode.of("35123"))).isEqualTo(State.ALABAMA);
    assertThat(repository.findBy(PostalCode.of("99712"))).isEqualTo(State.ALASKA);
    assertThat(repository.findBy(PostalCode.of("85500"))).isEqualTo(State.ARIZONA);
    assertThat(repository.findBy(PostalCode.of("71700"))).isEqualTo(State.ARKANSAS);
    assertThat(repository.findBy(PostalCode.of("95059"))).isEqualTo(State.CALIFORNIA);
    assertThat(repository.findBy(PostalCode.of("80555"))).isEqualTo(State.COLORADO);
    assertThat(repository.findBy(PostalCode.of("06123"))).isEqualTo(State.CONNECTICUT);
    assertThat(repository.findBy(PostalCode.of("20200"))).isEqualTo(State.DISTRICT_OF_COLUMBIA);
    assertThat(repository.findBy(PostalCode.of("33123"))).isEqualTo(State.FLORIDA);
    assertThat(repository.findBy(PostalCode.of("30500"))).isEqualTo(State.GEORGIA);
    assertThat(repository.findBy(PostalCode.of("96812"))).isEqualTo(State.HAWAII);
    assertThat(repository.findBy(PostalCode.of("83512"))).isEqualTo(State.IDAHO);
    assertThat(repository.findBy(PostalCode.of("61500"))).isEqualTo(State.ILLINOIS);
    assertThat(repository.findBy(PostalCode.of("47123"))).isEqualTo(State.INDIANA);
    assertThat(repository.findBy(PostalCode.of("52003"))).isEqualTo(State.IOWA);
    assertThat(repository.findBy(PostalCode.of("66321"))).isEqualTo(State.KANSAS);
    assertThat(repository.findBy(PostalCode.of("41842"))).isEqualTo(State.KENTUCKY);
    assertThat(repository.findBy(PostalCode.of("70712"))).isEqualTo(State.LOUISIANA);
    assertThat(repository.findBy(PostalCode.of("04840"))).isEqualTo(State.MAINE);
    assertThat(repository.findBy(PostalCode.of("01248"))).isEqualTo(State.MASSACHUSETTS);
    assertThat(repository.findBy(PostalCode.of("48421"))).isEqualTo(State.MICHIGAN);
    assertThat(repository.findBy(PostalCode.of("56012"))).isEqualTo(State.MINNESOTA);
    assertThat(repository.findBy(PostalCode.of("39013"))).isEqualTo(State.MISSISSIPPI);
    assertThat(repository.findBy(PostalCode.of("64088"))).isEqualTo(State.MISSOURI);
    assertThat(repository.findBy(PostalCode.of("59123"))).isEqualTo(State.MONTANA);
    assertThat(repository.findBy(PostalCode.of("69696"))).isEqualTo(State.NEBRASKA);
    assertThat(repository.findBy(PostalCode.of("89012"))).isEqualTo(State.NEVADA);
    assertThat(repository.findBy(PostalCode.of("03612"))).isEqualTo(State.NEW_HAMPSHIRE);
    assertThat(repository.findBy(PostalCode.of("08421"))).isEqualTo(State.NEW_JERSEY);
    assertThat(repository.findBy(PostalCode.of("88421"))).isEqualTo(State.NEW_MEXICO);
    assertThat(repository.findBy(PostalCode.of("12480"))).isEqualTo(State.NEW_YORK);
    assertThat(repository.findBy(PostalCode.of("27193"))).isEqualTo(State.NORTH_CAROLINA);
    assertThat(repository.findBy(PostalCode.of("58100"))).isEqualTo(State.NORTH_DAKOTA);
    assertThat(repository.findBy(PostalCode.of("45195"))).isEqualTo(State.OHIO);
    assertThat(repository.findBy(PostalCode.of("74190"))).isEqualTo(State.OKLAHOMA);
    assertThat(repository.findBy(PostalCode.of("97205"))).isEqualTo(State.OREGON);
    assertThat(repository.findBy(PostalCode.of("19600"))).isEqualTo(State.PENNSYLVANIA);
    assertThat(repository.findBy(PostalCode.of("02999"))).isEqualTo(State.RHODE_ISLAND);
    assertThat(repository.findBy(PostalCode.of("29123"))).isEqualTo(State.SOUTH_CAROLINA);
    assertThat(repository.findBy(PostalCode.of("57888"))).isEqualTo(State.SOUTH_DAKOTA);
    assertThat(repository.findBy(PostalCode.of("37520"))).isEqualTo(State.TENNESSEE);
    assertThat(repository.findBy(PostalCode.of("76100"))).isEqualTo(State.TEXAS);
    assertThat(repository.findBy(PostalCode.of("84210"))).isEqualTo(State.UTAH);
    assertThat(repository.findBy(PostalCode.of("05678"))).isEqualTo(State.VERMONT);
    assertThat(repository.findBy(PostalCode.of("22422"))).isEqualTo(State.VIRGINIA);
    assertThat(repository.findBy(PostalCode.of("98210"))).isEqualTo(State.WASHINGTON);
    assertThat(repository.findBy(PostalCode.of("26062"))).isEqualTo(State.WEST_VIRGINIA);
    assertThat(repository.findBy(PostalCode.of("54321"))).isEqualTo(State.WISCONSIN);
    assertThat(repository.findBy(PostalCode.of("83012"))).isEqualTo(State.WYOMING);
  }

  @Test
  public void findStateByNineDigitZipCodes() {

    StateZipCodesRepository repository = StateZipCodesRepository.getInstance();

    assertThat(repository.findBy(ZIP.of("52003-3002"))).isEqualTo(State.IOWA);
    assertThat(repository.findBy(ZIP.of("59123-4321"))).isEqualTo(State.MONTANA);
    assertThat(repository.findBy(ZIP.of("59000-0000"))).isEqualTo(State.MONTANA);
    assertThat(repository.findBy(ZIP.of("59999-9999"))).isEqualTo(State.MONTANA);
    assertThat(repository.findBy(ZIP.of("97205-5515"))).isEqualTo(State.OREGON);
    assertThat(repository.findBy(ZIP.of("54321-1234"))).isEqualTo(State.WISCONSIN);
    assertThat(repository.findBy(ZIP.of("53000-0000"))).isEqualTo(State.WISCONSIN);
    assertThat(repository.findBy(ZIP.of("54999-9999"))).isEqualTo(State.WISCONSIN);
  }

  @Test
  public void findStateByNullPostalCode() {

    assertThatIllegalArgumentException()
      .isThrownBy(() -> StateZipCodesRepository.getInstance().findBy(null))
      .withMessage("PostalCode used to find a State is required")
      .withNoCause();
  }

  @Test
  public void findStateByUnknownPostalCode() {

    assertThatIllegalArgumentException()
      .isThrownBy(() -> StateZipCodesRepository.getInstance().findBy(ZIP.of("00010")))
      .withMessage("State for ZIP code [00010] not found")
      .withNoCause();
  }

  @Test
  public void newZipCodeRegionWithPrefix() {

    StateZipCodesRepository.ZipCodeRegion region = StateZipCodesRepository.ZipCodeRegion.of("01");

    assertThat(region).isNotNull();
    assertThat(region.getZipCodeStart()).isEqualTo("01");
    assertThat(region.getZipCodeEnd()).isNull();
    assertThat(region.isRange()).isFalse();
    assertThat(region.getAdjustedZipCodeEnd()).isEqualTo("999999999");
  }

  @Test
  public void newZipCodeRegionWithRange() {

    StateZipCodesRepository.ZipCodeRegion region =
      StateZipCodesRepository.ZipCodeRegion.of("01", "02");

    assertThat(region).isNotNull();
    assertThat(region.getZipCodeStart()).isEqualTo("01");
    assertThat(region.getZipCodeEnd()).isEqualTo("02");
    assertThat(region.isRange()).isTrue();
    assertThat(region.getAdjustedZipCodeEnd()).isEqualTo("029999999");
  }

  @Test
  public void newZipCodeRegionWithInvalidZipCodeStart() {

    Arrays.asList("  ", "", null).forEach(zipCodeStart ->
      assertThatIllegalArgumentException()
        .isThrownBy(() -> StateZipCodesRepository.ZipCodeRegion.of(zipCodeStart, "99"))
        .withMessage("The beginning [%s] of the ZIP code range is required", zipCodeStart)
        .withNoCause());
  }

  @Test
  public void isInRegion() {

    StateZipCodesRepository.ZipCodeRegion region =
      StateZipCodesRepository.ZipCodeRegion.of("01", "02");

    assertThat(region).isNotNull();
    assertThat(region.isRange()).isTrue();
    assertThat(region.isInRegion(PostalCode.of("01000-0000"))).isTrue();
    assertThat(region.isInRegion(PostalCode.of("010"))).isTrue();
    assertThat(region.isInRegion(PostalCode.of("011"))).isTrue();
    assertThat(region.isInRegion(PostalCode.of("019"))).isTrue();
    assertThat(region.isInRegion(PostalCode.of("02"))).isTrue();
    assertThat(region.isInRegion(PostalCode.of("020"))).isTrue();
    assertThat(region.isInRegion(PostalCode.of("021"))).isTrue();
    assertThat(region.isInRegion(PostalCode.of("022"))).isTrue();
    assertThat(region.isInRegion(PostalCode.of("02999-9999"))).isTrue();
    assertThat(region.isInRegion(PostalCode.of("02999-999999"))).isTrue();
  }

  @Test
  public void isNotInRegion() {

    StateZipCodesRepository.ZipCodeRegion region =
      StateZipCodesRepository.ZipCodeRegion.of("01", "02");

    assertThat(region).isNotNull();
    assertThat(region.isRange()).isTrue();
    assertThat(region.isInRegion(PostalCode.of("00100"))).isFalse();
    assertThat(region.isInRegion(PostalCode.of("10"))).isFalse();
    assertThat(region.isInRegion(PostalCode.of("00200"))).isFalse();
    assertThat(region.isInRegion(PostalCode.of("03"))).isFalse();
    assertThat(region.isInRegion(PostalCode.of("09"))).isFalse();
    assertThat(region.isInRegion(PostalCode.of("90"))).isFalse();
  }

  @Test
  public void startWithZipCodePrefix() {

    StateZipCodesRepository.ZipCodeRegion region =
      StateZipCodesRepository.ZipCodeRegion.of("01");

    assertThat(region).isNotNull();
    assertThat(region.isRange()).isFalse();
    assertThat(region.isInRegion(PostalCode.of("01"))).isTrue();
    assertThat(region.isInRegion(PostalCode.of("010"))).isTrue();
    assertThat(region.isInRegion(PostalCode.of("011"))).isTrue();
    assertThat(region.isInRegion(PostalCode.of("019999"))).isTrue();
    assertThat(region.isInRegion(PostalCode.of("019999-9999"))).isTrue();
  }

  @Test
  public void doesNotStartWithZipCodePrefix() {

    StateZipCodesRepository.ZipCodeRegion region =
      StateZipCodesRepository.ZipCodeRegion.of("01");

    assertThat(region).isNotNull();
    assertThat(region.isRange()).isFalse();
    assertThat(region.isInRegion(PostalCode.of("001"))).isFalse();
    assertThat(region.isInRegion(PostalCode.of("1"))).isFalse();
    assertThat(region.isInRegion(PostalCode.of("10"))).isFalse();
    assertThat(region.isInRegion(PostalCode.of("2"))).isFalse();
  }
}
